package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.commentary.Commentary;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.evaluationAnswer.EvaluationAnswer;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class CourseEvaluation {
	
	private int id;
	private Date date;
	private List<Criterion> criterions;
	private Set<Person> students;
	private List<Commentary> commentaries;
	private Set<EvaluationAnswer> evalAnswers;
	
	public CourseEvaluation(){
		
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Person> getStudents() {
		return students;
	}

	public void setStudents(Set<Person> students) {
		this.students = students;
	}

	public List<Commentary> getCommentaries() {
		return commentaries;
	}

	public void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}

	public Set<EvaluationAnswer> getEvalAnswers() {
		return evalAnswers;
	}

	public void setEvalAnswers(Set<EvaluationAnswer> evalAnswers) {
		this.evalAnswers = evalAnswers;
	}
	
	public void insertEvaluationAnswer(EvaluationAnswer evalAnswer){
		if(this.evalAnswers == null){
			this.evalAnswers = new HashSet<EvaluationAnswer>();
		}
		this.evalAnswers.add(evalAnswer);
	}
	
	public void insertStudent(Person student){
		if(this.students == null){
			this.students = new HashSet<Person>();
		}
		this.students.add(student);
	}
	
	public void insertCommentary(Commentary com){
		if(this.commentaries == null){
			this.commentaries = new ArrayList<Commentary>();
		}
		this.commentaries.add(com);
	}
	
	public boolean containsPerson(Person p){
		boolean result = false;
		if(students != null){
			result = students.contains(p);
		}
		return result;
	}
	
	public int getStudentsAmount(){
		int result = 0;
		if(this.students != null){
			result = students.size();
		} 
		return result;
	}

}
