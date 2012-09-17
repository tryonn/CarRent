package br.ufpe.cin.middleware.services.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;


public class SessionService_impl implements SessionService, Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Session> sessionTable;
	private Map<String, Long> keepAliveTable;
	
	private transient Thread monitorar;
	
	public SessionService_impl(){
		this.sessionTable = Collections.synchronizedMap(new Hashtable<String, Session>());
		this.keepAliveTable = Collections.synchronizedMap(new Hashtable<String, Long>());
		this.monitorar = new Thread(){
			public void run() {
				monitorar();
			}
		};
		this.monitorar.setName("Thread do servidor de sessão");
	}
	
	public void startMonitoring() {
		this.monitorar.start();
	}

	public boolean create(Session session) throws Exception {
		boolean retorno = false;
		//TODO Modificar o equals de sessao!!!
		if(!this.sessionTable.containsKey(session.getKey())){
			this.keepAliveTable.put(session.getKey(), System.currentTimeMillis());
			this.sessionTable.put(session.getKey(), session);
			retorno = true;
		}else{
			throw new Exception("A sessão já foi criada anteriormente!!!");
		}
		return retorno;
	}

	public Session[] getUserbyMicroMundo(String microMundo) {
		Vector<Session> v = new Vector<Session>();
		Iterator<Session> sessionEnum = this.sessionTable.values().iterator();
		Session s = null;
		while(sessionEnum.hasNext()){
			s = sessionEnum.next();
			if(s.getMicromundos().contains(microMundo)){
				v.addElement(s);
			}
		}
		Session[] retorno = new Session[v.size()]; 
		v.copyInto(retorno);
		return retorno;
	}

	public Session[] getUsers() {
		Collection<Session> collection = this.sessionTable.values();
		Vector<Session> v = new Vector<Session>(collection);
		Session[] retorno = new Session[v.size()]; 
		v.copyInto(retorno);
		return retorno;
	}

	public boolean remove(Session session) throws Exception {
		boolean retorno = false;
		if(this.sessionTable.containsKey(session.getKey())){
			this.sessionTable.remove(session.getKey());
			this.keepAliveTable.remove(session.getKey());
			retorno = true;
		}else throw new Exception("Essa sessão não existe no servidor!!!");
		return retorno;
	}

	public boolean update(Session session) throws Exception {
		boolean retorno = false;
		if(this.sessionTable.containsKey(session.getKey())){
			this.keepAliveTable.remove(session.getKey());
			this.sessionTable.remove(session.getKey());
			
			this.keepAliveTable.put(session.getKey(), System.currentTimeMillis());
			this.sessionTable.put(session.getKey(), session);
			retorno = true;
			
		}else throw new Exception("O sessão não existe no servidor!!!");
		return retorno;
	}
	
	public void monitorar(){
		while(this.monitorar.isAlive()) {
			synchronized (this.sessionTable) {
				Iterator<Entry<String,Long>> iterator= this.keepAliveTable.entrySet().iterator();
				
				while (iterator.hasNext()) {
					Entry<String, Long> lastTime = iterator.next();
					if((System.currentTimeMillis() - lastTime.getValue()) > (Session.TIME_OUT_SECONDS * 4000)){
						this.keepAliveTable.remove(lastTime.getKey());
						this.sessionTable.remove(lastTime.getKey());
					}
					
				}
			}
			try {
				Thread.sleep(Session.TIME_OUT_SECONDS * 2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}

}
