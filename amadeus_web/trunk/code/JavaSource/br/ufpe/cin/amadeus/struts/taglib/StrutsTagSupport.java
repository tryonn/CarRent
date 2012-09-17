package br.ufpe.cin.amadeus.struts.taglib;

import java.util.Locale;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

public abstract class StrutsTagSupport extends TagSupport {

	public int doEndTag() {
		return EVAL_PAGE;
	}// doEndTag

	protected Object getSessionBean(String key) {
		return pageContext.getSession().getAttribute(key);
	}// getSessionBean

	protected Object getRequestBean(String key) {
		return pageContext.getRequest().getAttribute(key);
	}// getRequestBean

	private Locale getSessionLocale() {
		return (Locale) getSessionBean(Globals.LOCALE_KEY);
	}// getSessionLocale

	private MessageResources getMessageResources() {
		return (MessageResources) pageContext.getAttribute(
				Globals.MESSAGES_KEY, PageContext.APPLICATION_SCOPE);
	}// getMessageResources

	protected String getMessage(String key) {
		return getMessageResources().getMessage(getSessionLocale(), key);
	}// getMessage

	protected String getMessage(String key, Object[] args) {
		return getMessageResources().getMessage(getSessionLocale(), key, args);
	}// getMessage

}//class StrutsTagSupport