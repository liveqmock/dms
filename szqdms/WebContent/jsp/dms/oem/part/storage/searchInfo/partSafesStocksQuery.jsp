<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件上下限查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 配件上下限查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<!-- 将仓库查询条件取消，默认只查本部库 -->
						<input type="hidden" id="STOCK_CODE" name="STOCK_CODE" datasource="STOCK_CODE" datatype="1,is_digit_letter_cn,30" operation="=" value="K000001"/>
						
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
<%-- 								<tr>
									<td><label>仓库：</label></td>
									<td>										
										<input type="text" id="STOCK_CODE" name="STOCK_CODE" datasource="STOCK_CODE" datatype="1,is_digit_letter_cn,30" 
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100110, 100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<td><label>采购员：</label></td>
									<td><input type="text" id="USER_NAME" name="USER_NAME" datasource="USER_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
								</tr> --%>
								<tr>
									<td><label>配件编号：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_CODE" datasource="PART_CODE" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>配件上下限：</label></td>
									<td>
										<select id="AVAILABLE_AMOUNT" name="AVAILABLE_AMOUNT" datasource="AVAILABLE_AMOUNT" value="" operation="=" datatype="1,is_null,300" >
											<option selected="selected" value="-1">--</option>
											<option value=">">可用数量大于上限</option>
											<option value="<">可用数量小于下限</option>
										</select>								
									</td>
									<td><label>采购员：</label></td>
									<td><input type="text" id="UNAME" name="UNAME" datasource="USER_ACCOUNT" value="" operation="=" datatype="1,is_null,300" 
												operation="=" isreload="true" kind="dic"
												src="T#PT_BA_PCH_ATTRIBUTE:USER_ACCOUNT:USER_NAME{USER_ACCOUNT,USER_NAME}:1=1 AND STATUS = 100201 GROUP BY USER_ACCOUNT,USER_NAME"
									/></td>
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
<!-- 									<th fieldname="STOCK_CODE" colwidth="60px">仓库代码</th>
									<th fieldname="STOCK_NAME" colwidth="100px">仓库名称</th> -->
									<th fieldname="PART_CODE" colwidth="120px">配件编号</th>
									<th fieldname="PART_NAME" >配件名称</th>
									<th fieldname="UNIT" colwidth="50px">计量单位</th>
									<th fieldname="MM" colwidth="50px">最小包装</th>
									<th fieldname="UPPER_LIMIT" colwidth="50px">库存上限</th>
									<th fieldname="LOWER_LIMIT" colwidth="50px">库存下限</th>
									<th fieldname="USER_NAME" colwidth="50px">采购员</th>
									<th fieldname="AREA_CODE" colwidth="60px">库区代码</th>
									<th fieldname="AREA_NAME" colwidth="120px">库区名称</th>
									<th fieldname="AVAILABLE_AMOUNT" colwidth="50px">可用数量</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/PartSafesStocksQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/PartSafesStocksQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
})
</script>