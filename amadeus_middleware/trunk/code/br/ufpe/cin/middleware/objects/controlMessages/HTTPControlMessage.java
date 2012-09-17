package br.ufpe.cin.middleware.objects.controlMessages;

import java.io.IOException;
import br.ufpe.cin.middleware.objects.Message;
/**
 * Classe que representa as mensagens de controle do m�dulo HTTP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 * 
 */
public class HTTPControlMessage extends Message {

	private static final long serialVersionUID = 1L;
	/**
	 * Contrutor da classe HTTPControlMessage que recebe como par�metro um array de bytes.
	 * 
	 * @param array - um array de bytes
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 * @throws ClassNotFoundException - Exce��o levantada quando a classe n�o � encontrada.
	 */
	public HTTPControlMessage(byte[] array) throws IOException, ClassNotFoundException {
		super(array);
	}
	/**
	 * Construtor da classe HTTPControlMessage.
	 * 
	 * @param content - objeto que representa o conte�do da mensagem.
	 * @param destinationIP - <code>String</code> que representa o endere�o IP destino.
	 * @param sourceIP - <code>String</code> que representa o endere�o IP origem.
	 * @param destinationPort - <code>int</code> que representa a porta destino.
	 * @param sourcePort - <code>int</code> que representa a porta origem.
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 */
	public HTTPControlMessage(Object content, String destinationIP, String sourceIP, int destinationPort, int sourcePort) throws IOException {
		super("HTTP",content, destinationIP, sourceIP, destinationPort, sourcePort);
	}
	/**
	 * Contrutor da classe HTTPControlMessage.
	 * 
	 * @param content - objeto que representa o conte�do da mensagem.
	 * @param destination - <code>String</code> que representa o endere�o IP destino.
	 * @param source - <code>String</code> que representa o endere�o IP origem.
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO. 
	 */
	public HTTPControlMessage(Object content, String destination, String source) throws IOException {
		super("HTTP",content, destination, source);
	}
}
