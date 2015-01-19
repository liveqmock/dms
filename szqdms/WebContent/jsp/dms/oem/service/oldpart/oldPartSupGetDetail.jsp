<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="dia_supGetDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia_supGetForm" class="editForm" >
			<input type="hidden" id="storageId" datasource="STORAGE_ID" />
			<input type="hidden" id="sumAmount" datasource="SUM_AMOUNT" />
			<input type="hidden" id="outAmount" datasource="OUT_AMOUNT" />
			<div align="left">
				<table class="editTable" id="dia_supGetTable">
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="dia_partCode" name="dia_partCode" datasource="PART_CODE" value="" readonly="readonly"></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="dia_partName" name="dia_partName" datasource="PART_NAME" value="" readonly="readonly"></td>
					</tr>
					<tr>
						<td><label>库存数量：</label></td>
						<td><input type="text" id="dia_surplusAmount" name="dia_surplusAmount" datasource="SURPLUS_AMOUNT" value=""  readonly="readonly"/></td>
						<td><label>认领数量：</label></td>
						<td><input type="text" id="dia_getAmount" name="dia_getAmount" datasource="GET_AMOUNT" datatype="0,is_digit_0,100" /></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="dia_remarks" name="dia_remarks" style="width:100%" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="dia_save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartSupGetAction";
//弹出窗体
var dialog = $("body").data("supGet");
$(function()
{
	var selectedRows = $("#oldPartSupList").getSelectedRows();
	setEditValue("dia_supGetForm",selectedRows[0].attr("rowdata"));
	$("#dia_getAmount").val(selectedRows[0].attr("SURPLUS_AMOUNT"));
	
	$("#dia_save").bind("click",function(){
		var $f = $("#dia_supGetForm");
		if (submitForm($f) == false) return false;
		var kcsl=$("#dia_surplusAmount").val();
		var sl=$("#dia_getAmount").val();
		if(sl-kcsl > 0 ){
			alertMsg.warn("认领数量不能大于当前库存数量");
			return false;
		}
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        var url =diaSaveAction+"/oldPartSupGetSave.ajax";
        doNormalSubmit($f, url, "dia_save", sCondition, saveCallBack);
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
}); 
function saveCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		//result 不等于1 是继续认领，result 等于1  认领完成
		if(result!='1'){
			var s1=$("#dia_surplusAmount").val();//库存数量
		    var	s2=$("#dia_getAmount").val();//认领数量
		    var	s3=$("#outAmount").val();//出库数量
		    $("#dia_surplusAmount").val(s1-s2);
		    $("#dia_getAmount").val(s1-s2);
		    $("#outAmount").val(parseInt(s2)+parseInt(s3));
		}else{
			$.pdialog.close(dialog);
		}
		$("#search").trigger("click");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>