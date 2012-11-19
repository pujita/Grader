package edu.gatech.arktos;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for edu.gatech.cc.arktos.testcases");
		//$JUnit-BEGIN$
		suite.addTestSuite(SessionTest.class);
		suite.addTestSuite(GradesDBTest.class);
		suite.addTestSuite(AssignmentTest.class);
		suite.addTestSuite(StudentTest.class);
		//$JUnit-END$
		return suite;
	}

}
