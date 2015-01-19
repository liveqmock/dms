<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="clbwxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-clbwxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="clbwxz">
			    <tr>
					<td><label>车辆部位代码：</label></td>
					<td><input type="text" id="DI_CLBWDM" name="DI_CLBWDM" datatype="0,is_digit_letter,100" /></td>
				</tr>
				<tr>
					<td><label>车辆部位名称：</label></td>
					<td><input type="text" id="DI_CLBWMC" name="DI_CLBWMC" datatype="0,is_null,100"  /></td>
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
		$("#DI_CLBWDM").val("CXBWDM1");
		//代码不可修改
		$("#DI_CLBWDM").attr("readonly",true);
		$("#DI_CLBWMC").val("车辆部位名称1");
	}
});
function doSave(){
	var $f = $("#di-clbwxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("clbwxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>