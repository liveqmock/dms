<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>积压件查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 积压件管理   &gt; 积压件查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
					<tr>
						<td><label>渠道商代码：</label></td>
						<td>										
							<input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="ORG_CODE"  datatype="0,is_null,10000000" operation="in" readonly="readonly" hasBtn="true" callFunction="open();"/>
						</td>
						<td><label>渠道商名称：</label></td>
						<td>										
							<input type="text" id="ORG_NAME" name="ORG_NAME"  datatype="1,is_null,10000000" operation="in" readonly="readonly" />
						</td>
					</tr>
                    <tr>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,300" operation="like" /></td>
                        <td><label>配件代码</label></td>
                        <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,300" operation="like" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重&nbsp;&nbsp;置</button></div></div></li>
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
                            <th type="multi" name="XH" unique="STOCK_ID" style="display: none"></th>
                            <th fieldname="ORG_NAME">渠道名称</th>
                            <th fieldname="ORG_CODE"  colwidth="60">渠道代码</th>
                            <th fieldname="PART_NAME" class="desc" >配件名称</th>
                            <th fieldname="PART_CODE" class="desc" colwidth="120">配件代码</th>
                            <th fieldname="IS_OVERSTOCK" colwidth="60px">是否积压件</th>
                            <th fieldname="AMOUNT" colwidth="60">库存数量</th>
                            <th fieldname="OCCUPY_AMOUNT"colwidth="60" >占用数量</th>
                            <th fieldname="AVAILABLE_AMOUNT"colwidth="60" >可用数量</th>
                            <th fieldname="STORAGE_STATUS" colwidth="60px" >库存状态</th>
                            <th fieldname="SUPPLIER_NAME" >供应商名称</th>
                            <th fieldname="SUPPLIER_CODE"  colwidth="80px">供应商代码</th>
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
</html>
<script type="text/javascript">
$(function(){
	
	// 积压件维护查询
	$("#btn-search").click(function(){
		$("#val0").val("");
		var seachActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartAction/queryOverstockPartAll.ajax";
		var $f = $("#fm-index");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,seachActionURL,"btn-search",1,sCondition,"tab-index");
	});
	
	// 清除
	$("#btn-clear").click(function(){
		$("#ORG_CODE").val("");
		$("#ORG_NAME").val("");
		$("#PART_NAME").val("");
		$("#PART_CODE").val("");
	})

})
function open(){
	var id="ORG_CODE";
	/* 	showOrgTree(id,busType); */
	/**
	 * create by andy.ten@tom.com 2011-12-04
	 * 弹出组织选择树
	 * id:弹出选择树的input框id
	 * busType:业务类型[1,2]，1表示配件2表示售后
	 * partOrg:配件业务使用：[1,2]，1表示只显示配送中心2表示只显示服务商和经销商，不传表示全显示
	 * multi:[1,2],1表示默认是多选，2表示是单选
	 */
	showOrgTree(id,1,2,1);
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	  $("#ORG_CODE").val(orgCode).attr("code",orgCode); 
	  $("#ORG_NAME").val(orgName);
}
</script>