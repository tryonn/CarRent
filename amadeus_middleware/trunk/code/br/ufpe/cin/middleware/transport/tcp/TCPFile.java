package br.ufpe.cin.middleware.transport.tcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.objects.Message;

public class TCPFile extends TCP implements TCPFileMBean {
	
	private String begin = "beginOfFile";
	private String end = "endOfFile";
	private int oneMega;
	private byte[] data;
	private int num;
	private File file;
	private FileOutputStream receivedFile;
	private Message message;
    
	public TCPFile(TCPAddress address) throws UnknownHostException, IOException, InvalidPortException, InvalidIPException {
		super(address);
		oneMega = 1024000;
		data = new byte[oneMega];
		num = 0;
	}

	public void send(Object file) throws IOException, NotConnectedException{
		this.num = 0;
		//sinaliza o inicio do arquivo
		super.send((Object)this.begin);
		//envia o nome do arquivo
		super.send(String.valueOf(file));
		File teste = new File(String.valueOf(file));
		FileInputStream fis = new FileInputStream(teste);
		while ((num = fis.read(data, 0, oneMega)) > 0) { // read 1mb of data from file input stream
			if(num < oneMega){
				byte[] content = new byte[num];
				int count = 0;
				while(count < this.num){
					content[count] = data[count];
					count++;
				}
				super.send((Object)content);
			}else{
				super.send((Object)data); // chama o send do ClientTCP
			}
		}
		fis.close();
		super.send(this.end);
	}
	
	public void sendTo(Object file,String destinationIP, int destinationPort ) throws IOException, NotConnectedException, InvalidIPException, NotExistingClientException{
		this.num = 0;
		//sinaliza o inicio do arquivo
		super.sendTo((Object)this.begin, destinationIP, destinationPort);
		//envia o nome do arquivo
		super.sendTo(String.valueOf(file), destinationIP, destinationPort);
		File teste = new File(String.valueOf(file));
		FileInputStream fis = new FileInputStream(teste);
		while ((num = fis.read(data, 0, oneMega)) > 0) { // read 1mb of data from file input stream
			if(num < oneMega){
				byte[] content = new byte[num];
				int count = 0;
				while(count < this.num){
					content[count] = data[count];
					count++;
				}
				super.sendTo((Object)content, destinationIP, destinationPort);
			}else{
				super.sendTo((Object)data, destinationIP, destinationPort); // chama o send do ClientTCP
			}
		}
		fis.close();
		super.sendTo(this.end, destinationIP, destinationPort);
		
	}

	//servidor
	public void sendToAll(Object file) throws IOException, NotConnectedException{
		this.num = 0;
		//sinaliza o inicio do arquivo
		super.sendToAll((Object)this.begin);
		//envia o nome do arquivo
		super.sendToAll(String.valueOf(file));
		File teste = new File(String.valueOf(file));
		FileInputStream fis = new FileInputStream(teste);
		while ((num = fis.read(data, 0, oneMega)) > 0) { // read 1mb of data from file input stream
			if(num < oneMega){
				byte[] content = new byte[num];
				int count = 0;
				while(count < this.num){
					content[count] = data[count];
					count++;
				}
				super.sendToAll((Object)content);
			}else{
				super.sendToAll((Object)data); // chama o send do ClientTCP
			}
		}
		fis.close();
		super.sendToAll(this.end);
	}

	public Message receive() throws IOException, ClassNotFoundException, NotConnectedException{
		/***********receiveMessage********/
		this.message = super.receive();
		/*********************************/
		if (this.message.getContent().equals(this.begin)){
			//recebe o nome do arquivo
			this.message = super.receive();
			this.file = new File("received_" + String.valueOf(this.message.getContent()));
			this.receivedFile = new FileOutputStream(file);
			boolean endOfFile = false;
			while(!endOfFile){
				/***********receiveMessage********/
				this.message = super.receive();
				/*********************************/
				if(!this.message.getContent().equals(this.end)){
					receivedFile.write((byte[])this.message.getContent());
				}else{
					endOfFile = true;
				}
			}
			receivedFile.close();
		}
		return this.message;
		
	}

	public void close(String destination, int destinationPort) throws Throwable{
		super.close(destination, destinationPort);
	}
}