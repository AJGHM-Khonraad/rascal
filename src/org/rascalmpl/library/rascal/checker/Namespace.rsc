module rascal::checker::Namespace

import rascal::checker::Types;
import rascal::checker::ListUtils;
import rascal::checker::SubTypes;
import rascal::checker::Signature;
import rascal::checker::SymbolTable;

import List;
import Graph;
import IO;
import Set;
import Map;

import rascal::\old-syntax::Rascal;

// 
// TODOs
//
// 1. Tags can include expressions, which thus need to be typechecked. Add checking for
//    tags. UPDATE: This is actually wrong, tags don't allow expressions. However, they
//    do introduce a new namespace, so we need to store them and update the scope information.
//    For now they are not enabled in Rascal so just ignore them.
//
// 2. DONE: For each module we should create a module "signature" that includes all externally
//    visible functions, datatypes, etc. For now, we are restricted to the information
//    in just the current module.
//
// 3. DONE: This should be fully functional -- we currently maintain a small amount of state,
//    but it would be better to remove this.
//
// 4. DONE: Variables introduced in comprehensions and patterns should not have scope outside
//    of the related structures; for comprehensions this is outside of the comprehension,
//    for patterns this is outside of the action or surrounding code (used in an if,
//    it has scope inside the if). It may be easiest to just always introduce a new
//    scoping layer when entering one of these constructs, since that would then ensure
//    scope is cleaned up properly upon exiting.
//
// 5. DONE: Give each scope item a unique ID. This will make it easier to keep them unique,
//    since it is (remotely) possible that two items, such as two blocks, could each have
//    parents that looked the same.
//
// 6. DEFER: Would it be good to wrap each scope item with functions for accessors? This would
//    shield it a bit from changes to the arity of the item. Or it could be useful to
//    use the . field accessors and as much as possible eliminate the use of pattern
//    matching over these structures. UPDATE: This may be nice, but defer for now, things are
//    working as they are.
//
// 7. DONE: Would it be good to split the FunctionLayer into a layer and an item? It may be
//    nice to come up with a more general way to think about scoping, made up of layers,
//    namespaces, visibilities, and items, that we could apply to any language, including
//    Rascal.
//
// 8. DONE: Add checks to ensure non-overlaps on overloaded functions and constructors
// 8a. EXCEPT: We would still have an issue with f(int...) and f(list[int]), so we need
//    to add handling specifically for this case. See #15 below.
//
// 9. DONE: Add checks to ensure names are not repeated at the same scoping level (important:
//    this includes within a function, since names cannot be shadowed by new names defined
//    inside blocks)
//    NOTE: Support has now been added to stop lookup at function/closure boundaries; this
//          makes it easier to tell if we are violating this rule or not. This is also useful
//          in cases where we should not cross function boundaries looking for a valid name,
//          for instance with labels on statements.
//
// 10. DONE: Need to enforce proper scoping for boolean expressions; for instance,
//     A && B && C creates a scope, with names introduced in A visible in B and C
//
// 11. DONE: Introduce a new boolean expression scope inside a function argument list
//
// 12. DONE: Need to enforce name availability on all paths from a boolean expression. For
//       instance, in something like (a := p1 && b := p2) || (a := p1 && c := p3), only
//       a should be visible (i.e., in scope) outside of the || expression, since b and c
//       are only bound along one of the paths UPDATE: Done for boolean OR expressions, see
//       what other expressions need this as well. UPDATE: Also added for implication and
//		 equivalence.
//
// 13. DONE: Add support for the Reducer expression
//
// 14. Add alias expansion (since we have all the name information here) -- maybe add
//       as an Alias type, so we can keep track of the original type name used as well,
//       which would make more sense to the user than the expanded form.
//
// 15. Special case, one function ends with list[T], another with T...
//
// 16. DONE: As a follow-up from above, need to introduce the ... var into scope, so it can be
//       checked inside the checker. UPDATE: This is just the last var in the parameter list,
//	   and the handling is already added to properly return the type for the parameter as a
//	   varargs type. The checker needs to handle this properly to equate it with a list of the
//     underlying type.
//
// 17. Should resolve aliases during module imports, since we could have different aliases in
//     different imported modules, but with the same name. We don't want to inadvertently change
//     the type of an imported item.
//
// 18. DONE: Properly handle lookups of qualified names, these are in modules and are resolved from the
//     top level.
//
// 19. Add support for tags.
//
// 20. DONE: Add support for rules.
//
// 21. Add support for tests.
//
// 22. Add checking to ensure that insert, append, fail, break, and continue are all used in the
//     correct contexts.
//

// This is a hack -- this ensures the empty list is of type list[RType], not list[Void] or list[Value]
list[RType] mkEmptyList() { return tail([makeVoidType()]); }

// Same hack -- this ensures the empty list is of type list[STItemId], not list[Void] or list[Value]
list[STItemId] mkEmptySIList() { return tail([3]); }

public list[Import] getImports(Tree t) {
	if ((Module) `<Header h> <Body b>` := t) {
		switch(h) {
			case `<Tags t> module <QualifiedName n> <Import* i>` : return [il | il <- i];
			case `<Tags t> module <QualifiedName n> <ModuleParameters p> <Import* i>` : return [il | il <- i];
			default : throw "Unexpected module format";
		}
	}
	throw "getModuleName, unexpected module syntax, cannot find module name";
}

public RName getModuleName(Tree t) {
	if ((Module) `<Tags t> module <QualifiedName n> <Import* i> <Body b>` := t) {
		return convertName(n);
	} else if ((Module) `<Tags t> module <QualifiedName n> <ModuleParameters p> <Import* i> <Body b>` := t) {
		return convertName(n);
	}
	throw "getModuleName, unexpected module syntax, cannot find module name";
}

alias SignatureMap = map[Import importedModule, RSignature moduleSignature];

//
// Given a tree representing a module, build the namespace. Note that
// we only process one module at a time, although this code can
// trigger the processing of other modules that are imported.
//
public SymbolTable buildNamespace(Tree t, SignatureMap signatures) {
	SymbolTable symbolTable = justSymbolTable(pushNewTopScope(t@\loc, createNewSymbolTable()));

	// Add the module present in this tree. This is nested under TopLayer, so we can, in
	// theory, load multiple modules into the same scope information structure.
	if ((Module) `<Header h> <Body b>` := t) {
		if (`<Tags t> module <QualifiedName n> <Import* i>` := h || `<Tags t> module <QualifiedName n> <ModuleParameters p> <Import* i>` := h) {
			symbolTable = handleModuleImports(i, signatures, symbolTable);
			symbolTable = justSymbolTable(pushNewModuleScope(convertName(n), t@\loc, symbolTable));
			symbolTable = handleModuleBodyFull(b, handleModuleBodyNamesOnly(b, symbolTable));
			symbolTable = popScope(symbolTable);
		} else {
     	   throw "buildNamespace: failed to match module syntax";
		}
	} else {
        throw "buildNamespace: failed to match module syntax";
	}

	return symbolTable;
}		

// Load information from the imported modules. Note that the import list is reversed before processing;
// this is because the last module loaded "wins" in conflicts, but it's easier to model this by starting
// with the last first then handling duplicate definitions as they arise.
public SymbolTable handleModuleImports(Import* il, SignatureMap signatures, SymbolTable symbolTable) {
	list[Import] impList = reverse([imp | imp <- il]); 
	for (imp <- impList) {
		if (`import <ImportedModule im> ;` := imp || `extend <ImportedModule im> ;` := imp) {
			if (imp in signatures)
				symbolTable = handleImportedModule(im, signatures[imp], imp@\loc, symbolTable);
			else
				throw "No signature found for imported module <imp>";
		}
	}
	return symbolTable;
}

//
// TODO: Need to handle actuals, renamings -- for now, just handle the basic import scenario
//
public SymbolTable handleImportedModule(ImportedModule im, RSignature signature, loc l, SymbolTable symbolTable) {
	switch(im) {
		case `<QualifiedName qn> <ModuleActuals ma> <Renamings rn>` :
			return addImportsToScope(qn, signature, l, symbolTable);
			
		case `<QualifiedName qn> <ModuleActuals ma>` :
			return addImportsToScope(qn, signature, l, symbolTable);
			
		case `<QualifiedName qn> <Renamings rn>` :
			return addImportsToScope(qn, signature, l, symbolTable);
			
		case (ImportedModule)`<QualifiedName qn>` :
			return addImportsToScope(qn, signature, l, symbolTable);
			
		default :
			throw "Error in handleImportedModule, case not handled: <im>";
	}
}

