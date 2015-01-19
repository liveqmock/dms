<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>大客户定保次数</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#dbcslb").show();
	$("#dbcslb").jTable();
	/* $("#search").click(function(){
		$("#dbcslb").show();
		$("#dbcslb").jTable();
	}); */
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/dbcssz.jsp", "dbcssz", "设置大客户定保次数", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;大客户定保次数</h4>
		<div class="page">
		<!-- <div class="pageHeader">
			<form id="dbcsform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="dbcsTable">
						<tr>
						   <td><label>VIN：</label></td>
      					   <td><textarea name="textarea" id="vin" name="vin" cols="18" rows="3" ></textarea></td>
					 	</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div> -->
		<div class="pageContent">
			<div id="dbcs">
				<table width="100%" id="dbcslb" name="dbcslb" style="display: none" >
					<thead>
						<tr>
							<th>定保次数</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>