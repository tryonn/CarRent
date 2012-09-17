 package br.ufpe.cin.amadeus.system.academic.keyword;

public class Keyword implements Comparable{
	
	private int id;
	private String name;
	private int popularity;
	private int group;
	
	public Keyword(){
		popularity = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object keyword){
		if(!(keyword instanceof Keyword)){
			return false;
		}
		Keyword key = (Keyword) keyword;
		return this.name.equalsIgnoreCase(key.getName());
	}

	public int hashCode() {
		return id;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	public void increasePopularity(){
		this.popularity++;
	}

	public void decreasePopularity() {
		this.popularity--;
	}

	public int compareTo(Object keyword){
		if(!(keyword instanceof Keyword))
			return -10;
		Keyword anotherKeyword = (Keyword) keyword;
		return this.name.compareToIgnoreCase(anotherKeyword.getName());
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
}
