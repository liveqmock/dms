<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="clxsrqxg" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-clxsrqxg" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="clxsrqxgTable">
				<tr>
					<td><label>VIN：</label></td>
    				<td><textarea name="textarea" id="di_vin" name="di_vin" cols="18" rows="3" ></textarea></td>
			 	</tr>
				<tr>
					<td><label>用户类别：</label></td>
					<td><select type="text" id="DI_YHLB" name="DI_YHLB" class="combox" kind="dic" src="E#1=军车:2=民车" datatype="0,is_null,100">
							<option value=1>军车</option>
						</select>
					</td>
					<td><label>销售日期：</label></td>
					<td>
			    		<input type="text" id="di-xsrq"  name="di-xsrq"  datatype="0,is_null,30" onclick="WdatePicker()" />
			   		</td>
			 	</tr>
			 	<tr>
			 		<td><label>车辆用途 ：</label></td>
			 		<td><select type="text" id="DI_CLYT" name="DI_CLYT" class="combox" kind="dic" src="E#1=公路用车:2=非公路用车" datatype="0,is_null,100">
							<option value=1>公路用车</option>
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
		$("#di_vin").val("VIN1");
		$("#di-xsrq").val("2014-4-30");
		$("#di_vin").attr("readonly",true);
});
function doSave(){
	var $f = $("#di-clxsrqxg");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("clxsrqxg");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>