<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript">
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton";
 //下载索赔提报流程
$(function(){
	var $f = $("");
	var sCondition = {};
   	sCondition = $f.combined() || {};
   	var searchServiceUrl =mngUrl+"/queryDtl.ajax";
   	sendPost(searchServiceUrl,"",sCondition,searchServiceCallBack,"false");
	 $("#dia-save").bind("click",function(event){
			var $fm = $("#oldPartFm");
			if (submitForm($fm) == false) {
				return false;
			}
			var sCondition = {};
			sCondition = $fm.combined(1) || {};
			var updateUrl = mngUrl + "/getNameUpdate.ajax";
			doNormalSubmit($fm,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
		});
	 $("#addAtt").bind("click",function(event){
			var supplierId = $("#supplierId").val();
		    $.filestore.open(supplierId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	 $("#doDownLoad").bind("click",function(event){
			$("td input[type=radio]",$(obj)).attr("checked",true);
			var supplierId = $("#supplierId").val();
			var attOptions = {max:false,width:640,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
			$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/appendixAdd.jsp?relationId="+supplierId, "供应商配件照片上传", "供应商配件照片上传", attOptions);
	});
});
//保存回调
function diaUpdateCallBack(res){
	try
	{
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function searchServiceCallBack(res)
{
	setEditValue("oldPartFm",res);
	return true;
}
</script>
</head>
<body>
<div class="pageContent">
	<form name="lForm" id="oldPartFm" method="post" >
	<input type="hidden" id="supplierId" name="supplierId"  datasource="SUPPLIER_ID"   value="" />
		<div class="pageContent">
			<table class="searchContent" id="oldPartTable">
				<tr>
					<td><label>认领人姓名：</label></td>
					<td><input type="text" id="getName" name="getName" style="width:700px;" datasource="GET_NAME"  datatype="1,is_null,100"  value="" /></td>
				</tr>
				<tr>
					<td><label>认领人身份证号：</label></td>
					<td><input type="text" id="getNo" name="getNo" style="width:700px;" datasource="GET_NO"   datatype="1,is_null,100"  value="" /></td>
				</tr>
				<tr>
					<td><label>认领人联系电话：</label></td>
					<td><input type="text" id="getPhone" name="getPhone" style="width:700px;"  datasource="GET_PHONE"   datatype="1,is_null,100"  value="" /></td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上传附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="doDownLoad">查看附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</form>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>