<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>返利审核</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		$("#xjzhgllb").show();
		$("#xjzhgllb").jTable();
		
	});
});
//列表编辑连接
 function doUpdate(rowobj)
 {
 	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
 	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/cwgl/flgl/flshmx.jsp", "flshmx", "返利审核", options);
 }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;返利管理&gt;返利审核</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="xjzhglform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="xjzhglTable">
					<tr>
				    	<td><label>返利计算周期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>	
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
						<th  name="XH" colwidth="10">序号</th>
						<th colwidth="140" >返利计算周期</th>
						<th colwidth="85" type="link" title="[审核]"  action="doUpdate" >操作</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
					<td class="rownums"><div>1</div></td>
					<td><div>2014-06-01至2014-06-30</div></td>
					<td><a href="#" onclick="doUpdate()" class="op">[审核]</a></td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>