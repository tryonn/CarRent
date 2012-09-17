package br.ufpe.cin.middleware.objects.controlMessages;

import java.io.IOException;
import br.ufpe.cin.middleware.objects.Message;
/**
 * Classe que representa as mensagens de controle do módulo HTTP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 * 
 */
public class HTTPControlMessage extends Message {

	private static final long serialVersionUID = 1L;
	/**
	 * Contrutor da classe HTTPControlMessage que recebe como parâmetro um array de bytes.
	 * 
	 * @param array - um array de bytes
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 * @throws ClassNotFoundException - Exceção levantada quando a classe não é encontrada.
	 */
	public HTTPControlMessage(byte[] array) throws IOException, ClassNotFoundException {
		super(array);
	}
	/**
	 * Construtor da classe HTTPControlMessage.
	 * 
	 * @param content - objeto que representa o conteúdo da mensagem.
	 * @param destinationIP - <code>String</code> que representa o endereço IP destino.
	 * @param sourceIP - <code>String</code> que representa o endereço IP origem.
	 * @param destinationPort - <code>int</code> que representa a porta destino.
	 * @param sourcePort - <code>int</code> que representa a porta origem.
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 */
	public HTTPControlMessage(Object content, String destinationIP, String sourceIP, int destinationPort, int sourcePort) throws IOException {
		super("HTTP",content, destinationIP, sourceIP, destinationPort, sourcePort);
	}
	/**
	 * Contrutor da classe HTTPControlMessage.
	 * 
	 * @param content - objeto que representa o conteúdo da mensagem.
	 * @param destination - <code>String</code> que representa o endereço IP destino.
	 * @param source - <code>String</code> que representa o endereço IP origem.
	 * @throws IOException - Exceção levantada quando ocorre erro de IO. 
	 */
	public HTTPControlMessage(Object content, String destination, String source) throws IOException {
		super("HTTP",content, destination, source);
	}
}
