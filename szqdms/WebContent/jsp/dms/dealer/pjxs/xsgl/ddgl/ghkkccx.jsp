<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供货库库存查询</title>
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
	//新增方法
	$("#btn-xz").bind("click",function(event){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/ddwh.jsp?action=1", "ddxxwh", "订单信息新增", options);
	});
});
//列表编辑链接
function doUpdate(row)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/ddwh.jsp?action=2", "ddxxwh", "订单信息维护", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 供货库库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>供货配件库：</label></td>
					    <td>
					    	<select type="text" id="in-ghpjk"  name="in-ghpjk" datasource="GHPJK" kind="dic" src="E#1=配送中心一:2=配送中心二:3=陕重汽配件公司" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>配件图号：</label></td>
					    <td><input type="text" id="dia-in-cx-pjth" name="dia-in-cx-pjth" datasource="PJBM" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-in-cx-pjmc" name="dia-in-cx-pjmc" datasource="PJMC" datatype="1,is_digit_letter,30" operation="like" /></td>
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
							<th fieldname="GHKMC" ordertype='local' class="desc">供货库名称</th>
							<th fieldname="PJBM" >配件图号</th>
							<th fieldname="PJMC" >配件名称</th>
							<th fieldname="JLDW" >计量单位</th>
							<th fieldname="ZXBZS" >最小包装数</th>
							<th fieldname="ZXBZDW" >最小包装单位</th>
							<th fieldname="JXSJ" align="right">经销商价</th>
							<th fieldname="KC" style="color:red;font-weight:bold;">库存</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>配送中心一</div></td>
							<td><div>PJ001</div></td>
							<td><div>配件001</div></td>
							<td><div>件</div></td>
							<td><div>10</div></td>
							<td><div>包</div></td>
							<td><div>10.00</div></td>
							<td><div>有</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>配送中心二</div></td>
							<td><div>PJ001</div></td>
							<td><div>配件001</div></td>
							<td><div>件</div></td>
							<td><div>10</div></td>
							<td><div>包</div></td>
							<td><div>10.00</div></td>
							<td><div>无</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>配送中心一</div></td>
							<td><div>PJ002</div></td>
							<td><div>配件002</div></td>
							<td><div>件</div></td>
							<td><div>10</div></td>
							<td><div>包</div></td>
							<td><div>10.00</div></td>
							<td><div>有</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>