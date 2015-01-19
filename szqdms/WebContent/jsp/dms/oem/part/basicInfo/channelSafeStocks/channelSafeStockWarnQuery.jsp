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
<title>配送中心库存预警明细查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配送中心库存预警明细查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-DCSafeStockWarnQuery">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-queryDCSafeStockWarn">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道商名称：</label></td>
					    <td>
					    	<input type="text" id="dia-orgName"  datatype="1,is_null,1000000000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,1)" operation="=" />
					    	<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,1000000000" datasource="S.ORG_ID" operation="in"/>
					    </td>
					    <td><label>配件代码：</label></td>
					    <td>
					    	<input type="text" id="partCode"  name="partCode" datasource="S.PART_CODE"  datatype="1,is_null,1000" operation="like" />
					    </td>
					    <td><label>配件名称：</label></td>
					    <td>
					    	<input type="text" id="partName" name="partName" datasource="S.PART_NAME" datatype="1,is_null,1000" operation="like" />
					    </td>
					</tr>
					<tr>
					    <td><label>库存区间：</label></td>
					    <td colspan="3">
					    	<input type="text" id="amount1" name="amount1" datasource="S.AMOUNT" datatype="1,is_digit,10000" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="amount2" name="amount2" datasource="S.AMOUNT" datatype="1,is_digit,10000" operation="<=" style="width:60px;"/>
					    </td>
					    <td><label>下限区间：</label></td>
					    <td colspan=3>
					    	<input type="text" id="lowerLimit1" name="lowerLimit1" datasource="S.LOWER_LIMIT" datatype="1,is_digit,10000" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="lowerLimit2" name="lowerLimit2" datasource="S.LOWER_LIMIT" datatype="1,is_digit,10000" operation="<=" style="width:60px;"/>
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重&nbsp;&nbsp;置</button></div></div></li>
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
							<th fieldname="ORG_CODE" colwidth="90px">配送中心代码</th>								
							<th fieldname="ORG_NAME" >配送中心名称</th>	
							<th fieldname="PART_CODE" colwidth="120px">配件编号</th>
							<th fieldname="PART_NAME" colwidth="120px">配件名称</th>
							<th fieldname="AMOUNT" colwidth="80px">库存数量</th>			
							<th fieldname="LOWER_LIMIT" colwidth="80px">下限数量</th>			
							<th fieldname="DELIVERY_COUNT" colwidth="80px">在途数量</th>			
							<th fieldname="DIFF_AMOUNT" colwidth="80px">差异数量</th>		
							<th fieldname="DIFF_RATE" colwidth="80px">达标率(%)</th>
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
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/ChannelSafeStockAction/DCSafeStockWarnQuery.ajax";
var exportUrl = "<%=request.getContextPath()%>/part/basicInfoMng/ChannelSafeStockAction/reportExportExcel.ajax";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-DCSafeStockWarnQuery");
		
		//判断上下限的指的合理性下限<上限
		var lowerLimit1 = $("#lowerLimit1").val();
		var lowerLimit2 = $("#lowerLimit2").val();
		var amount1 = $("#amount1").val();
		var amount2 = $("#amount2").val();
		if (parseInt(lowerLimit1) >= parseInt(lowerLimit2)) {
			alert("下限区间值第一个值必须小于第二个值！");
			return false;
		}
		if (parseInt(amount1) >= parseInt(amount2)) {
			alert("库存区间值第一个值必须小于第二个值！");
			return false;
		}
		
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-DCSafeStockWarnList");
	});
	
	$("#btn-export").click(function(){alert(0);
		var $f = $("#fm-DCSafeStockWarnQuery");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action",exportUrl);
		$("#exportFm").submit();
	})
	
	// 重置
	$("#btn-clear").click(function(){
		$("input", "#tab-queryDCSafeStockWarn").each(function(index, obj){
			$(obj).val("");
		})
	})
});

// 组织树的回调函数
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#dia-orgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#orgId").val($(res).attr("orgId"));
}
</script>