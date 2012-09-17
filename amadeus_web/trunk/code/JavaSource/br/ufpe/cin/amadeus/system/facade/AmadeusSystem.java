package br.ufpe.cin.amadeus.system.facade;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.keyword.Keyword;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluation;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionEvaluationResults;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.evaluationAnswer.EvaluationAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.academic.module.homework.delivery.Delivery;
import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.academic.module.poll.Poll;
import br.ufpe.cin.amadeus.system.academic.module.poll.answer.Answer;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.Choice;
import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.exception.ImageSizeException;
import br.ufpe.cin.amadeus.system.exception.RequestException;
import br.ufpe.cin.amadeus.system.exception.SendMailException;
import br.ufpe.cin.amadeus.system.exception.UserRegistrationException;
import br.ufpe.cin.amadeus.system.human_resources.image.Image;
import br.ufpe.cin.amadeus.system.human_resources.permission.Permission;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.person.PersonRoleCourse;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;
import br.ufpe.cin.amadeus.system.human_resources.resume.Resume;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest;
import br.ufpe.cin.amadeus.util.Constants;
import br.ufpe.cin.amadeus.util.DateConstructor;
import br.ufpe.cin.amadeus.util.Thumbnail;
/**
 * Fachada do sistema
 * @author amadeus
 *
 */
public class AmadeusSystem {

	private static AmadeusSystem system;
	
	private AccessControlController accessControlController;
	private AcademicController academicController;
	private HumanResourcesController humanResourcesController;

	public static AmadeusSystem getInstance() {
		if (system == null) {
			system = new AmadeusSystem();
		}
		return system;
	}


	private AmadeusSystem() {
		accessControlController = new AccessControlController();
		academicController = new AcademicController();
    	humanResourcesController = new HumanResourcesController();
	}

    /**
     * Método que checa se o dado usuário está matriculado no dado curso
     * @param user - usuário a ser checado
     * @param course - curso a ser checado
     * @return true se usuário esta matriculado, else se não.
     */
	public boolean isRegistered (User user, Course course) {
    	return humanResourcesController.isRegistered(user,course);
    }
    
	/**
	 * Método que verifica se o usuário pode fazer a matricula
	 * @param user Usuario a ser matriculado
	 * @param course Curso no qual o usuario sera matriculado
	 * @return true se é possivel. false se não.
	 */
	public boolean canRegisterUser(User user, Course course) {
		Date today = DateConstructor.today();
		if (today.compareTo(course.getInitialRegistrationDate()) < 0 ||
				today.compareTo(course.getFinalRegistrationDate()) > 0)
			return false;
		
    	if(humanResourcesController.isRegistered(user,course))
    		return false;
    		
		Role studentRole = academicController.searchRoleByConstant(Constants.STUDENT);
		if(!humanResourcesController.hasVacancy(course,studentRole))
			return false;
			
		Set<Permission> permissions = user.getProfile().getPermissions();
		if (!permissions.contains(humanResourcesController.searchPermissionByConstant(Constants.MATRICULA)))
			return false;

    	return true;
	}
	
    /**
     * Método que matricula o usuário em um curso
     * @param user a ser matriculado
     * @param course no qual o usuario ira se matricular
     * @throws UserRegistrationException 
     */
    public void registerUser(User user, Course course) throws UserRegistrationException  {
		Date today = DateConstructor.today();
		if (today.compareTo(course.getInitialRegistrationDate()) < 0 ||
				today.compareTo(course.getFinalRegistrationDate()) > 0)
			throw new UserRegistrationException("errors.registration.date");
		
    	if(humanResourcesController.isRegistered(user,course))
    		throw new UserRegistrationException("errors.registration.alreadyRegistered");
    		
		Role studentRole = academicController.searchRoleByConstant(Constants.STUDENT);
		if(!humanResourcesController.hasVacancy(course,studentRole))
			throw new UserRegistrationException("errors.registration.vacancy");
			
		Set<Permission> permissions = user.getProfile().getPermissions();
		if (!permissions.contains(humanResourcesController.searchPermissionByConstant(Constants.MATRICULA)))
			throw new UserRegistrationException("errors.registration.permission");

		PersonRoleCourse ppc = new PersonRoleCourse();
		ppc.setCourse(course);
		ppc.setPerson(user.getPerson());
		ppc.setRole(studentRole);//1
		ppc.setDate(new Date());
		humanResourcesController.insertPersonRoleCourse(ppc);
    }
    
    
    /**
     * Método que conta todos os alunos de um dado curso
     * @param course - a ter seus alunos contados
     * @return - o número de alunos.
     */
    public int countStudentsByCourse(Course course){
    	Role studentRole = academicController.searchRoleByConstant(Constants.STUDENT);
    	return humanResourcesController.countUsersByCourse(course,studentRole);
    }
    
