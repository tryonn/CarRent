package br.ufpe.cin.amadeus.io.erimont;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.io.Source;

/**
 * Classe adaptadora do leitor da porta paralela
 * 
 * @author Amadeus
 * @version 1.0
 *  
 */

public class ErimontAdapter implements Runnable{
	
	private Source source;
	
	
	private volatile Thread readThread = null;
	private volatile ErimontNotifier notifyThread = null;

	private ErimontObserver observer = null;
	private boolean stopRead = false;
	private int nanoToSleep = 100;
	
	/**
	 * Construtor da classe
	 * 
	 * @param source Fonte da leitura
	 */
	public ErimontAdapter(Source source) {
		this.source = source;
	}

	/**
	 * Método que escreve um byte
	 * 
	 * @param aByte Byte a ser escrito
	 */
	public void write(int aByte) {
		source.write(aByte);
	}
	
	/**
	 * Método que inicia a leitura da porta paralela
	 *
	 */
	public void startRead() {
		if (readThread == null) {
			readThread = new Thread(this);
			notifyThread = new ErimontNotifier();
		}
		stopRead = false;
		readThread.start();
		notifyThread.start();
	}

	/**
	 * Método que finaliza a leitura da porta paralela
	 *
	 */
	public void stopRead() {
		if (readThread != null) {
			stopRead = true;
		}
		readThread = null;
	}
	
	/**
	 * Método que substitui o observador atual pelo passado como parâmetro
	 * 
	 * @param observer Novo observador
	 */
	public void setObserver(ErimontObserver observer) {
		this.observer = observer;
	}
	
	/**
	 * Método que notifica um observador que houve uma interceptação do sensor
	 * 
	 * @param nanoTime Tempo da interceptação do sensor
	 */
	private void notifyObserver(long nanoTime) {
			this.observer.interceptSensor(nanoTime);
	}

	/**
	 * Método que inicia a thread
	 */
	public void run() {

		int aByte;
		long time;
		boolean leiaTempo = true;
		
		while (! stopRead) {
		
		    aByte = source.read();      // Lê um byte da porta paralela
			time = System.nanoTime();   // Lê tempo em nanosegundos
			
			
			if (leiaTempo && aByte == 127) {
				
				notifyThread.add(time); // Adiciona o tempo tomado para ser 
				                        // notificado aos observadores
				leiaTempo = false;
			}
			
			if (aByte != 127) {
				leiaTempo = true;
			} 
			
			try {
				Thread.sleep(0, this.nanoToSleep);
			} catch (InterruptedException e) {
				// Não faz nada
			}
		}
		
	}

	/**
	 * Método que retorna o tempo que a thread deverá "dormir"
	 * 
	 * @return int tempo que a thread deverá "dormir"
	 */
	public int getNanoToSleep() {
		return nanoToSleep;
	}

	/**
	 * Método que modifica o tempo que a thread deverá "dormir"
	 * 
	 * @param nanoToSleep Novo tempo o qual a thread deverá "dormir"
	 */
	public void setNanoToSleep(int nanoToSleep) {
		this.nanoToSleep = nanoToSleep;
	}

	/**
	 * Classe Thread notificadora. Responsável pela notificação de todos os 
	 * observers.
	 * 
	 * @author Amadeus
	 * @version 1.0
	 */
	public class ErimontNotifier extends Thread {

		private List<Long> data = new ArrayList<Long>();
		
		/**
		 * Método que adiciona um tempo à lista de isntantes de interceptação 
		 * do sensor
		 * 
		 * @param time Novo tempo a ser adicionado à lista
		 */
		public void add(Long time) {
			data.add(time);
		}
		
		/**
		 * Método que "roda" a thread
		 */
		public void run() {

			while (! stopRead) {
				if (! data.isEmpty()) {
					long time = data.remove(0);
					notifyObserver(time);
				}
				
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// Não faz nada
				}
			}
			
			data.clear();
		}
	}
}
