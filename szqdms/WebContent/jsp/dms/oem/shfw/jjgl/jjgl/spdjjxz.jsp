<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_in_jjxz" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di_in_jjxzform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_in_jjxzTable">
					<tr>
						<td><label>旧件代码：</label></td>
						<td><input type="text" id="di_id_jjdm" name="di_id_jjdm" datatype="1,is_null,100"  value="" /></td>
						<td><label>旧件名称：</label></td>
						<td><input type="text" id="di_id_jjmc" name="di_id_jjmc" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchOldPart">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_in_jj">
			<table width="100%" id="di_in_jjlb" name="di_in_jjlb" style="display: none" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:" ></th>
						<th>旧件代码</th>
						<th>旧件名称</th>
						<th>旧件类别</th>
						<th>旧件数量</th>
						<th>供应商代码</th>
						<th>供应商名称</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="radio" onclick="doSelectOldPart()" /></td>
						<td>代码1</td>
						<td>名称1</td>
						<td>类别1</td>
						<td><input type="text" id="sl"/></td>
						<td>供应商代码1</td>
						<td>供应商名称1</td>
					</tr>
					<tr>
						<td><input type ="radio" onclick="doSelectOldPart()"/></td>
						<td>代码2</td>
						<td>名称2</td>
						<td>类别2</td>
						<td><input type="text" id="sl"/></td>
						<td>供应商代码2</td>
						<td>供应商名称2</td>
					</tr>
					<tr>
						<td><input type ="radio" onclick="doSelectOldPart()"/></td>
						<td>代码3</td>
						<td>名称3</td>
						<td>类别3</td>
						<td><input type="text" id="sl"/></td>
						<td>供应商代码3</td>
						<td>供应商名称3</td>
					</tr>
					<tr>
						<td><input type ="radio" onclick="doSelectOldPart()"/></td>
						<td>代码4</td>
						<td>名称4</td>
						<td>类别4</td>
						<td><input type="text" id="sl"/></td>
						<td>供应商代码4</td>
						<td>供应商名称4</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("spdjjxz");
function doSelectOldPart(){
	$("#di_jjdm").val("旧件代码2");
	$("#di_jjmc").val("旧件名称2");
	$("#di_jjsl").val("1");
	$("#di_jjlb").val("旧件类别2");
	$("#di_gysdm").val("供应商代码2");
	$("#di_gysmc").val("供应商代码2");
	$.pdialog.close(dialog);
	return false;
}
$(function()
{
	$("#di_searchOldPart").click(function(){
		$("#di_in_jjlb").show();
		$("#di_in_jjlb").jTable();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>