<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="jsdxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-jsdxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="jsdyjxx">
				<tr>
					<td><label>结算单号：</label></td>
					<td>结算单号1</td>
					<td><label>结算产生日期：</label></td>
					<td>2014-4-8</td>
				</tr>
				<tr>
					<td><label>结算类型：</label></td>
					<td>服务费</td>
					<td><label>索赔总金额(元)：</label></td>
					<td>400</td>
				</tr>
				<tr>
					<td><label>付款票号：</label></td>
					<td><input type="text" id="di_yddh" name="di_yddh" datatype="0,is_digit_letter,100"/></td>
					<td><label>付款时间：</label></td>
					<td><input type="text" id="di-kpsj" name="di-kpsj" datatype="0,is_date,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
				</tr>
				<tr>
					<td><label>付款金额：</label></td>
					<td><input type="text" id="di_dh" name="di_fpje" datatype="0,is_double,100"/></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
				    <td colspan="2"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">付款完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
function doSave(){
	var $f = $("#di-jsdxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("付款完成");
}
//弹出窗体
var dialog = $("body").data("fkdjmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>