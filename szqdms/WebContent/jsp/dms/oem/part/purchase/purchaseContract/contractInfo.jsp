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
<title>合同信息</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String CONTRACT_ID = request.getParameter("CONTRACT_ID");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractOperationManageAction";
var CONTRACT_ID = "<%=CONTRACT_ID%>";
$(function(){
	$("button.close").click(function(){
		var dialog = parent.$("body").data("contractDetail");
		parent.$.pdialog.close(dialog);
		return false;
	});
		$("input", "#tab-contract").each(function(index, obj){
			$(obj).attr("readonly", "readonly");
		})
		$("#CONTRACT_ID").val(CONTRACT_ID);
		var $f = $("");
		var sCondition = {};
	   	sCondition = $f.combined() || {};
	   	var search = mngUrl+"/contractInfoSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
	   	sendPost(search,"",sCondition,searchContractCallBack,"false");
		
		$("button[name='btn-pre']").bind("click",function(event) 
				{
						$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex")) - 1);
				});
				$("button[name='btn-next']").bind("click", function(event) 
					{
							var searchPartUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractAppendixManageAction/contractPartSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
							var $f = $("#fm-part-searchContract");
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
	setEditValue("tab-contract",res.documentElement);
	var a =getNodeText(rows[0].getElementsByTagName("ERP_NUM").item(0));
	if(a){
		$("#erpname").show();
		$("#erpnum").show();
	}
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
					    <li><a href="javascript:void(0)"><span>合同基本信息</span></a></li>
						<li><a href="javascript:void(0)"><span>供货品种信息</span></a></li>
					</ul>
				</div>
			</div>
  <div class="tabsContent">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-contract" class="editForm" style="width:99%;">
		<!-- 隐藏域 -->
		
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="tab-contract">
						<tr>
						<input type="hidden" id="CONTRACT_ID" name="CONTRACT_ID" datasource="CONTRACT_ID" />
								<td><label>合同编号：</label></td>
							    <td>
							    	<input type="text" id="CONTRACT_NO" name="CONTRACT_NO" datasource="CONTRACT_NO" />
							    </td>
							    <td><label>合同名称：</label></td>
							    <td>
							    	<input type="text" id="CONTRACT_NAME" name="CONTRACT_NAME" datasource="CONTRACT_NAME" />
							    </td>
							    <td><label>合同类别：</label></td>
							    <td>
								    <input type="text" id="CONTRACT_TYPE" name="CONTRACT_TYPE" datasource="CONTRACT_TYPE" />
								</td>
							    
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" />
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datasource="SUPPLIER_NAME" />
							    </td>
								<td><label>发票类型：</label></td>
							    <td>
							    	<input type="text" id="dia-INVOICE_TYPE" name="dia-INVOICE_TYPE" datasource="INVOICE_TYPE"  readonly="true"/>
							    </td>
							</tr>
							<tr>
								<td><label>厂家资质：</label></td>
							    <td>
							    	<input type="text" id="SUPPLIER_QUALIFY" name="SUPPLIER_QUALIFY" datasource="SUPPLIER_QUALIFY" />
							    </td>
								<td><label>厂家法人：</label></td>
							    <td>
							    	<input type="text" id="LEGAL_PERSON" name="LEGAL_PERSON" datasource="LEGAL_PERSON" />
							    </td>
								<td><label>法人联系方式：</label></td>
							    <td>
							    	<input type="text" id="LEGAL_PERSON_PHONE" name="LEGAL_PERSON_PHONE" datasource="LEGAL_PERSON_PHONE" />
							    </td>
							    
							</tr>
							<tr>
								<td><label>税率(%)：</label></td>
								<td>
							    	<input type="text" id="TAX_RATE" name="TAX_RATE" datasource="TAX_RATE" />
							    </td>
								<td><label>业务联系人：</label></td>
							    <td>
							    	<input type="text" id="BUSINESS_PERSON" name="BUSINESS_PERSON" datasource="BUSINESS_PERSON" />
							    </td>
								<td><label>业务联系方式：</label></td>
							    <td>
							    	<input type="text" id="BUSINESS_PERSON_PHONE" name="BUSINESS_PERSON_PHONE" datasource="BUSINESS_PERSON_PHONE" />
							    </td>
							</tr>
							<tr>
								<td><label>挂账结算周期(月)：</label></td>
							    <td>
							    	<input type="text" id="OPEN_ACCOUNT" name="OPEN_ACCOUNT" datasource="OPEN_ACCOUNT" />
							    </td>
								<td><label>有效期：</label></td>
							    <td>
							    	<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq"   dataSource="EFFECTIVE_CYCLE_BEGIN" style="width:75px;"  kind="date" datatype="1,is_date,30" readonly="true" />
						    			<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
									<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" dataSource="EFFECTIVE_CYCLE_END" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" readonly="true" />
							    </td>
							</tr>
							<tr>
								<td><label>质保金(元)：</label></td>
							    <td>
							    	<input type="text" id="GUARANTEE_MONEY" name="GUARANTEE_MONEY" datasource="GUARANTEE_MONEY" />
							    </td>
								<td><label>质保期(月)：</label></td>
							    <td>
							    	<input type="text" id="WARRANTY_PERIOD" name="WARRANTY_PERIOD" datasource="WARRANTY_PERIOD" />
							    </td>
							    <td id = "erpname" style="display:none"><label>ERP编码：</label></td>
							    <td id = "erpnum" style="display:none">
							    	<input type="text" id="ERP_NUM" name="ERP_NUM" datasource="ERP_NUM" />
							    </td>
							</tr>
							<tr>
							    <td><label>追偿条款：</label></td>
							    <td colspan="5">
								  <textarea id="RECOVERY_CLAUSE" style="width:450px;height:40px;" name="RECOVERY_CLAUSE" datasource="RECOVERY_CLAUSE" readonly="true"></textarea>
							    </td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
							    <td colspan="5">
								  <textarea id="REMARKS" style="width:450px;height:40px;" name="REMARKS" datasource="REMARKS"  readonly="true"></textarea>
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
			<fieldset>	
			<legend align="right"><a onclick="onTitleClick('part')">&nbsp;供货品种信息&gt;&gt;</a></legend>
			<div>
					<form method="post" id="fm-part-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="part-tab-search">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>配件代码：</label></td>
									<td>
										<input type="text" id="DETAIL_PART_CODE" name="DETAIL_PART_CODE" datatype="1,is_null,600" operation="like" dataSource="PART_CODE"  />
									</td>
									<td><label>配件名称：</label></td>
									<td>
										<input type="text" id="DETAIL_PART_NAME" name="DETAIL_PART_NAME" datatype="1,is_null,600" operation="like" dataSource="PART_NAME" />
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="part-btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="part-btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
			</div>
			<div id="part">
					<table style="display:none;width:99%;" id="tab-partinfo"  name="tablist" ref="part" edit="false" refQuery="fm-part-searchContract" >
							<thead>
								<tr>
									<th fieldname="PART_CODE" >供货品种代码</th>
									<th fieldname="PART_NAME" >供货品种名称</th>
									<th fieldname="UNIT_PRICE">单价(不含税)</th>
									<th fieldname="DELIVERY_CYCLE">供货周期</th>
									<th fieldname="MIN_PACK_UNIT">最小包装单位</th>
									<th fieldname="MIN_PACK_COUNT">最小包装数</th>
									<th fieldname="REMARKS">备注</th>
								</tr>
							</thead>
					</table>
			</div>
			<form id="exportFm" method="post" style="display:none">
				<input type="hidden" id="params" name="data"></input>
				<input type="hidden" id="claimNoInfo" name="data"></input>
			</form>
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
<script type="text/javascript">
$(function(){
	$("#part-btn-search").click(function(){
		var searchPartUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractAppendixManageAction/contractPartSearch.ajax?CONTRACT_ID=<%=CONTRACT_ID%>";
		var $f = $("#fm-part-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchPartUrl,"",1,sCondition,"tab-partinfo");
	});
	$("#part-btn-export").click(function(){
		var $f = $("#part-tab-search");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractInfoSearchManageAciton/download.do?CONTRACT_ID=<%=CONTRACT_ID%>");
		$("#exportFm").submit();
	});
})
</script>