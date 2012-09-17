package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.evaluationAnswer;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class EvaluationAnswerHibernateDAO extends GenericHibernateDAO<EvaluationAnswer, Integer> implements EvaluationAnswerDAO {
	
	public EvaluationAnswerHibernateDAO(){
		super(EvaluationAnswer.class);
	}

}
