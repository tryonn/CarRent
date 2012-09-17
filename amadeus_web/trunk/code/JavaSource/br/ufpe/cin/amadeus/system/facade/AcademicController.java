package br.ufpe.cin.amadeus.system.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.course.CourseDAO;
import br.ufpe.cin.amadeus.system.academic.keyword.Keyword;
import br.ufpe.cin.amadeus.system.academic.keyword.KeywordDAO;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.academic.module.ModuleDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluation;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluationDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswerDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionDAO;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionEvaluationResults;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.evaluationAnswer.EvaluationAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.EvaluationDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.EvaluationUserAnswerDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveAnswerDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.DiscoursiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.answer.question.ObjectiveQuestionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.BlankFillingQuestionDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.BlanksFillingQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ColumnMatching;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ColumnMatchingDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestionDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.MultipleChoiceQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.MultipleChoiceQuestionDAO;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.TrueOrFalseQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.TrueOrFalseQuestionDAO;
import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.academic.module.homework.HomeworkDAO;
import br.ufpe.cin.amadeus.system.academic.module.homework.delivery.Delivery;
import br.ufpe.cin.amadeus.system.academic.module.homework.delivery.DeliveryDAO;
import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.academic.module.material.MaterialDAO;
import br.ufpe.cin.amadeus.system.academic.module.poll.Poll;
import br.ufpe.cin.amadeus.system.academic.module.poll.PollDAO;
import br.ufpe.cin.amadeus.system.academic.module.poll.answer.Answer;
import br.ufpe.cin.amadeus.system.academic.module.poll.answer.AnswerDAO;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.Choice;
import br.ufpe.cin.amadeus.system.academic.role.Role;
import br.ufpe.cin.amadeus.system.academic.role.RoleDAO;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.profile.Profile;
import br.ufpe.cin.amadeus.system.util.dao.DAOFactory;
import br.ufpe.cin.amadeus.util.Constants;


public class AcademicController {

	CourseDAO courseRepository = (CourseDAO) DAOFactory.DEFAULT.createDAO(Course.class);
	RoleDAO roleRepository = (RoleDAO) DAOFactory.DEFAULT.createDAO(Role.class);
	KeywordDAO keywordRepository = (KeywordDAO)DAOFactory.DEFAULT.createDAO(Keyword.class);
	ModuleDAO moduleRepository = (ModuleDAO)DAOFactory.DEFAULT.createDAO(Module.class);
	HomeworkDAO homeworkRepository = (HomeworkDAO)DAOFactory.DEFAULT.createDAO(Homework.class);
	DeliveryDAO deliveryRepository = (DeliveryDAO)DAOFactory.DEFAULT.createDAO(Delivery.class);
	PollDAO pollRepository = (PollDAO)DAOFactory.DEFAULT.createDAO(Poll.class);
	MaterialDAO materialRepository = (MaterialDAO)DAOFactory.DEFAULT.createDAO(Material.class);
	AnswerDAO answerRepository = (AnswerDAO)DAOFactory.DEFAULT.createDAO(Answer.class);
	CriterionDAO criterionRepository = (CriterionDAO)DAOFactory.DEFAULT.createDAO(Criterion.class);
	CourseEvaluationDAO courseEvaluationRepository = (CourseEvaluationDAO)DAOFactory.DEFAULT.createDAO(CourseEvaluation.class);
	CriterionAnswerDAO criterionAnswerRepository = (CriterionAnswerDAO)DAOFactory.DEFAULT.createDAO(CriterionAnswer.class);
	EvaluationDAO evaluationRepository = (EvaluationDAO)DAOFactory.DEFAULT.createDAO(Evaluation.class);
	
