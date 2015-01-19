<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>促销活动查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-pjlb").is(":hidden"))
		{
			$("#tab-pjlb").show();
			$("#tab-pjlb").jTable();
		}
	});
});
//查看活动详细信息
function doView(row)
{
	var options = {max:false,width:950,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/xsgl/cxgl/cxhdmx.jsp", "cxxxmx", "促销信息明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 促销管理   &gt; 促销活动查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>活动名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>活动日期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>活动状态：</label></td>
					     <td>
					     	<select type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#1=促销中:2=已关闭" datasource="DDBH" datatype="1,is_null,30">
					     		<option value="-1">--</option>
					     	</select>
					     </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_pjlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" ordertype='local' class="desc">活动编码</th>
							<th fieldname="DDLX" >活动名称</th>
							<th fieldname="GHPJK" >活动类型</th>
							<th fieldname="QWDHRQ" >活动开始日期</th>
							<th fieldname="ZJE">活动结束日期</th>
							<th fieldname="QWDHRQ" >是否免运费</th>
							<th fieldname="ZJE" maxlength="20">备注</th>
							<th fieldname="ZJE">活动状态</th>
							<th type="link" title="[查看]"  action="doView" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>CX001</div></td>
							<td><div>油品促销</div></td>
							<td><div>折扣促销</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>2014-08-30</div></td>
							<td><div>否</div></td>
							<td><div>本次促销活动概要说明</div></td>
							<td><div>促销中</div></td>
							<td><a href="#" onclick="doView()" class="op">[查看]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>CX002</div></td>
							<td><div>新品上市促销</div></td>
							<td><div>新品上市</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>2014-08-30</div></td>
							<td><div>是</div></td>
							<td><div>本次促销活动概要说明</div></td>
							<td><div>已关闭</div></td>
							<td><a href="#" onclick="doView()" class="op">[查看]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>