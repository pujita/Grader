package edu.gatech.arktos;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.google.gdata.util.ServiceException;

public class GradesTool {
	
	private static JComboBoxThemed<Student> _comboBoxStudent;
	private static JComboBoxThemed _comboBoxProject;
	private static JComboBoxThemed _comboBoxGroup;
	private static JComboBoxThemed<Assignment> _comboBoxAssignments;
	private static JComboBoxThemed<Assignment> _comboBoxProjects;
	
	private static LabelExtended labelGTIDvalue;
	private static LabelExtended labelEmailValue;
	private static LabelExtended labelAttendanceValue;
	private static LabelExtended labelAssignmentsAverageGrade;
	private static LabelExtended labelAssignmentGradeValue;
	private static LabelExtended labelAssignmentAverageGrade;
	
	private static LabelExtended labelProjectsAverageGrade;
	private static LabelExtended labelProjectGradeValue;
	private static LabelExtended labelProjectContributionGrade;
	private static LabelExtended labelProjectAverageGrade;
	
	private static GradesDB gdb;
	
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
		}
		catch (ResourceException e1) {
			e1.printStackTrace();
		}
		
		_comboBoxStudent = new JComboBoxThemed();
		frame.add(_comboBoxStudent);
		_comboBoxStudent.setLocation(new Point(15, 35));
		_comboBoxStudent.setSize(new Dimension(200, 35));
		_comboBoxStudent.setFont(font);
		_comboBoxStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Student s = _comboBoxStudent.getSelectedItem();
				
				if (s != null) {
					labelGTIDvalue.setText(s.getGtid());
					labelEmailValue.setText(s.getEmail());
					labelAttendanceValue.setText(String.valueOf(s.getAttendance()));
					
					labelAssignmentsAverageGrade.setText("average " + String.valueOf(s.getAverageAssignmentGrade()));
					
					_comboBoxAssignments.removeAllItems();
					ArrayList<Assignment> assigns = s.getAssignments();
					for (Assignment assign: assigns) {
						_comboBoxAssignments.addItem(assign);
					}
				}
			}
		});
		
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
		
		LabelExtended labelGTID = new LabelExtended("GTID:", font);
		labelGTID.setLocation(25, 90);
		frame.add(labelGTID);
		labelGTIDvalue = new LabelExtended("<Undefined>", font);
		labelGTIDvalue.setBold(true);
		labelGTIDvalue.setSize(185, labelGTIDvalue.getSize().height);
		labelGTIDvalue.setLocation(115, 90);
		labelGTIDvalue.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelGTIDvalue);
		
		LabelExtended labelEmail = new LabelExtended("e-mail:", font);
		labelEmail.setLocation(25, 110);
		frame.add(labelEmail);
		labelEmailValue = new LabelExtended("<Undefined>", font);
		labelEmailValue.setBold(true);
		labelEmailValue.setSize(185, labelEmailValue.getSize().height);
		labelEmailValue.setLocation(115, 110);
		labelEmailValue.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelEmailValue);
		
		LabelExtended labelAttendance = new LabelExtended("Attendance:", font);
		labelAttendance.setLocation(25, 130);
		frame.add(labelAttendance);
		labelAttendanceValue = new LabelExtended("<Undefined>", font);
		labelAttendanceValue.setBold(true);
		labelAttendanceValue.setSize(185, labelAttendanceValue.getSize().height);
		labelAttendanceValue.setLocation(115, 130);
		labelAttendanceValue.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelAttendanceValue);
		
		
		Map<TextAttribute,Object> map = new Hashtable<TextAttribute,Object>();
		map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font fontUnderline = font.deriveFont(map).deriveFont(18.0f);
		LabelExtended labelGrades = new LabelExtended("Grades", fontUnderline);
		labelGrades.setLocation(15, 170);
		labelGrades.setBold(true);
		frame.add(labelGrades);
		
		LabelExtended labelAssignment = new LabelExtended("Assignments:", font);
		labelAssignment.setLocation(25, 200);
		frame.add(labelAssignment);
		labelAssignmentsAverageGrade = new LabelExtended("<Undefined>", font);
		labelAssignmentsAverageGrade.setBold(true);
		labelAssignmentsAverageGrade.setSize(185, labelAssignmentsAverageGrade.getSize().height);
		labelAssignmentsAverageGrade.setLocation(115, 200);
		labelAssignmentsAverageGrade.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelAssignmentsAverageGrade);
		
		_comboBoxAssignments = new JComboBoxThemed();
		frame.add(_comboBoxAssignments);
		_comboBoxAssignments.setLocation(new Point(25, 230));
		_comboBoxAssignments.setSize(new Dimension(275, 29));
		_comboBoxAssignments.setFont(font);
		_comboBoxAssignments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Assignment a = _comboBoxAssignments.getSelectedItem();
				
				if (a != null) {
					labelAssignmentGradeValue.setText(a.getGrade());
					
					int avgGrade = gdb.getAssignmentGrade(a.getNumber());
					labelAssignmentAverageGrade.setText(String.valueOf(avgGrade));
				}
			}
		});
		
		LabelExtended labelAssignmentGrade = new LabelExtended("Student grade:", font);
		labelAssignmentGrade.setLocation(55, 260);
		frame.add(labelAssignmentGrade);
		labelAssignmentGradeValue = new LabelExtended("<Undefined>", font);
		labelAssignmentGradeValue.setBold(true);
		labelAssignmentGradeValue.setSize(185, labelAssignmentGradeValue.getSize().height);
		labelAssignmentGradeValue.setLocation(115, 260);
		labelAssignmentGradeValue.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelAssignmentGradeValue);
		
		LabelExtended labelAssignmentAverage = new LabelExtended("Average class grade:", font);
		labelAssignmentAverage.setLocation(55, 280);
		frame.add(labelAssignmentAverage);
		labelAssignmentAverageGrade = new LabelExtended("<Undefined>", font);
		labelAssignmentAverageGrade.setBold(true);
		labelAssignmentAverageGrade.setSize(185, labelAssignmentAverageGrade.getSize().height);
		labelAssignmentAverageGrade.setLocation(115, 280);
		labelAssignmentAverageGrade.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelAssignmentAverageGrade);
		
		
		LabelExtended labelProject = new LabelExtended("Projects:", font);
		labelProject.setLocation(25, 315);
		frame.add(labelProject);
		labelProjectAverageGrade = new LabelExtended("<Undefined>", font);
		labelProjectAverageGrade.setBold(true);
		labelProjectAverageGrade.setSize(185, labelProjectAverageGrade.getSize().height);
		labelProjectAverageGrade.setLocation(115, 315);
		labelProjectAverageGrade.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelProjectAverageGrade);
		
		_comboBoxProjects = new JComboBoxThemed();
		frame.add(_comboBoxProjects);
		_comboBoxProjects.setLocation(new Point(25, 345));
		_comboBoxProjects.setSize(new Dimension(275, 29));
		_comboBoxProjects.setFont(font);
		_comboBoxProjects.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		LabelExtended labelProjectGrade = new LabelExtended("Team grade:", font);
		labelProjectGrade.setLocation(55, 375);
		frame.add(labelProjectGrade);
		labelProjectsAverageGrade = new LabelExtended("<Undefined>", font);
		labelProjectsAverageGrade.setBold(true);
		labelProjectsAverageGrade.setSize(185, labelProjectsAverageGrade.getSize().height);
		labelProjectsAverageGrade.setLocation(115, 375);
		labelProjectsAverageGrade.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelProjectsAverageGrade);
		
		LabelExtended labelProjectAverage = new LabelExtended("Average teams grade:", font);
		labelProjectAverage.setLocation(55, 395);
		frame.add(labelProjectAverage);
		labelProjectAverageGrade = new LabelExtended("<Undefined>", font);
		labelProjectAverageGrade.setBold(true);
		labelProjectAverageGrade.setSize(185, labelProjectAverageGrade.getSize().height);
		labelProjectAverageGrade.setLocation(115, 395);
		labelProjectAverageGrade.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelProjectAverageGrade);
		
		LabelExtended labelProjectContribution = new LabelExtended("Contribution grade received:", font);
		labelProjectContribution.setLocation(55, 415);
		frame.add(labelProjectContribution);
		labelProjectContributionGrade = new LabelExtended("<Undefined>", font);
		labelProjectContributionGrade.setBold(true);
		labelProjectContributionGrade.setSize(185, labelProjectContributionGrade.getSize().height);
		labelProjectContributionGrade.setLocation(115, 415);
		labelProjectContributionGrade.setHorizontalAlignment(LabelExtended.ALIGN_RIGHT);
		frame.add(labelProjectContributionGrade);
		
		
		fillData();
		frame.setVisible(true);
	}
	
	private static void fillData() throws IOException, ServiceException {
		Session sess = new Session();
		sess.login(Constants.USERNAME, Constants.PASSWORD);
		
		gdb = sess.getDBByName(Constants.GRADES_DB);
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
