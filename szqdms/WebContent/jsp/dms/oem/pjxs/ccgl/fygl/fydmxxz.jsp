<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>订单编号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
		<div class="pageContent" >
			<div id="dia-zxlb" style="height:280px;overflow:auto;">
				<table style="width:100%;" id="tab-zxlb" name="tablist"  ref="dia-zxlb" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" name="XH"></th>
						<th type="multi" name="CX-XH"></th>
						<th fieldname="PJMC" colwidth="150" freadonly="true">订单编号</th>
						<th fieldname="PJMC" freadonly="true">订单类型</th>
						<th fieldname="JLDWMC" freadonly="true">提报单位</th>
						<th fieldname="JLDWMC" freadonly="true">配件数量</th>
						<th fieldname="JLDWMC" freadonly="true">配件金额</th>
						<th fieldname="JLDWMC" freadonly="true">收货地址</th>
						<th fieldname="KW" freadonly="true">联系人</th>
						<th fieldname="ZXBZ" freadonly="true">联系方式</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="rownums"><div>1</div></td>
						<td><div><input type="checkbox"/></div></td>
						<td><div>YD20140519001</div></td>
						<td><div>月度订单</div></td>
						<td><div>河南配送中心</div></td>
						<td><div>80</div></td>
						<td><div align="right">15,000.00</div></td>
						<td><div>河南省洛阳市</div></td>
						<td><div>王五</div></td>
						<td><div>029-85855566</div></td>
					</tr>
					<tr >
						<td class="rownums"><div>2</div></td>
						<td><div><input type="checkbox"/></div></td>
						<td><div>ZD20140519001</div></td>
						<td><div>周度订单</div></td>
						<td><div>河南配送中心</div></td>
						<td><div>21</div></td>
						<td><div align="right">500.00</div></td>
						<td><div>河南省洛阳市</div></td>
						<td><div>王五</div></td>
						<td><div>029-85855566</div></td>
					</tr>
					<tr >
						<td class="rownums"><div>3</div></td>
						<td><div><input type="checkbox"/></div></td>
						<td><div>ZD20140519002</div></td>
						<td><div>周度订单</div></td>
						<td><div>河南配送中心</div></td>
						<td><div>25</div></td>
						<td><div align="right">725.00</div></td>
						<td><div>河南省洛阳市</div></td>
						<td><div>王五</div></td>
						<td><div>029-85855566</div></td>
					</tr>
				</tbody>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-mxsave">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
$(function()
{
	$("dia-btn-cx").bind("click",function(){
		$("#tab-zxlb").show();
		$("#tab-zxlb").jTable();
	});
	$("#tab-zxlb").show();
	$("#tab-zxlb").jTable();
	$("#dia-close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-mxsave").bind("click",function(){
    	alertMsg.info("保存成功.");
    	return false;
	});
	//设置高度
	$("#ddjbxx").height(document.documentElement.clientHeight-100);
});
</script>