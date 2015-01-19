<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_jjkcxx" style="width:100%;">
	<div class="page">
	<div class="pageContent">
		<div id="di_jjkcmx">
			<table width="100%" id="di_jjkcmxlb" name="di_jjkcmxlb" style="display:" >
				<thead>
					<tr>
						<th  name="XH" style="display:" >序号</th>
						<th>供应商代码</th>
						<th>供应商名称</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>入库日期</th>
						<th>库存数量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>供应商代码1</td>
						<td>供应商代码1</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>2014-04-23</td>
						<td>1</td>
					</tr>
					<tr>
						<td>2</td>
						<td>供应商代码2</td>
						<td>供应商代码2</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>2014-04-23</td>
						<td>1</td>
					</tr>
					<tr>
						<td>3</td>
						<td>供应商代码3</td>
						<td>供应商代码3</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>2014-04-23</td>
						<td>1</td>
					</tr>
					<tr>
						<td>4</td>
						<td>供应商代码4</td>
						<td>供应商代码4</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>2014-04-23</td>
						<td>1</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar" >
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("jjkcmx");
$(function()
{
	$("#di_jjkcmxlb").jTable();
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>