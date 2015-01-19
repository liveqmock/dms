<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>里程调整审核</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Spdgl/SpdglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-spdlb").is(":hidden"))
		{
			$("#tab-spdlb").show();
			$("#tab-spdlb").jTable();
		}
	});
});
//列表编辑链接(明细)
function doDetail(claimId)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/common/comSpdwxxx.jsp?action=1", "spdxxmx", "索赔单信息", options,true);
}
//列表编辑链接(明细)
function doSq(row)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/spgl/spgl/lctzshmx.jsp", "lctzshmx", "里程调整审核", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 里程调整审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-sbdcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-sbdcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>服务站代码：</label></td>
					    <td><input type="text" id="in-fwzdm" name="in-fwzdm" datasource="DEALER_CODE" datatype="1,is_null,30" operation="like" hasBtn="true" callFunction="selectDealer()"/></td>
					    <td><label>服务站名称：</label></td>
					    <td><input type="text" id="in-fwzmc" name="in-fwzmc" datasource="DEALER_NAME" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>索赔单号：</label></td>
					    <td><input type="text" id="in-spdh" name="in-spdh" datasource="CLAIM_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>申请日期：</label></td>
					    <td>
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="REPORT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="REPORT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
				   		 <td ><label>VIN：</label></td> 
					    <td colspan="3"> <textarea  cols="18" name="in-vin" id="in-vin" rows="3" ></textarea></td> 
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
		<div id="page_spdlb" >
			<table style="display:none;width:100%;" id="tab-spdlb" name="tablist" ref="page_spdlb" refQuery="fm-sbdcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="" >申请时间</th>
							<th fieldname="" >原里程</th>
							<th fieldname="" >新里程数</th>
							<th fieldname="" >申请原因</th>
							<th colwidth="105" type="link" title="[申请]"  action="doSq" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a href="###" onclick="doDetail();">索赔单号01</a></div></td>
							<td><div>VIN1</div></td>
							<td><div>2014-05-21</div></td>
							<td><div>3000</div></td>
							<td><div>5000</div></td>
							<td><div>因....</div></td>
							<td><a href="#" onclick="doSq()" class="op">[审核]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a href="###" onclick="doDetail();">索赔单号02</a></div></td>
							<td><div>VIN2</div></td>
							<td><div>2014-05-21</div></td>
							<td><div>30001</div></td>
							<td><div>50001</div></td>
							<td><div>因....</div></td>
							<td><a href="#" onclick="doSq()" class="op">[审核]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div><a href="###" onclick="doDetail();">索赔单号03</a></div></td>
							<td><div>VIN3</div></td>
							<td><div>2014-05-21</div></td>
							<td><div>30002</div></td>
							<td><div>50002</div></td>
							<td><div>因....</div></td>
							<td><a href="#" onclick="doSq()" class="op">[审核]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>