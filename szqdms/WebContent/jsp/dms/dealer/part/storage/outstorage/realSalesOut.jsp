<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>实销出库</title>
<script type="text/javascript">
    //变量定义
    //初始化方法
    var url = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/RealSaleOutAction";
    var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $(function(){
        //查询方法
        $("#btn-search").bind("click",function(event){
            // 查询实销单
            searchRealSalesOut();
        });
        
        $("#btn-add").bind("click",function(event){
            var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/outstorage/realSalesOutAdd.jsp?action=1", "realSaleOutAdd", "实销出库新增", options);
        });
    });

    // 查询实销单
    function searchRealSalesOut() {

        var $f =$("#fm-SaleOutSearch");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/realSalesearch.ajax";
        doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-saleOut");
    }
    // 列表编辑链接
    function doUpdate(rowobj) {

        $("td input[type=radio]", $(rowobj)).attr("checked", true);
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/outstorage/realSalesOutAdd.jsp?action=2", "realSaleOutUpdate", "实销出库编辑", options);
    }

    function doDetail(rowobj) { 
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/storage/outstorage/realSaleDetail.jsp", "orderCheckIn", "实销单明细", diaOptions);
    }

    var $row;
    //删除方法，rowobj：行对象，非jquery类型
    function doDeleteOut(rowobj) {
        $row = $(rowobj);
        
        var deleteUrl = url + "/realSaleDelete.ajax?&sale_id=" + $(rowobj).attr("SALE_ID");
        sendPost(deleteUrl, "", "", deleteRealSalesCallBack, "true");
    }

    //删除回调方法
    function deleteRealSalesCallBack(res) {
        try {
            // 查询实销单
            searchRealSalesOut();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    
    function doSaleOut(rowobj) {  
        $row = $(rowobj);
        var count = $(rowobj).attr("COUNT");
        if(!count){
        	alertMsg.warn('请先维护实销单所需配件信息!');
        	return false;
        }else{
        	var updatesaleUrl = url + "/realSaleOut.ajax?&sale_id=" + $(rowobj).attr("SALE_ID")+"&out_no=" + $(rowobj).attr("SALE_NO");
            sendPost(updatesaleUrl, "", "", saleoutCallBack, "true");
        }
        
    }

    //更新回调函数
    function saleoutCallBack(res) {
        try {
            $("#btn-search").trigger("click");
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    function showAnNiu(obj){
        var $tr = $(obj).parent();
        var outType = $tr.find("td").eq(6).attr("code");
        if(outType==<%=DicConstant.SXLX_01%>){
            obj.html("<A class=op title=[编辑] onclick='doUpdate(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[编辑]</A><A class=op title=[实销出库] onclick='doSaleOut(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[实销出库]</A>");
        }else{
        	obj.html("");
        }
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("SALE_ID")+") class='op'>"+$row.attr("SALE_NO")+"</a>";
    }
    function openDetail(SALE_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/realSaleOrderInfoDetail.jsp?SALE_ID="+SALE_ID, "saleOrderDetail", "实销单明细", options,true);
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：仓储管理  &gt; 出库管理   &gt; 实销出库</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-SaleOutSearch">
            <!-- 定义隐藏域条件 -->
            <input type="hidden" id="salestauts" name="salestauts" datasource="SALE_STATUS" datatype="1,is_digit_letter,30" operation="like" />
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-salesSearch">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>实销单号：</label></td>
                        <td><input type="text" id="out-saleNo" name="out-saleNo" datasource="T.SALE_NO" datatype="1,is_null,30" operation="like" /></td>
                        <td><label>客户名称：</label></td>
                        <td><input type="text" id="out-custName" name="out-custName" datasource="T.CUSTOMER_NAME" datatype="1,is_null,30" operation="like" /></td>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="out-partName" name="out-partName" datasource="PART_CODE" datatype="1,is_null,30" action="show" operation="like"/></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_SaleOutlb" >
            <table style="display:none;width:100%;" id="tab-saleOut" name="tablist" ref="page_SaleOutlb" refQuery="fm-SaleOutSearch" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none"></th>
                            <th fieldname="SALE_NO" refer="showLink" ordertype='local' class="desc" colwidth="160">实销单号</th>
                            <th fieldname="CUSTOMER_NAME">客户名称</th>
                            <th fieldname="LINK_PHONE">联系电话</th>
                            <th fieldname="LINK_ADDR">联系地址</th>
                            <th fieldname="OUT_TYPE" colwidth="60">实销类型</th>
                            <th fieldname="SALE_STATUS" colwidth="60">实销状态</th>
                            <th colwidth="105" type="link" refer="showAnNiu" title="[编辑]|[实销出库]"  action="doUpdate|doSaleOut" >操作</th>
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