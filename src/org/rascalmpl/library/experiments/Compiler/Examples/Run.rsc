module experiments::Compiler::Examples::Run

import Prelude;
import experiments::Compiler::Compile;

import experiments::Compiler::Examples::Capture;
import experiments::Compiler::Examples::D1D2;
import experiments::Compiler::Examples::Fac;
import experiments::Compiler::Examples::Fib;
import experiments::Compiler::Examples::ListMatch;

loc base = |std:///experiments/Compiler/Examples/|;

value run(str example bool debug = false, bool listing=false){
  v = execute(base + (example + ".rsc"), debug=debug, listing=listing);
  return v;
}

test bool tst() = run("Capture") == experiments::Compiler::Examples::Capture::main([]);
test bool tst() = run("D1D2") == experiments::Compiler::Examples::D1D2::main([]);
test bool tst() = run("Fac") == experiments::Compiler::Examples::Fac::main([]);
test bool tst() = run("Fib") == experiments::Compiler::Examples::Fib::main([]);
test bool tst() = run("ListMatch") == experiments::Compiler::Examples::ListMatch::main([]);