//
// Load the imported signatures into scope. This function assumes that we are currently at the
// top level scope -- i.e., that we are not inside a module scope. A new module scope is added
// for the imported module to allow both a top-level version of the name and a module-specific
// version of the name, useful for resolving qualified names.
//
public SymbolTable addImportsToScope(QualifiedName qn, RSignature signature, loc l, SymbolTable symbolTable) {
	symbolTable = justSymbolTable(pushNewModuleScope(convertName(qn), l, symbolTable));
	
	// First, load in aliases. These may used in the definitions of ADTs (and may use ADTs, but just as names)
	// and for variables, constructors, etc. Check for duplicates, since we do not allow distinct aliases
	// to be loaded more than once (two declarations of the form alias A = int are fine). We assume
	// the signature itself is fine, though -- any errors with duplicates within a module are not
	// handled here, but should instead be caught when type checking the module being loaded. So, we
	// only do a top-level check (which would still happen to catch these), not an in-module-level check.
	// TODO: We assume here that the signature is unordered, and aliases in the signature cannot refer
	// to other aliases in the same module signature. If this is not true, we will need to order the
	// additions of the aliases based on which aliases refer to which other aliases. 
	for (AliasSigItem(aliasName,aliasType,aliasedType,at) <- signature.signatureItems) {
		symbolTable = justSymbolTable(addAliasToScope(aliasType, aliasedType, true, at, symbolTable));
		symbolTable = justSymbolTable(checkForDuplicateAliases(addAliasToTopScope(aliasType, aliasedType, true, at, symbolTable), at));
	}
	
	// Load in ADTs second, just in case they are used in the signatures of functions, constructors,
	// etc., and use aliases. There is no checking for duplicates at this point; any duplicates will be merged
	// later, so we can still maintain information about the various locations of definitions if needed.
	// Note that, at this point, we don't add anything from the ADT signature; there are instead given as
	// separate constructor items.
	for (ADTSigItem(adtName,adtType,at) <- signature.signatureItems) {
		symbolTable = justSymbolTable(addADTToScope(adtType, true, at, symbolTable));
		symbolTable = justSymbolTable(addADTToTopScope(adtType, true, at, symbolTable));
	}

	// Third, load up the other items in the signature. For named items, such as functions, we assume the
	// signature is fine, but we will have to check to see if the names are duplicates of already-defined
	// items when we load them into the top level. If we find an overlap, we ignore it -- we don't
	// register an error, we just don't add the item.
	//
	// TODO: This is potentially confusing. For instance, say we import modules M1 and M2. M1 has f(str)
	// and f(int), while M2 has f(int) and f(bool). So, there is an overlap between f(int) in M1 and M2.
	// Under the current policy, where the last wins, this means we get M1.f(str), M2.f(int), and
	// M2.f(bool). This could be confusing, though, since the user may not realize there is a clash, and
	// may expect that he is calling M1.f(int) instead. Worse, M1.f(str) may call m1.f(int), so different
	// public functions would be called depending on the caller. We should at least issue a warning here,
	// but we may want to do something more rigorous.
	for (item <- signature.signatureItems) {
		switch(item) {
			// Add a function scope layer and associated item for a function in the imported signature. Note that
			// we pop the scope after each add, since we don't want to stay inside the function scope.
			case FunctionSigItem(fn,st,at) : {
				symbolTable = justSymbolTable(pushNewFunctionScope(fn, getFunctionReturnType(st), [ <RSimpleName(""),t,at,at> | t <- getFunctionArgumentTypes(st) ], [ ], true, at, symbolTable));
				symbolTable = popScope(symbolTable);

				// This is where we check for overlap, since (like above) we assume the loaded module 
				// has already been type checked, so we don't look for overload conflicts within the import.
				// If we find an overlap, just don't import the function into the top level scope; it is
				// still available using a fully qualified name.
				if (!willFunctionOverlap(fn,st,symbolTable,symbolTable.topSTItemId)) {
					symbolTable = justSymbolTable(pushNewFunctionScopeAtTop(fn, getFunctionReturnType(st), [ <RSimpleName(""), t ,at,at> | t <- getFunctionArgumentTypes(st) ], [ ], true, at, symbolTable));
					symbolTable = popScope(symbolTable);
				} 
				// TODO: Issue a warning if the function would overlap
				// TODO: Maybe remove other overlaps from the top-level environment
			}

			// Add a variable item to the top and module-level scopes. If the name already appears in the
			// top level scope, we do not add it. 
			case VariableSigItem(vn,st,at) : {
				symbolTable = justSymbolTable(addVariableToScope(vn, st, true, at, symbolTable));

				if (! (size(getItemsForName(symbolTable, symbolTable.topSTItemId, vn)) > 0)) {
					symbolTable = justSymbolTable(addVariableToTopScope(vn, st, true, at, symbolTable));
				} 
			}

			// Add a constructor to the top and module-level scopes. We look up the ADT in the same
			// scope so we can tie the constructor to the appropriate ADT. In cases where the ADT appears
			// more than once, we just use an arbitrary item ID, since we will later consolidate them.
			case ConstructorSigItem(constructorName,adtName,constructorTypes,at) : {
				set[STItemId] possibleADTs = getTypeItemsForNameMB(symbolTable, symbolTable.currentScope, adtName);
				possibleADTs = { t | t <- possibleADTs, ADTItem(_,_,_) := symbolTable.scopeItemMap[t] };
				if (size(possibleADTs) == 0) throw "Error: Cannot find ADT <prettyPrintName(adtName)> to associate with constructor: <item>";
				STItemId adtItemId = getOneFrom(possibleADTs);
				symbolTable = justSymbolTable(addConstructorToScope(constructorName, constructorTypes, adtItemId, true, at, symbolTable));

				possibleADTs = getTypeItemsForName(symbolTable, symbolTable.topSTItemId, adtName);
				possibleADTs = { t | t <- possibleADTs, ADTItem(_,_,_) := symbolTable.scopeItemMap[t] };
				if (size(possibleADTs) == 0) throw "Error: Cannot find ADT <prettyPrintName(adtName)> to associate with constructor: <item>";
				adtItemId = getOneFrom(possibleADTs);
				// Check for overlap here; if we find an overlap, this will trigger an error, since we should not have
				// overlapping constructors and, unlike functions, we can't just take a "last in wins" approach.
				symbolTable = justSymbolTable(checkConstructorOverlap(addConstructorToTopScope(constructorName, constructorTypes, adtItemId, true, at, symbolTable),at));
			}

			// Add an annotation item to the top and module-level scopes. If an annotation of the same name 
			// already appears in the top level scope, we consider this to be an error. This is handled using
			// checkForDuplicateAnnotations. 
			case AnnotationSigItem(an,st,ot,at) : {
				symbolTable = justSymbolTable(addAnnotationToScope(an, st, ot, true, at, symbolTable)); 
				
				if (size(getAnnotationItemsForName(symbolTable, symbolTable.topSTItemId, an)) == 0) { 
					symbolTable = justSymbolTable(checkForDuplicateAnnotations(addAnnotationToTopScope(an, st, ot, true, at, symbolTable),at));
				} 
			}

			// NOTE: We do not import rule signature items; they are used by the interpreter, but do not
			// constitute part of the module signature that we must be aware of for type checking.
			// RuleSigItem(RName ruleName, loc at)

			// TODO
			// TagSigItem(RName tagName, list[RType] tagTypes, loc at)
			// case TagSigItem(tn,tt,at) : 3;
		}
	}

	return popScope(symbolTable);
}

//
// Process the individual items contained at the top level of the module.
//
public SymbolTable handleModuleBody(Body b, SymbolTable symbolTable) {
	return handleModuleBodyFull(b, handleModuleBodyNamesOnly(b, symbolTable));
}

//
// Gather the names of variables and functions. These are visible throughout the module (a variable 
// can be used in a function declared higher up in the file, for instance) so just the top-level 
// names are gathered first (i.e., we don't descend into function bodies, etc).
//
public SymbolTable handleModuleBodyNamesOnly(Body b, SymbolTable symbolTable) {
	if ((Body)`<Toplevel* ts>` := b) {
		for (Toplevel t <- ts) {
			switch(t) {
				// Alias
				case (Toplevel) `<Tags tgs> <Visibility v> alias <UserType typ> = <Type btyp> ;` :
					symbolTable = handleAliasNamesOnly(tgs,v,typ,btyp,t@\loc,symbolTable);
			}
		}

		for (Toplevel t <- ts) {
			switch(t) {
				// ADT without variants
				case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> ;` :
					symbolTable = handleAbstractADTNamesOnly(tgs,v,typ,t@\loc,symbolTable);
				
				// ADT with variants
				case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> = <{Variant "|"}+ vars> ;` :
					symbolTable = handleADTNamesOnly(tgs,v,typ,vars,t@\loc,symbolTable);
			}
		}

		for (Toplevel t <- ts) {
			switch(t) {
				// Variable declaration
				case (Toplevel) `<Tags tgs> <Visibility v> <Type typ> <{Variable ","}+ vs> ;` :
					symbolTable = handleVarItemsNamesOnly(tgs, v, typ, vs, symbolTable);
	
				// Abstract (i.e., without a body) function declaration
				case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> ;` :
					symbolTable = handleAbstractFunctionNamesOnly(tgs,v,s,t@\loc,symbolTable);
	 
	 			// Concrete (i.e., with a body) function declaration
				case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> <FunctionBody fb>` :
					symbolTable = handleFunctionNamesOnly(tgs,v,s,fb,t@\loc,symbolTable);
				
				// Annotation declaration
				case (Toplevel) `<Tags tgs> <Visibility v> anno <Type typ> <Type otyp> @ <Name n> ;` :
					symbolTable = handleAnnotationDeclarationNamesOnly(tgs,v,typ,otyp,n,t@\loc,symbolTable);
									
				// Tag declaration
				case (Toplevel) `<Tags tgs> <Visibility v> tag <Kind k> <Name n> on <{Type ","}+ typs> ;` :
					symbolTable = handleTagDeclarationNamesOnly(tgs,v,k,n,typs,t@\loc,symbolTable);
				
				// Rule declaration
				case (Toplevel) `<Tags tgs> rule <Name n> <PatternWithAction pwa> ;` :
					symbolTable = handleRuleDeclarationNamesOnly(tgs,n,pwa,t@\loc,symbolTable);
				
				// Test
				case (Toplevel) `<Test tst> ;` :
					symbolTable = handleTestNamesOnly(tst,t@\loc,symbolTable);
								
				// View
				case (Toplevel) `<Tags tgs> <Visibility v> view <Name n> <: <Name sn> = <{Alternative "|"}+ alts> ;` :
					symbolTable = handleViewNamesOnly(tgs,v,n,sn,alts,t@\loc,symbolTable);
			}
		}
	}

	// Now, consolidate ADT definitions and look for errors
	symbolTable = consolidateADTDefinitionsForLayer(symbolTable, symbolTable.currentScope, true);
	symbolTable = checkADTDefinitionsForConsistency(symbolTable);
		
	return symbolTable;
}

