package br.ufpe.cin.amadeus.system.academic.module.poll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.poll.answer.Answer;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.Choice;

public class Poll {
	
	private int id;
	private int votes;
	private String name;
	private String question;
	private Date creationDate;
	private Date finishDate;
	private List<Choice> choices = new ArrayList<Choice>();
	private List<Answer> answers = new ArrayList<Answer>();
	
	public Poll()
	{
		
	}
	
	public void increaseVotes(){
		this.votes++;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public boolean equals(Object poll){
		boolean result = false;
		if(poll instanceof Poll){
			Poll p = (Poll) poll;
			if(this.id == p.getId())
				result = true;
		}
		return result;
	}
	
}
