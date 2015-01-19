<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务派工</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchMngAction/searchWorkOrderDispatch.ajax";
//定义弹出窗口样式
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	doSearch();
	$("#btn-search").bind("click", function(event){
		doSearch();
	});
});
function doSearch(){
	var $f = $("#fm-search");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
}
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/claimmng/workDispatchOemAdd.jsp?action=2&orgId="+$(rowobj).attr("ORG_ID"), "WorkDispatch", "服务派工信息", options);
}
function workDispatchInfoLink(obj)
{
	var $row=$(obj).parent();
	if($row.attr("WORK_ID"))
	{
	return "<a href='#' onclick=showWorkDispatchInfo("+$row.attr("WORK_ID")+") class='op'>"+$row.attr("WORK_NO")+"</a>";
	}else
	{
		return "";
	}
}
function showWorkDispatchInfo(workId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/claimmng/WorkOrderShow.jsp?action=3&workId="+workId, "WorkOrderShow", "工单信息", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 工单管理   &gt; 派工执行单</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form id="fm-search" method="post">
			<input type="hidden" id = "workStatus" name="workStatus" datasource="T.WORK_STATUS" value="302201"/>
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,100" value="" operation="in" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="orgName" name="orgName" datasource="O.SNAME"  operation ="like" datatype="1,is_null,100" value="" /></td>
						<td><label>工单号：</label></td>
						<td><input type="text" id="WORK_NO" name="WORK_NO" datasource="T.WORK_NO" operation="like" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th colwidth="45" type="link" title="[派工]"  action="doUpdate" >操作</th>
						<th fieldname="ORG_CODE" colwidth="80" >渠道商代码</th>
						<th fieldname="ORG_NAME" colwidth="200" >渠道商名称</th>
						<th fieldname="WORK_NO" refer="workDispatchInfoLink" colwidth="120">工单号</th>
						<th fieldname="WORK_VIN" colwidth="120">工单VIN</th>
						<th fieldname="APPLY_USER" >客户名称</th>
						<th fieldname="APPLY_MOBIL" >客户联系电话</th>
						<th fieldname="APPLY_DATE" colwidth="120">报修时间</th>
						<th fieldname="WORK_STATUS" >工单状态</th>						
						<th fieldname="APPLY_ADDRESS" >报修地点</th>
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