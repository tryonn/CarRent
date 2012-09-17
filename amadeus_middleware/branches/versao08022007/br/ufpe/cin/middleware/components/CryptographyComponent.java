package br.ufpe.cin.middleware.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;

import br.ufpe.cin.middleware.objects.ServiceMessage;
import br.ufpe.cin.middleware.objects.controlMessages.TCPControlMessage;
import br.ufpe.cin.middleware.util.Port;

/**
 * Classe que representa o componente de criptografia.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class CryptographyComponent extends NotificationBroadcasterSupport
implements NotificationListener, CryptographyComponentMBean {

	private SecretKey key;
	private String algoName = "DESede";
	private byte[] tripleDesKeyData = new String("232349dsmwejcxb87ABC83#)").getBytes();
	//portas
	private Port inPort = new Port(this.getClass().getSimpleName() + "_inPort");
	private Port outPort = new Port(this.getClass().getSimpleName() + "_outPort");
	private boolean ready = false;

	
	/**
	 * Construtor da classe CryptographyComponent.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public CryptographyComponent() throws NoSuchAlgorithmException {
		this.key = new SecretKeySpec(tripleDesKeyData,algoName);
	}

	/**
	 * Método que criptografa um objeto.
	 * 
	 * @param msg - objeto a ser criptografado
	 * @return Retorna um objeto criptografado.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public Object encrypt(Object msg) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException {
		byte[] originalMessage = null;
		Cipher cipher = Cipher.getInstance(algoName);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		if (msg instanceof File) {
			FileInputStream fileInput = new FileInputStream((File)msg);
			int disp = fileInput.available();
			originalMessage = new byte[disp];
			fileInput.read(originalMessage);
		} else {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bStream);
			os.writeObject(msg);
			os.flush();
			originalMessage = bStream.toByteArray();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CipherOutputStream cos = new CipherOutputStream(baos, cipher);
		cos.write(originalMessage);
		cos.close();

		java.util.Arrays.fill(originalMessage, (byte) 0);
		return baos.toByteArray();
	}

	/**
	 * Método que descriptografa um objeto.
	 * 
	 * @param msg - objeto a ser descriptografado.
	 * @return Retorna um objeto descriptografado.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws ClassNotFoundException
	 */
	private Object decrypt(Object msg) throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, ClassNotFoundException {
		byte[] bytes = (byte[]) msg;
		Cipher cipher = Cipher.getInstance(algoName);
		cipher.init(Cipher.DECRYPT_MODE, key);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CipherOutputStream cos = new CipherOutputStream(baos, cipher);
		cos.write(bytes);
		cos.close();

		byte[] decrypted = baos.toByteArray();

		InputStream inputStream = null;
		boolean isObject = false;
		File file = new File("received");

		ByteArrayInputStream bytearray = new ByteArrayInputStream(decrypted);
		DataInputStream data = new DataInputStream(bytearray);
		try {
			inputStream = new ObjectInputStream(data);
			isObject = true;
		} catch(StreamCorruptedException e) {
			FileOutputStream outPut = new FileOutputStream(file);
			outPut.write(decrypted);
			outPut.flush();
		}

		Object returned = null;
		if(isObject) {
			returned = ((ObjectInputStream) inputStream).readObject();
		} else {
			returned = file;
		}
		return returned;
	}
	/**
	 * Escreve um objeto na porta de entrada deste componente.
	 * 
	 * @param method - Método a ser utilizado.
	 * @param obj - Objeto a ser escrito.
	 * @throws IOException
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public void writeInPort(Object obj, String method) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		this.inPort.write(method);
		this.inPort.write(obj);
		this.process();
	}

	/**
	 * Lê um objeto da porta de saída deste componente.
	 * 
	 * @return Retorna o objeto lido da porta de saída.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object readOutPort() throws IOException, ClassNotFoundException{
		while(!this.ready){}
		this.ready = false;
		Object returnObj = this.outPort.read(0); 
		this.outPort.clear();
		return returnObj;
	}
	/**
	 * Faz o processamento desta classe, tratando portas de saída e de entrada.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private void process()  throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String method;
		try {
			method = (String) this.inPort.read(0);
			Object msg = null;
			if(method.equals("send") || method.equals("sendTo") || method.equals("sendToAll")){
				msg = this.encrypt(this.inPort.read(1));
			}else if(method.equals("receive")){
				msg = this.inPort.read(1);
				//TODO Não precisa decriptografar se a msg for TCPControlMessage ou ServiceMessage
				if (!((msg instanceof TCPControlMessage) || (msg instanceof ServiceMessage)))
					msg = this.decrypt(this.inPort.read(1));
			}
			this.inPort.clear();
			this.outPort.write(msg);
		} finally {
			this.ready = true;
		}
	}

	public void handleNotification(Notification arg0, Object arg1) {}

}