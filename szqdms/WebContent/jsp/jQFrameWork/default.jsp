<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.org.frameImpl.Constant"%>
<%@page import="com.org.framework.common.Menu"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.framework.component.orgmanage.MenuManager"%>
<%@ page import="com.org.framework.component.template.UserTemplateManager"%>
<%@ page import="com.org.framework.common.UserTemplate"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String userName = user.getPersonName();
	String loginIp = user.getLoginIP();
	String deptName = user.getPersonKind().equals(Constant.YHLX_01)?"":user.getOrgDept().getSname();
	String loginTime = user.getLoginTime().toLocaleString();
	String  contextPath = request.getContextPath();
	String loginStyle = (String)request.getSession().getAttribute("LOGINSTYLE");
	Menu[] menus;
	//超级用户，看见系统中所有菜单
	if (User.SUPER_USER.equals(user.getPersonKind())) 
	{
  		menus = MenuManager.getInstance().getMenus("");
	}
	else 
	{
		menus = user.getAllowedMenus("");
	}
	int menuLen = 0;
	if(menus != null)
		menuLen = menus.length;
	String sysLanguage = (String)request.getSession().getAttribute(Globals.SYS_LANGUAGE);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>陕重汽DMS系统</title>
<link href="<%=contextPath %>/themes/<%=loginStyle %>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath %>/themes/css/core.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="<%=contextPath %>/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lte IE 9]>
<script src="<%=contextPath %>/lib/core/speedup.js" type="text/javascript"></script>
<![endif]-->
<style type="text/css">
	#header{height:93px;}
	#leftside, #container, #splitBar, #splitBarProxy{top:95px;}
	/* --隐藏时打开 begin
	#sidebar{left:-121px;}
	#container{left:31px;}
	/* end */
</style>
<script src="<%=contextPath %>/lib/jquery/jquery.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script type="text/javascript">
var syslanguage= "<%=sysLanguage %>";
</script>
<script src="<%=contextPath %>/lib/core/sci.core.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.barDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.drag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.tree.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.accordion.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.ui.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.theme.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.switchEnv.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.alertMsg.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.contextmenu.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.navTab.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.tab.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.resize.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.dialog.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.dialogDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.cssTable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.stable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.taskBar.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.ajax.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.pagination.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.panel.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.checkbox.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.history.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.combox.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/clock.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.message.js" type="text/javascript"></script>
<%
	if("2".equals(sysLanguage)){
%>
<script src="<%=contextPath %>/lib/plugins/default.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/HashMap.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/scisite/mine.core.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/scisite/mine.html.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/scisite/mine.pagelist.js" type="text/javascript"></script>
<%
	}
%>
<script type="text/javascript">
var curRootMenu;
$(function()
{
	$("#footer").html("<div class='footer-item'><span text-rel='T-DLYH'>【登录用户:】</span><%=userName%>&nbsp;&nbsp;<span text-rel='T-BM'>【部门:】</span><%=deptName%>&nbsp;&nbsp;<span text-rel='T-DLSJ' >【会话剩余时间:】</span><span id='sessionTime' name='sessionTime' value='<%=session.getMaxInactiveInterval()%>'><%=loginTime%></span>&nbsp;&nbsp;<span text-rel='T-DLIP'>【登录IP:】</span><%=loginIp%>&nbsp;&nbsp;&nbsp;&nbsp;</div>");
	var menuLen = "<%=menuLen%>" + "";
	switch(menuLen)
	{
		case "0" :
				$("#navMenu").hide();
			break;
		case "1" :
				$("#navMenu").hide();
				var $a = $("#navMenu a[name='rootmenu']:first");
				$("#leftMenu").attr("parentName",$a.attr("menuName"));
				var url = "<%=contextPath%>/jsp/jQFrameWork/leftMenu.jsp" + "?parentName="+$("#leftMenu").attr("parentName")+"";
				$("#leftMenu").attr("src",url);
			break;
		default:
			$("#navMenu").show();
			break;
	}
	var initX  = "";
	if(syslanguage == "1")
		initX = "/lib/core/sci.frag.xml";
	else
		initX = "/lib/core/sci.frag.en.xml";
	DWZ.init("<%=contextPath%>/" + initX, 
	{
		loginUrl:"<%=contextPath%>/jsp/jQFrameWork/dialog/dialogLogin.jsp", loginTitle:"登录",	// 弹出登录对话框
		callback:function()
		{
			initEnv();
			if(syslanguage == "2")
			{
				$.html.initUI("global_en");
				$.html.initUI("defaultindex_en");
			}
			//控制sidebar隐藏/显示
			//隐藏时打开
			//$("#leftside").hideLeftMenu();
			$("#leftside").show();
			//隐藏时打开
			//$("#sidebar").css("left",-121);
			//隐藏时打开
			//$("#container").css("left",31);
			///* 不隐藏时打开begin
			$("#navMenu a[name='rootmenu']:first").trigger("click");//去掉
			//$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
			//$("#leftMenu").height($(window).height() - $("#header").height() - 34 + 10);
			//end*/
		}
	});
	//$("#leftside").hideJBar();
	var defH = document.documentElement.clientHeight;
	//$("#mainpage").height(defH-147);
	$("#leftMenu").height(defH-140);
	$("#footer").attr("clientHeight",defH-147);
	showTime();
});
function showTime()
{
	var $sessionTime = $("#sessionTime");
	if(parseInt($sessionTime.attr("value"))>0)
	{
		$sessionTime.val(parseInt($sessionTime.attr("value"))-1);
		$sessionTime.css("color","");
		var hour = Math.floor(parseInt($sessionTime.attr("value"))/(60*60));
		var minute = Math.floor((parseInt($sessionTime.attr("value"))-hour*(60*60))/60);
		var second = Math.floor(parseInt($sessionTime.attr("value"))-hour*(60*60)-minute*60);
		hour=hour<10?'0'+hour:hour;
		minute=minute<10?'0'+minute:minute;
		second=second<10?'0'+second:second;
		$sessionTime.html(""+hour+":"+minute+":"+second);
	}else{
		$sessionTime.html("00:00:00");
		if($sessionTime.css("color")=="")
			$sessionTime.css("color","red");
		else
			$sessionTime.css("color","");
	}
	setTimeout('showTime()',1000);
}

