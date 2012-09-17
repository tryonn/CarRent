package br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;
public class DiscoursiveQuestionAnswerHibernateDAO extends GenericHibernateDAO<DiscoursiveQuestionAnswer,Integer> implements DiscoursiveAnswerDAO{
	
	public DiscoursiveQuestionAnswerHibernateDAO(){
		super(DiscoursiveQuestionAnswer.class);
	}

}
