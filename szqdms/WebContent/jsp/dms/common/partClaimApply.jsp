<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包申请明细</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String APPLY_ID = request.getParameter("APPLY_ID");
%>
</head>
<body>
<div id="layout" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="fm-index" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="pjsbsqTable">
                    <tr>
                        <td><label>三包申请单号：</label></td>
                        <td>
                            <input type="hidden" id="applyId-Edit" name="applyId-Edit" datasource="APPLY_ID"/>
                            <input type="text" id="applyOrder-Edit" name="applyOrder-Edit" datasource="APPLY_NO" readonly="readonly"/>
                        </td>
                        <td><label>系统入库单：</label></td>
                        <td>
                            <input type="text" id="inStockOrderNo-Edit" name="inStockOrderNo-Edit" datasource="IN_ORDER_NO" readOnly/>
                            <input type="hidden" id="inStockOrderId-Edit" name="inStockOrderId-Edit" datasource="IN_ORDER_ID"/>
                        </td>
                        <td><label>系统出库单：</label></td>
                        <td>
                            <input type="text" id="outStockOrderNo-Edit" name="outStockOrderNo-Edit" datasource="OUT_ORDER_NO" readOnly/>
                            <input type="hidden" id="outStockOrderId-Edit" name="outStockOrderId-Edit" datasource="OUT_ORDER_ID"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>出库日期：</label></td>
                        <td><input type="text" id="outStockDate-Edit" name="outStockDate-Edit" datasource="OUT_DATE" readonly="readonly"/></td>
                        <td><label>客户名称：</label></td>
                        <td>
                            <input type="text" id="customerName-Edit" name="customerName-Edit" datasource="CUSTOMER_NAME" readonly="readonly" />
                            <input type="hidden" id="linkMan-Edit" name="linkMan-Edit" datasource="LINK_MAN" readonly="readonly" />
                        </td>
                        <td><label>联系电话 ：</label></td>
                        <td><input type="text" id="linkPhone-Edit" name="linkPhone-Edit" datasource="PHONE" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td>
                            <input type="text" id="partCode-Edit" name="partCode-Edit" datasource="PART_CODE" readonly="readonly"/>
                            <input type="hidden" id="partId-Edit" name="partId-Edit" datasource="PART_ID"/>
                        </td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="partName-Edit" name="partName-Edit" datasource="PART_NAME" readonly="readonly" /></td>
                        <td><label>单位 ：</label></td>
                        <td><input type="text" id="unit-Edit" name="unit-Edit" datasource="UNIT" readonly="readonly" /></td>
                    </tr>
                     <tr>
                        <td><label>数量：</label></td>
                        <td><input type="text" id="saleCount-Edit" name="saleCount-Edit" datasource="CLAIM_COUNT" readonly="readonly" /></td>
                        <td><label>经销价：</label></td>
                        <td><input type="text" id="unitPrice-Edit" name="unitPrice-Edit" datasource="SALE_PRICE" readonly="readonly"/></td>
                        <td><label>金额 ：</label></td>
                        <td><input type="text" id="amount-Edit" name="amount-Edit" datasource="AMOUNT" readonly="readonly" /></td>
                    </tr>
                     <tr>
                        <td><label>出库数量：</label></td>
                        <td><input type="text" id="outStockCount-Edit" name="outStockCount-Edit" datasource="OUT_COUNT" readonly="readonly"/></td>
                        <td><label>生产厂家：</label></td>
                        <td>
                            <input type="text" id="supplierName-Edit" name="supplierName-Edit" datasource="SUPPLIER_NAME" readonly="readonly"/>
                            <input type="hidden" id="supplierId-Edit" name="supplierId-Edit" datasource="SUPPLIER_ID"/>
                            <input type="hidden" id="supplierCode-Edit" name="supplierCode-Edit" datasource="SUPPLIER_CODE"/>
                        </td>
                        <td id="source"><label>原入库单：</label></td>
                        <td id="source1">
	                        <input type="text" id="sourceInNo-Edit" name="sourceInNo-Edit" datatype="1,is_null,100" datasource="SOURCE_IN_NO" readonly="readonly" />
	                        <input type="hidden" id="sourceInId-Edit" name="sourceInId-Edit" datasource="SOURCE_IN_ID"/>
	                    </td>
                    </tr>
                    <tr>
                        <td><label>故障情况：</label></td>
                        <td colspan="5"><textarea id="faultConditons-Edit" name="faultConditons-Edit" datasource="FAULT_CONDITONS"  style="width:450px;height:40px;" datatype="1,is_null,500" readonly="readonly" ></textarea></td>
                    </tr>
                    <tr>
                        <td><label>备注：</label></td>
                        <td colspan="5"><textarea id="remarks-Edit" name="remarks-Edit" datasource="REMARKS" style="width:450px;height:40px;" datatype="1,is_null,500" readonly="readonly" ></textarea></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    </div>
</div>
</body>
<script type="text/javascript">
var dialog = $("body").data("claimCyclCheckEdit");
var applyId = '<%=APPLY_ID%>';
     $(function(){
    	// 关闭按钮绑定
         $("button.close").click(function(){
				var dialog = parent.$("body").data("claimCyclCheckEdit");
				parent.$.pdialog.close(dialog);
				return false;
			});
         var $f = $("");
     		var sCondition = {};
        	sCondition = $f.combined() || {};
        	var search = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/searchInfo/ClaimCyclApplyQueryMngAction/getPartClaimInfo.ajax?APPLY_ID="+applyId;
        	sendPost(search,"",sCondition,searchContractCallBack,"false");
    	});
     function searchContractCallBack(res)
     {
     	var rows = res.getElementsByTagName("ROW");
     	setEditValue("pjsbsqTable",res.documentElement);
     	return true;
     }

</script>
</html>