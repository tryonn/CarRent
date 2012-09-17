<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="java.util.List"
		import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.academic.keyword.Keyword" %>

<logic:present name="user"> 
	<logic:redirect forward="fMenu"/> 
</logic:present>

<%	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	List<Keyword> keywords = amadeus.getMostPopularKeywords();
	pageContext.setAttribute("keywords", keywords); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head>
		<title><bean:message key="welcome.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css">
		<link href="<html:rewrite page="/cloud.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
	<div id="all">
	<div id="header">

	</div>
	<div id="institutional_menu">
		<ul>
			<li><html:link forward="fProject"><bean:message key="institutionalLinks.project"/></html:link></li>
<!-- 				<li><html:link forward="fCases"><bean:message key="institutionalLinks.cases"/></html:link></li>-->
			<li><html:link forward="fCCTE"><bean:message key="institutionalLinks.ccte"/></html:link></li>
			<li><html:link forward="fReachUs"><bean:message key="institutionalLinks.reachUs"/></html:link></li>
			<li><html:link forward="fLicense"><bean:message key="institutionalLinks.license"/></html:link></li>
		</ul>
	</div>
	<div id="percep">
	<div id="login">
		<dl id="formlogin">
			<html:errors property="invalid"/>
			<html:form action="/validateLogin" focus="login" onsubmit="return validateLogonForm(this);">
		  	<dt><html:text property="login" styleClass="inputlogin" size="15" maxlength="15" /></dt>
      		<dt><html:password property="password" styleClass="inputlogin" size="15" maxlength="15" />&nbsp;
				<html:submit styleClass="button"><bean:message key="welcome.button.submit"/></html:submit></dt>
            <dt><html:link forward="fRemindPassword" styleClass="insert"><bean:message key="remindPassword.heading"/></html:link> <br/>      
	            <html:link forward="fInsertUser" styleClass="insert"><bean:message key="userRegistrationForm.heading"/></html:link></dt>
	            
       		</html:form>
		</dl>
	</div>
	</div>
	<div id="areas">
		<dl class="insert2">
            
			<html:form action="/searchCourse" onsubmit="return validateSearchCourseForm(this);">
			<dt><bean:message key="courses.search"/>:</dt>
				<dd class="field"><html:text property="course" styleClass="formfield2" styleId="realname"/></dd>
				<dd class="description"></dd>
				<html:hidden property="type" value="rule"/>
			<dt class="field"><html:submit styleClass="button"><bean:message key="courses.searchButton"/></html:submit></dt>
			</html:form>
			<dt><bean:message key="keywords.title"/>:</dt>
			<dd class="tagcloud">
			<div id="tagcloud">
				<logic:iterate id="keyword" name="keywords" indexId="count" type="br.ufpe.cin.amadeus.system.academic.keyword.Keyword">
					<a href="searchCourse.do?type=keyword&course=<%=keyword.getName()%>" class="<%="keywork" + keyword.getGroup()%>">
					<%=keyword.getName()%></a><%
						
							if (count < keywords.size() - 1)	out.write(", ");
						
				%></logic:iterate>
			</div>
			</dd>
		</dl>
	</div>       
	<div id="footnote">
		<dl><bean:message key="copyright.title"/></dl>
	</div>
	</div>
	<html:javascript formName="logonForm"/>
	<html:javascript formName="searchCourseForm"/>
	</body>
</html:html>
