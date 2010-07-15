module box::rascal::Default
import ParseTree;
import box::Concrete;
import box::Box;
import IO;
import box::rascal::Modules;
import box::rascal::Declarations;
import box::rascal::Constructors;
import box::rascal::Expressions;
import box::rascal::Statements;
import box::rascal::Types;
import box::rascal::Rascal;

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

list[int] isIndented(list[Symbol] q, list[Tree] z) {
        if (isScheme(q , ["N","T", "(", "N", ")", "N"])) return isBlock(z, 5);  // for
        if (isScheme(q , ["N","T", "(", "N", ")", "N", "N"])) return isBlock(z, 5); // if then
        if (isScheme(q , ["N", "N", "N", "N"])) return isBody(z,3); // Visibility Signature FunctionBody
        if (isScheme(q , ["N","T", "(", "N", ")", "N", "else", "N"])) return [5,7];
                      // isBlock(z,5)+isBlock(z,7); If then else
        if (isScheme(q , ["T", "N", "N"])) return isBlock(z, 1);  // try
        if (isScheme(q , ["T", "T", "N"])) return isBlock(z, 2);  // catch
        if (isScheme(q , ["T", "N", "T", "N"])) return  isBlock(z, 3);  // catch
        if (isScheme(q , ["N", ":", "N"])) return isBlock(z, 2);  // pattern with action
        if (isScheme(q , ["N", "T", "(", "N", ")","{", "N","}"])) return [5, 6, 7];  // switch visit
        if (isScheme(q , ["T", "(", "N", ")","{", "N","}"])) return [4, 5, 6];  // visit
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

bool isSeperated(list[Symbol] q) {
     if (isScheme(q , ["N","N"])) return true;  // type name
     if (isScheme(q , ["case","N"])) return true;  // case pattern-with-action
     if (isScheme(q , ["return","N"])) return true; 
     if (isScheme(q , ["throw","N"])) return true; 
     return false;
     }
     
public text toList(loc asf){
     Tree a = parse(#Module, asf);
     // rawPrintln(a);
     return returnText(a, extraRules, isIndented, isCompact, isSeperated);
     }

// Don't change this part 

public Box extraRules(Tree q) {  
   for (UserDefinedFilter userDefinedFilter<-userDefinedFilters) {
           Box b = userDefinedFilter(q);
           if (b!=NULL()) return b;
           }
    return NULL();
    }
    
  

/*
public void main(){
    Tree a = parse(#Module, |file:///ufs/bertl/asfix/A.rsc|);
    concrete(a);
    }
*/
