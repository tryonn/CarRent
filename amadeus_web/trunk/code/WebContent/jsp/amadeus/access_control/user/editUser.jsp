<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.util.Constants" %>

<logic:notPresent name="user"> 
	<logic:redirect forward="fWelcome"/> 
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User) request.getSession().getAttribute("user");

	int amountTasks = 0;
	amountTasks = amadeus.getAmountPendingTasks(user);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
	
		<title><bean:message key="editUser.title"/></title>
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
			<h2><bean:message key="editUser.heading"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="editUser.heading"/></li>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
				<ul id="context_menu">
					<li><html:link action="myProfile"><bean:message key="sideMenu.myProfile"/></html:link></li>
					<li><html:link forward="fEditPassword"><bean:message key="editPassword.heading"/></html:link></li>
					<% boolean isTeacher = false;
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
					<html:errors/>
					<!-- <dt><html:errors property="invalidDate"/></dt> -->
					<html:img action="/photo.do" />
					<html:form action="/editUser" focus="name" enctype="multipart/form-data" onsubmit="return validateEditUserForm(this);">
					<dt><bean:message key="userRegistrationForm.fullName"/></dt>
				 		<dd class="field"><html:text name="userData" property="name" styleClass="formfield2" styleId="realname"/></dd>
						<dd class="description"><bean:message key="userRegistrationForm.fullNameDescription"/></dd>
					<dt><bean:message key="editUser.CPF"/></dt>	
						<dd class="field"><html:text name="userData" property="cpf" styleClass="cpf" styleId="ddd" maxlength="9" /> -	
							<html:text name="userData" property="cpfDigit" styleClass="cpfdigi" styleId="ddd" maxlength="2" /></dd>
						<dd class="description"><bean:message key="editUser.CPFDescription"/></dd>
					<dt><bean:message key="editUser.phone"/></dt>	
						<dd class="field"><html:text name="userData" property="phoneDDD" styleClass="ddd" styleId="realname" maxlength="2" /> -
						   <html:text name="userData" property="phone" styleClass="phone" styleId="realname" maxlength="8" /> </dd>
						<dd class="description"><bean:message key="editUser.phoneDescription"/></dd>
					<dt><bean:message key="editUser.gender"/></dt>
						<dd><html:radio name="userData" property="gender" value="M" ><bean:message key="editUser.gender.male"/></html:radio>
                        	<html:radio name="userData" property="gender" value="F" ><bean:message key="editUser.gender.female"/></html:radio></dd>
						<dd class="description"><bean:message key="editUser.genderDescription"/></dd>
					<dt><bean:message key="editUser.birthDate"/></dt>	
					 	<dd class="field2">
					 		<html:text name="userData" property="day" styleClass="date2" styleId="dia" maxlength="2" /> / 
	 				 		<html:text name="userData" property="month" styleClass="date2" styleId="mes" maxlength="2" /> / 
	 				 		<html:text name="userData" property="year" styleClass="date" styleId="ano" maxlength="4" />
	 				 	</dd> 
						<dd class="description"><bean:message key="editUser.birthDateDescription"/></dd>
					<dt><bean:message key="userRegistrationForm.email"/></dt>	
						<dd class="field"><html:text name="userData" property="email" styleClass="formfield2" styleId="mail" /></dd>
						<dd class="description"><bean:message key="userRegistrationForm.emailDescription"/></dd>
					<dt><bean:message key="editUser.state"/></dt>	
						<dd class="field"><html:select name="userData" property="state" styleClass="state">
					    	<html:option value=""></html:option>
					    	<html:option value="AC">AC</html:option>
					    	<html:option value="AL">AL</html:option>
					    	<html:option value="AM">AM</html:option>
					    	<html:option value="AP">AP</html:option>
					    	<html:option value="BA">BA</html:option>
					    	<html:option value="CE">CE</html:option>
					    	<html:option value="DF">DF</html:option>
					    	<html:option value="ES">ES</html:option>
					    	<html:option value="GO">GO</html:option>
					    	<html:option value="MA">MA</html:option>
					    	<html:option value="MG">MG</html:option>
					    	<html:option value="MS">MS</html:option>
					    	<html:option value="MT">MT</html:option>
					    	<html:option value="PA">PA</html:option>
					    	<html:option value="PB">PB</html:option>
					    	<html:option value="PE">PE</html:option>
					    	<html:option value="PI">PI</html:option>
					    	<html:option value="PR">PR</html:option>
					    	<html:option value="RJ">RJ</html:option>
					    	<html:option value="RN">RN</html:option>
					    	<html:option value="RO">RO</html:option>
					    	<html:option value="RR">RR</html:option>
					    	<html:option value="RS">RS</html:option>
					    	<html:option value="SC">SC</html:option>
					    	<html:option value="SE">SE</html:option>
					    	<html:option value="SP">SP</html:option>
					    	<html:option value="TO">TO</html:option>
					    </html:select> -
					    <html:text name="userData" property="city" styleClass="city" styleId="state" /></dd>
						<dd class="description"><bean:message key="editUser.stateDescription"/></dd>
					<dt><bean:message key="editUser.picture"/></dt>
						<dd class="field"><html:file property="picture" size="35" styleClass="avatar"/></dd>
					<!-- <html:button property="button" styleClass="button"><bean:message key="welcome.button.submit"/></html:button></dd> -->
						<dd class="description"><bean:message key="editUser.selectPicture"/></dd>
					<dt><bean:message key="requestForm.degree"/></dt>
						<dd><html:select property="degree" name="userData" styleClass="formfield" styleId="username">
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
						<dd><html:text property="yearDegree" name="userData" styleClass="formfield" maxlength="4" styleId="realname"/>	
						<dd class="description"><bean:message key="requestForm.yearDescription"/></dd>
					<dt><bean:message key="requestForm.institution"/></dt>	
						<dd><html:text property="institution" name="userData" styleClass="formfield" styleId="realname"/>
						<dd class="description"><bean:message key="requestForm.institutionDescription"/></dd>
					<dt><bean:message key="requestForm.description"/></dt>	
						<dd><html:textarea property="curriculum" name="userData" styleClass="formfield" styleId="realname"/>
						<dd class="description"><bean:message key="requestForm.descriptionDescription"/></dd>
					

						<html:hidden name="userData" property="login" styleClass="formfield2" styleId="username" />
						<html:hidden name="userData" property="password" styleClass="formfield2" styleId="password" />
					<dt class="field"><html:submit styleClass="button"><bean:message key="editUser.edit"/></html:submit></dt>
				</html:form>
			</dl>
		
		  </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
		
	<html:javascript formName="editUserForm"/>	
	</body>
</html:html>
