<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="jlxsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-jlxsxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="jlxsxz">
				<tr>
					<td><label>服务商代码：</label></td>
					<td><input type="text" id="XZ_WFSDM" name="XZ_WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openFwz();"/></td>
			    	<td><label>激励类别：</label></td>
			    	<td><select type="text"  kind="dic" src="E#1=大客户:2=NPG" id="XZ_JLLB" name="XZ_JLLB" datatype="0,is_null,100">
			    	 		<option value=1 selected>大客户</option>
			    	 	</select>
			    	</td>
			    </tr>
			    <tr>
					<td><label>激励系数：</label></td>
					<td><input type="text" id="XZ_JLXS" name="XZ_JLXS" datatype="0,is_double,100" /></td>
					<td ><label>发动机型号：</label></td>
			    	<td id="fdjse">
			    		<select type="text"  kind="dic" src="E#1=c30020:2=ISM340E20"  id="XZ_FDJXH" name="XZ_FDJXH" datatype="1,is_null,100">
			    	 		<option value=1 selected>c30020</option>
			    	 	</select>
			    	</td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td colspan="3"><textarea id="XZ_MS" name="XZ_MS"  class="dos placeholder collect" rows="2" style="width:100%" datatype="1,is_null,500"  placeholder="请输入文字内容，支持普通文本和网址！"/></td>
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
	$("#fdjse").hide();
	//初始化页面
	if(action != "1")
	{
		$("#XZ_WFSDM").val("服务商代码1");
		$("#XZ_JLXS").val("0.2 ");
	}
	$("#XZ_JLLB").change(function(){
		var jllbVal=$(this).val();
		if(jllbVal==1){
			$("#fdjse").hide();
		}
		if(jllbVal==2){
			$("#fdjse").show();
		}
	});
});
function openFwz(){
	alertMsg.info("服务站选择界面");
}
function doSave(){
	var $f = $("#fm-jlxsxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("jlxsxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>