<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动类别管理</title>
<script type="text/javascript">
//查询提交方法
var url = "<%=request.getContextPath()%>/Fwhdlbgl/FwhdlbglAction.do";
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#fwhdlbgllb").is(":hidden")){
			$("#fwhdlbgllb").show();
			$("#fwhdlbgllb").jTable();
		}
	});
});
function doAdd(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdlbxz.jsp?action=1", "fwhdxx", "服务活动新增", options,true);
	
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);//行记录选择项
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdlbxz.jsp?action=2", "fwhdxx", "服务活动修改", options,true);
}
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动管理&gt;服务活动类别管理</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fwhdlbglform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdlbglTable">
						<tr>
							<td><label>活动代码：</label></td>
							<td><input type="text" id="HDDM" name="HDDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>活动名称：</label></td>
							<td><input type="text" id="HDMC" name="HDMC" datatype="1,is_null,100" value="" /></td>
						</tr>
						<tr>
							<td><label>活动类别：</label></td>
							<td><select  type="text" id="HDLB" name="HDLB" kind="dic" class="combox" src="E#1=整改:2=促销"  datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
							<td><label>处理方式：</label></td>
							<td><select type="text" id="CLFS" name="CLFS"  kind="dic" class="combox" src="E#1=维修:2=零件更换" datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
							<td><label>状态：</label></td>
							<td><select type="text" id="HDZT" name="HDZT"  kind="dic" class="combox" src="E#1=未发布:2=已取消" datatype="1,is_null,100" value="" >
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
			<div id="fwhdlbgl">
				<table width="100%" id="fwhdlbgllb" name="fwhdlbgllb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>活动代码</th>
							<th>活动名称</th>
							<th>活动类别</th>
							<th>处理方式</th>
							<th>开始日期</th>
							<th>结束日期</th>
							<th>状态</th>
							<th colwidth="85" type="link" title="[修改]|[删除]"  action="doUpdate|doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>代码1</td>
							<td>名称1</td>
							<td>整改</td>
							<td>维修</td>
							<td>2013-03-05</td>
							<td>2013-09-25</td>
							<td>未发布</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[修改]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>代码2</td>
							<td>名称2</td>
							<td>促销</td>
							<td>零件更换</td>
							<td>2012-06-01</td>
							<td>2012-07-31</td>
							<td>已取消</td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>代码3</td>
							<td>名称3</td>
							<td>促销</td>
							<td>零件更换</td>
							<td>2014-03-01</td>
							<td>2014-06-31</td>
							<td>已取消</td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>代码4</td>
							<td>名称4</td>
							<td>整改</td>
							<td>维修</td>
							<td>2014-03-01</td>
							<td>2014-06-31</td>
							<td>未发布</td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>