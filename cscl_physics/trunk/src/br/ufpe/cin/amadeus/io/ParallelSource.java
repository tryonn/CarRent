package br.ufpe.cin.amadeus.io;

import parport.ParallelPort;
import util.resource.Resource;

/**
 * Classe adaptadora para leitura de porta paralela. 
 * Para que esta classe funcione adequadamente é necessário que o arquivo
 * "parport.dll" esteja no diretorio bin do java
 * 
 * @author Amadeus
 * @version 1.0
 *  
 */

public class ParallelSource implements Source {
	
	public static int LPT1_PORT = 0x378; 
	public static int LPT2_PORT = 0x278; 
	
	/** Endereço padrão para porta LPT1 */
	private static int DEFAULT_PORT = LPT1_PORT; 
	
	private ParallelPort parallelPort;
	
	/**
	 * Cria um objeto ParallelSource para acesso a porta padrão LPT1
	 */
	public ParallelSource() {
		parallelPort = new ParallelPort(DEFAULT_PORT);
	}

	/**
	 * Cria um objeto ParallelSource para acesso a porta paralela 
	 * com o endereço especificado no parâmetro port
	 * 
	 * @param port Endereço da porta paralela
	 */
	public ParallelSource(int port) {
		parallelPort = new ParallelPort(port);
	}

	/**
	 * Retorna o nome da fonte da porta paralela
	 * 
	 * @return String nome da fonte
	 */
	public String getSourceName() {
		return Resource.sourceName;
	}

	/** 
	* 
	* Lê um byte a partir do endereço de STATUS da porta paralela
	* 
    * The byte read contains 5 valid bits, corresponing to 5 pins of input
    * from the STATUS pins of the parallel port (the STATUS is located
    * at "portBase + 1", e.g. the STATUS address for LPT1 is 0x379).
    * 
    * This diagram shows the content of the byte:
    *
    *  Bit | Pin # | Printer Status  | Inverted
    * -----+-------+-----------------+-----------
    *   7  |  ~11  | Busy            |   Yes
    *   6  |   10  | Acknowledge     |
    *   5  |   12  | Out of paper    |
    *   4  |   13  | Selected        |
    *   3  |   15  | I/O error       |
    * 
    * Note that Pin 11 is inverted, this means that "Hi" input on pin
    * means 0 on bit 7, "Low" input on pin means 1 on bit 7.
    */
	public int read() {
		return parallelPort.read();
	}

	/** 
	* 
	* Escreve um byte para o endereço de dados da porta paralela
	*  
    * The byte is written to the DATA pins of the port. The DATA pins are
    * located at the base address of the port (e.g. DATA address for LPT1
    * is 0x378).
    *
    * This diagram shows how the byte is written:
    *
    *  Bit | Pin # | Printer DATA
    * -----+-------+--------------
    *   7  |   9   |   DATA 7
    *   6  |   8   |   DATA 6
    *   5  |   7   |   DATA 5
    *   4  |   6   |   DATA 4
    *   3  |   5   |   DATA 3
    *   2  |   4   |   DATA 2
    *   1  |   3   |   DATA 1
    *   0  |   2   |   DATA 0
    */
	public void write(int aByte) {
		parallelPort.write(aByte);
	}

}
