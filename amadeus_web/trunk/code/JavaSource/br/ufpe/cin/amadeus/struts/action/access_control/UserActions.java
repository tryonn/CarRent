
package br.ufpe.cin.amadeus.struts.action.access_control;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import br.ufpe.cin.amadeus.struts.util.CPFValidator;
import br.ufpe.cin.amadeus.struts.util.DateValidator;
import br.ufpe.cin.amadeus.struts.util.FieldValidator;
import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.exception.ImageFormatException;
import br.ufpe.cin.amadeus.system.exception.ImageSizeException;
import br.ufpe.cin.amadeus.system.exception.RequestException;
import br.ufpe.cin.amadeus.system.exception.SendMailException;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;
import br.ufpe.cin.amadeus.system.human_resources.image.Image;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.system.human_resources.resume.Resume;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest;
import br.ufpe.cin.amadeus.util.Constants;

public class UserActions extends MappingDispatchAction {

	
	public ActionForward validateLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		DynaValidatorForm dvForm = (DynaValidatorForm)form;

        String login = (String) dvForm.get("login");
        String password = (String) dvForm.get("password");
        
        AmadeusSystem amadeus = AmadeusSystem.getInstance();
        
        User user = amadeus.searchUserByLogin(login);
        if (user == null){
	       	ActionMessages messages = new ActionMessages();
	        messages.add("invalid", new ActionMessage("errors.auth.invalid"));
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
            messages.add("invalid", new ActionMessage("errors.auth.invalid"));
            saveErrors(request, messages);
            dvForm.initialize(mapping);
            request.getSession().removeAttribute("user");
            return mapping.getInputForward();
        }
	}
	
	public ActionForward editPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;

        String currentPassword = (String) dvForm.get("currentPassword");
        String newPassword = (String) dvForm.get("newPassword");
        String newPasswordConfirmation = (String) dvForm.get("newPasswordConfirmation");
        
        AmadeusSystem amadeus = AmadeusSystem.getInstance();
        User loggedUser = (User)request.getSession().getAttribute("user");
        
        if (!loggedUser.getPassword().equals(currentPassword))
	        messages.add("invalid", new ActionMessage("errors.password.invalid"));
        if (!newPassword.equals(newPasswordConfirmation))
	        messages.add("invalid", new ActionMessage("errors.confirmation.invalid"));
        
		if (!messages.isEmpty()) {
			dvForm.initialize(mapping);
			saveErrors(request, messages);
			return mapping.getInputForward();
		}
        
        loggedUser.setPassword(newPassword);
        amadeus.updateUser(loggedUser);
        return mapping.findForward("success");
	}
	
	public ActionForward signOut(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

	      	request.getSession().invalidate();
            return mapping.findForward("success");
    }
	
	public ActionForward insertUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

   	 	ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
        AmadeusSystem amadeus = AmadeusSystem.getInstance();
        
        Person person = new Person();
        person.setName((String) dvForm.get("name"));
        person.setEmail((String) dvForm.get("email"));
        
        String login = (String) dvForm.get("login");
        String password = (String) dvForm.get("password");
        String passwordConfirmation = (String) dvForm.get("passwordConfirmation");
        
        User user = new User();
        Resume resume = new Resume();
                
        user.setLogin(login);
        user.setPassword(password);
        user.setPerson(person);
        resume.setPerson(person);
        
        if (amadeus.searchUserByLogin(user.getLogin()) != null)
        	messages.add("confirmation", new ActionMessage("errors.login.alreadyExists"));
        if (user.getLogin().indexOf(" ") != -1)
        	messages.add("confirmation", new ActionMessage("errors.login.withSpace"));
        if (amadeus.searchUserByEmail(user.getPerson().getEmail()) != null)
        	messages.add("confirmation", new ActionMessage("errors.email.alreadyExists"));
        if (!passwordConfirmation.equals(password))
        	messages.add("confirmation", new ActionMessage("errors.confirmation.invalid"));
        
        if (!messages.isEmpty()) {
        	saveErrors(request, messages);
        	dvForm.set("password", "");
        	dvForm.set("passwordConfirmation", "");
        	return mapping.getInputForward();
        }
        amadeus.insertUser(user);
        amadeus.insertResume(resume);
        amadeus.confirmRegistry(user);
        request.getSession().setAttribute("user", user);
        return mapping.findForward("success");
	}	

	public ActionForward editUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		User user = (User)request.getSession().getAttribute("user");
		
		Integer yearDegree = (Integer) dvForm.get("yearDegree");

		Date today = new Date();
		if ((today.getYear() + 1900) < yearDegree) {
			messages.add("invalid", new ActionMessage("errors.grad.year"));
	    }
		String email = (String) dvForm.get("email");
		if ((amadeus.searchUserByEmail(email) != null) &&
				(!user.getPerson().getEmail().equals(email)))
        	messages.add("confirmation", new ActionMessage("errors.email.alreadyExists"));
		
		String cpf = (String) dvForm.get("cpf");
		String cpfDigit = (String) dvForm.get("cpfDigit");
		
		String day = (String) dvForm.get("day");
		String month = (String) dvForm.get("month");
		String year = (String) dvForm.get("year");
		
		String name = (String) dvForm.get("name");
		String phoneDDD = (String) dvForm.get("phoneDDD");
		String phone = (String) dvForm.get("phone");
		String city = (String) dvForm.get("city");
		String state = (String) dvForm.get("state");
		FormFile picture = (FormFile) dvForm.get("picture");
		String degree = (String) dvForm.get("degree");
		
		String institution = (String) dvForm.get("institution");
		String curriculum = (String) dvForm.get("curriculum");
		
		String sexString = (String)dvForm.get("gender");
		if (sexString != null && sexString.length() != 0) {
			user.getPerson().setGender(sexString.charAt(0));			
		}

		Image image = new Image();
		image.setPhoto(picture.getFileData());
		image.setPerson(user.getPerson());
		
		FieldValidator fValidator = new FieldValidator(messages);
		CPFValidator cValidator = new CPFValidator(messages);
		DateValidator dValidator =
			new DateValidator(messages, day, month, year, false);
		
		fValidator.validatePhone(phoneDDD, phone);
		fValidator.validateSSN(cpf, cpfDigit);
		
		fValidator.validateCity(city);
		fValidator.validateInstitution(institution);
		fValidator.validateCurriculum(curriculum);
		
		cValidator.checkCPF(cpf + cpfDigit);
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			request.setAttribute("userData", dvForm);
			return mapping.getInputForward();
		}

		String completePhone = null;
		if (phoneDDD.length() != 0)
			completePhone = "(" + phoneDDD + ")" + phone;
		user.getPerson().setCpf(cpf + cpfDigit);
		user.getPerson().setPhoneNumber(completePhone);
		user.getPerson().setBirthDate(dValidator.getDate());
		user.getPerson().setEmail(email);
		user.getPerson().setCity(city);
		user.getPerson().setState(state);
		user.getPerson().setName(name);
		
		if (yearDegree == 0)	yearDegree = null;
		
		Resume resume = amadeus.searchResume(user.getPerson());
		resume.setDegree(degree);
		resume.setYear(yearDegree);
		resume.setInstitution(institution);
		resume.setDescription(curriculum);
		
		amadeus.updateUser(user);
		if (picture.getFileSize() != 0) {
			try {
				amadeus.insertImage(image);
			} catch (ImageFormatException e) {
				messages.add("invalid", new ActionMessage("errors.invalidPicture"));
				saveErrors(request, messages);
				request.setAttribute("userData", dvForm);
				return mapping.getInputForward();
			} catch (ImageSizeException e){
				messages.add("invalid", new ActionMessage("errors.invalidPictureSize"));
				saveErrors(request, messages);
				request.setAttribute("userData", dvForm);
				return mapping.getInputForward();
			}
		}
		picture.destroy();
		amadeus.updateResume(resume);
		return mapping.findForward("success");
	}
	
	public ActionForward fEditUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		HashMap<String, Object> userData = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Person person = user.getPerson();
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		Resume resume = amadeus.searchResume(person);
		
		userData.put("name", person.getName());
		userData.put("email", person.getEmail());
		userData.put("gender", Character.toString(person.getGender()));
		userData.put("city", person.getCity());
		userData.put("state", person.getState());
		
		String cpf = person.getCpf();
		if (cpf != null && cpf.length() != 0) {
			userData.put("cpf", cpf.substring(0, 9));
			userData.put("cpfDigit", cpf.substring(9));
		}

		String phone = person.getPhoneNumber();
		if (phone != null && phone.length() != 0) {
			int left = phone.indexOf('(');
			int right = phone.indexOf(')');
			if ((right + 1) != phone.length())
				userData.put("phone", phone.substring(right+1));
			userData.put("phoneDDD", phone.substring(left+1, right)); 
		}
		
		Date birthDate = person.getBirthDate();
		if (birthDate != null) {
			Calendar gc = new GregorianCalendar();
			gc.setTime(birthDate);
			
			userData.put("day", gc.get(Calendar.DAY_OF_MONTH));
			userData.put("month", gc.get(Calendar.MONTH) + 1);
			userData.put("year", gc.get(Calendar.YEAR));
		}
		
		String degree = resume.getDegree(); 
		Integer yearDegree = resume.getYear(); 
		String institution = resume.getInstitution();
		String curriculum = resume.getDescription();	
			
		userData.put("degree", degree);
		userData.put("yearDegree", yearDegree);
		userData.put("institution", institution);
		userData.put("curriculum", curriculum);

		request.setAttribute("userData", userData);
		return mapping.findForward("success");
	}
	
	public ActionForward remindPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		String email = (String) dvForm.get("email");

        try {
        	amadeus.remindPassword(email);		
        } catch (SendMailException sme) {
        	ActionMessages messages = new ActionMessages();
        	messages.add("invalid", new ActionMessage(sme.getMessage()));
	        saveErrors(request, messages);
	        dvForm.initialize(mapping);
	        return mapping.getInputForward();
        }
        return mapping.findForward("success");
	}
	
	
	public ActionForward teachingRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		ActionMessages messages = new ActionMessages();
    	DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		User user = (User) request.getSession().getAttribute("user");
		
		String degree = (String) dvForm.get("degree");
		Integer year = (Integer) dvForm.get("year");
		Date today = new Date();
		if ((today.getYear() + 1900) < year) {
			request.setAttribute("resume", dvForm);
			messages.add("requestError", new ActionMessage("errors.grad.year"));
			saveErrors(request, messages);
	        return mapping.getInputForward();
	    }
		String institution = (String) dvForm.get("institution");
		String description = (String) dvForm.get("description");
		String interest = (String) dvForm.get("interest");
		
		UserRequest userRequest = new UserRequest();
		userRequest.setPerson(user.getPerson());
		userRequest.setUserRequestDate(new Date());
		userRequest.setInterest(interest);
		userRequest.setTeachingRequest(true);
		
		Resume resume = amadeus.searchResume(user.getPerson());
		resume.setDegree(degree);
		resume.setYear(year);
		resume.setInstitution(institution);
		resume.setDescription(description);
		
		amadeus.updateResume(resume);
		try {
			amadeus.requestTeacherProfile(userRequest);
		} catch (RequestException re) {
        	messages.add("requestError", new ActionMessage(re.getMessage()));
	        saveErrors(request, messages);
	        return mapping.getInputForward();
		}
		return mapping.findForward("success");
	}
	
	public ActionForward assistanceRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		ActionMessages messages = new ActionMessages();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		User user = (User) request.getSession().getAttribute("user");
		
		Integer year = (Integer) dvForm.get("year");
		Date today = new Date();
		if ((today.getYear() + 1900) < year) {
			request.setAttribute("resume", dvForm);
			messages.add("requestError", new ActionMessage("errors.grad.year"));
			saveErrors(request, messages);
	        return mapping.getInputForward();
	    }
		String degree = (String) dvForm.get("degree");
		String institution = (String) dvForm.get("institution");
		String description = (String) dvForm.get("description");
		String interest = (String) dvForm.get("interest");
		Integer courseId = (Integer) dvForm.get("courseId");
		Course course = amadeus.searchCourse(courseId);
		request.setAttribute("courseId", courseId);
		
		UserRequest userRequest = new UserRequest();
		userRequest.setPerson(user.getPerson());
		userRequest.setCourse(course);
		userRequest.setUserRequestDate(new Date());
		userRequest.setInterest(interest);
		userRequest.setTeachingRequest(false);
		
		Resume resume = amadeus.searchResume(user.getPerson());
		resume.setDegree(degree);
		resume.setYear(year);
		resume.setInstitution(institution);
		resume.setDescription(description);
		
		amadeus.updateResume(resume);
		try {
			amadeus.requestAssistance(userRequest);
		} catch (RequestException re) {
        	messages.add("requestError", new ActionMessage(re.getMessage()));
	        saveErrors(request, messages);
	        return mapping.getInputForward();
		}
		return mapping.findForward("success");
	}
	
	public ActionForward viewPendingTasks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		User user = (User) request.getSession().getAttribute("user");
		int usrProfile = user.getProfile().getConstantProfile();
		
		if (usrProfile == Constants.ADMIN) {
			List<UserRequest> requests = amadeus.getPossibleTeachers();
			request.setAttribute("requests", requests);
			return mapping.findForward("admin");
		}
		if (usrProfile == Constants.PROFESSOR) {
			List<UserRequest> requests = amadeus.getPossibleTeacherAssistants(user);
			request.setAttribute("requests", requests);
			return mapping.findForward("teacher");
		}
		if (usrProfile == Constants.STUDENT) {
			return mapping.findForward("student");
		}
		if (usrProfile == Constants.MONITOR) {
			return mapping.findForward("monitor");
		}
		return null;
	}
	
	public ActionForward myProfile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("userId", user.getId());
		return mapping.findForward("success");
	}

	public ActionForward userPrivateData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {
		
		String userId = request.getParameter("userId");
		request.setAttribute("userId", Integer.parseInt(userId));
		return mapping.findForward("success");
	}

}