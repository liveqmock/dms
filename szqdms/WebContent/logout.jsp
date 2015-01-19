<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	try
	{
		User user = (User)session.getAttribute(Globals.USER_KEY);
		com.org.framework.log.LogManager.writeLogoutLog(user);
		session.removeAttribute(Globals.USER_KEY);  
		session.invalidate();
	}catch(Exception e){e.printStackTrace();}
%>
<html>
<head>
<title>
注销  
</title>
<script type="text/JavaScript">
  self.parent.location = "<%=request.getContextPath()%>/index.jsp";
</script>
</head>
<body>
</body>
</html>