<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="ysqjbxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-ysqjbxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="ysqjbxz">
					<tr>
						<td><label>授权级别编号：</label></td>
						<td>150</td>
						<td><label>授权级别名称：</label></td>
						<td><input type="text" id="XZ_SQJBMC" datatype="0,is_null,100"/></td>
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
$(function(){
	//初始化页面
	$("#XZ_SQJBMC").val("外出服务经理");
});
function doSave(){
	var $f = $("#fm-ysqjbxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("ysqjbxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>