package br.ufpe.cin.amadeus.system.academic.module.poll;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class PollHibernateDAO extends GenericHibernateDAO<Poll,Integer> implements PollDAO {

	public PollHibernateDAO(){
		super(Poll.class);
	}
	
}
