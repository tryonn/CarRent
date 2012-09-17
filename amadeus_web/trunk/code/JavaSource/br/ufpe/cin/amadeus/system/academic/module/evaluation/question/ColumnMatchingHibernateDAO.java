package br.ufpe.cin.amadeus.system.academic.module.evaluation.question;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class ColumnMatchingHibernateDAO extends GenericHibernateDAO<ColumnMatching, Integer> implements ColumnMatchingDAO{

	public ColumnMatchingHibernateDAO() {
		super(ColumnMatching.class);
		// TODO Auto-generated constructor stub
	}

}
