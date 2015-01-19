<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>额度调整</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		$("#xjzhgllb").show();
		$("#xjzhgllb").jTable();
		
	});
});
function open(){
	alertMsg.info("弹出服务商树！");
}
//列表编辑连接
function doUpdate(rowobj)
{
	var options = {max:false,width:790,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/cwgl/xyedgl/edtzmx.jsp", "edtzmx", "信用额度调整", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;信用额度管理&gt;信用额度调整</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="xjzhglform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="xjzhglTable">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
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
		<div id="xjzhgl">
			<table width="100%" id="xjzhgllb" name="xjzhgllb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH"     colwidth="20">序号</th>
						<th colwidth="60" >服务商代码</th>
						<th colwidth="60" >服务商名称</th>
						<th colwidth="100" align="right">当前额度</th>
						<th colwidth="60" >额度类型</th>
						<th colwidth="140" >有效期限</th>
						<th colwidth="85" type="link" title="[调整]"  action="doUpdate" >操作</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
					<td class="rownums"><div>1</div></td>
					<td><div>配送中心1</div></td>
					<td><div>配送中心1</div></td>
					<td><div>100,000.00</div></td>
					<td><div>固定</div></td>
					<td><div></div></td>
					<td><a href="#" onclick="doUpdate()" class="op">[调整]</a></td>
				</tr>
				 <tr>
					<td class="rownums"><div>2</div></td>
					<td><div>配送中心2</div></td>
					<td><div>配送中心2</div></td>
					<td><div>200,000.00</div></td>
					<td><div>临时</div></td>
					<td><div>2014-06-01至2014-12-31</div></td>
					<td><a href="#" onclick="doUpdate()" class="op">[调整]</a></td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>