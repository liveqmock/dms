<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-pjdawh" class="editForm" >
		<input type="hidden" id="dia-supplier_id" name="dia-supplier_id" datasource="SUPPLIER_ID" />
		<input type="hidden" id="dia-belong_assembly" name="dia-belong_assembly" datasource="BELONG_ASSEMBLY" />
			<div align="left">
				<fieldset>
				<table class="editTable" id="dia-tab-pjxx">
					<tr>
						<td><label>供应商代码：</label></td>
					    <td><input type="text" id="dia-supplier_code"  name="dia-supplier_code" datasource="SUPPLIER_CODE" datatype="0,is_digit_letter,7" value=""/></td>
					    <td><label>供应商名称：</label></td>
					    <td><input type="text" id="dia-supplier_name"  name="dia-supplier_name" datasource="SUPPLIER_NAME" datatype="0,is_null,300" value=""/></td>	
					    <td><label>ERP编码：</label></td>
					    <td><input type="text" id="dia-erp_no"  name="dia-erp_no" datasource="ERP_NO" datatype="0,is_null,300" value=""/></td>					    																					   			   			    
					</tr>
					<tr>
					    <td><label>厂家资质：</label></td>
					    <td><input type="text" id="dia-SUPPLIER_QUALIFY"  name="dia-SUPPLIER_QUALIFY" datasource="SUPPLIER_QUALIFY" datatype="0,is_null,300" value=""/></td>
					    <td><label>质保金：</label></td>
					    <td><input type="text" id="dia-GUARANTEE_MONEY"  name="dia-GUARANTEE_MONEY" datasource="GUARANTEE_MONEY" datatype="1,is_null,300" value=""/></td>
						<td><label>质保期：</label></td>
					    <td><input type="text" id="dia-WARRANTY_PERIOD"  name="dia-WARRANTY_PERIOD" datasource="WARRANTY_PERIOD" datatype="1,is_null,300" value=""/></td>
					</tr>	
					<tr>
						<!-- --> <td><label>税票类型：</label></td>
					    <!-- <td><input type="text" id="dia-parakey_lx"  name="dia-parakey_lx" datasource="PARAKEY" kind="dic"  src="T#USER_PARA_CONFIGURE:PARAKEY:PARAVALUE1{PARAKEY,PARAVALUE1}:1=1 AND APPTYPE='SPLX'"   datatype="1,is_null,50" operation="="  />
					    	<input type="hidden" id="dia-tax_type" name="dia-tax_type" datasource="TAX_TYPE" />
					    	<input id="dia-out" type="hidden"/>	
					    </td> -->
					    <td><input type="text" id="dia-INVOICE_TYPE" name="dia-INVOICE_TYPE" datasource="TAX_TYPE" kind="dic" src="FPLX" datatype="0,is_null,300"/></td>
						<td><label>税率：</label></td>
					    <td><input type="text" id="dia-parakey_sl"  name="dia-parakey_sl" datasource="PARAKEY"  kind="dic"  src="T#USER_PARA_CONFIGURE:PARAKEY:PARAVALUE1{PARAVALUE1}:1=1 AND APPTYPE='SL'"   datatype="1,is_null,50" operation="="  />
					    	<input type="hidden" id="dia-tax_rate" name="dia-tax_rate" datasource="TAX_RATE" />
					    	<input id="dia-out" type="hidden"/>	
					    </td>
					    <td><label>税号：</label></td>
					    <td><input type="text" id="dia-tax_no"  name="dia-tax_no" datasource="TAX_NO" datatype="0,is_digit_letter,50" value=""/></td>
					</tr>	
					<tr>
						<td><label>法人：</label></td>
					    <td><input type="text" id="dia-legal_person"  name="dia-legal_person" datasource="LEGAL_PERSON" datatype="0,is_null,30" value=""/></td>
					    <td><label>法人联系方式：</label></td>
					    <td><input type="text" id="dia-LEGAL_PERSON_PHONE"  name="dia-LEGAL_PERSON_PHONE" datasource="LEGAL_PERSON_PHONE" datatype="0,is_null,30" value=""/></td>
						<td><label>传真：</label></td>
					    <td><input type="text" id="dia-FAX_NO"  name="dia-FAX_NO" datasource="FAX_NO" datatype="0,is_null,30" value=""/></td>
					</tr>	
					<tr>
						<td><label>业务联系人：</label></td>
					    <td><input type="text" id="dia-BUSINESS_PERSON"  name="dia-BUSINESS_PERSON" datasource="BUSINESS_PERSON" datatype="1,is_null,30" value=""/></td>
					    <td><label>业务联系方式：</label></td>
					    <td><input type="text" id="dia-BUSINESS_PERSON_PHONE"  name="dia-BUSINESS_PERSON_PHONE" datasource="BUSINESS_PERSON_PHONE" datatype="1,is_null,30" value=""/></td>
						<td><label>结算周期：</label></td>
					    <td><input type="text" id="dia-OPEN_ACCOUNT"  name="dia-OPEN_ACCOUNT" datasource="OPEN_ACCOUNT" datatype="0,is_null,30" value=""/></td>
					</tr>
					<tr>
						<td><label>开户银行：</label></td>
					    <td><input type="text" id="dia-open_bank"  name="dia-open_bank" datasource="OPEN_BANK" datatype="0,is_null,50" value=""/></td>					    
						<td><label>银行账号：</label></td>
					    <td><input type="text" id="dia-account"  name="dia-account" datasource="ACCOUNT" datatype="0,is_digit_letter,50" value=""/></td>
					    <td><label>是否军品供应商：</label></td>
					    <td>
					    	<select type="text" id="dia-in-sfzdgys"  name="dia-in-sfzdgys" kind="dic" src="SF" datasource="IF_ARMY"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>	
					</tr>	
					<tr>		    					     
					    <td><label>配件标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="dia-part_identify" name="dia-part_identify" kind="dic" src="YXBS" datasource="PART_IDENTIFY" datatype="0,is_null,30" >	
				     		<option value="-1" selected>--</option>			 
				    	</select>
			    		</td>	    
					</tr>	
					<tr>
						<td><label>追偿条款：</label></td>
						<td colspan="3"><textarea id="dia-RECOVERY_CLAUSE" style="width:89%;height:40px;" name="dia-RECOVERY_CLAUSE" datasource="RECOVERY_CLAUSE"  datatype="1,is_null,100"></textarea></td>
					</tr>														
					<tr>
						<td><label>备注：</label></td>
						<td colspan="3"><textarea id="dia-ta-bz" style="width:89%;height:40px;" name="dia-ta-bz" datasource="REMARKS"  datatype="1,is_null,100"></textarea></td>
					</tr>
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button"  id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaSupplierAction";
var diaAction = "<%=action%>";
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-pjdawh");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-pjdawh").combined(1) || {};
		if(diaAction == "1")	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			
		}
	});
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-order_info").getSelectedRows();
		setEditValue("dia-fm-pjdawh",selectedRows[0].attr("rowdata"));
		$("#dia-supplier_code").attr("readonly",true);
		
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		$("#dia-parakey_lx").attr("code",selectedRows[0].attr("TAX_TYPE"));
        $("#dia-parakey_lx").val(selectedRows[0].attr("TAX_TYPE"));
        
        $("#dia-parakey_sl").attr("code",selectedRows[0].attr("TAX_RATE"));
        $("#dia-parakey_sl").val(selectedRows[0].attr("TAX_RATE"));
	}

	
});


//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id == 'dia-parakey_lx'){									
        $('#dia-tax_type').val($row.attr('TAX_TYPE'));
    }	
    if(id == 'dia-parakey_sl'){									
        $('#dia-tax_rate').val($row.attr('PARAVALUE1'));
    }	
	return true;
}



//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		$("#tab-order_info").insertResult(res,0);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}



//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-order_info").getSelectedIndex();
		$("#tab-order_info").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
		$("#btn-search").trigger("click");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


</script>