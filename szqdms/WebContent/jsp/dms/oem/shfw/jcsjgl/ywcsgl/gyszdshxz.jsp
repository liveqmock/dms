<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="cdrqxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-gyszdshxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="gyszdshxz">
				<tr>
					<td><label>供应商代码：</label></td>
					<td><input type="text" id="DI_GYSDM" name="DI_GYSDM" datatype="0,is_null,100"  value=""/></td>
				</tr>
				<tr>
					<td><label>供应商商名称：</label></td>
					<td><input type="text" id="DI_GYSMC" name="DI_GYSMC" datatype="0,is_null,100" value=""/></td>
				</tr>
				<tr>	
					<td><label>自动审核时间(分钟)：</label></td>
					<td><input type="text"  id="DI_SJ" name="DI_SJ" datatype="0,is_null,100" value="" /></td>
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
	if(action != "1"){
		$("#DI_GYSDM").val("供应商代码1");
		$("#DI_GYSMC").val("供应商名称1");
		$("#DI_SJ").val("30");
		$("#DI_GYSDM").attr("readonly",true);
		$("#DI_GYSMC").attr("readonly",true);
	}
});
function doSave(){
	var $f = $("#di-gyszdshxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("gyszdshxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>