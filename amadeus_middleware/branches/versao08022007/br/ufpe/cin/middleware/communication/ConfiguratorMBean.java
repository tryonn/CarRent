package br.ufpe.cin.middleware.communication;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.OperationNotImplemmentedException;
/**
 * Interface que representa as opera��es do Configurador que s�o vis�veis para o mundo externo.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface ConfiguratorMBean {
	/**
	 * M�todo respons�vel por ler do arquivo de configura��o qual o canal de comunica��o
	 * o usu�rio deseja utilizar.
	 * 
	 * @throws IOException - Exce��o levantada em caso de erro de IO.
	 */
	public void open() throws IOException;
	/**
	 * M�todo respons�vel por aguardar uma conex�o.
	 * 
	 * @throws OperationNotImplemmentedException - Exce��o levantada caso alguma opera��o n�o existente for requisitada.
	 */
	public void listen() throws OperationNotImplemmentedException;
	/**
	 * M�todo respons�vel por enviar a mensagem para o destino (m�todo exclusivo do cliente).
	 * 
	 * @param msg Par�metro que representa o objeto que ser� enviado.
	 * @param destinationIP Par�metro que representa o endere�o IP do destino (usado na transmiss�o via UDP).
	 * @param destinationPort Par�metro que representa a porta do destino (usado na transmiss�o via UDP).
	 * 
	 * @throws IOException - Exce��o levantada em caso de erro de IO.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException;
	/**
	 * M�todo respons�vel por enviar a mensagem para o destino.
	 * 
	 * @param msg Par�metro que representa o objeto que ser� enviado.
	 * @param destinationIP Par�metro que representa o endere�o IP do destino (usado na transmiss�o via UDP).
	 * @param destinationPort Par�metro que representa a porta do destino (usado na transmiss�o via UDP).
	 * 
	 * @throws IOException - Exce��o levantada em caso de erro de IO.
	 * @throws OperationNotImplemmentedException - Exce��o levantada caso alguma opera��o n�o existente for requisitada.
	 */
	public void sendTo(Object msg, String destinationIP, int destinationPort) throws IOException, OperationNotImplemmentedException;
	/**
	 * M�todo respons�vel por enviar a mensagem para todos os clientes conectados (m�todo exclusivo do servidor).
	 * 
	 * @param msg Par�metro que representa o objeto que ser� enviado.
	 * @throws OperationNotImplemmentedException - Exce��o levantada caso alguma opera��o n�o existente for requisitada.
	 */
	public void sendToAll(Object msg) throws OperationNotImplemmentedException;
	/**
	 * M�todo respons�vel por receber a mensagem.
	 * 
	 * @return Object Retorna o objeto enviado pelo outro lado da comunica��o
	 * @throws IOException - Exce��o levantada em caso de erro de IO.
	 */
	public Object receive() throws IOException;
	/**
	 * M�todo respons�vel por fechar a conex�o estabelecida previamente.
	 * 
	 * @throws OperationNotImplemmentedException - Exce��o levantada caso alguma opera��o n�o existente for requisitada.
	 */
	public void close(String destination, int destinationPort) throws OperationNotImplemmentedException;
//	/**
//	 * M�todo que invoca remotamente uma opera��o.
//	 * 
//	 * @param rc informa��es sobre a opera��o a ser invocada.
//	 * @return Retorna uma resposta com informa��es de retorno da opera��o invocada.
//	 * @throws MalformedObjectNameException
//	 * @throws NullPointerException
//	 * @throws InstanceNotFoundException
//	 * @throws MBeanException
//	 * @throws ReflectionException
//	 */
//	public RemoteCallReply invoke(RemoteCall rc) throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException, MBeanException, ReflectionException;

}