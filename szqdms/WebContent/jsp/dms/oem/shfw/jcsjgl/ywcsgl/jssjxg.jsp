<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="jssjxgxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-jssjxgxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="jssjxgxxTable">
				<tr>
					<td><label>年：</label></td>
					<td>2014</td>
			 	</tr>
			 	<tr>
					<td><label>月份：</label></td>
					<td>8</td>
			 	</tr>
			 	<tr>
					<td><label>结算日期：</label></td>
					<td><input type="text" value="8" id="DI_JSRQ" name="DI_JSRQ" datatype="0,is_digit,100" /></td>
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
});
function doSave(){
	var $f = $("#di-jssjxgxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("jssjxg");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>