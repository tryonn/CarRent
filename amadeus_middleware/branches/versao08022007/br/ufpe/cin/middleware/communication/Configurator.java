package br.ufpe.cin.middleware.communication;

import java.io.IOException;

import javax.management.MBeanServer;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

import br.ufpe.cin.middleware.connectors.LogicConnector;
import br.ufpe.cin.middleware.exceptions.OperationNotImplemmentedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.util.EChannels;
import br.ufpe.cin.middleware.util.XMLParser;
/**
 * Classe que representa o configurador do middleware. Essa classe tem como responsabilidade
 * gerenciar as notificações trocadas entre todos os MBeans do middleware.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class Configurator extends NotificationBroadcasterSupport implements ConfiguratorMBean {
	
	private int serverPort; //porta remota
	private int bindPort; //porta local
	private boolean isServer;
	
	private EChannels channel;
	private JMXHandler jmxHandler;
	/**
	 * Construtor da classe Configurador usado para o lado do cliente.
	 * 
	 * @param isServer Parâmetro que indica se o configurador está sendo usado no lado do servidor ou do cliente.
	 * @param serverHost Parâmetro que representa o endereço do destino, no caso do TCP, o endereço do servidor.
	 * @param serverPort Porta na qual o servidor esta registrado.
	 * @param bindPort Porta na qual o cliente esta registrado.
	 * @param mServer Servidor MBean correspondente ao configurador.
	 * @param name Nome do configurador.
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public Configurator(boolean isServer, String serverHost, Integer bindPort, Integer serverPort, MBeanServer mServer, ObjectName name) throws IOException {
		this.isServer = isServer;
		this.serverPort = serverPort.intValue();
		if (bindPort != null) {
			this.bindPort = bindPort.intValue();
		} else {
			this.bindPort = -1;
		}				
		this.jmxHandler = new JMXHandler(this.serverPort,this.bindPort,this.isServer,serverHost,this.channel,name,mServer);		
	}
	/**
	 * Construtor da classe Configurador usado para conexões TCP do lado do servidor
	 * 
	 * @param isServer Parâmetro que indica se o configurador está sendo usado no lado do servidor ou do cliente.
	 * @param mServer Servidor MBean correspondente ao configurador.
	 * @param name Nome do configurador. 
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public Configurator(boolean isServer, MBeanServer mServer, ObjectName name) throws IOException {
		this.isServer = isServer;
		this.bindPort = -1;				
		this.jmxHandler = new JMXHandler(this.serverPort, this.bindPort,this.isServer,null,this.channel,name,mServer);				
	}
	/**
	 * Método responsável por ler do arquivo de configuração qual o canal de comunicação
	 * o usuário deseja utilizar.
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public void open() throws IOException {
		this.readFile(); //lendo o arquivo de configuracao
		String temp = jmxHandler.getLogicConnector().getConnector();
		
		if (temp.equals("Reliable")) {
			channel = EChannels.RELIABLE_CONNECTION;
		}else if(temp.equals("Reliable_File")){
			channel = EChannels.RELIABLE_FILE_CONNECTION;
		}else if (temp.equals("NotReliable")) {
			channel = EChannels.NOT_RELIABLE_CONNECTION;
		} else if (temp.equals("Web")) {
			channel = EChannels.WEB_CONNECTION;
		}
		jmxHandler.setChannel(this.channel);
		jmxHandler.initializeJMX();
	}
	/**
	 * Método responsável por aguardar uma conexão.
	 * 
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void listen() throws OperationNotImplemmentedException {
		String operation = "listen";
		String protocol = this.getProtocolByChannel();
		if ((this.channel == EChannels.RELIABLE_CONNECTION)||(this.channel == EChannels.RELIABLE_FILE_CONNECTION)) {
			if (isServer) {
				jmxHandler.listen();
			} else {
				//voltar**
				protocol = "ClientTCP";
			}
		} else  {
			throw new OperationNotImplemmentedException(operation,protocol);
		}
	}
	/**
	 * Método responsável por enviar a mensagem para o destino (método exclusivo do cliente).
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @param destinationIP Parâmetro que representa o endereço IP do destino (usado na transmissão via UDP).
	 * @param destinationPort Parâmetro que representa a porta do destino (usado na transmissão via UDP).
	 * 
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException {
		Object temp = jmxHandler.passThroughComponents("send",msg);
		//se o "temp" vier nulo significa que nao passou por nenhum componente...
		if (temp != null) {
			msg = temp;
		}
		jmxHandler.send(msg,destinationIP,destinationPort);
	}
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
	public void sendTo(Object msg, String destinationIP, int destinationPort) throws IOException, OperationNotImplemmentedException {
		String operation = "sendTo";
		String protocol = this.getProtocolByChannel();
		if ((this.channel == EChannels.RELIABLE_CONNECTION)||(this.channel == EChannels.RELIABLE_FILE_CONNECTION)) {
			Object temp = jmxHandler.passThroughComponents("sendTo",msg);
			if (temp != null) {
				msg = temp;
			}
			jmxHandler.sendTo(msg,destinationIP,destinationPort);
		} else {
			throw new OperationNotImplemmentedException(operation,protocol);
		}
	}
	/**
	 * Método responsável por enviar a mensagem para todos os clientes conectados (método exclusivo do servidor).
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void sendToAll(Object msg) throws OperationNotImplemmentedException {
		String operation = "sendToAll";
		String protocol = this.getProtocolByChannel();
		
		if (this.channel == EChannels.RELIABLE_CONNECTION) {
			Object temp = jmxHandler.passThroughComponents("sendToAll",msg);
			if (temp != null) {
				msg = temp;
			}
			jmxHandler.sendToAll(msg);
		} else {
			throw new OperationNotImplemmentedException(operation,protocol);
		}
	}
	/**
	 * Método responsável por receber a mensagem.
	 * 
	 * @return Object Retorna o objeto enviado pelo outro lado da comunicação
	 * @throws IOException - Exceção levantada em caso de erro de IO.
	 */
	public Message receive() throws IOException {
		Message returnedMessage = jmxHandler.receive();
		//Passing through the components...
		Object temp = jmxHandler.passThroughComponents("receive",returnedMessage.getContent());		
		if (temp != null) { 
			returnedMessage.setContent(temp);
		}
		return returnedMessage; 
	}
	/**
	 * Método responsável por fechar a conexão estabelecida previamente.
	 * 
	 * @throws OperationNotImplemmentedException - Exceção levantada caso alguma operação não existente for requisitada.
	 */
	public void close(String destination, int destinationPort) throws OperationNotImplemmentedException {
		jmxHandler.close(destination, destinationPort);
	}
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
//	public RemoteCallReply invoke(RemoteCall rc) throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException, MBeanException, ReflectionException{
//		return jmxHandler.invoke(rc);
//	}
	/**
	 * Método que retorna um nome de protocolo, baseado no canal selecionado para comunicação.
	 * 
	 * @return Retorna o nome do protocolo.
	 */
	private String getProtocolByChannel() {
		String protocol = null;
		
		if ((channel == EChannels.RELIABLE_CONNECTION)||(channel == EChannels.RELIABLE_FILE_CONNECTION)) {
			protocol = "TCP";
		} else if (channel == EChannels.NOT_RELIABLE_CONNECTION) {
			protocol = "UDP";
		} else if (channel == EChannels.WEB_CONNECTION) {
			protocol = "HTTP";
		}
		return protocol;
	}
	/**
	 * Método que lê o arquivo de configuração do middleware e cria o Configuration do Configurator.
	 * 
	 * @throws IOException - Exceção levantada caso ocorra algum erro de IO.
	 */
	private void readFile() throws IOException {
		//TODO Método alterado de INI para XML.
		String connector = XMLParser.CONNECTOR;
		String[] componentsNames = XMLParser.COMPONENTS;
//		BufferedReader buffer = new BufferedReader(new FileReader("config.ini"));
//		String connector = buffer.readLine();
//		int numberOfComponents = Integer.parseInt(buffer.readLine());
//		String[] componentsNames;
//		if (numberOfComponents != 0) {
//			componentsNames = new String[numberOfComponents];
//			for (int i = 0; i < componentsNames.length; i++) {
//				componentsNames[i] =  buffer.readLine();
//			}
//		} else  {
//			componentsNames = null;
//		}		
//		buffer.close();
		jmxHandler.setLogicConnector(new LogicConnector(connector,componentsNames));
	}
	
}
