<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>实销单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：渠道信息查询  &gt; 仓储相关    &gt;  销售出库单查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-SaleOutSearch">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-salesSearch">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>销售单号：</label></td>
                        <td><input type="text" id="out-saleNo" name="out-saleNo" datasource="ORDER_NO" datatype="1,is_null,30" operation="like" /></td>
                        <td><label>联系人：</label></td>
                        <td><input type="text" id="out-custName" name="out-custName" datasource="LINK_MAN" datatype="1,is_null,30" operation="like" /></td>
                    </tr>
                    <tr>
                    	<td><label>配件：</label></td>
                        <td>
                       		<input type="text" id="partName" name="partName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showPartInfo({showAllPart: 1})" operation="="/>
							<input type="hidden" id="partId" name="partId" datatype="1,is_null,30" datasource="PART_ID" action="show" operation="="/>
                        </td>
                    	<td><label>订单类型：</label></td>
                        <td>
                           <select name="ORDER_TYPE" id="ORDER_TYPE" dataSource="ORDER_TYPE" kind="dic" src="<%=DicConstant.DDLX %>" operation="=" datatype="1,is_null,6"
						    	filtercode="<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>"
						    >
								<option value="-1" selected="selected">--</option>
							</select>
                        </td>
                    </tr>
                    <tr>
                    	<td><label>申请日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" 
                            onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'in-jscjrq\')}'})" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" 
                            onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'in-kscjrq\')}'})" />
                        </td>
                        <td><label>关单日期：</label></td>
                        <td>
                            <input type="text" id="CLOSE_DATE_B"  name="CLOSE_DATE_B" operation=">="  dataSource="CLOSE_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" 
                            onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}'})" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" id="CLOSE_DATE_E"  name="CLOSE_DATE_E" operation="<=" dataSource="CLOSE_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" 
                            onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}'})" />
                        </td>
                    </tr>
                    <tr>
                    	<td><label>客户代码：</label></td>
                        <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="O.ORG_CODE" datatype="1,is_null,30" operation="like" /></td>
                    	<td><label>客户名称：</label></td>
                        <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="O.ORG_NAME" datatype="1,is_null,300" operation="like" /></td>
                    </tr>
					<tr>
						<td><label>出库日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="C.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="C.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
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
                            <th fieldname="ORDER_NO" colwidth="160" refer="showLink">销售单号</th>
                            <th fieldname="ORG_CODE">客户代码</th>
                            <th fieldname="ORG_NAME">客户名称</th>
                            <th fieldname="LINK_MAN">联系人</th>
                            <th fieldname="PHONE">联系电话</th>
                            <th fieldname="DELIVERY_ADDR">交货地址</th>
                            <th fieldname="ORDER_TYPE" colwidth="60">订单类型</th>
                            <th fieldname="ORDER_STATUS" colwidth="60">订单状态</th>
                            <th fieldname="OUT_DATE" colwidth="150">出库日期</th>
                            <th fieldname="AMOUNT" refer="formatAmount" align="right" colwidth="100">实发金额</th>
                            <th fieldname="APPLY_DATE" colwidth="150">申请日期</th>
                            <th fieldname="CLOSE_DATE" colwidth="150">关单日期</th>
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
<script type="text/javascript">
    //变量定义
    //初始化方法
   
    var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $(function(){
        //查询方法
        $("#btn-search").bind("click",function(event){
        	
        	 var url = "<%=request.getContextPath()%>/part/storageMng/search/DealerSaleOrderSearchAction";
        	 var $f =$("#fm-SaleOutSearch");
             var sCondition = {};
             sCondition = $f.combined() || {};
             var searchUrl = url+"/searchInfo.ajax";
             doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-saleOut");
        });
        
        // 导出
        $("#btn-export").click(function(){
        	var $f = $("#fm-SaleOutSearch");
        	if (submitForm($f) == false) return false;
        	var sCondition = $f.combined() || {};
        	$("#params").val(sCondition);
        	$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/DealerSaleOrderSearchAction/exportExcel.do");
        	$("#exportFm").submit();
        })

    });


    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
    
    // 订单详细信息
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    }
    
    function openDetail(ORDER_ID){
    	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }
    
	 // 配件选择回调函数
	 // res = {"PART_ID" : "", "PART_CODE": "", "PART_NAME": ""}
	 function showPartInfoCallback(res){
	 	$("#partName").val(res["PART_NAME"]);
	 	$("#partId").val(res["PART_ID"]);
	 }

</script>
</body>
</html>