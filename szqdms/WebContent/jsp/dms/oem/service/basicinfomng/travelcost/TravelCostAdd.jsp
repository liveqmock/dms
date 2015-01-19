<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%> 
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="wcxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="dia-tb-option">
				<input type="hidden" id="dia-CODE_ID" name="dia-CODE_ID" datasource="CODE_ID" />
					
				<!--  <td><input type="hidden" id="dia-STATUS" name="dia-STATUS" datasource="STATUS" value="100201" /></td>	 -->			
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE"  datasource="ORG_CODE" datatype="1,is_null,100" readOnly hasBtn="true" callFunction="openFws();" /></td>
				   		<td><label>服务商名称：</label></td>
						<td><input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" datatype="0,is_null,100" readonly value="" /></td>
						<td><input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID"  value="" /></td>
				    </tr>	
					<tr>
						<td><label>外出次数：</label></td>
						<td><select type="text" class="combox" id="dia-TRAVEL_TIMES" name="dia-TRAVEL_TIMES" datasource="TRAVEL_TIMES" kind="dic" src="WCCS" datatype="0,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
						<td><label>外出时间：</label></td>
						<td><select type="text" class="combox" id="dia-TRAVEL_DATE" name="dia-TRAVEL_DATE" datasource="TRAVEL_DATE"  kind="dic" src="WCSJ" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
				   </tr>
				   <tr>
				       <td><label>备车类型：</label></td>
						<td><select type="text" class="combox" id="dia-VEHICLE_TYPE" name="dia-VEHICLE_TYPE" datasource="VEHICLE_TYPE" kind="dic" src="WCFS" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
						<td><label>状态：</label></td>
						<td>
							  <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >
						    	<option value="-1">--</option>
					   		</select>
						</td>
				   </tr>
				     <tr  id="if_miles">  
				     	<td><label>里程(开始)：</label></td>
				        <td><input type="text" id="dia-START_MILES" name="dia-START_MILES" datasource="START_MILES" datatype="0,is_double,100"/></td>
				        <td><label>里程(结束)：</label></td>
				        <td><input type="text" id="dia-END_MILES" name="dia-END_MILES" datasource="END_MILES" datatype="0,is_double,100"/></td>
				   
				   </tr>
				   <tr> 
				     	<td><label>费用：</label></td>
				        <td><input type="text" id="dia-COST" name="dia-COST" datasource="COST" datatype="0,is_double,100"/></td>
				         <td><label>费用类别：</label></td>
						<td><select type="text" class="combox" id="dia-COSTS_TYPE" name="dia-COSTS_TYPE" datasource="COSTS_TYPE" kind="dic" src="FYLB" datatype="0,is_null,10" >
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
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/TravelCostMngAction";
var diaAction = "<%=action%>";
//初始化
$(function(){
 	$("#dia-UNIT_PRICE").attr("readonly",true);

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
		
		if($("#dia-COSTS_TYPE").attr("code")==305101){
			$("#if_miles").show();
		}else{
			$("#if_miles").hide();
		}
	
		//服务商代码不可修改
		$("#dia-ORG_CODE").attr("readonly",true);	
	    $("#dia-ORG_NAME").attr("readonly",true);			
	 
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
	/* //外出时间
	$("#dia-TRAVEL_DATE").val("302301");
	$("#dia-TRAVEL_DATE").attr("code","302301");
	$("#dia-TRAVEL_DATE").find("option").val("302301");
	$("#dia-TRAVEL_DATE").find("option").text("白天"); */
	
	
		//外出次数
	$("#dia-TRAVEL_TIMES").val("301201");
	$("#dia-TRAVEL_TIMES").attr("code","301201");
	$("#dia-TRAVEL_TIMES").find("option").val("301201");
	$("#dia-TRAVEL_TIMES").find("option").text("一次外出");
	
	//费用类别
	$("#dia-COSTS_TYPE").val("<%=DicConstant.FYLB_01%>");
	$("#dia-COSTS_TYPE").attr("code","<%=DicConstant.FYLB_01%>");
	$("#dia-COSTS_TYPE").find("option").val("<%=DicConstant.FYLB_01%>");
	$("#dia-COSTS_TYPE").find("option").text("在途补助");
	 
/* 	 //备车类型
	$("#dia-VEHICLE_TYPE").val("301301");
	$("#dia-VEHICLE_TYPE").attr("code","301301");
	$("#dia-VEHICLE_TYPE").find("option").val("301301");
	$("#dia-VEHICLE_TYPE").find("option").text("自备车"); */
	//$("#dia-TASK_TIME_RATIO").attr("readonly",true);
}


//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
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
	  $("#dia-ORG_CODE").val(orgCode);
	   $("#dia-ORG_ID").val(orgId);
	  $("#dia-ORG_NAME").val(orgName);
}
function afterDicItemClick(id, $row, selIndex){	

	if(id.indexOf("dia-COSTS_TYPE")==0){
		if($("#dia-COSTS_TYPE").attr("code")==305101){
			$("#if_miles").show();
			setDiaDefaultValue1();
		}else{
			$("#if_miles").hide();
		}
	}
	return true;
}
function setDiaDefaultValue1()
{
	<%-- //是否
	$("#dia-ifVehicle").val("<%=DicConstant.SF_01%>");
	$("#dia-ifVehicle").attr("code","<%=DicConstant.SF_01%>");
	$("#op_combox_dia-ifVehicle").find(".selected").removeClass("selected");
	$("#op_combox_dia-ifVehicle").find("a[value='<%=DicConstant.SF_01%>']").addClass("selected");
	$("a[name='dia-ifVehicle']").text("是"); --%>
}

</script>