	DiscoursiveQuestionDAO discoursiveQuestionRepository = (DiscoursiveQuestionDAO)DAOFactory.DEFAULT.createDAO(DiscoursiveQuestion.class);
	MultipleChoiceQuestionDAO multipleChoiceQuestionRepository = (MultipleChoiceQuestionDAO)DAOFactory.DEFAULT.createDAO(MultipleChoiceQuestion.class);
	BlankFillingQuestionDAO blankFillingQuestionRepository = (BlankFillingQuestionDAO)DAOFactory.DEFAULT.createDAO(BlanksFillingQuestion.class);
	TrueOrFalseQuestionDAO trueOrFalseQuestionRepository = (TrueOrFalseQuestionDAO)DAOFactory.DEFAULT.createDAO(TrueOrFalseQuestion.class);
	ColumnMatchingDAO columnMatchingQuestionRepository = (ColumnMatchingDAO)DAOFactory.DEFAULT.createDAO(ColumnMatching.class);
	
	DiscoursiveAnswerDAO discoursiveQuestionAnswerRepository = (DiscoursiveAnswerDAO)DAOFactory.DEFAULT.createDAO(DiscoursiveQuestionAnswer.class);
	
	EvaluationUserAnswerDAO evaluationUserAnswerRepository = (EvaluationUserAnswerDAO)DAOFactory.DEFAULT.createDAO(EvaluationUserAnswer.class);
	
	public Course searchCourse(int id){
		return courseRepository.findById(id,false);
	}
	
	public Keyword updateKeyword(Keyword keyword){
		return keywordRepository.makePersistentU(keyword);
	}
	
	public void insertCourse(Course course){
		courseRepository.makePersistent(course);
	}
	
	public void updateCourse(Course course){
		courseRepository.makePersistentU(course);
	}
	
	public List<Course> listCourses(){
		return courseRepository.findAll();
	}
	
	public void insertRole(Role role){
		roleRepository.makePersistent(role);
	}
	
	public Role searchRole(int id){
		return roleRepository.findById(id,false);
	}
	
	
	public Role searchRoleByConstant(int id){
		Role role = new Role();
		role.setConstantRole(id);
		List<Role> results = roleRepository.findByExample(role);
		if(!results.isEmpty()){
			return results.get(0);
		} else {
			return null;
		}
	}	
	
	public Role searchRoleByName(String name){
		Role role = new Role();
		role.setName(name);
		List<Role> results = roleRepository.findByExample(role);
		if(!results.isEmpty()){
			return results.get(0);
		} else {
			return null;
		}
	}
	
	public List<Course>[] searchCourseByTerm(String term){
		Role teacherRole = searchRoleByConstant(Constants.PROFESSOR);
		return courseRepository.searchCourses(term, teacherRole.getId());
	}
	
	public Keyword insertKeyword(Keyword keyword){
		return keywordRepository.makePersistent(keyword);
	}
	
	public Keyword searchKeywordById(int id){
		return keywordRepository.findById(id,false);
	}
	
	public Keyword searchKeywordByName(String name){
		Keyword criteria = new Keyword();
		criteria.setName(name);
		List<Keyword> results = keywordRepository.findByExampleS(criteria);
		if(results.isEmpty()){
			return null;
		} else {
			return results.get(0);	
		}
	}
	
	public List<Course>[] searchCourseByKeyword(String keyword){
		return courseRepository.findByKeyword(keyword);
	}
	
	public int searchRoleByUser(User user, Course course){
		return roleRepository.searchRoleByUser(user,course);
	}
	
	public List<Course> searchCoursesByUser(User user){
		return courseRepository.searchCoursesByUser(user);
	}
	
	public boolean exists(Keyword keyword){
		Keyword temp = searchKeywordById(keyword.getId());
		if(temp == null){
			return false;
		}
		return true;
	}
	
	public Module insertModule(Module module){
		return moduleRepository.makePersistent(module);
	}
	
	public Module updateModule(Module module){
		return moduleRepository.makePersistentU(module);
	}
	
	public Module searchModule(int id){
		return moduleRepository.findById(id,false);
	}
	
