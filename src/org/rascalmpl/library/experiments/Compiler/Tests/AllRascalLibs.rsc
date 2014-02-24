module experiments::Compiler::Tests::AllRascalLibs

import Prelude;
import experiments::Compiler::Compile;

/*
 * Results of compiling Rascal library modules.
 */

list[str] libs = [

/*
"Boolean", 			// OK
"DateTime",			// OK
"Exception", 		// OK
"Grammar", 			// OK
"IO",				// OK
"List", 			// OK
"ListRelation",		// OK
"Map", 				// OK
"Message", 			// OK
"Node",				// OK
"Origins",			// OK
"ParseTree", 		// OK		
"Prelude",			// OK	
"Relation",			// OK
"Set",				// OK
"String",			// OK
"Time", 			// OK
"Type", 			// OK
"ToString", 		// OK
"Traversal",		// OK
"Tuple", 			// OK
"ValueIO", 			// OK
"util::Benchmark",	// OK
"util::Eval",		// OK
"util::FileSystem", // OK
"util::Highlight",	// OK
"util::Math",		// OK
"util::Maybe",		// OK
"util::Monitor",	// OK
"util::PriorityQueue",// OK
"util::Reflective", 	// OK
"util::ShellExec",	// OK
"util::Webserver",		// OK

// Not yet OK

"Ambiguity",			// #483
						//|rascal://lang::rascal::types::CheckTypes|(31671,1,<634,13>,<634,14>): Expected map[RName, int], but got map[RName, value]     	


"APIGen", 			 	// #482
						//error("Type of pattern could not be computed, please add additional type annotations",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3641,16,<89,62>,<89,78>))
						//error("Type of pattern could not be computed, please add additional type annotations",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3281,33,<80,24>,<80,57>))
						//error("Name cs is not in scope",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(7837,2,<207,39>,<207,41>))
						//error("Expected type bool, found fail",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(7726,25,<206,46>,<206,71>))
						//error("Multiple constructors and/or productions match this pattern, add additional type annotations",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(2134,15,<61,11>,<61,26>))
						//error("Type of pattern could not be computed",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(2134,15,<61,11>,<61,26>))
						//error("Field definitions does not exist on type type",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(7738,13,<206,58>,<206,71>))
						//error("Name t2 is not in scope",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3093,2,<73,87>,<73,89>))
						//error("Function of type fun str(str) cannot be called with argument types (inferred(11))",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(2159,18,<61,36>,<61,54>))
						//error("Multiple constructors and/or productions match this pattern, add additional type annotations",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(6028,12,<166,11>,<166,23>))
						//error("Function of type fun str(Symbol) cannot be called with argument types (inferred(41))",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3603,21,<89,24>,<89,45>))
						//error("Function of type fun str(Symbol) cannot be called with argument types (value)",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3468,22,<85,26>,<85,48>))
						//error("Field definitions does not exist on type type",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(7769,13,<206,89>,<206,102>))
						//error("Expected type str, found fail",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(7795,48,<206,115>,<207,45>))
						//error("Type of pattern could not be computed",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(6028,12,<166,11>,<166,23>))
						//error("Expected type bool, found fail",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(7753,39,<206,73>,<206,112>))
						//error("Invalid return type str, expected return type void",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3323,61,<81,5>,<81,66>))
						//error("Could not calculate function type because of errors calculating the parameter types",|project://rascal/src/org/rascalmpl/library/APIGen.rsc|(3259,56,<80,2>,<80,58>))

// "Number"				// DEPRECATED: TC gives errors


"util::LOC"			// #394
						// error("Field top does not exist on type Tree",|std:///util/LOC.rsc|(943,5,<44,8>,<44,13>))
						*/
/*
"analysis::formalconcepts::FCA",
"analysis::graphs::Graph",				
"analysis::graphs::LabeledGraph",
"analysis::linearprogramming::LinearProgramming",
"analysis::m3::AST",
"analysis::m3::Core",
"analysis::m3::Registry",
"analysis::m3::TypeSymbol",
"analysis::statistics::Correlation",
"analysis::statistics::Descriptive",
"analysis::statistics::Frequency",
"analysis::statistics::Inference",
"analysis::statistics::SimpleRegression"
"demo::basic::Ackermann",	//OK
"demo::basic::Bottles",		//OK
"demo::basic::Bubble",		// OK
"demo::basic::BubbleTest",	// OK
"demo::basic::Factorial",	// OK
"demo::basic::FactorialTest",	// OK

"demo::basic::FizzBuzz",
"demo::basic::Hello",
"demo::basic::Quine",
"demo::basic::Squares",
"demo::common::WordCount::CountInLine1",
"demo::common::WordCount::CountInLine2",
"demo::common::WordCount::CountInLine3",
"demo::common::WordCount::WordCount",
"demo::common::Calls",
"demo::common::ColoredTrees",
"demo::common::ColoredTreesTest",	// OK
"demo::common::CountConstructors",
"demo::common::Crawl",			// OK
"demo::common::Cycles",	//OK
"demo::common::Derivative",	//OK
"demo::common::Lift",	//OK
"demo::common::LiftTest",	//OK
"demo::common::StringTemplate",	//OK
"demo::common::StringTemplateTest",	//OK
"demo::common::Trans",	//OK
"demo::common::WordReplacement",	// OK
"demo::common::WordReplacementTest"	//OK
"demo::lang::Exp::Abstract::Eval",	//OK
"demo::lang::Exp::Combined::Automatic::Eval",	// ERROR
"demo::lang::Exp::Combined::Manual::Eval"	// ERROR
"demo::lang::Exp::Concrete::NoLayout::Eval",	//OK
"demo::lang::Exp::Concrete::WithLayout::Eval"	// OK
"demo::lang::Func::AST",
"demo::lang::Func::Eval0",
"demo::lang::Func::Eval1",
"demo::lang::Func::Eval2",
"demo::lang::Func::Eval3",
"demo::lang::Func::Func",
"demo::lang::Func::Parse",
"demo::lang::Func::Test",

"demo::lang::Lisra::Eval",
"demo::lang::Lisra::Parse",
"demo::lang::Lisra::Pretty",
"demo::lang::Lisra::Runtime",
"demo::lang::Lisra::Syntax",
"demo::lang::Lisra::Test",

"demo::lang::MissGrant::AST",
"demo::lang::MissGrant::CheckController",
"demo::lang::MissGrant::DesugarResetEvents",
"demo::lang::MissGrant::Implode",
"demo::lang::MissGrant::MissGrant",
"demo::lang::MissGrant::Outline",
"demo::lang::MissGrant::ParallelMerge",
"demo::lang::MissGrant::Parse",
"demo::lang::MissGrant::Step",
"demo::lang::MissGrant::ToDot",
"demo::lang::MissGrant::ToMethods",
"demo::lang::MissGrant::ToObjects",
"demo::lang::MissGrant::ToRelation",
"demo::lang::MissGrant::ToSwitch"




"demo::lang::Pico::Compile",
"demo::lang::Pico::ControlFlow",
"demo::lang::Pico::Eval",
"demo::lang::Pico::Typecheck",
"demo::lang::Pico::Uninit",
"demo::lang::Pico::UseDef",
"demo::lang::Pico::Visualize"


"demo::lang::turing::l1::ast::Load",
"demo::lang::turing::l1::ast::Turing",
"demo::lang::turing::l1::cst::Parse",
"demo::lang::turing::l1::cst::Syntax",
"demo::lang::turing::l1::interpreter::Interpreter",
"demo::lang::turing::l2::ast::Load",
"demo::lang::turing::l2::ast::Turing",
"demo::lang::turing::l2::check::Check",
"demo::lang::turing::l2::cst::Parse",
"demo::lang::turing::l2::cst::Syntax",
"demo::lang::turing::l2::desugar::Desugar",
"demo::lang::turing::l2::format::Format",

"demo::vis::Higher",
"demo::vis::Logo",
"demo::vis::VisADT"
*/
"experiments::Compiler::RVM::AST",
"experiments::Compiler::muRascal::AST",

"experiments::Compiler::muRascal2RVM::PeepHole",
"experiments::Compiler::muRascal2RVM::RascalReifiedTypes",
"experiments::Compiler::muRascal2RVM::ReifiedTypes",
"experiments::Compiler::muRascal2RVM::StackSize",
"experiments::Compiler::muRascal2RVM::ToplevelType",
"experiments::Compiler::muRascal2RVM::mu2rvm"




];

value main(list[value] args){
  crashes = [];
  for(lib <- libs){
    println("**** Compiling <lib> ****");
    try {
	    //compile("module TMP  extend Exception; extend <lib>;", recompile=true);
	    
    	compile(|project://rascal/src/org/rascalmpl/library/| + (replaceAll(lib, "::", "/") + ".rsc"), recompile=true);
    } catch e: {
      crashes += <lib, "<e>">;
    }
  }
  if(size(crashes) > 0){
    println("\nERRORS:\n");
     for(<lib, msg> <- crashes){
       println("<lib>: <msg>");
    }
  }
  return true;
}