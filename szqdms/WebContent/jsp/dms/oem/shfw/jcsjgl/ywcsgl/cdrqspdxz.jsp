<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flag = request.getParameter("flag");
	if(flag == null)
		flag = "1";
%>
<div id="cdrqspxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-cdrqspxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="cdrqsp">
				<tr>
					<td><label>索赔单号：</label></td>
					<td><input type="text" id="DI_SPDH" name="DI_SPDH" value="" readOnly hasBtn="true" callFunction="openClaim();"/></td>
					<td><label>超单天数：</label></td>
					<td><input type="text"  id="DI_SPCDTS" name="DI_SPCDTS" value="" /></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSaveClaim()" id="save1">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var flag = "<%=flag%>";
$(function(){
	if(flag != "1"){
		$("#DI_SPDH").val("索赔单号1");
		$("#DI_SPDH").attr("readonly",true);
		$("#DI_SPCDTS").val("8");
	}
});
function openClaim(){
	alertMsg.info("弹出被驳回索赔单！");
}
function doSaveClaim(){
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("cdrqspd");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>