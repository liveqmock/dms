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
<title>配件销售回款统计表</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/part/salesMng/search/PartSalesReturnStaticQueryAction/queryListInfo.ajax";
var exportUrl = "<%=request.getContextPath()%>/part/salesMng/search/PartSalesReturnStaticQueryAction/reportExportExcel.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

$(function()
{
	$("#btn-search").bind("click", function(event){ 
		var $f = $("#fm-DCSafeStockWarnQuery");
		
		//判断上下限的指的合理性下限<上限
		var year = $("#year").val();
		var month = $("#month").val();

		if ((parseInt(month) < 1) || (parseInt(month) > 12)) {
			alert("请输入正确的月份！");
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

//列表编辑连接
function doStatiChart(rowobj)
{  
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var selectedRows = $("#tab-DCSafeStockWarnList").getSelectedRows();
	
	//将表字典里查出的名称回显到修改页面代码对应的名称项中
	var rowdata=selectedRows[0].attr("rowdata");	
	var objXML;
	if (typeof(rowdata) == "object") objXML = rowdata;
	else objXML = $.parseXML(rowdata);
	
	//获取统计主键
	var statisticId = getNodeValue(objXML, "STATISTIC_ID", 0);
	
	$.pdialog.open(webApps+"/jsp/dms/oem/part/sales/query/partSalesReturnStaticChart.jsp?statisticId="+statisticId, "查看统计图属性", "查看统计图属性", diaAddOptions,true);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配件销售回款统计表</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-DCSafeStockWarnQuery">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-queryDCSafeStockWarn">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>年份：</label></td>
						<td>
							<input type="text" id="year" name="year" datasource="YEAR" datatype="0,is_null,30" operation="like"/>
						</td>
						<td><label>月份：</label></td>
						<td>
							<input type="text" id="month" name="month" datasource="MONTH" datatype="0,is_null,30" operation="like"/>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
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
							<th fieldname="BSC_NAME">办事处</th>								
							<th fieldname="DC_CODE">渠道代码</th>	
							<th fieldname="DC_NAME">渠道名称</th>
							<th fieldname="RETURN_ACCOUT_14">回款额</th>
							<th fieldname="RETURN_ACCOUT_YEAR_GROWTH">同比</th>			
							<th fieldname="SALES_ACCOUNT_14">销售金额</th>			
							<th fieldname="SALES_ACCOUNT_YEAR_GROWTH">同比</th>			
							<th fieldname="OIL_ACCOUT">油品</th>		
							<th fieldname="OUT_LX_ACCOUT">保外滤芯</th>
							<th fieldname="OUT_LHQ_ACCOUT">保外离合器</th>
							<th fieldname="CQJ_ACCOUT">车桥件</th>
							<th fieldname="QDZC_ACCOUT">七大总成</th>
							<th fieldname="OTHER_OUT_ACCOUT">其他保外产品</th>
							<th fieldname="OTHER_ACCOUT">其他</th>
<!--							<th colwidth="85" type="link" title="[统计图]"  action="doStatiChart" >操作</th>-->
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
