package br.ufpe.cin.middleware.communication.http;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.util.Port;

/**
 * Classe responsável por implementar o comportamento da transmissão de dados
 * fazendo uso do protocolo HTTP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class HTTP implements HTTPMBean {

	private static final String PROTOCOL = "HTTP";
	private static final int DEFAULT_PORT = 80;

	private String host; //Endereco que eu vou me conectar
	private String path;
	private String localAddress;

	private URL url; //URL
	private HttpURLConnection connection; //Conexão

	//portas
	private Port inPort = new Port(this.getClass().getSimpleName() + "_inPort");
	private Port outPort = new Port(this.getClass().getSimpleName() + "_outPort");
	private boolean ready = false;

	/**
	 * Construtor da classe HTTP.
	 * Alterado verifivar configurador q tb foi alterado.
	 * @param host Endereço no qual o HTTP irá se conectar.
	 * 
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 */
	public HTTP(String host, String path) throws IOException {
		this.host = host;
		this.path = path;
		this.localAddress = InetAddress.getLocalHost().getHostAddress();
		this.url = new URL(PROTOCOL,host,path);
	}
	/**
	 * Método que abre a conexão.
	 * 
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 * 
	 */
	public void open() throws IOException {
		//Abre a conexão
		this.connection = (HttpURLConnection) this.url.openConnection();
		//Seta os parametros da conexão
		this.connection.setDoInput(true);
		this.connection.setDoOutput(true);
		//Conecta-se
		this.connection.connect();
	}
	/**
	 * Método que envia uma mensagem ao destino.
	 * 
	 * @param msg Mensagem a ser enviada.
	 * 
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 * 
	 */
	public void send(Object msg) throws IOException {
		this.open();
		Message m = new Message(PROTOCOL,msg,host,localAddress,DEFAULT_PORT,DEFAULT_PORT);
		ObjectOutputStream out = new ObjectOutputStream(this.connection.getOutputStream());
		out.writeObject(m);
		out.flush();
		this.close();
	}
	/**
	 * Método que recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 * 
	 * @throws ClassNotFoundException - Exceção levantada caso
	 * @throws IOException - Exceção levantada caso haja algum erro de IO.
	 * 
	 */
	public Message receive() throws IOException, ClassNotFoundException {
		this.open();
		//String contentType = this.connection.getContentType();
		InputStream in = this.connection.getInputStream();
		DataInputStream data = new DataInputStream(in);
		byte[] array = new byte[data.available()];
		data.readFully(array);
		//analizar o tipo de conteudo
		//text , image, application
		String ret = new String(array);
		Message m = new Message(PROTOCOL,ret,localAddress,host,DEFAULT_PORT,DEFAULT_PORT);
		this.close();
		return m;
	}
	/**
	 * Método que fecha a conexão.
	 * 
	 */
	public void close() {
		this.connection.disconnect();
	}
	/**
	 * Escreve um objeto na porta de entrada deste componente.
	 * 
	 * @param method - Método a ser utilizado.
	 * @param obj - Objeto a ser escrito.
	 * @throws IOException
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public void writeInPort(Object obj, String method) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		this.inPort.write(method);
		this.inPort.write(obj);
		this.process();
	}
	/**
	 * Lê um objeto da porta de saída deste componente.
	 * 
	 * @return Retorna o objeto lido da porta de saída.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object readOutPort() throws IOException, ClassNotFoundException{
		while(!this.ready){}
		this.ready = false;
		Object returnObj = this.outPort.read(0); 
		this.outPort.clear();
		return returnObj;
	}
	/**
	 * Faz o processamento desta classe, tratando portas de saída e de entrada.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private void process()  throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String method;
		try {
			method = (String) this.inPort.read(0);
			if (method.equals("open")){
				this.open();
			}else if(method.equals("send")){
				this.send(this.inPort.read(1));
			}else if(method.equals("receive")){
				Message msg = null;
				msg = this.receive();
				this.outPort.write(msg);
			}else if(method.equals("close")){
				this.close();
			}
			this.inPort.clear();
		} finally {
			this.ready = true;
		}
	}
	/**
	 * Método que retorna o HttpURLConnection.
	 * 
	 * @return Retorna o <code>HttpURLConnection</code>.
	 */
	public HttpURLConnection getConnection() {
		return connection;
	}
	/**
	 * Método que modifica o HttpURLConnection.
	 * 
	 * @param connection Novo <code>HttpURLConnection</code>.
	 */
	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}
	/**
	 * Método que retorna o endereço no qual o http está conectado.
	 * 
	 * @return Retorna o endereço no qual o http está conectado.
	 */
	public String getHost() {
		return host;
	}
	/**
	 * Método que modifica o host no qual o http está conectado.
	 * 
	 * @param host Novo <code>host</code>.
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * Método que retorna a URL.
	 * 
	 * @return Retorna a <code>URL</code>.
	 */
	public URL getUrl() {
		return url;
	}
	/**
	 * Método que modifica a URL.
	 * 
	 * @param url Nova <code>URL</code>.
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	/**
	 * Método que retorna o endereço local.
	 * 
	 * @return Retorna o <code>localAdress</code>.
	 */
	public String getLocalAddress() {
		return localAddress;
	}
	/**
	 * Método que altera o endereço local.
	 * 
	 * @param localAddress o novo <code>localAddress</code>.
	 */
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}
	/**
	 * Método que retorna o caminho.
	 * 
	 * @return Retorna o <code>path</code>.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Método que altera o caminho.
	 * 
	 * @param path o novo <code>path</code>.
	 */
	public void setPath(String path) {
		this.path = path;
	}

}


