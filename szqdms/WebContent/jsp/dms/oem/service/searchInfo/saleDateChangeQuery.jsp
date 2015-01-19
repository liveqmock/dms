<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>销售日期更改查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 售后管理  &gt; 信息查询   &gt; 销售日期更改查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>VIN：</label></td>
									<td><input type="text" id="vin" name="vin" datasource="VIN" operation="like" datatype="1,is_null,17" /></td>
									<td><label>车型：</label></td>
									<td><input type="text" id="modelsCode" name="modelsCode" datasource="MODELS_CODE" operation="like" datatype="1,is_null,30" /></td>
								</tr>
								<tr>
									<td><label>审批状态：</label></td>
									<td>
				        				<select id="status"  name="status" datasource="CHECK_STATUS" kind="dic" src="CLXSRQZT" datatype="1,is_null,6" >
				        					<option value="-1">--</option>
							    			<option value="<%=DicConstant.CLXSRQZT_01 %>" selected>未审批</option>
							    			<option value="<%=DicConstant.CLXSRQZT_02 %>" >审批通过</option>
							    			<option value="<%=DicConstant.CLXSRQZT_03 %>" >审批驳回</option>
						    			</select>
				        			</td>
				        			<td><label>原销售日期是否为空：</label></td>
				        			<td>
				        				<select id="SF"  name="SF" datasource="SF" kind="dic" src="SF" datatype="1,is_null,6" action="show" >
				        					<option value="-1">--</option>
						    			</select>
				        			</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
			</div>
			<div class="pageContent">
				<div id="page_contract" >
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="VIN" >VIN</th>
								<th fieldname="MODELS_CODE" >车型代码</th>
								<th fieldname="SALE_STATUS">车辆状态</th>
								<th fieldname="OLD_SDATE">原销售日期</th>
								<th fieldname="NEW_SDATE">申请销售日期</th>
								<th fieldname="APPLY_REASON" colwidth="150px">申请原因</th>
								<th fieldname="APPLY_COMPANY">申请单位</th>
								<th fieldname="APPLY_DATE">申请时间</th>
								<th fieldname="CHECK_REMARKS" maxlength="4">审批结果</th>
								<th fieldname="CHECK_USER">审批人</th>
								<th fieldname="CHECK_DATE">审批时间</th>
								<th fieldname="CHECK_STATUS">审批状态</th>
								<th colwidth="40" type="link" title="[查看]"  action="doCheck" >操作</th>
							</thead>
							<tbody>
		                    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
 	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/service/searchInfo/SaleDateChangeQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	
		// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/searchInfo/SaleDateChangeQueryAction/exportExcel.do");
		$("#exportFm").submit();
	});
	
	
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

//查看审批
function doCheck(rowobj)
{
	var diaCheckOptions = {max:false,width:750,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/searchInfo/saleDateChangeQueryDtl.jsp", "check", "查看", diaCheckOptions);
}
</script>