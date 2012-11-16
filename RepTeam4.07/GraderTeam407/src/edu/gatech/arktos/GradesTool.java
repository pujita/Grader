package edu.gatech.arktos;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.google.gdata.util.ServiceException;

public class GradesTool {
	
	private static JComboBoxThemed<Student> _comboBoxStudent;
	private static JComboBoxThemed _comboBoxProject;
	private static JComboBoxThemed _comboBoxGroup;
	
	public static void main(String [] args) throws IOException, ServiceException {
		/*char userChar;
		
		try {
			do {
				printData();
				printContinue();
				
				userChar = readUserDecision();
				
			}
			while(isContinuePrinting(userChar)); // loop until the user decides to stop printing
		}
		catch (Throwable ex) {
		}*/
		
		final JFrame frame = new JFrame("GradesTool");
		frame.setSize(new Dimension(640, 480));
		frame.setResizable(false);
		frame.setLayout(null);
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				
			}
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				System.exit(0);
			}
		});
		
		Font font = null;
		try {
			String pathSeparator = System.getProperty("file.separator");
			font = ResourcesDispatcher.getFont("resources" + pathSeparator + "project.ttf", Font.TRUETYPE_FONT);
			font = font.deriveFont(15.0f);
			
		} catch (ResourceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		_comboBoxStudent = new JComboBoxThemed();
		frame.add(_comboBoxStudent);
		_comboBoxStudent.setLocation(new Point(15, 35));
		_comboBoxStudent.setSize(new Dimension(200, 35));
		_comboBoxStudent.setFont(font);
		
		_comboBoxProject = new JComboBoxThemed();
		frame.add(_comboBoxProject);
		_comboBoxProject.setLocation(new Point(350, 35));
		_comboBoxProject.setSize(new Dimension(65, 35));
		_comboBoxProject.setFont(font);
		
		_comboBoxGroup = new JComboBoxThemed();
		frame.add(_comboBoxGroup);
		_comboBoxGroup.setLocation(new Point(430, 35));
		_comboBoxGroup.setSize(new Dimension(80, 35));
		_comboBoxGroup.setFont(font);
		
		LabelExtended labelStudent = new LabelExtended("Student:");
		labelStudent.setFont(font);
		labelStudent.setLocation(15, 12);
		frame.add(labelStudent);
		
		LabelExtended labelProjectNumber = new LabelExtended("Project #:");
		labelProjectNumber.setFont(font);
		labelProjectNumber.setLocation(350, 12);
		frame.add(labelProjectNumber);
		
		LabelExtended labelProjectGroup = new LabelExtended("Group #:");
		labelProjectGroup.setFont(font);
		labelProjectGroup.setLocation(430, 12);
		frame.add(labelProjectGroup);
		
		fillData();
		frame.setVisible(true);
	}
	
	private static void fillData() throws IOException, ServiceException {
		Session sess = new Session();
		sess.login(Constants.USERNAME, Constants.PASSWORD);
		
		GradesDB gdb = sess.getDBByName(Constants.GRADES_DB);
		HashSet<Student> stds = gdb.getStudents();
		
		for (Student std: stds) {
			_comboBoxStudent.addItem(std);
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
