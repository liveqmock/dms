<?xml version="1.0" encoding="UTF-8" ?>
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
<title>车厂库存查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 车厂库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>仓库：</label></td>
						<td>
							<input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME" datasource="" datatype="0,is_digit_letter_cn,30" 
									<%
										if(isAm){
									%>												
									
										   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100102,100110) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
									<%
										}else{
									%>
										   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101,100109,100103,100105,100104,100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
									
									<%
										}
									%> 
								   operation="=" isreload="true" kind="dic"
							/>
							<input type="hidden" id="WAREHOUSE_ID" name="T1.WAREHOUSE_ID" datatype="1,is_null,30" dataSource="T1.WAREHOUSE_ID" operation="=" />
							
						</td>
						<td><label>库区：</label></td>
						<td>
							<input type="text" id="AREA_NAME" name="AREA_NAME" datasource="" datatype="1,is_digit_letter_cn,30" 
								   src="T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME}:1=1 AND AREA_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY AREA_CODE" 
								   operation="=" isreload="true" kind="dic"
							/>
							<input type="hidden" id="AREA_ID" name="T1.AREA_ID" datatype="1,is_null,30" dataSource="T1.AREA_ID" operation="="  details="true" />
						</td>
					</tr>
					<tr>
						<td><label>货位：</label></td>
						<td colspan="3">
							<input type="text" id="POSITION_NAME" name="POSITION_NAME" datasource="" datatype="1,is_null,30" 
								   src="T#PT_BA_WAREHOUSE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1 AND POSITION_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY POSITION_CODE" 
								   operation="=" isreload="true" kind="dic"
							/>
							<input type="hidden" id="POSITION_ID" name="T1.POSITION_ID" datatype="1,is_null,30" dataSource="T1.POSITION_ID" operation="="  details="true"/>
						</td>
					</tr>
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,300" dataSource="T1.PART_CODE" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td colspan="3"><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_digit_letter_cn,300" dataSource="T1.PART_NAME" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>库管员姓名：</label></td>
					    <td><input type="text" id="USER_NAME" name="USER_NAME" datatype="1,is_digit_letter_cn,30" dataSource="T1.USER_NAME" operation="like" /></td>
					    <td><label>库管员账号：</label></td>
					    <td colspan="3"><input type="text" id="USER_ACCOUNT" name="USER_ACCOUNT" datatype="1,is_digit_letter,30" dataSource="T1.USER_ACCOUNT" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >汇总查询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-details-search">明细查询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear">重置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent" id="allDataDiv">
		<div id="page_contract_all" >
			<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract_all" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="ACCOUNT" style="display:none;"></th>
							<th fieldname="WAREHOUSE_CODE" >仓库代码</th>
							<th fieldname="WAREHOUSE_NAME" >仓库名称</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="AMOUNT" >库存数量</th>
							<th fieldname="OCCUPY_AMOUNT" >占用数量</th>
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
							<th fieldname="USER_ACCOUNT" >库管员账号</th>
							<th fieldname="USER_NAME" >库管员名称</th>
							<th fieldname="PLAN_PRICE" refer="amountFormat">计划价格</th>
							<th fieldname="SALE_PRICE" refer="amountFormat">销售价格</th>
							<th fieldname="UPPER_LIMIT" >库存上限</th>
							<th fieldname="LOWER_LIMIT" >库存下限</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	<div class="pageContent" id="detailsDataDiv" style="display: none;">
		<div id="page_contract_details" >
			<table style="display:none;width:100%;" id="detailsTable" name="tablist" ref="page_contract_details" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="ACCOUNT" style="display:none;"></th>
							<th fieldname="WAREHOUSE_CODE" >仓库代码</th>
							<th fieldname="WAREHOUSE_NAME" >仓库名称</th>
							<th fieldname="AREA_CODE" >库区代码</th>
							<th fieldname="AREA_NAME" >库区名称</th>
							<th fieldname="POSITION_CODE" >库位代码</th>
							<th fieldname="POSITION_NAME" >库位名称</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="AMOUNT" >库存数量</th>
							<th fieldname="OCCUPY_AMOUNT" >占用数量</th>
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
							<th fieldname="MIN_PACK" >最小包装数</th>
							<th fieldname="USER_ACCOUNT" >库管员账号</th>
							<th fieldname="USER_NAME" >库管员名称</th>
							<th fieldname="PLAN_PRICE" refer="amountFormat">计划价格</th>
							<th fieldname="SALE_PRICE" refer="amountFormat">销售价格</th>
							<th fieldname="UPPER_LIMIT" >库存上限</th>
							<th fieldname="LOWER_LIMIT" >库存下限</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
<script type="text/javascript">
$(function(){
	
	// 查询按钮
	$("#btn-search").click(function(){

		// 将明细查询中的查询条件过滤
		$("input[details=true]").each(function(index, obj){
			$(obj).removeAttr("dataSource");
		});
	
		$("#allDataDiv").show();
		$("#detailsDataDiv").hide();
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/InventoryQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");

	});
	
	// 详细信息查询
	$("#btn-details-search").click(function(){
		
		// 为明细查询添加明细查询条件
		$("input[details=true]").each(function(index, obj){
			$(obj).removeAttr("dataSource").attr("dataSource",$(obj).attr("name"));
		});
	
		$("#allDataDiv").hide();
		$("#detailsDataDiv").show();
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/InventoryQueryAction/queryDetailsInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-details-search",1,sCondition,"detailsTable");

	});
	
	// 重置
	$("#btn-clear").click(function(){
		$("input", "#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

//表选回调函数：id,触发函数对象ID，$row 查询结果点击的行对象, selIndex，查询结果点击行号
function afterDicItemClick(id, $row, selIndex){
  var ret = true; 		// 返回值
  switch(id){
  case "WAREHOUSE_NAME":
	  $("#WAREHOUSE_NAME").val($row.attr("WAREHOUSE_NAME")); // 设置选择字典的值
	  var warehouseId = $row.attr("WAREHOUSE_ID")
	  $("#WAREHOUSE_ID").val($row.attr("WAREHOUSE_ID"))
	  var sql = "T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME}:1=1 AND AREA_STATUS=<%=DicConstant.YXBS_01 %> AND WAREHOUSE_ID = "+warehouseId+" ORDER BY AREA_CODE";
	  // 设置库区的表选查询SQL
	  $("#AREA_NAME").val("").attr("src", sql);
	  break;
  case "AREA_NAME":
	  $("#AREA_NAME").val($row.attr("AREA_NAME"));
	  var areaId = $row.attr("AREA_ID");
	  $("#AREA_ID").val($row.attr("AREA_ID"))
	  var sql = "T#PT_BA_WAREHOUSE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME}:1=1 AND POSITION_STATUS=<%=DicConstant.YXBS_01 %> AND AREA_ID = "+areaId+" ORDER BY POSITION_CODE";
	  $("#POSITION_NAME").val("").attr("src", sql);
	  break;
  case "POSITION_NAME":
	  $("#POSITION_NAME").val($row.attr("POSITION_NAME"));
	  $("#POSITION_ID").val($row.attr("POSITION_ID"));
	  break;
  }
  return ret;
}

</script>
</html>