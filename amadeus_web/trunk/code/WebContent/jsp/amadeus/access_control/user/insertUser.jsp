<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="userRegistrationForm.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="all">
		<div id="header">
		  <div id="login">
			<dl id="formlogin">
				<html:errors property="invalid"/>
				<html:form action="/validateLogin" focus="login" onsubmit="return validateLogonForm(this);">
		  		<dt><html:text property="login"  styleClass="inputlogin" size="15" maxlength="15" /></dt>
      			<dt><html:password property="password" styleClass="inputlogin" size="15" maxlength="15" />&nbsp;
					<html:submit styleClass="button"><bean:message key="welcome.button.submit"/></html:submit></dt>
	            <dt><html:link forward="fInsertUser" styleClass="insert"><bean:message key="userRegistrationForm.heading"/></html:link><br/></dt>
	       		</html:form>
			</dl>
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
			<h2><bean:message key="userRegistrationForm.heading"/></h2>		
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fWelcome"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="userRegistrationForm.heading"/></li>
			</ul>
		</div>
			<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
				</dd>
			</dl>
			</div>
			
			
			
			<!--  Ver os html:errors --><!-- dl class="insertastro" -->
			<div id="content">
			
					<dl class="insert2">
						<html:form action="/insertUser" focus="name" onsubmit="return validateUserForm(this);">
						<html:errors property="confirmation"/>
						<dt><bean:message key="userRegistrationForm.fullName"/></dt>
							<dd class="field"><html:text property="name" styleClass="formfield" styleId="realname"/></dd>
							<dd class="description"><bean:message key="userRegistrationForm.fullNameDescription"/></dd>
						<dt><bean:message key="userRegistrationForm.email"/></dt>
							<dd class="field"><html:text property="email" styleClass="formfield" styleId="mail"/></dd>
							<dd class="description"><bean:message key="userRegistrationForm.emailDescription"/></dd>
						<dt><bean:message key="userRegistrationForm.login"/></dt>
							<dd class="field"><html:text property="login" styleClass="formfield" size="15" maxlength="15" styleId="username"/></dd>
							<dd class="description"><bean:message key="userRegistrationForm.loginDescription"/></dd>
						<dt><bean:message key="userRegistrationForm.password"/></dt>
							<dd class="field"><html:password property="password" styleClass="formfield" size="15" maxlength="15" styleId="password"/></dd>
							<dd class="description"><bean:message key="userRegistrationForm.passwordDescription"/></dd>
						<dt><bean:message key="userRegistrationForm.passwordConfirmation"/></dt>
							<dd class="field"><html:password property="passwordConfirmation" styleClass="formfield" size="15" maxlength="15" styleId="realname"/></dd>
							<dd class="description"><bean:message key="userRegistrationForm.passwordConfirmationDescription"/></dd>
						    <dt class="field"><html:submit styleClass="button"><bean:message key="userRegistrationForm.register"/></html:submit></dt>
							<dd class="description2">	<br/><bean:message key="general.required"/></dd>
						</html:form>
					</dl>				
			</div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>

<html:javascript formName="userForm"/>
</body>
</html:html>




    
   

