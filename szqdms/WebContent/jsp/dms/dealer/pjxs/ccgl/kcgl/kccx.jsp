 <?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>库存查询</title>
<script type="text/javascript">
//初始化方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		$("#kclb").show();
		$("#kclb").jTable();
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 库存管理   &gt; 库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-kccx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-kccx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh"  datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>供应商：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#GYS1=供应商一:GYS2=供应商二:GYS3=供应商三" datasource="DDBH" datatype="1,is_null,30" operation="=" /></td>
				   </tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_kclb" >
			<table style="display:none;width:100%;" id="kclb" name="kclb" ref="page_kclb" refQuery="fm-kccx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th fieldname="DDBH" colwidth="100">配件代码</th>
							<th fieldname="GHPJK" colwidth="100">配件名称</th>
							<th fieldname="GYSDM" colwidth="100">供应商代码</th>
							<th fieldname="GYSMC" colwidth="100">供应商名称</th>
							<th fieldname="DW" colwidth="100">单位</th>
							<th fieldname="ZXBZS" colwidth="100">最小包装数</th>
							<th fieldname="GHPJK" colwidth="80">库存数量</th>
							<th fieldname="GHPJK" colwidth="80">占用数量</th>
							<th fieldname="GHPJK" colwidth="80">可用库存</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div>PJ0001</div></td>
							<td><div>配件一</div></td>
							<td><div>GYS1</div></td>
							<td><div>供应商一</div></td>
							<td><div>箱</div></td>
							<td><div>20</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div>PJ0002</div></td>
							<td><div>配件二</div></td>
							<td><div>GYS2</div></td>
							<td><div>供应商二</div></td>
							<td><div>箱</div></td>
							<td><div>20</div></td>
							<td><div>110</div></td>
							<td><div>30</div></td>
							<td><div>80</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td><div>PJ0003</div></td>
							<td><div>配件三</div></td>
							<td><div>GYS3</div></td>
							<td><div>供应商三</div></td>
							<td><div>箱</div></td>
							<td><div>20</div></td>
							<td><div>190</div></td>
							<td><div>30</div></td>
							<td><div>160</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td><div>PJ0004</div></td>
							<td><div>配件四</div></td>
							<td><div>GYS2</div></td>
							<td><div>供应商二</div></td>
							<td><div>箱</div></td>
							<td><div>20</div></td>
							<td><div>70</div></td>
							<td><div>30</div></td>
							<td><div>40</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>5</div></td>
							<td><div>PJ0005</div></td>
							<td><div>配件五</div></td>
							<td><div>GYS2</div></td>
							<td><div>供应商二</div></td>
							<td><div>箱</div></td>
							<td><div>20</div></td>
							<td><div>150</div></td>
							<td><div>30</div></td>
							<td><div>120</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>