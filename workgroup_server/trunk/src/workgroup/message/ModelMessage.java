package workgroup.message;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe responsável por armazenar a data da mensagem
 * 
 * @author amadeus
 *
 */
public class ModelMessage extends Message {

	private static final long serialVersionUID = 13L;
	
	private Date timeStamp;
	
	/**
	 * Construtor da classe
	 * 
	 * @param model Objeto a ser armazenado na mensagem (ver construtor de Mensagem)
	 */
	public ModelMessage(Serializable model) {
		super(model);
		setTimeStamp(new Date());
	}
	
	/**
	 * Método que retorna a data da mensagem
	 * 
	 * @return Date A data da mensagem
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Método que altera a data da mensagem para a data fornecida
	 * 
	 * @param date A data a ser tida como "data da mensagem"
	 */
	public void setTimeStamp(Date date) {
		timeStamp = date;
	}

}
