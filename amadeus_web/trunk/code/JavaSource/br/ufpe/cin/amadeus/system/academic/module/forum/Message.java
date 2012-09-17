package br.ufpe.cin.amadeus.system.academic.module.forum;

import java.sql.Time;
import java.util.Date;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class Message {

	private int id;
	private String title;
	private String body;
	private Date date;
	private Time hour;
	private Person author;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getHour() {
		return hour;
	}
	public void setHour(Time hour) {
		this.hour = hour;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Person getAuthor() {
		return author;
	}
	public void setAuthor(Person author) {
		this.author = author;
	}
		
}
