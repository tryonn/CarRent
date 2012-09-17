package br.ufpe.cin.middleware.transport.udp;

import java.io.IOException;
import java.net.SocketException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.objects.Message;

public interface UDPMBean  {

	public void open(String destinationIP, int destinationPort) throws SocketException, InvalidIPException, InvalidPortException;
	public void send(Object msg, String destinationIP, int destinationPort) throws IOException;
	public Message receive() throws IOException, ClassNotFoundException;	
	public void close();
	
}
