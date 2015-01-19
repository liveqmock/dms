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
<title>库存盘点</title>
<script type="text/javascript">
var action = "<%=action%>";
function doInit(){
		$("#dia-pdbh").html("PDBH2014050901");
		$("#dia-pdck").attr("code","1");
		$("#dia-pdck").attr("readonly","readonly");
		$("#dia-pdck").val("本部库");
		$("#dia-pdlx").attr("code","1");
		$("#dia-pdlx").val("全部");
		$("#dia-pdlx").attr("readonly","readonly");
		$("#dia-pdsj").val("2014-05-09");
		$("#dia-pdsj").attr("readonly","readonly");
		$("#dia-bz").val("测试");
		$("#dia-bz").attr("readonly","readonly");
		$("#dia-tab-pjlb").show();
		$("#dia-tab-pjlb").jTable();
}
$(function(){
	$("#dia-close").bind("click",function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
function addPjmx()
{
	alertMsg.info("可按照配件、库区等进行添加配件盘点明细");
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" >
		<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
		<div align="left">
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;盘点设置信息&gt;&gt;</a></legend>
			<table class="editTable" id="dia-tab-pjxx">
				<tr>
				    <td><label>盘点编号：</label></td>
				    <td><div id="dia-pdbh">系统自动生成</div></td>
				    <td><label>盘点仓库：</label></td>
				    <td>
				    	<input type="text" id="dia-pdck" name="dia-pdck" kind="dic" src="E#1=本部库:2=待检库:3=报废库:4=军品库" datasource="DDLX" datatype="0,is_null,30" operation="="/>
				    </td>
				    <td><label>盘点人：</label></td>
				    <td><div id="dia-pdr">张三</div></td>
				</tr>
				<tr>
					<td><label>盘点类型：</label></td>
				    <td>
				    	<input type="text" id="dia-pdlx" name="dia-pdlx" kind="dic" src="E#1=全部:2=部分" datasource="DDLX" datatype="0,is_null,30"/>
				    </td>
				    <td><label>盘点时间：</label></td>
				    <td><input type="text"  id="dia-pdsj"  name=""dia-pdsj"" style="width:75px;" datasource="TBRQ" datatype="0,is_null,30" onclick="WdatePicker()" /></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td colspan="5">
						<textarea id="dia-bz" name="dia-bz" style="width:460px;" datasource="DDBH" datatype="1,is_null,30"></textarea>
					</td>
				</tr>
			</table>
			</fieldset>	
		</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		<div id="dia-pjmx" style="height:290px;overflow:hidden;">
			<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" name="XH" append="plus|addPjmx" ></th>
							<th fieldname="PJDM">配件图号</th>
							<th fieldname="JLDWMC">配件名称</th>
							<th fieldname="JLDWMC" colwidth="80">供应商代码</th>
							<th fieldname="JLDWMC" colwidth="80">供应商名称</th>
							<th fieldname="JLDWMC">单位</th>
							<th fieldname="ZXBZSL">最小包装</th>
							<th fieldname="ZXBZSL">库区</th>
							<th fieldname="ZXBZSL">库位</th>
							<th fieldname="ZXBZSL">库管员</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div>1</div></td>
							<td><div>PJDM001</div></td>
							<td><div>配件名称一</div></td>
							<td><div>GYS1</div></td>
							<td><div>供应商一</div></td>
							<td><div>件</div></td>
							<td><div>10件/包</div></td>
							<td><div>A01</div></td>
							<td><div>A01-01</div></td>
							<td><div>李四</div></td>
						</tr>
						<tr>
							<td><div>2</div></td>
							<td><div>PJDM002</div></td>
							<td><div>配件名称二</div></td>
							<td><div>GYS2</div></td>
							<td><div>供应商二</div></td>
							<td><div>件</div></td>
							<td><div>10件/包</div></td>
							<td><div>A01</div></td>
							<td><div>A01-02</div></td>
							<td><div>李四</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
