package br.ufpe.cin.middleware.stub_skeleton.proxies;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.stub_skeleton.interoperability.ReplyPDU;
import br.ufpe.cin.middleware.stub_skeleton.interoperability.RequestPDU;
import br.ufpe.cin.middleware.stub_skeleton.transport.Buffer;
import br.ufpe.cin.middleware.util.Debug;
import br.ufpe.cin.middleware.util.XMLParser;

public class Calculator_stub implements Calculator {

	
	private static final long serialVersionUID = 1L;
	RequestPDU request;
	ReplyPDU reply;
	
	String host = XMLParser.hosts.get("Calculator");
	int port = XMLParser.localPorts.get("Calculator");
	int serverPort = XMLParser.serverPorts.get("Calculator");
	
	Buffer<RequestPDU> bufReq = new Buffer<RequestPDU>(1000);
	Buffer<ReplyPDU> bufRep = new Buffer<ReplyPDU>(1000);
	AmadeusMiddleware middleware = new AmadeusMiddleware(host, serverPort, port);
	
	public Calculator_stub() {
		this.middleware.open();
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
		this.middleware.close(host, port);
	}
	
	@SuppressWarnings("unchecked")
	public int add(int a, int b) {
		request = new RequestPDU("add",a,b);
		bufReq.write(request);
		middleware.send(bufReq,host, port);
		try {
			bufRep = (Buffer<ReplyPDU>) middleware.receive().getContent();
		} catch (NotConnectedException e) {
			Debug.printStack(e);
		}
		reply = bufRep.read();
		return (Integer) reply.repBody;
	}

	@SuppressWarnings("unchecked")
	public int sub(int a, int b) {
		request = new RequestPDU("sub",a,b);
		bufReq.write(request);
		middleware.send(bufReq,host, port);
		try {
			bufRep = (Buffer<ReplyPDU>) middleware.receive().getContent();
		} catch (NotConnectedException e) {
			Debug.printStack(e);
		}
		reply = bufRep.read();
		return (Integer) reply.repBody;
	}

	@SuppressWarnings("unchecked")
	public int mult(int a, int b) {
		request = new RequestPDU("mult",a,b);
		bufReq.write(request);
		middleware.send(bufReq,host, port);
		try {
			bufRep = (Buffer<ReplyPDU>) middleware.receive().getContent();
		} catch (NotConnectedException e) {
			Debug.printStack(e);
		}	
		reply = bufRep.read();
		return (Integer) reply.repBody;
	}

	@SuppressWarnings("unchecked")
	public int invert(int a) {
		request = new RequestPDU("invert",a);
		bufReq.write(request);
		middleware.send(bufReq,host, port);
		try {
			bufRep = (Buffer<ReplyPDU>) middleware.receive().getContent();
		} catch (NotConnectedException e) {
			Debug.printStack(e);
		}
		reply = bufRep.read();
		return (Integer) reply.repBody;
	}

}
