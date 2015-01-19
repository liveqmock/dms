<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包急件明细</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){ 
	$("#JJBH").val("急件编号1");
	
	$("#sbjjpjlb").show();
	$("#sbjjpjlb").jTable();
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("sbjjmx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>三包急件基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-sbjjxx" class="editForm" >
					<div align="left">
					<fieldset>
					<table class="editTable" id="sbjjxx">
						<tr>
							<td><label>急件编号：</label></td>
							<td><input type="text" id="JJBH" name="JJBH" readonly="readonly" /></td>
						</tr>
					    <tr>
							<td><label>VIN：</label></td>
							<td><input type="text" id="VIN" name="VIN" datatype="1,is_digit_letter,17"  readonly="readonly" value="VIN1"/></td>
							<td><label>发动机号：</label></td>
							<td><input type="text" id="FDJH" name="FDJH" " datatype="1,is_digit_letter,30"  readonly="readonly" value="发动机号1"/></td>
						</tr>
						<tr>
							<td><label>车辆型号：</label></td>
							<td><input type="text" id="CLXH" name="CLXH" value="车辆型号1" readonly="readonly" /></td>
							<td><label>购车日期：</label></td>
							<td><input type="text" id="GCRQ" name="GCRQ" value="2013-01-01" datatype="1,is_null,30" readonly="readonly" /></td>
							<td><label>行驶里程(公里)：</label></td>
							<td><input type="text" id="XSLC" name="XSLC" value="3100" datatype="1,is_number,30" readonly="readonly"/></td>
						</tr>
						<tr >
							<td><label>客户名称：</label></td>
							<td><input type="text" id="KHMC" name="KHMC" value="张三" datatype="1,is_null,30" readonly="readonly"/></td>
							<td><label>联系人：</label></td>
							<td><input type="text" id="LXR" name="LXR" value="李四" datatype="1,is_number,30" readonly="readonly"/></td>
							<td><label>联系电话：</label></td>
							<td><input type="text" id="LXRDH" name="LXRDH" value="" datatype="1,is_number,30" readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label>故障分析及处理：</label></td>
							<td ><input id="GZFXBZ"  name="GZFXBZ" datatype="1,is_null,100" multi="true"  class="combox" kind="dic" src="E#故障分析代码1=故障分析名称1:故障分析代码2=故障分析名称2"/></td>
							<td><label>客户地址：</label></td>
							<td><textarea id="KHDZ" style="width: 150px; height: 30px;" name="KHDZ" datatype="1,is_null,100">地址</textarea></td>
							<td><label>本次故障日期：</label></td>
							<td><input type="text" id="BCGZRQ" name="BCGZRQ" value="2014-5-25"  datatype="1,is_null,30" /></td>
						</tr>
						<tr >
							<td><label>收货人：</label></td>
							<td><input type="text" id="SHR" name="SHR" value="" datatype="1,is_null,30" /></td>
							<td><label>收货人电话：</label></td>
							<td><input type="text" id="SHRDH" name="SHRDH" value="" datatype="1,is_number,30" /></td>
							<td><label>收货地址：</label></td>
							<td><textarea id="SHDZ" style="width: 150px; height: 30px;" name="SHDZ" datatype="1,is_null,100">地址</textarea></td>
						</tr>
						<tr>
							<td><label>申请人备注：</label></td>
							<td colspan="5"></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</div>
			<div class="page">
			<div class="pageContent">
				<div id="sbjjpj">
					<table width="100%" id="sbjjpjlb" name="sbjjpjlb" style="display: none" >
						<thead>
							<tr>
							<th  name="XH" style="display:" ></th>
								<th>配件代码</th>
								<th>配件名称</th>
								<th>数量</th>
								<th align="right">索赔单价(元)</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>代码1</td>
								<td>名称1</td>
								<td>2</td>
								<td>50</td>
								<td></td>
							</tr>
							<tr>
								<td>2</td>
								<td>代码2</td>
								<td>名称2</td>
								<td>1</td>
								<td>50</td>
								<td></td>
							</tr>
							<tr>
								<td>3</td>
								<td>代码3</td>
								<td>名称3</td>
								<td>3</td>
								<td>50</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>