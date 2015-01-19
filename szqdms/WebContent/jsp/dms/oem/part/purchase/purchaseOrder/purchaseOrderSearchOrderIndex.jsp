<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购订单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询   &gt; 采购相关   &gt; 采购订单查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<tr>
						<td><label>采购单号</label></td>
					    <td colspan="3"><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="T.SPLIT_NO" operation="like" /></td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,600" operation="like" dataSource="PART_CODE"  />
						</td>
						<td><label>配件名称：</label></td>
						<td>
							<input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,600" operation="like" dataSource="PART_NAME" />
						</td>
					</tr>
					<tr>
				    	<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,3000"/></td>
					    <td><label>制单日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_DATE" style="width:70px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'in-jscjrq\')}'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_DATE" style="width:70px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'in-kscjrq\')}'})" />
				   		 </td>
				   		 <td><label>关单日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq1,in-jscjrq1"  id="in-kscjrq1"  name="in-kscjrq1" operation=">="  dataSource="CLOSE_DATE" style="width:70px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'in-jscjrq\')}'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq1,in-jscjrq1"  id="in-jscjrq1"  name="in-jscjrq1" operation="<=" dataSource="CLOSE_DATE" style="width:70px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'in-kscjrq\')}'})" />
				   		 </td>
					</tr>
					<tr>
					    <td><label>采购类型：</label></td>
					    <td><select type="text" class="combox" id="PURCHASE_TYPE" name="PURCHASE_TYPE" kind="dic" src="CGDDLX" datasource="PURCHASE_TYPE"  datatype="1,is_null,6" readonly="readonly">
								    	<option value="-1" selected>--</option>
								    </select></td>
					    <td><label>采购单状态：</label></td>
					    <td><select type="text" class="combox" id="ORDER_STATUS" name="ORDER_STATUS" kind="dic" src="CGDDZT" datasource="ORDER_STATUS" filtercode="<%=DicConstant.CGDDZT_02%>|<%=DicConstant.CGDDZT_03%>|<%=DicConstant.CGDDZT_04%>|<%=DicConstant.CGDDZT_05%>" datatype="1,is_null,6" readonly="readonly">
						    	<option value="-1" selected>--</option>
						    </select>
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重&nbsp;&nbsp;置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" refQuery="fm-searchPchOrder" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="SPLIT_NO" refer="showLink" colwidth="160px">订单编号</th>
							<th fieldname="PLAN_DISTRIBUTION">计配号</th>
							
							<!-- 添加采购金额，开票金额 by fuxiao 2014-12-19 -->
							<th fieldname="PURCHASE_AMOUNT" refer="amountFormat" align="right">采购金额</th>
							<th fieldname="PRINT_AMOUNT" refer="amountFormat" align="right">已开票金额</th>
							
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="PURCHASE_TYPE">采购类型</th>
							<th fieldname="SELECT_MONTH" >所属月度</th>
							<th fieldname="ORDER_STATUS">采购单状态</th>
							<th fieldname="APPLY_USER">制单人</th>
							<th fieldname="APPLY_DATE">制单日期</th>
							<th fieldname="CLOSE_DATE">关单日期</th>
							<th fieldname="REMARKS" colwidth="150px">备注</th>
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
</html>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDetailAction";
$(function(){
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/orderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	}).click();
	
	$("#btn-clear").click(function(){
		$("input","#tab-orderSearch").each(function(index, obj){
			$(obj).val("");
		});
	});
});
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("SPLIT_ID")+") class='op'>"+$row.attr("SPLIT_NO")+"</a>";
}
function openDetail(SPLIT_ID){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/purchaseOrderPartDetails.jsp?SPLIT_ID="+SPLIT_ID, "pchOrderDetail", "采购单明细", options);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>