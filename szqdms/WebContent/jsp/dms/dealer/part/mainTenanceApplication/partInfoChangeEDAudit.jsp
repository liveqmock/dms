<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
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
			<div class="pageContent" style="overflow-x:scroll;overflow-y:auto;">
				<div class="searchBar" align="left">
					<table class="searchContent">
						<!-- 定义查询条件 -->
						<thead>
							<tr>
								<th width="30"></th>
								<th width="120">原零件编号</th>
								<th >原零件名称</th>
								<th width="120">车辆识别码</th>
								<th width="120">更新编号</th>
								<th >更新名称</th>
								<th >更新（禁用）原因</th>
								<th >技术科意见</th>
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
	<!-- 技术科审批人-->
	<input type="hidden" id="ENGINEERING_DEPARTMENT_A" name="ENGINEERING_DEPARTMENT_A" datatype="1,is_digit_letter,30" dataSource="ENGINEERING_DEPARTMENT_A" />
	<!-- 审批状态 -->
	<input type="hidden" id="APPLICATION_STATUS_A" name="APPLICATION_STATUS_A" datatype="1,is_digit_letter,30" dataSource="APPLICATION_STATUS_A" />
	<!-- 技术科审批备注-->
	<input type="hidden" id="ENGINEERING_DEPARTMENT_REMARK_A" name="ENGINEERING_DEPARTMENT_REMARK_A" datatype="1,is_digit_letter,30" dataSource="ENGINEERING_DEPARTMENT_REMARK_A" />
	<!-- 配件信息录入子表ID -->
	<input type="hidden" name="ENTRY_ID_S_A" id="ENTRY_ID_S_A" datasource="ENTRY_ID_S_A" />
	<!-- 配件信息录入子表ID -->
	<input type="hidden" name="ENGINEERING_DEPARTMENT_REMARK_S_A" id="ENGINEERING_DEPARTMENT_REMARK_S_A" datasource="ENGINEERING_DEPARTMENT_REMARK_S_A" />
</form>
<script type="text/javascript">

// 校验子页面的必填项
function validateSubpage(){
	var result = true;
	// 获取父页面的审批结果:当审核通过后则校验子项，没有通过则对子项不做校验
	var auditStatus = $.trim($("#APPLICATION_STATUS_AUDIT_D",parent.document).val());
	if(auditStatus == <%=DicConstant.PJWHSQZT_03%>){
		var inputArray = $("input[type=text][requiredInput=y]", ".searchContent");
		inputArray.each(function(index, inputObj){
			var inputVal = $(inputObj).val();
			if($.trim(inputVal) == ""){
				var message = "";
				if($(inputObj).attr("name") == "ENGINEERING_DEPARTMENT_REMARK"){
					message = "请填写技术科意见";
				} 
				$(inputObj).addClass("errorInput");
				parent.alertMsg.warn(message);
				result = false;
				return false;

			}else{
				$(inputObj).removeClass("errorInput");
			}
		})
	}
	return result;
}

// 初始化提交表单的数据，将需要保存至后台的数据保存至form表单中
function initSaveForm(){
	
	// 获取父页面的值，并将值赋予子页面中提交表单的隐藏域中
	$("#APPLICATION_ID_A").val($("#APPLICATION_ID_D",parent.document).val());										 // 申请单ID
	$("#ENGINEERING_DEPARTMENT_A").val($.trim($("#ENGINEERING_DEPARTMENT_D",parent.document).val())); 		   		 // 技术科审批人
	$("#APPLICATION_STATUS_A").val($.trim($("#APPLICATION_STATUS_AUDIT_D",parent.document).val())); 	  		     // 审批结果
	$("#ENGINEERING_DEPARTMENT_REMARK_A").val($.trim($("#ENGINEERING_DEPARTMENT_REMARK_D",parent.document).val()));  // 技术科审批备注
	$("#APPLICATION_TYPE_A").val($.trim($("#APPLICATION_TYPE_CODE_D",parent.document).val())); 			   			 // 申请单类型
	initSaveSubpageData(); 																							 // 调用子页面的初始化数据
}

// 初始化子页面中提交的数据
function initSaveSubpageData(){
	var entryIdStr = "";
	var edRemark = "";
	var inputArray = $("input[requiredInput=y]", ".searchContent");
	inputArray.each(function(index, inputObj){
		var inputVal = $.trim($(inputObj).val() == null ? "" : $(inputObj).val());
		if($(inputObj).attr("name") == "ENTRY_ID"){
			entryIdStr += inputVal + ",";
		} else if($(inputObj).attr("name") == "ENGINEERING_DEPARTMENT_REMARK"){
			edRemark += inputVal + ",";
		} 
	})
	entryIdStr = entryIdStr.substring(0, entryIdStr.length - 1);
	edRemark = edRemark.substring(0, edRemark.length - 1);

	// 将获取的值保存至隐藏的form表单中
	$("#ENTRY_ID_S_A").val(entryIdStr);
	$("#ENGINEERING_DEPARTMENT_REMARK_S_A").val(edRemark);
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
	var getDetailsURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationPartInfoChangeDetailsForEdit.ajax";
	
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
		
		var trHtmlStr = "<tr>"+
						"	<td></td>"+
						"	<td><input type='hidden' name='ENTRY_ID' requiredInput='y' /><input type='text' class='textInput readonly' style='margin: 0px;' name='OLD_PART_NO'  readonly='readonly' /></td>"+
						"	<td><input type='text' class='textInput readonly' style='margin: 0px;' name='OLD_PART_NAME'  readonly='readonly'  /></td>"+
						"	<td><input type='text' class='textInput readonly' style='margin: 0px;' name='VIN' readonly='readonly' /></td>"+
						"	<td><input type='text' class='textInput readonly' style='margin: 0px;' name='NEW_PART_NO'   readonly='readonly' /></td>"+
						"	<td><input type='text' class='textInput readonly' style='margin: 0px;' name='NEW_PART_NAME'  readonly='readonly' /></td>"+
						"	<td><input type='text' class='textInput readonly' style='margin: 0px;' name='REMARK'  readonly='readonly' /></td>"+
						"	<td><input type='text' class='textInput' style='margin: 0px;' name='ENGINEERING_DEPARTMENT_REMARK'  requiredInput='y'/><span style='color:red;'>*</span></td>"+
						"</tr>";
		
		for (var son in jsonObj ){
			$(".searchContent tbody").append(trHtmlStr);
		}
		
		$("tr", ".searchContent tbody").each(function(index, obj){
			var trData = jsonObj["row"+index];
			$("input", obj).each(function(indexj, inputObj){
				var nameStr = $(inputObj).attr("name");
				$(inputObj).val(trData[$.trim(nameStr)]);
			});
		});
		initRowNum();
		changeParentIframeHeight();
	}
	
	// 初始化行No
	function initRowNum(){
		$("td:first",".searchContent tbody tr").each(function(index,obj){
			$(obj).text(index+1);
		})
	}
})

// 改变Iframe高度
var $parentIframe = $("#applicationTypeFrame",parent.document);
$parentIframe.height($("body").height());

function changeParentIframeHeight(){
	var height = $("tr","body").height() * $("tr","body").size();
	$parentIframe.height($("body").height() + height);
}
</script>
</html>
