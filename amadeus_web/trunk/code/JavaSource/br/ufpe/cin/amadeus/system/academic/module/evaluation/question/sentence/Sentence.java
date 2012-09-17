package br.ufpe.cin.amadeus.system.academic.module.evaluation.question.sentence;

public class Sentence {
	
	private int id;
	private String description;
	private boolean value;
	private char order;
	private int position;
	
	public Sentence(){
		
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

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
	public int getPosition(){
		return this.position;
	}
	
	public void setPosition(int position){
		this.position = position;
	}
	
	

}
