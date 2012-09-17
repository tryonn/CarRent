package br.ufpe.cin.middleware.services.session;

public class ServerSession {
	private Session session;
	private long keepAlive;
	
	public ServerSession(Session session, long keepAlive){
		this.session = session;
		this.keepAlive = keepAlive;
		
	}

	public long getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(long keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
