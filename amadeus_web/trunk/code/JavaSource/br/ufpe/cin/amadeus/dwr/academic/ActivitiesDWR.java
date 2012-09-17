
package br.ufpe.cin.amadeus.dwr.academic;

import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getMessage;
import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getModuleDWR;
import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getSessionBean;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;
import br.ufpe.cin.amadeus.dwr.academic.bean.ForumJSBean;
import br.ufpe.cin.amadeus.dwr.academic.bean.MessageJSBean;
import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.CourseEvaluation;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.commentary.Commentary;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.Criterion;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionAnswer;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.criterion.CriterionEvaluationResults;
import br.ufpe.cin.amadeus.system.academic.module.courseEvaluation.evaluationAnswer.EvaluationAnswer;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.Evaluation;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.BlanksFillingQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.DiscoursiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.MultipleChoiceQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.ObjectiveQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.TrueOrFalseQuestion;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.alternative.Alternative;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.blank.Blank;
import br.ufpe.cin.amadeus.system.academic.module.evaluation.question.sentence.Sentence;
import br.ufpe.cin.amadeus.system.academic.module.forum.Forum;
import br.ufpe.cin.amadeus.system.academic.module.forum.Message;
import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.academic.module.poll.Poll;
import br.ufpe.cin.amadeus.system.academic.module.poll.answer.Answer;
import br.ufpe.cin.amadeus.system.academic.module.poll.choice.Choice;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.util.Constants;
import br.ufpe.cin.amadeus.util.DateConstructor;

public class ActivitiesDWR {

	private Module module;
	private AmadeusSystem amadeus;
	private Map<Integer, Poll> polls;
	private int pollsCounter;
	private Map<Integer, Homework> mds;
	private int mdsCounter;
	private Map<Integer, Forum> foruns;
	private int forumCounter;
	private CourseEvaluation courseEvaluation;
	private Map<Integer, Evaluation> studentEvaluations;
	private int studentEvalCounter;
	
	public ActivitiesDWR() {
		System.out.println("CRIOU ACTIVITIES");
		amadeus = AmadeusSystem.getInstance();
		Course c = (Course)getSessionBean("course");
		WebContext wc = WebContextFactory.get();
		wc.getSession().setAttribute("course", c);
		this.polls = new LinkedHashMap<Integer, Poll>();
		this.mds = new LinkedHashMap<Integer, Homework>();
		this.foruns = new LinkedHashMap<Integer, Forum>();
		this.studentEvaluations = new LinkedHashMap<Integer, Evaluation>();
		
	}
	
	public Object[] init(int editing) {
		pollsCounter = 0;
		polls.clear();
		mdsCounter = 0;
		mds.clear();
		forumCounter = 0;
		foruns.clear();
		courseEvaluation = null;
		module = null;
		studentEvalCounter = 0;
		studentEvaluations.clear();
		

		ModuleDWR moduleDWR = getModuleDWR();
		module = moduleDWR.getModule(editing);
		if(amadeus.getEvaluationsFromModule(module.getId())!= null)
			module.setStudentEvaluations(amadeus.getEvaluationsFromModule(module.getId()));
		
		for (Poll poll : module.getPolls()) {
			polls.put(pollsCounter, poll);
			pollsCounter++;
		}
		for (Homework hw : module.getHomeworks()) {
			mds.put(mdsCounter, hw);
			mdsCounter++;
		}
		for (Forum f : module.getForums()) {
			foruns.put(forumCounter, f);
			forumCounter++;
		}
		
		for (Evaluation e : module.getStudentEvaluations()) {
			studentEvaluations.put(studentEvalCounter, e);
			studentEvalCounter++;
		}
		
		CourseEvaluation eval = module.getCourseEvaluation();
		if (eval != null) 
		  	courseEvaluation = eval;
		  
		 
		 
		return getActivitiesData();
	}
	
	private Object[] getActivitiesData() {
		Object[] obj;
		List<Object[]> list = new ArrayList<Object[]>();
		for (Integer id : mds.keySet()) {
			obj = new Object[] {id, mds.get(id).getName(), "MaterialDelivery"};
			list.add(obj);
		}
		for (Integer id : polls.keySet()) {
			obj = new Object[] {id, polls.get(id).getName(), "Poll"};
			list.add(obj);
		}
		for (Integer id : foruns.keySet()) {
			obj = new Object[] {id, foruns.get(id).getName(), "Forum"};
			list.add(obj);
		}
		if (this.courseEvaluation != null) {
			//obj = new Object[] {1, getMessage("evaluation.name"), "Course Evaluation"}; para internacionalizar, descomente esta linha e comente a de baixo
		  	obj = new Object[] {1, "Avaliação de Curso", "CourseEvaluation"};
		  	list.add(obj);
		
		}
		for (Integer id : studentEvaluations.keySet()) {
			obj = new Object[] {id, studentEvaluations.get(id).getName(), "StudentEvaluation"};
			list.add(obj);
		}		
		
		 
		return list.toArray();
	}
	
	public Map<String, String> listActivities(int current) {
		Map<String, String> activities = new LinkedHashMap<String, String>();
		String[] values;
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		if (module.getCourseEvaluation()==null){
			String[] value = {
					"activities.select", "activities.material",
					"activities.poll", "activities.scorm",
					"activities.forum", "activities.evaluation", 
					"activities.studentEvaluation"
			};
			values = value;
			
		} 
		
		else {
			String[] value = {
					"activities.select", "activities.material",
					"activities.poll", "activities.scorm",
					"activities.forum", "activities.studentEvaluation"
			};
			values = value;
		}
		 
		for (String value : values) {
			activities.put(value.substring(11), getMessage(value));
		}
		return activities;
	}
	
