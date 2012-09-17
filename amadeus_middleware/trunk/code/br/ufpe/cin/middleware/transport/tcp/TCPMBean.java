package br.ufpe.cin.middleware.transport.tcp;

import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;

public interface TCPMBean {

	public void listen() throws IOException, ClassNotFoundException;
	public void send(Object obj) throws IOException, NotConnectedException;
	public void sendTo(Object obj, String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException;
	public void sendToAll(Object obj) throws IOException, NotConnectedException;
	public Message receive() throws IOException, ClassNotFoundException, NotConnectedException;
	public void close(String destination, int destinationPort) throws Throwable;
}