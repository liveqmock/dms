<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" >
		<form method="post" id="dia-fm" class="editForm" style="width:99%;">
		<div align="left">
			<fieldset>
<!--			<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;盘点设置维护&gt;&gt;</a></legend>-->
			<table class="editTable" id="dia-tab-pjxx">
				<tr>					
				    <td><label>盘点编号：</label></td>				   
				    <td>
				    	<input type="text" id="dia-inventory_no" name="dia-inventory_no" datasource="INVENTORY_NO" readonly />
				    	<input type="hidden" id="dia-inventory_id" name="dia-inventory_id" datasource="INVENTORY_ID"/>        
				    </td>
				    <td><label>盘点仓库：</label></td>
				    <td>
				    	<input type="hidden" id="dia-warehouse_id" name="dia-warehouse_id" datasource="WAREHOUSE_ID"/>
                        <input type="text" id="dia-warehouse_name" name="dia-warehouse_name" datasource="WAREHOUSE_NAME" readonly/>
				    	<input type="hidden" id="dia-warehouse_code" name="dia-warehouse_code" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" datatype="1,is_null,30" readonly/>				   
				    </td>				    
				</tr>
				<tr>
					<td><label>盘点类型：</label></td>
				    <td>
				    	<input type="hidden" id="dia-inventory_type" name="dia-inventory_type" kind="dic" src="PDLX" datasource="INVENTORY_TYPE"  readonly/>
				    	<input type="hidden" id="dia-inventory_type1" name="dia-inventory_type1" datasource="INVENTORY_TYPE1" />
				    	<input type="text" id="dia-inventory_type" name="dia-inventory_type" datasource="INVENTORY_TYPE" readonly/>
				    </td>
				    <td><label>盘点人：</label></td>
				    <td>
				    	<input type="text" id="dia-inventory_user" name="dia-inventory_user" datasource="INVENTORY_USER" readonly  />
				    </td>
				</tr>
				<tr>
					<td><label>盘点时间：</label></td>
				    <td><input type="text"  id="dia-pdsj"  name=""dia-inventory_date"" style="width:75px;" datasource="INVENTORY_DATE"  readonly/></td>			
					<td><label>盘点状态：</label></td>
					    <td>
					    	<input type="text" id="in-inventory_status" name="in-inventory_status" datasource="INVENTORY_STATUS"  readonly/>
						</td>
				</tr>
				
								
				<tr>
					<td><label>备注：</label></td>
					<td colspan="5">
						<textarea id="dia-bz" name="dia-remarks" style="width:460px;" datasource="REMARKS" readonly></textarea>
					</td>
				</tr>
			</table>
			</fieldset>	
		</div>        
		</form>
<!--		<div class="formBar">-->
<!--			<ul>-->
<!--				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>-->
<!--			</ul>-->
<!--		</div>-->
		<form method="post" id="dia-fm1" class="editForm" style="width:99%;">
			<div id="dia-pjmx" style="height:290px;overflow:hidden;">
			<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" >
					<thead>
						<tr>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="AREA_CODE" >库区代码</th>
							<th fieldname="POSITION_NAME" >库位名称</th>
							<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>
							<th fieldname="PAPER_COUNT" colwidth="50">账面数量</th>
							<th fieldname="MATERIAL_COUNT" colwidth="50" >实盘数量</th>
							<th fieldname="CYSL" colwidth="50">差异数量</th>
							<th fieldname="PAPER_AMOUNT" refer="amountFormat" align="right">账面金额</th>
							<th fieldname="MATERIAL_AMOUNT" refer="amountFormat" align="right">实盘金额</th>
							<th fieldname="CYJE" refer="amountFormat" align="right">差异金额</th>
							<th fieldname="INVENTORY_RESULT" >盘点结果</th>
							<th fieldname="REMARKS" >差异原因</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
			</div>
			</form>
			<form method="post" id="dia-fm2" class="editForm" style="width:99%;">
		          	<div align="left">
					  <fieldset>
							<div style="text-align:left;">
								<span>确认意见：</span><input type="text" id="CHECK_REMARKS" name="CHECK_REMARKS" datasource="CHECK_REMARKS" datatype="0,is_null,100" style="height:20px;width:50%;" />
							</div>
					  </fieldset>
					</div>
			<div class="formBar">
				<ul>
<!--			<li><div class="buttonContent" align="left">确认意见：<input style="width:300px;" type="text" id="ww" name="ww" datatype="0,is_null,1000" /></div></li>-->
<!--				<li><div class="buttonContent" align="left">确认意见：<input style="width:300px;" type="text" id="ww" name="ww"  datasource="INVENTORY_NO"/></div></li>-->
				<li><div class="button"><div class="buttonContent"><button id="dia-pass" type="button">通&nbsp;&nbsp;过</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button id="dia-nopass" type="button">驳&nbsp;&nbsp;回</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</form>	
	</div>
</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBuInventoryAction";
var diaAction = "<%=action%>";
//初始化
$(function(){
		$("#dia-pjmx").attr("layoutH",document.documentElement.clientHeight-240);
		var selectedRows = $("#tab-list").getSelectedRows();
        setEditValue("dia-fm", selectedRows[0].attr("rowdata"));
		var INVENTORY_ID = $("#dia-inventory_id").val();
		var $f = $("#dia-fm1");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm1").combined(1) || {};
		var selectUrl = diaSaveAction + "/partList.ajax?inventory_id="+INVENTORY_ID;
		doFormSubmit($f,selectUrl,"",1,sCondition,"dia-tab-pjlb");	
		
	$("#dia-pass").bind("click",function(){
		var INVENTORY_ID = $("#dia-inventory_id").val();
		var $f = $("#dia-fm2");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm2").combined(1) || {};
		var checkUrl = diaSaveAction + "/checkUpdatePass.ajax?inventory_id="+INVENTORY_ID;
		doNormalSubmit($f,checkUrl,"dia-pass",sCondition,diaUpdateCallBack);
	});
	$("#dia-nopass").bind("click",function(){
		var INVENTORY_ID = $("#dia-inventory_id").val();
		var $f = $("#dia-fm2");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm2").combined(1) || {};
		var checkUrl = diaSaveAction + "/checkUpdateNoPass.ajax?inventory_id="+INVENTORY_ID;
		doNormalSubmit($f,checkUrl,"dia-nopass",sCondition,diaUpdateCallBack);
	});
	});
//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{	
		$("#btn-cx").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}	
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>