	public Object[] saveMaterial(String name, String desc,
			int day, int month, int year, boolean allow, int editing, int current) {
		
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		Date deadline = gc.getTime();
		if (deadline.before(course.getInitialCourseDate())) {
			return returnError(getMessage("errors.deliveryDateBefore"));
		}
		if (deadline.after(course.getFinalCourseDate())) {
			return returnError(getMessage("errors.deliveryDateAfter"));
		}
		
		Homework hw = mds.get(editing);
		boolean wasNull = (hw == null);
		if (wasNull)
			hw = new Homework();
		hw.setName(name);
		hw.setDescription(desc);
		hw.setAllowed(allow);
		hw.setDeadLine(deadline);
		if (wasNull) {
			module.getHomeworks().add(hw);
			mds.put(mdsCounter++, hw);
		}
		if (module.getName() != null)
			amadeus.updateModule(module);
		return getActivitiesData();
	}
	
	public Object[] deleteMaterialDelivery(int materialId) {
		Homework hw = mds.get(materialId);
		if (hw != null) {
			mds.remove(materialId);
			module.getHomeworks().remove(hw);
			if (module.getName() != null)
				amadeus.updateModule(module);
		}
		return getActivitiesData();
	}
	
	public Map retrieveMaterialDelivery(int materialId) {
		Map<String, Object> m = new HashMap<String, Object>();
		Homework hw = mds.get(materialId);
		if (hw != null) {
			m.put("materialId", materialId);
			m.put("name", hw.getName());
			m.put("allow", hw.isAllowed());
			m.put("description", hw.getDescription());
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(hw.getDeadLine());
			m.put("day", gc.get(Calendar.DAY_OF_MONTH));
			m.put("month", gc.get(Calendar.MONTH) + 1);
			m.put("year", gc.get(Calendar.YEAR));
			return m;
		}
		return null;
	}
	/**
	 * 
	 * 
	 * @param criteria
	 * @param current
	 * @return
	 */
	public Object[] saveCourseEvaluation(boolean[] criteria, int current) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		List<Criterion> criterions = new ArrayList<Criterion>();
		for(int i = 0; i<criteria.length; i++){
			if(criteria[i]){
				Criterion crit = amadeus.searchCriterionByConstant(i);
				Criterion crit2 = new Criterion();
				crit2.setName(crit.getName());
				crit2.setConstantCriterion(crit.getConstantCriterion());
				criterions.add(crit2);
			}
		}
		CourseEvaluation courseEval = new CourseEvaluation();
		courseEval.setCriterions(criterions);
		courseEval.setDate(new Date());
		module.setCourseEvaluation(courseEval);
		module.setHasCourseEval(true);
		amadeus.updateModule(module);
		courseEvaluation = courseEval;
		
