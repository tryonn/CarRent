package br.ufpe.cin.middleware.communication.udp;

import java.io.IOException;
import java.net.SocketException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * Interface que define os métodos a serem implementados pelo UDP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface UDPMBean  {
	/**
	 * Método que "conecta" a um determinado destino.
	 * 
	 * @param destinationIP  Endereço IP destino.
	 * @param destinationPort  Porta destino.
	 * @throws SocketException  Exceção levantada quando ocorre alguma erro de socket. 
	 * @throws InvalidIPException Exceção levantada quando o IP é inválido.
	 * @throws InvalidPortException Exceção levantada quando a porta é inválida.
	 */
	public void open(String destinationIP, int destinationPort) throws SocketException, InvalidIPException, InvalidPortException;
	/**
	 * Método que envia uma mensagem para um destino.
	 * @param msg Objeto a ser enviado.
	 * @param destinationIP Endereço IP destino.
	 * @param destinationPort Porta destino.
	 * @throws IOException Exceção levantada quando ocorreu erro de IO.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException;
	/**
	 * Método que recebe uma mensagem.
	 * @return Retorna a mensagem recebida.
	 * @throws IOException Exceção levantada quando ocorreu erro de IO.
	 * @throws ClassNotFoundException Exceção levantada quando a classe procurada não é encontrada.
	 */
	public Message receive() throws IOException, ClassNotFoundException;	
	/**
	 * Método onde o UDP se "desconecta".
	 *
	 */
	public void close();
}
