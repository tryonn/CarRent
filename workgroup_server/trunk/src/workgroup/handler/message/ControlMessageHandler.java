package workgroup.handler.message;

import workgroup.group.GroupManager;
import workgroup.group.Member;
import workgroup.message.ControlMessage;
import workgroup.message.Message;

public class ControlMessageHandler extends MessageHandler {

	public ControlMessageHandler() {
	}

	public void process(GroupManager groupManager, Member member, Message message) {
		if (message instanceof ControlMessage) {
			processMessage(groupManager, member, (ControlMessage) message);
		} else {
			groupManager.getLogger().debug("<ModelMessageHandler> Message recebida não é ModelMessage");
		}
	}

	private void processMessage(GroupManager groupManager, Member member, ControlMessage message) {
		if (message.getType() == ControlMessage.REQUEST_FLOOR_CONTROL) {
			groupManager.requestControl(member);
		} else if (message.getType() == ControlMessage.LEAVE_FLOOR_CONTROL) {
			groupManager.leaveControl(member);
		}
	}

}
