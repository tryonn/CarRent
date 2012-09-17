package br.ufpe.cin.middleware.proxies;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.interoperability.ReplyPDU;
import br.ufpe.cin.middleware.interoperability.RequestPDU;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.services.naming.RemoteInterface;
import br.ufpe.cin.middleware.transport.Buffer;
import br.ufpe.cin.middleware.util.Debug;

public abstract class Stub_abstract implements RemoteInterface {

	RequestPDU request;
	ReplyPDU reply;
	Buffer<RequestPDU> bufReq = new Buffer<RequestPDU>(1000);
	Buffer<ReplyPDU> bufRep = new Buffer<ReplyPDU>(1000);

	String service;
	String host;
	int port;
	int serverPort;

	AmadeusMiddleware middleware;


	public Stub_abstract(String service, String host, int remotePort) {
		this.service = service;
		this.host = host;
		this.serverPort = remotePort;
		this.port = 1100;
		middleware = new AmadeusMiddleware(this.host, this.serverPort, this.port);
		this.middleware.open();
	}

	protected void finalize() throws Throwable {
		super.finalize();
		this.middleware.close(host, port);
	}

	@SuppressWarnings("unchecked")
	public Object process(String op, Object... args){
		request = new RequestPDU(op,args);
		bufReq = new Buffer<RequestPDU>(1000);
		bufReq.write(request);
		middleware.send(bufReq,host, serverPort);
		try {
			bufRep = (Buffer<ReplyPDU>) middleware.receive().getContent();
		} catch (NotConnectedException e) {
			Debug.printStack(e);
		}
		reply = bufRep.read();
		return reply.repBody;
	}

}
