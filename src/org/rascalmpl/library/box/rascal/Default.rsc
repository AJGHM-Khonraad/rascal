module box::rascal::Default
import ParseTree;
import box::Concrete;
import box::Box;
import IO;
import SystemAPI;
import box::rascal::Modules;
import box::rascal::Declarations;
import box::rascal::Constructors;
import box::rascal::Expressions;
import box::rascal::Statements;
import box::rascal::Types;
import box::rascal::Rascal;
import box::rascal::Keywords;


import rascal::\old-syntax::Rascal;

alias UserDefinedFilter = Box(Tree t) ;

list[UserDefinedFilter] userDefinedFilters = [ 
       getExpressions, 
       getStatements, 
       getModules, 
       getDeclarations, 
       getConstructors, 
       getRascal, 
       getTypes
       ];
       
list[int] isIndent(list[Symbol] q) {
       if (isScheme(q , ["N", "T", "(", "N", ")","{", "N","}"])) return [6, 7];
       if (isScheme(q , ["T", "(", "N", ")","{", "N","}"])) return [5, 6];  // visit
       return [];
       }
       
list[int] isBlok(list[Symbol] q, list[Tree] z) {
        if (isScheme(q , ["N","T", "(", "N", ")", "N"])) return isBlock(z, 5);  // for
        if (isScheme(q , ["N","T", "(", "N", ")", "N", "N"])) return isBlock(z, 5); // if then
        if (isScheme(q , ["N", "N", "N", "N"])) return isBody(z,3); // Visibility Signature FunctionBody
        if (isScheme(q , ["N","T", "(", "N", ")", "N", "else", "N"])) return [5,7];
                      // isBlock(z,5)+isBlock(z,7); If then else
        if (isScheme(q , ["T", "N", "N"])) return isBlock(z, 1);  // try
        if (isScheme(q , ["T", "T", "N"])) return isBlock(z, 2);  // catch
        if (isScheme(q , ["T", "N", "T", "N"])) return  isBlock(z, 3);  // catch
        if (isScheme(q , ["N", ":", "N"])) return isBlock(z, 2);  // pattern with action
          // switch visit
     return [];
     }

list[segment] isCompact(list[Symbol] q) {
        if (isScheme(q , ["N","T", "T", "N", "T", "N"])) return [<2,4>];  // for
        if (isScheme(q , ["N","T", "T", "N", "T", "N", "N"])) return [<2,4>]; // if then
        if (isScheme(q , ["N","T", "T", "N", "T", "N", "T", "N"])) return [<2,4>]; // if then else
        if (isScheme(q , ["N","[", "N", "]"])) return [<0,3>]; // if then else
        if (isScheme(q , ["N", "T", "(", "N", ")","{", "N","}"])) return [<2,4>]; // switch, visit
        if (isScheme(q , ["T", "(", "N", ")","{", "N","}"])) return [<1, 3>];  // visit
     return [];
     }

bool isSeparated(list[Symbol] q) {
     if (isScheme(q , ["N","N"])) return true;  // type name
     if (isScheme(q , ["case","N"])) return true;  // case pattern-with-action
     if (isScheme(q , ["return","N"])) return true; 
     if (isScheme(q , ["throw","N"])) return true; 
     return false;
     }
     
 bool isKeyword(Symbol a) {
     if (\lit(str s):=a) {
         if (s in keywords) return true;
         }
     return false;
     }
     
 bool isString(Symbol a) {
     if (\cf(\sort(str s)):=a) {
        // println("Found isString1 <s>");
        if (s=="StringLiteral") return true;
        }
     if (\lex(\sort(str s)):=a) {
        // println("Found isString2 <s>");
        if (s=="RegExpLiteral") return true;
        }
     return false;
     }

void setUserRules() {
    setUserDefined(extraRules);
    setIndent(isIndent);
    setBlock(isBlok);
    setCompact(isCompact);
    setKeyword(isKeyword);
    setString(isString);
    setSeparated(isSeparated);
    }  
      
public text toText(loc asf){
    //  println(locationIntro);
     // Tree a = parse(#Module, asf);
     str s = getRascalFileContent(asf);
     println("Txt: parse");
     Tree a = parse(#Module, s);
     setUserRules();
     text r = toText(a);
     writeData(asf, r, ".txt");
     return r;
     }
     
public text toLatex(loc asf){
     str s = getRascalFileContent(asf);
     println("toLatex: parse");
     Tree a = parse(#Module, s);
     println("toLatex: parsed");
     // rawPrintln(a);
     setUserRules();
     println("start:");
     text r =[];
     r = toText(a);
     writeData(asf, r, ".txt");
     r = toLatex(a);
     writeData(asf, r, ".tex");
     return r;
     }
     
public Box getDefault(Tree q) {
if (LocationLiteral a:=q) 
switch(a) {
	case `<ProtocolPart protocolPart> <PathPart pathPart>`: {
	         return STRING(L("<protocolPart><pathPart>"));
	         }
	
}
return NULL();
}    

// Don't change this part 

public Box extraRules(Tree q) {  
   for (UserDefinedFilter userDefinedFilter<-userDefinedFilters) {
           Box b = userDefinedFilter(q);
           if (b!=NULL()) return b;
           }
    b = getDefault(q);
    if (b!=NULL()) return b;
    return NULL();
    }
    
  

/*
public void main(){
    Tree a = parse(#Module, |file:///ufs/bertl/asfix/A.rsc|);
    concrete(a);
    }
*/
