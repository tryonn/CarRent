<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://amadeus.com/taglib" prefix="amadeus" %>
<%@ page import="br.ufpe.cin.amadeus.struts.util.CourseSearchManager" %>

<logic:notPresent name="user"> 
	<logic:redirect forward="fListCoursesNotLogged"/>			
</logic:notPresent>

<%
	CourseSearchManager csm = (CourseSearchManager) request.getSession().getAttribute("csm");
	String criteria = csm.getCriteria();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
	<head> 
		<title><bean:message key="listCourses.title"/></title>
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
			<h2><bean:message key="listCourses.title"/></h2>
		</div>
		<div id="breadcrumbs">
			<ul id="breadcrumb">
				<li><html:link forward="fMenu"><bean:message key="menu.name"/></html:link></li>
				<li><bean:message key="listCourses.heading"/><%=' ' + criteria%></li>
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
		 	<amadeus:courseSearch manager="csm"/>
			<dl class="insert2">
				<html:form action="/searchCourse" onsubmit="return validateSearchCourseForm(this);">
			  	<dt><bean:message key="courses.search"/>:</dt>
    	        	<dd class="field"><html:text property="course" styleClass="formfield2" styleId="realname"/></dd>
        	    	<dd class="description"></dd>
	            	<html:hidden property="type" value="rule"/>
            	<dt class="field"><html:submit styleClass="button"><bean:message key="courses.searchButton"/></html:submit></dt>
            	</html:form>
			</dl>
			
		 </div>
			<div id="footnote">
				<dl><bean:message key="copyright.title"/></dl>
			</div>
		</div>
	    <html:javascript formName="searchCourseForm"/>
	</body>
</html:html>
