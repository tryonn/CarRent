<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem" %>
<%@ page import="br.ufpe.cin.amadeus.system.access_control.user.User" %>
<%@ page import="br.ufpe.cin.amadeus.system.human_resources.resume.Resume" %>
<%@ page import="java.util.HashMap"%>


<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	String userId = request.getParameter("userId");

	User user = amadeus.searchUser(Integer.parseInt(userId));
	Resume resume = amadeus.searchResume(user.getPerson());
	String resume_degree = "requestForm.degree."+resume.getDegree();
	
	request.setAttribute("person", user.getPerson());
	request.setAttribute("resume", resume);
	request.setAttribute("resume_degree", resume_degree);
	HashMap<String, String> hash = new HashMap<String, String>();
	hash.put("action", "/userPublicData.do?" +
				"userId="+ user.getId());
	request.setAttribute("hash", hash);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="userPublicData.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="all">
		<div id="header">
				<div id="login">
				<logic:present name="user"> 
				<bean:message key="menu.greeting"/>
				<bean:define id="u_person" name="user" property="person"/>
				<html:link action="myProfile"><bean:write name="u_person" property="name"/></html:link>.<br/>
				<html:link forward="signOut"><bean:message key="general.leave"/></html:link><br/>
				</logic:present>
				<logic:notPresent name="user">
				<dl id="formlogin">
						<html:errors property="invalid"/>
						<html:form action="/validateLoginAction" focus="login" onsubmit="return validateLogonActionForm(this);">
						<html:hidden property="action" name="hash"/>
				  		<dt><html:text property="login"  styleClass="inputlogin" size="15" maxlength="15" value="login"/></dt>
		      			<dt><html:password property="password" styleClass="inputlogin" size="15" maxlength="15" value="password"/>&nbsp;
							<html:submit styleClass="button"><bean:message key="welcome.button.submit"/></html:submit></dt>
			            <dt><html:link forward="fInsertUser" styleClass="insert"><bean:message key="userRegistrationForm.heading"/></html:link><br/></dt>
			       		</html:form>
			       	</dl>
			     </logic:notPresent>	
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
			<h2><bean:message key="userPublicData.title"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="userPublicData.heading"/><%=' ' + user.getPerson().getName()%></li>
			</ul>
		</div>
		<div id="side_menu">
			<dl>
			<dt></dt>
				<dd>
					<ul id="context_menu">
						<logic:present name="user"> 
						<li><bean:message key="sideMenu.sendMessage"/></li>
						<li><bean:message key="sideMenu.addContact"/></li>
						</logic:present>
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