    /**
     * Método que lista todos os alunos de um curso
     * @param course - curso do qual os alunos devem ser relacionados
     * @return os alunos do curso, vazio caso não haja nenhum aluno
     */
    public List<User> listStudentsByCourse(Course course) {
    	Role studentRole = academicController.searchRoleByConstant(Constants.STUDENT);
    	return humanResourcesController.listUsersByCourse(course, studentRole);
    }
    
    /**
     * Método que procura um curso por um dado critério 
     * @param rule - String critério
     * @return Uma lista de curso que contem o dado critério
     */
    public List<Course>[] searchCourseByRule(String rule) {
    	List<Course>[] results = academicController.searchCourseByTerm(rule);
    	return results;
    }
    
    /**
     * Método que lista os professores de um curso
     * @param course - curso do qual os professores devem ser relacionados
     * @return lista de professores encontrados
     */
    public List<User> listTeachersByCourse(Course course) {
    	Role teacherRole = academicController.searchRoleByConstant(Constants.PROFESSOR);
    	return humanResourcesController.listUsersByCourse(course, teacherRole);
    }
    
   /**
    * Método que lista os monitores de um dado curso
    * @param course - curso do qual serão listados os monitores
    * @return a lista dos monitores encontrados
    */
    public List<User> listAssistantsByCourse(Course course){
    	Role assistantRole = academicController.searchRoleByConstant(Constants.MONITOR);
    	return humanResourcesController.listUsersByCourse(course, assistantRole);
    }
    
    
	/**
	 * Procura um usuário
	 * @param id - do usuário a ser procurado
	 * @return usuário achado ou null se não há usuário com o dado id
	 */
    public User searchUser(int id){
       return accessControlController.searchUser(id);
    }
    
    /**
     * Method that looks for a user by the given login
     * @param login - the user's login
     * @return - the found user or null if there is no user with the given login
     */
    public User searchUserByLogin(String login){
    	return accessControlController.searchUserByLogin(login);
    }
    
    /**
     * Method that looks for a user by the given email address
     * @param email - the user's email
     * @return - the found user or null if there is no user with the giver email
     */
    public User searchUserByEmail(String email){
    	return accessControlController.searchUserByEmail(email);
    }
    
    /**
     * Method that inserts a new user
     * @param user - to be inserted
     * @return the inserted user
     */
    public User insertUser(User user) {
    	return accessControlController.insertUser(user);
    }
    
    /**
     * Method that removes a user from the user repository
     * @param user - user to be removed
     */
    public void removeUser(User user) {
    	accessControlController.removeUser(user);
    }
    
    /**
     * Method that updates a user
     * @param user - to be updated
     * @return the new user
     */
    public User updateUser(User user) {
    	return accessControlController.updateUser(user);
    }
    
    /**
     * Method that list all users in the user repository
     * @return A user list
     */
    public List<User> listUsers(){
    	return accessControlController.listUsers();
    }
    
    /**
     * Method that searches a course by its id
     * @param id - the course's id
     * @return the found course or null if there is no course with the given id
     */
    public Course searchCourse(int id){
    	return academicController.searchCourse(id);
    }
    
    /**
     * Method that inserts a course in the course repository
     * @param course - to be inserted
     */
    public void insertCourse(Course course){
    	Role professorRole = academicController.searchRoleByConstant(Constants.PROFESSOR);
    	User user = searchUser(course.getIdAuthor());
    	PersonRoleCourse ppc = new PersonRoleCourse();
		ppc.setCourse(course);
		ppc.setPerson(user.getPerson());
		ppc.setRole(professorRole);//1
		ppc.setDate(new Date());
    	academicController.insertCourse(course);
		humanResourcesController.insertPersonRoleCourse(ppc);
    }
    
    /**
     * Method that updates a course 
     * @param course - to be updated
     */
    public void updateCourse(Course course){
    	academicController.updateCourse(course);
    }
    
    /**
     * Method that lists all active courses
     * @return a list with all active courses
     */
    public List<Course> listCourses(){
    	return academicController.listCourses();
    }
    
    /**
     * Method that searches a profile with the given id
     * @param id - the profile's id
     * @return the found profile or null if there is no profile with the given id
     */
    public Profile searchProfile(int id){
    	return humanResourcesController.searchProfile(id);
    }
    
    /**
     * Method that inserts a profile in the profile repository 
     * @param - profile to be inserted
     */
    public void insertProfile(Profile profile){
    	humanResourcesController.insertProfile(profile);
    }
    
