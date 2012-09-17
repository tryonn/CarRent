package br.ufpe.cin.amadeus.system.human_resources.user_request;

import java.util.List;

import org.hibernate.Session;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.hibernate.GenericHibernateDAO;

public class UserRequestHibernateDAO extends GenericHibernateDAO<UserRequest, Integer>
			implements UserRequestDAO{
	
	public UserRequestHibernateDAO(){
		super(UserRequest.class);
	}

	public List<UserRequest> searchUserRequestByUserID(int courseId, int personId){
		Session session = getSession();
		List<UserRequest> results = session.createSQLQuery(
				"SELECT {u.*} from request {u} where u.sqperson = '" + personId
						+ "' AND u.sqcourse = '" + courseId + "'").addEntity(
				"u", UserRequest.class).list();
		return results;
	}
	
	public List<UserRequest> searchTeachingRequest(int personId){
		Session session = getSession();
		List<UserRequest> results = session.createSQLQuery(
				"SELECT {u.*} from request {u} where u.sqperson = '" + personId
						+ "' AND u.sqcourse IS NULL ").addEntity("u",
				UserRequest.class).list();
		return results;
	}

	public int getNumberOfRequestsToAdmin() {
		Session session = getSession();
		List<UserRequest> results = session
				.createSQLQuery(
						"SELECT {u.*} from request {u} where u.instatus like 'ag' and in_teaching = true")
				.addEntity("u", UserRequest.class).list();
		return results.size();
	}

	public int getNumberOfRequestsToProfessor(User user, Role teacherRole) {
		Session session = getSession();
		List<UserRequest> results = session
				.createSQLQuery(
						"SELECT {r.*} from request {r} where r.instatus like 'ag' and r.in_teaching = false"
								+ " and r.sqcourse in (select sqcourse from person_role_course where sqperson = "
								+ user.getPerson().getId()
								+ " and sqrole = "
								+ teacherRole.getId() + " )").addEntity("r",
						UserRequest.class).list();
		return results.size();
	}
	
	public List<UserRequest> getPossibleTeachers(){
		Session session = getSession();
		List<UserRequest> results = session.createSQLQuery("SELECT {r.*} from request {r} where " +
				"r.in_teaching = true and instatus like 'ag'").addEntity("r",UserRequest.class).list();
		return results;
	}
	
	public List<UserRequest> getPossibleTeacherAssistants(User user, Role teacherRole){
		Session session = getSession();
		List<UserRequest> results = session.createSQLQuery("SELECT {r.*} from request {r} where r.instatus like 'ag' and " +
				"r.in_teaching = false and r.sqcourse in" +
						" (select sqcourse from person_role_course where sqrole =" +teacherRole.getId()+" and sqperson = " +
								user.getPerson().getId()+")").addEntity("r",UserRequest.class).list();
		return results;
	}
	
}
