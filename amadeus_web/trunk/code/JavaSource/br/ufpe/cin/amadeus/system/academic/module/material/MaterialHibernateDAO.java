package br.ufpe.cin.amadeus.system.academic.module.material;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class MaterialHibernateDAO extends GenericHibernateDAO<Material,Integer> implements MaterialDAO{
	
	public MaterialHibernateDAO(){
		super(Material.class);
	}

}
