<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="xjzhlx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<div id="zhcx">
			<table width="100%" id="zhcxlb" name="zhcxlb" style="display: none" >
				<thead>
					<tr>
						<th>账户类型</th>
						<th align="right">余额(元)</th>
						<th align="right">占用(元)</th>
						<th align="right">可用余额(元)</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>定金账户</td>
						<td>2,000,000.00</td>
						<td>1,000,000.00</td>
						<td>1,000,000.00</td>
					</tr>
					<tr>
						<td>发车款账户</td>
						<td>5,000,000.00</td>
						<td>3,000,000.00</td>
						<td>2,000,000.00</td>
					</tr>
					<tr>
						<td>信用额度账户</td>
						<td>1,000,000.00</td>
						<td>0.00</td>
						<td>1,000,000.00</td>
					</tr>
					<tr>
						<td>返利账户</td>
						<td>500,000.00</td>
						<td></td>
						<td>500,000.00</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	$("#zhcxlb").show();
	$("#zhcxlb").jTable();
});
//弹出窗体
var dialog = $("body").data("xjzhlx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>