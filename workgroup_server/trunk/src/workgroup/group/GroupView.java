package workgroup.group;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GroupView implements Serializable{
	
	private static final long serialVersionUID = 16L;

	protected String groupName;
	protected Date timestamp;
	protected List<User> userList;
	
	public GroupView() {
	}
	
	public GroupView(String groupName, List<User> userList, Date timestamp) {
		this.groupName = groupName;
		this.timestamp = timestamp;
		this.userList = userList;
	}
	
	public List<User> getuserList() {
		return userList;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getGroupName() {
		return groupName;
	}

}
