<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>合同签订</title>
<script type="text/javascript">
//变量定义
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-htlb").is(":hidden"))
		{
			$("#tab-htlb").show();
			$("#tab-htlb").jTable();
		}
	});
});
function doDetail(id)
{
	var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/cggl/htgl/htmx.jsp", "htmx", "合同明细信息", options);
}
function doUpdate(row)
{
	alertMsg.info("合同签订完成。");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购合同管理   &gt; 合同签订</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-htcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>合同编号：</label></td>
					    <td><input type="text" id="in-htbh" name="in-htbh" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>厂家名称：</label></td>
					    <td ><input type="text" id="in-cjmc" name="in-cjmc" datatype="1,is_null,60" operation="like" /></td>
					    <td><label>创建日期：</label></td>
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
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_lb" >
			<table style="display:none;width:100%;" id="tab-htlb" name="tablist" ref="page_htlb" refQuery="fm-htcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="" >合同编号</th>
							<th fieldname="">厂家名称</th>
							<th fieldname="" >厂家编码</th>
							<th fieldname="" >厂家资质</th>
							<th fieldname="" >企业法人</th>
							<th fieldname="" colwidth="80">法人联系方式</th>
							<th fieldname="" >业务联系人</th>
							<th fieldname="" colwidth="80">业务联系方式</th>
							<th fieldname="" colwidth="80">质保金金额</th>
							<th fieldname="" >质保期</th>
							<th fieldname="" colwidth="120">挂账结算周期（天数）</th>
							<th fieldname="" colwidth="80">厂家供货周期</th>
							<th fieldname="" >税率</th>
							<th fieldname="" colwidth="80">追偿条款</th>
							<th fieldname="" >备注</th>
							<th colwidth="40" type="link" title="[签订]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a href="#" onclick="doDetail()">合同编号1</a></div></td>
							<td><div>厂家名称1</div></td>
							<td><div>厂家编码1</div></td>
							<td><div>税号1</div></td>
							<td><div>企业法人1</div></td>
							<td><div>法人联系方式1</div></td>
							<td><div>业务联系人1</div></td>
							<td><div>业务联系方式1</div></td>
							<td><div>1,683.50</div></td>
							<td><div>1年</div></td>
							<td><div>60</div></td>
							<td><div>60</div></td>
							<td><div>1.17</div></td>
							<td><div>无</div></td>
							<td><div>备注</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[签订]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><a href="#" onclick="doDetail()">合同编号2</a></td>
							<td><div>厂家名称2</div></td>
							<td><div>厂家编码2</div></td>
							<td><div>税号2</div></td>
							<td><div>企业法人2</div></td>
							<td><div>法人联系方式2</div></td>
							<td><div>业务联系人2</div></td>
							<td><div>业务联系方式2</div></td>
							<td><div>1,683.50</div></td>
							<td><div>1年</div></td>
							<td><div>90</div></td>
							<td><div>60</div></td>
							<td><div>1.17</div></td>
							<td><div>无</div></td>
							<td><div>备注2</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[签订]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>