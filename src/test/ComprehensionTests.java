package test;

import static org.junit.Assert.*;
import org.junit.Test;

public class ComprehensionTests extends TestFramework {
	
	@Test public void testSetComprehension1() {
		
		assertTrue(runTest("{ X | int X : {} } == {};"));
		assertTrue(runTest("{ X | int X : [] } == {};"));
		
		assertTrue(runTest("{ X | int X : {1}} == {1};"));
		assertTrue(runTest("{ X | int X : [1]} == {1};"));
		
		assertTrue(runTest("{ X | int X : {1, 2}} == {1,2};"));
		assertTrue(runTest("{ X | int X : [1, 2]} == {1,2};"));
		
		assertTrue(runTest("{ X | int X : {1, 1, 1}} == {1};"));
		assertTrue(runTest("{ X | int X : [1, 1, 1]} == {1};"));
		
		assertTrue(runTest("{ 1 | int X : {1,2,3}} == {1};"));
		assertTrue(runTest("{ 1 | int X : [1,2,3]} == {1};"));
		
		assertTrue(runTest("{ 1 | int X : {1,2,3}, true } == {1};"));
		assertTrue(runTest("{ 1 | int X : [1,2,3], true } == {1};"));
		
		assertTrue(runTest("{ 1 | int X : {1,2,3}, false} 	== {};"));
		assertTrue(runTest("{ 1 | int X : [1,2,3], false} 	== {};"));
		
		assertTrue(runTest("{ X | int X : {1,2,3}} == {1,2,3};"));
		assertTrue(runTest("{ X | int X : [1,2,3]} == {1,2,3};"));
		
		assertTrue(runTest("{  X | int X : {1,2,3}, true} == {1,2,3};"));
		assertTrue(runTest("{  X | int X : [1,2,3], true} == {1,2,3};"));
		
		assertTrue(runTest("{  X | int X : {1,2,3}, false} 	== {};"));
		assertTrue(runTest("{  X | int X : [1,2,3], false} 	== {};"));
		
		assertTrue(runTest("{  X | int X : {1,2,3}, X >= 2, X < 3} == {2};"));
		assertTrue(runTest("{  X | int X : [1,2,3], X >= 2, X < 3} == {2};"));
	}
	
	@Test public void testSetComprehension2() {	
		
		assertTrue(runTest("{  {} | int X : {1,2,3}} == {{}};"));
		assertTrue(runTest("{  {} | int X : [1,2,3]} == {{}};"));
		
		assertTrue(runTest("{  {} | int X : {1,2,3}, true} == {{}};"));
		assertTrue(runTest("{  {} | int X : [1,2,3], true} == {{}};"));
		
		assertTrue(runTest("{  {} | int X : {1,2,3}, false} == {};"));
		assertTrue(runTest("{  {} | int X : [1,2,3], false} == {};"));
		
		assertTrue(runTest("{ <1,2,3> | int X : {1,2,3}} 	== {<1,2,3>};"));
		assertTrue(runTest("{ <1,2,3> | int X : [1,2,3]} 	== {<1,2,3>};"));
		
		assertTrue(runTest("{ <1,2,3> | int X : {1,2,3}, true} 	== {<1,2,3>};"));
		assertTrue(runTest("{ <1,2,3> | int X : [1,2,3], true} 	== {<1,2,3>};"));
		
		assertTrue(runTest("{ <1,2,3> | int X : {1,2,3}, true, true} == {<1,2,3>};"));
		assertTrue(runTest("{ <1,2,3> | int X : [1,2,3], true, true} == {<1,2,3>};"));
		
		assertTrue(runTest("{ <1,2,3> | int X : {1,2,3}, false}	== {} ;"));
		assertTrue(runTest("{ <1,2,3> | int X : [1,2,3], false}	== {} ;"));
		
		assertTrue(runTest("{ Y | list[int] Y : [[1,2,3],[10,20,30],[100,200,300]] } == { [1,2,3],[10,20,30],[100,200,300]};"));
		assertTrue(runTest("{1 | 3 > 2} == {1} ;"));
		assertTrue(runTest("{1 | 2 > 3} == {} ;"));
	}
	
