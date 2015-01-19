<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="pjlshxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-pjlshxg" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="pjlshxg">
				<tr>
					<td><label>配件代码：</label></td>
					<td><input type="text" id="DI_PJDM" name="DI_PJDM" datatype="1,is_null,100" value="配件代码1" readonly="readonly"/></td>
					<td><label>配件名称：</label></td>
					<td><input type="text" id="DI_PJMC" name="DI_PJMC" datatype="1,is_null,100" value="配件名称1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>供应商代码：</label></td>
					<td><input type="text" id="DI_PJDM" name="DI_PJDM" datatype="1,is_null,100" value="供应商代码1" readonly="readonly"/></td>
					<td><label>供应商名称：</label></td>
					<td><input type="text" id="DI_PJMC" name="DI_PJMC" datatype="1,is_null,100" value="供应商名称1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>是否设置流水号</label></td>
					<td><select type="text" id="DI_SF" name="DI_SF" class="combox" kind="dic" src="SF">
							<option value=-1>--</option>
						</select></td>
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
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("pjlsh");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>