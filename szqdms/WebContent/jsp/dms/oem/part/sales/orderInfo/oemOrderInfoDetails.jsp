<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>本部订单查询——详情</title>
</head>
<body>
	<div class="tabs" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:changeTabStatus('orderInfoMain')"><span>订单主信息</span></a></li>
					<li><a href="javascript:changeTabStatus('partInfoPage')"><span>配件详细信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page" id="orderInfoMain">
				<div class="pageContent">
						<fieldset>
							<legend align="right"><a onclick="onTitleClick('ddzxiDiv')">&nbsp;订单主信息&gt;&gt;</a></legend>
							<div id="ddzxiDiv">
							<table class="editTable" align="left">
								<tr>
									<td width="100"><label>订单编号：</label></td>
								    <td><input type="text" id="ORDER_NO" value="" readonly="readonly"/></td>
								    <td><label>订单类型：</label></td>
								    <td><input type="text" id="ORDER_TYPE" value=""  readonly="readonly"/></td>
								    <td><label>订单状态：</label></td>
								    <td><input type="text" id="ORDER_STATUS" value="" readonly="readonly"/></td>
								    <td><label>发运状态：</label></td>
								    <td><input type="text" id="SHIP_STATUS" value=""  readonly="readonly"/></td>
								</tr>
								<tr>
									<td><label>开票状态：</label></td>
								    <td><input type="text" id="INVOICE_STATUS" value="" readonly="readonly"/></td>
								    <td><label>运输方式：</label></td>
								    <td><input type="text" id="TRANS_TYPE" value=""  readonly="readonly"/></td>
								    <td><label>延期：</label></td>
								    <td><input type="text" id="IF_DELAY_ORDER" value="" readonly="readonly"/></td>
								    <td><label>渠道内订单：</label></td>
								    <td><input type="text" id="IF_CHANEL_ORDER" value=""  readonly="readonly"/></td>
								</tr>
								<tr>
								    <td><label>仓库代码：</label></td>
								    <td><input type="text" id="WAREHOUSE_CODE" value=""   readonly="readonly"/></td>
								    <td><label>仓库名称：</label></td>
								    <td><input type="text" id="WAREHOUSE_NAME" value=""  readonly="readonly"/></td>
								    <td><label>期望交货日期：</label></td>
								    <td><input type="text" id="EXPECT_DATE" value=""  readonly="readonly"/></td>
								    <td><label>客户名称：</label></td>
								    <td><input type="text" id="CUSTORM_NAME" value=""   readonly="readonly"/></td>
								</tr>
								<tr>
								    <td><label>联系人：</label></td>
								    <td><input type="text" id="LINK_MAN" value=""  readonly="readonly"/></td>
								    <td><label>手机：</label></td>
								    <td><input type="text" id="PHONE" value=""  readonly="readonly"/></td>
								    <td><label>邮编：</label></td>
								    <td><input type="text" id="ZIP_CODE" value=""  readonly="readonly"/></td>
								    <td><label>交货地点：</label></td>
								    <td><input type="text" id="DELIVERY_ADDR" value=""   readonly="readonly"/></td>
								</tr>
								<tr>
								    <td><label>备注：</label></td>
								    <td colspan="3">
								   		<textarea id="REMARKS" style="width:500px;height:50px;" readonly="readonly"></textarea> 
								    </td>
								</tr>
							</table>
							</div>
						</fieldset>	
						<br />
						<fieldset>
							<legend align="right"><a onclick="onTitleClick('amountInfoTable')">&nbsp;付款信息&gt;&gt;</a></legend>
							<div id="fkxiDiv">
							<table class="editTable" align="left" >
								<tr>
									<td width="160"><label>订单总金额：</label></td>
								    <td><input type="text" id="ORDER_AMOUNT" value=""   readonly="readonly"/></td>
								    <td><label>是否免运费：</label></td>
								    <td><input type="text" id="IF_TRANS" value=""   readonly="readonly"/></td>
								    <td><label>是否使用信用额度：</label></td>
								    <td><input type="text" id="IF_CREDIT" value=""   readonly="readonly"/></td>
								</tr>
							</table>
							<div style="clear: both;width:800px;float: left;padding-left: 50px;">
							<table id="amountInfoTable" align="left" style="display: none;">
									<thead>
										<tr>
											<th fieldname="ACCOUNT_TYPE" >账户类型</th>
											<th fieldname="ACCOUNT_TYPE_NAME">账户类型名称</th>
											<th fieldname="AVAILABLE_AMOUNT"  refer="amountFormat" >可用余额（元）</th>
											<th fieldname="PAY_AMOUNT"  refer="amountFormat" >本次使用金额（元）</th>
										</tr>
									</thead>
									<tbody>
				                    </tbody>
							</table>
							</div>
							</div>
						</fieldset>
						<br />
						<fieldset>
							<legend align="right"><a onclick="onTitleClick('checkInfoTable')">&nbsp;审核信息&gt;&gt;</a></legend>
							<div id="shxiDiv" style="clear: both;width:800px;float: left;padding-left: 50px;">
							<table id="checkInfoTable"  align="left" width="70%" style="display: none;">
									<thead>
										<tr>
											<th fieldname="CHECK_USER" >审核人</th>
											<th fieldname="CHECK_DATE">审核时间</th>
											<th fieldname="CHECK_RESULT" >审核结果</th>
											<th fieldname="CHECK_ORG">审核单位</th>
											<th fieldname="REMARKS">备注</th>
										</tr>
									</thead>
									<tbody>
				                    </tbody>
							</table>
							</div>
						</fieldset>
				</div>
			</div>
			
			<div class="page" id="partInfoPage" style="display:none;">
					<fieldset>
						<legend align="right"><a onclick="onTitleClick('partInfoTable')">&nbsp;配件详细信息&gt;&gt;</a></legend>
						<div id="shxiDiv" style="clear: both;float: left;">
						<table id="partInfoTable"  align="left" width="98%" style="display: none;">
								<thead>
									<tr>
										<th fieldname="PART_CODE" >配件代码</th>
										<th fieldname="PART_NAME">配件名称</th>
										<th fieldname="UNIT_PRICE" refer="amountFormat" >单价</th>
										<th fieldname="PLAN_PRICE" refer="amountFormat">计划价</th>
										<th fieldname="PLAN_UNIT">计量单位</th>
										<th fieldname="ORDER_COUNT">订购数量</th>
										<th fieldname="DELIVERY_COUNT">发运数量</th>
										<th fieldname="SIGN_COUNT">签收数量</th>
										<th fieldname="AMOUNT" refer="amountFormat">金额</th>
										<th fieldname="REMARKS">备注</th>
									</tr>
								</thead>
								<tbody>
			                    </tbody>
						</table>
						</div>
				</fieldset>
			</div>
		</div>
		<div class="tabsFooter">
		
		</div>
	</div>
