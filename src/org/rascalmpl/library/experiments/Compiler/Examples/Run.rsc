module experiments::Compiler::Examples::Run

import Prelude;
import experiments::Compiler::Compile;

import experiments::Compiler::Examples::Capture;
import experiments::Compiler::Examples::D1D2;
import experiments::Compiler::Examples::Fac;
import experiments::Compiler::Examples::Fib;
import experiments::Compiler::Examples::Fail;
import experiments::Compiler::Examples::ListMatch;
import experiments::Compiler::Examples::Odd;
import experiments::Compiler::Examples::SendMoreMoney;
//import experiments::Compiler::Examples::Tmp;
import experiments::Compiler::Examples::TestSuite;
import experiments::Compiler::Examples::Template;

loc base = |std:///experiments/Compiler/Examples/|;

value demo(str example bool debug = false, bool listing=false, bool testsuite=false) =
  execute(base + (example + ".rsc"), debug=debug, listing=listing, testsuite=testsuite);

test bool tst() = demo("Capture") == experiments::Compiler::Examples::Capture::main([]);
test bool tst() = demo("D1D2") == experiments::Compiler::Examples::D1D2::main([]);
test bool tst() = demo("Fac") == experiments::Compiler::Examples::Fac::main([]);
test bool tst() = demo("Fib") == experiments::Compiler::Examples::Fib::main([]);
test bool tst() = demo("ListMatch") == experiments::Compiler::Examples::ListMatch::main([]);
test bool tst() = demo("Odd") == experiments::Compiler::Examples::Odd::main([]);
test bool tst() = demo("SendMoreMoney") == experiments::Compiler::Examples::SendMoreMoney::main([]);
test bool tst() = demo("Template") == experiments::Compiler::Examples::Template::main([]);