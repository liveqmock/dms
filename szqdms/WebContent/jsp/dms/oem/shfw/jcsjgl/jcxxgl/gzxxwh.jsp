<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>基础代码维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Gzxxgl/GzxxglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#gzlb").is(":hidden"))
		{
			$("#gzlb").show();
			$("#gzlb").jTable();
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
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gzxxxz.jsp?action=1", "gzxx", "基础代码新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gzxxxz.jsp?action=2", "gzxx", "基础代码编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;基础代码维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="gzwhform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="gzTable">
						<tr>
							<td><label>代码类别：</label></td>
							<td><select type="text" class="combox" id="DMLB" name="DMLB" kind="dic" src="E#1=故障来源:2=故障模式:3=故障程度" datatype="1,is_null,10" >
					        	<option value="-1" selected>--</option>
					        </select>
					        </td>
							<td><label>代码：</label></td>
							<td><input type="text" id="DM" name="DM"datatype="1,is_null,100" value="" /></td>
							<td><label>描述：</label></td>
							<td><input type="text" id="MS" name="MS"datatype="1,is_null,100" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="gz">
				<table width="100%" id="gzlb" name="gzlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:"></th>
							<th>类别名称</th>
							<th>代码</th>
							<th>描述</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>故障来源</td>
							<td>GZLY1</td>
							<td>来电</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>故障模式</td>
							<td>GZMS1</td>
							<td>断裂</td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>故障程度</td>
							<td>GZCD1</td>
							<td>严重故障</td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>故障模式</td>
							<td>GZMS2</td>
							<td>开裂  </td>
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