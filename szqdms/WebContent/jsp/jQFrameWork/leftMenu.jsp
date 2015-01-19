<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.org.framework.common.Menu"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.framework.component.orgmanage.MenuManager"%>
<%@ page import="com.org.framework.component.template.UserTemplateManager"%>
<%@ page import="com.org.framework.common.UserTemplate"%>
<% 
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String  contextPath = request.getContextPath();
	String parentName = request.getParameter("parentName");
	if(parentName == null || "".equals(parentName))
			parentName = "";
	String loginStyle = (String)request.getSession().getAttribute("LOGINSTYLE");
	String sysLanguage = (String)request.getSession().getAttribute(Globals.SYS_LANGUAGE);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DMS系统</title>
<link href="<%=contextPath %>/themes/<%=loginStyle %>/style.css" rel="stylesheet" type="text/css" id="tStyle"/>
<link href="<%=contextPath %>/themes/css/core.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="<%=contextPath %>/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<script src="<%=contextPath %>/lib/jquery/jquery.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/jquery/jquery.cookie.js" type="text/javascript"></script>
<script type="text/javascript">
var syslanguage= "<%=sysLanguage %>";
</script>
<script src="<%=contextPath %>/lib/core/sci.core.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.barDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.tree.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.accordion.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.ui.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.theme.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.alertMsg.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.contextmenu.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.tab.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.dialog.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.dialogDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.cssTable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.stable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.taskBar.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.effects.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.panel.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.checkbox.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.history.js" type="text/javascript"></script>
<style>
body 
{
	background:#EAEEF5;
}
</style>
<script type="text/javascript">
$(function()
{
	var initX  = "";
	if(syslanguage == "1")
		initX = "/lib/core/sci.frag.xml";
	else
		initX = "/lib/core/sci.frag.en.xml";
	DWZ.init("<%=contextPath%>/" + initX, 
	{
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function()
		{
			initEnv(2);
		}
	});
});
function doOpenExtWindow(link)
{
	var $a = $(link);
	var title = "newwin";
	var url = "";
	if($a.attr("rel")=="HDQSPDTB_D") //汉德桥索赔单提报
	{
		title = "HDQSPDTB_D";
		url = "http://service.handeaxle.com:8099/faqA/fwzlogin.do?fwzcode=<%=user.getOrgCode()%>&sqlogin=1";		
	}
	if($a.attr("rel")=="GDWH_D") //工单维护
	{
		title = "GDWH_D"
		url = "http://www.sqllzx.com/default.aspx?usersn=<%=user.getOrgCode()%>&Type=sxzq";	
	}
	if($a.attr("rel")=="FWWXSJ") //服务维修数据
	{
		title = "FWWXSJ"
		url = "http://www.sxzqrs.com/wxsj/index.htm";	
	}
	var win = top.window.open(url,title);
}
</script>
</head>

<body scroll="no" >
	<div id="layout" >
		<div id="sidebar" style="left:0px">
		<div class="accordion" fillSpace="sidebar" id="childMenu" >
				<%
						if(!"".equals(parentName))
						{
							Menu[] menus;
					    	//超级用户，看见系统中所有菜单
					    	if (User.SUPER_USER.equals(user.getPersonKind())) 
					    	{
					      		menus = MenuManager.getInstance().getMenus(parentName);
					    	}
					    	else 
					    	{
					    		menus = user.getAllowedMenus(parentName);
					    	}
					    	if(menus != null)
					    	{
					    		//System.out.println(MenuManager.getInstance().getMenus(user, menus ,parentName ,sysLanguage));
					    		out.println(MenuManager.getInstance().getMenus(user, menus ,parentName ,sysLanguage));
					    		out.println("</ul>");
					    		out.println("</div>");
					    	}
						}else
						{
							//out.println("当前登录部门：");
						}
					%>	
		</div>		
		</div>
	</div>
<script type="text/javascript">
$("body").contextMenu('docMenu', {
	bindings:{
		refresh:function(m,t){
			window.location.reload(true);
		},

		closeMenu:function(m,t){
			try
			{
				if(parent)
					parent.$.pdialog.closeCurrent();
				else if(parent.parent)
						parent.parent.$.pdialog.closeCurrent();
				else if(parent.parent.parent)
						parent.parent.parent.$.pdialog.closeCurrent();
					 else
						$.pdialog.closeCurrent();
			}catch(e)
			{
			}
		}
	}	
});
</script>
</body>
</html>