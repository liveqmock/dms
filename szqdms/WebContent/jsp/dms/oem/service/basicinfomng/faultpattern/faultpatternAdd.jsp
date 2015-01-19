<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-faultpatternInfo" class="editForm" >
        	<!-- 隐藏域 -->
		    <input type="hidden" id="dia-patternId" name="dia-patternId" datasource="PATTERN_ID" />
		    <input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		    <input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />		
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-faultpatternInfo">
			    <tr>
					<td><label>故障模式代码：</label></td>
					<td><input type="text" id="dia-faultcode" name="dia-faultcode" datasource="FAULT_CODE" datatype="0,is_digit_letter,30" /></td>
					<td><label>故障模式名称：</label></td>
					<td><input type="text" id="dia-faultname" name="dia-faultname" datasource="FAULT_NAME" datatype="0,is_null,300" /></td>
				</tr>
				<tr>
					<td><label>车辆部位：</label></td>
					<td><input type="text" id="dia-positioncode" name="dia-positioncode" datasource="POSITION_CODE"  hasBtn="true" datatype="1,is_null,30" callFunction="openVehiclePosition()"/>
						<input type="hidden" id="dia-positionid" name="dia-positionid" datasource="POSITION_ID"/>
						<input type="hidden" id="dia-positionname" name="dia-positionname" datasource="POSITION_NAME"/>	
					</td>
					<td><label>故障类别：</label></td>
					<td><input type="text" id="dia-faultpatterncode" name="dia-faultpatterncode" datasource="FAULT_PATTERN_CODE" operation="like" kind="dic" src="T#SE_BA_CODE:CODE:NAME{CODE_ID}:CODE_TYPE=302702 AND STATUS=100201" datatype="0,is_null,30" /> 
						<input type="hidden" id="dia-faultpatternname" name="dia-faultpatternname" datasource="FAULT_PATTERN_NAME"/>
						<input type="hidden" id="dia-codeid" name="dia-codeid" datasource="CODE_ID"/>
					</td>
				</tr>
				<tr>
					<td><label>严重程度：</label></td>
					<td><input type="text" id="dia-severity" name="dia-severity" datasource="SEVERITY" operation="like" kind="dic" src="T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302703 AND STATUS=100201" datatype="0,is_null,30" />
					     <input type="hidden" id="dia-name" name="dia-name" datasource="NAME"/>
					</td>	
					<td><label>有效标示：</label></td>
					<td>
		        		<select id="dia-status"  name="dia-status" datasource="STATUS" kind="dic" src="YXBS" datatype="0,is_null,6" >
				    		<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    	</select>
		        	</td>
				</tr>
                <tr>
					<td><label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label></td>
					<td colspan="3">
						<textarea id="dia-remarks" style="width:89%;height:40px;" name="dia-remarks" datasource="REMARKS"  datatype="1,is_null,1000"></textarea>
					</td>			
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

var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/FaultPatternMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-faultpatternInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-faultpatternInfo").combined(1) || {};
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
	
	//绑定重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#fm-faultpatternInfo")[0].reset();
	}); 
	
	//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-faultpatternlist").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		setEditValue("fm-faultpatternInfo",selectedRows[0].attr("rowdata"));
		
		$("#dia-faultcode").attr("readonly",true);
		
		////////////////////////////////////////////////
		//从后台获取的值赋值给编辑打开时默认的初始值，将name赋给code
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		//把车辆部位的名称赋给车辆代码表示项		
		var positioncode=getNodeValue(objXML, "POSITION_CODE", 0);
		var positionname=getNodeValue(objXML, "POSITION_NAME", 0);	
		$("#dia-positioncode").val(positionname);
		$("#dia-positioncode").attr("code",positioncode);
		//$("#dia-positionname").val(positionname);
		//把故障类别的名称赋给故障代码表示项		
		var faultpatterncode=getNodeValue(objXML, "FAULT_PATTERN_CODE", 0);
		var faultpatternname=getNodeValue(objXML, "FAULT_PATTERN_NAME", 0);
		$("#dia-faultpatterncode").val(faultpatternname);
		$("#dia-faultpatterncode").attr("code",faultpatterncode);
		//把严重程度的名称赋给严重程度代码表示项		
		var severitycode=getNodeValue(objXML, "SEVERITY", 0);
		var severityname=getNodeValue(objXML, "NAME", 0);
		$("#dia-severity").val(severityname);
		$("#dia-severity").attr("code",severitycode);		
		/////////////////////////////////////////////////////				
	}else
	{
		setDiaDefaultValue();
	}
});

//设置初始值
function setDiaDefaultValue()
{}

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-faultpatternlist").insertResult(res,0);
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
		var selectedIndex = $("#tab-faultpatternlist").getSelectedIndex();
		$("#tab-faultpatternlist").updateResult(res,selectedIndex);
		//关闭当前窗口
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   
	//给故障类别隐藏域赋值
	if(id=="dia-faultpatterncode") 
	{
		//alert("name="+$("#"+id).val()+"   code="+$("#"+id).attr("code"))
		$("#dia-faultpatternname").val($("#"+id).val());//取得选择故障类别带出来的名称，将其赋值（对于在页面字典项中显示出来的值，用这种赋值方式）
		$("#dia-codeid").val($row.attr("CODE_ID"));//将隐藏域里面在页面上未显示的值取出来，只能用该方法
	}

	//给严重程度隐藏域赋值
	if(id=="dia-severity") 
	{   
		$("#dia-name").val($("#"+id).val());
	}
	return true;
}
</script>