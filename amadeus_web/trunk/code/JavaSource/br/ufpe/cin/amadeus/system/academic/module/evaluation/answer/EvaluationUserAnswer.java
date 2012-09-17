package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.ObjectiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.access_control.user.User;

public class EvaluationUserAnswer {
	
	private int id;
	private Evaluation evaluation;
	private User user;
	private Date dateAnswer;
	private List<DiscoursiveQuestionAnswer> discoursiveAnswers;
	private List<ObjectiveQuestionAnswer> objectiveAnswers;
	
	
	public EvaluationUserAnswer(){
		this.discoursiveAnswers = new ArrayList<DiscoursiveQuestionAnswer>();
		this.objectiveAnswers = new ArrayList<ObjectiveQuestionAnswer>();
	}
	
	public List<DiscoursiveQuestionAnswer> getDiscoursiveAnswers() {
		return discoursiveAnswers;
	}
	
	public void setDiscoursiveAnswers(
			List<DiscoursiveQuestionAnswer> discoursiveAnswers) {
		this.discoursiveAnswers = discoursiveAnswers;
	}
	
	public Evaluation getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	
	public List<ObjectiveQuestionAnswer> getObjectiveAnswers() {
		return objectiveAnswers;
	}
	
	public void setObjectiveAnswers(List<ObjectiveQuestionAnswer> objectiveAnswers) {
		this.objectiveAnswers = objectiveAnswers;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Date getDateAnswer() {
		return dateAnswer;
	}

	public void setDateAnswer(Date dateAnswer) {
		this.dateAnswer = dateAnswer;
	}
	
	public void addDiscoursiveQuestion(DiscoursiveQuestionAnswer dq){
		this.discoursiveAnswers.add(dq);
	}
	
	public void addObjectiveQuestion(ObjectiveQuestionAnswer dq){
		this.objectiveAnswers.add(dq);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
