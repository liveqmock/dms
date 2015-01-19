<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件占用查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 配件占用查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>配件：</label></td>
									<td>
										<input type="text" id="partName" name="partName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showPartInfo({showAllPart: 1})" operation="="/>
										<input type="hidden" id="partId" name="partId" datatype="1,is_null,30" datasource="I.PART_ID" operation="="/>
									</td>
									<td><label>订单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="T1.ORDER_NO" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
<%-- 									<td><label>订单类型：</label></td>
									<td colspan="3">
										<select class="combox" name="ORDER_TYPE" id="ORDER_TYPE" datasource="T1.ORDER_TYPE" kind="dic" src="<%=DicConstant.DDLX%>" operation="=" datatype="1,is_null,100" >
											<option value="-1" selected="selected">--</option>
											<option value="销售订单" >销售订单</option>
											<option value="实销订单" >实销订单</option>
											<option value="退货单" >退货单</option>
										</select>
									</td> --%>
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
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<tr>
									<th fieldname="PART_CODE" colwidth="120px">配件代码</th>
									<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
									<th fieldname="OCCUPY_AMOUNT" colwidth="80px">占用数量</th>
									<th fieldname="ORDER_NO" colwidth="150px">订单号</th>
									<th fieldname="ORDER_TYPE" colwidth="80px">订单类型</th>
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
		<input type="hidden" id="params" name="data"></input>
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/PartOccupyForDealerQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	
	$("#btn-clear").click(function(){
		$("input", "#fm-searchContract").each(function(index, obj){
			$(obj).val("");
		});
	});
})

// 配件选择回调函数
// res = {"PART_ID" : "", "PART_CODE": "", "PART_NAME": ""}
function showPartInfoCallback(res){
	$("#partName").val(res["PART_NAME"]);
	$("#partId").val(res["PART_ID"]);
}

</script>