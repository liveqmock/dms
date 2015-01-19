<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String account = user.getAccount();
	String name = user.getPersonName();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>军品采购订单维护</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/ArmyPurchaseOrderMngAction";
$(function()
{
	var userAccount = '<%=account%>';
	var name = '<%=name%>';
	if(userAccount=='ADMIN'){
		$('#PERSON_NAME').attr("src","T#TM_USER A,TM_ORG B:A.ACCOUNT:A.PERSON_NAME{A.ACCOUNT,A.PERSON_NAME}:1=1 AND A.STATUS=<%=DicConstant.YXBS_01 %> AND A.ORG_ID = B.ORG_ID AND B.CODE='XS10905'");
	}else{
		$('#PERSON_NAME').attr('code',userAccount);
        $('#PERSON_NAME').val(name);
        $('#PERSON_NAME').attr("src","");
	}
	$("#btn-search").bind("click", function(event){
		doSearchOrder();
	});
	$("#btn-search").trigger("click");
	$("#btn-add").bind("click", function(event){
		var ACCOUNT = $('#PERSON_NAME').val();
		if(!ACCOUNT){
			alertMsg.warn("请选择采购员.");
			return false;
		}
		$.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrder/armyPurchaseOrderAdd.jsp?action=1", "addContract", "新增订单", diaAddOptions);
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/armyPurchaseOrderAdd.jsp?action=2", "addContract", "修改订单", diaAddOptions);
}
var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = mngUrl + "/purchaseOrderDelete.ajax?PURCHASE_ID="+$(rowobj).attr("PURCHASE_ID");
	sendPost(url, "", "", deletePurchaseCallBack, "true");
}
function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "PERSON_NAME")
	{
		var ACCOUNT = $row.attr("A.ACCOUNT");
		var NAME = $row.attr("A.PERSON_NAME")
		$('#PERSON_NAME').attr('code',ACCOUNT);
        $('#PERSON_NAME').val(NAME);
	}
	return ret;
}
function  deletePurchaseCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-order_info").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doOrderReport(rowobj)
{
	$row = $(rowobj);
	var count = $(rowobj).attr("COUNT");
	if(count){
		var url = mngUrl + "/purchaseOrderReport.ajax?PURCHASE_ID="+$(rowobj).attr("PURCHASE_ID");
		sendPost(url,"report","",reportPurchaseCallBack,"true");
	}else{
		alertMsg.warn('请先维护采购订单所需配件信息!');
		return false;
	}
	
}
function  reportPurchaseCallBack(res)
{
	try
	{
		doApproveOk($row.attr("PURCHASE_ID"));
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doSearchOrder(){
	var searchUrl = mngUrl+"/orderSearch.ajax";
	var $f = $("#fm-searchPchOrder");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
}
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("SPLIT_ID")+") class='op'>"+$row.attr("SPLIT_NO")+"</a>";
}
function openDetail(SPLIT_ID){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/purchaseOrderInfoDetail.jsp?SPLIT_ID="+SPLIT_ID, "pchOrderDetail", "采购拆分单明细", options,true);
}
function doApproveOk(PURCHASE_ID) {
	var changeoptions = {width:1024,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var orderUrl = webApps +"/jsp/dms/oem/part/purchase/purchaseOrder/orderTimeChange.jsp?&PURCHASE_ID="+PURCHASE_ID+"&id=orderpartSel&flag=2";
	$.pdialog.open(orderUrl, "orderpartSel", "采购拆分单", changeoptions);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购订单管理   &gt; 军品采购订单提报</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>采购员：</label></td>
						<td>
					    	<input type="text" id="PERSON_NAME" name="PERSON_NAME" datasource="APPLY_USER" kind="dic" src="" datatype="1,is_null,300" readonly="true"/>
						</td>
					</tr>
					<tr>
					    <td><label>订单编号：</label></td>
					    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="ORDER_NO" operation="like" /></td>
				    	<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %> AND IF_ARMY=<%=DicConstant.SF_01%> AND ERP_NO IS NOT NULL"  datatype="1,is_null,3000"/></td>
					    <td><label>制单日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="CREATE_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="CREATE_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
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
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" refQuery="fm-searchPchOrder" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORDER_NO" >订单编号</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="APPLY_DATE" >制单日期</th>
							<th fieldname="APPLY_USER" >制单人</th>
							<th colwidth="105" type="link" title="[提报]|[编辑]"  action="doOrderReport|doUpdate" >操作</th>
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