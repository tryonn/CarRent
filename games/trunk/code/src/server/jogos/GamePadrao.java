package server.jogos;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import server.Constantes;
import server.Protocol;
import server.Util;
import server.lobby.PoolLobby;

public class GamePadrao {

	private int id;
	private Vector listaUsuarios;
	
	protected void setListaUsuarios(Vector listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	
	protected Vector getListaUsuarios() {
		return listaUsuarios;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public GamePadrao() {
		setListaUsuarios(new Vector());
	}
	
	public void addProtocol(Protocol p){
		getListaUsuarios().add(p);
		enviaListaUsers(p);
		userEntrou(p);		
		
	}
	
	public void work() throws IOException{
		Iterator i = getListaUsuarios().iterator();
		StringBuffer command = new StringBuffer();
		while (i.hasNext()){
			
			Protocol p = (Protocol) i.next();
			
			if (p.isDied(Constantes.JOGO) == true){
				userSaiu(p,true);
				p.exit();						
				i.remove();
				System.out.println("TIMEOUT JOGO");
			}else
				if (p.isReady()){
					command = p.getCommand();
					if ( interpretadorDeMSG(p, command) ) //SIM -> caso o usuário tenha saido
						i.remove();
				}
		}	
	}

	private void sendBroadCast(StringBuffer msg){
		Iterator i = getListaUsuarios().iterator();
		while (i.hasNext()){
			
			Protocol p = (Protocol) i.next();
			p.sendMsg(msg);
		}
	}
	
	private void sendUnlessMe(Protocol p, StringBuffer msg){
		Iterator i = getListaUsuarios().iterator();
		while (i.hasNext()){
			
			Protocol p2 = (Protocol) i.next();
			if (p != p2)
				p2.sendMsg(msg);
		}
	}	

	protected boolean interpretadorDeMSG(Protocol p, StringBuffer msg){
		boolean saiu = false;
		if ( !msg.substring(0,5).equals((""+Constantes.JOGO + Constantes.CLIENTE_SERVIDOR + Constantes.L_CS_TIMEOUT)) ) { 
			if ( msg.substring(0,5).equals((""+Constantes.JOGO + Constantes.CLIENTE_SERVIDOR + Constantes.J_JS_MsgBroadcast)) ){
				StringBuffer send = new StringBuffer();
				StringBuffer mensagem = Util.getParametro(1,msg);
				send.append(Constantes.JOGO);
				send.append(Constantes.SERVIDOR_CLIENTE);
				send.append(Constantes.J_SJ_Msg);
				send.append("0001");
				send.append( Util.sizeParameter(5, mensagem.toString() ) );		
				send.append( mensagem );
				sendBroadCast(send);
			}else
				if ( msg.substring(0,5).equals((""+Constantes.JOGO + Constantes.CLIENTE_SERVIDOR + Constantes.J_JS_MsgUnlessMe)) ){
					StringBuffer send = new StringBuffer();
					StringBuffer mensagem = Util.getParametro(1,msg);
					send.append(Constantes.JOGO);
					send.append(Constantes.SERVIDOR_CLIENTE);
					send.append(Constantes.J_SJ_Msg);
					send.append("0001");
					send.append( Util.sizeParameter(5, mensagem.toString() ) );		
					send.append( mensagem );
					sendUnlessMe(p,send);
				}else
					if ( msg.substring(0,5).equals((""+Constantes.JOGO + Constantes.CLIENTE_SERVIDOR + Constantes.J_JS_UsuarioSaiu)) ){
						userSaiu(p,false);
						saiu = true;
					}
		}
		return saiu;
	}

	private void userSaiu(Protocol p, boolean timeout){
		String login = p.getNome();
		
		StringBuffer send = new StringBuffer();
		send.append(Constantes.JOGO);
		send.append(Constantes.SERVIDOR_CLIENTE);
		send.append(Constantes.J_SJ_UsuarioSaiu);
		send.append("0001");
		send.append( Util.sizeParameter(5, login ) );		
		send.append(login );
		sendUnlessMe(p, send);
		
		PoolLobby pollLobby = PoolLobby.getInstance();
		pollLobby.userExitGame(id,login,timeout);
	}
	
	//Envia para o usuário q entrou a lista dos usuarios da sua partida em ordem de entrada
	private void enviaListaUsers(Protocol p){
		StringBuffer send = new StringBuffer();
		send.append(Constantes.JOGO);
		send.append(Constantes.SERVIDOR_CLIENTE);
		send.append(Constantes.J_SJ_ListaUsers);
		send.append(  ("000"+Integer.toString(getListaUsuarios().size())).substring(("000"+Integer.toString(getListaUsuarios().size())).length()-4,("000"+Integer.toString(getListaUsuarios().size())).length()) );
		Iterator i = listaUsuarios.iterator();
		while (i.hasNext()){
			Protocol user = (Protocol) i.next();
			send.append(Util.sizeParameter(5, user.getNome()) );
			send.append(user.getNome() );
		}
		p.sendMsg(send);
	}

	//Quando um usuário entra todos os outros clientes saberam q ele entrou
	private void userEntrou(Protocol p){
		StringBuffer send = new StringBuffer();
		send.append(Constantes.JOGO);
		send.append(Constantes.SERVIDOR_CLIENTE);
		send.append(Constantes.J_SJ_UserEntrou);
		send.append("0001");
		send.append(Util.sizeParameter(5, p.getNome()) );
		send.append(p.getNome() );

		Iterator i = listaUsuarios.iterator();
		while (i.hasNext()){
			Protocol user = (Protocol) i.next();
			if (user != p)
				user.sendMsg(send);
		}
	}
}
