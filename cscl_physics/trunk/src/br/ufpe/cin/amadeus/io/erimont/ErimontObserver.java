package br.ufpe.cin.amadeus.io.erimont;

/**
 * Interface observadora
 * 
 * @author amadeus
 * @version 1.0
 */
public interface ErimontObserver {

	/**
	 * M�todo que intercepta o sensor
	 * 
	 * @param nanoTime Tempo da intercepta��o do sensor
	 */
	public void interceptSensor(long nanoTime);
	
}
