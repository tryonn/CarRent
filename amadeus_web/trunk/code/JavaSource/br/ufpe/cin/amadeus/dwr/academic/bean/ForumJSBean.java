package br.ufpe.cin.amadeus.dwr.academic.bean;

import java.text.SimpleDateFormat;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.forum.Forum;
import br.ufpe.cin.amadeus.system.academic.module.forum.Message;

public class ForumJSBean {

	private int forumId;
	private int moduleId;
	
	private String name;
	private String desc;
	private String dateString;
	
	private MessageJSBean[] messages;
	
	public ForumJSBean(int forumId, int moduleId, Forum forum, boolean loadMessages) {
		this.forumId  = forumId;
		this.moduleId = moduleId;
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String time = timeFormat.format(forum.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String date = dateFormat.format(forum.getDate());

		name = forum.getName();
		desc = forum.getDescription();
		dateString = time + " de " + date;
		
		if (loadMessages) {
			List<Message> list = forum.getMessages();
			messages = new MessageJSBean[list.size()];
			for (int i = 0; i < list.size(); i++) {
				messages[i] = new MessageJSBean(i, forumId, moduleId, list
						.get(i));
			}
		}
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public MessageJSBean[] getMessages() {
		return messages;
	}

	public void setMessages(MessageJSBean[] messages) {
		this.messages = messages;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