	@Test public void testAny()  {
		
		assertTrue(runTest("any(int X : {1,2,3}, X > 2);"));
		assertTrue(runTest("any(int X : {1,2,3}, X > 2, X <10);"));
		assertTrue(runTest("any(int X : {1,2,3}, X > 2 && X <10);"));
		
		assertTrue(runTest("any(int X : [1,2,3], X > 2);"));
		assertTrue(runTest("any(int X : [1,2,3], X > 2, X < 10);"));
		assertTrue(runTest("any(int X : [1,2,3], X > 2 && X < 10);"));
		
		assertFalse(runTest("any(int X : {1,2,3}, X > 10);"));
		assertFalse(runTest("any(int X : [1,2,3], X > 10);"));
		
		assertTrue(runTest("any(<int X, int Y> : {<1,10>,<30,3>,<2,20>}, X > Y);"));
		assertTrue(runTest("any(<int X, int Y> : [<1,10>,<30,3>,<2,20>], X > Y);"));
		
		assertFalse(runTest("any(<int X, int Y> : {<1,10>,<30,3>,<2,20>}, X > 100*Y);"));
		assertFalse(runTest("any(<int X, int Y> : [<1,10>,<30,3>,<2,20>], X > 100*Y);"));
	}
	
	@Test public void testAll() {
		
		assertTrue(runTest("all(int X : {1,2,3}, X >= 1);"));
		assertTrue(runTest("all(int X : {1,2,3}, X >= 1, X < 10);"));
		assertTrue(runTest("all(int X : {1,2,3}, X >= 1 && X < 10);"));
		assertTrue(runTest("all(int X : [1,2,3], X >= 1);"));
		assertTrue(runTest("all(int X : {1,2,3}, X >= 1, X < 10);"));
		assertTrue(runTest("all(int X : {1,2,3}, X >= 1 && X < 10);"));
		
		assertFalse(runTest("all(int X : {1,2,3}, X >= 2);"));
		assertFalse(runTest("all(int X : {1,2,3}, X >= 2, X <=2);"));
		assertFalse(runTest("all(int X : {1,2,3}, X >= 2 && X <=2);"));
		assertFalse(runTest("all(int X : [1,2,3], X >= 2);"));
		assertFalse(runTest("all(int X : [1,2,3], X >= 2, X <= 2);"));
		assertFalse(runTest("all(int X : [1,2,3], X >= 2 && X <= 2);"));
		
		assertTrue(runTest("all(<int X, int Y> : {<1,10>,<3,30>,<2,20>}, X < Y);"));
		assertTrue(runTest("all(<int X, int Y> : [<1,10>,<3,30>,<2,20>], X < Y);"));
		
		assertFalse(runTest("all(<int X, int Y> : {<1,10>,<30,3>,<2,20>}, X < Y);"));
		assertFalse(runTest("all(<int X, int Y> : [<1,10>,<30,3>,<2,20>], X < Y);"));
	}
	
	@Test public void testSetComprehension3() {	
		
		assertTrue(runTest("{X + 1 | int X : {1,2,3}} == {2,3,4};"));
		assertTrue(runTest("{X + 1 | int X : [1,2,3]} == {2,3,4};"));
		
		assertTrue(runTest("{X | int X : {1,2,3}, X + 1 < 3} == {1};"));
		assertTrue(runTest("{X | int X : [1,2,3], X + 1 < 3} == {1};"));
		
		assertTrue(runTest("{X - 1 | int X : {1,2,3}} == {0,1,2};"));
		assertTrue(runTest("{X - 1 | int X : [1,2,3]} == {0,1,2};"));
		
		assertTrue(runTest("{X | int X : {1,2,3}, X - 1 < 3} == {1,2,3};"));
		assertTrue(runTest("{X | int X : [1,2,3], X - 1 < 3} == {1,2,3};"));
		
		assertTrue(runTest("{X * 2 | int X : {1,2,3}} == {2,4,6};"));
		assertTrue(runTest("{X * 2 | int X : [1,2,3]} == {2,4,6};"));
	}
	