function openLoginWindow(){
	var options = {max:false,mask:true,mixable:true,minable:true,resizable:false,drawable:true,width:1000,height:550};
	$.pdialog.open("<%=contextPath %>/jsp/jQFrameWork/dialog/dialogLogin.jsp", "loginWindow", "切换用户", options);
}
</script>
</head>
<body>
	<!-- 多窗口session -->
	<input type="hidden" id="frameSessionUserId" name="frameSessionUserId" value="<%=user.getUserId()%>" />
	<input type="hidden" id="frameSessionId" name="frameSessionId" value="<%=session.getId()%>" />
	<div id="layout">
		<div id="header">
			<div class="headerNav" >
				<h1><img src="<%=request.getContextPath() %>/images/default/logo1.png" alt="DMS系统 DMS System"  /></h1>
				<ul class="themeList" id="themeList" style="position:absolute;z-index:999;top:14px;right:260px;">
					<li>&nbsp;</li>
					<li><span id="clock"></span></li>
				</ul>
				<div class="buttons-bar">
					<div class="headerIconBox"><a  title="安全退出系统" title-rel="TI-TC" href="<%=contextPath %>/logout.jsp"><img src="<%=contextPath %>/images/quick/exit_btn.png"></img><span>退出系统</span></a></div>
					<div class="headerIconBox"><a  title="关于本系统" title-rel="TI-GYBXT" href="<%=contextPath %>/jsp/jQFrameWork/dialog/helptext.jsp" target="dialog"><img src="<%=contextPath %>/images/quick/about_btn.png"></img><span>帮助说明</span></a></div>
					<div class="headerIconBox"><a  title="切换用户" rel="qhyh" title-rel="TI-QHYH" href="javascript:openLoginWindow();"><img src="<%=contextPath %>/images/quick/switch_btn.png"></img><span>切换用户</span></a></div>
					<div class="headerIconBox"><a  title="修改密码" rel="xgmm" title-rel="TI-GRXX" href="<%=contextPath %>/jsp/jQFrameWork/dialog/changePassword.jsp" target="dialog" ><img src="<%=contextPath %>/images/quick/user_btn.png"></img><span>修改密码</span></a></div>
				</div>
			</div>
			<!-- navMenu -->
			<%
				if(menuLen == 0 || menuLen == 1)
					out.println("<div id='navMenu' class='MenuNav' style='display:none;'>");
				else
					out.println("<div id='navMenu' class='MenuNav' style='position:absolute;z-index:1000;display:'>");
				out.println("<a href='javascript:void(0);' id='MenuHome' class='MenuNavHomeLink' url='' menuName='HomePage'>首页</a>");	
				String url = "";
				String menuname = "";
		    	if(menuLen != 0)
			    	for(int i=0;i<menuLen;i++)
			    	{
			    		 url = contextPath+"/Menu/MenuManagerAction.do?method=getMenusByParent&parentName="+menus[i].getName();
			    		 menuname = "1".equals(sysLanguage)?menus[i].getTitle():menus[i].getTitleEn();
			    		 out.println("<a href='javascript:void(0);' class='MenuNavlinks'  url='"+url+"'name='rootmenu' menuName='"+menus[i].getName()+"'>"+menuname+"</a>");
			    	}
		    	out.println("</div>");
			%>
		</div>
		<div id="leftside" style="display:none">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
					<%
						if("1".equals(sysLanguage)){
					%>
						<div class="navigate"><div style='padding-top:20px;font-size:13px;'>导<p/>航<p/>菜<p/>单<p/></div></div>
					<%
						}else{
					%>
						<div class="navigate"><div style='padding-top:20px;font-size:16px;'>M<br/>E<br/>N<br/>U</div></div>
					<%
						}
					%>	
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2 text-rel="T-DHCD">导航菜单</h2><div text-rel="T-SS" style="display:none;">收缩</div></div>
				<div fillSpace="sidebar" id="childMenu" >
						<iframe id="leftMenu" src="<%=contextPath%>/jsp/jQFrameWork/leftMenu.jsp" parentName="" width="100%" height="100%" frameborder="0" scrolling="yes"></iframe>
				</div>
			</div>
		</div>	
		<div id="container" style="width:100%;">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent" >
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon" text-rel="T-WDZY">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft ">left</div>
					<div class="tabsRight">right</div>
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList" >
					<li><a href="javascript:void(0);" text-rel="T-WDZY">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent" >
					<div class="page" id="div-mainpage"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer" ></div>
