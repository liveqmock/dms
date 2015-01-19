<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_zgmspj" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di_zgmspjfm" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_zgmspjTable">
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="DI_PJDM" name="DI_PJDM" datatype="1,is_null,100"  value="" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="DI_PJMC" name="DI_PJMC" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchPart">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_zgmspj">
			<table width="100%" id="di_zgmspjlb" name="di_zgmspjlb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:" ></th>
						<th>配件代码</th>
						<th>配件名称</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码1</td>
						<td>名称1</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码2</td>
						<td>名称2</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码3</td>
						<td>名称3</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码4</td>
						<td>名称4</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar" id="opBtnPart" style="display:none">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">确&nbsp;&nbsp;定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
function doSave(){
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("gzmspjxz");
$(function()
{
	$("#di_searchPart").click(function(){
		if($("#di_zgmspjlb").is(":hidden")){
			$("#di_zgmspjlb").show();
			$("#di_zgmspjlb").jTable();	
		}
		$("#opBtnPart").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>