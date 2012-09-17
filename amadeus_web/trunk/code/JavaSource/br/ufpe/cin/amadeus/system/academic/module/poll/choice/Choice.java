package br.ufpe.cin.amadeus.system.academic.module.poll.choice;


public class Choice {
	
	private int id;
	private String option;
	private String alternative;
	private int votes;
	private double percentage;
	
	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Choice(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int seq) {
		this.votes = seq;
	}

	public String getAlternative() {
		return alternative;
	}

	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}
	
	public void increaseVotes(){
		this.votes++;
	}
	
}
