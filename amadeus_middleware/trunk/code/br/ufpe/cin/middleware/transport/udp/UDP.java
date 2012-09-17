package br.ufpe.cin.middleware.transport.udp;

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

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.exceptions.NullSocketException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.controlMessages.UDPControlMessage;
import br.ufpe.cin.middleware.objects.controlMessages.UDPPackage;
import br.ufpe.cin.middleware.transport.Transport;
import br.ufpe.cin.middleware.util.Debug;

public class UDP implements Transport, UDPMBean {

	private UDPAddress address;
	private boolean connected = false;
	
	private static final int SEND_BUFFER_SIZE 		= 32000;
	private static final int RECEIVE_BUFFER_SIZE 	= 32000;
	
	private DatagramSocket dSocket;	

	public UDP(UDPAddress address) throws SocketException, InvalidPortException, InvalidIPException {
		this.address = address;
		if (address.isServer()) {
			this.dSocket = new DatagramSocket(address.getPort());
			this.dSocket.setSendBufferSize(SEND_BUFFER_SIZE);
			this.dSocket.setReceiveBufferSize(Integer.MAX_VALUE);
		} else {
			this.dSocket = new DatagramSocket();
			this.address.setPort(this.dSocket.getLocalPort());
			this.open(address.getDestinationIP(), UDPAddress.SERVER_PORT);
		}
	}

	public void send(Object msg, String destinationIP, int destinationPort) throws IOException {
		int localPort = this.dSocket.getLocalPort();
		String localAddress = this.dSocket.getLocalAddress().getHostAddress();
		byte[] contentArray = this.toByteArray(msg);
		int contentLength = contentArray.length;
		
		//Envio de mensagem de protocolo
		   //Montando a mensagem de protocolo
		int packsNumber = (int) Math.ceil( (double)contentLength/SEND_BUFFER_SIZE ); //Defino o numero de pacotes a serem enviados
		if(packsNumber == 0) {
			packsNumber = 1;
		}
		int msgType = 0;
		if(msg instanceof File) {
			msgType = UDPControlMessage.FILE;
		} else {
			msgType = UDPControlMessage.OBJECT;
		}
		int protocol[] = {contentLength,packsNumber,msgType};
		UDPControlMessage controlMessage;
		if(!this.address.isServer()) {
			if(msgType == UDPControlMessage.FILE) {
				File f = (File) msg;
				controlMessage = new UDPControlMessage(protocol,f.getName(),this.address.getDestinationIP(),localAddress,this.address.getDestinationPort(),localPort);
			} else {
				controlMessage = new UDPControlMessage(protocol,"",this.address.getDestinationIP(),localAddress,this.address.getDestinationPort(),localPort);
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
		if (!this.address.isServer()) {
			dPacket.setSocketAddress(new InetSocketAddress(this.address.getDestinationIP(),this.address.getDestinationPort()));
		} else {
			dPacket.setSocketAddress(new InetSocketAddress(destinationIP,destinationPort));
		}
		dSocket.send(dPacket);///Envia a mensagem de protocolo
		//Fim do envio da mensagem de protocolo
		int packSent = 0;
		for(int count = 0; count < contentLength; count = count + SEND_BUFFER_SIZE)  {
			floodControl();
			int arrayLenght = 0;
			if(count + SEND_BUFFER_SIZE > contentLength) {
				arrayLenght = contentLength - count;
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
			
			if (!this.address.isServer()) {
				dPacket.setSocketAddress(new InetSocketAddress(this.address.getDestinationIP(),this.address.getDestinationPort()));
			} else {
				dPacket.setSocketAddress(new InetSocketAddress(destinationIP,destinationPort));
			}
			dSocket.send(dPacket);
			
		}
	}

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
		Message message = new Message("UDP",received,controlMessage.getDestinationIP(),controlMessage.getSourceIP(),controlMessage.getDestinationPort(),controlMessage.getSourcePort());
		message.setHasFile(controlMessage.isHasFile());
		message.setFileName((String)controlMessage.getContent());
		message.checkMessage();
		return message;
	}

	public void open(String destinationIP, int destinationPort) throws SocketException, InvalidIPException, InvalidPortException {
		if(!this.address.isServer()) {
			this.address.setDestinationIP(destinationIP); 
			this.address.setDestinationPort(destinationPort); 
			SocketAddress address = new InetSocketAddress(destinationIP,destinationPort);
			this.dSocket.connect(address);
			this.setConnected(true);
		}
	}
	
	public void close() {
		this.setConnected(false);
	}
	
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public DatagramSocket getDsocket() {
		return dSocket;
	}

	public void setDsocket(DatagramSocket dsocket) throws NullSocketException {
		if(dsocket != null) {
			this.dSocket = dsocket;
		} else {
			throw new NullSocketException();
		}
	}

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
	
	private void floodControl() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			Debug.printStack(e);
		}
	}
	
}
