<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/head.jsp" />
<title>订单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 销售相关   &gt; 订单查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="ORDER_NO" operation="like" /></td>
                        <td><label>订单类型：</label></td>
                        <td>
                        	<select type="text" id="ORDER_TYPE"  name="ORDER_TYPE" datasource="ORDER_TYPE" kind="dic" src="DDLX" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
                        </td>
                        <td><label>订单状态：</label></td>
                        <td>
                        	<!-- 过滤未提报及审核驳回的状态 -->
                        	<select type="text" id="ORDER_STATUS"  name="ORDER_STATUS" datasource="ORDER_STATUS" kind="dic" src="DDZT" filtercode="202202|202203|202205|202206|202207" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select> 
                        </td>
                    </tr>
                    <tr>
                        <td><label>渠道代码：</label></td>
                        <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="ORG_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>渠道名称：</label></td>
                        <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="ORG_NAME" datatype="1,is_null,30" operation="like" /></td>
                        <td><label>提报日期</label></td>
					    <td>
					    	<input type="text" style="width:75px;" id="APPLY_DATE" name="APPLY_DATE" readonly="readonly" kind="date" operation=">=" datatype="1,is_date,20" dataSource="APPLY_DATE" class="Wdate" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
					    	<input type="text" style="margin-left:-25px; width:75px;" id="APPLY_DATE" name="APPLY_DATE" readonly="readonly" kind="date" operation="<=" datatype="1,is_date,20" dataSource="APPLY_DATE" class="Wdate" onclick="WdatePicker()" />
					    </td>
                    </tr>
                    <tr>
                        <td><label>仓库代码：</label></td>
                        <td><input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>仓库名称：</label></td>
                        <td><input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME" datasource="WAREHOUSE_NAME" datatype="1,is_null,30" operation="like" /></td>
                    </tr>
                    <tr>
                    	<td><label>发运状态</label></td>
					    <td>
						    <select type="text" id="SHIP_STATUS"  name="SHIP_STATUS" datasource="SHIP_STATUS" kind="dic" src="DDFYZT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>开票状态</label></td>
					    <td>
					    	<select type="text" id="INVOICE_STATUS"  name="INVOICE_STATUS" datasource="INVOICE_STATUS" kind="dic" src="KPZT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
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
        <div id="page_index" >
            <table style="display:none;width:100%;" id="dealerSalesOrderTable" name="tablist" ref="page_index" refQuery="fm-pjcx" >
                    <thead>
                        <tr>
							<th type="single" name="XH" unique="ORDER_ID" fieldname="ORDER_ID" style="display:none;"></th>
							<th fieldname="ORDER_NO" refer="orderNoStyle" width="170">订单编号</th>
							<th fieldname="ORDER_TYPE" width="70">订单类型</th>
							<th fieldname="ORDER_STATUS" width="70">订单状态</th>
							<th fieldname="SHIP_STATUS" width="70">发运状态</th>
							<th fieldname="INVOICE_STATUS" width="70">开票状态</th>
							<th fieldname="TRANS_TYPE" >运输方式</th>
							<th fieldname="IF_DELAY_ORDER" width="70">是否延期</th>
							<th fieldname="APPLY_DATE" >提报日期</th>
							<th fieldname="ORDER_AMOUNT" refer="amountFormat">订单总金额</th>
							<th fieldname="REAL_AMOUNT" refer="amountFormat">实发总金额</th>
							<th fieldname="CLOSE_DATE" >关闭订单日期</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
    </div>
    </div>
</div>

<script type="text/javascript">
$(function(){
	
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/salesOrderQuery.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"dealerSalesOrderTable");
	})
})
// 明细查询方法
function doDetail(orderNoObj){
 	$(orderNoObj).parent().parent().parent().find("input:first").prop("checked",true);
    var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/sales/orderInfo/oemOrderInfoDetails.jsp", "", "订单明细查询", options ,true);
}

// 订单编号格式化为超链接
function orderNoStyle(cellObj){
	return "<a href='javascript:void(0)' onclick='doDetail(this)' style='color:red;'>"+$(cellObj).html()+"</a>";
}

// 金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>

</body>
</html>