	public List<Module> searchModule(String name){
		Module criteria = new Module();
		criteria.setName(name);
		return moduleRepository.findByExample(criteria);
	}
	
	public Module getCourseModuleByOrder(Course course, int order){
		List<Module> modules = course.getModules();
		Module result = new Module();
		if(order > modules.size() || order < 0){
			return result;
		}
		for(Module m : modules){
			if(m.getOrder() == order){
				result = m;
			}
		}
		return result;
	}
	
	public void removeModule(Module module){
		moduleRepository.makeTransient(module);
	}
	
	@SuppressWarnings("unchecked")
	public List<Keyword> getMostPopularKeywords(){
		List<Keyword> temp = keywordRepository.getMostPopularKeywords();
		List<Keyword> results = new ArrayList<Keyword>();
		int maxPop = temp.get(0).getPopularity();
		for (Keyword key : temp){
			key.setGroup((int)Math.round((key.getPopularity()*4.0)/maxPop));
			results.add(key);
		}
		temp = null;
		Collections.sort(results);
		return results;
	}
	
	public Homework insertHomework(Homework homework){
		return homeworkRepository.makePersistent(homework);
	}
	
	public Homework searchHomework(int id){
		return homeworkRepository.findById(id,false);
	}
	
	public Homework updateHomework(Homework homework){
		return homeworkRepository.makePersistentU(homework);
	}
	
	public Delivery insertDelivery(Delivery delivery){
		return deliveryRepository.makePersistentU(delivery);
	}
	
	public Delivery searchDelivery(int id){
		return deliveryRepository.findById(id,false);
	}
	
	public Delivery updateDelivery(Delivery delivery){
		return deliveryRepository.makePersistentU(delivery);
	}
	
	public Poll insertPoll(Poll poll){
		return pollRepository.makePersistent(poll);
	}
	
	public Poll updatePoll(Poll poll){
		return pollRepository.makePersistentU(poll);
	}
	
	public Poll searchPoll(int id){
		return pollRepository.findById(id,false);
	}
	
	public Poll getPercentage(Poll poll){
		List<Choice> choices = poll.getChoices();
		for(Choice c : choices){
			if(poll.getVotes()!=0){
				c.setPercentage((double)c.getVotes()/poll.getVotes()*100.0);
			} else {
				c.setPercentage(0);
			}
		}
		return poll;
	}
	
	public Poll answerPoll(Poll poll, Choice choice, Person person) {
		choice.increaseVotes();
		poll.increaseVotes();
		Answer answer = new Answer();
		answer.setChoice(choice);
		answer.setAnswerDate(new Date());
		answer.setPerson(person);
		answer.setPoll(poll);
		this.insertAnswer(answer);
		poll.getAnswers().add(answer);
		return this.updatePoll(poll);
	}
	
	public boolean hasVoted(Person person, Poll poll){
		boolean result = false;
		List<Answer> answers = poll.getAnswers();
		for(Answer a : answers){
			if (a.getPerson().equals(person) && a.getPoll().equals(poll))
				result = true;
		}
		return result;
	}
	
	public Material searchMaterial(int id){
		return materialRepository.findById(id,false);
	}
	
	public int getAmountPendingTasks(User user){
		int result = 0;
		HumanResourcesController hrc = new HumanResourcesController();
		Profile userProfile = user.getProfile();
		if(userProfile.getConstantProfile() == Constants.ADMIN){
			result = hrc.getAdminRequests();
		}
		if(userProfile.getConstantProfile() == Constants.PROFESSOR){
			Role teacherRole = searchRoleByConstant(Constants.PROFESSOR);
			result = hrc.getProfessorRequests(user, teacherRole);
		}
		if(userProfile.getConstantProfile() == Constants.STUDENT){
			result = this.getNumberOfPendantHomeworks(user);
		}
		return result;
	}

	private int getNumberOfPendantHomeworks(User user) {
		int doneHomeworks = this.getNumberOfDoneHomeworks(user);
		int total = this.getTotalHomeworks(user);
		return total - doneHomeworks;
	}

