module experiments::Compiler::Tests::AllRascalTests

import IO;
import Type;
import List;
import experiments::Compiler::Execute;

loc base1 = |project:///rascal-test/tests/functionality|;

// Percentage of succeeded tests, see spreadsheet TestOverview.ods

list[str] functionalityTests = [

 // OK
"AliasTests",				// OK
"AnnotationTests",			// OK
"AssignmentTests",			// OK
"BackTrackingTests",		// OK
"ComprehensionTests",		// OK
							 //3 tests fail that correspond to empty enumerations: interpreter gives false, compiler gives true.
"DataTypeTests",			// OK
"ReducerTests",				// OK
"DataDeclarationTests",		// OK
"DeclarationTests",		// OK, these are conscious changes in the scoping rules
							 //error("Cannot re-declare name that is already declared in the current function or closure",|project://rascal-test/src/tests/functionality/DeclarationTests.rsc|(985,1,<31,18>,<31,19>))
							 //error("Cannot re-declare name that is already declared in the current function or closure",|project://rascal-test/src/tests/functionality/DeclarationTests.rsc|(1071,1,<35,14>,<35,15>))
							 //error("Cannot re-declare name that is already declared in the current function or closure",|project://rascal-test/src/tests/functionality/DeclarationTests.rsc|(1167,1,<39,24>,<39,25>))
"ProjectionTests", 		// OK
"RangeTests",				// OK, 4 tests fail but this is due to false 1. == 1.0 comparisons.
"RegExpTests",				// OK
 							 //Commented out 6: Treatment of redeclared local variables
"ScopeTests",				// OK
							 //Commented out several tests: no shadowing allowed
"SubscriptTests",			// OK
"TryCatchTests"			// OK

// Not yet OK

//"AccumulatingTests"		// [15] 2 tests fail: append that crosses function boundary: make tmp scope dependent?

//"CallTests"				// [58] keyword parameters Issue #456
                     
//"FunctionCompositionTests"	// Issue #468						
							
//"PatternTestsexe"			// [420] Issue #458
//"PatternTestsList3"
//"PatternTestsDescendant"
							
//"StatementTests"			// Fail in overloaded constructor gives problem ==> Issue posted
				
//"VisitTests"				// 13 fail [98]
];


list[str] rascalTests = [

"Booleans",					// OK
							// Commented out fromInt test

"Integers",					// OK
"Tuples",					// OK
"SolvedIssues",				// OK
"Lists",					// OK
"Maps",						// OK
"Nodes",					// OK
"Strings" , 				// OK
"StringTests"				// OK

// Not yet OK

//"Equality"				// OK
							// Added parentheses for ? operator

//"BacktrackingTests"		// [12]
							// error("Name s is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8573,1,<223,10>,<223,11>))
							//error("Name L is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8246,1,<218,13>,<218,14>))
							//error("Name s is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8315,1,<219,9>,<219,10>))
							//error("Name l8 is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(9056,2,<233,6>,<233,8>))
							//error("Name r is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8317,1,<219,11>,<219,12>))
							//error("Name l13 is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(9259,3,<238,6>,<238,9>))
							//error("Name r is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8575,1,<223,12>,<223,13>))
							//error("Name L is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8319,1,<219,13>,<219,14>))
							//error("Name l9 is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(9071,2,<234,6>,<234,8>))
							//error("Name L is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8577,1,<223,14>,<223,15>))
							//error("Cannot assign pattern of type list[str] to non-inferred variable of type list[int]",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8324,28,<219,18>,<219,46>))
							//error("Name s is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8242,1,<218,9>,<218,10>))
							//error("Cannot assign pattern of type list[value] to non-inferred variable of type list[str]",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8582,32,<223,19>,<223,51>))
							//error("Name r is not in scope",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8244,1,<218,11>,<218,12>))
							//error("Cannot assign pattern of type list[int] to non-inferred variable of type list[str]",|project://rascal-test/src/tests/BacktrackingTests.rsc|(8251,28,<218,18>,<218,46>))
							// Issue posted
						
//"Functions"			// [3]
						// Checking function callKwp
						// |rascal://lang::rascal::types::CheckTypes|(206380,13,<4071,16>,<4071,29>): The called signature: checkExp(sort("Expression"), Configuration),
						// does not match the declared signature:	CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  CheckResult checkExp(sort("Expression"), Configuration); (concrete pattern);  

//"IO"					// [6]
						// testreport_add: trouble generating random value for ADT Encoding since cannot find its definition, go back to reified type.

//"ListRelations"		// TC tests commented out
						// Issue #462




//"Matching"			// TC, #450
	
//"Relations"			// 1 test fails, nested any
									

//"Sets"					// 4 tests fails
						// Issue #459
						// Issue #460
];

list[str] libraryTests = [

// OK

"BooleanTests",			// OK
"GraphTests",			// OK
"IntegerTests",			// OK
"ListTests" ,			// OK
"MapTests",				// OK
"MathTests"	,			// OK
"NumberTests",			// OK
"RelationTests",		// OK
"SetTests",				// OK
"StringTests"			// OK
];

loc base = |rascal-test:///tests/library|;

str summary(lrel[loc,int,str] test_results) = "<size(test_results)> tests executed; < size(test_results[_,0])> failed; < size(test_results[_,2])> ignored";

lrel[loc,int,str] runTests(list[str] names, loc base){
 all_test_results = [];
 for(tst <- names){
      if(lrel[loc,int,str] test_results := execute(base + (tst + ".rsc"), [], recompile=true, testsuite=true, listing=false, debug=false)){
         println("TEST REPORT ***** <tst> ***** <base>");
         println(summary(test_results));
         all_test_results += test_results;
      } else {
         println("testsuite did not return a list of test results");
      }
  }
 
  return all_test_results;
}
  
value main(list[value] args){
  all_results = [];
  all_results += runTests(functionalityTests, |project://rascal-test/src/tests/functionality|);
  all_results += runTests(rascalTests, |project://rascal-test/src/tests|);
  all_results += runTests(libraryTests, |project://rascal-test/src/tests/library|);
  failed = all_results[_,0];
  if(size(failed) > 0){
	  println("FAILED TESTS:");
	  for(<l, 0, msg> <- all_results){
	      println("<l>: FALSE <msg>");
	  }
  }
  ignored = all_results[_,2];
  if(size(ignored) > 0){
	  println("IGNORED TESTS:");
	  for(<l, 2, msg> <- all_results){
	      println("<l>: IGNORED");
	  }
  }
  println("\nSUMMARY ALL TEST REPORTS: " + summary(all_results));
  return size(failed) == 0;
}