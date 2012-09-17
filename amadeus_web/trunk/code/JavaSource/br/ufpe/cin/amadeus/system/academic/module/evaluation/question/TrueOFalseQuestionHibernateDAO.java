package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class TrueOFalseQuestionHibernateDAO extends GenericHibernateDAO<TrueOrFalseQuestion, Integer> implements TrueOrFalseQuestionDAO{

	public TrueOFalseQuestionHibernateDAO() {
		super(TrueOrFalseQuestion.class);
		// TODO Auto-generated constructor stub
	}

}
