module experiments::Compiler::Tests::CompileRascalLibs

import Prelude;
import experiments::Compiler::Compile;

void run(str lib) = compile("module TST import <lib>;");

list[str] libs = [

//"Ambiguity",		// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(13601,5,<349,56>,<349,61>): NoSuchField("parameters")
//"APIGen", 		// reifiedTypeNodes |rascal://lang::rascal::types::CheckTypes|(178871,21,<3518,22>,<3518,43>): "Not yet implemented"
//"Boolean", 		// OK
//"DateTime"		// imported List fails to compile
//"Exception", 		// OK
//"Grammar", 		// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(13601,5,<349,56>,<349,61>): NoSuchField("parameters")
//"IO", 			// OK
//"List", 			// |rascal://experiments::Compiler::Rascal2muRascal::RascalType|(2860,5,<55,29>,<55,34>): Undeclared variable: type
/* Caused by:
public map[&K, &V] mapper(map[&K, &V] M, &L (&K) F, &W (&V) G)
{
  return (F(key) : G(M[key]) | &K key <- M);
}
*/


//"ListRelation",	// |rascal://lang::rascal::types::CheckTypes|(92402,19,<1852,49>,<1852,68>): The called signature: prettyPrintName(lex("Name")),
					// does not match the declared signature:	str prettyPrintName(RName); (abstract pattern);  str prettyPrintName(RName); (abstract pattern);  
//"Map", 			// |rascal://experiments::Compiler::Rascal2muRascal::RascalType|(2860,5,<55,29>,<55,34>): Undeclared variable: type
//"Message", 		// OK
//"Node",			// |rascal://experiments::Compiler::Rascal2muRascal::RascalStatement|(1288,7,<27,71>,<27,78>): "visit"
//"Number", 		// TC gives errors
//"Origins",		// OK
//"ParseTree", 		// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(13601,5,<349,56>,<349,61>): NoSuchField("parameters")
//"Prelude",
//"Relation", 		// |rascal://lang::rascal::types::CheckTypes|(92402,19,<1852,49>,<1852,68>): The called signature: prettyPrintName(lex("Name")),
               		// does not match the declared signature:	str prettyPrintName(RName); (abstract pattern);  str prettyPrintName(RName); (abstract pattern);  
//"Set",			// |rascal://experiments::Compiler::Rascal2muRascal::RascalType|(2860,5,<55,29>,<55,34>): Undeclared variable: type
//"String",			// |rascal://experiments::Compiler::Rascal2muRascal::RascalExpression|(2763,42,<72,55>,<72,97>): "RexExpLiteral cannot occur in expression"
// "Time", 			// OK
//"ToString", 		// OK
//"Traversal",		// OK
//"Tuple", 			// OK
//"Type", 			// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(4067,1,<134,20>,<134,21>): NoSuchKey(|rascal:///Type.rsc|(9039,7,<228,45>,<228,52>))
/* Issue: || used in comparable.
*/
//"ValueIO", 		// OK

//"util::Benchmark",	// TC gives errors
//"util::Eval",
//"util::FileSystem",	// TC gives errors
//"util::Highlight",	// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(13601,5,<349,56>,<349,61>): NoSuchField("parameters")
//"util::LOC", 			// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(13601,5,<349,56>,<349,61>): NoSuchField("parameters")
//"util::Math", 		// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(4067,1,<134,20>,<134,21>): NoSuchKey(|std:///util/Math.rsc|(3114,1,<153,5>,<153,6>))
/* Caused by: || operator in ceil */

//"util::Maybe",		// OK
//"util::Monitor",		// OK
//"util::PriorityQueue",// TC gives errors
//"util::Reflective", 	// |rascal://experiments::Compiler::Rascal2muRascal::TypeUtils|(13601,5,<349,56>,<349,61>): NoSuchField("parameters")
//"util::ShellExec",	// OK
//"util::Webserver",	// TC gives errors
];

value main(list[value] args){
  for(lib <- libs){
    println("**** Compiling <lib> ****");
    compile("module TST import <lib>;");
  }
  return true;
}