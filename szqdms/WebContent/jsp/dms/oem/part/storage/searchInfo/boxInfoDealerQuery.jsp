<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>装箱清单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询 &gt;销售相关&gt;装箱清单查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>订单号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datatype="1,is_null,30" datasource="O.ORDER_NO" operation="like"/></td>
                        <td><label>订单类型：</label></td>
                        <td>
                            <select type="text" id="orderType"  name="orderType" datasource="O.ORDER_TYPE" kind="dic" src="DDLX" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
						</tr>
                    </tr>
                    <tr>
                        <td><label>订单状态：</label></td>
                        <td>
                            <select type="text" id="orderStatus"  name="orderStatus" datasource="O.ORDER_STATUS" kind="dic" src="DDZT" 
                            		filtercode="<%=DicConstant.DDZT_02%>|<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_04%>|<%=DicConstant.DDZT_05%>|<%=DicConstant.DDZT_06%>" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>订单申请日期：</label></td>
                        <td>
                            <input type="text" group="startDate1,endDate1"  id="startDate1" kind="date" name="startDate1" style="width:75px;" datasource="O.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate1,endDate1"  id="endDate1" kind="date" name="endDate1" style="width:75px;margin-left:-30px;" datasource="O.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
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
                        <th fieldname="ORDER_NO" refer="showNoInfo" colwidth="150px">订单号</th>
                        <th fieldname="ORDER_TYPE" colwidth="100px">订单类型</th>
                        <th fieldname="ORDER_STATUS" colwidth="100px">订单状态</th>
                        <th fieldname="APPLY_DATE" colwidth="130px">申请日期</th>
<!--                         <th fieldname="ORG_CODE" colwidth="100px">渠道代码</th>
                        <th fieldname="ORG_NAME" colwidth="150px">渠道名称</th> -->
                        <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">订单金额(元)</th>
                        <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">实发金额(元)</th>
                        <th type="link" title="[导出装箱单信息]"colwidth="120px"   action="downInfo" >操作</th>
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
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/BoxInfoDealerQueryAction/queryListInfo.ajax";
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

    });

     // 入库单号超链接
     function showNoInfo($cell){
     	$tr = $cell.parent();
     	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("ORDER_ID")+"\")'>"+$tr.attr("ORDER_NO")+"</a>";
     }

     // 打开详细页面
     function openDetailPage(orderId){
     	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
         $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/boxInfoDealerQueryForDetails.jsp?id="+orderId, "forDetailsPage", "订单明细", options);
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
    
    // 下载装箱单信息
    function downInfo(row){
    	
    	// 订单ID
    	$("#downSearchOrderId").val($(row).attr("ORDER_ID"));
 		var $f = $("#exportSearchFm");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/orderMng/BoxInfoDealerQueryAction/exportExcel.do");
		$("#exportFm").submit();
    }

</script>