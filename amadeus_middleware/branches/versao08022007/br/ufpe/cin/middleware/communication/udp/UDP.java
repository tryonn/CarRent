package br.ufpe.cin.middleware.communication.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.exceptions.NullSocketException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.controlMessages.UDPControlMessage;
import br.ufpe.cin.middleware.objects.controlMessages.UDPPackage;
import br.ufpe.cin.middleware.util.Debug;

/**
 * Classe respons�vel por implementar o comportamento da transmiss�o de dados
 * fazendo uso do protocolo UDP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class UDP extends NotificationBroadcasterSupport implements UDPMBean, NotificationListener {

	private static final String PROTOCOL = "UDP";
	
	public static final int SERVER_PORT = 1099; //Porta default para o servidor UDP.	
	public static final int SEND_BUFFER_SIZE = 32000;
	public static final int RECEIVE_BUFFER_SIZE = 32000;
	
	private DatagramSocket dSocket;
	private int port;
	private boolean connected;
	private boolean server;
	
	private String destinationIP;
	private int destinationPort;
	/**
	 * Construtor da classe usado caso o UDP esteja se comportando como servidor.
	 * 
	 * @throws SocketException - Exce��o levantada caso haja algum erro de socket.
	 * @throws InvalidPortException - Exce��o levantada caso a porta seja inv�lida.
	 */
	public UDP() throws SocketException, InvalidPortException { //UDP SERVER
		this.setServer(true);
		this.dSocket = new DatagramSocket(SERVER_PORT);
		this.setPort(SERVER_PORT);
		this.dSocket.setSendBufferSize(SEND_BUFFER_SIZE);
		this.dSocket.setReceiveBufferSize(Integer.MAX_VALUE);
	}
	/**
	 * Construtor da classe usado caso o UDP esteja se comportando como cliente.
	 * 
	 * @param destinationIP Endere�o IP do destino.
	 * 
	 * @throws SocketException - Exce��o levantada caso haja algum erro de socket.
	 * @throws InvalidPortException - Exce��o levantada caso a porta seja inv�lida.
	 * @throws InvalidIPException - Exce��o levantada caso o IP seja inv�lido.
	 */
	public UDP(String destinationIP) throws SocketException, InvalidPortException, InvalidIPException { //UDP CLIENT
		this.setServer(false);
		this.dSocket = new DatagramSocket();
		this.setPort(dSocket.getLocalPort());
		this.open(destinationIP,SERVER_PORT);	
		//this.dSocket.setSoTimeout(500);
	}
	/**
	 * M�todo que envia uma mensagem para um destino.
	 * @param msg Objeto a ser enviado.
	 * @param destinationIP Endere�o IP destino.
	 * @param destinationPort Porta destino.
	 * @throws IOException Exce��o levantada quando ocorreu erro de IO.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException {
		int localPort = this.dSocket.getLocalPort();
		String localAddress = this.dSocket.getLocalAddress().getHostAddress();
		byte[] contentArray = this.toByteArray(msg);
		int contentLenght = contentArray.length;
		
		//Envio de mensagem de protocolo
		   //Montando a mensagem de protocolo
		int packsNumber = (int) Math.ceil( (double)contentLenght/SEND_BUFFER_SIZE ); //Defino o numero de pacotes a serem enviados
		if(packsNumber == 0) {
			packsNumber = 1;
		}
		int msgType = 0;
		if(msg instanceof File) {
			msgType = UDPControlMessage.FILE;
		} else {
			msgType = UDPControlMessage.OBJECT;
		}
		int protocol[] = {contentLenght,packsNumber,msgType};
		UDPControlMessage controlMessage;
		if(!this.isServer()) {
			if(msgType == UDPControlMessage.FILE) {
				File f = (File) msg;
				controlMessage = new UDPControlMessage(protocol,f.getName(),this.destinationIP,localAddress,this.destinationPort,localPort);
			} else {
				controlMessage = new UDPControlMessage(protocol,"",this.destinationIP,localAddress,this.destinationPort,localPort);
			}
		} else {
			if(msgType == UDPControlMessage.FILE) {
				File f = (File) msg;
				controlMessage = new UDPControlMessage(protocol,f.getName(),destinationIP,localAddress,destinationPort,localPort);
			} else {
				controlMessage = new UDPControlMessage(protocol,"",destinationIP,localAddress,destinationPort,localPort);	
			}
		}
		   //Termina de montar a mensagem de protocolo
		byte[] protocolMessage = controlMessage.toByteArray(); //Transforma a mensagem de protocolo em array de bytes
		DatagramPacket dPacket = new DatagramPacket(protocolMessage, protocolMessage.length);
		if (!this.isServer()) {
			dPacket.setSocketAddress(new InetSocketAddress(this.getDestinationIP(),this.getDestinationPort()));
		} else {
			dPacket.setSocketAddress(new InetSocketAddress(destinationIP,destinationPort));
		}
		dSocket.send(dPacket);///Envia a mensagem de protocolo
		//Fim do envio da mensagem de protocolo
		int packSent = 0;
		for(int count = 0; count < contentLenght; count = count + SEND_BUFFER_SIZE)  {
			floodControl();
			int arrayLenght = 0;
			if(count + SEND_BUFFER_SIZE > contentLenght) {
				arrayLenght = contentLenght - count;
			} else {
				arrayLenght = SEND_BUFFER_SIZE;
			}
			byte[] arrayToSend = new byte[arrayLenght];
			for (int i = 0; i < arrayToSend.length; i++) {
				arrayToSend[i] = contentArray[count + i];
			}
			
			UDPPackage pack = new UDPPackage(packSent,arrayToSend,arrayToSend.length);
			packSent++;
			byte[] packBytes = this.toByteArray(pack);
			dPacket = new DatagramPacket(packBytes, packBytes.length);
			
			if (!this.isServer()) {
				dPacket.setSocketAddress(new InetSocketAddress(this.getDestinationIP(),this.getDestinationPort()));
			} else {
				dPacket.setSocketAddress(new InetSocketAddress(destinationIP,destinationPort));
			}
			dSocket.send(dPacket);
			
		}
	}
	/**
	 * M�todo que recebe uma mensagem.
	 * @return Retorna a mensagem recebida.
	 * @throws IOException Exce��o levantada quando ocorreu erro de IO.
	 * @throws ClassNotFoundException Exce��o levantada quando a classe procurada n�o � encontrada.
	 */
	public Message receive() throws IOException, ClassNotFoundException {
		//Recebe mensagem de protocolo
		byte[] receivedTemp = new byte[RECEIVE_BUFFER_SIZE]; //array de bytes temp
		DatagramPacket packet = new DatagramPacket(receivedTemp,receivedTemp.length);
		dSocket.receive(packet);
		receivedTemp = packet.getData();
		ByteArrayInputStream byteArray = new ByteArrayInputStream(receivedTemp);
		ObjectInputStream object = new ObjectInputStream(byteArray);
		UDPControlMessage controlMessage = (UDPControlMessage) object.readObject();
		//Recebeu mensagem de protocolo
			
		int[] info = (int[]) controlMessage.getControl();
		int messageLength = info[0]; //Tamanho total da mensagem
		int packsNumber = info[1];//Numero de pacotes a serem enviados.
		byte[] received = new byte[messageLength]; //Array que guarda toda a mensagem
		int limit = 0;
		
		for(int i = 0; i < packsNumber; i++) {
			if(i == packsNumber - 1) {
				if(packsNumber == 1) {
					limit = messageLength;
				} else {
					limit = messageLength - (i)*RECEIVE_BUFFER_SIZE;
				}
			} else  {
				limit = RECEIVE_BUFFER_SIZE;
			}
			int limit2 = limit + 142;
			receivedTemp = new byte[limit2];
			packet = new DatagramPacket(receivedTemp,receivedTemp.length);
			try {
				dSocket.receive(packet);
				receivedTemp = packet.getData();
				UDPPackage pack = new UDPPackage(receivedTemp);
				
				for(int a = 0; a < pack.getLength(); a++) {
					int pos = RECEIVE_BUFFER_SIZE*pack.getNumber() + a;
					received[pos] = pack.getContent()[a]; 
				}
			} catch (SocketTimeoutException e) {
				break;
			}
		}
		Message message = new Message(PROTOCOL,received,controlMessage.getDestinationIP(),controlMessage.getSourceIP(),controlMessage.getDestinationPort(),controlMessage.getSourcePort());
		message.setHasFile(controlMessage.isHasFile());
		message.setFileName((String)controlMessage.getContent());
		message.checkMessage();
		return message;
	}
	/**
	 * M�todo que "conecta" a um determinado destino.
	 * 
	 * @param destinationIP  Endere�o IP destino.
	 * @param destinationPort  Porta destino.
	 * @throws SocketException  Exce��o levantada quando ocorre alguma erro de socket. 
	 * @throws InvalidIPException Exce��o levantada quando o IP � inv�lido.
	 * @throws InvalidPortException Exce��o levantada quando a porta � inv�lida.
	 */
	public void open(String destinationIP, int destinationPort) throws SocketException, InvalidIPException, InvalidPortException {
		if(!this.isServer()) {
			this.setDestinationIP(destinationIP); 
			this.setDestinationPort(destinationPort); 
			SocketAddress address = new InetSocketAddress(destinationIP,destinationPort);
			this.dSocket.connect(address);
			this.setConnected(true);
		}
	}
	/**
	 * M�todo onde o UDP se "desconecta".
	 *
	 */
	public void close() {
		this.setConnected(false);
		this.destinationIP = null;
		this.destinationPort = 0 ; 
	}
	/**
	 * M�todo que verifica se o cliente UDP est� conectado.
	 * 
	 * @return Retorna <code>true</code> caso o cliente esteja conectado e <code>false</code> caso contr�rio.
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * M�todo que modifica o estado de conex�o do cliente UDP.
	 * 
	 * @param connected Novo estado de conex�o.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	/**
	 * M�todo que retorna o DatagramSocket do UDP.
	 * 
	 * @return Retorna o DatagramSocket do UDP.
	 */
	public DatagramSocket getDsocket() {
		return dSocket;
	}
	/**
	 * M�todo que modifica o Datagramsocket do UDP.
	 * 
	 * @param dsocket Novo DatagramSocket.
	 * 
	 * @throws NullSocketException - Exce��o levantada caso o socket seja nulo.
	 */
	public void setDsocket(DatagramSocket dsocket) throws NullSocketException {
		if(dsocket != null) {
			this.dSocket = dsocket;
		} else {
			throw new NullSocketException();
		}
	}
	/**
	 * M�todo que retorna a porta local.
	 * 
	 * @return Retorna a porta local.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * M�todo que modifica a porta local.
	 * 
	 * @param port Nova porta local.
	 * 
	 * @throws InvalidPortException - Exce��o levantada caso a porta seja inv�lida.
	 */
	public void setPort(int port) throws InvalidPortException {
		if(this.isValidPort(port)) {
			this.port = port;
			
		} else {
			throw new InvalidPortException();
		}
	}
	/**
	 * M�todo que retorna o endere�o IP do destino.
	 * 
	 * @return Retorna o endere�o IP do destino.
	 */
	public String getDestinationIP() {
		return destinationIP;
	}
	/**
	 * M�todo que modifica o endere�o IP do destino.
	 * 
	 * @param destinationIP Novo endere�o IP do destino.
	 * 
	 * @throws InvalidIPException - Exce��o levantada caso o IP seja inv�lido.
	 */
	public void setDestinationIP(String destinationIP) throws InvalidIPException {
		if(destinationIP != null) {
			this.destinationIP = destinationIP;
		} else {
			throw new InvalidIPException();
		}
	}
	/**
	 * M�todo que retorna a porta do destino.
	 * 
	 * @return Retorna a porta do destino.
	 */
	public int getDestinationPort() {
		return destinationPort;
	}
	/**
	 * M�todo que modifica a porta do destino.
	 * 
	 * @param destinationPort Nova porta.
	 * 
	 * @throws InvalidPortException - Exce��o levantada caso a porta seja inv�lida.
	 */
	public void setDestinationPort(int destinationPort) throws InvalidPortException {
		if(this.isValidPort(destinationPort)) {
			this.destinationPort = destinationPort;
		} else {
			throw new InvalidPortException();
		}
	}
	/**
	 * M�todo que retorna a informa��o de se o udp est� sendo usado como servidor.
	 * 
	 * @return Retorna <code>true</code> caso o UDP esteja sendo usado como servido, e <code>false</code> caso contr�rio.
	 */
	public boolean isServer() {
		return server;
	}
	/**
	 * M�todo que modifica a informa�ao de se o UDP est� sendo usado como servidor.
	 * 
	 * @param server Novo estado do UDP.
	 */
	public void setServer(boolean server) {
		this.server = server;
	}
	/**
	 * M�todo auxiliar que converte um objeto num array de bytes.
	 * 
	 * @param obj objeto a ser convertido.
	 * @return Retorna um array de bytes correspondente ao objeto original.
	 * 
	 * @throws IOException - Exce��o levantada caso ocorra algum erro de IO.
	 */
	private byte[] toByteArray(Object obj) throws IOException {
		byte[] resul;
		
		if(obj instanceof File) {
			FileInputStream fileInput = new FileInputStream((File)obj);
			int disp = fileInput.available();
			byte[] fileBytes = new byte[disp];
			fileInput.read(fileBytes);
			resul = fileBytes;
		}  else {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bStream);
			os.writeObject(obj);
			os.flush();
			resul = bStream.toByteArray();
		}
		
		return resul;		
	}
	/**
	 * M�todo auxiliar que verifica se uma dada porta � valida.
	 * 
	 * @param port Porta a ser verificada.
	 * @return Retorna <code>true</code> caso a porta seja e <code>false</code> caso contr�rio.
	 */
	private boolean isValidPort(int port) {
		if( (port > 0 && port < 1023) || port > 65535 ) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * M�todo auxiliar que controla a velocidade ao enviar uma mensagem (UDP � n�o confi�vel).
	 */
	private void floodControl() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			Debug.printStack(e);
		}
	}
	
	public void handleNotification(Notification arg0, Object arg1) { }
}
