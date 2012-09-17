package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.personEvaluateCourse;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class PersonEvaluateCourseHibernateDAO extends GenericHibernateDAO<PersonEvaluateCourse, Integer> implements PersonEvaluateCourseDAO {
	
	public PersonEvaluateCourseHibernateDAO(){
		super(PersonEvaluateCourse.class);
	}

}
