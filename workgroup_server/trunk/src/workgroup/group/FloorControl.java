package workgroup.group;

public interface FloorControl {

	public void requestFloor(String nameSpace, Member owner);	
	public void leaveFloor(String nameSpace, Member owner);
	public boolean isOwner(String nameSpace, Member member);
	public Member getOwner(String nameSpace);

}
