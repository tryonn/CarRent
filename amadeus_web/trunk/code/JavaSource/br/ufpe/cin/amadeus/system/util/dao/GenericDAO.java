package br.ufpe.cin.amadeus.system.util.dao;

import java.io.Serializable;
import java.util.List;



/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 *
 * @author christian.bauer@jboss.com
 */
public interface GenericDAO<T, ID extends Serializable> {

    T findById(ID id, boolean lock);

    List<T> findAll();

    List<T> findByExample(T exampleInstance);
    
    List<T> findByExampleS(T exampleInstance);

    T makePersistent(T entity);
    
    T makePersistentU(T entity);

    void makeTransient(T entity);
    
}
