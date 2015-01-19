<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包申请</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#pjsbsqlb").is(":hidden")){
			$("#pjsbsqlb").show();
			$("#pjsbsqlb").jTable();	
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
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/pjspgl/spgl/pjsbsqxz.jsp?action=1", "pjsbsqxx", "配件三包申请新增", options,true);
}
function doUpdate(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/pjspgl/spgl/pjsbsqxz.jsp?action=2", "pjsbsqxx", "配件三包申请编辑", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包申请</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="pjsbsqform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="pjsbsqTable">
					<tr>
						<td><label>三包申请单号：</label></td>
						<td><input type="text" id="SBSQDH" name="SBSQDH" datatype="1,is_null,100" value="" /></td>
						<td><label>状态：</label></td>
						<td><select type="text" id="ZT" name="ZT" kind="dic" class="combox" src="E#1=已保存:2=审批驳回">
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
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
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="pjsbsq">
			<table width="100%" id="pjsbsqlb" name="pjsbsqlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>三包申请单号</th>
						<th>购货单位</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>数量</th>
						<th>提报日期</th>
						<th>状态</th>
						<th>驳回日期</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>申请单号1</td>
						<td>张三</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>2</td>
						<td>2014-06-01</td>
						<td>已保存</td>
						<td></td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>申请单号2</td>
						<td>张三</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>2</td>
						<td>2014-06-01</td>
						<td>审批驳回</td>
						<td>2014-06-03</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>申请单号3</td>
						<td>张三</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>2</td>
						<td>2014-06-01</td>
						<td>已保存</td>
						<td></td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>申请单号4</td>
						<td>张三</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>2</td>
						<td>2014-06-01</td>
						<td>已保存</td>
						<td></td>
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