<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包急件新增</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){ 
	if(action != "1"){
		$("#JJBH").val("急件编号1");
		
	}else{
		$("#JJBH").val("系统自动生成");
		$("#applyBtn").hide();
	}
	$("#sbjjpjlb").show();
	$("#sbjjpjlb").jTable();
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("sbjjxx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
//新增配件
function doAddPart(){
	var options = {max:false,width:870,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/sbjjgl/sbjjgl/sbjjpjxz.jsp", "sbjjpj", "三包急件配件新增", options);
}
function doSave(){
	alertMsg.info("保存成功");
	$("#applyBtn").show();
}
function doApply(){
	alertMsg.info("提报成功");
}
function doDelete(){
	alertMsg.info("删除成功");
}
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
							<td><input type="text" id="VIN" name="VIN" datatype="0,is_digit_letter,17" operation="like" />(请输入后8位或者17位)</td>
							<td><label>发动机号：</label></td>
							<td><input type="text" id="FDJH" name="FDJH" " datatype="0,is_digit_letter,30" operation="like" /></td>
							<td colspan="2"><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin"onclick="checkVin()">验&nbsp;&nbsp;证</button></div></div></td>
						</tr>
						<tr>
							<td><label>车辆型号：</label></td>
							<td><input type="text" id="CLXH" name="CLXH" value="校验车辆信息后自动带出" readonly="readonly" /></td>
							<td><label>购车日期：</label></td>
							<td><input type="text" id="GCRQ" name="GCRQ" value="2013-01-01" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
							<td><label>行驶里程(公里)：</label></td>
							<td><input type="text" id="XSLC" name="XSLC" value="3100" datatype="0,is_number,30" /></td>
						</tr>
						<tr >
							<td><label>客户名称：</label></td>
							<td><input type="text" id="KHMC" name="KHMC" value="张三" datatype="0,is_null,30" /></td>
							<td><label>联系人：</label></td>
							<td><input type="text" id="LXR" name="LXR" value="李四" datatype="0,is_number,30" /></td>
							<td><label>联系电话：</label></td>
							<td><input type="text" id="LXRDH" name="LXRDH" value="" datatype="0,is_number,30" /></td>
						</tr>
						<tr>
							<td><label>故障分析及处理：</label></td>
							<td ><input id="GZFXBZ"  name="GZFXBZ" datatype="1,is_null,100" multi="true"  class="combox" kind="dic" src="E#故障分析代码1=故障分析名称1:故障分析代码2=故障分析名称2"/></td>
							<td><label>客户地址：</label></td>
							<td><textarea id="KHDZ" style="width: 150px; height: 30px;" name="KHDZ" datatype="1,is_null,100">地址</textarea></td>
							<td><label>本次故障日期：</label></td>
							<td><input type="text" id="BCGZRQ" name="BCGZRQ" value="2014-5-25"  datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})"/></td>
						</tr>
						<tr >
							<td><label>收货人：</label></td>
							<td><input type="text" id="SHR" name="SHR" value="" datatype="0,is_null,30" /></td>
							<td><label>收货人电话：</label></td>
							<td><input type="text" id="SHRDH" name="SHRDH" value="" datatype="0,is_number,30" /></td>
							<td><label>收货地址：</label></td>
							<td><textarea id="SHDZ" style="width: 150px; height: 30px;" name="SHDZ" datatype="1,is_null,100">地址</textarea></td>
						</tr>
						<tr>
							<td><label>申请人备注：</label></td>
							<td colspan="5"><textarea id="SQRBZ" style="width: 450px; height: 50px;" name="SQRBZ" datatype="1,is_null,100"></textarea></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
					<li id="applyBtn"><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doApply()" id="apply">提&nbsp;&nbsp;报</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</div>
			<div class="page">
			<div class="pageContent">
				<div id="sbjjpj" style="height:600px;overflow:hidden;">
					<table style="display:none;width:100%;" id="sbjjpjlb" layoutH="350" name="tablist" ref="sbjjpj" pageRows="15" edit="false" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:" append="plus|doAddPart" colwidth="60"></th>
								<th fieldname="PJDM"  colwidth="60">配件代码</th>
								<th fieldname="PJMC"  colwidth="60">配件名称</th>
								<th fieldname="SL"  colwidth="60">数量</th>
								<th fieldname="DJ"  colwidth="60" align="right">索赔单价(元)</th>
								<th fieldname="BZ"  colwidth="60">备注</th>
								<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><div></div></td>
								<td><div>代码1</div></td>
								<td><div>名称1</div></td>
								<td><div>2</div></td>
								<td><div>50</div></td>
								<td><div></div></td>
								<td ><div><a href="#" onclick="doDelete()" class="op">[删除]</a></div></td>
							</tr>
							<tr>
								<td><div></div></td>
								<td><div>代码2</div></td>
								<td><div>名称2</div></td>
								<td><div>1</div></td>
								<td><div>50</div></td>
								<td><div></div></td>
								<td ><div><a href="#" onclick="doDelete()" class="op">[删除]</a></div></td>
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