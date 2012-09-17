<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.system.human_resources.resume.Resume" %>
<%@ page import="br.ufpe.cin.amadeus.util.Constants"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fWelcome"/>			
</logic:notPresent>

<% 
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	int requestUserId = (Integer) request.getAttribute("userId");

	User user = amadeus.searchUser(requestUserId);
	User loggedUser = (User) request.getSession().getAttribute("user");
	int profile = loggedUser.getProfile().getConstantProfile();
	int loggedUserId = loggedUser.getId();
	//Se o cara for professor mas não do curso, ele vai conseguir ver os dados pessoais do povo
	if ((profile != Constants.ADMIN) && (profile != Constants.PROFESSOR) && (loggedUserId != requestUserId)) {%>
		<logic:redirect forward="fNotAllowed"/>		
<%  }
	
	
	Resume resume = amadeus.searchResume(user.getPerson());
	String resume_degree = "requestForm.degree."+resume.getDegree();
	
	request.setAttribute("login", user.getLogin());
	request.setAttribute("person", user.getPerson());
	request.setAttribute("resume", resume);
	request.setAttribute("resume_degree", resume_degree);
	int amountTasks = 0;
	amountTasks = amadeus.getAmountPendingTasks(loggedUser);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<% if (loggedUserId == requestUserId) {%>
		<title><bean:message key="userPrivateData.title2"/></title>
		<% } else {%>
		<title><bean:message key="userPrivateData.title"/></title>
		<% }%>
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
			<% if (loggedUserId == requestUserId) {%>
			<h2><bean:message key="userPrivateData.title2"/></h2>
			<% } else {%>
			<h2><bean:message key="userPrivateData.title"/></h2>
			<% }%>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<% if (loggedUserId == requestUserId) {%>
				<li><bean:message key="userPrivateData.title2"/></li>
				<% } else {%>
				<li><bean:message key="userPrivateData.title"/></li>
				<% }%>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
				<ul id="context_menu">
						<% if (loggedUserId == requestUserId) {%>
						<li><html:link action="fEditUser"><bean:message key="editUser.heading"/></html:link></li>
						<li><html:link forward="fEditPassword"><bean:message key="editPassword.heading"/></html:link></li>
						<%  boolean isTeacher = false;
					   		if (profile == Constants.PROFESSOR || profile == Constants.ADMIN) {
						    	isTeacher = true;
					   		}
					   		if (!isTeacher && amadeus.canRequestTeaching(loggedUser.getPerson())) {%>
						<li><html:link forward="fTeachingRequest"><bean:message key="teachingRequest.heading"/></html:link></li>
						<%  } 
					   		if (amountTasks > 0) {%>
						<li><a href="viewPendingTasks.do"><bean:message key="sideMenu.tasks"/></a></li>   
						<% } else {%>
    	      			<li><bean:message key="sideMenu.tasks"/></li>  
        	  			<%} %>
						<li><html:link forward="fMailBox"><bean:message key="sideMenu.mailBox"/></html:link></li>
						<li><html:link forward="fContacts"><bean:message key="sideMenu.contacts"/></html:link></li>										
						<li><html:link forward="fOnlineContacts"><bean:message key="sideMenu.onlineContacts"/></html:link></li>
						<li><html:link forward="fClassmates"><bean:message key="sideMenu.classmates"/></html:link></li>
						<%  if (isTeacher) {%>
          				<li><html:link forward="fInsertCourseStepOne"><bean:message key="courseForm.createNewCourse"/></html:link></li>
	          			<%} %>
						<%} else {%>
						<li><bean:message key="sideMenu.sendMessage"/></li>
						<li><bean:message key="sideMenu.addContact"/></li>
						<%}%>
					</ul>
				</dd>
			</dl>
		  </div>
		  <div id="content">
				<dl class="insert2">
					<img src="photo.do?id=<%=user.getId()%>"/>
					<logic:notEqual value="" name="person" property="name">
					<dt><bean:message key="userRegistrationForm.fullName"/></dt>
				 		<dd class="showDataForm"><bean:write name="person" property="name"/></dd>
					</logic:notEqual>

					<logic:equal value="M" name="person" property="gender">
						<dt><bean:message key="editUser.gender"/></dt>
				 		<dd class="showDataForm"><bean:message key="editUser.gender.male"/></dd>
					</logic:equal>
					<logic:equal value="F" name="person" property="gender">
						<dt><bean:message key="editUser.gender"/></dt>
				 		<dd class="showDataForm"><bean:message key="editUser.gender.female"/></dd>
					</logic:equal>
					
					<logic:notEqual value="" name="person" property="city">
					<dt><bean:message key="userPublicData.city"/></dt>	
				 		<dd class="showDataForm"><bean:write name="person" property="city"/></dd>
					</logic:notEqual>
					
					<logic:notEqual value="" name="person" property="state">
					<dt><bean:message key="userPublicData.state"/></dt>	
				 		<dd class="showDataForm"><bean:write name="person" property="state"/></dd>
					</logic:notEqual>

					<logic:notEqual value="" name="person" property="email">
					<dt><bean:message key="userRegistrationForm.email"/></dt>	
				 		<dd class="showDataForm"><bean:write name="person" property="email"/></dd>
					</logic:notEqual>
	
					<logic:notEqual value="" name="person" property="cpf">
					<dt><bean:message key="userPrivateData.cpf"/></dt>	
				 		<dd class="showDataForm"><bean:write name="person" property="cpf"/></dd>
					</logic:notEqual>
					
					<logic:notEqual value="" name="login">
					<dt><bean:message key="userPrivateData.login"/></dt>	
				 		<dd class="showDataForm"><bean:write name="login"/></dd>
					</logic:notEqual>
					
					<logic:notEqual value="" name="person" property="phoneNumber">
					<dt><bean:message key="userPrivateData.phoneNumber"/></dt>	
				 		<dd class="showDataForm"><bean:write name="person" property="phoneNumber"/></dd>
					</logic:notEqual>
					
					<logic:notEqual value="" name="person" property="birthDate">
					<dt><bean:message key="userPrivateData.birthDate"/></dt>	
				 		<dd class="showDataForm"><bean:write name="person" property="birthDate" format="dd / MM / yyyy"/></dd>
					</logic:notEqual>

					<logic:notEqual value="" name="resume" property="degree">
					<dt><bean:message key="requestForm.degree"/></dt>
				 		<dd class="showDataForm"><bean:message name="resume_degree"/></dd>
					</logic:notEqual>

					<logic:notEqual value="" name="resume" property="year">
					<dt><bean:message key="requestForm.year"/></dt>
				 		<dd class="showDataForm"><bean:write name="resume" property="year"/></dd>
					</logic:notEqual>

					<logic:notEqual value="" name="resume" property="institution">
					<dt><bean:message key="requestForm.institution"/></dt>	
				 		<dd class="showDataForm"><bean:write name="resume" property="institution"/></dd>
					</logic:notEqual>

					<logic:notEqual value="" name="resume" property="description">
					<dt><bean:message key="requestForm.description"/></dt>	
				 		<dd class="showDataForm"><bean:write name="resume" property="description"/></dd>
					</logic:notEqual>					
			</dl>
		  </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
	</body>
</html:html>
