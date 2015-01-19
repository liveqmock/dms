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
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="T1.PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="T1.PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>订单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="ORD.ORDER_NO" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
									<td><label>订单状态：</label></td>
									<td>
										<select class="combox" name="ORDER_STATUS" id="ORDER_STATUS" datasource="ORD.ORDER_STATUS" kind="dic" src="<%=DicConstant.DDZT%>" operation="=" datatype="1,is_null,6" 
											filtercode="<%=DicConstant.DDZT_02%>|<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_04%>|<%=DicConstant.DDZT_05%>|<%=DicConstant.DDZT_06%>|<%=DicConstant.DDZT_07%>"
										>
											<option value="-1" selected="selected">--</option>
										</select>	
									</td>
								</tr>
								<tr>
									<td><label>订单类型：</label></td>
									<td colspan="3">
										<select class="combox" name="ORDER_TYPE" id="ORDER_TYPE" datasource="ORD.ORDER_TYPE" kind="dic" src="<%=DicConstant.DDLX%>" operation="=" datatype="1,is_null,6" >
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
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
									<th fieldname="POSITION_NAME">所属库位</th>
									<th fieldname="AREA_NAME">所属库区</th>
									<th fieldname="ORDER_NO" colwidth="150px">订单号</th>
									<th fieldname="ORDER_STATUS" colwidth="80px">订单状态</th>
									<th fieldname="ORDER_TYPE" colwidth="80px">订单类型</th>
									<th fieldname="APPLY_DATE" colwidth="130px">申请日期</th>
									<th fieldname="ORG_CODE" colwidth="80px">申请单位代码</th>
									<th fieldname="ORG_NAME" >申请单位名称</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/PartOccupyQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
})
</script>