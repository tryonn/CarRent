package br.ufpe.cin.amadeus.io.erimont;

/**
 * Interface observadora
 * 
 * @author amadeus
 * @version 1.0
 */
public interface ErimontObserver {

	/**
	 * Método que intercepta o sensor
	 * 
	 * @param nanoTime Tempo da interceptação do sensor
	 */
	public void interceptSensor(long nanoTime);
	
}
