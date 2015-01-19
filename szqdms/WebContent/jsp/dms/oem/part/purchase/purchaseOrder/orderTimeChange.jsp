<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String PURCHASE_ID = request.getParameter("PURCHASE_ID");
	String id = request.getParameter("id");
	String flag = request.getParameter("flag");
%>
<div id="layout1" style="width:100%;">
	<div class="page" >
	<div >
		<form method="post" id="fm-searchPchOrder1">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-Confirm" >确&nbsp;&nbsp;定</button></div></div></li>
						<li style="display:none"><div class="button"><div class="buttonContent"><button class="close" id="buttonClose" type="button">关&nbsp;&nbsp;闭</button></div></div></li>					
					</ul>
				</div>
			</div>
		</form>
		<form method="post" id="fm-carrierInfo" >
	        <input type="hidden" id="SPLIT_ID" datasource="SPLIT_ID"/>
	        <input type="hidden" id="PLUS_DATE" datasource="REPUIREMENT_TIME"/>
	    </form>
	</div>
	<div class="pageContent">
		<div id="page_order1" style="">
			<table style="width:100%;" id="tab-purchase_info" name="tablist" ref="page_order1" refQuery="fm-searchPchOrder1" >
					<thead>
						<tr>
							<th type="single" name="XH" style=""></th>
							<th fieldname="SPLIT_NO" >采购拆分单号</th>
							<th fieldname="SUPPLIER_CODE" >供应商名称</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="CREATE_TIME" >制单日期</th>
							<th fieldname="REPUIREMENT_TIME" refer="myInput">要求完成时间</th>
							<th colwidth="105" type="link" title="[编辑]"  action="doPartSave">操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
var orderUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction";
var PURCHASE_ID = "<%=PURCHASE_ID%>";
var id = "<%=id%>";
var flag = "<%=flag%>";
$(function()
{
	var searchUrl = orderUrl+"/splitOrderSearch.ajax?PURCHASE_ID="+PURCHASE_ID;
	var $f = $("#tab-orderSearch");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	
	var row;
    $("#btn-Confirm").bind("click", function(event){
    	sendPost(orderUrl + "/updateSplitNoChange.ajax?PURCHASE_ID="+PURCHASE_ID, "", "", confirmCallBack);
	});
});
function confirmCallBack(res){
    try {
    	var editWin = $("body").data("orderpartSel");
    	if(flag =='1'){
    		doClear();
    	}else{
    		 $("#buttonClose").trigger("click");
    		doSearchOrder();
    	}
    	
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function doPartSave(rowobj)
{
	try
	{
		$("td input[type=radio]",$(rowobj)).attr("checked",true);
		var splitId = $(rowobj).attr("SPLIT_ID");
		$("#SPLIT_ID").val(splitId);
		$("#PLUS_DATE").val($(rowobj).find("td").eq(6).find("input:first").val());
		var $f = $("#fm-carrierInfo");
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        if (submitForm($f) == false) return false;
	 	var url = orderUrl + "/updateRepuirementTime.ajax";
        sendPost(url,"",sCondition,diaListSaveCallBack,"true");
	}catch(e){
		
	}
}
function diaListSaveCallBack(res){
	var searchUrl = orderUrl+"/splitOrderSearch.ajax?PURCHASE_ID="+PURCHASE_ID;
	var $f = $("#tab-orderSearch");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
    return true;
}
function myInput(obj){
	return "<input type='text' style='width:110px;' name='myDate' value='"+$(obj).html()+"' datatype='0,is_date,30' onclick='WdatePicker()'/>";
}
</script>
