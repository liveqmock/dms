<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String recoveryId=request.getParameter("recoveryId");
%>
<div id="dia_partDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="diaUnknowDetailFm" class="editForm" style="width: 99%;">
		</form>
		<div id="divDetail" style="">
			<table style="width:100%;" id="divDetailList" name="divDetailList" ref="divDetail" >
				<thead>
					<tr>
						<th fieldname="SUPPLIER_CODE" >供应商代码</th>
						<th fieldname="SUPPLIER_NAME" >供应商名称</th>
						<th fieldname="CLAIM_NO" >索赔单号</th>
						<th fieldname="CLAIM_COST" >报单费用</th>
						<th fieldname="UNKNOWN_COST" >未知费用</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var recoveryId='<%=recoveryId%>';
var diaOperateAction="<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction";
//弹出窗体
var dialog = $("body").data("recoveryDetail");
$(function()
{
	var $fm = $("#diaUnknowDetailFm");
	var sCondition = {};//定义json条件对象
	sCondition = $fm.combined() || {};
	var searchDetailUrl=diaOperateAction+"/searchRecoveryDetail.ajax?recoveryId="+recoveryId;
	doFormSubmit($fm,searchDetailUrl,"dia-save1",1,sCondition,"divDetailList");
	
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