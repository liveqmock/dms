<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.component.orgmanage.OrgRoleManager"%>
<%@page import="com.org.framework.util.Pub"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.component.orgmanage.MenuManager"%>
<%@page import="com.org.framework.common.Menu"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String sysLanguage = (String)request.getSession().getAttribute(Globals.SYS_LANGUAGE);
	String  selectMenusContextPath = request.getContextPath();
	String roleId = Pub.val(request, "roleId");

	String action = Pub.val(request, "action");
	String parentName = request.getParameter("rootname");
	if(parentName == null)
		parentName = "";
	String parentTitle = request.getParameter("roottitle");
	if(parentTitle == null)
		parentTitle = "";
	
    String title = java.net.URLDecoder.decode(parentTitle,"utf-8");
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
	Menu[] childMenus;
	//超级用户，看见系统中所有菜单
	if (User.SUPER_USER.equals(user.getPersonKind())) 
	{
		childMenus = MenuManager.getInstance().getMenus(parentName);
	}
	else 
	{
		if(parentName.equals(""))
			childMenus = user.getAllowedMenus(parentName);
		else
			childMenus = MenuManager.getInstance().getMenus(parentName);
	}
	//System.out.println("debug:"+parentName);
	Menu[] grantMenus = null;
	if(parentName != null && grantMenus == null) 
		grantMenus = OrgRoleManager.getInstance().getGrantedMenusByParent(roleId,parentName);
%>
<!-- 
	 Title:功能选择
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<script src="<%=request.getContextPath() %>/lib/core/sci.tree.js" type="text/javascript"></script>
<div style="width:100%;heigth:100%">
	<div id="menulistShows" style=" float:right; display:block; margin:10px;  overflow:hidden; width:70%; height:100%;border:solid 1px #CCC; line-height:21px; background:#FFF;">
		<div style="float:right;">
			<%
			if(action.equals("0")){
			%>
				<div class="buttonActive"><div class="buttonContent"><button type="button" id="okMenu">授予</button></div></div>
			<%
				}
			%>
			<div class="button"><div class="buttonContent"><button type="button" id="closeMenu">关闭</button></div></div>
		</div>
		<div style="float:right; display:block;overflow:auto; width:100%; height:93%;">
			<ul class="tree treeFolder treeCheck">
				<%
				    	if(!"".equals(parentName))
				    	{
					    		out.println("<li ><a tname='menus' tvalue='"+parentName+"'>"+title+"</a>");
					    		//System.out.println("<li ><a tname='menus' tvalue='"+menus[i].getName()+"'>"+menus[i].getTitle()+"</a>");
					    		out.println("<ul>");
					    		//System.out.println("<ul>");
								 
						    	if(childMenus != null)
						    	{ 
						    		out.println(MenuManager.getInstance().getChildMenusList(grantMenus, childMenus ,parentName , sysLanguage));
						    		//System.out.println(MenuManager.getInstance().getChildMenusList(grantMenus, childMenus ,parentName));
						    	}
						    	out.println("</ul>");
						    	//System.out.println("</ul>");
					    		out.println("</li>");
					    		//System.out.println("</li>");
					    		out.println("</ul>");
						    	//System.out.println("</ul>");
					    		out.println("</li>");
					    		//System.out.println("</li>");
				    	}
				%>
			
			</ul>
		</div>
	</div>
	<br/>
	<div>
		<table class="dlist" style="width:85px;fload:left;">
		<%
		if(menus != null)
	    	for(int i=0;i<menus.length;i++)
	    	{
				out.println("<tr><td>");
				out.println("<a style='cursor:hand;' onclick='reloadMenuList(this)' menutitle='" + menus[i].getTitle() + "' menuname='" + menus[i].getName() + "'>" + menus[i].getTitle() +"</a>");
				out.println("</td></tr>");
	    	}
		%>
		</table>
	</div>
	
	<br/>
	<br/>
	<br/>
		
</div>
<script type="text/javascript">
var saveMenuAction = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/saveRoleMenuMap.ajax";
var syncMenuAction = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/synchronize.ajax";
$(function()
{
	$("#menulistShows").height($.pdialog._current.height()-60);
	$("#okMenu").bind("click",function(){
		var checkedMenus = $("a[tname='menus'][checked='checked'],a[tname='menus'][checked='true']",$("#menulistShows"));
		var menus = "";
		//alert($("#menulistShows").html());
		//alert(checkedMenus.size());
		for(var i=0;i<checkedMenus.size();i++)
		{
			if(menus)
				menus += "," + checkedMenus.eq(i).attr("tvalue");
			else
				menus = checkedMenus.eq(i).attr("tvalue");
		}
		var sUrl = saveMenuAction + "?roleId=<%=roleId%>"+"&rootname=<%=parentName%>";
		sendPost(sUrl,"okMenu",menus,saveRoleMenuCallBack,"true");
		
	});
});
function saveRoleMenuCallBack()
{
	//同步集群节点（业务功能不需要）
	var syncUrl = syncMenuAction + "?type=4&roleId=<%=roleId%>";
	sendPost(syncUrl,"","","","false");
	return true;
}
function reloadMenuList(linkObj)
{
	var name1=encodeURI($(linkObj).attr("menutitle"));
    var name2=encodeURI(name1);
    var roleId;
    if("<%=action%>" == 0)
    	roleId = $("#dia-roleId").val();
    else
    	roleId = $("#tab-rolelist").getSelectedRows()[0].attr("ROLE_ID");
	var url = webApps +"/jsp/jQFrameWork/dialog/MultiSelectMenus.jsp?action=<%=action%>&roleId="+roleId+"&rootname="+$(linkObj).attr("menuname")+"&roottitle="+name2+"";
	var h = document.documentElement.clientHeight;
	var options = {dialogId:'MultiSelectMenus',width:400,height:h,max:false,maxable:false,mask:true,mixable:false,minable:false,resizable:false,drawable:false};
	$.pdialog.reload(url,options);
}

//弹出窗体
var dialogMenu = $("body").data("MultiSelectMenus");
$(function()
{
	$("#closeMenu").click(function(){
		$.pdialog.close(dialogMenu);
		return false;
	});

});
</script>