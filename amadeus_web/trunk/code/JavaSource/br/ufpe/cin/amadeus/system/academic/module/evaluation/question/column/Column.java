package br.ufpe.cin.amadeus.system.academic.module.evaluation.question.column;

public class Column {
	
	private int id;
	private String term;
	private char order;
	private String sentence;
	
	public Column(){
		
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

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	

}
