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
								<th fieldname="PART_NO">零件编号</th>
								<th fieldname="PART_NAME">零件名称</th>
								<th fieldname="OLD_SUPPLIER">原供应商</th>
								<th fieldname="NEW_SUPPLIER">新供应商</th>
								<th fieldname="REMARK">更新（禁用）原因</th>
								<th fieldname="OLD_SUPPLIER_REMARK">原厂家采购主管意见</th>
								<th fieldname="NEW_SUPPLIER_REMARK">新厂家采购主管意见</th>
								<th fieldname="PURCHASE_PRICE">新采购价（不含税）</th>
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
	var id = $("#APPLICATION_ID_D",parent.document).val();
	$("#APPLICATION_ID").val(id);
	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationSupplierChangeDetails.ajax";
    var $f = $("#partInfoDetailsForm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
})


</script>
</html>
