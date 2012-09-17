<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.access_control.user.User"
		import="br.ufpe.cin.amadeus.system.academic.course.Course"
		import="br.ufpe.cin.amadeus.system.academic.keyword.Keyword"
		import="java.util.List"
		import="br.ufpe.cin.amadeus.util.Constants"%>

<logic:notPresent name="user"> 
	<logic:redirect forward="fWelcome"/> 
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	User user = (User) request.getSession().getAttribute("user");

	List<Course> userCourses = amadeus.searchCoursesByUser(user);
	pageContext.setAttribute("userCourses", userCourses);
	List<Keyword> keywords = amadeus.getMostPopularKeywords();
	pageContext.setAttribute("keywords", keywords);
	int amountTasks = 0;
	int usrProfile = user.getProfile().getConstantProfile();
	amountTasks = amadeus.getAmountPendingTasks(user);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html locale="true">
	<head>
		<title><bean:message key="menu.title"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<link href="<html:rewrite page="/css.css" />" rel="stylesheet" type="text/css"> 
		<link href="<html:rewrite page="/cloud.css" />" rel="stylesheet" type="text/css">
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
	<div id="percep">
	<dl>
	<dt><bean:message key="menu.youHave"/>:</dt>
	  <dd>
	    <ul>
          <li>0 <bean:message key="menu.messages"/>.</li>
          <%
          	if (amountTasks > 0) {
          %>
          <li><a href="viewPendingTasks.do"><%=amountTasks%> <bean:message key="menu.tasks"/></a>.</li>
          <%} else {%>
          <li>0 <bean:message key="menu.tasks"/>.</li>  
          <%}%>
          <li>0 <bean:message key="menu.peopleOnline"/>.</li>
        </ul>
	  </dd>
	  <dt><bean:message key="courses.yourCourses"/></dt>
	  <dd>
	    <ul>
	      <logic:iterate id="course" name="userCourses">
			<li><h3>
			  	<bean:define id="courseId" name="course" property="id"/>
				<a href="viewCourse.do?courseId=<%=courseId%>">
				<bean:write name="course" property="name"/></a></h3>
			</li>
	      </logic:iterate>
	      
	    </ul>
	  </dd>
	</dl>
	</div>
	<div id="areas">
		<dl class="insert2">
			<html:form action="/searchCourse" focus="course" onsubmit="return validateSearchCourseForm(this);">
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
    <html:javascript formName="searchCourseForm"/>
    </body>
</html:html>
