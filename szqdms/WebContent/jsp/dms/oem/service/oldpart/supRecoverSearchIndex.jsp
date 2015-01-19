<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供应商追偿清单查询</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#recoverform");
		var sCondition = {};
	  	sCondition = $f.combined() || {};
		var url =searchUrl+"/searchSupRecoverList.ajax";
		doFormSubmit($f,url,"search",1,sCondition,"recoverList");
	});
	$("#btn-exp").bind("click",function(){
        var url = encodeURI("<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction/downloadSup.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
	});
	$("#btn-reset").bind("click", function(event){
		$("#recoverform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;供应商追偿清单查询</h4>
	<div class="page">
		<div class="pageHeader">
			<form id="recoverform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="recoverTable">
						<tr>
						   <!--  <td><label>供应商代码：</label></td>
							<td><input type="text" id="supplierCode" name="supplierCode" datasource="SUPPLIER_CODE"  datatype="1,is_null,100"  value=""/></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="SUPPLIER_NAME"  datatype="1,is_null,100"  value="" /></td> -->
							<td><label>追偿日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  operation = ">=" group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="RECOVERY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind="date"/>
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" operation = "<=" group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="RECOVERY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind="date"/>
					   		 </td>
					   		<%--  <td><label>追偿单价：</label></td>
						  	<td >
				    			<input type="text" id="DI_ZCFY1" operation = ">=" name="DI_ZCFY" style="width:75px;" datasource="RECOVERY_PRICE" datatype="1,is_money,30"/>
				    			<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="DI_ZCFY2" operation = "<=" name="DI_ZCFY" style="width:75px;margin-left:-30px;" datasource="RECOVERY_PRICE" datatype="1,is_money,30"/>
				   		 	</td> --%>
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
							<th fieldname="RECOVERY_DATE"  colwidth="80">追偿日期</th>
							<th fieldname="ADJUST_TIME" colwidth="80">调整日期</th>
							<th fieldname="ADJUST_USER" colwidth="80">调整人</th>
							<th fieldname="TOTAL_COST" align="right" colwidth="80" refer="amountFormat">总费用</th>
							<th fieldname="CLAIM_TOTAL_COST" align="right" colwidth="80" refer="amountFormat">报单总费用</th>
							<th fieldname="CLAIM_MANAGE_COST" align="right" colwidth="80" refer="amountFormat">服务管理费</th>
							<th fieldname="OLDPART_MANAGE_COST" align="right" colwidth="80" refer="amountFormat">旧件管理费</th>
							<th fieldname="ADJUST_COST" align="right" colwidth="80" refer="amountFormat">调整费用</th>
							<th fieldname="UNKNOWN_TOTAL_COST" align="right" colwidth="140" refer="doDetail">未知费用</th>
							<th colwidth="65" type="link" title="[查看附件]"  action="doView" >操作</th>
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