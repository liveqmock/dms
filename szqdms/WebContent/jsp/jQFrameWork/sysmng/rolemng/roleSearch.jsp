<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String gsid = user.getCompanyId();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:角色管理
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>角色管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/search.ajax";
$(function()
{
	$("#btn-search").bind("click",function(event){
		var $f = $("#fm-searchRole");
		var sCondition = {};
    	sCondition = $("#fm-searchRole").combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-rolelist");
	});
});

//列表查看连接
function doView(rowobj)
{
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:750,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/rolemng/roleDetailView.jsp", "viewRole", "查看角色", options);
}

</script>
</head>
<body>
<div id="layout" style="width:100%">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 角色管理   &gt; 角色管理</h4>
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="fm-searchRole">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-searchRole">
				<tr>
					<td><label>角色代码：</label></td>
				    <td><input type="text" id="code"  name="code" datasource="CODE" datatype="1,is_digit_letter,30" operation="like" value=""/></td>
				    <td><label>角色名称：</label></td>
				    <td><input type="text" id="rname" name="rname" datasource="RNAME" operation="like" datatype="1,is_null,60" value=""/></td>
				</tr>
				<tr>
				    <td><label>所属组织：</label></td>
				    <td><input type="text" id="orgId"  name="orgId" datasource="ORG_ID" kind="dic" src="ZZJG" datatype="1,is_null,30" value=""/>
				    </td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-rolelist" >
			<table style="display:none" width="100%"  id="tab-rolelist" name="tablist" ref="page-rolelist" refQuery="tab-searchRole" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CODE" >角色代码</th>
							<th fieldname="RNAME" >角色名称</th>
							<th fieldname="ROLE_REMARK" >角色描述</th>
							<th fieldname="ORG_ID" >所属组织</th>
							<th fieldname="LEVEL_CODE" >角色级别</th>
							<th fieldname="STATUS" >有效标识</th>
							<th type="link" title="[查看]" action="doView" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>	
</body>
</html>