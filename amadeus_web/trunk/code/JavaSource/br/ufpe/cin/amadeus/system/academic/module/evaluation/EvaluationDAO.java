package br.ufpe.cin.amadeus.system.academic.module.evaluation;


import java.util.List;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface EvaluationDAO extends GenericDAO<Evaluation, Integer>{
	
	public List<Evaluation> getEvaluations_module(int id);
	
}
