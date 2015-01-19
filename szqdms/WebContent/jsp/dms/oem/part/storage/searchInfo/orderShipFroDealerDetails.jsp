<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<div style="width: 100%;">
	<div class="page">
		<div class="pageHeader" >
					<div class="searchBar" align="left" >
						<table class="searchContent" id="detailsTable">
							<tr>
								<td><label>发运单号：</label></td>
								<td><input type="text" id="SHIP_NO_D" /></td>
								<td><label>发运状态：</label></td>
								<td><input type="text" id="SHIP_STATUS_D" /></td>
							</tr>
							<tr>
								<td><label>制单人：</label></td>
								<td>
									<input type="text" id="CREATE_USER_D" />
								</td>
								<td><label>制单日期：</label></td>
								<td><input type="text" id="CREATE_TIME_D" /></td>
							</tr>
							<tr>
								<td><label>备注：</label></td>
								<td colspan="3"><input type="text" id="REMARKS_D" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button class="close" type="button" id="btn-close" >关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
		</div>
		<div></div>
		
	    <fieldset>
        <legend align="right">&nbsp;承运司机信息&gt;&gt;</legend>
		<form id="driverInfoFm" method="post" style="display:none">
			<input type="hidden" datasource="SHIP_ID" value="<%=id%>" />
		</form>
		<div class="pageContent">
			<div id="page_contract_d1">
				<table style="display:none;width:100%;" id="tab-contract-1" name="tablist" ref="page_contract_d1" refQuery="driverInfoFm">
						<thead>
							<tr>
								<th fieldname="LICENSE_PLATE" colwidth="160px">承运车辆</th>
								<th fieldname="DRIVER_NAME" colwidth="160px">承运司机</th>
								<th fieldname="DRIVER_PHONE"  >司机电话</th>
							</tr>
						</thead>
						<tbody>
	                    </tbody>
				</table>
			</div>
		</div>
		</fieldset>
		
	    <fieldset>
        <legend align="right">&nbsp;订单信息&gt;&gt;</legend>
		<form id="orderInfoFm" method="post" style="display:none">
			<input type="hidden" datasource="D.SHIP_ID" value="<%=id%>" />
		</form>
		<div class="pageContent">
			<div id="page_contract_d2">
				<table style="display:none;width:100%;" id="tab-contract-2" name="tablist" ref="page_contract_d2" refQuery="orderInfoFm">
						<thead>
							<tr>
								<th fieldname="ORDER_NO" colwidth="160px">订单编号</th>
								<th fieldname="ORG_NAME" align="right" >提报单位</th>
								<th fieldname="COUNT" >配件数量</th>
								<th fieldname="AMOUNT" refer="amountFormat" align="right">计划金额</th>
								<th fieldname="DELIVERY_ADDR" >收货地址</th>
								<th fieldname="LINK_MAN">联系人</th>
								<th fieldname="PHONE" >联系电话</th>
							</tr>
						</thead>
						<tbody>
	                    </tbody>
				</table>
			</div>
		</div>
		</fieldset>
	</div>
</div>

<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	var id = "<%=id%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/storageMng/search/OrderShipForDealerQueryAction/queryInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?id="+id,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
	// 查询回调函数
	function callbackShowDetailsInfo(res,sData){
		var applicationInfo;							// 此变量保存回调对象中包含的后台查询到的数据
		var explorer = window.navigator.userAgent;		// 判断浏览器
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		
		// 调用显示主信息的函数
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		var rowData = jsonObj["ROW_0"];			// 获取第一行的数据
		$("input","#detailsTable").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).val(rowData[inputName]).prop("readonly", true);
		});
		
		// 调用显示司机信息
		showDirverInfo();
	}
});

// 显示司机信息
function showDirverInfo(){
	var searchURL = "<%=request.getContextPath()%>/part/storageMng/search/OrderShipForDealerQueryAction/queryDriverInfoById.ajax";
    var $f = $("#driverInfoFm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-close", 1, sCondition, "tab-contract-1");
    
    // 显示订单信息
    showOrderInfo();
}

// 显示订单信息
function showOrderInfo(){
	var searchURL = "<%=request.getContextPath()%>/part/storageMng/search/OrderShipForDealerQueryAction/queryOrderInfoById.ajax";
    var $f = $("#orderInfoFm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-close", 1, sCondition, "tab-contract-2");
}
</script>