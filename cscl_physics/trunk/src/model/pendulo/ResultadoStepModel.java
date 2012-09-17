package model.pendulo;

import br.ufpe.cin.amadeus.io.ParallelSource;
import br.ufpe.cin.amadeus.io.erimont.ErimontAdapter;
import br.ufpe.cin.amadeus.io.erimont.ErimontObserver;

/**
 * Classe que cont�m a estrutura de dados do passo Resultado
 * 
 * @author amadeus
 * @version 1.0
 */
public class ResultadoStepModel extends StepModel implements ErimontObserver {

	private static final int TIME_TO_READ = 10;
	private static int countRead = 0;
	
	private boolean reading = false;

	private transient ErimontAdapter erimont = new ErimontAdapter(new ParallelSource());
	private ResultadoTableModel tableModel = new ResultadoTableModel();

	/**
	 * Construtor da classe 
	 *  
	 * @param order Ordem do passo dentre os demais passos do programa
	 */
	public ResultadoStepModel(int order) {
		super(order);
		erimont.setObserver(this);
	}

	/**
	 * M�todo que retorna false porque este modelo n�o possui instru��es
	 * 
	 * @return boolean Sempre false
	 */
	public boolean next() {
		return false;
	}

	/**
	 * M�todo que cancela a leitura da porta paralela e retorna false,
	 * j� que este modelo n�o possui instru��es 
	 * 
	 * @return boolean Sempre false
	 */
	public boolean prior() {
		if ( isReading() ) {
			cancelRead();
		}
		return false;
	}
	
	/**
	 * M�todo que inicia a leitura da porta paralela
	 */
	public void onEnter() {
		readSensor();
	}

	/**
	 * M�todo que l� da porta paralela
	 *
	 */
	public void readSensor() {
		reading = true;
		countRead = 0;
		tableModel.clear();
		erimont.startRead();
	}
	
	/**
	 * M�todo que cancela a leitura da porta paralela
	 *
	 */
	public void cancelRead() {
		erimont.stopRead();
		reading = false;
		tableModel.clear();
	}

	/**
	 * M�todo que adiciona mais um tempo � tabela de resultados e notifica
	 * o fim da leitura quando necess�rio
	 */
	public void interceptSensor(long nanoTime) {
		/* System.out.println(nanoTime);*/
		if (countRead < TIME_TO_READ + 2) {
			tableModel.addTime(nanoTime);
		} else {
			erimont.stopRead();
			reading = false;
			notifyObservers();
		}
		countRead++;
	}

	/**
	 * M�todo que diz se a leitura da porta paralela ainda est� em andamento
	 * 
	 * @return boolean O andamento da leitura
	 */
	public boolean isReading() {
		return reading;
	}

	/**
	 * M�todo que retorna o modelo da tabela de resultados
	 * 
	 * @return ResultadoTableModel o modelo da tabela de resultados
	 */
	public ResultadoTableModel getTableModel() {
		return tableModel;
	}
}
