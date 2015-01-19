<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>   
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="gzmsgsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-tb-fault">
			 <input type="hidden" id="dia-RELATION_ID" name="dia-RELATION_ID" datasource="RELATION_ID" />
			 
			  <input type="hidden" id="dia-PATTERN_ID" name="dia-PATTERN_ID" datasource="PATTERN_ID" />
			  <!-- <input type="hidden" id="dia-FAULT_PATTERN_NAME" name="dia-FAULT_PATTERN_NAME" datasource="FAULT_PATTERN_NAME" /> -->
			 
			<input type="hidden" id="dia-AMOUNT_ID" name="dia-AMOUNT_ID" datasource="AMOUNT_ID" />
			  <input type="hidden" id="dia-TIME_NAME" name="dia-TIME_NAME" datasource="TIME_NAME" />
			    <tr>
					<td><label>故障模式代码：</label></td>					
					
				<!-- <td><input type="text" id="dia-FAULT_PATTERN_CODE" name="dia-FAULT_PATTERN_CODE"   datasource="FAULT_PATTERN_CODE"  dicwidth="350" kind="dic" src="T#SE_BA_FAULT_PATTERN A:A.FAULT_CODE:A.FAULT_NAME{PATTERN_ID,FAULT_CODE,FAULT_NAME}:1=1 " datatype="0,is_null,100"  /></td> -->
				<td><input type="text" id="dia-FAULT_PATTERN_CODE" name="dia-FAULT_PATTERN_CODE"   datasource="FAULT_PATTERN_CODE"  hasBtn="true" readonly datatype="0,is_null,30" callFunction="openFault()"/></td>
		    	<td><label>故障模式名称：</label></td>
				    <td><input type="text" id="dia-FAULT_PATTERN_NAME" name="dia-FAULT_PATTERN_NAME"  datasource="FAULT_PATTERN_NAME"  readonly datatype="0,is_null,100" /></td>
				
				</tr> 
				<tr>
					<td><label>工时名称：</label></td>
					<td><input type="text" id="dia-TIME_CODE" name="dia-TIME_CODE"   datasource="TIME_CODE" dicwidth="350" kind="dic" src="T#SE_BA_TASK_AMOUNT A:A.TIME_CODE:A.TIME_NAME{AMOUNT_ID,TIME_CODE,TIME_NAME}:1=1 " datatype="0,is_null,100"  /></td>
					<td><input type="hidden"/></td>
					<td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction";
var diaAction = "<%=action%>";
$(function(){

	$("#btn-save").bind("click", function(event){

			//获取需要提交的form对象
		var $f = $("#dia-fm-option");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-option").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{
			var updateUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaInsertCallBack);

		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
		
	});

	  //修改操作
	if(diaAction != "1")
	{
	   var selectedRows = $("#tab-list").getSelectedRows();	   
		setEditValue("dia-tb-fault",selectedRows[0].attr("rowdata"));		 
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		var timeCode=getNodeValue(objXML, "TIME_CODE", 0);
		var timeName=getNodeValue(objXML, "TIME_NAME", 0);
		$("#dia-TIME_CODE").val(timeName);
		$("#dia-TIME_CODE").attr("dic_code",timeCode);		
		//工时代码不可修改
		$("#dia-FAULT_PATTERN_CODE").attr("readonly",true);
	    
	   }else
		{
			
			setDiaDefaultValue();
		}
});

function setDiaDefaultValue()
{


	//有效标示
	$("#dia-STATUS").val("<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").attr("code","<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").find("option").val("<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").find("option").text("有效");
	$("#dia-STATUS").attr("readonly",true);
	//$("#dia-STATUS1").attr("disabled",true);	
	
}
function openFault()
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/dms/oem/service/basicinfomng/faulttasktime/faulttasktimeSel.jsp";	
	$.pdialog.open(url, "faulttasktimeSel", "故障模式查询", options);				
}
function SelCallBack(obj)
{
	
		$("#dia-PATTERN_ID").val($(obj).attr("PATTERN_ID"));
		$("#dia-FAULT_PATTERN_CODE").val($(obj).attr("FAULT_CODE"));
		$("#dia-FAULT_PATTERN_NAME").val($(obj).attr("FAULT_NAME"));
}
//新增回调函数
function diaInsertCallBack(res)
{
	 
	try
	{
	$("#tab-list").insertResult(res,0);	
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{		
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典
	
		//判断id中是否包含dia-TIME_CODE的值
	if(id.indexOf("dia-FAULT_PATTERN_CODE") == 0)
	{
		if($row.attr("PATTERN_ID")){
		
		$("#dia-PATTERN_ID").val($row.attr("PATTERN_ID"));
		}
	
		if($row.attr("FAULT_NAME")){
		
		$("#dia-FAULT_PATTERN_NAME").val($row.attr("FAULT_NAME"));
		}
		return true;
	}

	//判断id中是否包含dia-TIME_CODE的值
	if(id.indexOf("dia-TIME_CODE") == 0)
	{
		if($row.attr("AMOUNT_ID")){
		
		$("#dia-AMOUNT_ID").val($row.attr("AMOUNT_ID"));
		}
	
		if($row.attr("TIME_NAME")){
		
		$("#dia-TIME_NAME").val($row.attr("TIME_NAME"));
		}
		return true;
	}
 
	return true;
}

/* function doSave(){
	var $f = $("#dia-fm-option");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
} */

</script>