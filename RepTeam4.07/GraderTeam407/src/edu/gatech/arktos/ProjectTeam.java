package edu.gatech.arktos;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;

/**
 * Project class for students
 * @author Pujita
 *
 */
public class ProjectTeam {

	private int projectNumber;
	private String teamName;
	private ArrayList<String> teamMembers;
	private HashMap<String, Double> averageScore;//from P1 contribution etc
	private HashMap<String, ArrayList<Integer>> peerScores;//from P1 contribution etc
	private HashMap<String, Integer> teamScores; //from P1 grades etc.
	
	/**
	 * Constructor
	 * @param projectNumber
	 * @param teamName
	 * @param teamMembers
	 */
	public ProjectTeam (int projectNumber, String teamName, ArrayList<String> teamMembers) {
		this.projectNumber = projectNumber;
		this.teamName = teamName;
		this.teamMembers = teamMembers;
		averageScore = new HashMap<String, Double>();
		peerScores = new HashMap<String, ArrayList<Integer>>();
		teamScores = new HashMap<String, Integer>();
	}
	
	
	/**
	 * Read P1 P2 and P3 grades and store all the scores and their breakdowns in teamScores.
	 * To get the total, use key TOTAL
	 * @param gradesFeed
	 */
	public void addprojectGrades( ListFeed gradesFeed) {
		String criteria, gradeStr;
		int grade;
		for (ListEntry row : gradesFeed.getEntries()) {	
	    	
	    	CustomElementCollection cells = row.getCustomElements();
	    	criteria = cells.getValue("Criteria").replace(":", "");
	    	gradeStr = cells.getValue(this.getTeamName().replace(" ", ""));
	    	if(gradeStr != null && !gradeStr.isEmpty()) {
	    		grade = GradesDB.convertToInt(gradeStr);
	    		this.addTeamScores(criteria, grade);
	    	}
		}
	}
	
	
	/**
	 * Read P1 P2 and P3 contributions and populate averageScore and peerScores
	 * @param contributionsFeed
	 */
	public void addprojectContributions( ListFeed contributionsFeed) {
		String name, studentName, contribution;
		int numMembers = teamMembers.size();
		int count = numMembers, i;
		double average;
		ArrayList<Integer> contributions;
		boolean found = false;
		
		for (ListEntry row : contributionsFeed.getEntries()) {	
	    	CustomElementCollection cells = row.getCustomElements();
	    	
	    	// Find the row in the spreadsheet with the team contributions information
	    	if(!found) {
		    	name = cells.getValue("TeamName");
		    	if(name == null || name.isEmpty() || !name.equals(this.teamName)) {
		    		continue;
		    	} else {
		    		found = true;
		    		continue;
		    	}
	    	}
	    	// Return when all the students in the team have been read    	
	    	if (count == 0) {
	    		return;
	    	}
	    	studentName = cells.getValue("Students");
	    	average = Double.parseDouble(cells.getValue("Average"));
	    	contributions = new ArrayList<Integer>();
	    	// get contributions
	    	for (i = 1; i <= numMembers; i++) {
	    		contribution = cells.getValue("Grader" + i);
	    		if(contribution != null && !contribution.isEmpty()) {
	    			contributions.add(GradesDB.convertToInt(contribution));
	    		}
	    	}
	    	this.addAverageScore(studentName, average);
	    	this.addPeerScores(studentName, contributions);
	    	count --;
		}
	}
	
	public String getTeamNumber() {
		return teamName;
	}
	
	/**
	 * From P1 Teams document etc
	 * @param teamNumber
	 */
	public void setTeamNumber (String teamNumber) {
		this.teamName = teamNumber;
	}
	
	public ArrayList<String> getTeamMembers() {
		return teamMembers;
	}
	
	/**
	 * From P1 Teams document etc
	 * @param teamNumber
	 */
	public void setTeamMembers (ArrayList<String> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	public Double getAverageScore (String name) {
		return averageScore.get(name);
	}
	
	public void addAverageScore (String name, Double score) {
		averageScore.put(name, score);
	}
	
	public ArrayList<Integer> getPeerScores (String name) {
		return peerScores.get(name);
	}
	
	public void addPeerScores (String name, ArrayList<Integer> peerScores) {
		this.peerScores.put(name, peerScores);
	}
	
	public Integer getTeamScores (String name) {
		return teamScores.get(name);
	}
	
	public void addTeamScores(String name, Integer teamScore) {
		this.teamScores.put(name, teamScore);
	}

	public int getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
}
