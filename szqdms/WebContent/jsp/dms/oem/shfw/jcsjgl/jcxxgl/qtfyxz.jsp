<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="qtfyxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-qtfyxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="qtfyxz">
			    <tr>
					<td><label>项目代码：</label></td>
					<td><input type="text" id="XZ_XMDM" name="XZ_XMDM" datatype="0,is_digit_letter,100" /></td>
				</tr>	
				<tr>
					<td><label>项目名称：</label></td>
					<td><input type="text" id="XZ_XMMC" name="XZ_XMMC" datatype="0,is_null,100" /></td>
				</tr>
				<tr>
					<td colspan="2"><font color="red">*注:只维护项目，费用是在索赔单中填写的</font></td>
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
		$("#XZ_XMDM").val("TC2");
		//代码不可修改
		$("#XZ_XMDM").attr("readonly",true);
		$("#XZ_XMMC").val("高速拖车费");
	}
});
function doSave(){
	var $f = $("#fm-qtfyxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("qtfyxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>