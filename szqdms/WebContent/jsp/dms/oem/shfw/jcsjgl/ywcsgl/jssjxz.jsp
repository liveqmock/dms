<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="jssjxzxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-jssjxzxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="jssjxzxxTable">
				<tr>	
					<td><label>年：</label></td>
					<td><select  type="text" id="DI_JSN" name="DI_JSN" >
						</select> 
					</td>
			 	</tr>
			</table>
			</fieldset>
			</div>
		</form>
	</div>
	<form  method="post" id="jssjform">
			<div id="jssjxz">
			<table style="display:none" width="100%" layoutH="230"  id="jssjxzlb" name="jssjxzlb" >
					<thead>
						<tr>
							<th>月份</th> 
							<th>结算日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>2</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>3</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>4</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>5</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>6</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>7</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>8</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>9</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>10</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>11</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td>12</td>
							<td><input type="text" ></td>
						</tr>
					</tbody>
			</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	for(var i=2013;i<=2015; i++){
		if(i==2014){
			$("#DI_JSN").append("<option value="+i+" selected>"+i+"</option>");
		}else{
			$("#DI_JSN").append("<option value="+i+">"+i+"</option>");
		}
	}
	$("#jssjxzlb").show();
	$("#jssjxzlb").jTable();
});
function doSave(){
	var $f = $("#di-jssjxzxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("jssjxz");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>