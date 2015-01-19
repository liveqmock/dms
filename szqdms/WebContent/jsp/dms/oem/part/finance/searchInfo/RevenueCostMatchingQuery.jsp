<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>收入成本匹配查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt;财务相关&gt;收入成本匹配查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form action="post" id="searchForm">
                <input type="hidden" id="orderId" name="orderId"/>
                <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME"/>
                <input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO"/>
                <input type="hidden" id="dia-ORDER_AMOUNT" name="dia-ORDER_AMOUNT"/>
                <input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE"/>
        </form>
        <form id="exportFm" method="post" style="display:none">
           <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="searchAmountFm" method="post" style="display:none">
			<input type="hidden" id="paramsAmount" name="data" datasource="data" />
		</form>
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>订单号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datatype="1,is_null,30" datasource="A.ORDER_NO" operation="like"/></td>
                        <td><label>订单关闭日期：</label></td>
                        <td>
                            <input type="text" group="startDate3,endDate3"  id="startDate3" kind="date" name="startDate3" style="width:75px;" datasource="A.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate3,endDate3"  id="endDate3" kind="date" name="endDate3" style="width:75px;margin-left:-30px;" datasource="A.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>渠道商：</label></td>
                    <td>
	                    <input type="text" id="dia-orgName" name="dia-orgName"  datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
	                    <input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="A.ORG_ID" operation="="/>
                    </td>
                    </tr>
                    <tr>
                        <td><label>经销额合计（元）：</label></td>
                        <td><input type="text" id="orderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
                    	<td><label>计划金额合计（元）：</label></td>
                        <td colspan="3"><input type="text" id="sendOrderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
                   </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid">
            <table style="display:none;width:100%;" id="tab-grid-index" name="tablist" ref="page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ORDER_NO" refer="showLink">订单号</th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="UNIT_PRICE" refer="amountFormat" align="right">经销合计(元)</th>
                        <th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划合计(元)</th>
                        <th fieldname="CLOSE_DATE" colwidth="135">订单关闭日期</th>
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
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/financeMng/searchInfo/RevenueCostMatchingQueryMngAction/searchOrder.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
         });

         // 导出按钮绑定
         $("#btn-export-index").click(function(){
             var $f = $("#fm-condition");
             if (submitForm($f) == false) {
                 return false;
             }
             var sCondition = {};
             sCondition = $f.combined() || {};
             $("#data").val(sCondition);
             var url = encodeURI("<%=request.getContextPath()%>/part/financeMng/searchInfo/RevenueCostMatchingQueryMngAction/download.do");
             window.location.href = url;
             $("#exportFm").attr("action",url);
             $("#exportFm").submit();
         });

    });

    // 订单查询
    function searchOrder() {
        var $f = $("#fm-condition");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        $("#paramsAmount").val(sCondition);	
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
    }
    function callbackSearch(responseText, tabId){
   	 	var searchAmountUrl = "<%=request.getContextPath()%>/part/financeMng/searchInfo/RevenueCostMatchingQueryMngAction/getAmount.ajax";
   		sendPost(searchAmountUrl,"", $("#paramsAmount").val() ,callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
   	}
    function callbackShowDetailsInfo(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length > 0)
            {
                for(var i=0;i<rows.length;i++){
                	var unitAmount = getNodeText(rows[i].getElementsByTagName("UNIT_AMOUNT").item(0));
                	var planAmount = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
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
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    }
    function openDetail(ORDER_ID){
    	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
   
    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }
</script>