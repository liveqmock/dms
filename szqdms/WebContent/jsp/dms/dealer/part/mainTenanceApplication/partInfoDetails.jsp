<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div class="page">
			<div class="pageContent">
				<div id="page_contract">
					<table style="display:none; width: 100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="partInfoDetailsForm">
						<thead>
							<tr>
								<th type="single" name="APPLICATION_ID" style="display: none;"></th>
								<th fieldname="PART_NO" refer="showDetails" colwidth="120">零件编号</th>
								<th fieldname="PART_NAME" >零件名称</th>
								<th fieldname="VIN" colwidth="130">车辆识别码</th>
								<th fieldname="PROCESS_ROUTE">工艺路线</th>
								<th fieldname="OWN_FIRST_LEVEL">所属一级</th>
								<th fieldname="OWN_SECOND_LEVEL">所属二级</th>
								<th fieldname="SUPPLIER_NAME">供应商名称</th>
								<th fieldname="ENGINEERING_DEPARTMENT_REMARK">技术科审核备注</th>
								<th fieldname="PURCHASING_DEPARTMENT_REMARK">采供科审核备注</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<form method="post" id="partInfoDetailsForm" style="display: none;">
		<input type="hidden" id="APPLICATION_ID" name="APPLICATION_ID"  dataSource="APPLICATION_ID" operation="=" />
		<input type="button" id="btn-search"/>
	</form>
</body>
<script type="text/javascript">
$(function(){
	$("#applicationTypeFrame",parent.document).height(400);
	var id = $("#APPLICATION_ID_D",parent.document).val();
	$("#APPLICATION_ID").val(id);
	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationPartInfoDetails.ajax";
    var $f = $("#partInfoDetailsForm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
})

function callbackSearch(){
	changeParentIframeHeight()
}

function changeParentIframeHeight(){
	// 动态改变iframe高度
	var $parentIframe = $("#applicationTypeFrame",parent.document);
	$parentIframe.height($("#tab-contract_content").height() + 200);
}
</script>
</html>
