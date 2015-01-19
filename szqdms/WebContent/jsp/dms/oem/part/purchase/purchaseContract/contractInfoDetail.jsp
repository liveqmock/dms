<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
<div class="tabs" eventType="click" id="dia-tabs" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:void(0)"><span>合同基本信息</span></a></li>
					    <li><a href="javascript:void(0)"><span>合同资料信息</span></a></li>
						<li><a href="javascript:void(0)"><span>供货品种信息</span></a></li>
						<li><a href="javascript:void(0)"><span>合同轨迹信息</span></a></li>
					</ul>
				</div>
			</div>
 <div class="tabsContent">
  <div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="dia-fm-id" >
		<input type="hidden" id="CONTRACT_ID" name="CONTRACT_ID" datasource="CONTRACT_ID" />
	</form>
		<form method="post" id="dia-fm-contract" class="editForm" style="width:99%;">
		<!-- 隐藏域 -->
		<input type="hidden" id="dia-CONTRACT_ID" name="dia-CONTRACT_ID" datasource="CONTRACT_ID" />
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-contract">
						<tr>
								<td><label>合同编号：</label></td>
							    <td>
							    	<input type="text" id="dia-CONTRACT_NO" name="dia-CONTRACT_NO" datasource="CONTRACT_NO" datatype="0,is_digit_letter,30"/>
							    </td>
							    <td><label>合同名称：</label></td>
							    <td>
							    	<input type="text" id="dia-CONTRACT_NAME" name="dia-CONTRACT_NAME" datasource="CONTRACT_NAME" datatype="0,is_null,30"/>
							    </td>
							    <td><label>合同类别：</label></td>
							    <td>
								    <input type="text" id="dia-CONTRACT_TYPE" name="dia-CONTRACT_TYPE" datasource="CONTRACT_TYPE" datatype="0,is_null,30"/>
								</td>
							    
							</tr>
							<tr>
								<td><label>厂家代码：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE" />
							    </td>
								<td><label>厂家名称：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="0,is_null,3000"/>
							    </td>
								
							</tr>
							<tr>
								<td><label>厂家资质：</label></td>
							    <td>
							    	<input type="text" id="dia-SUPPLIER_QUALIFY" name="dia-SUPPLIER_QUALIFY" datasource="SUPPLIER_QUALIFY" datatype="0,is_null,30"/>
							    </td>
								<td><label>厂家法人：</label></td>
							    <td>
							    	<input type="text" id="dia-LEGAL_PERSON" name="dia-LEGAL_PERSON" datasource="LEGAL_PERSON" datatype="0,is_null,30"/>
							    </td>
								<td><label>法人联系方式：</label></td>
							    <td>
							    	<input type="text" id="dia-LEGAL_PERSON_PHONE" name="dia-LEGAL_PERSON_PHONE" datasource="LEGAL_PERSON_PHONE" datatype="0,is_null,30"/>
							    </td>
							    
							</tr>
							<tr>
								<td><label>税率(%)：</label></td>
								<td>
							    	<input type="text" id="dia-TAX_RATE" name="dia-TAX_RATE" datasource="TAX_RATE" datatype="0,is_money_4,30"/>
							    </td>
								<td><label>业务联系人：</label></td>
							    <td>
							    	<input type="text" id="dia-BUSINESS_PERSON" name="dia-BUSINESS_PERSON" datasource="BUSINESS_PERSON" datatype="0,is_null,30"/>
							    </td>
								<td><label>业务联系方式：</label></td>
							    <td>
							    	<input type="text" id="dia-BUSINESS_PERSON_PHONE" name="dia-BUSINESS_PERSON_PHONE" datasource="BUSINESS_PERSON_PHONE" datatype="0,is_null,30"/>
							    </td>
							</tr>
							<tr>
								<td><label>挂账结算周期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-OPEN_ACCOUNT" name="dia-OPEN_ACCOUNT" datasource="OPEN_ACCOUNT" datatype="0,is_null,30"/>
							    </td>
								<td><label>有效期：</label></td>
							    <td>
							    	<input type="text" id="dia-EFFECTIVE_CYCLE" name="dia-EFFECTIVE_CYCLE" datasource="EFFECTIVE_CYCLE" datatype="0,is_null,30"/>
							    </td>
							</tr>
							<tr>
								<td><label>质保金(元)：</label></td>
							    <td>
							    	<input type="text" id="dia-GUARANTEE_MONEY" name="dia-GUARANTEE_MONEY" datasource="GUARANTEE_MONEY" datatype="0,is_money,30"/>
							    </td>
								<td><label>质保期(月)：</label></td>
							    <td>
							    	<input type="text" id="dia-WARRANTY_PERIOD" name="dia-WARRANTY_PERIOD" datasource="WARRANTY_PERIOD" datatype="0,is_null,30"/>
							    </td>
							</tr>
							<tr>
							    <td><label>追偿条款：</label></td>
							    <td colspan="5">
								  <textarea id="dia-RECOVERY_CLAUSE" style="width:450px;height:40px;" name="dia-RECOVERY_CLAUSE" datasource="RECOVERY_CLAUSE" datatype="1,is_null,100"></textarea>
							    </td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
							    <td colspan="5">
								  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>	
			  <div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
		</div>
	</div>	
	<div class="page">
			<div class="pageContent">  
				<div align="left">
					<div id="dia-files">
					<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
						<thead>
							<tr>
								<th fieldname="ROWNUMS" style="display:"></th>
								<th fieldname="FILE_NAME" >附件名称</th>
								<th fieldname="CREATE_USER" >上传人</th>
								<th fieldname="CREATE_TIME" >上传时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					</div>
             	</div> 
			</div>
			<div class="formBar">
				<ul>
					<li id="dia-nextH2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一页</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	<div class="page">
	<div class="pageContent" style="" >
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
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>		
    </div>	
    <div class="page">
	<div class="pageContent" style="" >
	 <fieldset>	
			<legend align="right"><a onclick="onTitleClick('dia-track')">&nbsp;合同轨迹信息&gt;&gt;</a></legend>
			<div id="dia-track" style="overflow:hidden;">
					<table style="display:none;width:99%;" id="dia-tab-trackinfo"  name="tablist" ref="dia-track" edit="false" >
							<thead>
								<tr>
									<th fieldname="CONTRACT_STATUS" >合同状态</th>
									<th fieldname="CHECK_STATUS" >节点状态</th>
									<th fieldname="CHECK_USER" >审核人</th>
									<th fieldname="CHECK_DATE" >审核时间</th>
									<th fieldname="CHECK_REMARKS" >审核意见</th>
								</tr>
							</thead>
					</table>
				</div>
		</fieldset>		
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一页</button></div></div></li>
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
var dialogViewAttach = $("body").data("dialog-viewAttachment");
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractInfoSearchManageAciton";
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	var row;
		var selectedRows = $("#tab-contract").getSelectedRows();
		setEditValue("dia-fm-contract",selectedRows[0].attr("rowdata"));
		var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
		$("#CONTRACT_ID").val(CONTRACT_ID);
    	
    	$("#dia-tab-partinfo").show();
		$("#dia-tab-partinfo").jTable();
		$("#dia-tab-trackinfo").show();
		$("#dia-tab-trackinfo").jTable();
		
		$('input').attr("readonly","readonly");
		$('textarea').attr("readonly","readonly");
		$("button[name='btn-pre']").bind("click",function(event) 
				{
						$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
				});
				$("button[name='btn-next']").bind("click", function(event) 
					{
							
							var $tabs = $("#dia-tabs");
							switch (parseInt($tabs.attr("currentIndex"))) 
							{
								case 0:
									//附件查询
									var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
									var $f = $("#dia-fm-id");
									var sCondition = {};
							    	sCondition = $("#dia-fm-id").combined() || {};
							    	var viewUrl = mngUrl+"/contractAppendixSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
							    	doFormSubmit($f.eq(0),viewUrl,"dialog-btn-search",1,sCondition,"dia-fileslb");
									break;
								case 1:
									//配件查询
									var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
									var searchPartUrl = mngUrl+"/contractPartSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
									var $f = $("#dia-fm-id");
									var sCondition = {};
							    	sCondition = $f.combined() || {};
									doFormSubmit($f,searchPartUrl,"",1,sCondition,"dia-tab-partinfo");
									break;
								case 2:
									//轨迹查询
									var CONTRACT_ID = $("#dia-CONTRACT_ID").val();
									var searchPartUrl = mngUrl+"/contractTrackSearch.ajax?CONTRACT_ID="+CONTRACT_ID;
									var $f = $("#dia-fm-id");
									var sCondition = {};
							    	sCondition = $f.combined() || {};
									doFormSubmit($f,searchPartUrl,"",1,sCondition,"dia-tab-trackinfo");
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
								case 3://第4个tab页
									break;
								default:
									break;
							}
						})
						(parseInt($tabs.attr("currentIndex")));
				 });	
});
</script>
</div>

