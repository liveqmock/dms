<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>回运单查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#hydcxlb").is(":hidden"))
		{
			$("#hydcxlb").show();
			$("#hydcxlb").jTable();
		}
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;回运单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="hydcxform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="hydcxTable">
						<tr>
							<td><label>回运单号：</label></td>
							<td><input type="text" id="HYDH" name="HYDH" datatype="1,is_null,100"  value="" /></td>
							<td><label>运输方式：</label></td>
							<td><select  type="text" id="YSFS" name="YSFS" kind="dic" class="combox" src="E#1=配货:2=专车:3=铁路:4=空运"  datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>状态：</label></td>
							<td><select  type="text" id="HYZT" name="HYZT" kind="dic" class="combox" src="E#1=已上报:2=已入库"  datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
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
			<div id="hydcx">
				<table width="100%" id="hydcxlb" name="hydcxlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>回运单号</th>
							<th>索赔单数量</th>
							<th>回运旧件数量</th>
							<th>装箱总数</th>
							<th>运输方式</th>
							<th>服务站回运日期</th>
							<th>配送中心回运日期</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>回运单号1</td>
							<td>2</td>
							<td>5</td>
							<td>1</td>
							<td>专车</td>
							<td>2014-5-20</td>
							<td>2014-5-25</td>
							<td>已上报</td>
						</tr>
						<tr>
							<td>2</td>
							<td>回运单号2</td>
							<td>3</td>
							<td>6</td>
							<td>2</td>
							<td>铁路</td>
							<td>2014-5-20</td>
							<td>2014-5-25</td>
							<td>已入库</td>
						</tr>
						<tr>
							<td>3</td>
							<td>回运单号3</td>
							<td>2</td>
							<td>5</td>
							<td>1</td>
							<td>配货</td>
							<td>2014-5-20</td>
							<td>2014-5-25</td>
							<td>已入库</td>
						</tr>
						<tr>
							<td>4</td>
							<td>回运单号4</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>空运</td>
							<td>2014-5-20</td>
							<td>2014-5-25</td>
							<td>已上报</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>