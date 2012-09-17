package br.ufpe.cin.amadeus.io;

/**
 * Interface para uso da porta paralela
 * 
 * @author amadeus
 * @version 1.0
 */
public interface Source {
	
	/**
	 * Método que retorna o nome da Fonte
	 * 
	 * @return O nome da fonte
	 */
	public String getSourceName();
	
	/**
	 * Método que lê um byte e o retorna 
	 * 
	 * @return int O byte lido sob a forma de inteiro
	 */
	
	public int read();
	
	/**
	 * Método que escreve um byte
	 * 
	 * @param aByte Byte a ser escrito (sob a forma de inteiro)
	 */
	public void write(int aByte);
	
}
