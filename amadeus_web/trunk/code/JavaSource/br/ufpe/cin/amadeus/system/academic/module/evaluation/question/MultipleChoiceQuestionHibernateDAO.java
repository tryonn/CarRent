package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class MultipleChoiceQuestionHibernateDAO extends GenericHibernateDAO<MultipleChoiceQuestion, Integer> implements MultipleChoiceQuestionDAO{

	public MultipleChoiceQuestionHibernateDAO() {
		super(MultipleChoiceQuestion.class);
		
	}

}
