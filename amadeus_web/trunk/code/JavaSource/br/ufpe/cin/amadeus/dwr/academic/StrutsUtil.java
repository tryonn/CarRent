package br.ufpe.cin.amadeus.dwr.academic;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;

public class StrutsUtil {
	public static Object getSessionBean(String key) {
		WebContext wc = WebContextFactory.get();
		return wc.getSession().getAttribute(key);
	}// getSessionBean

	public static Object getRequestBean(String key) {
		WebContext wc = WebContextFactory.get();
		return wc.getHttpServletRequest().getAttribute(key);
	}// getRequestBean

	private static Locale getSessionLocale() {
		return (Locale) getSessionBean(Globals.LOCALE_KEY);
	}// getSessionLocale

	private static MessageResources getMessageResources() {
		WebContext wc = WebContextFactory.get();
		ServletContext sc = wc.getServletContext();
		return (MessageResources) sc.getAttribute(Globals.MESSAGES_KEY);
	}// getMessageResources

	public static String getMessage(String key) {
		return getMessageResources().getMessage(getSessionLocale(), key);
	}// getMessage

	public static String getMessage(String key, Object[] args) {
		return getMessageResources().getMessage(getSessionLocale(), key, args);
	}// getMessage
	
	public static ModuleDWR getModuleDWR() {
		ModuleDWR m = (ModuleDWR) getSessionBean("Module");
		if (m == null) {
			m = new ModuleDWR();
			WebContext wc = WebContextFactory.get();
			wc.getSession().setAttribute("Module", m);
		}
		return m;
	}// getModuleDWR
}
