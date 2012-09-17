<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="java.util.ArrayList"
		import="br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest"
		import="br.ufpe.cin.amadeus.system.human_resources.resume.Resume"
		import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.access_control.user.User"
		import="br.ufpe.cin.amadeus.system.academic.course.Course"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fWelcome"/>			
</logic:notPresent>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="pendingTasks.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css">
		<script src="<html:rewrite page="/dwr/util.js"/>" type="text/javascript"></script>
		<script src="<html:rewrite page="/dwr/engine.js"/>" type="text/javascript"></script>
		<script src="<html:rewrite page="/dwr/interface/RequestValidation.js"/>" type="text/javascript"></script>
		<script src="<html:rewrite page="/request.js"/>" type="text/javascript"></script>
	</head>
	<body onload="init('<bean:message key="request.approve"/>','<bean:message key="request.cancel"/>');">
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
			<h2><bean:message key="pendingTasks.title"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="sideMenu.tasks"/></li>
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
						<li><html:link forward="fMailBox"><bean:message key="sideMenu.mailBox"/></html:link></li>
						<li><html:link forward="fContacts"><bean:message key="sideMenu.contacts"/></html:link></li>										
						<li><html:link forward="fOnlineContacts"><bean:message key="sideMenu.onlineContacts"/></html:link></li>
						<li><html:link forward="fClassmates"><bean:message key="sideMenu.classmates"/></html:link></li>
					</ul>
				</dd>
			</dl>
		</div>
		<div id="content">
			<dl class="pendingTitle"><bean:message key="pendingTasks.approveTeaching"/></dl><%
			AmadeusSystem amadeus = AmadeusSystem.getInstance();
			ArrayList<UserRequest> requests = (ArrayList<UserRequest>)request.getAttribute("requests");
			for (UserRequest reqst : requests) {
				int id = reqst.getIdUserRequest();
				Resume resume = amadeus.searchResume(reqst.getPerson());
				pageContext.setAttribute("degree", "requestForm.degree."+resume.getDegree());
%>
<div id="request<%=id%>" class="requestColapsed pinball-scoop">
<a onclick="showDetails(<%=id%>);" class="pinball-sinkhole"></a>
<bean:message key="userRegistrationForm.name"/>: <%=reqst.getPerson().getName()%><br />
	<div id="reqInfo<%=id%>" style="display: none">
	<bean:message key="requestForm.degree"/>: <bean:message name="degree"/><br />
	<bean:message key="requestForm.year"/>: <%=resume.getYear()%><br />
	<bean:message key="requestForm.institution"/>: <%=resume.getInstitution()%><br />
	<bean:message key="requestForm.interest"/>: <%=reqst.getInterest()%><br />
	<bean:message key="requestForm.description"/>: <%=resume.getDescription()%><br />
	</div>
	<div id="reqJust<%=id%>" style="display: none">
	<bean:message key="pendingTasks.justification"/>:<br />
	<textarea cols="32" id="reqJustification<%=id%>"></textarea>
	</div>
	<div id="reqBtns<%=id%>" style="display: none" class="moduleControl">
	<input type="button" id="reqBtnLeft<%=id%>" value="<bean:message key="request.approve"/>" onclick="approveRequest(<%=id%>)"/>
	<input type="button" id="reqBtnRight<%=id%>" value="<bean:message key="request.reprove"/>" onclick="showJustBox(<%=id%>)"/>
	</div>
</div>
<%			}%>
		</div>
		<div id="footnote">
			<dl><bean:message key="copyright.title"/></dl>
		</div>
	</div>
	</body>
</html:html>
