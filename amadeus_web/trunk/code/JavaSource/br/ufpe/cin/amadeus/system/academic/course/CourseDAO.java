package br.ufpe.cin.amadeus.system.academic.course;

import java.util.List;

import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;


public interface CourseDAO extends GenericDAO<Course, Integer> {
	
	public List<Course> findByName(String name);
	public List<Course>[] findByKeyword (String keyword);
	public List<Course> searchCoursesByUser(User user);
	public List<Course>[] searchCourses(String term, int teacherRole);
	public List<Course> findByKeyword2(String keyword);

}
