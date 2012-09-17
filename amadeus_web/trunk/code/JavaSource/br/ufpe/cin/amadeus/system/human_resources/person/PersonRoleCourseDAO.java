package br.ufpe.cin.amadeus.system.human_resources.person;

import java.util.List;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;


public interface PersonRoleCourseDAO extends GenericDAO <PersonRoleCourse, Integer>{
	
	public List<PersonRoleCourse> findRegistered(User usr, Course course);
	public List<PersonRoleCourse> findCurso(Course course, Role role);
	public List<User> listUsersByCourse(Course course, Role role);
	public PersonRoleCourse searchPersonRoleCourse(Person person, Course course);

}
