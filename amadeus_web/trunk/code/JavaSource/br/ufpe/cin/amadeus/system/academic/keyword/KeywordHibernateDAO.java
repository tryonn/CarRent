package br.ufpe.cin.amadeus.system.academic.keyword;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class KeywordHibernateDAO extends GenericHibernateDAO<Keyword, Integer>
		implements KeywordDAO {
	
	public KeywordHibernateDAO(){
		super(Keyword.class);
	}
	
	public List<Keyword> getMostPopularKeywords(){
		Session session = getSession();
		List<Keyword> results = session.createSQLQuery(
				"SELECT {k.*} FROM KEYWORD {k} ORDER BY k.qt_popularity desc LIMIT 50")
				.addEntity("k", Keyword.class).list();
		return results;
	}

}
