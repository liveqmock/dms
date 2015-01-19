<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia_applyDetail">
	<div class="page">
		<div class="pageContent" style="" >
			<form class="editForm">
			<div align="left">
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('checkDetail')">&nbsp;历史审核轨迹&gt;&gt;</a></legend>
				<div id="checkDetail">
					<table style="display:none;width:100%;" id="checkDetailList" name="checkDetailList" ref="checkDetail" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="CLAIM_NO">索赔单号</th>
								<th fieldname="CHECK_USER">审核人</th>
								<th fieldname="CHECK_DATE">审核时间</th>
								<th fieldname="CHECK_RESULT">审核结果</th>
								<th fieldname="CHECK_REMARKS" maxlength="40" >审核意见</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</fieldset>
			</div> 
			</form>
			<form method="post" id="dia_applyDetailFm" class="editForm" style="width:100%;">
				<input type="hidden" id="dia_claimId" name="dia_claimId" datasource="C.CLAIM_ID"/>
	        	<div align="left">
					<table class="editTable" id="dia_applyDetailTable">
						<tr>
						    <td><label>申请原因：</label></td>
						    <td>
								<textarea id="dia_reApplyReason" style="width:450px;height:40px;" name="dia_reApplyReason"  datasource="REAPPLY_REASON" datatype="1,is_null,30"></textarea>
						    </td>
						</tr>
					</table>
				</div>
			</form>		
		</div>
	    <div class="formBar">
			<ul>
			    <li><div class="button"><div class="buttonContent"><button type="button" id="dia_addAtt" >上传附件</button></div></div></li>
			    <li><div class="button"><div class="buttonContent"><button type="button" id="dia_viewAtt">查看附件</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia_save" >申&nbsp;&nbsp;请</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
var diaSearchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimReApplyAction/checkSearch.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimReApplyAction";
$(function(){
	var selectedRows = $("#claimReApplyList").getSelectedRows();
	$("#dia_claimId").val(selectedRows[0].attr("CLAIM_ID"));
	//setEditValue("dia-Detailfm",selectedRows[0].attr("rowdata"));
	var $fm = $("#dia_applyDetailFm");
	var sCondition = {};
   	sCondition = $fm.combined() || {};
	doFormSubmit($fm,diaSearchUrl,"",1,sCondition,"checkDetailList");
	//添加附件
	$("#dia_addAtt").bind("click",function(){
		var claimId=$("#dia_claimId").val();
		$.filestore.open(claimId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	//查看附件
	$("#dia_viewAtt").bind("click",function(){
		var claimId=$("#dia_claimId").val();
		$.filestore.view(claimId);
	});
	//申请
	$("#dia_save").bind("click",function(){
		var $f1 = $("#dia_applyDetailFm");
		var reason =$("#dia_reApplyReason").val();
		if(reason.length==0){
			alertMsg.warn("申请原因不能为空，请填写申请原因.");
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia_applyDetailFm").combined(1) || {};
		var updateUrl = diaSaveAction + "/claimUpdate.ajax";
		doNormalSubmit($f1,updateUrl,"dia_save",sCondition,diaUpdateCallBack);
	});
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
});
//申请回调
function diaUpdateCallBack(res){
	try
	{
		var $row = $("#claimReApplyList").getSelectedRows();//选择行
		if($row[0]){
			$.pdialog.closeCurrent();
			$("#search").click();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>

