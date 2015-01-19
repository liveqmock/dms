<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单审核</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-ddlb").is(":hidden"))
		{
			$("#tab-ddlb").show();
			$("#tab-ddlb").jTable();
		}
	});
});
//列表审核链接
function doCheck(row)
{
	var w = document.documentElement.clientWidth-100;
	var options = {max:false,width:w,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/ddshmx.jsp", "ddshmx", "订单审核", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 服务站订单审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>订单编号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
						<td><label>订单类型：</label></td>
					    <td>
					    	<select type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#YD=月度订单:LS=临时订单:ZD=周度订单:ZC=总成订单:ZF=直发订单:CX=促销订单:BW=直营订单" datasource="DDLX" datatype="1,is_null,30">
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
				    	<td><label>提报日期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
					<tr>
						<td><label>服务站：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" kind="dic" src="E#1=经销商一:2=经销商二" multi="true" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>订单状态：</label></td>
					    <td>
					    	<select type="text" id="in-ddzt"  name="in-ddzt" datasource="DDZT" kind="dic" src="E#1=已提报:2=驳回再提报" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
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
		<div id="page_ddlb" >
			<table style="display:none;width:100%;" id="tab-ddlb" name="tablist" ref="page_ddlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" ordertype='local' class="desc">订单编号</th>
							<th fieldname="DDLX" >订单类型</th>
							<th fieldname="DDBH" ordertype='local' class="desc">所属单位</th>
							<th fieldname="QWDHRQ" >期望到货日期</th>
							<th fieldname="ZJE" align="right">总金额(元)</th>
							<th fieldname="DDZT" >订单状态</th>
							<th fieldname="TBR" >提报人</th>
							<th fieldname="TBSJ" ordertype='local' class="desc">提报时间</th>
							<th fieldname="TBSJ" maxlength="20">备注</th>
							<th type="link" title="[审核]"  action="doCheck" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>YD20140519002</div></td>
							<td><div>月度订单</div></td>
							<td><div>服务站一</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>1,683.50</div></td>
							<td><div>已提报</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-19 8:30:26</div></td>
							<td><div>无</div></td>
							<td><a href="#" onclick="doCheck()" class="op">[审核]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>YD20140519003</div></td>
							<td><div>月度订单</div></td>
							<td><div>服务站二</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>2,683.50</div></td>
							<td><div>驳回再提报</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-18 8:30:26</div></td>
							<td><div>该订单客户要求非常着急，请优先发运</div></td>
							<td><a href="#" onclick="doCheck()" class="op">[审核]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>LS20140519004</div></td>
							<td><div>临时订单</div></td>
							<td><div>服务站一</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>2,283.50</div></td>
							<td><div>驳回再提报</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-16 8:30:26</div></td>
							<td><div>无</div></td>
							<td><a href="#" onclick="doCheck()" class="op">[审核]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>