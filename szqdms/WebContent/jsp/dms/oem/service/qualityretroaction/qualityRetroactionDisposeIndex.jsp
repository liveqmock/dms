<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>质量反馈处理</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction/qualityretroactionDisposeSearch.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#qualityRetroactionform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"qualityRetroactionlist");
	});
});
function doDispose(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/qualityretroaction/qualityRetroactionDisposeOemDetail.jsp", "qualityRetroactionDisposeDetail", "质量反馈处理", options,true); 
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
							<td><input type="text" id="engineNo" name="engineNo" datasource="MV.ENGINE_NO" datatype="1,is_null,100" value=""  operation="like" /></td>
							<td><label>提报日期：</label></td>
						    <td colspan="3">
					    		<input type="text" id="in-ckrq" group="in-ckrq,in-jsrq"style="width:75px;" name="in-ckrq" datasource="WRITE_DATE"  onclick="WdatePicker()" kind="date" operation = ">=" datatype="1,is_date,30" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" group="in-ckrq,in-jsrq"style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="WRITE_DATE"  kind="date" onclick="WdatePicker()" operation = "<=" datatype="1,is_date,30" />
					   		 </td>
						</tr>	
						<tr>
					   		<td><label>VIN：</label></td>
      						<td><input type="text" id="vin-id" name="vin"   datatype="1,is_null,17" datasource="MV.VIN" operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
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
							<th fieldname="CODE" >渠道商代码</th>
							<th fieldname="ONAME" >渠道商名称</th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="ENGINE_NO" >发动机号</th>
							<th fieldname="COM_NAME" >单位名称</th>
							<th fieldname="WRITE_DATE" >填报日期</th>
							<th fieldname="BUY_DATE" >购买日期</th>
							<th fieldname="FAULT_DATE" >故障日期</th>
							<th fieldname="FBACK_STATUS" >反馈状态</th>
							<th colwidth="85" type="link" title="[处理]"  action="doDispose" >操作</th>
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