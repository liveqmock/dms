<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="gzxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-gzxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="gzxz">
				<tr>
					<td><label>代码类别：</label></td>
					<td><select type="text" class="combox" id="XZ_DMLB" name="XZ_DMLB" kind="dic" src="E#1=故障来源:2=故障程度:3=故障分析" datatype="0,is_null,10" >
		        		<option value="1" selected>故障来源</option>
		        	</select>
		        	</td>
			    </tr>
			    <tr>
					<td><label>代码：</label></td>
					<td><input type="text" id="XZ_DM" name="XZ_DM" datatype="0,is_digit_letter,100" /></td>
				</tr>
				<tr>
					<td><label>描述：</label></td>
				    <td ><textarea class="" rows="2" id="XZ_MS" name="XZ_MS" style="width:100%" datatype="1,is_null,500"></textarea></td>
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
		$("#XZ_DM").val("GZLY1");
		//代码不可修改
		$("#XZ_DM").attr("readonly",true);
		$("#XZ_MS").val("来电");
	}
});
function doSave(){
	var $f = $("#fm-gzxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("gzxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>