package br.ufpe.cin.amadeus.system.academic.module.forum;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Forum {
	
	private int id;
	private String name;
	private String description;
	private Date date;
	private Time time;
	private List<Message> messages = new ArrayList<Message>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
