package br.ufpe.cin.amadeus.system.academic.module.evaluation.question.alternative;

public class Alternative {
	
	private int id;
	private String description;
	private boolean correct;
	private char order;
	
	public Alternative(){
		
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
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

	public char getOrder() {
		return order;
	}

	public void setOrder(char order) {
		this.order = order;
	}
	
	

}
