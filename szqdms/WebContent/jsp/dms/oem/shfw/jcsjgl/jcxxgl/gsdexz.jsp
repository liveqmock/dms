<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="gsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-gsxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="gzxz">
			    <tr>
					<td><label>工时代码：</label></td>
					<td><input type="text" id="XZ_GSDM" name="XZ_GSDM" datatype="0,is_digit_letter,100" /></td>
					<td><label>工时名称：</label></td>
					<td><input type="text" id="XZ_GSMC" name="XZ_GSMC" datatype="0,is_null,100" /></td>
				</tr>
				<tr>
					<td><label>用户类别：</label></td>
					<td><select type="text" id="XZ_YHLB" name="XZ_YHLB" class="combox" kind="dic" src="E#1=军车:2=民车" datatype="0,is_null,100">
							<option value=1>军车</option>
						</select>
					</td>
					<td><label>工时定额：</label></td>
					<td><input type="text" id="XZ_GSDE" name="XZ_GSDE" datatype="0,is_digit,100"  /></td>
				</tr>
				<tr>
					<td><label>车型：</label></td>
					<td><input type="text" id="XZ_CX"  operation="right_like"  name="XZ_CX" class="combox" kind="dic" src="E#车型代码1=车型名称1:车型代码2=车型名称2"/></td>
					<td><label>车辆部位：</label></td>
					<td><input type="text" id="XZ_CLBW" operation="right_like" style="width:220" name="XZ_CLBW" class="combox" kind="dic" src="E#车辆部位代码1=车辆部位名称1:车辆部位代码2=车辆部位名称2"/></td>
					<td><input type="hidden"/></td>
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
</div>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	//初始化页面
	if(action != "1")
	{
		$("#XZ_GSDM").val("11002");
		//代码不可修改
		$("#XZ_GSDM").attr("readonly",true);
		$("#XZ_GSMC").val("换中减过桥箱盖");
		$("#XZ_GSDE").val("8");
		$("#XZ_CX").val("车型名称1");
		$("#XZ_CLBW").val("车辆部位名称1");
	}
});
function doSave(){
	var $f = $("#fm-gsxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("gsxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>