<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			 
			  <input type="hidden" id="dia-R_ORGID" name="dia-R_ORGID" datasource="R_ORGID" />
			  <input type="hidden" id="dia-R_ORGNAME" name="dia-R_ORGNAME" datasource="R_ORGNAME" />
			 
			<input type="hidden" id="dia-D_ORGID" name="dia-D_ORGID" datasource="D_ORGID" />
			  <!-- <input type="hidden" id="dia-D_ORGNAME" name="dia-D_ORGNAME" datasource="D_ORGNAME" /> -->
			  
			   <input type="hidden" id="dia-STATUS" name="dia-STATUS" datasource="STATUS" value="100201"/>
			    <tr>
					<td><label>旧件集中点名称：</label></td>					
					
				<td><input type="text" id="dia-R_ORGCODE" name="dia-R_ORGCODE"   datasource="R_ORGCODE" class="combox" kind="dic" src="T#TM_ORG:CODE:ONAME{ORG_ID,ONAME}:1=1 AND STATUS='100201' AND IS_IC = 100101" datatype="0,is_null,100"  /></td>
				</tr>	
		<!-- 		<tr>
					<td><label>故障模式名称：</label></td>
				    <td><input type="text" id="dia-R_ORGNAME" name="dia-R_ORGNAME"  datasource="R_ORGNAME" datatype="0,is_null,100" /></td>
				
				</tr> -->
				<tr>
					<td><label>服务站名称：</label></td>
				
					<td><input type="text" id="dia-D_ORGCODE" name="dia-D_ORGCODE"  datasource="D_ORGCODE"  datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openDW();"/></td>
					<td><label>服务站名称：</label></td>
				    <td><input type="text" id="dia-D_ORGNAME" name="dia-D_ORGNAME"  datasource="D_ORGNAME" datatype="0,is_null,100" readOnly/></td>
			
					</tr>
				<tr>
					<td><label>服务商与旧件集中点距离：</label></td>
				    <td><input type="text" id="dia-RD_MILES" name="dia-RD_MILES"  datasource="RD_MILES" datatype="0,is_null,100" /></td>
					<td><label>单价：</label></td>
				    <td><input type="text" id="dia-RD_PRICE" name="dia-RD_PRICE"  datasource="RD_PRICE" datatype="0,is_null,100" /></td>
			
				
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
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/ReturnDealerRelationMngAction";
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
		//服务商
		var dOrgCode=getNodeValue(objXML, "D_ORGCODE", 0);
		var dOrgName=getNodeValue(objXML, "D_ORGNAME", 0);
		$("#dia-D_ORGCODE").val(dOrgName);
		$("#dia-D_ORGCODE").attr("dic_code",dOrgCode);		
		
		//旧件集中点
		var rOrgCode=getNodeValue(objXML, "R_ORGCODE", 0);
		var rOrgName=getNodeValue(objXML, "R_ORGNAME", 0);
		$("#dia-R_ORGCODE").val(rOrgName);
		$("#dia-R_ORGCODE").attr("dic_code",rOrgCode);	
	    
	   }
});
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


function openDW(){
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
	   $("#dia-D_ORGCODE").attr("code",orgCode); 	   
	   $("#dia-D_ORGCODE").val(orgCode); 
	   $("#dia-D_ORGID").val(orgId);
	  $("#dia-D_ORGNAME").val(orgName);
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典
	
		//判断id中是否包含dia-D_ORGCODE的值
	if(id.indexOf("dia-R_ORGCODE") == 0)
	{
		if($row.attr("ORG_ID")){
		
		$("#dia-R_ORGID").val($row.attr("ORG_ID"));
		}
	
	/* 	if($row.attr("ORG_NAME")){
		
		$("#dia-R_ORGNAME").val($row.attr("ORG_NAME"));
		}
		 */
		
			$("#dia-R_ORGNAME").val($("#"+id).val());
		return true;
	}

	//判断id中是否包含dia-D_ORGCODE的值
	if(id.indexOf("dia-D_ORGCODE") == 0)
	{
		if($row.attr("ORG_ID")){
		
		$("#dia-D_ORGID").val($row.attr("ORG_ID"));
		}
	   $("#dia-D_ORGNAME").val($("#"+id).val());
	/* 	if($row.attr("ORG_NAME")){
		
		$("#dia-D_ORGNAME").val($row.attr("ORG_NAME"));
		} */
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