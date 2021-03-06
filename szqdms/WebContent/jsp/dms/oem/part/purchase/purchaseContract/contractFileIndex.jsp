<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>合同归档</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractOperationManageAction/fileContractSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	});
});
function doFile(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseContract/contractFileEdit.jsp?action=2", "合同归档", "合同归档", diaAddOptions);
}
<%-- var row;
function doFile(rowobj)
{
	$row = $(rowobj);
	var reportUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractOperationManageAction/contractFile.ajax";
	var url = reportUrl + "?CONTRACT_ID="+$(rowobj).attr("CONTRACT_ID");
	sendPost(url,"file","",fileCallBack,"true");
}
function  fileCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-contract").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} --%>
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("CONTRACT_ID")+") class='op'>"+$row.attr("CONTRACT_NO")+"</a>";
}
function openDetail(CONTRACT_ID){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseContract/contractInfo.jsp?CONTRACT_ID="+CONTRACT_ID, "contractDetail", "合同明细", options,true);
}
function toAppend(obj){
	 var $tr = $(obj).parent();
	 return $tr.attr("EFFECTIVE_CYCLE_BEGIN_sv")+"~"+ $tr.attr("EFFECTIVE_CYCLE_END_sv");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购合同管理   &gt; 合同归档</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>合同编号：</label></td>
					    <td><input type="text" id="CONTRACT_NO" name="CONTRACT_NO" datatype="1,is_digit_letter,30" dataSource="CONTRACT_NO" operation="like" /></td>
				    	<td><label>厂家名称：</label></td>
					    <td ><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datatype="1,is_null,300" dataSource="SUPPLIER_NAME" operation="like" /></td>
					    <td><label>创建日期：</label></td>
					    <td >
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="CREATE_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="CREATE_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="fm-htcx" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="CONTRACT_NO" refer="showLink">合同编号</th>
							<th fieldname="CONTRACT_NAME" >合同名称</th>
							<th fieldname="SUPPLIER_NAME">厂家名称</th>
							<th fieldname="SUPPLIER_CODE" >厂家编码</th>
							<th fieldname="SUPPLIER_QUALIFY" style="display:none;">厂家资质</th>
							<th fieldname="LEGAL_PERSON" style="display:none;">企业法人</th>
							<th fieldname="LEGAL_PERSON_PHONE" style="display:none;">法人联系方式</th>
							<th fieldname="BUSINESS_PERSON" style="display:none;">业务联系人</th>
							<th fieldname="BUSINESS_PERSON_PHONE" colwidth="85"style="display:none;">业务联系方式</th>
							<th fieldname="GUARANTEE_MONEY" colwidth="70" style="display:none;">质保金金额</th>
							<th fieldname="WARRANTY_PERIOD" style="display:none;">质保期</th>
							<th fieldname="OPEN_ACCOUNT" colwidth="130" style="display:none;">挂账结算周期（月）</th>
							<th fieldname="" colwidth="180" refer="toAppend">有效期</th>
							<th fieldname="TAX_RATE" style="display:none;">税率</th>
							<th fieldname="RECOVERY_CLAUSE" style="display:none;">追偿条款</th>
							<th fieldname="REMARKS" style="display:none;">备注</th>
							<th colwidth="50" type="link" title="[归档]"  action="doFile" >操作</th>
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>