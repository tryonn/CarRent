package br.ufpe.cin.middleware.objects.controlMessages;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class UDPPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	private int number;
	private int length;
	private byte[] content;
	
	public UDPPackage(int number,byte[] content, int length) {
		this.number = number;
		this.length = length;
		this.content = content;
	}
	
	public UDPPackage(byte[] array) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bytearray = new ByteArrayInputStream(array);
		DataInputStream data = new DataInputStream(bytearray);
		ObjectInputStream object = new ObjectInputStream(data);
		Object re = object.readObject();
		UDPPackage pack = (UDPPackage) re;
		this.content = pack.getContent();
		this.length = pack.getLength();
		this.number = pack.getNumber();
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int lenght) {
		this.length = lenght;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
	
}
