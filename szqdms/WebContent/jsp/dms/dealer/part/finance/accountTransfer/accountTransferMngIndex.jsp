<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>转账申请</title>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/transfer/TransferReportMngAction";
$(function()
{
	var searchUrl = mngUrl+"/transferSearch.ajax";
	var $f = $("#fm-searchTransfer");
	var sCondition = {};
   	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"",1,sCondition,"tab-transfer_info");
	
	var url = mngUrl+"/getBalanceAmount.ajax";
	 sendPost(url,"","",addTransferCallBack,"false");
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var BALANCE_AMOUNT = $("#add-BALANCE_AMOUNT").val();
	$.pdialog.open(webApps+"/jsp/dms/dealer/part/finance/accountTransfer/accountTransferAdd.jsp?action=2&BALANCE_AMOUNT="+BALANCE_AMOUNT, "转账修改", "转账修改", diaAddOptions);
}
var row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = mngUrl + "/transferDelete.ajax?TRANSFER_ID="+$(rowobj).attr("TRANSFER_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
var row;
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-transfer_info").removeResult($row);
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
	var url = mngUrl + "/transferReport.ajax?TRANSFER_ID="+$(rowobj).attr("TRANSFER_ID");
	sendPost(url,"report","",reportCallBack,"true");
}
function  reportCallBack(res)
{
	try
	{
		
		if($row) 
			$("#tab-transfer_info").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function addTransfer() {
	 /* var url = mngUrl+"/getBalanceAmount.ajax";
	 sendPost(url,"","",addTransferCallBack,"false"); */
	 var BALANCE_AMOUNT = $("#add-BALANCE_AMOUNT").val();
	 var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
     $.pdialog.open(webApps + "/jsp/dms/dealer/part/finance/accountTransfer/accountTransferAdd.jsp?BALANCE_AMOUNT="+BALANCE_AMOUNT, "partSelWin", "新增转账", options);
}

function addTransferCallBack(res) {
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows.length>0){
        	 var BALANCE_AMOUNT = getNodeText(rows[0].getElementsByTagName("BALANCE_AMOUNT").item(0));
             $("#add-BALANCE_AMOUNT").val(BALANCE_AMOUNT);
        }
    } catch (e) {
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 转账管理   &gt; 转账申请</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchTransfer">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-transferSearch">
				<input type="hidden" id="add-BALANCE_AMOUNT" name="add-BALANCE_AMOUNT" datasource=BALANCE_AMOUNT />
				</table>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_transfer" >
			<table style="display:none;width:100%;" id="tab-transfer_info" name="tablist" ref="page_transfer" refQuery="fm-searchTransfer" >
					<thead>
						<tr>
							<th type="single" name="XH" append="plus|addTransfer"></th>
							<th fieldname="OUT_TYPE" >转出账户类型</th>
							<th fieldname="IN_TYPE" >转入账户类型</th>
							<th fieldname="AMOUNT" refer="formatAmount">转账金额</th>
							<th fieldname="APPLY_DATE" >申请日期</th>
							<th fieldname="REMARKS" >转账原因</th>
							<th fieldname="TRANSFER_STATUS">申请状态</th>
							<th colwidth="140" type="link" title="[提报]|[修改]|[删除]"  action="doReport|doUpdate|doDelete" >操作</th>
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