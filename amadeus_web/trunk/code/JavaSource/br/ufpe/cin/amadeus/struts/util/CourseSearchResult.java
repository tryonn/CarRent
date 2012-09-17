package br.ufpe.cin.amadeus.struts.util;

public class CourseSearchResult {

	private int courseId;
	private int professorId;
	private int noStudents;
	private String courseName;
	private String professorName;
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getNoStudents() {
		return noStudents;
	}
	public void setNoStudents(int noStudents) {
		this.noStudents = noStudents;
	}
	public int getProfessorId() {
		return professorId;
	}
	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
}
