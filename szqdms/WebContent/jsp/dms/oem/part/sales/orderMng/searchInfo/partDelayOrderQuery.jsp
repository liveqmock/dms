<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>延期订单查询</title>

</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt;销售相关&gt;延期订单查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form action="post" id="searchForm">
                <input type="hidden" id="orderId" name="orderId"/>
                <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME"/>
                <input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO"/>
                <input type="hidden" id="dia-ORDER_AMOUNT" name="dia-ORDER_AMOUNT"/>
                <input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE"/>
        </form>
        <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode" name=""partCode"" datatype="1,is_null,30" datasource="B.PART_CODE" operation="like"/></td>
						<td><label>仓库：</label></td>
						<td colspan="3">										
							<input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME" datatype="0,is_null,3000" 
								   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100105) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
								   operation="=" isreload="true" kind="dic"
							/>
							<input type="hidden" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="C.WAREHOUSE_CODE" operation="=" />
						</td>
					</tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear">重&nbsp;&nbsp;置</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid">
            <table style="display:none;width:100%;" id="tab-grid-index" name="tablist" ref="page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="PART_CODE" colwidth="130px">配件代码</th>
                        <th fieldname="PART_NAME" colwidth="150px">配件名称</th>
                        <th fieldname="PART_STATUS" colwidth="60px">配件状态</th>
                        <th fieldname="UNIT" colwidth="60px">计量单位</th>
                        <th fieldname="USER_ACCOUNT" colwidth="60px">库管员</th>
                        <th fieldname="WAREHOUSE_NAME" colwidth="80px">仓库</th>
                        <th fieldname="ORDER_COUNT" colwidth="80px">未执行订单需求数量</th>
                        <th fieldname="AMOUNT" colwidth="40px">可用库存</th>
                        <th fieldname="MIN_PACK" colwidth="40px" refer="appendPack_Unit">最小包装</th>
                        <th fieldname="COUNT" colwidth="50px">当前缺件数量</th>
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
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartDelayOrderQueryMngAction/searchOrder.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
         });
         
         // 清除
         $("#btn-clear").click(function(){
        	 $("input", "#ycsbTable").each(function(index, obj){
        		 $(obj).val("");
        	 });
         });

         // 导出按钮绑定
         $("#btn-export-index").click(function(){
             var $f = $("#fm-condition");
             if (submitForm($f) == false) {
                 return false;
             }
             var sCondition = {};
             sCondition = $f.combined() || {};
             $("#data").val(sCondition);
             var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/orderMng/PartDelayOrderQueryMngAction/download.do");
             window.location.href = url;
             $("#exportFm").attr("action",url);
             $("#exportFm").submit();
         });

    });

    // 订单查询
    function searchOrder() {
        var $f = $("#fm-condition");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
    }

    // 
    function showCountBtn(obj){
        obj.html("<a href='#' onclick='doDetail(this)' class='op'>" + obj.html() + "</a>");
    }

    // 订单明细
    function doDetail(obj){
        var $obj = $(obj);
        var $tr = $obj.parent().parent().parent();
    	$tr.find("td input:first").attr("checked",true);
        var orderId = $tr.attr("ORDER_ID");
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/sales/orderMng/searchInfo/partorderQueryDtl.jsp?orderId="+orderId, "orderDtl", "订单明细", options);
    }

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

    //列表审核链接
    function doPrint(rowobj){
    	$row = $(rowobj);
    	$("#orderId").val($(rowobj).attr("ORDER_ID"));
    	$("#dia-ORDER_NO").val($(rowobj).attr("ORDER_NO"));
    	$("#dia-ORG_NAME").val($(rowobj).attr("ORG_NAME"));
    	$("#dia-ORDER_AMOUNT").val($(rowobj).attr("ORDER_AMOUNT"));
    	$("#dia-APPLY_DATE").val($(rowobj).attr("APPLY_DATE_sv"));
    	window.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printOrderDtl.jsp");
    }

    // 最小包装数+最小包装单位(例:10/包)
    function appendPack_Unit(obj) {
        var $tr = $(obj).parent();
        var minPack = $tr.attr("MIN_PACK");
        var minUnit = $tr.attr("MIN_UNIT_sv");
        if (minPack==null) {
            minPack="";
        }
        if (minUnit == null) {
            minUnit="";
        }
        return minPack + '/' + minUnit;
    }
    
    // 表选回调函数
	function afterDicItemClick(id, $row, selIndex){
	  var ret = true;
	  $("#WAREHOUSE_NAME").val($row.attr('WAREHOUSE_NAME'));
	  $("#WAREHOUSE_CODE").val($row.attr('WAREHOUSE_CODE'));
	  return ret;
	
	}
</script>