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
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-civilVehicleSaleDateSetCheckInfo" class="editForm" >
			<!-- 隐藏域 -->			
		    <input type="hidden" id="dia-logId" name="dia-logId" datasource="LOG_ID" />
		    <input type="hidden" id="dia-userType" name="dia-userType" datasource="USER_TYPE" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-civilVehicleSaleDateSetCheckInfo">
				<tr>
					<td><label>VIN：</label></td>
					<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN"  readonly datatype="1,is_digit_letter,17"/></td>
					<td><label>车型代码：</label></td>
					<td><input type="text" id="dia-modelsCode" name="dia-modelsCode" datasource="MODELS_CODE"  readonly/></td>
			    </tr>
				<tr>
					<td><label>原销售日期：</label></td>
					<td><input type="text" id="dia-oldSDate" style="width:75px;" name="dia-oldSDate" datasource="OLD_SDATE"  readonly/></td>
					<td><label>申请销售日期：</label></td>
					<td><input type="text" id="dia-newSDate" style="width:75px;" name="dia-newSDate" datasource="NEW_SDATE" datatype="1,is_date,30" readonly/></td>
				</tr>		   
                <tr>
					<td><label>申请原因：</label></td>
					<td colspan="3">
						<textarea id="dia-applyReason" style="width:90%;height:40px;" name="dia-applyReason" datasource="APPLY_REASON"   datatype="1,is_null,1000" readonly></textarea>
					</td>			
				</tr>
				<tr>
					<td><label>审批结果：</label></td>
					<td colspan="3">
						<textarea id="dia-checkRemarks" style="width:90%;height:40px;" name="dia-checkRemarks" datasource="CHECK_REMARKS"   datatype="0,is_null,1000"></textarea>
					</td>			
				</tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li id="dia-searchAtt"><div class="button"><div class="buttonContent"><button type="button" id="btn-downAtt">查看附件</button></div></div></li>
				<li id="dia-check"><div class="button"><div class="buttonContent"><button type="button" id="btn-check">审批通过</button></div></div></li>
				<li id="dia-checkNo"><div class="button"><div class="buttonContent"><button type="button" id="btn-checkNo">审批驳回</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/VehicleSaleDateMngAction"; 

//初始化
$(function(){
	//给弹出框赋值
	var selectedRows = $("#tab-civilVehicleSaleDateSetCheckList").getSelectedRows();
	setEditValue("fm-civilVehicleSaleDateSetCheckInfo",selectedRows[0].attr("rowdata"));
	
	//审批结果不为空,不显示审批通过和审批驳回按钮
	var checkRemarks = $("#dia-checkRemarks").val();
    if(checkRemarks !=""&&checkRemarks!=null)
    {
    	$("#dia-check").hide();
    	$("#dia-checkNo").hide();
    }
	
	//绑定审批通过按钮
	$("#btn-check").bind("click", function(event){

		//判断审批结果是否填写
		var checkRemarks=$("#dia-checkRemarks").val();
		if(checkRemarks.length == 0 ){
			alertMsg.warn("审批结果不能为空！");
			return false;
		}
		
		var $f = $("#fm-civilVehicleSaleDateSetCheckInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-civilVehicleSaleDateSetCheckInfo").combined(1) || {};
				
		var checkUrl = diaSaveAction + "/check.ajax?checkStatus="+<%=DicConstant.CLXSRQZT_02%>;
		doNormalSubmit($f,checkUrl,"btn-check",sCondition,diaCheckCallBack);
	});
	
	//绑定审批驳回按钮
	$("#btn-checkNo").bind("click", function(event){

		//判断审批结果是否填写
		var checkRemarks=$("#dia-checkRemarks").val();
		if(checkRemarks.length == 0 ){
			alertMsg.warn("审批结果不能为空！");
			return false;
		}
		
		var $f = $("#fm-civilVehicleSaleDateSetCheckInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-civilVehicleSaleDateSetCheckInfo").combined(1) || {};
				
		var checkUrl = diaSaveAction + "/check.ajax?checkStatus="+<%=DicConstant.CLXSRQZT_03%>;
		doNormalSubmit($f,checkUrl,"btn-checkNo",sCondition,diaCheckCallBack);
	});
	
	//查看附件
	$("#btn-downAtt").bind("click", function(event){
		var logId =$("#dia-logId").val();
		$.filestore.view(logId);
	})
});

//审批回调函数
function diaCheckCallBack(res)
{
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

</script>