    /**
     * Private method that assists registerUser() 
     * inserts a PersonRoleCourse in the PersonRoleCourse Repository
     * @param prc - argument to be inserted
     */
    private PersonRoleCourse insertPersonRoleCourse(PersonRoleCourse prc){
    	return humanResourcesController.insertPersonRoleCourse(prc);
    }
    
    /**
     * Method that inserts a role in the role repository
     * @param role to be inserted
     */
    public void insertRole(Role role){
    	academicController.insertRole(role);
    }
    
    /**
     * Method that looks for a role with the given id
     * @param id - the role's id
     * @return the role found or null if there is no role with the given id
     */public Role searchRole(int id){
    	return academicController.searchRole(id);
    }
    
    /**
     * Method that inserts a permission in the permission repository
     * @param permission - to be inserted
     */
     public void insertPermission(Permission permission){
    	humanResourcesController.insertPermission(permission);
    }
    
    /**
     * Method that searches a role by its name
     * @param name - the name of the role to be found
     * @return the found role ou null if there is no role with this name
     */
     public Role searchRoleByName(String name){
    	return academicController.searchRoleByName(name);
    }
     
     /**
      * Method that searches a resume by its owner
      * @param person the owner for the resume
      * @return the found resume or null if there is no resume attached to the giver person
      */
     public Resume searchResume(Person person){
    	 return humanResourcesController.searchResumeByPerson(person);
     }
     
     /**
      * Method that inserts a resume in the resume repository
      * @param resume - to be inserted
      * @return the inserted resume
      */
     public Resume insertResume(Resume resume){
    	 return humanResourcesController.insertResume(resume);
     }
     
     /**
      * Method that updates a resume in the repository
      * @param resume to be updated
      * @return the updated resume
      */
     public Resume updateResume(Resume resume){
    	 return humanResourcesController.updateResume(resume);
     }

     /**
      * Method that sends an email to a user to remind him the password
      * @param email the user's address
      * @throws SendMailException
      */
     public void remindPassword(String email) throws SendMailException {
    	 User user = searchUserByEmail(email);
    	 if(user==null){
    		 throw new SendMailException("errors.user.notRegistered");
    	 }
    	 try{
    		 humanResourcesController.remindPassword(user);
    	 } catch (MessagingException m){
    		 throw new SendMailException("errors.mail.send"); 
    	 }
     }

     public void confirmRegistry(User user) {
    	 humanResourcesController.confirmRegistry(user);
     }

     /**
      * Method that inserts a keyword in the keyword repository
      * @param keyword - to be inserted
      * @return the inserted keyword
      */
     public Keyword insertKeyword(Keyword keyword){
    	 Keyword k = academicController.searchKeywordByName(keyword.getName());
    	 if(k != null){
    		 k.increasePopularity();
    		 academicController.updateKeyword(k);
    		 return k;
    	 }
    	 keyword.increasePopularity();
    	 return academicController.insertKeyword(keyword);
     }
     
     /**
      * Method that searches a keyword by its id
      * @param id of the keyword to be found
      * @return the found keyword or null if there is no keyword with this id in the repository
      */
     public Keyword searchKeywordById(int id){
    	 return academicController.searchKeywordById(id);
     }
     
     /**
      * Method that searches a keyword by its name
      * @param name of the keyword to be found
      * @return the found keyword or null if there is no keyword with this id in the repository
      */
     public Keyword searchKeywordByName(String name){
    	 return academicController.searchKeywordByName(name);
     }
     
     public boolean canRequestAssistance(Person person, Course course) {
    	 return humanResourcesController.canRequestAssistance(person, course);
     }
     
     /**
      * Method that requests teaching assistance
      * @param userRequest 
      * @throws RequestException
      */
     public void requestAssistance(UserRequest userRequest) throws RequestException{
    	 humanResourcesController.requestAssistance(userRequest);
     }
     
     public boolean canRequestTeaching(Person person) {
    	 return humanResourcesController.canRequestTeaching(person);
     }
     
     /**
      * Method that requests a teacher profile
      * @param userRequest
      * @throws RequestException
      */
     public void requestTeacherProfile(UserRequest userRequest) throws RequestException{
    	 humanResourcesController.requestTeacherProfile(userRequest);
     }
     
 	/**
 	 * Method that searches courses attached to the given keyword
 	 * @param keyword 
 	 * @return the found courses
 	 */
     public List<Course>[] searchCourseByKeyword(String keyword){
 		return academicController.searchCourseByKeyword(keyword);
 	}
     
