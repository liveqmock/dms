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
		$("#dia-pdck").val("本部库");
		$("#dia-pdck").attr("readonly","readonly");
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
	$("#dia-save").bind("click",function(){
		alertMsg.info("保存成功.");
		return false;
	});
	$("#dia-downLoad").bind("click",function(){
		alertMsg.info("下载盘点结果execl.");
		return false;
	});
	$("#dia-submit").bind("click",function(){
		alertMsg.info("操作成功.");
		return false;
	});
});
function addPjmx(){
	alertMsg.info("可按照配件、库区等进行添加配件盘点明细.");
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="page">
	<div class="pageHeader" >
		<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-ckd">
				<!-- 定义查询条件 -->
				<tr>
					<td><label>配件代码：</label></td>
				    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
				    <td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="in-ddlx" name="in-ddlx" datasource="DDLX" datatype="1,is_null,30" operation="like"/>
				    </td>
				    <td><label>盘点结果：</label></td>
				    <td>
				    	<input type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#1=盘盈:2=盘亏" datasource="DDLX" datatype="1,is_null,30" operation="="/>
				    </td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent" >
		<div id="dia-pjmx" style="height:360px;overflow:hidden;">
			<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" name="XH" style="display:" ></th>
							<th type="multi" name="CX-XH"></th>
							<th fieldname="PJDM">配件图号</th>
							<th fieldname="JLDWMC">配件名称</th>
							<th fieldname="JLDWMC" colwidth="70">供应商代码</th>
							<th fieldname="JLDWMC" colwidth="70">供应商名称</th>
							<th fieldname="JLDWMC">单位</th>
							<th fieldname="ZXBZSL" colwidth="70">最小包装</th>
							<th fieldname="ZXBZSL">库区</th>
							<th fieldname="ZXBZSL">库位</th>
							<th fieldname="ZXBZSL">库管员</th>
							<th fieldname="ZXBZSL" colwidth="50">账面数量</th>
							<th fieldname="ZXBZSL" colwidth="50">实物数量</th>
							<th fieldname="ZXBZSL" colwidth="50">差异值</th>
							<th fieldname="ZXBZSL">计划价</th>
							<th fieldname="ZXBZSL">金额</th>
							<th fieldname="ZXBZSL">盘点结果</th>
							<th fieldname="ZXBZSL">备注</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div>1</div></td>
							<td><div><input type="checkbox"/></div></td>
							<td><div>PJDM001</div></td>
							<td><div>配件名称一</div></td>
							<td><div>GYS1</div></td>
							<td><div>供应商一</div></td>
							<td><div>件</div></td>
							<td><div>10件/包</div></td>
							<td><div>A01</div></td>
							<td><div>A01-01</div></td>
							<td><div>李四</div></td>
							<td><div>50</div></td>
							<td><div><input type="text" value="45"/></div></td>
							<td><div>5</div></td>
							<td><div>40.00</div></td>
							<td><div align="right">200.00</div></td>
							<td><div>盘亏</div></td>
							<td><div><input type="text" value="损坏"/></div></td>
						</tr>
						<tr>
							<td><div>2</div></td>
							<td><div><input type="checkbox"/></div></td>
							<td><div>PJDM002</div></td>
							<td><div>配件名称二</div></td>
							<td><div>GYS2</div></td>
							<td><div>供应商二</div></td>
							<td><div>件</div></td>
							<td><div>10件/包</div></td>
							<td><div>A01</div></td>
							<td><div>A01-02</div></td>
							<td><div>李四</div></td>
							<td><div>50</div></td>
							<td><div><input type="text" value="55"/></div></td>
							<td><div>5</div></td>
							<td><div>80.00</div></td>
							<td><div align="right">400.00</div></td>
							<td><div>盘盈</div></td>
							<td><div><input type="text" value="厂家多送"/></div></td>
						</tr>
					</tbody>
			</table>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-downLoad" type="button">盘点结果导出</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-submit" type="button">调整完成</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
	</div>
</div>
</div>
</body>
</html>
