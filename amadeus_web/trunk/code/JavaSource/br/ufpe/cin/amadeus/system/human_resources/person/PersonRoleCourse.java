package br.ufpe.cin.amadeus.system.human_resources.person;

import java.io.Serializable;
import java.util.Date;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.role.Role;



public class PersonRoleCourse implements Serializable{
	
	private Person person;
	private Role role;
	private Course course;
	private Date date;
	
	public PersonRoleCourse(){
		
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
