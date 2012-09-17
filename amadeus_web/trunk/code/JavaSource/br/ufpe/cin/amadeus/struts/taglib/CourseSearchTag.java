package br.ufpe.cin.amadeus.struts.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import br.ufpe.cin.amadeus.struts.util.CourseSearchManager;
import br.ufpe.cin.amadeus.struts.util.CourseSearchResult;

public class CourseSearchTag extends StrutsTagSupport {

	private String manager;
	private CourseSearchManager csm;
	private List<CourseSearchResult>[] csr;
	
	public int doStartTag() {
		try {
			this.csm = (CourseSearchManager) getSessionBean(manager);
			this.csr = csm.getCsr();
			this.header();
			this.list();
		} catch (Exception e) {
			System.out.println("Error in CourseSearchTag: " + e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return (SKIP_BODY);
	}
	 
	private void header() throws IOException {
		JspWriter out = pageContext.getOut();
		out.println("<dl class=\"insert2\">");
		
		String msg = getMessage("listCourses.thereAre");
		msg += " " + csm.getNoResults() + " ";
		msg += getMessage("listCourses.results");
		msg += " <em>&quot;" + csm.getCriteria() + "&quot;</em> :";
		out.println("<dt>" + msg + "</dt>");
		
	}
	
	private void list() throws IOException {
		JspWriter out = pageContext.getOut();
		int firstCourse = csm.getFirstCourse() + 1;
		if (csm.getLastCourse() == 0) {
			firstCourse = 0;
		}
		String msg = getMessage("listCourses.showing");
		msg += " " + firstCourse + " - " + csm.getLastCourse();
		out.println("<dt>" + msg + "</dt>");
		out.println("</dl>");
		out.println("<ul id=\"resultbusca\">");
		int courseCounter = csm.getFirstCourse();
		int courseArrayIndex = csm.findArrayIndex(courseCounter);
		boolean firstTime = true;
		for (CourseSearchResult csr : csm.getCurrentList()) {
			if (firstTime && courseArrayIndex == 0) {
				firstTime = false;
				msg = "";
				msg = getMessage("listCourses.inRegistrationPeriod");
				out.println("<dl><dt>" + msg + "</dt></dl>");
			} else if ( (firstTime && courseArrayIndex == 1) || (this.csr[0].size() == courseCounter) ) {
				firstTime = false;
				msg = "";
				msg = getMessage("listCourses.inClassPeriod");
				out.println("<br/><dl><dt>" + msg + "</dt></dl><br/>");
			} else if ( (firstTime && courseArrayIndex == 2) || ( (this.csr[0].size() + this.csr[1].size()) == courseCounter) ) {
				firstTime = false;
				msg = "";
				msg = getMessage("listCourses.inFinishedClassPeriod");
				out.println("<br/><dl><dt>" + msg + "</dt></dl><br/>");
			}
			out.print("<li><h3>");
			out.print("<a href=\"viewCourse.do?courseId=");
			out.print(csr.getCourseId() + "\">");
			out.print(csr.getCourseName() + "</a></h3>");
			out.print("<a href=\"userPublicData.do?userId=");
			out.print(csr.getProfessorId() + "\">");
			out.print(csr.getProfessorName() + "</a>, ");
			out.print(csr.getNoStudents() + " ");
			out.print(getMessage("listCourses.students") + ".");
			out.println("</li>");
			courseCounter++;
		}
		if (this.csm.hasMoreThanOnePage()) {
			this.paging();
		}
		out.println("</ul>");
	}
	
	private void paging() throws IOException {
		JspWriter out = pageContext.getOut();
		out.print("<br />");
		linker("&lt;&lt;", csm.FIRST, 1);
		linker("&lt;", csm.PREVIOUS, 1);
		for (int i : csm.getPageIndexes()) {
			linker(i + "", i + "", i);
		}
		linker("&gt;", csm.NEXT, csm.getLastPageIndex());
		linker("&gt;&gt;", csm.LAST, csm.getLastPageIndex());
		out.println();
	}
	
	private void linker(String str, String page, int index) throws IOException {
		JspWriter out = pageContext.getOut();
		if (csm.getPageIndex() != index)
			out.print("<a href=\"iterateSearch.do?page=" + page + "\">");
		out.print(str);
		if (csm.getPageIndex() != index)
			out.print("</a>");
		out.print("&nbsp;&nbsp;");
	}
	
	public void setManager(String manager) {
		this.manager = manager;
	}
}
