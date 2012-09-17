package workgroup.group;

import java.util.LinkedList;

import workgroup.message.ControlMessage;

public class SimpleFloorControl implements FloorControl {
	
	private Member owner = null;
	private LinkedList<Member> floorRequests = new LinkedList<Member>();
	private GroupManager manager;
	
	public SimpleFloorControl(GroupManager manager) {
		this.manager = manager;
	}

	public void requestFloor(String nameSpace, Member member) {
		if (this.owner == null) {
			setOwner(member);
		} else {
			if (! floorRequests.contains(member)) {
				floorRequests.add(member);
				member.getUser().setControlRequested(true);
			}
		}
	}
	
	public void leaveFloor(String nameSpace, Member member) {
		 if (this.owner == member) {
		 	member.getUser().leaveControl();
			if (floorRequests.size() > 0) {
				setOwner(floorRequests.removeFirst());
			} else {
				this.owner = null;
			}
		} else {
			floorRequests.remove(member);
		}
	}
	
	private void setOwner(Member member) {
		this.owner = member;
		member.getUser().getControl();
		ControlMessage message = new ControlMessage(ControlMessage.TAKE_FLOOR_CONTROL);
		member.send(message);
	}

	public boolean isOwner(String nameSpace, Member member) {
		return member == getOwner(nameSpace);
	}
	
	public Member getOwner(String nameSpace) {
		return owner;
	}
}