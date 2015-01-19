<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>外出费用维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Wcfygl/WcfyglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#wclb").is(":hidden"))
		{
			$("#wclb").show();
			$("#wclb").jTable();
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
	var options = {max:false,width:750,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/wcfyxz.jsp?action=1", "wcxx", " 外出费用新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:750,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/wcfyxz.jsp?action=2", "wcxx", "外出费用编辑", options);
}
function open(){
	alertMsg.info("弹出服务商树");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;外出费用维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="wcwhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="wcTable">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						<td><label>外出次数：</label></td>
						<td><select type="text" class="combox" id="WCCS" name="WCCS" kind="dic" src="E#1=一次外出:2=二次外出" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
					</tr>
					<tr>
						<td><label>外出时间：</label></td>
						<td><select type="text" class="combox" id="WCSJ" name="WCSJ" kind="dic" src="E#1=白天:2=夜间" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
				        <td><label>备车类型：</label></td>
						<td><select type="text" class="combox" id="ZBCLX" name="ZBCLX" kind="dic" src="E#1=自备车:2=非自备车" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
						<td><label>状态：</label></td>
						<td>
							<select type="text" class="combox" id="ZT" name="ZT" kind="dic" src="YXBS"  datatype="1,is_null,30" value="">
						    	<option value="-1">--</option>
					   		</select>
						</td>
					</tr>
						<tr>
						<td><label>费用类别：</label></td>
						<td>
							<select type="text" class="combox" id="FYLB" name="FYLB" kind="dic" src="E#1=在途补助:2=服务车费:3=差旅费" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
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
		<div id="wc">
			<table width="100%" id="wclb" name="wclb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>服务商代码</th>
						<th>服务商名称</th>
						<th>费用类别</th>
						<th>外出次数</th>
						<th>时间范围</th>
						<th>备车类型</th>
						<th>里程（开始）</th>
						<th>里程（结束）</th>
						<th align="right">费用(单价)</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>服务商代码1</td>
						<td>服务商名称1</td>
						<td>在途补助</td>
						<td>一次外出</td>
						<td>夜间 </td>
						<td>自备车</td>
						<td>0</td>
						<td>99</td>
						<td>3.5</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>服务商代码2</td>
						<td>服务商名称2</td>
						<td>服务车费</td>
						<td>一次外出</td>
						<td>白天 </td>
						<td>非自备车</td>
						<td>0</td>
						<td>99</td>
						<td>3</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>服务商代码3</td>
						<td>服务商名称3</td>
						<td>差旅费</td>
						<td></td>
						<td></td>
						<td>自备车</td>
						<td></td>
						<td></td>
						<td>1</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>服务商代码4</td>
						<td>服务商名称4</td>
						<td>在途补助</td>
						<td>二次外出</td>
						<td>白天</td>
						<td>非自备车</td>
						<td>0</td>
						<td>99</td>
						<td>1</td>
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