	@Test public void testListComprehension1()  {
		
		assertTrue(runTest("[ X | int X : {} ] == [];"));
		assertTrue(runTest("[ X | int X : [] ] == [];"));
		assertTrue(runTest("[ X |     X : [] ] == [];"));
		
		assertTrue(runTest("[ X | int X : {1}] == [1];"));
		assertTrue(runTest("[ X | int X : [1]] == [1];"));
		assertTrue(runTest("[ X |     X : [1]] == [1];"));
		
		assertTrue(runTest("{L = [ X | int X : {1, 2}]; (L == [1,2]) || (L == [2, 1]);}"));
		assertTrue(runTest("[ X | int X : [1, 2]] == [1,2];"));
		
		assertTrue(runTest("[ X | int X : {1, 1, 1}] == [1];"));
		assertTrue(runTest("[ X | int X : [1, 1, 1]] == [1, 1, 1];"));
		
		assertTrue(runTest("[ 1 | int X : {1,2,3}] == [1, 1, 1];"));
		assertTrue(runTest("[ 1 | int X : [1,2,3]] == [1, 1, 1];"));
		
		assertTrue(runTest("[ 1 | int X : {1,2,3}, true ] == [1, 1, 1];"));
		assertTrue(runTest("[ 1 | int X : [1,2,3], true ] == [1, 1, 1];"));
		
		assertTrue(runTest("[ 1 | int X : {1,2,3}, false] 	== [];"));
		assertTrue(runTest("[ 1 | int X : [1,2,3], false] 	== [];"));
		
		assertTrue(runTest("{L = [ X | int X : {1,2}]; (L == [1,2]) || (L == [2, 1]);}"));
		assertTrue(runTest("[ X | int X : [1,2,3]] == [1,2,3];"));
		
		assertTrue(runTest("{L = [  X | int X : {1,2}, true]; (L == [1,2]) || (L == [2, 1]);}"));
		assertTrue(runTest("[  X | int X : [1,2,3], true] == [1,2,3];"));
		
		assertTrue(runTest("[  X | int X : {1,2,3}, false] == [];"));
		assertTrue(runTest("[  X | int X : [1,2,3], false] == [];"));
		
		assertTrue(runTest("[  X | int X : {1,2,3}, X >= 2, X < 3] == [2];"));
		assertTrue(runTest("[  X | int X : [1,2,3], X >= 2, X < 3] == [2];"));
	}
	
	@Test public void testListComprehension2() {
		
		assertTrue(runTest("[  [] | int X : {1,2,3}] == [[], [], []];"));
		assertTrue(runTest("[  [] | int X : [1,2,3]] == [[], [], []];"));
		
		assertTrue(runTest("[  [] | int X : {1,2,3}, true] == [[], [], []];"));
		assertTrue(runTest("[  [] | int X : [1,2,3], true] == [[], [], []];"));
		
		assertTrue(runTest("[  [] | int X : {1,2,3}, false] == [];"));
		assertTrue(runTest("[  [] | int X : [1,2,3], false] == [];"));
		
		assertTrue(runTest("[ <1,2,3> | int X : {1,2,3}] == [<1,2,3>, <1,2,3>, <1,2,3>];"));
		assertTrue(runTest("[ <1,2,3> | int X : [1,2,3]] == [<1,2,3>, <1,2,3>, <1,2,3>];"));
		
		assertTrue(runTest("[ <1,2,3> | int X : {1,2,3}, true] == [<1,2,3>, <1,2,3>, <1,2,3>];"));
		assertTrue(runTest("[ <1,2,3> | int X : [1,2,3], true] == [<1,2,3>, <1,2,3>, <1,2,3>];"));
		
		assertTrue(runTest("[ <1,2,3> | int X : {1,2,3}, true, true] == [<1,2,3>, <1,2,3>, <1,2,3>];"));
		assertTrue(runTest("[ <1,2,3> | int X : [1,2,3], true, true] == [<1,2,3>, <1,2,3>, <1,2,3>];"));
		
		assertTrue(runTest("[ <1,2,3> | int X : {1,2,3}, false]	== [] ;"));
		assertTrue(runTest("[ <1,2,3> | int X : [1,2,3], false]	== [] ;"));
	}
	
	@Test public void testListComprehension3()  {
		
		assertTrue(runTest("[ Y | list[int] Y : [[1,2,3],[10,20,30],[100,200,300]] ] == [ [1,2,3], [10,20,30],[100,200,300]];"));
		assertTrue(runTest("[1 | 3 > 2] == [1] ;"));
		assertTrue(runTest("[1 | 2 > 3] == [] ;"));
		
		assertTrue(runTest("{L = [X + 1 | int X : {1,2}]; (L == [2,3]) || (L == [3,2]);}"));
		assertTrue(runTest("[X + 1 | int X : [1,2,3]] == [2,3,4];"));
		
		assertTrue(runTest("[X | int X : {1,2,3}, X + 1 < 3] == [1];"));
		assertTrue(runTest("[X | int X : [1,2,3], X + 1 < 3] == [1];"));
		
		assertTrue(runTest("{L = [X - 1 | int X : {1,2}]; (L == [0,1]) || (L == [1,0]);}"));
		assertTrue(runTest("[X - 1 | int X : [1,2,3]] == [0,1,2];"));
		
		assertTrue(runTest("{L = [X | int X : {2,3}, X - 1 < 3]; (L == [2,3]) || (L == [3,2]);}"));
		assertTrue(runTest("[X | int X : [1,2,3], X - 1 < 3] == [1,2,3];"));
		
		assertTrue(runTest("{ L = [X * 2 | int X : {2,3}]; (L == [4,6]) || (L == [6,4]);}"));
		assertTrue(runTest("[X * 2 | int X : [1,2,3]] == [2,4,6];"));

	}
	
