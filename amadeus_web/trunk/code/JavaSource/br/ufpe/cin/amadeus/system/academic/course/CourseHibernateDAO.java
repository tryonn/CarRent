package br.ufpe.cin.amadeus.system.academic.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;
import br.ufpe.cin.amadeus.util.DateConstructor;

public class CourseHibernateDAO
	extends GenericHibernateDAO <Course, Integer> 
	implements CourseDAO {
	
	public CourseHibernateDAO(){
		super(Course.class);
	}
	
	public List<Course> findByName(String name){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from COURSE {c} where c.nmcourse ILIKE '%"
				+name+"%' ").addEntity("c", Course.class).list();
		return results;
	}
	
	public List<Course> findByContent(String name){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from COURSE {c} where c.dscontents ILIKE '%"
				+name+"%' ").addEntity("c", Course.class).list();
		return results;
	}
	
	public List<Course> findByObjective(String objective){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from COURSE {c} where c.dsobjectives ILIKE '%"
				+objective+"%' ").addEntity("c", Course.class).list();
		return results;
	}
	
	public List<Course>[] findByKeyword(String keyword){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from COURSE {c} where c.sqcourse in " +
				"(SELECT sqcourse from course_keyword where sqkeyword in (SELECT sqkeyword FROM keyword where nmkeyword " +
				"ILIKE '" +keyword+"' ))").addEntity("c",Course.class).list();
		List<Course>[] result = new ArrayList[3];
		for (int i = 0; i < result.length; i++)
			result[i] = new ArrayList<Course>();
		Date today = new Date();
		for(Course course : results){
			if(today.compareTo(course.getFinalRegistrationDate())<=0 ){ //&& today.compareTo(course.getInitialRegistrationDate())>=0
				if(result[0].contains(course) == false){
					result[0].add(course);
				}
			}
			
			if((today.compareTo(course.getInitialCourseDate()) >=0 && today.compareTo(course.getFinalCourseDate())<=0))
				if(result[1].contains(course) == false){
					result[1].add(course);
				}
			
			if(today.compareTo(course.getFinalCourseDate())>0)
				if(result[2].contains(course) == false){
					result[2].add(course);
				}
		}
		return result;
	}
	
	public List<Course> findByKeyword2(String keyword){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from COURSE {c} where c.sqcourse in " +
				"(SELECT sqcourse from course_keyword where sqkeyword in (SELECT sqkeyword FROM keyword where nmkeyword " +
				"ILIKE '%" +keyword+"%' ))").addEntity("c",Course.class).list();
		return results;
	}

	/**
	 * Procura todos os cursos de um dado usuário
	 */
	public List<Course> searchCoursesByUser(User user){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from COURSE {c} where c.sqcourse in " +
				"(SELECT sqcourse from PERSON_ROLE_COURSE where sqperson = "
				+user.getPerson().getId() +" )").addEntity("c",Course.class).list();
		return results;
	}
	
	public List<Course>[] searchCourses(String term, int teacherRole){
		List<Course> results = findByName(term);
		
		add(results, findByContent(term));
		add(results, findByObjective(term));
		add(results, findByKeyword2(term));
		add(results, listCoursesByUsers(term, teacherRole));

		List<Course>[] result = new ArrayList[3];
		for (int i = 0; i < result.length; i++)
			result[i] = new ArrayList<Course>();
		Date today = DateConstructor.today();
		for (Course course : results) {
			if (today.compareTo(course.getFinalRegistrationDate()) <= 0) { // && today.compareTo(course.getInitialRegistrationDate())>=0
				if (!result[0].contains(course)) {
					result[0].add(course);
				}
			}

			if (today.compareTo(course.getInitialCourseDate()) >= 0 && today.compareTo(course.getFinalCourseDate()) <= 0)
				if (!result[1].contains(course)) {
					result[1].add(course);
				}

			if (today.compareTo(course.getFinalCourseDate()) > 0)
				if (!result[2].contains(course)) {
					result[2].add(course);
				}
		}
		return result;
	}
	
	private void add(List<Course> target, List<Course> toBeAdded) {
		for (Course c : toBeAdded)
			if (!target.contains(c))	target.add(c);
	}

	/**
	 * Lista todos os cursos de um usuário relacionado com o dado papel
	 * @param name nome do usuário
	 * @param role papel do usuário
	 * @return lista de cursos
	 */
	public List<Course> listCoursesByUsers(String name, int role){
		Session session = getSession();
		List<Course> results = session.createSQLQuery("SELECT {c.*} from Course {c} where c.sqcourse in " +
				"(SELECT sqcourse from person_role_course where sqrole = "+ role +" AND sqperson in " +
						"(SELECT sqperson from person where nmperson ILIKE '%"+name+"%' ))").addEntity("c",Course.class).list();
		return results;
	}
}
