<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>激励系数维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Jlxsgl/JlxsglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#jlxslb").is(":hidden")){
			$("#jlxslb").show();
			$("#jlxslb").jTable();
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
function open(){
	alertMsg.info("打开服务店选择窗口！");
}
function doAdd(){
	var options = {max:false,width:600,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/jlxsxz.jsp?action=1", "jlxsxx", "激励系数新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:600,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/jlxsxz.jsp?action=2", "jlxsxx", "激励系数编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;激励系数维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jlxsform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jlxsTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
							<td><label>激励类别：</label></td>
							<td><select type="text" id="WFSDM" name="WFSDM" class="combox" kind="dic" src="E#1=大客户:2=NPG" datatype="1,is_null,100" >
								<option value=-1 selected>--</option>
							</select>
							</td>
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
			<div id="jlxs">
				<table width="100%" id="jlxslb" name="jlxslb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务站代码</th>
							<th>服务站名称</th>
							<th>激励类别</th>
							<th>发动机型号</th>
							<th>激励系数</th>
							<th>备注</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务站名称1</td>
							<td>NPG</td>
							<td></td>
							<td>0.2</td>
							<td></td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码2</td>
							<td>服务站名称2</td>
							<td>NPG</td>
							<td></td>
							<td>0.2</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务商代码3</td>
							<td>服务站名称3</td>
							<td>NPG</td>
							<td></td>
							<td>0.2</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>