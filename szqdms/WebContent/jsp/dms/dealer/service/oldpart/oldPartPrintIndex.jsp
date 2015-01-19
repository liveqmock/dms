<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件标签打印</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction/oldPartPrintSearch.ajax";
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		var $f = $("#oldPartform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var sUrl=searchUrl + "?ifPrint="+$("#IF_PRINT").attr("code");
		doFormSubmit($f,sUrl,"search",1,sCondition,"oldPartList");
	});
	$("#export").bind("click",function(){
		var $f = $("#oldPartform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction/dealerDownload.do");
		$("#exportFm").submit();
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	//var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	//window.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartPrintDetail.jsp","","scrollbars=yes,width=800,height=500,top="+(screen.availHeight-500)/2+",left="+(screen.availWidth-800)/2);
	//$.pdialog.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartPrintDetail.jsp", "oldPartPrint", "旧件标签打印", options,true); 
	window.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartPrintDetail.jsp", "newwindow", "height=550, width=850, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件标签打印</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartTable">
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="CLAIM_NO" name="CLAIM_NO" datasource="CLAIM_NO" datatype="1,is_null,30" operation="like"  value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="OLD_PART_CODE" name="OLD_PART_CODE" datasource="OLD_PART_CODE" datatype="1,is_null,30" operation="like"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="OLD_PART_NAME" name="OLD_PART_NAME" datasource="OLD_PART_NAME" datatype="1,is_null,30"  operation="like" value="" /></td>
						</tr>
						<tr>
							<td><label>是否已打印：</label></td>
							<td><select type="text" id="IF_PRINT" name="IF_PRINT"  datatype="1,is_null,6" class="combox" kind="dic" src="SF" >
									<option value=-1>--</option>
								</select>
							</td>
							<td><label>旧件产生日期：</label></td>
					   	    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="CHECKPASS_DATE" datatype="1,is_date,30" onclick="WdatePicker()"  kind="date" operation=">="/>
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CHECKPASS_DATE" datatype="1,is_date,30" kind="date" operation="<=" onclick="WdatePicker()" />
				   		 </td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						  	<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldPart">
				<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldPart" refQuery="oldPartTable"  >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th colwidth="45" type="link"  title="[打印]" action="doUpdate">操作</th>
							<th fieldname="IF_PRINT" >是否已打印</th>
							<th fieldname="CLAIM_NO"  ordertype='local' class="desc">索赔单号</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="FAULT_DATE">故障日期</th>
							<th fieldname="MILEAGE">行驶里程</th>
							<th fieldname="WXCS"style="display:none">维修次数</th>
							<th fieldname="OLD_PART_CODE">配件代码</th>
							<th fieldname="OLD_PART_NAME">配件名称</th>
							<th fieldname="OLD_PART_COUNT">配件数量</th>
							<th fieldname="CLAIM_COSTS" align="right" refer="amountFormat">结算材料费</th>
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="MAIN_SUPP_CODE">责任供应商代码</th>
							<th fieldname="MAIN_SUPP_NAME">责任供应商名称</th>
							<th fieldname="FAULT_REASON">质损原因</th>
							<th fieldname="REMARKS"style="display:none">备注</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
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