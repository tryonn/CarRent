package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.blank.Blank;

public class BlanksFillingQuestion extends ObjectiveQuestion{
	
	private String text;
	private List<Blank> blanks;

	public BlanksFillingQuestion(){
		this.blanks = new ArrayList<Blank>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Blank> getBlanks() {
		return blanks;
	}

	public void setBlanks(List<Blank> blanks) {
		this.blanks = blanks;
	}
	
	public void addBlanks(Blank blank){
		this.blanks.add(blank);
	}
	
	
}
