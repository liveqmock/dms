<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动服务商管理</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#fwhdxglb").is(":hidden")){
			$("#fwhdxglb").show();
			$("#fwhdxglb").jTable();
		}
	});
});
function openDealer(){
	alertMsg.info("弹出服务商树！");
}
function doAdd(){
	var options = {max:false,width:800,heigth:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdfwsxz.jsp", "fwhdfws", "服务活动服务商新增", options);
}
function doDelete(rowobj){
	//alert(alertMsg.confirm("确定删除?"));
	row = $(rowobj);
	alertMsg.info("批量删除成功");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if(row)
			row.remove();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<div class="page">
		<div class="pageHeader">
			<form id="fwhdxgform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdxgTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openDealer();"/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="delete" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="fwhdxg">
				<table width="100%" id="fwhdxglb" name="fwhdxglb" style="display: none" >
					<thead>
						<tr>
							<th  type="multi" name="XH" style="display:"></th>
							<th>服务商代码</th>
							<th>服务商名称</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>代码1</td>
							<td>名称1</td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>代码2</td>
							<td>名称2</td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>代码3</td>
							<td>名称3</td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>代码4</td>
							<td>名称4</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>