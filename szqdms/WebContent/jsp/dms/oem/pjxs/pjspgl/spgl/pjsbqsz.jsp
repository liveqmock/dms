<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包期设置</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#pjsbqlb").is(":hidden")){
			$("#pjsbqlb").show();
			$("#pjsbqlb").jTable();
		}
	});
});
function doDelete(rowobj){
	//alert(alertMsg.confirm("确定删除?"));
	row = $(rowobj);
	alertMsg.info("删除成功");
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
function doAdd(){
	var options = {max:false,width:700,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/pjspgl/spgl/pjsbqxz.jsp", "pjsbqxx", "配件三包期新增", options);
}
function doUpdate(){
	var options = {max:false,width:700,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/pjspgl/spgl/pjsbqxg.jsp", "pjsbqxg", "配件三包期编辑", options);
}
function doImp(){
	alertMsg.info("导入配件三包期！");
}
function doExp(){
	alertMsg.info("导出配件三包期模版！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包期设置</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="pjsbqform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="pjsbqTable">
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100" value="" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="imp" onclick="doImp()">导&nbsp;&nbsp;入</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">导&nbsp;&nbsp;出</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="pjsbq">
			<table width="100%" id="pjsbqlb" name="pjsbqlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>三包月份</th>
						<th>延保月份</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>6</td>
						<td>2</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>12</td>
						<td>2</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>9</td>
						<td>3</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>6</td>
						<td>3</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>