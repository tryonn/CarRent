package br.ufpe.cin.amadeus.struts.action.academic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import br.ufpe.cin.amadeus.struts.util.CoursePopulator;
import br.ufpe.cin.amadeus.struts.util.CourseSearchManager;
import br.ufpe.cin.amadeus.struts.util.CourseSearchResult;
import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.keyword.Keyword;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.exception.UserRegistrationException;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;


public class CourseActions extends MappingDispatchAction {

	public ActionForward validateLoginCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		DynaValidatorForm dvForm = (DynaValidatorForm)form;

        String login = (String) dvForm.get("login");
        String password = (String) dvForm.get("password");
        
        AmadeusSystem amadeus = AmadeusSystem.getInstance();
        
        User user = amadeus.searchUserByLogin(login);
        if (user == null){
	       	ActionMessages messages = new ActionMessages();
	        messages.add("invalid", new ActionMessage("errors.login.invalid"));
	        saveErrors(request, messages);
	        dvForm.initialize(mapping);
	        return mapping.getInputForward();
        }

        if (user.checkPassword(password)) {
        	request.getSession().setAttribute("user", user);
            return mapping.findForward("success");
        }
        else{
            ActionMessages messages = new ActionMessages();
            messages.add("invalid", new ActionMessage("errors.password.invalid"));
            saveErrors(request, messages);
            dvForm.initialize(mapping);
            return mapping.getInputForward();
        }
	}
	
	public ActionForward validateLoginAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
        AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
        String login = (String) dvForm.get("login");
        String password = (String) dvForm.get("password");
        String action = (String) dvForm.get("action");
        
        User user = amadeus.searchUserByLogin(login);
        if (user == null){
	        messages.add("invalid", new ActionMessage("errors.login.invalid"));
	    } else if (!user.checkPassword(password)) {
        	messages.add("invalid", new ActionMessage("errors.password.invalid"));
        }
        if (!messages.isEmpty()) {
            saveErrors(request, messages);
            dvForm.initialize(mapping);
            return new ActionForward(action);
        }
    	request.getSession().setAttribute("user", user);
        return new ActionForward(action);
	}
	
	public ActionForward insertCourseStepOne(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();

		
		User user = (User)request.getSession().getAttribute("user");
		int personId = user.getPerson().getId();
		CoursePopulator populator = new CoursePopulator(dvForm);
		
		populator.populate(messages, personId);
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.getInputForward();
		}

		Course course = populator.getCourse();
		request.getSession().setAttribute("incompleteCourse", course);
		request.setAttribute("courseId", ""+course.getId());
		return mapping.findForward("success");
	}
	
	public ActionForward insertCourseStepTwo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();

		Integer courseId = (Integer)dvForm.get("courseId");
		String rawKeywords = dvForm.getString("keywords");
		Course course = (Course)request.getSession().getAttribute("incompleteCourse");
		Set<Keyword> keywords = new HashSet<Keyword>();
		
		Scanner scanner = new Scanner(rawKeywords);
		while (scanner.hasNext()) {
			Keyword keyword = new Keyword();
			keyword.setName(scanner.next().trim());
			keyword = amadeus.insertKeyword(keyword);
			keywords.add(keyword);
		}
		if (keywords.size() == 0) {
			ActionMessages messages = new ActionMessages();
			messages.add("empty", new ActionMessage("errors.keywords.empty"));
			saveErrors(request, messages);
			request.setAttribute("courseId", ""+courseId);
			return mapping.getInputForward();
		}
		course.setKeywords(keywords);
		course.setModules(new ArrayList<Module>());
		amadeus.insertCourse(course);
		request.getSession().setAttribute("course", course);
		return mapping.findForward("success");
	}
	
	public ActionForward fEditCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		Course course = amadeus.searchCourse(courseId);
		
		GregorianCalendar gc = new GregorianCalendar();
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		gc.setTime(course.getInitialRegistrationDate());
		data.put("initialRegistrationDay", gc.get(Calendar.DAY_OF_MONTH));
		data.put("initialRegistrationMonth", gc.get(Calendar.MONTH) + 1);
		data.put("initialRegistrationYear", gc.get(Calendar.YEAR));
		gc.setTime(course.getFinalRegistrationDate());
		data.put("finalRegistrationDay", gc.get(Calendar.DAY_OF_MONTH));
		data.put("finalRegistrationMonth", gc.get(Calendar.MONTH) + 1);
		data.put("finalRegistrationYear", gc.get(Calendar.YEAR));
		gc.setTime(course.getInitialCourseDate());
		data.put("initialCourseDay", gc.get(Calendar.DAY_OF_MONTH));
		data.put("initialCourseMonth", gc.get(Calendar.MONTH) + 1);
		data.put("initialCourseYear", gc.get(Calendar.YEAR));
		gc.setTime(course.getFinalCourseDate());
		data.put("finalCourseDay", gc.get(Calendar.DAY_OF_MONTH));
		data.put("finalCourseMonth", gc.get(Calendar.MONTH) + 1);
		data.put("finalCourseYear", gc.get(Calendar.YEAR));
		
		String keywords = "";
		StringBuilder sb = new StringBuilder();
		for (Keyword keyword : course.getKeywords()) {
			sb.append(keyword.getName() + " ");
		}
		if (sb.length() != 0)
			keywords = sb.substring(0, sb.length()-1);
		data.put("keywords", keywords);
		
		request.setAttribute("courseId", courseId);
		request.setAttribute("data", data);
		request.setAttribute("course", course);
		return mapping.findForward("success");
	}
	
	public ActionForward editCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		Integer courseId = (Integer) dvForm.get("id");
		Course course = amadeus.searchCourse(courseId);
		CoursePopulator populator = new CoursePopulator(dvForm, course);
		
		populator.populate(messages);
		Set<Keyword> keywords = new HashSet<Keyword>();

		String rawKeywords = (String) dvForm.get("keywords");
		
		Scanner scanner = new Scanner(rawKeywords);
		while (scanner.hasNext()) {
			Keyword keyword = new Keyword();
			keyword.setName(scanner.next());
			keyword = amadeus.insertKeyword(keyword);
			keywords.add(keyword);
		}
		if (keywords.size() == 0)
			messages.add("empty", new ActionMessage("errors.keywords.empty"));

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			request.setAttribute("courseId", courseId);
			request.setAttribute("data", dvForm);
			request.setAttribute("course", dvForm);
			return mapping.getInputForward();
		}

		amadeus.removeKeywordFromCourse(course);
		
		course.getKeywords().addAll(keywords);
		amadeus.updateCourse(course);
		return mapping.findForward("success");
	}
	
	public ActionForward searchCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		List<CourseSearchResult> results[] =
			new ArrayList[3];

		List<Course>[] result = null;
		String criteria = (String) dvForm.get("course");
		String searchType = (String) dvForm.get("type");
		
		if (searchType.equals("keyword")) {
			result = amadeus.searchCourseByKeyword(criteria);
		}else if (searchType.equals("rule")) {
			result = amadeus.searchCourseByRule(criteria);
		}
		for (int i = 0; i < 3; i++) {
			results[i] = new ArrayList();
			for (Course course : result[i]) {
				CourseSearchResult resultSearch = new CourseSearchResult();
				resultSearch.setCourseId(course.getId());
				resultSearch.setCourseName(course.getName());
					
				int no = amadeus.countStudentsByCourse(course);
				resultSearch.setNoStudents(no);
					
				User author = amadeus.searchUser(course.getIdAuthor());
				resultSearch.setProfessorId(author.getId());
				resultSearch.setProfessorName(author.getPerson().getName());
				results[i].add(resultSearch);
			}
		}
		
		HttpSession session = request.getSession();
		CourseSearchManager csm = new CourseSearchManager(results, criteria);
		session.setAttribute("csm", csm);
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			HashMap<String, String> hash = new HashMap<String, String>();
			hash.put("action", "/searchCourse.do?" +
					"type="+ searchType+"&course="+criteria);
			request.setAttribute("hash", hash);
			return mapping.findForward("notLogged");
		}
		return mapping.findForward("logged");
	}
	
	public ActionForward iterateSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		HttpSession session = request.getSession();
		String page = request.getParameter("page");
		
		CourseSearchManager csm;
		csm = (CourseSearchManager) session.getAttribute("csm");
		csm.update(page);
		
		request.setAttribute("noResults", csm.getNoResults());
		request.setAttribute("criteria", csm.getCriteria());
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			HashMap<String, String> hash = new HashMap<String, String>();
			hash.put("action", "/iterateSearch.do?page="+page);
			request.setAttribute("hash", hash);
			return mapping.findForward("notLogged");
		}
		return mapping.findForward("logged");
	}
	
	public ActionForward viewCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		String courseId = request.getParameter("courseId");
		request.setAttribute("courseId", Integer.parseInt(courseId));
		
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			HashMap<String, String> hash = new HashMap<String, String>();
			hash.put("action", "/viewCourse.do?courseId="+courseId);
			request.setAttribute("hash", hash);
			return mapping.findForward("notLogged");
		}
		return mapping.findForward("logged");
	}
	
	public ActionForward register(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		int id = (Integer)dvForm.get("id");
		Course course = amadeus.searchCourse(id);
		User user = (User)request.getSession().getAttribute("user");
		try {
			amadeus.registerUser(user, course);
		} catch (UserRegistrationException mue) {
			request.setAttribute("courseId", id);
			ActionMessages messages = new ActionMessages();
        	messages.add("registrationError", new ActionMessage(mue.getMessage()));
        	saveErrors(request, messages);
			return mapping.getInputForward();
		}
		request.setAttribute("enrolled", "enrolled");
		request.setAttribute("courseId", id);
		return mapping.findForward("success");
	}
	
	public ActionForward listParticipants(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		String courseId = request.getParameter("courseId");
		Course course = amadeus.searchCourse(Integer.parseInt(courseId));
		
		List<User> participants = amadeus.listStudentsByCourse(course);
		request.setAttribute("participants", participants);
		List<User> assistants = amadeus.listAssistantsByCourse(course);
		request.setAttribute("assistants", assistants);
		List<User> teachers = amadeus.listTeachersByCourse(course);
		request.setAttribute("teachers", teachers);
		
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("course", course);
		if (user == null) {
			HashMap<String, String> hash = new HashMap<String, String>();
			hash.put("action", "/listParticipants.do?courseId="+courseId);
			request.setAttribute("hash", hash);
			return mapping.findForward("notLogged");
		}
		return mapping.findForward("logged");
	}
}