<style type="text/css">
a.rollings{
 display:block;/*这个属性是必须的*/
 font-size:12px;
 line-height:21px;
 text-decoration:none;
 font-family:Arial;
}
.shell{
 border:1px solid #aaa;
 width:100%;
 padding:3px 2px 2px 20px;
}
#div1{
 height:21px;
 overflow:hidden;
}
</style>
<script type="text/javascript">
/**
 * added by andy.ten@tom.com 2011-10-19
 */
//doesn't block the load event
 function createIframe() 
 {
     var i = document.createElement("iframe");
     i.id = "mainpage";
     i.src = "<%=contextPath%>/jsp/jQFrameWork/mainpage.jsp";
     i.scrolling = "auto";
     i.setAttribute("frameborder", '0',0);
     i.border="0";
     i.width = "100%";
     i.height = document.documentElement.clientHeight - 147;
     document.getElementById("div-mainpage").appendChild(i);
 };
//Check for browser support of event handling capability
 if (window.addEventListener) window.addEventListener("load", createIframe, false);
 else if (window.attachEvent) window.attachEvent("onload", createIframe);
 else window.onload = createIframe;
$("#navMenu a[name='rootmenu']").click(function(){
	var $this = $(this);
	//$("#navMenu li").attr("class","");
	//$this.parent().attr("class","selected");
	var mn = $this.attr("menuName");
	if(mn == curRootMenu)
	{
		if($("#sidebar",$("#leftside")).position().left <= 0)
			$(".collapse",$("#leftside")).trigger("click");
		return;
	}
	curRootMenu = mn;
	$("#leftMenu").attr("parentName",mn);
	var url = "<%=contextPath%>/jsp/jQFrameWork/leftMenu.jsp" + "?parentName="+$("#leftMenu").attr("parentName")+"";
	$("#leftMenu").attr("src",url);
	if($("#sidebar",$("#leftside")).position().left <= 0)
		$(".collapse",$("#leftside")).trigger("click");
});
$(document).ready(function() {
	$('.headerIconBox').hover(function () {
		$(this).addClass('headerIconBoxHover');
	},function () {
		$(this).removeClass('headerIconBoxHover');
	});
	$('.headerIconBox').click (
		function () {
			$('.headerIconBoxActive').removeClass('headerIconBoxActive');
			$(this).addClass('headerIconBoxActive');
		}
	);
	$('#MenuHome').click (
		function () {
				$('.MenuNavlinksActive').removeClass('MenuNavlinksActive');
				$(this).removeClass('MenuNavHomeHover');
				$(this).addClass('MenuNavHomeLink');
				navTab._switchTab(0);
				return (false);
			}
		);
	$('.MenuNavlinks').click (
		function () {
			$('#MenuHome').removeClass('MenuNavHomeLink');
			$('#MenuHome').addClass('MenuNavHomeHover');
			$('.MenuNavlinksActive').removeClass('MenuNavlinksActive');
			$(this).addClass('MenuNavlinksActive');
			return (false);
		}
	);
});
</script>
</body>
</html>