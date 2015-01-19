<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_jhmxxz" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-jhmxxzform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di-jhmxxzTable">
					<tr>
						<td colspan="6"><font color="red">*注:列表中仓库对应的配件不在计划中存在</font></td>
					</tr>
					<tr>
						<td><label>配件仓库：</label></td>
						<td><select type="text" id="FM_PJCK" name="FM_PJCK" class="combox" kind="dic" src="E#1=中心配件仓库" >
								<option value=-1>--</option>
							</select>
						</td>
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
		<div id="di_jhmx">
			<table width="100%" id="di_jhmxlb" name="di_jhmxlb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:" ></th>
						<th>仓库名称</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>库存数量</th>
						<th>计划数量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>仓库名称1</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>30</td>
						<td><input type="text" id="sl"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>仓库名称2</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>30</td>
						<td><input type="text" id="sl"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>仓库名称3</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>30</td>
						<td><input type="text" id="sl"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>仓库名称4</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>30</td>
						<td><input type="text" id="sl"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar" id="opBtnPart" style="display:none">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="di_doSave()" id="save">确&nbsp;&nbsp;定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
function di_doSave(){
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("jhmxxz");
$(function()
{
	$("#di_jhmxlb").show();
	$("#di_jhmxlb").jTable();
	$("#opBtnPart").show();
	$("#di_searchPart").click(function(){
		if($("#di_jhmxlb").is(":hidden")){
			$("#di_jhmxlb").show();
			$("#di_jhmxlb").jTable();
		}
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>