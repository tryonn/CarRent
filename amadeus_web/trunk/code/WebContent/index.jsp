<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<logic:present name="org.apache.struts.action.MESSAGE" scope="application">
	<logic:redirect forward="fWelcome"/>
<%--
Redirect default requests to Welcome global ActionForward.
By using a redirect, the user-agent will change address to match the path of our Welcome ActionForward. 
--%>
</logic:present>

<html>
<head><title>Error</title></head>
<body>
	<font color="red">
		ERROR:  Application resources not loaded -- check servlet container
		logs for error messages.
	</font>
</body>
</html>