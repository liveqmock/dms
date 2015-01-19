<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>万里定保单查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#wldblb").is(":hidden"))
		{
			$("#wldblb").show();
			$("#wldblb").jTable();
		}
		
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：索赔管理&gt;万里定保单管理&gt;万里定保单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="wldbform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="wldbTable">
						<tr>
							<td><label>VIN：</label></td>
      						<td><textarea name="textarea" id="vin" name="vin" cols="18" rows="3" ></textarea></td>
							<td><label>发动机号：</label></td>
							<td><input type="text" id="FDJH" name="FDJH" datatype="1,is_null,100" value="" /></td>
						</tr>	
						<tr>
							<td><label>车牌号：</label></td>
							<td><input type="text" id="CPH" name="CPH" datatype="1,is_null,100"  value="" /></td>
							<td><label>客户姓名：</label></td>
							<td><input type="text" id="KHXM" name="KHXM" datatype="1,is_null,100"  value="" /></td>
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
			<div id="wldb">
				<table width="100%" id="wldblb" name="wldblb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>VIN</th>
							<th>发动机号</th>
							<th>定保日期</th>
							<th>里程</th>
							<th>客户名称</th>
							<th>定保次数</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>VIN1</td>
							<td>发动机1</td>
							<td>2014-5-30</td>
							<td>5000</td>
							<td>张三</td>
							<td>3</td>
						</tr>
						<tr>
							<td>2</td>
							<td>VIN2</td>
							<td>发动机2</td>
							<td>2014-5-30</td>
							<td>5000</td>
							<td>张三</td>
							<td>1</td>
						</tr>
						<tr>
							<td>3</td>
							<td>VIN3</td>
							<td>发动机3</td>
							<td>2014-5-30</td>
							<td>5000</td>
							<td>张三</td>
							<td>3</td>
						</tr>
						<tr>
							<td>4</td>
							<td>VIN4</td>
							<td>发动机4</td>
							<td>2014-5-30</td>
							<td>5000</td>
							<td>张三</td>
							<td>3</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>