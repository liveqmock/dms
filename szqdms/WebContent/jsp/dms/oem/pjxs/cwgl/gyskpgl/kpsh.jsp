<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>开票审核</title>
<script type="text/javascript">
//变量定义
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
//列表编辑链接
function doPass(row)
{
	alertMsg.info("审核通过");
}
function doRebut(row)
{
	alertMsg.info("审核驳回，清空供应商填写的发票号。");
}
function doDetail(id)
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/cwgl/gyskpgl/kpshmx.jsp", "kpshmx", "开票审核明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理&gt;供应商管理&gt;开票审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>供应商：</label></td>
					    <td>
					    	<select type="text" id="in-ddzt"  name="in-ddzt" datasource="DDZT" kind="dic" src="E#1=供应商1:2=供应商2:3=供应商3" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>月度：</label></td>
					    <td >
					    	<select type="text" id="in-year"  name="in-year"  kind="dic" src="E#2013=2013:2014=2014:2015=2015" datatype="1,is_null,30" >
					    		<option value="2014" selected>2014</option>
					    	</select>
					    	<select type="text" id="in-month"  name="in-month"  kind="dic" src="E#1=1月:2=2月:3=3月:4=4月:5=5月:6=6月:7=7月:8=8月:9=9月:10=10月:11=11月:12=12月" datatype="1,is_null,30" >
					    		<option value="6" selected>6月</option>
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
							<th fieldname="" colwidth="60">供应商代码</th>
							<th fieldname="" colwidth="60">供应商名称</th>
							<th fieldname="" colwidth="60" align="right">开票金额汇总</th>
							<th fieldname="" colwidth="60">审核意见</th>
							<th colwidth="120" type="link" title="[审核通过]|[审核驳回]"  action="doPass|doRebut" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><div><a href="#" onclick="doDetail()" >14,491.60</a></div></td>
							<td><div><input id="dia-bz1" value="" datatype="1,is_null,50"/></div></td>
							<td><a href="#" onclick="doPass()" class="op">[审核通过]</a><a href="#" onclick="doRebut()" class="op">[审核驳回]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div>供应商代码2</div></td>
							<td><div>供应商名称2</div></td>
							<td><div><a href="#" onclick="doDetail()" >14,491.60</a></div></td>
							<td><div><input id="dia-bz1" value="" datatype="1,is_null,50"/></div></td>
							<td><a href="#" onclick="doPass()" class="op">[审核通过]</a><a href="#" onclick="doRebut()" class="op">[审核驳回]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>