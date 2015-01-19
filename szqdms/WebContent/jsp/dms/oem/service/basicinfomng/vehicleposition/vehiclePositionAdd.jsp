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
		<form method="post" id="fm-vehiclePositionInfo" class="editForm" >
			<!-- 隐藏域 -->
		    <input type="hidden" id="dia-positionId" name="dia-positionId" datasource="POSITION_ID" />
		    <input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		    <input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />
		    <input type="hidden" id="dia-pId" name="dia-pId" datasource="P_ID"/>
			<input type="hidden" id="dia-pCode" name="dia-pCode" datasource="P_CODE"/>
			<input type="hidden" id="dia-positionLevel" name="dia-positionLevel" datasource="POSITION_LEVEL"/>
			<input type="hidden" id="dia-positionLevelNew" name="dia-positionLevelNew" datasource="POSITION_LEVEL_NEW"/>
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-vehiclePositionInfo">
				<tr>
					<td><label>车辆部位代码：</label></td>
					<td><input type="text" id="dia-positionCode" name="dia-positionCode" datasource="POSITION_CODE" datatype="0,is_digit_letter,30" /></td>
					<td><label>车辆部位名称：</label></td>
					<td><input type="text" id="dia-positionName" name="dia-positionName" datasource="POSITION_NAME" datatype="0,is_null,30" /></td>
			    </tr>
			    <tr>
                    <td><label>父级总成：</label></td>
                    <td>
                        <input type="text" id="dia-pName" name="dia-pName" datasource="P_NAME"  hasBtn="true" readonly datatype="1,is_null,30" callFunction="openPname()"/>
                    </td>
					<td><label>有效标示：</label></td>
					<td>
		        		<select id="dia-status"  name="dia-status" datasource="STATUS" kind="dic" src="YXBS" datatype="0,is_null,6" >
				    		<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
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
				<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">


var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/VehiclePositionMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-vehiclePositionInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-vehiclePositionInfo").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	
	//绑定重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#fm-vehiclePositionInfo")[0].reset();
	}); 
	
	//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-vehiclePositionList").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		setEditValue("fm-vehiclePositionInfo",selectedRows[0].attr("rowdata"));
		//设置置灰值
		$("#dia-positionCode").attr("readonly",true);
	}else{}
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-vehiclePositionList").insertResult(res,0);
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
		var selectedIndex = $("#tab-vehiclePositionList").getSelectedIndex();
		$("#tab-vehiclePositionList").updateResult(res,selectedIndex);
		//关闭当前窗口
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//弹出查询父级总成界面
function openPname()
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/dms/oem/service/basicinfomng/vehicleposition/vehiclePositionSel.jsp";	
	$.pdialog.open(url, "vehiclePositionSel", "父级总成查询", options);				
}

//弹出框回调方法,将选定的父级总成等信息带回输入框
function SelCallBack(obj)
{
	$("#dia-pId").val($(obj).attr("POSITION_ID"));						//查出总成的Id
	$("#dia-pCode").val($(obj).attr("POSITION_CODE"));					//查出总成的Code
	$("#dia-pName").val($(obj).attr("POSITION_NAME"));					//查出总成的Name
	$("#dia-positionLevelNew").val($(obj).attr("POSITION_LEVEL"));		//查出总成的级别,传到后台计算本车辆部位级别
}

</script>