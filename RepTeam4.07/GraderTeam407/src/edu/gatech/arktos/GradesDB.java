package edu.gatech.arktos;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

import edu.gatech.arktos.Student;

/**
 * GradesDB class to extract all the information from a spreadsheet
 * @author Pujita
 *
 */
public class GradesDB {
	
	private SpreadsheetService service;
	private int numStudents;
	private int numAssignments;
	private int numProjects;
	private HashMap<String, Student> students;
	private WorksheetEntry details, data, attendance, grades;
	private ArrayList<WorksheetEntry> projectTeams, projectGrades, projectContributions;
	
	/**
	 * Constructor
	 * @param spreadsheet
	 * @param service
	 * @throws IOException
	 * @throws ServiceException
	 */
	public GradesDB (SpreadsheetEntry spreadsheet, SpreadsheetService service) throws IOException, ServiceException {
		
		this.service = service;
	    List<WorksheetEntry> worksheets;
		worksheets = spreadsheet.getWorksheets();	
	    
	    // Iterate through each worksheet in the spreadsheet
	    for (WorksheetEntry worksheet : worksheets) {

	    	if (worksheet.getTitle().getPlainText().equals("Details")) {
	    		details = worksheet;			
	    	} else if (worksheet.getTitle().getPlainText().equals("Data")) {
	    		data = worksheet;
	    	} else if (worksheet.getTitle().getPlainText().equals("Attendance")) {
	    		attendance = worksheet;
	    	} else if (worksheet.getTitle().getPlainText().equals("Grades")) {
	    		grades = worksheet;
	    	} else if (worksheet.getTitle().getPlainText().equals("P1 Teams")) {
	    		projectTeams = new ArrayList<WorksheetEntry>();
	    		projectTeams.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P2 Teams")) {
	    		projectTeams.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P3 Teams")) {
	    		projectTeams.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P1 Grades")) {
	    		projectGrades = new ArrayList<WorksheetEntry>();
	    		projectGrades.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P2 Grades")) {
	    		projectGrades.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P3 Grades")) {
	    		projectGrades.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P1 Contri")) {
	    		projectContributions = new ArrayList<WorksheetEntry>();
	    		projectContributions.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P2 Contri")) {
	    		projectContributions.add(worksheet);
	    	} else if (worksheet.getTitle().getPlainText().equals("P3 Contri")) {
	    		projectContributions.add(worksheet);
	    	}
	    }
	    
	    processDetails();
	    processData();
	    createProjects();
	}
	
	
	/**
	 * Get all the student information from the Details spreadsheet
	 * @param worksheet
	 * @throws IOException
	 * @throws ServiceException
	 */
	private void processDetails() throws IOException, ServiceException{
		URL  detailsUrl = details.getListFeedUrl();
	    ListFeed detailsFeed = service.getFeed(detailsUrl, ListFeed.class);
	    students = new HashMap<String, Student>();
	    
	    URL attendanceUrl = attendance.getListFeedUrl();
	    ListFeed attendanceFeed = service.getFeed(attendanceUrl, ListFeed.class);
	    
	    URL gradesUrl = grades.getListFeedUrl();
	    ListFeed gradesFeed = service.getFeed(gradesUrl, ListFeed.class);
	    
	    for (ListEntry row : detailsFeed.getEntries()) {	    	    	    	
	    	String name = row.getTitle().getPlainText();
	    	if (name.isEmpty()) continue;
	    	
	    	CustomElementCollection cells = row.getCustomElements();
	    	
	    	// get attendance and assignment grade information
	    	int attendance = getAttendance(numStudents, attendanceFeed);
	    	int averageAssignmentGrade = getAverageAssignmentGrade(numStudents, gradesFeed);
	    	ArrayList<Integer> assignmentGrades = getAssignmentGrade(numStudents, gradesFeed);
	    	
	    	Student s = new Student(name, cells.getValue("GTID"), cells.getValue("EMAIL"), attendance);	 
	    	s.setAssignmentGrades(assignmentGrades);
	    	s.setAverageAssignmentGrade(averageAssignmentGrade);
	    	students.put(name, s);
	    	numStudents++;
	    }
	}
		

