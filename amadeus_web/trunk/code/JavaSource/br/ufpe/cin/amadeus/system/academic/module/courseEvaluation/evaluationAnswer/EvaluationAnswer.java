package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.evaluationAnswer;

import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswer;

public class EvaluationAnswer {
	
	private int id;
	private Criterion criterion;
	private char frequencyType;
	private CriterionAnswer criterionAnswer;
	
	public EvaluationAnswer(){
		
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	public CriterionAnswer getCriterionAnswer() {
		return criterionAnswer;
	}

	public void setCriterionAnswer(CriterionAnswer criterionAnswer) {
		this.criterionAnswer = criterionAnswer;
	}

	public char getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(char frequencyType) {
		this.frequencyType = frequencyType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
