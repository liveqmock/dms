<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdcx" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fwhdgs" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdgsTable">
					<tr>
						<td><label>工时代码：</label></td>
						<td><input type="text" id="DI_GSDM" name="DI_GSDM" datatype="1,is_null,100"  value="" /></td>
						<td><label>工时名称：</label></td>
						<td><input type="text" id="DI_GSMC" name="DI_GSMC" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchWorkH">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_fwhdgs">
			<table width="100%" id="di_fwhdgslb" name="di_fwhdgslb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:" ></th>
						<th>工时代码</th>
						<th>工时名称</th>
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
		<div class="formBar" id="opBtnWorkH" style="display:none">
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
var dialog = $("body").data("fwhdgs");
$(function()
{
	$("#di_searchWorkH").click(function(){
		if($("#di_fwhdgslb").is(":hidden")){
			$("#di_fwhdgslb").show();
			$("#di_fwhdgslb").jTable();
		}
		$("#opBtnWorkH").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>