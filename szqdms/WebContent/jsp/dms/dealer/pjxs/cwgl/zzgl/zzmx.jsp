<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="zzmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-zzmx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="zzmxT">
			    <tr>
					<td><label>转出账户类型：</label></td>
					<td><input type="text" id="DI_ZHLX" name="DI_ZHLX" value="返利账户" readonly="readonly"/></td>
				</tr>	
				<tr>
					<td><label>转入账户类型：</label></td>
					<td><input type="text" id="DI_ZHLX" name="DI_XJZH" value="现金账户" readonly="readonly"/></td>
				</tr>
				<tr >
					<td><label>余额：</label></td>
					<td><input type="text" id="DI_YE" name="DI_YE" datatype="1,is_null,100" value="10000000.00" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>转账金额：</label></td>
					<td><input type="text" id="DI_ZZJE" name="DI_ZZJE" class="combox" kind="dic" src="E#2014-05-04=100000.00:2014-06-05=150000.00" datatype="0,is_null,100" /></td>
					<td><input type="hidden"/></td>
				</tr>
				<tr>
					<td><label>转账原因：</label></td>
				    <td ><textarea class="" rows="2" id="DI_ZZYY" name="DI_ZZYY" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">申&nbsp;&nbsp;请</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
function doSave(){
	var $f = $("#zzmx");
	if (submitForm($f) == false) return false;
	var ye=$("#DI_YE").val();
	var zzje=$("#DI_ZZJE").val();
	if(ye - zzje < 0){
		alertMsg.info("申请转账金额不能大于余额！");
		return false;
	}
	alertMsg.info("申请成功！");
}
//弹出窗体
var dialog = $("body").data("zzmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>