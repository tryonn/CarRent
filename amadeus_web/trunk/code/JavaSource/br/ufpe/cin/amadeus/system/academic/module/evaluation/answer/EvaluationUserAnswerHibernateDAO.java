package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class EvaluationUserAnswerHibernateDAO extends GenericHibernateDAO<EvaluationUserAnswer,Integer> implements EvaluationUserAnswerDAO {
	
	public EvaluationUserAnswerHibernateDAO(){
		super(EvaluationUserAnswer.class);
	}

	
	public List<EvaluationUserAnswer> getListEvaluationUserAnswer(int evaluationID){
		Session session  = this.getSession();
		SQLQuery query= session.createSQLQuery("SELECT {c.*} FROM evaluation_useranswer {c} WHERE sqevaluation = "+evaluationID).addEntity("c", EvaluationUserAnswer.class);
		List<EvaluationUserAnswer> list = query.list();
		return list;
	}
	
	public EvaluationUserAnswer getEvaluationUserAnswer(int evaluationID, int userID){
		Session session  = this.getSession();
		SQLQuery query= session.createSQLQuery("SELECT {c.*} FROM evaluation_useranswer {c} WHERE sqevaluation = "+evaluationID+" and squser = "+userID).addEntity("c", EvaluationUserAnswer.class);
		EvaluationUserAnswer answer = (EvaluationUserAnswer) query.uniqueResult();
		
		return answer;
	}
}
