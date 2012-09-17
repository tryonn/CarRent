package br.ufpe.cin.middleware.objects.controlMessages;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import br.ufpe.cin.middleware.objects.Message;

/**
 * Classe que representa as mensagens de controle do m�dulo UDP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 * 
 */
public class UDPControlMessage extends Message  {

	/** tipo de mensagem que indica que o conte�do � do tipo arquivo */
	public static final int FILE = 0;
	/** tipo de mensagem que indica que o conte�do � do tipo objeto */
	public static final int OBJECT = 1;
	private static final long serialVersionUID = 1L;
	/** informa��es de controle, um array de int de tamanho 3, no seguinte formato:<br>
	 * <code>{tamanho do conteudo, numero de pacotes, tipo de conteudo}</code>
	 * */
	private int[] control;

	/**
	 * Construtor da classe UDPControlMessage.
	 * 
	 * @param protocolInfo - informa��es de controle sobre o protocolo, um array de
	 * int de tamanho 3, no seguinte formato:<br>
	 * <code>{tamanho do conteudo, numero de pacotes, tipo de conteudo}</code>
	 * @param generalInfo - informa��es gerais (conte�do da mensagem).
	 * @param destinationIP - <code>String</code> que representa o endere�o IP destino.
	 * @param sourceIP - <code>String</code> que representa o endere�o IP origem.
	 * @param destinationPort - <code>int</code> que representa a porta destino.
	 * @param sourcePort - <code>int</code> que representa a porta origem.
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 */
	public UDPControlMessage(int[] protocolInfo, String generalInfo, String destinationIP, String sourceIP, int destinationPort, int sourcePort) throws IOException {
		super("UDP",generalInfo, destinationIP, sourceIP, destinationPort, sourcePort);
		this.control = protocolInfo;
		if(protocolInfo[2] == FILE) {
			this.setHasFile(true);
		} else if(protocolInfo[2] == OBJECT) {
			this.setHasFile(false);
		}
	}
	/**
	 * Construtor da classe UDPControlMessage.
	 * 
	 * @param protocolInfo - informa��es de controle sobre o protocolo, um array de
	 * int de tamanho 3, no seguinte formato:<br>
	 * <code>{tamanho do conteudo, numero de pacotes, tipo de conteudo}</code>
	 * @param generalInfo - informa��es gerais (conte�do da mensagem).
	 * @param destination - <code>String</code> que representa o endere�o IP destino.
	 * @param source - <code>String</code> que representa o endere�o IP origem.
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 */
	public UDPControlMessage(int[] protocolInfo, String generalInfo, String destination, String source) throws IOException {
		super("UDP",generalInfo, destination, source);
		this.control = protocolInfo;
		if(protocolInfo[2] == FILE) {
			this.setHasFile(true);
		} else if(protocolInfo[2] == OBJECT) {
			this.setHasFile(false);
		}
	}
	/**
	 * M�todo que transforma uma UDPControlMessage em um array de bytes.
	 * 
	 * @return Retorna o array de bytes referente a esta inst�ncia.
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 */
	public byte[] toByteArray() throws IOException {
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(bStream));
		os.writeObject(this);
		bStream.flush();
		os.flush();
		byte[] resul = bStream.toByteArray();
		return resul;		
	}
	/**
	 * Retorna informa��es de controle de uma UDPControlMessage.
	 * 
	 * @return Retorna o <code>control</code>.
	 */
	public int[] getControl() {
		return control;
	}
	/**
	 * Altera informa��es de controle de uma UDPControlMessage.
	 * 
	 * @param control - O novo <code>control</code>.
	 */
	public void setControl(int[] control) {
		this.control = control;
	}
}
