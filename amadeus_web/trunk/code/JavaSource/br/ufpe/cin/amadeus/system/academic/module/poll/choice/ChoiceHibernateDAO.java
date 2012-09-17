package br.ufpe.cin.amadeus.system.academic.module.poll.choice;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class ChoiceHibernateDAO extends GenericHibernateDAO<Choice,Integer> implements ChoiceDAO{
	
	public ChoiceHibernateDAO(){
		super(Choice.class);
	}

}
