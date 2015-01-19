<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp"/>
<title>发料单生成</title>
<script type="text/javascript">
//弹出窗体
$(function()
{
	$("#tab-pjlb").show();
	$("#tab-pjlb").jTable();
	$("#dia-fldlb").show();
	$("#tab-fldlb").jTable();
	$("#dia-close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-approve1").bind("click",function(){
    	alertMsg.info("按库管员生成发料单.");
    	$("#dia-approve1").hide();
    	return false;
	});
	
	//设置高度
	$("#ddjbxx").height(document.documentElement.clientHeight-100);
});

function doViewFldDetail(id){
	var options = {max:false,width:800,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/flgl/fldmxcx.jsp", "fldmx", "发料单明细", options);
}
</script>
</head>
<body>
<div id="dia-layout" style="width:100%;">
<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="searchBar" align="left" >
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('tab-fydwh')">&nbsp;订单基本信息&gt;&gt;</a></legend>
			<table class="editTable" id="tab-fydwh">
				<tr>
					<td><label>订单编号：</label></td>
				    <td><input type="text" id="mx-ddbh" name="mx-ddbh" readonly datasource="DDBH" datatype="1,is_null,30" value="YD20140519001"/></td>
				    <td><label>订单类型：</label></td>
				    <td><input type="text" id="mx_ddlx" name="mx_ddlx" readonly datasource="DDLX" datatype="1,is_null,30"  value="月度订单"/></td>
				    <td><label>提报时间：</label></td>
				    <td><input type="text" id="mx_shy" name="mx_shy" readonly datasource="DDBH" datatype="1,is_null,30"  value="2014-05-19 08:30:00"/></td>
				</tr>
				<tr>
				    <td><label>提报单位：</label></td>
				    <td><input type="text" id="mx_shy" name="mx_shy" readonly datasource="DDBH" datatype="1,is_null,30"  value="河南配送中心"/></td>
				    <td><label>审核员：</label></td>
				    <td><input type="text" id="mx_tbdw" name="mx_tbdw" readonly datasource="DDBH" datatype="1,is_null,30"  value="张三"/></td>
				    <td><label>审核时间：</label></td>
				    <td><input type="text" id="mx_shy" name="mx_shy" readonly datasource="DDBH" datatype="1,is_null,30"  value="2014-05-20 08:30:00"/></td>
				</tr>
			</table>
			</fieldset>
		</div>
		<div class="tabs" eventType="click" id="dia-tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>发料信息</span></a></li>
					<li><a href="javascript:void(0)"><span>发料单打印</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page">
				<div class="pageContent" >
					<div id="dia-pjmx" style="height:280px;overflow:auto;">
						<table style="width:100%;" id="tab-pjlb" name="tablist"  ref="dia-pjmx" >
						<thead>
							<tr>
								<th fieldname="ROWNUMS" style="display:"></th>
								<th type="multi" name="CX-XH" style="display:none;" ></th>
								<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" freadonly="true">配件图号</th>
								<th fieldname="PJMC" colwidth="150" freadonly="true">配件名称</th>
								<th fieldname="PJID" style="display:none" freadonly="true">配件ID</th>
								<th fieldname="JLDWMC" colwidth="50" freadonly="true" >计量单位</th>
								<th fieldname="KW" freadonly="true" >库位</th>
								<th fieldname="ZXBZ" colwidth="90" freadonly="true" >最小包装</th>
								<th fieldname="SFZDGYS" freadonly="true" colwidth="100" >是否指定供应商</th>
								<th fieldname="GYSMC">供应商</th>
								<th fieldname="KC" freadonly="true" >可用库存</th>
								<th fieldname="YF">应发数量</th>
								<th fieldname="KG">库管员</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="rownums"><div>1</div></td>
								<td><div>PJ001</div></td>
								<td><div>配件名称1</div></td>
								<td><div>件</div></td>
								<td><div>A01-01</div></td>
								<td><div>10/包</div></td>
								<td><div>否</div></td>
								<td><div></div></td>
								<td><div>300</div></td>
								<td><div>20</div></td>
								<td><div>王五</div></td>
							</tr>
							<tr >
								<td class="rownums"><div>2</div></td>
								<td><div>PJ002</div></td>
								<td><div>配件名称2</div></td>
								<td><div>件</div></td>
								<td><div>A01-02</div></td>
								<td><div>10/包</div></td>
								<td><div>是</div></td>
								<td><div>供应商一</div></td>
								<td><div>50</div></td>
								<td><div>20</div></td>
								<td><div>王五</div></td>
							</tr>
							<tr >
								<td class="rownums"><div>3</div></td>
								<td><div>PJ003</div></td>
								<td><div>配件名称3</div></td>
								<td><div>件</div></td>
								<td><div>A01-03</div></td>
								<td><div>10/包</div></td>
								<td><div>否</div></td>
								<td><div></div></td>
								<td><div>70</div></td>
								<td><div>20</div></td>
								<td><div>王五</div></td>
							</tr>
							<tr>
								<td class="rownums"><div>4</div></td>
								<td><div>PJ004</div></td>
								<td><div>配件名称4</div></td>
								<td><div>件</div></td>
								<td><div>A02-01</div></td>
								<td><div>10/包</div></td>
								<td><div>否</div></td>
								<td><div></div></td>
								<td><div>300</div></td>
								<td><div>20</div></td>
								<td><div>李四</div></td>
							</tr>
							<tr >
								<td class="rownums"><div>5</div></td>
								<td><div>PJ005</div></td>
								<td><div>配件名称5</div></td>
								<td><div>件</div></td>
								<td><div>A02-02</div></td>
								<td><div>10/包</div></td>
								<td><div>是</div></td>
								<td><div>供应商一</div></td>
								<td><div>50</div></td>
								<td><div>20</div></td>
								<td><div>李四</div></td>
							</tr>
							<tr >
								<td class="rownums"><div>6</div></td>
								<td><div>PJ006</div></td>
								<td><div>配件名称6</div></td>
								<td><div>件</div></td>
								<td><div>A02-03</div></td>
								<td><div>10/包</div></td>
								<td><div>否</div></td>
								<td><div></div></td>
								<td><div>70</div></td>
								<td><div>20</div></td>
								<td><div>李四</div></td>
							</tr>
						</tbody>
				</table>
				</div>
				</div>
			</div>
			<div class="page">
				<div class="pageContent" >
					<div id="dia-fldxx" style="height:280px;overflow:auto;">
						<table style="width:100%;" id="tab-fldlb" name="tablist"  ref="dia-fldxx" >
						<thead>
							<tr>
								<th fieldname="ROWNUMS" style="display:"></th>
								<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" freadonly="true">发料单号</th>
								<th fieldname="PJMC">订单号码</th>
								<th fieldname="PJMC">订单类型</th>
								<th fieldname="PJID">提报单位</th>
								<th fieldname="KG">审核员</th>
								<th fieldname="KG">库管员</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="rownums"><div>1</div></td>
								<td><div><a style="color:red;" onclick="doViewFldDetail('YD20140519001');">YD20140519001001</a></div></td>
								<td><div>YD20140519001</div></td>
								<td><div>月度订单</div></td>
								<td><div>河南配送中心</div></td>
								<td><div>张三</div></td>
								<td><div>王五</div></td>
							</tr>
							<tr >
								<td class="rownums"><div>2</div></td>
								<td><div><a style="color:red;" onclick="doViewFldDetail('YD20140519001');">YD20140519001002</a></div></td>
								<td><div>YD20140519001</div></td>
								<td><div>月度订单</div></td>
								<td><div>河南配送中心</div></td>
								<td><div>张三</div></td>
								<td><div>李四</div></td>
							</tr>
						</tbody>
				</table>
				</div>
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-approve1">生成发料单</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>
