package br.ufpe.cin.middleware.stub_skeleton.interoperability;

import java.io.Serializable;

public class RequestHeader implements Serializable {

	private static final long serialVersionUID = 1L;

	//ServiceContextList service_context;
	public long request_id;
	public boolean response_expected;
	//Vector<byte> object_key;
	public String operation;
	//Principal requesting_principal;
	
}
