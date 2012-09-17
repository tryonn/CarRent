<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ page import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.access_control.user.User"
		import="br.ufpe.cin.amadeus.system.academic.course.Course"
		import="br.ufpe.cin.amadeus.util.Constants"
		import="java.util.List"
		import="java.util.Date"
		import="java.text.SimpleDateFormat"%>


<logic:notPresent name="user"> 
	<logic:redirect forward="fCourseNotLogged"/>			
</logic:notPresent>

<%
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	Course course = (Course)session.getAttribute("course");
	String courseName = course.getName();
	List<User> teachers = amadeus.listTeachersByCourse(course);
	int teacherListSize = teachers.size();
	String teacherNames = "";
	for (int i = 0; i < teacherListSize; i++) {
		teacherNames += teachers.get(i).getPerson().getName();
		if (i != teacherListSize - 1) 
			teacherNames += ", ";
	}
	
	List<User> assistants = amadeus.listAssistantsByCourse(course);
	int assistListSize = assistants.size();
	String assistNames = "";
	for (int i = 0; i < assistListSize; i++) {
		assistNames += assistants.get(i).getPerson().getName();
		if (i != assistListSize - 1)
			assistNames += ", ";  
	}
	
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
	String initialDate = formater.format(course.getInitialCourseDate());
	String finalDate = formater.format(course.getFinalCourseDate());
	
	User user = (User)session.getAttribute("user");
	int profile = user.getProfile().getConstantProfile();
	if ((profile != Constants.ADMIN) && (profile != Constants.PROFESSOR)) {%>
	<logic:redirect forward="fNotAllowed"/>		
<%  }%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<title><bean:message key="courseForm.title"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link  href="<html:rewrite page="/css.css"/>" type="text/css" rel="stylesheet"/>
	<link  href="<html:rewrite page="/table.css"/>" type="text/css" rel="stylesheet"/>
	<script src="<html:rewrite page="/dwr/util.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/engine.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/Activities.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/Materials.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/ContManag.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/dwr/interface/Module.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/validation.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/javascript.js"/>" type="text/javascript"></script>
	<script src="<html:rewrite page="/material.js"/>" type="text/javascript"></script>
</head>
<body onload="initModuleCreation(); ">
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
				<li><html:link forward="fInsertCourseStepOne"><bean:message key="courseForm.heading"/></html:link></li>
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
			<div>
				<table class="tableDescripCur">
				  <tr> 
				    <td colspan="2" class="headTab"><%=courseName%></td>
				  </tr>
				  <tr> 
				    <td colspan="2"><bean:message key="courseCharacteristics.teachers"/>: <%=teacherNames%><%if (!teacherNames.equals("")){ %>.<%} %></td>
				  </tr>
				  <tr> 
				    <td colspan="2"><bean:message key="courseCharacteristics.assistants"/>: <%=assistNames%><%if (!assistNames.equals("")){ %>.<%} %></td>
				  </tr>
				  <tr> 
				    <td align="right" clas="modDescrip"><bean:message key="courseForm.initialDate"/>: <%=initialDate%></td>
				  </tr>
				  <tr> 
				    <td align="right" clas="modDescrip"><bean:message key="courseForm.finalDate"/>: <%=finalDate%></td>
				  </tr>
			    </table>
			</div>
			<div id="form">  
			 	<table id="module1" class="tableMod">
			 		<!--<a href="javascript:void(0)" onclick="createNewModule();">Criar novo m&oacute;dulo</a>-->
			 		<tr>
    					<td width="50%" class="headTab"><input type="text" name="moduleName1" value="Nome do M&oacute;dulo"/></td>
					    <td align="center" class="headTab"><bean:message key="courseForm.visible"/> <input name="moduleVisible1" type="checkbox" checked="checked" /></td>
					    <td align="right" class="headTab"><select name="moduleNumber1">
					        <option value="1">1</option></select>
    					</td>
  					</tr>
			 		<tr>
    					<td colspan="3"><bean:message key="courseForm.modDesc"/>:</td>
  					</tr>
					<tr>
					    <td colspan="3" class="modDescrip"><textarea name="moduleDescription1" class="modDescriptTextarea">Lorem ipsum sit amet abajour rolis strange text just for fun girls have more than this</textarea></td>
					</tr>
					<tr>
					    <td class="headTab2"><bean:message key="courseForm.materials"/> [<a href="javascript:void(0)" onclick="chooseMaterial();">+</a>]</td>
					    <td colspan="2" class="headTab2"><bean:message key="courseForm.activities"/> [<a href="javascript:void(0)" onclick="chooseActivity();">+</a>]</td>
					</tr>
			 		<tr valign="top">
    					<td>
							<ul id="materialsList1"></ul>
						</td>
    					<td colspan="2">
							<ul id="activitiesList1"></ul>
						</td>
				    </tr>
				    <tr align="right" id="dynamic1" >
    					<td colspan="3"><a href="javascript:void(0)" onclick="saveModuleInEdit();"><bean:message key="courseForm.create"/></a>
    								  / <a href="javascript:void(0)" onclick="cancelModule();"><bean:message key="courseForm.cancel"/></a>
        					          / <a href="javascript:void(0)" onclick="deleteModule();"><bean:message key="courseForm.delete"/></a>
        				</td>
  					</tr>
  				</table>
			</div>
			
			</dl>
		</div>
		<div id="footnote">
			<dl><bean:message key="copyright.title"/></dl>
		</div>
	</div>

<html:javascript formName="courseContentForm"/>	 
</body>
</html:html>
