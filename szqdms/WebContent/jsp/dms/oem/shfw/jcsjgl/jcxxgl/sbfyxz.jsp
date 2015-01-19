<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="qtfyxx" style="width;100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-qtfyxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="qtfyxz">
				    <tr>
						<td><label>驱动形式：</label></td>
						<td><input type="text" id="XZ_QDXS" name="XZ_QDXS" datatype="0,is_null,100"/></td>
					</tr>	
					<tr>
						<td><label>首保费用：</label></td>
						<td><input type="text" id="XZ_SBFY" name="XZ_SBFY" datatype="0,is_double,100" /></td>
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
		$("#XZ_QDXS").val("6*2");
		$("#XZ_QDXS").attr("readonly",true);
		$("#XZ_SBFY").val("150");
	}
});
function doSave(){
	var $f = $("#fm-qtfyxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("sbfyxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>