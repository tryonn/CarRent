package br.ufpe.cin.middleware.components;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;

import br.ufpe.cin.middleware.util.Port;

/**
 * Classe que representa o componente de compressão.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public class CompressionComponent extends NotificationBroadcasterSupport
implements CompressionComponentMBean, NotificationListener {

	private final int BUFFER = 2048;
	private Port inPort = new Port(this.getClass().getSimpleName() + "_inPort");
	private Port outPort = new Port(this.getClass().getSimpleName() + "_outPort");
	private boolean ready = false;

	/**
	 * Construtor da classe CompressionComponent.
	 */
	public CompressionComponent() {}

	/**
	 * Faz o marshalling de um arquivo, comprimindo-o.
	 * 
	 * @param original - o arquivo a ser feito marshalling.
	 * @return Retorna o arquivo comprimido.
	 * @throws IOException
	 */
	private Object marshalling(Object original) throws IOException {
		if(original instanceof File) {
			BufferedInputStream origin = null;
			File compressedFile = new File("compressed.zip");
			FileOutputStream dest = new FileOutputStream(compressedFile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];
			File f = (File) original;
			FileInputStream fi = new FileInputStream(f);
			origin = new BufferedInputStream(fi, BUFFER);
			ZipEntry entry = new ZipEntry(f.getName());
			out.putNextEntry(entry);
			int count;
			while((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
			out.close();
			return compressedFile;
		} else {
			return original;	
		}
	}

	/**
	 * Faz o unmarshalling de um arquivo, descomprimindo-o
	 * 
	 * @param compressed - arquivo comprimido a ser feito unmarshalling.
	 * @return Retorna o arquivo descomprimido.
	 * @throws IOException
	 */
	private Object unMarshalling(Object compressed) throws IOException {
		BufferedOutputStream dest;
		if(compressed instanceof File) {
			FileInputStream fInStream = new FileInputStream((File)compressed);
			ZipInputStream zipStream = new ZipInputStream(new BufferedInputStream(fInStream));
			File fileReturn = null;
			ZipEntry entry = zipStream.getNextEntry();
			if(entry != null) {
				int count;
				byte data[] = new byte[BUFFER];
				fileReturn = new File(entry.getName());
				FileOutputStream fos = new 
				FileOutputStream(fileReturn);
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zipStream.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zipStream.close();
			return fileReturn;
		} else {
			return compressed;
		}
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
				msg = this.marshalling(this.inPort.read(1));
			}else if(method.equals("receive")){
				msg = this.unMarshalling(this.inPort.read(1));
			}
			this.inPort.clear();
			this.outPort.write(msg);
		} finally {
			this.ready = true;
		}
	}

	public void handleNotification(Notification arg0, Object arg1) {}

}
