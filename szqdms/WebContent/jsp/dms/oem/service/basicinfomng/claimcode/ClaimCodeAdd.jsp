<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>    
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";		
  User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String codeIds = request.getParameter("codeIds");		
%>
<div id="spjbcsxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="dia-tb-option">
				 <input type="hidden" id="dia-CODE_ID" name="dia-CODE_ID" datasource="CODE_ID" />		 		
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE"  datasource="ORG_CODE" datatype="1,is_null,100" readOnly hasBtn="true" callFunction="openFws();" /></td>
				   		<td><label>服务商名称：</label></td>
						<td><input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" datatype="0,is_null,100" readonly value="" /></td>
						<td><input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID"  value="" /></td>
				    </tr>
				    <tr>
						<td><label>基础工时(元)：</label></td>
						<td><input type="text" id="dia-BASE_TASK_TIME" name="dia-BASE_TASK_TIME"  datasource="BASE_TASK_TIME"  datatype="0,is_double,100"  blurback="true" /></td>
						<td><label>工时系数：</label></td>
						<td><input type="text" id="dia-TASK_TIME_RATIO" name="dia-TASK_TIME_RATIO" datasource="TASK_TIME_RATIO"  datatype="0,is_double,100"  blurback="true" /></td>
					</tr>
					<tr>
			<!-- 			<td><label>用户类别：</label></td>
					   <td>
					     <select type="text" class="combox" id="dia-USER_TYPE" name="dia-USER_TYPE" kind="dic" src="CLYHLX" datasource="USER_TYPE" datatype="0,is_null,10" >
						 <option value="-1" selected>--</option>	
						 </select>
					</td> -->
					<td><label>时间类型：</label></td>
						<td><select type="text" class="combox" id="dia-TIME_TYPE" name="dia-TIME_TYPE" datasource="TIME_TYPE"  kind="dic" src="WCSJ" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
						<td><label>工时单价(元)：</label></td>
						<td><input type="text" id="dia-UNIT_PRICE" name="dia-UNIT_PRICE" datasource="UNIT_PRICE" readonly datatype="0,is_double,100" /></td>
					</tr>
					
					<tr>
					    <td><label>状态：</label></td>
						<td>
							  <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >
						    	 <option value="-1" selected>--</option>	
					   		</select>
						</td>
					</tr>	
						<tr>
						    <td><label>起始时间（天）：</label></td>
						    <td>
					    		<input type="text" id="dia-START_DATE" group="dia-START_DATE,dia-END_DATE"   name="dia-START_DATE" datasource="START_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
					    </td>
					      <td><label>结束时间（天）：</label></td>
						    <td>
								<input type="text" id="dia-END_DATE" group="dia-START_DATE,dia-END_DATE"    name="dia-END_DATE" datasource="END_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
					   		</td>
					 	</tr>
					 	<tr>
						<td colspan="2"><label><font color="red">*注：工时单价=基础工时*工时系数，工时单价不可编辑</font></label></td>
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
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/ClaimCodeMngAction";
var diaAction = "<%=action%>";
var codeIds="<%=codeIds%>";
//初始化
$(function(){
   //绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//开始时间不能大于结束时间
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
		
		}
		
		else if(diaAction == 2)	//插入动作
		{
			var addUrl = diaSaveAction + "/update.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaUpdateCallBack);
		
		}
		else	//更新动作
		{
		 	var updateUrl = diaSaveAction + "/updateAll.ajax?codeIds="+codeIds;
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	
	});
    //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#dia-fm-option")[0].reset();
	}); 
    
	//修改操作
	if(diaAction == "2")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));
	
		//服务商代码不可修改
		$("#dia-ORG_CODE").attr("readonly",true);	
		$("#dia-TASK_TIME_RATIO").attr("readonly",true);	
	 
	}else if(diaAction == "3")
	{
	/* 	var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata")); */		
	    $("#dia-CODE_ID").attr(codeIds);
		//服务商代码不可修改
		$("#dia-ORG_CODE").attr("readonly",true);		
		 //
		$("#dia-TASK_TIME_RATIO").val("1");
		$("#dia-TASK_TIME_RATIO").attr("readonly",true);
	}	
	else
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
	//用户类型
	$("#dia-USER_TYPE").val("300102");
	$("#dia-USER_TYPE").attr("code","300102");
	$("#dia-USER_TYPE").find("option").val("300102");
	$("#dia-USER_TYPE").find("option").text("军用");
	//
	$("#dia-TASK_TIME_RATIO").val("1");
	$("#dia-TASK_TIME_RATIO").attr("readonly",true);
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
	 	$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
		/* var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent(); */
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
/* 




$(function(){
	//初始化页面
	$("#XZ_GSDJ").attr("readonly",true);
	if(action != "1")
	{
		$("#XZ_FWSDM").val("服务商代码1");
		//服务商代码不可修改
		$("#XZ_FWSDM").attr("readonly",true);
		$("#XZ_JCGS").val("30.00");
		$("#XZ_GSXS").val("1.00");
		$("#XZ_GSDJ").val("30.00");
	}
}); */


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
function blurBack(tempId)
{
	if(!tempId) return false;
	if(tempId && (tempId == "dia-BASE_TASK_TIME"||tempId == "dia-TASK_TIME_RATIO"))
	{
	var baseTaskTime=$("#dia-BASE_TASK_TIME").val();
 	var taskTimeRatio=$("#dia-TASK_TIME_RATIO").val();
 	var unitPrice=0;
 	if(baseTaskTime!=null&&taskTimeRatio!=null){
 	unitPrice=baseTaskTime*taskTimeRatio;
 	$("#dia-UNIT_PRICE").val(unitPrice);
 	}
	}
}

</script>