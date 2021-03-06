<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.system.human_resources.resume.Resume" %>

<logic:notPresent name="user"> 
	<logic:redirect forward="fWelcome"/> 
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User) request.getSession().getAttribute("user");

	if (request.getAttribute("resume") == null) {
		Resume resume = amadeus.searchResume(user.getPerson());
		request.setAttribute("resume", resume);
	}
	int amountTasks = 0;
	amountTasks = amadeus.getAmountPendingTasks(user);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="teachingRequest.title"/></title>
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
			<h2><bean:message key="teachingRequest.heading"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="teachingRequest.heading"/></li>
			</ul>
		</div>
			<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
				<ul id="context_menu">
					<li><html:link action="myProfile"><bean:message key="sideMenu.myProfile"/></html:link></li>
					<li><html:link action="fEditUser"><bean:message key="editUser.heading"/></html:link></li>
					<li><html:link forward="fEditPassword"><bean:message key="editPassword.heading"/></html:link></li>
					<%if (amountTasks > 0) {%>
					<li><a href="viewPendingTasks.do"><bean:message key="sideMenu.tasks"/></a></li>   
					<%} else {%>
          			<li><bean:message key="sideMenu.tasks"/></li>  
          			<%} %>
					<li><html:link forward="fMailBox"><bean:message key="sideMenu.mailBox"/></html:link></li>
					<li><html:link forward="fContacts"><bean:message key="sideMenu.contacts"/></html:link></li>										
					<li><html:link forward="fOnlineContacts"><bean:message key="sideMenu.onlineContacts"/></html:link></li>
					<li><html:link forward="fClassmates"><bean:message key="sideMenu.classmates"/></html:link></li>
					</ul>
				</dd>
			</dl>
			</div>
			<div id="content">
				
				<dl class="insert2">
					<html:form action="/teachingRequest" onsubmit="return validateRequestForm(this);">
					<html:errors property="requestError"/>
					<dt><bean:message key="requestForm.degree"/></dt>
						<dd><html:select property="degree" name="resume" styleClass="formfield" styleId="username">
							<html:option value=""><bean:message key="requestForm.degree.select"/></html:option>
							<html:option value="1degree"><bean:message key="requestForm.degree.1degree"/></html:option>
							<html:option value="2degree"><bean:message key="requestForm.degree.2degree"/></html:option>
							<html:option value="3degree"><bean:message key="requestForm.degree.3degree"/></html:option>
							<html:option value="specialization"><bean:message key="requestForm.degree.specialization"/></html:option>
							<html:option value="masters"><bean:message key="requestForm.degree.masters"/></html:option>
							<html:option value="doctorate"><bean:message key="requestForm.degree.doctorate"/></html:option>
							<html:option value="other" ><bean:message key="requestForm.degree.other"/></html:option>
						</html:select></dd>
						<dd class="description"><bean:message key="requestForm.degreeDescription"/></dd>
					<dt><bean:message key="requestForm.year"/></dt>	
						<dd><html:text property="year" name="resume" styleClass="formfield" maxlength="4" styleId="realname"/>	
						<dd class="description"><bean:message key="requestForm.yearDescription"/></dd>
					<dt><bean:message key="requestForm.institution"/></dt>	
						<dd><html:text property="institution" name="resume" styleClass="formfield" styleId="realname"/>
						<dd class="description"><bean:message key="requestForm.institutionDescription"/></dd>
					<dt><bean:message key="requestForm.description"/></dt>	
						<dd><html:textarea property="description" name="resume" styleClass="formfield" styleId="realname"/>
						<dd class="description"><bean:message key="requestForm.descriptionDescription"/></dd>
					<dt><bean:message key="requestForm.interest"/></dt>	
						<dd><html:textarea property="interest" styleClass="formfield" styleId="realname"/>
						<dd class="description"><bean:message key="requestForm.interestDescription"/></dd>
					<dt class="field"><html:submit styleClass="button"><bean:message key="teachingRequest.sendRequest"/></html:submit></dt>
					<dd class="description2"><br/><bean:message key="general.required"/></dd>
				</html:form>
			</dl>
				
		
		  </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
		<html:javascript formName="requestForm"/>
	
	</body>
</html:html>
