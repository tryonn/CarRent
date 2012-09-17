package br.ufpe.cin.middleware.services.calculator;

import br.ufpe.cin.middleware.proxies.Stub_abstract;


public class Calculator_stub extends Stub_abstract implements Calculator {

	
	private static final long serialVersionUID = 1L;
	
	
	public Calculator_stub(String host, int port) {
		super("Calculator",host,port);
	}
	
	public int add(int a, int b) {
		return (Integer)this.process("add",a,b);
	}

	public int sub(int a, int b) {
		return (Integer)this.process("sub",a,b);
	}

	public int mult(int a, int b) {
		return (Integer)this.process("mult",a,b);
	}

	public int invert(int a) {
		return (Integer)this.process("invert",a);
	}
	


}