 	/**
 	 * Metodo que retorna o papel de um dado usuario num curso.
 	 * @param user - usuario cujo papel quer se saber
 	 * @param course - curso no qual o osuario tem o papel a ser procurado
 	 * @return - papel do dado usuario no dado curso
 	 */
     public int searchRoleByUser(User user, Course course){
 		return academicController.searchRoleByUser(user,course);
 	}
 	
 	public List<Course> searchCoursesByUser(User user){
 		return academicController.searchCoursesByUser(user);
 	}
 	
 	public Image searchImageByPerson(Person person){
 		return humanResourcesController.searchImageByPerson(person); 
 	}
 	
 	public Image insertImage(Image image) throws Exception {
 		Image retorno = null;
 		Image temp = searchImageByPerson(image.getPerson());
 		Thumbnail min = new Thumbnail();
 		byte[] photo = image.getPhoto();
 		if(photo.length > 500000)
 			throw new ImageSizeException("Formato inválido");
 		image.setPhoto(min.resize(photo));
 		if(temp == null){
 			retorno = humanResourcesController.insertImage(image);
 		} else {
 			temp.setPhoto(image.getPhoto());
 			retorno = humanResourcesController.updateImage(temp);
 		}
 		return retorno;
 	}
 	
 	public Module insertModule(Module module){
 		return academicController.insertModule(module);
 	}
 	
 	public Module updateModule(Module module) {
 		return academicController.updateModule(module);
	}
 	
 	public Module searchModule(int id){
 		return academicController.searchModule(id);
 	}
 	
 	public List<Module> searchModule(String name){
 		return academicController.searchModule(name);
 	}
 	
 	public Module getCourseModuleByOrder(Course course, int order){
 		return academicController.getCourseModuleByOrder(course, order);
 	}
 	
 	public void removeModule(Module module){
 		academicController.removeModule(module);
 	}
 	
 	public List<Keyword> getMostPopularKeywords(){
 		return academicController.getMostPopularKeywords();
 	}
 	
 	public Homework insertHomework(Homework homework){
 		return academicController.insertHomework(homework);
 	}
 	
 	public Homework searchHomework(int id){
 		return academicController.searchHomework(id);
 	}
 	
 	public Homework updateHomework(Homework homework){
 		return academicController.updateHomework(homework);
 	}
 	
 	public Delivery insertDelivery(Delivery delivery){
 		return academicController.insertDelivery(delivery);
 	}
 	
 	public Delivery searchDelivery(int id){
 		return academicController.searchDelivery(id);
 	}
 	
 	public Delivery updateDelivery(Delivery delivery){
 		return academicController.updateDelivery(delivery);
 	}
 	
 	public Poll answerPoll(Poll poll, Choice choice, Person person){
 		return academicController.answerPoll(poll,choice,person);
 	}
 	
 	public boolean hasVoted(Poll poll, Person person){
 		return academicController.hasVoted(person,poll);
 	}
 	
 	public Material searchMaterial(int id){
 		return  academicController.searchMaterial(id);
 	}
 	
 	public int getAmountPendingTasks(User user){
 		return academicController.getAmountPendingTasks(user);
 	}
 	
 	public List<UserRequest> getPossibleTeachers(){
 		return humanResourcesController.getPossibleTeachers();
 	}
 	
 	public List<UserRequest> getPossibleTeacherAssistants(User user){
 		return humanResourcesController.getPossibleTeacherAssistants(user);
 	}
 	
 	public void disapproveRequest(UserRequest userRequest, Person evaluator){
 		humanResourcesController.disapproveRequest(userRequest, evaluator);
 	}
 	
 	public void approveRequest(UserRequest ur, Person evaluator) {
 		if(ur.isTeachingRequest()){
 			User user = accessControlController.searchUserByPersonId(ur.getPerson().getId());
 			user.setProfile(humanResourcesController.searchProfileByConstant(Constants.PROFESSOR));
 			this.updateUser(user);
 		}
 		humanResourcesController.approveRequest(ur, evaluator);
 	}
 	
 	public UserRequest searchUserRequest (int id){
 		return humanResourcesController.searchUserRequest(id);
 	}
 	
 	public Poll getPercentage(Poll poll){
 		return academicController.getPercentage(poll);
 	}
 	
 	public void orderModules(List<Module> modules){
 		academicController.orderModules(modules);
 	}
 	
 	public void deleteAnswer(Answer answer){
 		academicController.deleteAnswer(answer);
 	}
 	
 	public Keyword updateKeyword(Keyword keyword){
 		return academicController.updateKeyword(keyword);
 	}
 	
