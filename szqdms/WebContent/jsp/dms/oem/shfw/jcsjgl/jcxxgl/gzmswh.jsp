<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>故障模式维护</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#gzmslb").is(":hidden")){
			$("#gzmslb").show();
			$("#gzmslb").jTable();
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
	var options = {max:false,width:750,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gzmsxz.jsp?action=1", "gzmsxx", "故障模式新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:750,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gzmsxz.jsp?action=2", "gzmsxx", "故障模式编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;故障模式维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="gzmswhform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="gzmswhTable">
						<tr>
							<td><label>故障代码：</label></td>
							<td><input type="text" /></td>
							<td><label>故障名称：</label></td>
							<td><input type="text"  /></td>
							<td><label>严重程度：</label></td>
							<td><select type="text"  id="YZCD" class="combox" kind="dic" src="E#-1=--:1=致命故障:2=严重故障:3=一般故障">
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>故障模式代码：</label></td>
							<td><input type="text" id="GZMSDM" name="GZMSDM" class="combox" kind="dic" src="E#模式代码1=模式名称1:模式代码2=模式名称2"/></td>
							<td><label>车辆部位：</label></td>
							<td><input type="text" id="CLBW" name="CLBW" class="combox" dicWidth="300" kind="dic" src="E#车辆部位代码1=车辆部位名称1:车辆部位代码2=车辆部位名称2" /></td>
							<td><input type="hidden" /></td>
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
			<div id="gzms">
				<table width="100%" id="gzmslb" name="gzmslb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:"></th>
							<th>故障代码</th>
							<th>故障名称</th>
							<th>故障模式代码</th>
							<th>故障模式名称</th>
							<th>车辆部位</th>
							<th>严重程度</th>
							<th>备注</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>故障代码1</td>
							<td>故障名称1</td>
							<td>故障模式代码1</td>
							<td>故障模式名称1</td>
							<td>车辆部位1</td>
							<td>严重故障</td>
							<td></td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>故障代码2</td>
							<td>故障名称2</td>
							<td>故障模式代码2</td>
							<td>故障模式名称2</td>
							<td>车辆部位2</td>
							<td>一般故障</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>故障代码3</td>
							<td>故障名称3</td>
							<td>故障模式代码3</td>
							<td>故障模式名称3</td>
							<td>车辆部位3</td>
							<td>严重故障</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>故障代码4</td>
							<td>故障名称4</td>
							<td>故障模式代码1</td>
							<td>故障模式名称1</td>
							<td>车辆部位2</td>
							<td>致命故障</td>
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