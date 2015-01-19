<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供应商认领资格查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton/supplierSearch.ajax";
var reportUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton/appendixAduit.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton/deletePhoto.ajax";
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	});
	$("#export").bind("click",function(){
		var $f = $("#claimSearchform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton/download.do");
		$("#exportFm").submit();
	});
});
var sId;
function doAudit(rowobj)
{
	$row = $(rowobj);
	sId=$row.attr("SUPPLIER_ID");
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/supplierPartPhotoEdit.jsp", "photoCheck", "配件照片审核", diaAddOptions,true);
}

var row;
function doReport(rowobj)
{
	$row = $(rowobj);
	var url = reportUrl + "?CONTRACT_ID="+$(rowobj).attr("CONTRACT_ID");
	sendPost(url,"report","",reportCallBack,"true");
}
function  reportCallBack(res)
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
}
function toAppend(obj){
	 var $tr = $(obj).parent();
	 return $tr.attr("EFFECTIVE_CYCLE_BEGIN_sv")+"~"+ $tr.attr("EFFECTIVE_CYCLE_END_sv");
}
function doDownLoad(rowobj){
	$("td input[type=radio]",$(obj)).attr("checked",true);
	var relationId = $(rowobj).attr("SUPPLIER_ID");
	var attOptions = {max:false,width:640,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/appendixDtl.jsp?relationId="+relationId, "供应商配件照片上传", "供应商配件照片上传", attOptions);
}
function dodelete(rowobj){
	$row = $(rowobj);
	var url = deleteUrl + "?supId="+$(rowobj).attr("SUPPLIER_ID");
	sendPost(url,"report","",reportCallBack,"true");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 旧件管理  &gt; 旧件管理管理   &gt; 供应商认领资格审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>供应商代码：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datatype="1,is_digit_letter,30" dataSource="S.SUPPLIER_CODE" operation="like" /></td>
				    	<td><label>供应商名称：</label></td>
					    <td ><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datatype="1,is_null,60" dataSource="S.SUPPLIER_NAME" operation="like" /></td>
					    <td><label>提报日期：</label></td>
					    <td >
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
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
							<th fieldname="SUPPLIER_ID" style="display:none;">厂家ID</th>
							<th fieldname="SUPPLIER_CODE" >厂家编码</th>
							<th fieldname="SUPPLIER_NAME">厂家名称</th>
							<th fieldname="GET_NAME">认领人姓名</th>
							<th fieldname="GET_NO">认领人身份证号</th>
							<th fieldname="GET_PHONE">认领人联系电话</th>
							<th colwidth="180" type="link" title="[认领人附件信息]"  action="doDownLoad" >操作</th>
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data"></input>
</form>
</body>
</html>