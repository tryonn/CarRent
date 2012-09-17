package br.ufpe.cin.amadeus.system.academic.module.evaluation;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class EvaluationHibernateDAO extends GenericHibernateDAO<Evaluation, Integer> implements EvaluationDAO {
	
	public EvaluationHibernateDAO(){
		super(Evaluation.class);
	}
	
	
	
	/**
	 * id do modulo
	 */
	public List<Evaluation> getEvaluations_module(int sqmodule){
		Session session = this.getSession();
		Query query = session.createSQLQuery("SELECT {e.*} FROM evaluation {e} WHERE sqmodule = "+sqmodule).addEntity("e",Evaluation.class);
		List<Evaluation> list = query.list();
		return list;
	}
	
	
}
