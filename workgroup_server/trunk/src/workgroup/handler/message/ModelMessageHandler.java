package workgroup.handler.message;

import workgroup.group.GroupManager;
import workgroup.group.Member;
import workgroup.message.Message;
import workgroup.message.ModelMessage;

public class ModelMessageHandler extends MessageHandler {

	public ModelMessageHandler() {
	}

	public void process(GroupManager groupManager, Member member, Message message) {
		if (message instanceof ModelMessage) {
			if (groupManager.isControlOwner(null, member)) {
				sendToOthers(groupManager, member, message);
			}
		} else {
			groupManager.getLogger().debug("<ModelMessageHandler> Message recebida não é ModelMessage");
		}
	}

}
