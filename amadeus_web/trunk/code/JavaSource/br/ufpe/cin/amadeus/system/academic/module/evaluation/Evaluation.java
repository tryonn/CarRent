package br.ufpe.cin.amadeus.system.academic.module.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Evaluation {
	
	private int id;
	private String name;
	private Date beginning;
	private Date end;
	private double weight;
	private double maxScore;
	private int sqmodule;
	private Person author;// olhar bd
	//trocar por list, so teste
	private List<DiscoursiveQuestion> discoursiveQuestions;
	private List<ObjectiveQuestion> objectiveQuestions;
	
	
	
	public Evaluation(){
		this.discoursiveQuestions = new ArrayList<DiscoursiveQuestion>();
		this.objectiveQuestions = new ArrayList<ObjectiveQuestion>();
	}

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}
	
	public List<ObjectiveQuestion> getObjectiveQuestions() {
		return objectiveQuestions;
	}

	public void setObjectiveQuestions(List<ObjectiveQuestion> questions) {
		this.objectiveQuestions = questions;
	}
	
	public List<DiscoursiveQuestion> getDiscoursiveQuestions(){
		return discoursiveQuestions;
	}
	
	public void setDiscoursiveQuestions(List<DiscoursiveQuestion> questions){
		this.discoursiveQuestions = questions;
	}
	
	public void addDiscoursiveQuestion(DiscoursiveQuestion dq){
		this.discoursiveQuestions.add(dq);
	}
	
	public void addObjectiveQuestion(ObjectiveQuestion dq){
		this.objectiveQuestions.add(dq);
	}

	public int getSqmodule() {
		return sqmodule;
	}

	public void setSqmodule(int sqmodule) {
		this.sqmodule = sqmodule;
	}
}
