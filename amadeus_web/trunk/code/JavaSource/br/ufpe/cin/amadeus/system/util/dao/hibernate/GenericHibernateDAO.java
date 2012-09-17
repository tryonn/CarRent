package br.ufpe.cin.amadeus.system.util.dao.hibernate;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import br.ufpe.cin.amadeus.hibernate.util.HibernateUtil;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;


/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p>
 * You have to inject the <tt>Class</tt> object of the persistent class and a current
 * Hibernate <tt>Session</tt> to construct a DAO.
 *
 * @see HibernateDAOFactory
 *
 * @author christian.bauer@jboss.com
 */
public class GenericHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private Class<T> persistentClass;
    private static Session session;

    public GenericHibernateDAO(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
		if (session == null)
			session = HibernateUtil.getSessionFactory().openSession();
	}
    
    public Session getSession(){
    	return session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock){
    	Session session = getSession();
    	Transaction tx = session.beginTransaction();
    	T entity = null;
        
        if(session.get(getPersistentClass(),id)!=null){
	        if (lock){
	        	entity = (T) session.load(getPersistentClass(), id, LockMode.UPGRADE);
	        } else {
	            entity = (T) session.load(getPersistentClass(), id);
	        }
        }
        tx.commit();
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance) {
    	List<T> results = null;
        results = findByCriteria(Example.create(exampleInstance).ignoreCase().enableLike());
        return results;
    }
    
    public List<T> findByExampleS(T exampleInstance){
    	List<T> results = null;
        results = findByCriteria(Example.create(exampleInstance).ignoreCase().enableLike().excludeZeroes());
        return results;
    }

    @SuppressWarnings("unchecked")
    public T makePersistent(T entity) {
    	Session session = getSession();
    	Transaction t = session.beginTransaction(); 
    	try {
			session.save(entity);
			t.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			t.rollback();
			BatchUpdateException b = (BatchUpdateException) e.getCause();
			b.getNextException().printStackTrace();
		}
    	return entity;
    }

    public void makeTransient(T entity) {
    	Session session = getSession();
    	Transaction t = session.beginTransaction(); 
    	session.delete(entity);
    	t.commit();
    }
    
    public T makePersistentU(T entity){
    	Session session = getSession();
    	Transaction t = session.beginTransaction();
    	session.saveOrUpdate(entity);
    	t.commit();
    	return entity;
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
    	Session session = getSession();
    	List<T> results = null;
        Criteria crit = session.createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        results = crit.list();
        return results;
   }
    
}

