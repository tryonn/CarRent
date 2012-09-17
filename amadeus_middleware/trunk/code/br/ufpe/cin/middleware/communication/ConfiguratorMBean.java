package br.ufpe.cin.middleware.communication;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.OperationNotImplemmentedException;
/**
 * Interface que representa as operações do Configurador que são visíveis para o mundo externo.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public interface ConfiguratorMBean {
	/**
	 * Método responsável por ler do arquivo de configuração qual o canal de comunicação
	 * o usuário deseja utilizar.
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public void open() throws IOException;
	/**
	 * Método responsável por aguardar uma conexão.
	 * 
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void listen() throws OperationNotImplemmentedException;
	/**
	 * Método responsável por enviar a mensagem para o destino (método exclusivo do cliente).
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @param destinationIP Parâmetro que representa o endereço IP do destino (usado na transmissão via UDP).
	 * @param destinationPort Parâmetro que representa a porta do destino (usado na transmissão via UDP).
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException;
	/**
	 * Método responsável por enviar a mensagem para o destino.
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @param destinationIP Parâmetro que representa o endereço IP do destino (usado na transmissão via UDP).
	 * @param destinationPort Parâmetro que representa a porta do destino (usado na transmissão via UDP).
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void sendTo(Object msg, String destinationIP, int destinationPort) throws IOException, OperationNotImplemmentedException;
	/**
	 * Método responsável por enviar a mensagem para todos os clientes conectados (método exclusivo do servidor).
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void sendToAll(Object msg) throws OperationNotImplemmentedException;
	/**
	 * Método responsável por receber a mensagem.
	 * 
	 * @return Object Retorna o objeto enviado pelo outro lado da comunicação
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public Object receive() throws IOException;
	/**
	 * Método responsável por fechar a conexão estabelecida previamente.
	 * 
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void close(String destination, int destinationPort) throws OperationNotImplemmentedException;
//	/**
//	 * Método que invoca remotamente uma operação.
//	 * 
//	 * @param rc informações sobre a operação a ser invocada.
//	 * @return Retorna uma resposta com informações de retorno da operação invocada.
//	 * @throws MalformedObjectNameException
//	 * @throws NullPointerException
//	 * @throws InstanceNotFoundException
//	 * @throws MBeanException
//	 * @throws ReflectionException
//	 */
//	public RemoteCallReply invoke(RemoteCall rc) throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException, MBeanException, ReflectionException;

}