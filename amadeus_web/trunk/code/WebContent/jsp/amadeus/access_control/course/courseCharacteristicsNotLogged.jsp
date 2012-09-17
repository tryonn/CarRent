<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.system.academic.course.Course" %>
<%@ page import="br.ufpe.cin.amadeus.system.academic.keyword.Keyword" %>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	int courseId = (Integer)request.getAttribute("courseId");

	Course course = amadeus.searchCourse(courseId);
	List<User> teachers = amadeus.listTeachersByCourse(course);
	List<User> assistants = amadeus.listAssistantsByCourse(course);
	Set<Keyword> keywords = course.getKeywords();
	request.setAttribute("course", course);
	request.setAttribute("teachers", teachers);
	request.setAttribute("assistants", assistants);
	request.setAttribute("keywords", keywords);
	request.setAttribute("size", keywords.size());
	String courseName = course.getName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="courseForm.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="all">
		<div id="header">
				<div id="login">
					<dl id="formlogin">
						<html:errors property="invalid"/>
						<html:form action="/validateLogin" focus="login" onsubmit="return validateLogonActionForm(this);">
						<html:hidden property="action" name="hash"/>
				  		<dt><html:text property="login"  styleClass="inputlogin" size="15" maxlength="15" value="login"/></dt>
		      			<dt><html:password property="password" styleClass="inputlogin" size="15" maxlength="15" value="password"/>&nbsp;
							<html:submit styleClass="button"><bean:message key="welcome.button.submit"/></html:submit></dt>
			            <dt><html:link forward="fInsertUser" styleClass="insert"><bean:message key="userRegistrationForm.heading"/></html:link><br/></dt>
			       		</html:form>
			       	</dl>
				</div>
        </div>
		<div id="institutional_menu">
			<ul>
				<li><html:link forward="fProject"><bean:message key="institutionalLinks.project"/></html:link></li>
<!-- 				<li><html:link forward="fCases"><bean:message key="institutionalLinks.cases"/></html:link></li>-->
				<li><html:link forward="fCCTE"><bean:message key="institutionalLinks.ccte"/></html:link></li>
				<li><html:link forward="fReachUs"><bean:message key="institutionalLinks.reachUs"/></html:link></li>
				<li><html:link forward="fLicense"><bean:message key="institutionalLinks.license"/></html:link></li>
			</ul>
		</div>
		<div id="page_title">
			<h2><bean:message key="courseCharacteristics.heading"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fWelcome"><bean:message key="menu.name"/></html:link></li>
				<li><%=courseName%></li>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
					<ul id="context_menu">
					</ul>
				</dd>
			</dl>
		  </div>
		  <div id="content">
			 <dl class="insert2">
					<html:hidden property="id" name="course"/>
					<dt><bean:message key="courseCharacteristics.courseName"/></dt>
						<dd class="showDataForm"><bean:write property="name" name="course" /></dd>

					<dt><bean:message key="courseCharacteristics.teachers"/></dt>
					<logic:iterate id="teacher" name="teachers">
						<bean:define id="person" name="teacher" property="person"/>
						<dd class="showDataForm"><bean:write name="person" property="name"/></dd>
					</logic:iterate>
					
					<logic:notEmpty name="assistants">
					<dt><bean:message key="courseCharacteristics.assistants"/></dt>
					<logic:iterate id="assistant" name="assistants">
						<bean:define id="person" name="assistant" property="person"/>
						<dd class="showDataForm"><bean:write name="person" property="name"/></dd>
					</logic:iterate>
					</logic:notEmpty>
					
					<dt><bean:message key="courseCharacteristics.objectives"/></dt>
						<dd class="showDataForm"><bean:write property="objectives" name="course" /></dd>
					<dt><bean:message key="courseCharacteristics.content"/></dt>
						<dd class="showDataForm"><bean:write property="content" name="course" /></dd>
					<dt><bean:message key="courseCharacteristics.students"/></dt>
						<dd class="showDataForm"><bean:write property="students" name="course" /></dd>
					<dt><bean:message key="courseCharacteristics.initialRegistrationDate"/></dt>
						<dd class="showDataForm"><bean:write property="initialRegistrationDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.finalRegistrationDate"/></dt>
						<dd class="showDataForm"><bean:write property="finalRegistrationDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.initialCourseDate"/></dt>
						<dd class="showDataForm"><bean:write property="initialCourseDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.finalCourseDate"/></dt>
						<dd class="showDataForm"><bean:write property="finalCourseDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.keywords"/></dt>
					<dd class="showDataForm">
					<logic:iterate id="keyword" name="keywords" indexId="count" type="br.ufpe.cin.amadeus.system.academic.keyword.Keyword">
					<a href="searchCourse.do?type=keyword&course=<%=keyword.getName()%>" class="<%="keywork" + keyword.getGroup()%>">
					<%=keyword.getName()%></a><%
						
							if (count < keywords.size() - 1)	out.write(", ");
						
					%></logic:iterate></dd>
			 </dl>
		 </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
		
	<html:javascript formName="logonActionForm"/>
	</body>
</html:html>
