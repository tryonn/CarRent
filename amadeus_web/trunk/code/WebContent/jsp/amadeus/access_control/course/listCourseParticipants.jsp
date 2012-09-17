<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.access_control.user.User"
		import="br.ufpe.cin.amadeus.system.academic.course.Course"
		import="br.ufpe.cin.amadeus.util.Constants" %>

<logic:notPresent name="user"> 
	<logic:redirect forward="fListCoursesNotLogged"/>			
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User)session.getAttribute("user");
	
	Course course = (Course)request.getAttribute("course");
	int role = amadeus.searchRoleByUser(user, course);
	int profile = user.getProfile().getConstantProfile();
	
	boolean canRequest = amadeus.canRequestAssistance(user.getPerson(), course);
	
	String link = "userPublicData.do?userId=";
	boolean isCourseTeacherOrAdmin = false;
	if ((role == Constants.PROFESSOR) || (profile == Constants.ADMIN)) {
		isCourseTeacherOrAdmin = true;
		link = "userPrivateData.do?userId=";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="listParticipants.title"/></title>
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
<!-- 					<li><html:link forward="fCases"><bean:message key="institutionalLinks.cases"/></html:link></li>-->
				<li><html:link forward="fCCTE"><bean:message key="institutionalLinks.ccte"/></html:link></li>
				<li><html:link forward="fReachUs"><bean:message key="institutionalLinks.reachUs"/></html:link></li>
				<li><html:link forward="fLicense"><bean:message key="institutionalLinks.license"/></html:link></li>
			</ul>
		</div>
		<div id="page_title">
			<h2><bean:message key="listParticipants.title"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><a href="viewCourse.do?courseId=<%=course.getId()%>"><%=course.getName()%></a></li>
				<li><bean:message key="listParticipants.heading"/></li>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
					<ul id="context_menu">
						<li><a href="viewCourse.do?courseId=<%=course.getId()%>"><bean:message key="courseCharacteristics.heading"/></a></li>
						<%	if ((role == -1 || role == Constants.STUDENT) && canRequest) { %>
							<li><a href="fAssistanceRequest.do?courseId=<%=course.getId()%>">
							<bean:message key="sideMenu.assistanceRequest"/></a></li>
						<%  }
					   		if (role == Constants.MONITOR || role == Constants.STUDENT || role == Constants.PROFESSOR || profile == Constants.ADMIN) {%>
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
			 <ul id="resultbusca">
			 	<logic:notEmpty name="participants">
			 		<dt><bean:message key="courseCharacteristics.students_"/></dt>
	  				<logic:iterate id="participant" name="participants">
					  <li><h3>
					    <bean:define id="p_person" name="participant" property="person"/>
				      	<a href="<%=link%><bean:write name="participant" property="id"/>">
				      	<bean:write name="p_person" property="name"/></a></h3></li>
					</logic:iterate>
				</logic:notEmpty>
				
				<dt><bean:message key="courseCharacteristics.teachers"/></dt>
				<logic:iterate id="teacher" name="teachers">
					<li><h3>
					<bean:define id="person" name="teacher" property="person"/>
					<a href="<%=link%><bean:write name="teacher" property="id"/>">
					<bean:write name="person" property="name"/></a></h3></li>
				</logic:iterate>
				
				<logic:notEmpty name="assistants">
					<dt><bean:message key="courseCharacteristics.assistants"/></dt>
					<logic:iterate id="assistant" name="assistants">
						<li><h3>
						<bean:define id="person" name="assistant" property="person"/>
						<a href="<%=link%><bean:write name="assistant" property="id"/>">
						<bean:write name="person" property="name"/></a></h3></li>
					</logic:iterate>
				</logic:notEmpty>
				</ul>
			
		 </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
	</body>
</html:html>
