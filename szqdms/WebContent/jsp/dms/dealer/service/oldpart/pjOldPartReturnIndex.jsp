<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运</title>
<script type="text/javascript">

// 需要回运的查询
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPJReturnAction/oldPartSearch.ajax";

// 回运Action
var returnPart = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPJReturnAction/updateReturnDate.ajax";

// 查询按钮响应方法
$(function(){
	
	// 查询回运数据
	$("#search").bind("click",function(){
		var $f = $("#oldpartform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
	
	// 更新回运时间
	$("#returnPart").click(function(){
		var returnPartApplyId = $("[name='multivals']").eq(0).val();
		if(!returnPartApplyId.trim()){
			alertMsg.info("请选择需要回运的申请单");
			return
		}else{
			alertMsg.confirm("是否确认回运?", {
                okCall: function(){
                	sendPost(returnPart+"?applyIds="+returnPartApplyId,null,null,function(message){
        				$("#search").click();
        				return true;
        			},null,null);
                }
			});
		}
	})
});

// 表格控件：checkBox选中
function doCheckbox(checkbox) {
	
	// 获取checkBox的val值
	var arr = [];
	var $checkbox = $(checkbox);
	var applyId = $(checkbox).val();
	arr.push(applyId);
	multiSelected($checkbox,arr);

}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;配件管理&gt;旧件回运</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldpartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldpartTable">
						<tr>
							<td><label>箱号：</label></td>
							<td><input type="text" id="BOX_NO" name="BOX_NO" datasource="BOX_NO" datatype="1,is_null,30" operation="like" value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,30" operation="like"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,30"  operation="like" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="returnPart">回&nbsp;&nbsp;运</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldpart">
				<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldpart" refQuery="oldpartTable" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="APPLY_ID"></th>
							<th fieldname="APPLY_NO"  ordertype='local' class="desc" colwidth="130px">申请单号</th>
							<th fieldname="BOX_NO"  ordertype='local' class="desc">箱号</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="CLAIM_COUNT" align="right" >索赔数量</th>
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="FAULT_CONDITONS">故障情况</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<table style="display:none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
			</table>
		</div>
	</div>
</div>
<form id="applyIdForm" method="post" style="display:none">
		<input type="hidden" id="applyIds" name="applyIds" />
</form>
</body>
</html>