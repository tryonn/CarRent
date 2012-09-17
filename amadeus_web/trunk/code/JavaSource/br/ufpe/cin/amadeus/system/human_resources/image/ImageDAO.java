package br.ufpe.cin.amadeus.system.human_resources.image;

import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface ImageDAO extends GenericDAO<Image, Integer> {
	
	public Image searchImageByPerson(Person person);

}
