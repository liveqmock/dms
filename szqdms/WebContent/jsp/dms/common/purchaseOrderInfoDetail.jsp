<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购订单信息</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String SPLIT_ID = request.getParameter("SPLIT_ID");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDetailAction";
var SPLIT_ID = "<%=SPLIT_ID%>";
$(function(){
	$("button.close").click(function(){
		var dialog = parent.$("body").data("pchOrderDetail");
		parent.$.pdialog.close(dialog);
		return false;
	});
	
		$("#part").height(document.documentElement.clientHeight-80);
		$("#SPLIT_ID").val(SPLIT_ID);
		var $f = $("");
		var sCondition = {};
	   	sCondition = $f.combined() || {};
	   	var search = mngUrl+"/purchaseOrderInfoSearch.ajax?SPLIT_ID="+SPLIT_ID;
	   	sendPost(search,"",sCondition,searchContractCallBack,"false");
		
		$("button[name='btn-pre']").bind("click",function(event) 
				{
						$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex")) - 1);
				});
				$("button[name='btn-next']").bind("click", function(event) 
					{
							var searchPartUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDetailAction/purchaseOrderPartSearch.ajax?SPLIT_ID="+SPLIT_ID;
							var $f = $("#fm-id");
							var sCondition = {};
					    	sCondition = $f.combined() || {};
							doFormSubmit($f,searchPartUrl,"",1,sCondition,"tab-partinfo");
							var $tabs = $("#tabs");
							switch (parseInt($tabs.attr("currentIndex"))) 
							{
								case 0:
									break;
								case 1:
									break;
							}
						$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
						//跳转后实现方法
						(function(ci) 
						{
							switch (parseInt(ci)) 
							{
								case 1://第2个tab页					
									break;
								case 2://第3个tab页
									break;
								default:
									break;
							}
						})
						(parseInt($tabs.attr("currentIndex")));
				 });
});
function searchContractCallBack(res)
{
	var rows = res.getElementsByTagName("ROW");
	setEditValue("dia-tab-order",res.documentElement);
	var PURCHASE_TYPE = getNodeText(rows[0].getElementsByTagName("PURCHASE_TYPE").item(0));
<%--     if(PURCHASE_TYPE==<%=DicConstant.CGDDLX_04%>){
    	$("#mounth").hide();
        $("#mounth1").hide();
    } --%>
	return true;
}
</script>
</head>
<body>
<div id="layout">
<div class="tabs" eventType="click" id="tabs" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					    <li><a href="javascript:void(0);"><span>订单基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>采购配件信息</span></a></li>
					</ul>
				</div>
			</div>
  <div class="tabsContent">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-contract" class="editForm" style="width:99%;">
		<input type="hidden" id="SPLIT_ID" name="SPLIT_ID" datasource="SPLIT_ID" />
		<!-- 隐藏域 -->
		
		  <div align="left">
			  <fieldset>
				<table class="editTable" id="dia-tab-order">
					<tr>
						<td><label>采购单编号：</label></td>
					    <td><input type="text" id="dia-SPLIT_NO" name="dia-SPLIT_NO" datasource="SPLIT_NO" readonly="readonly"/></td>
					    <td id="mounth"><label>所属月度：</label></td>
					    <td id="mounth1">
					    	<input type="text"  id="dia-SELECT_MONTH"  name="dia-SELECT_MONTH"   dataSource="SELECT_MONTH" style="width:75px;"  readonly="readonly" />
					    </td>
					    <td><label>采购类型：</label></td>
					    <td>
					    	<input type="text" id="dia-PURCHASE_TYPE" name="dia-PURCHASE_TYPE" datasource="PURCHASE_TYPE" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td><label>厂家代码：</label></td>
					    <td>
					    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  readonly="readonly"/>
					    	<input type="hidden" id="dia-SUPPLIER_ID" name="dia-SUPPLIER_ID" datasource="SUPPLIER_ID" />
					    </td>
						<td><label>厂家名称：</label></td>
					    <td>
					    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" readonly="true"/>
					    </td>
					    <td><label>要求完成时间：</label></td>
					    <td>
					    	<input type="text" id="dia-REPUIREMENT_TIME" name="dia-REPUIREMENT_TIME" datasource="REPUIREMENT_TIME" readonly="true"/>
					    </td>
					</tr>
				</table>
			  </fieldset>
			</div>
			</form>	
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next0" name="btn-next">下一页</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>		
	</div>
	<div class="page">
       <div class="pageContent">
       		<form method="post" id="fm-searchPart" class="editForm">
                <table class="searchContent" id="tab-searchPart"></table>
            </form>
			<fieldset>	
			<div id="part" style="overflow:hidden;">
					<table style="display:none;width:99%;" id="tab-partinfo"  name="tablist" ref="part" edit="false" >
							<thead>
								<tr>
									<th type="single" name="XH"  style="display:none;"></th>
		                            <th fieldname="PART_CODE">配件代码</th>
		                            <th fieldname="PART_NAME">配件名称</th>
		                            <th fieldname="UNIT" colwidth="75">计量单位</th>
									<th fieldname="MIN_PACK" colwidth="75" refer="toAppendStr">最小包装</th>
		                            <th fieldname="PCH_COUNT">采购数量</th>
		                            <th fieldname="PCH_PRICE" refer="amountFormatStr" align="right">采购价格</th>
		                            <th fieldname="PCH_AMOUNT" refer="amountFormatStr" align="right">金额</th>
		                            <th fieldname="DELIVERY_CYCLE">供货周期</th>
		                            <th fieldname="STORAGE_COUNT">入库数量</th>
		                            <th fieldname="STORAGE_AMOUNT" refer="amountFormatStr" align="right">入库金额</th>
		                            <th fieldname="REMARKS">备注</th>
								</tr>
							</thead>
					</table>
				</div>
		</fieldset>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="pre1" name="btn-pre">上一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
				</div>						
      </div>				
	</div>		
</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>	
</body>
</html>