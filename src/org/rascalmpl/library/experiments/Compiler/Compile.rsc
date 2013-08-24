module experiments::Compiler::Compile

import Prelude;
import experiments::Compiler::Rascal2muRascal::RascalModule;
import experiments::Compiler::muRascal::AST;
import experiments::Compiler::RVM::Run;
import experiments::Compiler::RVM::AST;
import experiments::Compiler::muRascal2RVM::mu2rvm;



RVMProgram compile(loc rascalSource, bool listing=false){
   muCode  = r2mu(rascalSource);
   rvmCode = mu2rvm(muCode, listing=listing);
   return rvmCode;
}

RVMProgram compile(str rascalSource, bool listing=false){
   muCode  = r2mu(rascalSource);
   rvmCode = mu2rvm(muCode, listing=listing);
   return rvmCode;
}

value execute(RVMProgram rvmCode, bool debug=false, bool listing=false){
   <v, t> = executeProgram(rvmCode, debug, 1);
   println("Result = <v>, [<t> msec]");
   return v;
}

value execute(loc rascalSource, bool debug=false, bool listing=false){
   rvmCode = compile(rascalSource, listing=listing);
   return execute(rvmCode, debug=debug);
}

value execute(str rascalSource, bool debug=false, bool listing=false){
   rvmCode = compile(rascalSource, listing=listing);
   return execute(rvmCode, debug=debug);
}