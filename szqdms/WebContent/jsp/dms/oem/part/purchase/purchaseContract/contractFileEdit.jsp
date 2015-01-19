<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
<div class="tabs" eventType="click" id="dia-tabs" >
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
		<form method="post" id="dia-fm-contract" class="editForm" style="width:99%;">
		<!-- 隐藏域 -->
		<input type="hidden" id="dia-CONTRACT_ID" name="dia-CONTRACT_ID" datasource="CONTRACT_ID" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-contract">
						<tr>
								<td><label>合同编号：</label></td>
							    <td>
							    	<input type="text" id="dia-CONTRACT_NO" name="dia-CONTRACT_NO" datasource="CONTRACT_NO" readonly="true"/>
							    </td>
							    <td><label>合同名称：</label></td>
							    <td>
							    	<input type="text" id="dia-CONTRACT_NAME" name="dia-CONTRACT_NAME" datasource="CONTRACT_NAME" readonly="true"/>
							    </td>
							    <td><label>合同类别：</label></td>
							    <td>
								    <input type="text" id="dia-CONTRACT_TYPE" name="dia-CONTRACT_TYPE" datasource="CONTRACT_TYPE" readonly="true"/>
								</td>
							    
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" readonly="true"/>
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" readonly="true"/>
							    </td>
								<td><label>发票类型：</label></td>
							    <td>
							    	<input type="text" id="dia-INVOICE_TYPE" name="dia-INVOICE_TYPE" datasource="INVOICE_TYPE"  readonly="true"/>
							    </td>
							</tr>
							<tr>
								<td><label>厂家资质：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_QUALIFY" name="dia-SUPPLIER_QUALIFY" datasource="SUPPLIER_QUALIFY" readonly="true"/>
							    </td>
								<td><label>厂家法人：</label></td>
							    <td>
							    	<input type="text" id="dia-LEGAL_PERSON" name="dia-LEGAL_PERSON" datasource="LEGAL_PERSON" readonly="true"/>
							    </td>
								<td><label>法人联系方式：</label></td>
							    <td>
							    	<input type="text" id="dia-LEGAL_PERSON_PHONE" name="dia-LEGAL_PERSON_PHONE" datasource="LEGAL_PERSON_PHONE" readonly="true"/>
							    </td>
							    
							</tr>
							<tr>
								<td><label>税率(%)：</label></td>
								<td>
							    	<input type="text" id="dia-TAX_RATE" name="dia-TAX_RATE" datasource="TAX_RATE" readonly="true"/>
							    </td>
								<td><label>业务联系人：</label></td>
							    <td>
							    	<input type="text" id="dia-BUSINESS_PERSON" name="dia-BUSINESS_PERSON" datasource="BUSINESS_PERSON" readonly="true"/>
							    </td>
								<td><label>业务联系方式：</label></td>
							    <td>
							    	<input type="text" id="dia-BUSINESS_PERSON_PHONE" name="dia-BUSINESS_PERSON_PHONE" datasource="BUSINESS_PERSON_PHONE" readonly="true"/>
							    </td>
							</tr>
							<tr>
								<td><label>结算周期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-OPEN_ACCOUNT" name="dia-OPEN_ACCOUNT" datasource="OPEN_ACCOUNT" datatype="0,is_digit,30"/>
							    </td>	
								<td><label>有效期：</label></td>
							    <td>
							    	<!-- <input type="text"  id="dia-EFFECTIVE_CYCLE"  name="dia-EFFECTIVE_CYCLE"   dataSource="EFFECTIVE_CYCLE" style="width:75px;"  kind="date" datatype="0,is_date,30" onclick="WdatePicker()" onblur="doCheck(this)" /> -->
						    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq"   dataSource="EFFECTIVE_CYCLE_BEGIN" style="width:75px;"  kind="date" datatype="0,is_date,30" onclick="WdatePicker()" readonly="true" />
						    			<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
									<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" dataSource="EFFECTIVE_CYCLE_END" style="width:75px;margin-left:-30px;" kind="date" datatype="0,is_date,30" onclick="WdatePicker()" readonly="true" />
							    </td>
							</tr>
							<tr>
								<td><label>质保金(元)：</label></td>
							    <td>
							    	<input type="text" id="dia-GUARANTEE_MONEY" name="dia-GUARANTEE_MONEY" datasource="GUARANTEE_MONEY" datatype="0,is_money,30"/>
							    </td>
								<td><label>质保期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-WARRANTY_PERIOD" name="dia-WARRANTY_PERIOD" datasource="WARRANTY_PERIOD" datatype="0,is_digit,30"/>
							    </td>
							</tr>
							<tr>
								<td><label>质保期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-WARRANTY_PERIOD" name="dia-WARRANTY_PERIOD" datasource="WARRANTY_PERIOD" readonly="true"/>
							    </td>
							</tr>
							<tr>
							    <td><label>追偿条款：</label></td>
							    <td colspan="5">
								  <textarea id="dia-RECOVERY_CLAUSE" style="width:450px;height:40px;" name="dia-RECOVERY_CLAUSE" datasource="RECOVERY_CLAUSE" readonly="true"></textarea>
							    </td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
							    <td colspan="5">
								  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS"  readonly="true"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>	
				<form method="post" id="dia-fm-aduit" class="editForm" style="width:99%;">
		          	<div align="left">
					  <fieldset>
							<input type="hidden" id="CONTRACT_ID" name="CONTRACT_ID" datasource="CONTRACT_ID" />
							<input type="hidden" id="CHECK_ID" name="CHECK_ID" datasource="CHECK_ID" />
							<div style="text-align:center;">
								<span>ERP编码：</span><input type="text" id="ERP_NUM" name="ERP_NUM" datasource="ERP_NUM" datatype="0,is_digit_letter,10" style="height:18px;width:50%;" ></input>
							</div>
					  </fieldset>
					</div>
				</form>	
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-pass" >归&nbsp;&nbsp;档</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一页</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>		
	</div>
	<div class="page">
       <div class="pageContent">
			<fieldset>	
			<legend align="right"><a onclick="onTitleClick('dia-part')">&nbsp;供货品种信息&gt;&gt;</a></legend>
			<div id="dia-part" style="overflow:hidden;">
					<table style="display:none;width:99%;" id="dia-tab-partinfo"  name="tablist" ref="dia-part" edit="false" >
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
		</fieldset>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一页</button></div></div></li>
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
<script type="text/javascript">
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	var row;
	$("#btn-pass").bind("click", function(event){
		var $f = $("#dia-fm-aduit");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm-aduit").combined(1) || {};
		var passUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractOperationManageAction/contractFile.ajax";
		doNormalSubmit($f,passUrl,"btn-pass",sCondition,aduitCallBack);
	});
		var selectedRows = $("#tab-contract").getSelectedRows();
		setEditValue("dia-fm-contract",selectedRows[0].attr("rowdata"));
		var ERP_NO = selectedRows[0].attr("ERP_NO");
		if(ERP_NO){
			$("#ERP_NUM").val(ERP_NO);
			$("#ERP_NUM").attr("readonly",true);
		}
		
		var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
		$("#CONTRACT_ID").val(CONTRACT_ID);
		
		$("button[name='btn-pre']").bind("click",function(event) 
				{
						$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
				});
				$("button[name='btn-next']").bind("click", function(event) 
					{
							var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
							var searchPartUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractOperationManageAction/contractPartSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
							var $f = $("#dia-fm-id");
							var sCondition = {};
					    	sCondition = $f.combined() || {};
							doFormSubmit($f,searchPartUrl,"",1,sCondition,"dia-tab-partinfo");
							var $tabs = $("#dia-tabs");
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
								default:
									break;
							}
						})
						(parseInt($tabs.attr("currentIndex")));
				 });
});
function aduitCallBack(res)
{
	try
	{
		var selectedRows = $("#tab-contract").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-contract").removeResult($row);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</div>

