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
					<td><label>开票状态：</label></td>
					<td><input type="text" id="DI_KPZT" name="DI_KPZT" datatype="1,is_null,100" value="已开票" readonly="readonly"/></td>
					<td><label>金额：</label></td>
					<td><input type="text" id="DI_JE" name="DI_JE" datatype="1,is_null,100" value="400" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>开票单号：</label></td>
					<td><input type="text" id="DI_KPDH" name="DI_KPDH" datatype="1,is_null,100" value="开票单号1" readonly="readonly"/></td>
					<td><label>开票日期：</label></td>
					<td><input type="text" id="DI_KPRQ" name="DI_KPRQ" datatype="1,is_null,100" value="2014-06-03" readonly="readonly"/></td>
					<td><label>开票金额：</label></td>
					<td><input type="text" id="DI_KPJE" name="DI_KPJE" datatype="1,is_null,100" value="400" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>快递单号：</label></td>
					<td><input type="text" id="di_yddh" name="di_yddh" datatype="0,is_digit_letter,100"/></td>
					<td><label>收件人：</label></td>
					<td><input type="text" id="di_sjr" name="di_sjr" datatype="0,is_null,100"/></td>
				</tr>
				<tr>
					<td><label>电话：</label></td>
					<td><input type="text" id="di_dh" name="di_dh" datatype="0,is_phone,11"/></td>
					<td><label>地址：</label></td>
					<td><input type="text" id="di_dz" name="di_dz" datatype="0,is_null,100"/></td>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">邮&nbsp;&nbsp;寄</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("yjmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function doSave(){
	alertMsg.info("邮寄完成");
}
</script>