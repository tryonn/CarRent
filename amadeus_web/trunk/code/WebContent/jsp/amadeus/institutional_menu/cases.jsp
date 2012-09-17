<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
	<bean:message key="institutionalLinks.cases"/> 
	<html:link forward="fWelcome"><bean:message key="institutionalLinks.back"/></html:link><br/>
	<html:link forward="fMenu"><bean:message key="institutionalLinks.back2"/></html:link>
</body>
</html:html>