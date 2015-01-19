<?xml version="1.0" encoding="UTF-8" ?>
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
<jsp:include page="/head.jsp" />
<title>组织管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/sysmng/orgmng/OrgMngAction/search.ajax";
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
		$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/orgAdd.jsp?action=1", "addOrg", "新增组织", options);
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
	$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/orgAdd.jsp?action=2", "orgAdd", "修改组织", options);
}

var $row;
function doDelete(rowobj)
{
	row = $(rowobj);
	var url = "<%=request.getContextPath()%>/OrgDept/OrgDeptAction.do?method=delete&jgdm="+$(rowobj).attr("JGDM");
	sendPost(url,"delete","",deleteCallBack,"true");
}

function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-userlist").removeResult($row);
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
				    <td><input type="text" id="orgCode" name="orgCode" datasource="CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
				    <td><label>组织名称：</label></td>
				    <td><input type="text" id="orgName" name="orgName" datasource="ONAME" operation="like" datatype="1,is_null,60" /></td>
				</tr>
				<tr>
					<td><label>组织级别：</label></td>
				    <td><input type="text" id="levelCode" name="levelCode" datasource="LEVEL_CODE" kind="dic" src="JGJB" datatype="1,is_null,10" /></td>
				    <td><label>上级组织：</label></td>
				    <td><input type="text" id="pId" name="pId" datasource="PID" kind="dic" src="ZZJG" datatype="1,is_null,30" />
				    </td>
				</tr>
				<tr style="display:none;">
					<td><label>排序方式 ：</label></td>
				    <td>
				    	<select class="combox" id="orderFilter" kind="dic" src="E#JGDM=组织代码:JGMC=组织名称" name="orderFilter" datasource="ORDERFILTER" datatype="1,is_null,10" value="--">
				    		<option value="-1" selected>--</option>
			   			</select>
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
							<th fieldname="STATUS" >状态</th>
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