package workgroup.group;

import java.io.Serializable;
import java.util.List;

public class GroupStateView implements Serializable {

	private static final long serialVersionUID = 12L;
	
	private GroupModel groupModel;
	
	public GroupStateView(GroupModel groupModel) {
		this.groupModel = groupModel;
	}

	public String getGroupName() {
		return groupModel.getGroupName();
	}
	
	public List<User> getUserList()	{
		return groupModel.getUserList();
	}
}
