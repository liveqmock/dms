<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>追偿清单查询</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#recoverform");
		var sCondition = {};
	  	sCondition = $f.combined() || {};
		var url =searchUrl+"/searchRecoverList.ajax";
		doFormSubmit($f,url,"search",1,sCondition,"recoverList");
	});
	//导出
	$("#btn-exp").bind("click",function(){
        var $f = $("#recoverform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction/download.do");
		$("#exportFm").submit();
	});
});
//金额格式化
function amountFormat(obj){
  return amountFormatNew($(obj).html());
}

//未知费用明细
function doDetail(obj){
	
	var $tr=$(obj).parent();
	return "<a href='#' onclick=showUnknowDetail("+$tr.attr("RECOVERY_ID")+") class='op'>"+amountFormatNew($(obj).html())+"</a>";
}
function showUnknowDetail(recoveryId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/oldpart/recoverUnknowDetail.jsp?recoveryId="+recoveryId, "recoveryDetail", "未知费用明细", options);
}

//查看附件
function doView(rowobj){
	var recoveryId=$(rowobj).attr("RECOVERY_ID");
	$.filestore.view(recoveryId);
}
function doClaimCosts(rowobj){
	var recoveryId=$(rowobj).attr("RECOVERY_ID");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/oldpart/recoverClaimDetail.jsp?recoveryId="+recoveryId, "recoverClaimDetail", "费用明细", options);
}
//vin连接
function claimInfoLink(obj)
{
	var $row=$(obj).parent();
	if($row.attr("CLAIM_ID"))
	{
		return "<a href='#' onclick=claimInfoLink1("+$row.attr("CLAIM_ID")+") class='op'>"+$row.attr("CLAIM_NO")+"</a>";
	}else
	{
		return "";
	}
}
function claimInfoLink1(claimId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;追偿清单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="recoverform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="recoverTable">
						<tr>
						    <td><label>供应商代码：</label></td>
							<td><input type="text" id="supplierCode" name="supplierCode" datasource="SUPPLIER_CODE"  datatype="1,is_null,100"  value=""operation="like" /></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="SUPPLIER_NAME"  datatype="1,is_null,100"  value="" operation="like" /></td>
						</tr>
						<tr>	
							<td><label>追偿日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  operation = ">=" group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="RECOVERY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})"  kind="date" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" operation = "<=" group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="RECOVERY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})"  kind="date"/>
					   		 </td>
					   		 <td><label>索赔单号：</label></td>
							 <td><input type="text" id="claimNo" name="claimNo" datasource="C.CLAIM_NO"  datatype="1,is_null,100"  value="" operation="like" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-exp" >导&nbsp;&nbsp;出</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="recover">
				<table style="display:none;width:100%;" id="recoverList" name="recoverList"  ref="recover" refQuery="recoverTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="RECOVERY_DATE" >追偿日期</th>
							<th fieldname="CLAIM_NO" refer="claimInfoLink">索赔单号</th>
							<th fieldname="MATERIAL_COSTS" refer="amountFormat">材料费用</th>
							<th fieldname="WORK_COSTS" refer="amountFormat">工时费用</th>
							<th fieldname="SEVEH_COSTS" refer="amountFormat">外出车费</th>
							<th fieldname="MEALS_COSTS" refer="amountFormat">人员补助</th>
							<th fieldname="UNKNOWN_COST" refer="amountFormat">未知费用</th>
							<!-- 追偿费用查到明细 -->
							<!-- <th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME" colwidth="160" maxlength="30">供应商名称</th>
							<th fieldname="RECOVERY_DATE" colwidth="80">追偿日期</th>
							<th fieldname="ADJUST_TIME" colwidth="80">调整日期</th>
							<th fieldname="ADJUST_USER" colwidth="60">调整人</th>
							<th fieldname="TOTAL_COST" colwidth="60" align="right" refer="amountFormat">总费用</th>
							<th fieldname="CLAIM_TOTAL_COST" colwidth="60" align="right" refer="amountFormat">报单总费用</th>
							<th fieldname="CLAIM_MANAGE_COST" colwidth="60" align="right" refer="amountFormat">服务管理费</th>
							<th fieldname="OLDPART_MANAGE_COST" colwidth="60" align="right" refer="amountFormat">旧件管理费</th>
							<th fieldname="ADJUST_COST" colwidth="60" align="right" refer="amountFormat">调整费用</th>
							<th fieldname="UNKNOWN_TOTAL_COST" colwidth="60" align="right" refer="doDetail">未知费用</th>
							<th colwidth="100" type="link" title="[查看附件]|[明细]"  action="doView|doClaimCosts" >操作</th> -->
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>