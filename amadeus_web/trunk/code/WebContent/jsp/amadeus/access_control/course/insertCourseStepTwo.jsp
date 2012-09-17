<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.util.Constants" %>

<logic:notPresent name="user"> 
	<logic:redirect forward="fCourseNotLogged"/>			
</logic:notPresent>

<%
	User user = (User)session.getAttribute("user");
	int profile = user.getProfile().getConstantProfile();
	if ((profile != Constants.ADMIN) && (profile != Constants.PROFESSOR)) {%>
	<logic:redirect forward="fNotAllowed"/>		
<%  }
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
			<h2><bean:message key="courseForm.heading"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="courseForm.heading"/></li>
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
			 	<html:errors/>
				<html:form action="/insertCourseStepTwo" focus="keywords" onsubmit="return validateCourseFormStepTwo(this);">
					<dt><bean:message key="courseForm.keywords"/></dt>
						<% String courseId = (String) request.getAttribute("courseId"); %>
						<html:hidden property="courseId" value="<%=courseId%>"/>
						<dd class="field"><html:textarea property="keywords" styleClass="formfield" styleId="realname"/></dd>
						<dd class="description"><bean:message key="courseForm.stepTwoDescription"/></dd>
					<dt class="field"><html:submit styleClass="button"><bean:message key="courseForm.register"/></html:submit></dt>
				</html:form>
			 </dl>
		 </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
		
	<html:javascript formName="courseFormStepTwo"/>	
	</body>
</html:html>
