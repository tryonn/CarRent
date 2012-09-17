package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class DiscoursiveQuestionHibernateDAO extends GenericHibernateDAO<DiscoursiveQuestion, Integer> implements DiscoursiveQuestionDAO{
	
	public DiscoursiveQuestionHibernateDAO(){
		super(DiscoursiveQuestion.class);
	}

}
