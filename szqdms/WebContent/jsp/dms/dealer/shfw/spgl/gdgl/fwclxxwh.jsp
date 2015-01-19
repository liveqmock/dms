<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务车辆信息维护</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Spdgl/SpdglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-pgrylb").is(":hidden"))
		{
			$("#tab-pgrylb").show();
			$("#tab-pgrylb").jTable();
		}
	});
});
//列表编辑链接(修改)
function doUpdate(row)
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/gdgl/fwclxxxz.jsp?action=2", "fwclxxwh", "服务车辆信息维护", options);
}
//新增
function doAddpgyx()
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/gdgl/fwclxxxz.jsp?action=1", "fwclxxwh", "服务车辆信息维护", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 工单管理   &gt; 服务车辆信息维护</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pgrycx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pgrycx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>车牌号码：</label></td>
					    <td><input type="text" id="in-ryxm" name="in-ryxm" datatype="1,is_digit_letter,30" operation="like" /></td>
				   		<td><label>状态：</label></td>
					    <td> <select type="text" id="dia-in-fwlx"  name="dia-in-fwlx" kind="dic" src="E#1=有效:0=无效"  datatype="1,is_null,30" value="">
								    		<option value="-1" selected>请选择</option>
					    </select></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" onclick="doAddpgyx();">新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_pgrylb" >
			<table style="display:none;width:100%;" id="tab-pgrylb" name="tablist" ref="page_pgrylb" refQuery="fm-pgrycx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="" >车牌号码</th>
							<th fieldname="">车型</th>
							<th fieldname="" >发动机号</th>
							<th fieldname="" >底盘号</th>
							<th fieldname="" >状态</th>
							<th fieldname="" >备注</th>
							<th colwidth="105" type="link" title="[修改]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>车牌号码1</div></td>
							<td><div>车型1</div></td>
							<td><div>发动机号1</div></td>
							<td><div>底盘号1</div></td>
							<td><div>有效</div></td>
							<td><div></div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>车牌号码2</div></td>
							<td><div>车型2</div></td>
							<td><div>发动机号2</div></td>
							<td><div>底盘号2</div></td>
							<td><div>无效</div></td>
							<td><div></div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>车牌号码3</div></td>
							<td><div>车型3</div></td>
							<td><div>发动机号3</div></td>
							<td><div>底盘号3</div></td>
							<td><div>有效</div></td>
							<td><div></div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>