package br.ufpe.cin.amadeus.system.human_resources.resume;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface ResumeDAO extends GenericDAO<Resume, Integer > {
	
	public Resume searchResumeByPerson(Person person);

}
