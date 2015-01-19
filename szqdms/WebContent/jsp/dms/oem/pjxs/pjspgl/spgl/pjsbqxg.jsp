<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_pjsbqxg" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di_pjsbqxgform" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="di_pjsbqxgTable">
				<tr>
					<td><label>配件代码：</label></td>
					<td><input type="text" id="DI_X_PJDM" name="DI_X_PJDM" datatype="1,is_null,100" value="配件代码1" readonly="readonly" /></td>
					<td><label>配件名称：</label></td>
					<td><input type="text" id="DI_X_PJMC" name="DI_X_PJMC" datatype="1,is_null,100" value="配件名称1" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>三包月份：</label></td>
					<td><input type="text" id="DI_SBYF" name="DI_SBYF" datatype="0,is_digit,100" value="" /></td>
					<td><label>延保月份：</label></td>
					<td><input type="text" id="DI_YBYF" name="DI_YBYF" datatype="0,is_digit,100" value="" /></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
function doSave(){
	var $f = $("#di_pjsbqxgform");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("pjsbqxg");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>