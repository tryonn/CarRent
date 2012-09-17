package br.ufpe.cin.middleware.interoperability;

import java.io.Serializable;

public class MessageHeader implements Serializable {

	private static final long serialVersionUID = 1L;

	public char[] magic = {'G','I','O','P'};
	public Version GIOP_Version = new Version((byte) 1,(byte) 0);
	public boolean byte_order = System.getProperty("sun.cpu.endian").equals("little");
	public byte message_type; //0 request, 1 reply
	public long message_size; //que no nosso caso vai ser indiferente, pois n usamos arrays de bytes

	public static byte REQUEST = 0;
	public static byte REPLY = 1;
	
}

