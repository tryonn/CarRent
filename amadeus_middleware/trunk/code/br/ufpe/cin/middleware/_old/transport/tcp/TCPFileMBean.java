package br.ufpe.cin.middleware._old.transport.tcp;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * 
 * Interface que define os m�todos a serem implementados pelo TCPFile.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface TCPFileMBean {
	/**
	 * M�todo que envia um arquivo. (m�todo exclusivo do cliente)
	 *   
	 * @param file arquivo a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void send(Object file) throws IOException, NotConnectedException;
	/**
	 * M�todo que envia um arquivo, atrav�s de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param file arquivo a ser enviado.
	 * @param destinationIP endere�o de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 * @throws NotExistingClientException
	 */
	public void sendTo(Object file,String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException;
	/**
	 * M�todo que envia um arquivo para todos os clientes.
	 * (m�todo exclusivo do servidor).
	 * 
	 * @param file arquivo a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void sendToAll(Object file) throws IOException, NotConnectedException;
	/**
	 * Recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NotConnectedException
	 */
	public Message receive() throws IOException, ClassNotFoundException, NotConnectedException;
	/**
	 * Fecha uma conex�o. 
	 * 
	 * @param destination endere�o IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws Throwable
	 */
	public void close(String destination, int destinationPort) throws Throwable;
}
