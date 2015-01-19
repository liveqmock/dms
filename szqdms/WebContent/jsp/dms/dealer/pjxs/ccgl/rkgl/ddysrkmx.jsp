<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单验收入库</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	$("#dia-mxlb").show();
	$("#dia-mxlb").jTable();
	$("#dia-close").bind("click",function(){
		parent.$.pdialog.closeCurrent();
	});
	$("#dia-save").bind("click",function(){
		alertMsg.info("验收入库完成.");
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
			<form method="post" id="">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('ddxx')">&nbsp;订单发运信息&gt;&gt;</a></legend>
				<table class="editTable" id="ddxx">
					<tr>
						<td><label>订单编号：</label></td>
					    <td><input type="text" id="in-ddbh" readonly name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" value="YD20140519001"/></td>
						<td><label>订单类型：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" readonly  datasource="DDLX" datatype="1,is_null,30" value="月度订单"/>
					    </td>
					    <td><label>提报日期：</label></td>
					   <td>
				    		<input type="text" id="in-kstbrq"  name="in-kstbrq" readonly style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" value="2014-05-20" />
				   	   </td>
					</tr>
					<tr>
					   <td><label>审核员：</label></td>
					   <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_null,30" value="张三"/></td>
				   	   <td><label>发运单号：</label></td>
					   <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_null,30" value="FYD20140522001"/></td>
				   	   <td><label>发运日期：</label></td>
					    <td>
				    		<input type="text" id="in-kstbrq"  name="in-kstbrq" readonly style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" value="2014-05-22" />
				   	   </td>
					</tr>
					<tr>
					   <td><label>承运商：</label></td>
					   <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_null,30" value="物流公司一"/></td>
				   	   <td><label>司机：</label></td>
					   <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_null,30" value="李四"/></td>
				   	   <td><label>联系电话：</label></td>
					    <td>
				    		<input type="text" id="in-kstbrq"  name="in-kstbrq" readonly  datasource="TBRQ" datatype="1,is_null,30" value="13290881088" />
				   	   </td>
					</tr>
				</table>
				</fieldset>
			</form>
		</div>
		<div class="pageContent" >
			<div id="dia-mxxx" style="height:290px;overflow:auto;">
				<table style="width:100%;" id="dia-mxlb" name="tablist"  ref="dia-mxxx" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" style="display:"></th>
						<th fieldname="PJDM">配件图号</th>
						<th fieldname="PJMC">配件名称</th>
						<th fieldname="JLDWMC">单位</th>
						<th fieldname="JLDWMC">最小包装</th>
						<th fieldname="JLDWMC" colwidth="80">是否指定供应商</th>
						<th fieldname="JLDWMC">供应商</th>
						<th fieldname="JLDWMC">应发数量</th>
						<th fieldname="JLDWMC">实发数量</th>
						<th fieldname="JLDWMC" colwidth="70">验收入库数量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="rownums"><div>1</div></td>
						<td><div>PJ001</div></td>
						<td><div>配件名称1</div></td>
						<td><div>件</div></td>
						<td><div>10件/包</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div>20</div></td>
						<td><div><input type="text" value="20"/></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>2</div></td>
						<td><div>PJ002</div></td>
						<td><div>配件名称2</div></td>
						<td><div>件</div></td>
						<td><div>10件/包</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>10</div></td>
						<td><div>10</div></td>
						<td><div><input type="text" value="10"/></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>3</div></td>
						<td><div>PJ003</div></td>
						<td><div>配件名称3</div></td>
						<td><div>件</div></td>
						<td><div>10件/包</div></td>
						<td><div>是</div></td>
						<td><div>供应商一</div></td>
						<td><div>25</div></td>
						<td><div>25</div></td>
						<td><div><input type="text" value="25"/></div></td>
					</tr>
				</tbody>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonContent">验收回执：<input type="text" style="width:400px;"/></div></li>
				<li><div class="button"><div class="buttonContent"><button id="dia-save" type="button">验收入库</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</div>
</body>
</html>