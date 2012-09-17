package br.ufpe.cin.amadeus.system.util.dao;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.HibernateDAOFactory;

public abstract class DAOFactory <T extends GenericDAO> {

    public static final DAOFactory HIBERNATE = new HibernateDAOFactory();

    public static final DAOFactory DEFAULT = HIBERNATE;

    // Add your DAO interfaces here
    public abstract T createDAO(Class classe);

}