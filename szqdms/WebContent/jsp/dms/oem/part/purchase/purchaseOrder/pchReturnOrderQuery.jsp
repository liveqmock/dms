<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购退货单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询   &gt; 采购相关   &gt; 采购退货单查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<tr>
						<td><label>采购退货单号：</label></td>
					    <td colspan="3">
					    	<input type="text" id="RETURN_NO" name="RETURN_NO" datatype="1,is_digit_letter,30" dataSource="RETURN_NO" operation="like" />
					    </td>
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
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="ORDER_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'in-jscjrq\')}'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="ORDER_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'in-kscjrq\')}'})" />
				   		 </td>
					</tr>
					<tr>
					    <td><label>采购退货单状态：</label></td>
					    <td colspan="1">
					    	<select type="text" class="combox" id="RETURN_STATUS" name="RETURN_STATUS" kind="dic" src="CGTHDZT" datasource="RETURN_STATUS" filtercode="<%=DicConstant.CGTHDZT_02%>|<%=DicConstant.CGTHDZT_03%>|<%=DicConstant.CGTHDZT_04%>" datatype="1,is_null,6" readonly="readonly">
						    	<option value="-1" selected>--</option>
						    </select>
					    </td>
					    <td><label>关闭日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq1,in-jscjrq1"  id="in-kscjrq1"  name="in-kscjrq1" operation=">="  dataSource="CLOSE_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'in-jscjrq\')}'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq1,in-jscjrq1"  id="in-jscjrq1"  name="in-jscjrq1" operation="<=" dataSource="CLOSE_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,vel:'d1',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'in-kscjrq\')}'})" />
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
							<th fieldname="RETURN_NO" refer="showLink" colwidth="150px">采购退货单号</th>
							<th fieldname="ORDER_DATE" colwidth="130px">制单日期</th>
							<th fieldname="ORDER_USER" colwidth="100px">制单人</th>
							<th fieldname="RETURN_STATUS" colwidth="80px">退货单状态</th>
							<th fieldname="SUPPLIER_CODE" colwidth="150px">供应商代码</th>
							<th fieldname="SUPPLIER_NAME" colwidth="150px">供应商名称</th>
							<th fieldname="CLOSE_DATE" colwidth="130px">关闭日期</th>
							<th fieldname="SIGN_STAUTS" colwidth="110px">签收状态</th>
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
$(function(){
	var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PchReturnOrderQueryAction";
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/queryListInfo.ajax";
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
    return "<a href='#' onclick=openDetail("+$row.attr("RETURN_ID")+") class='op'>"+$row.attr("RETURN_NO")+"</a>";
}
function openDetail(id){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/pchReturnOrderForDetails.jsp?id=" + id, "pchOrderDetail", "采购退货单明细", options);
}
</script>