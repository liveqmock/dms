<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_sbjjpj" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-sbjjpjform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="sbjjpjTable">
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
		<div id="di_sbjjpj">
			<table width="100%" id="di_sbjjpjlb" name="di_sbjjpjlb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:" ></th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>索赔单价</th>
						<th>数量</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码1</td>
						<td>名称1</td>
						<td>50.00</td>
						<td><input type="text" id="sl"/></td>	
						<td><input type="text" id="bz"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码2</td>
						<td>名称2</td>
						<td>60.00</td>
  						<td><input type="text" id="sl"/></td>
						<td><input type="text" id="bz"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码3</td>
						<td>名称3</td>
						<td>50.00</td>
						<td><input type="text" id="sl"/></td>
						<td><input type="text" id="bz"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>代码4</td>
						<td>名称4</td>
						<td>50.00</td>
						<td><input type="text" id="sl"/></td>
						<td><input type="text" id="bz"/></td>
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
	var $f = $("#di-sbjjpjform");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("sbjjpj");
$(function()
{
	$("#di_searchPart").click(function(){
		$("#di_sbjjpjlb").show();
		$("#di_sbjjpjlb").jTable();
		$("#opBtnPart").show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>