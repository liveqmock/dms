<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
					<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN"  readonly datatype="1,is_null,2000"/></td>
					<td><label>车型代码：</label></td>
					<td><input type="text" id="dia-modelsCode" name="dia-modelsCode" datasource="MODELS_CODE"  readonly datatype="1,is_null,2000"/></td>
			    </tr>
				<tr>
					<td><label>原销售日期：</label></td>
					<td><input type="text" id="dia-oldSDate" style="width:75px;" name="dia-oldSDate" datasource="OLD_SDATE"  readonly datatype="1,is_null,2000"/></td>
					<td><label>申请销售日期：</label></td>
					<td><input type="text" id="dia-newSDate" style="width:75px;" name="dia-newSDate" datasource="NEW_SDATE" readonly datatype="1,is_null,2000" readonly/></td>
				</tr>		   
                <tr>
					<td><label>申请原因：</label></td>
					<td colspan="3">
						<textarea id="dia-applyReason" style="width:90%;height:40px;" name="dia-applyReason" datasource="APPLY_REASON"   datatype="1,is_null,2000" readonly></textarea>
					</td>			
				</tr>
				<tr>
					<td><label>审批结果：</label></td>
					<td colspan="3">
						<textarea id="dia-checkRemarks" style="width:90%;height:40px;" name="dia-checkRemarks" datasource="CHECK_REMARKS"  readonly  datatype="1,is_null,2000"></textarea>
					</td>			
				</tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li id="dia-searchAtt"><div class="button"><div class="buttonContent"><button type="button" id="btn-downAtt">查看附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
//初始化
$(function(){
	//给弹出框赋值
	var selectedRows = $("#invertoryTable").getSelectedRows();
	setEditValue("fm-civilVehicleSaleDateSetCheckInfo",selectedRows[0].attr("rowdata"));
	
	//查看附件
	$("#btn-downAtt").bind("click", function(event){
		var logId =$("#dia-logId").val();
		$.filestore.view(logId);
	})
});

</script>