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
<title>配件三包申请新增</title>
<script type="text/javascript">
var action=<%=action%>;
$(function(){
	if(action != "1"){
		$("#FM_SBSQDH").val("三包申请单号1");
	}
	var dialog = parent.$("body").data("pjsbsqxx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});

});
function openRkd(){
	alertMsg.info("弹出入库单");
}
function openCkd(){
	alertMsg.info("弹出出库单");
}
function doSave(){
	alertMsg.info("保存成功！");
}
function doApply(){
	alertMsg.info("提报成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageContent">
		<form method="post" id="fm-pjsbsqxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="pjsbsqxz">
				<tr>
					<td><label>三包申请单号：</label></td>
					<td><input type="text" id="FM_SBSQDH" name="FM_SBSQDH" datatype="1,is_null,100" value="系统自动生成" readonly="readonly"/></td>
					<td><label>系统入库单：</label></td>
					<td><input type="text" id="FM_XDRKD" name="FM_XDRKD" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openRkd();"/></td>
					<td><label>系统出库单：</label></td>
					<td><input type="text" id="FM_XDCKD" name="FM_XDCKD" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openCkd();"/></td>
			    </tr>
			    <tr>
			    	<td><label>出库日期：</label></td>
					<td><input type="text" id="FM_CKRQ" name="FM_CKRQ" datatype="1,is_null,100" value="选择出库单信息自动带出" readonly="readonly"/></td>
			    	<td><label>出库数量：</label></td>
					<td><input type="text" id="FM_CKSL" name="FM_CKSL" datatype="1,is_null,100" value="选择出库单信息自动带出" readonly="readonly"/></td>
					<td><label>购货单位：</label></td>
					<td><input type="text" id="FM_GHDW" name="FM_GHDW" datatype="1,is_double,100" value="选择出库单信息自动带出" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>联系人：</label></td>
					<td><input type="text" id="FM_LXR" name="FM_LXR" datatype="1,is_double,100" value="选择出库单信息自动带出" readonly="readonly" /></td>
					<td><label>联系电话 ：</label></td>
					<td><input type="text" id="FM_LXDH" name="FM_LXDH" datatype="1,is_double,100" value="选择出库单信息自动带出" readonly="readonly" /></td>
				</tr>
				<tr>
					<td><label>配件代码：</label></td>
					<td><input type="text" id="FM_PJDM" name="FM_PJDM" class="combox" kind="dic" src="E#配件代码1=配件代码1:配件代码2=配件代码2" datatype="0,is_null,100" /></td>
					<td><label>配件名称：</label></td>
					<td><input type="text" id="FM_PJMC" name="FM_PJMC" datatype="1,is_double,100" value="选择备件代码表选信息自动带出" readonly="readonly" /></td>
					<td><label>单位 ：</label></td>
					<td><input type="text" id="FM_PJDW" name="FM_PJDW" datatype="1,is_double,100" value="选择备件代码表选信息自动带出" readonly="readonly" /></td>
				</tr>
				 <tr>
					<td><label>数量：</label></td>
					<td><input type="text" id="FM_PJSL" name="FM_PJSL" datatype="0,is_double,100" value="选择备件代码表选信息自动带出" /></td>
					<td><label>经销价：</label></td>
					<td><input type="text" id="FM_JXJ" name="FM_JXJ" datatype="1,is_double,100" value="选择备件代码表选信息自动带出" readonly="readonly"/></td>
					<td><label>金额 ：</label></td>
					<td><input type="text" id="FM_JE" name="FM_JE" datatype="1,is_double,100" value="选择备件代码表选信息自动带出" readonly="readonly" /></td>
				</tr>
				 <tr>
					<td><label>生产厂家：</label></td>
					<td colspan="5"><input type="text" id="FM_SCCJ" name="FM_SCCJ" value="选择备件代码表选信息自动带出" datatype="0,is_double,100" /></td>
				</tr>
				<tr>
					<td><label>故障情况：</label></td>
					<td colspan="5"><textarea id="FM_GZQK" name="FM_GZQK"  style="width:450px;height:40px;" datatype="1,is_null,500" ></textarea></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td colspan="5"><textarea id="FM_BZ" name="FM_BZ" style="width:450px;height:40px;" datatype="1,is_null,500" ></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doApply()" id="apply">提&nbsp;&nbsp;报</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
</body>
</html>