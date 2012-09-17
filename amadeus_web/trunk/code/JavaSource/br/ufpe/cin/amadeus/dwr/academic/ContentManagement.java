package br.ufpe.cin.amadeus.dwr.academic;

import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getSessionBean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

public class ContentManagement {
	
	private MaterialsDWR materials;
	private ActivitiesDWR activities;
	
	public ContentManagement() {
		materials = getMaterialsDWR();
		activities = getActivitiesDWR();
	}
	
	public Map<String, Object[]> init(int moduleId) {
		Map<String, Object[]> m = new HashMap<String, Object[]>();
		m.put("materials", materials.init(moduleId));
		m.put("activities", activities.init(moduleId));
		return m;
	}
	
	public void clear() {
		ModuleDWR module = new ModuleDWR();
		materials	= new MaterialsDWR();
		activities	= new ActivitiesDWR();
		
		HttpSession session = WebContextFactory.get().getSession();
		session.setAttribute("Module", module);
		session.setAttribute("Materials", materials);
		session.setAttribute("Activities", activities);
	}
	
	
	// ################################################
	
	public MaterialsDWR getMaterialsDWR() {
		MaterialsDWR m = (MaterialsDWR) getSessionBean("Materials");
		if (m == null) {
			m = new MaterialsDWR();
			WebContext wc = WebContextFactory.get();
			wc.getSession().setAttribute("Materials", m);
		}
		return m;
	}

	public ActivitiesDWR getActivitiesDWR() {
		ActivitiesDWR m = (ActivitiesDWR) getSessionBean("Activities");
		if (m == null) {
			m = new ActivitiesDWR();
			WebContext wc = WebContextFactory.get();
			wc.getSession().setAttribute("Activities", m);
		}
		return m;		
	}

}
