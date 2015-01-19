<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发料单查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-ddlb").is(":hidden"))
		{
			$("#tab-ddlb").show();
			$("#tab-ddlb").jTable();
		}
	});
});

//查看订单详细信息
function doViewOrderDetail(orderNo)
{
	var w = document.documentElement.clientWidth-100;
	var options = {max:false,width:w,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/ddmx.jsp", "ddmx", "订单明细", options, true);
}
function doDownload(row){
	alertMsg.info("下载销售清单Excel.");
}
function doViewFldDetail(id){
	var options = {max:false,width:800,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/flgl/fldmx.jsp", "fldmx", "发料单明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 发料单管理   &gt; 发料单查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>订单编号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>延期订单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>订单类型：</label></td>
					    <td>
					    	<select type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#YD=月度订单:LS=临时订单:ZD=周度订单:ZC=总成订单:CX=促销订单:BW=保外订单" datasource="DDLX" datatype="1,is_null,30">
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					 	<td><label>提报日期：</label></td>
					    <td>
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		</td>
						<td><label>提报单位代码：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
						<td><label>提报单位名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>审核日期：</label></td>
					    <td>
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		</td>
						<td><label>审核员：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#1=张三:2=李四:3=王五" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>库管员：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#1=张三:2=李四:3=王五" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>发料单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh"  datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
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
	<div class="pageContent">
		<div id="page_ddlb" >
			<table style="display:none;width:100%;" id="tab-ddlb" name="tablist" ref="page_ddlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" colwidth="120" ordertype='local' class="desc">发料号</th>
							<th fieldname="DDBH" colwidth="120" ordertype='local' class="desc">订单编号</th>
							<th fieldname="DDLX" >订单类型</th>
							<th fieldname="GHPJK" >提报单位</th>
							<th fieldname="TBSJ" ordertype='local' class="desc">提报时间</th>
							<th fieldname="SHY" >审核员</th>
							<th fieldname="TBSJ" ordertype='local' class="desc">审核时间</th>
							<th fieldname="SHY" >库管员</th>
							<th fieldname="SHY" >是否已打印</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a style="color:red;" onclick="doViewFldDetail('YD20140519001');">YD20140519001001</a></div></td>
							<td><div><a style="color:red;" onclick="doViewOrderDetail('YD20140519001');">YD20140519001</a></div></td>
							<td><div>月度订单</div></td>
							<td><div>河南配送中心</div></td>
							<td><div>2014-05-19 8:30:26</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-20 8:30:26</div></td>
							<td><div>李四</div></td>
							<td><div>已打印</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a style="color:red;" onclick="doViewFldDetail('YD20140519001');">YD20140519001001</a></div></td>
							<td><div><a style="color:red;" onclick="doViewOrderDetail('YD20140519001');">YD20140519001</a></div></td>
							<td><div>周度订单</div></td>
							<td><div>河南配送中心</div></td>
							<td><div>2014-05-19 8:30:26</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-20 8:30:26</div></td>
							<td><div>王五</div></td>
							<td><div>已打印</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>