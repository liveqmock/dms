<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.*"%>
<%@ page import="com.org.framework.Globals"%>
<html>
<!-- 
	 Title:系统框架空工程
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2011-10
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<title>
Error Message
</title>
<%
	String extMessage =(String) request.getAttribute(Globals.KEY_ERROR_MESSAGE);
	String gotourl = (String) request.getAttribute(Globals.KEY_WHEN_ERROR_GOTO);
	String action = (String) request.getAttribute(Globals.KEY_ERROR_ACTION);
	if(request.getParameter("goto") != null)
		gotourl = (String) request.getParameter("goto");
	if(request.getParameter("message") != null)
		extMessage = (String) request.getParameter("message");
	//if(gotourl == null || gotourl.equals(""))
		gotourl = "/BlueTemplateindex.jsp";
	if(gotourl.charAt(0) == '/') gotourl = request.getContextPath() + gotourl;
	else gotourl = request.getContextPath() + "/" + gotourl;
	if(request.getParameter("action") != null)
		action = (String) request.getParameter("action");
%>
<script type="text/JavaScript">
<!--
var url = "<%=gotourl%>";
function processError()
{
	if(<%=(action==null || action.trim().equals(""))?"true":"false"%>)
	{
		if(top)
			top.location.href = url;
		else if(parent)
		{
			var obj = parent;
			for(;;)
			{
				if(obj.parent) obj = obj.parent;
				else break;
			}
			obj.location.href = url;
		}
		else if(opener)
		{
			opener.location.href = url;
		}
		else location.href = url;
	}
}

function doError()
{
	try
	{
		<%=action%>
	}
	catch(e)
	{
	}
}
-->
</script>
</head>
<body>
<table style="width:100%;border:0px;text-align:center">
<tr><td align="center">
    <table style="border:0px;text-align:center">
    <tr><td style="width:340px;height:270px;" align="center" background="<%=request.getContextPath() %>/images/default/err.gif"></td></tr>
    <tr><td align="center">
	<p style="font-size:22px;color:ee0000;">
<%
	if(extMessage!=null)
	{
    	out.println(extMessage);
    }else
    	out.println("操作失败，请联系系统管理员。");
%>
	</p>
    </td> </tr>
 	<tr><td align=center><BR><a href="<%=request.getContextPath() %>/index.jsp"> 返  回 </a> </td></tr>
    </table>
</td></tr>
</table>
</body>
</html>