//
// Identify names used inside functions or in static initializers, noting type information. This pass 
// actually descends into functions, building the scope information within them as well.
//
public SymbolTable handleModuleBodyFull(Body b, SymbolTable symbolTable) {
	if ((Body)`<Toplevel* ts>` := b) {
		for (Toplevel t <- ts) {
			switch(t) {
				// Variable declaration
				case (Toplevel) `<Tags tgs> <Visibility v> <Type typ> <{Variable ","}+ vs> ;` :
					symbolTable = handleVarItems(tgs, v, typ, vs, symbolTable);
	
				// Abstract (i.e., without a body) function declaration
				case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> ;` : 
					symbolTable = handleAbstractFunction(tgs, v, s, t@\loc, symbolTable);
	 
	 			// Concrete (i.e., with a body) function declaration
				case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> <FunctionBody fb>` :
					symbolTable = handleFunction(tgs, v, s, fb, t@\loc, symbolTable);
				
				// Annotation declaration
				case (Toplevel) `<Tags tgs> <Visibility v> anno <Type typ> <Type otyp> @ <Name n> ;` :
					symbolTable = handleAnnotationDeclaration(tgs, v, typ, otyp, n, t@\loc, symbolTable);
									
				// Tag declaration
				case (Toplevel) `<Tags tgs> <Visibility v> tag <Kind k> <Name n> on <{Type ","}+ typs> ;` :
					symbolTable = handleTagDeclaration(tgs, v, k, n, typs, t@\loc, symbolTable);
				
				// Rule declaration
				case (Toplevel) `<Tags tgs> rule <Name n> <PatternWithAction pwa> ;` :
					symbolTable = handleRuleDeclaration(tgs, n, pwa, t@\loc, symbolTable);
				
				// Test
				case (Toplevel) `<Test tst> ;` :
					symbolTable = handleTest(tst, t@\loc, symbolTable);
								
				// ADT without variants
				case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> ;` :
					symbolTable = handleAbstractADT(tgs, v, typ, t@\loc, symbolTable);
				
				// ADT with variants
				case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> = <{Variant "|"}+ vars> ;` :
					symbolTable = handleADT(tgs, v, typ, vars, t@\loc, symbolTable);

				// Alias
				case (Toplevel) `<Tags tgs> <Visibility v> alias <UserType typ> = <Type btyp> ;` :
					symbolTable = handleAlias(tgs, v, typ, btyp, t@\loc, symbolTable);
								
				// View
				case (Toplevel) `<Tags tgs> <Visibility v> view <Name n> <: <Name sn> = <{Alternative "|"}+ alts> ;` :
					symbolTable = handleView(tgs, v, n, sn, alts, t@\loc, symbolTable);
				
				default: throw "handleModuleBodyFull: No match for item <t>";
			}
		}
	}
	
	return symbolTable;
}

//
// Handle variable declarations, with or without initializers
//
public SymbolTable handleVarItemsNamesOnly(Tags ts, Visibility v, Type t, {Variable ","}+ vs, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(ts, symbolTable);
	RType varType = convertType(t);
	for (vb <- vs) {
		if (`<Name n>` := vb || `<Name n> = <Expression e>` := vb) {
			if (size(getItemsForNameMB(symbolTable, symbolTable.currentScope, convertName(n))) > 0) {		
				symbolTable = addScopeError(symbolTable, n@\loc, "Duplicate declaration of name <n>");
			} 
			symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(convertName(n), varType, isPublic(v), vb@\loc, symbolTable),[<true,n@\loc>]));
		}
	}
	return symbolTable;
}

//
// Identify any names used inside variable declarations
//
public SymbolTable handleVarItems(Tags ts, Visibility v, Type t, {Variable ","}+ vs, SymbolTable symbolTable) {
	symbolTable = handleTags(ts, symbolTable);
	for (`<Name n> = <Expression e>` <- vs) symbolTable = handleExpression(e, symbolTable);
	return symbolTable;
}

//
// Handle standard function declarations (i.e., function declarations with bodies), but
// do NOT descend into the bodies
//
public SymbolTable handleFunctionNamesOnly(Tags ts, Visibility v, Signature s, FunctionBody b, loc l, SymbolTable symbolTable) {
	return handleAbstractFunctionNamesOnly(ts,v,s,l,symbolTable);		
}

//
// Handle abstract function declarations (i.e., function declarations without bodies)
//
public SymbolTable handleAbstractFunctionNamesOnly(Tags ts, Visibility v, Signature s, loc l, SymbolTable symbolTable) {
	// Add the new function into the scope and process any parameters.
	SymbolTable addFunction(Name n, RType retType, Parameters ps, list[RType] thrsTypes, bool isPublic, SymbolTable symbolTable) {
		// Get back a list of tuples representing the parameters; these will actually be added into the scope
		// in the next step
		list[tuple[RName pname, RType ptype, loc ploc, loc nloc]] params = handleParametersNamesOnly(ps, symbolTable);
 		
		// Add a new function scope, getting back the updated scope and a list of added scope IDs
		ResultTuple rt = pushNewFunctionScope(convertName(n), retType, params, thrsTypes, isPublic, l, symbolTable);

		// Add uses, checking for overlaps
		symbolTable = justSymbolTable(checkFunctionOverlap(addSTItemUses(rt,([<false,l>, <true,n@\loc>] + [<true,p.nloc> | tuple[RName pname, RType ptype, loc ploc, loc nloc] p <- params])),n@\loc));

		// Check to make sure that any type variables included in the parameters or the return type are
		// consistent.
		// TODO: Check to make sure the bounds are consistent
		// TODO: Add checking for type parameters in function parameters 
 		if (typeHasTypeVars(retType)) {
 			set[RType] retTypeVars = collectTypeVars(retType);
 			set[RName] retTypeVarNames = { getTypeVarName(tv) | tv <- retTypeVars };
 			
			for (tvn <- retTypeVarNames) {
				if (insideEnclosingFunction(symbolTable, symbolTable.currentScope) && size(getTypeVarItemsForNameMB(symbolTable, symbolTable.currentScope, tvn)) == 0) {
					symbolTable = addScopeError(symbolTable, l, "Type variable <prettyPrintName(tvn)>, included in return type, is not bound");
				}
			}
 		}
 		
		// Pop the new scope and exit
		return popScope(symbolTable);
	}

	symbolTable = handleTagsNamesOnly(ts, symbolTable);
	
	switch(s) {
		case `<Type t> <FunctionModifiers ns> <Name n> <Parameters ps>` : {
			symbolTable = addFunction(n, convertType(t), ps, mkEmptyList(), isPublic(v), symbolTable);
		}

		case `<Type t> <FunctionModifiers ns> <Name n> <Parameters ps> throws <{Type ","}+ thrs> ` : {
			symbolTable = addFunction(n, convertType(t), ps, [convertType(thrsi) | thrsi <- thrs], 
									isPublic(v), symbolTable);
		}
	}
	return symbolTable;
}

//
// Just process the tags; this function has no body, and the function header was processed already.
//
public SymbolTable handleAbstractFunction(Tags ts, Visibility v, Signature s, loc l, SymbolTable symbolTable) {
	return handleTags(ts, symbolTable);
}

//
// Handle parameter declarations. Parameters currently have no defaults, etc, so there is no other
// version of this function.
//
public list[tuple[RName pname, RType ptype, loc ploc, loc nloc]] handleParametersNamesOnly(Parameters p, SymbolTable symbolTable) {
	list[tuple[RName pname, RType ptype, loc ploc, loc nloc]] params = [];

	// Add each parameter into the scope; the current scope is the function that
	// is being processed.
	// TODO: Add type variables into environment here	
	if (`( <Formals f> )` := p) {
		if ((Formals)`<{Formal ","}* fs>` := f) {
			for (fp <- fs) {
				if ((Formal)`<Type t> <Name n>` := fp) {
					params += < convertName(n), convertType(t), fp@\loc, n@\loc >;
				} 					
			}
		}
	} else if (`( <Formals f> ... )` := p) {
		if ((Formals)`<{Formal ","}* fs>` := f) {
			for (fp <- fs) {
				if ((Formal)`<Type t> <Name n>` := fp) {
					params += < convertName(n), convertType(t), fp@\loc, n@\loc>;
				} 					
			}
			params[size(params)-1].ptype = makeVarArgsType(params[size(params)-1].ptype);
		}
	}

	return params;
}

//
// Handle standard function declarations (i.e., function declarations with bodies). The header has
// already been processed, so this just enters the scope of the header and then processes the
// function body.
//
public SymbolTable handleFunction(Tags ts, Visibility v, Signature s, FunctionBody b, loc l, SymbolTable symbolTable) {
	symbolTable = handleTags(ts, symbolTable);

	// First, get back the scope item at location l so we can switch into the proper function scope
	symbolTable = pushScope(getLayerAtLocation(l, symbolTable), symbolTable);
	
	switch(s) {
		case `<Type t> <FunctionModifiers ns> <Name n> <Parameters ps>` : {
			symbolTable = handleFunctionBody(b,symbolTable);
		}

		case `<Type t> <FunctionModifiers ns> <Name n> <Parameters ps> throws <{Type ","}+ tts> ` : {
			symbolTable = handleFunctionBody(b,symbolTable);
		}
	}
	
	return popScope(symbolTable);	
}

//
// Handle function bodies
//
public SymbolTable handleFunctionBody(FunctionBody fb, SymbolTable symbolTable) {
	if (`{ <Statement* ss> }` := fb) {
		for (s <- ss) symbolTable = handleStatement(s, symbolTable);
	} else {
		throw "handleFunctionBody, unexpected syntax for body <fb>";
	}
	return symbolTable;
}

//
// Check is visibility represents public or private
//
private bool isPublic(Visibility v) {
	return (`public` := v);
}

//
// Introduce the annotation name into the current scope. Duplicates are not allowed, so we check for them
// here and tag the name with a scope error if we find one.
//
public SymbolTable handleAnnotationDeclarationNamesOnly(Tags t, Visibility v, Type ty, Type ot, Name n, loc l, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(t, symbolTable);
	symbolTable = justSymbolTable(checkForDuplicateAnnotations(addAnnotationToScope(convertName(n),convertType(ty),convertType(ot),isPublic(v),l,symbolTable), n@\loc));
	return symbolTable;
}

