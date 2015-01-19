<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="spjbcsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-spjbcsxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="spjbcsxz">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="XZ_FWSDM" name="XZ_FWSDM" datatype="1,is_null,100" readOnly hasBtn="true" callFunction="openFws();" /></td>
				    </tr>
				    <tr>
						<td><label>基础工时(元)：</label></td>
						<td><input type="text" id="XZ_JCGS" name="XZ_JCGS" datatype="0,is_double,100" /></td>
						<td><label>工时系数：</label></td>
						<td><input type="text" id="XZ_GSXS" name="XZ_GSXS" datatype="0,is_double,100" /></td>
					</tr>
					<tr>
						<td><label>用户类别：</label></td>
						<td><select type="text" id="XZ_YHLB" name="XZ_YHLB" class="combox" kind="dic" src="E#1=军车:2=民车" datatype="0,is_null,100">
								<option value=1>军车</option>
							</select>
						</td>
						<td><label>工时单价(元)：</label></td>
						<td><input type="text" id="XZ_GSDJ" name="XZ_GSDJ" datatype="0,is_double,100" /></td>
					</tr>
					<tr>
						<td colspan="2"><label><font color="red">*注：工时单价=基础工时*工时系数，工时单价不可编辑</font></label></td>
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
	$("#XZ_GSDJ").attr("readonly",true);
	if(action != "1")
	{
		$("#XZ_FWSDM").val("服务商代码1");
		//服务商代码不可修改
		$("#XZ_FWSDM").attr("readonly",true);
		$("#XZ_JCGS").val("30.00");
		$("#XZ_GSXS").val("1.00");
		$("#XZ_GSDJ").val("30.00");
	}
});
function openFws(){
	alertMsg.info("选择服务商树！");
}
function doSave(){
	var $f = $("#fm-spjbcsxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("spjbcs");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>