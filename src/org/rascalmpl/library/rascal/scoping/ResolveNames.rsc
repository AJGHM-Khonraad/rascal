@bootstrapParser
module rascal::scoping::ResolveNames

import List;
import Graph;
import IO;
import Set;
import Map;
import ParseTree;
import Message;

import rascal::checker::ListUtils;
import rascal::checker::TreeUtils;
import rascal::types::Types;
import rascal::types::SubTypes;
import rascal::types::TypeSignatures;
import rascal::checker::Annotations;
import rascal::scoping::SymbolTable;
import rascal::scoping::ScopedTypes;

import rascal::syntax::RascalRascal;

// 
// TODOs
//
// 1. Tags can include expressions, which thus need to be typechecked. Add checking for
//    tags. UPDATE: This is actually wrong, tags don't allow expressions. However, they
//    do introduce a new namespace, so we need to store them and update the scope information.
//    For now they are not enabled in Rascal so just ignore them.
//
// 2. Should resolve aliases during module imports, since we could have different aliases in
//    different imported modules, but with the same name. We don't want to inadvertently change
//    the type of an imported item.
//
// 3. Add checking to ensure that insert, append, fail, break, and continue are all used in the
//     correct contexts.
//
// 4. See what extra can be done here to support the new function declaration styles, including
//    parameters as formal parameter lists.
//
// 5. Add support for the extends method of module importation

//
// This is a hack -- this ensures the empty list is of type list[RType], not list[Void] or list[Value]
//
list[RType] mkEmptyList() { return tail([makeVoidType()]); }

//
// Same hack -- this ensures the empty list is of type list[ItemId], not list[Void] or list[Value]
//
list[ItemId] mkEmptySIList() { return tail([3]); }

//
// Run the name resolution pass over a tree.
//
public Tree resolveTree(Tree t) {
    <t2, st> = resolveTreeAux(t,true);
    return t2;
}

public Tree resolveTreeNoTagging(Tree t) {
    <t2, st> = resolveTreeAux(t,false);
    return t2;
}

public tuple[Tree,STBuilder] resolveTreeAux(Tree t, bool addNames) {
    println("NAME RESOLVER: Getting Imports for Module");
    list[Import] imports = getImports(t);
    println("NAME RESOLVER: Got Imports");
    
    println("NAME RESOLVER: Generating Signature Map");
    SignatureMap sigMap = populateSignatureMap(imports);
    println("NAME RESOLVER: Generated Signature Map");
    
    println("NAME RESOLVER: Generating Symbol Table"); 
    STBuilder st = buildTable(t, sigMap);
    println("NAME RESOLVER: Generated Symbol Table");
    
    if (addNames) {
        println("NAME RESOLVER: Associating Scope Information with Names");
        t = addNamesToTree(st,t);
        println("NAME RESOLVER: Associated Scope Information with Names");
    }
    
    println("NAME RESOLVER: Adding Information for Scope Errors");
    if (size(st.messages) > 0) 
        t = t[@messages = st.messages<1>];
    println("NAME RESOLVER: Added Information for Scope Errors");

    return <t,st>;     
}

//
// Using the information gathered in the symbol table, add IDs to each name indicating which
// symbol table item(s) the name points to
//
// TODO: Removing qualifiers causes an error during filtering. This needs to be fixed (I'm
// looking at you, Jurgen!)
//
public Tree addNamesToTree(STBuilder stBuilder, Tree t) {
    loc generateLink(ItemId id) {
        return stBuilder.scopeItemMap[id].definedAt;
    }
    
    set[loc] generateLinks(set[ItemId] ids) {
    	return { stBuilder.scopeItemMap[id].definedAt | id <- ids };
    }
    
    str generateDocString(set[ItemId] ids) {
        str result = "";
        if (size(ids) == 1) {
            result = prettyPrintSIWLoc(stBuilder, stBuilder.scopeItemMap[getOneFrom(ids)]);
        } else {
            result = "Multiple Options:\n";
// TODO: File bug report, this should work            
//            result += joinList(toList({ stBuilder.scopeItemMap[id] | id <- ids }), prettyPrintSIWLoc, "\n", "");
            list[str] pp = [ prettyPrintSIWLoc(stBuilder, stBuilder.scopeItemMap[id]) | id <- ids ];
            result += joinList(pp, str(str s) { return s; }, "\n", "");             
        }
        
        return result;
    }
    
    Tree annotateNode(Tree tn) {
        set[ItemId] ids = stBuilder.itemUses[tn@\loc];
        if (size(ids) > 1)
            return tn[@nameIds = ids][@doc = generateDocString(ids)][@links = generateLinks(ids)];
        else if (size(ids) == 1)
        	return tn[@nameIds = ids][@doc = generateDocString(ids)][@link = generateLink(getOneFrom(ids))];
        else
            return tn;
    }
    
    return visit(t) {
        case tn:appl(prod(_, sort("Name"), _), _) :
            if (tn@\loc in stBuilder.itemUses<0>)
                insert(annotateNode(tn));
                
        case tn:appl(prod(_, sort("QualifiedName"), _), _) :
            if (tn@\loc in stBuilder.itemUses<0>)
                insert(annotateNode(tn));

//        case (Name)`<Name n>` :
//            if (n@\loc in stBuilder.itemUses)
//                insert(annotateNode(n));

//        case (QualifiedName)`<QualifiedName qn>` :
//            if (qn@\loc in stBuilder.itemUses)
//                insert(annotateNode(qn));

//        case (Pattern)`<Name n>` :
//            if (n@\loc in stBuilder.itemUses)
//                insert(annotateNode(n,n@\loc));
    
//        case (Pattern)`<QualifiedName qn>` :
//            if (qn@\loc in stBuilder.itemUses)
//                insert(annotateNode(qn,qn@\loc));
                
//        case (UserType)`<QualifiedName qn>` :
//            if (qn@\loc in stBuilder.itemUses)
//                insert(annotateNode(qn,qn@\loc));

//        case (Assignable)`<QualifiedName qn>` :
//            if (qn@\loc in stBuilder.itemUses)
//                insert(annotateNode(qn,qn@\loc));
    };
}

//
// Retrieve the list of imports from the module
//
public list[Import] getImports(Tree t) {
    if ((Module) `<Header h> <Body b>` := t) {
        switch(h) {
            case (Header)`<Tags t> module <QualifiedName n> <Import* i>` : 
                return [il | il <- i];
            case (Header)`<Tags t> module <QualifiedName n> <ModuleParameters p> <Import* i>` : 
                return [il | il <- i];
            default : 
                throw "Unexpected module format";
        }
    }
    throw "getModuleName, unexpected module syntax, cannot find module name";
}

//
// Get the name of the module
//
public RName getModuleName(Tree t) {
    if ((Module) `<Tags t> module <QualifiedName qn> <Import* i> <Body b>` := t)
        return convertName(qn);
    else if ((Module) `<Tags t> module <QualifiedName qn> <ModuleParameters p> <Import* i> <Body b>` := t)
        return convertName(qn);
    throw "getModuleName, unexpected module syntax, cannot find module name";
}

//
// Given a tree representing a module, build the namespace. Note that
// we only process one module at a time, although this code can
// trigger the processing of other modules that are imported.
//
public STBuilder buildTable(Tree t, SignatureMap signatures) {
    // Create the new symbol table, including pushing the top layer
    STBuilder stBuilder = justSTBuilder(pushNewTopScope(createNewSTBuilder()));

    // Add the module present in this tree. This also handles loading the
    // modules imported by this module. Each module is attached under the
    // top layer.
    if ((Module) `<Header h> <Body b>` := t) {
        if ((Header)`<Tags tg> module <QualifiedName qn> <Import* i>` := h || (Header)`<Tags tg> module <QualifiedName qn> <ModuleParameters p> <Import* i>` := h) {
            stBuilder = handleModuleImports(i, signatures, stBuilder);
            stBuilder = justSTBuilder(pushNewModuleScope(convertName(qn), t@\loc, stBuilder));
            stBuilder = handleModuleBodyFull(b, handleModuleBodyNamesOnly(b, stBuilder));
            stBuilder = popScope(stBuilder);
        } else {
            throw "buildTable: failed to match module syntax";
        }
    } else {
        throw "buildTable: failed to match module syntax";
    }

    // NOTE: We remain inside the top scope, we don't pop that when we are done.
    return stBuilder;
}		

//
// Load information from the imported modules. This also checks for conflicts in the loaded information,
// generating scope errors where appropriate.
//
public STBuilder handleModuleImports(Import* il, SignatureMap signatures, STBuilder stBuilder) {
    list[Import] impList = [imp | imp <- il];

    for (imp <- impList) {
        if ((Import)`import <ImportedModule im> ;` := imp || (Import)`extend <ImportedModule im> ;` := imp) {
            if (imp in signatures)
                stBuilder = importModuleTypes(im, signatures[imp], imp@\loc, stBuilder);
            else
                stBuilder = addScopeError(stBuilder, imp@\loc, "Could not build signature for imported module <getNameOfImportedModule(im)>");
        }
    }

    for (imp <- impList) {
        if ((Import)`import <ImportedModule im> ;` := imp || (Import)`extend <ImportedModule im> ;` := imp) {
            if (imp in signatures)
                stBuilder = importModuleItems(im, signatures[imp], imp@\loc, stBuilder);
        }
    }

	return stBuilder;
}

public STBuilder importModuleTypes(ImportedModule im, RSignature signature, loc l, STBuilder stBuilder) {
    if ((ImportedModule)`<QualifiedName qn> <ModuleActuals ma> <Renamings rn>` := im ||
        (ImportedModule)`<QualifiedName qn> <ModuleActuals ma>` := im ||
        (ImportedModule)`<QualifiedName qn> <Renamings rn>` := im ||
        (ImportedModule)`<QualifiedName qn>` := im) {
        return addImportedTypesToScope(qn, signature, l, stBuilder);
    } else {
        throw "Error in importModuleTypes, case not handled: <im>";
    }
}

public STBuilder importModuleItems(ImportedModule im, RSignature signature, loc l, STBuilder stBuilder) {
    if ((ImportedModule)`<QualifiedName qn> <ModuleActuals ma> <Renamings rn>` := im ||
        (ImportedModule)`<QualifiedName qn> <ModuleActuals ma>` := im ||
        (ImportedModule)`<QualifiedName qn> <Renamings rn>` := im ||
        (ImportedModule)`<QualifiedName qn>` := im) {
        return addImportedItemsToScope(qn, signature, l, stBuilder);
    } else {
        throw "Error in importModuleItems, case not handled: <im>";
    }
}

//
// Add imported types into the scope, based on the given signature.
//
public STBuilder addImportedTypesToScope(QualifiedName qn, RSignature signature, loc l, STBuilder stBuilder) {
    stBuilder = justSTBuilder(pushNewModuleScope(convertName(qn), l, stBuilder));

    for (AliasSigItem(aliasName,aliasType,at) <- signature.signatureItems) {
        stBuilder = justSTBuilder(addAliasToScope(aliasType, true, at, stBuilder));
        // TODO: Add duplicate checking again later
        // stBuilder = justSTBuilder(checkForDuplicateAliases(addAliasToTopScope(aliasType, true, at, stBuilder), at));
        stBuilder = justSTBuilder(addAliasToTopScope(aliasType, true, at, stBuilder));
    }

    for (ADTSigItem(adtName,adtType,at) <- signature.signatureItems) {
        stBuilder = justSTBuilder(addADTToScope(adtType, true, at, stBuilder));
        stBuilder = justSTBuilder(addADTToTopScope(adtType, true, at, stBuilder));
    }

    return popScope(stBuilder);
}

