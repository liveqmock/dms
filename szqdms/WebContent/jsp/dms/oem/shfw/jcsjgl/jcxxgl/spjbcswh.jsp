<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔基本参数设定</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Spjbcsgl/SpjbcsglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#spjbcslb").is(":hidden"))
		{
			$("#spjbcslb").show();
			$("#spjbcslb").jTable();
		}
	});
});
function open(){
	alertMsg.info("选择服务商树！");
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
function doAdd(){
	var options = {max:false,width:760,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/spjbcsxz.jsp?action=1", "spjbcs", "索赔基本参数新增", options);
}
function doUpdate(rowobj){
	$("td input",$(rowobj)).attr("checked",true);
	var options = {max:false,width:760,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/spjbcsxz.jsp?action=2", "spjbcs", "索赔基本参数编辑", options);
}
function doUpdateAll(){
	var options = {max:false,width:760,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/spjbcsxz.jsp?action=1", "spjbcs", "索赔基本参数批量修改", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;索赔基本参数设定 </h4>
	<div class="page">
	<div class="pageHeader">
		<form id="spjbcsform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="spjbcsTable">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
					</tr>	
					<tr>
						<td><label>用户类别：</label></td>
						<td><select type="text" id="YHLB" name="YHLB" class="combox" kind="dic" src="E#1=军车:2=民车">
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>工时系数：</label></td>
						<td><input type="text" id="GSXS" name="GSXS" datatype="1,is_null,100" value="" /></td>
						<td><label>基础工时(元)：</label></td>
						<td><input type="text" id="JCGS" name="JCGS" datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="update"  onclick="doUpdateAll()">批&nbsp;&nbsp;量&nbsp;&nbsp;修&nbsp;&nbsp;改</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="gz">
			<table width="100%" id="spjbcslb" name="spjbcslb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>服务商代码</th>
						<th>服务商名称</th>
						<th>用户类别</th>
						<th>基础工时</th>
						<th>工时系数</th>
						<th align="right">工时单价(元)</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>服务商代码1</td>
						<td>服务商名称1</td>
						<td>军车</td>
						<td>28</td>
						<td>1</td>
						<td>28.00</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>服务商代码2</td>
						<td>服务商名称2</td>
						<td>民车</td>
						<td>30</td>
						<td>1</td>
						<td>30.00</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>服务商代码3</td>
						<td>服务商名称3</td>
						<td>民车</td>
						<td>28</td>
						<td>1</td>
						<td>28.00</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>服务商代码4</td>
						<td>服务商名称4</td>
						<td>民车</td>
						<td>28</td>
						<td>1</td>
						<td>28.00</td>
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