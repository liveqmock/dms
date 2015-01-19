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
<title>实销单信息</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String SALE_ID = request.getParameter("SALE_ID");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var SALE_ID = "<%=SALE_ID%>";
var mngUrl = "<%=request.getContextPath()%>/part/storageMng/search/DealerRaleSaleSearchMngAction";
$(function(){
	$("#dia-partlist").height(document.documentElement.clientHeight-100);
	$("button.close").click(function(){
		var dialog = parent.$("body").data("saleOrderDetail");
		parent.$.pdialog.close(dialog);
		return false;
	});
	var $f = $("");
	var sCondition = {};
   	sCondition = $f.combined() || {};
   	var search = mngUrl+"/realSaleOrderInfoSearch.ajax?SALE_ID="+SALE_ID;
   	sendPost(search,"",sCondition,searchContractCallBack,"false");
   
});
function searchContractCallBack(res)
{
	var rows = res.getElementsByTagName("ROW");
	setEditValue("dia-tab-realsale",res.documentElement);
	searchPromPart();
	return true;
}
function searchPromPart() {
    var $f = $("#dia-fm-partlist");
    var sCondition = {};
    sCondition = $f.combined() || {};
    var partSearchUrl1 =mngUrl+"/realSaleDtlsearch.ajax?saleId="+SALE_ID; 
    doFormSubmit($f,partSearchUrl1,"btn-next",1,sCondition,"dib-tab-partlist");
    $("#dib-tab-partlist").show();
}
</script>
</head>
<body>
<div id="dia-layout">
    <div class="tabs" eventType="click" id="dia-tabs" currentIndex="1">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0)"><span>实销出库单信息</span></a></li>
                    <li><a href="javascript:void(0)"><span>配件出库清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" >
            <div class="page">
            <div class="pageContent" >
                <form method="post" id="dib-fm-realSale" class="editForm" style="width:99%;">
                  <input type="hidden" id="dib-Sale_ID" name="dib-Sale_ID" datasource="SALE_ID"/>
                <div align="left">
                    <fieldset>
                    <table class="editTable" id="dia-tab-realsale">
                        <tr>
                            <td><label>实销单号：</label></td>
                            <td><input id ="dib-SaleNo" name="dib-SaleNo" readonly="readonly" type="text" datasource="SALE_NO"  /></td>
                            <td><label>实销类型：</label></td>
                            <td><input id ="dib-outType" name="dib-outType" readonly="readonly" type="text" datasource="OUT_TYPE" /></td>
                        </tr>
                        <tr id="cgrk" >
                            <td><label>客户名称：</label></td>
                            <td><input type="text" id="customer_name" name="customer_name" datasource="CUSTOMER_NAME" datatype="0,is_null,30"  readonly="readonly"/></td>
                            <td><label>联系电话：</label></td>
                            <td><input type="text" id="link_phone"  name="link_phone" datasource="LINK_PHONE" datatype="0,is_null,30"  readonly="readonly"/></td>
                            <td><label>联系地址：</label></td>
                            <td><input type="text" id="link_addr" name="link_addr"  datasource="LINK_ADDR"  readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td><label>备注：</label></td>
                            <td colspan="5">
                                <textarea id="remark" name="remark" style="width:460px;" datasource="REMARK" datatype="1,is_null,600" readonly="readonly"></textarea>
                            </td>
                        </tr>
                    </table>
                    </fieldset>
                </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-next" name="btn-next">下一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close0" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            </div>
            <div class="page">
                <div class="pageContent" >
                <div align="left">
                <form method="post" id="dia-fm-partlist"></form>
                <div id="dia-partlist">
                <fieldset>
                <legend align="right"><a onclick="onTitleClick('dib-tab-partlist')">&nbsp;配件出库清单&gt;&gt;</a></legend>
                    <table style="display:none;width:100%;" id="dib-tab-partlist" name="dib-tab-partlist" ref="dia-partlist" edit="false" >
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display:none" ></th>
                                <th fieldname="PART_ID" style="display: none;">配件ID</th>
                                <th fieldname="PART_CODE" >配件代码</th>
                                <th fieldname="PART_NAME" >配件名称</th>
                                <th fieldname="UNIT" >单位</th>
                                <th fieldname="MIN_PACK" >最小包装</th>
                                <th fieldname="SALE_COUNT" >出库数量</th>
                                <th fieldname="SALE_PRICE" refer="formatAmount">零售价</th>
                                <th fieldname="AMOUNT" refer="formatAmount">金额</th>
                            </tr>
                        </thead>
                    </table>
                </fieldset>
                </div>
                </div>
                </div>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close1" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>
</body>
</html>