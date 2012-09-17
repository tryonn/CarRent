package br.ufpe.cin.middleware.principal;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import br.ufpe.cin.middleware.communication.Configurator;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.objects.controlMessages.TCPControlMessage;
import br.ufpe.cin.middleware.util.Debug;
/**
 * Classe que representa as operações disponíveis no middleware para o usuário.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class AmadeusMiddleware {
	
	private MBeanServer mServer;
	private ObjectName configuratorName;
	private static final String CONFIGURATOR_NAME =  "communication:name=Configurator";
		
	/**
	 * Construtor da classe AmadeusMiddleware usado no lado do servidor.
	 * 
	 */
	public AmadeusMiddleware() {
		try {
			ObjectName name = this.getConfiguratorName();
			this.mServer = MBeanServerFactory.createMBeanServer();
			this.mServer.registerMBean(new Configurator(true,mServer,name),name);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Construtor da classe AmadeusMiddleware usado no lado do cliente.
	 * 
	 * @param serverHost Endereço do destino.
	 * @param serverPort Porta local na qual o servidor está registrado.
	 * @param bindPort Porta local na qual o cliente está registrado.
	 */
	public AmadeusMiddleware(String serverHost, Integer serverPort, Integer bindPort) {
		try {
			ObjectName name = this.getConfiguratorName();
			this.mServer = MBeanServerFactory.createMBeanServer();
			this.mServer.registerMBean(new Configurator(false,serverHost,bindPort,serverPort,mServer,name),name);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método responsável por ler do arquivo de configuração qual o canal de comunicação
	 * o usuário deseja utilizar.
	 */
	public void open() {
		try {
			this.mServer.invoke(this.getConfiguratorName(),"open",null,null);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método responsável por aguardar conexões de clientes, do lado do servidor. 
	 *
	 */	
	public void listen() {
		try {
			this.mServer.invoke(this.getConfiguratorName(),"listen",null,null);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método responsável por enviar a mensagem para o destino.
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @param destinationIP Parâmetro que representa o endereço IP do destino (usado na transmissão via UDP).
	 * @param destinationPort Parâmetro que representa a porta do destino (usado na transmissão via UDP).
	 * 
	 */
	public void send(Object msg, String destinationIP, int destinationPort) {
		try {
			Object data[] =  { msg, destinationIP, destinationPort };
			String sig[] =  { Object.class.getName(), String.class.getName(), int.class.getName()};
			this.mServer.invoke(this.getConfiguratorName(),"send",data,sig);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método responsável por enviar a mensagem para o destino.
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 * @param destinationIP Parâmetro que representa o endereço IP do destino.
	 * @param destinationPort Parâmetro que representa a porta do destino.
	 * 
	 */
	public void sendTo(Object msg, String destinationIP, int destinationPort) {
		try {
			Object data[] =  { msg, destinationIP, destinationPort };
			String sig[] =  { Object.class.getName(), String.class.getName(), int.class.getName()};
			this.mServer.invoke(this.getConfiguratorName(),"sendTo",data,sig);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método responsável por enviar a mensagem para todos os clientes conectados ao servidor.
	 * 
	 * @param msg Parâmetro que representa o objeto que será enviado.
	 */
	public void sendToAll(Object msg) {
		try {
			Object data[] = { msg };
			String sig[] = { Object.class.getName() };
			this.mServer.invoke(this.getConfiguratorName(),"sendToAll",data,sig);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método responsável por receber mensagens.
	 * 
	 * @return O objeto enviado pelo outro lado da comunicação.
	 * @throws NullPointerException 
	 * @throws ReflectionException 
	 * @throws MBeanException 
	 * @throws MalformedObjectNameException 
	 * @throws InstanceNotFoundException 
	 * @throws ConnectionClosedException 
	 */
	public Message receive() throws NotConnectedException {
		Message msg = null;
		Object returnedMsg = null;
		try {
			returnedMsg = this.mServer.invoke(this.getConfiguratorName(),"receive",null,null);
		} catch (Exception e) {
			Debug.printStack(e);
		}
		msg = (Message)returnedMsg;
		
		if (msg.getContent() instanceof TCPControlMessage) {
			this.close(msg.getSourceIP(), msg.getSourcePort());
			throw new NotConnectedException();
		}
		
		//checa se é um pedido remoto.
/*		if(msg.getContent() instanceof RemoteCall){
			RemoteCall rc = (RemoteCall)msg.getContent();
			Object data[] = {rc};
			String sig[] ={RemoteCall.class.getName()};
			RemoteCallReply rcr = (RemoteCallReply) this.mServer.invoke(this.getConfiguratorName(), "invoke", data, sig);
			msg.setContent(rcr);
			this.sendTo(rcr, msg.getSourceIP(), msg.getSourcePort());
		}*/
		return msg;
	}
	/**
	 * Método responsável por fechar uma conexão estabelecida previamente.
	 * 
	 * @param destination - Parâmetro que representa o IP da conexão a ser fechada.
	 * @param destinationPort - Parâmetro que representa a porta da conexão a ser fechada.
	 */
	public void close(String destination, int destinationPort) {
		try {
			Object data[] = { destination, destinationPort };
			String sig[] = { String.class.getName(), int.class.getName() };
			this.mServer.invoke(this.getConfiguratorName(),"close",data,sig);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Retorna o nome do <code>Configurator</code> que está sendo
	 * utilizado por esta classe.
	 * 
	 * @return um <code>ObjectName</code> que representa o nome do <code>Configurator</code>;
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 */
	private ObjectName getConfiguratorName() throws MalformedObjectNameException, NullPointerException {
		if (configuratorName == null) {
			this.configuratorName = new ObjectName(CONFIGURATOR_NAME);		
		}
		return configuratorName;
	}
	
//	/**
//	 * Método responsável por invocar uma chamada remota.
//	 * 
//	 * @param rc - um objeto <code>RemoteCall</code>, que contém informações sobre a chamada
//	 * 			   remota a ser executada.
//	 * @return um objeto <code>RemoteCallReply</code>, representando o retorno do
//	 * 			   método remoto chamado.
//	 */
//	public RemoteCallReply invoke(RemoteCall rc){
//		RemoteCallReply rcr = null;
//		this.send(rc, null, -1);
//		try {
//			Message m = this.receive();
//			rcr = (RemoteCallReply) m.getContent();
//		} catch (Exception e) {
//			Debug.printStack(e);
//		}
//		return rcr;
//	}
}
