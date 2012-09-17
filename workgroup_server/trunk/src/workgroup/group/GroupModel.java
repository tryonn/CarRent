package workgroup.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import workgroup.server.ServerModel;

public class GroupModel {
	
	private String groupName;
	private GroupManager groupManager;
	private Vector<Member> members;
	private FloorControl floorControl;
	
	
	public GroupModel(String groupName, GroupManager manager) {
		this.groupName = groupName;
		this.groupManager = manager;
		this.members = new Vector<Member>();
		this.floorControl = new SimpleFloorControl(manager);
	}

	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}
	
	/**
	 * @return Returns the GroupManager.
	 */
	public GroupManager getGroupManager() {
		return groupManager;
	}

	public List<Member> getMemberList() {
		List<Member> memberList = new ArrayList<Member>(members);
		return memberList;
	}
	
	public List<User> getUserList()	{
		List<User> userList = new ArrayList<User>(members.size());
		for (int i = 0; i < members.size(); i++) {
			Member member = members.get(i);
			userList.add(member.getUser());
		}
		return (userList);
	}
	
	/**
	 * @return
	 */
	public GroupView getGroupView() {
		return ( new GroupView(this.getGroupName(), this.getUserList(), new Date()) );
	}

	public void addMember(Member newMember) {
		synchronized (members) {
			if(newMember != null && !members.contains(newMember))
			{
				members.addElement(newMember);
				if (size() == 1) {
					floorControl.requestFloor(null, newMember);			
				}
			}
		}
		this.modelChanged();
	}
	
	public void remove(Member oldMember) {
		synchronized (members) {
			if(oldMember != null)
			{
				members.remove(oldMember);
				floorControl.leaveFloor(null, oldMember);
				if (floorControl.getOwner(null) == null && size() > 0) {
					floorControl.requestFloor(null, members.get(0));
				}
			}
		}
		this.modelChanged();
	}

	public void clear() {
		synchronized (members) {
			members.removeAllElements();
		}
		this.modelChanged();
	}

	public int size() {
		synchronized (members) {
			return members.size();
		}
	}
	
	public void requestControl(Member member) {
		floorControl.requestFloor(null, member);
		this.modelChanged();
	}

	public void leaveControl(Member member) {
		floorControl.leaveFloor(null, member);
		if (floorControl.getOwner(null) == null) {
			requestControl(members.firstElement());
		}
		this.modelChanged();
	}

	public boolean isControlOwner(String nameSpace, Member member) {
		return floorControl.isOwner(nameSpace, member);
	}
	
	protected void modelChanged() { // Modelo alterado
		groupManager.sendGroupView();
		ServerModel.getInstance().modelChanged();
	}
}
