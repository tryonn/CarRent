package br.ufpe.cin.amadeus.system.academic.module.forum;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class MessageHibernateDAO extends GenericHibernateDAO<Message, Integer>
	implements MessageDAO{
	
	public MessageHibernateDAO(){
		super(Message.class);
	}

}
