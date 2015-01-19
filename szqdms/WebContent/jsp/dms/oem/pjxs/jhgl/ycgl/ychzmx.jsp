<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_ychzmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di_ychzmxfm" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="di_ychzmxT">
				<tr>	
					<td><label>预测月份：</label></td>
					<td><input type="text"  id="DI_HZ_YCYF" name="DI_HZ_YCYF" value="2014-06" readonly="readonly"/></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<form  method="post" id="partform">
			<div id="ychzmxdz">
			<table style="display:none" width="100%"  id="ychzmxdzlb" name="ychzmxdzlb" >
					<thead>
						<tr>
							<th type="multi" name="XH" style="display:"></th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th>数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td><input type="button" id="btnj1" style="width:20px" value="-"/><input type="text" id="val1" style="width:35px" value="240"/><input type="button"  id="btnj11" style="width:20px" value="+"/></td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码2</td>
							<td>配件名称2</td>
							<td><input type="button" id="btnj2" style="width:20px" value="-"/><input type="text" id="val2" style="width:35px" value="240"/><input type="button"  id="btnj22" style="width:20px" value="+"/></td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td><input type="button" id="btnj3" style="width:20px" value="-"/><input type="text" id="val3" style="width:35px" value="240"/><input type="button"  id="btnj33" style="width:20px" value="+"/></td>
						</tr>
					</tbody>
			</table>
			</div>
		</form>
		<div class="formBar" id="opBtn" >
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("ychzmx");
$(function()
{
	$("#ychzmxdzlb").show();
	$("#ychzmxdzlb").jTable();
	$("#btnj1").click(function(){
		var val1=$("#val1").val()-0;
		var val=val1-1;
		$("#val1").val(val);
	});
	$("#btnj11").click(function(){
		var val1=$("#val1").val()-0;
		var val=val1+1;
		$("#val1").val(val);
	});
	
	$("#btnj2").click(function(){
		var val1=$("#val2").val()-0;
		var val=val1-1;
		$("#val2").val(val);
	});
	$("#btnj22").click(function(){
		var val1=$("#val2").val()-0;
		var val=val1+1;
		$("#val2").val(val);
	});
	$("#btnj3").click(function(){
		var val1=$("#val3").val()-0;
		var val=val1-1;
		$("#val3").val(val);
	});
	$("#btnj33").click(function(){
		var val1=$("#val3").val()-0;
		var val=val1+1;
		$("#val3").val(val);
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function doSave(){
	alertMsg.info("保存成功！");
}
</script>