		return getActivitiesData();
	}
	
	/**
	 * Este método exclui uma avaliação de curso criada pelo professor.
	 * @param id Este id não será utilizado. Apenas para seguir o padrão.
	 */
	public Object[] deleteCourseEvaluation(int id) {
		//module.remove(this.evaluation);
		amadeus.deleteCourseEvaluation(module);
		this.courseEvaluation = null;
		return getActivitiesData();
	}
	
	public Object[] saveForum(String name, String desc, int editing, int current) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		Forum forum = foruns.get(editing);
		boolean wasNull = (forum == null);
		
		if (wasNull) {
			forum = new Forum();			
			forum.setDate(new Date());
			forum.setTime(new Time(System.currentTimeMillis()));
		}
		
		forum.setName(name);
		forum.setDescription(desc);

		if (wasNull) {
			module.getForums().add(forum);
			foruns.put(forumCounter++, forum);
		}
		if (module.getName() != null)
			amadeus.updateModule(module);
		return getActivitiesData();
	}
	
	public Object[] deleteForum(int forumId) {
		Forum forum = foruns.get(forumId);
		if (forum != null) {
			foruns.remove(forumId);
			module.getForums().remove(forum);
			if (module.getName() != null)
				amadeus.updateModule(module);
		}
		return getActivitiesData();
	}
	
	
	public ForumJSBean retrieveForum(int forumId) {
		Map<String, Object> m = new HashMap<String, Object>();
		Forum forum = foruns.get(forumId);

		return new ForumJSBean(forumId, -1, forum, false);
	}
	
	public ForumJSBean viewForumMessages(int forumId, int moduleId) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(moduleId - 1);
		init(moduleId);
		Forum forum = foruns.get(forumId);

		return new ForumJSBean(forumId, moduleId, forum, true);
	}
	
	public MessageJSBean expandForumDetails(int messageId, int forumId, int moduleId) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(moduleId - 1);
		init(moduleId);
		Forum f = foruns.get(forumId);
		List<Message> messages = f.getMessages();
		Message msg = messages.get(messageId - 1);	
	
		return new MessageJSBean(messageId, forumId, moduleId, msg);
	}
	
	public int replyForum (int forumId, int moduleId, String message) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(moduleId - 1);
		init(moduleId);
		User usr = (User)getSessionBean("user");
		List<Message> msgs = foruns.get(forumId).getMessages();
		Message msg = new Message();
		msg.setAuthor(usr.getPerson());
		msg.setBody(message);
		msg.setTitle("");
		msg.setDate(new Date());
		msg.setHour(new Time(System.currentTimeMillis()));
		msgs.add(msg);
		foruns.get(forumId).setMessages(msgs);
		amadeus.updateModule(module);
		return moduleId;
	}
	
	public Object[] savePoll(String name, String question, int day,
			int month, int year, String[] alternatives, int editing, int current) {
		
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		Date finishDate = gc.getTime();
		if (finishDate.before(course.getInitialCourseDate())) {
			return returnError(getMessage("errors.pollDateBefore"));
		}
		if (finishDate.after(course.getFinalCourseDate())) {
			return returnError(getMessage("errors.pollDateAfter"));
		}
		
		Poll poll = polls.get(editing);
		boolean wasNull = (poll == null);
		
		if (wasNull)
			poll = new Poll();
		
		poll.setName(name);
		poll.setQuestion(question);
		poll.setCreationDate(new Date());
		poll.setFinishDate(finishDate);
		
		// TODO Rever em baixo
		List<Answer> answers = poll.getAnswers();
		for(Answer a : answers){
			amadeus.deleteAnswer(a);
		}
		answers.clear();
		List<Choice> choices = poll.getChoices();
		choices.clear();

		for (int i = 0; i < alternatives.length; i++) {
			Choice choice = new Choice();
			choice.setOption(alternatives[i]);
			choice.setAlternative("" + i);
			choice.setVotes(0);
			choices.add(choice);
			//amadeus.insertChoice(choice);
		}
		poll.setVotes(0);
		poll.setChoices(choices);
		
		if (wasNull) {
			module.getPolls().add(poll);
			polls.put(pollsCounter++, poll);
		}
		if (module.getName() != null)
			amadeus.updateModule(module);
		return getActivitiesData();
	}
	
	public Object[] deletePoll(int pollId) {
		Poll poll = polls.get(pollId);
		if (poll != null) {
			polls.remove(pollId);
			module.getPolls().remove(poll);
			if (module.getName() != null)
				amadeus.updateModule(module);
		}
		return getActivitiesData();
	}
	
	public Map retrievePoll(int pollId) {
		Map<String, Object> m = new HashMap<String, Object>();
		Poll poll = polls.get(pollId);
		User usr = (User)getSessionBean("user");
		Person person = usr.getPerson();
		if (poll != null) {
			m.put("pollId", pollId);
			m.put("name", poll.getName());
			m.put("question", poll.getQuestion());
			
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(poll.getFinishDate());
			m.put("day", gc.get(Calendar.DAY_OF_MONTH));
			m.put("month", gc.get(Calendar.MONTH) + 1);
			m.put("year", gc.get(Calendar.YEAR));
			m.put("hasAnswered", amadeus.hasVoted(poll, person));
			Object[] choices = poll.getChoices().toArray();
			String[] alts = new String[choices.length];
			for (int i = 0; i < alts.length; i++)
				alts[i] = ((Choice) choices[i]).getOption();
				
			m.put("alts", alts);
			return m;
		}
		return null;
	}
	
	public Object[] returnError(String ... errors) {
		Object[] err = new Object[errors.length];
		for (int i = 0; i < errors.length; i++) {
			err[i] = new Object[] {i, errors[i], "Error"};
		}
		return err;
	}
	
	
	
	
	
	//Used to show the student´s view of the modules
	public Map retrieveInfo(int currentModule) {
		Map<String, Object> m = new HashMap<String, Object>();
		Course c = (Course)getSessionBean("course");
		Module module = c.getModules().get(currentModule - 1);
		init(currentModule);
		
		User usr = (User)getSessionBean("user");
		Person person = usr.getPerson();
		
		Object[] polls = module.getPolls().toArray();
		Object[] homeworks = module.getHomeworks().toArray();
		Object[] foruns = module.getForums().toArray();
		Object[] studentEvaluation = module.getStudentEvaluations().toArray();
		
		String[] pollSet = new String[polls.length];
		String[] homeworkSet = new String[homeworks.length];
		String[] forumSet = new String[foruns.length];
		String[] studentEvaluationSet = new String[studentEvaluation.length];
		
		
		int tempMaxLength = Math.max(pollSet.length, Math.max(homeworkSet.length, studentEvaluationSet.length));
		int maxLength = Math.max(tempMaxLength, forumSet.length);
		for (int i = 0; i < maxLength; i++) {
			if (i < polls.length) 
				pollSet[i] = ((Poll)polls[i]).getName();
			if (i < homeworks.length)
				homeworkSet[i] = ((Homework)homeworks[i]).getName();
			if (i < foruns.length)
				forumSet[i] = ((Forum)foruns[i]).getName();
			if (i < studentEvaluation.length)
				studentEvaluationSet[i] = ((Evaluation)studentEvaluation[i]).getName();
		}
		m.put("polls", pollSet);
		m.put("deliveries", homeworkSet);
		m.put("foruns", forumSet);
		m.put("pollCounter", polls.length);
		m.put("mdCounter", homeworks.length);
		m.put("forumCounter", foruns.length);
		m.put("studentEvaluationCounter", studentEvaluation.length);
		if (module.getCourseEvaluation() != null){
			m.put("evaluation", true);
			if(usr.getProfile().getConstantProfile() == 1){
				if(module.getCourseEvaluation().containsPerson(person))
					m.put("hasEvaluated", true);
			}
		}
		m.put("studentEvaluation", studentEvaluationSet);
		m.put("current", currentModule);
		return m;
	}
	
	public int replyPoll(int pollId, int alt, int currentModule) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(currentModule - 1);
		init(currentModule);
		Poll poll = polls.get(pollId);
		User usr = (User)getSessionBean("user");
		Person person = usr.getPerson();
		Object[] choices = poll.getChoices().toArray();
		Choice choice = ((Choice)choices[alt]);
		amadeus.answerPoll(poll, choice, person);
		return currentModule;
	}
	
	/**
	 * Este método salva as respostas fornecidas pelo aluno para uma avaliação de curso.
	 * @param currentModule indice do módulo
	 * @param message comentários do aluno
	 * @param answers resposta da avaliação de curso.
	 * @return indice do modulo
	 */
	public int replyCourseEvaluation(int currentModule, String message, String[] answers) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(currentModule - 1);
		init(currentModule);
		CourseEvaluation courseEval = module.getCourseEvaluation();
		User usr = (User)getSessionBean("user");
		Person person = usr.getPerson();
		Object[] criterions =  courseEval.getCriterions().toArray();
		Criterion crit = null;
		Commentary commentary = new Commentary();
		commentary.setDescription(message);
		courseEval.insertCommentary(commentary);
		for(int i = 0; i< criterions.length; i++){
			crit = (Criterion) criterions[i];					
			EvaluationAnswer currentFrequence = new EvaluationAnswer();
			EvaluationAnswer desiredFrequence = new EvaluationAnswer();
			CriterionAnswer currentCriterion = new CriterionAnswer();
			CriterionAnswer desiredCriterion = new CriterionAnswer();
			currentFrequence.setCriterion(crit);
			currentFrequence.setFrequencyType(Constants.CURRENT_FREQUENCY);
			currentFrequence.setCriterionAnswer(currentCriterion);
			char currentR1 = answers[i].charAt(1);			
			currentCriterion.setR1(currentR1);
			char currentR2 = answers[i].charAt(4);
			currentCriterion.setR2(currentR2);
			char currentR3 =  answers[i].charAt(7);
			currentCriterion.setR3(currentR3);
			char currentR4 =  answers[i].charAt(10);
			currentCriterion.setR4(currentR4);
			desiredFrequence.setCriterion(crit);
			desiredFrequence.setFrequencyType(Constants.DESIRED_FREQUENCY);
			desiredFrequence.setCriterionAnswer(desiredCriterion);
			char desiredR1 =  answers[i].charAt(2);
			desiredCriterion.setR1(desiredR1);
			char desiredR2 =  answers[i].charAt(5);
			desiredCriterion.setR2(desiredR2);
			char desiredR3 =  answers[i].charAt(8);
			desiredCriterion.setR3(desiredR3);
			char desiredR4 =  answers[i].charAt(11);
			desiredCriterion.setR4(desiredR4);
			amadeus.evaluateCourse(courseEval, desiredFrequence, currentFrequence, 
									desiredCriterion, currentCriterion, person);
		}
		return currentModule;
	}
	
	public Object[] viewPollResults(int pollId, int current) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		Object[] obj;
		List<Object[]> list = new ArrayList<Object[]>();
		Poll poll = polls.get(pollId);
		poll = amadeus.getPercentage(poll);
		List<Choice> choices = poll.getChoices();
		for (Choice c : choices) {
			obj = new Object[]{c.getOption(), c.getPercentage()};
			list.add(obj);
		}
		list.add(new Object[]{current});
		list.add(new Object[]{poll.getQuestion()});
		return list.toArray();
	}
	
	public Map retrieveHW(int homeworkId, int moduleId) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(moduleId - 1);
		init(moduleId);
		Map<String, Object> m = new HashMap<String, Object>();
		Homework hw = mds.get(homeworkId);
		if (hw != null) {
			m.put("name", hw.getName());
			m.put("allow", hw.isAllowed());
			m.put("description", hw.getDescription());
			m.put("homeworkId", hw.getId());
			
			Date today = DateConstructor.today();
			m.put("isLate", today.after(hw.getDeadLine()));
			
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			m.put("deadline", formater.format(hw.getDeadLine()));
			m.put("today", formater.format(today));
			return m;
		}
		return null;
	}
	
	public Map retrieveP(int pollId, int current) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		Map<String, Object> m = new HashMap<String, Object>();
		Poll poll = polls.get(pollId);
		User usr = (User)getSessionBean("user");
		Person person = usr.getPerson();
		if (poll != null) {
			m.put("pollId", pollId);
			m.put("name", poll.getName());
			m.put("question", poll.getQuestion());
			
			Date today = DateConstructor.today();
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			m.put("finishDate", formater.format(poll.getFinishDate()));
			m.put("hasAnswered", amadeus.hasVoted(poll, person));
			m.put("isLate", today.after(poll.getFinishDate()));
			
			Object[] choices = poll.getChoices().toArray();
			String[] alts = new String[choices.length];
			for (int i = 0; i < alts.length; i++)
				alts[i] = ((Choice) choices[i]).getOption();
				
			m.put("alts", alts);
			m.put("module", current);
			return m;
		}
		return null;
	}
	
	/**
	 * Esete método recupera as informações sobre a avaliação de curso no banco.
	 * @param currentModule
	 * @return
	 */
	public Map retrieveCourseEvaluation(int currentModule) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(currentModule - 1);
		init(currentModule);
		Map<String, Object> m = new HashMap<String, Object>();
		CourseEvaluation eval = module.getCourseEvaluation();
		User usr = (User)getSessionBean("user");
		boolean isAssistent = false;
		if (eval != null) {
			Object[] criterions =  eval.getCriterions().toArray();
			int [] constCriterions = new int[criterions.length];
			for(int i = 0; i< criterions.length; i++){
				constCriterions[i] = ((Criterion) criterions[i]).getConstantCriterion();					
			}
			List<User> users= amadeus.listAssistantsByCourse(course);
			if(users.contains(usr))
			isAssistent = true;
			
			if(usr.getProfile().getConstantProfile() == 0 || usr.getProfile().getConstantProfile() == 2 || isAssistent){
				List<CriterionEvaluationResults> evalResultsList = amadeus.getCourseEvaluationResults(eval);
				Iterator it = evalResultsList.iterator();
				double desiredResults [][], currentResults[][];
				while(it.hasNext()){
					CriterionEvaluationResults criterionResults = (CriterionEvaluationResults) it.next();
					desiredResults = criterionResults.getRd();
					currentResults = criterionResults.getRc();
					double answerQuestion [][];
					for(int a = 0; a<4; a++){
						answerQuestion = new double[2][5];
						for(int j = 0; j< 5; j++){
								answerQuestion[0][j] = currentResults[a][j];
								answerQuestion[1][j] = desiredResults[a][j];
						}
						String s = "crit"+Integer.toString(criterionResults.getCriterion().getConstantCriterion());
						s += "question"+Integer.toString(a);
						m.put(s, answerQuestion);
						
					}				
				}
			}
			m.put("constCriterions", constCriterions);
			m.put("module", currentModule);
			if(isAssistent)
			m.put("profile", 3);
			else
			m.put("profile", usr.getProfile().getConstantProfile());
			
			return m;
		}
		return null;
	}
	
	/**
	 * Este método recupera as informações sobre as avaliações de aluno existentes no módulo.
	 * @param evaluationId id da Avaliação.
	 * @return
	 */
	public Map retrieveStudentEvaluation(int evaluationId){
		Map<String, Object> m = new HashMap<String, Object>();
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		if (studentEval != null) {
			m.put("evaluationId", evaluationId);
			m.put("name", studentEval.getName());
			m.put("score", studentEval.getWeight());			
			
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(studentEval.getBeginning());
			int day =  gc.get(Calendar.DAY_OF_MONTH);
			String dayS = "";
			if(day<10)
				dayS= "0"+day;
			else
				dayS= ""+day;
			
			int month =  gc.get(Calendar.MONTH)+1;
			String monthS = "";
			if(month<10)
				monthS= "0"+month;
			else
				monthS= ""+month;
			
			int year =  gc.get(Calendar.YEAR);
			String yearS = "";
			if(year<10)
				yearS= "0"+year;
			else
				yearS= ""+year;
			
			
			m.put("startDate", dayS+"/"+monthS +"/"+yearS);
			
			int hour = gc.get(Calendar.HOUR_OF_DAY);
			String hourS = "";
			if(hour<10)
				hourS = "0"+hour;
			else
				hourS = ""+hour;
			int minute = gc.get(Calendar.MINUTE);
			String minuteS = "";
			if(minute<10)
				minuteS = "0"+minute;
			else
				minuteS = ""+minute;
			m.put("startTime", hourS+":"+minuteS);
			
			m.put("maxNote", studentEval.getMaxScore());
			
			gc = new GregorianCalendar();
			gc.setTime(studentEval.getEnd());			
			day =  gc.get(Calendar.DAY_OF_MONTH);
			dayS = "";
			if(day<10)
				dayS= "0"+day;
			else
				dayS= ""+day;
			
			month =  gc.get(Calendar.MONTH)+1;
			monthS = "";
			if(month<10)
				monthS= "0"+month;
			else
				monthS= ""+month;
			
			year =  gc.get(Calendar.YEAR);
			yearS = "";
			if(year<10)
				yearS= "0"+year;
			else
				yearS= ""+year;			
			m.put("finishDate", dayS+"/"+monthS +"/"+yearS);
						
			hour = gc.get(Calendar.HOUR_OF_DAY);
			hourS = "";
			if(hour<10)
				hourS = "0"+hour;
			else
				hourS = ""+hour;
			minute = gc.get(Calendar.MINUTE);
			minuteS = "";
			if(minute<10)
				minuteS = "0"+minute;
			else
				minuteS= ""+minute;
			m.put("finishTime", hourS+":"+minuteS);
			
			m.put("hasQuestions", false);
			
			if(!studentEval.getDiscoursiveQuestions().isEmpty()){
				m.put("hasQuestions", true);
				Iterator it = studentEval.getDiscoursiveQuestions().iterator();
				String [][] array;
				int size = (studentEval.getDiscoursiveQuestions().size()+
	                    studentEval.getObjectiveQuestions().size());
				if(!studentEval.getObjectiveQuestions().isEmpty()){
					array = new String[size][3];
					int i = 0;
					DiscoursiveQuestion dq;
					for(; i<studentEval.getDiscoursiveQuestions().size(); i++){
						dq = (DiscoursiveQuestion)it.next();
						array[i][0]= dq.getQuestion();
						array[i][1]= String.valueOf(dq.getLevel());
						array[i][2]= String.valueOf(dq.getId());
					}
					it = studentEval.getObjectiveQuestions().iterator();
					ObjectiveQuestion oq;
					for(; i<size; i++){
						oq = (ObjectiveQuestion)it.next();
						array[i][0]= oq.getQuestion();
						array[i][1]= String.valueOf(oq.getLevel());
						array[i][2]= String.valueOf(oq.getId());
					}
				}else{
					array = new String[studentEval.getDiscoursiveQuestions().size()][3];
					DiscoursiveQuestion dq;
					for(int i = 0; i<studentEval.getDiscoursiveQuestions().size(); i++){
						dq = (DiscoursiveQuestion)it.next();
						array[i][0]= dq.getQuestion();
						array[i][1]= String.valueOf(dq.getLevel());
						array[i][2]= String.valueOf(dq.getId());
					}
				}
				
				m.put("questions", array);

			}else{
				if(!studentEval.getObjectiveQuestions().isEmpty()){
					m.put("hasQuestions", true);
					String [][] array = new String[studentEval.getObjectiveQuestions().size()][3];
					Iterator it = studentEval.getObjectiveQuestions().iterator();
					ObjectiveQuestion oq;
					for(int i = 0; i<studentEval.getObjectiveQuestions().size(); i++){
						oq = (ObjectiveQuestion)it.next();
						array[i][0]= oq.getQuestion();
						array[i][1]= String.valueOf(oq.getLevel());
						array[i][2]= String.valueOf(oq.getId());
					}
					m.put("questions", array);
					
				}
			}
			return m;
		}
		return null;
		
	}
	
	/**
	 * Este método recupera as informações sobre as avaliações de aluno existentes no módulo.
	 * @param evaluationId id da avaliação
	 * @param currentModule id do módulo de edição.
	 * @return
	 */
	public Map retrieveStudentEval(int evaluationId, int currentModule){
		Map<String, Object> m = new HashMap<String, Object>();
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(currentModule - 1);
		init(currentModule);
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		
		if (studentEval != null) {
			m.put("evaluationId", evaluationId);
			m.put("name", studentEval.getName());
			m.put("score", studentEval.getWeight());			
			
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(studentEval.getBeginning());
			int day =  gc.get(Calendar.DAY_OF_MONTH);
			String dayS = "";
			if(day<10)
				dayS= "0"+day;
			else
				dayS= ""+day;
			
			int month =  gc.get(Calendar.MONTH)+1;
			String monthS = "";
			if(month<10)
				monthS= "0"+month;
			else
				monthS= ""+month;
			
			int year =  gc.get(Calendar.YEAR);
			String yearS = "";
			if(year<10)
				yearS= "0"+year;
			else
				yearS= ""+year;
			
			
			m.put("startDate", dayS+"/"+monthS +"/"+yearS);
			
			int hour = gc.get(Calendar.HOUR_OF_DAY);
			String hourS = "";
			if(hour<10)
				hourS = "0"+hour;
			else
				hourS = ""+hour;
			int minute = gc.get(Calendar.MINUTE);
			String minuteS = "";
			if(minute<10)
				minuteS = "0"+minute;
			else
				minuteS = ""+minute;
			m.put("startTime", hourS+":"+minuteS);
			
			m.put("maxNote", studentEval.getMaxScore());
			
			gc = new GregorianCalendar();
			gc.setTime(studentEval.getEnd());			
			day =  gc.get(Calendar.DAY_OF_MONTH);
			dayS = "";
			if(day<10)
				dayS= "0"+day;
			else
				dayS= ""+day;
			
			month =  gc.get(Calendar.MONTH)+1;
			monthS = "";
			if(month<10)
				monthS= "0"+month;
			else
				monthS= ""+month;
			
			year =  gc.get(Calendar.YEAR);
			yearS = "";
			if(year<10)
				yearS= "0"+year;
			else
				yearS= ""+year;			
			m.put("finishDate", dayS+"/"+monthS +"/"+yearS);
						
			hour = gc.get(Calendar.HOUR_OF_DAY);
			hourS = "";
			if(hour<10)
				hourS = "0"+hour;
			else
				hourS = ""+hour;
			minute = gc.get(Calendar.MINUTE);
			minuteS = "";
			if(minute<10)
				minuteS = "0"+minute;
			else
				minuteS= ""+minute;
			m.put("finishTime", hourS+":"+minuteS);
			
			m.put("module", currentModule);
			
			m.put("hasQuestions", false);
			
			if(!studentEval.getDiscoursiveQuestions().isEmpty()){
				m.put("hasQuestions", true);
				Iterator it = studentEval.getDiscoursiveQuestions().iterator();
				String [][] array;
				int size = (studentEval.getDiscoursiveQuestions().size()+
	                    studentEval.getObjectiveQuestions().size());
				if(!studentEval.getObjectiveQuestions().isEmpty()){
					array = new String[size][3];
					int i = 0;
					DiscoursiveQuestion dq;
					for(; i<studentEval.getDiscoursiveQuestions().size(); i++){
						dq = (DiscoursiveQuestion)it.next();
						array[i][0]= dq.getQuestion();
						array[i][1]= String.valueOf(dq.getLevel());
						array[i][2]= String.valueOf(dq.getId());
					}
					it = studentEval.getObjectiveQuestions().iterator();
					ObjectiveQuestion oq;
					for(; i<size; i++){
						oq = (ObjectiveQuestion)it.next();
						array[i][0]= oq.getQuestion();
						array[i][1]= String.valueOf(oq.getLevel());
						array[i][2]= String.valueOf(oq.getId());
					}
				}else{
					array = new String[studentEval.getDiscoursiveQuestions().size()][3];
					DiscoursiveQuestion dq;
					for(int i = 0; i<studentEval.getDiscoursiveQuestions().size(); i++){
						dq = (DiscoursiveQuestion)it.next();
						array[i][0]= dq.getQuestion();
						array[i][1]= String.valueOf(dq.getLevel());
						array[i][2]= String.valueOf(dq.getId());
					}
				}
				
				m.put("questions", array);

			}else{
				if(!studentEval.getObjectiveQuestions().isEmpty()){
					m.put("hasQuestions", true);
					String [][] array = new String[studentEval.getObjectiveQuestions().size()][3];
					Iterator it = studentEval.getObjectiveQuestions().iterator();
					ObjectiveQuestion oq;
					for(int i = 0; i<studentEval.getObjectiveQuestions().size(); i++){
						oq = (ObjectiveQuestion)it.next();
						array[i][0]= oq.getQuestion();
						array[i][1]= String.valueOf(oq.getLevel());
						array[i][2]= String.valueOf(oq.getId());
					}
					m.put("questions", array);
					
				}
			}
			
			return m;
		}
		return null;
		
	}
	
	/**
	 * Este método exclui uma avaliação criada pelo professor.
	 * @param id Este id não será utilizado. Apenas para seguir o padrão.
	 * @return
	 */
	public Object[] deleteStudentEvaluation(int evaluationId) {
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		if (studentEval != null) {
			studentEvaluations.remove(evaluationId);
			module.getStudentEvaluations().remove(studentEval);
			amadeus.removeEvaluation(studentEval);
			if (module.getName() != null)
				amadeus.updateModule(module);
		}
		return getActivitiesData();
	}
	
	
	
	/**
	 * Este método salva uma avaliação de aluno criada pelo professor.
	 * @param current indice do módulo em edição.
	 * @return
	 */
	
	public Object[] saveStudentEvaluation(String name, int score,String startDate, String startTime,
			int maxNote, String finishDate, String finishTime,int evaluationId, int current) {
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		init(current);
		User usr = (User)getSessionBean("user");
		Person person = usr.getPerson();
		GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(startDate.substring(6,10)),
				Integer.parseInt(startDate.substring(3,5)) - 1,Integer.parseInt(startDate.substring(0,2)), 
				Integer.parseInt(startTime.substring(0,2)),Integer.parseInt(startTime.substring(3,5)));
		Date evalStartDate = gc.getTime();
		if (evalStartDate.before(course.getInitialCourseDate())) {
			return returnError(getMessage("errors.evaluationStartDateBefore"));
		}
		if (evalStartDate.after(course.getFinalCourseDate())) {
			return returnError(getMessage("errors.evaluationStartDateAfter"));
		}
		
		gc = new GregorianCalendar(Integer.parseInt(finishDate.substring(6,10)),
				Integer.parseInt(finishDate.substring(3,5)) - 1, Integer.parseInt(finishDate.substring(0,2)), 
				Integer.parseInt(finishTime.substring(0,2)),Integer.parseInt(finishTime.substring(3,5)));
		Date evalFinishDate = gc.getTime();
		if (evalFinishDate.before(course.getInitialCourseDate())) {
			return returnError(getMessage("errors.evaluationFinishDateBefore"));
		}
		if (evalFinishDate.after(course.getFinalCourseDate())) {
			return returnError(getMessage("errors.evaluationFinishDateAfter"));
		}
		
		if (evalFinishDate.before(evalStartDate)) {
			return returnError(getMessage("errors.evaluationFinishDateBeforeStartDate"));
		}
		
		Evaluation evaluation = this.studentEvaluations.get(evaluationId);
		boolean wasNull = (evaluation == null);
		
		if (wasNull)
			evaluation = new Evaluation();
		
		evaluation.setAuthor(person);
		evaluation.setBeginning(evalStartDate);
		evaluation.setEnd(evalFinishDate);
		evaluation.setMaxScore(maxNote);
		evaluation.setName(name);
		evaluation.setWeight(score);
		evaluation.setSqmodule(module.getId());
		
		if (wasNull) {
			module.getStudentEvaluations().add(evaluation);
			studentEvaluations.put(studentEvalCounter++, evaluation);
		}
		if (module.getName() != null)
			amadeus.insertEvaluation(evaluation);
			amadeus.updateModule(module);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++SALVOU++++++++++++++++");
		return getActivitiesData();
	}
	
	/**
	 * Este método lista as opções de questões para uma avaliação.
	 * @param current
	 * @return
	 */
	public Map<String, String> listQuestions(int current) {
		Map<String, String> questions = new LinkedHashMap<String, String>();
		Course course = (Course) getSessionBean("course");
		module = course.getModules().get(current - 1);
		String[] values = {
					"questions.select", "questions.discursive",
					"questions.association","questions.blanksfilling", 
					"questions.multChoice",	"questions.trueFalse"
		};
		
		
		
		for (String value : values) {
			questions.put(value.substring(10), getMessage(value));
		}
		return questions;
	}
	public Object[] teste(){
		return getActivitiesData();
	}
	
	/**
	 * Esse método salava uma questão do Verdadeiro ou Salvo no banco.
	 * @param sentences Senteças da questão
	 * @param level nível da questãao
	 * @param evaluationId id da Avaliação
	 * @param questionId o id da Questão (se for uma nova questão a id deverá ser -1)
	 */
	public Map saveTrueFalseQuestion(String[][] sentences, int level, int evaluationId, int questionId){
		Sentence temp;
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		TrueOrFalseQuestion tof = new TrueOrFalseQuestion();
		if(questionId == -1){
			tof.setQuestion("Questão de Verdadeiro ou Falso");
			tof.setLevel(String.valueOf(level).charAt(0));
			for(int i = 1; i < sentences.length; i++){
				temp = new Sentence();
				temp.setDescription(sentences[i][0]);
				if(sentences[i][1].equalsIgnoreCase("1")){
					temp.setValue(true);
				}else{
					temp.setValue(false);
				}
				temp.setId(i);
				temp.setOrder(String.valueOf(i).charAt(0));
				tof.addSentence(temp);
			}
			amadeus.insertObjectiveQuestion_Evaluation(studentEval, tof);
		}else{
			Iterator it = studentEval.getObjectiveQuestions().iterator();
			ObjectiveQuestion ob;
			while(it.hasNext()){
				ob = (ObjectiveQuestion)it.next();
				if(ob.getId() == questionId){
					tof = (TrueOrFalseQuestion)ob;
					tof.setLevel(String.valueOf(level).charAt(0));
					tof.setSentences(new ArrayList<Sentence>());
					for(int i = 1; i < sentences.length; i++){
						temp = new Sentence();
						temp.setDescription(sentences[i][0]);
						if(sentences[i][1].equalsIgnoreCase("1")){
							temp.setValue(true);
						}else{
							temp.setValue(false);
						}
						temp.setId(i);
						temp.setOrder(String.valueOf(i).charAt(0));
						tof.addSentence(temp);
					}
				}
			}
			amadeus.editQuestion(tof);
		}
		amadeus.updateModule(module);
		return this.retrieveStudentEvaluation(evaluationId);
	}
	
	/**
	 * Este método salva uma questão Múltipla Escolha no banco.
	 * @param question
	 * @param alternatives
	 * @param right
	 * @param level
	 * @param evalId
	 * @param questionId
	 * @return
	 */
	public Map saveMultChoiceQuestion(String question, String[] alternatives, int right, int level, int evalId, int questionId){
		Alternative temp;
		Evaluation studentEval = this.studentEvaluations.get(evalId);
		MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
		if(questionId == -1){
			mcq.setLevel(String.valueOf(level).charAt(0));
			mcq.setQuestion(question);
			for(int i = 1; i < alternatives.length; i++){
				temp = new Alternative();
				temp.setDescription(alternatives[i]);
				if(i==right){
					temp.setCorrect(true);
				}else{
					temp.setCorrect(false);
				}
				temp.setOrder(String.valueOf(i).charAt(0));
				mcq.addAlternatives(temp);
			}
			amadeus.insertObjectiveQuestion_Evaluation(studentEval, mcq);
			
		}else{
			Iterator it = studentEval.getObjectiveQuestions().iterator();
			ObjectiveQuestion ob;
			while(it.hasNext()){
				ob = (ObjectiveQuestion)it.next();
				if(ob.getId() == questionId){
					mcq = (MultipleChoiceQuestion)ob;
					mcq.setAlternatives(new ArrayList<Alternative>());
					mcq.setLevel(String.valueOf(level).charAt(0));
					mcq.setQuestion(question);
					for(int i = 1; i < alternatives.length; i++){
						temp = new Alternative();
						temp.setDescription(alternatives[i]);
						if(i==right){
							temp.setCorrect(true);
						}else{
							temp.setCorrect(false);
						}
						temp.setOrder(String.valueOf(i).charAt(0));
						mcq.addAlternatives(temp);
					}
				}
			}
			amadeus.editQuestion(mcq);
		}
		amadeus.updateModule(module);
		return this.retrieveStudentEvaluation(evalId);
	}
	
	/**
	 * Este método salva uma questão de lacunas
	 * @param evaluationId
	 * @param questionId
	 * @return
	 */
	public Map saveBlanksFillingQuestion(int evaluationId, int questionId, int level, String questionText){
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		BlanksFillingQuestion blanksQuestion = new BlanksFillingQuestion();
		
		blanksQuestion.setLevel(String.valueOf(level).charAt(0));
		
		int blankIdCount = 0;
		String textWithBlankSpaces = "";
		for(int i=0; i<questionText.length(); i++){
			if(questionText.charAt(i) == '['){
				i++;
				String temp = "";
				while(questionText.charAt(i) != ']'){
					temp = temp + questionText.charAt(i);
					textWithBlankSpaces = textWithBlankSpaces + " "; 
					i++;
				}
				blankIdCount++;
				Blank b = new Blank();
				b.setWord(temp);
				b.setId(blankIdCount);
				blanksQuestion.addBlanks(b);
			} else {
				textWithBlankSpaces = textWithBlankSpaces + questionText.charAt(i); 
			}
		}
		blanksQuestion.setText(textWithBlankSpaces);
		blanksQuestion.setQuestion("Questão de lacunas");
		
		if(questionId == -1){
			amadeus.insertObjectiveQuestion_Evaluation(studentEval, blanksQuestion);
			amadeus.updateModule(module);
		}else{
			Iterator it = studentEval.getObjectiveQuestions().iterator();
			ObjectiveQuestion ob;
			BlanksFillingQuestion bq = new BlanksFillingQuestion();
			while(it.hasNext()){
				ob = (ObjectiveQuestion)it.next();
				if(ob.getId() == questionId){
					bq = (BlanksFillingQuestion)ob;
					bq.setBlanks(blanksQuestion.getBlanks());
					bq.setLevel(blanksQuestion.getLevel());
					bq.setQuestion(blanksQuestion.getQuestion());
					bq.setText(blanksQuestion.getText());
					
				}
			}
			amadeus.editQuestion(bq);
			amadeus.updateModule(module);
		}
			
		//amadeus.updateModule(module);
		return this.retrieveStudentEvaluation(evaluationId);
	}
	
	
	/**
	 * Este método salva uma questão discursiva no banco
	 * @param evaluationId
	 * @param questionText
	 * @param level
	 * @param questionId
	 * @return
	 */
	public Map saveDiscursiveEvaluation(int evaluationId, String questionText, int level, int questionId){
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		DiscoursiveQuestion discursive = new DiscoursiveQuestion();
		discursive.setLevel(String.valueOf(level).charAt(0));
		discursive.setQuestion(questionText);
		if(questionId==-1){
			amadeus.insertDiscoursiveQuestion_Evaluation(studentEval, discursive);
		}else{
			Iterator it = studentEval.getDiscoursiveQuestions().iterator();
			DiscoursiveQuestion dq = new DiscoursiveQuestion();
			while(it.hasNext()){
				dq = (DiscoursiveQuestion)it.next();
				if(dq.getId() == questionId){
					dq.setLevel(discursive.getLevel());
					dq.setQuestion(discursive.getQuestion());
				}
			}
			amadeus.editQuestion(dq);
		}
		amadeus.updateModule(module);
		return this.retrieveStudentEvaluation(evaluationId);
	}
	
	/**
	 * Este método recupera as informações da questão do banco
	 * @param evaluationId
	 * @param questionId
	 * @return
	 */
	public Map retrieveQuestionData(int evaluationId, int questionId){
		System.out.println("++++++++++  retrieveQuestionData  ++++++++++++");
		Map<String, Object> m = new HashMap<String, Object>();
		Evaluation studentEval = this.studentEvaluations.get(evaluationId);
		Iterator it = studentEval.getObjectiveQuestions().iterator();
		ObjectiveQuestion ob;
		while(it.hasNext()){
			ob = (ObjectiveQuestion)it.next();
			if(ob.getId() == questionId){	
				if (ob instanceof TrueOrFalseQuestion) {
					m.put("type", "trueOrFalse");
					m.put("question", ob.getQuestion());
					Object[] temp = ((TrueOrFalseQuestion)ob).getSentences().toArray();
					String[] question = new String[temp.length];
					boolean[] value = new boolean[temp.length];
					for(int i = 0; i<temp.length;i++){
						question[i] = ((Sentence)temp[i]).getDescription();
						value[i] = ((Sentence)temp[i]).isValue();		
					}
					m.put("value", value);
					m.put("sentences", question);
					m.put("id",questionId);
					m.put("evaluationId", evaluationId);
					m.put("nivel", ob.getLevel());
					return m;
				}
				
				if (ob instanceof BlanksFillingQuestion) {
					m.put("type", "blanksFilling");
				//	m.put("question", ob.getQuestion());
					BlanksFillingQuestion temp = (BlanksFillingQuestion)ob;
					
					m.put("question", temp.getText());
					m.put("id",questionId);
					m.put("evaluationId", evaluationId);
					m.put("nivel", temp.getLevel());
					return m;
				}
				
				if (ob instanceof MultipleChoiceQuestion){
					m.put("type", "multipleChoice");
					m.put("question", ob.getQuestion());
					Object[] temp = ((MultipleChoiceQuestion)ob).getAlternatives().toArray();
					String[] question = new String[temp.length];
					boolean[] value = new boolean[temp.length];
					for(int i = 0; i<temp.length;i++){
						question[i] = ((Alternative)temp[i]).getDescription();
						value[i] = ((Alternative)temp[i]).isCorrect();
					}
					m.put("value", value);
					m.put("alternatives", question);
					m.put("id",questionId);
					m.put("evaluationId", evaluationId);
					m.put("nivel", ob.getLevel());
					return m;
				}				
			}
		}
		it = studentEval.getDiscoursiveQuestions().iterator();
		DiscoursiveQuestion dq;
		while(it.hasNext()){
			dq = (DiscoursiveQuestion)it.next();
			if(dq.getId() == questionId){
				m.put("nivel", dq.getLevel());
				m.put("type", "discursiveQuestion");
				m.put("question", dq.getQuestion());
				m.put("id",questionId);
				m.put("evaluationId", evaluationId);
				return m;
			}
		}
		return null;
	}
	
	/*public Map answerDiscursiveQuestion(int evaluationId, String questionAnswer, int questionId){
		/*Evaluation studentEval = this.studentEvaluations.get(evaluationId);	
		DiscoursiveQuestionAnswer discursiveAnswer = new DiscoursiveQuestionAnswer();
		User usr = (User)getSessionBean("user");
		EvaluationUserAnswer eua = new EvaluationUserAnswer();
		eua.setUser(usr);
		eua.setEvaluation(studentEval);
		eua.setDateAnswer(new Date());
		eua.addDiscoursiveQuestion(discursiveAnswer);		
		discursiveAnswer.setAnswer(questionAnswer);								
		
		amadeus.updateModule(module);
		
		return this.retrieveStudentEvaluation(evaluationId);
	}*/
}
