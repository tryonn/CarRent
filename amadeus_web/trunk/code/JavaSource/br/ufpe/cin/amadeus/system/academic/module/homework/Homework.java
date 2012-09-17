package br.ufpe.cin.amadeus.system.academic.module.homework;

import java.util.Date;

public class Homework {
	
	private int id;
	private String name;
	private String description;
	private Date date;
	private Date deadLine;
	private boolean allowed;
	
	public Homework(){
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean permits) {
		this.allowed = permits;
	}
	
	public boolean equals(Object homework){
		boolean result = false;
		if(homework instanceof Homework){
			Homework temp = (Homework) homework;
			if (this.id==temp.getId()){
				result = true;
			}
		}
		return result;
	}

}
