package br.ufpe.cin.amadeus.system.facade;

/*
 * Provisório. Serviços aqui implementado estão a espera de uma 
 * decisão quanto ao contexto em que estão inseridos. (ControleAcesso 
 * RecursosHumanos.
 */

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.exception.RequestException;
import br.ufpe.cin.amadeus.system.human_resources.image.Image;
import br.ufpe.cin.amadeus.system.human_resources.image.ImageDAO;
import br.ufpe.cin.amadeus.system.human_resources.permission.Permission;
import br.ufpe.cin.amadeus.system.human_resources.permission.PermissionDAO;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonDAO;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonRoleCourse;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonRoleCourseDAO;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;
import br.ufpe.cin.amadeus.system.human_resources.profile.ProfileDAO;
import br.ufpe.cin.amadeus.system.human_resources.resume.Resume;
import br.ufpe.cin.amadeus.system.human_resources.resume.ResumeDAO;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequestDAO;
import br.ufpe.cin.amadeus.system.human_resources.user_request.request_evaluation.RequestEvaluation;
import br.ufpe.cin.amadeus.system.human_resources.user_request.request_evaluation.RequestEvaluationDAO;
import br.ufpe.cin.amadeus.system.util.dao.DAOFactory;
import br.ufpe.cin.amadeus.util.Constants;
import br.ufpe.cin.amadeus.util.MailSender;


public class HumanResourcesController {
	
	ProfileDAO profileRepository = (ProfileDAO) DAOFactory.DEFAULT.createDAO(Profile.class);
	PersonRoleCourseDAO prcRepository = (PersonRoleCourseDAO) DAOFactory.DEFAULT.createDAO(PersonRoleCourse.class);
	PermissionDAO permissionRepository = (PermissionDAO) DAOFactory.DEFAULT.createDAO(Permission.class);
	ResumeDAO resumeRepository = (ResumeDAO) DAOFactory.DEFAULT.createDAO(Resume.class);
	UserRequestDAO userRequestRepository = (UserRequestDAO) DAOFactory.DEFAULT.createDAO(UserRequest.class);
	ImageDAO imageRepository = (ImageDAO) DAOFactory.DEFAULT.createDAO(Image.class);
	PersonDAO personRepository = (PersonDAO) DAOFactory.DEFAULT.createDAO(Person.class);
	RequestEvaluationDAO requestEvaluationRepository = (RequestEvaluationDAO) DAOFactory.DEFAULT.createDAO(RequestEvaluation.class);
	
	public HumanResourcesController(){

	}
	
	/**
	 * Busca um perfil pelo id
	 * @param id - do perfil a ser buscado
	 * @return o perfil buscado
	 */
	public Profile searchProfile(int id){
		return profileRepository.findById(id,false);
	}
	
	/**
	 * cadastra o perfil
	 * @param p - o perfil a ser cadastrado
	 */
	public void insertProfile(Profile p){
		profileRepository.makePersistent(p);
	}
	
	/**
	 * busca um perfil pelo nome
	 * @param name - do perfil a ser buscado
	 * @return perfil buscado pelo nome
	 */
	public Profile searchProfileByName(String name){
		Profile p = new Profile();
		p.setName(name); // perfil que vai servir como critério pra pesquisa
		List<Profile> profiles = profileRepository.findByExample(p); //procura por exemplo
		if(profiles.isEmpty()){
			return null;
		} else {
			return profiles.get(0);
		}
	}
	
	public Profile searchProfileByConstant(int constant){
		Profile result = new Profile();
		List<Profile> profiles = profileRepository.searchProfileByConstant(constant);
		if(!profiles.isEmpty()){
			result = profiles.get(0);
		}
		return result;
	}
	
	public PersonRoleCourse insertPersonRoleCourse(PersonRoleCourse prc){
		return prcRepository.makePersistent(prc);
	}
	
	public boolean hasVacancy(Course course, Role role){
		List<PersonRoleCourse> results = prcRepository.findCurso(course,role);
		int registereds = results.size();
		if(results.isEmpty() || registereds < course.getStudents()){
			return true;
		} else {
			return false;
		}
	}
	
	public int countUsersByCourse(Course course, Role role){
		List<PersonRoleCourse> results = prcRepository.findCurso(course,role);
		return results.size();
	}
	
	public void insertPermission(Permission permission){
		permissionRepository.makePersistent(permission);
	}
	