	private int getTotalHomeworks(User user) {
		Role studentRole = this.searchRoleByConstant(Constants.STUDENT);
		return homeworkRepository.searchTotalUserHomeworks(user, studentRole);
	}

	private int getNumberOfDoneHomeworks(User user) {
		Role studentRole = this.searchRoleByConstant(Constants.STUDENT);
		return deliveryRepository.getNumberOfDoneHomeworks(user, studentRole);
	}
	
	public void orderModules(List<Module> modules){
		Collections.sort(modules);
	}
	
	public void deleteAnswer(Answer answer){
		answerRepository.makeTransient(answer);
	}
	
	public Answer insertAnswer(Answer answer){
		return answerRepository.makePersistent(answer);
	}
	
	public Criterion searchCriterionByConstant(int constant){
		Criterion result = null;
		Criterion c = new Criterion();
		c.setConstantCriterion(constant);
		List<Criterion> results = criterionRepository.findByExample(c);
		if(!results.isEmpty())
			result = results.get(0);
		return result;
	}

	public void addCriterionToCourseEvaluation(Module module, List<Criterion> criterions) {
		module.getCourseEvaluation().getCriterions().addAll(criterions);
		moduleRepository.makePersistentU(module);
		
	}
	
	public void deleteCourseEvaluation(Module module){
		CourseEvaluation cEval = module.getCourseEvaluation();
 		module.setCourseEvaluation(null);
 		module.setHasCourseEval(false);
 		this.updateModule(module);
 		courseEvaluationRepository.makeTransient(cEval);
	}
	
	public CourseEvaluation updateCourseEvaluation(CourseEvaluation courseEvaluation){
		return courseEvaluationRepository.makePersistentU(courseEvaluation);
	}

	public CourseEvaluation evaluateCourse(CourseEvaluation eval, EvaluationAnswer desiredEval, EvaluationAnswer curEval, CriterionAnswer desiredCrit, CriterionAnswer curCrit, Person student) {
		desiredEval.setCriterionAnswer(desiredCrit);
		curEval.setCriterionAnswer(curCrit);
		eval.insertEvaluationAnswer(desiredEval);
		eval.insertEvaluationAnswer(curEval);
		eval.insertStudent(student);
		return this.updateCourseEvaluation(eval);
	}

	public boolean hasEvaluatedCourse(CourseEvaluation eval, Person student) {
		boolean result = false;
		if(eval.getStudents() != null){
			result = eval.getStudents().contains(student);
		}
		return result;
	}
	
	public List<CriterionEvaluationResults> getCourseEvaluationResults(CourseEvaluation eval) {
		List<CriterionEvaluationResults> results = new ArrayList<CriterionEvaluationResults>();
		int totalVotes = eval.getStudentsAmount();
		for(Criterion c : eval.getCriterions()){
			CriterionEvaluationResults cer = new CriterionEvaluationResults();
			cer.setCriterion(c);
			//início freqüência desejada
			double[][] dResults = new double[4][];
			for(int i = 0; i <= 3; i++){
				double[] eachFreqResults = new double[5];
				for(int j = 0; j <= 4; j++){
					int freqVotes = this.getFrequencyVotes(i,j,0,c.getId(),eval.getId()); //0 se eh desejada, 1 se eh atual
					if(totalVotes == 0) {
						eachFreqResults[j] = 0;
					} else {
						double square = (double)freqVotes / totalVotes;
						double multipliedSquare = square * 100;
						eachFreqResults[j] = multipliedSquare;
					}
				}
				dResults[i] = eachFreqResults;
			}
			cer.setRd(dResults);
			//fim da frequencia desejada
			//início frequencia atual
			double[][] cResults = new double[4][];
			for(int i = 0; i <= 3; i++){
				double[] eachFreqResults = new double[5];
				for(int j = 0; j <= 4; j++){
					int freqVotes = this.getFrequencyVotes(i,j,1,c.getId(),eval.getId()); //0 se eh desejada, 1 se eh atual
					if(totalVotes == 0){
						eachFreqResults[j] = 0;
					} else {
						double square = (double) freqVotes / totalVotes;
						double multipliedSquare = square * 100;
						eachFreqResults[j] = multipliedSquare;
					}
				}
				cResults[i] = eachFreqResults;
			}
			cer.setRc(cResults);
			//fim da frequencia atual
			results.add(cer);
		}
		return results;
	}
	
