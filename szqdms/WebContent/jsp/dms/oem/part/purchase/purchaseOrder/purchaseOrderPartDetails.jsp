<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String SPLIT_ID = request.getParameter("SPLIT_ID");
%>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder-details">
		    <input type="hidden" id="SPLIT_ID" name="SPLIT_ID" datasource="T.SPLIT_ID" value="<%=SPLIT_ID %>" />
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch-details">
					<tr>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE_DETAILS" name="PART_CODE_DETAILS" datatype="1,is_null,600" operation="like" dataSource="T1.PART_CODE"  />
						</td>
						<td><label>配件名称：</label></td>
						<td>
							<input type="text" id="PART_NAME_DETAILS" name="PART_NAME_DETAILS" datatype="1,is_null,600" operation="like" dataSource="T1.PART_NAME" />
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-details" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_transfer" >
			<table style="display:none;width:100%;" id="tab-transfer_info" name="tablist" ref="page_transfer" refQuery="fm-searchPchOrder-details" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PART_CODE" colwidth="150px">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="150px">配件名称</th>
                            <th fieldname="UNIT" colwidth="75px">计量单位</th>
							<th fieldname="MIN_PACK" colwidth="60px" refer="toAppendStr">最小包装</th>
                            <th fieldname="PCH_PRICE" refer="amountFormatStr" align="right">采购价格</th>
                            <th fieldname="PCH_COUNT" colwidth="75px" >采购数量</th>
                            <th fieldname="PCH_AMOUNT" refer="amountFormatStr" align="right">采购金额</th>
                            <th fieldname="STORAGE_COUNT" colwidth="80px">入库数量</th>
                            <!-- 修改开票数量查询：by fuxiao 2014-12-19  -->
<!--                             <th fieldname="STORAGE_COUNT" colwidth="80px" >开票数量</th> -->
                            <th fieldname="PRINT_COUNT" colwidth="80px" >开票数量</th>
                            
                            <th fieldname="REMARKS" colwidth="150px" >备注</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
<script type="text/javascript">
$(function(){
	$("#btn-search-details").click(function(){
		var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDetailAction/partSearch.ajax";
		var $f = $("#fm-searchPchOrder-details");
		var sCondition = {};
	   	sCondition = $f.combined() || {};
		doFormSubmit($f,mngUrl,"btn-search-details",1,sCondition,"tab-transfer_info");
	}).click();
});
function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
</script>