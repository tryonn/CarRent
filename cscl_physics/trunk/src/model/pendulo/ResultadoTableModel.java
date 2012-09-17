package model.pendulo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import util.resource.Resource;

/**
 * Classe que cont�m a estrutura de dados da tabela de resultados
 * 
 * @author amadeus
 * @version 1.0
 */
public class ResultadoTableModel extends AbstractTableModel {

	private String[] columnNames = {Resource.columnNameInstant, Resource.columnNameTime}; 
	private List<Long> times = new ArrayList<Long>();
	
	/**
	 * Construtor da tabela
	 *
	 */
	public ResultadoTableModel() {
		/*
		times.add(23384758420413D);
		times.add(23385316017817D);
		times.add(23385819916777D);
		times.add(23386375553596D);
        times.add(23386878468911D);
		times.add(23387435088258D);
		times.add(23387937027192D);
		times.add(23388495600977D);
		times.add(23388997537675D);
		times.add(23389554160654D);
		times.add(23390057073734D);
		times.add(23390614681474D);
		times.add(23391116608954D);
		times.add(23391675185253D);
		times.add(23392176146409D);
		times.add(23392734721591D);
		times.add(23393235702302D);
		*/
	}

	/**
	 * M�todo que limpa a lista de tempos
	 *
	 */
	public void clear() {
		times.clear();
	}
	
	/**
	 * M�todo que adiciona um tempo � tabela
	 * 
	 * @param time Tempo a ser adicionado na tabela
	 */
	public void addTime(Long time) {
		times.add(time);
	}
	
	/**
	 * M�todo que retorna o nome da coluna da tabela
	 * 
	 * @param col Indicador da tabela cujo nome ser� retornado
	 * @return String O nome da coluna
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/**
	 * M�todo que retorna o n�mero de linhas da tabela
	 * 
	 * @return int O n�mero de linhas da tabela
	 */
	public int getRowCount() {
		int rowCount = times.size() - 2;
		if (rowCount < 0) {
			rowCount = 0;
		}
		return rowCount;
	}

	/**
	 * M�todo que retorna o n�mero de colunas da tabela
	 * 
	 * @return int O n�mero de colunas da tabela
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * M�todo que retona o valor da c�lula indicada pelos par�metros
	 * 
	 * @param row Linha da c�lula
	 * @param col Coluna da c�lula
	 * @return Object objeto que se encontra na c�lula cuja linha e coluna 
	 * foram fornecidas como par�metro
	 */
	public Object getValueAt(int row, int col) {
		Object retorno = null;
		if (col == 0) {
			retorno = "t" + row;
		} else {
			retorno = ((double)times.get(row + 2) - times.get(row))/1000000000;
		}
		return retorno;
	}

	/**
	 * M�todo que diz se uma c�lula � edit�vel ou n�o (todas s�o)
	 * 
	 * @param row Linha da c�lula
	 * @param col Coluna da c�lula
	 * @return boolean Sempre false
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
