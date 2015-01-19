<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包策略维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Sbclgl/SbclglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#sbcllb").is(":hidden")){
			$("#sbcllb").show();
			$("#sbcllb").jTable();
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
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbclxz.jsp?action=1", "sbclxx", "三包策略新增", options,true);
}
function doUpdate(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbclxz.jsp?action=2", "sbclxx", "三包策略编辑", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;三包策略维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="sbclwhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="sbclTable">
					<tr>
						<td><label>三包策略代码：</label></td>
						<td><input type="text" id="SBCLDM" name="SBCLDM"datatype="1,is_null,100" value="" /></td>
						<td><label>三包策略名称：</label></td>
						<td><input type="text" id="SBCLMC" name="SBCLMC"datatype="1,is_null,100" value="" /></td>
						<td><label>用户类别：</label></td>
						<td><select type="text" id="YHLB" name="YHLB" class="combox" kind="dic" src="E#1=军车:2=民车">
								<option value=-1>--</option>
							</select>
						</td>
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
		<div id="sbcl">
			<table width="100%" id="sbcllb" name="sbcllb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>三包策略代码</th>
						<th>三包策略名称</th>
						<th>用户类别</th>
						<th>规则类型</th>
						<th>三包规则代码</th>
						<th>三包规则名称</th>
						<th>状态</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>CL001</td>
						<td>策略名称1</td>
						<td>军车</td>
						<td>公路用车</td>
						<td>GZ001</td>
						<td>2011年后公路用车</td>
						<td>有效</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>CL002</td>
						<td>策略名称2</td>
						<td>民车</td>
						<td>非公路用车</td>
						<td>GZ002</td>
						<td>2011年后非公路用车</td>
						<td>有效</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>CL003</td>
						<td>策略名称3</td>
						<td>民车</td>
						<td>公路用车</td>
						<td>GZ003</td>
						<td>2011年前公路用车</td>
						<td>有效</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>CL004</td>
						<td>策略名称3</td>
						<td>民车</td>
						<td>非公路用车</td>
						<td>GZ004</td>
						<td>2011年前非公路用车</td>
						<td>有效</td>
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