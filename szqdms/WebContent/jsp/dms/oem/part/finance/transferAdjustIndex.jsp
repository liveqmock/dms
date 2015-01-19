<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>转账审核</title>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/transfer/TransferReportAdjustMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/transferSearch.ajax";
		var $f = $("#fm-searchTransfer");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-transfer_info");
	});
	$("#btn-search").trigger("click");
	$("#btn-reset").bind("click", function(event){
		$("#fm-searchTransfer")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
});
function doAdjuested(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/finance/transferAdjust.jsp?action=2", "转账申请审核", "转账申请审核", diaAddOptions);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 转账管理   &gt; 转账审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchTransfer">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-transferSearch">
					<tr>
						<td><label>渠道代码：</label></td>
					    <td>
					    	<input type="text" id="orgCode" name="orgCode" datasource="T1.ORG_ID" datatype="1,is_null,300000"  hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" readonly="true" operation="in"/>
					    </td>
					    <td><label>渠道名称：</label></td>
					    <td>
					    	<input type="text" id="ORG_NAME" name="ORG_NAME" datasource="T1.ONAME"  operation="like"/>
					    </td>
				    	<td><label>申请日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
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
		<div id="page_transfer" >
			<table style="display:none;width:100%;" id="tab-transfer_info" name="tablist" ref="page_transfer" refQuery="fm-searchTransfer" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="CODE" >渠道代码</th>
							<th fieldname="ONAME" >渠道名称</th>
							<th fieldname="OUT_TYPE" >转出帐号类型</th>
							<th fieldname="IN_TYPE" >转入帐号类型</th>
							<th fieldname="AMOUNT" refer="formatAmount" align="right">转账金额</th>
							<th fieldname="APPLY_DATE" >申请日期</th>
							<th fieldname="REMARKS" >转账原因</th>
							<th colwidth="45" type="link" title="[审核]"  action="doAdjuested" >操作</th>
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