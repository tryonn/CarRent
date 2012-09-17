package br.ufpe.cin.middleware.transport.http;

import java.io.IOException;

public interface HTTPMBean {

	public void open() throws IOException;
	public void send(Object msg) throws IOException;
	public Object receive() throws IOException, ClassNotFoundException;
	public void close();
}
