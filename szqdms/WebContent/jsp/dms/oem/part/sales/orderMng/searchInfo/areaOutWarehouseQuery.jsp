<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>区域配送中心出库统计</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置： 信息查询 &gt;销售相关&gt;区域配送中心出库统计</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
						<td><label>配送中心：</label></td>
					    <td>
					    	<input type="text" id="dia-orgName"  datatype="1,is_null,1000000000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,1)" operation="=" />
					    	<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,1000000000" datasource="A.ORG_ID" operation="in"/>
					    </td>
                    </tr>
                    <tr>
                        <td><label>日期：</label></td>
                        <td>
                            <input type="text" group="startDate1,endDate1"  id="startDate1" kind="date" name="startDate1" style="width:75px;" datasource="A.OUT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate1,endDate1"  id="endDate1" kind="date" name="endDate1" style="width:75px;margin-left:-30px;" datasource="A.OUT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-clear-index">重&nbsp;&nbsp;置</button></div></div></li>
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
                        <th fieldname="PSZX_CODE" colwidth="100px">配送中心编码</th>
                        <th fieldname="PSZX_NAME" colwidth="100px">配送中心名称</th>
                        <th fieldname="OUT_DATE" colwidth="100px">日期</th>
                        <th fieldname="PART_CODE" colwidth="200px">零件编号</th>
                        <th fieldname="PART_NAME" colwidth="100px">零件名称</th>
                        <th fieldname="UNIT" colwidth="50px">单位</th>
                        <th fieldname="OUT_COUNTS" colwidth="50px">出库数量</th>
                        <th fieldname="SALE_PRICE" refer="amountFormat" colwidth="80px">销售单价(元)</th>
                        <th fieldname="SALE_AMOUNT" refer="amountFormat" colwidth="80px">销售金额(元)</th>
                        <th fieldname="TO_CODE" colwidth="100px">销往单位编码</th>
                        <th fieldname="TO_NAME" colwidth="100px">销往单位名称</th>
                        <th fieldname="REMARKS" colwidth="100px">备注</th>
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
 	<form id="exportSearchFm" method="post" style="display:none">
		<input type="hidden" id="downSearchOrderId" name="orderId" datasource="O.ORDER_ID" />
	</form>
	
</body>
</html>
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/areaOutWarehouseQuery.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
        	 var $f = $("#fm-condition");
             var sCondition = {};
             if (submitForm($f) == false) {
                 return false;
             }
             sCondition = $f.combined() || {};
             doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
         });

         // 清空查询条件
         $("#btn-clear-index").click(function(){
        	 $("input", "#ycsbTable").each(function(index, obj){
        		 $(obj).val("");
        	 });
         })
         
         // 导出按钮绑定
         $("#btn-export-index").click(function(){
             var $f = $("#fm-condition");
             if (submitForm($f) == false) {
                 return false;
             }
             var sCondition = {};
             sCondition = $f.combined() || {};
             $("#data").val(sCondition);
             var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/downAreaOutWarehouse.do");
             window.location.href = url;
             $("#exportFm").attr("action",url);
             $("#exportFm").submit();
         });
    });

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
    
    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }

</script>