<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-dia-activity-search">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-dia-odlpart-search">
					<input type="hidden" id="dia-di-claimUser" name="dia-di-claimUser" datasource="V.CLAIM_USER" value="100102" />
					<tr>
						<td><label>服务活动代码：</label></td>
					    <td><input type="text" id="dia-in-activitycode" name="dia-in-activitycode" datasource="A.ACTIVITY_CODE" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>服务活动名称：</label></td>
					    <td><input type="text" id="dia-in-activityname" name="dia-in-activityname" datasource="A.ACTIVITY_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-dia-activity-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dia-activity" >
			<table style="display:none;width:100%;" id="tab-activity-list" name="tablist" ref="page-dia-activity" refQuery="fm-dia-activity-search" >
					<thead>
						<tr>
							<th type="single"  style="align:center;"></th>
							<th fieldname="ACTIVITY_CODE" >活动代码</th>
							<th fieldname="ACTIVITY_NAME"  >活动名称</th>
							<th fieldname="VEHICLE_ID" >活动车辆</th>
						    <th fieldname="IF_FIXCOSTS" >是否固定费用</th>
						    <th fieldname="IF_CLAIM" >是否追偿</th>
						    <th colwidth="60" type="link" title="[确定]"  action="doSelectActivity">操作</th>
						</tr>
					</thead>
			</table>
			</div>
	     </div>
	</div>
<script type="text/javascript">
var diaClaimActivityUrl="<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
var diaActivityDialog = $("body").data("claimActivityAdd");
$(function(){
	$("#btn-dia-activity-search").bind("click",function(event){
		var $f = $("#fm-dia-activity-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var diaClaimActivitySearchUrl=diaClaimActivityUrl+"/searchActivity.ajax?vehicleId="+vehicleId;
		doFormSubmit($f,diaClaimActivitySearchUrl,"btn-dia-activity-search",1,sCondition,"tab-activity-list");
	});
	
});
function doSelectActivity(daiRowobj)
{
	var claimId=$("#dia-claimId").val();
	var activityCode = $(daiRowobj).attr("ACTIVITY_CODE");
	var activityId = $(daiRowobj).attr("ACTIVITY_ID");
	var ifFixcosts = $(daiRowobj).attr("IF_FIXCOSTS");
	var ifClaim = $(daiRowobj).attr("IF_CLAIM");
	var activityCosts = $(daiRowobj).attr("ACTIVITY_COSTS");
	var manageType = $(daiRowobj).attr("MANAGE_TYPE");
	$("#dia-in-fwhdh").val(activityCode);//活动代码
	$("#dia-in-fwhid").val(activityId);//活动ID
	$("#dia-in-fwhsfgdfy").val(ifFixcosts);//是否固定费用
	$("#dia-in-fwhsfzc").val(ifClaim);//是否追偿费用
	$("#dia-in-fwhclfs").val(manageType);//处理方式
	$("#dia-in-fwhfy").val(activityCosts);//活动费用
	var claimActivityUrl= diaClaimActivityUrl+"/updateClaimActivity.ajax?claimId="+claimId+"&activityId="+activityId+"&ifFixcosts="+ifFixcosts+"&ifClaim="+ifClaim+"&activityCosts="+activityCosts+"&manageType="+manageType;
	sendPost(claimActivityUrl,"","",activityCallBack,"false");
}
function activityCallBack(res){
	$.pdialog.close(diaActivityDialog);
}
</script>
</div>

