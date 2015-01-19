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
		<form method="post" id="fm-vehicleSaleDateSetInfo" class="editForm" >
			<!-- 隐藏域 -->			
		    <input type="hidden" id="dia-logId" name="dia-logId" datasource="LOG_ID" />
		    <input type="hidden" id="dia-userType" name="dia-userType" datasource="USER_TYPE" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-vehicleSaleDateSetInfo">
				<tr>
					<td><label>VIN：</label></td>
					<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN"  hasBtn="true" readonly datatype="0,is_digit_letter,17" callFunction="openVin()"/></td>
					<td><label>车型代码：</label></td>
					<td><input type="text" id="dia-modelsCode" name="dia-modelsCode" datasource="MODELS_CODE"  readonly/></td>
			    </tr>
				<tr>
					<td><label>原销售日期：</label></td>
					<td><input type="text" id="dia-oldSDate" style="width:75px;" name="dia-oldSDate" datasource="OLD_SDATE"  readonly/></td>
					<td><label>申请销售日期：</label></td>
					<td><input type="text" id="dia-newSDate" style="width:75px;" name="dia-newSDate" datasource="NEW_SDATE" datatype="0,is_date,30" onclick="WdatePicker()" /></td>
				</tr>		   
                <tr>
                	<td><label>首保日期：</label></td>
					<td><input type="text" id="dia-mainteancedate" style="width:75px;" name="dia-mainteancedate"  readonly/></td>
				</tr>		   
                <tr>
					<td><label>申请原因：</label></td>
					<td colspan="3">
						<textarea id="dia-applyReason" style="width:90%;height:40px;" name="dia-applyReason" datasource="APPLY_REASON"   datatype="0,is_null,1000"></textarea>
					</td>			
				</tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li id="dia-addAtt"><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上传附件</button></div></div></li>
				<li id="dia-save"><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/VehicleSaleDateSetMngAction";

//初始化
$(function(){

	//保存前先隐藏上传按钮
	$("#dia-addAtt").hide();

	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-vehicleSaleDateSetInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sbrq=$("#dia-mainteancedate").val();
		var xsrq=$("#dia-newSDate").val();
		if(""==sbrq){
		}else{
			if(xsrq>sbrq){
				$("#dia-in-buyDate").val("");
				$("#dia-in-maintenanceDate").val("");
				alertMsg.warn("申请的销售日期不可以大于首保日期。");
				return false; 
			}
		}
		//判断申请原因是否填写
		var applyReason=$("#dia-applyReason").val();
		if(applyReason.length == 0 ){
			alertMsg.warn("申请原因不能为空.");
			return false; 
		}
		
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-vehicleSaleDateSetInfo").combined(1) || {};

		var applyUrl = diaSaveAction + "/apply.ajax";
		doNormalSubmit($f,applyUrl,"btn-save",sCondition,diaApplyCallBack);
	});
	
	//上传附件按钮
	$("#addAtt").bind("click",function(){
		var logId = $("#dia-logId").val();
		$.filestore.open(logId,{"folder":"true","holdName":"false","uploadLimit":4,"fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
});

//申请回调函数
function diaApplyCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var logId =getNodeText(rows[0].getElementsByTagName("LOG_ID").item(0));//保存成功后,传递主键
			$("#dia-logId").val(logId);	
		}else
		{
			return false;
		}
		//第二个参数0表示插入到第几行
		$("#tab-vehicleSaleDateSetList").insertResult(res,0);
		//保存成功后,显示上传附件按钮,隐藏保存按钮
		$("#dia-addAtt").show();
		$("#dia-save").hide();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//弹出查询VIN界面
function openVin()
{
	var options = {max:false,width:750,height:220,mask:true,mixable:false,minable:false,resizable:true,drawable:true};
	var url = webApps +"/jsp/dms/dealer/service/claimmng/vehicleSaleDateSetSel.jsp";	
	$.pdialog.open(url, "vehicleSaleDateSetSel", "VIN验证(VIN位数17位)", options);				
}

//弹出框回调方法，将选定的VIN等信息带回输入框
function SelCallBack(obj)
{
	$("#dia-vin").val($(obj).attr("VIN"));							//VIN
	$("#dia-modelsCode").val($(obj).attr("MODELS_CODE"));			//车型
	$("#dia-userType").val($(obj).attr("USER_TYPE"));				//用户类型
	$("#dia-mainteancedate").val($(obj).attr("MAINTENANCE_DATE_SV"));	//车辆首保日期
	//$("#dia-userTypeName").val($(obj).attr("USER_TYPE_SV"));		//用户类型(加_SV直接将车辆用户类型字典翻译成汉字)
	$("#dia-oldSDate").val($(obj).attr("BUY_DATE_SV"));				//原销售日期(加_SV直接获得日期对应的格式)
}

</script>