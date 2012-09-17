package workgroup.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que gerencia a troca de mensagens
 * @author amadeus
 *
 */

public abstract class Message implements Serializable {
	
	
	protected HashMap<String, String> headers = null;
	private byte[]  data = null;
	private Date reciveTime = null;
	
	/**
	 * Construtor
	 */

	public Message() {
	}
	
	/**
	 * Construtor
	 * 
	 * @param data Recebe o objeto (mensagem) a ser enviado
	 */

	public Message(Serializable data) {
		setObject(data);
	}
	
	/**
	 * Método que retorna todos os headers 
	 * 
	 * @return Map todos os headers da classe
	 */

	public Map getHeaders() {
		return headers;
	}

	/**
	 * Método que inclui um header na lista de headers
	 * 
	 * @param key A chave (índice) do hashmap
	 * @param hdr O header em si
	 */

	public void putHeader(String key, String hdr) {
		if (headers == null)
			headers = new HashMap<String, String>();
		headers.put(key, hdr);
	}

	/**
	 * Método que remove um header da lista de headers
	 * 
	 * @param key O índice necessário para localizar o header no hashmap
	 * @return String O header em si 
	 */

	public String removeHeader(String key) {
		return headers != null ? headers.remove(key) : null;
	}

	/**
	 * Método que remove todos os headers do hashmap
	 */

	public void removeHeaders() {
		if(headers != null)
			headers.clear();
	}

	/**
	 * Método que retorna um header do hashmap
	 * 
	 * @param key O índice necessário para localizar o header no hashmap
	 * @return O header em si
	 */

	public String getHeader(String key) {
		return headers != null ? headers.get(key) : null;
	}

	/**
	 * Método que remove todos os headers do hashmap e apaga o array de dados da mensagem
	 */
	public void reset() {
		setData(null);
		if(headers != null)
			headers.clear();
	}

	/**
	 * Método que retorna os dados da mensagem
	 * 
	 * @return byte[] O array de bytes que contém o dado da mensagem
	 */
	public byte[] getData() {
		if(data == null)
			return null;
			
		return data;
	}

	/**
	 * Método que modifica o dado da mensagem com o dado fornecido
	 * 
	 * @param b O novo dado a ser armazenado no array
	 */
	public void setData(byte[] b) {
		data = b;
	}

	/**
	 * Método que recebe um objeto, o torna um array de bytes e o armazena no
	 * array de bytes (atributo da classe)
	 * 
	 * @param obj Objeto a ser armazenado no array de bytes
	 */
	public void setObject(Serializable obj) {
		if(obj == null) return;
		try {
			ByteArrayOutputStream out_stream = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(out_stream);
			out.writeObject(obj);
			setData(out_stream.toByteArray());
		}
		catch(IOException ex) {
			throw new IllegalArgumentException(ex.toString());
		}
	}

	/**
	 * Método que pega o array de bytes (atributo da classe) e o transforma 
	 * num objeto e o retorna
	 * 
	 * @return Object O objeto que estava armazenado no array de bytes
	 */
	public Object getObject() {
		if(data == null) return null;
		try {
			ByteArrayInputStream in_stream=new ByteArrayInputStream(data);
			ObjectInputStream in=new ObjectInputStream(in_stream);
			return in.readObject();
		}
		catch(Exception ex) {
			throw new IllegalArgumentException(ex.toString());
		}
	}

	/**
	 * Método que retorna a data de recebimento de algum dado
	 * 
	 * @return Date A data de recebimento de algum dado
	 */
	public Date getReciveTime() {
		return reciveTime;
	}

	/**
	 * Método que seta a data de recebimento de algum dado para a data fornecida
	 * 
	 * @param reciveTime A data a ser guardada como data de recebimento de algum dado
	 */
	public void setReciveTime(Date reciveTime) {
		this.reciveTime = reciveTime;
	}
}