//
// Add non-type imported items into scope, based on the given signature.
//
public STBuilder addImportedItemsToScope(QualifiedName qn, RSignature signature, loc l, STBuilder stBuilder) {
    stBuilder = pushScope(getOneFrom(stBuilder.scopeNames[head(stBuilder.scopeStack),convertName(qn)]), stBuilder);

    for (item <- signature.signatureItems) {
        switch(item) {
            case FunctionSigItem(fn,ps,rt,at) : {
                stBuilder = justSTBuilder(pushNewFunctionScope(fn, rt, ps, [ ], true, isVarArgsParameters(ps), at, stBuilder));
                stBuilder = handleParametersNamesOnly(ps, stBuilder);
                stBuilder = popScope(stBuilder);
                // TODO: Add back in check for overlap
//                if (!willFunctionOverlap(fn,st,stBuilder,stBuilder.topItemId)) {
                    stBuilder = justSTBuilder(pushNewFunctionScopeAtTop(fn, rt, ps, [ ], true, isVarArgsParameters(ps), at, stBuilder));
                    stBuilder = handleParametersNamesOnly(ps, stBuilder);
                    stBuilder = popScope(stBuilder);
//                } 
                // TODO: Issue a warning if the function would overlap
                // TODO: Maybe remove other overlaps from the top-level environment
			}

            case VariableSigItem(vn,st,at) : {
                stBuilder = justSTBuilder(addVariableToScope(vn, st, true, at, stBuilder));
                if (! (size(getItems(stBuilder, last(stBuilder.scopeStack), vn, FCVs())) > 0)) {
                    stBuilder = justSTBuilder(addVariableToTopScope(vn, st, true, at, stBuilder));
                } 
            }

            case ConstructorSigItem(constructorName,constructorType,at) : {
                RName adtName = getADTName(constructorType);
                list[RNamedType] constructorTypes = getConstructorArgumentTypesWithNames(constructorType);
                
                set[ItemId] possibleADTs = getItems(stBuilder, head(stBuilder.scopeStack), adtName, Types());
                possibleADTs = { t | t <- possibleADTs, ADT(_,_,_,_) := stBuilder.scopeItemMap[t] };
                if (size(possibleADTs) == 0) throw "Error: Cannot find ADT <prettyPrintName(adtName)> to associate with constructor: <item>";
                ItemId adtItemId = getOneFrom(possibleADTs);
                stBuilder = justSTBuilder(addConstructorToScope(constructorName, constructorTypes, adtItemId, true, at, stBuilder));

                possibleADTs = getItems(stBuilder, last(stBuilder.scopeStack), adtName, Types());
                possibleADTs = { t | t <- possibleADTs, ADT(_,_,_,_) := stBuilder.scopeItemMap[t] };
                if (size(possibleADTs) == 0) throw "Error: Cannot find ADT <prettyPrintName(adtName)> to associate with constructor: <item>";
                adtItemId = getOneFrom(possibleADTs);
                // TODO: Check for overlap here; if we find an overlap, this will trigger an error, since we should not have
                // overlapping constructors and, unlike functions, we can't just take a "last in wins" approach.
                //stBuilder = justSTBuilder(checkConstructorOverlap(addConstructorToTopScope(constructorName, constructorTypes, adtItemId, true, at, stBuilder),at));
                stBuilder = justSTBuilder(addConstructorToTopScope(constructorName, constructorTypes, adtItemId, true, at, stBuilder));
            }

            case AnnotationSigItem(an,st,ot,at) : {
                stBuilder = justSTBuilder(addAnnotationToScope(an, st, ot, true, at, stBuilder)); 
                // TODO: Need to actually make this check on types, we could have the same name appear
                // multiple times, so, for now, take this check out...				
//				if (size(getAnnotationItemsForName(stBuilder, stBuilder.topItemId, an)) == 0) {
                // TODO: Add back in duplicates check 
//                    stBuilder = justSTBuilder(checkForDuplicateAnnotations(addAnnotationToTopScope(an, st, ot, true, at, stBuilder),at));
                    stBuilder = justSTBuilder(addAnnotationToTopScope(an, st, ot, true, at, stBuilder));
//				} 
            }

            // TODO
            // TagSigItem(RName tagName, list[RType] tagTypes, loc at)
            // case TagSigItem(tn,tt,at) : 3;
        }
    }

    return popScope(stBuilder);
}

//
// Process the individual items contained at the top level of the module.
//
public STBuilder handleModuleBody(Body b, STBuilder stBuilder) {
    return handleModuleBodyFull(b, handleModuleBodyNamesOnly(b, stBuilder));
}

//
// Gather the names of variables and functions. These are visible throughout the module (a variable 
// can be used in a function declared higher up in the file, for instance) so just the top-level 
// names are gathered first (i.e., we don't descend into function bodies, etc). We process the
// names in a specific order: first aliases, then ADTs, then everything else. The first two
// could potentially be merged, but doing aliases and ADTs first ensures all type names are
// visible when we start to process functions, variables, etc.
//
// TODO: See if it makes sense to merge the first two loops.
//
public STBuilder handleModuleBodyNamesOnly(Body b, STBuilder stBuilder) {
    if ((Body)`<Toplevel* ts>` := b) {
        for (Toplevel t <- ts) {
            switch(t) {
                // Alias
                case (Toplevel) `<Tags tgs> <Visibility v> alias <UserType typ> = <Type btyp> ;` :
                    stBuilder = handleAliasNamesOnly(tgs,v,typ,btyp,t@\loc,stBuilder);
            }
        }

        for (Toplevel t <- ts) {
            switch(t) {
                // ADT without variants
                case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> ;` :
                    stBuilder = handleAbstractADTNamesOnly(tgs,v,typ,t@\loc,stBuilder);

                // ADT with variants
                case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> = <{Variant "|"}+ vars> ;` :
                    stBuilder = handleADTNamesOnly(tgs,v,typ,vars,t@\loc,stBuilder);
            }
        }

        for (Toplevel t <- ts) {
            switch(t) {
                // Variable declaration
                case (Toplevel) `<Tags tgs> <Visibility v> <Type typ> <{Variable ","}+ vs> ;` :
                    stBuilder = handleVarItemsNamesOnly(tgs, v, typ, vs, stBuilder);

                // Abstract (i.e., without a body) function declaration
                case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> ;` :
                    stBuilder = handleAbstractFunctionNamesOnly(tgs,v,s,t@\loc,stBuilder);

                // Concrete (i.e., with a body) function declaration
                case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> <FunctionBody fb>` :
                    stBuilder = handleFunctionNamesOnly(tgs,v,s,fb,t@\loc,stBuilder);

                // Concrete (i.e., with a body) function declaration, in expression form
// TODO: Add this back in when this case is supported by the parser                
//                case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> = <Expression e>;` :
//                    stBuilder = handleFunctionExpNamesOnly(tgs,v,s,e,t@\loc,stBuilder);

                // Annotation declaration
                case (Toplevel) `<Tags tgs> <Visibility v> anno <Type typ> <Type otyp> @ <Name n> ;` :
                    stBuilder = handleAnnotationDeclarationNamesOnly(tgs,v,typ,otyp,n,t@\loc,stBuilder);

                // Tag declaration
                case (Toplevel) `<Tags tgs> <Visibility v> tag <Kind k> <Name n> on <{Type ","}+ typs> ;` :
                    stBuilder = handleTagDeclarationNamesOnly(tgs,v,k,n,typs,t@\loc,stBuilder);

                // Rule declaration
                case (Toplevel) `<Tags tgs> rule <Name n> <PatternWithAction pwa> ;` :
                    stBuilder = handleRuleDeclarationNamesOnly(tgs,n,pwa,t@\loc,stBuilder);

                // Test
                case (Toplevel) `<Test tst> ;` :
                    stBuilder = handleTestNamesOnly(tst,t@\loc,stBuilder);

                // View
                case (Toplevel) `<Tags tgs> <Visibility v> view <Name n> <: <Name sn> = <{Alternative "|"}+ alts> ;` :
                    stBuilder = handleViewNamesOnly(tgs,v,n,sn,alts,t@\loc,stBuilder);
            }
        }
    }

    // Now, consolidate ADT definitions and look for errors
    stBuilder = consolidateADTDefinitionsForLayer(stBuilder, head(stBuilder.scopeStack), true);
    stBuilder = checkADTDefinitionsForConsistency(stBuilder);

    return stBuilder;
}

