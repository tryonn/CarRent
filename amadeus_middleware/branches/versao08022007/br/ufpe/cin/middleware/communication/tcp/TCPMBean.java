package br.ufpe.cin.middleware.communication.tcp;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * Interface que define os métodos a serem implementados pelo TCP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface TCPMBean {
	/**
	 * Aguarda por uma conexão. (método exclusivo do servidor).
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void listen() throws IOException, ClassNotFoundException;
	/**
	 * Método que envia um objeto. (método exclusivo do cliente)
	 *   
	 * @param obj objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void send(Object obj) throws IOException, NotConnectedException;
	/**
	 * Método que envia um objeto, através de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param obj objeto a ser enviado.
	 * @param destinationIP endereço de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 * @throws NotExistingClientException
	 */
	public void sendTo(Object obj, String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException;
	/**
	 * Método que envia um objeto para todos os clientes.
	 * (método exclusivo do servidor).
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
	 * Fecha uma conexão. 
	 * 
	 * @param destination endereço IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws Throwable
	 */
	public void close(String destination, int destinationPort) throws Throwable;
}