<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdfws" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fwhdfwsxz" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di-fwhdfwsTable">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="DI_WFSDM" name="DI_WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openDealer();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="DI_WFSMC" name="DI_WFSMC" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchDealer">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="di_fwhdfwsH">
			<table width="100%" id="di_fwhdfwslb" name="di_fwhdfwslb" style="display: none" >
				<thead>
					<tr>
						<th  type="multi" name="XH" style="display:"></th>
						<th>服务商代码</th>
						<th>服务商名称</th>
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
		<div class="formBar" id="opBtnDealer" style="display:none">
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
var dialog = $("body").data("fwhdfws");
$(function()
{
	$("#di_searchDealer").click(function(){
		if($("#di_fwhdfwslb").is(":hidden")){
			$("#di_fwhdfwslb").show();
			$("#di_fwhdfwslb").jTable();
		}
		$("#opBtnDealer").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>