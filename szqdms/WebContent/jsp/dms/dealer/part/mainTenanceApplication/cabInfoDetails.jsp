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
					<table style="display:none;height: 500px;" id="tab-contract" name="tablist" ref="page_contract" refQuery="partInfoDetailsForm">
						<thead>
							<tr>
								<th type="single" name="APPLICATION_ID" style="display: none;"></th>
								<th colwidth="150px" fieldname="CAB_NO">驾驶本体编号</th>
								<th fieldname="CAB_DESCRIPTION">驾驶室本体状态描述</th>
								<th colwidth="120px" fieldname="VIN">车辆识别代码</th>
								<th fieldname="REMARK">技术科科长意见</th>
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
	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationCABInfoDetails.ajax";
    var $f = $("#partInfoDetailsForm");
    var sCondition = {};
    sCondition = $f.combined(1) || {};
    doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
    
    changeParentIframeHeight();
    
})
//改变Iframe高度
var $parentIframe = $("#applicationTypeFrame",parent.document);
$parentIframe.height($("body").height() + 200);

function changeParentIframeHeight(){
	var height = $("tr","body").height() * $("tr","body").size();
	$parentIframe.height($("body").height() + height + 200);
}

</script>
</html>
