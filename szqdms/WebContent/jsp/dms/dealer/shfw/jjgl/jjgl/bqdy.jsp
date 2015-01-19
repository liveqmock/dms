<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="bqdyxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-bqdyxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="bqdyTable">
			    <tr>
					<td><label>配件代码：</label></td>
					<td>配件代码1</td>
					<td rowspan="6"><img src="code.jpg" style="width:200px;height:200px;" /></td>
				</tr>	
				<tr>
					<td><label>配件名称：</label></td>
					<td>配件名称1</td>
				</tr>
				<tr>
					<td><label>生产供应商：</label></td>
					<td>供应商1</td>
				</tr>	
				<tr>
					<td><label>责任供应商：</label></td>
					<td>责任供应商1</td>
				</tr>
				<tr>
					<td><label>责任供应商代码：</label></td>
					<td>责任供应商代码1</td>
				</tr>
				<tr>
					<td><label>质损原因：</label></td>
					<td>破裂</td>
				</tr>	
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doPrint()" id="print">打&nbsp;&nbsp;印</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<style type="text/css">  
        table {  
            border: 1px solid #B1CDE3; 
            padding:0;   
            margin:0 auto;  
            border-collapse: collapse;  
        }  
          
        td {  
            border: 1px solid #B1CDE3; 
            background: #fff;  
            font-size:12px;  
            padding: 3px 0px 3px 8px;  
            color: #4f6b72;  
            white-space:nowrap;
        }  
</style>
<script type="text/javascript">
function doPrint(){
	alertMsg.info("打印成功！");
}
//弹出窗体
var dialog = $("body").data("bqdy");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>