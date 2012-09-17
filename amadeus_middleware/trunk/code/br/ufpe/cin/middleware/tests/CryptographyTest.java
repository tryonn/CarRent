package br.ufpe.cin.middleware.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class CryptographyTest {
  
	private SecretKey key;
	
	public CryptographyTest() throws NoSuchAlgorithmException {
		this.key = this.generateKey();
	}
	
  public SecretKey generateKey() throws NoSuchAlgorithmException {
    KeyGenerator keygen = KeyGenerator.getInstance("DESede");
    return keygen.generateKey();
  }

  public Object encrypt(Object msg) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException {
	  byte[] originalMessage = null;
	  Cipher cipher = Cipher.getInstance("DESede");
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
   * Use the specified TripleDES key to decrypt bytes ready from the input
   * stream and write them to the output stream. This method uses uses Cipher
   * directly to show how it can be done without CipherInputStream and
   * CipherOutputStream.
 * @throws ClassNotFoundException 
   */
  public Object decrypt(Object msg) throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, ClassNotFoundException {
	  Cipher cipher = Cipher.getInstance("DESede");
	  cipher.init(Cipher.DECRYPT_MODE, key);
    
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	  CipherOutputStream cos = new CipherOutputStream(baos, cipher);
	  cos.write((byte[])msg);
	  cos.close();
	  
	  byte[] decrypted = baos.toByteArray();
	  ByteArrayInputStream bytearray = new ByteArrayInputStream(decrypted);
	  DataInputStream data = new DataInputStream(bytearray);
	  ObjectInputStream object = new ObjectInputStream(data);
	  Object re = object.readObject();
	  return re;
  }
}