package workgroup.client;

import workgroup.group.GroupView;
import workgroup.message.Message;

public interface WorkGroupListener  {

	public void onMessage(Message message);
	
	public void onClose();

	public void onChangeGroupView(GroupView view);
	
	public void onTakeControl();
}
