<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-dia-preauth-search">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-dia-preauth-search">
					<input type="hidden" id="dia-di-ifApplyclaim" name="dia-di-ifApplyclaim" datasource="A.IF_APPLYCLAIM" value="100102" />
					<input type="hidden" id="dia-di-status" name="dia-di-status" datasource="A.STATUS" value="100201" />
					<input type="hidden" id="dia-di-aythorStatus" name="dia-di-aythorStatus" datasource="A.AUTHOR_STATUS" value="300404" />
					<tr>
						<td><label>预授权单号：</label></td>
					    <td><input type="text" id="dia-in-authorNo" name="dia-in-authorNo" datasource="A.AUTHOR_NO" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-dia-preauth-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dia-preauth" >
			<table style="display:none;width:100%;" id="tab-preauth-list" name="tablist" ref="page-dia-preauth" refQuery="fm-dia-preauth-search" >
					<thead>
						<tr>
							<th type="single"  style="align:center;"></th>
							<th fieldname="AUTHOR_NO" >预授权单号</th>
							<th fieldname="VIN"  >VIN</th>
						    <th fieldname="AUTHOR_TYPE" >预授权类型</th>
						    <th colwidth="60" type="link" title="[确定]"  action="doSelectPreauth">操作</th>
						</tr>
					</thead>
			</table>
			</div>
	     </div>
	</div>
<script type="text/javascript">
var diaClaimPreauthUrl="<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
var diaPreAuthDialog = $("body").data("claimPreauthAdd");
$(function(){
	$("#btn-dia-preauth-search").bind("click",function(event){
		var claimType=$("#dia-in-splx").attr("code");//索赔单类型
		var $f = $("#fm-dia-preauth-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var diaClaimPreauthSearchUrl=diaClaimPreauthUrl+"/searchPreauth.ajax?vehicleId="+vehicleId+"&claimType="+claimType;
		doFormSubmit($f,diaClaimPreauthSearchUrl,"btn-dia-preauth-search",1,sCondition,"tab-preauth-list");
	});
	
});
function doSelectPreauth(daiRowobj)
{
	var claimId=$("#dia-claimId").val();
	$("#dia-in-ysqh").val($(daiRowobj).attr("AUTHOR_NO"));
	$("#dia-in-ysqid").val($(daiRowobj).attr("AUTHOR_ID"));
	var claimPreathUrl= diaClaimPreauthUrl+"/updateClaimPreauth.ajax?claimId="+claimId+"&preauthorId="+$(daiRowobj).attr("AUTHOR_ID");
	sendPost(claimPreathUrl,"","",preCallBack,"false");
}
//回调
function preCallBack(){
	$.pdialog.close(diaPreAuthDialog);
}
</script>
</div>

