package br.ufpe.cin.middleware.objects;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe que representa as mensagens a serem trocadas.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private String usedProtocol;
	
	private String sourceIP;
	private int sourcePort;
	private String destinationIP;
	private int destinationPort;
	private Object content;
	
	private boolean hasFile = false;
	private String fileName = "noname";
	
	/**
	 * Construtor da classe Message.
	 * 
	 * @param usedProtocol - protocolo usado por esta mensagem.
	 * @param content - objeto que representa o conte�do da mensagem.
	 * @param destinationIP - <code>String</code> que representa o endere�o IP destino.
	 * @param sourceIP - <code>String</code> que representa o endere�o IP origem.
	 * @param destinationPort - <code>int</code> que representa a porta destino.
	 * @param sourcePort - <code>int</code> que representa a porta origem.
	 * @throws IOException
	 */
	public Message(String usedProtocol, Object content, String destinationIP, String sourceIP, int destinationPort, int sourcePort) throws IOException {
		this.verifyInputContent(content);
		this.usedProtocol = usedProtocol;
		this.destinationIP = destinationIP;
		this.sourceIP = sourceIP;
		this.destinationPort = destinationPort;
		this.sourcePort = sourcePort;
	}
	/**
	 * Contrutor da classe Message.
	 * 
	 * @param usedProtocol - protocolo usado por esta mensagem.
	 * @param content - objeto que representa o conte�do da mensagem.
	 * @param destination - <code>String</code> que representa o endere�o IP destino.
	 * @param source - <code>String</code> que representa o endere�o IP origem.
	 * @throws IOException 
	 */
	public Message(String usedProtocol, Object content, String destination, String source) throws IOException {
		this.verifyInputContent(content);
		this.usedProtocol = usedProtocol;
		this.destinationIP = destination;
		this.sourceIP = source;
		this.destinationPort = -1;
		this.sourcePort = -1;
	}
	/**
	 * Contrutor da classe Message que recebe como par�metro um array de bytes.
	 * 
	 * @param array - um array de bytes
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 * @throws ClassNotFoundException -  - Exce��o levantada quando a classe n�o � encontrada.
	 */
	public Message(byte[] array) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bytearray = new ByteArrayInputStream(array);
		DataInputStream data = new DataInputStream(bytearray);
		ObjectInputStream object = new ObjectInputStream(data);
		Object re = object.readObject();
		Message ret = (Message) re;
		this.verifyInputContent(ret.getContent());
		this.destinationIP = ret.destinationIP;
		this.sourceIP = ret.sourceIP;
		this.destinationPort = ret.destinationPort;
		this.sourcePort = ret.sourcePort;
		this.usedProtocol = ret.usedProtocol;
	}
	/**
	 * M�todo que transforma uma Message em um array de bytes.
	 * 
	 * @return - Retorna o array de bytes referente a esta inst�ncia.
	 * @throws IOException - Exce��o levantada quando ocorre erro de IO.
	 */
	public byte[] toByteArray() throws IOException {
		byte[] resul;
		if(this.content instanceof File) {
			FileInputStream fileInput = new FileInputStream((File)this.getContent());
			int disp = fileInput.available();
			byte[] fileBytes = new byte[disp];
			fileInput.read(fileBytes);
			this.verifyInputContent(fileBytes);
		} 
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(bStream));
			os.writeObject(this);
			bStream.flush();
			os.flush();
			resul = bStream.toByteArray();
		
		return resul;		
	}
	/**
	 * Retorna o conte�do de uma mensagem.
	 * 
	 * @return um objeto representando o conte�do desta mensagem.
	 */
	public Object getContent() {
		return content;
	}
	/**
	 * Altera o conte�do de uma mensagem.
	 * 
	 * @param content - objeto que representa o novo conte�do da mensagem.
	 */
	public void setContent(Object content) {
		this.content = content;
	}
	/**
	 * Verifica o conte�do de uma mensagem.
	 * 
	 * @param content um objeto que representa o conte�do da mensagem.
	 * @throws IOException - Exce��o levantada quando ocorre um erro de IO.
	 */
	private void verifyInputContent(Object content) throws IOException {
		//TODO este m�todo REALMENTE eh publico?! alguem + precisa dele??
		if(content != null) {
			if(content instanceof File) {
				byte[] fileBytes = this.getByteArray((File) content);
				this.content = fileBytes;
				this.hasFile = true;
				File temp = (File)content;
				this.fileName = temp.getName();
			} else {
				this.content = content;
			}
		} else {
			throw new NullPointerException("Conteudo nulo");
		}
	}
	/**
	 * Checa o conte�do de uma mensagem, transformando-o de acordo com o tipo de protocolo. 
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void checkMessage() throws IOException, ClassNotFoundException {
		if(this.hasFile) {
			File file = this.createFile((byte[])this.getContent());
			this.content = file;
			this.hasFile = false;
			this.fileName = "";
		} else if((this.content instanceof byte[]) && (!this.hasFile) && usedProtocol.equals("UDP")) {//usado em udp
			ByteArrayInputStream bytearray = new ByteArrayInputStream((byte[]) this.content);
			DataInputStream data = new DataInputStream(bytearray);
			ObjectInputStream object = new ObjectInputStream(data);
			Object re = object.readObject();
			this.content = re;
		}
	}
	/**
	 * Transforma um array de bytes num arquivo.
	 * 
	 * @param content o array de bytes original.
	 * @return o arquivo destino.
	 * @throws IOException - Exce��o levantada caso algum ocorra algum erro de IO.
	 */
	private File createFile(byte[] content) throws IOException {
		File f = new File("received_" + this.fileName);
		FileOutputStream outPut = new FileOutputStream(f);
		outPut.write(content);
		outPut.flush();
		return f;
	}
	/**
	 * Transforma um arquivo num array de bytes.
	 * 
	 * @param file o arquivo original
	 * @return o array de bytes destino.
	 * @throws IOException - Exce��o levantada caso algum ocorra algum erro de IO.
	 */
	private byte[] getByteArray(File file) throws IOException {
		FileInputStream fileInput = new FileInputStream(file);
		int disp = fileInput.available();
		byte[] fileBytes = new byte[disp];
		fileInput.read(fileBytes);
		return fileBytes;
	}
	/**
	 * M�todo que retorna o endere�o IP destino da mensagem.
	 * 
	 * @return Retorna o <code>destinationIP</code> da mensagem.
	 */
	public String getDestinationIP() {
		return destinationIP;
	}
	/**
	 * M�todo que modifica o endere�o IP destino da mensagem.
	 * 
	 * @param destinationIP O novo <code>destinationIP</code>.
	 */
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}

	/**
	 * M�todo que retorna a porta destino da mensagem.
	 * 
	 * @return Retorna o <code>destinationPort</code> da mensagem.
	 */
	public int getDestinationPort() {
		return destinationPort;
	}
	/**
	 * M�todo que modifica a porta destino da mensagem.
	 * 
	 * @param destinationPort A nova <code>destinationPort</code>.
	 */
	public void setDestinationPort(int destinationPort) {
		this.destinationPort = destinationPort;
	}
	/**
	 * M�todo que retorna o endere�o IP da origem da mensagem.
	 * 
	 * @return Retorna o <code>sourceIP</code> da mensagem.
	 */
	public String getSourceIP() {
		return sourceIP;
	}
	/**
	 * M�todo que modifica o endere�o IP origem da mensagem.
	 * 
	 * @param sourceIP O novo <code>sourceIP</code>.
	 */
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}
	/**
	 * M�todo que retorna a porta origem da mensagem.
	 * 
	 * @return Retorna o <code>sourcePort</code> da mensagem.
	 */
	public int getSourcePort() {
		return sourcePort;
	}
	/**
	 * M�todo que modifica a porta origem da mensagem.
	 * 
	 * @param sourcePort A nova <code>sourcePort</code>.
	 */
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}
	/**
	 * M�todo que retorna o nome do arquivo contido numa mensagem.
	 * 
	 * @return Retorna o <code>fileName</code> da mensagem.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * M�todo que altera o nome do arquivo contido numa mensagem.
	 * 
	 * @param fileName O novo <code>fileName</code>.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * M�todo que retorna se o conte�do de uma mensagem � ou n�o um arquivo.
	 * 
	 * @return <code>true</code>, se o conte�do da mensagem for um arquivo,
	 * 		   <code>false</code> caso contr�rio.
	 */
	public boolean isHasFile() {
		return hasFile;	
	}
	/**
	 * M�todo que altera a indica��o de que a mensagem cont�m ou n�o um arquivo.
	 * 
	 * @param hasFile - <code>true</code>, se o conte�do da mensagem for um arquivo,
	 * 		   			<code>false</code> caso contr�rio.
	 */
	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}	

	//---------------------------------------------------------------------------
	/*
	 * ISSO � UM TESTE
	 *
	//**
	 * Contrutor da classe message.
	 * 
	 * @param partOfFile - Array de bytes que comp�e o arquivo
	 * @param size - Inteiro que indica o conte�do v�lido do array
	 * @param destinationIP - String que representa o endere�o IP destino.
	 * @param sourceIP - String que representa o endere�o IP original.
	 * @param destinationPort - int que representa a porta destino.
	 * @param sourcePort - int que representa a porta origem.
	 * @throws IOException 
	 */
	
	
	/*public Message(String usedProtocol, byte[] partOfFile,int size, String destinationIP, String sourceIP, int destinationPort, int sourcePort) throws IOException {
		this.usedProtocol = usedProtocol;
		this.destinationIP = destinationIP;
		this.sourceIP = sourceIP;
		this.destinationPort = destinationPort;
		this.sourcePort = sourcePort;
		int count = 0;
		byte[] content = new byte[size];
		
		while(count < size){
			content[count] = partOfFile[count];
			count++;
		}
		this.content = content;
	}*/
	
	
}
