package br.ufpe.cin.middleware.stub_skeleton.proxies;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.stub_skeleton.interoperability.ReplyPDU;
import br.ufpe.cin.middleware.stub_skeleton.interoperability.ReplyStatusType;
import br.ufpe.cin.middleware.stub_skeleton.interoperability.RequestPDU;
import br.ufpe.cin.middleware.stub_skeleton.transport.Buffer;

public class Calculator_skeleton implements Calculator {


	private static final long serialVersionUID = 1L;

	Calculator_impl impl = new Calculator_impl();

	RequestPDU request;
	ReplyPDU reply;

	Buffer<RequestPDU> bufReq = new Buffer<RequestPDU>(1000);
	Buffer<ReplyPDU> bufRep = new Buffer<ReplyPDU>(1000);
	AmadeusMiddleware middleware = new AmadeusMiddleware();

	public Calculator_skeleton() {
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
						Calculator_skeleton.this.init();
					} catch (NotConnectedException e) {
						System.err.println(String.format("%19s%s", "",e.toString()));
						this.interrupt();
						break;
					}					
				}
			};
		}.start();

	}

	public int add(int a, int b) {
		return impl.add(a,b);
	}

	public int sub(int a, int b) {
		return impl.sub(a,b);
	}

	public int mult(int a, int b) {
		return impl.mult(a,b);
	}

	public int invert(int a) {
		return impl.invert(a);
	}

	@SuppressWarnings("unchecked")
	public void init() throws NotConnectedException {
		Message msg = middleware.receive();
		bufReq = (Buffer<RequestPDU>) msg.getContent();
		request = bufReq.read();

		int result = Integer.MIN_VALUE;

		if (request.reqHeader.operation.equalsIgnoreCase("add")){
			result = this.add((Integer) request.reqBody[0],(Integer) request.reqBody[1]);
		}
		if (request.reqHeader.operation.equalsIgnoreCase("sub")){
			result = this.sub((Integer) request.reqBody[0],(Integer) request.reqBody[1]);
		}
		if (request.reqHeader.operation.equalsIgnoreCase("mult")){
			result = this.mult((Integer) request.reqBody[0],(Integer) request.reqBody[1]);
		}
		if (request.reqHeader.operation.equalsIgnoreCase("mult")){
			result = this.invert((Integer) request.reqBody[0]);
		}

		if (result != Integer.MIN_VALUE) {
			reply = new ReplyPDU(ReplyStatusType.NO_EXCEPTION,result);
		} else {
			reply = new ReplyPDU(ReplyStatusType.USER_EXCEPTION,result);
		}

		bufRep.write(reply);
		middleware.sendTo(bufRep,msg.getSourceIP(), msg.getSourcePort());
	}





}
