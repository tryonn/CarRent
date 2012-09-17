package workgroup.group;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import workgroup.handler.message.MessageHandler;
import workgroup.message.ControlMessage;
import workgroup.message.Message;
import workgroup.server.ServerModel;

public class GroupManager {

	private static Logger log = Logger.getLogger(GroupManager.class);
	private GroupModel groupModel;

	public GroupManager(String groupName) {
		groupModel = new GroupModel(groupName, this);
	}
	
	public void addCliente(String login, Socket clientSocket) throws IOException {
		System.out.println("IP: " + clientSocket.getInetAddress().getHostAddress());
		System.out.println("Porta: " + clientSocket.getPort());
		
		Member member = new Member(this, new User(login), clientSocket);
		addMember(member);
	}

	protected void addMember(Member newMember) {
		groupModel.addMember(newMember);
	}
	
	protected void remove(Member member) {
		groupModel.remove(member);
	}
	
	public void receive(Member member, Message message) {
		log.debug("Recebida messagem de [" + member + "]");
		MessageHandler handler = (MessageHandler) ServerModel.getInstance().getHandler(message.getClass().getName());
		handler.receive(this, member, message);
	}

	public void sendTo(Message message, Member target) {
		target.send(message);
	}

	/**
	 * @param message
	 */
	public void sendToAll(Message message) {
		List<Member> members = groupModel.getMemberList();
		Iterator<Member> it = members.iterator();
		while (it.hasNext()) {
			Member member = it.next();
			member.send(message);
		}
	}

	/**
	 * @param message
	 */
	public void sendToAll(Member source, Message message) {
		List<Member> members = groupModel.getMemberList();
		Iterator<Member> it = members.iterator();
		while (it.hasNext()) {
			Member member = it.next();
			member.send(source, message);
		}
	}

	/**
	 * @param member
	 * @param message
	 */
	public void sendToOthers(Member source, Message message) {
		List<Member> members = groupModel.getMemberList();
		Iterator<Member> it = members.iterator();
		while (it.hasNext()) {
			Member member = it.next();
			if (member != source) {
				member.send(message);
			}
		}
	}
	
	public void sendGroupView() {
		ControlMessage message = new ControlMessage(ControlMessage.VIEW_CHANGE, groupModel.getGroupView());
		sendToAll(message);
	}

	public void requestControl(Member member) {
		groupModel.requestControl(member);
	}

	public void leaveControl(Member member) {
		groupModel.leaveControl(member);
	}

	public boolean isControlOwner(String nameSpace, Member member) {
		return groupModel.isControlOwner(nameSpace, member);
	}

	public Logger getLogger() {
		return log;
	}

	/**
	 * @return Returns the groupModel.
	 */
	public GroupModel getGroupModel() {
		return groupModel;
	}
}
