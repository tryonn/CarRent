package br.ufpe.cin.middleware.communication.http;

import java.io.IOException;

/**
 * Interface que define os m�todos a serem implementados pelo HTTP.
 * 
 * @author Eliaquim Lima   (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface HTTPMBean {
	/**
	 * M�todo que abre a conex�o.
	 * 
	 * @throws IOException - Exce��o levantada caso haja algum erro de IO.
	 * 
	 */
	public void open() throws IOException;
	/**
	 * M�todo que envia uma mensagem ao destino.
	 * 
	 * @param msg Mensagem a ser enviada.
	 * 
	 * @throws IOException - Exce��o levantada caso haja algum erro de IO.
	 * 
	 */
	public void send(Object msg) throws IOException;
	/**
	 * M�todo que recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 * 
	 * @throws ClassNotFoundException - Exce��o levantada caso
	 * @throws IOException - Exce��o levantada caso haja algum erro de IO.
	 * 
	 */
	public Object receive() throws IOException, ClassNotFoundException;
	/**
	 * M�todo que fecha a conex�o.
	 * 
	 */
	public void close();
}
