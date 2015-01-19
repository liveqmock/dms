<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdxm" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fwhdxm" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdxmTable">
					<tr>
						<td><label>项目代码：</label></td>
						<td><input type="text" id="DI_XMDM" name="DI_XMDM" datatype="1,is_null,100"  value="" /></td>
						<td><label>项目名称：</label></td>
						<td><input type="text" id="DI_XMMC" name="DI_XMMC" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchModel">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_fwhdxm">
			<table width="100%" id="di_fwhdxmlb" name="di_fwhdxmlb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:" ></th>
						<th>项目代码</th>
						<th>项目名称</th>
						<th align="right">费用(元)</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码1</td>
						<td>名称1</td>
						<td><input type="text" id="fy"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码2</td>
						<td>名称2</td>
						<td><input type="text" id="fy"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码3</td>
						<td>名称3</td>
						<td><input type="text" id="fy"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码4</td>
						<td>名称4</td>
						<td><input type="text" id="fy"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar" id="opBtnPro" style="display:none">
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
	var $f = $("#fm-fwzxjpdxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("fwhdxm");
$(function()
{
	$("#di_searchModel").click(function(){
		if($("#di_fwhdxmlb").is(":hidden")){
			$("#di_fwhdxmlb").show();
			$("#di_fwhdxmlb").jTable();
		}
		$("#opBtnPro").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>