module lang::rascal::checker::TTL::TTLGen

extend lang::rascal::\syntax::Rascal;
import IO;
import String;
import List;

start syntax TTL = ttl: TestItem* items;

start syntax TestItem =
	  defMod:     "define" Name name "{" Module moduleText "}"
	| defDecl:    "define" Name name "{" Declaration declaration "}"
	| defTest:    "test" Name* names "{" Use use Statement+ statements "}" "expect" "{" {Expect ","}* expectations "}" 
	| defInfix:   "infix" Name name {StringLiteral ","}+ operators "{" {BinarySignature ","}+ signatures "}"
	| defPrefix:  "prefix" Name name {StringLiteral ","}+ operators "{" {UnarySignature ","}+ signatures "}"
	| defPostfix: "postfix" Name name {StringLiteral ","}+ operators "{" {UnarySignature ","}+ signatures "}"
	;

syntax BinarySignature = ExtendedType left "x" ExtendedType right "-\>" ExtendedType result Condition condition;
syntax UnarySignature = ExtendedType left "-\>" ExtendedType result Condition condition;

syntax Condition = 
       nonempty: "when" "&" Name name "is" "not" "a" RascalKeywords typeName
     | empty: ()
     ;

syntax ExtendedType =
       intType: "int"          
     | boolType: "bool"
     | realType: "real"
     | ratType: "rat"
     | strType: "str"
     | numType: "num"
     | nodeType: "node"
     | voidType: "void"
     | valueType: "value"
     | locType: "loc"
     | datetimeType: "datetime"
     | listType: "list" "[" ExtendedType elemType "]"
     | lrelType:  "lrel" "["  {ExtendedType ","}+ elemTypes"]"
     | setType:  "set" "[" ExtendedType elemType "]"
     | relType:  "rel" "["  {ExtendedType ","}+ elemTypes "]"
     | mapType:  "map" "[" ExtendedType keyType "," ExtendedType valType "]"
     | tupleType: "tuple" "[" {ExtendedType ","}+ elemTypes "]"
     | lubType:  "LUB" "(" ExtendedType left "," ExtendedType right ")"
     | typeVar: "&" Name name
     | typeVarBounded: "&" Name name "\<:" ExtendedType bound
     ;

start syntax Use = use: "use" Name+ names "::" |  none: ()  ;

start syntax Expect =
         inferred: Type expectedType Name name
       | message: RegExpLiteral regexp
       | exception: Name name
       ;  
       
loc TTLRoot = |project://RascalStandardLibrary/lang/rascal/checker/TTL/|;
str TTL = "ttl";

void main() { 
  for(ttl <- (TTLRoot + "specs").ls, ttl.extension == TTL)
      generate(ttl); 
}

str basename(loc l) = l.file[ .. findFirst(l.file, ".")];
       
data Symbol = LUB(Symbol l, Symbol r);

