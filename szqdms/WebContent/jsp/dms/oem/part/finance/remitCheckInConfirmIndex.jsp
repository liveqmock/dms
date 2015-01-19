<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>到账确认</title>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/remitMng/RemitCheckInConfirmAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		doSearch();
	});
	$("#btn-search").trigger("click");

	$("#btn-reset").bind("click", function(event){
			$("#fm-searchRemit")[0].reset();
			$("#orgCode").attr("code","");
			$("#orgCode").val("");
		});
});
function doSearch(){
	var searchUrl = mngUrl+"/remitSearch.ajax";
	var $f = $("#fm-searchRemit");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-remit_info");
}
var row;
function doConfirm(rowobj)
{
/* 	$row = $(rowobj);
	var url = mngUrl + "/remitConfirm.ajax?REMIT_ID="+$(rowobj).attr("REMIT_ID");
	sendPost(url,"report","",reportCallBack,"true"); */
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/finance/remitCheckIn.jsp?action=2", "打款确认", "打款确认", diaAddOptions);
}
function  reportCallBack(res)
{
	try
	{
		
		if($row) 
			$("#tab-remit_info").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = mngUrl + "/remitDelete.ajax?REMIT_ID="+$(rowobj).attr("REMIT_ID");
	sendPost(url,"report","",reportCallBack,"true"); 
}
function  reportCallBack(res)
{
	try
	{
		doSearch();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 打款管理   &gt; 到账登记</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchRemit">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-remitSearch">
					<tr>
						<td><label>渠道代码：</label></td>
					    <td>
					    	<input type="text" id="orgCode" name="orgCode" datasource="T1.CODE" datatype="1,is_null,300000" kind="dic" src="T#TM_ORG:CODE:ONAME:1=1 AND ORG_TYPE = 200005 AND STATUS='100201' ORDER BY CODE"/>
					    	
					    	<!-- <input type="text" id="orgCode" name="orgCode" datasource="T1.ORG_ID" datatype="1,is_null,300000"  hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" readonly="true" operation="in"/> -->
					    </td>
					    <td><label>渠道名称：</label></td>
					    <td>
					    	<input type="text" id="ORG_NAME" name="ORG_NAME" datasource="T1.ONAME"  operation="like"/>
					    </td>
					</tr>
					<tr>
				    	<td><label>账户类型：</label></td>
					    <td><select type="text" class="combox" id="AMOUNT_TYPE" name="AMOUNT_TYPE" kind="dic" src="ZJZHLX" filtercode="<%=DicConstant.ZJZHLX_01%>|<%=DicConstant.ZJZHLX_02%>" datasource="AMOUNT_TYPE" datatype="1,is_null,6" readonly="readonly">
						    	<option value="-1" selected>--</option>
						    </select>
					    </td>
				    	<td><label>登记日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="FILIING_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="FILIING_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_remit" >
			<table style="display:none;width:100%;" id="tab-remit_info" name="tablist" ref="page_remit" refQuery="fm-searchRemit" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th colwidth="105" type="link" title="[确认]|[删除]"  action="doConfirm|doDelete" >操作</th>
							<th fieldname="ORG_CODE" >渠道代码</th>
							<th fieldname="ORG_NAME" >渠道名称</th>
							<th fieldname="FILIING_DATE" >登记日期</th>
							<th fieldname="AMOUNT_TYPE" >账户类型</th>
							<th fieldname="DRAFT_NO" >承兑票号</th>
							<th fieldname="BILL_AMOUNT" refer="formatAmount" align="right">金额</th>
							<th fieldname="REMARK" >备注</th>
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