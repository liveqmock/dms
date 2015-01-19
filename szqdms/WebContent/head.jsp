<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contextPath = request.getContextPath();
	String loginStyle = (String)request.getSession().getAttribute("LOGINSTYLE");
	String sysLanguage = (String)request.getSession().getAttribute(Globals.SYS_LANGUAGE);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="copyright" content="Copyright &copy;2012" />
<meta name="author" content="andy.ten@tom.com|xukx0710@icloud.com"> 
<link href="<%=contextPath %>/themes/<%=loginStyle %>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath %>/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath %>/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" /><!--[if IE]>
<link href="<%=contextPath %>/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lte IE 9]>
<script src="<%=contextPath %>/lib/core/speedup.js" type="text/javascript"></script>
<![endif]-->
<script src="<%=contextPath %>/lib/jquery/jquery.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script type="text/javascript">
var syslanguage= "<%=sysLanguage %>";
</script>
<script src="<%=contextPath %>/lib/core/sci.core.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.barDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.drag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.ui.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.tab.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.resize.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.dialog.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.dialogDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.sortDrag.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.cssTable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.stable.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.ajax.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.pagination.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.alertMsg.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.contextmenu.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.effects.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.panel.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.checkbox.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.history.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/core/sci.combox.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/HashMap.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.rightmenu.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.info.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/validate/validate.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.win.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.dic.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/default.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.table.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/mine.dialog.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/My97DatePicker/WdatePicker.js"></script>
<%
	if("2".equals(sysLanguage)){
%>
<script src="<%=contextPath %>/lib/plugins/scisite/mine.core.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/scisite/mine.html.js" type="text/javascript"></script>
<%
	}
%>
<script src="<%=contextPath %>/js/util.js" type="text/javascript"></script>
<script type="text/JavaScript">
//<![CDATA[
//在head标签结束前
$(function()
{
	try
	{
		//重置session时间
		var sessionTime=<%=session.getMaxInactiveInterval()%>;
		top.window.document.getElementById("sessionTime").value = sessionTime;
		//多窗口session
		var frameSessionUserId="";
		var frameSessionId="";
		frameSessionUserId = top.window.document.getElementById("frameSessionUserId").value;
		frameSessionId = top.window.document.getElementById("frameSessionId").value;
		var userId2="<%=user.getUserId()%>";
		var sessionid2="<%=session.getId()%>";
		if(frameSessionUserId!=userId2)
		  //(frameSessionUserId==userId2 && frameSessionId!=sessionid2))
		{
		 	alert("同一台机器有多个用户登录，出现用户互窜现象，请重新登录系统.");
			top.window.location.href = "<%=request.getContextPath() %>/index.jsp";
		}
	}catch(e){}
	
	var initX  = "";
	if(syslanguage == "1")
		initX = "/lib/core/sci.frag.xml";
	else
		initX = "/lib/core/sci.frag.en.xml";
	DWZ.init(webApps + initX, 
	{
		loginUrl:webApps+"/jsp/jQFrameWork/dialog/dialogLogin.jsp？loginStyle=<%=loginStyle%>&zyw="+syslanguage, loginTitle:"登录",	// 弹出登录对话框
		callback:function()
		{
			initEnv(1);
			/**
			if($(".pageContent").next().size()>0)
			{
				if($(".pageContent").next().hasClass("formBar"))
					//$(".pageContent").height($(window).height()-30);
			}else
				$(".pageContent").height($(window).height()-30);
			*/
			$("body").height(document.documentElement.clientHeight);
			try
			{
				initStyle();
			}catch(e){}
			var ajaxbg= $(".background,.progressBar");
			ajaxbg.hide();
		}
	});
	$("body").bind("click",function(){
		//hide dic
		//alert(event.srcElement.tagName);
		try{
			if($("#sidebar",top.$("#leftside")).position().left > 5)
				$(".collapse",top.$("#leftside")).trigger("click");
			var t = event.srcElement;
			if(g_xDic && g_xDic.object.xWin.is(":visible") && t.tagName != "INPUT")
			{
				if(g_xDic.object.xWin.find($(t)).size()>0)
					return true;
				switch(t.tagName)
				{
					case "IMG":
						if(t.type && t.type != "tree")
							g_xDic.hidden();
						break;
					case "DIV":
						if(!t.id || t.id !="treediv")
							g_xDic.hidden();
						break;
					default:
						g_xDic.hidden();
						break;
				}
			}
		}catch(e){}
	});
	/**
	$("body").bind("keydown",function(){
		if(event.keyCode==13)
		{
			var dia = $("body").find(".dialogContent:visible");
			if(dia.size() == 0)
				$("div.buttonActive").find("button:first").trigger("click");
			else
				dia.find("div.buttonActive").find("button:first").trigger("click");
		}
	});
	*/
	/**
	$("body").contextMenu('docMenu', {
		bindings:{
			myPage:function(m,t){
				top.navTab._switchTab(0);
			},
			refresh:function(m,t){
				var curNavTab = top.navTab._getTabs().eq(top.navTab._currentIndex);
				top.navTab._reload(curNavTab,true);
			},
			closeMenu:function(m,t){
				try
				{
					if(parent.navTab)
						parent.navTab.closeCurrentTab();
					else if(parent) parent.$.pdialog.closeCurrent();
					else if(parent.parent) parent.parent.$.pdialog.closeCurrent();
					else if(parent.parent.parent)
							parent.parent.parent.$.pdialog.closeCurrent();
						 else
							$.pdialog.closeCurrent();
				}catch(e)
				{
					if(parent.navTab)
						parent.navTab.closeCurrentTab();
				}
			}
		}
	});
	*/
});
//]]>
</script>