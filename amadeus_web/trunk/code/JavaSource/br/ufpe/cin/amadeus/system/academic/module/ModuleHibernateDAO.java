package br.ufpe.cin.amadeus.system.academic.module;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class ModuleHibernateDAO extends GenericHibernateDAO<Module, Integer> implements ModuleDAO {
	
	public ModuleHibernateDAO(){
		super(Module.class);
	}

}
