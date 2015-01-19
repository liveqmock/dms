<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>合同资料上传</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractAppendixManageAction";
$(function()
{
		var searchUrl = mngUrl+"/contractSearch.ajax";
		var $f = $("#fm-searchContract");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/contractSearch.ajax";
		var $f = $("#fm-searchContract");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	});
});
function doUpload(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseContract/uploadAppendixAdd.jsp?action=2", "合同资料上传", "合同资料上传", diaAddOptions);
}

var row;
function doReport(rowobj)
{
	
	$row = $(rowobj);
	var url = mngUrl+"/uploadReport.ajax?CONTRACT_ID="+$(rowobj).attr("CONTRACT_ID");
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购合同管理   &gt; 合同资料上传</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>合同编号：</label></td>
					    <td><input type="text" id="CONTRACT_NO" name="CONTRACT_NO" dataSource="A.CONTRACT_NO" operation="like" /></td>
					    <td><label>创建日期：</label></td>
					    <td >
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="A.CREATE_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="A.CREATE_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>是否上传完成：</label></td>
					    <td>
					    	<select type="text" class="combox" id="STATUS" name="STATUS" kind="dic" src="SF" datasource="B.STATUS"  datatype="1,is_null,6" readonly="readonly">
					    	<option value="-1" selected>--</option>
					    	</select>
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
							<th fieldname="CONTRACT_NO" >合同编号</th>
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
							<th fieldname="STATUS" >是否上传完成</th>
							<th colwidth="105" type="link" title="[上传]|[提交]"  action="doUpload|doReport" >操作</th>
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>