package org.meta_environment.rascal.test.StandardLibraryTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.meta_environment.rascal.test.TestFramework;


public class RelationTests extends TestFramework {

	@Test
	public void carrier() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("carrier({<1,10>,<2,20>}) == {1,2,10,20};"));
		assertTrue(runTestInSameEvaluator("carrier({<1,10,100>,<2,20,200>}) == {1,2,10,20,100,200};"));
		assertTrue(runTestInSameEvaluator("carrier({<1,10,100,1000>,<2,20,200,2000>}) == {1,2,10,20,100,200,1000,2000};"));
		assertTrue(runTestInSameEvaluator("carrier({<1,10,100,1000,10000>,<2,20,200,2000,20000>}) == {1,2,10,20,100,200,1000,2000,10000,20000};"));

	}

	@Test
	public void carrierR() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("carrierR({<1,10>,<2,20>}, {} ) == {};"));
		assertTrue(runTestInSameEvaluator("carrierR({<1,10>,<2,20>}, {2,3} ) == {};"));
		assertTrue(runTestInSameEvaluator("carrierR({<1,10>,<2,20>}, {2,20} ) == {<2,20>};"));
		assertTrue(runTestInSameEvaluator("carrierR({<1,10,100>,<2,20,200>}, {2, 20,200}) == {<2,20,200>};"));
		assertTrue(runTestInSameEvaluator("carrierR({<1,10,100>,<2,20,200>}, {1,2,10,20,100,200}) == {<1,10,100>,<2,20,200>};"));
		assertTrue(runTestInSameEvaluator("carrierR({<1,10,100,1000>,<2,20,200,2000>}, {1,10,100,1000}) == {<1,10,100,1000>};"));
		assertTrue(runTestInSameEvaluator("carrierR({<1,10,100,1000>,<2,20,200,2000>}, {2,20,200,2000}) == {<2,20,200,2000>};"));
	}

	@Test
	public void carrierX() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("carrierX({<1,10>,<2,20>}, {} ) == {<1,10>,<2,20>};"));
		assertTrue(runTestInSameEvaluator("carrierX({<1,10>,<2,20>}, {2,3} ) == {<1,10>};"));
		assertTrue(runTestInSameEvaluator("carrierX({<1,10,100>,<2,20,200>}, {20}) == {<1,10,100>};"));
		assertTrue(runTestInSameEvaluator("carrierX({<1,10,100>,<2,20,200>}, {20,100}) == {};"));
		assertTrue(runTestInSameEvaluator("carrierX({<1,10,100,1000>,<2,20,200,2000>}, {1000}) == {<2,20,200,2000>};"));
		assertTrue(runTestInSameEvaluator("carrierX({<1,10,100,1000>,<2,20,200,2000>}, {2}) == {<1,10,100,1000>};"));
	}

	@Test
	public void complement() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("complement({<1,10>,<2,20>}) == {<2,10>,<1,20>};"));
		assertTrue(runTestInSameEvaluator("complement({<1,10,100>,<2,20,200>}) == {<2,20,100>,<2,10,200>,<2,10,100>,<1,20,200>,<1,20,100>,<1,10,200>};"));
		assertTrue(runTestInSameEvaluator("complement({<1,10,100,1000>,<2,20,200,2000>}) == {<2,20,200,1000>,<1,10,100,2000>,<1,10,200,1000>,<1,10,200,2000>,<1,20,100,1000>,<1,20,100,2000>,<1,20,200,1000>,<1,20,200,2000>,<2,10,100,1000>,<2,10,100,2000>,<2,10,200,1000>,<2,10,200,2000>,<2,20,100,1000>,<2,20,100,2000>};"));
	}

	@Test
	public void domain() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("domain({<1,10>,<2,20>}) == {1,2};"));
		assertTrue(runTestInSameEvaluator("domain({<1,10,100>,<2,20,200>}) == {1,2};"));
		assertTrue(runTestInSameEvaluator("domain({<1,10,100,1000>,<2,20,200,2000>}) == {1,2};"));
		assertTrue(runTestInSameEvaluator("domain({<1,10,100,1000,10000>,<2,20,200,2000,20000>}) == {1,2};"));
	}

	@Test
	public void domainR() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("domainR({<1,10>,<2,20>}, {}) == {};"));
		assertTrue(runTestInSameEvaluator("domainR({<1,10>,<2,20>}, {2}) == {<2,20>};"));
		assertTrue(runTestInSameEvaluator("domainR({<1,10,100>,<2,20,200>}, {2,5}) == {<2,20,200>};"));
		assertTrue(runTestInSameEvaluator("domainR({<1,10,100,1000>,<2,20,200,2000>}, {1,3}) == {<1,10,100,1000>};"));
		assertTrue(runTestInSameEvaluator("domainR({<1,10,100,1000,10000>,<2,20,200,2000,20000>},{2,5}) == {<2,20,200,2000,20000>};"));
	}

	@Test
	public void domainX() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("domainX({<1,10>,<2,20>}, {}) == {<1,10>,<2,20>};"));
		assertTrue(runTestInSameEvaluator("domainX({<1,10>,<2,20>}, {2}) == {<1,10>};"));
		assertTrue(runTestInSameEvaluator("domainX({<1,10,100>,<2,20,200>}, {2,5}) == {<1,10,100>};"));
		assertTrue(runTestInSameEvaluator("domainX({<1,10,100,1000>,<2,20,200,2000>}, {1,3}) == {<2,20,200,2000>};"));
		assertTrue(runTestInSameEvaluator("domainX({<1,10,100,1000,10000>,<2,20,200,2000,20000>},{2,5}) == {<1,10,100,1000,10000>};"));

	}
	
	@Test
	public void ident() {

		prepare("import Relation;");
		
		//assertTrue(runTestInSameEvaluator("ident({}) == {};"));
		assertTrue(runTestInSameEvaluator("ident({1}) == {<1,1>};"));
		assertTrue(runTestInSameEvaluator("ident({1,2,3}) == {<1,1>,<2,2>,<3,3>};"));
	}

	@Test
	public void invert() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("invert({<1,10>,<2,20>}) == {<10,1>,<20,2>};"));
		assertTrue(runTestInSameEvaluator("invert({<1,10,100>,<2,20,200>}) == {<100,10,1>,<200,20,2>};"));
		assertTrue(runTestInSameEvaluator("invert({<1,10,100,1000>,<2,20,200,2000>}) == {<1000,100,10,1>,<2000,200,20,2>};"));
		assertTrue(runTestInSameEvaluator("invert({<1,10,100,1000,10000>,<2,20,200,2000,20000>}) == {<10000,1000,100,10,1>,<20000,2000,200,20,2>};"));
	}

	@Test
	public void range() {

		prepare("import Relation;");

		assertTrue(runTestInSameEvaluator("range({<1,10>,<2,20>}) == {10,20};"));
		assertTrue(runTestInSameEvaluator("range({<1,10,100>,<2,20,200>}) == {<10,100>,<20,200>};"));
		assertTrue(runTestInSameEvaluator("range({<1,10,100,1000>,<2,20,200,2000>}) == {<10,100,1000>,<20,200,2000>};"));
		assertTrue(runTestInSameEvaluator("range({<1,10,100,1000,10000>,<2,20,200,2000,20000>}) == {<10,100,1000,10000>,<20,200,2000,20000>};"));
	}

	@Test
	public void rangeR() {

		prepare("import Relation;");

		// assertTrue(runTestInSameEvaluator("rangeR({<1,10>,<2,20>}, {}) == {};"));
		assertTrue(runTestInSameEvaluator("rangeR({<1,10>,<2,20>}, {20}) == {<2,20>};"));
		// assertTrue(runTestInSameEvaluator("rangeR({<1,10,100>,<2,20,200>}) == {<10,100>,<20,200>};"));
		// assertTrue(runTestInSameEvaluator("rangeR({<1,10,100,1000>,<2,20,200,2000>}) == {<10,100,1000>,<20,200,2000>};"));
		// assertTrue(runTestInSameEvaluator("rangeR({<1,10,100,1000,10000>,<2,20,200,2000,20000>}) == {<10,100,1000,10000>,<20,200,2000,20000>};"));

	}

	@Test
	public void rangeX() {

		prepare("import Relation;");

		// assertTrue(runTestInSameEvaluator("rangeX({<1,10>,<2,20>}, {}) == {<1,10>,<2,20>};"));
		assertTrue(runTestInSameEvaluator("rangeX({<1,10>,<2,20>}, {20}) == {<1,10>};"));
		// assertTrue(runTestInSameEvaluator("rangeX({<1,10,100>,<2,20,200>}) == {<10,100>,<20,200>};"));
		// assertTrue(runTestInSameEvaluator("rangeX({<1,10,100,1000>,<2,20,200,2000>}) == {<10,100,1000>,<20,200,2000>};"));
		// assertTrue(runTestInSameEvaluator("rangeX({<1,10,100,1000,10000>,<2,20,200,2000,20000>}) == {<10,100,1000,10000>,<20,200,2000,20000>};"));

	}

}
