package br.ufpe.cin.amadeus.system.academic.module.forum;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class ForumHibernateDAO extends GenericHibernateDAO<Forum, Integer>
	implements ForumDAO{
	
	public ForumHibernateDAO(){
		super(Forum.class);
	}

}
