<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>供应商认领资格申请</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton";

$(function()
{
		var searchUrl = mngUrl+"/search.ajax";
		var $f = $("#fm-searchSupPart");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/search.ajax";
		var $f = $("#fm-searchSupPart");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	});
	$("#oldPartGet").bind("click",function(){
		var noptions = {max:false,mask:true,width:850,height:200,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrder/oldPartGet.jsp", "oldPartGet", "旧件认领人维护", noptions,false);
	});
});
function addAtt(rowobj)
{
	var relationId = $(rowobj).attr("RELATION_ID");
	$.filestore.open(relationId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
}
function doDownLoad(rowobj){
	$("td input[type=radio]",$(obj)).attr("checked",true);
	var relationId = $(rowobj).attr("RELATION_ID");
	var attOptions = {max:false,width:640,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/appendixAdd.jsp?relationId="+relationId, "供应商配件照片上传", "供应商配件照片上传", attOptions);
}
var row;
function doReport(rowobj)
{
	
	$row = $(rowobj);
	var partId = $(rowobj).attr("PART_ID");
	var relationId = $(rowobj).attr("RELATION_ID");
	var url = mngUrl+"/partPhotoReport.ajax?partId="+partId+"&relationId="+relationId;
	sendPost(url,"report","",reportCallBack,"true");
}
function  reportCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			alertMsg.warn("请先上传配件照片！");
		}
		$("#btn-search").click();
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 旧件管理  &gt; 旧件管理   &gt; 供应商认领资格申请</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchSupPart">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" dataSource="P.PART_CODE" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="PART_NAME" name="PART_NAME" dataSource="P.PART_NAME" operation="like" /></td>
				   		<td><label>配件类型：</label></td>
					    <td>
					    	<select type="text" class="combox" id="PART_TYPE" name="PART_TYPE" kind="dic" src="PJLB" datasource="PART_TYPE"  datatype="1,is_null,6" readonly="readonly">
					    	<option value="-1" selected>--</option>
					    	</select>
				    	</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="oldPartGet">旧件认领人维护</button></div></div></li>
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
							<th fieldname="PART_ID" style="display:none;">配件ID</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="PART_TYPE" >配件类型</th>
							<th fieldname="RELATION_ID" style="display:none;">配件类型</th>
							<th fieldname="MIN_UNIT">最小包装单位</th>
							<th fieldname="MIN_PACK">最小包装数</th>
							<th fieldname="APPLY_STATUS">配件照片状态</th>
							<th colwidth="145" type="link" title="[上传]|[查看附件]|[提交]"  action="addAtt|doDownLoad|doReport" >操作</th>
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>