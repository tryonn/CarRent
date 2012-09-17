package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;
public class BlankFillingQuestionHibernateDAO extends GenericHibernateDAO<BlanksFillingQuestion, Integer> implements BlankFillingQuestionDAO{

	public BlankFillingQuestionHibernateDAO() {
		super(BlanksFillingQuestion.class);
		
	}

}
