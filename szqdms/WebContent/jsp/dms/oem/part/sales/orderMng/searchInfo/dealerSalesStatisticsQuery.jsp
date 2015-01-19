<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String account = user.getAccount();
	String name = user.getPersonName();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />

<title>配件经销商销量统计查询</title>
<script type="text/javascript">                
var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/dealerSalesStatisticsQuery.ajax";
var exportUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/dealerSalesStatiReportExportExcel.ajax";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-DCSafeStockWarnQuery");
		
		//判断上下限的指的合理性下限<上限
		var closeDate1 = $("#closeDate1").val();
		var closeDate2 = $("#closeDate2").val();
		if (!closeDate1 || !closeDate2) {
			alert("时间区间值不能为空！");
			return false;
		}
		if (closeDate1 >= closeDate2) {
			alert("时间区间值第一个值必须小于第二个值！");
			return false;
		}

		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-DCSafeStockWarnList");
	});
	
	$("#btn-export").click(function(){
		var $f = $("#fm-DCSafeStockWarnQuery");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action",exportUrl);
		$("#exportFm").submit();
	})
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配件经销商销量统计查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-DCSafeStockWarnQuery">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-queryDCSafeStockWarn">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>经销商名称：</label></td>
					    <td>
					    	<input type="text" id="dealerCode"  name="dealerCode" kind="dic" 
					    		src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND ORG_TYPE='200006' AND STATUS='100201'" 
					    		datasource="T1.CODE" datatype="1,is_null,100" />
					    	<input type="hidden" id="dealerName"  name="dealerName" datasource="T1.ONAME"  />
					    </td>
					    <td><label>上级配送中心：</label></td>
					    <td>
					    	<input type="text" id="dcCode"  name="dcCode" kind="dic" 
					    		src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND ORG_TYPE='200005' AND STATUS='100201'" 
					    		datasource="A.DC_CODE" datatype="1,is_null,100" />
					    	<input type="hidden" id="dcName"  name="dcName" datasource="T2.ONAME"  />
					    </td>
					    <td><label>办事处名称：</label></td>
					    <td>
					    	<input type="text" id="officeCode"  name="officeCode" kind="dic" 
					    		src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND ORG_TYPE='200006' AND STATUS='100201'" 
					    		datasource="T3.CODE" datatype="1,is_null,100" />
					    	<input type="hidden" id="officeName"  name="officeName" datasource="T3.ONAME"  />
					    </td>
					</tr>
					<tr>
					    <td><label>时间区间：</label></td>
					    <td>
					    	<input type="text" id="closeDate1" name="closeDate1" dataSource="B.CLOSE_DATE1" datatype="0,is_date,30" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="closeDate2" name="closeDate2" datasource="B.CLOSE_DATE2" datatype="0, is_date,10" 
								style="width:75px;"  kind="date"  onclick="WdatePicker()" />
							<input type="hidden" id="startDate"  name="startDate" datasource="START_DATE"  />
							<input type="hidden" id="endDate"  name="endDate" datasource="END_DATE"  />
					    </td>
					    <td><label>经销商采购总金额：</label></td>
					    <td colspan=3>
					    	<input type="text" id="sumAmount1" name="sumAmount1" datasource="C.SUM_AMOUNT" datatype="1,is_double,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="sumAmount2" name="sumAmount2" datasource="C.SUM_AMOUNT" datatype="1,is_double,10" operation="<=" style="width:60px;"/>
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
		<div id="page_DCSafeStockWarnList" >
			<table style="display:none;width:100%;" id="tab-DCSafeStockWarnList" name="tablist" ref="page_DCSafeStockWarnList" refQuery="tab-queryDCSafeStockWarn" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="DEALER_CODE" >经销商代码</th>								
							<th fieldname="DEALER_NAME" >经销商名称</th>	
							<th fieldname="DC_NAME" >上级配送中心</th>
							<th fieldname="OFFICE_NAME" >办事处</th>
							<th fieldname="SUM_REAL_AMOUNT" align="right">经销商采购总金额</th>			
							<th fieldname="DATE_PIREIOD" >时间段</th>			
						</tr>
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