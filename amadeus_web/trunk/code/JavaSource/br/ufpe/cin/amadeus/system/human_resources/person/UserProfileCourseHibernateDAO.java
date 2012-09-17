package br.ufpe.cin.amadeus.system.human_resources.person;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;


public class UserProfileCourseHibernateDAO extends GenericHibernateDAO <UserProfileCourse,Integer> 
											implements UserProfileCourseDAO{
	
	public UserProfileCourseHibernateDAO(){
		super(UserProfileCourse.class);
	}

}
