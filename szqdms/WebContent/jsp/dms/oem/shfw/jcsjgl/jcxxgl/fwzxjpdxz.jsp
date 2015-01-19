<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="fwzxjpdxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-fwzxjpdxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="fwzxjpdxz">
			    <tr>
					<td><label>服务商代码：</label></td>
					<td>服务商代码1</td>
				</tr>
				<tr>	
					<td><label>服务商名称：</label></td>
					<td>服务商名称1 </td>
				</tr>
				<tr>
					<td><label>星级评定：</label></td>
					<td><select type="text" id="XZ_XJPD" name="XZ_XJPD" class="combox" kind="dic" src="E#1=1:2=2:3=3:4=4" datatype="0,is_digit,100"  >
							<option value=3 selected>3</option>
						</select>
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
var action = "<%=action%>";
$(function(){
	//初始化页面
	if(action != "1")
	{
		$("#XZ_XJPD").val("3");
	}
});
function openFws(){
	alertMsg.info("弹出没有维护星级评定的服务商！");
}
function doSave(){
	var $f = $("#fm-fwzxjpdxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("fwzxjpdxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>