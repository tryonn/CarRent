package br.ufpe.cin.middleware.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import br.ufpe.cin.middleware.util.Debug;

/**
 * This class defines methods for encrypting and decrypting using the Triple DES
 * algorithm and for generating, reading and writing Triple DES keys. It also
 * defines a main() method that allows these methods to be used from the command
 * line.
 */
public class Teste {
  
	private SecretKey key;
	
	public Teste() throws NoSuchAlgorithmException {
		this.key = this.generateKey();
	}
	
  /** Generate a secret TripleDES encryption/decryption key */
  public SecretKey generateKey() throws NoSuchAlgorithmException {
    KeyGenerator keygen = KeyGenerator.getInstance("DESede");
    return keygen.generateKey();
  }

  /**
   * Use the specified TripleDES key to encrypt bytes from the input stream
   * and write them to the output stream. This method uses CipherOutputStream
   * to perform the encryption and write bytes at the same time.
   */
  public Object encrypt(Object msg) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException {
	  byte[] originalMessage = null;
	  // Create and initialize the encryption engine
	  Cipher cipher = Cipher.getInstance("DESede");
	  cipher.init(Cipher.ENCRYPT_MODE, key);
    
	  if (msg instanceof String) {
		  //originalMessage = ((String)msg).getBytes();
		  
	  } else if (msg instanceof File) {
		  FileInputStream fileInput = new FileInputStream((File)msg);
		  int disp = fileInput.available();
		  originalMessage = new byte[disp];
		  fileInput.read(originalMessage);
	  }
	  // Create a special output stream to do the work for us
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	  CipherOutputStream cos = new CipherOutputStream(baos, cipher);
	  cos.write(originalMessage);
	  cos.close();

	  // For extra security, don't leave any plaintext hanging around memory.
	  java.util.Arrays.fill(originalMessage, (byte) 0);
	  return baos.toByteArray();
  }

  /**
   * Use the specified TripleDES key to decrypt bytes ready from the input
   * stream and write them to the output stream. This method uses uses Cipher
   * directly to show how it can be done without CipherInputStream and
   * CipherOutputStream.
   */
  public Object decrypt(Object msg) throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
	  // Create and initialize the decryption engine
	  Cipher cipher = Cipher.getInstance("DESede");
	  cipher.init(Cipher.DECRYPT_MODE, key);
    
	  // Create a special output stream to do the work for us
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	  CipherOutputStream cos = new CipherOutputStream(baos, cipher);
	  cos.write((byte[])msg);
    
	  // Write out the final bunch of decrypted bytes
	  //cos.write(cipher.doFinal());
	  //cos.flush();
	  return baos.toByteArray();
  }
  
  public static void main(String[] args) {
	try {
		Teste test = new Teste();
		byte[] encryptedMessage = (byte[])test.encrypt("Eliaquim");
		String encriptada = new String(encryptedMessage);
		System.out.println("Olha como ficou a string depois do encrypt: " + encriptada);
		byte decryptedMessage[] = (byte[]) test.decrypt(encryptedMessage);
		String decriptada = new String(decryptedMessage);
		System.out.println("Olha como ficou a string depois do decrypt: " + decriptada);
		
	} catch (NoSuchAlgorithmException e) {
		Debug.printStack(e);
	} catch (InvalidKeyException e) {
		Debug.printStack(e);
	} catch (NoSuchPaddingException e) {
		Debug.printStack(e);
	} catch (IOException e) {
		Debug.printStack(e);
	} catch (IllegalBlockSizeException e) {
		Debug.printStack(e);
	} catch (BadPaddingException e) {
		Debug.printStack(e);
	}
  }
}