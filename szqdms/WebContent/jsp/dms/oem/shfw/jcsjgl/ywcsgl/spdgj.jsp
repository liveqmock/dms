<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="cdrqxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form  method="post" id="claimform">
			<div id="spd">
			<table style="display:none" width="100%"  id="spdlb" name="spdlb" >
					<thead>
						<tr>
							<th  name="XH" style="display:" >序号</th>
							<th>索赔单号</th>
							<th>超单天数</th>
							<th>修改日期</th>
							<th>修改人</th>
							<th>修改类型</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>索赔单号1</td>
							<td>8</td>
							<td>2014-5-23</td>
							<td>霍华龙</td>
							<td>新增</td>
						</tr>
						<tr>
							<td>2</td>
							<td>索赔单号2</td>
							<td>8</td>
							<td>2014-5-23</td>
							<td>霍华龙</td>
							<td>删除</td>
						</tr>
						<tr>
							<td>3</td>
							<td>索赔单号3</td>
							<td>8</td>
							<td>2014-5-23</td>
							<td>霍华龙</td>
							<td>新增</td>
						</tr>
						<tr>
							<td>4</td>
							<td>索赔单号4</td>
							<td>8</td>
							<td>2014-5-23</td>
							<td>霍华龙</td>
							<td>新增</td>
						</tr>
					</tbody>
			</table>
			</div>
		</form>
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
	$("#spdlb").show();
	$("#spdlb").jTable();
});
//弹出窗体
var dialog = $("body").data("spdxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>