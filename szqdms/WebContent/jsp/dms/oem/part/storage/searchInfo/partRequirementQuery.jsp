<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件需求统计</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 配件需求统计</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>时间范围：</label></td>
									<td>
										<input  type="text" id="APPLY_DATE_B" name="APPLY_DATE_B" style="width: 75px;" datasource="O.APPLY_DATE" datatype="0,is_null,30" 
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'APPLY_DATE_E\')}'})" /> 
	                                    <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                    <input  type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -20px;" datasource="O.APPLY_DATE" datatype="0,is_null,30"
			                                     kind ="date"  operation="<="
			                                     onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'APPLY_DATE_B\')}'})" />
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
									<th fieldname="PART_CODE" colwidth="150px">配件代码</th>
									<th fieldname="PART_NAME" colwidth="180px">配件名称</th>
									<th fieldname="ORDER_COUNT" colwidth="50px">需求量</th>
									<th fieldname="REAL_COUNT" colwidth="50px">实发量</th>
									<th fieldname="RATE" refer="vatShow" colwidth="50px">满足率</th>
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
	
	// 查询
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/PartRequirementQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/PartRequirementQueryAction/exportExcel.do"+"?truncStr="+$("#truncStr").val());
		$("#exportFm").submit();
	})
	
})
</script>