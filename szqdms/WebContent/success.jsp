<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.*"%>
<%@ page import="com.org.framework.Globals"%>
<html>
<head>
<title>
操作成功
</title>
<%
	String extMessage =(String) request.getAttribute(Globals.KEY_SUCCESS_MESSAGE);
	String gotourl = (String) request.getAttribute(Globals.KEY_WHEN_SUCCESS_GOTO);
	String action = (String) request.getAttribute(Globals.KEY_SUCCESS_ACTION);
	if(request.getParameter("goto") != null)
		gotourl = (String) request.getParameter("goto");
	if(request.getParameter("message") != null)
		extMessage = (String) request.getParameter("message");
	if(gotourl == null || gotourl.equals("")) gotourl = "/jsp/framework/menu/MainPage.jsp";
	if(gotourl.charAt(0) == '/') gotourl = request.getContextPath() + gotourl;
	else gotourl = request.getContextPath() + "/" + gotourl;
	if(request.getParameter("action") != null)
		action = (String) request.getParameter("action");
%>
<script type="text/javascript">
function closeP()
{
	var dialog = parent.$("body").data("scfj");
	parent.$.pdialog.close(dialog);
}

</script>
</head>
<body >
<table border=0 width="80%" align="center">
<tr><td align="center">
    <table border=0 align="center">
    <tr><td style="width:399;height:120" align="center" background="images/default/success.gif"></td></tr>
    <tr><td align="center">
	<p style="font-size:16px;color:ee0000;">
<%
if(extMessage!=null)
{
	out.println(extMessage);
}
%>
	</p>
    </td> </tr>
 	<tr><td align=center><input type="button" value="关闭" onclick="closeP()"></td></tr>
    </table>
</td></tr>
</table>
</body>
</html>
