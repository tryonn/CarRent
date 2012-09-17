<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		 import="br.ufpe.cin.amadeus.system.access_control.user.User"
		 import="br.ufpe.cin.amadeus.util.Constants"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fWelcome"/>			
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User)session.getAttribute("user");

	int amountTasks = 0;
	amountTasks = amadeus.getAmountPendingTasks(user);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="mailbox.title"/></title>
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
			<h2><bean:message key="mailbox.title"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="sideMenu.mailBox"/></li>
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
						<%  boolean isTeacher = false;
					   		int profile = user.getProfile().getConstantProfile();
					   		if (profile == Constants.PROFESSOR || profile == Constants.ADMIN) {
						  		isTeacher = true;
					   		}
					   		if (!isTeacher && amadeus.canRequestTeaching(user.getPerson())) {%>
						<li><html:link forward="fTeachingRequest"><bean:message key="teachingRequest.heading"/></html:link></li>
						<% } 
					   		if (amountTasks > 0) {%>
						<li><a href="viewPendingTasks.do"><bean:message key="sideMenu.tasks"/></a></li>   
						<% } else {%>
		          		<li><bean:message key="sideMenu.tasks"/></li>  
		          		<%} %>
						<li><html:link forward="fContacts"><bean:message key="sideMenu.contacts"/></html:link></li>										
						<li><html:link forward="fOnlineContacts"><bean:message key="sideMenu.onlineContacts"/></html:link></li>
						<li><html:link forward="fClassmates"><bean:message key="sideMenu.classmates"/></html:link></li>
					</ul>
				</dd>
			</dl>
		</div>
		<div id="content">
			
		</div>
		<div id="footnote">
			<dl><bean:message key="copyright.title"/></dl>
		</div>
	</div>
	</body>
</html:html>
