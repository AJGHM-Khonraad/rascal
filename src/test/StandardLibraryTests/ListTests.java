package test.StandardLibraryTests;

import org.junit.Test;
import org.meta_environment.rascal.interpreter.errors.EmptyListError;
import org.meta_environment.rascal.interpreter.errors.IndexOutOfBoundsError;

import test.TestFramework;
import static org.junit.Assert.*;

public class ListTests extends TestFramework {

	@Test
	public void average() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{int N = List::average([],0); N == 0;}"));
		assertTrue(runTestInSameEvaluator("{int N = average([],0); N == 0;}"));
		assertTrue(runTestInSameEvaluator("{int N = List::average([1],0); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = List::average([1, 3],0); N == 2;}"));
	}

	@Test
	public void domain() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{domain([]) == [];}"));
		assertTrue(runTestInSameEvaluator("{domain([1]) == [0];}"));
		assertTrue(runTestInSameEvaluator("{domain([1, 2]) == [0, 1];}"));
	}

	@Test
	public void getOneFrom() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{int N = List::getOneFrom([1]); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = getOneFrom([1]); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = List::getOneFrom([1,2]); (N == 1) || (N == 2);}"));
		assertTrue(runTestInSameEvaluator("{int N = List::getOneFrom([1,2,3]); (N == 1) || (N == 2) || (N == 3);}"));
		assertTrue(runTestInSameEvaluator("{real D = List::getOneFrom([1.0,2.0]); (D == 1.0) || (D == 2.0);}"));
		assertTrue(runTestInSameEvaluator("{str S = List::getOneFrom([\"abc\",\"def\"]); (S == \"abc\") || (S == \"def\");}"));
	}
	

	@Test(expected=EmptyListError.class)
	public void getOneFromError() {
		runTest("import List;", "getOneFrom([]);");
	}

	@Test
	public void head() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::head([1]) == 1;}"));
		assertTrue(runTestInSameEvaluator("{head([1]) == 1;}"));
		assertTrue(runTestInSameEvaluator("{List::head([1, 2]) == 1;}"));

		assertTrue(runTestInSameEvaluator("{head([1, 2, 3, 4], 0) == [];}"));
		assertTrue(runTestInSameEvaluator("{head([1, 2, 3, 4], 1) == [1];}"));
		assertTrue(runTestInSameEvaluator("{head([1, 2, 3, 4], 2) == [1,2];}"));
		assertTrue(runTestInSameEvaluator("{head([1, 2, 3, 4], 3) == [1,2,3];}"));
		assertTrue(runTestInSameEvaluator("{head([1, 2, 3, 4], 4) == [1,2,3,4];}"));
	}
	
	@Test(expected=EmptyListError.class)
	public void headError1() {
		runTest("import List;", "head([]);");
	}
	
	@Test(expected=IndexOutOfBoundsError.class)
	public void headError2() {
		runTest("import List;", "head([],3);");
	}
	
	@Test(expected=IndexOutOfBoundsError.class)
	public void testHead2() {
		runTest("import List;", "head([1,2,3], 4);");
	}

	@Test
	public void insertAt() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("List::insertAt(1, 0, []) == [1];"));
		assertTrue(runTestInSameEvaluator("insertAt(1, 0, []) == [1];"));
		assertTrue(runTestInSameEvaluator("List::insertAt(1, 1, [2,3]) == [2,1, 3];"));
		assertTrue(runTestInSameEvaluator("insertAt(1, 1, [2,3]) == [2, 1, 3];"));
		assertTrue(runTestInSameEvaluator("List::insertAt(1, 2, [2,3]) == [2,3,1];"));
		assertTrue(runTestInSameEvaluator("insertAt(1, 2, [2,3]) == [2, 3, 1];"));
	}
	

	@Test(expected=IndexOutOfBoundsError.class)
	public void testInsertAt() {
		runTest("import List;", "insertAt([1,2,3], 4, 5);");
	}

	@Test
	public void mapper() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{int inc(int n) {return n + 1;} mapper([1, 2, 3], #inc) == [2, 3, 4];}"));
	}

	@Test
	public void max() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::max([1, 2, 3, 2, 1]) == 3;}"));
		assertTrue(runTestInSameEvaluator("{max([1, 2, 3, 2, 1]) == 3;}"));
	}

	@Test
	public void min() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::min([1, 2, 3, 2, 1]) == 1;}"));
		assertTrue(runTestInSameEvaluator("{min([1, 2, 3, 2, 1]) == 1;}"));
	}

	@Test
	public void multiply() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{multiply([1, 2, 3, 4], 1) == 24;}"));
		assertTrue(runTestInSameEvaluator("{List::multiply([1, 2, 3, 4], 1) == 24;}"));

	}

	@Test
	public void reducer() {

		prepare("import List;");
		String add = "int add(int x, int y){return x + y;}";

		assertTrue(runTestInSameEvaluator("{" + add
				+ "reducer([1, 2, 3, 4], #add, 0) == 10;}"));
	}

	@Test
	public void reverse() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::reverse([]) == [];}"));
		assertTrue(runTestInSameEvaluator("{reverse([]) == [];}"));
		assertTrue(runTestInSameEvaluator("{List::reverse([1]) == [1];}"));
		assertTrue(runTestInSameEvaluator("{List::reverse([1,2,3]) == [3,2,1];}"));
	}

	@Test
	public void size() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::size([]) == 0;}"));
		assertTrue(runTestInSameEvaluator("{size([]) == 0;}"));
		assertTrue(runTestInSameEvaluator("{List::size([1]) == 1;}"));
		assertTrue(runTestInSameEvaluator("{List::size([1,2,3]) == 3;}"));
	}

	@Test
	public void slice() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 0, 0) == [];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 0, 1) == [1];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 0, 2) == [1,2];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 0, 3) == [1,2,3];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 0, 4) == [1,2,3,4];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 1, 0) == [];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 1, 1) == [2];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 1, 2) == [2,3];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 3, 0) == [];}"));
		assertTrue(runTestInSameEvaluator("{slice([1,2,3,4], 3, 1) == [4];}"));
	}

	@Test
	public void sort() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::sort([]) == [];}"));
		assertTrue(runTestInSameEvaluator("{sort([]) == [];}"));
		assertTrue(runTestInSameEvaluator("{List::sort([1]) == [1];}"));
		assertTrue(runTestInSameEvaluator("{sort([1]) == [1];}"));
		assertTrue(runTestInSameEvaluator("{List::sort([2, 1]) == [1,2];}"));
		assertTrue(runTestInSameEvaluator("{sort([2, 1]) == [1,2];}"));
		assertTrue(runTestInSameEvaluator("{List::sort([2,-1,4,-2,3]) == [-2,-1,2,3, 4];}"));
		assertTrue(runTestInSameEvaluator("{sort([2,-1,4,-2,3]) == [-2,-1,2,3, 4];}"));
	}

	@Test
	public void sum() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{sum([1,2,3],0) == 6;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([1,2,3], 0) == 6;}"));

		assertTrue(runTestInSameEvaluator("{List::sum([], 0) == 0;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([], 0) == 0;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([1], 0) == 1;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([1, 2], 0) == 3;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([1, 2, 3], 0) == 6;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([1, -2, 3], 0) == 2;}"));
		assertTrue(runTestInSameEvaluator("{List::sum([1, 1, 1], 0) == 3;}"));
	}

	@Test
	public void tail() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::tail([1]) == [];}"));
		assertTrue(runTestInSameEvaluator("{tail([1]) == [];}"));
		assertTrue(runTestInSameEvaluator("{List::tail([1, 2]) == [2];}"));
		assertTrue(runTestInSameEvaluator("{tail([1, 2, 3]) + [4, 5, 6]  == [2, 3, 4, 5, 6];}"));
		assertTrue(runTestInSameEvaluator("{tail([1, 2, 3]) + tail([4, 5, 6])  == [2, 3, 5, 6];}"));

		assertTrue(runTestInSameEvaluator("{tail([1, 2, 3], 2) == [2,3];}"));
		assertTrue(runTestInSameEvaluator("{tail([1, 2, 3], 0) == [];}"));
	}
	

	@Test(expected=IndexOutOfBoundsError.class)
	public void tailError1() {
		runTest("import List;", "tail([]);");
	}
	
	@Test(expected=IndexOutOfBoundsError.class)
	public void tailError2() {
		runTest("import List;",  "tail([1,2,3], 4);");
	}

	@Test
	public void takeOneFrom() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{<E, L> = takeOneFrom([1]); (E == 1) && (L == []);}"));
		assertTrue(runTestInSameEvaluator("{<E, L> = List::takeOneFrom([1,2]); ((E == 1) && (L == [2])) || ((E == 2) && (L == [1]));}"));
	}
	

	@Test(expected=EmptyListError.class)
	public void takeOneFromError() {
		runTest("import List;", "takeOneFrom([]);");
	}

	@Test
	public void toMap() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::toMap([]) == ();}"));
		assertTrue(runTestInSameEvaluator("{toMap([]) == ();}"));
		assertTrue(runTestInSameEvaluator("{List::toMap([<1,10>, <2,20>]) == (1=>10, 2=>20);}"));
	}

	@Test
	public void toSet() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::toSet([]) == {};}"));
		assertTrue(runTestInSameEvaluator("{toSet([]) == {};}"));
		assertTrue(runTestInSameEvaluator("{List::toSet([1]) == {1};}"));
		assertTrue(runTestInSameEvaluator("{toSet([1]) == {1};}"));
		assertTrue(runTestInSameEvaluator("{List::toSet([1, 2, 1]) == {1, 2};}"));
	}

	@Test
	public void testToString() {

		prepare("import List;");

		assertTrue(runTestInSameEvaluator("{List::toString([]) == \"[]\";}"));
		assertTrue(runTestInSameEvaluator("{toString([]) == \"[]\";}"));
		assertTrue(runTestInSameEvaluator("{List::toString([1]) == \"[1]\";}"));
		assertTrue(runTestInSameEvaluator("{List::toString([1, 2]) == \"[1,2]\";}"));
	}
}