</body>
<form action="" id="formTest"></form>
<script type="text/javascript">

$(function(){
	
	// 关闭按钮
	$("#closeButton").click(function(){
		parent.$.pdialog.closeCurrent();
	});
	
	
	// 获取Index页面审核行对象及订单ID（orderId）
	var orderId = parent.$("#dealerSalesOrderTable").getSelectedRows()[0].attr("ORDER_ID");
 	var selectRowObj = parent.$("#dealerSalesOrderTable").getSelectedRows()[0];
	
 	// 查询订单主信息ActionURL 
	var getOrderInfoActionURL = "<%=request.getContextPath()%>/part/storageMng/search/DealerSalesOrderSearchAction/selectOrderInfoById.ajax?orderId="+orderId;
	
	// 1, 根据orderId查询订单详情
	sendPost(getOrderInfoActionURL,"","",callbackOrderInfoFuncion,null,null);
	
	
	// 根据orderId查询订单主信息的回调函数
	function callbackOrderInfoFuncion(res,sData){
		
		// 此变量保存回调对象中包含的零件JSON信息
		var applicationInfo;
		
		// 判断浏览器
		var explorer = window.navigator.userAgent;
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		
		// 显示对应的值
		showInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 循环显示
	function showInfo(jsonObj){
 		$("body").find("input,textarea").each(function(index,obj){
			var objVal = jsonObj[$(obj).attr("id")];
			if(objVal){
				$(obj).val(objVal);
			}
		}); 
	}
	

})

// 切换Tab内容
function changeTabStatus(pageId){
	if(pageId == "orderInfoMain"){
		$("#orderInfoMain").show();
		$("#partInfoPage").hide();
	}else{
		$("#orderInfoMain").hide();
		$("#partInfoPage").show();
		
		// 调用显示配件详情的方法
		onTitleClick('partInfoTable');
	}
}

var orderId = parent.$("#dealerSalesOrderTable").getSelectedRows()[0].attr("ORDER_ID");	
// 账户查询
var amountTableActionURL = "<%=request.getContextPath()%>/part/storageMng/search/DealerSalesOrderSearchAction/salesOrderAmountById.ajax?orderId="+orderId;
	
// 审核查询
var checkInfoActionURL = "<%=request.getContextPath()%>/part/storageMng/search/DealerSalesOrderSearchAction/checkOrderInfoById.ajax?orderId="+orderId;

// 配件信息查询
var partInfoActionURL = "<%=request.getContextPath()%>/part/storageMng/search/DealerSalesOrderSearchAction/orderPartInfoQuery.ajax?orderId="+orderId;

// 是否Ajax加载付款信息状态：true加载，false不加载，当第一次加载完毕后，将状态置为false，以后就不在加载
var loadfkxiDataStatus = true;

// 是否Ajax加载审核信息状态：true加载，false不加载，当第一次加载完毕后，将状态置为false，以后就不在加载
var loadshxiDataStatus = true;

//是否Ajax加载配件信息状态：true加载，false不加载，当第一次加载完毕后，将状态置为false，以后就不在加载
var loadpartDataStatus = true;
function onTitleClick(id){
	// 获取对象的显示属性
	var displayAttr = $("#"+id).css("display");
	
	if(id.indexOf("amountInfoTable") != -1){
		if(displayAttr == "none" && loadfkxiDataStatus){
			doFormSubmit($("#formTest"),amountTableActionURL,null,1,null,"amountInfoTable");
			loadfkxiDataStatus = false;
		}else{
			$("#"+id).parent().parent().parent().toggle();
		}
	}else if(id.indexOf("checkInfoTable") != -1){
		if(displayAttr == "none" && loadshxiDataStatus){
		 	doFormSubmit($("#formTest"),checkInfoActionURL,null,1,null,"checkInfoTable");
		 	loadshxiDataStatus = false;
		}else{
			$("#"+id).parent().parent().parent().toggle();
		}
	}else if(id.indexOf("partInfoTable") != -1){
		if(displayAttr == "none" && loadpartDataStatus){
		 	doFormSubmit($("#formTest"),partInfoActionURL,null,1,null,"partInfoTable");
		 	loadpartDataStatus = false;
		}else{
			$("#"+id).parent().parent().parent().toggle();
		}
	} else{
		$("#"+id).toggle();
	}
	
 	if(displayAttr == "none"){
		$("#"+id).show();
		
	}else{
		$("#"+id).hide();
	} 
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</html>