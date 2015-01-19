<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>手工帐申请</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction/manuallySearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction/manuallyDelete.ajax";
var reportUrl = "<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction/returnOldReport.ajax";
var diaSaveAction="<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction";
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldPartform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"manuallyList");
	});
	$("#add").bind("click",function(){
		$.pdialog.open(webApps+"/jsp/dms/oem/service/finance/manuallyEdit.jsp?action=1", "oldPart", "手工帐申请新增", options,true);
	});
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/finance/manuallyEdit.jsp?action=2", "oldPart", "手工帐申请修改", options,true);
}
var $row;
function doApply(rowobj){
	$row = $(rowobj);
	var url = reportUrl + "?manuallyId="+$(rowobj).attr("MANUALLY_ID")+"&flag="+1;
	sendPost(url,"","",reportCallBack,"true");
}
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?manuallyId="+$(rowobj).attr("MANUALLY_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#manuallyList").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//列表编辑链接(提报)
function reportCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result!='1'){
			if($row) 
				$("#manuallyList").removeResult($row);
		}
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;结算管理&gt;手工帐申请</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartTable">
						<tr>
							<td><label>入账单位代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode"  datasource="ORG_CODE" operation="like" datatype="1,is_null,100"  value="" /></td>
							<td><label>入账类型：</label></td>
							<td><select  type="text" id="manuallyType" name="manuallyType" datasource="MANUALLY_TYPE" kind="dic" class="combox" src="SGRZLX"  datatype="1,is_null,6" value="" >
									<option value="-1" selected>--</option>
								</select>
							</td>
							<td><label>入账途径：</label></td>
							<td><select  type="text" id="manuallyWay" name="manuallyWay" datasource="MANUALLY_WAY" kind="dic" class="combox" src="JSLX"  datatype="1,is_null,6" value="" >
									<option value="-1" selected>--</option>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldPart">
				<table style="display:none;width:100%;"  id="manuallyList" name="manuallyList" ref="oldPart" refQuery="oldPartTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th  fieldname="ORG_CODE">入账服务站代码</th>
							<th  fieldname="ORG_NAME">入账服务站名称</th>
							<th  fieldname="MANUALLY_COSTS">入账金额</th>
							<th  fieldname="MANUALLY_TYPE">入账类型</th>
							<th  fieldname="MANUALLY_WAY">入账路径</th>
							<th  fieldname="MANUALLY_STATUS">入账状态</th>
							<th  fieldname="CLAIM_NO">索赔单号</th>
							<th  fieldname="REMARKS">备注</th>
							<th colwidth="145" type="link"  title="[编辑]|[提报]|[删除]" action="doUpdate|doApply|doDelete">操作</th>
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