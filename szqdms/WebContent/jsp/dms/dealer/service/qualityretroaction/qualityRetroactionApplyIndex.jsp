<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>质量反馈提报</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction/qualityretroactionSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction/qualityretroactionDelete.ajax";
var reportUrl = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction/qualityretroactionReport.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#qualityRetroactionform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"qualityRetroactionlist");
	});
	$("#btn-add").bind("click",function(event){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/dealer/service/qualityretroaction/qualityRetroactionEdit.jsp?action=1", "qualityRetroaction", "质量反馈新增", options,true); 
	});
});

function doDelete(rowobj)
{	
	$row = $(rowobj);
	var url = deleteUrl + "?fbackId="+$(rowobj).attr("FBACK_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/qualityretroaction/qualityRetroactionEdit.jsp?action=2", "qualityRetroaction", "质量反馈编辑", options,true); 
}
function doReport(rowobj){
	$row = $(rowobj);
	var url = reportUrl + "?fbackId="+$(rowobj).attr("FBACK_ID")+"&flag="+1;
	sendPost(url,"","",reportCallBack,"true");
}
function reportCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			if($row) 
				$("#qualityRetroactionlist").removeResult($row);
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#qualityRetroactionlist").removeResult($row);
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
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：质量反馈管理&gt;质量反馈管理&gt;质量反馈提报</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="qualityRetroactionform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="dispatchPartTable">
						<tr>
							<td><label>发动机号：</label></td>
							<td><input type="text" id="engineNo" name="engineNo" datasource="ENGINE_NO" datatype="1,is_null,100" value=""  operation="like" /></td>
							<td><label>提报日期：</label></td>
						    <td colspan="3">
					    		<input type="text" id="in-ckrq" group="in-ckrq,in-jsrq"style="width:75px;" name="in-ckrq" datasource="WRITE_DATE"  onclick="WdatePicker()" datatype="1,is_date,30" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="WRITE_DATE"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
						</tr>	
						<tr>
					   		<td><label>VIN：</label></td>
      						<td><input type="text" id="vin-id" name="vin"   datatype="1,is_null,30" datasource="VIN" operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="qualityRetroactionlist" name="qualityRetroactionlist" ref="preAuth" refQuery="dispatchPartTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="ENGINE_NO" >发动机号</th>
							<th fieldname="COM_NAME" >单位名称</th>
							<th fieldname="WRITE_DATE" >填报日期</th>
							<th fieldname="BUY_DATE" >购买日期</th>
							<th fieldname="FAULT_DATE" >故障日期</th>
							<th fieldname="FBACK_STATUS" >反馈状态</th>
							<th colwidth="145" type="link" title="[提报]|[编辑]|[删除]"  action="doReport|doUpdate|doDelete" >操作</th>
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