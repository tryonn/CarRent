package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.sentence.Sentence;

public class TrueOrFalseQuestion extends ObjectiveQuestion{
	
	private List<Sentence> sentences;
	
	public TrueOrFalseQuestion(){
		this.sentences = new ArrayList<Sentence>();
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	public void addSentence(Sentence sentence){
		this.sentences.add(sentence);
	}

}
