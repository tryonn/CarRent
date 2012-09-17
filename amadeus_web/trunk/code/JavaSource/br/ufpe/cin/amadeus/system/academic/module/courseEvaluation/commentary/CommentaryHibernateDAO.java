package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.commentary;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class CommentaryHibernateDAO extends GenericHibernateDAO<Commentary, Integer> implements CommentaryDAO {
	
	public CommentaryHibernateDAO(){
		super(Commentary.class);
	}

}
