package br.ufpe.cin.middleware.components;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 * Interface que representa as opera��es de CryptographyComponent
 * que s�o vis�veis externamente.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 * 
 */
public interface CryptographyComponentMBean extends ComponentAbstract {
	
	public void writeInPort(Object obj, String method) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
	public Object readOutPort() throws IOException, ClassNotFoundException;
}