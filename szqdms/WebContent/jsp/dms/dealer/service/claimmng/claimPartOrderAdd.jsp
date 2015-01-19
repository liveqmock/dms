<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-dia-partOrder-search">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-dia-partOrder-search">
					<input type="hidden" id="dia-di-dispatchStatus" name="dia-di-dispatchStatus" datasource="D.DISPATCH_STATUS" value="303402" />
					<input type="hidden" id="dia-di-partstatus" name="dia-di-partstatus" datasource="D.STATUS" value="100201" />
					<tr>
						<td><label>订单号：</label></td>
					    <td><input type="text" id="dia-in-dispatchNo" name="dia-in-dispatchNo" datasource="A.DISPATCH_NO" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-dia-partOrder-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dia-partOrder" >
			<table style="display:none;width:100%;" id="tab-partOrder-list" name="tablist" ref="page-dia-partOrder" refQuery="fm-dia-partOrder-search" >
					<thead>
						<tr>
							<th type="single"  style="align:center;"></th>
							<th fieldname="DISPATCH_NO" >订单号</th>
							<th fieldname="VIN"  >VIN</th>
						    <th colwidth="60" type="link" title="[确定]"  action="doSelectActivity">操作</th>
						</tr>
					</thead>
			</table>
			</div>
	     </div>
	</div>
<script type="text/javascript">
var diaClaimPartOrderUrl="<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
$(function(){
	$("#btn-dia-partOrder-search").bind("click",function(event){
		var $f = $("#fm-dia-partOrder-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var diaClaimPartOrderSearchUrl=diaClaimPartOrderUrl+"/searchPartOrder.ajax?vehicleId="+vehicleId;
		doFormSubmit($f,diaClaimPartOrderSearchUrl,"btn-dia-partOrder-search",1,sCondition,"tab-partOrder-list");
	});
	
});
function doSelectActivity(daiRowobj)
{
	$("#dia-in-sbjjdd").val($(daiRowobj).attr("DISPATCH_NO"));
	$("#dia-in-partOrderid").val($(daiRowobj).attr("DISPATCH_ID"));
	$.pdialog.closeCurrent();
}
</script>
</div>

