package br.ufpe.cin.middleware.tests.chat;

import java.io.Serializable;

public class ChatUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String ip;
	private int port;
	
	
	public ChatUser(String id, String ip, int port) {
		super();
		this.id = id;
		this.ip = ip;
		this.port = port;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the ip.
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip The ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
