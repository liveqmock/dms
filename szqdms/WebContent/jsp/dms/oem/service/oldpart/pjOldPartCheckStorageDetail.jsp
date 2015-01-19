<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运审核</title>
<script type="text/javascript">
var $true=true;
var saveFormAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPJCheckStorageAction/saveInCount.ajax";
var cancelPartAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPJCheckStorageAction/cancelPartStorage.ajax";
$(function(){
	
	// 获取Index页面审核行对象
	var selectedRows = parent.$("#oldPartList").getSelectedRows();
	
	// 将审核对象行数据与Tab1中form控件数据进行绑定，并显示
	setEditValue("dia-oldpartfm",selectedRows[0].attr("rowdata"));
	
	if($("#IN_COUNT").val() == 0){
		$("#IN_COUNT").val("");
	}

	
	// 审核通过
	$("#dia_save").click(function(){
		
		// 获取需要提交的form对象
        var $f = $("#dia-oldpartfm");
		if(submitForm($f) === true){
           
			if($("#IN_COUNT").val() > $("#CLAIM_COUNT").val()){
				alertMsg.error("入库数量不能大于索赔数量");
				return;
			}
			
            //将需要提交的内容拼接成json
            var sCondition = $f.combined() || {};
            
            // 保存操作
            doNormalSubmit($f, saveFormAction, "dia_save", sCondition, function(responseText){
            	parent.$("#search").click();
            	parent.$.pdialog.closeCurrent();
            	alertMsg.info("审核成功");
            });
		}
	});
	
	//作废
	$("#dia_cancellation").click(function(){
		alertMsg.confirm("确定作废？",{okCall:function(){
				sendPost(cancelPartAction+"?APPLY_ID="+$("#APPLY_ID").val(),"","",function(responseText){
					parent.$("#search").click();
					parent.$.pdialog.closeCurrent();
				},"false");
			}
		});
		
	});
	
	
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	
	<div class="pageContent" style="" >
			<form method="post" id="dia-oldpartfm" class="editForm" >
				<input type="hidden" name="APPLY_ID" id="APPLY_ID" datasource="APPLY_ID" value=""/>
				<div align="left">
				<table class="editTable" id="dia-oldpartTab">
				    <tr>
						<td><label>申请单号：</label></td>
						<td><input type="text" id="APPLY_NO" name="APPLY_NO" datasource="APPLY_NO" datatype="1,is_null,100" value="" readonly="readonly"/></td>
						<td><label>箱号：</label></td>
						<td><input type="text" id="BOX_NO" name="BOX_NO" datasource="BOX_NO"  datatype="1,is_null,100" value="" readonly="readonly"/></td>
						<td><label>回运日期：</label></td>
						<td><input type="text" id="RETURN_DATE" name="RETURN_DATE" datasource="RETURN_DATE"  datatype="1,is_null,100" value="" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,100" value="" readonly="readonly"/></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" value=""  datatype="1,is_null,30" readonly="readonly" /></td>
						<td ><label>索赔数量：</label></td>
						<td><input type="text" id="CLAIM_COUNT" name="CLAIM_COUNT" datasource="CLAIM_COUNT" datatype="1,is_digit,10" readonly="readonly" /></td>
					</tr>
					<tr>	
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="SUPPLIER_CODE" name="ORG_CODE" datasource="ORG_CODE" value="" datatype="1,is_null,30"  readonly="readonly" /></td>	
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="SUPPLIER_NAME" name="ORG_NAME" datasource="ORG_NAME" value="" datatype="1,is_null,30"  readonly="readonly" /></td>	
					</tr>
					<tr>
						<td><label>供应商代码：</label></td>
						<td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" value="" datatype="1,is_null,30"  readonly="readonly" /></td>	
						<td><label>供应商名称：</label></td>
						<td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datasource="SUPPLIER_NAME" value="" datatype="1,is_null,30"  readonly="readonly" /></td>	
					</tr>
					<tr>
						<td><label>入库数量：</label></td>
						<td><input type="text" id="IN_COUNT" name="IN_COUNT" datasource="IN_COUNT" value="" datatype="0,is_digit_0,30"   /></td>	
					</tr>
					<tr>
						<td><label>备&nbsp;&nbsp;注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="ADUIT_REMARKS" name="ADUIT_REMARKS" datasource="ADUIT_REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</div>
			</form>
	</div>
	<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_save" confirm="确定审核通过？">审核通过</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_cancellation" confirm="确定作废？">作&nbsp;&nbsp;废</button></div></div></li>
			</ul>
	</div>
	<form id="fm-hidInfo" style="display: none;">
		<input type="hidden" id="detailIds" datasource="DETAIL_IDS"/>
		<input type="hidden" id="instorAmounts" datasource="INSTOR_AMOUNTS"/>
		<input type="hidden" id="remarksHi" datasource="REMARKS"/>
    </form>
</div>
</body>
</html>