 	public void removeKeywordFromCourse(Course course){
 		academicController.removeKeywordFromCourse(course);
 	}
 	
 	public Criterion searchCriterionByConstant(int constant){
 		return academicController.searchCriterionByConstant(constant);
 	}
 	
 	public void addCriterionToCourseEvaluation(Module module, List<Criterion> criterions){
 		academicController.addCriterionToCourseEvaluation(module, criterions);
 	}
 	
 	public void deleteCourseEvaluation(Module module){
 		academicController.deleteCourseEvaluation(module);
 	}
 	
 	public CourseEvaluation evaluateCourse(CourseEvaluation cEval,
			EvaluationAnswer desiredEval, EvaluationAnswer curEval,
			CriterionAnswer desiredCrit, CriterionAnswer curCrit, Person student) {
		return academicController.evaluateCourse(cEval, desiredEval, curEval,
				desiredCrit, curCrit, student);
	}
 	
 	public boolean hasEvaluatedCourse(CourseEvaluation cEval, Person student){
 		return academicController.hasEvaluatedCourse(cEval, student);
 	}
 	
 	public List<CriterionEvaluationResults> getCourseEvaluationResults(CourseEvaluation eval){
 		return academicController.getCourseEvaluationResults(eval);
 	}
 	
 	//Insere somente a avaliação, sem questões adicionadas!
 	public void insertEvaluation(Evaluation evaluation){
 		this.academicController.insertEvaluation(evaluation);
 	}
 	
 	//insere uma questao discursiva a avaliação
 	public void insertDiscoursiveQuestion_Evaluation(Evaluation evaluation, DiscoursiveQuestion question){
 		this.academicController.insertDiscoursiveQuestion_Evaluation(evaluation, question);
 	}
 	
 	/*
 	 * Insere uma questao objetiva a avaliação
 	 * Essa questao pode ser: MultipleChoice ou TrueOrFalse ou
 	 * ColumnMatching ou BlanksFilling
 	 */
 	public void insertObjectiveQuestion_Evaluation(Evaluation evaluation, ObjectiveQuestion question){
 		this.academicController.insertObjectiveQuestion_Evaluation(evaluation, question);
 	}
 	
 	/**
 	 * Procura uma avaliação pelo seu ID
 	 * @param id
 	 * @return
 	 */
 	public Evaluation searchEvaluation(int id){
 		return this.academicController.searchEvaluation(id);
 	}
 	/**
 	 * Da um get nas avaliações pertencentes ao modulo
 	 * @param id
 	 * @return
 	 */
 	public List<Evaluation> getEvaluationsFromModule(int id){
 		return this.academicController.getEvaluationsFromModule(id);
 	}
 	
 	/**
 	 * Remove uma avaliação da base de dados
 	 * @param evaluation
 	 */
 	public void removeEvaluation(Evaluation evaluation){
 		this.academicController.removeEvaluation(evaluation);
 	}
 	
 	
 	/**
 	 * remove uma questao 
 	 * A questao pode ser discursiva ou objetiva
 	 * @param question
 	 */
 	public void removeQuestionFromEvaluation(Object question){
 		this.academicController.removeQuestionFromEvaluation(question);
 	}
 	
 	/**
 	 * Edita uma questao. A questao pode ser discursiva ou objetiva
 	 * @param question
 	 */
 	public void editQuestion(Object question){
 		this.academicController.editQuestion(question);
 	}
 	
 	 	
 	/**
 	 * insere resposta de avaliacao
 	 * @param evaluation
 	 */
 	 public void insertEvaluationAnswer(EvaluationUserAnswer evaluation){
 		 this.academicController.insertEvaluationAnswer(evaluation);
 	 }
 	 
 	 /**
 	  * insere resposta de avaliacao passando a questao. pode ser usada como edit
 	  * @param evaluation
 	  * @param question
 	  */
 	 public void insertQuestionAnswerEvaluationAnswer(EvaluationUserAnswer evaluation, Object question){
 		 this.academicController.insertQuestionAnswerEvaluationAnswer(evaluation, question);
 	 }
 	 
 	 
 	 /**
 	  * retorna resposta de avaliacao
 	  * @param evaluationUserAnswerID
 	  * @return
 	  */
 	public List<EvaluationUserAnswer> getListEvaluationUserAnswer(int evaluationID){
 		return this.academicController.getListEvaluationUserAnswer(evaluationID);
 	} 

 	public EvaluationUserAnswer getEvaluationUserAnswer(int evaluationID, int userID){
 		return this.academicController.getEvaluationUserAnswer(evaluationID, userID);
 	}
 	
}