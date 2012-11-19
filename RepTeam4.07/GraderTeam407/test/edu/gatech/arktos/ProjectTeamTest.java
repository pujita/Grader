package edu.gatech.arktos;

import java.util.ArrayList;
import java.util.HashMap;

import edu.gatech.arktos.ProjectTeam;
import junit.framework.TestCase;

/* Test cases for ProjectTeam class */
public class ProjectTeamTest extends TestCase {

	// Object to access ProjectTeam class through
	private ProjectTeam pTest;
	
	public ProjectTeamTest(String name) {
		super(name);
	}

	/* Setup environment - called before any test method */
	protected void setUp() throws Exception {
		super.setUp();
		// Call constructor with values from spreadsheet
		ArrayList<String> teamMembers = new ArrayList<String>();
		teamMembers.add("Freddie Catlay");
		teamMembers.add("Shevon Wise");
		teamMembers.add("Kym Hiles");
		teamMembers.add("Ernesta Anderson");
		teamMembers.add("Sheree Gadow");
		pTest = new ProjectTeam(1, "Team 1", "Description", teamMembers);
	}

	/* Release environment - called after any test method */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/* Test assignments under constructor as well as all standard get and set functions */
	public void testProjectTeam() {
		// Set functions
		pTest.setTeamNumber("1");
		pTest.setProjectNumber(1);
		pTest.setTeamName("Team 1");
		ArrayList<String> teamMembers1 = new ArrayList<String>();
		teamMembers1.add("Test Name 1");
		teamMembers1.add("Test Name 2");		
		pTest.setTeamMembers(teamMembers1);
		// Get functions
		assertEquals("1", pTest.getTeamNumber());
		assertEquals(1, pTest.getProjectNumber());
		assertEquals("Team 1", pTest.getTeamName());
		ArrayList<String> teamMembers2 = pTest.getTeamMembers();
		assertEquals("Test Name 1", teamMembers2.get(0));
		assertEquals("Test Name 2", teamMembers2.get(2));
		//assertEquals("Description", pTest.getDescription());
	}

	/* Test Average Score functions */
	public void testAverageScore() {
		pTest.addAverageScore("Freddie Catlay", 9.25);
		assertEquals(9.25, pTest.getAverageScore("Freddie Catlay"));
	}

	/* Test all functions relating to HashMap<String, Integer> teamScores */
	public void testTeamScores() {
		// Test addTeamScores()
		pTest.addTeamScores("Team 1", 93);
		// Test getTeamScores()
		int teamScore = pTest.getTeamScores("Team 1");
		assertEquals(93, teamScore);
		// Test getAllTeamScores()
		HashMap<String, Integer> allTeamScores = pTest.getAllTeamScores();
		int teamScoreFromHashMap = allTeamScores.get("Team 1");
		assertEquals(93, teamScoreFromHashMap);
	}
	
	/* Test all functions relating to HashMap<String, ArrayList<Integer>> peerScores */
	public void testPeerScores() {
		// Populate and add list of peer scores to the hash map
		ArrayList<Integer> peerScores = new ArrayList<Integer>();
		peerScores.add(92);
		peerScores.add(100);
		peerScores.add(88);
		// Test addPeerScores()
		pTest.addPeerScores("Team 2", peerScores);
		// Test getPeerScores()
		int testScore = pTest.getPeerScores("Team 2").get(0);
		assertEquals(92, testScore);
	}
	
	/* Test toString() function */
	public void testToString() {
		assertEquals("1. Description", pTest.toString());
	}

}
