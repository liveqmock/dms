<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:(配件发运单)运费调整结算
	 Version:1.0
     Author：suoxiuli 2014-08-22
     Remark：TransCostsBalance
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>运费调整结算</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/TransCostsBalanceAction/search.ajax";
var transSettleUrl = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/TransCostsBalanceAction/transSettle.ajax";
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchTransCostsBala");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-transCostsBalaList");
	});
});

//列表结算连接
var $row;
function doSettle(rowobj)
{
	var actulCosts = $("#actulCosts").val();
	var remarks = $("#remarks").val();
	var pattern = /^[0-9]+(.[0-9]{2})?$/; 
	if (actulCosts == null || actulCosts == "") {
		alert("请输入实际运费！");
		return false;
	}
	if (!pattern.test(actulCosts)) {
		alert("请输入正确的浮点数，并保留两位小数！");
		return false;
	}
	
	$row = $(rowobj); 
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var selectedRows = $("#tab-transCostsBalaList").getSelectedRows();
	setEditValue("fm-tansCostsBala",selectedRows[0].attr("rowdata"));
	
	var $f = $("#fm-tansCostsBala");
	if (submitForm($f) == false) return false;
	var sCondition = {};
	sCondition = $f.combined(1) || {};
	var url = transSettleUrl + "?actulCosts="+actulCosts+"&remarks="+remarks;
	doNormalSubmit($f,url,"doSettle",sCondition,settleCallBack);
}

//删除回调方法
function  settleCallBack(res)
{
	try
	{
		if($row) 
		{   
		    $("#btn-search").trigger("click");
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//实际费用
function actulCosts(obj)
{
    return "<input type='text' style='width:50px;' id='actulCosts'  datasource='ACTUL_COSTS' onblur='doMyInputBlur(this)' maxlength='6'/>";
}

//备注
function remarks(obj)
{
    return "<input type='text' style='width:50px;' id='remarks' datasource='REMARKS' onblur='doMyInputBlur(this)' maxlength='20'/>";
}

//金额格式化
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	
	<div id="progressBar1" class="progressBar">loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 仓储管理   &gt; 物流信息管理   &gt; 运费调整结算</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchTransCostsBala">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchTransCostsBala">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>发运单号：</label></td>
					    <td>
					    	<input type="text" id="shipNO"  name="shipNO" datasource="A.SHIP_NO"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>承运商名称：</label></td>
					    <td>
					    	<input type="text" id="carrierCode"  name="carrierCode" kind="dic" 
					    		src="T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID}:1=1 AND STATUS='100201' " 
					    		datasource="A.CARRIER_CODE" datatype="1,is_null,30" />
					    	<input type="hidden" id="carrierName"  name="carrierName" datasource="A.CARRIER_NAME" />
					    	<input type="hidden" id="carrierId"  name="carrierId" datasource="A.CARRIER_ID" />
					    </td>
					</tr>
					<tr>
						<td><label>到货时间：</label></td>
					    <td>
					    	<input type="text" id="receiveTime1"  name="receiveTime1" operation=">=" dataSource="A.RECEIVE_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="receiveTime2"  name="receiveTime2" operation="<=" dataSource="A.RECEIVE_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
					    </td>
					    <td><label>回执确认时间：</label></td>
					    <td>
					    	<input type="text" id="confirmTime1"  name="confirmTime1" operation=">=" dataSource="A.CONFIRM_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="confirmTime2"  name="confirmTime2" operation="<=" dataSource="A.CONFIRM_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
					    </td>
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
		<div id="page_transCostsBalaList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-transCostsBalaList" name="tablist" ref="page_transCostsBalaList" refQuery="tab-searchTransCostsBala" >
					<thead>
						<tr><!-- 发运单号、承运商、计划运费、实际运费输入框，备注输入框、结算按钮 -->
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SHIP_NO" >发运单号</th>
							<th fieldname="CARRIER_NAME" >承运商名称</th>
							<th fieldname="PLAN_COSTS" refer="amountFormat" align="right">计划运费</th>
							<th fieldname="ACTUL_COSTS" colwidth="50" refer="actulCosts">实际运费</th>
							<th fieldname="RECEIVE_TIME" >到货时间</th>
							<th fieldname="BALANCE_STATUS" >运费结算状态</th>
							<th fieldname="CONFIRM_USER" >回执确认人</th>
							<th fieldname="CONFIRM_TIME" >回执确认时间</th>
							<th fieldname="REMARKS" colwidth="50" refer="remarks">备注</th>
							<th fieldname="STATUS" >有效标识</th>
							<th colwidth="85" type="link" title="[结算]"  action="doSettle" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	<div >
		<form method="post" id="fm-tansCostsBala" class="editForm">
			<input type="hidden" id="dia-shipId" name="dia-shipId" datasource="SHIP_ID" />
			<input type="hidden" id="dia-carrierId" name="dia-carrierId" datasource="CARRIER_ID" />
			<input type="hidden" id="dia-carrierCode" name="dia-carrierCode" datasource="CARRIER_CODE" />
			<input type="hidden" id="dia-carrierName" name="dia-carrierName" datasource="CARRIER_NAME" />
			<input type="hidden" id="dia-planCosts" name="dia-planCosts" datasource="PLAN_COSTS" />
			<input type="hidden" id="dia-actulCosts" name="dia-actulCosts" datasource="ACTUL_COSTS" />
			<input type="hidden" id="dia-remarks" name="dia-remarks" datasource="REMARKS" />
		</form>
	</div>
	</div>
</div>	
</body>
<script type="text/javascript">
function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("carrierCode") == 0)
	{  
		$("#carrierName").val($("#"+id).val());
		
		if($row.attr("CARRIER_ID")){
			$("#carrierId").val($row.attr("CARRIER_ID"));
		}
		return true;
	}

	return true;
}
</script>
</html>