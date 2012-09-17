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
 * Classe que representa as opera��es dispon�veis no middleware para o usu�rio.
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
	 * @param serverHost Endere�o do destino.
	 * @param serverPort Porta local na qual o servidor est� registrado.
	 * @param bindPort Porta local na qual o cliente est� registrado.
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
	 * M�todo respons�vel por ler do arquivo de configura��o qual o canal de comunica��o
	 * o usu�rio deseja utilizar.
	 */
	public void open() {
		try {
			this.mServer.invoke(this.getConfiguratorName(),"open",null,null);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * M�todo respons�vel por aguardar conex�es de clientes, do lado do servidor. 
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
	 * M�todo respons�vel por enviar a mensagem para o destino.
	 * 
	 * @param msg Par�metro que representa o objeto que ser� enviado.
	 * @param destinationIP Par�metro que representa o endere�o IP do destino (usado na transmiss�o via UDP).
	 * @param destinationPort Par�metro que representa a porta do destino (usado na transmiss�o via UDP).
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
	 * M�todo respons�vel por enviar a mensagem para o destino.
	 * 
	 * @param msg Par�metro que representa o objeto que ser� enviado.
	 * @param destinationIP Par�metro que representa o endere�o IP do destino.
	 * @param destinationPort Par�metro que representa a porta do destino.
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
	 * M�todo respons�vel por enviar a mensagem para todos os clientes conectados ao servidor.
	 * 
	 * @param msg Par�metro que representa o objeto que ser� enviado.
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
	 * M�todo respons�vel por receber mensagens.
	 * 
	 * @return O objeto enviado pelo outro lado da comunica��o.
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
		
		//checa se � um pedido remoto.
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
	 * M�todo respons�vel por fechar uma conex�o estabelecida previamente.
	 * 
	 * @param destination - Par�metro que representa o IP da conex�o a ser fechada.
	 * @param destinationPort - Par�metro que representa a porta da conex�o a ser fechada.
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
	 * Retorna o nome do <code>Configurator</code> que est� sendo
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
//	 * M�todo respons�vel por invocar uma chamada remota.
//	 * 
//	 * @param rc - um objeto <code>RemoteCall</code>, que cont�m informa��es sobre a chamada
//	 * 			   remota a ser executada.
//	 * @return um objeto <code>RemoteCallReply</code>, representando o retorno do
//	 * 			   m�todo remoto chamado.
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
