package br.ufpe.cin.middleware.communication.tcp;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * Interface que define os m�todos a serem implementados pelo TCP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface TCPMBean {
	/**
	 * Aguarda por uma conex�o. (m�todo exclusivo do servidor).
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void listen() throws IOException, ClassNotFoundException;
	/**
	 * M�todo que envia um objeto. (m�todo exclusivo do cliente)
	 *   
	 * @param obj objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void send(Object obj) throws IOException, NotConnectedException;
	/**
	 * M�todo que envia um objeto, atrav�s de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param obj objeto a ser enviado.
	 * @param destinationIP endere�o de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 * @throws NotExistingClientException
	 */
	public void sendTo(Object obj, String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException;
	/**
	 * M�todo que envia um objeto para todos os clientes.
	 * (m�todo exclusivo do servidor).
	 * 
	 * @param obj objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void sendToAll(Object obj) throws IOException, NotConnectedException;
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