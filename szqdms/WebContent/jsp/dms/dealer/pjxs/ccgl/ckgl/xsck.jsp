<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>销售出库</title>
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

//查看订单详细信息
function toXsck(orderNo)
{
	var w = document.documentElement.clientWidth-100;
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/ccgl/ckgl/xsckmx.jsp", "xsckmx", "销售出库明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 出库管理   &gt; 销售出库</h4>
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
				    	<td><label>延期订单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
						<td><label>订单类型：</label></td>
					    <td>
					    	<select type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#YD=月度订单:LS=临时订单:ZD=周度订单:ZC=总成订单:CX=促销订单:BW=保外订单" datasource="DDLX" datatype="1,is_null,30">
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>提报单位：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>提报日期：</label></td>
					    <td>
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
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
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_ddlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" ordertype='local' class="desc" colwidth="120">订单编号</th>
							<th fieldname="DDLX" >订单类型</th>
							<th fieldname="DDLX" >提报单位</th>
							<th fieldname="GHPJK" colwidth="65">是否有延期</th>
							<th fieldname="TBSJ" ordertype='local' class="desc" colwidth="80">提报时间</th>
							<th fieldname="TBSJ" ordertype='local' class="desc" colwidth="80">审核时间</th>
							<th fieldname="ZJE" align="left">应出数量</th>
							<th fieldname="ZJE" align="left" colwidth="55">应发金额</th>
							<th colwidth="80" type="link" title="[销售出库]"  action="toYsrk" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a style="color:red;" onclick="doViewOrderDetail('YD20140519001');">YD20140519001</a></div></td>
							<td><div>月度订单</div></td>
							<td><div>服务站1</div></td>
							<td><div>有</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>50</div></td>
							<td><div align="right">2,500.00</div></td>
							<td><div><a style="color:red;" onclick="toXsck();">[销售出库]</a></div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a style="color:red;" onclick="doViewOrderDetail('YD20140519001');">ZD20140519001</a></div></td>
							<td><div>周度订单</div></td>
							<td><div>服务站2</div></td>
							<td><div>无</div></td>
							<td><div>2014-05-22</div></td>
							<td><div>2014-05-24</div></td>
							<td><div>40</div></td>
							<td><div align="right">800.00</div></td>
							<td><div><a style="color:red;" onclick="toXsck();">[销售出库]</a></div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>