// TODO: The annotation name was handled above, here we just check to make sure the types used are actually
// in scope.
public SymbolTable handleAnnotationDeclaration(Tags t, Visibility v, Type ty, Type ot, Name n, loc l, SymbolTable symbolTable) {
	return handleTags(t, symbolTable);
}

// TODO: Implement
public SymbolTable handleTagDeclaration(Tags t, Visibility v, Kind k, Name n, {Type ","}+ ts, loc l, SymbolTable symbolTable) {
	symbolTable = handleTags(t, symbolTable);
	//throw "handleTagDeclaration not yet implemented";
	return symbolTable;
}

// TODO: Implement
public SymbolTable handleTagDeclarationNamesOnly(Tags t, Visibility v, Kind k, Name n, {Type ","}+ ts, loc l, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(t, symbolTable);
	//throw "handleTagDeclarationNamesOnly not yet implemented";
	return symbolTable;
}

public SymbolTable handleRuleDeclarationNamesOnly(Tags t, Name n, PatternWithAction p, loc l, SymbolTable symbolTable) {
	return justSymbolTable(checkForDuplicateRules(addRuleToScope(convertName(n), l, handleTagsNamesOnly(t, symbolTable)), n@\loc));
}
								
public SymbolTable handleRuleDeclaration(Tags t, Name n, PatternWithAction p, loc l, SymbolTable symbolTable) {
	return handlePatternWithAction(p, handleTags(t, symbolTable));
}

// TODO: Implement
public SymbolTable handleTestNamesOnly(Test t, loc l, SymbolTable symbolTable) {
	//throw "handleTestNamesOnly not yet implemented";
	return symbolTable;
}

// TODO: Implement
public SymbolTable handleTest(Test t, loc l, SymbolTable symbolTable) {
	//throw "handleTest not yet implemented";
	return symbolTable;
}

//
// Handle abstract ADT declarations (ADT's without variants). This introduces the ADT name into scope. Note
// that duplicate ADT names are not an error; the constructors of all ADTs sharing the same name will be
// merged together, allowing them to be introduced piecemeal.
//
public SymbolTable handleAbstractADTNamesOnly(Tags ts, Visibility v, UserType adtType, loc l, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(ts, symbolTable);
	return justSymbolTable(addSTItemUses(addADTToScope(convertUserType(adtType), isPublic(v), l, symbolTable),[<true,getUserTypeRawName(adtType)@\loc>]));
}

//
// This just handles the tags; the ADT name was introduced into scope in handleAbstractADTNamesOnly, so
// there is nothing left to process at this point.
//
public SymbolTable handleAbstractADT(Tags ts, Visibility v, UserType adtType, loc l, SymbolTable symbolTable) {
	return handleTags(ts, symbolTable);
}

//
// Handle ADT declarations (ADT's with variants). This will introduce the ADT and constructor names into
// scope. It will also check for overlaps with the constructor names to ensure references to introduced
// constructors can be unambiguous.
//
public SymbolTable handleADTNamesOnly(Tags ts, Visibility v, UserType adtType, {Variant "|"}+ vars, loc l, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(ts, symbolTable);
	ResultTuple rt = addSTItemUses(addADTToScope(convertUserType(adtType), isPublic(v), l, symbolTable),[<true,getUserTypeRawName(adtType)@\loc>]);
	STItemId adtId = head(rt.addedItems);
	symbolTable = justSymbolTable(rt);

	// Process each given variant, adding it into scope	
	for (var <- vars) {
		if (`<Name n> ( <{TypeArg ","}* args> )` := var) {
			symbolTable = justSymbolTable(checkConstructorOverlap(addSTItemUses(addConstructorToScope(convertName(n), [ convertTypeArg(targ) | targ <- args ], adtId, true, l, symbolTable),[<true,n@\loc>]),n@\loc));
		}
	}
	
	return symbolTable;
}

//
// The ADT declaration is brought into scope with the last function, therefore this just
// checks the tags to make sure they are sensible but doesn't further process the
// ADT.
//
public SymbolTable handleADT(Tags ts, Visibility v, UserType adtType, {Variant "|"}+ vars, loc l, SymbolTable symbolTable) {
	return handleTags(ts, symbolTable);
}

//
// Handle alias declarations
//
// TODO: Should tag the aliased type with where it comes from
//
public SymbolTable handleAliasNamesOnly(Tags ts, Visibility v, UserType aliasType, Type aliasedType, loc l, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(ts, symbolTable);

	Name aliasRawName = getUserTypeRawName(aliasType);
	RName aliasName = convertName(aliasRawName);
	symbolTable = justSymbolTable(checkForDuplicateAliases(addSTItemUses(addAliasToScope(convertUserType(aliasType), convertType(aliasedType), isPublic(v), l, symbolTable),[<true,aliasRawName@\loc>]),aliasRawName@\loc));
	return symbolTable;
}

public SymbolTable handleAlias(Tags ts, Visibility v, UserType aliasType, Type aliasedType, loc l, SymbolTable symbolTable) {
	return handleTags(ts, symbolTable); 
}

// TODO: Implement
public SymbolTable handleViewNamesOnly(Tags ts, Visibility v, Name n, Name sn, {Alternative "|"}+ alts, loc l, SymbolTable symbolTable) {
	symbolTable = handleTagsNamesOnly(ts, symbolTable);
	//throw "handleViewNamesOnly not yet implemented";
	return symbolTable;
}

public SymbolTable handleView(Tags ts, Visibility v, Name n, Name sn, {Alternative "|"}+ alts, loc l, SymbolTable symbolTable) {
	return handleTags(ts, symbolTable);
}

//
// Handle individual statements
//
public SymbolTable handleStatement(Statement s, SymbolTable symbolTable) {
	switch(s) {
		case `solve (<{QualifiedName ","}+ vs> <Bound b>) <Statement sb>` : {
			
			for (v <- vs)
				symbolTable = addItemUses(symbolTable, getItemsForName(symbolTable, symbolTable.currentScope, convertName(v)), v@\loc);
			
			if (`; <Expression e>` := b)
				symbolTable = handleExpression(e, symbolTable);
			
			symbolTable = handleStatement(sb, symbolTable);		
		}

		case `<Label l> for (<{Expression ","}+ es>) <Statement b>` : {
			symbolTable = handleLabel(l,symbolTable);			
			symbolTable = justSymbolTable(pushNewBooleanScope(s@\loc, symbolTable));
			for (e <- es) symbolTable = handleExpression(e, symbolTable);
			symbolTable = handleStatement(b, symbolTable);
			symbolTable = popScope(symbolTable);
		}

		case `<Label l> while (<{Expression ","}+ es>) <Statement b>` : {
			symbolTable = handleLabel(l,symbolTable);			
			symbolTable = justSymbolTable(pushNewBooleanScope(s@\loc, symbolTable));
			for (e <- es) symbolTable = handleExpression(e, symbolTable);
			symbolTable = handleStatement(b, symbolTable);
			symbolTable = popScope(symbolTable);
		}

		case `<Label l> do <Statement b> while (<Expression e>);` :
			symbolTable = handleExpression(e, handleStatement(b, handleLabel(l,symbolTable)));			

		case `<Label l> if (<{Expression ","}+ es>) <Statement bt> else <Statement bf>` : {
			symbolTable = handleLabel(l,symbolTable);			
			symbolTable = justSymbolTable(pushNewBooleanScope(s@\loc, symbolTable));
			for (e <- es) symbolTable = handleExpression(e, symbolTable);
			symbolTable = handleStatement(bf, handleStatement(bt, symbolTable));
			symbolTable = popScope(symbolTable);
		}

		case `<Label l> if (<{Expression ","}+ es>) <Statement bt>` : {
			symbolTable = handleLabel(l,symbolTable);			
			symbolTable = justSymbolTable(pushNewBooleanScope(s@\loc, symbolTable));
			for (e <- es) symbolTable = handleExpression(e, symbolTable);
			symbolTable = handleStatement(bt, symbolTable);
			symbolTable = popScope(symbolTable);
		}

		case `<Label l> switch (<Expression e>) { <Case+ cs> }` : {
			symbolTable = handleExpression(e,handleLabel(l,symbolTable));						
			for (c <- cs) symbolTable = handleCase(c, symbolTable);
		}

		case (Statement)`<Label l> <Visit v>` :
			symbolTable = handleVisit(v, handleLabel(l,symbolTable));						
			
		case `<Expression e> ;` :
			symbolTable = handleExpression(e, symbolTable);

		case `<Assignable a> <Assignment op> <Statement b>` :
			symbolTable = handleStatement(b, handleAssignable(a, symbolTable));
		
		case `assert <Expression e> ;` :
			symbolTable = handleExpression(e, symbolTable);

		case `assert <Expression e> : <Expression em> ;` :
			symbolTable = handleExpression(em, handleExpression(e, symbolTable));
		
		case `return <Statement b>` : {
			symbolTable = handleStatement(b, symbolTable);
			symbolTable = markReturnType(getEnclosingFunctionType(symbolTable), s, symbolTable);
		}
		
		case `throw <Statement b>` :
			symbolTable = handleStatement(b, symbolTable);
		
		case `insert <DataTarget dt> <Statement b>` :
			symbolTable = handleStatement(b, handleTarget(dt, symbolTable));
		
		case `append <DataTarget dt> <Statement b>` :
			symbolTable = handleStatement(b, handleTarget(dt, symbolTable));
		
		case (Statement) `<Tags ts> <Visibility v> <Signature sig> <FunctionBody fb>` : {
			// First get back the function signature information, creating the scope item
			symbolTable = handleFunctionNamesOnly(ts,v,sig,fb,s@\loc,handleTagsNamesOnly(ts, symbolTable));
					
			// Now, descend into the function, processing the body
			symbolTable = handleFunction(ts,v,sig,fb,s@\loc,handleTags(ts, symbolTable));
		}
		
		case (Statement) `<Type t> <{Variable ","}+ vs> ;` :
			symbolTable = handleLocalVarItems(t,vs,symbolTable);
		
		case (Statement) `dynamic <Type t> <{Variable ","}+ vs> ;` :
			// TODO: Handle scoping of dynamics properly
			symbolTable = handleLocalVarItems(t,vs,symbolTable);
		
		case `break <Target t> ;` :
			symbolTable = handleTarget(dt, symbolTable);
		
		case `fail <Target t> ;` :
			symbolTable = handleTarget(dt, symbolTable);
		
		case `continue <Target t> ;` :
			symbolTable = handleTarget(dt, symbolTable);
		
		case `try <Statement b> <Catch+ cs>` : {
			symbolTable = handleStatement(b, symbolTable);
			for (ct <- cs) symbolTable = handleCatch(ct, symbolTable);
		}
		
		case `try <Statement b> <Catch+ cs> finally <Statement bf>` : {
			symbolTable = handleStatement(b, symbolTable);
			for (ct <- cs) symbolTable = handleCatch(ct, symbolTable);
			symbolTable = handleStatement(bf, symbolTable);
		}
		
		case `<Label l> { <Statement+ bs> }` : {
			symbolTable = handleLabel(l,symbolTable);			
			symbolTable = justSymbolTable(pushNewBlockScope(s@\loc, symbolTable));
			for (b <- bs) symbolTable = handleStatement(b,symbolTable);
			symbolTable = popScope(symbolTable);
		}
	}
	
	return symbolTable;
}

