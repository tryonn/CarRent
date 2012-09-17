package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.alternative.AlternativeAnswer;



public class MultipleChoiceAnswer extends ObjectiveQuestionAnswer{
	
	
	private List<AlternativeAnswer> alternatives;
	

	
	public MultipleChoiceAnswer(){
		this.alternatives = new ArrayList<AlternativeAnswer>();
	}
	
	public void setAlternativeAnswer(List<AlternativeAnswer> alt){
		this.alternatives = alt;
	}
	
	public List<AlternativeAnswer> getAlternatives(){
		return alternatives;
	}
	
	public void addAlternatives(AlternativeAnswer alternative){
		this.alternatives.add(alternative);
	}

	public void setAlternatives(List<AlternativeAnswer> alternatives) {
		this.alternatives = alternatives;
	}
	
	

}
