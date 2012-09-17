package br.ufpe.cin.middleware.objects.controlMessages;

import java.io.IOException;

import br.ufpe.cin.middleware.objects.Message;

/**
 * Classe que representa as mensagens de controle do módulo TCP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 * 
 */
public class TCPControlMessage extends Message {

	private static final long serialVersionUID = 1L;
	/** tipo de mensagem que indica o fechamento de uma conexão */
	public static final int CLOSE_CONNECTION = 0;
	/** tipo de mensagem de controle */
	private int messageType;

	/**
	 * Construtor da classe TCPControlMessage.
	 * 
	 * @param type - tipo de mensagem de controle.
	 * @param destinationIP - <code>String</code> que representa o endereço IP destino.
	 * @param sourceIP - <code>String</code> que representa o endereço IP origem.
	 * @param destinationPort - <code>int</code> que representa a porta destino.
	 * @param sourcePort - <code>int</code> que representa a porta origem.
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 */
	public TCPControlMessage(int type, String destinationIP, String sourceIP, int destinationPort, int sourcePort) throws IOException {
		super("TCP","CLOSE_OPERATION", destinationIP, sourceIP, destinationPort, sourcePort);
		this.messageType = type;
	}
	/**
	 * Construtor da classe TCPControlMessage.
	 * 
	 * @param type - tipo de mensagem de controle.
	 * @param destination - <code>String</code> que representa o endereço IP destino.
	 * @param source - <code>String</code> que representa o endereço IP origem.
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 */
	public TCPControlMessage(int type, String destination, String source) throws IOException {
		super("TCP","CLOSE_OPERATION", destination, source);
		this.messageType = type;
	}
	/**
	 * Retorna o tipo da mensagem de controle.
	 * 
	 * @return retorna o <code>messageType</code>.
	 */
	public int getMessageType() {
		return messageType;
	}
	/**
	 * Altera o tipo da mensagem de controle.
	 * 
	 * @param messageType O novo <code>messageType</code>.
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

}
