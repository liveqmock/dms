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
			<input type="hidden" id="dia-belong_assembly" name="dia-belong_assembly" datasource="BELONG_ASSEMBLY" />
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件供货关系信息编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">			
					<input type="hidden" id="dia-RELATION_ID" name="dia-RELATION_ID" datasource="RELATION_ID" />							
					<tr>
					    <td><label>配件名称：</label></td>
						<td>
						<input type="text" id="dia-part_name" name="dia-part_name" datasource="PART_NAME" datatype="0,is_null,300" hasBtn="true" callFunction="openPurchase('PART_ID');" readonly="true"/>
						<input type="hidden" id="dia-part_code" name="dia-part_code" datasource="PART_CODE" />
						<input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID" />
						</td>			
					     <td><label>供应商名称：</label></td>
					    <td><input type="text" id="dia-supplier_code"  name="dia-supplier_code" kind="dic"  datasource="SUPPLIER_CODE"  src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID,SUPPLIER_NAME,SUPPLIER_CODE}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %> ORDER BY SUPPLIER_ID" datatype="0,is_null,300" />		
						<input type="hidden" id="dia-supplier_id" name="dia-supplier_id"  datasource="SUPPLIER_ID" />
						<input type="hidden" id="dia-supplier_name" name="dia-supplier_name"  datasource="SUPPLIER_NAME" />
						</td>
					</tr>
<!--					<tr>-->
<!--					    <td><label>采购价格：</label></td>-->
<!--					    <td><input type="text" id="dia-PCH_PRICE"  name="dia-PCH_PRICE" datasource="PCH_PRICE" datatype="1,is_null,30" value=""/>					   			    -->
<!--					    </td>					    -->
<!--					     <td><label>计划价格：</label></td>-->
<!--					    <td><input type="text" id="dia-PLAN_PRICE"  name="dia-PLAN_PRICE" datasource="PLAN_PRICE" datatype="1,is_null,30" value=""/></td>					    -->
<!--					</tr>		-->
						
					<tr>
					     <td><label>供货周期：</label></td>
					    <td>
					    	<input type="text" id="dia-paravalue1" name="dia-paravalue1" kind="dic" src="T#USER_PARA_CONFIGURE:PARAVALUE2:PARAVALUE1{PARAVALUE2,PARAVALUE1}:1=1 AND APPTYPE='3001' AND STATUS='100201'"  datasource="PARAVALUE1" datatype="0,is_null,100" operation="=" readonly="true" />
					    	<input type="hidden" id="dia-apply_cycle" name="dia-apply_cycle" datasource="APPLY_CYCLE" />
					    </td>
					     <td><label>配件标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="dia-part_identify" name="dia-part_identify" kind="dic" src="YXBS" datasource="PART_IDENTIFY" datatype="0,is_null,100" >	
				     		<option value="-1" selected>--</option>				 
				    	</select>
			    	</td>
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
		
		//字典表数据显示		
		$("#dia-supplier_code").attr("code",selectedRows[0].attr("SUPPLIER_CODE"));
        $("#dia-supplier_code").val(selectedRows[0].attr("SUPPLIER_NAME"));
        $("#dia-supplier_id").val(selectedRows[0].attr("SUPPLIER_ID"));
        $("#dia-supplier_name").val(selectedRows[0].attr("SUPPLIER_NAME"));
        
        
        $("#dia-paravalue1").attr("code",selectedRows[0].attr("APPLY_CYCLE"));
        $("#dia-paravalue1").val(selectedRows[0].attr("APPLY_CYCLE"));
		
	}else
	{			
		setDiaDefaultValue();
	}
	function setDiaDefaultValue()
	{
		
		//配件标示
		$("#dia-part_identify").val("<%=DicConstant.YXBS_01%>");
		$("#dia-part_identify").attr("code","<%=DicConstant.YXBS_01%>");
		$("#dia-part_identify").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-part_identify").find("option").text("有效");
		
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
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-pjdawh").combined(1) || {};
		if(diaAction == "1")	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			
		}
	});
	

	
});



//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		$("#tab-pjlb").insertResult(res,0);
		//$("#btn-cx").trigger("click");
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
		doSearch();
	/* 	var selectedIndex = $("#tab-pjlb").getSelectedIndex();
		$("#tab-pjlb").updateResult(res,selectedIndex); */
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
		var options = {width:800,height:500,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
		var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbapartsupplierrl/ptbapartsupplierrlSel.jsp";	
		$.pdialog.open(url, "ptbapartsupplierrlSel", "配件查询", options);		
	
	}
	function afterDicItemClick(id,$row,selIndex){
		var ret = true;
		if(id == "dia-paravalue1")
		{
			$("#"+id).attr("APPLY_CYCLE",$row.attr("PARAVALUE2"));
			$("#"+id).attr("code",$row.attr("PARAVALUE1"));
			$("#dia-paravalue1").val($("#"+id).val());
			$("#dia-paravalue1").val($("#"+id).attr("code"));
			$("#dia-apply_cycle").val($("#"+id).attr("APPLY_CYCLE"));
		}
		if(id == "dia-supplier_code")
		{
			$("#dia-supplier_code").val($row.attr("SUPPLIER_NAME"));
			$("#dia-supplier_code").attr("code",$row.attr("SUPPLIER_CODE"));
			$("#dia-supplier_id").val($row.attr("SUPPLIER_ID"));
		}
		return ret;
	}

</script>