	/**
	 * 
	 * @param i - Qual a pergunta cujas respostas vai se fazer a busca
	 * @param j - Qual resposta para a qual está se fazendo a busca ("frequentemente", "raramente"...)
	 * @param k - indicador se é frequência desejada ou atual
	 * @param criterion - que critério está se buscando
	 * @param eval - para qual avaliação de curso
	 * @return retorna um inteiro com o número de respostas
	 */
	private int getFrequencyVotes(int i, int j,int k, int criterion, int eval) {
		return criterionAnswerRepository.getFrequencyVotes(i,j,k,criterion,eval);
	}
	
	/**
	 * Insere avaliacao
	 * @param evaluation
	 */
	public void insertEvaluation(Evaluation evaluation){
		this.evaluationRepository.makePersistent(evaluation);
	}
	
	/**
	 * Salva questao na avaliacao
	 * @param evaluation
	 * @param question
	 */
	public void insertDiscoursiveQuestion_Evaluation(Evaluation evaluation, DiscoursiveQuestion question){

		evaluation.addDiscoursiveQuestion(question);
		this.evaluationRepository.makePersistentU(evaluation);
	}
	
	
	/**
	 * Insere uma questao objetiva na avaliacao
	 * @param evaluation
	 * @param question
	 */
	public void insertObjectiveQuestion_Evaluation(Evaluation evaluation, ObjectiveQuestion question){
		
		evaluation.addObjectiveQuestion(question);
		this.evaluationRepository.makePersistentU(evaluation);
		
	}
	
	/**
	 * PRocura avaliação pelo id
	 * @param id
	 * @return
	 */
	public Evaluation searchEvaluation(int id){
		Evaluation evaluation  = this.evaluationRepository.findById((Integer)id,false);
		return evaluation;
	}
	
	/**
	 * Pega avaliacoes de um modulo modulo
	 * @param id
	 * @return
	 */
	public List<Evaluation> getEvaluationsFromModule(int moduleID){
		List<Evaluation> list =  this.evaluationRepository.getEvaluations_module(moduleID);
		if(list.isEmpty()){
			return null;
		}else return list;
	}
	
