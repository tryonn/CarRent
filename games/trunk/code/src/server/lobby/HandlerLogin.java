package server.lobby;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import server.Constantes;
import server.Protocol;
import server.Util;
import server.management.IthreadAmadeus;
import server.management.PoolThread;

public class HandlerLogin implements IthreadAmadeus{

	private static HandlerLogin instance = null;
	private static PoolThread instancePoolThread = null;
	private Vector protocols;
	
	private HandlerLogin(){	
		protocols = new Vector();		
	}
	
	
	public static HandlerLogin getInstance(){
		if(instance == null){
			instance = new HandlerLogin();	
			Thread t = new PoolThread();
			t.start();			
		}
		return instance;
	}

	//Adiciona a instancio do HandlerLogin na fila do PoolThread
	public void addProtocol(Protocol protocol) {
		protocols.addElement(protocol);
		add( (IthreadAmadeus) instance);
	}
	
	//Transfere o protocolo ao PoolLobby e depois exclui o protocolo de sua lista
	public void work() throws IOException{
		
		Iterator i =protocols.iterator();
		
		while(i.hasNext()){
			Protocol temp = (Protocol)i.next();
			if(temp.isReady()){
				directLobby(temp);

				i.remove(); 				
			}
		}
	}

	private void directLobby(Protocol protocol) throws IOException {
	try{
		StringBuffer msg = new StringBuffer("");
		msg.append( protocol.getCommand() );
		if ( msg.substring(0,5).equals( Integer.toString(Constantes.LOBBY) +
				Integer.toString(Constantes.CLIENTE_SERVIDOR)+Constantes.L_CS_LoginUser) ){
			
			protocol.setNome( (Util.getParametro(1,msg)).toString() );
			PoolLobby pollLobby = PoolLobby.getInstance();
			pollLobby.userToYourLobby(protocol, Integer.parseInt((Util.getParametro(2,msg)).toString()));
			
		}	
	} catch (Exception e) {
		System.out.println("Ocorreu um erro na entrega do cliente ao LOBBY");
		
		//e.printStackTrace();
	}
	}

	public void add(IthreadAmadeus hl){
		if ( instancePoolThread == null){
			instancePoolThread = PoolThread.getInstance();
			instancePoolThread.newIthreadAmadeus( (IthreadAmadeus) hl);
		}
		
	}


}
