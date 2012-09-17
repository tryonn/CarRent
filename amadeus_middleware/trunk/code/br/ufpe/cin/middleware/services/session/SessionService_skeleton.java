package br.ufpe.cin.middleware.services.session;


import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.interoperability.ReplyPDU;
import br.ufpe.cin.middleware.interoperability.ReplyStatusType;
import br.ufpe.cin.middleware.interoperability.RequestPDU;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.proxies.Skeleton_abstract;
import br.ufpe.cin.middleware.transport.Buffer;

public class SessionService_skeleton extends Skeleton_abstract implements
		SessionService {

	private static final long serialVersionUID = 1L;

	SessionService_impl impl = null;
	
	public SessionService_skeleton() {
		super();
		this.impl = new SessionService_impl();
	}
	

	public boolean create(Session session) throws Exception {
		return this.impl.create(session);
		
	}

	public Session[] getUserbyMicroMundo(String microMundo) {
		return this.impl.getUserbyMicroMundo(microMundo);
	}

	public Session[] getUsers() {
		return this.impl.getUsers();
	}

	public boolean remove(Session session) throws Exception {
		return this.impl.remove(session);
	}

	public boolean update(Session session) throws Exception {
		return this.impl.update(session);
		
	}

	@SuppressWarnings("unchecked")
	public void init() throws NotConnectedException {
		Message msg = middleware.receive();
		
		bufReq = (Buffer<RequestPDU>) msg.getContent();
		request = bufReq.read();
		
		Object result = null;
		
		try {
			if (request.reqHeader.operation.equalsIgnoreCase("create")) {
				result = this.create((Session) request.reqBody[0]);
			} else if (request.reqHeader.operation.equalsIgnoreCase("remove")) {
				result = this.remove((Session) request.reqBody[0]);
			} else if (request.reqHeader.operation.equalsIgnoreCase("update")) {
				result = this.update((Session) request.reqBody[0]);
			} else if (request.reqHeader.operation.equalsIgnoreCase("getUsers")) {
				result = this.getUsers();
			} else if (request.reqHeader.operation.equalsIgnoreCase("getUserByMicroMundo")) {
				result = this.getUserbyMicroMundo((String) request.reqBody[0]);
			} 
			reply = new ReplyPDU(ReplyStatusType.NO_EXCEPTION,result);
		} catch (Exception e) {
			reply = new ReplyPDU(ReplyStatusType.USER_EXCEPTION,result);
		}		
		bufRep = new Buffer<ReplyPDU>(10);
		bufRep.write(reply);
		middleware.sendTo(bufRep,msg.getSourceIP(), msg.getSourcePort());
		
	}	
	
	public void startServices() {
		this.impl.startMonitoring();
	}
	

}
