package br.ufpe.cin.middleware.util;

import java.io.IOException;

/**
 * 
 * Classe que representa uma porta.
 * Cada componente possui duas portas: uma de entrada e outra de saída,
 * através das quais os componentes comunicam-se com outras entidades. 
 * 
 * @author Bruno Barros (blbs@cin.ufpe.br) 
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 *
 */
public class Port {
	
	private Object port[];
	private int indice;
	
	public Port(String portName) {
		this.port = new Object[10000];
		this.indice = 0;
		
	}
	
	public void write(Object obj) throws IOException {
		this.port[indice] = obj;
		indice++;
	}

	public Object read(int i) throws IOException, ClassNotFoundException{
		return this.port[i];
	}
	
	public void clear() throws IOException{
		this.indice = 0;
	}
	
	
}
