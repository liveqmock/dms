<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="aqjcfyxg" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-aqjcfyxg" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="aqjcfyxgTable">
				<tr>
				    <td><label>服务商代码：</label></td>
    				<td>服务商代码1</td>
			 	</tr>
			 	<tr>
				    <td><label>服务商名称：</label></td>
    				<td>服务商名称1</td>
			 	</tr>
				<tr>
				    <td><label>费用(元)：</label></td>
				    <td>
			    		<input type="text" id="di-fy"  name="di-fy"  datatype="0,is_double,30" />
			   		 </td>
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
	$("#di-fy").val("300.00");
});
function doSave(){
	var $f = $("#di-aqjcfyxg");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("aqjcfyxg");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>