	@Test public void testRelationComprehension() {
		
		assertTrue(runTest("{<X,Y> | <int X, int Y> : {}} == {} ;"));
		assertTrue(runTest("{<X,Y> | <int X, int Y> : []} == {} ;"));
		
		assertTrue(runTest("{<X,Y> | int X : {}, int Y : {}} == {};"));
		assertTrue(runTest("{<X,Y> | int X : [], int Y : []} == {};"));
		
		assertTrue(runTest("{<X,Y> | int X : {1,1,1}, int Y : {2,2,2}} == {<1,2>};"));
		assertTrue(runTest("{<X,Y> | int X : [1,1,1], int Y : [2,2,2]} == {<1,2>};"));
		
		assertTrue(runTest("{<1,2> | int X : {1,2,3}} == {<1,2>};"));
		assertTrue(runTest("{<1,2> | int X : [1,2,3]} == {<1,2>};"));
		
		assertTrue(runTest("{<X,Y> | int X : {1,2,3}, int Y : {2,3,4}} ==  {<1, 2>, <1, 3>, <1, 4>, <2, 2>, <2, 3>, <2, 4>, <3, 2>, <3, 3>, <3, 4>};"));
		assertTrue(runTest("{<X,Y> | int X : [1,2,3], int Y : [2,3,4]} ==  {<1, 2>, <1, 3>, <1, 4>, <2, 2>, <2, 3>, <2, 4>, <3, 2>, <3, 3>, <3, 4>};"));
		
		assertTrue(runTest("{<X,Y> | int X : {1,2,3}, int Y : {2,3,4}, true} ==	{<1, 2>, <1, 3>, <1, 4>, <2, 2>, <2, 3>, <2, 4>, <3, 2>, <3, 3>, <3, 4>};"));
		assertTrue(runTest("{<X,Y> | int X : [1,2,3], int Y : [2,3,4], true} ==	{<1, 2>, <1, 3>, <1, 4>, <2, 2>, <2, 3>, <2, 4>, <3, 2>, <3, 3>, <3, 4>};"));
		
		
		assertTrue(runTest("{<X,Y> | int X : {1,2,3}, int Y : {2,3,4}, false} == {};"));
		assertTrue(runTest("{<X,Y> | int X : [1,2,3], int Y : [2,3,4], false} == {};"));
		
		assertTrue(runTest("{<X,Y> | int X : {1,2,3}, int Y : {2,3,4}, X >= Y} =={<2, 2>, <3, 2>, <3, 3>};"));
		assertTrue(runTest("{<X,Y> | int X : [1,2,3], int Y : [2,3,4], X >= Y} =={<2, 2>, <3, 2>, <3, 3>};"));
	/****/	
		assertTrue(runTest("{<X,Y> | int X : {1,2,3}, <X, int Y> : {<1,10>, <7,70>, <3,30>,<5,50>}} == {<1, 10>, <3, 30>};"));
		assertTrue(runTest("{<X,Y> | int X : [1,2,3], <X, int Y> : [<1,10>, <7,70>, <3,30>,<5,50>]} == {<1, 10>, <3, 30>};"));
		
		assertTrue(runTest("{<X,Y> | int X : {1,2,3}, <X, str Y> : {<1,\"a\">, <7,\"b\">, <3,\"c\">,<5,\"d\">}} == {<1, \"a\">, <3, \"c\">};"));
		assertTrue(runTest("{<X,Y> | int X : [1,2,3], <X, str Y> : [<1,\"a\">, <7,\"b\">, <3,\"c\">,<5,\"d\">]} == {<1, \"a\">, <3, \"c\">};"));
		
		}
	
	@Test public void testMapComprehension()  {
		
		assertTrue(runTest("( X : 2 * X | int X : {} ) == ();"));
		assertTrue(runTest("( X : 2 * X | int X : [] ) == ();"));
		
		assertTrue(runTest("( X : 2 * X | int X : {1}) == (1:2);"));
		assertTrue(runTest("( X : 2 * X | int X : [1]) == (1:2);"));
		
		assertTrue(runTest("( X : 2 * X | int X : {1, 2}) == (1:2,2:4);"));
		assertTrue(runTest("( X : 2 * X | int X : [1, 2]) == (1:2,2:4);"));
		
		assertTrue(runTest("( X: 2 * X| int X: [1,2,3] ) == (1:2,2:4,3:6);"));
	}
	
