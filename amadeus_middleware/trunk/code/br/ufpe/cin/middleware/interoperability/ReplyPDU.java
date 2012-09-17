package br.ufpe.cin.middleware.interoperability;

import java.io.Serializable;

import static br.ufpe.cin.middleware.interoperability.MessageHeader.REPLY;
public class ReplyPDU implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int id_counter;
	
	public MessageHeader msgHeader;
	public ReplyHeader repHeader;
	public Object repBody;
	
	public ReplyPDU (ReplyStatusType e, Object result) {
		this.msgHeader = new MessageHeader();
		this.msgHeader.message_type = REPLY;
		this.repHeader = new ReplyHeader();
		this.repHeader.reply_status = e;
		synchronized (this) {
			this.repHeader.request_id = id_counter++;
		}
		this.repBody = result;
	}
	
}
