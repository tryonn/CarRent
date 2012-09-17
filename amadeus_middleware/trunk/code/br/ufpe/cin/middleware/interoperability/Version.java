package br.ufpe.cin.middleware.interoperability;

import java.io.Serializable;

public class Version implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte major;
	public byte minor;
	
	public Version(byte i, byte j) {
		this.major = i;
		this.minor = j;
	}
	
}
