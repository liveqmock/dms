<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.frameImpl.Constant"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:公司管理
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
var searchUrl = "<%=request.getContextPath()%>/sysmng/orgmng/CompanyMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/sysmng/orgmng/CompanyMngAction/delete.ajax";
var syncCacheUrl = "<%=request.getContextPath()%>/sysmng/orgmng/CompanyMngAction/synchronize.ajax";
$(function()
{
	$("#btn-search").click(function(){
		var $f = $("#fm-companySearch");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-companyList");
	});
	
	$("#btn-addCompany").click(function(){
		var options = {max:false,width:750,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/companyEdit.jsp?action=1", "editCompany", "新增公司", options);
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
	$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/companyEdit.jsp?action=2", "editCompany", "修改公司", options);
}

var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl +"?companyId="+$(rowobj).attr("COMPANY_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}

function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-companyList").removeResult($row);
		//同步集群节点（业务功能不需要）
		var syncUrl = syncCacheUrl + "?type=3&companyId="+$row.attr("COMPANY_ID");
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 组织管理   &gt; 公司管理</h4>
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="fm-companySearch">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-companySearch">
				<tr>
					<td><label>公司代码：</label></td>
				    <td><input type="text" id="companyCode" name="companyCode" datasource="CODE" datatype="1,is_digit_letter,10" operation="like" /></td>
				    <td><label>公司名称：</label></td>
				    <td><input type="text" id="companyName" name="companyName" datasource="CNAME" operation="like" datatype="1,is_null,60" /></td>
				</tr>
				<tr>
				    <td><label>公司类型：</label></td>
				    <td>
				    	<select type="text" id="companyType" name="companyType" datasource="COMPANY_TYPE" kind="dic" src="<%=DicConstant.GSLX %>" datatype="1,is_null,30" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				    <td><label>有效标识：</label></td>
				    <td>
				    	<select type="text" id="status" name="status" datasource="STATUS" kind="dic" src="YXBS" datatype="1,is_null,10" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="btn-addCompany">新&nbsp;&nbsp;增</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-tab-companyList" >
			<table style="display:none" id="tab-companyList" name="tablist" ref="page-tab-companyList" refQuery="tab-companySearch" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CODE" >公司代码</th>
							<th fieldname="CNAME" >公司全称</th>
							<th fieldname="SNAME" >公司简称</th>
							<th fieldname="CONTACT" >联系方式</th>
							<th fieldname="COMPANY_TYPE" >公司类别</th>
							<th fieldname="STATUS" >状态</th>
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