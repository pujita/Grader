package edu.gatech.arktos;

import java.io.IOException;
import java.util.HashSet;

import com.google.gdata.util.ServiceException;

public class GradesTool {
	public static void main(String [] args) {
		char userChar;
		
		try {
			do {
				printData();
				printContinue();
				
				userChar = readUserDecision();
				
			}
			while(isContinuePrinting(userChar)); // loop until the user decides to stop printing
		}
		catch (Throwable ex) {
		}
	}
	
	private static void printData() throws IOException, ServiceException {
		Session sess = new Session();
		sess.login(Constants.USERNAME, Constants.PASSWORD);
		
		GradesDB gdb = sess.getDBByName(Constants.GRADES_DB);
		HashSet<Student> stds = gdb.getStudents();
		
		
		System.out.println("*** CS 6300 Class Information ***");
		System.out.println("Number of students: " + gdb.getNumStudents());
		System.out.println("Students information:");
		
		int i = 1;
		for (Student std: stds) {
			System.out.println("  " + i + ") " + std.getName() + ", GTID: " + std.getGtid() + ", E-mail: " + std.getEmail() + ", Attendance: " + std.getAttendance() + "%");
			++i;
		}
		
		System.out.println("Class information:");
		System.out.println("  Number of assignments: " + gdb.getNumAssignments());
		System.out.println("  Number of projects: " + gdb.getNumProjects());
	}
	
	private static void printContinue() {
		System.out.println("");
		System.out.println("Do you want to continue? [Y/n]: ");
	}
	
	private static char readUserDecision() throws IOException {
		char c = (char)System.in.read();
		while ((char)System.in.read() != '\n'); //discard other chars until newline
		
		return c;
	}
	
	private static boolean isContinuePrinting(char c) {
		char[] arrayChar = new char[] { c };
		
		return !(new String(arrayChar).toLowerCase()).equals("n");
	}
}