	@Test public void testNodeGenerator()  {
		prepare("data TREE = i(int N) | f(TREE a,TREE b) | g(TREE a, TREE b);");
		
		assertTrue(runTestInSameEvaluator("[ X | int X : f(i(1),g(i(2),i(3))) ] == [1,2,3];"));
		assertTrue(runTestInSameEvaluator("[ X | value X : f(i(1),g(i(2),i(3))) ] == [1,i(1),2,i(2),3,i(3),g(i(2),i(3)),f(i(1),g(i(2),i(3)))];"));
		
		assertTrue(runTestInSameEvaluator("[N | value N : f(i(1),i(2))] == [1,i(1),2,i(2),f(i(1),i(2))];"));
		assertTrue(runTestInSameEvaluator("[N | bottom-up value N : f(i(1),i(2))] == [1,i(1),2,i(2),f(i(1),i(2))];"));
		
		assertTrue(runTestInSameEvaluator("[N | top-down value N : f(i(1),i(2))] == [f(i(1),i(2)),i(1),1,i(2),2];"));
		
		assertTrue(runTestInSameEvaluator("[N | TREE N : f(i(1),i(2))] == [i(1),i(2),f(i(1),i(2))];"));
		assertTrue(runTestInSameEvaluator("[N | bottom-up TREE N : f(i(1),i(2))] == [i(1),i(2),f(i(1),i(2))];"));
		assertTrue(runTestInSameEvaluator("[N | top-down TREE N : f(i(1),i(2))] == [f(i(1),i(2)),i(1),i(2)];"));
		
		assertTrue(runTestInSameEvaluator("[N | int N : f(i(1),i(2))] == [1,2];"));
		
		assertTrue(runTestInSameEvaluator("[N | value N : f(i(1),g(i(2),i(3)))] == [1,i(1),2,i(2),3,i(3),g(i(2),i(3)),f(i(1),g(i(2),i(3)))];"));
		assertTrue(runTestInSameEvaluator("[N | bottom-up value N : f(i(1),g(i(2),i(3)))] == [1,i(1),2,i(2),3,i(3),g(i(2),i(3)),f(i(1),g(i(2),i(3)))];"));
		
		assertTrue(runTestInSameEvaluator("[N | top-down value N : f(i(1),g(i(2),i(3)))] == [f(i(1),g(i(2),i(3))),i(1),1,g(i(2),i(3)),i(2),2,i(3),3];"));
		
		assertTrue(runTestInSameEvaluator("[N | TREE N : f(i(1),g(i(2),i(3)))] == [i(1),i(2),i(3),g(i(2),i(3)),f(i(1),g(i(2),i(3)))];"));
		assertTrue(runTestInSameEvaluator("[N | bottom-up TREE N : f(i(1),g(i(2),i(3)))] == [i(1),i(2),i(3),g(i(2),i(3)),f(i(1),g(i(2),i(3)))];"));
		
		assertTrue(runTestInSameEvaluator("[N | top-down TREE N : f(i(1),g(i(2),i(3)))] == [f(i(1),g(i(2),i(3))), i(1),g(i(2),i(3)),i(2),i(3)];"));		
		
		assertTrue(runTestInSameEvaluator("[N | int N : f(i(1),g(i(2),i(3)))] == [1,2,3];"));
		assertTrue(runTestInSameEvaluator("[N | bottom-up int N : f(i(1),g(i(2),i(3)))] == [1,2,3];"));
		
		assertTrue(runTestInSameEvaluator("[N | top-down int N : f(i(1),g(i(2),i(3)))] == [1,2,3];"));
	}
	
	@Test public void xxtestRegularGenerators() {
		
		assertTrue(runTest("[S | /@<S:[a-z]+>@/ : [\"@abc@\", \"@def@\"]] == [\"abc\",\"def\"];"));
		assertTrue(runTest("{S | /@<S:[a-z]+>@/ : [\"@abc@\", \"@def@\"]} == {\"abc\", \"def\"};"));
		assertTrue(runTest("{S | /@<S:[a-z]+>@/ : {\"@abc@\", \"@def@\"}} == {\"abc\", \"def\"};"));
	}
}
