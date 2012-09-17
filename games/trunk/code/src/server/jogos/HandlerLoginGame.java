package server.jogos;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import server.Constantes;
import server.Protocol;
import server.Util;
import server.management.IthreadAmadeus;
import server.management.PoolThread;

public class HandlerLoginGame implements IthreadAmadeus{

	private static HandlerLoginGame instance = null;
	private static PoolThread instancePoolThread = null;
	private Vector protocolos; 
	
	
	public HandlerLoginGame() {
		protocolos = new Vector();		
	}

	public static HandlerLoginGame getInstance(){
		if(instance == null){
			instance = new HandlerLoginGame();			
		}
		return instance;
	}

	public void addProtocol(Protocol protocol) {
		protocolos.addElement(protocol);
		if ( instancePoolThread == null)
			add( (IthreadAmadeus) instance);
		
	}	
	
	public void work() throws IOException {
		Iterator i =protocolos.iterator();
		while(i.hasNext()){
			Protocol temp = (Protocol)i.next();
			if(temp.isReady()){
				
				StringBuffer command = temp.getCommand();
				//Identifica se a msg inicial é de login.
				if ( command.substring(0,5).equals((""+Constantes.JOGO + Constantes.CLIENTE_SERVIDOR + Constantes.J_JS_UsuarioEntrou)) ){
					
					try {
						
						int id_jogo = Integer.parseInt( Util.getParametro(1,command).toString() );
						String login = Util.getParametro(2,command).toString();
						temp.setNome( login );
						directyGame(temp,id_jogo);
						
					} catch (NumberFormatException e) {
						System.out.println("Id_Jogo inválido, msg = " + command);
						e.printStackTrace();
					}				}
				i.remove();
			}else{
				if (temp.isDied(Constantes.JOGO) == true){
					temp.exit();						
					i.remove();
				}
			}
		}
		
	}

	public void add(IthreadAmadeus hl) {
		if ( instancePoolThread == null){
			instancePoolThread = PoolThread.getInstance();
			instancePoolThread.newIthreadAmadeus( (IthreadAmadeus) hl);
		}		
	}

	private void directyGame(Protocol p, int Id_Jogo){
		PoolGame instancePoolGame = PoolGame.getInstance();
		instancePoolGame.gameToYourMatch(p,Id_Jogo);
	}

}
