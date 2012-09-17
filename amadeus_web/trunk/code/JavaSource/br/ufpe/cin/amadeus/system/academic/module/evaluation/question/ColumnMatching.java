package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.column.Column;

public class ColumnMatching extends ObjectiveQuestion {
	
	private List<Column> columns;
	
	public ColumnMatching(){
		this.columns = new ArrayList<Column>();
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public void addColumn(Column col){
		this.columns.add(col);
	}

}
