<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>积压件维护</title>
<script type="text/javascript">
$(function(){
	
	// 积压件维护查询
	$("#btn-search").click(function(){
		$("#val0").val("");
		var seachActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartAction/queryOverstockPart.ajax";
		var $f = $("#fm-index");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,seachActionURL,"btn-search",1,sCondition,"tab-index");
	});
	
	// 批量更新配件状态为积压件
	$("#btn-all-edit").click(function(){
		var stockIds = $("#val0").val();
		if(!stockIds.trim()){
			alertMsg.info("请选择零件");
			return
		}else{
			alertMsg.confirm("确认将选择的零件批量更新为积压件？", {
                okCall: function(){
                	updateOverstockStatus(stockIds,"<%=DicConstant.SF_01%>");
                }
			});
		}
	});
})
// 表格控件：checkBox保存选中的值
function doCheckbox(checkbox) {
	
	// 获取checkBox的val值
	var arr = [];
	var $checkbox = $(checkbox);
	var applyId = $(checkbox).val();
	arr.push(applyId);
	multiSelected($checkbox,arr);

}

// 标示为积压件
function mark(rowObj){
	var status = "<%=DicConstant.SF_01%>";
	if($(rowObj).attr("IS_OVERSTOCK") == status){
		alertMsg.info("此零件已经为积压件");
		return;
	}
	alertMsg.confirm("确认标示此零件为积压件？",{okCall:function(){
		var stockId = $(rowObj).attr("STOCK_ID");
		updateOverstockStatus(stockId, status);
	}});
}

// 取消积压件标示
function remark(rowObj){
	var status = "<%=DicConstant.SF_02%>";
	if($(rowObj).attr("IS_OVERSTOCK") == status){
		alertMsg.info("此零件已取消积压件标示");
		return;
	}
	alertMsg.confirm("确认取消此零件的积压件标示？",{okCall:function(){
		var stockId = $(rowObj).attr("STOCK_ID");
		updateOverstockStatus(stockId, status);
	}});
}

// 更新积压状态
function updateOverstockStatus(stockId, status){
	var updateActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartAction/updatePartIsOverstockMark.ajax";
	sendPost(updateActionURL+"?stockIds="+stockId+"&status="+status,null,null,callbackFun,null,null)
}

// 回调函数
function callbackFun(res,sData){
	
	$("#btn-search").click();
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 积压件管理   &gt; 积压件维护</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,300" operation="like" /></td>
                        <td><label>配件代码</label></td>
                        <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,300" operation="like" /></td>
                        <td><label>是否积压件</label></td>
                        <td>
                        	<select  type="text" id="IS_OVERSTOCK" name="IS_OVERSTOCK" datasource="IS_OVERSTOCK" kind="dic" src="SF" class="combox" src="" datatype="1,is_null,8" >
								<option value=-1>--</option>
							</select>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-all-edit" >批量标示为积压件</button></div></div></li>
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
                            <th type="multi" name="XH" unique="STOCK_ID" style=""></th>
                            <th fieldname="PART_NAME" class="desc" colwidth="120">配件名称</th>
                            <th fieldname="PART_CODE" class="desc">配件代码</th>
                            <th fieldname="IS_OVERSTOCK" >是否积压件</th>
                            <th fieldname="AMOUNT" >库存数量</th>
                            <th fieldname="OCCUPY_AMOUNT" >占用数量</th>
                            <th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
                            <th fieldname="STORAGE_STATUS" >库存状态</th>
                            <th fieldname="SUPPLIER_NAME" >供应商名称</th>
                            <th fieldname="SUPPLIER_CODE">供应商代码</th>
                         	<th colwidth="80" type="link" title="[标示]|[撤销]"  action="mark|remark" >操作</th>
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
</body>
</html>