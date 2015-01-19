<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>销售在途物资查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置： 信息查询 &gt;仓储相关&gt;销售在途物资查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
						<td><label>仓库：</label></td>
						<td>										
							<input type="text" id="CODE" name="CODE" datasource="WAREHOUSE_CODE" datatype="0,is_null,300" action="show"
								   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100111, 100110) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
								   operation="=" isreload="true" kind="dic"
							/>
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
                        <th fieldname="PART_CODE" colwidth="100px">配件图号</th>
                        <th fieldname="PART_NAME" colwidth="200px">配件名称</th>
                        <th fieldname="OUT_COUNTS" colwidth="100px">出库数量</th>
                        <th fieldname="PLAN_PRICE" refer="amountFormat" colwidth="80px">计划价(元)</th>
                        <th fieldname="ALL_AMOUNT" refer="amountFormat" >总金额(元)</th>
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
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/querySaleOnroad.ajax";
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
             if (submitForm($f) == false) return false;
     		 var sCondition = $f.combined() || {};
             $("#params").val(sCondition);
     		 $("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/downloadSaleOnroad.do");
     		 $("#exportFm").submit();
         });
    });

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

</script>