	/**
	 * Remove uma avaliação
	 * @param evaluation
	 */
	public void removeEvaluation(Evaluation evaluation){
		this.evaluationRepository.makeTransient(evaluation);                                          
	}
	

  
  /**
   * Remove uma questao da avaliação
   * @param question
   */
  public void removeQuestionFromEvaluation(Object question){
	  if(question instanceof DiscoursiveQuestion){
		  DiscoursiveQuestion dq = (DiscoursiveQuestion)question;
		  this.discoursiveQuestionRepository.makeTransient(dq);
	  }
	  else if(question instanceof TrueOrFalseQuestion){
		  TrueOrFalseQuestion ob = (TrueOrFalseQuestion)question;
		  this.trueOrFalseQuestionRepository.makeTransient(ob);
	  }
	  else if(question instanceof ColumnMatching){
		  ColumnMatching cm = (ColumnMatching)question;
		  this.columnMatchingQuestionRepository.makeTransient(cm);
	  }
	  else if (question instanceof BlanksFillingQuestion){
		  BlanksFillingQuestion bf = (BlanksFillingQuestion)question;
		  this.blankFillingQuestionRepository.makeTransient(bf);
	  }
	  else if (question instanceof MultipleChoiceQuestion){
		  MultipleChoiceQuestion mc = (MultipleChoiceQuestion)question;
		  this.multipleChoiceQuestionRepository.makeTransient(mc);
	  }
  }
  
  
  public void editQuestion(Object question){
	  if(question instanceof DiscoursiveQuestion){
		  DiscoursiveQuestion dq = (DiscoursiveQuestion)question;
		  this.discoursiveQuestionRepository.makePersistentU(dq);
	  }
	  else if(question instanceof ColumnMatching){
		  ColumnMatching cm = (ColumnMatching)question;
		  this.columnMatchingQuestionRepository.makePersistentU(cm);
	  }
	  else if(question instanceof BlanksFillingQuestion){
		  BlanksFillingQuestion bf = (BlanksFillingQuestion)question;
		  this.blankFillingQuestionRepository.makePersistentU(bf);
	  }
	  else if(question instanceof MultipleChoiceQuestion){
		  MultipleChoiceQuestion mc = (MultipleChoiceQuestion)question;
		  this.multipleChoiceQuestionRepository.makePersistentU(mc);
	  }
	  else if(question instanceof TrueOrFalseQuestion){
		  TrueOrFalseQuestion tf = (TrueOrFalseQuestion)question;
		  this.trueOrFalseQuestionRepository.makePersistentU(tf);
	  }
  }
  
    
  /**
   * Resposta da avaliacao
   */
  public void insertEvaluationAnswer(EvaluationUserAnswer evaluation){
	  this.evaluationUserAnswerRepository.makePersistentU(evaluation);
  }
 
  
  
  /**
   * Insere uma resposta de questao
   * @param evaluation
   * @param question
   */
  
  public void insertQuestionAnswerEvaluationAnswer(EvaluationUserAnswer evaluation, Object question){
	  if(question instanceof DiscoursiveQuestionAnswer){
		  DiscoursiveQuestionAnswer dqa = (DiscoursiveQuestionAnswer)question;
		  evaluation.addDiscoursiveQuestion(dqa);
		  this.evaluationUserAnswerRepository.makePersistentU(evaluation);
	  }
	  else if(question instanceof ObjectiveQuestionAnswer){
		  ObjectiveQuestionAnswer oba = (ObjectiveQuestionAnswer)question;
		  evaluation.addObjectiveQuestion(oba);
		  this.evaluationUserAnswerRepository.makePersistentU(evaluation);
	  }
  }
  
  
  
  /**
   * este metodo retorna uma lista de respostas para uma dada avaliação
   * 
   * @param ID de uma avaliação
   * @return Uma lista de respostas
   *  
   */
  
  
  public List<EvaluationUserAnswer> getListEvaluationUserAnswer(int evaluationID){
	  List<EvaluationUserAnswer> list =  this.evaluationUserAnswerRepository.getListEvaluationUserAnswer(evaluationID);
		if(list.isEmpty()){
			list =  null;
		}
		return list;
  }
  
  /**
   * Este metodo retorna a resposta de um aluno de uma dada avaliação
   * 
   * @param evaluationID
   * @param userID
   * @return 
   */
  
  public EvaluationUserAnswer getEvaluationUserAnswer(int evaluationID, int userID){
	  return (this.evaluationUserAnswerRepository.getEvaluationUserAnswer(evaluationID, userID));
  }
  
   
  public void removeKeywordFromCourse(Course course) {
	  Set<Keyword> keys = new HashSet<Keyword>();
	  keys.addAll(course.getKeywords());
	  course.getKeywords().clear();
	  this.updateCourse(course);
	  Iterator<Keyword> keysIt = keys.iterator();
	  while(keysIt.hasNext()){
		  Keyword temp = keysIt.next();
		  temp.decreasePopularity();
		  if(temp.getPopularity()==0){
			  keywordRepository.makeTransient(temp);
		  } else {
			  keywordRepository.makePersistentU(temp);
		  }
	  }
	  keysIt = null;
	  keys = null;
  }
}