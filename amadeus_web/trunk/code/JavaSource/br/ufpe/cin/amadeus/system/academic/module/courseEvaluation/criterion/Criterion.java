package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion;

import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.question.Question;

public class Criterion {
	
	private int id;
	private String name;
	private List<Question> questions;
	private int constantCriterion;
	
	public Criterion(){
		
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getConstantCriterion() {
		return constantCriterion;
	}

	public void setConstantCriterion(int constantCriterion) {
		this.constantCriterion = constantCriterion;
	}
	
	public boolean equals(Object criterion){
		boolean result = false;
		if(criterion instanceof Criterion){
			Criterion temp = (Criterion) criterion;
			if (this.id==temp.getId()){
				result = true;
			}
		}
		return result;
	}
	
}
