package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;


public class Protocol{

	private String nome;
	private int status;// 0 para livre e 1 para ocupado, caso seja uma pessoa

	private Socket socket;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private Date lastMsgSend;
	private boolean sendTimeOut = false; 
	private Date timeSendTimeOut;

	public Protocol(Socket s)  throws IOException {		
		socket = s;
		out = new PrintWriter(socket.getOutputStream(),true);
		out.flush();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.lastMsgSend = new Date();
		this.setStatus(0);
	}

	public boolean isReady() throws IOException{
		
		return in.ready();
	}

	public boolean isDied(int identificador) {
		
		return timeOut(identificador);
	}

	public StringBuffer getCommand() {
		StringBuffer  command = new StringBuffer("");
		
		char temp = '0';

		try {
			while( isReady() ){
				temp = (char)in.read();			
				if (temp != Constantes.FINALIZADORFLASH)
				command.append(temp);
			}
			
//			ByteBuffer bf = ByteBuffer.allocate(2048);
//			bf.	
//			
//			System.out.println( charset.decode( command.toString()) );
					
					
			this.lastMsgSend = new Date();
			this.sendTimeOut = false;
		}catch (IOException e) {
			System.out.println("Erro na Leitura do Comando Recebido. " + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Erro na Leitura do Comando Recebido. " + e.getMessage());
			e.printStackTrace();
		}
		return command;
	}
		
	public void sendMsg(StringBuffer msg){
		msg.append(Constantes.FINALIZADORFLASH);
		out.print(  msg.toString() );
		out.flush();
	}

	public void exit(){
		try {
			System.out.println("Cliente de login = "+getNome()+" e ip = "+socket.getInetAddress().getHostAddress()+" desconectou!");
			this.socket.close();
			
		} catch (IOException e) {
			System.out.println("ERRO FECHAR A CONEXÃO");
			e.printStackTrace();
		}
	}

	private boolean timeOut(int identificador){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, -Constantes.TIMEOUT_LOBBY);

		if ( (!sendTimeOut)&&(cal.getTime().after(this.lastMsgSend))   ){
			this.sendTimeOut = true;
			StringBuffer send = new StringBuffer();
			send.append(identificador);
			send.append(Constantes.SERVIDOR_CLIENTE);
			send.append(Constantes.L_SC_TIMEOUT);
			send.append("0000");
			sendMsg(send);
			this.timeSendTimeOut = new Date();
		}
		boolean res = false;
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, -Constantes.TIMEOUT_LOBBY);
		
		if ( (sendTimeOut)&&(cal.getTime().after(this.timeSendTimeOut))   ){
			System.out.println("O usuário "+getNome() + " de IP "+socket.getInetAddress().getHostAddress()+" sera desconectado por TimeOut");
			this.sendTimeOut = false;
			res = true;
		}
		return res;
	}

	public void setNome(String nome){
		this.nome= nome;
	}	
	
	public String getNome(){
		return this.nome;
	}	
	
	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}

