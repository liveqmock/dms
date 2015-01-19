<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:机构管理
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<jsp:include page="/head.jsp" />
<title>组织管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/sysmng/orgmng/OrgMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/sysmng/orgmng/OrgMngAction/delete.ajax";
var syncCacheUrl = "<%=request.getContextPath()%>/sysmng/orgmng/OrgMngAction/synchronize.ajax";
//同步方法
var syncUrl = "<%=request.getContextPath()%>/DicTreeAction/expOrg_DeptDic.ajax?type='zzjg'";
$(function()
{
	$("#btn-search").click(function(){
		var $f = $("#fm-orgSearch");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-orgList");
	});
	
	$("#btn-addOrg").click(function(){
		var options = {max:false,width:720,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/orgEdit.jsp?action=1", "editOrg", "新增组织", options);
	});
	
	$("#btn-sync").click(function(){
		sendPost(syncUrl,"btn-sync",'','',"true");
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:720,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/orgEdit.jsp?action=2", "editOrg", "修改组织", options);
}

var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl +"?orgId="+$(rowobj).attr("ORG_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}

function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-orgList").removeResult($row);
		//同步集群节点（业务功能不需要）
		var syncUrl = syncCacheUrl + "?type=3&orgId="+$row.attr("ORG_ID");
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>
</head>
<body>
<div id="layout" style="width:100%">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 组织管理   &gt; 组织管理</h4>
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="fm-orgSearch">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-orgSearch">
				<tr>
					<td><label>组织代码：</label></td>
				    <td><input type="text" id="orgCode" name="orgCode"  datasource="CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
				    <td><label>组织名称：</label></td>
				    <td><input type="text" id="orgName" name="orgName" datasource="ONAME" operation="like" datatype="1,is_null,60" /></td>
					<td><label>业务类型：</label></td>
				    <td>
				    	<select type="text" id="busType" name="busType" kind="dic" src="<%=DicConstant.YWLX %>" datasource="BUS_TYPE" datatype="1,null,30" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				</tr>
				<tr>
					<td><label>组织类别：</label></td>				    
				    <td>
				    	<select type="text" id="orgType" name="orgType" kind="dic" src="<%=DicConstant.ZZLB %>" datasource="ORG_TYPE" datatype="1,is_null,60" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
					<td><label>组织级别：</label></td>
				    <td><input type="text" id="levelCode" name="levelCode" datasource="LEVEL_CODE" kind="dic" src="<%=DicConstant.JGJB %>" datatype="1,is_null,10" /></td>
				    <td><label>上级组织：</label></td>
				    <td><input type="text" id="pId" name="pId" datasource="PID" kind="dic" src="ZZJG" datatype="1,is_null,30"  /></td>
				</tr>
				<tr>
					<td><label>有效标识：</label></td>
				    <td>
				    	<select type="text" id="status" name="status" datasource="STATUS" kind="dic" src="YXBS" datatype="1,is_null,10" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				    <td><label>业务状态：</label></td>
				    <td>
				    	<select type="text" id="busStatus" name="busStatus" datasource="BUS_STATUS" kind="dic" src="ZZYWZT" datatype="1,is_null,30"  >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="btn-addOrg">新&nbsp;&nbsp;增</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-tab-orgList" >
			<table style="display:none" id="tab-orgList" name="tablist" ref="page-tab-orgList" refQuery="tab-orgSearch" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="COMPANY_ID" >所属公司</th>
							<th fieldname="CODE" ordertype='local' class="desc">组织代码</th>
							<th fieldname="ONAME" >组织全称</th>
							<th fieldname="SNAME" >组织简称</th>
							<th fieldname="PID" >上级组织</th>
							<th fieldname="LEVEL_CODE" >组织级别</th>
							<th fieldname="ORG_TYPE" >组织类别</th>
							<th fieldname="BUS_TYPE" >业务类型</th>
							<th fieldname="STATUS" >有效标识</th>
							<th fieldname="BUS_STATUS" >业务状态</th>
							<th type="link" colwidth="90" title="[编辑]|[删除]" action="doUpdate|doDelete" >操作</th>
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