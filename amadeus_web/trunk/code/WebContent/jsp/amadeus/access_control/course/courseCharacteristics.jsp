<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.system.academic.course.Course" %>
<%@ page import="br.ufpe.cin.amadeus.system.academic.keyword.Keyword" %>
<%@ page import="br.ufpe.cin.amadeus.util.Constants"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fCourseCharacteristicsNotLogged"/>			
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User)session.getAttribute("user");
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
		<title><bean:message key="courseForm.view"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="all">
		<div id="header">
				<div id="login">
				<bean:message key="menu.greeting"/>
				<bean:define id="u_person" name="user" property="person"/>
				<html:link action="myProfile"><bean:write name="u_person" property="name"/></html:link>.<br/>
				<html:link forward="signOut"><bean:message key="general.leave"/></html:link><br/>
	            </div> 
        </div>
		<div id="institutional_menu">
			<ul>
				<li><html:link forward="fProject"><bean:message key="institutionalLinks.project"/></html:link></li>
				<!-- <li><html:link forward="fCases"><bean:message key="institutionalLinks.cases"/></html:link></li>-->
				<li><html:link forward="fCCTE"><bean:message key="institutionalLinks.ccte"/></html:link></li>
				<li><html:link forward="fReachUs"><bean:message key="institutionalLinks.reachUs"/></html:link></li>
				<li><html:link forward="fLicense"><bean:message key="institutionalLinks.license"/></html:link></li>
			</ul>
		</div>
		<div id="page_title">
			<h2><bean:message key="courseForm.view"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><%=courseName%></li>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
					<ul id="context_menu">
						<li><a href="listParticipants.do?courseId=<%=course.getId()%>">
						<bean:message key="sideMenu.listParticipants"/></a></li>
						<%  boolean isCourseTeacherOrAdmin = false;
						    int role = amadeus.searchRoleByUser(user, course);
					   		int profile = user.getProfile().getConstantProfile();
					   		if (role == Constants.PROFESSOR || profile == Constants.ADMIN) {
						   		isCourseTeacherOrAdmin = true;
					   		}
					   		boolean canRequest = amadeus.canRequestAssistance(user.getPerson(), course);
					   		if ((role == -1 || role == Constants.STUDENT) && canRequest) { %>
							<li><a href="fAssistanceRequest.do?courseId=<%=course.getId()%>">
							<bean:message key="sideMenu.assistanceRequest"/></a></li>
						<%  }
					   		if (role == Constants.MONITOR || role == Constants.STUDENT || isCourseTeacherOrAdmin) {%>
								<li><a href="fCourseContent.do?courseId=<%=course.getId()%>">
								<bean:message key="courseForm.courseContent"/></a></li>
						<%	}					   							   		
					   		if (isCourseTeacherOrAdmin) {%>
								<li><a href="fEditCourse.do?courseId=<%=course.getId()%>">
								<bean:message key="sideMenu.editCourse"/></a></li>
						<%  }%>
					</ul>
				</dd>
			</dl>
		  </div>
		  <div id="content">
			 <dl class="insert2">
			 		<html:errors property="registrationError"/>
					<html:form action="/register" focus="name">
					<html:hidden property="id" name="course"/>
					<dt><bean:message key="courseCharacteristics.courseName"/></dt>
						<dd class="showDataForm"><bean:write property="name" name="course" /></dd>

					<dt><bean:message key="courseCharacteristics.teachers"/></dt>
					<logic:iterate id="teacher" name="teachers">
						<bean:define id="person" name="teacher" property="person"/>
						<dd class="showDataForm">
						<%  if (isCourseTeacherOrAdmin) {%>
						<a href="userPrivateData.do?userId=<bean:write name="teacher" property="id"/>">
						<% } else {%>
						<a href="userPublicData.do?userId=<bean:write name="teacher" property="id"/>">
						<%} %>
						<bean:write name="person" property="name"/></a></dd>
					</logic:iterate>
					
					<logic:notEmpty name="assistants">
					<dt><bean:message key="courseCharacteristics.assistants"/></dt>
					<logic:iterate id="assistant" name="assistants">
						<bean:define id="person" name="assistant" property="person"/>
						<dd class="showDataForm">
						<%  if (isCourseTeacherOrAdmin) {%>
						<a href="userPrivateData.do?userId=<bean:write name="assistant" property="id"/>">
						<% } else {%>
						<a href="userPublicData.do?userId=<bean:write name="assistant" property="id"/>">
						<%} %>
						<bean:write name="person" property="name"/></a></dd>
					</logic:iterate>
					</logic:notEmpty>
					
					<dt><bean:message key="courseCharacteristics.objectives"/></dt>
						<dd class="showDataForm"><bean:write property="objectives" name="course" /></dd>
					<dt><bean:message key="courseCharacteristics.content"/></dt>
						<dd class="showDataForm"><bean:write property="content" name="course" /></dd>
					<dt><bean:message key="courseCharacteristics.students"/></dt>
						<dd class="showDataForm"><bean:write property="students" name="course" /></dd>
					<dt><bean:message key="courseCharacteristics.viewInitialRegistrationDate"/></dt>
						<dd class="showDataForm"><bean:write property="initialRegistrationDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.viewFinalRegistrationDate"/></dt>
						<dd class="showDataForm"><bean:write property="finalRegistrationDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.viewInitialCourseDate"/></dt>
						<dd class="showDataForm"><bean:write property="initialCourseDate" name="course" format="dd / MM / yyyy"/></dd>
					<dt><bean:message key="courseCharacteristics.viewFinalCourseDate"/></dt>
						<dd class="showDataForm"><bean:write property="finalCourseDate" name="course" format="dd / MM / yyyy"/></dd>

					<dt><bean:message key="courseCharacteristics.keywords"/></dt>
					<dd class="showDataForm">
					<logic:iterate id="keyword" name="keywords" indexId="count">
						<a href="searchCourse.do?type=keyword&course=<bean:write property="name" name="keyword"/>">
						<bean:write property="name" name="keyword"/></a>
						<%
							Integer size = (Integer) request.getAttribute("size");
							if (count < size - 1)
								out.write(", ");
						%>
					</logic:iterate></dd>
					
					<%
						if (amadeus.canRegisterUser(user, course)) { %>
					<dt class="field"><html:submit styleClass="button"><bean:message key="courseCharacteristics.registration"/></html:submit></dt>
					<%  } %>
					</html:form>	
			</dl>
		 </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
	</body>
</html:html>
