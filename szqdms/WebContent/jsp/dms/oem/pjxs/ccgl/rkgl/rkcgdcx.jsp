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
						<td><label>采购拆分单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>采购订单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    
					</tr>
					<tr>
						<td><label>供应商：</label></td>
					    <td>
					    	<input type="text" id="in-ddlx" name="in-ddlx" kind="dic" src="E#GYS1=供应商一:GYS2=供应商二:GYS3=供应商三" datasource="DDLX" datatype="1,is_null,30"/>
					    </td>
						<td><label>采购员：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
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
							<th fieldname="DDBH" colwidth="130" ordertype='local' class="desc">采购拆分单号</th>
							<th fieldname="DDBH" colwidth="120" ordertype='local' class="desc">采购订单号</th>
							<th fieldname="DDLX">采购类型</th>
							<th fieldname="DDLX">采购类别</th>
							<th fieldname="DDLX">供应商</th>
							<th fieldname="SHY">采购员</th>
							<th fieldname="SHY">采购数量</th>
							<th fieldname="SHY" colwidth="60">采购金额</th>
							<th fieldname="SHY" colwidth="60">计划金额</th>
							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>CG2014051900101</div></td>
							<td><div>CG20140519001</div></td>
							<td><div>缺件</div></td>
							<td><div>采购</div></td>
							<td><div>供应商一</div></td>
							<td><div>张三</div></td>
							<td><div>50</div></td>
							<td><div align="right">5,000.00</div></td>
							<td><div align="right">5,500.00</div></td>
							<td><div><a href="javascript:void(0);" title="操作" class="btnSelect" onclick="doOk(this.parentElement.parentElement.parentElement)">操作</a></div></td>
						</tr>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>CG2014051900102</div></td>
							<td><div>CG20140519001</div></td>
							<td><div>其他</div></td>
							<td><div>领用</div></td>
							<td><div>通汇</div></td>
							<td><div>张三</div></td>
							<td><div>30</div></td>
							<td><div align="right">3,000.00</div></td>
							<td><div align="right">3,200.00</div></td>
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