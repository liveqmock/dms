<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>维修完工</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchMngAction/searchOemWorkOrder.ajax";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function(){
	doSearch();
	
	//查询按钮响应
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
	$.pdialog.open(webApps+"/jsp/dms/oem/service/claimmng/workDispatchFinishOemEdit.jsp", "WorkDispatchFinish", "维修完成信息", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 工单管理   &gt; 维修完工</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
			<input type="hidden" id="WORK_STATUS" name="WORK_STATUS" datasource="T.WORK_STATUS" operation="<=" value="302203"/>
			<table class="searchContent" id="tab-search">
				<tr>
					<td><label>渠道商代码：</label></td>
					<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
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
						<th type="single" name="XH" style="display:none"></th>
						<th colwidth="85" type="link" title="[维修完成]"  action="doUpdate" >操作</th>
						<th fieldname="ORG_NAME" >渠道商名称</th>
						<th fieldname="ORG_CODE" >渠道商代码</th>
						<th fieldname="WORK_NO" >工单号</th>
						<th fieldname="WORK_VIN" >工单VIN</th>
						<th fieldname="REPAIR_USER" >维修人</th>
						<th fieldname="REPAIR_DATE" >维修时间</th>
						<th fieldname="WORK_TYPE" >工单类型</th>
						<th fieldname="IF_OUT" >是否有外出</th>
						<th fieldname="WORK_STATUS" >工单状态</th>
						<th fieldname="APPLY_USER" >报修人</th>
						<th fieldname="APPLY_MOBIL" >报修人电话</th>
						<th fieldname="APPLY_DATE" >报修时间</th>
						<th fieldname="APPLY_ADDRESS" >报修地点</th>
						<th fieldname="APPLY_REMARKS" >报修备注</th>
						<th fieldname="REP_USER_TEL" >维修人手机号</th>
						<th fieldname="REJECTION_DATE" >拒绝日期</th>
						<th fieldname="GO_DATE" >出发时间</th>
						<th fieldname="ARRIVE_DATE" >到达时间</th>
						<th fieldname="COMPLETE_DATE" >维修完成时间</th>
						<th fieldname="START_LONGITUDE" >出发经度</th>
						<th fieldname="END_LONGITUDE" >到达经度</th>
						<th fieldname="START_LATITUDE" >出发纬度</th>
						<th fieldname="END_LATITUDE" >到达纬度</th>
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