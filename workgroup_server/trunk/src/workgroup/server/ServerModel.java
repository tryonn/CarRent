package workgroup.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import workgroup.group.GroupManager;
import workgroup.group.GroupModel;
import workgroup.handler.message.ChatMessageHandler;
import workgroup.handler.message.ControlMessageHandler;
import workgroup.handler.message.MessageHandler;
import workgroup.handler.message.ModelMessageHandler;
import workgroup.message.ChatMessage;
import workgroup.message.ControlMessage;
import workgroup.message.ModelMessage;
import workgroup.server.services.Service;

public class ServerModel {

	// Singleton Instance
	private static ServerModel serverModel = null;
	
	// Referencia ao servidor
	private PlattusServer server;
	
	// Gerenciadores de grupo
	private Map<String, GroupManager> groupModels = new HashMap<String, GroupManager>();
	
	// Mapa de Tipos de Mensagens e Handles
	private Map<String, MessageHandler> handlerMap = new HashMap<String, MessageHandler>();
	
	// Lista de serviços
	private List<Service> services = new ArrayList<Service>(); 

	// Lista de listeners
	List<ServerModelListener> listeners = new ArrayList<ServerModelListener>();

	protected ServerModel() {
		handlerMap.put(ChatMessage.class.getName(), new ChatMessageHandler());
		handlerMap.put(ModelMessage.class.getName(), new ModelMessageHandler());
		handlerMap.put(ControlMessage.class.getName(), new ControlMessageHandler());
	}
	
	public void addListener(ServerModelListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(ServerModelListener listener) {
		this.listeners.remove(listener);
	}
	
	private void notifyListeners(ServerStateView view) {
		for (ServerModelListener listener : listeners) {
			listener.ServerModelChange(view);
		}
	}
	
	public GroupManager getGroupManager(String grupo) {
		return groupModels.get(grupo);
	}
	
	public GroupManager addGroup(String grupo) {
		GroupManager manager = new GroupManager(grupo);
		groupModels.put(grupo, manager);
		this.modelChanged();
		return manager;
	}
	
	public static ServerModel getInstance() {
		if (serverModel == null) {
			serverModel = new ServerModel();
		}
		return serverModel;
	}

	public void setServer(PlattusServer server) {
		this.server = server;
	}
	
	public Logger getLogger() {
		return server.getLogger();
	}

	public MessageHandler getHandler(String name) {
		return handlerMap.get(name);
	}
	
	public String[] getGroupList() {
		int len = groupModels.keySet().size();
		String[] list = new String[len];
		groupModels.keySet().toArray(list);
		Arrays.sort(list);
		return list;
	}
	
	public GroupModel getGroupModel(String groupName){
		GroupManager manager = this.groupModels.get(groupName);
		return manager.getGroupModel();
	}

	public void modelChanged() {
		// Modelo alterado
		notifyListeners(new ServerStateView(this));
	}

	public Map<String, MessageHandler> getMessageHandlers() {
		return handlerMap;
	}

	public void addService(Service service) {
		services.add(service);
	}
	
	public Service[] getServiceList() {
		Service[] list = new Service[0]; 
		return services.toArray(list);
	}
}