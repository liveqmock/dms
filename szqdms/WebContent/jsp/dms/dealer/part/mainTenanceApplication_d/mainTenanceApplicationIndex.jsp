<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>维护申请</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件管理 &gt; 配件信息维护申请管理 &gt; 维护申请
		</h4>
		<div class="page">
			<div class="pageHeader">
				<form method="post" id="fm-searchContract">
					<!-- 申请单类型 -->
					<input type="hidden" id="APPLICATION_TYPE" name="APPLICATION_TYPE" dataSource="APPLICATION_TYPE" value="<%=DicConstant.PJWHSQLX_01%>" />
					<div class="searchBar" align="left">
						<table class="searchContent">
							<!-- 定义查询条件 -->
							<tr>
								<td><label>申请单号</label></td>
								<td><input type="text" id="APPLICATION_NO" name="APPLICATION_NO" datatype="1,is_digit_letter,30" dataSource="APPLICATION_NO" operation="like" /></td>
								<td><label>申请单状态</label></td>
								<td>
									<select type="text" id="APPLICATION_STATUS" name="APPLICATION_STATUS"  datasource="APPLICATION_STATUS" datatype="1,is_null,30"
											kind="dic" src="PJWHSQZT"
											filtercode="<%=DicConstant.PJWHSQZT_01%>|<%=DicConstant.PJWHSQZT_04%>|<%=DicConstant.PJWHSQZT_06%>" >
										<option value=-1>--</option>
									</select>
								</td>
								<td><label>车辆识别码</label></td>
								<td><input type="text" id="VIN" name="VIN" datatype="1,is_digit_letter,30" dataSource="VIN" operation="=" /></td>
							</tr>
							<tr>
								<td><label>申请时间</label></td>
								<td colspan="5" >
									<input type="text" id="APPLICATION_TIME_BEGIN" name="APPLICATION_TIME_BEGIN" style="width: 75px;" datasource="APPLICATION_TIME" datatype="1,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="APPLICATION_TIME_END" name="APPLICATION_TIME_END" style="width: 75px; margin-left: -30px;"
                                    datasource="APPLICATION_TIME" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                </td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="page_contract">
					<table style="display:none; width: 100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="fm-searchContract">
						<thead>
							<tr>
								<th type="single" name="APPLICATION_ID" style="display: none;"></th>
								<th fieldname="APPLICATION_NO" refer="showDetails"  colwidth="130px">申请单号</th>
								<th fieldname="APPLICATION_TYPE" colwidth="80px">申请单类型</th>
								<th fieldname="APPLICATION_STATUS" colwidth="80px">申请单状态</th>
								<th fieldname="APPLICATION_TIME" colwidth="120px">申请时间</th>
								<th fieldname="ENGINEERING_DEPARTMENT_REMARK">技术科审核备注</th>
								<th fieldname="PURCHASING_DEPARTMENT_REMARK">采供科审核备注</th>
								<th colwidth="100" type="link" title="[修改]|[提报]" action="doEdit|doSub">操作</th>
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
<form id="applicationForm_I" style="display:none" method="post">
	<!-- 申请人 -->
	<input type="hidden" name="APPLICATION_ID_I" id="APPLICATION_ID_I" datasource="APPLICATION_ID_I" val=""/>
	<input type="button" id="tempSubBtn" value="提交"/>
</form>
<script type="text/javascript">
$(function(){

    $("#btn-search").click(function(){
    	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationSelect.ajax";
        var $f = $("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
    });

    // 新增按钮绑定
    $("#btn-add").click(function(){
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,height:600};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/mainTenanceApplication_d/mainTenanceApplicationForAdd.jsp", "addApplication", "申请单新增", options);
    });
});

// 点击盛情单号显示详情
function showDetails(cellObj){
	return "<a href='#' onclick='javascript:openDetilsPage(this)' style='color:red'>" + cellObj.text() + "</a>"
}

// 打开详细信息页面
function openDetilsPage(obj){
	$tr = $(obj).parent().parent().parent();
	$tr.find("input:radio").prop("checked","true");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/dealer/part/mainTenanceApplication_d/mainTenanceApplicationForDetails.jsp", "forDetailsPage", "申请单详情", options);
}

// 提交: 将未提交状态申请单提交至相关部门
function doSub(rowObj){
	var status = $(rowObj).attr("APPLICATION_STATUS"); 		// 获取提交的申请单状态
	if(status == "<%=DicConstant.PJWHSQZT_01%>"
			|| status == "<%=DicConstant.PJWHSQZT_04%>"
			|| status == "<%=DicConstant.PJWHSQZT_06%>") {  // 01:未提交，04：技术科审核驳回, 06：采供科审核驳回
				// 保存Action
	    		var saveActionURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/submitApplication.ajax";
	    		$("#APPLICATION_ID_I").val($(rowObj).attr("APPLICATION_ID"));
	    		var $f = $("#applicationForm_I");
	    		sCondition = $f.combined(1) || {};
	    		doNormalSubmit($f, saveActionURL, "tempSubBtn", sCondition, function(responseText,tabId,sParam){
	    			alertMsg.correct("提交成功");
	            	$("#btn-search").click();
	            });
	}else{
		alertMsg.warn("此状态不能提交！");
	}
}

// 修改：对未提交，技术科审核驳回，采供科审核驳回的申请单进行修改
function doEdit(rowObj){
	var status = $(rowObj).attr("APPLICATION_STATUS"); 		// 获取提交的申请单状态
	if(status == "<%=DicConstant.PJWHSQZT_01%>"
			|| status == "<%=DicConstant.PJWHSQZT_04%>"
			|| status == "<%=DicConstant.PJWHSQZT_06%>") {  // 01:未提交，04：技术科审核驳回, 06：采供科审核驳回
		$("input:radio",rowObj).prop("checked","true");
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	    $.pdialog.open(webApps+"/jsp/dms/dealer/part/mainTenanceApplication_d/mainTenanceApplicationForEdit.jsp", "forEditPage", "申请单详情", options);
	}else{
		alertMsg.warn("此状态不能修改!");
	}
}
</script>
</html>