	public Permission searchPermissionByName(String name){
		Permission permission = new Permission();
		permission.setName(name);
		List<Permission> permissions = permissionRepository.findByExample(permission);
		if(!permissions.isEmpty()){
			return permissions.get(0);
		} else {
			return null;
		}
	}
	
	public Permission searchPermissionByConstant(int constant){
		Permission permission = new Permission();
		permission.setPermissionConstant(constant);
		List<Permission> permissions = permissionRepository.findByExample(permission);
		if(!permissions.isEmpty()){
			return permissions.get(0);
		} else {
			return null;
		}
	}
	
	public boolean isRegistered(User user, Course course){
		List<PersonRoleCourse> results = prcRepository.findRegistered(user,course);
		//System.out.println(resultados.get(0).getCurso().getNome()+" :: "+resultados.get(0).getPessoa().getNome());
		if(results.isEmpty()){
			return false;
		} else {
			return true;
		}
	}
	
	public List<User> listUsersByCourse(Course course, Role role){
		return prcRepository.listUsersByCourse(course,role);
	}
	
	public Resume searchResumeByPerson(Person person){
		return resumeRepository.searchResumeByPerson(person);
	}
	
	public Resume insertResume(Resume resume){
		return resumeRepository.makePersistent(resume);
	}
	
	public Resume updateResume(Resume resume){
		return resumeRepository.makePersistentU(resume);
	}
	
 	public void remindPassword(User user) throws MessagingException {
 		String subject = "Amadeus: Lembrança de senha";
 		String message = "Sua senha é: " + user.getPassword();
 		MailSender.sendMail(user, subject, message);
	}
 	
 	public void confirmRegistry(User user) {
 		String subject = "Amadeus: Confirmação cadastro";
 		String message = "Seus dados:\n";
 		message += "Nome: " + user.getPerson().getName() + "\n";
 		message += "Login: " + user.getLogin() + "\n";
 		message += "Senha: " + user.getPassword() + "\n";
 		try {
 			MailSender.sendMail(user, subject, message);
 		} catch (MessagingException me) {}
 	}
 	
 	private UserRequest insertUserRequest(UserRequest userRequest){
 		return userRequestRepository.makePersistent(userRequest);
 	}
 	
 	private UserRequest updateUserRequest(UserRequest userRequest){
 		return userRequestRepository.makePersistentU(userRequest);
 	}
 	
 	private List<UserRequest> searchRequestByUserID(int courseId, int personId){
 		return userRequestRepository.searchUserRequestByUserID(courseId, personId);
 	}
 	
 	private List<UserRequest> searchTeachingRequest(int personID){
 		return userRequestRepository.searchTeachingRequest(personID);
 	}
 	
 	public boolean canRequestAssistance(Person person, Course course) {
 		List<UserRequest> results = searchRequestByUserID(course.getId(), person.getId());
 		for (UserRequest request : results) {
 			if (request.getStatus().equalsIgnoreCase("ag"))
 				return false;
 			if (request.getStatus().equalsIgnoreCase("ap"))
 				return false;
 		}
 		return true;
 	}
 	
 	public void requestAssistance(UserRequest userRequest) throws RequestException{
 		List<UserRequest> results = searchRequestByUserID(userRequest.getCourse().getId(), userRequest.getPerson().getId());
 		for (UserRequest request : results){
 			if (request.getStatus().equalsIgnoreCase("ag")){
 				throw new RequestException("errors.request.waiting");
 			}
 			if (request.getStatus().equalsIgnoreCase("ap")){
 				throw new RequestException("errors.request.approved");
 			}
 		}
 		userRequest.setStatus("ag");
		insertUserRequest(userRequest);
 	}
 	
 	public boolean canRequestTeaching(Person person) {
 		List<UserRequest> results = searchTeachingRequest(person.getId());
 		for (UserRequest request : results) {
 			if (request.getStatus().equalsIgnoreCase("ag"))
 				return false;
 			if (request.getStatus().equalsIgnoreCase("ap"))
 				return false;
 		}
 		return true;
 	}
 	
 	public void requestTeacherProfile(UserRequest userRequest) throws RequestException{
 		List<UserRequest> results = searchTeachingRequest(userRequest.getPerson().getId());
 		for (UserRequest request : results){
 			if (request.getStatus().equalsIgnoreCase("ag")){
 				throw new RequestException("errors.request.waiting");
 			}
 			if (request.getStatus().equalsIgnoreCase("ap")){
 				throw new RequestException("errors.request.approved");
 			}
 		}
 		userRequest.setStatus("ag");
 		insertUserRequest(userRequest);
 	}
 	