//
// Identify names used inside functions or in static initializers, noting type information. This pass 
// actually descends into functions, building the scope information within them as well.
//
public STBuilder handleModuleBodyFull(Body b, STBuilder stBuilder) {
    if ((Body)`<Toplevel* ts>` := b) {
        for (Toplevel t <- ts) {
            switch(t) {
                // Variable declaration
                case (Toplevel) `<Tags tgs> <Visibility v> <Type typ> <{Variable ","}+ vs> ;` :
                    stBuilder = handleVarItems(tgs, v, typ, vs, stBuilder);

                // Abstract (i.e., without a body) function declaration
                case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> ;` : 
                    stBuilder = handleAbstractFunction(tgs, v, s, t@\loc, stBuilder);

                // Concrete (i.e., with a body) function declaration
                case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> <FunctionBody fb>` :
                    stBuilder = handleFunction(tgs, v, s, fb, t@\loc, stBuilder);

                // Concrete (i.e., with a body) function declaration, in expression form
// TODO: Add this back in when this case is supported by the parser                
//                case (Toplevel) `<Tags tgs> <Visibility v> <Signature s> = <Expression e>;` :
//                    stBuilder = handleFunctionExp(tgs,v,s,e,t@\loc,stBuilder);

                // Annotation declaration
                case (Toplevel) `<Tags tgs> <Visibility v> anno <Type typ> <Type otyp> @ <Name n> ;` :
                    stBuilder = handleAnnotationDeclaration(tgs, v, typ, otyp, n, t@\loc, stBuilder);

                // Tag declaration
                case (Toplevel) `<Tags tgs> <Visibility v> tag <Kind k> <Name n> on <{Type ","}+ typs> ;` :
                    stBuilder = handleTagDeclaration(tgs, v, k, n, typs, t@\loc, stBuilder);

                // Rule declaration
                case (Toplevel) `<Tags tgs> rule <Name n> <PatternWithAction pwa> ;` :
                    stBuilder = handleRuleDeclaration(tgs, n, pwa, t@\loc, stBuilder);

                // Test
                case (Toplevel) `<Test tst> ;` :
                    stBuilder = handleTest(tst, t@\loc, stBuilder);

                // ADT without variants
                case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> ;` :
                    stBuilder = handleAbstractADT(tgs, v, typ, t@\loc, stBuilder);

                // ADT with variants
                case (Toplevel) `<Tags tgs> <Visibility v> data <UserType typ> = <{Variant "|"}+ vars> ;` :
                    stBuilder = handleADT(tgs, v, typ, vars, t@\loc, stBuilder);

                // Alias
                case (Toplevel) `<Tags tgs> <Visibility v> alias <UserType typ> = <Type btyp> ;` :
                    stBuilder = handleAlias(tgs, v, typ, btyp, t@\loc, stBuilder);

                // View
                case (Toplevel) `<Tags tgs> <Visibility v> view <Name n> <: <Name sn> = <{Alternative "|"}+ alts> ;` :
                    stBuilder = handleView(tgs, v, n, sn, alts, t@\loc, stBuilder);

                default: throw "handleModuleBodyFull: No match for item <t>";
            }
        }
    }

    return stBuilder;
}

//
// Handle variable declarations, with or without initializers. We don't allow duplicate top-level names, but we do
// allow this name to shadow a name from an imported module. This is why the duplicate check is module bounded.
//
public STBuilder handleVarItemsNamesOnly(Tags ts, Visibility v, Type t, {Variable ","}+ vs, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(ts, stBuilder);

    ConvertTuple ct = convertRascalType(stBuilder, t);
    RType varType = ct.rtype; stBuilder = ct.stBuilder;

    for (vb <- vs) {
        if ((Variable)`<Name n>` := vb || (Variable)`<Name n> = <Expression e>` := vb) {
            if (size(getItems(stBuilder, head(stBuilder.scopeStack), convertName(n), FCVs())) > 0) {		
                stBuilder = addScopeError(stBuilder, n@\loc, "Duplicate declaration of name <n>");
            } 
            stBuilder = justSTBuilder(addVariableToScope(convertName(n), varType, isPublic(v), vb@\loc, stBuilder));
        }
    }
    return stBuilder;
}

//
// Process the initializer expressions given inside the variable declaration.
//
public STBuilder handleVarItems(Tags ts, Visibility v, Type t, {Variable ","}+ vs, STBuilder stBuilder) {
    stBuilder = handleTags(ts, stBuilder);
    for ((Variable)`<Name n> = <Expression e>` <- vs) stBuilder = handleExpression(e, stBuilder);
    return stBuilder;
}

//
// Handle standard function declarations (i.e., function declarations with bodies), but
// do NOT descend into the bodies
//
public STBuilder handleFunctionNamesOnly(Tags ts, Visibility v, Signature s, FunctionBody b, loc l, STBuilder stBuilder) {
    return handleAbstractFunctionNamesOnly(ts,v,s,l,stBuilder);		
}

//
// Handle standard function declarations with expressions for bodies, but
// do NOT descend into the bodies
//
public STBuilder handleFunctionExpNamesOnly(Tags ts, Visibility v, Signature s, FunctionBody b, loc l, STBuilder stBuilder) {
    return handleAbstractFunctionNamesOnly(ts,v,s,l,stBuilder);       
}

//
// Handle abstract function declarations (i.e., function declarations without bodies)
//
public STBuilder handleAbstractFunctionNamesOnly(Tags ts, Visibility v, Signature s, loc l, STBuilder stBuilder) {
    // Add the new function into the scope and process any parameters.
    STBuilder addFunction(Name n, RType retType, loc rloc, Parameters ps, list[RType] thrsTypes, bool isPublic, STBuilder stBuilder) {
        stBuilder = justSTBuilder(pushNewFunctionScope(convertName(n),retType,ps,thrsTypes,isPublic,isVarArgsParameters(ps),l,stBuilder));
        stBuilder = handleParametersNamesOnly(ps, stBuilder);
        
        // Check if the return type has any type variables; if so, make sure they are in scope
        for (tvv <- collectTypeVars(retType)) {
            set[ItemId] tvItems = getItems(stBuilder, head(stBuilder.scopeStack), getTypeVarName(tvv), TypeVars());
            if (size(tvItems) == 0) {
                stBuilder = addScopeError(stBuilder, rloc, "Type variable <prettyPrintName(tvv.varName)> used in return type not previously declared.");        
            } else {
               // TODO: We should just have one, check to see if we have more
               RType tvType = stBuilder.scopeItemMap[getOneFrom(tvItems)].typeVar;
               if (tvType.varTypeBound != tvv.varTypeBound) {
                    stBuilder = addScopeError(stBuilder, rloc, "Illegal redefinition of bound on type variable <prettyPrintName(tvv.varName)> with existing bound <prettyPrintType(tvType.varTypeBound)>.");        
               }
            }
        }
        
        return popScope(stBuilder);
    }

    stBuilder = handleTagsNamesOnly(ts, stBuilder);

    switch(s) {
        case (Signature)`<Type t> <FunctionModifiers ns> <Name n> <Parameters ps>` : {
            ConvertTuple ct = convertRascalType(stBuilder, t);
            RType retType = ct.rtype; stBuilder = ct.stBuilder;
            stBuilder = addFunction(n, retType, t@\loc, ps, mkEmptyList(), isPublic(v), stBuilder);
        }

        case (Signature)`<Type t> <FunctionModifiers ns> <Name n> <Parameters ps> throws <{Type ","}+ thrs> ` : {
            ConvertTuple ct = convertRascalType(stBuilder, t);
            RType retType = ct.rtype; stBuilder = ct.stBuilder;
            list[RType] throwsTypes = [ ];
            for (thrsi <- thrs) { ct = convertRascalType(stBuilder, thrsi); throwsTypes = throwsTypes + ct.rtype; stBuilder = ct.stBuilder; }
            stBuilder = addFunction(n, retType, t@\loc, ps, throwsTypes, isPublic(v), stBuilder);
        }
    }
    return stBuilder;
}

//
// This function has no body, and the function header was processed already, so just process the tags.
//
public STBuilder handleAbstractFunction(Tags ts, Visibility v, Signature s, loc l, STBuilder stBuilder) {
    return handleTags(ts, stBuilder);
}

//
// Handle parameter declarations. Parameters currently have no defaults, etc, so there is no other
// version of this function (no non "NamesOnly" version).
//
// TODO: A current requirement is that, for varargs functions, the last parameter is just
// a type variable pattern. Enforce that here.
//
public STBuilder handleParametersNamesOnly(Parameters p, STBuilder stBuilder) {
    if ((Parameters)`( <Formals f> )` := p || (Parameters)`( <Formals f> ... )` := p) {
        if ((Formals)`<{Pattern ","}* fs>` := f) {
            for (fp <- fs) {
                stBuilder = handlePattern(fp, stBuilder);
            }
        }
    }
    
    return stBuilder;
}

//
// Handle standard function declarations (i.e., function declarations with bodies). The header has
// already been processed, so this just enters the scope of the header and then processes the
// function body.
//
public STBuilder handleFunction(Tags ts, Visibility v, Signature s, FunctionBody b, loc l, STBuilder stBuilder) {
    stBuilder = handleTags(ts, stBuilder);

    // First, get back the scope item at location l so we can switch into the proper function scope
    stBuilder = pushScope(getLayerAtLocation(l, stBuilder), stBuilder);

    // Now, process the function body
    switch(s) {
        case (Signature)`<Type t> <FunctionModifiers ns> <Name n> <Parameters ps>` : {
            stBuilder = handleFunctionBody(b,stBuilder);
        }

        case (Signature)`<Type t> <FunctionModifiers ns> <Name n> <Parameters ps> throws <{Type ","}+ tts> ` : {
            stBuilder = handleFunctionBody(b,stBuilder);
        }
    }

    return popScope(stBuilder);	
}

//
// Handle standard function declarations with expressions for bodies. The header has
// already been processed, so this just enters the scope of the header and then processes the
// function body.
//
public STBuilder handleFunctionExp(Tags ts, Visibility v, Signature s, Expression e, loc l, STBuilder stBuilder) {
    stBuilder = handleTags(ts, stBuilder);

    // First, get back the scope item at location l so we can switch into the proper function scope
    stBuilder = pushScope(getLayerAtLocation(l, stBuilder), stBuilder);

    // Now, process the function body
    switch(s) {
        case (Signature)`<Type t> <FunctionModifiers ns> <Name n> <Parameters ps>` : {
            stBuilder = handleExpression(e,stBuilder);
        }

        case (Signature)`<Type t> <FunctionModifiers ns> <Name n> <Parameters ps> throws <{Type ","}+ tts> ` : {
            stBuilder = handleExpression(e,stBuilder);
        }
    }

    return popScope(stBuilder);   
}

//
// Handle function bodies
//
public STBuilder handleFunctionBody(FunctionBody fb, STBuilder stBuilder) {
    if ((FunctionBody)`{ <Statement* ss> }` := fb) {
        for (s <- ss) stBuilder = handleStatement(s, stBuilder);
    } else {
        throw "handleFunctionBody, unexpected syntax for body <fb>";
    }
    return stBuilder;
}

//
// Check is visibility represents public or private
//
private bool isPublic(Visibility v) {
    return ((Visibility)`public` := v);
}

//
// Introduce the annotation name into the current scope. Duplicates are not allowed, so we check for them
// here and tag the name with a scope error if we find one.
//
// TODO: We should probably put these into a table, like the ADTs, so we can figure out more easily
// during checking which values have which annotations.
//
// TODO: Make sure the duplicate check only checks for duplicates on the same type, we can have multiple
// declarations for the same annotation name as long as they are on different types.
//
public STBuilder handleAnnotationDeclarationNamesOnly(Tags t, Visibility v, Type ty, Type ot, Name n, loc l, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(t, stBuilder);
    ConvertTuple ct = convertRascalType(stBuilder, ty);
    RType annoType = ct.rtype; stBuilder = ct.stBuilder;
    ct = convertRascalType(stBuilder, ot);
    RType onType = ct.rtype; stBuilder = ct.stBuilder;
    // TODO: Add back in duplicate check, if needed
//    stBuilder = justSTBuilder(checkForDuplicateAnnotations(addAnnotationToScope(convertName(n),annoType,onType,isPublic(v),l,stBuilder), n@\loc));
    stBuilder = justSTBuilder(addAnnotationToScope(convertName(n),annoType,onType,isPublic(v),l,stBuilder));
    return stBuilder;
}

//
// All checks were done above specifically for annotations, so just handle the tags here.
//
public STBuilder handleAnnotationDeclaration(Tags t, Visibility v, Type ty, Type ot, Name n, loc l, STBuilder stBuilder) {
    return handleTags(t, stBuilder);
}

//
// TODO: Implement
//
public STBuilder handleTagDeclaration(Tags t, Visibility v, Kind k, Name n, {Type ","}+ ts, loc l, STBuilder stBuilder) {
    return handleTags(t, stBuilder);
}

//
// TODO: Implement
//
public STBuilder handleTagDeclarationNamesOnly(Tags t, Visibility v, Kind k, Name n, {Type ","}+ ts, loc l, STBuilder stBuilder) {
    return handleTagsNamesOnly(t, stBuilder);
}

//
// In this first pass we just worry about the name of the rule, we don't yet descend into the pattern.
//
public STBuilder handleRuleDeclarationNamesOnly(Tags t, Name n, PatternWithAction p, loc l, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(t, stBuilder);
    return justSTBuilder(addRuleToScope(convertName(n), l, handleTagsNamesOnly(t, stBuilder)));
}

//
// For the second pass, descend into the rule pattern with action.
//							
public STBuilder handleRuleDeclaration(Tags t, Name n, PatternWithAction p, loc l, STBuilder stBuilder) {
    return handlePatternWithAction(p, handleTags(t, stBuilder));
}

//
// Tests don't introduce any top-level names, so we only need to handle the test tag on this first pass.
//
public STBuilder handleTestNamesOnly(Test t, loc l, STBuilder stBuilder) {
    if ((Test)`<Tags tgs> test <Expression exp>` := t || (Test)`<Tags tgs> test <Expression exp> : <StringLiteral sl>` := t) {
        return handleTagsNamesOnly(tgs, stBuilder);
    }
    throw "Unexpected syntax for test: <t>";
}

//
// Tests can use names in the test expression, so we need to descend into the expression of the test
// on the second pass.
//
public STBuilder handleTest(Test t, loc l, STBuilder stBuilder) {
    if ((Test)`<Tags tgs> test <Expression exp>` := t || (Test)`<Tags tgs> test <Expression exp> : <StringLiteral sl>` := t) {
        return handleExpression(exp,handleTags(tgs, stBuilder));
    }
    throw "Unexpected syntax for test: <t>";
}

//
// Handle abstract ADT declarations (ADT's without variants). This introduces the ADT name into scope. Note
// that duplicate ADT names are not an error; the constructors of all ADTs sharing the same name will be
// merged together, allowing them to be introduced piecemeal.
//
public STBuilder handleAbstractADTNamesOnly(Tags ts, Visibility v, UserType adtType, loc l, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(ts, stBuilder);
    RType adtBase = convertUserType(adtType);
    RType adtRType = makeParameterizedADTType(getUserTypeName(adtBase),getUserTypeParameters(adtBase));
    return justSTBuilder(addADTToScope(adtRType, isPublic(v), l, stBuilder));
}

//
// This just handles the tags; the ADT name was introduced into scope in handleAbstractADTNamesOnly, so
// there is nothing left to process at this point.
//
public STBuilder handleAbstractADT(Tags ts, Visibility v, UserType adtType, loc l, STBuilder stBuilder) {
    return handleTags(ts, stBuilder);
}

//
// Handle ADT declarations (ADT's with variants). This will introduce the ADT and constructor names into
// scope. It will also check for overlaps with the constructor names to ensure references to introduced
// constructors can be unambiguous.
//
public STBuilder handleADTNamesOnly(Tags ts, Visibility v, UserType adtType, {Variant "|"}+ vars, loc l, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(ts, stBuilder);
    RType adtBase = convertUserType(adtType);
    RType adtRType = makeParameterizedADTType(getUserTypeName(adtBase),getUserTypeParameters(adtBase));
    < stBuilder, adtId > = addADTToScope(adtRType, isPublic(v), l, stBuilder);

    // Process each given variant, adding it into scope	
    for (var <- vars) {
        if ((Variant)`<Name n> ( <{TypeArg ","}* args> )` := var) {
            list[RNamedType] cparams = [ ];
            for (targ <- args) { ConvertTupleN ct = convertRascalTypeArg(stBuilder, targ); cparams = cparams + ct.rtype; stBuilder = ct.stBuilder; }
            //stBuilder = justSTBuilder(checkConstructorOverlap(addItemUses(addConstructorToScope(convertName(n), cparams, adtId, true, l, stBuilder),[<true,n@\loc>]),n@\loc));
            // TODO: Re-add overlap check
            stBuilder = justSTBuilder(addConstructorToScope(convertName(n), cparams, adtId, true, l, stBuilder));
        }
    }

    return stBuilder;
}

//
// The ADT declaration is brought into scope with the last function, therefore this just
// checks the tags to make sure they are sensible but doesn't further process the
// ADT.
//
public STBuilder handleADT(Tags ts, Visibility v, UserType adtType, {Variant "|"}+ vars, loc l, STBuilder stBuilder) {
    return handleTags(ts, stBuilder);
}

//
// Handle alias declarations. Note that we don't check to see if the type being pointed to exists, since it may
// be another alias, ADT, etc that is also being processed in this first step.
//
public STBuilder handleAliasNamesOnly(Tags ts, Visibility v, UserType aliasType, Type aliasedType, loc l, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(ts, stBuilder);
    Name aliasRawName = getUserTypeRawName(aliasType);
    RName aliasName = convertName(aliasRawName);

    ConvertTuple ct = convertRascalUserType(stBuilder, aliasType);
    RType aType = ct.rtype; stBuilder = ct.stBuilder;
    ct = convertRascalType(stBuilder, aliasedType);
    RType tType = ct.rtype; stBuilder = ct.stBuilder;
    RType aliasRType = makeParameterizedAliasType(getUserTypeName(aType), tType, getUserTypeParameters(aType));
    
    //stBuilder = justSTBuilder(checkForDuplicateAliases(addItemUses(addAliasToScope(aliasRType, isPublic(v), l, stBuilder),[<true,aliasRawName@\loc>]),aliasRawName@\loc));
    // TODO: Add checking for duplicates
    stBuilder = justSTBuilder(addAliasToScope(aliasRType, isPublic(v), l, stBuilder));

    return stBuilder;
}

//
// Handle the alias declaration in the second pass.
//
// TODO: This may be a good time to verify that the aliased type actually exists.
//
public STBuilder handleAlias(Tags ts, Visibility v, UserType aliasType, Type aliasedType, loc l, STBuilder stBuilder) {
    return handleTags(ts, stBuilder); 
}

//
// TODO: Implement later, views aren't currently supported
//
public STBuilder handleViewNamesOnly(Tags ts, Visibility v, Name n, Name sn, {Alternative "|"}+ alts, loc l, STBuilder stBuilder) {
    stBuilder = handleTagsNamesOnly(ts, stBuilder);
    //throw "handleViewNamesOnly not yet implemented";
    return stBuilder;
}

//
// TODO: Implement later
//
public STBuilder handleView(Tags ts, Visibility v, Name n, Name sn, {Alternative "|"}+ alts, loc l, STBuilder stBuilder) {
    return handleTags(ts, stBuilder);
}

//
// Handle individual statements
//
public STBuilder handleStatement(Statement s, STBuilder stBuilder) {
    switch(s) {
        // solve statement; note that the names are not binders, they should already be in scope
        case (Statement)`solve (<{QualifiedName ","}+ vs> <Bound b>) <Statement sb>` : {
            for (v <- vs)
                stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), convertName(v), FCVs()), v@\loc);

            if ((Bound)`; <Expression e>` := b)
                stBuilder = handleExpression(e, stBuilder);

            stBuilder = handleStatement(sb, stBuilder);		
        }

        // for statement; this opens a boolean scope, ensuring bindings in the for expression are visible just in the body
        case (Statement)`<Label l> for (<{Expression ","}+ es>) <Statement b>` : {
            stBuilder = handleLabel(l,stBuilder);			
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- es) stBuilder = handleExpression(e, stBuilder);
            stBuilder = handleStatement(b, stBuilder);
            stBuilder = popScope(stBuilder);
        }

        // while statement; this opens a boolean scope, ensuring bindings in the while expression are visible just in the body
        case (Statement)`<Label l> while (<{Expression ","}+ es>) <Statement b>` : {
            stBuilder = handleLabel(l,stBuilder);			
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- es) stBuilder = handleExpression(e, stBuilder);
            stBuilder = handleStatement(b, stBuilder);
            stBuilder = popScope(stBuilder);
        }

        // do statement; in this case the expression is not a binder, since it comes after the first iteration
        case (Statement)`<Label l> do <Statement b> while (<Expression e>);` :
            stBuilder = handleExpression(e, handleStatement(b, handleLabel(l,stBuilder)));			

        // if statement; this opens a boolean scope, ensuring bindings in the if guard expression are visible just in the body		
        case (Statement)`<Label l> if (<{Expression ","}+ es>) <Statement bt> else <Statement bf>` : {
            stBuilder = handleLabel(l,stBuilder);			
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- es) stBuilder = handleExpression(e, stBuilder);
            stBuilder = handleStatement(bf, handleStatement(bt, stBuilder));
            stBuilder = popScope(stBuilder);
        }

        // if statement with no else; this opens a boolean scope, ensuring bindings in the if guard expression are visible just in the body
        case (Statement)`<Label l> if (<{Expression ","}+ es>) <Statement bt> <NoElseMayFollow _>` : {
            stBuilder = handleLabel(l,stBuilder);			
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- es) stBuilder = handleExpression(e, stBuilder);
            stBuilder = handleStatement(bt, stBuilder);
            stBuilder = popScope(stBuilder);
        }

        // switch statement
        case (Statement)`<Label l> switch (<Expression e>) { <Case+ cs> }` : {
            stBuilder = handleExpression(e,handleLabel(l,stBuilder));						
            for (c <- cs) stBuilder = handleCase(c, stBuilder);
        }

        // visit statement
        case (Statement)`<Label l> <Visit v>` :
            stBuilder = handleVisit(v, handleLabel(l,stBuilder));						

        // expression statement
        case (Statement)`<Expression e> ;` :
            stBuilder = handleExpression(e, stBuilder);

        // assignment statement
        case (Statement)`<Assignable a> <Assignment op> <Statement b>` :
            stBuilder = handleStatement(b, handleAssignable(a, stBuilder));

        // assert statement
        case (Statement)`assert <Expression e> ;` :
            stBuilder = handleExpression(e, stBuilder);

        // assert statement with guard
        case (Statement)`assert <Expression e> : <Expression em> ;` :
            stBuilder = handleExpression(em, handleExpression(e, stBuilder));

        // return statement -- we also save the ID of the associated function item, since this makes
        // it easier for the type checker (or other anayses) to know which function this return is
        // associated with 
        case (Statement)`return <Statement b>` : {
            stBuilder = handleStatement(b, stBuilder);
            < inFunction, functionId > = getSurroundingFunction(stBuilder, head(stBuilder.scopeStack));
            if (inFunction) {
                stBuilder = markReturnFunction(functionId, s@\loc, stBuilder);
            } else {
                stBuilder = addScopeError(stBuilder, s@\loc, "Return statement must be given inside a function");
            }
        }

        // throw statement
        case (Statement)`throw <Statement b>` :
            stBuilder = handleStatement(b, stBuilder);

        // insert statement
        case (Statement)`insert <DataTarget dt> <Statement b>` :
            stBuilder = handleStatement(b, handleDataTarget(dt, stBuilder));

        // append statement
        case (Statement)`append <DataTarget dt> <Statement b>` :
            stBuilder = handleStatement(b, handleDataTarget(dt, stBuilder));

        // local function declaration; the called functions handle the scoping so we don't have to here
        case (Statement) `<Tags ts> <Visibility v> <Signature sig> <FunctionBody fb>` : {
            // First get back the function signature information, creating the scope item
            stBuilder = handleFunctionNamesOnly(ts,v,sig,fb,s@\loc,handleTagsNamesOnly(ts, stBuilder));

            // Now, descend into the function, processing the body
            stBuilder = handleFunction(ts,v,sig,fb,s@\loc,handleTags(ts, stBuilder));
        }

        // local function declaration; the called functions handle the scoping so we don't have to here
// TODO: Add this back in when this case is supported by the parser                
//        case (Statement) `<Tags ts> <Visibility v> <Signature sig> = <Expression e>;` : {
//            // First get back the function signature information, creating the scope item
//            stBuilder = handleFunctionExpNamesOnly(ts,v,sig,e,s@\loc,handleTagsNamesOnly(ts, stBuilder));
//
//            // Now, descend into the function, processing the body
//            stBuilder = handleFunctionExp(ts,v,sig,e,s@\loc,handleTags(ts, stBuilder));
//        }

        // local variable declaration
        case (Statement) `<Type t> <{Variable ","}+ vs> ;` :
            stBuilder = handleLocalVarItems(t,vs,stBuilder);

        // dynamic variable declaration; TODO this is not implemented yet by Rascal
        case (Statement) `dynamic <Type t> <{Variable ","}+ vs> ;` :
            stBuilder = handleLocalVarItems(t,vs,stBuilder);

        // break statement		
        case (Statement)`break <Target t> ;` :
            stBuilder = handleTarget(t, stBuilder);

        // fail statement
        case (Statement)`fail <Target t> ;` :
            stBuilder = handleTarget(t, stBuilder);

        // continue statement
        case (Statement)`continue <Target t> ;` :
            stBuilder = handleTarget(t, stBuilder);

        // try/catch statement
        case (Statement)`try <Statement b> <Catch+ cs>` : {
            stBuilder = handleStatement(b, stBuilder);
            for (ct <- cs) stBuilder = handleCatch(ct, stBuilder);
        }

        // try/catch/finally statement
        case (Statement)`try <Statement b> <Catch+ cs> finally <Statement bf>` : {
            stBuilder = handleStatement(b, stBuilder);
            for (ct <- cs) stBuilder = handleCatch(ct, stBuilder);
            stBuilder = handleStatement(bf, stBuilder);
        }

        // labeled statement block
        case (Statement)`<Label l> { <Statement+ bs> }` : {
            stBuilder = handleLabel(l,stBuilder);			
            stBuilder = justSTBuilder(pushNewBlockScope(s@\loc, stBuilder));
            for (b <- bs) stBuilder = handleStatement(b,stBuilder);
            stBuilder = popScope(stBuilder);
        }
    }

    return stBuilder;
}

//
// Pick apart a map to properly introduce its names into scope
//
public list[Tree] getMapMappings(Tree t) {
    list[Tree] mapParts = [ ];

    // t[1] holds the parse tree contents for the map
    if (list[Tree] mapTop := t[1]) {
        // mapTop[0] = (, mapTop[1] = layout, mapTop[2] = map contents, mapTop[3] = layout, mapTop[4] = ), so we get out 2
        if (appl(_,list[Tree] mapItems) := mapTop[2]) {
            if (size(mapItems) > 0) {
                // The map items include layout and commas, so we use a mod 4 to account for this: we have
                // item layout comma layout item layout comma layout etc
                list[Tree] mapMappings = [ mapItems[n] | n <- [0..size(mapItems)-1], n % 4 == 0];

                // Each item should have the domain and range inside. It is organized as pat layout : layout pat
                for (n <- [0..size(mapMappings)-1]) {
                    if (appl(_,list[Tree] mapContents) := mapMappings[n]) {
                        if (size(mapContents) == 5 && `<Tree tl>` := mapContents[0] && `<Tree tr>` := mapContents[4]) {
                            mapParts = mapParts + [ tl, tr ]; 
                        }
                    } 
                }
            }
        }
    }

    return mapParts;
}

//
// Return domain : range expression pairs as a list of tuples for a map expression
//
public list[tuple[Expression mapDomain, Expression mapRange]] getMapExpressionContents(Expression exp) {
    list[Tree] mm = getMapMappings(exp); // What comes back is in the form [domain,range,domain,range,...]

    if (size(mm) > 0)
        return [ <el, er> | n <- [0..size(mm)-1], n % 2 == 0, `<Expression el>` := mm[n], `<Expression er>` := mm[n+1] ];
    else
        return [ ];
}

//
// Return domain : range pattern pairs as a list of tuples for a map pattern
//
public list[tuple[Pattern mapDomain, Pattern mapRange]] getMapPatternContents(Pattern pat) {
    list[Tree] mm = getMapMappings(pat); // What comes back is in the form [domain,range,domain,range,...]

    if (size(mm) > 0)
        return [ <pl, pr> | n <- [0..size(mm)-1], n % 2 == 0, `<Pattern pl>` := mm[n], `<Pattern pr>` := mm[n+1] ];
    else
        return [ ];
}

//
// Scope handling for map expressions -- this is done separately since we cannot use matching to get back
// the parts of the map.
//
public STBuilder handleMapExpression(Expression exp, STBuilder stBuilder) {
    list[tuple[Expression mapDomain, Expression mapRange]] mapContents = getMapExpressionContents(exp);
    for (<md,mr> <- mapContents) stBuilder = handleExpression(mr, handleExpression(md, stBuilder));
    return stBuilder;
}

//
// TODO: We still need to add support for concrete syntax, both here and in patterns (below).
//
public STBuilder handleExpression(Expression exp, STBuilder stBuilder) {
    STBuilder handleExpName(RName n, loc l, STBuilder stBuilder) {
        if (size(getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {
            stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs()), l);
        } else {
            stBuilder = addScopeError(stBuilder, l, "<prettyPrintName(n)> not defined before use");
        }
        return stBuilder;
    }

    switch(exp) {
        // Strings (in case of interpolation)
        case (Expression)`<StringLiteral sl>`: {
            list[Tree] ipl = prodFilter(sl,bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
        }

        // Locations (in case of interpolation)
        case (Expression)`<LocationLiteral ll>`: {
            list[Expression] ipl = prodFilter(ll, bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd; });
            for (ipe <- ipl) stBuilder = handleExpression(ipe, stBuilder);
        }

        // Name _
        // TODO: This is not really true, since _ can appear in a relation subscript. Handle this there, though,
        // as _ cannot appear elsewhere...
        case (Expression)`_`: 
            stBuilder = addScopeError(stBuilder, exp@\loc, "_ cannot be used as a variable name in an expression.");

		// Name (other than _)
        case (Expression)`<Name n>`: 
            stBuilder = handleExpName(convertName(n),n@\loc,stBuilder);

        // QualifiedName
        case (Expression)`<QualifiedName qn>`: 
            stBuilder = handleExpName(convertName(qn),qn@\loc,stBuilder);

        // ReifiedType
        case (Expression)`<BasicType t> ( <{Expression ","}* el> )` : {
            // NOTE: We don't ensure t is well-formed here, because it need not be; for instance, to
            // give the reified type form of list[int], we would specify list(int()), but this means
            // that list, the basic type, is not a valid type, since it must take an element type if
            // used for a variable type, function parameter type, etc. So,
            // TODO: Make sure el is a well-formed type expression, like list(int())
            for (ei <- el) stBuilder = handleExpression(ei, stBuilder);
        }

        // CallOrTree
        case (Expression)`<Expression e1> ( <{Expression ","}* el> )` : {
            stBuilder = handleExpression(e1, stBuilder);

            // Parameters maintain their own scope for backtracking purposes
            stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
            for (ei <- el) stBuilder = handleExpression(ei, stBuilder);
            stBuilder = popScope(stBuilder);
        }

        // List
        case (Expression)`[<{Expression ","}* el>]` :
            for (ei <- el) stBuilder = handleExpression(ei, stBuilder);

        // Set
        case (Expression)`{<{Expression ","}* el>}` :
            for (ei <- el) stBuilder = handleExpression(ei, stBuilder);

        // Tuple, just one expression
        case (Expression) `<<Expression ei>>` :
            stBuilder = handleExpression(ei, stBuilder);

        // Tuple, more than one expression
        case (Expression)`<<Expression ei>, <{Expression ","}* el>>` : {
            stBuilder = handleExpression(ei,stBuilder);
            for (eli <- el) stBuilder = handleExpression(eli, stBuilder);
        }

        // Closure
        // TODO: Should we verify that p is not varargs here?
        case (Expression)`<Type t> <Parameters p> { <Statement+ ss> }` : {
            ConvertTuple ct = convertRascalType(stBuilder, t);
            RType retType = ct.rtype; stBuilder = ct.stBuilder;
            stBuilder = justSTBuilder(pushNewClosureScope(retType,p,exp@\loc,stBuilder));
            stBuilder = handleParametersNamesOnly(p, stBuilder);
            
            // Check if the return type has any type variables; if so, make sure they are in scope
            for (tvv <- collectTypeVars(retType)) {
                set[ItemId] tvItems = getItems(stBuilder, head(stBuilder.scopeStack), getTypeVarName(tvv), TypeVars());
                if (size(tvItems) == 0) {
                    stBuilder = addScopeError(stBuilder, t@\loc, "Type variable <prettyPrintName(tvv.varName)> used in return type not previously declared.");        
                } else {
                   // TODO: We should just have one, check to see if we have more
                   RType tvType = stBuilder.scopeItemMap[getOneFrom(tvItems)].typeVar;
                   if (tvType.varTypeBound != tvv.varTypeBound) {
                        stBuilder = addScopeError(stBuilder, t@\loc, "Illegal redefinition of bound on type variable <prettyPrintName(tvv.varName)> with existing bound <prettyPrintType(tvType.varTypeBound)>.");        
                   }
                }
            }
            
            for (s <- ss) stBuilder = handleStatement(s, stBuilder);
            stBuilder = popScope(stBuilder);
        }

		// VoidClosure
        case (Expression)`<Parameters p> { <Statement* ss> }` : {
            stBuilder = justSTBuilder(pushNewVoidClosureScope(p,exp@\loc,stBuilder));
            stBuilder = handleParametersNamesOnly(p, stBuilder);
            for (s <- ss) stBuilder = handleStatement(s, stBuilder);
            stBuilder = popScope(stBuilder);
        }

        // NonEmptyBlock
        case (Expression)`{ <Statement+ ss> }` : {
            stBuilder = justSTBuilder(pushNewBlockScope(s@\loc, stBuilder));
            for (s <- ss) stBuilder = handleStatement(s, stBuilder);
            stBuilder = popScope(stBuilder);
        }

        // Visit
        case (Expression) `<Label l> <Visit v>` :
            stBuilder = handleVisit(v, handleLabel(l,stBuilder));						

        // ParenExp
        case (Expression)`(<Expression e>)` :
            stBuilder = handleExpression(e, stBuilder);

        // Range
        case (Expression)`[ <Expression e1> .. <Expression e2> ]` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // StepRange
        case (Expression)`[ <Expression e1>, <Expression e2> .. <Expression e3> ]` :
            stBuilder = handleExpression(e3, handleExpression(e2, handleExpression(e1, stBuilder)));

        // FieldUpdate
        // NOTE: We don't add this name into the symbol table or try to look it up
        // since we don't actually even know if this name is valid. We need to know
        // the type of e1 first, so this is just handled by the type checker.
        case (Expression)`<Expression e1> [<Name n> = <Expression e2>]` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // FieldAccess
        // NOTE: We don't add this name into the symbol table or try to look it up
        // since we don't actually even know if this name is valid. We need to know
        // the type of e1 first, so this is just handled by the type checker.
        case (Expression)`<Expression e1> . <Name n>` :
            stBuilder = handleExpression(e1, stBuilder);

        // FieldProject
        // NOTE: We don't add this name into the symbol table or try to look it up
        // since we don't actually even know if this name is valid. We need to know
        // the type of e1 first, so this is just handled by the type checker.
        case (Expression)`<Expression e1> < <{Field ","}+ fl> >` :
            stBuilder = handleExpression(e1, stBuilder);

        // Subscript
        // NOTE: We explicitly handle _ here as a possible expression in the subscript.
        // If we find it, we just skip it. That way, above we can explicitly mark the
        // name _ as an error if we find it in another context. The type checker needs
        // to handle the case of whether _ is a valid expression in this context -- it
        // is for relations, but not for lists, for instance.
        case (Expression)`<Expression e1> [ <{Expression ","}+ el> ]` : {
            stBuilder = handleExpression(e1, stBuilder);
            for (e <- el) {
                if ((Expression)`_` := e) stBuilder = handleExpression(e, stBuilder);
            }
		}

        // IsDefined
        case (Expression)`<Expression e> ?` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e, stBuilder);
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Negation
        case (Expression)`! <Expression e>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e, stBuilder);
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Negative
        case (Expression)`- <Expression e> ` :
            stBuilder = handleExpression(e, stBuilder);

        // TransitiveReflexiveClosure
        case (Expression)`<Expression e> * ` :
            stBuilder = handleExpression(e, stBuilder);

        // TransitiveClosure
        case (Expression)`<Expression e> + ` :
            stBuilder = handleExpression(e, stBuilder);

        // GetAnnotation
        case (Expression)`<Expression e> @ <Name n>` : {
            stBuilder = handleExpression(e, stBuilder);
            stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), convertName(n), Annotations()), n@\loc);
        }

        // SetAnnotation
        case (Expression)`<Expression e1> [@ <Name n> = <Expression e2>]` : {
            stBuilder = handleExpression(e2,handleExpression(e1, stBuilder));
            stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), convertName(n), Annotations()), n@\loc);
        }

        // Composition
        case (Expression)`<Expression e1> o <Expression e2>` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // Product
        case (Expression)`<Expression e1> * <Expression e2>` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // Join
        case (Expression)`<Expression e1> join <Expression e2>` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // Div
        case (Expression)`<Expression e1> / <Expression e2>` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // Mod
        case (Expression)`<Expression e1> % <Expression e2>` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // Intersection
        case (Expression)`<Expression e1> & <Expression e2>` :
            stBuilder = handleExpression(e2, handleExpression(e1, stBuilder));

        // Plus
        case (Expression)`<Expression e1> + <Expression e2>` :
            stBuilder = handleExpression(e2,handleExpression(e1, stBuilder));

        // Minus
        case (Expression)`<Expression e1> - <Expression e2>` :
            stBuilder = handleExpression(e2,handleExpression(e1, stBuilder));

        // NotIn
        case (Expression)`<Expression e1> notin <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

		// In
        case (Expression)`<Expression e1> in <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // LessThan
        case (Expression)`<Expression e1> < <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // LessThanOrEq
        case (Expression)`<Expression e1> <= <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // GreaterThan
        case (Expression)`<Expression e1> > <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // GreaterThanOrEq
        case (Expression)`<Expression e1> >= <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Equals
        case (Expression)`<Expression e1> == <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // NotEquals
        case (Expression)`<Expression e1> != <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // IfThenElse (Ternary)
        case (Expression)`<Expression e1> ? <Expression e2> : <Expression e3>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e3, handleExpression(e2, handleExpression(e1, stBuilder)));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // IfDefinedOtherwise
        case (Expression)`<Expression e1> ? <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Implication
        case (Expression)`<Expression e1> ==> <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }

            // First, push a scope for the left-hand side of the or and evaluate
            // the expression there
            stBuilder = justSTBuilder(pushNewOrScope(exp@\loc, stBuilder));
            ItemId orScope1 = head(stBuilder.scopeStack);
            stBuilder = handleExpression(e1, stBuilder);
            stBuilder = popScope(stBuilder);

            // Now, do the same for the right-hand side.
            stBuilder = justSTBuilder(pushNewOrScope(exp@\loc, stBuilder));
            ItemId orScope2 = head(stBuilder.scopeStack);
            stBuilder = handleExpression(e2, stBuilder);
            stBuilder = popScope(stBuilder);

            // Merge the names shared by both branches of the or into the current scope
            stBuilder = mergeOrScopes(stBuilder, [orScope1, orScope2], head(stBuilder.scopeStack));

            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Equivalence
        case (Expression)`<Expression e1> <==> <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }

            // First, push a scope for the left-hand side of the or and evaluate
            // the expression there
            stBuilder = justSTBuilder(pushNewOrScope(exp@\loc, stBuilder));
            ItemId orScope1 = head(stBuilder.scopeStack);
            stBuilder = handleExpression(e1, stBuilder);
            stBuilder = popScope(stBuilder);

            // Now, do the same for the right-hand side.
            stBuilder = justSTBuilder(pushNewOrScope(exp@\loc, stBuilder));
            ItemId orScope2 = head(stBuilder.scopeStack);
            stBuilder = handleExpression(e2, stBuilder);
            stBuilder = popScope(stBuilder);

            // Merge the names shared by both branches of the or into the current scope
            stBuilder = mergeOrScopes(stBuilder, [orScope1, orScope2], head(stBuilder.scopeStack));

            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

		// And
        case (Expression)`<Expression e1> && <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handleExpression(e2, handleExpression(e1,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Or
        case (Expression)`<Expression e1> || <Expression e2>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }

            // First, push a scope for the left-hand side of the or and evaluate
            // the expression there
            stBuilder = justSTBuilder(pushNewOrScope(exp@\loc, stBuilder));
            ItemId orScope1 = head(stBuilder.scopeStack);
            stBuilder = handleExpression(e1, stBuilder);
            stBuilder = popScope(stBuilder);

            // Now, do the same for the right-hand side.
            stBuilder = justSTBuilder(pushNewOrScope(exp@\loc, stBuilder));
            ItemId orScope2 = head(stBuilder.scopeStack);
            stBuilder = handleExpression(e2, stBuilder);
            stBuilder = popScope(stBuilder);

            // Merge the names shared by both branches of the or into the current scope
            stBuilder = mergeOrScopes(stBuilder, [orScope1, orScope2], head(stBuilder.scopeStack));

            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Match
        case (Expression)`<Pattern p> := <Expression e>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handlePattern(p, handleExpression(e,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // NoMatch
        case (Expression)`<Pattern p> !:= <Expression e>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handlePattern(p, handleExpression(e,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Enumerator
        case (Expression)`<Pattern p> <- <Expression e>` : {
            bool popAtTheEnd = false;
            if (! inBoolLayer (stBuilder)) {
                stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));
                popAtTheEnd = true;
            }
            stBuilder = handlePattern(p, handleExpression(e,stBuilder));
            if (popAtTheEnd) stBuilder = popScope(stBuilder);
        }

        // Set Comprehension
        case (Expression) `{ <{Expression ","}+ el> | <{Expression ","}+ er> }` : {
            // Open a new boolean scope for the generators, this makes them available on the left
            stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));

            for (e <- er) stBuilder = handleExpression(e, stBuilder);
            for (e <- el) stBuilder = handleExpression(e, stBuilder);

            // Now pop the scope to take the names out of scope
            stBuilder = popScope(stBuilder);
        }

        // List Comprehension
        case (Expression) `[ <{Expression ","}+ el> | <{Expression ","}+ er> ]` : {
            // Open a new boolean scope for the generators, this makes them available on the left
            stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));

            for (e <- er) stBuilder = handleExpression(e, stBuilder);
            for (e <- el) stBuilder = handleExpression(e, stBuilder);

            // Now pop the scope to take the names out of scope
            stBuilder = popScope(stBuilder);
        }

        // Map Comprehension
        case (Expression) `( <Expression ef> : <Expression et> | <{Expression ","}+ er> )` : {
            // Open a new boolean scope for the generators, this makes them available on the left
            stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));

            for (e <- er) stBuilder = handleExpression(e, stBuilder);
            stBuilder = handleExpression(et, handleExpression(ef, stBuilder));

            // Now pop the scope to take the names out of scope
            stBuilder = popScope(stBuilder);
        }

        // Reducer
        case (Expression)`( <Expression ei> | <Expression er> | <{Expression ","}+ egs> )` : {
            stBuilder = handleExpression(ei, stBuilder);

            // Open a new boolean scope for the generators, this makes them available in the reducer
            stBuilder = justSTBuilder(pushNewBooleanScope(exp@\loc, stBuilder));

            // Calculate the scope info for the generators and expressors; we add "it" as a variable automatically
            for (e <- egs) stBuilder = handleExpression(e, stBuilder);
            stBuilder = addFreshVariable(RSimpleName("it"), ei@\loc, stBuilder);
            stBuilder = handleExpression(er, stBuilder);

            // Switch back to the prior scope to take expression bound names and "it" out of scope
            stBuilder = popScope(stBuilder);			
        }

        // It
        case (Expression)`it` :
            stBuilder = handleExpName(RSimpleName("it"),exp@\loc,stBuilder);

        // All 
        case (Expression)`all ( <{Expression ","}+ egs> )` :
            for (e <- egs) stBuilder = handleExpression(e, stBuilder);

        // Any 
        case (Expression)`all ( <{Expression ","}+ egs> )` :
            for (e <- egs) stBuilder = handleExpression(e, stBuilder);
    }

    // Logic for handling maps -- we cannot directly match them, so instead we need to pick apart the tree
    // representing the map.
    // exp[0] is the production used, exp[1] is the actual parse tree contents
    if (prod(_,_,attrs([_*,term(cons("Map")),_*])) := exp[0]) {
        stBuilder = handleMapExpression(exp, stBuilder);
    }

    return stBuilder;
}

