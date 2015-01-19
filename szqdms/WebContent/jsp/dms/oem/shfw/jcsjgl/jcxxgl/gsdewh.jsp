<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工时定额维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Gsdegl/GsdeglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#gslb").is(":hidden"))
		{
			$("#gslb").show();
			$("#gslb").jTable();
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
	var options = {max:false,width:720,height:270,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gsdexz.jsp?action=1", "gsxx", "工时定额新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:720,height:270,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gsdexz.jsp?action=2", "gsxx", "工时定额编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;工时定额维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="gswhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="gsTable">
					<tr>
						<td><label>用户类别：</label></td>
						<td><select type="text" id="YHLB" name="YHLB" class="combox" kind="dic" src="E#1=军车:2=民车">
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>工时代码：</label></td>
						<td><input type="text" id="GSDM" name="GSDM" datatype="1,is_null,100" value="" /></td>
						<td><label>工时名称：</label></td>
						<td><input type="text" id="GSMC" name="GSMC" datatype="1,is_null,100" value="" /></td>
					</tr>
					<tr>
						<td><label>车型：</label></td>
						<td><input type="text" id="CX" name="CX"  operation="right_like" multi="true" class="combox" kind="dic" src="E#车型代码1=车型名称1:车型代码2=车型名称2"/></td>
						<td><label>车辆部位：</label></td>
						<td><input type="text" id="CLBW" name="CLBW" operation="right_like" multi="true" class="combox" kind="dic" src="E#车辆部位代码1=车辆部位名称1:车辆部位代码2=车辆部位名称2"/></td>
						<td></td>
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
		<div id="gs">
			<table width="100%" id="gslb" name="gslb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:"></th>
						<th>用户类别</th>
						<th>工时代码</th>
						<th>工时名称</th>
						<th>车型</th>
						<th>车辆部位</th>
						<th>定额</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>军车</td>
						<td>11001</td>
						<td>换手油门总成</td>
						<td>车型1</td>
						<td>车辆部位1</td>
						<td>2</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>民车</td>
						<td>11002</td>
						<td>换中减过桥箱盖 </td>
						<td>车型2</td>
						<td>车辆部位2</td>
						<td>8</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>民车</td>
						<td>11003</td>
						<td>换前围上面罩锁体总成</td>
						<td>车型3</td>
						<td>车辆部位3</td>
						<td>4</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>民车</td>
						<td>11004</td>
						<td>换排气管焊合总成</td>
						<td>车型4</td>
						<td>车辆部位4</td>
						<td>5</td>
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