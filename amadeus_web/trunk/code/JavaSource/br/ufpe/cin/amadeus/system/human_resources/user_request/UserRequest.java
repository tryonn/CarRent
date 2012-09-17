package br.ufpe.cin.amadeus.system.human_resources.user_request;

import java.util.Date;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;


public class UserRequest {

	private int idUserRequest;
	private Person person;
	private Course course;
	private String status;
	private Date userRequestDate; 
	private String interest;
	private boolean teachingRequest;
	
	public UserRequest(){
		
	}

	public Date getUserRequestDate() {
		return userRequestDate;
	}

	public void setUserRequestDate(Date userRequestDate) {
		this.userRequestDate = userRequestDate;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course idCurso) {
		this.course = idCurso;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person idPerson) {
		this.person = idPerson;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public int getIdUserRequest() {
		return idUserRequest;
	}

	public void setIdUserRequest(int idRequest) {
		this.idUserRequest = idRequest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isTeachingRequest() {
		return teachingRequest;
	}

	public void setTeachingRequest(boolean teachingRequest) {
		this.teachingRequest = teachingRequest;
	}
	
}
