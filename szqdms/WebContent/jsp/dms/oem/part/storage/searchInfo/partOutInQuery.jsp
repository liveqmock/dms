<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件出入库查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置： 信息查询 &gt;仓储相关&gt;配件出入库汇总查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
						<td><label>单位代码：</label></td>
						<td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="ORG_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
						<td><label>单位名称：</label></td>
						<td><input type="text" id="ONAME" name="ONAME" datasource="ONAME" value="" operation="like" datatype="1,is_null,300" /></td>
                    </tr>
                    <tr>
						<td><label>统计日期：</label></td>
						<td>
							<input type="text" id="IN_DATE_B" name="IN_DATE_B" style="width: 75px;" datasource="IN_DATE_B" datatype="0,is_null,30"
	                                   onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
	                                 <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                 <input type="text" id="IN_DATE_E" name="IN_DATE_E" style="width: 75px; margin-left: -30px;" datasource="IN_DATE_E" datatype="0,is_null,30"
	                                   onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
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
                        <th fieldname="PART_CODE" colwidth="100px">零件号</th>
                        <th fieldname="PART_NAME" colwidth="200px">零件名称</th>
                        <th fieldname="CODE" colwidth="100px">单位代码</th>
                        <th fieldname="ONAME" colwidth="200px">单位名称</th>
                        <th fieldname="QC">期初库存数</th>
                        <th fieldname="RSL">本期入库数</th>
                        <th fieldname="CSL">本期出库数</th>
                        <th fieldname="QM">期末库存数</th>
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
    var searchUrl = "<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/queryPartOutIn.ajax";
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
     		 $("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/OEMSalesOrderSearchAction/downPartOutIn.do");
     		 $("#exportFm").submit();
         });
    });

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

</script>