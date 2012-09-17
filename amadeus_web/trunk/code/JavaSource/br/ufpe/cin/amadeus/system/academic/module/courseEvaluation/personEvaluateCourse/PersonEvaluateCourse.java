package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.personEvaluateCourse;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class PersonEvaluateCourse {
	
	private Person person;
	private Course course;
	
	public PersonEvaluateCourse(){
		
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}
