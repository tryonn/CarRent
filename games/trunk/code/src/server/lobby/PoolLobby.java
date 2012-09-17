package server.lobby;

import java.util.Iterator;
import java.util.Vector;

import server.Protocol;

public class PoolLobby {
	
	private static PoolLobby instance = null;
	private Vector lobbys;
	
	private PoolLobby(){
		lobbys = new Vector();
	}
	
	public static PoolLobby getInstance(){
		if(instance == null){
			instance = new PoolLobby();
		}
		return instance;
	}

	public void userToYourLobby(Protocol protocol, int idDisciplina) {		
		boolean exists = false;

		Iterator i = lobbys.iterator();
		while(i.hasNext()){
			Lobby lb = (Lobby)i.next();
			
			if(lb.getId() == idDisciplina){
				lb.addUser(protocol);
				exists = true;
				break;
			}
		}		
		
		if (!exists){
			Lobby newLb = new Lobby(idDisciplina);
			lobbys.addElement(newLb);
			newLb.addUser(protocol);
		}

	}

	public void userExitGame(int id_room, String Login, boolean timeout){
		Iterator i = lobbys.iterator();
		while(i.hasNext()){
			Lobby lb = (Lobby)i.next();			
			if ( lb.existeIDRoomAndExitRoom(id_room,Login,timeout) )
				break;
			
		}		
	}

	public boolean existeIdRoom(int id_room){
		boolean res = false;
		Iterator i = lobbys.iterator();
		while(i.hasNext()){
			Lobby lb = (Lobby)i.next();
			
			if ( lb.existeIDRoom(id_room) ){
				res = true;
				break;
			}			
		}
		return res;
	}

}