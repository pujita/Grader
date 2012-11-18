package edu.gatech.arktos;

public class Assignment {

	private String name;
	private String desc;
	private String grade;
	private Integer number;
	
	public Assignment(String name, String desc, Integer number, String grade) {
		this.name = name;
		this.desc = desc;
		this.grade = grade;
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String toString() {
		return getNumber() + ". " + getDesc();
	}
	
}
