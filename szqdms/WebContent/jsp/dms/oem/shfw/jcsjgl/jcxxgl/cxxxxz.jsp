<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="cxxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-cxxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="gzxz">
			    <tr>
					<td><label>车型代码：</label></td>
					<td><input type="text" id="DI_CXDM" name="DI_CXDM" datatype="0,is_digit_letter,100" /></td>
				</tr>
				<tr>
					<td><label>车型名称：</label></td>
					<td><input type="text" id="DI_CXMC" name="DI_CXMC" datatype="0,is_null,100"  /></td>
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
		$("#DI_CXDM").val("CXDM1");
		//代码不可修改
		$("#DI_CXDM").attr("readonly",true);
		$("#DI_CXMC").val("车型名称1");
	}
});
function doSave(){
	var $f = $("#di-cxxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("cxxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>