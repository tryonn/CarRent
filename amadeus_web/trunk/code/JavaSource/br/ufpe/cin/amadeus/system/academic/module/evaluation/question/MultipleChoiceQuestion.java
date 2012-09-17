/**
 * 
 */
package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.alternative.Alternative;

/**
 * @author Yuri Cesar
 *
 */
public class MultipleChoiceQuestion extends ObjectiveQuestion{
	
	private List<Alternative> alternatives;
	
	public MultipleChoiceQuestion(){
		this.alternatives = new ArrayList<Alternative>();
	}

	public List<Alternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}
	
	public void addAlternatives(Alternative alternative){
		this.alternatives.add(alternative);
	}

}
