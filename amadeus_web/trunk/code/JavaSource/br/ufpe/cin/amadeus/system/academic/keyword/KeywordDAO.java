package br.ufpe.cin.amadeus.system.academic.keyword;

import java.util.List;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface KeywordDAO extends GenericDAO<Keyword, Integer> {
	
	public List<Keyword> getMostPopularKeywords();

}
