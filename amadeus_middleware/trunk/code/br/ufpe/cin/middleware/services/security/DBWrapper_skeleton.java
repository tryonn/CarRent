package br.ufpe.cin.middleware.services.security;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.interoperability.ReplyPDU;
import br.ufpe.cin.middleware.interoperability.ReplyStatusType;
import br.ufpe.cin.middleware.interoperability.RequestPDU;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.proxies.Skeleton_abstract;
import br.ufpe.cin.middleware.transport.Buffer;


public class DBWrapper_skeleton extends Skeleton_abstract implements DBWrapper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DBWrapper_impl dWrapper_Impl = new DBWrapper_impl();
	
	public boolean validateLogin(String login, String password) {
		return this.dWrapper_Impl.validateLogin(login, password);
	}

	@SuppressWarnings("unchecked")
	public void init() throws NotConnectedException {
		Message msg = middleware.receive();
		bufReq = (Buffer<RequestPDU>) msg.getContent();
		request = bufReq.read();

		boolean result = false;

		if (request.reqHeader.operation.equalsIgnoreCase("validateLogin")){
			result = this.validateLogin((String) request.reqBody[0],(String) request.reqBody[1]);
		}
		
		//TODO Ver se não levanta exeção mesmo!!!
		reply = new ReplyPDU(ReplyStatusType.NO_EXCEPTION,result);
		
		bufRep = new Buffer<ReplyPDU>(10);
		bufRep.write(reply);
		middleware.sendTo(bufRep,msg.getSourceIP(), msg.getSourcePort());
	}
	
	public void startServices() {
		this.dWrapper_Impl.init();
	}
	
}
