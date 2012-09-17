<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.system.academic.course.Course"%>
<%@ page import="br.ufpe.cin.amadeus.util.Constants"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fCourseNotLogged"/>			
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User)session.getAttribute("user");

	int courseId = (Integer) request.getAttribute("courseId");
	Course course = amadeus.searchCourse(courseId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="sideMenu.editCourse"/></title>
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
			<h2><bean:message key="sideMenu.editCourse"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><a href="viewCourse.do?courseId=<%=course.getId()%>"><%=course.getName()%></a></li>
				<li><bean:message key="sideMenu.editCourse"/></li>
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
						    int role = amadeus.searchRoleByUser(user, course);
					   		int profile = user.getProfile().getConstantProfile();
					   		if (role == Constants.MONITOR || role == Constants.STUDENT || role == Constants.PROFESSOR || profile == Constants.ADMIN) {%>
								<li><a href="fCourseContent.do?courseId=<%=course.getId()%>">
								<bean:message key="courseForm.courseContent"/></a></li>
						<%	}%>	
					</ul>
				</dd>
			</dl>
		  </div>
		  <div id="content">
			 <dl class="insert2">
				<html:form action="/editCourse" focus="name" onsubmit="return validateCourseForm(this);">
					<html:errors/>
					<dt><bean:message key="courseForm.courseName"/></dt>
						<dd class="field"><html:text property="name" name="course" styleClass="formfield" styleId="realname"/></dd>
						<dd class="description"><bean:message key="courseForm.courseNameDescription"/></dd>
					<dt><bean:message key="courseForm.objectives"/></dt>
						<dd class="field"><html:textarea property="objectives" name="course" styleClass="formfield" styleId="mail"/></dd>
						<dd class="description"><bean:message key="courseForm.courseObjectives"/></dd>
					<dt><bean:message key="courseForm.content"/></dt>
						<dd class="field"><html:textarea property="content" name="course" styleClass="formfield" styleId="username"/></dd>
						<dd class="description"><bean:message key="courseForm.courseContent"/></dd>
					<dt><bean:message key="courseForm.students"/></dt>
						<dd class="field"><html:text property="students" name="course" styleClass="formfield" styleId="password"/></dd>
						<dd class="description"><bean:message key="courseForm.maxCourseStudentsAmount"/></dd>
					<dt><bean:message key="courseForm.initialRegistrationDate"/></dt>
						<dd class="field2"><html:text property="initialRegistrationDay" name="data" styleClass="date2" styleId="dia" maxlength="2"/> / 
									      <html:text property="initialRegistrationMonth" name="data" styleClass="date2" styleId="mes" maxlength="2"/> / 
										  <html:text property="initialRegistrationYear" name="data" styleClass="date" styleId="ano" maxlength="4"/></dd>
						<dd class="description"><bean:message key="courseForm.initialRegistrationDateDescription"/></dd>
					<dt><bean:message key="courseForm.finalRegistrationDate"/></dt>
						<dd class="field2"><html:text property="finalRegistrationDay" name="data" styleClass="date2" styleId="dia" maxlength="2"/> / 
										  <html:text property="finalRegistrationMonth" name="data" styleClass="date2" styleId="mes" maxlength="2"/> / 
										  <html:text property="finalRegistrationYear" name="data" styleClass="date" styleId="ano" maxlength="4"/></dd>
						<dd class="description"><bean:message key="courseForm.finalRegistrationDateDescription"/></dd>
					<dt><bean:message key="courseForm.initialCourseDate"/></dt>
						<dd class="field2"><html:text property="initialCourseDay" name="data" styleClass="date2" styleId="dia" maxlength="2"/> / 
										  <html:text property="initialCourseMonth" name="data" styleClass="date2" styleId="mes" maxlength="2"/> / 
						                  <html:text property="initialCourseYear" name="data" styleClass="date" styleId="ano" maxlength="4"/></dd>
						<dd class="description"><bean:message key="courseForm.initialCourseDateDescription"/></dd>
					<dt><bean:message key="courseForm.finalCourseDate"/></dt>
						<dd class="field2"><html:text property="finalCourseDay" name="data" styleClass="date2" styleId="dia" maxlength="2"/> / 
		              					  <html:text property="finalCourseMonth" name="data" styleClass="date2" styleId="mes" maxlength="2"/> / 
						                  <html:text property="finalCourseYear" name="data" styleClass="date" styleId="ano" maxlength="4"/></dd>
						<dd class="description"><bean:message key="courseForm.finalCourseDateDescription"/></dd>
					<dt><bean:message key="courseForm.keywords"/></dt>
						<dd class="field"><html:textarea property="keywords" name="data" styleClass="formfield" styleId="realname"/></dd>
						<dd class="description"><bean:message key="courseForm.stepTwoDescription"/></dd>

					<html:hidden property="id" name="course"/>
					<dt class="field"><html:submit styleClass="button"><bean:message key="courseForm.register"/></html:submit></dt>
					</html:form>	
			 </dl>
		 </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
		
	<html:javascript formName="courseForm"/>	
	</body>
</html:html>
