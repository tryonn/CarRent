package server.lobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import bd.JogosBO;
import bd.JogosDTO;
import bd.ParticipantesBO;
import bd.PartidasBO;
import bd.PartidasDTO;
import server.Constantes;
import server.Protocol;
import server.Util;
import server.management.IthreadAmadeus;
import server.management.PoolThread;

public class Lobby implements IthreadAmadeus{
	
	private static PoolThread instancePoolThread = null;
	private int id;
	private Vector<Protocol> protocols;
	private Vector<Room> salas;
	private Vector<Game> games;
	
	
	public Lobby(int id){
		this.id = id;
		protocols = new Vector<Protocol>();
		salas = new Vector<Room>();
		games = new Vector<Game>();
		add(this);
		setGames();
				
	}
	
	public int getId() {
		return this.id;
	}

	private boolean existeJogo(JogosBO jogoBD, Vector v){//Ira retorna 1: Jogo OK, 2:Atualizar Jogo, 3:Não Existe Jogo
		
		int id_jogo = jogoBD.getId_jogo();
		Iterator i = v.iterator();
		boolean res = false;
		while (i.hasNext()){
			Game j = (Game) i.next();
			if (id_jogo == j.getId_game()) {// Caso ja tenha o jogo ADD, verificar se ele foi atualizado.
				
				if ( j.getAtualizacao().compareTo(jogoBD.getAtualizacao()) != 0 ){//Caso a Data de Atualização seja diferente atualiza os dados
					j.setAtualizacao( jogoBD.getAtualizacao() );
					j.setComentario( jogoBD.getComentario() );
					j.setFlag( jogoBD.getFlag() );
					j.setUrl( jogoBD.getUrl() );
				}
				
				res = true;
				break;
			}				
		}					
		return res;
	}
	
	private boolean existeJogoBD(int id_jogo, Vector v){
		
		Iterator i = v.iterator();
		boolean res = false;
		while (i.hasNext()){
			JogosBO j = (JogosBO) i.next();
			if (id_jogo == j.getId_jogo()) {
				
				res = true;
				break;
			}				
		}					
		return res;
	}	
	
	private void setGames(){
		JogosDTO jogosDentroBD= new JogosDTO();		
		Vector jogosDoBD = jogosDentroBD.getAll(getId());
		
		Vector jogosJaAdd = getGames();

		if (jogosJaAdd.size() != 0){//Verifica se existe algum jogo ja adicionado
			
			Iterator i = jogosDoBD.iterator();
			while (i.hasNext()){
				JogosBO j = (JogosBO) i.next();				
				if ( !existeJogo(j, jogosJaAdd) ){//Descobre se existe um novo jogo do BD q não foi add
					Game g = new Game(j.getId_jogo(), j.getNm_jogo(), j.getMaximo(), j.getUrl(), j.getFlag(), j.getComentario(), j.getAtualizacao());
					games.addElement(g);
				}
			}
			
			i = jogosJaAdd.iterator();
			while (i.hasNext()){//Procurar algum jogo que foi excluido do BD, caso TRUE excluir.
				Game g = (Game) i.next();				
				if ( !existeJogoBD(g.getId_game(), jogosDoBD) ){					
					games.remove(g);
				}
			}
			
		}else{//caso não exista nenhum jogo adicionado, adicione todos.
			Iterator i = jogosDoBD.iterator();
			while (i.hasNext()){
				JogosBO j = (JogosBO) i.next();
				Game g = new Game(j.getId_jogo(), j.getNm_jogo(), j.getMaximo(), j.getUrl(), j.getFlag(), j.getComentario(), j.getAtualizacao());
				games.addElement(g);
			}
		}

	}
	
	private Vector getGames(){		
		return this.games;
	}
	
	public void addUser(Protocol p) {
		Iterator i = new ArrayList(protocols).iterator();
		boolean loginExistente = false; 
		while (i.hasNext()){
			String nomeP = ((Protocol) i.next()).getNome();
			if (nomeP.equals(p.getNome())){//SE FOR VERDADEIRO LOGIN JA EXISTE NÃO FAZER NADA
				loginExistente = true;
				break;
			}
		}
		
		if (!loginExistente){
			protocols.addElement(p);

			//Envia para quem se conectou a lista dos usuario
			p.sendMsg( getUserLobby() );
			p.sendMsg( getNameUsersSalas() );

			//Envia para todos da sala menos para quem entrou o new user
			StringBuffer res = new StringBuffer("");
			res = getLoginUser(p);		

			sendToAll(false, p,res);
		}else{
			System.out.println("Login " + p.getNome() +" ja existe, sera desconectado!");
			jaFezLogin(p);
			p.exit();
		}
	}

