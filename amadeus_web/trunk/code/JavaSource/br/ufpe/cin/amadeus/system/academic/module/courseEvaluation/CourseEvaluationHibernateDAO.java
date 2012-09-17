package br.ufpe.cin.amadeus.system.academic.module.courseEvaluation;

import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class CourseEvaluationHibernateDAO extends GenericHibernateDAO<CourseEvaluation, Integer> implements CourseEvaluationDAO {
	
	public CourseEvaluationHibernateDAO(){
		super(CourseEvaluation.class);
	}

}
