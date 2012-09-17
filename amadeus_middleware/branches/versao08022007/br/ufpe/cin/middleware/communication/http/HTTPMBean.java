package br.ufpe.cin.middleware.communication.http;

import java.io.IOException;

/**
 * Interface que define os métodos a serem implementados pelo HTTP.
 * 
 * @author Eliaquim Lima   (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface HTTPMBean {
	/**
	 * Método que abre a conexão.
	 * 
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 * 
	 */
	public void open() throws IOException;
	/**
	 * Método que envia uma mensagem ao destino.
	 * 
	 * @param msg Mensagem a ser enviada.
	 * 
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 * 
	 */
	public void send(Object msg) throws IOException;
	/**
	 * Método que recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 * 
	 * @throws ClassNotFoundException - Exceção levantada caso
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 * 
	 */
	public Object receive() throws IOException, ClassNotFoundException;
	/**
	 * Método que fecha a conexão.
	 * 
	 */
	public void close();
}
