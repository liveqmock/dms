<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>未入库订单汇总</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 未入库订单汇总</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<!-- 定义查询条件 -->
								<tr>
									<td><label>库区代码：</label></td>
									<td>										
										<input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="ORG.CODE"  datatype="1,is_null,100" operation="in" readonly="readonly" hasBtn="true" callFunction="open();"/>
									</td>
									<td><label>库区名称：</label></td>
									<td>										
										<input type="text" id="ORG_NAME" name="ORG_NAME"  datatype="1,is_null,100" operation="in" readonly="readonly" />
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重&nbsp;&nbsp;置</button></div></div></li>
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
									<th fieldname="ORG_CODE" colwidth="70px">库区编码</th>
									<th fieldname="ORG_NAME">库区名称</th>
									<th fieldname="ORDER_NO">入库单号</th>
									<th fieldname="REAL_AMOUNT" refers="amountFormat" align="right">入库单金额</th>
									<th fieldname="LAST_AREA">上级发货库区</th>
									<th fieldname="ORDER_NO">发货库区出库单号</th>
									<th fieldname="OUT_DATE" colwidth="130px">出库日期</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/salesMng/search/NotPutInStorageQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/search/NotPutInStorageQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
	// 清除
	$("#btn-clear").click(function(){
		$("#ORG_CODE").val("");
		$("#ORG_NAME").val("");
	})
	
})
function open(){
	var id="ORG_CODE";
	/* 	showOrgTree(id,busType); */
	/**
	 * create by andy.ten@tom.com 2011-12-04
	 * 弹出组织选择树
	 * id:弹出选择树的input框id
	 * busType:业务类型[1,2]，1表示配件2表示售后
	 * partOrg:配件业务使用：[1,2]，1表示只显示配送中心2表示只显示服务商和经销商，不传表示全显示
	 * multi:[1,2],1表示默认是多选，2表示是单选
	 */
	showOrgTree(id,1,"",1);
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	  $("#ORG_CODE").val(orgCode).attr("code",orgCode); 
	  $("#ORG_NAME").val(orgName);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>