<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="jjrl" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-jjrl" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="jjrlTable">
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="di_pjdm" name="di_pjdm" value="PJDM001" readonly="readonly"></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="di_pjdm" name="di_pjdm" value="PJMC001" readonly="readonly"></td>
					</tr>
					<tr>
						<td><label>库存数量：</label></td>
						<td><input type="text" id="di_kcsl" name="di_kcsl" value="7"  readonly="readonly"/></td>
						<td><label>认领数量：</label></td>
						<td><input type="text" id="di_sl" name="di_sl" datatype="0,is_digit,100" /></td>
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
function doSave(){
	var $f = $("#di-jjrl");
	if (submitForm($f) == false) return false;
	
	var kcsl=$("#di_kcsl").val();
	var sl=$("#di_sl").val();
	if(sl-kcsl > 0 ){
		alertMsg.info("认领数量不能大于当前库存数量");
		return false;
	}
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("jjrl");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
}); 
</script>