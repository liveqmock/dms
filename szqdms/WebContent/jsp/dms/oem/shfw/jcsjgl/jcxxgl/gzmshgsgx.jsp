<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="gzmsgsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-gzmsgsxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="gzmsgsxx">
			    <tr>
					<td><label>故障模式代码：</label></td>
					<td><input type="text" id="DI_GZMSDM" name="DI_GZMSDM" datatype="1,is_null,100" value="GZMSDM1"  readonly="readonly" /></td>
				</tr>	
				<tr>
					<td><label>故障模式名称：</label></td>
					<td><input type="text" id="DI_GZMSMC" name="DI_GZMSMC" datatype="1,is_null,100" value="GZMSMC1"  readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>工时代码：</label></td>
					<td><input type="text" id="DI_GSDM" name="DI_GSDM" class="combox" kind="dic" src="E#工时代码1=工时名称1:工时代码2=工时名称2" datatype="0,is_null,100"  /></td>
					<td><input type="hidden"/></td>
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
	var $f = $("#di-gzmsgsxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("gzmshgsgx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>