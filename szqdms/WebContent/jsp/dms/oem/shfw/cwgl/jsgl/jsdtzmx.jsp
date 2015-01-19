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
					<td><label>服务站名称：</label></td>
					<td>服务站名称1</td>
					<td><label>结算产生日期：</label></td>
					<td>2014-4-8</td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<form method="post" id="dia-fm-fwfy" class="editForm" style="width:100%;">
		  <div align="left">
			  <fieldset>
		            <legend align="right"><a onclick="onTitleClick('dia-tab-fwfyxx')">&nbsp;服务费用信息&gt;&gt;</a></legend>
					<table class="editTable" id="dia-tab-fwfyxx">
						<tr>
							<td><label>服务费：</label></td>
							<td><input type="text" id="di_fwf" name="di_fwf" value="400.00" readonly="readonly"/></td>
							<td><label>旧件运费：</label></td>
							<td><input type="text" id="di-jjyf" name="di-jjyf" datatype="0,is_money,30" value="100.00" /></td>
						</tr>
						<tr>
							<td><label>政策支持：</label></td>
							<td><input type="text" id="di-zccc" name="di-zccc" datatype="0,is_money,30" value="0" /></td>
							<td><label>礼金：</label></td>
							<td><input type="text" id="di-lj" name="di-lj" datatype="0,is_money,30" value="0" /></td>
						</tr>
						<tr>
							<td><label>售车奖励：</label></td>
							<td><input type="text" id="di-scjl" name="di-scjl" datatype="0,is_money,30" value="0" /></td>
							<td><label>考核费用：</label></td>
							<td><input type="text" id="di-lj" name="di-lj" datatype="0,is_money,30" value="0" /></td>
						</tr>
						<tr>
							<td><label>其他费用：</label></td>
							<td><input type="text" id="di-qtfy" name="di-qtfy" datatype="0,is_money,30" value="0" /></td>
							<td><label>汇总：</label></td>
							<td><input type="text" id="di-lj" name="di-lj" datatype="1,is_money,30" value="500.00" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="2"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
						</tr>
					</table>
			  </fieldset>
			</div>
			</form>	
			<form method="post" id="dia-fm-clfywh" class="editForm" style="width:100%;">
		  <div align="left">
			  <fieldset>
		            <legend align="right"><a onclick="onTitleClick('dia-tab-clfyxx')">&nbsp;材料费用信息&gt;&gt;</a></legend>
					<table class="editTable" id="dia-tab-clfyxx">
						<tr>
							<td><label>材料费：</label></td>
							<td><input type="text" id="di_fwf" name="di_fwf" value="1000.00" readonly="readonly"/></td>
							<td><label>配件返利：</label></td>
							<td><input type="text" id="di-jjyf" name="di-jjyf" datatype="0,is_money,30" value="0" /></td>
						</tr>
						<tr>
							<td><label>政策支持：</label></td>
							<td><input type="text" id="di-zccc" name="di-zccc" datatype="0,is_money,30" value="0" /></td>
							<td><label>礼金：</label></td>
							<td><input type="text" id="di-lj" name="di-lj" datatype="0,is_money,30" value="0" /></td>
						</tr>
						<tr>
							<td><label>售车奖励：</label></td>
							<td><input type="text" id="di-scjl" name="di-scjl" datatype="0,is_money,30" value="0" /></td>
							<td><label>考核费用：</label></td>
							<td><input type="text" id="di-lj" name="di-lj" datatype="0,is_money,30" value="0" /></td>
						</tr>
						<tr>
							<td><label>其他费用：</label></td>
							<td><input type="text" id="di-qtfy" name="di-qtfy" datatype="0,is_money,30" value="0" /></td>
							<td><label>汇总：</label></td>
							<td><input type="text" id="di-lj" name="di-lj" datatype="1,is_money,30" value="1000.00" readonly="readonly"/></td>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">调整完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
function doSave(){
	alertMsg.info("调整完成");
	$.pdialog.closeCurrent();
}
$(function()
{
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
});
</script>