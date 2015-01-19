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
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/bscQueryWorkOrder.ajax";
//初始化方法
$(function(){
	//查询方法
	$("#btn-search").bind("click",function(event){
		var $f = $("#fm-searchWorkOrder");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-workOrderList");
	});
	  //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#fm-searchWorkOrder")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	}); 
	$("#export").bind("click",function(){
		var $f = $("#fm-searchWorkOrder");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/bscDownload.do");
		$("#exportFm").submit();
	});
});
//工单号链接
function workNoInfo(obj)
{
	var $row=$(obj).parent();
	if($row.attr("WORK_NO"))
	{
		return "<a href='#' onclick=workNoInfoDtl("+$row.attr("WORK_ID")+") class='op'>"+$row.attr("WORK_NO")+"</a>";
	}else
	{
		return "";
	}
}
//工单号链接
function licensePlateInfo(obj)
{
	var $row=$(obj).parent();
	if($row.attr("LICENSE_PLATE"))
	{
		return "<a href='#' onclick=licensePlateInfoDtl('"+$row.attr("LICENSE_PLATE")+"','"+$row.attr("WORK_NO")+"') class='op'>"+$row.attr("LICENSE_PLATE")+"</a>";
	}else
	{
		return "";
	}
}
function workNoInfoDtl(workId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/claimmng/workOrderDtl.jsp?workId="+workId, "workNoInfoDtl", "工单明细信息", options);
}
function licensePlateInfoDtl(licensePlate,workNo){
	var url=encodeURI("http://172.16.3.8/HQGPS/WEBGIS/TaskTrack.aspx?vn="+licensePlate+"&wn="+workNo+"");
	window.open(url, "newwindow", "top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
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
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="T.CODE" datatype="1,is_null,10000" operation="like" value=""/></td>
					    <td><label>派工单号：</label></td>
					    <td><input type="text" id="IN_WORK_NO" name="IN_WORK_NO" datasource="T.WORK_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
				   		<td><label>维修人：</label></td>
					    <td><input type="text" id="IN_REPAIR_USER" name="IN_REPAIR_USER" datasource="T.REPAIR_USER" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>完工日期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="T.COMPLETE_DATE" datatype="1,is_null,10" onclick="WdatePicker()"  kind="date" operation=">="/>
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="T.COMPLETE_DATE" datatype="1,is_null,10" kind="date" operation="<=" onclick="WdatePicker()" />
				   		 </td>
						<td><label>工单类型：</label></td>
						<td><select type="text" id="workType" name="workType" datasource="WORK_TYPE" datatype="1,is_null,6" class="combox" kind="dic" src="PGLX" >
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>工单状态：</label></td>
						<td><select type="text" id="workStatus" name="workStatus" datasource="WORK_STATUS" datatype="1,is_null,6" class="combox" kind="dic" src="PGDZT" >
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>派工日期：</label></td>
						<td ><input type="text" group="in-pgrqks,in-pgrqjs"  id="in-pgrqks"  name="in-pgrqks" style="width:75px;" datasource="T.JOBORDER_TIME" datatype="1,is_null,10" onclick="WdatePicker()"  kind="date" operation=">="/>
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-pgrqjs,in-pgrqjs"  id="in-pgrqjs"  name="in-pgrqjs" style="width:75px;margin-left:-30px;"  datasource="T.JOBORDER_TIME" datatype="1,is_null,10" kind="date" operation="<=" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>车牌号：</label></td>
					    <td><input type="text" id="LICENSE_PLATE" name="LICENSE_PLATE" datasource="LICENSE_PLATE" datatype="1,is_null,30" operation="like" /></td> 
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
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
							<th fieldname="WORK_NO" refer="workNoInfo"colwidth="120">派工单号</th>
							<th fieldname="ORG_CODE" colwidth="70">渠道商代码</th>
							<th fieldname="ORG_NAME" colwidth="70">渠道商名称</th>
							<th fieldname="LICENSE_PLATE" refer="licensePlateInfo" colwidth="70">车牌号</th>
							<th fieldname="WORK_VIN" colwidth="90">工单VIN</th>
							<th fieldname="WORK_STATUS" colwidth="70">工单状态</th>
							<th fieldname="REPAIR_USER" colwidth="70">维修人</th>
							<th fieldname="JOBORDER_TIME"colwidth="120" ordertype='local' class="desc">派工时间</th>
							<th fieldname="GO_DATE" colwidth="120" ordertype='local' class="desc">出发时间</th>
							<th fieldname="ARRIVE_DATE" colwidth="120" ordertype='local' class="desc">到达时间</th>
							<th fieldname="COMPLETE_DATE" colwidth="120" ordertype='local' class="desc">维修完成时间</th>
							<th fieldname="WORK_TYPE" colwidth="60">派工类型</th>
							<th fieldname="IF_OUT" colwidth="60">是否有外出</th>
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data"></input>
</form>
</body>
</html>