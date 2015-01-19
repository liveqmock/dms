<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="gzmsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-gzmsxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="gzmsxz">
			    <tr>
					<td><label>故障代码：</label></td>
					<td><input type="text" id="DI_GZDM" name="DI_GZDM" datatype="0,is_digit_letter,100" /></td>
					<td><label>故障名称：</label></td>
					<td><input type="text" id="DI_GZMC" name="DI_GZMC" datatype="0,is_null,100" /></td>
					
				</tr>
				<tr>
					<td><label>车辆部位：</label></td>
					<td ><input type="text" id="DI_CLBW"  name="DI_CLBW" class="combox" dicWidth="300" value="" kind="dic" src="E#车辆部位代码1=车辆部位名称1:车辆部位代码2=车辆部位名称2" datatype="0,is_null,30"/></td>
					<td><label>严重程度：</label></td>
					<td><select type="text"  id="DI_YZCD" class="combox" kind="dic" src="E#1=致命故障:2=严重故障:3=一般故障">
							<option value=2>严重故障</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>故障模式代码：</label></td>
					<td><input type="text" id="DI_GZMSDM" name="DI_GZMSDM" class="combox" dicWidth="300" value="" kind="dic" src="E#故障模式代码1=故障模式代码1:故障模式代码2=故障模式代码2" datatype="0,is_null,100" /></td>
					<td><label>故障模式名称：</label></td>
					<td><input type="text" id="DI_GZMSMC" name="DI_GZMSMC" datatype="0,is_null,100" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
				    <td colspan="3"><textarea class="" rows="2" id="XZ_BZ" name="XZ_BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
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
	<script type="text/javascript">
var action = "<%=action%>";
var dialog = $("body").data("gzmsxx");
$(function(){
	//初始化页面
	if(action != "1")
	{
		$("#DI_GZDM").val("GZ001");
		//代码不可修改
		$("#DI_GZDM").attr("readonly",true);
		$("#DI_GZMC").val("故障名称1");
		$("#DI_CLBW").attr("code","车辆部位代码1");
		$("#DI_GZMSDM").val("故障模式代码1");
		$("#DI_GZMSDM").attr("code","故障模式代码1");
		$("#DI_GZMSMC").val("故障模式名称1");
		$("#DI_CLBW").val("车辆部位名称1");
	}
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});

function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="DI_GZMSDM")
	{
		if($("#DI_GZMSDM").val()=='故障模式代码1'){
			$("#DI_GZMSMC").val("故障模式名称1");
		}
		if($("#DI_GZMSDM").val()=='故障模式代码2'){
			$("#DI_GZMSMC").val("故障模式名称2");
		}
	}
	return true;
}
function doSave(){
	var $f = $("#di-gzmsxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
</script>
</div>
