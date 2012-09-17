package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;



/**
 * Classe que o aluno responde uma avaliacao
 * @author amadeus
 *
 */
public class DiscoursiveQuestionAnswer {
	
	private int id;
	private String answer;
	private int posInEvaluation;
	private DiscoursiveQuestion discoursiveQuestion;

	
	public int getPosInEvaluation() {
		return posInEvaluation;
	}

	public DiscoursiveQuestion getDiscoursiveQuestion() {
		return discoursiveQuestion;
	}

	public void setDiscoursiveQuestion(DiscoursiveQuestion discoursiveQuestion) {
		this.discoursiveQuestion = discoursiveQuestion;
	}

	public void setPosInEvaluation(int posInEvaluation) {
		this.posInEvaluation = posInEvaluation;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
