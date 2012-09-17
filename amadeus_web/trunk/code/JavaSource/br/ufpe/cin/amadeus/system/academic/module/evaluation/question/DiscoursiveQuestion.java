package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

public class DiscoursiveQuestion {
	
	private int id;
	private String question;
	private char level;
	private int posInEvaluation;
	
	

	public DiscoursiveQuestion(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getLevel() {
		return level;
	}

	public void setLevel(char level) {
		this.level = level;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public int getPosInEvaluation() {
		return posInEvaluation;
	}

	public void setPosInEvaluation(int posInEvaluation) {
		this.posInEvaluation = posInEvaluation;
	}

}
