package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question;

import java.util.Date;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.access_control.user.User;

public abstract class ObjectiveQuestionAnswer {
	
	private int id;
	private int posInEvaluation;
	private ObjectiveQuestion objectiveQuestion;
	
	

	public ObjectiveQuestion getObjectiveQuestion() {
		return objectiveQuestion;
	}
	public void setObjectiveQuestion(ObjectiveQuestion objectiveQuestion) {
		this.objectiveQuestion = objectiveQuestion;
	}
	public int getPosInEvaluation() {
		return posInEvaluation;
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
	
	
	

}
