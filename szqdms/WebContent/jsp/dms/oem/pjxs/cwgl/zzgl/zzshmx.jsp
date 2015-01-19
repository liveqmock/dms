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
			    	<td><label>服务商代码：</label></td>
					<td><input type="text" id="DI_FWSDM" name="DI_FWSDM" datatype="1,is_null,100" value="服务商代码1" readonly="readonly"/></td>
			    	<td><label>服务商名称：</label></td>
					<td><input type="text" id="DI_FWSMC" name="DI_FWSMC" datatype="1,is_null,100" value="服务商名称1" readonly="readonly"/></td>
					<td><label>转出账户类型：</label></td>
					<td><input type="text" id="DI_ZHLX" name="DI_ZHLX" value="返利账户" readonly="readonly"/></td>
					
				</tr>
				<tr>
					<td><label>转入账户类型：</label></td>
					<td><input type="text" id="DI_ZCZHLX" name="DI_ZCZHLX" value="现金账户" readonly="readonly"/></td>
					<td><label>转账金额：</label></td>
					<td><input type="text" id="DI_ZZJE" name="DI_ZZJE" datatype="1,is_double,100" value="10000000.00" readonly="readonly"/></td>
					<td><label>申请日期：</label></td>
					<td><input type="text" id="DI_SQRQ" name="DI_SQRQ" datatype="1,is_date,100" value="2014-06-06" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>转账原因：</label></td>
				    <td colspan="3"><textarea class="" rows="2" id="DI_ZZYY" name="DI_ZZYY" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
				<tr>
					<td><label>审核意见：</label></td>
				    <td colspan="3"><textarea class="" rows="2" id="DI_SHYJ" name="DI_SHYJ" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doPass()" id="pass">审核通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doReject()" id="reject">审核驳回</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("zzmx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function doPass(){
	alertMsg.info("审核通过");
}
function doReject(){
	alertMsg.info("审核驳回");
}
</script>