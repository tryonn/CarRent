package br.ufpe.cin.middleware.stub_skeleton.interoperability;

import java.io.Serializable;
import static br.ufpe.cin.middleware.stub_skeleton.interoperability.MessageHeader.REQUEST;

public class RequestPDU implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int id_counter;
	
	public MessageHeader msgHeader;
	public RequestHeader reqHeader;
	public Object[] reqBody;
	
	public RequestPDU (String method, Object... params) {
		this.msgHeader = new MessageHeader();
		this.msgHeader.message_type = REQUEST;
		this.reqHeader = new RequestHeader();
		this.reqHeader.operation = method;
		synchronized (this) {
			this.reqHeader.request_id = id_counter++;
		}
		this.reqBody = params;
	}
	
}
