package workgroup.handler.message;

import java.util.Date;

import workgroup.group.GroupManager;
import workgroup.group.Member;
import workgroup.message.Message;

public abstract class MessageHandler {

	public MessageHandler() {
	}

	protected void sendToAll(GroupManager groupManager, Member member, Message message) {
		groupManager.sendToAll(member, message);
	}

	protected void sendToOthers(GroupManager groupManager, Member member, Message message) {
		groupManager.sendToOthers(member, message);
	}
	
	public void receive(GroupManager groupManager, Member member, Message message) {
		message.setReciveTime(new Date());
		process(groupManager, member, message);
	}
	
	abstract public void process(GroupManager groupManager, Member member, Message message);
}
