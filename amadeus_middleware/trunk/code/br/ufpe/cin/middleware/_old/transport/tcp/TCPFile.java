package br.ufpe.cin.middleware._old.transport.tcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.ufpe.cin.middleware.exceptions.InvalidIPException;
import br.ufpe.cin.middleware.exceptions.InvalidPortException;
import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.exceptions.NotExistingClientException;
import br.ufpe.cin.middleware.exceptions.NullSocketException;
import br.ufpe.cin.middleware.objects.Message;

/**
 * 
 * Classe responsável por implementar o comportamento da transmissão de dados, 
 * (especificamente arquivos) fazendo uso do protocolo TCP.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class TCPFile extends TCP implements TCPFileMBean {
	
	//ver como vai ficar o arquivo...
	String begin = "beginOfFile";
    String end = "endOfFile";
    int oneMega;
    byte[] data;
    int num;
    File file_out;
    File file ;
    FileOutputStream receivedFile;
    Message message;
    
    /**
     * Construtor da classe TCPFile para o lado do servidor.
     * 
     * @throws IOException
     * @throws NullSocketException
     */
	public TCPFile() throws IOException, NullSocketException {
		super();
		oneMega = 1024000;
		data = new byte[oneMega];
		num = 0;		
	}
	/**
	 * Construtor da classe TCPFile para o lado do cliente.
	 * 
	 * @param host endereço do destino.
	 * @param serverPort porta de origem.
	 * @param bindPort porta do destino.
	 * @throws IOException
	 * @throws NullSocketException
	 * @throws InvalidPortException
	 * @throws InvalidIPException
	 */
	public TCPFile(String host, int serverPort, int bindPort) throws IOException, NullSocketException, InvalidPortException, InvalidIPException {
		super(host, serverPort, bindPort);
		oneMega = 1024000;
		data = new byte[oneMega];
		num = 0;
	}
	/**
	 * Método que envia um arquivo. (método exclusivo do cliente)
	 *   
	 * @param file arquivo a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
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
	/**
	 * Método que envia um objeto, através de um host e de uma porta.
	 * (tanto para o cliente quanto para o servidor)
	 *    
	 * @param file arquivo a ser enviado.
	 * @param destinationIP endereço de IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws IOException
	 * @throws NotConnectedException
	 * @throws InvalidIPException
	 * @throws NotExistingClientException
	 */
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
	/**
	 * Método que envia um objeto para todos os clientes.
	 * (método exclusivo do servidor).
	 * 
	 * @param msg objeto a ser enviado.
	 * @throws IOException
	 * @throws NotConnectedException
	 */
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
	/**
	 * Recebe uma mensagem.
	 * 
	 * @return Retorna a mensagem recebida.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NotConnectedException
	 */
	public Message receive() throws IOException, ClassNotFoundException, NotConnectedException{
		/***********receiveMessage********/
		this.message = super.receive();
		/*********************************/
		if (this.message.getContent().equals("beginOfFile")){
			//recebe o nome do arquivo
			this.message = super.receive();
			this.file = new File("received_" + String.valueOf(this.message.getContent()));
			this.receivedFile = new FileOutputStream(file);
			boolean endOfFile = false;
			while(!endOfFile){
				/***********receiveMessage********/
				this.message = super.receive();
				/*********************************/
				if(!this.message.getContent().equals("endOfFile")){
					receivedFile.write((byte[])this.message.getContent());
				}else{
					endOfFile = true;
				}
			}
			receivedFile.close();
		}
		return this.message;
		
	}
	/**
	 * Fecha uma conexão. 
	 * 
	 * @param destination endereço IP do destino.
	 * @param destinationPort porta do destino.
	 * @throws Throwable
	 */
	public void close(String destination, int destinationPort) throws Throwable{
		super.close(destination, destinationPort);
	}
}