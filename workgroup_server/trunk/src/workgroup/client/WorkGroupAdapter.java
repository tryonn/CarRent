package workgroup.client;

import workgroup.group.GroupView;
import workgroup.message.Message;

public class WorkGroupAdapter implements WorkGroupListener {

	public void onMessage(Message message) {}

	public void onClose() {}

	public void changeGroupView(GroupView view) {}

	public void onChangeGroupView(GroupView view) {}

	public void onTakeControl() {}

}
