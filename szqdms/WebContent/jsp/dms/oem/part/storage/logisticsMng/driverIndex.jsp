<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:司机信息管理
	 Version:1.0
     Author：suoxiuli 2014-08-21
     Remark：driver
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>司机信息管理</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/DriverAction/search.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchDriver");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-driverList");
	});
	
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/logisticsMng/driverAdd.jsp?action=1", "addDriver", "新增司机信息", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/storage/logisticsMng/driverAdd.jsp?action=2", "updateDriver", "修改司机信息", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	
	<div id="progressBar1" class="progressBar">loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 仓储管理   &gt; 物流信息管理   &gt; 司机信息管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchDriver">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchDriver">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>承运商名称：</label></td>
					    <td>
					    	<input type="text" id="carrierCode"  name="carrierCode" kind="dic" 
					    		src="T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID}:1=1 AND STATUS='100201' " 
					    		datasource="CARRIER_CODE" datatype="1,is_null,30" />
					    	<input type="hidden" id="carrierName"  name="carrierName" datasource="CARRIER_NAME" />
					    	<input type="hidden" id="carrierId"  name="carrierId" datasource="CARRIER_ID" />
					    </td>
				    	<td><label>司机姓名：</label></td>
					    <td>
					    	<input type="text" id="driverName"  name="driverName" datasource="DRIVER_NAME"  datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					<tr>
						<td><label>性　　　别：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="XB" datasource="SEX" datatype="1,is_null,6" operation="=" >
					    	<option value="-1">---</option>
					    	</select>
					    </td>
					    <td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_driverList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-driverList" name="tablist" ref="page_driverList" refQuery="tab-searchDriver" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CARRIER_CODE" >承运商代码</th>
							<th fieldname="CARRIER_NAME" >承运商名称</th>
							<th fieldname="DRIVER_NAME" >司机姓名</th>
							<th fieldname="SEX" >性别</th>
							<th fieldname="DRIVER_NO" >身份证号</th>
							<th fieldname="PHONE" >手机号码</th>
							<th fieldname="FIXED_LINE" >固定电话</th>
							<th fieldname="EMAIL" >邮箱</th>
							<th fieldname="STATUS" >有效标识</th>
							<th fieldname="CREATE_USER" >添加人</th>
							<th fieldname="CREATE_TIME" >添加时间</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>	
</body>

<script type="text/javascript">

function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("carrierCode") == 0)
	{  
		$("#carrierName").val($("#"+id).val());
		
		if($row.attr("CARRIER_ID")){
			$("#carrierId").val($row.attr("CARRIER_ID"));
		}
		return true;
	}
	
	if(id.indexOf("dia-carrierCode") == 0)
	{  
		$("#dia-carrierName").val($("#"+id).val());
		
		if($row.attr("CARRIER_ID")){
			$("#dia-carrierId").val($row.attr("CARRIER_ID"));
		}
		return true;
	}

	return true;
}

</script>

</html>