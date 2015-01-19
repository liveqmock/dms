<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.dms.dao.part.storageMng.search.StockWarningQueryDao"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	boolean isPchUser = false;
	Connection conn = null;
	try{
		StockWarningQueryDao dao = new StockWarningQueryDao();
		isPchUser = dao.checIfPchUser(user.getAccount());
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(conn != null){
			conn.close();
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>库存预警查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 库存预警查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>仓库：</label></td>
									<td>										
										<input type="text" id="STOCK_CODE" name="STOCK_CODE" datasource="STOCK_CODE" datatype="0,is_null,300" action="show"
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<%
										if(isPchUser){
									%>
									<td></td>
									<td>
										<input type="hidden" id="UNAME" name="UNAME" datasource="USER_ACCOUNT" operation="=" datatype="1,is_null,300"  value="<%=user.getAccount()%>"/>
									</td>
									<%
										}else{
									%>
									<td><label>采购员：</label></td>
									<td><input type="text" id="UNAME" name="UNAME" datasource="USER_ACCOUNT" value="" operation="=" datatype="1,is_null,300" 
												operation="=" isreload="true" kind="dic"
												src="T#PT_BA_PCH_ATTRIBUTE:USER_ACCOUNT:USER_NAME{USER_ACCOUNT,USER_NAME}:1=1 AND STATUS = 100201 GROUP BY USER_ACCOUNT,USER_NAME"
										/>
									</td>
									<%
										}
									%>
								</tr>
								<tr>
									<td><label>配件编号：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="I.PART_CODE" value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_CODE" datasource="I.PART_NAME" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
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
									<td><label>在途数量：</label></td>
									<td>
										<input type="text" id="PCH_COUNT_B" name="PCH_COUNT_B" datasource="PCH_COUNT_B" value="" operation=">=" datatype="1,is_digit_letter,30" action="show" style="width: 75px;" />
										 <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
										<input type="text" id="PCH_COUNT_E" name="PCH_COUNT_E" datasource="PCH_COUNT_E" value="" operation="<=" datatype="1,is_digit_letter,30" action="show" style="width: 75px; margin-left: -30px;" />
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
									<th fieldname="PART_CODE" colwidth="100px">配件编号</th>
									<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
									<th fieldname="UPPER_LIMIT" colwidth="50px">库存上限</th>
									<th fieldname="LOWER_LIMIT" colwidth="50px">库存下限</th>
									<th fieldname="PCH_COUNT" colwidth="50px">在途数量</th>
									<th fieldname="AVAILABLE_AMOUNT" colwidth="50px">可用数量</th>
									<th fieldname="USER_NAME" colwidth="50px">采购员</th>
									<th fieldname="SUPPLIER_CODE">供应商编码</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/StockWarningQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/StockWarningQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
})
</script>