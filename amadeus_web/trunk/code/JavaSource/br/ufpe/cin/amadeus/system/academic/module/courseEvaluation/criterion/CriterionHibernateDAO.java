package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class CriterionHibernateDAO extends GenericHibernateDAO<Criterion, Integer> implements CriterionDAO {
	
	public CriterionHibernateDAO(){
		super(Criterion.class);
	}

}
