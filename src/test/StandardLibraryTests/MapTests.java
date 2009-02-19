package test.StandardLibraryTests;

import org.junit.Test;
import org.meta_environment.rascal.interpreter.errors.*;

import test.TestFramework;
import static org.junit.Assert.*;

public class MapTests extends TestFramework {

	@Test
	public void getOneFrom() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("getOneFrom((1:10)) == 1;"));
		assertTrue(runTestInSameEvaluator("{int N = getOneFrom((1:10, 2:20)); (N == 1) || (N ==2);}"));
	}
	
	@Test(expected=EmptyMapError.class)
	public void getOneFromError() {
		runTest("import Map;", "getOneFrom(());");
	}

	@Test
	public void domain() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("domain(()) == {};"));
		assertTrue(runTestInSameEvaluator("domain((1:10, 2:20)) == {1,2};"));
	}

	// mapper
	@Test
	public void mapper() {

		prepare("import Map;");

		String inc = "int inc(int n) {return n + 1;} ";
		String dec = "int dec(int n) {return n - 1;} ";

		assertTrue(runTestInSameEvaluator("{" + inc
				+ "mapper((), #inc, #inc) == ();}"));
		assertTrue(runTestInSameEvaluator("{" + inc
				+ "mapper((1:10,2:20), #inc, #inc) == (2:11,3:21);}"));

		assertTrue(runTestInSameEvaluator("{" + inc + dec
				+ "mapper((), #inc, #dec) == ();}"));
		assertTrue(runTestInSameEvaluator("{" + inc + dec
				+ "mapper((1:10,2:20), #inc, #dec) == (2:9,3:19);}"));
	}

	// range
	@Test
	public void range() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("range(()) == {};"));
		assertTrue(runTestInSameEvaluator("range((1:10, 2:20)) == {10,20};"));
	}

	// size
	@Test
	public void size() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("size(()) == 0;"));
		assertTrue(runTestInSameEvaluator("size((1:10)) == 1;"));
		assertTrue(runTestInSameEvaluator("size((1:10,2:20)) == 2;"));
	}

	// toList
	@Test
	public void toList() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("toList(()) == [];"));
		assertTrue(runTestInSameEvaluator("toList((1:10)) == [<1,10>];"));
		assertTrue(runTestInSameEvaluator("{list[tuple[int,int]] L = toList((1:10, 2:20)); (L == [<1,10>,<2,20>]) || (L == [<2,20>,<1,10>]);}"));
	}

	// toRel
	@Test
	public void toRel() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("toRel(()) == {};"));
		assertTrue(runTestInSameEvaluator("toRel((1:10)) == {<1,10>};"));
		assertTrue(runTestInSameEvaluator("{rel[int,int] R = toRel((1:10, 2:20)); R == {<1,10>,<2,20>};}"));
	}

	// toString
	@Test
	public void testToString() {

		prepare("import Map;");

		assertTrue(runTestInSameEvaluator("toString(()) == \"()\";"));
		assertTrue(runTestInSameEvaluator("toString((1:10)) == \"(1:10)\";"));
	}

}
