<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包规则查询</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#sbgzlb").is(":hidden")){
			$("#sbgzlb").show();
			$("#sbgzlb").jTable();
		}
	});
});
function doDownload(){
	alertMsg.info("下载配件三包规则");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;配件三包规则查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="sbgzform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="sbgzTable">
					<tr>
						<td><label>三包规则类型：</label></td>
					   	<td><select type="text" id="SBGZLX" name="SBGZLX"  kind="dic" class="combox" src="E#1=系统规则:2=业务规则">
								<option value=-1>--</option>	
					   		</select>
						</td>
						<td><label>三包规则代码：</label></td>
						<td><input type="text" id="SBGZDM" name="SBGZDM" datatype="1,is_null,100" value="" /></td>
						<td><label>三包规则名称：</label></td>
						<td><input type="text" id="SBGZMC" name="SBGZMC" datatype="1,is_null,100" value="" /></td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100" value="" /></td>
						<td><label>配件名称：</label></td>
						<td colspan="3"><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
						<li>	
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="download" onclick="doDownload()">下&nbsp;&nbsp;载</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="sbgz">
			<table width="100%" id="sbgzlb" name="sbgzlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>三包规则代码</th>
						<th>三包规则名称</th>
						<th>规则类型</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>三包月份</th>
						<th>三包里程</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>三包规则代码1</td>
						<td>三包规则名称1</td>
						<td>业务规则</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>24</td>
						<td>25000</td>
					</tr>
					<tr>
						<td>2</td>
						<td>三包规则代码2</td>
						<td>三包规则名称2</td>
						<td>业务规则</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>24</td>
						<td>25000</td>
					</tr>
					<tr>
						<td>3</td>
						<td>三包规则代码3</td>
						<td>三包规则名称3</td>
						<td>业务规则</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>24</td>
						<td>25000</td>
					</tr>
					<tr>
						<td>4</td>
						<td>三包规则代码4</td>
						<td>三包规则名称4</td>
						<td>业务规则</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>24</td>
						<td>25000</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>