<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>积压件调拨确认</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 积压件管理   &gt; 积压件调拨确认</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
        	<%--积压件申请单状态：JYJSQZT_02 已提交待确认 --%>
        	<input type="hidden" id="APPLY_STATUS" name="APPLY_STATUS" datasource="APPLY_STATUS" operation="=" value="<%=DicConstant.JYJSQZT_02%>"/>
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>申请单号：</label></td>
                        <td><input type="text" id="OVERSTOCK_NO" name="OVERSTOCK_NO" datasource="OVERSTOCK_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>申请时间</label></td>
                        <td>
                        	<input type="text"  id="APPLY_DATE" readonly="readonly"  name="APPLY_DATE" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text"  id="APPLY_DATE" readonly="readonly"  name="APPLY_DATE" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index" >
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-index" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" unique="OVERSTOCK_ID" style="display:none;"></th>
                            <th fieldname="OVERSTOCK_NO" colwidth="160" class="desc" refer="formatOverstockNo">申请单号</th>
                            <th fieldname="APPLY_STATUS">申请状态</th>
                            <th fieldname="APPLY_USER">申请人</th>
                            <th fieldname="APPLY_DATE" >申请时间</th>
                            <th fieldname="AMOUNT" >申请单金额</th>
                            <th fieldname="ORG_CODE" >申请渠道代码</th>
                            <th fieldname="ORG_NAME" >申请渠道名称</th>
                         	<th colwidth="80" type="link" title="[确认]"  action="confirmApply" >操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var seachActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/applyConfirmQuery.ajax";
		var $f = $("#fm-index");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,seachActionURL,"btn-search",1,sCondition,"tab-index");
	});
})


// TODO 申请清单提交
function confirmApply(rowObj){
	$(rowObj).find("input:radio").prop("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/overstockParts/overstockPartsApplyForConfirm.jsp", "overstockApplyEdit", "积压件申请单-确认", options);
}

//自定义显示申请单单号显示形式:参数为当前单元格的JQuery对象 
function formatOverstockNo(cellObj){
	return "<a herf='javascript:void(0)' onclick=\"showOverstockInfo(this)\" style='color:red;cursor:pointer'>" + cellObj.text() + "</a>";
}

// TODO 显示申请单详情
function showOverstockInfo(obj){
	// 设置当前行隐藏checkbox为选中状态，为打开窗口获取选中行使用
	$(obj).parent().parent().parent().find("input:radio").prop("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/overstockParts/overstockPartsApplyForDetails.jsp", "OVERSTOCK-APPLY-DETAILS-INFO", "积压件申请单-详情", options);
}


</script>
</body>
</html>