 	public PersonRoleCourse updatePersonRoleCourse(PersonRoleCourse prc){
 		System.out.println("SQL QUERY PRA UPDATE PRC: ");
 		return prcRepository.makePersistentU(prc);
 	}
 	
 	public Image searchImageByPerson(Person person){
 		return imageRepository.searchImageByPerson(person);
 	}
 	
 	public Image insertImage(Image image){
 		return imageRepository.makePersistent(image);
 	}
 	
 	public Image updateImage(Image image){
 		return imageRepository.makePersistentU(image);
 	}

 	
// 	public Image insertDefaultImage(Person person){
// 		Image amadeusImage = new Image();
// 		byte[] buffer = null;
// 		try {
// 		    BufferedImage image = ImageIO.read(this.getClass().getResource("../../util/005.jpg"));
// 		    
// 		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
// 		    ImageIO.write(image, "jpg", bos);
// 		 
// 		    buffer = bos.toByteArray();
// 		} catch (Exception e) {
// 		    e.getCause();
// 			e.printStackTrace();
// 		}
// 		amadeusImage.setPerson(person);
// 		amadeusImage.setPhoto(buffer);
//		return this.insertImage(amadeusImage);
// 	}

	public int getAdminRequests() {
		return userRequestRepository.getNumberOfRequestsToAdmin();
	}

	public int getProfessorRequests(User user, Role teacherRole) {
		return userRequestRepository.getNumberOfRequestsToProfessor(user, teacherRole);
	}

	public List<UserRequest> getPossibleTeachers() {
		return userRequestRepository.getPossibleTeachers();
	}

	public List<UserRequest> getPossibleTeacherAssistants(User user) {
		AcademicController ac = new AcademicController();
		Role teacherRole = ac.searchRoleByConstant(Constants.PROFESSOR);
		return userRequestRepository.getPossibleTeacherAssistants(user,teacherRole);
	}
	
	public void approveAssistantRequest(UserRequest request, Person evaluator){
		AcademicController ac = new AcademicController();
		Role role = ac.searchRoleByConstant(Constants.MONITOR);
		PersonRoleCourse prc = this.searchPersonRoleCourse(request.getPerson(),request.getCourse());
		if(prc != null){
			this.deletePersonRoleCourse(prc);
		}
		PersonRoleCourse prcc = new PersonRoleCourse();
		prcc.setCourse(request.getCourse());
		prcc.setPerson(request.getPerson());
		prcc.setRole(role);
		prcc.setDate(new Date());
		this.updatePersonRoleCourse(prcc);
	}
	
	public PersonRoleCourse searchPersonRoleCourse(Person person, Course course){
		return prcRepository.searchPersonRoleCourse(person, course);
	}
	
	public void deletePersonRoleCourse(PersonRoleCourse prc){
		prcRepository.makeTransient(prc);
	}
	
	public void approveRequest(UserRequest request, Person evaluator) {
		RequestEvaluation requestEvaluation = new RequestEvaluation();
		requestEvaluation.setPerson(evaluator);
		requestEvaluation.setRequest(request);
		requestEvaluation.setEvaluationDate(new Date());
		request.setStatus(Constants.APPROVED);
		this.insertRequestEvaluation(requestEvaluation);
		this.updateUserRequest(request);
		if(!request.isTeachingRequest()){
			this.approveAssistantRequest(request,evaluator);
		}
	}
	
	public RequestEvaluation insertRequestEvaluation(RequestEvaluation requestEvaluation){
		return requestEvaluationRepository.makePersistent(requestEvaluation);
	}
	
	public RequestEvaluation updateRequestEvaluation(RequestEvaluation requestEvaluation){
		return requestEvaluationRepository.makePersistentU(requestEvaluation);
	}
	
	public UserRequest searchUserRequest(int requestId){
		return userRequestRepository.findById(requestId, false);
	}

	public void disapproveRequest(UserRequest userRequest, Person evaluator) {
		RequestEvaluation requestEvaluation = new RequestEvaluation();
		requestEvaluation.setPerson(evaluator);
		requestEvaluation.setRequest(userRequest);
		requestEvaluation.setEvaluationDate(new Date());
		userRequest.setStatus(Constants.DISAPPROVED);
		this.insertRequestEvaluation(requestEvaluation);
		this.updateUserRequest(userRequest);
	}
 	
}
