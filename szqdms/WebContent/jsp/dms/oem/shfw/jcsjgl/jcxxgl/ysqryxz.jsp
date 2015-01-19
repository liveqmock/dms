<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="ysqryxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-ysqryxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="ysqryxz">
					<tr>	
						<td><label>员工代码：</label></td>
						<td>XS1075</td>
					</tr>
					<tr>
						<td><label>员工姓名：</label></td>
						<td>霍华龙 </td>
					</tr>
					<tr>	
						<td><label>预授权级别：</label></td>
						<td><select type="text" class="combox" id="YSQJB" name="YSQJB" kind="dic" src="E#1=外出服务经理:2=服务追偿科科员:3=服务追偿科科长:4=服务部经理:5=销售公司经理" datatype="0,is_null,10" >
				        	<option value="1" selected>外出服务经理</option>
				        </select>
				        </td>
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
$(function(){
	//初始化页面
	$("#XZ_SQJBMC").val("外出服务经理");
});
function doSave(){
	var $f = $("#fm-ysqryxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("ysqryxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>