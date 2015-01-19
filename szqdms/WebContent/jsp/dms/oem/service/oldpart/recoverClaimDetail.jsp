<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String recoveryId=request.getParameter("recoveryId");
%>
<div id="dia_partDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="diaClaimDetailFm" class="editForm" style="width: 100%;">
		</form>
		<div id="divClaim" style="">
			<table style="width:100%;" id="divClaimList" name="divClaimList" ref="divClaim" >
				<thead>
					<tr>
						<th fieldname="SUPPLIER_CODE" >供应商代码</th>
						<th fieldname="SUPPLIER_NAME" >供应商名称</th>
						<th fieldname="CLAIM_NO" >索赔单号</th>
						<th fieldname="MATERIAL_COSTS" refer="amountFormat">材料费用</th>
						<th fieldname="WORK_COSTS" refer="amountFormat">工时费用</th>
						<th fieldname="SEVEH_COSTS" refer="amountFormat">外出车费</th>
						<th fieldname="MEALS_COSTS" refer="amountFormat">人员补助</th>
						<th fieldname="UNKNOWN_COST" refer="amountFormat">未知费用</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expClaim" >导&nbsp;&nbsp;出</button></div></div></li>
		</ul>
	</div>
	</div>	
</div>
<script type="text/javascript">
var recoveryId='<%=recoveryId%>';
var diaOperateAction="<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction";
//弹出窗体
var diadialog = $("body").data("recoverClaimDetail");
$(function()
{
	var $fm = $("#diaClaimDetailFm");
	var sCondition = {};//定义json条件对象
	sCondition = $fm.combined() || {};
	var searchDetailUrl=diaOperateAction+"/searchRecoveryClaimDetail.ajax?recoveryId="+recoveryId;
	doFormSubmit($fm,searchDetailUrl,"dia-save1",1,sCondition,"divClaimList");
	
	$("#btn-expClaim").bind("click",function(){
		
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction/downloadClaim.do?recoveryId="+recoveryId);
		$("#exportFm").submit();
	});
	$("button.close").click(function(){
		$.pdialog.close(diadialog);
		return false;
	});
});

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>