public STBuilder handleStringTemplate(StringTemplate s, STBuilder stBuilder) {
    switch(s) {
        case (StringTemplate)`for (<{Expression ","}+ gens>) { <Statement* pre> <StringMiddle body> <Statement* post> }` : {
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- gens) stBuilder = handleExpression(e, stBuilder);
            for (st <- pre) stBuilder = handleStatement(st, stBuilder);
            list[Tree] ipl = prodFilter(body, 
                             bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
            for (st <- post) stBuilder = handleStatement(st, stBuilder);
            stBuilder = popScope(stBuilder);		
		}

        case (StringTemplate)`if (<{Expression ","}+ conds>) { <Statement* pre> <StringMiddle body> <Statement* post> }` : {
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- conds) stBuilder = handleExpression(e, stBuilder);
            for (st <- pre) stBuilder = handleStatement(st, stBuilder);
            list[Tree] ipl = prodFilter(body, 
                             bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
            for (st <- post) stBuilder = handleStatement(st, stBuilder);
            stBuilder = popScope(stBuilder);		
        }

        case (StringTemplate)`if (<{Expression ","}+ conds>) { <Statement* preThen> <StringMiddle bodyThen> <Statement* postThen> } else { <Statement* preElse> <StringMiddle bodyElse> <Statement* postElse> }` : {
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (e <- conds) stBuilder = handleExpression(e, stBuilder);
            for (st <- preThen) stBuilder = handleStatement(st, stBuilder);
            list[Tree] ipl = prodFilter(bodyThen, 
                             bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
            for (st <- postThen) stBuilder = handleStatement(st, stBuilder);
            for (st <- preElse) stBuilder = handleStatement(st, stBuilder);
            ipl = prodFilter(bodyElse, 
                  bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
            for (st <- postElse) stBuilder = handleStatement(st, stBuilder);
            stBuilder = popScope(stBuilder);		
        }

        case (StringTemplate)`while (<Expression cond>) { <Statement* pre> <StringMiddle body> <Statement* post> }` : {
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            stBuilder = handleExpression(cond, stBuilder);
            for (st <- pre) stBuilder = handleStatement(st, stBuilder);
            list[Tree] ipl = prodFilter(body, 
                             bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
            for (st <- post) stBuilder = handleStatement(st, stBuilder);
            stBuilder = popScope(stBuilder);		
        }

        case (StringTemplate)`do { <Statement* pre> <StringMiddle body> <Statement* post> } while (<Expression cond>)` : {
            stBuilder = justSTBuilder(pushNewBooleanScope(s@\loc, stBuilder));
            for (st <- pre) stBuilder = handleStatement(st, stBuilder);
            list[Tree] ipl = prodFilter(body, 
                             bool(Production prd) { return prod(_,\cf(sort("Expression")),_) := prd || prod(_,\cf(sort("StringTemplate")),_) := prd; });
            for (ipe <- ipl) {
                if (`<Expression ipee>` := ipe)
                    stBuilder = handleExpression(ipee, stBuilder);
                else if (`<StringTemplate ipet>` := ipe)
                    stBuilder = handleStringTemplate(ipet, stBuilder);
            }
            for (st <- post) stBuilder = handleStatement(st, stBuilder);
            stBuilder = handleExpression(cond, stBuilder);
            stBuilder = popScope(stBuilder);		
        }
    }

    return stBuilder;
}

public STBuilder handleCase(Case c, STBuilder stBuilder) {
    switch(c) {
        case (Case)`case <PatternWithAction p>` :
            stBuilder = handlePatternWithAction(p, stBuilder);

        case (Case)`default : <Statement b>` :
            stBuilder = handleStatement(b, stBuilder);
    }

    return stBuilder;
}

public STBuilder handleAssignable(Assignable a, STBuilder stBuilder) {
	switch(a) {
		// Name _
		case (Assignable)`_` :
			stBuilder = addFreshAnonymousVariable(a@\loc, stBuilder);
	
		// Assignment to a variable
		case (Assignable)`<QualifiedName qn>` : {
			if (size(getItems(stBuilder, head(stBuilder.scopeStack), convertName(qn), FCVs())) > 0) {		
				stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), convertName(qn), FCVs()), qn@\loc);
			} else {
				stBuilder = addFreshVariable(convertName(qn), qn@\loc, stBuilder);			
			}
		}
		
		// Subscript assignment
		case (Assignable)`<Assignable al> [ <Expression e> ]` :
			stBuilder = handleExpression(e, handleAssignable(al, stBuilder));			

		// Field assignment, since the field name is part of the type, not a declared variable, we don't mark it here
		case (Assignable)`<Assignable al> . <Name n>` :
			stBuilder = handleAssignable(al, stBuilder);
		
		// If-defined assignment
		case (Assignable)`<Assignable al> ? <Expression e>` :
			stBuilder = handleExpression(e, handleAssignable(al, stBuilder));			
		
		// Annotation assignment
		case (Assignable)`<Assignable al> @ <Name n>` : {
			stBuilder = handleAssignable(al, stBuilder);
			stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), convertName(n), Annotations()), n@\loc);
		}

		// Tuple assignable, with just one tuple element		
		case (Assignable)`< <Assignable ai> >` :
			stBuilder = handleAssignable(ai, stBuilder);

		// Tuple assignable, with multiple elements in the tuple
		case (Assignable)`< <Assignable ai>, <{Assignable ","}* al> >` : {
			stBuilder = handleAssignable(ai, stBuilder);
			for (ali <- al) stBuilder = handleAssignable(ali, stBuilder);
		}
		
		default : 
			throw "Found unhandled assignable case during namespace construction: <a>";
	}
	
	return stBuilder;
}

