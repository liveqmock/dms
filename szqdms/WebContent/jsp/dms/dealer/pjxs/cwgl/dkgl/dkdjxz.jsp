<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="dkdjxz" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-dkdjxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="dkdjxzT">
			    <tr>
					<td><label>账户类型：</label></td>
					<td><select type="text" id="DI_ZHLX" name="DI_ZHLX" class="combox" kind="dic" src="E#1=现金账户:2=承兑汇票账户" datatype="1,is_null,100" value="" >
							<option value=1>现金账户</option>
						</select>
					</td>
				</tr>	
				<tr id="tr1" style="display:none">
					<td><label>承兑票号：</label></td>
					<td><input type="text" id="DI_CDPH" name="DI_CDPH" datatype="0,is_digit_letter,100" /></td>
				</tr>
				<tr>
					<td><label>金额：</label></td>
					<td><input type="text" id="DI_JE" name="DI_JE" datatype="0,is_double,100" /></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
				    <td colspan="3"><textarea class="" rows="2" id="DI_BZ" name="DI_BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="di_doApply()" id="di_apply">提&nbsp;&nbsp;报</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	$("#DI_ZHLX").change(function(){
		if($(this).val()==1){
			$("#tr1").hide();
		}
		if($(this).val()==2){
			$("#tr1").show();
		}
	});
});
function doSave(){
	var $f = $("#di-dkdjxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
function di_doApply(){
	var $f = $("#di-dkdjxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("提报成功！");
}
//弹出窗体
var dialog = $("body").data("dkdjxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>