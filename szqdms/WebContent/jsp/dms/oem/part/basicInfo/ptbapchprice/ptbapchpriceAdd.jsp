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
			<input type="hidden" id="dia-RELATION_ID" name="dia-RELATION_ID" datasource="RELATION_ID" />
			<input type="hidden" id="dia-LOG_ID" name="dia-LOG_ID" datasource="LOG_ID" />
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件采购价格编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">										
					<tr>
					     <td><label>配件名称：</label></td>
						<td>
						<input type="text" id="dia-part_name" name="dia-part_name" datasource="PART_NAME" datatype="0,is_null,300"  readonly="true"/>
						<input type="hidden" id="dia-part_code" name="dia-part_code" datasource="PART_CODE" />
						<input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID" />
						</td>			
					     <td><label>供应商名称：</label></td>
					    <td><input type="text" id="dia-supplier_code"  name="dia-supplier_code" kind="dic"  datasource="SUPPLIER_CODE" datatype="0,is_null,300" readonly="true" />		
						<input type="hidden" id="dia-supplier_id" name="dia-supplier_id"  datasource="SUPPLIER_ID" />
						<input type="hidden" id="dia-supplier_name" name="dia-supplier_name"  datasource="SUPPLIER_NAME" />
						</td>	
					</tr>
					<tr>
					    <td><label>采购价格：</label></td>
					    <td><input type="text" id="dia-pch_price"  name="dia-pch_price" datasource="PCH_PRICE" datatype="0,is_digit_letter,300" value=""/>	
					    <input type="hidden" id="dia-supplier_name" name="dia-supplier_name"  datasource="SUPPLIER_NAME" />				   			    
					    </td>					    
<!--					     <td><label>计划价格：</label></td>-->
<!--					    <td><input type="text" id="dia-PLAN_PRICE"  name="dia-PLAN_PRICE" datasource="PLAN_PRICE" datatype="1,is_null,30" value=""/></td>					    -->
					</tr>		
						
																							
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction";
var diaAction = "<%=action%>";


//修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-pjlb").getSelectedRows();
		setEditValue("dia-fm-pjdawh",selectedRows[0].attr("rowdata"));
		
		$("#dia-part_id").attr("readonly",true);
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		if(!$("#dia-pch_price").val()){
			 $('#dia-pch_price').val("0.00");
		}
		
		//字典表数据显示		
		$("#dia-supplier_code").attr("code",selectedRows[0].attr("SUPPLIER_CODE"));
        $("#dia-supplier_code").val(selectedRows[0].attr("SUPPLIER_NAME"));
        $("#dia-supplier_id").val(selectedRows[0].attr("SUPPLIER_ID"));
        $("#dia-supplier_name").val(selectedRows[0].attr("SUPPLIER_NAME"));
        
        
        $("#dia-parakey").attr("code",selectedRows[0].attr("APPLY_CYCLE"));
        $("#dia-parakey").val(selectedRows[0].attr("APPLY_CYCLE"));
      
		
	}else
	{			
		setDiaDefaultValue();
	}
	function setDiaDefaultValue()
	{
		//有效标示
		$("#dia-status").val("<%=Constant.YXBS_01%>");
		$("#dia-status").attr("code","<%=Constant.YXBS_01%>");
		$("#dia-status").find("option").val("<%=Constant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");
		$("#dia-status").attr("readonly",true);
		//$("#dia-STATUS1").attr("disabled",true);
		
		//用户类型
		$("#dia-USER_TYPE").val("300102");
		$("#dia-USER_TYPE").attr("code","300102");
		$("#dia-USER_TYPE").find("option").val("300102");
		$("#dia-USER_TYPE").find("option").text("军用");

	}

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-pjdawh");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		
		var pattern = /^[0-9]+(.[0-9]{1,2})?$/; 
		var pch_price = $("#dia-pch_price").val();	
		if (!pattern.test(pch_price)) {
			alert("采购价格必须符合财务数字，如12.65或12");
			return false;
		}
		
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-pjdawh").combined(1) || {};
		//判断采购价格有无更改
		var original_price=selectedRows[0].attr("PCH_PRICE");
		var now_price=$('#dia-pch_price').val();
		if(now_price==original_price){
//       if(now_price==original_price){
       	alert("价格未做任何调整");
       }else{
       	var updateUrl = diaSaveAction + "/updatePchPrice.ajax?original_price="+original_price+"&now_price="+now_price;
//		var updateUrl = diaSaveAction + "/updatePchPrice.ajax";
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
       }
	});
});



//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id == 'dia-supplier_code'){									
        $('#dia-supplier_id').val($row.attr('SUPPLIER_ID'));
        $('#dia-supplier_name').val($row.attr('SUPPLIER_NAME'));
    }	
    if(id == 'dia-parakey'){									
        $('#dia-apply_cycle').val($row.attr('PARAVALUE2'));
    }	
	return true;
}



//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		$("#tab-pjlb").insertResult(res,0);
//		$("#btn-cx").trigger("click");
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
		var selectedIndex = $("#tab-pjlb").getSelectedIndex();
		$("#tab-pjlb").updateResult(res,selectedIndex);
//		$("#btn-cx").trigger("click");

		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function SelCallBack(obj)
	{
		$("#dia-part_id").val($(obj).attr("PART_ID"));		//配件主键
		$("#dia-part_code").val($(obj).attr("PART_CODE"));	//配件代码
		$("#dia-part_name").val($(obj).attr("PART_NAME"));	//配件名称
	}
/*TD中放大镜按钮响应事件  */
	function openPurchase()
	{
//		if(!$("#dia-SUPPLIER_ID").val()){
//			alertMsg.warn('请选择配件!')
//		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbapartsupplierrl/ptbapartsupplierrlSel.jsp";	
			$.pdialog.open(url, "ptbapartsupplierrlSel", "配件查询", options);		
//		}
		
	}


</script>