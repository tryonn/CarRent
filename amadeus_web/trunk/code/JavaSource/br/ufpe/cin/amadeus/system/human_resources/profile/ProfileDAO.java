package br.ufpe.cin.amadeus.system.human_resources.profile;

import java.util.List;

import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface ProfileDAO extends GenericDAO <Profile,Integer> {
	
	public List<Profile>searchProfileByConstant(int constant);
	
}
