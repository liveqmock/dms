<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<style type="text/css">
.searchBar table{
	border-collapse:collapse!important;
}
.searchContent tr th{
	background:#E2F0FF url(<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/themes/default/images/grid/header-bg.gif) repeat-x left -1px;
	border-color:#A3C0E8;
	color:#00579b;
	font-size:13px;
	font-weight:normal;
	height:25px;
	line-height:25px;
	padding:0 3px;
	vertical-align:top;
	white-space:nowrap;
}
.searchContent tr td, .searchContent tr th{
	border: 1px solid #99BBE8;
}
.searchContent tr td{
	line-height:24px;
	white-space:nowrap;
	overflow:hidden;
	padding:0 3px;
	vertical-align:middle;
	background-color:white;
}

.searchContent tr td:first-child{
	TEXT-ALIGN: center; BORDER-LEFT: #99bbe8 1px solid; BACKGROUND-COLOR: #e0ecff;
}

.errorInput {
	border-color: red;
}

</style>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div class="page">
			<div class="pageContent">
				<div class="searchBar" align="left">
					<table class="searchContent">
						<!-- 定义查询条件 -->
						<thead>
							<tr>
								<th width="50"><input type="checkbox" style="margin: 0px" id="allCheckbox"/></th>
								<th width="180">零件编号</th>
								<th width="180">零件名称</th>
								<th width="180">原供应商</th>
								<th width="180">新供应商</th>
								<th width="180">更新（禁用）原因</th>
								<th width="180">原厂家采购主管意见</th>
								<th width="200">新厂家采购主管意见</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<form id="applicationForm" style="display:none" method="post">
	<!-- 申请单ID -->
	<input type="hidden" id="APPLICATION_ID_A" name="APPLICATION_ID_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_A" />
	<!-- 申请单类型 -->
	<input type="hidden" name="APPLICATION_TYPE_A" id="APPLICATION_TYPE_A" datasource="APPLICATION_TYPE_A" val=""/>
	<!-- 采购科审批人-->
	<input type="hidden" id="PURCHASING_DEPARTMENT_A" name="PURCHASING_DEPARTMENT_A" datatype="1,is_digit_letter,30" dataSource="PURCHASING_DEPARTMENT_A" />
	<!-- 审批状态 -->
	<input type="hidden" id="APPLICATION_STATUS_A" name="APPLICATION_STATUS_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_STATUS_A" />
	<!-- 采购科审批备注-->
	<input type="hidden" id="PURCHASING_DEPARTMENT_REMARK_A" name="PURCHASING_DEPARTMENT_REMARK_A" datatype="1,is_digit_letter,30" dataSource="PURCHASING_DEPARTMENT_REMARK_A" />
</form>
<script type="text/javascript">


// 校验子页面的必填项
function validateSubpage(){
	var result = true;
	return result;
}

// 初始化提交表单的数据，将需要保存至后台的数据保存至form表单中
function initSaveForm(){
	
	// 获取父页面的值，并将值赋予子页面中提交表单的隐藏域中
	$("#APPLICATION_ID_A").val($("#APPLICATION_ID_D",parent.document).val());										 // 申请单ID
	$("#PURCHASING_DEPARTMENT_A").val($.trim($("#PURCHASING_DEPARTMENT_D",parent.document).val())); 		   		 // 采购科审批人
	$("#APPLICATION_STATUS_A").val($.trim($("#APPLICATION_STATUS_AUDIT_D",parent.document).val())); 	  		     // 审批结果
	$("#PURCHASING_DEPARTMENT_REMARK_A").val($.trim($("#PURCHASING_DEPARTMENT_REMARK_D",parent.document).val()));    // 采购科审批备注
	$("#APPLICATION_TYPE_A").val($.trim($("#APPLICATION_TYPE_CODE_D",parent.document).val())); 			   			 // 申请单类型
	
	// 调用子页面的初始化数据
	initSaveSubpageData();
}

// 初始化子页面中提交的数据
function initSaveSubpageData(){

}

function saveForm(){
	initSaveForm(); // 调用初始化表单数据
	var saveActionURL = "";
	var $f = $("#applicationForm"); // 保存操作
	sCondition = $f.combined(1) || {};
	var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/saveApplication.ajax";
	doNormalSubmit($f, saveActionURL, "allCheckbox", sCondition, function(responseText,tabId,sParam){
    	$("#btn-search",parent.document).click();
    	parent.alertMsg.correct("保存成功");
		$("#edAuditCloseBtn",parent.document).click();
    }); 
}

$(function(){
	var id = $("#APPLICATION_ID_D",parent.document).val(); 						// 获取申请表ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationSupplierChangeDetailsForEdit.ajax";
	
	// 查询申请表子信息
	sendPost(getDetailsURL+"?applicationId="+id,"","",callbackShowDetailsInfo,null,null);
	
	// 主信息查询回调函数
	function callbackShowDetailsInfo(res,sData){
		var applicationInfo; 													// 此变量保存回调对象中包含的后台查询到的数据
		var explorer = parent.window.navigator.userAgent; 								// 判断浏览器
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		showApplicationInfo(eval("(" + applicationInfo + ")")); 				// 调用显示主信息的函数
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		
		var trHtmlStr =     "<tr>"+
							"	<td></td>"+
							"	<td><input type='hidden' name='ENTRY_ID' requiredInput='y'/><input type='text' class='textInput readonly' name='PART_NO' readonly='readonly' /></td>"+
							"	<td><input type='text' class='textInput readonly' name='PART_NAME' readonly='readonly' /></td>"+
							"	<td><input type='text' class='textInput readonly' name='OLD_SUPPLIER'  readonly='readonly' /></td>"+
							"	<td><input type='text' class='textInput readonly' name='NEW_SUPPLIER'  readonly='readonly' /></td>"+
							"	<td><input type='text' class='textInput readonly' name='REMARK' readonly='readonly' /></td>"+
							"	<td><input type='text' class='textInput readonly' name='OLD_SUPPLIER_REMARK' readonly='readonly' /></td>"+
							"	<td><input type='text' class='textInput readonly' name='NEW_SUPPLIER_REMARK' readonly='readonly' /></td>"+
							"</tr>";
		
		for (var son in jsonObj ){
			$(".searchContent tbody").append(trHtmlStr);
		}
		
		$("tr", ".searchContent tbody").each(function(index, obj){
			var trData = jsonObj["row"+index];
			$("input[type=text]", obj).each(function(indexj, inputObj){
				var nameStr = $(inputObj).attr("name");
				$(inputObj).val(trData[$.trim(nameStr)]);
			});
		});
		
		initRowNum();
	}
	
	// 初始化行No
	function initRowNum(){
		$("td:first",".searchContent tbody tr").each(function(index,obj){
			$(obj).text(index+1);
		})
	}
})
</script>
</html>
