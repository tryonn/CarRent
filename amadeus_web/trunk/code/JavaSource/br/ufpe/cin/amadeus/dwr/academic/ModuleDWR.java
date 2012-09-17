package br.ufpe.cin.amadeus.dwr.academic;

import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getSessionBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;
import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;

public class ModuleDWR {

	private AmadeusSystem amadeus;
	private Map<Integer, Module> modules;
	
	public ModuleDWR() {
		System.out.println("CRIOU MODULE");
		amadeus = AmadeusSystem.getInstance();
		modules = new HashMap<Integer, Module>();
		Course c = (Course)getSessionBean("course");
		WebContext wc = WebContextFactory.get();
		wc.getSession().setAttribute("course", c);
	}
	
	public void saveModule(String name, String desc, 
			    boolean visible, int number, int editing) {

		Module module = modules.get(editing);
		boolean wasNull = (module == null);
		boolean firstSave = (wasNull || module.getName() == null);
		
		if (wasNull)
			module = new Module();
		
		module.setName(name);
		module.setDescription(desc);
		module.setVisible(visible);
		module.setOrder(number);
		
		Course course = (Course) getSessionBean("course");
		if (firstSave) {
			amadeus.insertModule(module);
			modules.put(editing, module);
			
			course.getModules().add(module);
			amadeus.updateCourse(course);
		} else {
			amadeus.updateModule(module);
		}
	}
	
	public void deleteModule(int editing) {
		Module module = modules.get(editing);
		Course course = (Course) getSessionBean("course");
		if (module != null) {
			if (module.getName() != null) {
				course.getModules().remove(module);
				amadeus.updateCourse(course);
				amadeus.removeModule(module);
			}
			modules.remove(editing);
		}
	}

	public Map cancelModule(int editing) {
		Map<String, Object> m = new HashMap<String, Object>();
		Module module = modules.get(editing);
		if (module != null && module.getName() != null) {
			m.put("name", module.getName());
			m.put("desc", module.getDescription());
			m.put("visible", module.getVisible());
			m.put("order", module.getOrder());
		} else
			m = null;
		return m;
	}
	
	public Module getModule(int editing) {
		Module m = modules.get(editing);
		if (m == null) {
			m = new Module();
			modules.put(editing, m);
		}
		return m;
	}
	
	public Map reorderModules(int newOrder, int formerOrder) {
		Map<String, Object> result;
		Course c = (Course)getSessionBean("course");
		int size = c.getModules().size();
		List<Module> mods = new ArrayList<Module>(size);
		for (int i = 0; i < size; i++) {
			mods.add(i, modules.get(i + 1));
		}
		Module m = modules.get(formerOrder);
		if ((m == null) || (m.getName() == null)) {
			result = null;
		} else {
			for (int i = 1; i <= size; i++) {
				if (newOrder < formerOrder) {
					if (i >= newOrder && i < formerOrder) {
						m = modules.get(i);
						m.setOrder(i + 1);
						mods.set(i, m);
					} else if (i == formerOrder) {
						m = modules.get(formerOrder);
						m.setOrder(newOrder);
						mods.set(newOrder - 1, m);
					} else 
						mods.set(i - 1, modules.get(i));
					
				} else {
					if (i > formerOrder && i <= newOrder) {
						m = modules.get(i);
						m.setOrder(i - 1);
						mods.set(i - 2, m);
					} else if (i == formerOrder) {
						m = modules.get(formerOrder);
						m.setOrder(newOrder);
						mods.set(newOrder - 1, m);
					} else 
						mods.set(i - 1, modules.get(i));
				}
			}
			amadeus.orderModules(mods);
			c.getModules().clear();
			c.setModules(mods);
			for (int i = 0; i < size; i++) {
				modules.put(i + 1, mods.get(i));
			}
			amadeus.updateCourse(c);
			result = new HashMap<String, Object>(); 
			result.put("newOrder", newOrder);
			result.put("noModules", size);
		}
		return result;
	}
	
	//Used to show the student´s view of the module
	public Map retrieveInfo(int currentModule) {
		Map<String, Object> m = new HashMap<String, Object>();
		Course c = (Course)getSessionBean("course");
		int size = c.getModules().size();
		List<Module> mods = c.getModules();
		for (int i = 0; i < size; i++) {
			modules.put(i + 1, mods.get(i));
		}
		Module module = modules.get(currentModule);
		if (module != null && module.getName() != null) {
			m.put("name", module.getName());
			m.put("description", module.getDescription());
			m.put("visible", module.getVisible());
			m.put("order", module.getOrder());
			m.put("materialDeliveries", module.getHomeworks());
			m.put("polls", module.getPolls());
			m.put("materials", module.getMaterials());
			m.put("foruns", module.getForums());
			if (module.getCourseEvaluation() != null)
			m.put("evaluation", true);
			m.put("studentEvaluation", module.getStudentEvaluations());
			m.put("current", currentModule);
		} else 
			m = null;
		return m;
	}
}
