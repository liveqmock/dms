<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String claimDtlId=request.getParameter("claimDtlId");
%>
<div id="dia_partDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="diaPartFm" class="editForm" style="width: 99%;">
		</form>
		<div id="divPartDetail" style="">
			<table style="width:100%;" id="divPartDetailList" name="divPartDetailList" ref="divPartDetail" >
				<thead>
					<tr>
						<th fieldname="FAULT_TYPE" >故障类别</th>
						<th fieldname="MEASURES" >处理措施</th>
						<th fieldname="OLD_PART_CODE" >旧件代码</th>
						<th fieldname="OLD_PART_NAME" >旧件名称</th>
						<th fieldname="OLD_PART_COUNT" >旧件数量</th>
						<th fieldname="OLD_SUPPLIER_CODE" >旧件供应商代码</th>
						<th fieldname="OLD_SUPPLIER_NAME" >旧件供应商名称</th>
						<th fieldname="OLD_PART_STREAM" >旧件零件流水号</th>
						<th fieldname="REPAY_UPRICE" align="right" refer="amountFormat">追偿单价</th>
						<th fieldname="BRIDGE_CODE" >桥代码</th>
						<th fieldname="FAULT_REASON" >故障原因</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var claimDtlId='<%=claimDtlId%>';
//弹出窗体
var dialog = $("body").data("claimPart");
$(function()
{
	var $fm = $("#diaPartFm");
	var sCondition = {};//定义json条件对象
	sCondition = $fm.combined() || {};
	var searchFaultPartUrl=diaSaveAction+"/searchPart.ajax?claimDtlId="+claimDtlId;
	doFormSubmit($fm,searchFaultPartUrl,"dia-save1",1,sCondition,"divPartDetailList");
	
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>