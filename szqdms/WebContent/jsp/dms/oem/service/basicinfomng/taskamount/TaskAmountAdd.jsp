<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
		
			<div align="left">
			<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tb-option')">&nbsp;工时定额&gt;&gt;</a></legend>
			<table class="editTable" id="dia-tb-option">
			    <input type="hidden" id="dia-AMOUNT_ID" name="dia-AMOUNT_ID" datasource="AMOUNT_ID" />
			    <input type="hidden" id="dia-MODELS_ID" name="dia-MODELS_ID" datasource="MODELS_ID" />	   
			     <input type="hidden" id="dia-MODELS_NAME" name="dia-MODELS_NAME" datasource="MODELS_NAME" />	      
			     <input type="hidden" id="dia-POSITION_ID" name="dia-POSITION_ID" datasource="POSITION_ID" />
			      <input type="hidden" id="dia-POSITION_NAME" name="dia-POSITION_NAME" datasource="POSITION_NAME" />
			    <tr>
			    
					<td><label>工时代码：</label></td>
					<td><input type="text" id="dia-TIME_CODE" name="dia-TIME_CODE"   datasource="TIME_CODE" datatype="0,is_digit_letter,100" /></td>
					<td><label>工时名称：</label></td>
					<td><input type="text" id="dia-TIME_NAME" name="dia-TIME_NAME"  datasource="TIME_NAME" datatype="0,is_null,100" /></td>
				</tr>
				<tr>
					<!-- <td><label>用户类别：</label></td>
					<td>
					<select type="text" class="combox" id="dia-USER_TYPE" name="dia-USER_TYPE" kind="dic" src="CLYHLX" datasource="USER_TYPE" datatype="0,is_null,10" >

						 <option value="-1" selected>--</option>	
						</select>
					</td> -->
					<td><label>工时定额：</label></td>
					<td><input type="text" id="dia-AMOUNT_SET" name="dia-AMOUNT_SET" datasource="AMOUNT_SET" datatype="0,is_null,100"  /></td>
				<td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
				     <option value="-1" selected>--</option>				 
				    </select>
			    	</td>
				</tr>
			<!--	<tr>
				 	<td><label>车型：</label></td>
					<td>
					 <input type="text"  id="dia-MODELS_CODE"  name="dia-MODELS_CODE" datasource="MODELS_CODE" kind="dic"  src="T#MAIN_MODELS A:A.MODELS_CODE:A.MODELS_NAME{MODELS_ID,MODELS_CODE,MODELS_NAME}:1=1  AND A.STATUS = 100201 "  dicwidth="300" tree="false" datatype="0,is_null,30" operation="=" value="" disabledCode="true"/>
					</td>
					<td><label>车辆部位：</label></td>
					<td>
					 
					<input type="text" id="dia-POSITION_CODE" operation="right_like" multi="true" name="dia-POSITION_CODE" datasource="CLBW"  class="combox" kind="dic" src="E#车辆部位代码1=车辆部位名称1:车辆部位代码2=车辆部位名称2" datatype="0,is_null,30"/>
					 
					 <input type="text" id="dia-POSITION_CODE"  name="dia-POSITION_CODE" datasource="POSITION_CODE" kind="dic"  src="T#SE_BA_VEHICLE_POSITION A:A.POSITION_CODE:A.POSITION_NAME{POSITION_ID,POSITION_CODE,POSITION_NAME}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="0,is_null,30" operation="=" value="" disabledCode="true"/>
					 <input id="diaTest" type="hidden" value=""/>
					</td>
					<td></td>
				</tr>-->
		<!-- 		
				<tr> <td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
				     <option value="-1" selected>--</option>				 
				    </select>
			    	</td>
			    </tr> -->
				
				<tr>
					<td><label>备注：</label></td>
				    <td colspan="3"><textarea class="" rows="2" id="dia-REMARKS" name="dia-REMARKS" datasource="REMARKS" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/TaskAmountMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
   //绑定保存按钮
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
    //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#dia-fm-option")[0].reset();
	}); 
    
	//修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		var modelsCode=getNodeValue(objXML, "MODELS_CODE", 0);
		var modelsName=getNodeValue(objXML, "MODELS_NAME", 0);
		$("#dia-MODELS_CODE").val(modelsName);
		$("#dia-MODELS_CODE").attr("dic_code",modelsCode);
		
		var positionCode=getNodeValue(objXML, "POSITION_CODE", 0);
		var positionName=getNodeValue(objXML, "POSITION_NAME", 0);
		$("#dia-POSITION_CODE").val(positionName);
		$("#dia-POSITION_CODE").attr("dic_code",positionCode);
		//工时代码不可修改
		$("#dia-TIME_CODE").attr("readonly",true);
	}else
	{
		
		setDiaDefaultValue();
	}
   }
);

function setDiaDefaultValue()
{


	//有效标示
	$("#dia-STATUS").val("<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").attr("code","<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").find("option").val("<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").find("option").text("有效");
	$("#dia-STATUS").attr("readonly",true);
	//$("#dia-STATUS1").attr("disabled",true);
	
	//用户类型
	$("#dia-USER_TYPE").val("300102");
	$("#dia-USER_TYPE").attr("code","300102");
	$("#dia-USER_TYPE").find("option").val("300102");
	$("#dia-USER_TYPE").find("option").text("军用");
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典

	//判断id中是否包含dia-MODELS_CODE的值
	if(id.indexOf("dia-MODELS_CODE") == 0)
	{
		if($row.attr("MODELS_ID")){
		
		$("#dia-MODELS_ID").val($row.attr("MODELS_ID"));
		}
	
		if($row.attr("MODELS_NAME")){
		
		$("#dia-MODELS_NAME").val($row.attr("MODELS_NAME"));
		}
		return true;
	}

		//判断id中是否包含dia-POSITION_CODE的值
	if(id.indexOf("dia-POSITION_CODE") == 0)
	{
		
		if($row.attr("POSITION_ID")){
		
		$("#dia-POSITION_ID").val($row.attr("POSITION_ID"));
		}		
	   
		if($row.attr("POSITION_NAME")){
		
		$("#dia-POSITION_NAME").val($row.attr("POSITION_NAME"));
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

//弹出窗体
</script>