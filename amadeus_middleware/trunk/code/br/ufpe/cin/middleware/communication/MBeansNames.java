package br.ufpe.cin.middleware.communication;

/**
 * Classe que contém todos os nomes utilizados pelo MBeanServer.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class MBeansNames {
	
	public static final String WEB_CONNECTOR = "connectors:name=WebConnector";
	public static final String NOTRELIABLE_CONNECTOR = "connectors:name=NotReliableConnector";
	public static final String RELIABLE_CONNECTOR = "connectors:name=ReliableConnector";
	public static final String SERVER_TCP = "communication:name=ServerTCP";
	public static final String CLIENT_TCP = "communication:name=ClientTCP";
	public static final String SERVER_TCP_FILE = "communication:name=ServerTCPFile";
	public static final String CLIENT_TCP_FILE = "communication:name=ClientTCPFile";
	public static final String COMPRESSION_COMPONENT = "components:name=CompressionComponent";
	public static final String CRYPTOGRAPHY_COMPONENT = "components:name=CryptographyComponent";
	public static final String HTTP = "communications:name=HTTP";
	public static final String UDP = "communication:name=UDP";
	//public static final String REMOTE_CALL = "components:name=RemoteCallHandler";

}
