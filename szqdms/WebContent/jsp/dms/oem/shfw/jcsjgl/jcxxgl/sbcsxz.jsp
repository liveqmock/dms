<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sbcsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-sbcsxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="sbcsxz">
					<tr>
						<td><label>发动机：</label></td>
						<td><input type="text" id="XZ_FDJ" name="XZ_FDJ" datatype="0,is_double,100" /></td>
					</tr>
					<tr>
						<td><label>变速箱：</label></td>
						<td><input type="text" id="XZ_BSX" name="XZ_BSX" datatype="0,is_double,100" /></td>
					</tr>
					<tr>
						<td><label>桥：</label></td>
						<td><input type="text" id="XZ_Q" name="XZ_Q" datatype="0,is_double,100" /></td>
					</tr>
					<tr>
						<td colspan="2"><font color="red">*注：桥等于驱动形式的首保费用减去发动机、变速箱的值</font></td>
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
	//初始化页面
	$("#XZ_FDJ").val("60");
	$("#XZ_BSX").val("50");
	$("#XZ_Q").val("40");
});
function doSave(){
	var $f = $("#fm-sbcsxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("sbcsxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>