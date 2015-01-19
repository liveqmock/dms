<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配送中心返利查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 财务相关&gt; 配送中心返利统计</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>季度：</label></td>
					    <td>
                            <input type="text" id="diaExpectDate" name="diaExpectDate" datasource="JD" datatype="1,is_null,30" class="Wdate" onclick="WdatePicker({dateFmt:'yyyyMM', isShowOK:false,disabledDates:['....-0[5-9]-..','....-1[0-2]-..'], startDate:'%y-01-01' })" />
                        </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重置查询条件</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ONAME">办事处</th>
							<th fieldname="DEPTCODE">渠道代码</th>
							<th fieldname="DEPTNAME">渠道名称</th>
							<th fieldname="ZZFL" refer="formatAmount" align="right" colwidth="100">采购返利总额(元)</th>
							<th fieldname="RATE" colwidth="60">同比(%)</th>
							<th fieldname="YPCGJE" refer="formatAmount" align="right" colwidth="90">油品(元)</th>
							<th fieldname="ILCGJE" refer="formatAmount" align="right" colwidth="90">保外滤芯(元)</th>
							<th fieldname="JLCGJE" refer="formatAmount" align="right" colwidth="90">保外离合器(元)</th>
							<th fieldname="HLCGJE" refer="formatAmount" align="right" colwidth="90">车桥件(元)</th>
							<th fieldname="DZCJE" refer="formatAmount" align="right" colwidth="90">七大总成(元)</th>
							<th fieldname="QTBYJE" refer="formatAmount" align="right" colwidth="100">其他保外产品(元)</th>
							<th fieldname="GLFJE" refer="formatAmount" align="right" colwidth="90">其他(元)</th>
							<th fieldname="BYXSFLZE" refer="formatAmount" align="right" colwidth="135">保外销售返利总额(元)</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
      <input type="hidden" id="data" name="data"></input>
   </form>
</div>
</body>
</html>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/searchInfo/DealerRebaseSearchMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/rebateSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	
    	// 后台没有使用自动拼接方法，如果如果添加查询条件，请也在后台添加拼接SQL的逻辑
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	});
	// 重置
	$("#btn-clear").click(function(){
		$("input","#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
});
function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
</script>