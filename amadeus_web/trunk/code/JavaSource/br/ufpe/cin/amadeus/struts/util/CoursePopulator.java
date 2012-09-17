package br.ufpe.cin.amadeus.struts.util;

import java.util.Date;
import java.util.HashSet;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

import br.ufpe.cin.amadeus.system.academic.area.Area;
import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.tool.Tool;

public class CoursePopulator {

	private Course course;
	private DynaValidatorForm dvForm;
	private boolean newInstance;
	
	public CoursePopulator(DynaValidatorForm dvForm) {
		this(dvForm, new Course());
		this.newInstance = true;
	}

	public CoursePopulator(DynaValidatorForm dvForm, Course course) {
		this.dvForm = dvForm;
		this.course = course;
	}

	public Course getCourse() {
		return this.course;
	}
	
	public void populate(ActionMessages messages) {
		populate(messages, 0);
	}
	
	public void populate(ActionMessages messages, int personId) {
		String name = (String) dvForm.get("name");
		String objectives = (String) dvForm.get("objectives");
		String content = (String) dvForm.get("content");
		int students = (Integer)dvForm.get("students");
		if (students <= 0) 
			messages.add("invalidDate", new ActionMessage("errors.invalidNumberStudents"));
		String initialRegistrationDay = (String) dvForm.get("initialRegistrationDay");
		String initialRegistrationMonth = (String) dvForm.get("initialRegistrationMonth");
		String initialRegistrationYear = (String) dvForm.get("initialRegistrationYear");
		String finalRegistrationDay = (String) dvForm.get("finalRegistrationDay");
		String finalRegistrationMonth = (String) dvForm.get("finalRegistrationMonth");
		String finalRegistrationYear = (String) dvForm.get("finalRegistrationYear");
		String initialCourseDay = (String) dvForm.get("initialCourseDay");
		String initialCourseMonth = (String) dvForm.get("initialCourseMonth");
		String initialCourseYear = (String) dvForm.get("initialCourseYear");
		String finalCourseDay = (String) dvForm.get("finalCourseDay");
		String finalCourseMonth = (String) dvForm.get("finalCourseMonth");
		String finalCourseYear = (String) dvForm.get("finalCourseYear");
		
				
		DateValidator ir = new DateValidator(messages,
				initialRegistrationDay, initialRegistrationMonth, initialRegistrationYear, true);
		DateValidator fr = new DateValidator(messages,
				finalRegistrationDay, finalRegistrationMonth, finalRegistrationYear, true);
		DateValidator ic = new DateValidator(messages,
				initialCourseDay, initialCourseMonth, initialCourseYear, true);
		DateValidator fc = new DateValidator(messages,
				finalCourseDay, finalCourseMonth, finalCourseYear, true);
		
		if (!messages.isEmpty())
			return;
		
		Date today = new Date();
		today.setDate(today.getDate() - 1);
		Date initReg = ir.getDate();
		Date finalReg = fr.getDate();
		Date initCourse = ic.getDate();
		Date finalCourse = fc.getDate();
		if (newInstance && initReg.before(today))
			messages.add("invalidDate", new ActionMessage("errors.todayRegistrationDate"));
		else if (initReg.after(finalReg))
			messages.add("invalidDate", new ActionMessage("errors.initialRegistrationDate"));
		else if (initCourse.after(finalCourse))
			messages.add("invalidDate", new ActionMessage("errors.initialCourseDate"));
		else if (finalReg.after(initCourse))
			messages.add("invalidDate", new ActionMessage("errors.finalRegistrationDate"));
		
		if (!messages.isEmpty())
			return;
		
		course.setName(name);
		course.setObjectives(objectives);
		course.setContent(content);
		course.setStudents(students);
		course.setInitialRegistrationDate(initReg);
		course.setFinalRegistrationDate(finalReg);
		course.setInitialCourseDate(initCourse);
		course.setFinalCourseDate(finalCourse);
		
		if (newInstance) {
			course.setIdAuthor(personId);
			course.setCreationDate(new Date());
			course.setArea(new Area());
			course.setTools(new HashSet<Tool>());
		}
	}
}
