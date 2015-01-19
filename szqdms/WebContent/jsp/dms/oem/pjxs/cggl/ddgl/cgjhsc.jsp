<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购计划生成</title>
<script type="text/javascript">
//变量定义
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-jhlb").is(":hidden"))
		{
			$("#tab-jhlb").show();
			$("#tab-jhlb").jTable();
		}
	});
});
function doDownload()
{
	alertMsg.info("下载成EXCEL。");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购订单管理   &gt; 采购计划生成</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-jhcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-jhcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="in-pjdm" name="in-pjdm" datatype="1,is_null,30" operation="like" /></td>
				    	<td><label>配件名称：</label></td>
					    <td ><input type="text" id="in-pjmc" name="in-pjmc" datatype="1,is_null,60" operation="like" /></td>
					    <td><label>时间段：</label></td>
					    <td >
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" style="width:75px;"  datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" style="width:75px;margin-left:-30px;" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" onclick="doDownload();">下&nbsp;&nbsp;载</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_jhlb" >
			<table style="display:none;width:100%;" id="tab-jhlb" name="tablist" ref="page_jhlb" refQuery="fm-cx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="">时间周期</th>
							<th fieldname="">配件代码</th>
							<th fieldname="">配件名称</th>
							<th fieldname="">预测量</th>
							<th fieldname="">计划模型量</th>
							<th fieldname="" >月度订单未满足量</th>
							<th fieldname="" >其他订单未满足量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>2014-03-01至2014-03-31</div></td>
							<td><div>配件代码1</div></td>
							<td><div>配件名称1</div></td>
							<td><div>100</div></td>
							<td><div>99</div></td>
							<td><div>98</div></td>
							<td><div>1</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>2014-03-01至2014-03-31</div></td>
							<td><div>配件代码2</div></td>
							<td><div>配件名称2</div></td>
							<td><div>0</div></td>
							<td><div>99</div></td>
							<td><div>98</div></td>
							<td><div>1</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>2014-03-01至2014-03-31</div></td>
							<td><div>配件代码3</div></td>
							<td><div>配件名称3</div></td>
							<td><div>100</div></td>
							<td><div>0</div></td>
							<td><div>98</div></td>
							<td><div>1</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>2014-03-01至2014-03-31</div></td>
							<td><div>配件代码4</div></td>
							<td><div>配件名称4</div></td>
							<td><div>100</div></td>
							<td><div>0</div></td>
							<td><div>100</div></td>
							<td><div>0</div></td>
						</tr>
							<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>2014-03-01至2014-03-31</div></td>
							<td><div>配件代码5</div></td>
							<td><div>配件名称5</div></td>
							<td><div>100</div></td>
							<td><div>0</div></td>
							<td><div>0</div></td>
							<td><div>100</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>