	private void jaFezLogin(Protocol p){
		StringBuffer res = new StringBuffer("");
		
		res.append( Constantes.LOBBY );
		res.append( Constantes.SERVIDOR_CLIENTE );
		res.append( Constantes.L_SC_JAFEZLOGIN );
		res.append( "0001" );
		res.append( Util.sizeParameter(5, p.getNome() ) );		
		res.append( p.getNome() );
		p.sendMsg(res);
	}
	
	private StringBuffer getUserLobby(){	
		StringBuffer res = new StringBuffer("");

		res.append( Constantes.LOBBY );
		res.append( Constantes.SERVIDOR_CLIENTE );
		res.append( Constantes.L_SC_ListarUsers );
		//O + 1 é pq um dos parametros e o total de user logados
		res.append(  ("000"+Integer.toString(1+2*protocols.size())).substring(("000"+Integer.toString(1+2*protocols.size())).length()-4,("000"+Integer.toString(1+2*protocols.size())).length()) );
		res.append( Util.sizeParameter(5, Integer.toString(protocols.size()) ) );		
		res.append( Integer.toString(protocols.size()) );		
		Iterator i =protocols.iterator();
		while(i.hasNext()){
			Protocol p = (Protocol)i.next();
			res.append( Util.sizeParameter(5,p.getNome()) );
			res.append( p.getNome() );	
			res.append( Util.sizeParameter(5,Integer.toString(p.getStatus())) );
			res.append( Integer.toString(p.getStatus()) );	
		}
		return res;
	}

	private StringBuffer getNameUsersSalas(){
		StringBuffer res = new StringBuffer("");
		res.append(Constantes.LOBBY);
		res.append(Constantes.SERVIDOR_CLIENTE);
		res.append(Constantes.L_SC_ListarSalas);
		

		StringBuffer aux = new StringBuffer("");
		int totalParametros = 0;
		
		Iterator i = salas.iterator();
		while (i.hasNext()){
			Room rm = (Room)i.next();
			aux.append( Util.sizeParameter(5,Integer.toString( rm.getIdRoom() ) ) );
			aux.append( Integer.toString( rm.getIdRoom() ) );
			totalParametros++;
			aux.append( Util.sizeParameter(5, rm.getNameGame() ) );
			aux.append( rm.getNameGame() );
			totalParametros++;			
			aux.append( Util.sizeParameter(5, rm.getUrl().toString() ) );
			aux.append( rm.getUrl() );
			totalParametros++;
			aux.append( Util.sizeParameter(5,  Integer.toString( rm.getFlag() ) ) );
			aux.append( rm.getFlag() );
			totalParametros++;			
			aux.append( Util.sizeParameter(5, rm.getComentario().toString() ) );
			aux.append( rm.getComentario() );
			totalParametros++;
			Vector user = rm.allUser();
			if ( !user.isEmpty() ){
				aux.append( Util.sizeParameter(5, Integer.toString( user.size() ) ) );
				aux.append( Integer.toString( user.size() ) );
				totalParametros++;			
			
				Iterator iuser = user.iterator();
				int inc =0;
				while ( iuser.hasNext() ){
					Protocol p = (Protocol)iuser.next(); 
					aux.append( Util.sizeParameter(5, p.getNome() ) );
					aux.append( p.getNome() );
					totalParametros++;
					inc++;
				}
			}
			aux.append("00001");
			totalParametros++;
			if (rm.getSize() != user.size())
				aux.append("0");
			else
				aux.append("1");
		}
		//Quantidade de Parâmetros
		res.append( ("000"+Integer.toString(totalParametros)).substring( ("000"+Integer.toString(totalParametros)).length()-4 ,("000"+Integer.toString(totalParametros)).length() ) );
		res.append(aux);
		return res;
	}
	
	private StringBuffer getLoginUser(Protocol p){		
		StringBuffer res = new StringBuffer("");
		
		res.append( Constantes.LOBBY );
		res.append( Constantes.SERVIDOR_CLIENTE );
		res.append( Constantes.L_SC_LoginUser );
		res.append( "0002" );
		res.append( Util.sizeParameter(5, p.getNome() ) );		
		res.append( p.getNome() );
		res.append( "00001" );		
		res.append( "0" );
		
		return res;
	}
	
