<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>打款登记</title>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/remitMng/RemitCheckInMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		doSearch();
	});
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/dealer/part/finance/remitMng/remitAdd.jsp?action=1", "addRmit", "打款新增", diaAddOptions);
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/dealer/part/finance/remitMng/remitAdd.jsp?action=2", "打款修改", "打款修改", diaAddOptions);
}
var row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = mngUrl + "/remitDelete.ajax?REMIT_ID="+$(rowobj).attr("REMIT_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
var row;
function  deleteCallBack(res)
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
function doReport(rowobj)
{
	$row = $(rowobj);
	var url = mngUrl + "/remitReport.ajax?REMIT_ID="+$(rowobj).attr("REMIT_ID");
	sendPost(url,"report","",reportCallBack,"true");
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
function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
function doSearch(){
	var searchUrl = mngUrl+"/remitSearch.ajax";
	var $f = $("#fm-searchRemit");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-remit_info");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 打款管理   &gt; 打款登记</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchRemit">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-remitSearch">
					<!-- 定义查询条件 -->
					<tr>
				    	<td><label>账户类型：</label></td>
					    <td><select type="text" class="combox" id="AMOUNT_TYPE" name="AMOUNT_TYPE" kind="dic" src="ZJZHLX" filtercode="<%=DicConstant.ZJZHLX_01%>|<%=DicConstant.ZJZHLX_02%>" datasource="T.AMOUNT_TYPE" datatype="1,is_null,6" readonly="readonly">
						    	<option value="-1" selected>--</option>
						    </select>
					    </td>
				    	<td><label>登记日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.FILIING_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.FILIING_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
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
		<div id="page_remit" >
			<table style="display:none;width:100%;" id="tab-remit_info" name="tablist" ref="page_remit" refQuery="fm-searchRemit" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="FILIING_DATE" >登记日期</th>
							<th fieldname="AMOUNT_TYPE" >账户类型</th>
							<th fieldname="DRAFT_NO" >承兑票号</th>
							<th fieldname="BILL_AMOUNT" refer="formatAmount" align="right">金额</th>
							<th fieldname="REMARK" >备注</th>
							<th colwidth="140" type="link" title="[提报]|[删除]"  action="doReport|doDelete" >操作</th>
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