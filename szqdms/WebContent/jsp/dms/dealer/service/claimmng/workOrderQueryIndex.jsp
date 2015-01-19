<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.org.dms.common.DicConstant"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工单查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/queryWorkOrder.ajax";
var invalidUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/nullityWorkOrder.ajax";
var validityUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/validityWorkOrder.ajax";
//初始化方法
$(function(){
	//查询方法
	$("#btn-search").bind("click",function(event){
		var $f = $("#fm-searchWorkOrder");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-workOrderList");
	});
});
var $dRow;
function doInvalid(rowobj){
	$dRow = $(rowobj);
	var status =$(rowobj).attr("STATUS");
	var workStatus = $(rowobj).attr("WORK_STATUS");
	alert(workStatus);
	if(workStatus==302204){
		alertMsg.warn('工单工单已转索赔单，不可修改为无效。');
	}else{
		if(100201==status){
			var url = invalidUrl + "?workId="+$(rowobj).attr("WORK_ID");
			sendPost(url,"","",deleteCallBack,"true");
		}else{
			alertMsg.warn('工单状态已为无效,无需修改。');
		}
	}
}
function doValidity(rowobj){
	$dRow = $(rowobj);
	var status =$(rowobj).attr("STATUS");
	if(100202==status){
		var url = validityUrl + "?workId="+$(rowobj).attr("WORK_ID");
		sendPost(url,"","",deleteCallBack,"true");
	}else{
		alertMsg.warn('工单状态已为有效,无需修改。');
	}
}
function deleteCallBack(){
	$("#btn-search").click();
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 工单管理   &gt; 工单查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchWorkOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-workOrderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>派工单号：</label></td>
					    <td><input type="text" id="IN_WORK_NO" name="IN_WORK_NO" datasource="T.WORK_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>完工日期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="T.COMPLETE_DATE" datatype="1,is_null,10" onclick="WdatePicker()"  kind="date" operation=">="/>
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="T.COMPLETE_DATE" datatype="1,is_null,10" kind="date" operation="<=" onclick="WdatePicker()" />
				   		 </td>
				   		<td><label>维修人：</label></td>
					    <td><input type="text" id="IN_REPAIR_USER" name="IN_REPAIR_USER" datasource="T.REPAIR_USER" datatype="1,is_null,30" operation="like" /></td>
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
		<div id="page_wrokOrderList" >
			<table style="display:none;width:100%;" id="tab-workOrderList" name="tablist" ref="page_wrokOrderList" refQuery="fm-searchWorkOrder" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="WORK_NO" colwidth="100">派工单号</th>
							<th fieldname="WORK_VIN" colwidth="100">工单VIN</th>
							<th fieldname="WORK_STATUS" colwidth="60">工单状态</th>
							<th fieldname="REPAIR_USER" colwidth="60">维修人</th>
							<th fieldname="GO_DATE" colwidth="100"  ordertype='local' class="desc">出发时间</th>
							<th fieldname="ARRIVE_DATE" colwidth="100" ordertype='local' class="desc">到达时间</th>
							<th fieldname="COMPLETE_DATE" colwidth="100" ordertype='local' class="desc">维修完成时间</th>
							<th fieldname="WORK_TYPE" colwidth="60">派工类型</th>
							<th fieldname="IF_OUT" colwidth="60">是否有外出</th>
							<th fieldname="STATUS" colwidth="60">状态</th>
							<th colwidth="120" type="link" title="[置为无效]|[置为有效]"  action="doInvalid|doValidity" >操作</th>
							
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>