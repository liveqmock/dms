<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>民车销售日期更改审核</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">

var diaSearchAction = "<%=request.getContextPath()%>/service/serviceparamng/VehicleSaleDateMngAction";

//定义弹出窗口样式
var diaCheckOptions = {max:false,width:750,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var diaUpdateOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchCivilVehicleSaleDateSetCheck");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchUrl = diaSearchAction + "/searchCheck.ajax?userType="+'<%=DicConstant.CLYHLX_01%>';
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-civilVehicleSaleDateSetCheckList");
	});
	
	//车厂端修改日期按钮响应
	$("#btn-oemUpdate").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/serviceparamng/civilvehiclesaledate/civilVehicleSaleDateIndex.jsp", "oemUpdate", "民车销售日期更改", diaUpdateOptions);
	});
});

//查看审批
function doCheck(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/serviceparamng/civilvehiclesaledate/civilVehicleSaleDateSetCheck.jsp", "check", "民车销售日期更改审批", diaCheckOptions);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：索赔管理&gt;索赔管理&gt;销售日期更改申请</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchCivilVehicleSaleDateSetCheck" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchCivilVehicleSaleDateSet">
						<tr>
							<td><label>VIN：</label></td>
							<td><input type="text" id="vin" name="vin" datasource="VIN" operation="like" datatype="1,is_null,17" /></td>
							<td><label>车型：</label></td>
							<td><input type="text" id="modelsCode" name="modelsCode" datasource="MODELS_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>审批状态：</label></td>
							<td>
		        				<select id="status"  name="status" datasource="CHECK_STATUS" kind="dic" src="CLXSRQZT" datatype="1,is_null,6" >
				    			<option value="<%=DicConstant.CLXSRQZT_01 %>" selected>未审批</option>
				    			<option value="<%=DicConstant.CLXSRQZT_02 %>" >审批通过</option>
				    			<option value="<%=DicConstant.CLXSRQZT_03 %>" >审批驳回</option>
				    			<option value="-1">--</option>
				    			</select>
		        			</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-oemUpdate">修改日期</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_civilVehicleSaleDateSetCheckList">
				<table style="display:none;width:100%;" id="tab-civilVehicleSaleDateSetCheckList" name="tablist" ref="page_civilVehicleSaleDateSetCheckList" refQuery="tab-searchCivilVehicleSaleDateSet">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="MODELS_CODE" >车型代码</th>
							<th fieldname="SALE_STATUS">车辆状态</th>
							<th fieldname="OLD_SDATE">原销售日期</th>
							<th fieldname="NEW_SDATE">申请销售日期</th>
							<th fieldname="APPLY_REASON" maxlength="4">申请原因</th>
							<th fieldname="APPLY_COMPANY">申请单位</th>
							<th fieldname="APPLY_DATE">申请时间</th>
							<th fieldname="CHECK_REMARKS" maxlength="4">审批结果</th>
							<th fieldname="CHECK_USER">审批人</th>
							<th fieldname="CHECK_DATE">审批时间</th>
							<th fieldname="CHECK_STATUS">审批状态</th>
							<th colwidth="40" type="link" title="[查看]"  action="doCheck" >操作</th>
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
</html>