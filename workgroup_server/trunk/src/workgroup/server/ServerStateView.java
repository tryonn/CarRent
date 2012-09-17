package workgroup.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import workgroup.group.GroupView;

public class ServerStateView implements Serializable {

	private static final long serialVersionUID = 16L;
	
	// Lista de grupos
	private String[] groupList;

	// Gerenciadores de grupo
	private Map<String, GroupView> groupViews = new HashMap<String, GroupView>();

	public ServerStateView(ServerModel serverModel) {
		this.groupList = serverModel.getGroupList();
		for (String group : groupList) {
			this.groupViews.put(group, serverModel.getGroupModel(group).getGroupView());
		}
	}
	
	public String[] getGroupList() {
		return this.groupList;
	}
	
	public GroupView getGroupView(String groupName){
		return groupViews.get(groupName);
	}
}
