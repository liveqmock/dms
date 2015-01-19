<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-checkUser" class="editForm" style="width: 100%;">
			<input type="hidden" id="dia-cuId" datasource="CU_ID"/>
			<table width="100%" id="dia-checkUserTable" name="dia-checkUserTable" class="editTable" >
                 <tr>
					<td><label>审核员：</label></td>
					<td><input type="text" id="dia-checkUser" name="dia-checkUser" value="" datasource="USER_NAME" datatype="1,is_null,100" readonly="readonly" /></td>
					<td><label>是否分配索赔单：</label></td>
					<td>
						<select type="text" id="dia-if" name="dia-if" kind="dic" src="SF" datasource="IF_DISTRIB" datatype="0,is_null,6" value="">
							<option value="100101" selected>是</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia-save" >保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>    	
	</div>
</div>
</div>
<script type="text/javascript">
var diaSaveAction =  "<%=request.getContextPath()%>/service/claimmng/CheckUserAction";
$(function(){
	var selectedRows = $("#checkUserList").getSelectedRows();
	setEditValue("dia-fm-checkUser",selectedRows[0].attr("rowdata"));
	
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	
	$("#dia-save").bind("click",function(){
		var $fm = $("#dia-fm-checkUser");
		if (submitForm($fm) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $fm.combined(1) || {};
		var updateUrl = diaSaveAction + "/checkUserUpdate.ajax";
		doNormalSubmit($fm,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
	});
});
//保存回调
function diaUpdateCallBack(res){
	try
	{
		var selectedIndex = $("#checkUserList").getSelectedIndex();
		$("#checkUserList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>

