<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.academic.course.Course"
		import="br.ufpe.cin.amadeus.system.access_control.user.User"
		import="br.ufpe.cin.amadeus.util.Constants"
		import="java.util.List"
		import="java.util.Date"
		import="java.text.SimpleDateFormat"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fCourseNotLogged"/>			
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	int courseId;
	String enrolled = (String)request.getAttribute("enrolled");
	if (enrolled != null)	//é para diferenciar quando o usuário vem da página de matrícula ou não
		courseId = (Integer)request.getAttribute("courseId"); 
	else
		courseId = Integer.parseInt(request.getParameter("courseId"));
	Course course = amadeus.searchCourse(courseId);
	session.setAttribute("course", course);
	int modulesCounter = course.getModules().size();
	User user = (User)session.getAttribute("user");
	int role = amadeus.searchRoleByUser(user, course);
	int profile = user.getProfile().getConstantProfile();
	
	String courseName = course.getName();
	List<User> teachers = amadeus.listTeachersByCourse(course);
	int teacherListSize = teachers.size();
	String teacherNames = "";
	for (int i = 0; i < teacherListSize; i++) {
		teacherNames += teachers.get(i).getPerson().getName();
		if (i != teacherListSize - 1) 
			teacherNames += ", ";
	}
	
	List<User> assistants = amadeus.listAssistantsByCourse(course);
	int assistListSize = assistants.size();
	String assistNames = "";
	for (int i = 0; i < assistListSize; i++) {
		assistNames += assistants.get(i).getPerson().getName();
		if (i != assistListSize - 1)
			assistNames += ", ";  
	}
	
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
	String initialDate = formater.format(course.getInitialCourseDate());
	String finalDate = formater.format(course.getFinalCourseDate());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<title><bean:message key="courseForm.contentManagement"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link  href="<html:rewrite page="/css.css"/>" type="text/css" rel="stylesheet"/>
	<link  href="<html:rewrite page="/table.css"/>" type="text/css" rel="stylesheet"/>
	<script src="<html:rewrite page="/dwr/util.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/engine.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/Materials.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/Activities.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/ContManag.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/Module.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/validation.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/javascript.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/material.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/courseEvaluation.js"/>" type="text/javascript"></script>
		<script src="<html:rewrite page="/studentEvaluation.js"/>" type="text/javascript"></script>
</head>
<body onload="fillModules(<%=modulesCounter%>, <%=role%>, <%=profile%>);">
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
<!-- 					<li><html:link forward="fCases"><bean:message key="institutionalLinks.cases"/></html:link></li>-->
				<li><html:link forward="fCCTE"><bean:message key="institutionalLinks.ccte"/></html:link></li>
				<li><html:link forward="fReachUs"><bean:message key="institutionalLinks.reachUs"/></html:link></li>
				<li><html:link forward="fLicense"><bean:message key="institutionalLinks.license"/></html:link></li>
			</ul>
		</div>
		<div id="page_title">
			<h2><bean:message key="courseForm.contentManagement"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><a href="viewCourse.do?courseId=<%=course.getId()%>"><%=course.getName()%></a></li>
				<li><bean:message key="courseForm.module"/></li>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
					<ul id="context_menu">
						<li><a href="viewCourse.do?courseId=<%=course.getId()%>"><bean:message key="courseCharacteristics.heading"/></a></li>
						<li><a href="listParticipants.do?courseId=<%=course.getId()%>">
						<bean:message key="sideMenu.listParticipants"/></a></li>
						<%  boolean isCourseTeacherOrAdmin = false;
						    if (role == Constants.PROFESSOR || profile == Constants.ADMIN) {
						   		isCourseTeacherOrAdmin = true;
					   		}
					   		boolean canRequest = amadeus.canRequestAssistance(user.getPerson(), course);
					   		if ((role == -1 || role == Constants.STUDENT) && canRequest) { %>
							<li><a href="fAssistanceRequest.do?courseId=<%=course.getId()%>">
							<bean:message key="sideMenu.assistanceRequest"/></a></li>
						<%  }
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
			<div>
				<table class="tableDescripCur">
				  <tr> 
				    <td colspan="2" class="headTab"><%=courseName%></td>
				  </tr>
				  <tr> 
				    <td colspan="2"><bean:message key="courseCharacteristics.teachers"/>: <%=teacherNames%><%if (!teacherNames.equals("")){ %>.<%} %></td>
				  </tr>
				  <tr> 
				    <td colspan="2"><bean:message key="courseCharacteristics.assistants"/>: <%=assistNames%><%if (!assistNames.equals("")){ %>.<%} %></td>
				  </tr>
				  <tr> 
				    <td align="right" clas="modDescrip"><bean:message key="courseForm.initialDate"/>: <%=initialDate%></td>
				  </tr>
				  <tr> 
				    <td align="right" clas="modDescrip"><bean:message key="courseForm.finalDate"/>: <%=finalDate%></td>
				  </tr>
			    </table>
			</div>
			<div id="form">  
			
			</div>
			</dl>
		</div>
		<div id="footnote">
			<dl><bean:message key="copyright.title"/></dl>
		</div>
	</div>

</body>
</html:html>
