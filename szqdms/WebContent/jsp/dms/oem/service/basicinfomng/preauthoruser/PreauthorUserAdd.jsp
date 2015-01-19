<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="ysqryxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="dia-tb-option">
				   <input type="hidden" id="dia-USER_ID" name="dia-USER_ID" datasource="USER_ID" />
		     		<input type="hidden" id="dia-CREATE_USER" name="dia-CREATE_USER" datasource="CREATE_USER" />
		     		<input type="hidden" id="dia-CREATE_TIME" name="dia-CREATE_TIME" datasource="CREATE_TIME" />
		     		    
					<tr>
						<td><label>员工名称：</label></td>
						<td><input type="text" id="dia-USER_CODE" name="dia-USER_CODE" datasource="USER_CODE" kind="dic" src="T#TM_USER A: A.ACCOUNT:A.PERSON_NAME{ACCOUNT,PERSON_NAME}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
 						<td><input type="hidden" id="dia-USER_NAME" name="dia-USER_NAME" datasource="USER_NAME" /></td>
					
					    <td><label>状态：</label></td>
					 	<td>
							  <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,10" >
						    	<option value="-1">--</option>
					   		</select>
						</td> 
		            </tr>
					 	<tr>
					 	<td><label>预授权级别：</label></td>
						<td>  <input type="text" id="dia-LEVEL_CODE"  name="dia-LEVEL_CODE" datasource="LEVEL_CODE" kind="dic" src="T#SE_BA_PREAUTHOR_LEVEL A: A.LEVEL_CODE:A.LEVEL_NAME{LEVEL_ID,LEVEL_NAME}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
				        </td>
				         <td><input type="hidden" id="dia-LEVEL_ID" name="dia-LEVEL_ID" datasource="T.LEVEL_ID" /></td>  
			             <td><input type="hidden" id="dia-LEVEL_NAME" name="dia-LEVEL_NAME" datasource="LEVEL_NAME" /></td>	      
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
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorUserMngAction";
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
		var levelCode=getNodeValue(objXML, "LEVEL_CODE", 0);
		var levelName=getNodeValue(objXML, "LEVEL_NAME", 0);
		$("#dia-LEVEL_CODE").val(levelName);
		$("#dia-LEVEL_CODE").attr("dic_code",levelCode);
		
		
		var userCode=getNodeValue(objXML, "USER_CODE", 0);
		var userName=getNodeValue(objXML, "USER_NAME", 0);
		$("#dia-USER_CODE").val(userName);
		$("#dia-USER_CODE").attr("dic_code",userCode);
		
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
 
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	
	//判断id中是否包含dia-LEVEL_CODE的值
	if(id.indexOf("dia-USER_CODE") == 0)
	{
	
		if($row.attr("PERSON_NAME")){
		
		$("#dia-USER_NAME").val($row.attr("PERSON_NAME"));
		}
		return true;
	}	
	

	//判断id中是否包含dia-LEVEL_CODE的值
	if(id.indexOf("dia-LEVEL_CODE") == 0)
	{
		if($row.attr("LEVEL_ID")){
		
		$("#dia-LEVEL_ID").val($row.attr("LEVEL_ID"));
		}
	
		if($row.attr("LEVEL_NAME")){
		
		$("#dia-LEVEL_NAME").val($row.attr("LEVEL_NAME"));
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
</script>