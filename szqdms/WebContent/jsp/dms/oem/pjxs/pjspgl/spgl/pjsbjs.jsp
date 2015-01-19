<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包结算</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#pjsbjslb").is(":hidden")){
			$("#pjsbjslb").show();
			$("#pjsbjslb").jTable();
		}
		$("#opBtn").show();
	});
});
function open(){
	alertMsg.info("弹出服务商树!");
}
function doDetail(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/pjspgl/spgl/pjsbmx.jsp", "pjsbmx", "配件三包明细", options);
}
function doAccount(){
	alertMsg.info("结算成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包结算</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="pjsbjsform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="pjsbjsTable">
					<tr>
						<td><label>三包申请单号：</label></td>
						<td><input type="text" id="SBSQDH" name="SBSQDH" datatype="1,is_null,100" value="" /></td>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100" value="" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100" value="" /></td>
						<td><label>审核人：</label></td>
						<td><input type="text" id="SHR" name="SHR" datatype="1,is_null,100" value="" /></td>
					</tr>
					<tr>
						<td><label>提报日期：</label></td>
						<td><input type="text" id="TBRQ" name="TBRQ" datatype="1,is_null,100"  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" value="" /></td>
						<td><label>审核日期：</label></td>
						<td><input type="text" id="SHRQ" name="SHRQ" datatype="1,is_null,100"  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="pjsbjs">
			<table width="100%" id="pjsbjslb" name="pjsbjslb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:"></th> 
						<th>服务商代码</th>
						<th>服务商名称</th>
						<th>三包申请单号</th>
						<th>购货单位</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>数量</th>
						<th align="right">金额</th>
						<th>审核人</th>
						<th>审核日期</th>
						<th>提报日期</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>服务商代码1</td>
						<td>服务商名称1</td>
						<td><a href="#" onclick="doDetail()" class="op">申请单号1</a></td>
						<td>张三</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>2</td>
						<td>100</td>
						<td>admin</td>
						<td>2014-05-25</td>
						<td>2014-05-1</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>服务商代码2</td>
						<td>服务商名称2</td>
						<td><a href="#" onclick="doDetail()" class="op">申请单号2</a></td>
						<td>张三</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>2</td>
						<td>300</td>
						<td>admin</td>
						<td>2014-05-25</td>
						<td>2014-05-1</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>服务商代码3</td>
						<td>服务商名称3</td>
						<td><a href="#" onclick="doDetail()" class="op">申请单号3</a></td>
						<td>张三</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>2</td>
						<td>200</td>
						<td>admin</td>
						<td>2014-05-25</td>
						<td>2014-05-1</td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>服务商代码4</td>
						<td>服务商名称4</td>
						<td><a href="#" onclick="doDetail()" class="op">申请单号4</a></td>
						<td>张三</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>2</td>
						<td>210</td>
						<td>admin</td>
						<td>2014-05-25</td>
						<td>2014-05-1</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="formBar" id="opBtn" style="display:none">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doAccount()" id="account">结&nbsp;&nbsp;算</button></div></div></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>