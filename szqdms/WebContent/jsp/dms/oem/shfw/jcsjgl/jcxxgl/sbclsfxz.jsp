<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_sbclsf" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di_sbclsfForm" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_sbclsfTable">
					<tr>
						<td><label>省份：</label></td>
						<td><input type="text" id="DI_SF" name="DI_SF" datatype="1,is_null,100"  value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchProvinces">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_sbclsf">
			<table width="100%" id="di_sbclsflb" name="di_sbclsflb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:"></th>
						<th>身份</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>吉林</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>山西</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>陕西</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>上海</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar" id="opBtn" style="display:none">
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
	var $f = $("#di_sbclcxForm");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("sbclsf");
$(function()
{
	$("#di_searchProvinces").click(function(){
		$("#di_sbclsflb").show();
		$("#di_sbclsflb").jTable();
		$("#opBtn").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>