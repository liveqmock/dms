<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdfj" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="di_fwhdfjxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="fwhdfjxz">
			    <tr>
					<td><label>文件上传：</label></td>
					<td><input type="file" id="DI_WJSC" name="DI_WJSC" /></td>
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
	var $f = $("#di_fwhdfjxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("fwhdfj");
$(function()
{
	$("#di_searchModel").click(function(){
		$("#di_fwhdcxlb").show();
		$("#di_fwhdcxlb").jTable();
		$("#opBtn").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>