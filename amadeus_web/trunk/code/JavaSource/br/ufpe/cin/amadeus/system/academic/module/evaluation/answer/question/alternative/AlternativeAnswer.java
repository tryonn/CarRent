package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.alternative;

public class AlternativeAnswer {
	
	private boolean answered;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAnswered(boolean answered){
		this.answered = answered;
	}
	
	public boolean isAnswered(){
		return answered;
	}
	

}
