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
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件计划价格编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">

				<td><input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID" /></td>
				<td><input type="hidden" id="dia-belong_assembly" name="dia-belong_assembly" datasource="BELONG_ASSEMBLY" /></td>
				
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-part_code"  name="dia-part_code" datasource="PART_CODE" datatype="0,is_digit_letter,300" readonly="true"/>					   			    
					    </td>
					    
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-part_name"  name="dia-part_name" datasource="PART_NAME" datatype="0,is_null,300" readonly="true"/></td>
					</tr>
					<tr>						
					    <td><label>计划价格：</label></td>
					    <td><input type="text" id="dia-plan_price"  name="dia-plan_price" datasource="PLAN_PRICE" datatype="0,is_digit_letter,30" value=""/></td>
					    					     				    
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction";
var diaAction = "<%=action%>";


//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-pjlb").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		setEditValue("dia-fm-pjdawh",selectedRows[0].attr("rowdata"));
		$("#dia-in-pjdm").attr("readonly",true);
		
		
		
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		if(!$("#dia-plan_price").val()){
			 $('#dia-plan_price').val("0.00");
		}
		
	}else
	{			
		setDiaDefaultValue();
	}
	function setDiaDefaultValue()
	{
		//有效标示
		$("#dia-STATUS").val("<%=Constant.YXBS_01%>");
		$("#dia-STATUS").attr("code","<%=Constant.YXBS_01%>");
		$("#dia-STATUS").find("option").val("<%=Constant.YXBS_01%>");
		$("#dia-STATUS").find("option").text("有效");
		$("#dia-STATUS").attr("readonly",true);
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
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-pjdawh").combined(1) || {};
		
		var pattern = /^[0-9]+(.[0-9]{1,2})?$/; 
		var plan_price = $("#dia-plan_price").val();	
		if (!pattern.test(plan_price)) {
			alert("计划价格必须符合财务数字，如12.65或12");
			return false;
		}
		//判断军品价格有无更改
		var original_price=selectedRows[0].attr("PLAN_PRICE");
		var now_price=$('#dia-plan_price').val();
		if(now_price==original_price){
       	alert("价格未做任何调整");
       }else{
       	var updateUrl = diaSaveAction + "/updatePlanPrice.ajax?original_price="+original_price+"&now_price="+now_price;
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
       }
	});
});



//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典

	//判断id中是否包含dia-MODELS_CODE的值
	if(id.indexOf("dia-position_code") == 0)
	{
		if($row.attr("POSITION_ID")){
		
		$("#dia-belong_assembly").val($row.attr("POSITION_ID"));
		}
	
	
		return true;
	}
	
	
	return true;
}



//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
//		$("#tab-pjlb").insertResult(res,0);
		$("#btn-cx").trigger("click");
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
//		var selectedIndex = $("#tab-pjlb").getSelectedIndex();
//		$("#tab-pjlb").updateResult(res,selectedIndex);
		$("#btn-cx").trigger("click");

		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


</script>