//
// Build symbol table information for local variable declarations. We do allow shadowing of names declared
// outside the function, but we do not allow shadowing inside the function, so our duplicates check is
// function bounded (see STBuilder for the implementation).
//
public STBuilder handleLocalVarItems(Type t, {Variable ","}+ vs, STBuilder stBuilder) {
    for (vb <- vs) {
        if ((Variable)`<Name n>` := vb || (Variable)`<Name n> = <Expression e>` := vb) {
            if (size(getItemsForConflicts(stBuilder, head(stBuilder.scopeStack), convertName(n), FCVs())) > 0) {
                stBuilder = addScopeError(stBuilder, n@\loc, "Illegal redefinition of <n>.");
            } else {
                ConvertTuple ct = convertRascalType(stBuilder, t);
                RType varType = ct.rtype; stBuilder = ct.stBuilder;
                stBuilder = justSTBuilder(addVariableToScope(convertName(n), varType, true, vb@\loc, stBuilder));
            } 
        }
		
        if ((Variable)`<Name n> = <Expression e>` := vb) {		
            stBuilder = handleExpression(e, stBuilder);
        }
    }
    return stBuilder;
}

public STBuilder handleCatch(Catch c, STBuilder stBuilder) {
    switch(c) {
        case (Catch)`catch : <Statement b>` :
            stBuilder = handleStatement(b, stBuilder);
		
        case (Catch)`catch <Pattern p> : <Statement b>` : {
            stBuilder = justSTBuilder(pushNewBooleanScope(c@\loc, stBuilder));
            stBuilder = handleStatement(b, handlePattern(p, stBuilder));
            stBuilder = popScope(stBuilder);
        }
    }

    return stBuilder;
}		

