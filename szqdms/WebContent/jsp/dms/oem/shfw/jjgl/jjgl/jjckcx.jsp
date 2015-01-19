<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件出库查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#jjcklb").is(":hidden")){
			$("#jjcklb").show();
			$("#jjcklb").jTable();
		}
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件出库查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jjckcxform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jjckcxTable">
						<tr>
						    <td><label>供应商代码：</label></td>
							<td><input type="text" id="GYSDM" name="GYSDM" datatype="1,is_null,100" value=""/></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="GYSMC" name="GYSMC" datatype="1,is_null,100" value="" /></td>
						    <td><label>出库日期：</label></td>
						    <td>
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					   		 </td>
					 	</tr>
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100" value="" /></td>
							<td><label>出库类型：</label></td>
							<td><select id="CKLX" name="CKLX" class="combox" kind="dic"  src="E#1=认领:2=旧件销毁:3=其他出库">
									<option value=-1>--</option>
							    </select>
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
			<div id="jjck">
				<table width="100%" id="jjcklb" name="jjcklb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>供应商代码</th>
							<th>供应商名称</th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th>出库数量</th>
							<th>出库类型</th>
							<th>出库日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>供应商代码1</td>
							<td>供应商名称1</td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td>7</td>
							<td>旧件销毁</td>
							<td>2014-5-20</td>
						</tr>
						<tr>
							<td>2</td>
							<td>供应商代码2</td>
							<td>供应商名称2</td>
							<td>配件代码2</td>
							<td>配件名称2</td>
							<td>14</td>
							<td>认领</td>
							<td>2014-5-20</td>
						</tr>
						<tr>
							<td>3</td>
							<td>供应商代码3</td>
							<td>供应商名称3</td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td>4</td>
							<td>其他出库</td>
							<td>2014-5-20</td>
						</tr>
						<tr>
							<td>4</td>
							<td>供应商代码4</td>
							<td>供应商名称4</td>
							<td>配件代码4</td>
							<td>配件名称4</td>
							<td>20</td>
							<td>认领</td>
							<td>2014-5-20</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>