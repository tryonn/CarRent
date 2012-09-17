package br.ufpe.cin.amadeus.system.human_resources.permission;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;


public class PermissionHibernateDAO extends GenericHibernateDAO<Permission,Integer> implements PermissionDAO {
	
	public PermissionHibernateDAO(){
		super(Permission.class);
	}

}
