package br.ufpe.cin.amadeus.system.human_resources.user_request.request_evaluation;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class RequestEvaluationHibernateDAO extends GenericHibernateDAO<RequestEvaluation, Integer> implements RequestEvaluationDAO{
	
	public RequestEvaluationHibernateDAO(){
		super(RequestEvaluation.class);
	}

}
