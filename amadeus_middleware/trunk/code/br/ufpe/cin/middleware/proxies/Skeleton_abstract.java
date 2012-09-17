package br.ufpe.cin.middleware.proxies;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.interoperability.ReplyPDU;
import br.ufpe.cin.middleware.interoperability.RequestPDU;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.transport.Buffer;

public abstract class Skeleton_abstract {
	
	
	protected RequestPDU request;
	protected ReplyPDU reply;

	protected Buffer<RequestPDU> bufReq = new Buffer<RequestPDU>(1000);
	protected Buffer<ReplyPDU> bufRep = new Buffer<ReplyPDU>(1000);
	protected AmadeusMiddleware middleware = new AmadeusMiddleware();
	
	public Skeleton_abstract() {
		this.middleware.open();
		new Thread(){
			public void run() {
				while (true) {
					try {
						middleware.listen();
						receive();
					} catch (Exception e) {
						System.err.println(String.format("%19s%s", "", e
								.toString()));
					}
				}				 
				
			};
		}.start();
	}
	
	private void receive() {
		new Thread(){
			public void run() {
				while (true) {
					try {
						Skeleton_abstract.this.init();
					} catch (NotConnectedException e) {
						System.err.println(String.format("%19s%s", "",e.toString()));
						this.interrupt();
						break;
					}					
				}
			};
		}.start();

	}
	
	public abstract void init() throws NotConnectedException;
	
	public void startServices() {}
	
	
}
