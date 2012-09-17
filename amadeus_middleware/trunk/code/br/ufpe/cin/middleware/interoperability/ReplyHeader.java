package br.ufpe.cin.middleware.interoperability;

import java.io.Serializable;

public class ReplyHeader implements Serializable {

	private static final long serialVersionUID = 1L;

	//ServiceContextList service_context;
	public long request_id;
	public ReplyStatusType reply_status;
	
}
