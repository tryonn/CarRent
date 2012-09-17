package br.ufpe.cin.middleware.stub_skeleton.interoperability;

import java.io.Serializable;

public class ReplyHeader implements Serializable {

	private static final long serialVersionUID = 1L;

	//ServiceContextList service_context;
	public long request_id;
	public ReplyStatusType reply_status;
	
}
