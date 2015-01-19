<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.dao.part.storageMng.search.StocksCountQueryDao" %>
<%@ page import="java.sql.Connection" %>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	StocksCountQueryDao dao = null;
	boolean isAm = false;
	Connection conn = null;
	try
	{
		dao = new StocksCountQueryDao();
		isAm = dao.checkUserIsAM(user);
		conn = dao.getConnection();
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
<title>本部库存及价格查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 本部库存及价格查询</h4>
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
										<input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME" datasource="ST.WAREHOUSE_CODE" datatype="0,is_digit_letter_cn,30" 
										<%
											if(isAm){
										%>												
										
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100102,100110) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
										<%
											}else{
										%>
										<%-- begin 20150106 by fuxiao 赵工提出取消军品的限制 --%>
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101,100102,100109,100103,100105,100104,100110,100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
										
										<%
											}
										%>
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<td><label>库区：</label></td>
									<td>
										<input type="text" id="AREA_NAME" name="AREA_NAME" datasource="D.AREA_CODE" datatype="1,is_digit_letter_cn,30" 
											   src="T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME}:1=1 AND AREA_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY AREA_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
								</tr>
								<tr>
									<td><label>货位：</label></td>
									<td>
										<input type="text" id="POSITION_NAME" name="POSITION_NAME" datasource="D.POSITION_CODE" datatype="1,is_null,30" 
											   src="T#PT_BA_WAREHOUSE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1 AND POSITION_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY POSITION_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="D.PART_NAME" operation="like" datatype="1,is_null,30" /></td>
								</tr>
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="D.PART_CODE" operation="like" datatype="1,is_null,300" /></td>
									<td><label>总库存数量：</label></td>
									<td>
										<input type="text" id="AMOUNT_B" name="AMOUNT_B" style="width: 75px;" datasource="D.AMOUNT" datatype="1,is_null,30" operation=">=" onblur="checkAmountNum({maxId:'AMOUNT_E'},this)"/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="AMOUNT_E" name="AMOUNT_E" style="width: 75px; margin-left: -30px;" datasource="D.AMOUNT" datatype="1,is_null,30" operation="<=" onblur="checkAmountNum({minId:'AMOUNT_B'},this)"/>
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
									<th fieldname="PART_CODE" colwidth="110">配件编号</th>
									<th fieldname="PART_NAME" >配件名称</th>
									<th fieldname="PLAN_PRICE" align="right" refer="amountFormat">计划价</th>
									<th fieldname="AMOUNT" colwidth="60px">总库存</th>
									<th fieldname="OCCUPY_AMOUNT" colwidth="60px">占用库存</th>
									<th fieldname="AVAILABLE_AMOUNT" colwidth="60px">可用库存</th>
									<th fieldname="SUPPLIER_NAME" >供应商</th>
									<th fieldname="USER_NAME" >库管员</th>
									<th fieldname="WAREHOUSE_NAME" colwidth="80px">仓库</th>
									<th fieldname="AREA_NAME" colwidth="80px">库区</th>
									<th fieldname="POSITION_NAME" >货位</th>
									<th fieldname="POSITION_CODE" >货位代码</th>
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
		if(checkAmountNum({maxId:'AMOUNT_E'},$("#AMOUNT_B")) && checkAmountNum({minId:'AMOUNT_B'},$("#AMOUNT_E"))){
			var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/StocksCountQueryAction/queryListInfoAndPrice.ajax";
			var $f = $("#fm-searchContract");
			var sCondition = {};
			sCondition = $f.combined() || {};
			doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
		}
	})
	
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/StocksCountQueryAction/exportExcelAndPrice.do");
		$("#exportFm").submit();
	})
})

// 表选回调函数：id,触发函数对象ID，$row 查询结果点击的行对象, selIndex，查询结果点击行号
function afterDicItemClick(id, $row, selIndex){
  var ret = true; 		// 返回值
  switch(id){
  case "WAREHOUSE_NAME":
	  $("#WAREHOUSE_NAME").val($row.attr("WAREHOUSE_NAME")); // 设置选择字典的值
	  var warehouseId = $row.attr("WAREHOUSE_ID")
	  var sql = "T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME}:1=1 AND AREA_STATUS=<%=DicConstant.YXBS_01 %> AND WAREHOUSE_ID = "+warehouseId+" ORDER BY AREA_CODE";
	  // 设置库区的表选查询SQL
	  $("#AREA_NAME").val("").attr("src", sql);
	  break;
  case "AREA_NAME":
	  $("#AREA_NAME").val($row.attr("AREA_NAME"));
	  var areaId = $row.attr("AREA_ID");
	  var sql = "T#PT_BA_WAREHOUSE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1 AND POSITION_STATUS=<%=DicConstant.YXBS_01 %> AND AREA_ID = "+areaId+" ORDER BY POSITION_CODE";
	  $("#POSITION_NAME").val("").attr("src", sql);
	  break;
  case "POSITION_NAME":
	  $("#POSITION_NAME").val($row.attr("POSITION_NAME"));
	  break;
  }
  return ret;
}

// 检测总库存数量区间
function checkAmountNum(paraJson, obj){
	var pattern = /^\+?\w*$/;
	if($(obj).val() != "" && pattern.test($(obj).val()) !== true){
		alertMsg.warn("总库存数量输入错误，请重新输入.");
		return false;
	}
	if(paraJson["maxId"] != null){
		var numVal = $("#"+paraJson["maxId"]).val();
		if(numVal != "" && $(obj).val() != "" && $(obj).val() > numVal){
			alertMsg.warn("总库存数量范围错误，请重新输入");
			return false;
		}
	}else if(paraJson["minId"] != null){
		var numVal = $("#"+paraJson["minId"]).val();
		if(numVal != "" && $(obj).val() != "" && $(obj).val() < numVal){
			alertMsg.warn("总库存数量范围错误，请重新输入");
			return false;
		}
	}
	return true;
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());

}
</script>