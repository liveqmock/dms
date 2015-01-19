<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>入库明细查询</title>
</head>
<body>
    <div id="layout" style="width: 100%;">
        <div id='background1' class='background'></div>
        <div id='progressBar1' class='progressBar'>loading...</div>
        <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 财务相关   &gt; 入库明细查询</h4>
        <div class="page">
            <div class="pageHeader" >
                    <form method="post" id="fm-searchContract">
                        <!-- 定义隐藏域条件 -->
                        <div class="searchBar" align="left" >
                            <table class="searchContent" id="tab-htcx">
                                <!-- 定义查询条件 -->
                                <tr>
                                    <td><label>入库日期：</label></td>
                                    <td>
                                        <input type="text" id="IN_DATE_B" name="IN_DATE_B" style="width: 75px;" datasource="T.IN_DATE" datatype="0,is_null,30"
                                                onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'IN_DATE_E\')}'})" kind ="date" operation=">="/> 
                                        <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
                                        <input type="text" id="IN_DATE_E" name="IN_DATE_E" style="width: 75px; margin-left: -20px;" datasource="T.IN_DATE" datatype="0,is_null,30"
                                                onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'IN_DATE_B\')}'})" kind ="date"  operation="<="/>
                                    </td>
                                    <td><label>入库单号：</label></td>
                                    <td><input type="text" id="IN_NO" name="IN_NO" datasource="T.IN_NO"  operation="like" datatype="1,is_digit_letter,30" /></td>

                                </tr>
                                <tr>
                                    <td><label>打印日期：</label></td>
                                    <td>
                                        <input type="text" id="PRINT_DATE_B" name="PRINT_DATE_B" style="width: 75px;" datasource="T.PRINT_DATE" datatype="1,is_null,30"
                                                onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                        <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                        <input type="text" id="PRINT_DATE_E" name="PRINT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="T.PRINT_DATE" datatype="1,is_null,30"
                                                onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                    </td>
                                    <td><label>配件代码：</label></td>
                                    <td><input type="text" id="PART_NO" name="PART_NO" datasource="T.PART_CODE"  operation="like" datatype="1,is_null,1000" /></td>
                                </tr>
                                <tr>
                                    <td><label>发货方代码：</label></td>
                                    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="T.SUPPLIER_CODE"  operation="like" datatype="1,is_digit_letter,30" /></td>
                                    <td><label>发货方名称：</label></td>
                                    <td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datasource="T.SUPPLIER_NAME"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
                                </tr>
                                <tr>
                                    <td><label>采购单号：</label></td>
                                    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="T.ORDER_NO" operation="like" datatype="1,is_digit_letter,30" /></td>
                                    <td><label>配送号：</label></td>
                                    <td colspan="3">
                                        <input type="text" id="DISTRIBUTION_NO" name="DISTRIBUTION_NO" datasource="T.DISTRIBUTION_NO" operation="=" datatype="1,is_null,300" />
                                    </td>
                                </tr>
                                <tr>
                                    <td><label>入库类型：</label></td>
                                    <td>
                                        <select name="IN_TYPE" id="IN_TYPE"  datasource="T.IN_TYPE" kind="dic" src="<%=DicConstant.RKLX %>" operation="=" datatype="1,is_null,6">
                                            <option value="-1" selected="selected">--</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
			                        <td><label>入库金额（元）：</label></td>
			                        <td><input type="text" id="orderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
			                    	<td><label>采购金额（元）：</label></td>
			                        <td colspan="3"><input type="text" id="sendOrderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
			                   </tr>
                            </table>
                            <div class="subBar">
                                <ul>
                                    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                                    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导出数据</button></div></div></li>
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
                                    <th fieldname="PART_CODE" colwidth="100px">配件代码</th>
                                    <th fieldname="PART_NAME" >配件名称</th>
                                    <!-- <th fieldname="WAREHOUSE_NAME" colwidth="80px">配件图号</th> -->
                                    <th fieldname="DISTRIBUTION_NO" >配送号</th>
                                    <th fieldname="IN_TYPE" >入库类型</th>
                                    <th fieldname="WAREHOUSE_NAME" >入库仓库</th>
                                    <th fieldname="ORDER_NO">订单号</th>
                                    <th fieldname="IN_NO">入库单号</th>
                                    <th fieldname="IN_DATE" colwidth="75px" >入库日期</th>
                                    <th fieldname="PRINT_DATE" colwidth="75px" >打印日期</th>
                                    <th fieldname="IN_AMOUNT" colwidth="50px">入库数量</th>
                                    <th fieldname="PLAN_PRICE" refer="amountFormat" align="right">入库计划价</th>
                                    <th fieldname="PLAN_AMOUNT" refer="amountFormat" align="right">入库金额</th>
                                    <th fieldname="PCH_PRICE" refer="amountFormat" align="right">采购价</th>
                                    <th fieldname="PCH_AMOUNT" refer="amountFormat" align="right">采购金额</th>
                                    <th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价</th>
                                    <th fieldname="SALE_AMOUNT" refer="amountFormat" align="right">销售金额</th>
                                    <th fieldname="SUPPLIER_CODE" colwidth="60px">发货方代码</th>
                                    <th fieldname="SUPPLIER_NAME" >发货方名称</th>
                                    <th fieldname="CHECK_USER" >审核员</th>
<!--                                     <th fieldname="SETTLE_STATUS" colwidth="60px" >结算状态</th> -->
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
        <input type="hidden" id="data" name="data" datasource="data" />
    </form>
    <form id="searchAmountFm" method="post" style="display:none">
		<input type="hidden" id="paramsAmount" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
    $("#btn-search").click(function(){
        if($("#IN_DATE_B").val() == "" || $("#IN_DATE_E").val() == ""){
            alertMsg.warn("请选择入库日期范围");
            return;
        }
        var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/StockInDtlQueryAction/queryListInfo.ajax";
        var $f = $("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#paramsAmount").val(sCondition);	
        doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
    })
    
        // 导出
    $("#btn-export").click(function(){
        var $f = $("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        $("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/StockInDtlQueryAction/exportExcel.do");
        $("#exportFm").submit();
    })
    
})
	function callbackSearch(responseText, tabId){
   	 	var searchAmountUrl = "<%=request.getContextPath()%>/part/storageMng/search/StockInDtlQueryAction/getAmount.ajax";
   		sendPost(searchAmountUrl,"", $("#paramsAmount").val() ,callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
   	}
    function callbackShowDetailsInfo(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length > 0)
            {
                for(var i=0;i<rows.length;i++){
                	var unitAmount = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
                	var planAmount = getNodeText(rows[i].getElementsByTagName("PCH_AMOUNT").item(0));
                	$("#orderAmountSum").val(unitAmount);
                	$("#sendOrderAmountSum").val(planAmount);
                }
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
</script>