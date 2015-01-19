<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>渠道库存金额统计</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 渠道库存金额统计</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>渠道商名称：</label></td>
								    <td>
										<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,'',2)" operation="="/>
										<input type="hidden" id="ORG_CODE" name="ORG_CODE" datatype="1,is_null,30" datasource="O1.CODE" operation="="/>
									</td>
									<td><label>渠道类别：</label></td>
									<td>
										<select name="ORG_TYPE" id="ORG_TYPE" datatype="0,is_null,1000" operation="="  datasource="O1.ORG_TYPE">
											<option value="-1">--</option>
											<option value="<%=DicConstant.ZZLB_09%>">配送中心</option>
											<option value="<%=DicConstant.ZZLB_10%>">配件经销商</option>
											<option value="<%=DicConstant.ZZLB_11%>">服务商</option>
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
									<th fieldname="ORG_CODE">渠道代码</th>
									<th fieldname="ORG_NAME">渠道名称</th>
									<th fieldname="ORG_TYPE">渠道类型</th>
									<th fieldname="P_ORG_NAME">所属办事处</th>
									<th fieldname="SUM_AMOUNT" refer="amountFormat" align="right">库存总金额</th>
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
		if(!checkOrgType()){
			return;
		}
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerStockAmountQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	
		// 导出
	$("#btn-export").click(function(){
		if(!checkOrgType()){
			return;
		}
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/DealerStockAmountQueryAction/exportExcel.do");
		$("#exportFm").submit();
	});
	
	// 渠道类别判断
	function checkOrgType(){
		if($("#ORG_TYPE").val() == -1){
			alertMsg.warn("请选择渠道类别");
			return false;
		}
		return true;
	}
	
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

// 渠道选择回调函数
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#dia-orgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#ORG_CODE").val($(res).attr("orgCode"));
}
</script>