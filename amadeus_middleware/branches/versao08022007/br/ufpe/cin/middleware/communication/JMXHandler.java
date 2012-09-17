package br.ufpe.cin.middleware.communication;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import br.ufpe.cin.middleware.communication.http.HTTP;
import br.ufpe.cin.middleware.communication.tcp.TCP;
import br.ufpe.cin.middleware.communication.tcp.TCPFile;
import br.ufpe.cin.middleware.communication.udp.UDP;
import br.ufpe.cin.middleware.components.CompressionComponent;
import br.ufpe.cin.middleware.components.CryptographyComponent;
import br.ufpe.cin.middleware.connectors.LogicConnector;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.util.Debug;
import br.ufpe.cin.middleware.util.EChannels;

/**
 * Classe responsável por lidar com os eventos de JMX.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class JMXHandler {
	
	//copia de atributos do configurador
	private int bindPort;
	private int serverPort;
	private boolean isServer;
	private String serverHost;
	private EChannels channel;
	//atributos que estao ligados a JMX
	private MBeanServer mBeanServer;
	private ObjectName configuratorName;
	private LogicConnector logicConnector;
		
	/**
	 * Construtor da classe JMXHandler.
	 * 
	 * @param serverPort - número da porta do lado do servidor
	 * @param bindPort - número da porta do cliente
	 * @param isServer - <code>true</code> se for servidor, <code>false </code>caso contrário.
	 * @param serverHost - host do servidor.
	 * @param channel - tipo de conexão, ver em {@link EChannels}.
	 * @param configuratorName
	 * @param beanServer
	 */
	public JMXHandler(int serverPort, int bindPort, boolean isServer, String serverHost, EChannels channel, 
			ObjectName configuratorName, MBeanServer beanServer) {
		this.bindPort = bindPort;
		this.isServer = isServer;
		this.serverHost = serverHost;
		this.channel = channel;
		this.serverPort = serverPort;
		
		this.setConfiguratorName(configuratorName);
		this.setMBeanServer(beanServer);
	}
	/**
	 * Método que retorna o conector lógico do JMXHandler.
	 * 
	 * @return Retorna o <code>logicConnector</code>.
	 */
	public LogicConnector getLogicConnector() {
		return logicConnector;
	}
	/**
	 * Método que altera o conector lógico do JMXHandler.
	 * 
	 * @param logicConnector o novo <code>logicConnector</code>.
	 */
	public void setLogicConnector(LogicConnector logicConnector) {
		this.logicConnector = logicConnector;
	}
	/**
	 * Metódo que retorna o nome do configurador do JMXHandler.
	 * 
	 * @return Retorna o <code>configuratorName</code>.
	 */
	public ObjectName getConfiguratorName() {
		return configuratorName;
	}
	/**
	 * Método que altera o nome do configurador de JMXHandler.
	 * 
	 * @param configuratorName o novo <code>configuratorName</code>.
	 */
	public void setConfiguratorName(ObjectName configuratorName) {
		this.configuratorName = configuratorName;
	}
	/**
	 * Método que retorna uma referência para o MBeanServer do JMXHandler.
	 * 
	 * @return Retorna o <code>mBeanServer</code>.
	 */
	public MBeanServer getMBeanServer() {
		return mBeanServer;
	}
	/**
	 * Método que altera o MBeanServer do JMXHandler.
	 * 
	 * @param beanServer o novo <code>mBeanServer</code>.
	 */
	public void setMBeanServer(MBeanServer beanServer) {
		mBeanServer = beanServer;
	}
	/**
	 * Método que retorna os canais utilizados pelo JMXHandler.
	 * 
	 * @return Retorna o <code>channel</code>.
	 */
	public EChannels getChannel() {
		return channel;
	}
	/**
	 * Método que altera os canais utilizados pelo JMXHandler.
	 * 
	 * @param channel o novo <code>channel</code>;
	 */
	public void setChannel(EChannels channel) {
		this.channel = channel;
	}
	/**
	 * Inicializa o JMX, seus conectores e seus componentes.
	 */
	public void initializeJMX() {
		try {
//			ObjectName name = new ObjectName(MBeansNames.REMOTE_CALL);
//			this.mBeanServer.registerMBean(new RemoteCallHandler(), name);
			
			if (channel == EChannels.RELIABLE_CONNECTION) {
				initReliable(configuratorName);
			}else if(channel == EChannels.RELIABLE_FILE_CONNECTION){
				initReliableFile(configuratorName);
			}else if (channel == EChannels.NOT_RELIABLE_CONNECTION) {
				initNotReliable(configuratorName);
			} else if (channel == EChannels.WEB_CONNECTION) {
				initWeb(configuratorName);
			} 
			if (logicConnector.getComponents() != null) {
				initComponents(configuratorName);			
			}
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Inicializa os componentes do JMXHandler.
	 * 
	 * @param configuratorName o nome do configurador.
	 */
	public void initComponents(ObjectName configuratorName) {
		ObjectName name = null;
		String[] componentsNames = logicConnector.getComponents();
		
		for (int i = 0; i < componentsNames.length; i++) {
			try {
				name = new ObjectName("components:name=" + componentsNames[i]);
				if (componentsNames[i].equals("CryptographyComponent")) {
					this.mBeanServer.registerMBean(new CryptographyComponent(),name);
				} else if (componentsNames[i].equals("CompressionComponent")) {
					this.mBeanServer.registerMBean(new CompressionComponent(),name);
				}
				/*this.mBeanServer.addNotificationListener(name,configuratorName,null,null);
				this.mBeanServer.addNotificationListener(configuratorName,name,null,null);*/
			} catch (Exception e) {
				Debug.printStack(e);
			}
		}
			
	}
	/**
	 * Inicializa o componente HTTP.
	 * 
	 * @param configuratorName o nome do configurador.
	 */
	public void initWeb(ObjectName configuratorName) {
		ObjectName name = null;
		try {
			name = new ObjectName(MBeansNames.HTTP);
			this.mBeanServer.registerMBean(new HTTP(this.serverHost,""),name);
			/*this.mBeanServer.addNotificationListener(name,configuratorName,null,null);
			this.mBeanServer.addNotificationListener(configuratorName,name,null,null);*/
		} catch (Exception e) {
			Debug.printStack(e);
		}
		
	}
	/**
	 * Inicializa o componente UDP.
	 * 
	 * @param configuratorName o nome do configurador.
	 */
	public void initNotReliable(ObjectName configuratorName) {
		ObjectName name = null;
		
		try {
			name = new ObjectName(MBeansNames.UDP);
			if (this.isServer) {
				this.mBeanServer.registerMBean(new UDP(),name);
			} else {
				this.mBeanServer.registerMBean(new UDP(this.serverHost),name);
			}
			/*this.mBeanServer.addNotificationListener(name,configuratorName,null,null);
			this.mBeanServer.addNotificationListener(configuratorName,name,null,null);*/
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Inicializa o componente TCP.
	 * 
	 * @param configuratorName o nome do configurador.
	 */
	public void initReliable(ObjectName configuratorName) {
		ObjectName name = null;
		
		try {
			if (this.isServer) {
				name = new ObjectName(MBeansNames.SERVER_TCP);
				this.mBeanServer.registerMBean(new TCP(), name);
			} else {
				name = new ObjectName(MBeansNames.CLIENT_TCP);
				this.mBeanServer.registerMBean(new TCP(this.serverHost,this.serverPort,this.bindPort),name);
			}
			/*this.mBeanServer.addNotificationListener(name,configuratorName,null,null);
			this.mBeanServer.addNotificationListener(configuratorName,name,null,null);*/
		} catch (Exception e) {
			Debug.printStack(e);
		}
		
	}
	/**
	 * Inicializa o componente TCP (para transferência de arquivos).
	 * 
	 * @param configuratorName o nome do configurador.
	 */
	public void initReliableFile(ObjectName configuratorName) {
		ObjectName name = null;
		
		try {
			if (this.isServer) {
				name = new ObjectName(MBeansNames.SERVER_TCP_FILE);
				this.mBeanServer.registerMBean(new TCPFile(), name);
			} else {
				name = new ObjectName(MBeansNames.CLIENT_TCP_FILE);
				this.mBeanServer.registerMBean(new TCPFile(this.serverHost,this.serverPort,this.bindPort),name);
			}
			this.mBeanServer.addNotificationListener(name,configuratorName,null,null);
			this.mBeanServer.addNotificationListener(configuratorName,name,null,null);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método que passa as operações através das portas dos componentes.
	 * 
	 * @param operationName nome da operação a ser realizada.
	 * @param obj objeto que receberá a operação.
	 * @return objeto com a operação já inserida.
	 */
	public Object passThroughComponents(String operationName, Object obj) {
		Object returnedObj = null;
		Object data[];
		String sig[];
		ObjectName name = null;
		String[] usedComponents = this.getLogicConnector().getComponents();
		
		if (usedComponents != null) { //verify if exists some components
			//iterates over components array
			for (int i = 0; i < usedComponents.length; i++) {
				try {
					if (usedComponents[i].equals("CryptographyComponent")) {
						name = new ObjectName(MBeansNames.CRYPTOGRAPHY_COMPONENT);
						data = new Object[2];
						sig = new String[2];
						data[0] = obj;
						data[1] = operationName;
						sig[0] = Object.class.getName();
						sig[1] = String.class.getName();
						this.getMBeanServer().invoke(name,"writeInPort",data,sig);
						data = null;
						sig = null;
						obj = this.getMBeanServer().invoke(name,"readOutPort",data,sig);
						/*if (operationName.equals("send") || operationName.equals("sendTo") || operationName.equals("sendToAll")) {														
							data[1] = "encrypt";
							this.getMBeanServer().invoke(name,"writeInPort",data,sig);
							data = null;
							sig = null;
							obj = this.getMBeanServer().invoke(name,"readOutPort",data,sig);
						} else if (operationName.equals("receive")) {
							data[1] = "decrypt";
							this.getMBeanServer().invoke(name,"writeInPort",data,sig);
							data = null;
							sig = null;
							obj = this.getMBeanServer().invoke(name,"readOutPort",data,sig);
						}*/
						
					} else if (usedComponents[i].equals("CompressionComponent")) {
						name = new ObjectName(MBeansNames.COMPRESSION_COMPONENT);
						data = new Object[2];
						sig = new String[2];
						data[0] = obj;
						data[1] = operationName;
						sig[0] = Object.class.getName();
						sig[1] = String.class.getName();
						this.getMBeanServer().invoke(name,"writeInPort",data,sig);
						data = null;
						sig = null;
						obj = this.getMBeanServer().invoke(name,"readOutPort",data,sig);
						
						/*if (operationName.equals("send") || operationName.equals("sendTo") || operationName.equals("sendToAll")) {							
							data[1] = "marshalling";
							this.getMBeanServer().invoke(name,"writeInPort",data,sig);
							data = null;
							sig = null;
							obj = this.getMBeanServer().invoke(name,"readOutPort",data,sig);
						} else if (operationName.equals("receive")) {
							data[1] = "unMarshalling";
							this.getMBeanServer().invoke(name,"writeInPort",data,sig);
							data = null;
							sig = null;
							obj = this.getMBeanServer().invoke(name,"readOutPort",data,sig);
						}*/
					}
				} catch (Exception e) {
					Debug.printStack(e);
				}
			}
		}
		returnedObj = obj;
		return returnedObj;
	}
	/**
	 * Método que envia um objeto, através de um host e de uma porta.
	 * (método exclusivo do cliente)
	 *   
	 * @param msg objeto a ser enviado.
	 * @param destinationIP endereço de IP do destino.
	 * @param destinationPort porta do destino.
	 */
	public void send(Object msg, String destinationIP, int destinationPort) {
		EChannels connector = this.channel;
		ObjectName name = null;
		
		try {
			if (connector == EChannels.RELIABLE_CONNECTION) {
				name = new ObjectName(MBeansNames.CLIENT_TCP);
				Object data[] = { msg };
				String sig[] = { Object.class.getName()};
				this.mBeanServer.invoke(name,"send",data,sig);
			}else if(connector == EChannels.RELIABLE_FILE_CONNECTION){
				name = new ObjectName(MBeansNames.CLIENT_TCP_FILE);
				Object data[] = { msg };
				String sig[] = { Object.class.getName()};
				this.mBeanServer.invoke(name,"send",data,sig);
			}else if (connector == EChannels.NOT_RELIABLE_CONNECTION) {
				name = new ObjectName(MBeansNames.UDP);			
				Object[] data = { msg, destinationIP, destinationPort };
				String[] sig = { Object.class.getName(), String.class.getName(), int.class.getName() };
				this.mBeanServer.invoke(name,"send", data, sig);
			} else if (connector == EChannels.WEB_CONNECTION) {
				name = new ObjectName(MBeansNames.HTTP);
				Object data[] = { msg };
				String sig[] = { Object.class.getName() };
				this.mBeanServer.invoke(name,"send",data,sig);
			}
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método que envia um objeto, através de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param msg objeto a ser enviado.
	 * @param destinationIP endereço de IP do destino.
	 * @param destinationPort porta do destino.
	 */
	public void sendTo(Object msg, String destinationIP, int destinationPort) {
		EChannels channel = this.channel;
		ObjectName name = null;
		
		try {
			if (channel == EChannels.RELIABLE_CONNECTION) {
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP);
				} else {
					name = new ObjectName(MBeansNames.CLIENT_TCP);
				}
				Object data[] = { msg, destinationIP, destinationPort };
				String sig[] = { Object.class.getName(), String.class.getName(), int.class.getName() };
				this.mBeanServer.invoke(name,"sendTo",data,sig);
			}else if (channel == EChannels.RELIABLE_FILE_CONNECTION) {
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP_FILE);
				} else {
					name = new ObjectName(MBeansNames.CLIENT_TCP_FILE);
				}
				Object data[] = { msg, destinationIP, destinationPort };
				String sig[] = { Object.class.getName(), String.class.getName(), int.class.getName() };
				this.mBeanServer.invoke(name,"sendTo",data,sig);
			}
			
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Método que envia um objeto para todos os clientes.
	 * (método exclusivo do servidor).
	 * 
	 * @param msg objeto a ser enviado.
	 */
	public void sendToAll(Object msg) {
		EChannels channel = this.channel;
		ObjectName name = null;
		
		try {
			if (channel == EChannels.RELIABLE_CONNECTION) {
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP);
				} else {
					name = new ObjectName(MBeansNames.CLIENT_TCP);
				}
				Object data[] = { msg };
				String sig[] = { Object.class.getName() };
				this.mBeanServer.invoke(name,"sendToAll",data,sig);
			}
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}

	/**
	 * Fecha uma conexão. 
	 * 
	 * @param destinationIP endereço IP do destino.
	 * @param destinationPort porta do destino.
	 */
	public void close(String destinationIP, int destinationPort) {
		EChannels channel = this.channel;
		ObjectName name = null;
		
		try {
			if (channel == EChannels.RELIABLE_CONNECTION) {
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP);
				} else {
					name = new ObjectName(MBeansNames.CLIENT_TCP);
				}
			} else if (channel == EChannels.NOT_RELIABLE_CONNECTION) {
				name = new ObjectName(MBeansNames.UDP);
			} else if (channel == EChannels.WEB_CONNECTION) {
				name = new ObjectName(MBeansNames.HTTP);
			}
			Object data[] = { destinationIP, destinationPort };
			String sig[] = { String.class.getName(), int.class.getName() };
			this.mBeanServer.invoke(name,"close",data,sig);
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Aguarda por uma conexão.
	 * 
	 */
	public void listen() {
		EChannels channel = this.channel;
		ObjectName name = null;
		
		try {
			if (channel == EChannels.RELIABLE_CONNECTION){
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP);
					this.mBeanServer.invoke(name,"listen",null,null);
				}
			}else if(channel == EChannels.RELIABLE_FILE_CONNECTION){
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP_FILE);
					this.mBeanServer.invoke(name,"listen",null,null);
				}
			}
		} catch (Exception e) {
			Debug.printStack(e);
		}
	}
	/**
	 * Recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 */
	public Message receive() {
		Message returnedMessage = null;
		EChannels channel = this.channel;
		ObjectName name = null;
		try {
			if (channel == EChannels.RELIABLE_CONNECTION) {
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP);					
				} else {
					name = new ObjectName(MBeansNames.CLIENT_TCP);					
				}
				returnedMessage = (Message)this.mBeanServer.invoke(name,"receive",null,null);
			}else if(channel == EChannels.RELIABLE_FILE_CONNECTION){
				if (isServer) {
					name = new ObjectName(MBeansNames.SERVER_TCP_FILE);					
				} else {
					name = new ObjectName(MBeansNames.CLIENT_TCP_FILE);					
				}
				returnedMessage = (Message)this.mBeanServer.invoke(name,"receive",null,null);
			}else if (channel == EChannels.NOT_RELIABLE_CONNECTION) {
				name = new ObjectName(MBeansNames.UDP);
				returnedMessage = (Message)this.mBeanServer.invoke(name,"receive",null,null);				
			} else if (channel ==  EChannels.WEB_CONNECTION) {
				name = new ObjectName(MBeansNames.HTTP);
				returnedMessage = (Message)this.mBeanServer.invoke(name,"receive",null,null);	
			}
			
		} catch (Exception e) {
			Debug.printStack(e);
		}		
		return returnedMessage;
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
//		ObjectName name = new ObjectName(MBeansNames.REMOTE_CALL);
//		Object data[] = {rc};
//		String sig[] = {RemoteCall.class.getName()};
//		RemoteCallReply rcr = (RemoteCallReply)this.mBeanServer.invoke(name, "checkMessage", data, sig);
//		return rcr;
//	}
	
}
