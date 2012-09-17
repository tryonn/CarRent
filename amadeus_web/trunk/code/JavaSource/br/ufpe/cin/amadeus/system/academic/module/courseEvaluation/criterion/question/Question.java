package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.question;

import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;

public class Question {
	
	private int id;
	private String description;
	private int order;
	private Criterion criterion;
	
	public Question(){
		
	}
	
	public Criterion getCriterion() {
		return criterion;
	}
	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
	
}
