<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
    <div class="page">
        <div class="pageHeader" >
            <form method="post" id="dib-fm-contract">
                <input  type="hidden"  id="sale_id" name="sale_id" datasource="SALE_ID" />
                <table class="editTable" id="order_info">
                    <tr>
                        <td><label>实销单号：</label></td>
                        <td><input type="text" id="in-saleno" readonly name="in-saleno" datasource="SALE_NO" datatype="1,is_null,30"/></td>
                        <td><label>实销类型：</label></td>
                        <td>
                            实销出库
                        </td>
                        <td><label>实销日期：</label></td>
                        <td><input type="text" id="in-saleno" readonly name="in-saleno" datasource="SALE_DATE" datatype="1,is_null,30"/></td>
                    </tr>
                    <tr>
                       <td><label>客户名称：</label></td>
                       <td><input type="text" id="in-cusname" name="in-cusname" readonly datasource="CUSTOMER_NAME" datatype="1,is_null,30"/></td>
                          <td><label>联系电话：</label></td>
                       <td><input type="text" id="in-phone" name="in-phone" readonly datasource="LINK_PHONE" datatype="1,is_null,30"/></td>
                          <td><label>联系地址：</label></td>
                        <td>
                            <input type="text" id="in-addr"  name="in-addr" readonly style="width:300px;" datasource="LINK_ADDR" datatype="1,is_null,30" />
                          </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="pageHeader" >
         <form method="post" id="dib-fmserch-contract">
            
        </form> 
        </div>
<div class="pageContent">
        <div id="page_partList" >
            <table style="display:none;width:100%;" id="tab-partList" name="tab-partList" ref="page_partList" refQuery="fm-partsearch" >
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style="display: none;"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="MIN_PACK">最小包装数量</th>
                            <th fieldname="UNIT">单位</th>
                            <th fieldname="SALE_COUNT" >实销出库数量</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount">零售价</th>
                            <th fieldname="AMOUNT" refer="formatAmount">金额</th>
                            <th fieldname="STOCK_ID" style="display: none;" >仓储ID</th>
                        </tr>
                    </thead>
            </table>
        </div>
            <div class="formBar">
            <ul>
                <li><div class="button"><div class="buttonContent"><button class="close" id="dib-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
//变量定义
//查询提交方法
var partSearchUrl = "<%=request.getContextPath()%>/part/storageMng/enterStorage/RealSaleInAction/realSaleDtlsearch.ajax";
//初始化方法
$(function(){
    var selectedRows = $("#tab-saleOut").getSelectedRows();
    setEditValue("dib-fm-contract",selectedRows[0].attr("rowdata"));
    var sale_id= $("#sale_id").val();
    
    var $f = $("#dib-fmserch-contract");
    var sCondition = {};
    sCondition = $("#dib-fmserch-contract").combined() || {};
    var searchurl =partSearchUrl+"?saleId="+$("#sale_id").val(); 
    doFormSubmit($f.eq(0),searchurl,"btn-search",1,sCondition,"tab-partList");
});
var orderCheckIn = $("body").data("orderCheckIn");

</script>