public STBuilder handleLabel(Label l, STBuilder stBuilder) {
    if ((Label)`<Name n> :` := l) {
        // First, check to see if this label already exists
        set[ItemId] ls = getItemsForConflicts(stBuilder, head(stBuilder.scopeStack), convertName(n), Labels());
        if (size(ls) > 0) {
            stBuilder = addScopeError(stBuilder, n@\loc, "Label <n> has already been defined.");
        } else {
            stBuilder = justSTBuilder(addLabelToScope(convertName(n), l@\loc, stBuilder));
        }					
    } 
    return stBuilder;
}

public STBuilder handleVisit(Visit v, STBuilder stBuilder) {
	if ((Visit)`visit (<Expression se>) { <Case+ cs> }` := v || (Visit)`<Strategy st> visit (<Expression se>) { <Case+ cs> }` := v) {
		stBuilder = handleExpression(se, stBuilder);
		for (c <- cs) stBuilder = handleCase(c, stBuilder);
	}
	return stBuilder;
}

public STBuilder handleMapPattern(Pattern pat, STBuilder stBuilder) {
    list[tuple[Pattern mapDomain, Pattern mapRange]] mapContents = getMapPatternContents(pat);
    for (<md,mr> <- mapContents) stBuilder = handlePattern(mr, handlePattern(md, stBuilder));
    return stBuilder;
}

