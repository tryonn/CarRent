package br.ufpe.cin.middleware.transport.http;

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
import br.ufpe.cin.middleware.transport.Transport;
import br.ufpe.cin.middleware.util.Port;

public class HTTP implements Transport, HTTPMBean {

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

	public HTTP(String host, String path) throws IOException {
		this.host = host;
		this.path = path;
		this.localAddress = InetAddress.getLocalHost().getHostAddress();
		this.url = new URL("HTTP",host,path);
	}

	public void open() throws IOException {
		//Abre a conexão
		this.connection = (HttpURLConnection) this.url.openConnection();
		//Seta os parametros da conexão
		this.connection.setDoInput(true);
		this.connection.setDoOutput(true);
		//Conecta-se
		this.connection.connect();
	}

	public void send(Object msg) throws IOException {
		this.open();
		Message m = new Message("HTTP",msg,host,localAddress,DEFAULT_PORT,DEFAULT_PORT);
		ObjectOutputStream out = new ObjectOutputStream(this.connection.getOutputStream());
		out.writeObject(m);
		out.flush();
		this.close();
	}

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
		Message m = new Message("HTTP",ret,localAddress,host,DEFAULT_PORT,DEFAULT_PORT);
		this.close();
		return m;
	}

	public void close() {
		this.connection.disconnect();
	}

	public void writeInPort(Object obj, String method) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		this.inPort.write(method);
		this.inPort.write(obj);
		this.process();
	}

	public Object readOutPort() throws IOException, ClassNotFoundException{
		while(!this.ready){}
		this.ready = false;
		Object returnObj = this.outPort.read(0); 
		this.outPort.clear();
		return returnObj;
	}
	
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


	
	
	
	
	/*           GETS E SETS           */

	
	
	
	
	
	public HttpURLConnection getConnection() {
		return connection;
	}
	
	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

}