void generate(loc src){
   spec = parse(#TTL, src);
   map[Name, Declaration] decls = ();
   map[Name, Module] modules = ();
   str tests = "";
   for(TestItem item <- spec.items){
       if(defMod(name, moduleText) := item){// Was: item is defMod){
       	     if(decls[name]?) throw "Ambiguous name <name> at <item@\loc>";
       	     if(modules[name]?) throw "Redeclared module name <name> at <item@\loc>";
             modules[name] = item.moduleText;
             writeFile(TTLRoot + "generated/<item.name>", item.moduleText); // TODO: Imports from different ttl files could conflict
       } else if(defDecl(name, declaration) := item){
       		if(modules[name]?) throw "Ambiguous name <name> at <item@\loc>";
       	     if(decls[name]?) throw "Redeclared declaration name <name> at <item@\loc>";
             decls[name] = declaration;
       } else if(item is defTest){
          tests += genTest(item, decls, modules);
       } else if(item is defInfix){
          tests += genInfix(item);
       } else if(item is defPrefix){
         tests += genUnary(item, true);
       } else if(item is defPostfix){
         tests += genUnary(item, false);
       } else {
         println("Skipped: <item>");
       }
   }
   generatedTests = "TypeCheckTests";
   typechecker = "Typechecker";
   code = "module lang::rascal::checker::TTL::generated::<basename(src)>
          'import lang::rascal::checker::TTL::Library;
          'import Type;
          'import IO;
          'import util::Eval;
          '<tests>
          '";
   writeFile(TTLRoot + "generated/<basename(src)>.rsc", code);
}

str genTest(TestItem item,  map[Name, Declaration] declarations,  map[Name, Module] modules){
  <imports, decls> = expandUsedNames(getUsedNames(item.use), declarations, modules);
  <inferred, messages, exception> = getExpectations([e | e <- item.expectations]);
 
  escapedChars =  ("\"" : "\\\"", "\\" : "\\\\");
  decls = [escape(d, escapedChars) | d <- decls];
  code = escape("<item.statements>", escapedChars);

  if(!isEmpty(exception))
  	exception = "@expect{<exception>}";
  	
  inferredChecks = "";
  for(<var, tp> <- inferred){
    inferredChecks += "if(!hasType(\"<var>\", #<tp>, checkResult)) return false;";
  }
  
  messageChecks = intercalate(" || ", ["<msg> := msg" | msg <- messages]);
  if(!isEmpty(messageChecks)){
     messageChecks = "for(msg \<-  getMessages(checkResult)){
		 			 '      if(<messageChecks>)
		 			 '	      return true;
					 '}";
  }
  checks = (isEmpty(inferredChecks) ? "" : inferredChecks) +
  		   (isEmpty(messageChecks) ? "" : "\n" + messageChecks);
    
  return "<exception>
  		 'test bool tst(){
         '  checkResult = checkExpString(\"<code>\", importedModules=<imports>, initialDecls = <decls>);
         '  <checks>
         '  return true;
         '}";
}

list[Name] getUsedNames(Use u){
    return (u is use) ? [name | name <- u.names] : [];
}

tuple[list[str],list[str]] expandUsedNames(list[Name] names, map[Name, Declaration] declarations,  map[Name, Module] modules){
    imports = [];
    decls = [];
    for(name <- names){
      if(modules[name]?)
         imports += "<name>";
      else if(declarations[name]?)
         decls += "<declarations[name]>";
      else
          throw "Undefined name <name> at <name@\loc>";
    }
    return <imports, decls>;
}

tuple[lrel[Name,Type],list[RegExpLiteral],str] getExpectations(list[Expect] expect){
  inferred = [];
  list[RegExpLiteral] message = [];
  exception = "";
  for(e <- expect){
      if(e is inferred){
         inferred += <e.name, e.expectedType>;
      } else if (e is message){
      	 message += e.regexp;
      } else {
        exception = "<e.name>";
      }
   }
   return <inferred, message, exception>;
}

str genCondition(sig){
    typeCondition = "\n";
    if(nonempty(name, typeName) := sig.condition){//sig has condition && sig.condition is nonempty){
       tname = "<typeName>";
       tname = toUpperCase(tname[0]) + tname[1..];
	   typeCondition = "if(is<tname>Type(bindings[\"<name>\"])) return true;\n";
    }
    return typeCondition;
}

str genInfix(TestItem item){
  tests = "";
  for(operator <- item.operators){
	  operatorName = "<operator>"[1..-1]; 
	  for(sig <- item.signatures){
	     typeCondition = "";
	     if(sig.condition is condition){
	        tname = "<sig.condition.typeName>";
	        tname = toUpperCase(tname[0]) + tname[1..];
	        typeCondition = "if(is<tname>Type(bindings[\"<sig.condition.name>\"])) return true;";
	     }
	     tests += "// Testing infix <item.name> <operatorName> for <sig>
	     		  'test bool tst(<sig.left> arg1, <sig.right> arg2){ 
	              '  ltype = typeOf(arg1);
	              '  rtype = typeOf(arg2);
	              '  if(isDateTimeType(ltype) || isDateTimeType(rtype))
	              '		return true;
	     		  '  <genArgument(sig.left, "l")>
	       		  '  <genArgument(sig.right, "r")>
				  '  if(lmatches && rmatches){
	              '     bindings = merge(lbindings, rbindings); 
	              '     <genCondition(sig)>
	              '     if(result(v) := eval(\"(\<escape(arg1)\>) <operatorName> (\<escape(arg2)\>);\")){ // apply the operator to its arguments
	              '        actualType = typeOf(v); 
	              '        expectedType = normalize(<toSymbol(sig.result)>, bindings);
	              '//      println(\"actual = \<actualType\>, expected = \<expectedType\>\");
	              '        return subtype(actualType, expectedType);
	              '     }
	              '  }
	              '  return false;
	              '}\n";
	  }
  }
  return tests;
}

str genUnary(TestItem item, bool prefix){
  tests = "";
  for(operator <- item.operators){
	  operatorName = "<operator>"[1..-1]; 
	  for(sig <- item.signatures){
	     expression = prefix ? "\"<operatorName> (\<escape(arg1)\>);\"" : "\"(\<escape(arg1)\>) <operatorName>;\"";
	     tests += "// Testing <prefix ? "prefix" : "postfix"> <item.name> <operatorName>
	     		  'test bool tst(<sig.left> arg1){ 
	              '  ltype = typeOf(arg1);
	              '  if(isDateTimeType(ltype))
	              '		return true;
	     		  '  <genArgument(sig.left, "l")>
				  '  if(lmatches){
				  '     <genCondition(sig)>
				  '	    if(result(v) := eval(<expression>)){ // apply the operator to its arguments
	              '        actualType = typeOf(v);
	              '        expectedType = normalize(<toSymbol(sig.result)>, lbindings);
	              '//      println(\"actual = \<actualType\>, expected = \<expectedType\>\");
	              '        return subtype(actualType, expectedType);
	              '		}
	              '  }
	              '  return false;
	              '}\n";
	  }
  }
  return tests;
}

str genArgument(ExtendedType a, str side) = "\<<side>matches, <side>bindings\> = bind(<toSymbol(a)>, <side>type);
                                    '// tp = <toSymbol(a)>; println(\"\<tp\>, <side>type = \<<side>type\>, <side>matches = \<<side>matches\> \<<side>bindings\>\");";

str toSymbol(ExtendedType t){
  if( t is intType) return  "\\int()";
  if( t is boolType) return  "\\bool()";
  if( t is realType) return  "\\real()";
  if( t is ratType) return  "\\rat()";
  if( t is strType) return  "\\str()";
  if( t is numType) return  "\\num()";
  if( t is nodeType) return  "\\node()";
  if( t is voidType) return  "\\void()";
  if( t is valueType) return  "\\value()";
  if( t is locType) return  "\\loc()";
  if( t is datetimeType) return  "\\datetime()";
  if(t is listType) return "\\list(<toSymbol(t.elemType)>)";
  if(t is setType) return "\\set(<toSymbol(t.elemType)>)";
  if(t is mapType) return "\\map(<toSymbol(t.keyType)>,<toSymbol(t.valType)>)";
  if(t is tupleType) return "\\tuple([<intercalate(",", [toSymbol(e) | e <- t.elemTypes])>])";
  if(t is relType) return "\\rel([<intercalate(",", [toSymbol(e) | e <- t.elemTypes])>])";
  if(t is lrelType) return "\\lrel([<intercalate(",", [toSymbol(e) | e <- t.elemTypes])>])";
  if(t is lubType) return "\\LUB(<toSymbol(t.left)>,<toSymbol(t.right)>)";
  if(t is typeVar) return "\\parameter(\"<t.name>\", \\value())";
  if(t is typeVarBounded) return "\\parameter(\"<t.name>\", <toSymbol(t.bound)>)";
  throw "unexpected case in toSymbol";
}