//
// TODO: We don't handle interpolation here. Does it make sense to allow this inside
// either string or location patterns? (for instance, to create the string to match against?)
//
public STBuilder handlePattern(Pattern pat, STBuilder stBuilder) {
	STBuilder handlePatternName(RName n, loc l, STBuilder stBuilder) {
		if (size(getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {		
			stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs()), l);
		} else {
			stBuilder = addFreshVariable(n, l, stBuilder);
		}
		return stBuilder;
	}
	
	STBuilder handleListMultiPatternName(RName n, loc l, STBuilder stBuilder) {
		if (size(getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {		
			stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs()), l);
		} else {
			stBuilder = addFreshListVariable(n, l, stBuilder);
		}
		return stBuilder;
	}
		
    STBuilder handleSetMultiPatternName(RName n, loc l, STBuilder stBuilder) {
        if (size(getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {     
            stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs()), l);
        } else {
            stBuilder = addFreshSetVariable(n, l, stBuilder);
        }
        return stBuilder;
    }

	STBuilder handleTypedPatternName(RName n, RType t, loc l, loc pl, STBuilder stBuilder) {
		if (size(getItemsForConflicts(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {
			set[ItemId] conflictItems = getItemsForConflicts(stBuilder, head(stBuilder.scopeStack), n, FCVs());
			set[loc] conflictLocations = { stBuilder.scopeItemMap[si].definedAt | si <- conflictItems };		
			stBuilder = addScopeError(stBuilder, l, "Illegal shadowing of already declared name <prettyPrintName(n)>; other declarations at <conflictLocations>");
		} else {
			stBuilder = justSTBuilder(addVariableToScope(n, t, false, pl, stBuilder));
		}
		
        // Handle any type variables in the type of the parameter IF this is in a function scope
        // (i.e., if this is a parameter declaration)
        if (Function(_,_,_,_,_,_,_,_) := stBuilder.scopeItemMap[head(stBuilder.scopeStack)]) {
            for(tvv <- collectTypeVars(t)) {
                set[ItemId] tvItems = getItems(stBuilder, head(stBuilder.scopeStack), getTypeVarName(tvv), TypeVars());
                if (size(tvItems) == 0) {
                    stBuilder = justSTBuilder(addTypeVariableToScope(tvv, pl, stBuilder));
                } else {
                   // TODO: We should just have one, check to see if we have more
                   RType tvType = stBuilder.scopeItemMap[getOneFrom(tvItems)].typeVar;
                   if (tvType.varTypeBound != tvv.varTypeBound) {
                        stBuilder = addScopeError(stBuilder, pl, "Illegal redefinition of bound on type variable <prettyPrintName(tvv.varName)> with existing bound <prettyPrintType(tvType.varTypeBound)>.");        
                   }
                }
            }
        }
        
		return stBuilder;
	}	

	switch(pat) {
        // Regular Expression literal
        case (Pattern)`<RegExpLiteral rl>` : {
            list[Tree] names = prodFilter(rl, bool(Production prd) { return prod(_,sort("Name"),_) := prd; });
            // For each name, either introduce it into scope, or tag the use of an existing name; we can
            // assume that names are of type string, since they will hold parts of strings (but will check
            // this during type checking in case names we don't introduce aren't actually strings)
            for (n <- names) {
                RName rn = RSimpleName("<n>");
                if (size(getItems(stBuilder, head(stBuilder.scopeStack), rn, FCVs())) > 0) {
                    stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), rn, FCVs()), n@\loc);
                } else {
                    stBuilder = justSTBuilder(addVariableToScope(rn, makeStrType(), false, n@\loc, stBuilder));
                }
            }
        }

        // Name _
        case (Pattern)`_` : {
            stBuilder = addFreshAnonymousVariable(pat@\loc, stBuilder);
        }			

        // Name other than _
        case (Pattern)`<Name n>` : {
            stBuilder = handlePatternName(convertName(n), n@\loc, stBuilder);
        }
		
		// QualifiedName
        case (Pattern)`<QualifiedName qn>` : {
            stBuilder = handlePatternName(convertName(qn), qn@\loc, stBuilder);
        }

        // ReifiedType
        // TODO: How much should we enforce that pl specifies types? Or, should
        // this all be deferred to the checker? (For instance, can this ever
        // be size(pl) > 1?)
        case (Pattern) `<BasicType t> ( <{Pattern ","}* pl> )` : {
            for (p <- pl) stBuilder = handlePattern(p, stBuilder);
        }

        // CallOrTree
        case (Pattern) `<Pattern p1> ( <{Pattern ","}* pl> )` : {
            stBuilder = handlePatternConstructorName(p1, stBuilder);
            for (p <- pl) stBuilder = handlePattern(p, stBuilder);
        }

        // List
        case (Pattern) `[<{Pattern ","}* pl>]` : {
            for (p <- pl) {
                if ((Pattern)`_*` := p) {
                    stBuilder = addFreshAnonymousListVariable(pat@\loc, stBuilder);
                } else if ((Pattern)`<QualifiedName qn> *` := p) {
                    stBuilder = handleListMultiPatternName(convertName(qn), qn@\loc, stBuilder);
                } else {
                    stBuilder = handlePattern(p, stBuilder);
                }
            }
        }

        // Set
        case (Pattern) `{<{Pattern ","}* pl>}` : {
            for (p <- pl) {
                if ((Pattern)`_*` := p) {
                    stBuilder = addFreshAnonymousSetVariable(pat@\loc, stBuilder);
                } else if ((Pattern)`<QualifiedName qn> *` := p) {
                    stBuilder = handleSetMultiPatternName(convertName(qn), qn@\loc, stBuilder);
                } else {
                    stBuilder = handlePattern(p, stBuilder);
                }
            }
        }

        // Tuple, with just one element
        case (Pattern) `<<Pattern pi>>` : {
            // println("NAMESPACE: Handling tuple pattern <pat>");
            stBuilder = handlePattern(pi, stBuilder);
        }

        // Tuple, with multiple elements
        case (Pattern) `<<Pattern pi>, <{Pattern ","}* pl>>` : {
            // println("NAMESPACE: Handling tuple pattern <pat>");
            stBuilder = handlePattern(pi, stBuilder);
            for (pli <- pl) stBuilder = handlePattern(pli, stBuilder);
        }

        // Typed Variable
        case (Pattern) `<Type t> <Name n>` : {
            // println("NAMESPACE: Handling typed variable pattern <pat>");
            ConvertTuple ct = convertRascalType(stBuilder, t);
            RType varType = ct.rtype; stBuilder = ct.stBuilder;
            stBuilder = handleTypedPatternName(convertName(n),varType,n@\loc,pat@\loc,stBuilder);
        }

// TODO: These two should not appear outside of a list or set pattern. Verify
// this, since we are only supporting them above for now!
        // Anonymous Multi Variable
//        case (Pattern) `_ *` : {
//            // println("NAMESPACE: Handling multivariable pattern <pat>");
//            stBuilder = addFreshAnonymousContainerVariable(pat@\loc, stBuilder);
//        }			

        // Multi Variable
//        case (Pattern) `<QualifiedName qn> *` : {
//            // println("NAMESPACE: Handling multivariable pattern <pat>");
//            stBuilder = handleMultiPatternName(convertName(qn), qn@\loc, stBuilder);
//        }

        // Descendant
        case (Pattern) `/ <Pattern p>` : {
            // println("NAMESPACE: Handling descendant pattern <pat>");
            stBuilder = handlePattern(p, stBuilder);
        }

        // Variable Becomes
        case (Pattern) `<Name n> : <Pattern p>` : {
            // println("NAMESPACE: Handling variable becomes pattern <pat>");
            stBuilder = handlePattern(p, handlePatternName(convertName(n), n@\loc, stBuilder));
        }
		
        // Typed Variable Becomes
        case (Pattern) `<Type t> <Name n> : <Pattern p>` : {
            // println("NAMESPACE: Handling typed variable becomes pattern <pat>");
            ConvertTuple ct = convertRascalType(stBuilder, t);
            RType varType = ct.rtype; stBuilder = ct.stBuilder;
            stBuilder = handlePattern(p, handleTypedPatternName(convertName(n),varType,n@\loc,pat@\loc,stBuilder));
        }
		
        // Guarded
        case (Pattern) `[ <Type t> ] <Pattern p>` : {
            // println("NAMESPACE: Handling guarded pattern <pat>");
            ConvertTuple ct = convertRascalType(stBuilder, t); // Just to check the type, we don't use it here
            stBuilder = handlePattern(p, ct.stBuilder);
        }
		
        // Anti
        case (Pattern) `! <Pattern p>` : {
            // println("NAMESPACE: Handling anti pattern <pat>");
            stBuilder = handlePattern(p, stBuilder);
        }
	}
	
    // Logic for handling maps -- we cannot directly match them, so instead we need to pick apart the tree
    // representing the map.
    // pat[0] is the production used, pat[1] is the actual parse tree contents
    if (prod(_,_,attrs([_*,term(cons("Map")),_*])) := pat[0]) {
        stBuilder = handleMapPattern(pat, stBuilder);
    }

    return stBuilder;
}

