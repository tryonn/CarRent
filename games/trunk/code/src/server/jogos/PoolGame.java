package server.jogos;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import server.Protocol;
import server.lobby.PoolLobby;
import server.management.IthreadAmadeus;
import server.management.PoolThread;

public class PoolGame implements IthreadAmadeus{

	private static PoolGame instance = null;
	private static PoolThread instancePoolThread = null;
	private Vector jogoPadrao;
	
	
	public PoolGame() {
		jogoPadrao = new Vector();
	}

	public static PoolGame getInstance(){
		if(instance == null){
			instance = new PoolGame();
		}
		return instance;
	}

	public void work() throws IOException {

		Iterator i = jogoPadrao.iterator();
		while (i.hasNext() ){
			GamePadrao gPT = (GamePadrao) i.next();
			gPT.work();
		}
		
	}

	public void add(IthreadAmadeus hl) {
		if ( instancePoolThread == null){
			instancePoolThread = PoolThread.getInstance();
			instancePoolThread.newIthreadAmadeus( (IthreadAmadeus) hl);
		}
		
	}

	public void gameToYourMatch(Protocol p, int id_room){
		if (instancePoolThread == null)
			add(this);

		PoolLobby pollLobby = PoolLobby.getInstance();
//TODO Procedimento abaixo tera q estar disponpivel em HTTP
		if ( pollLobby.existeIdRoom(id_room) ){ //Verifica se a partida é válida		
			try {
				Iterator i = jogoPadrao.iterator();
				boolean jaExiste = false;
				while (i.hasNext()){
					GamePadrao gPT = (GamePadrao) i.next();
	
					if (gPT.getId() == id_room){
						gPT.addProtocol(p);
						jaExiste = true;
					}			
				}				
				if (!jaExiste){
					GamePadrao newGPT = new GamePadrao();
					newGPT.setId(id_room);
					newGPT.addProtocol(p);
					jogoPadrao.add(newGPT);
				}				
			} catch (NumberFormatException  e) {
				System.out.println("Erro no parseInt !" + e.getMessage());
			}
		}else{
			System.out.print("Esse id_room " + id_room + " é inválido, com isso: ");
			//TODO Retornar a msg dizendo q a sala não foi criada, pq o ID_ROOm é inválido
			p.exit();
		}	
	}

	public void fimPartida(Protocol p, int id_room){
		
	}
}
