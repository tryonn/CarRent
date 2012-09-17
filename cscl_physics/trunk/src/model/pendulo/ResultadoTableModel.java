package model.pendulo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import util.resource.Resource;

/**
 * Classe que contém a estrutura de dados da tabela de resultados
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
	 * Método que limpa a lista de tempos
	 *
	 */
	public void clear() {
		times.clear();
	}
	
	/**
	 * Método que adiciona um tempo à tabela
	 * 
	 * @param time Tempo a ser adicionado na tabela
	 */
	public void addTime(Long time) {
		times.add(time);
	}
	
	/**
	 * Método que retorna o nome da coluna da tabela
	 * 
	 * @param col Indicador da tabela cujo nome será retornado
	 * @return String O nome da coluna
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/**
	 * Método que retorna o número de linhas da tabela
	 * 
	 * @return int O número de linhas da tabela
	 */
	public int getRowCount() {
		int rowCount = times.size() - 2;
		if (rowCount < 0) {
			rowCount = 0;
		}
		return rowCount;
	}

	/**
	 * Método que retorna o número de colunas da tabela
	 * 
	 * @return int O número de colunas da tabela
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Método que retona o valor da célula indicada pelos parâmetros
	 * 
	 * @param row Linha da célula
	 * @param col Coluna da célula
	 * @return Object objeto que se encontra na célula cuja linha e coluna 
	 * foram fornecidas como parâmetro
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
	 * Método que diz se uma célula é editável ou não (todas são)
	 * 
	 * @param row Linha da célula
	 * @param col Coluna da célula
	 * @return boolean Sempre false
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
