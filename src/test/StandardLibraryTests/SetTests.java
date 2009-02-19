package test.StandardLibraryTests;

import org.junit.Test;
import org.meta_environment.rascal.interpreter.errors.EmptySetError;

import test.TestFramework;
import static org.junit.Assert.*;

public class SetTests extends TestFramework {

	@Test
	public void average() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{int N = Set::average({},0); N == 0;}"));
		assertTrue(runTestInSameEvaluator("{int N = average({},0); N == 0;}"));
		assertTrue(runTestInSameEvaluator("{int N = Set::average({1},0); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = Set::average({1, 3},0); N == 2;}"));
	}

	@Test
	public void getOneFrom() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{int N = Set::getOneFrom({1}); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = Set::getOneFrom({1}); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = getOneFrom({1}); N == 1;}"));
		assertTrue(runTestInSameEvaluator("{int N = Set::getOneFrom({1, 2}); (N == 1) || (N == 2);}"));
		assertTrue(runTestInSameEvaluator("{int N = Set::getOneFrom({1, 2, 3}); (N == 1) || (N == 2) || (N == 3);}"));
		assertTrue(runTestInSameEvaluator("{real D = Set::getOneFrom({1.0,2.0}); (D == 1.0) || (D == 2.0);}"));
		assertTrue(runTestInSameEvaluator("{str S = Set::getOneFrom({\"abc\",\"def\"}); (S == \"abc\") || (S == \"def\");}"));
	}
	
	@Test(expected=EmptySetError.class)
	public void getOneFromError() {
		runTest("import Set;", "getOneFrom({});");
	}
	

	@Test
	public void mpper() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{int inc(int n) {return n + 1;} mapper({1, 2, 3}, #inc) == {2, 3, 4};}"));

	}

	@Test
	public void max() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{Set::max({1, 2, 3, 2, 1}) == 3;}"));
		assertTrue(runTestInSameEvaluator("{max({1, 2, 3, 2, 1}) == 3;}"));
	}

	@Test
	public void min() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{Set::min({1, 2, 3, 2, 1}) == 1;}"));
		assertTrue(runTestInSameEvaluator("{min({1, 2, 3, 2, 1}) == 1;}"));
	}

	@Test
	public void multiply() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{multiply({1, 2, 3, 4}, 1) == 24;}"));
		assertTrue(runTestInSameEvaluator("{Set::multiply({1, 2, 3, 4}, 1) == 24;}"));
	}

	@Test
	public void power() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{Set::power({}) == {{}};}"));
		assertTrue(runTestInSameEvaluator("{Set::power({1}) == {{}, {1}};}"));
		assertTrue(runTestInSameEvaluator("{Set::power({1, 2}) == {{}, {1}, {2}, {1,2}};}"));
		assertTrue(runTestInSameEvaluator("{Set::power({1, 2, 3}) == {{}, {1}, {2}, {3}, {1,2}, {1,3}, {2,3}, {1,2,3}};}"));
		assertTrue(runTestInSameEvaluator("{Set::power({1, 2, 3, 4}) == { {}, {1}, {2}, {3}, {4}, {1,2}, {1,3}, {1,4}, {2,3}, {2,4}, {3,4}, {1,2,3}, {1,2,4}, {1,3,4}, {2,3,4}, {1,2,3,4}};}"));
	}

	@Test
	public void reducer() {

		prepare("import Set;");
		String add = "int add(int x, int y){return x + y;}";
		assertTrue(runTestInSameEvaluator("{" + add
				+ "reducer({1, 2, 3, 4}, #add, 0) == 10;}"));
	}

	@Test
	public void size() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("Set::size({}) == 0;"));
		assertTrue(runTestInSameEvaluator("size({}) == 0;"));
		assertTrue(runTestInSameEvaluator("Set::size({1}) == 1;"));
		assertTrue(runTestInSameEvaluator("Set::size({1,2,3}) == 3;"));
	}

	@Test
	public void sum() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{sum({1,2,3},0) == 6;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({1,2,3}, 0) == 6;}"));

		assertTrue(runTestInSameEvaluator("{Set::sum({}, 0) == 0;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({}, 0) == 0;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({1}, 0) == 1;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({1, 2}, 0) == 3;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({1, 2, 3}, 0) == 6;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({1, -2, 3}, 0) == 2;}"));
		assertTrue(runTestInSameEvaluator("{Set::sum({1, 1, 1}, 0) == 1;}"));
	}

	@Test
	public void takeOneFrom() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{<E, SI> = Set::takeOneFrom({1}); (E == 1) && (SI == {}) ;}"));
		assertTrue(runTestInSameEvaluator("{<E, SI> = Set::takeOneFrom({1,2}); ((E == 1) && (SI == {2})) || ((E == 2) && (SI == {1}));}"));
	}
	

	@Test(expected=EmptySetError.class)
	public void takeOneFromError() {
		runTest("import Set;", "takeOneFrom({});");
	}

	@Test
	public void toList() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("{Set::toList({}) == [];}"));
		assertTrue(runTestInSameEvaluator("{toList({}) == [];}"));
		assertTrue(runTestInSameEvaluator("{Set::toList({1}) == [1];}"));
		assertTrue(runTestInSameEvaluator("{(Set::toList({1, 2, 1}) == [1, 2]) || (Set::toList({1, 2, 1}) == [2, 1]);}"));
	}

	@Test
	public void toMap() {

		prepare("import Set;");
		assertTrue(runTestInSameEvaluator("{Set::toMap({}) == ();}"));

		assertTrue(runTestInSameEvaluator("{toMap({}) == ();}"));
		assertTrue(runTestInSameEvaluator("{Set::toMap({<1, \"a\">}) == (1 : \"a\");}"));
		assertTrue(runTestInSameEvaluator("{Set::toMap({<1, \"a\">, <2, \"b\">}) == (1 : \"a\", 2 : \"b\");}"));
	}

	@Test
	public void testToString() {

		prepare("import Set;");

		assertTrue(runTestInSameEvaluator("Set::toString({}) == \"{}\";"));
		assertTrue(runTestInSameEvaluator("toString({}) == \"{}\";"));
		assertTrue(runTestInSameEvaluator("Set::toString({1}) == \"{1}\";"));
		assertTrue(runTestInSameEvaluator("{ S = Set::toString({1, 2}); (S == \"{1,2}\") || (S == \"{2,1}\");}"));
	}
}
