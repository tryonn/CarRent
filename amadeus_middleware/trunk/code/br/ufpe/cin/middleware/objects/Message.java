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
	 * @param content - objeto que representa o conteúdo da mensagem.
	 * @param destinationIP - <code>String</code> que representa o endereço IP destino.
	 * @param sourceIP - <code>String</code> que representa o endereço IP origem.
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
	 * @param content - objeto que representa o conteúdo da mensagem.
	 * @param destination - <code>String</code> que representa o endereço IP destino.
	 * @param source - <code>String</code> que representa o endereço IP origem.
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
	 * Contrutor da classe Message que recebe como parâmetro um array de bytes.
	 * 
	 * @param array - um array de bytes
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
	 * @throws ClassNotFoundException -  - Exceção levantada quando a classe não é encontrada.
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
	 * Método que transforma uma Message em um array de bytes.
	 * 
	 * @return - Retorna o array de bytes referente a esta instância.
	 * @throws IOException - Exceção levantada quando ocorre erro de IO.
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
	 * Retorna o conteúdo de uma mensagem.
	 * 
	 * @return um objeto representando o conteúdo desta mensagem.
	 */
	public Object getContent() {
		return content;
	}
	/**
	 * Altera o conteúdo de uma mensagem.
	 * 
	 * @param content - objeto que representa o novo conteúdo da mensagem.
	 */
	public void setContent(Object content) {
		this.content = content;
	}
	/**
	 * Verifica o conteúdo de uma mensagem.
	 * 
	 * @param content um objeto que representa o conteúdo da mensagem.
	 * @throws IOException - Exceção levantada quando ocorre um erro de IO.
	 */
	private void verifyInputContent(Object content) throws IOException {
		//TODO este método REALMENTE eh publico?! alguem + precisa dele??
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
	 * Checa o conteúdo de uma mensagem, transformando-o de acordo com o tipo de protocolo. 
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
	 * @throws IOException - Exceção levantada caso algum ocorra algum erro de IO.
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
	 * @throws IOException - Exceção levantada caso algum ocorra algum erro de IO.
	 */
	private byte[] getByteArray(File file) throws IOException {
		FileInputStream fileInput = new FileInputStream(file);
		int disp = fileInput.available();
		byte[] fileBytes = new byte[disp];
		fileInput.read(fileBytes);
		return fileBytes;
	}
	/**
	 * Método que retorna o endereço IP destino da mensagem.
	 * 
	 * @return Retorna o <code>destinationIP</code> da mensagem.
	 */
	public String getDestinationIP() {
		return destinationIP;
	}
	/**
	 * Método que modifica o endereço IP destino da mensagem.
	 * 
	 * @param destinationIP O novo <code>destinationIP</code>.
	 */
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}

	/**
	 * Método que retorna a porta destino da mensagem.
	 * 
	 * @return Retorna o <code>destinationPort</code> da mensagem.
	 */
	public int getDestinationPort() {
		return destinationPort;
	}
	/**
	 * Método que modifica a porta destino da mensagem.
	 * 
	 * @param destinationPort A nova <code>destinationPort</code>.
	 */
	public void setDestinationPort(int destinationPort) {
		this.destinationPort = destinationPort;
	}
	/**
	 * Método que retorna o endereço IP da origem da mensagem.
	 * 
	 * @return Retorna o <code>sourceIP</code> da mensagem.
	 */
	public String getSourceIP() {
		return sourceIP;
	}
	/**
	 * Método que modifica o endereço IP origem da mensagem.
	 * 
	 * @param sourceIP O novo <code>sourceIP</code>.
	 */
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}
	/**
	 * Método que retorna a porta origem da mensagem.
	 * 
	 * @return Retorna o <code>sourcePort</code> da mensagem.
	 */
	public int getSourcePort() {
		return sourcePort;
	}
	/**
	 * Método que modifica a porta origem da mensagem.
	 * 
	 * @param sourcePort A nova <code>sourcePort</code>.
	 */
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}
	/**
	 * Método que retorna o nome do arquivo contido numa mensagem.
	 * 
	 * @return Retorna o <code>fileName</code> da mensagem.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Método que altera o nome do arquivo contido numa mensagem.
	 * 
	 * @param fileName O novo <code>fileName</code>.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Método que retorna se o conteúdo de uma mensagem é ou não um arquivo.
	 * 
	 * @return <code>true</code>, se o conteúdo da mensagem for um arquivo,
	 * 		   <code>false</code> caso contrário.
	 */
	public boolean isHasFile() {
		return hasFile;	
	}
	/**
	 * Método que altera a indicação de que a mensagem contém ou não um arquivo.
	 * 
	 * @param hasFile - <code>true</code>, se o conteúdo da mensagem for um arquivo,
	 * 		   			<code>false</code> caso contrário.
	 */
	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}	

	//---------------------------------------------------------------------------
	/*
	 * ISSO É UM TESTE
	 *
	//**
	 * Contrutor da classe message.
	 * 
	 * @param partOfFile - Array de bytes que compõe o arquivo
	 * @param size - Inteiro que indica o conteúdo válido do array
	 * @param destinationIP - String que representa o endereço IP destino.
	 * @param sourceIP - String que representa o endereço IP original.
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
