<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="clbzxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-clbzxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="clbzxg">
				<tr>
					<td><label>服务商代码：</label></td>
					<td><input type="text" id="DI_FWSDM" name="DI_FWSDM" value=""/></td>
					<td><label>服务商名称：</label></td>
					<td><input type="text" id="DI_FWSMC" name="DI_FWSMC" value=""/></td>
				</tr>
				<tr>
					<td><label>补助类型：</label></td>
					<td>差旅费</td>	
					<td><label>每人每天单价：</label></td>
					<td><input type="text"  id="DI_DJ" name="DI_DJ" value="" datatype="0,is_double,100"/></td>
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
		$("#DI_FWSDM").val("服务商代码1");
		$("#DI_FWSMC").val("服务商名称1");
		$("#DI_DJ").val("70");
		$("#DI_FWSDM").attr("readonly",true);
		$("#DI_FWSMC").attr("readonly",true);
});
function doSave(){
	var $f
	=$("#di-clbzxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("clbzfxg");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>