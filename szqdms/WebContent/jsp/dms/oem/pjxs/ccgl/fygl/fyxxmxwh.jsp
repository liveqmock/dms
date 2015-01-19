<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发运单承运商指定</title>
<script type="text/javascript">
//弹出窗体
$(function()
{
	$("#tab-fydmxlb").show();
	$("#tab-fydmxlb").jTable();
	$("#dia-close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-save").bind("click",function(){
		alertMsg.confirm("确认保存?",{okCall:doSave,cancelCall:doSaveCell});
	});
	$("#dia-print").bind("click",function(){
		alertMsg.info("打印发运通知单.");
		return false;
	});
	$("#dia-printHzd").bind("click",function(){
		alertMsg.info("打印运输回执单.");
		return false;
	});
	$("#dia-download").bind("click",function(){
		alertMsg.info("下载运输清单excel.");
		return false;
	});
	$("#dia-submit").bind("click",function(){
		alertMsg.confirm("确认提交?",{okCall:doSave,cancelCall:doSaveCell});
		return false;
	});
	//设置高度
	$("#ddjbxx").height(document.documentElement.clientHeight-100);
});
function doViewZxDetail(orderNo)
{
	var w = document.documentElement.clientWidth-100;
	var options = {max:false,width:w,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/zxgl/zxqdmx.jsp", "zxmx", "装箱明细", options);
}
function addFydmx(){
	var w = document.documentElement.clientWidth-100;
	var options = {max:false,width:w,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/fygl/fydmxxz.jsp", "zxxz", "装箱信息选择", options);
}
function doDelete(){
	alertMsg.confirm("确认删除?",{okCall:doSave,cancelCall:doSaveCell});
}
function doSave(){
	alertMsg.info("操作成功.");
}
function doSaveCell(){
	return false;
}
</script>
</head>
<body>
<div id="dia-layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<div class="searchBar" align="left" >
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('tab-fydwh')">&nbsp;发运单信息&gt;&gt;</a></legend>
				<table class="editTable" id="tab-fydwh">
					<tr>
						<td><label>发运单号：</label></td>
					    <td><input type="text" id="mx-ddbh" name="mx-ddbh" readonly datasource="DDBH" datatype="1,is_null,30" value="FYD20140519001"/></td>
					    <td><label>承运单位：</label></td>
					    <td>
					    	<input type="text" id="mx_ddlx"  name="mx_ddlx" readonly datasource="DDLX" datatype="1,is_null,30"  value="物流公司一"/>
					    </td>
					</tr>
					<tr>
					    <td><label>联系人：</label></td>
					    <td><input type="text" id="mx_shy" name="mx_shy" readonly datasource="DDBH" datatype="1,is_null,30"  value="李四"/></td>
					    <td><label>联系电话：</label></td>
					    <td><input type="text" id="mx_tbdw" name="mx_tbdw" readonly datasource="DDBH" datatype="1,is_null,30"  value="13344335566"/></td>
					</tr>
					<tr>
					    <td><label>备注：</label></td>
					    <td colspan="3"><textarea id="mx_shrq" name="mx_shrq" readonly style="width:460px;" datasource="DDBH" datatype="1,is_null,30">请发运</textarea></td>
					</tr>
				</table>
				</fieldset>
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('tab-fydwh')">&nbsp;承运信息维护&gt;&gt;</a></legend>
				<table class="editTable" id="tab-fydwh">
					<tr>
						<td><label>承运车辆：</label></td>
					    <td><input type="text" id="mx-ddbh" name="mx-ddbh" kind="dic" src="E#1=陕A-B1234:2=陕A-B1235:3=陕A-B1236" datasource="DDBH" datatype="1,is_null,30" value="陕A-B1234"/></td>
					    <td><label>承运司机：</label></td>
					    <td><input type="text" id="mx_ddlx"  name="mx_ddlx" kind="dic" src="E#1=张三:2=李四:3=王五" datasource="DDLX" datatype="1,is_null,30"  value="李四"/></td>
					    <td><label>联系电话：</label></td>
					    <td><input type="text" id="mx_ddlx"  name="mx_ddlx"  datasource="DDLX" datatype="1,is_null,30"  value="13636739298"/></td>
					</tr>
					<tr>
					    <td><label>承运备注：</label></td>
					    <td colspan="3"><textarea id="mx_shrq" name="mx_shrq"  style="width:460px;" datasource="DDBH" datatype="1,is_null,30">收到</textarea></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-download">运输清单下载</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-submit">提&nbsp;&nbsp;交</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</form>
	</div>
		<div class="pageContent" >
			<div id="dia-fldmx" style="height:210px;overflow:auto;">
				<table style="width:100%;" id="tab-fydmxlb" name="tablist"  ref="dia-fldmx" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" name="XH" ></th>
						<th fieldname="PJMC" colwidth="150" freadonly="true">订单编号</th>
						<th fieldname="PJMC" freadonly="true">订单类型</th>
						<th fieldname="JLDWMC" freadonly="true">提报单位</th>
						<th fieldname="SFZDGYS" freadonly="true">配件数量</th>
						<th fieldname="SFZDGYS" freadonly="true">配件金额</th>
						<th fieldname="KW" freadonly="true">收货地址</th>
						<th fieldname="ZXBZ" freadonly="true">联系人</th>
						<th fieldname="SFZDGYS" freadonly="true">联系电话</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="rownums"><div>1</div></td>
						<td><div>YD20140519001</div></td>
						<td><div>月度订单</div></td>
						<td><div>河南配送中心</div></td>
						<td><div>50</div></td>
						<td><div align="right">2,000.00</div></td>
						<td><div>河南省洛阳市</div></td>
						<td><div>王五</div></td>
						<td><div>13322553366</div></td>
					</tr>
					<tr >
						<td class="rownums"><div>2</div></td>
						<td><div>ZD20140519001</div></td>
						<td><div>周度订单</div></td>
						<td><div>河南配送中心</div></td>
						<td><div>10</div></td>
						<td><div align="right">785.00</div></td>
						<td><div>河南省洛阳市</div></td>
						<td><div>王五</div></td>
						<td><div>13322553366</div></td>
					</tr>
					<tr >
						<td class="rownums"><div>3</div></td>
						<td><div>ZD20140519002</div></td>
						<td><div>周度订单</div></td>
						<td><div>河南配送中心</div></td>
						<td><div>5</div></td>
						<td><div align="right">325.00</div></td>
						<td><div>河南省洛阳市</div></td>
						<td><div>王五</div></td>
						<td><div>13322553366</div></td>
					</tr>
				</tbody>
		</table>
	</div>
</div>
</div>
</div>
</body>
</html>
