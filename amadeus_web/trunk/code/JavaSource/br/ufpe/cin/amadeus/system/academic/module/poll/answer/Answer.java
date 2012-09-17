package br.ufpe.cin.amadeus.system.academic.module.poll.answer;

import java.util.Date;

import br.ufpe.cin.amadeus.system.academic.module.poll.Poll;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.Choice;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Answer {
	
	private int id;
	private Person person;
	private Poll poll;
	private Choice choice;
	private Date answerDate;
	
	public Answer(){
		
	}
	
	public Choice getChoice() {
		return choice;
	}

	public void setChoice(Choice choice) {
		this.choice = choice;
	}

	public Date getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(Date answer_date) {
		this.answerDate = answer_date;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object answer){
		boolean result = false;
		if(answer instanceof Answer){
			Answer a = (Answer) answer;
			if(this.id == a.getId())
				result = true;
		}
		return result;
	}
}
