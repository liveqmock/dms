<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>大总成销量统计报表</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 大总成销量统计报表</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<input type="hidden" name="ORG_TYPE" id="ORG_TYPE" datasource="ORG_TYPE" datatype="0,is_null,30" operation="=" value="<%=DicConstant.ZZLB_09%>"/>
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>出库时间：</label></td>
									<td>
										<input type="text" id="APPLY_DATE_B" name="APPLY_DATE_B" style="width: 75px;" datasource="APPLY_DATE_B" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'APPLY_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -30px;" datasource="APPLY_DATE_E" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'APPLY_DATE_B\')}', maxDate: '%y-%M-%d'})" kind ="date"  operation="<="/>
									</td>
									<td><label>总成类别：</label></td>
									<td>
										<select name="ORDER_STATUS" id="BELONG_ASSEMBLY" name="BELONG_ASSEMBLY" datasource="BELONG_ASSEMBLY" kind="dic" src="<%=DicConstant.PJZCLB %>" operation="=" datatype="1,is_null,6">
											<option value="" selected="selected">--</option>
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
								<tr>
									<th fieldname="ORG_CODE" colwidth="80px">单位代码</th>
									<th fieldname="ORG_NAME">单位名称</th>
									<th fieldname="PART_CODE" colwidth="150px">配件代码</th>
									<th fieldname="PART_NAME">配件名称</th>
									<th fieldname="PART_COUNT" colwidth="80px">出库量</th>
									<th fieldname="COUNT_PRICE" refer="amountFormat" align="right">出库金额</th>
									<th fieldname="BELONG_ASSEMBLY">所属总成</th>
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
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/salesMng/search/PartAssemblyQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
		// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/search/PartAssemblyQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>