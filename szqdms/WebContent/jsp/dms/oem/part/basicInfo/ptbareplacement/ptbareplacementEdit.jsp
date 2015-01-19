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
			<input type="hidden" id="dia-replace_id" name="dia-replace_id" datasource="REPLACE_ID" />
			<input type="hidden" id="dia-belong_assembly" name="dia-belong_assembly" datasource="BELONG_ASSEMBLY" />
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件替换件关系信息编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">		
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-part_code"  name="dia-part_code" datasource="PART_CODE" datatype="0,is_null,300" hasBtn="true" callFunction="openPurchase(1);" readonly="true"/>					   			    
					    	<input type="hidden" id="dia-part_id" name="dia-part_id" datasource="PART_ID"/>
					    </td>
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-part_name"  name="dia-part_name" datasource="PART_NAME" datatype="0,is_null,300" readonly="true"  value=""/></td>
					</tr>
						
					<tr>					   
						<td><label>替换配件代码：</label></td>
					    <td><input type="text" id="dia-rpart_code"  name="dia-rpart_code" datasource="RPART_CODE" datatype="0,is_null,300" hasBtn="true" callFunction="openPurchase(2);" readonly="true"/>					   			    
					    	<input type="hidden" id="dia-rpart_id" name="dia-rpart_id" datasource="RPART_ID"/>
					    </td>		
					    <td><label>替换配件名称：</label></td>
					    <td><input type="text" id="dia-rpart_name"  name="dia-rpart_name" datasource="RPART_NAME" datatype="0,is_null,300" readonly="true" value=""/></td>
					</tr>
					<tr>					    
					    <td><label>有效标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,100" >	
				     		<option value="-1" selected>--</option>				 
				    	</select>    					    
					</tr>					
					<tr>
						<td><label>备注：</label></td>
						<td colspan="4"><textarea id="dia-remarks" style="width:89%;height:40px;" name="dia-remarks" datasource="REMARKS"  datatype="1,is_null,1000"></textarea></td>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaReplacementAction";
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
		$("#dia-part_id").attr("readonly",true);
		
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		
		//车辆总成字典表数据显示		
		var positioncode=getNodeValue(objXML, "POSITION_CODE", 0);
		var positionname=getNodeValue(objXML, "POSITION_NAME", 0);
		$("#dia-position_code").val(positionname);
		$("#dia-position_code").attr("code",positioncode);
		
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
		var $f = $("#dia-fm-pjdawh");
		if (submitForm($f) == false) return false;
		var sCondition = {};
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
function SelCallBack(obj,flag)
	{
//	alert(flag);
		if(flag=="1"){
			$("#dia-part_id").val($(obj).attr("PART_ID"));			//配件主键
			$("#dia-part_code").val($(obj).attr("PART_CODE"));		//配件代码
			$("#dia-part_name").val($(obj).attr("PART_NAME"));		//配件名称
		}
		else{
			$("#dia-rpart_id").val($(obj).attr("PART_ID"));		//替换配件主键
			$("#dia-rpart_code").val($(obj).attr("PART_CODE"));	//替换配件代码
			$("#dia-rpart_name").val($(obj).attr("PART_NAME"));	//替换配件名称
		}
	}
/*TD中放大镜按钮响应事件  */
	function openPurchase(flag)
	{
//		alert(flag);
//		if(!$("#dia-SUPPLIER_ID").val()){
//			alertMsg.warn('请选择配件!')
//		}else{
			var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
			var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbareplacement/ptbareplacementSel.jsp?flag="+flag;	
			$.pdialog.open(url, "ptbareplacementSel", "配件查询", options);		
//		}
		
	}

</script>