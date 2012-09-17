package br.ufpe.cin.amadeus.dwr.academic.bean;

import java.text.SimpleDateFormat;

import br.ufpe.cin.amadeus.system.academic.module.forum.Message;

public class MessageJSBean {

	private int messageId;
	private int forumId;
	private int moduleId;
	
	private String body;
	private String author;
	private String dateString;
	
	public MessageJSBean(int messageId, int forumId, int moduleId, Message message) {
		this.messageId	= messageId;
		this.forumId 	= forumId;
		this.moduleId	= moduleId;
		
		body = message.getBody();
		author = message.getAuthor().getName();
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String time = timeFormat.format(message.getHour());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String date = dateFormat.format(message.getDate());
		dateString = time + " de " + date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
}
