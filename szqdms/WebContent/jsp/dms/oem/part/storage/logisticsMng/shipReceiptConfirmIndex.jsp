<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:(配件发运单)发运单回执确认
	 Version:1.0
     Author：suoxiuli 2014-09-13
     Remark：TransCostsBalance
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发运单回执确认</title>
<script type="text/javascript">
                                     
var searchUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/searchShip1.ajax";
var confirmUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/recieptConfirmShip.ajax";
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchShipReceiptConfirm");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-shipReceiptConfirmList");
	});
});

//列表回执确认链接
var $row;
function doConfirm(rowobj)
{
	$row = $(rowobj); 
	var url = confirmUrl + "?shipId="+$(rowobj).attr("SHIP_ID");
	sendPost(url,"doConfirm","",settleCallBack,"true");
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

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id="progressBar1" class="progressBar">loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 仓储管理   &gt; 物流信息管理   &gt; 发运单回执确认</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchShipReceiptConfirm">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchShipReceiptConfirm">
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
					    		datasource="CARRIER_CODE" datatype="1,is_null,30" />
					    	<input type="hidden" id="carrierName"  name="carrierName" datasource="A.CARRIER_NAME" />
					    	<input type="hidden" id="carrierId"  name="carrierId" datasource="A.CARRIER_ID" />
					    </td>
					</tr>
					<tr>
						<td><label>回执号：</label></td>
					    <td>
					    	<input type="text" id="shipNO"  name="shipNO" datasource="B.RECEIPT_NO"  datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<!-- li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li-->
						</ul>
					</div>
				</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_shipReceiptConfirmList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-shipReceiptConfirmList" name="tablist" ref="page_shipReceiptConfirmList" refQuery="tab-searchShipReceiptConfirm" >
					<thead>
						<tr><!-- 发运单号、承运商、计划运费、实际运费输入框，备注输入框、结算按钮 -->
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SHIP_NO" >发运单号</th>
							<th fieldname="CARRIER_NAME" >承运商</th>
							<th fieldname="LICENSE_PLATE" >承运车辆牌照</th>
							<th fieldname="DRIVER_NAME" >承运司机</th>
							<th fieldname="SHIP_STATUS" >发运单状态</th>
							<th fieldname="RECEIPT_NO" >回执单号</th>
							<th fieldname="STATUS" >有效标识</th>
							<th fieldname="CREATE_USER" >创建人</th>
							<th colwidth="85" type="link" title="[回执确认]"  action="doConfirm" >操作</th>
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