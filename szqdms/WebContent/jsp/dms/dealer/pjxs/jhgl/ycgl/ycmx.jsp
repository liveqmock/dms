<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_ycmx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di_ycmxfm" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="di_ycmxT">
				<tr>
					<td><label>服务商代码：</label></td>
					<td><input type="text" id="DI_FWSDM" name="DI_FWSDM" value="服务商代码1" readonly="readonly"/></td>
					<td><label>服务商名称：</label></td>
					<td><input type="text" id="DI_FWSMC" name="DI_FWSMC" value="服务商名称1" readonly="readonly"/></td>
				</tr>
				<tr>	
					<td><label>预测月份：</label></td>
					<td><input type="text"  id="DI_YCYF" name="DI_YCYF" value="2014-06" readonly="readonly"/></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<form  method="post" id="partform">
			<div id="ycmx">
			<table style="display:none" width="100%"  id="ycmxlb" name="ycmxlb" >
					<thead>
						<tr>
							<th name="XH" style="display:">序号</th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th>数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td>60</td>
						</tr>
						<tr>
							<td>2</td>
							<td>配件代码2</td>
							<td>配件名称2</td>
							<td>60</td>
						</tr>
						<tr>
							<td>3</td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td>60</td>
						</tr>
					</tbody>
			</table>
			</div>
		</form>
		<div class="formBar" id="opBtn" >
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("ycsbxx");
$(function()
{
	$("#ycmxlb").show();
	$("#ycmxlb").jTable();
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>