<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>转账审核</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#zzshlb").is(":hidden")){
			$("#zzshlb").show();
			$("#zzshlb").jTable();
		}
	})
});
function open(){
	alertMsg.info("弹出服务商树！");
}
function doAudit(){
	var options = {max:false,width:1050,height:320,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/cwgl/zzgl/zzshmx.jsp", "zzmx", "审核", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;转账管理&gt;转账审核</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="zzshform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="zzshTable">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						<td><label>申请日期：</label></td>
						<td>
				    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   	</td>
					</tr>	
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="zzsh">
			<table width="100%" id="zzshlb" name="zzshlb" style="display: none" >
				<thead>
					<tr>
						<th>服务商代码</th>
						<th>服务商名称</th>
						<th>转出账户类型</th>
						<th>转入账户类型</th>
						<th align="right">金额</th>
						<th>申请日期</th>
						<th>转账原因</th>
						<th colwidth="85" type="link" title="[审核]"  action="doAudit">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>服务商代码1</td>
						<td>服务商名称1</td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>10,000,000.00</td>
						<td>2014-06-06</td>
						<td></td>
						<td><a href="#" onclick="doAudit()" class="op">[审核]</a></td>
					</tr>
					<tr>
						<td>服务商代码2</td>
						<td>服务商名称2</td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>10,000,000.00</td>
						<td>2014-06-06</td>
						<td></td>
						<td><a href="#" onclick="doAudit()" class="op">[审核]</a></td>
					</tr>
					<tr>
						<td>服务商代码3</td>
						<td>服务商名称3</td>
						<td>返利账户</td>
						<td>现金账户</td>
						<td>10,000,000.00</td>
						<td>2014-06-06</td>
						<td></td>
						<td><a href="#" onclick="doAudit()" class="op">[审核]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>