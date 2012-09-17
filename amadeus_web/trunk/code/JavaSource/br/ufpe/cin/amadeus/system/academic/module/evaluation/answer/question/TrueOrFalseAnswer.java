package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.sentence.SentenceAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.sentence.Sentence;

public class TrueOrFalseAnswer extends ObjectiveQuestionAnswer {
	
	
	private List<SentenceAnswer> sentences;
	
	public TrueOrFalseAnswer(){
		this.sentences = new ArrayList<SentenceAnswer>();
	}
	
	public List<SentenceAnswer> getSentences() {
		return sentences;
	}
	public void setSentences(List<SentenceAnswer> sentences) {
		this.sentences = sentences;
	}
	
	public void addSentences(SentenceAnswer sentence){
		this.sentences.add(sentence);
	}
	
	

}
