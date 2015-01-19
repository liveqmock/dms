<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="zzshmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-zzshmx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="zzshmxT">
			    <tr>
			    	<td><label>配送中心代码：</label></td>
					<td><input type="text" id="DI_PSZXDM" name="DI_PSZXDM" datatype="1,is_null,100" value="配送中心代码1" readonly="readonly"/></td>
			    	<td><label>配送中心名称：</label></td>
					<td><input type="text" id="DI_PSZXMC" name="DI_PSZXMC" datatype="1,is_null,100" value="配送中心名称1" readonly="readonly"/></td>
					<td><label>订单号：</label></td>
					<td><input type="text" id="DI_DDH" name="DI_DDH" value="订单号1" readonly="readonly"/></td>
					
				</tr>
				<tr>
					<td><label>订单状态：</label></td>
					<td><input type="text" id="DI_DDZT" name="DI_DDZT" datatype="1,is_null,100" value="已关闭" readonly="readonly"/></td>
					<td><label>开票状态：</label></td>
					<td><input type="text" id="DI_KPZT" name="DI_KPZT" datatype="1,is_null,100" value="未开票" readonly="readonly"/></td>
					<td><label>金额：</label></td>
					<td><input type="text" id="DI_JE" name="DI_JE" datatype="1,is_null,100" value="400" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>开票单号：</label></td>
					<td><input type="text" id="DI_KPDH" name="DI_KPDH" datatype="0,is_digit_letter,100" /></td>
					<td><label>开票日期：</label></td>
					<td><input type="text" id="DI_KPRQ" name="DI_KPRQ" datatype="0,is_datetime,100" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss '})" /></td>
					<td><label>开票金额：</label></td>
					<td><input type="text" id="DI_KPJE" name="DI_KPJE" datatype="0,is_duoble,100" /></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
				    <td colspan="3"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">开&nbsp;&nbsp;票</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("kpglmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function doSave(){
	alertMsg.info("开票完成");
}
</script>