//
// We have separate logic here since we don't allow general patterns to be used for the constructor
// position in a call or tree pattern. These patterns can be 1) constructors, or 2) nodes. We cannot
// use the other two options here, 3) functions, and 4) locations -- these cannot be used in pattern
// matches. For the constructor position, this means that we either need a name or qualified name for
// constructor matches, and either a string literal or a name of some sort (including a typed
// variable name) for node patterns.
//
// TODO: We may be able to just call back to handlePattern here, instead of adding our own
// logic.
//
public STBuilder handlePatternConstructorName(Pattern pat, STBuilder stBuilder) {
	STBuilder handlePatternName(RName n, loc l, STBuilder stBuilder) {
		if (size(getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {
            // TODO: Should we check here to verify that we have an actual constructor name? Or just let
            // this go until type checking? Probably the latter, we have more info then...
            stBuilder = addItemUses(stBuilder, getItems(stBuilder, head(stBuilder.scopeStack), n, FCVs()), l);
		} else {
			stBuilder = addScopeError(stBuilder, l, "Constructor name <prettyPrintName(n)> must be declared, in scope <head(stBuilder.scopeStack)>");
		}
		return stBuilder;
	}
	
    STBuilder handleTypedPatternName(RName n, RType t, loc l, loc pl, STBuilder stBuilder) {
        if (size(getItemsForConflicts(stBuilder, head(stBuilder.scopeStack), n, FCVs())) > 0) {
            set[ItemId] conflictItems = getItemsForConflicts(stBuilder, head(stBuilder.scopeStack), n, FCVs());
            set[loc] conflictLocations = { stBuilder.scopeItemMap[si].definedAt | si <- conflictItems };      
            stBuilder = addScopeError(stBuilder, l, "Illegal shadowing of already declared name <prettyPrintName(n)>; other declarations at <conflictLocations>");
        } else {
            stBuilder = justSTBuilder(addVariableToScope(n, t, false, pl, stBuilder));
        }
        return stBuilder;
    }   
	
	switch(pat) {
        case (Pattern)`_` :
            stBuilder = addScopeError(stBuilder, pat@\loc, "Illegal pattern for constructor or node name");

		case (Pattern)`<Name n>` :
			stBuilder = handlePatternName(convertName(n), n@\loc, stBuilder);
		
		case (Pattern)`<QualifiedName qn>` :
			stBuilder = handlePatternName(convertName(qn), qn@\loc, stBuilder);

		case (Pattern)`<StringLiteral sl>` :
		        stBuilder = stBuilder; // no-op, we get no new names, just avoid the default below

        // Typed Variable
        // TODO: We need to make sure the type checker has logic to 1) allow this case, and 2) verify the type
        // is string.
        case (Pattern) `<Type t> <Name n>` : {
            ConvertTuple ct = convertRascalType(stBuilder, t);
            RType varType = ct.rtype; stBuilder = ct.stBuilder;
            stBuilder = handleTypedPatternName(convertName(n),varType,n@\loc,pat@\loc,stBuilder);
        }

		default :
			stBuilder = addScopeError(stBuilder, pat@\loc, "Illegal pattern for constructor or node name");
	}
	
	return stBuilder;
}

//
// Extract scope information from PatternWithAction nodes. Note that this opens a
// new scope, since we can bind variables in the pattern which should then be available
// in the right hand side expression(s) and/or statements. The new scope is closed (popped)
// on the way out of the function.
//
public STBuilder handlePatternWithAction(PatternWithAction pwa, STBuilder stBuilder) {
    stBuilder = justSTBuilder(pushNewPatternMatchScope(pwa@\loc, stBuilder));

	switch(pwa) {
		case (PatternWithAction)`<Pattern p> => <Expression e>` :
			stBuilder = handleExpression(e, handlePattern(p, stBuilder));
		
		case (PatternWithAction)`<Pattern p> => <Expression er> when <{Expression ","}+ es>` : {
			stBuilder = handlePattern(p, stBuilder);
			for (e <- es) stBuilder = handleExpression(e, stBuilder);
			stBuilder = handleExpression(er, stBuilder);
		}
		
		case (PatternWithAction)`<Pattern p> : <Statement s>` :
			stBuilder = handleStatement(s, handlePattern(p, stBuilder));			
		
        default : throw "Unexpected Pattern With Action syntax, <pwa>";
	}
	
	return popScope(stBuilder);
}

public STBuilder handleDataTarget(DataTarget dt, STBuilder stBuilder) {
	if ((DataTarget)`<Name n> :` := dt) {
		set[ItemId] items = getItems(stBuilder, head(stBuilder.scopeStack), convertName(n), Labels());
		if (size(items) == 1) {
			stBuilder = addItemUses(stBuilder, items, n@\loc);
		} else if (size(items) == 0) {
			stBuilder = addScopeError(stBuilder, n@\loc, "Label <n> has not been defined.");			
		} else {
			stBuilder = addScopeError(stBuilder, n@\loc, "Label <n> has multiple definitions.");
		}
	}
	return stBuilder;
}

public STBuilder handleTarget(Target t, STBuilder stBuilder) {
	if ((Target)`<Name n>` := t) {
		set[ItemId] items = getItems(stBuilder, head(stBuilder.scopeStack), convertName(n), Labels());
		if (size(items) == 1) {
			stBuilder = addItemUses(stBuilder, items, n@\loc);
		} else if (size(items) == 0) {
			stBuilder = addScopeError(stBuilder, n@\loc, "Label <n> has not been defined.");			
		} else {
			stBuilder = addScopeError(stBuilder, n@\loc, "Label <n> has multiple definitions.");
		}
	}
	return stBuilder;
}

// TODO: Add tag handling here
public STBuilder handleTagsNamesOnly(Tags ts, STBuilder stBuilder) {
	return stBuilder;
}

// TODO: Add tag handling here
public STBuilder handleTags(Tags ts, STBuilder stBuilder) {
	return stBuilder;
}


//
// Routines to add inference vars of various types
//

public STBuilder addFreshVariable(RName n, loc nloc, STBuilder stBuilder) {
    RType freshType = makeInferredType(stBuilder.freshType);
    stBuilder.inferredTypeMap[stBuilder.freshType] = freshType;
    if (RSimpleName("it") := n) stBuilder.itBinder[nloc] = freshType;
    stBuilder.freshType = stBuilder.freshType + 1;
    stBuilder = justSTBuilder(addVariableToScope(n, freshType, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshAnonymousVariable(loc nloc, STBuilder stBuilder) {
    RType freshType = makeInferredType(stBuilder.freshType);
    stBuilder.inferredTypeMap[stBuilder.freshType] = freshType;
    stBuilder.freshType = stBuilder.freshType + 1;
    stBuilder = justSTBuilder(addVariableToScope(RSimpleName("_"), freshType, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshListVariable(RName n, loc nloc, STBuilder stBuilder) {
    RType freshType = makeListType(makeInferredType(stBuilder.freshType));
    stBuilder.inferredTypeMap[stBuilder.freshType] = getListElementType(freshType);
    stBuilder.freshType = stBuilder.freshType + 1;
    stBuilder = justSTBuilder(addVariableToScope(n, freshType, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshSetVariable(RName n, loc nloc, STBuilder stBuilder) {
    RType freshType = makeSetType(makeInferredType(stBuilder.freshType));
    stBuilder.inferredTypeMap[stBuilder.freshType] = getSetElementType(freshType);
    stBuilder.freshType = stBuilder.freshType + 1;
    stBuilder = justSTBuilder(addVariableToScope(n, freshType, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshAnonymousListVariable(loc nloc, STBuilder stBuilder) {
    RType freshType = makeListType(makeInferredType(stBuilder.freshType));
    stBuilder.inferredTypeMap[stBuilder.freshType] = getListElementType(freshType);
    stBuilder.freshType = stBuilder.freshType + 1;
    stBuilder = justSTBuilder(addVariableToScope(RSimpleName("_"), freshType, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshAnonymousSetVariable(loc nloc, STBuilder stBuilder) {
    RType freshType = makeSetType(makeInferredType(stBuilder.freshType));
    stBuilder.inferredTypeMap[stBuilder.freshType] = getSetElementType(freshType);
    stBuilder.freshType = stBuilder.freshType + 1;
    stBuilder = justSTBuilder(addVariableToScope(RSimpleName("_"), freshType, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshVariableWithType(RName n, loc nloc, RType rt, STBuilder stBuilder) {
    if (RSimpleName("it") := n) stBuilder.itBinder[nloc] = rt;
    stBuilder = justSTBuilder(addVariableToScope(n, rt, false, nloc, stBuilder));
    return stBuilder;
}

public STBuilder addFreshAnonymousVariableWithType(loc nloc, RType rt, STBuilder stBuilder) {
    stBuilder = justSTBuilder(addVariableToScope(RSimpleName("_"), rt, false, nloc, stBuilder));
    return stBuilder;
}
