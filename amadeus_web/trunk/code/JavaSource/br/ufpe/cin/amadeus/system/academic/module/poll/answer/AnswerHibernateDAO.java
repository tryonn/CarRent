package br.ufpe.cin.amadeus.system.academic.module.poll.answer;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class AnswerHibernateDAO 
	extends GenericHibernateDAO<Answer, Integer>
		implements AnswerDAO{
	
	public AnswerHibernateDAO(){
		super(Answer.class);
	}

}
