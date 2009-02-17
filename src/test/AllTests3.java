package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.StandardLibraryTests.BooleanTests;
import test.StandardLibraryTests.GraphTests;
import test.StandardLibraryTests.IntegerTests;
import test.StandardLibraryTests.ListTests;
import test.StandardLibraryTests.RealTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	BooleanTests.class,
	GraphTests.class,
	IntegerTests.class,
	ListTests.class,
	RealTests.class
	
})

public class AllTests3 {
	
}

