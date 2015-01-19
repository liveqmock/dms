<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发货详单查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 发货详单查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>货运公司：</label></td>
									<td colspan="3">
										<input type="text" id="CCode" name="CCode" datasource="SH.CARRIER_CODE" operation="like" datatype="1,is_null,30000" 
										src="T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_CODE,CARRIER_NAME}:1=1 AND STATUS = 100201 ORDER BY CARRIER_CODE"
										operation="=" isreload="true" kind="dic"/>
									</td>
								</tr>
								<tr>
									<td><label>发运日期：</label></td>
									<td>
										<input type="text" id="SHIP_DATE_B" name="SHIP_DATE_B" style="width: 75px;" datasource="SH.SHIP_DATE" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'SHIP_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                    <input type="text" id="SHIP_DATE_E" name="SHIP_DATE_E" style="width: 75px; margin-left: -20px;" datasource="SH.SHIP_DATE" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'SHIP_DATE_B\')}'})" kind ="date"  operation="<="/>
									</td>
									<td><label>收货单位：</label></td>
									<td>
			                        	<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
										<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="O.ORG_ID" operation="="/>
			                        </td>
								</tr>
								<tr>
									<td><label>回执单号：</label></td>
									<td><input type="text" id="RECEIPT_NO" name="RECEIPT_NO" datasource="CA.RECEIPT_NO" operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>订单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="O.ORDER_NO" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重置查询条件</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
			</div>
			<div class="pageContent">
				<div id="page_contract" >
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<tr>
									<th fieldname="SHIP_DATE" colwidth="130px">发运日期</th>
									<th fieldname="RECEIPT_NO" >回执单号</th>
									<th fieldname="ORDER_NO" colwidth="155px">订单号</th>
<!-- 									<th fieldname="ORDER_AMOUNT" colwidth="70px">订单金额</th> -->
									<th fieldname="REAL_AMOUNT" align="right" refer="amountFormat">实发金额</th>
									<th fieldname="ORG_NAME" >收货单位</th>
									<th fieldname="CARRIER_NAME" >货运公司</th>
									<th fieldname="CARRIER_CODE" colwidth="80px">货运公司代码</th>
									<th fieldname="DELIVERY_ADDR" >收货地址</th>
									<th fieldname="LICENSE_PLATE" colwidth="70px">车牌</th>
								</tr>
							</thead>
							<tbody>
		                    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
 	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/ShipOrderQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/ShipOrderQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
	// 重置
	$("#btn-clear").click(function(){
		$("input", "#tab-htcx").each(function(index,obj){
			$(obj).val("")
		});
	});
})

    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }
    
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>