	/**
	 * Get all assignments and projects information
	 * @param worksheet
	 * @throws ServiceException 
	 * @throws IOException 
	 */
	private void processData() throws IOException, ServiceException{
		URL dataUrl = data.getListFeedUrl();
	    ListFeed dataFeed = service.getFeed(dataUrl, ListFeed.class);
	    
	    for (ListEntry row : dataFeed.getEntries()) {	    	    	    	    	
	    	CustomElementCollection cells = row.getCustomElements();
	    	
	    	if (!cells.getValue("Assignments").isEmpty()) {
	    		numAssignments++;
	    	}
	    	if (!cells.getValue("Projects").isEmpty()) {
	    		numProjects++;
	    	}	    	
	    
	    }
	}
	
	
	/**
	 * Iterate through P1 P2 and P3 teams, contributions and grades and create projects and assign it to the students.
	 * @throws IOException
	 * @throws ServiceException
	 */
	private void createProjects() throws IOException, ServiceException {

		URL teamsUrl, gradesUrl, contributionsUrl;
		ListFeed teamsFeed, gradesFeed, contributionsFeed;
	    int projectNumber, i;
	    String teamName, student;
	    ArrayList<String> teamMembers = new ArrayList<String>();
	    ProjectTeam project;
	    
	    for (int j = 0; j < projectTeams.size(); j++) {
	    	//project = new ProjectTeam();
	    			
	    	// parse P1 P2 and P3 teams 
		    teamsUrl = projectTeams.get(j).getListFeedUrl();
		    teamsFeed = service.getFeed(teamsUrl, ListFeed.class);
		   
		    for (ListEntry row : teamsFeed.getEntries()) {	
		    	
		    	CustomElementCollection cells = row.getCustomElements();
		    	projectNumber = j + 1;
		    	teamMembers = new ArrayList<String>();
		    	teamName = cells.getValue("TeamName");
		    
		    	for (i =1; i<= 5; i++) {
		    		student = cells.getValue("Student" + i);
		    		if(student != null && !student.isEmpty()) {
		    			teamMembers.add(student);
		    		}
		    	}
		    	project = new ProjectTeam(projectNumber, teamName, teamMembers);
		    	
		    	// Parse P1 P2 and P3 grades
		    	gradesUrl = projectGrades.get(j).getListFeedUrl();
			    gradesFeed = service.getFeed(gradesUrl, ListFeed.class);
		    	project.addprojectGrades(gradesFeed);
		    	
		    	// Parse P1 P2 and P3 contributions
		    	contributionsUrl = projectContributions.get(j).getListFeedUrl();
			    contributionsFeed = service.getFeed(contributionsUrl, ListFeed.class);
		    	project.addprojectContributions(contributionsFeed);
		    	
		    	assignProjectsToStudents(teamMembers, project);
		    }
	    }
	}
	
	
	/**
	 * Assign the project to the all the team members
	 * @param teamMembers
	 * @param project
	 */
	public void assignProjectsToStudents(ArrayList<String> teamMembers, ProjectTeam project) {
		Student student;
		for (String teamMember: teamMembers) {
			student = students.get(teamMember);
			student.addProjects(project);
			students.put(student.getName(), student);
		}
	}
	
	
	/**
	 * get Attendance of a student from the attendance spreadsheet
	 * @param index
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws ServiceException
	 */
	private int getAttendance (int index, ListFeed attendancelistFeed)  throws IOException, ServiceException {	
	    ListEntry row = attendancelistFeed.getEntries().get(index+1);
	    CustomElementCollection cells = row.getCustomElements();
    	int attendance = convertToInt(cells.getValue("Total"));
    	return attendance;
	}

	
	/**
	 * Get the array of assignment grades from the grades spreadsheet
	 * @param index
	 * @param gradeslistFeed
	 * @return
	 */
	private ArrayList<Integer> getAssignmentGrade(int index,
			ListFeed gradeslistFeed) {
		ListEntry row = gradeslistFeed.getEntries().get(index);
	    CustomElementCollection cells = row.getCustomElements();
	    
	    ArrayList<Integer> assignmentGrades = new ArrayList<Integer>();    
	    assignmentGrades.add( convertToInt( cells.getValue("Assignment1")));
	    assignmentGrades.add( convertToInt( cells.getValue("Assignment2")));
	    assignmentGrades.add( convertToInt( cells.getValue("Assignment3")));
		return assignmentGrades;
	}

	
	/**
	 * Get the average assignment grades from the grades spreadsheet
	 * @param index
	 * @param gradeslistFeed
	 * @return
	 */
	private int getAverageAssignmentGrade(int index,
			ListFeed gradeslistFeed) {
		ListEntry row = gradeslistFeed.getEntries().get(index);
	    CustomElementCollection cells = row.getCustomElements();
	    return convertToInt(cells.getValue("Average"));	    
	}
	
	
	
	public int getNumStudents() {
		return numStudents;
	}

	public int getNumAssignments() {
		return numAssignments;
	}

	public int getNumProjects() {
		return numProjects;
	}

	public Student getStudentByID(String id) {
		Student student = null;
		for (String key: students.keySet()) {
			student = students.get(key);
			if (student.getGtid().equals(id)) {
				return student;
			}
		}		
		return null;
	}

	public Student getStudentByName(String name) {
		return students.get(name);
	}

	public HashSet<Student> getStudents() {
		HashSet<Student> studentSet = new HashSet<Student>();
		for (String key: students.keySet()) {
			studentSet.add(students.get(key));
		}
		return studentSet;
	}

	
	/**
	 * Convert the attendance in string to an integer
	 * @param string
	 * @return
	 */
	public static int convertToInt (String string) {
		string = string.replace("%", "").trim();
		int index = string.indexOf(".");
		if (index != -1) {
			string = string.substring(0,index);
		}
		return Integer.parseInt(string);
	}
}