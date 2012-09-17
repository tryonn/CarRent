package br.ufpe.cin.middleware.communication.udp;

import java.io.IOException;
import java.net.SocketException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * Interface que define os m�todos a serem implementados pelo UDP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface UDPMBean  {
	/**
	 * M�todo que "conecta" a um determinado destino.
	 * 
	 * @param destinationIP  Endere�o IP destino.
	 * @param destinationPort  Porta destino.
	 * @throws SocketException  Exce��o levantada quando ocorre alguma erro de socket. 
	 * @throws InvalidIPException Exce��o levantada quando o IP � inv�lido.
	 * @throws InvalidPortException Exce��o levantada quando a porta � inv�lida.
	 */
	public void open(String destinationIP, int destinationPort) throws SocketException, InvalidIPException, InvalidPortException;
	/**
	 * M�todo que envia uma mensagem para um destino.
	 * @param msg Objeto a ser enviado.
	 * @param destinationIP Endere�o IP destino.
	 * @param destinationPort Porta destino.
	 * @throws IOException Exce��o levantada quando ocorreu erro de IO.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException;
	/**
	 * M�todo que recebe uma mensagem.
	 * @return Retorna a mensagem recebida.
	 * @throws IOException Exce��o levantada quando ocorreu erro de IO.
	 * @throws ClassNotFoundException Exce��o levantada quando a classe procurada n�o � encontrada.
	 */
	public Message receive() throws IOException, ClassNotFoundException;	
	/**
	 * M�todo onde o UDP se "desconecta".
	 *
	 */
	public void close();
}
