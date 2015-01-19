<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="jjxhmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-jjxhmx" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="jjxhmxTable">
					<tr>
					    <td><label>供应商代码：</label></td>
						<td>GYSDM001</td>
						<td><label>供应商名称：</label></td>
						<td>GYSMC001</td>
				 	</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td>PJDM001</td>
						<td><label>配件名称：</label></td>
						<td>PJMC001</td>
					</tr>
					<tr>
						<td><label>当前库存数量：</label></td>
						<td id="DQKCSL">7</td>
						<td><label>旧件销毁数量：</label></td>
						<td><input type="text" id="JJXHSL" name="JJXHSL" value="7" datatype="0,is_digit,100" /></td>
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
	var $f = $("#di-jjxhmx");
	if (submitForm($f) == false) return false;
	
    var kcsl=$("#DQKCSL").html();
	var xhsl=$("#JJXHSL").val();
	if(xhsl - kcsl > 0){
		alertMsg.info("销毁数量不能大于当前库存数量");
		return false;
	}
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("jjxhmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
}); 
</script>