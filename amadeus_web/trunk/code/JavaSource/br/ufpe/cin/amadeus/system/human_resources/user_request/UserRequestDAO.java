package br.ufpe.cin.amadeus.system.human_resources.user_request;

import java.util.List;

import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.util.dao.GenericDAO;

public interface UserRequestDAO extends GenericDAO<UserRequest, Integer> {
	
	public List<UserRequest> searchUserRequestByUserID(int courseId, int personID);
	public List<UserRequest> searchTeachingRequest(int personId);
	public int getNumberOfRequestsToAdmin();
	public int getNumberOfRequestsToProfessor(User user, Role teacherRole);
	public List<UserRequest> getPossibleTeachers();
	public List<UserRequest> getPossibleTeacherAssistants(User user, Role teacherRole);
}
