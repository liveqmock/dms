<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="jlxsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-tb-option">
			   <input type="hidden" id="dia-COEFFICIENT_ID" name="dia-COEFFICIENT_ID" datasource="COEFFICIENT_ID" />
				    <input type="hidden" id="dia-MODELS_ID" name="dia-MODELS_ID" datasource="MODEL_ID" />	   
			     <input type="hidden" id="dia-MODELS_NAME" name="dia-MODELS_NAME" datasource="MODELS_NAME" />	  
	<!-- 		      <input type="hidden" id="dia-STATUS" name="dia-STATUS" datasource="STATUS" />	     -->      
	
				<tr>
				<!-- <td><label>服务商代码：</label></td>
						<td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE"  datasource="ORG_CODE" datatype="1,is_null,100" readOnly hasBtn="true" callFunction="openFws();" /></td>
	 -->	    	<td><label>激励类别：</label></td>
			    	<td><select type="text"  kind="dic" src="JLLX"  id="dia-COEFFICIENT_TYPE" name="dia-COEFFICIENT_TYPE" datasource="COEFFICIENT_TYPE"  datatype="0,is_null,100">
			    	 		<option value=1 selected>--</option>
			    	 	</select>
			    	</td>
			    	<td><label>激励系数：</label></td>
					<td><input type="text" id="dia-COEFFICIENT_RADIO" name="dia-COEFFICIENT_RADIO" datasource="COEFFICIENT_RADIO"  datatype="0,is_double,100" /></td>
			
			    </tr>			    
			    <tr>
					<td><label>车型：</label></td>
					<td>
					 <input type="text"  id="dia-MODELS_CODE"  name="dia-MODELS_CODE" datasource="MODELS_CODE" kind="dic"  src="T#MAIN_MODELS A:A.MODELS_CODE:A.MODELS_NAME{MODELS_ID,MODELS_CODE,MODELS_NAME}:1=1  AND A.STATUS = 100201 "  dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
					</td>
					<td ><label>发动机型号：</label></td>
			    	<td >
			    		 <input type="text"   kind="dic" src="T#SE_BA_ENGINE_TYPE A:A.TYPE_CODE:A.TYPE_NAME{TYPE_ID}:1=1  AND A.STATUS = 100201"  id="dia-TYPE_CODE" name="dia-TYPE_CODE" datasource="TYPE_CODE"  datatype="1,is_null,100">
			    	 	</td>
			    	  <input type="hidden" id="dia-ENGINE" name="dia-ENGINE" datasource="ENGINE" />
				</tr>
				<tr>
					 <td><label>销售起始日期（天）：</label></td>
						    <td>
					    		<input type="text" id="dia-START_DATE" group="dia-START_DATE,dia-END_DATE"   name="dia-START_DATE" datasource="START_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
					    </td>
					      <td><label>销售截止日期（天）：</label></td>
						    <td>
								<input type="text" id="dia-END_DATE" group="dia-START_DATE,dia-END_DATE"    name="dia-END_DATE" datasource="END_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
					   		</td>
					 	</tr>
					<tr> 	
					<td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
				     <option value="-1" selected>--</option>				 
				    </select>
			    	</td>
			    		</tr>	 	
				<tr>
					<td><label>备注：</label></td>
					<td colspan="3"><textarea id="dia-REMARKS" name="dia-REMARKS" datasource="REMARKS"   class="dos placeholder collect" rows="2" style="width:100%" datatype="1,is_null,500"  placeholder="请输入文字内容，支持普通文本和网址！"/></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/ExcitationCoefficientMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
   //绑定保存按钮
	$("#btn-save").bind("click", function(event){
		var kssj =$("#dia-START_DATE").val();
		var jssj =$("#dia-END_DATE").val();
		if(jssj==""){
			jssj=0;
		}
		if(kssj==""){
			kssj=0;
		}
		if(kssj>jssj){
			alertMsg.warn("开始日期不可大于结束日期！");
			return false;
		}
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
	   //$("#dia-USER_CODE").attr("dic_code",userCode);
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		var modelsCode=getNodeValue(objXML, "MODELS_CODE", 0);
		var modelsName=getNodeValue(objXML, "MODELS_NAME", 0);
		$("#dia-MODELS_CODE").val(modelsName);
		$("#dia-MODELS_CODE").attr("dic_code",modelsCode);
		
		var typeCode=getNodeValue(objXML, "TYPE_CODE", 0);
		var typeName=getNodeValue(objXML, "TYPE_NAME", 0);
		$("#dia-TYPE_CODE").val(typeName);
		$("#dia-TYPE_CODE").attr("dic_code",typeCode);
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
	//激励类型
	$("#dia-COEFFICIENT_TYPE").val("303301");
	$("#dia-COEFFICIENT_TYPE").attr("code","303301");
	$("#dia-COEFFICIENT_TYPE").find("option").val("303301");
	$("#dia-COEFFICIENT_TYPE").find("option").text("大客户");
	$("#dia-COEFFICIENT_TYPE").attr("readonly",true);
	//$("#dia-STATUS1").attr("disabled",true);
	
 
}
//字典
function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="dia-TYPE_CODE")
	{   
		$("#dia-TYPE_NAME").val($("#"+id).val());
		
		$("#dia-ENGINE").val($row.attr("TYPE_ID"));
		return true;
	}
	
	
	//判断id中是否包含dia-MODELS_CODE的值
	if(id.indexOf("dia-MODELS_CODE") == 0)
	{
		if($row.attr("MODELS_ID")){
		
		$("#dia-MODELS_ID").val($row.attr("MODELS_ID"));
		}	  
		if($row.attr("MODELS_NAME")){
		
		$("#dia-MODELS_NAME").val($("#"+id).val());
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
	//$("#tab-list").insertResult(res,0);	
		$("#btn-search").trigger("click");
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
		/* var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex); */
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function openFws(){
	var id="dia-ORG_CODE";
	var busType=2;
	/* 	showOrgTree(id,busType); */
		
	//最后一个参数1表示复选；2表示单选
	showOrgTree(id,busType,'',"2");
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;	 
	 	
	   $("#dia-ORG_CODE").attr("code",orgCode); 
	   $("#dia-ORG_ID").val(orgId);
	  $("#dia-ORG_NAME").val(orgName);
}
</script>