package br.ufpe.cin.middleware._old.transport.tcp;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * 
 * Interface que define os métodos a serem implementados pelo TCPFile.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface TCPFileMBean {
	/**
	 * Método que envia um arquivo. (método exclusivo do cliente)
	 *   
	 * @param file arquivo a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
	public void send(Object file) throws IOException, NotConnectedException;
	/**
	 * Método que envia um arquivo, através de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param file arquivo a ser enviado.
	 * @param destinationIP endereço de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 * @throws NotExistingClientException
	 */
	public void sendTo(Object file,String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException;
	/**
	 * Método que envia um arquivo para todos os clientes.
	 * (método exclusivo do servidor).
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
	 * Fecha uma conexão. 
	 * 
	 * @param destination endereço IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws Throwable
	 */
	public void close(String destination, int destinationPort) throws Throwable;
}
