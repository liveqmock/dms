<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
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
					    
					</tr>
					<tr>
						<td><label>订单类型：</label></td>
					    <td>
					    	<select type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#YD=月度订单:LS=临时订单:ZD=周度订单:ZC=总成订单:ZF=直发订单:CX=促销订单:BW=保外订单" datasource="DDLX" datatype="1,is_null,30">
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
						<td><label>提报单位：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    </tr>
				    <tr>
					    <td><label>审核员：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>审核日期：</label></td>
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
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
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
							<th fieldname="DDBH" ordertype='local' class="desc">订单编号</th>
							<th fieldname="DDLX" >订单类型</th>
							<th fieldname="SHY" >提报单位</th>
							<th fieldname="SHY" >审核员</th>
							<th fieldname="SHY" >审核日期</th>
							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>YD20140519001</div></td>
							<td><div>月度订单</div></td>
							<td><div>河南配送中心</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-19 8:30:26</div></td>
							<td><div><a href="javascript:void(0);" title="操作" class="btnSelect" onclick="doOk(this.parentElement.parentElement.parentElement)">操作</a></div></td>
						</tr>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>LS20140519001</div></td>
							<td><div>临时订单</div></td>
							<td><div>西安配送中心</div></td>
							<td><div>张三</div></td>
							<td><div>2014-05-19 8:30:26</div></td>
							<td><div><a href="javascript:void(0);" title="操作" class="btnSelect" onclick="doOk(this.parentElement.parentElement.parentElement)">操作</a></div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	$("#tab-pjlb").show();
	$("#tab-pjlb").jTable();
});
function doOk(rowobj){
	var lookupId = $.pdialog._current.data("id");
	try
	{
		ddCallBack(rowobj);	
	}catch(e){}
	$.pdialog.closeCurrent();
}
</script>