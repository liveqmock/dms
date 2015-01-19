<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预测上报新增</title>
<script type="text/javascript">
var action=<%=action%>;
//查询按钮响应方法 
$(function(){
	if(action != "1"){
		$("#FM_YCYF").val("2014-06");
		$("#FM_YCYF").attr("readonly",true);
	}
	$("#ycpjlb").show();
	$("#ycpjlb").jTable();
	$("#opBtn").show();
	
	var dialog = parent.$("body").data("ycsbxx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
function addPart(){
	var options = {max:false,width:700,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/jhgl/ycgl/ycpjxz.jsp", "ycpjxx", "预测配件信息", options);
}
function doDelete(){
	alertMsg.info("删除成功！");
}
function doSave(){
	alertMsg.info("保存成功！");
}
function doApply(){
	alertMsg.info("提报成功！");
}
function doExp(){
	alertMsg.info("导出预测配件模版！");
}
function doImp(){
	alertMsg.info("导入预测模版！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<div class="page">
		<div class="pageContent" style="" >
			<form method="post" id="fm-ycsbxz" class="editForm" >
				<div align="left">
				<fieldset>
				<table class="editTable" id="ycsbxzTable">
					<tr>	
						<td><label>预测月份：</label></td>
						<td><input type="text" id="FM_YCYF" name="FM_YCYF" datatype="0,is_date,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" /></td>
				 	</tr>
				</table>
				</fieldset>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doExp()" id="exp">导出模版</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doImp()" id="imp">批量导入</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doApply()" id="apply">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						
					</ul>
				</div>
			</form>
		</div>
		<form  method="post" id="pjform">
			<div id="ycpj">
			<table style="display:none" width="100%"  id="ycpjlb" name="ycpjlb" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:" append="plus|addPart"></th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th>数量</th>
							<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="radio" name="ra" /></td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td>60</td>
							<td><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td><input  type="radio" name="ra"/></td>
							<td>配件代码2</td>
							<td>配件名称2</td>
							<td>60</td>
							<td><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td><input  type="radio" name="ra" /></td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td>60</td>
							<td><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
			</table>
			</div>
		</form>
	</div>
</div>
</body>
</html>