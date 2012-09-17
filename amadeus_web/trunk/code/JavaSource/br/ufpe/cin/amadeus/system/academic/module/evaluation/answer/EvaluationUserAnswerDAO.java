package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer;

import java.util.List;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface EvaluationUserAnswerDAO extends GenericDAO<EvaluationUserAnswer,Integer>{

	public List<EvaluationUserAnswer> getListEvaluationUserAnswer(int evaluationID);
	
	public EvaluationUserAnswer getEvaluationUserAnswer(int evaluationID, int userID);
}
