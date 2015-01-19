<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="jjxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-jjxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="jjxxTable">
				<tr>
					<td><label>旧件代码：</label></td>
					<td><input type="text" id="di_jjdm" name="di_jjdm" datatype="1,is_null,100" value="旧件代码1" readOnly hasBtn="true" callFunction="openOldPart();"/></td>
			    	<td><label>旧件名称：</label></td>
			    	<td><input type="text" id="di_jjmc" name="di_jjmc" datatype="1,is_null,100" value="旧件名称1" readonly="readonly"/></td>
			    </tr>
			    <tr>
					<td><label>旧件数量：</label></td>
					<td><input type="text" id="di_jjsl" name="di_jjsl" datatype="0,is_null,100" value="2"/></td>
					<td><label>旧件类别：</label></td>
			    	<td><input type="text" id="di_jjlb" name="di_jjlb" datatype="1,is_null,100" value="类别1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>供应商代码：</label></td>
					<td><input type="text" id="di_gysdm" name="di_gysdm" datatype="1,is_null,100" value="供应商代码1" readonly="readonly"/></td>
			    	<td><label>供应商名称：</label></td>
			    	<td><input type="text" id="di_gysmc" name="di_gysmc" datatype="1,is_null,100" value="供应商代码1" readonly="readonly"/></td>
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
function openOldPart(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/jjgl/jjgl/spdjjxz.jsp", "spdjjxz", "旧件选择", options);
}
function doSave(){
	var $f = $("#fm-jjxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("spdjjbj");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>