	public void work() throws IOException{				
		Iterator i =protocols.iterator();
		try{
			while(i.hasNext()){
				Protocol p = (Protocol)i.next();
				
				if (p.isDied(Constantes.LOBBY) == true){
					exitUserALLRoom(p.getNome(),p);	
					sendToAll(true, p, doTimeOut(p) );
					p.exit();						
					i.remove();
	
				}else			
					if(p.isReady()){						
						StringBuffer send = new StringBuffer("");
						StringBuffer command = new StringBuffer("");
						command.append(p.getCommand());
						if (command.substring(0,1).equals( (Integer.toString(Constantes.LOBBY) ) ) ) {
							if ( !command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_TIMEOUT)) ){
								if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_EnviaMsg)) ){
									send.append( Constantes.LOBBY );
									send.append( Constantes.SERVIDOR_CLIENTE );
									send.append( Constantes.L_SC_NovaMsg );
									send.append( "0002" );
									send.append( Util.sizeParameter(5,Util.getParametro(1,command).toString()) );
									send.append( Util.getParametro(1,command) );
									send.append( Util.sizeParameter(5,Util.getParametro(2,command).toString()) );
									send.append( Util.getParametro(2,command) );
									sendToAll(true, p, send );
								}else
									if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_CHATPESSOAL)) ){
										chatPessoal(command);								
									}else
										if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_ListarGames)) ){
											p.sendMsg( getNameGames() );
										}else
											if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_CriaSala)) ){
												newSala(command,p);
											}else
												if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_EntrarSala)) ){
													entrarSala(command,p);
												}else
													if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_SairSala)) ){
														//exitUserRoom(Util.getParametro(1,command).toString(),p);
														System.out.println("\n\n AXO Q NÃO ERA PARA EXITIR MSG DE SAIR DA SALA QUE VEM DO LOBBY");
													}else
														if ( command.substring(0,5).equals((""+Constantes.LOBBY + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_LogoutUser)) ){
															send.append( Constantes.LOBBY );
															send.append( Constantes.SERVIDOR_CLIENTE );
															send.append( Constantes.L_SC_LogoffUser );
															send.append( "0001" );
															send.append( Util.sizeParameter(5,Util.getParametro(1,command).toString()) );
															send.append( Util.getParametro(1,command) );
															exitUserALLRoom(Util.getParametro(1,command).toString(),p);											
															p.exit();						
															i.remove();
															sendToAll(true, p, send );
														}
							}
						}						
					}//do if(p.isReady()){
			}
		}catch (Exception e) {
			System.out.println("Erro na Leitura do Comando Recebido. " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void add(IthreadAmadeus hl){
		
		if ( instancePoolThread == null){
			instancePoolThread = PoolThread.getInstance();
		}
		instancePoolThread.newIthreadAmadeus( (IthreadAmadeus) hl);
		
	}

	private void sendToAll(boolean all, Protocol p, StringBuffer msg){
		Iterator i =protocols.iterator();  

		while(i.hasNext()){			
			Protocol temp = (Protocol)i.next();			
			if ( all )
				temp.sendMsg(msg);
			else
				if ( (temp.getNome() != p.getNome()) )
					temp.sendMsg(msg);
		}
	}
	
	private void newSala(StringBuffer msg, Protocol p){
		Iterator i = games.iterator();
		String nameGame = Util.getParametro(2,msg).toString();
		Game g = null;
		while (i.hasNext()){
			g = (Game)i.next();
			if (  g.getNameGame().equals(nameGame)  ){
				break;
			}
		}
		
		String ownerRoom = Util.getParametro(1,msg).toString();
		if ( g.getFlag() == 1 ){//Somente cria sala se for jogo avulso!

			Room newSala = new Room(g,ownerRoom);
			newSala.addUser(p);
			salas.addElement(newSala);
			
			StringBuffer res = new StringBuffer("");
			res.append(Constantes.LOBBY);
			res.append(Constantes.SERVIDOR_CLIENTE);
			res.append(Constantes.L_SC_NovaSala);		
			res.append("0007");		
			res.append( Util.sizeParameter(5, Integer.toString(newSala.getIdRoom()) ) );
			res.append( Integer.toString(newSala.getIdRoom()) );
			res.append( Util.sizeParameter(5, newSala.getNameGame() ) );
			res.append( newSala.getNameGame() );
			res.append( Util.sizeParameter(5, newSala.getUrl().toString() ) );
			res.append( newSala.getUrl() );
			res.append( Util.sizeParameter(5, Integer.toString( newSala.getFlag() ) ) );
			res.append( newSala.getFlag() );
			res.append( Util.sizeParameter(5, newSala.getComentario().toString() ) );
			res.append( newSala.getComentario() );
			res.append( Util.sizeParameter(5, newSala.getCriador() ));
			res.append( newSala.getCriador() );
			res.append( "00001" );
			if (newSala.getSize() != newSala.totalUser())
				res.append("0");
			else
				res.append("1");		
			
			sendToAll(true, p, res );
			statusUser(p);

//	INCLUSÃO NO BD			
			JogosBO jogo = new JogosBO();
			jogo.setId_jogo(g.getId_game());
			jogo.setFlag(g.getFlag());//Flag Do jogo para saber se é avulso ou nao
			
			ParticipantesBO participantes = new ParticipantesBO();
			participantes.setId_partida(newSala.getIdRoom());
			participantes.setId_usuario(ownerRoom);

			PartidasBO partida = new PartidasBO();
			partida.setId_partida(newSala.getIdRoom());
			partida.setJogo(jogo);
			
			PartidasDTO newPart = new PartidasDTO();
			newPart.novaPartida(partida,participantes);
				
		}else{
			PartidasDTO newPart = new PartidasDTO();
			newPart.novaPartidaAvulsa(g.getId_game(), ownerRoom);
		}
	}

	private void entrarSala(StringBuffer msg, Protocol p){
		int id = Integer.parseInt( Util.getParametro(1,msg).toString() );
		Iterator i = salas.iterator();
		while (i.hasNext()){
			Room r = (Room)i.next();
			if (  r.getIdRoom() == id  ){
				if (r.getSize() > r.totalUser() ){
					r.addUser( (Protocol) p );
					//p.setStatus(1);
					
					StringBuffer send = new StringBuffer();
					send.append( Constantes.LOBBY );
					send.append( Constantes.SERVIDOR_CLIENTE );
					send.append( Constantes.L_SC_UserEntrouSala );
					send.append( "0002" );
					send.append( Util.sizeParameter(5,  Integer.toString(r.getIdRoom()) ) );					
					send.append( r.getIdRoom() );
					send.append( Util.sizeParameter(5,Util.getParametro(2,msg).toString()) );
					send.append( Util.getParametro(2,msg) );
					sendToAll(true, p, send );

					statusSala(r);
					statusUser(p);
					
					ParticipantesBO participantes = new ParticipantesBO();
					participantes.setId_partida(r.getIdRoom());
					participantes.setId_usuario(Util.getParametro(2,msg).toString());
					PartidasDTO novo = new PartidasDTO();
					novo.novoParticipante(participantes);
				}
				break;
			}
		}
	}
	
	private StringBuffer getNameGames(){
				
		setGames();
		StringBuffer res = new StringBuffer();
		res.append( Constantes.LOBBY );
		res.append( Constantes.SERVIDOR_CLIENTE );
		res.append( Constantes.L_SC_ListarGames );

		//TOTAL Parâmetros		
		String total = ("000"+Integer.toString( games.size()*4 )); //Descubro o total de jogos.
		res.append( total.substring( total.length() -4 , total.length() ) ); //pego as 4 ultimas casas		
		
		Iterator i = games.iterator();
		while (i.hasNext()){
			Game gm = (Game) i.next();
			//Nome do Jogo
			res.append( Util.sizeParameter(5,gm.getNameGame() ) );
			res.append( gm.getNameGame() );
			//URL
			res.append( Util.sizeParameter(5,gm.getUrl().toString() ) );
			res.append( gm.getUrl() );
			//Flag
			res.append( Util.sizeParameter(5,Integer.toString( gm.getFlag() ) ) );
			res.append( Integer.toString( gm.getFlag() ) );
			//URL
			res.append( Util.sizeParameter(5,gm.getComentario().toString() ) );
			res.append( gm.getComentario() );
			//Flag
		}

		return res;
	}
	
	public boolean existeIDRoomAndExitRoom(int id_Room, String login, boolean timeout){
		Iterator i = new ArrayList(salas).iterator();
		boolean res = false;
		while (i.hasNext()){	
			Room rm = (Room)i.next();
			if (rm.getIdRoom() == id_Room){
				exitUserRoom(rm,login,timeout);
				res = true;
				break;
			}
		}
		return res;
	}
	
	public boolean existeIDRoom(int id_Room){
		Iterator i = new ArrayList(salas).iterator();
		boolean res = false;
		while (i.hasNext()){	
			Room rm = (Room)i.next();
			if (rm.getIdRoom() == id_Room){
				res = true;
				break;
			}
		}
		return res;
	}

	private void exitUserRoom(Room rm,String nameUser, boolean timeout){ //Recebe o Login e descobre a conexão, usando quando para comunicação com os jogos
		
		StringBuffer res = new StringBuffer();	
		//Descubro qual é a conexão do usuário
		Protocol p = rm.removeUser(nameUser);
		
		res.append(Constantes.LOBBY);
		res.append(Constantes.SERVIDOR_CLIENTE);
		res.append(Constantes.L_SC_UserSaiuSala);		
		res.append("0002");	
		res.append( Util.sizeParameter(5, Integer.toString(rm.getIdRoom()) ) );
		res.append( Integer.toString(rm.getIdRoom()) );
		res.append( Util.sizeParameter(5, p.getNome() ) );
		res.append( p.getNome() );
		sendToAll(true, null, res );

		statusUser(p);

		ParticipantesBO participantes = new ParticipantesBO();
		participantes.setId_partida(rm.getIdRoom());
		participantes.setId_usuario(nameUser);
		if ( !timeout ){
			participantes.setFlag(0);//Saiu Normal
			participantes.setPontos(99);
		}else{
			participantes.setFlag(1);//TIMEOUT
			participantes.setPontos(0);
		}
		PartidasDTO saiuPart = new PartidasDTO();
		saiuPart.saiuParticipante(participantes);

		if (rm.allUser().size() == 0){
			res.append(Constantes.LOBBY);
			res.append(Constantes.SERVIDOR_CLIENTE);
			res.append(Constantes.L_SC_FimSala);		
			res.append("0001");	
			res.append( Util.sizeParameter(5, Integer.toString(rm.getIdRoom()) ) );
			res.append( Integer.toString(rm.getIdRoom()) );
			salas.remove(rm);
			sendToAll(true, null, res );

			PartidasBO partida = new PartidasBO();
			partida.setId_partida( rm.getIdRoom() );
			PartidasDTO fimSala = new PartidasDTO();
			fimSala.finalizaPartida(partida);
		}else{
			statusSala(rm);
		}
		System.out.println("User Saiu da Partida. login = " + nameUser);
	}
	
	private void exitUserALLRoom(String nameUser, Protocol p){ //Retira de todas as sala o usuário. Ocorre por timeout ou clicar em logoff
		if ( p.getStatus() == 1 ){ //Caso for 0 (Livre) não estara em nenhuma sala.
			StringBuffer res = new StringBuffer("");
			boolean userInRoom = false;
			Room rm = null;
			Iterator i = new ArrayList(salas).iterator();
	
			while (i.hasNext()){//Devera percorrer todas as salas, pois pode estar em mais de uma e devera sair de todas elas	
				rm = (Room)i.next();
				Vector user = rm.allUser();
				if (user.contains( (Protocol) p) ){
					userInRoom = true;
					//p.setStatus(0);
				}
			
				if (userInRoom){
					rm.removeUser((Protocol)p);
					res.append(Constantes.LOBBY);
					res.append(Constantes.SERVIDOR_CLIENTE);
					res.append(Constantes.L_SC_UserSaiuSala);		
					res.append("0002");	
					res.append( Util.sizeParameter(5, Integer.toString(rm.getIdRoom()) ) );
					res.append( Integer.toString(rm.getIdRoom()) );
					res.append( Util.sizeParameter(5, nameUser ) );
					res.append( nameUser );
					sendToAll(true, p, res );
					
					statusUser(p);				

					ParticipantesBO participantes = new ParticipantesBO();
					participantes.setId_partida(rm.getIdRoom());
					participantes.setId_usuario(nameUser);
					participantes.setFlag(1);//SERA POR TIMEOUT
					participantes.setPontos(0);
					PartidasDTO saiuPart = new PartidasDTO();
					saiuPart.saiuParticipante(participantes);
					
					if (rm.allUser().size() == 0){
						res.append(Constantes.LOBBY);
						res.append(Constantes.SERVIDOR_CLIENTE);
						res.append(Constantes.L_SC_FimSala);		
						res.append("0001");	
						res.append( Util.sizeParameter(5, Integer.toString(rm.getIdRoom()) ) );
						res.append( Integer.toString(rm.getIdRoom()) );
						salas.remove(rm);
						sendToAll(true, p, res );

						PartidasBO partida = new PartidasBO();
						partida.setId_partida( rm.getIdRoom() );
						PartidasDTO fimSala = new PartidasDTO();
						fimSala.finalizaPartida(partida);
					}else{
						statusSala(rm);
					}
				}	
			}//do WHILE
		}//do IF do início
	}

	private StringBuffer doTimeOut(Protocol p){
		
		StringBuffer res = new StringBuffer("");
		res.append( Constantes.LOBBY );
		res.append( Constantes.SERVIDOR_CLIENTE );
		res.append( Constantes.L_SC_LogoffUser );
		res.append( "0001" );
		res.append( Util.sizeParameter(5,p.getNome() ));
		res.append( p.getNome() );
		
		return res;
	}

	private void statusSala(Room rm){
		StringBuffer res = new StringBuffer();
		res.append(Constantes.LOBBY);
		res.append(Constantes.SERVIDOR_CLIENTE);
		res.append(Constantes.L_SC_STATUSSALA);		
		res.append("0002");	
		res.append( Util.sizeParameter(5, Integer.toString(rm.getIdRoom()) ) );
		res.append( Integer.toString(rm.getIdRoom()) );
		res.append( Util.sizeParameter(5, rm.getNameGame()) );
		res.append( rm.getNameGame() );
		res.append( "00001" );
		if (rm.getSize() != rm.totalUser())
			res.append("0");
		else
			res.append("1");		
		sendToAll(true, null, res );
	}

	private void statusUser(Protocol p){
		
		Iterator i = new ArrayList(salas).iterator();
		Room rm = null;
		boolean in_room = false;
		while (i.hasNext()){//Devera percorrer todas as salas, pois pode estar em mais de uma	
			rm = (Room)i.next();
			Vector user = rm.allUser();
			if (user.contains( (Protocol) p) ){//Caso ele esteja em alguma sela, confirmar estado de OCUPADO
				p.setStatus(1);
				in_room = true;
				break;
			}
		}
		if (! in_room) p.setStatus(0);//Caso ele esteja em alguma sela, confirmar estado de LIVRE
		
		StringBuffer res = new StringBuffer();
		res.append(Constantes.LOBBY);
		res.append(Constantes.SERVIDOR_CLIENTE);
		res.append(Constantes.L_SC_STATUSUSER);		
		res.append("0002");	
		res.append( Util.sizeParameter(5, p.getNome() ));
		res.append( p.getNome() );
		res.append( Util.sizeParameter(5, Integer.toString(p.getStatus())  ));
		res.append( Integer.toString(p.getStatus()) );
		sendToAll(true, p, res );
	}	

	private void chatPessoal(StringBuffer msg){
		String userEnviou = Util.getParametro(1,msg).toString();
		String userDestino = Util.getParametro(2,msg).toString();
		boolean findRecebe = false;
		boolean findEnviou = false;
		Protocol pEnviou = null;
		Protocol pRecebe = null;
		Protocol p = null;
		Iterator i = new ArrayList(protocols).iterator();
		while (i.hasNext()){
			p = (Protocol)i.next();
			if (p.getNome().equals( userDestino) ){
				pRecebe = p;
				findRecebe = true;
			}
			if (p.getNome().equals( userEnviou) ){
				pEnviou = p;
				findEnviou = true;
			}
		}
		StringBuffer send = new StringBuffer();
		send.append( Constantes.LOBBY );
		send.append( Constantes.SERVIDOR_CLIENTE );
		send.append( Constantes.L_SC_CHATPESSOAL );
		send.append( "0003" );
		send.append( Util.sizeParameter(5,userEnviou ));
		send.append( userEnviou );
		send.append( Util.sizeParameter(5,userDestino ));
		send.append( userDestino );
		send.append( Util.sizeParameter(5,Util.getParametro(3,msg).toString()) );
		send.append( Util.getParametro(3,msg) );
		if (findRecebe){
			pRecebe.sendMsg(send);
		}
		if (findEnviou){
			pEnviou.sendMsg(send);
		}
	}
}