//
// Handle individual expressions (which could contain closures, for instance)
//
// TODO: String Interpolation
// TODO: Location Interpolation
// TODO: Concrete syntax
//
public SymbolTable handleExpression(Expression exp, SymbolTable symbolTable) {

	SymbolTable handleExpName(RName n, loc l, SymbolTable symbolTable) {
		if (size(getItemsForName(symbolTable, symbolTable.currentScope, n)) > 0) {		
			symbolTable = addItemUses(symbolTable, getItemsForName(symbolTable, symbolTable.currentScope, n), l);
		} else {
			symbolTable = addScopeError(symbolTable, l, "<prettyPrintName(n)> not defined before use.");
		}
		return symbolTable;
	}
	
	switch(exp) {
		// Name _
		case (Expression)`_`: 
			symbolTable = addScopeError(symbolTable, exp@\loc, "_ cannot be used as a variable name in an expression.");
		
		// Name (other than _)
		case (Expression)`<Name n>`: 
			symbolTable = handleExpName(convertName(n),n@\loc,symbolTable);
		
		// QualifiedName
		case (Expression)`<QualifiedName qn>`: 
			symbolTable = handleExpName(convertName(qn),qn@\loc,symbolTable);

		// ReifiedType
		case `<BasicType t> ( <{Expression ","}* el> )` :
			for (ei <- el) symbolTable = handleExpression(ei, symbolTable);

		// CallOrTree
		case `<Expression e1> ( <{Expression ","}* el> )` : {
			symbolTable = handleExpression(e1, symbolTable);

			// Parameters maintain their own scope for backtracking purposes
			symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
			for (ei <- el) symbolTable = handleExpression(ei, symbolTable);
			symbolTable = popScope(symbolTable);
		}

		// List
		case `[<{Expression ","}* el>]` :
			for (ei <- el) symbolTable = handleExpression(ei, symbolTable);

		// Set
		case `{<{Expression ","}* el>}` :
			for (ei <- el) symbolTable = handleExpression(ei, symbolTable);

		// Tuple, just one expression
		case (Expression) `<<Expression ei>>` :
			symbolTable = handleExpression(ei, symbolTable);

		// Tuple, more than one expression
		case `<<Expression ei>, <{Expression ","}* el>>` : {
			symbolTable = handleExpression(ei,symbolTable);
			for (ei <- el) symbolTable = handleExpression(ei, symbolTable);
		}

		// TODO: Map: Need to figure out a syntax that works for matching this
		// case ...
		//case appl(prod([],cf(sort("Expression")),no-attrs),[...]) :

		// Closure
		case `<Type t> <Parameters p> { <Statement+ ss> }` : {
			list[tuple[RName pname, RType ptype, loc ploc, loc nloc]] params = handleParametersNamesOnly(p, symbolTable);
			symbolTable = justSymbolTable(pushNewClosureScope(convertType(t), params, exp@\loc, symbolTable));
			for (s <- ss) symbolTable = handleStatement(s, symbolTable);
			symbolTable = popScope(symbolTable);
		}

		// VoidClosure
		case `<Parameters p> { <Statement* ss> }` : {
			list[tuple[RName pname, RType ptype, loc ploc, loc nloc]] params = handleParametersNamesOnly(p, symbolTable);
			symbolTable = justSymbolTable(pushNewVoidClosureScope(params, exp@\loc, symbolTable));
			for (s <- ss) symbolTable = handleStatement(s, symbolTable);
			symbolTable = popScope(symbolTable);
		}

		// NonEmptyBlock
		case `{ <Statement+ ss> }` : {
			symbolTable = justSymbolTable(pushNewBlockScope(s@\loc, symbolTable));
			for (s <- ss) symbolTable = handleStatement(s, symbolTable);
			symbolTable = popScope(symbolTable);
		}
		
		// Visit
		case (Expression) `<Label l> <Visit v>` :
			symbolTable = handleVisit(v, handleLabel(l,symbolTable));						
		
		// ParenExp
		case `(<Expression e>)` :
			symbolTable = handleExpression(e, symbolTable);

		// Range
		case `[ <Expression e1> .. <Expression e2> ]` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// StepRange
		case `[ <Expression e1>, <Expression e2> .. <Expression e3> ]` :
			symbolTable = handleExpression(e3, handleExpression(e2, handleExpression(e1, symbolTable)));

		// FieldUpdate
		// NOTE: Here we do not add uses for n, since n should be the name of a field
		// on the type e1. We will instead check this inside the type checker, since we
		// need to know the type first before we can do this.
		case `<Expression e1> [<Name n> = <Expression e2>]` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// FieldAccess
		// NOTE: Here we do not add uses for n, since n should be the name of a field
		// on the type e1. We will instead check this inside the type checker, since we
		// need to know the type first before we can do this.
		case `<Expression e1> . <Name n>` :
			symbolTable = handleExpression(e1, symbolTable);

		// FieldProject
		// NOTE: Here we do not add uses for the fields, since we need to know the type of e1
		// to check this (these are not uses of names defined in local scope). We will instead 
		// check this inside the type checker. A field must be either a name or a number.
		case `<Expression e1> < <{Field ","}+ fl> >` :
			symbolTable = handleExpression(e1, symbolTable);			

		// Subscript
		case `<Expression e1> [ <{Expression ","}+ el> ]` : {
			symbolTable = handleExpression(e1, symbolTable);
			for (e <- el) symbolTable = handleExpression(e, symbolTable);
		}

		// IsDefined
		case `<Expression e> ?` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e, symbolTable);
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Negation
		case `! <Expression e>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e, symbolTable);
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Negative
		case `- <Expression e> ` :
			symbolTable = handleExpression(e, symbolTable);

		// TransitiveReflexiveClosure
		case `<Expression e> * ` :
			symbolTable = handleExpression(e, symbolTable);

		// TransitiveClosure
		case `<Expression e> + ` :
			symbolTable = handleExpression(e, symbolTable);

		// GetAnnotation
		case `<Expression e> @ <Name n>` : {
			symbolTable = handleExpression(e, symbolTable);
			symbolTable = addItemUses(symbolTable, getAnnotationItemsForName(symbolTable, symbolTable.currentScope, convertName(n)), n@\loc);
		}

		// SetAnnotation
		case `<Expression e1> [@ <Name n> = <Expression e2>]` : {
			symbolTable = handleExpression(e2,handleExpression(e1, symbolTable));
			symbolTable = addItemUses(symbolTable, getAnnotationItemsForName(symbolTable, symbolTable.currentScope, convertName(n)), n@\loc);
		}

		// Composition
		case `<Expression e1> o <Expression e2>` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// Product
		case `<Expression e1> * <Expression e2>` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// Join
		case `<Expression e1> join <Expression e2>` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// Div
		case `<Expression e1> / <Expression e2>` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// Mod
		case `<Expression e1> % <Expression e2>` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));

		// Intersection
		case `<Expression e1> & <Expression e2>` :
			symbolTable = handleExpression(e2, handleExpression(e1, symbolTable));
		
		// Plus
		case `<Expression e1> + <Expression e2>` :
			symbolTable = handleExpression(e2,handleExpression(e1, symbolTable));

		// Minus
		case `<Expression e1> - <Expression e2>` :
			symbolTable = handleExpression(e2,handleExpression(e1, symbolTable));

		// NotIn
		case `<Expression e1> notin <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// In
		case `<Expression e1> in <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// LessThan
		case `<Expression e1> < <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// LessThanOrEq
		case `<Expression e1> <= <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// GreaterThan
		case `<Expression e1> > <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// GreaterThanOrEq
		case `<Expression e1> >= <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Equals
		case `<Expression e1> == <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// NotEquals
		case `<Expression e1> != <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// IfThenElse (Ternary)
		case `<Expression e1> ? <Expression e2> : <Expression e3>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e3, handleExpression(e2, handleExpression(e1, symbolTable)));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// IfDefinedOtherwise
		case `<Expression e1> ? <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Implication
		case `<Expression e1> ==> <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			
			// First, push a scope for the left-hand side of the or and evaluate
			// the expression there
			symbolTable = justSymbolTable(pushNewOrScope(exp@\loc, symbolTable));
			STItemId orScope1 = symbolTable.currentScope;
			symbolTable = handleExpression(e1, symbolTable);
			symbolTable = popScope(symbolTable);

			// Now, do the same for the right-hand side.
			symbolTable = justSymbolTable(pushNewOrScope(exp@\loc, symbolTable));
			STItemId orScope2 = symbolTable.currentScope;
			symbolTable = handleExpression(e2, symbolTable);
			symbolTable = popScope(symbolTable);

			// Merge the names shared by both branches of the or into the current scope
			symbolTable = mergeOrLayers(symbolTable, [orScope1, orScope2], symbolTable.currentScope);

			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Equivalence
		case `<Expression e1> <==> <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}

			// First, push a scope for the left-hand side of the or and evaluate
			// the expression there
			symbolTable = justSymbolTable(pushNewOrScope(exp@\loc, symbolTable));
			STItemId orScope1 = symbolTable.currentScope;
			symbolTable = handleExpression(e1, symbolTable);
			symbolTable = popScope(symbolTable);

			// Now, do the same for the right-hand side.
			symbolTable = justSymbolTable(pushNewOrScope(exp@\loc, symbolTable));
			STItemId orScope2 = symbolTable.currentScope;
			symbolTable = handleExpression(e2, symbolTable);
			symbolTable = popScope(symbolTable);

			// Merge the names shared by both branches of the or into the current scope
			symbolTable = mergeOrLayers(symbolTable, [orScope1, orScope2], symbolTable.currentScope);

			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// And
		case `<Expression e1> && <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handleExpression(e2, handleExpression(e1,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Or
		case `<Expression e1> || <Expression e2>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}

			// First, push a scope for the left-hand side of the or and evaluate
			// the expression there
			symbolTable = justSymbolTable(pushNewOrScope(exp@\loc, symbolTable));
			STItemId orScope1 = symbolTable.currentScope;
			symbolTable = handleExpression(e1, symbolTable);
			symbolTable = popScope(symbolTable);

			// Now, do the same for the right-hand side.
			symbolTable = justSymbolTable(pushNewOrScope(exp@\loc, symbolTable));
			STItemId orScope2 = symbolTable.currentScope;
			symbolTable = handleExpression(e2, symbolTable);
			symbolTable = popScope(symbolTable);

			// Merge the names shared by both branches of the or into the current scope
			symbolTable = mergeOrLayers(symbolTable, [orScope1, orScope2], symbolTable.currentScope);

			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}
		
		// Match
		case `<Pattern p> := <Expression e>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handlePattern(p, handleExpression(e,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// NoMatch
		case `<Pattern p> !:= <Expression e>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handlePattern(p, handleExpression(e,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}

		// Enumerator
		case `<Pattern p> <- <Expression e>` : {
			bool popAtTheEnd = false;
			if (! inBoolLayer (symbolTable)) {
				symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
				popAtTheEnd = true;
			}
			symbolTable = handlePattern(p, handleExpression(e,symbolTable));
			if (popAtTheEnd) symbolTable = popScope(symbolTable);
		}
		
		// Set Comprehension
		case (Expression) `{ <{Expression ","}+ el> | <{Expression ","}+ er> }` : {
			// Open a new boolean scope for the generators, this makes them available on the left
			symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));

			for (e <- er) symbolTable = handleExpression(e, symbolTable);
			for (e <- el) symbolTable = handleExpression(e, symbolTable);

			// Now pop the scope to take the names out of scope
			symbolTable = popScope(symbolTable);
		}

		// List Comprehension
		case (Expression) `[ <{Expression ","}+ el> | <{Expression ","}+ er> ]` : {
			// Open a new boolean scope for the generators, this makes them available on the left
			symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));

			for (e <- er) symbolTable = handleExpression(e, symbolTable);
			for (e <- el) symbolTable = handleExpression(e, symbolTable);
			
			// Now pop the scope to take the names out of scope
			symbolTable = popScope(symbolTable);
		}
		
		// Map Comprehension
		case (Expression) `( <Expression ef> : <Expression et> | <{Expression ","}+ er> )` : {
			// Open a new boolean scope for the generators, this makes them available on the left
			symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));

			for (e <- er) symbolTable = handleExpression(e, symbolTable);
			symbolTable = handleExpression(et, handleExpression(ef, symbolTable));

			// Now pop the scope to take the names out of scope
			symbolTable = popScope(symbolTable);
		}

		// Reducer
		case `( <Expression ei> | <Expression er> | <{Expression ","}+ egs> )` : {
			symbolTable = handleExpression(e1, symbolTable);
			
			// Open a new boolean scope for the generators, this makes them available in the reducer
			symbolTable = justSymbolTable(pushNewBooleanScope(exp@\loc, symbolTable));
			
			// Calculate the scope info for the generators and expressors; we add "it" as a variable automatically
			for (e <- egs) symbolTable = handleExpression(e, symbolTable);
			symbolTable = addFreshVariable(RSimpleName("it"), ei@\loc, symbolTable);
			symbolTable = handleExpression(er, symbolTable);
			
			// Switch back to the prior scope to take expression bound names and "it" out of scope
			symbolTable = popScope(symbolTable);			
		}
		
		// It
		case `it` :
			symbolTable = handleExpName(RSimpleName("it"),exp@\loc,symbolTable);
			
		// All 
		case `all ( <{Expression ","}+ egs> )` :
			for (e <- egs) symbolTable = handleExpression(e, symbolTable);

		// Any 
		case `all ( <{Expression ","}+ egs> )` :
			for (e <- egs) symbolTable = handleExpression(e, symbolTable);
	}
	
	return symbolTable;
}

//
// Handle individual cases
//
public SymbolTable handleCase(Case c, SymbolTable symbolTable) {
	switch(c) {
		case `case <PatternWithAction p>` :
			symbolTable = handlePatternWithAction(p, symbolTable);
		
		case `default : <Statement b>` :
			symbolTable = handleStatement(b, symbolTable);
	}
	
	return symbolTable;
}

public SymbolTable handleAssignable(Assignable a, SymbolTable symbolTable) {
	switch(a) {
		// Name _
		case (Assignable)`_` :
			symbolTable = addFreshAnonymousVariable(a@\loc, symbolTable);
	
		// Assignment to a variable
		case (Assignable)`<QualifiedName qn>` : {
			if (size(getItemsForName(symbolTable, symbolTable.currentScope, convertName(qn))) > 0) {		
				symbolTable = addItemUses(symbolTable, getItemsForName(symbolTable, symbolTable.currentScope, convertName(qn)), qn@\loc);
			} else {
				symbolTable = addFreshVariable(convertName(qn), qn@\loc, symbolTable);			
			}
		}
		
		// Subscript assignment
		case `<Assignable al> [ <Expression e> ]` :
			symbolTable = handleExpression(e, handleAssignable(al, symbolTable));			

		// Field assignment
		case `<Assignable al> . <Name n>` :
			symbolTable = handleAssignable(al, symbolTable);
		
		// If-defined assignment
		case `<Assignable al> ? <Expression e>` :
			symbolTable = handleExpression(e, handleAssignable(al, symbolTable));			
		
		// Annotation assignment
		case `<Assignable al> @ <Name n>` : {
			symbolTable = handleAssignable(al, symbolTable);
			symbolTable = addItemUses(symbolTable, getAnnotationItemsForName(symbolTable, symbolTable.currentScope, convertName(n)), n@\loc);
		}

		// Tuple assignable, with just one tuple element		
		case (Assignable)`< <Assignable ai> >` :
			symbolTable = handleAssignable(ai, symbolTable);

		// Tuple assignable, with multiple elements in the tuple
		case (Assignable)`< <Assignable ai>, <{Assignable ","}* al> >` : {
			symbolTable = handleAssignable(ai, symbolTable);
			for (ali <- al) symbolTable = handleAssignable(ali, symbolTable);
		}
		
		default : 
			throw "Found unhandled assignable case during namespace construction: <a>";
	}
	
	return symbolTable;
}

public SymbolTable handleLocalVarItems(Type t, {Variable ","}+ vs, SymbolTable symbolTable) {
	for (vb <- vs) {
		if ((Variable)`<Name n>` := vb || (Variable)`<Name n> = <Expression e>` := vb) {
			if (size(getItemsForNameFB(symbolTable, symbolTable.currentScope, convertName(n))) > 0)
				symbolTable = addScopeError(symbolTable, n@\loc, "Illegal redefinition of <n>.");
			symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(convertName(n), convertType(t), true, vb@\loc, symbolTable), [<true,n@\loc>])); 
		}
		
		if ((Variable)`<Name n> = <Expression e>` := vb) {		
			symbolTable = handleExpression(e, symbolTable);
		}
	}
	return symbolTable;
}

public SymbolTable handleCatch(Catch c, SymbolTable symbolTable) {
	switch(c) {
		case `catch : <Statement b>` :
			symbolTable = handleStatement(b, symbolTable);
		
		case `catch <Pattern p> : <Statement b>` :
			symbolTable = handleStatement(b, handlePattern(p, symbolTable));
	}
	
	return symbolTable;
}		

public SymbolTable handleLabel(Label l, SymbolTable symbolTable) {
	if ((Label)`<Name n> :` := l) {
		// First, check to see if this label already exists
		set[STItemId] ls = getLabelItemsForNameFB(symbolTable, symbolTable.currentScope, convertName(n));
		if (size(ls) > 0)
			symbolTable = addScopeError(symbolTable, n@\loc, "Label <n> has already been defined.");
		symbolTable = justSymbolTable(addLabelToScope(convertName(n), l@\loc, symbolTable));					
	} // else Empty label, in which case we do nothing 
	return symbolTable;
}

public SymbolTable handleVisit(Visit v, SymbolTable symbolTable) {
	if (`visit (<Expression se>) { <Case+ cs> }` := v || `<Strategy st> visit (<Expression se>) { <Case+ cs> }` := v) {
		symbolTable = handleExpression(se, symbolTable);
		for (c <- cs) symbolTable = handleCase(c, symbolTable);
	}
	return symbolTable;
}

public SymbolTable addFreshTypeVar(RType t, loc nloc, SymbolTable symbolTable) {
	RType freshType = makeInferredType(symbolTable.freshType);
	symbolTable.inferredTypeMap[symbolTable.freshType] = freshType;
	symbolTable = justSymbolTable(addTypeVariableToScope(t, symbolTable.freshType, nloc, symbolTable));
	symbolTable.freshType = symbolTable.freshType + 1;
	return symbolTable;
}

public SymbolTable addFreshTypeVarIfMissing(RType t, loc nloc, SymbolTable symbolTable) {
	if (insideEnclosingFunction(symbolTable, symbolTable.currentScope)) {
		if (size(getTypeVarItemsForNameFB(symbolTable, symbolTable.currentScope, convertName(n))) == 0) {
			symbolTable = addFreshTypeVar(t, nloc, symbolTable);		
		} 
	} else {
		if (size(getTypeVarItemsForNameMB(symbolTable, symbolTable.currentScope, convertName(n))) == 0) {		
			symbolTable = addFreshTypeVar(t, nloc, symbolTable);		
		} 
	}	
	return symbolTable;
}

public SymbolTable addFreshVariable(RName n, loc nloc, SymbolTable symbolTable) {
	RType freshType = makeInferredType(symbolTable.freshType);
	symbolTable.inferredTypeMap[symbolTable.freshType] = freshType;
	if (RSimpleName("it") := n) symbolTable.itBinder[nloc] = freshType;
	symbolTable.freshType = symbolTable.freshType + 1;
	symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(n, freshType, false, nloc, symbolTable), [<true,nloc>]));
	return symbolTable;
}

public SymbolTable addFreshAnonymousVariable(loc nloc, SymbolTable symbolTable) {
	RType freshType = makeInferredType(symbolTable.freshType);
	symbolTable.inferredTypeMap[symbolTable.freshType] = freshType;
	symbolTable.freshType = symbolTable.freshType + 1;
	symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(RSimpleName("_"), freshType, false, nloc, symbolTable), [<true,nloc>]));
	return symbolTable;
}

public SymbolTable addFreshContainerVariable(RName n, loc nloc, SymbolTable symbolTable) {
	RType freshType = makeContainerType(makeInferredType(symbolTable.freshType));
	symbolTable.inferredTypeMap[symbolTable.freshType] = freshType;
	symbolTable.freshType = symbolTable.freshType + 1;
	symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(n, freshType, false, nloc, symbolTable), [<true,nloc>]));
	return symbolTable;
}

public SymbolTable addFreshVariableWithType(RName n, loc nloc, RType rt, SymbolTable symbolTable) {
	if (RSimpleName("it") := n) symbolTable.itBinder[nloc] = rt;
	symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(n, rt, false, nloc, symbolTable), [<true,nloc>]));
	return symbolTable;
}

public SymbolTable addFreshAnonymousVariableWithType(loc nloc, RType rt, SymbolTable symbolTable) {
	symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(RSimpleName("_"), rt, false, nloc, symbolTable), [<true,nloc>]));
	return symbolTable;
}

public SymbolTable addFreshContainerVariableWithType(RName n, loc nloc, RType rt, SymbolTable symbolTable) {
	symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(n, rt, false, nloc, symbolTable), [<true,nloc>]));
	return symbolTable;
}

//
// Handle patterns
//
// TODO: Interpolation
// TODO: Maps
//
public SymbolTable handlePattern(Pattern pat, SymbolTable symbolTable) {
	SymbolTable handlePatternName(RName n, loc l, SymbolTable symbolTable) {
		if (size(getItemsForNameMB(symbolTable, symbolTable.currentScope, n)) > 0) {		
			symbolTable = addItemUses(symbolTable, getItemsForNameMB(symbolTable, symbolTable.currentScope, n), l);
		} else {
			symbolTable = addFreshVariable(n, l, symbolTable);
		}
		return symbolTable;
	}
	
	SymbolTable handleMultiPatternName(RName n, loc l, SymbolTable symbolTable) {
		if (size(getItemsForNameMB(symbolTable, symbolTable.currentScope, n)) > 0) {		
			symbolTable = addItemUses(symbolTable, getItemsForNameMB(symbolTable, symbolTable.currentScope, n), l);
		} else {
			symbolTable = addFreshContainerVariable(n, l, symbolTable);
		}
		return symbolTable;
	}
		
	SymbolTable handleTypedPatternName(RName n, RType t, loc l, loc pl, SymbolTable symbolTable) {
		if (size(getItemsForNameFB(symbolTable, symbolTable.currentScope, n)) > 0) {
			set[STItemId] conflictItems = getItemsForNameFB(symbolTable, symbolTable.currentScope, n);
			set[loc] conflictLocations = { symbolTable.scopeItemMap[si]@at | si <- conflictItems, ( (symbolTable.scopeItemMap[si]@at)?) };		
			symbolTable = addScopeError(symbolTable, l, "Illegal shadowing of already declared name <prettyPrintName(n)>; other declarations at <conflictLocations>");
		} else {
			//println("Adding typed variable pattern name for type <prettyPrintType(t)>, name <prettyPrintName(n)>");
			symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(n, t, false, pl, symbolTable), [<true,l>]));
		}
		return symbolTable;
	}	

	// println("NAMESPACE: Handling pattern <pat>");
	
	switch(pat) {
		// Name _
		case (Pattern)`_` : {
			// println("NAMESPACE: Handling name pattern <pat>");
			symbolTable = addFreshAnonymousVariable(pat@\loc, symbolTable);
		}			

		// Name other than _
		case (Pattern)`<Name n>` : {
			// println("NAMESPACE: Handling name pattern <pat>");
			symbolTable = handlePatternName(convertName(n), n@\loc, symbolTable);
		}
		
		// QualifiedName
		case (Pattern)`<QualifiedName qn>` : {
			// println("NAMESPACE: Handling qualified name pattern <pat>");
			symbolTable = handlePatternName(convertName(qn), qn@\loc, symbolTable);
		}

		// ReifiedType
		case (Pattern) `<BasicType t> ( <{Pattern ","}* pl> )` : {
			// println("NAMESPACE: Handling reified type pattern <pat>");
			for (p <- pl) symbolTable = handlePattern(p, symbolTable);
		}

		// CallOrTree
		case (Pattern) `<Pattern p1> ( <{Pattern ","}* pl> )` : {
			// println("NAMESPACE: Handling call or tree pattern <pat>");
			symbolTable = handlePatternConstructorName(p1, symbolTable);
			for (p <- pl) symbolTable = handlePattern(p, symbolTable);
		}

		// List
		case (Pattern) `[<{Pattern ","}* pl>]` : {
			// println("NAMESPACE: Handling list pattern <pat>");
			for (p <- pl) symbolTable = handlePattern(p, symbolTable);
		}

		// Set
		case (Pattern) `{<{Pattern ","}* pl>}` : {
			// println("NAMESPACE: Handling set pattern <pat>");
			for (p <- pl) symbolTable = handlePattern(p, symbolTable);
		}

		// Tuple, with just one element
		case (Pattern) `<<Pattern pi>>` : {
			// println("NAMESPACE: Handling tuple pattern <pat>");
			symbolTable = handlePattern(pi, symbolTable);
		}

		// Tuple, with multiple elements
		case (Pattern) `<<Pattern pi>, <{Pattern ","}* pl>>` : {
			// println("NAMESPACE: Handling tuple pattern <pat>");
			symbolTable = handlePattern(pi, symbolTable);
			for (p <- pl) symbolTable = handlePattern(p, symbolTable);
		}

		// Typed Variable
		case (Pattern) `<Type t> <Name n>` : {
			// println("NAMESPACE: Handling typed variable pattern <pat>");
			symbolTable = handleTypedPatternName(convertName(n),convertType(t),n@\loc,pat@\loc,symbolTable);
		}

		// Multi Variable
		case `<QualifiedName qn> *` : {
			// println("NAMESPACE: Handling multivariable pattern <pat>");
			symbolTable = handleMultiPatternName(convertName(qn), qn@\loc, symbolTable);
		}

		// Descendant
		case `/ <Pattern p>` : {
			// println("NAMESPACE: Handling descendant pattern <pat>");
			symbolTable = handlePattern(p, symbolTable);
		}

		// Variable Becomes
		case `<Name n> : <Pattern p>` : {
			// println("NAMESPACE: Handling variable becomes pattern <pat>");
			symbolTable = handlePattern(p, handlePatternName(convertName(n), n@\loc, symbolTable));
		}
		
		// Typed Variable Becomes
		case `<Type t> <Name n> : <Pattern p>` : {
			// println("NAMESPACE: Handling typed variable becomes pattern <pat>");
			symbolTable = handlePattern(p, handleTypedPatternName(convertName(n),convertType(t),n@\loc,pat@\loc,symbolTable));
		}
		
		// Guarded
		case `[ <Type t> ] <Pattern p>` : {
			// println("NAMESPACE: Handling guarded pattern <pat>");
			symbolTable = handlePattern(p, symbolTable);
		}
		
		// Anti
		case `! <Pattern p>` : {
			// println("NAMESPACE: Handling anti pattern <pat>");
			symbolTable = handlePattern(p, symbolTable);
		}
	}
	
	return symbolTable;
}

public SymbolTable handlePatternConstructorName(Pattern pat, SymbolTable symbolTable) {
	SymbolTable handlePatternName(RName n, loc l, SymbolTable symbolTable) {
		if (size(getItemsForName(symbolTable, symbolTable.currentScope, n)) > 0) {		
			symbolTable = addItemUses(symbolTable, getItemsForName(symbolTable, symbolTable.currentScope, n), l);
		} else {
			symbolTable = addScopeError(symbolTable, l, "Constructor name <prettyPrintName(n)> must be declared");
		}
		return symbolTable;
	}
	
	SymbolTable handleTypedPatternName(RName n, RType t, loc l, loc pl, SymbolTable symbolTable) {
		if (size(getItemsForNameFB(symbolTable, symbolTable.currentScope, n)) > 0) {
			set[STItemId] conflictItems = getItemsForNameFB(symbolTable, symbolTable.currentScope, n);
			set[loc] conflictLocations = { symbolTable.scopeItemMap[si]@at | si <- conflictItems, ( (symbolTable.scopeItemMap[si]@at)?) };		
			symbolTable = addScopeError(symbolTable, l, "Illegal shadowing of already declared name <prettyPrintName(n)>; other declarations at <conflictLocations>");
		} else {
			// println("Adding typed variable pattern name for type <prettyPrintType(t)>, name <prettyPrintName(n)>");
			symbolTable = justSymbolTable(addSTItemUses(addVariableToScope(n, t, false, pl, symbolTable), [<true,l>]));
		}
		return symbolTable;
	}	

	// println("NAMESPACE: Handling call or tree constructor name pattern <pat>");
	
	switch(pat) {
		// Name other than _
		case (Pattern)`<Name n>` : {
			// println("NAMESPACE: Handling name pattern <pat>");
			symbolTable = handlePatternName(convertName(n), n@\loc, symbolTable);
		}
		
		// QualifiedName
		case (Pattern)`<QualifiedName qn>` : {
			// println("NAMESPACE: Handling qualified name pattern <pat>");
			symbolTable = handlePatternName(convertName(qn), qn@\loc, symbolTable);
		}

		// Typed Variable
		case (Pattern) `<Type t> <Name n>` : {
			// println("NAMESPACE: Handling typed variable pattern <pat>");
			symbolTable = handleTypedPatternName(convertName(n),convertType(t),n@\loc,pat@\loc,symbolTable);
		}

		default : {
			symbolTable = addScopeError(symbolTable, pat@\loc, "Illegal pattern for constructor or node name");
		}
	}
	
	return symbolTable;
}

//
// Handle Pattern with Action productions
//
public SymbolTable handlePatternWithAction(PatternWithAction pwa, SymbolTable symbolTable) {
	switch(pwa) {
		case `<Pattern p> => <Expression e>` : {
			symbolTable = justSymbolTable(pushNewPatternMatchScope(pwa@\loc, symbolTable));
			symbolTable = handleExpression(e, handlePattern(p, symbolTable));
			symbolTable = popScope(symbolTable);
		}
		
		case `<Pattern p> => <Expression er> when <{Expression ","}+ es>` : {
			symbolTable = justSymbolTable(pushNewPatternMatchScope(pwa@\loc, symbolTable));
			symbolTable = handlePattern(p, symbolTable);
			for (e <- es) symbolTable = handleExpression(e, symbolTable);
			symbolTable = handleExpression(er, symbolTable);
			symbolTable = popScope(symbolTable);
		}
		
		case `<Pattern p> : <Statement s>` : {
			symbolTable = justSymbolTable(pushNewPatternMatchScope(pwa@\loc, symbolTable));
			symbolTable = handleStatement(s, handlePattern(p, symbolTable));			
			symbolTable = popScope(symbolTable);
		}
	}
	
	return symbolTable;
}

public SymbolTable handleTarget(DataTarget dt, SymbolTable symbolTable) {
	if ((DataTarget)`<Name n> :` := dt) {
		set[STItemId] items = getLabelItemsForNameFB(symbolTable, symbolTable.currentScope, convertName(n));
		if (size(items) == 1) {
			symbolTable = addItemUses(symbolTable, items, n@\loc);
		} else if (size(items) == 0) {
			symbolTable = addScopeError(symbolTable, n@\loc, "Label <n> has not been defined.");			
		} else {
			symbolTable = addScopeError(symbolTable, n@\loc, "Label <n> has multiple definitions.");
		}
		
	}
	return symbolTable;
}

public bool hasRType(SymbolTable symbolTable, loc l) {
	if (l in symbolTable.itemUses || l in symbolTable.scopeErrorMap)
		return true;
	return false;
}

data RType = RLocatedType(RType actualType, loc l);

public RType getRType(SymbolTable symbolTable, loc l) {
	set[STItemId] items = (l in symbolTable.itemUses) ? symbolTable.itemUses[l] : { };
	set[str] scopeErrors = (l in symbolTable.scopeErrorMap) ? symbolTable.scopeErrorMap[l] : { };
	
	if (size(scopeErrors) == 0) {
		if (size(items) == 0) {
			// TODO: Should be an exception
			return makeVoidType();
		} else if (size(items) == 1) {
			STItemId anid = getOneFrom(items);
			STItem stitem = getSTItem(anid, symbolTable);
			if ( isFunctionOrConstructorItem(stitem) && ((stitem@at) ?)) {
				return RLocatedType(getTypeForItem(symbolTable, anid),stitem@at);
			} else {
				return getTypeForItem(symbolTable, anid);
			}
		} else {
			set[ROverloadedType] overloads = { };
			for (sii <- items) {
				STItem stitem = getSTItem(sii, symbolTable);
				if ( (stitem@at) ?)
					overloads += ROverloadedTypeWithLoc(getTypeForItem(symbolTable, sii), stitem@at);
				else
					overloads = ROverloadedType(getTypeForItem(symbolTable, sii));
			}
			return ROverloadedType(overloads);
		}
	} else {
		return collapseFailTypes({ makeFailType(s,l) | s <- scopeErrors });
	}
}

public Tree decorateNames(Tree t, SymbolTable symbolTable) {
	set[loc] scopeErrorLocs = domain(symbolTable.scopeErrorMap);
	
	t = visit(t) {
		case `<Name n>` : {
			if (hasRType(symbolTable, n@\loc)) {
				RType rt = getRType(symbolTable, n@\loc);
				if (RLocatedType(rt2,l) := rt) {
					insert(n[@rtype = rt2[@at=l]][@link = l]);
				} else
					insert(n[@rtype = rt]);
				scopeErrorLocs = scopeErrorLocs - n@\loc;
			}
		}
		
		case `<QualifiedName qn>` : {
			if (hasRType(symbolTable, qn@\loc)) {
				RType rt = getRType(symbolTable, qn@\loc);
				if (RLocatedType(rt2,l) := rt) {
					insert(qn[@rtype = rt2[@at=l]][@link = l]);
				} else
					insert(qn[@rtype = rt]);
				scopeErrorLocs = scopeErrorLocs - n@\loc;
			}
		}
	}
	
	t = visit(t) {
		case `<Module m>` : {
			if (size(scopeErrorLocs) > 0) {
				set[str] scopeErrors = { symbolTable.scopeErrorMap[l] | l <- scopeErrorLocs };
				if ( (m@rtype)? )
					insert(m[@rtype = collapseFailTypes({ makeFailType(s,m@\loc) | s <- scopeErrors } + {m@rtype})]);
				else
					insert(m[@rtype = collapseFailTypes({ makeFailType(s,m@\loc) | s <- scopeErrors })]);
			}
		}
	}
	
	return t;
}

// TODO: Add tag handling here
public SymbolTable handleTagsNamesOnly(Tags ts, SymbolTable symbolTable) {
	return symbolTable;
}

public SymbolTable handleTags(Tags ts, SymbolTable symbolTable) {
	return symbolTable;
}

// TODO: May want to handle aliases
public SymbolTable checkADTDefinitionsForConsistency(SymbolTable symbolTable) {
	STItemId moduleLayerId = getEnclosingModule(symbolTable);
	loc moduleLoc = symbolTable.scopeItemMap[moduleLayerId]@at;
	
	// Check each ADT individually for field type consistency
	for (n <- domain(symbolTable.adtMap)) {
		map[RName fieldName, RType fieldType] fieldMap = ( );

		// First check imported constructors. If we get errors, we would rather have them on the constructors
		// defined in the current module, since they are easier to fix -- checking them later preferences the
		// types assigned to field in imported types.
		for (ci <- symbolTable.adtMap[n].consItems, ci in symbolTable.scopeRel[symbolTable.topSTItemId]) {
			if (ConstructorItem(cn,params,_,_) := symbolTable.scopeItemMap[ci]) {
				for (RNamedType(nt,nn) <- params) {
					if (nn notin fieldMap) {
						fieldMap[nn] = nt;
					} else if (nn in fieldMap && !typeEquality(fieldMap[nn],nt)) {
						symbolTable = addScopeError(symbolTable, "Constructor <prettyPrintName(cn)> of ADT <prettyPrintName(n)> redefines the type of field <prettyPrintName(nn)> from <prettyPrintType(fieldMap[nn])> to <prettyPrintType(nt)>", moduleLoc);
					}
				}				
			} else {
				throw "checkADTDefinitionsForConsistency, unexpected constructor item <symbolTable.scopeItemMap[ci]>";
			}
		}
		
		// Now check the fields on the ADTs defined in the current module.
		// TODO: May be good to refactor out identical checking code
		for (ci <- symbolTable.adtMap[n].consItems, ci in symbolTable.scopeRel[moduleLayerId]) {
			if (ConstructorItem(cn,params,_,_) := symbolTable.scopeItemMap[ci]) {
				for (RNamedType(nt,nn) <- params) {
					if (nn notin fieldMap) {
						fieldMap[nn] = nt;
					} else if (nn in fieldMap && !typeEquality(fieldMap[nn],nt)) {
						symbolTable = addScopeError(symbolTable, "Constructor <prettyPrintName(cn)> of ADT <prettyPrintName(n)> redefines the type of field <prettyPrintName(nn)> from <prettyPrintType(fieldMap[nn])> to <prettyPrintType(nt)>", moduleLoc);
					}
				}				
			} else {
				throw "checkADTDefinitionsForConsistency, unexpected constructor item <symbolTable.scopeItemMap[ci]>";
			}
		}
	}
	
	return symbolTable;	
}

// NOTE: The code in ExampleGraph appears to be out of date, so this doesn't
// appear to work. Look at other ways to visualize.
//public void showScope(SymbolTable symbolTable, int w, int h) {
//	// First, create a node for each item in the scope info
//	nodes = [ box([id("<n>"), width(20), height(20), fillColor("lightblue")], text("<si>")) | n <- domain(symbolTable.scopeItemMap), si := symbolTable.scopeItemMap[n]];
//
//	// Now, create the edges based on the relation
//	edges = [ edge("<f>", "<t>") | < f, t > <- symbolTable.scopeRel];
//
//	// Finally, render
//	render(graph([width(w), height(h)], nodes, edges));

