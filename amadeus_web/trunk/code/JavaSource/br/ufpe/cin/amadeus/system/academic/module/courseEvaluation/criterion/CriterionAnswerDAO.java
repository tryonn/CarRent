package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface CriterionAnswerDAO extends GenericDAO<CriterionAnswer, Integer> {
	
	public int getFrequencyVotes(int i, int j,int k, int criterion, int eval);

}
