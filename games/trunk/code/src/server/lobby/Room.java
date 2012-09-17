package server.lobby;

import java.util.Iterator;
import java.util.Vector;

import bd.PartidasDTO;

import server.Protocol;

public class Room {

	private int idRoom;
	private Game game;
	private String criador;
	private Vector users;
	private static int nextIdRoom ;
	
	public Room(Game gm, String cr){
		this.idRoom = getNextIdRoom();
		users = new Vector();
		setGame(gm);
		setCriador(cr);
	}
	
	public String getNameGame(){
		return getGame().getNameGame();
	}
	
	public int getIdRoom(){
		return this.idRoom;
	}
	
	public void addUser(Protocol p){
		if (totalUser() < getGame().getMaxUsers())
	//		if ( !users.contains(p) )
				users.addElement((Protocol)p);
	}
	
	public void removeUser(Protocol p){
		users.remove((Protocol)p);		
	}
	
	public Protocol removeUser(String nm_user){
		
		Iterator i = users.iterator();
		Protocol p = null;
		while (i.hasNext()){
			p = (Protocol) i.next();
			if ( nm_user.equals(p.getNome()) ){
				break;
			}
		}
		if ( p!= null )
			removeUser(p);
		
		return p;
	}	
	
	public Vector allUser(){
		return users;		
	}

	public int getSize() {
		return getGame().getMaxUsers();
	}

	private static int getNextIdRoom() {
		if (nextIdRoom != 0){
		System.out.println( nextIdRoom );
			++nextIdRoom;
		}else{
			PartidasDTO partida = new PartidasDTO();
			int id_bd = partida.getUltimoID();
			nextIdRoom = ++id_bd;
		}
		
		return nextIdRoom;
	}
	
	public int totalUser(){
		return users.size();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
	
	public StringBuffer getComentario(){
		return getGame().getComentario();
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}

	public String getCriador() {
		return criador;
	}

	public StringBuffer getUrl(){
		return getGame().getUrl();
	}
	
	public int getFlag(){ //0 para jogo avulso e 1 para jogo padronizao
		return getGame().getFlag();
	}

}
