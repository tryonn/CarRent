package br.ufpe.cin.amadeus.system.human_resources.user_request.request_evaluation;

import java.io.Serializable;
import java.util.Date;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest;

public class RequestEvaluation implements Serializable {
	
	private UserRequest request;
	private Person person;
	private Date evaluationDate;
	
	public Date getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public UserRequest getRequest() {
		return request;
	}
	public void setRequest(UserRequest request) {
		this.request = request;
	}
	
	

}
