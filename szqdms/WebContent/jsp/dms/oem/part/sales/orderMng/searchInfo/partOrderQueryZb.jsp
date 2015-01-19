<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.org.dms.dao.part.salesMng.orderMng.PartOrderQueryMngDao"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);

	Connection conn = null;
	boolean isAmUser = false;
	//boolean ifdownBtn = false; // 判断用户是否可以看到导出按钮
	try{
		PartOrderQueryMngDao dao = new PartOrderQueryMngDao();
		isAmUser = dao.checkUserIsAM(user);
		//ifdownBtn = dao.checkUserIfDown(user);
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(conn != null){
			conn.close();
		}
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 销售相关&gt; 订单查询(总部)</h4>
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
                        <td><label>订单号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datatype="1,is_null,30" datasource="T.ORDER_NO" operation="like"/></td>
                        <%
                        	if(isAmUser){
                        %>
	                        <td><label>订单类型：</label></td>
	                        <td>
	                            <select type="text" id="orderType"  name="orderType" datasource="T.ORDER_TYPE" kind="dic" src="DDLX" datatype="1,is_null,30" operation="=">
	                                <option value="<%=DicConstant.DDLX_08 %>" selected>军品订单</option>
	                            </select>
	                        </td>
                        <%
                        	}else{
                        %>
	                        <td><label>订单类型：</label></td>
	                        <td>
	                            <select type="text" id="orderType"  name="orderType" datasource="T.ORDER_TYPE" kind="dic" src="DDLX" datatype="1,is_null,30" operation="="
	                            	filtercode="<%=DicConstant.DDLX_01 %>|<%=DicConstant.DDLX_02 %>|<%=DicConstant.DDLX_03 %>|<%=DicConstant.DDLX_04 %>|<%=DicConstant.DDLX_05 %>|<%=DicConstant.DDLX_06 %>|<%=DicConstant.DDLX_07 %>|<%=DicConstant.DDLX_09 %>|<%=DicConstant.DDLX_10 %>|<%=DicConstant.DDLX_11 %>|<%=DicConstant.DDLX_12%>"
	                            >
	                                <option value="-1" selected>--</option>
	                            </select>
	                        </td>
                        <%
                        		
                        	}
                        %>
                        <td><label>订单状态：</label></td>
                        <td>
                            <select type="text" id="orderStatus"  name="orderStatus" datasource="T.ORDER_STATUS" kind="dic" src="DDZT" filtercode="<%=DicConstant.DDZT_02%>|<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_04%>|<%=DicConstant.DDZT_05%>|<%=DicConstant.DDZT_06%>" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<td><label>渠道商：</label></td>
                        <td>
                        	<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
							<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="T.ORG_ID" operation="="/>
                        </td>
                    	<td><label>配件代码：</label></td>
                    	<td>
                    		<input type="text" name="partCode" id="partCode" dataSource="PART_CODE" operation="like" datatype="1,is_null,300" />
                    	</td>
                    	<td><label>配件名称：</label></td>
                    	<td>
                    		<input type="text" name="partName" id="partName" dataSource="PART_NAME" operation="like" datatype="1,is_null,300" />
                    	</td>
                    </tr>
                    <tr>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" group="startDate1,endDate1"  id="startDate1" kind="date" name="startDate1" style="width:70px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate1,endDate1"  id="endDate1" kind="date" name="endDate1" style="width:70px;margin-left:-30px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>打印日期：</label></td>
                        <td >
                            <input type="text" group="startDate2,endDate2"  id="startDate2" kind="date" name="startDate2" style="width:70px;" datasource="" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate2,endDate2"  id="endDate2" kind="date" name="endDate2" style="width:70px;margin-left:-30px;" datasource="" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
	                	<td><label>是否延期：</label></td>
                    	<td>
	                    	<select id="IF_DELAY_ORDER"  name="IF_DELAY_ORDER" datasource="IF_DELAY_ORDER" datatype="1,is_null,30" kind="dic" src="SF" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    </tr>
                    <tr>
                        <td><label>审核日期：</label></td>
                        <td>
                            <input type="text" group="startDate0,endDate0"  id="startDate0" kind="date" name="startDate0" style="width:70px;" datasource="T1.CHECK_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate0,endDate0"  id="endDate0" kind="date" name="endDate0" style="width:70px;margin-left:-30px;" datasource="T1.CHECK_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>关单日期：</label></td>
                        <td>
                            <input type="text" group="startDate3,endDate3"  id="startDate3" kind="date" name="startDate3" style="width:70px;" datasource="T.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate3,endDate3"  id="endDate3" kind="date" name="endDate3" style="width:70px;margin-left:-30px;" datasource="T.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>使用额度：</label></td>
                    	<td>
	                    	<select id="IF_CREDIT"  name="IF_CREDIT" datasource="IF_CREDIT" datatype="1,is_null,30" kind="dic" src="SF" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    </tr>
                    <tr>
                        <td><label>销售员：</label></td>
                        <td>
                        	<input type="text" id="PERSON_NAME" name="PERSON_NAME" datatype="1,is_null,30" datasource="T2.USER_ACCOUNT" operation="like"
                        		src="T#PT_BA_ORDER_CHECK:USER_ACCOUNT:USER_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01%> GROUP BY USER_NAME,USER_ACCOUNT" 
								operation="=" isreload="true" kind="dic"
                        	/>
                        </td>
                        <td><label>联系电话</label></td>
                        <td><input type="text" id="PHONE" name="PHONE" datatype="1,is_null,300" datasource="T.PHONE" operation="like"/></td>
                    	<td><label>打印发料单：</label></td>
                    	<td>
	                    	<select type="text" id="isPrint"  name="isPrint" datasource="IS_PRINT" datatype="1,is_null,30" kind="dic" src="SF" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    </tr>
                    <tr>
                    	<td><label>发运状态：</label></td>
                    	<td>
	                    	<select type="text" id="SHIP_STATUS"  name="SHIP_STATUS" datasource="T.SHIP_STATUS" kind="dic" src="DDFYZT" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    	<td><label>发运方式：</label></td>
                    	<td>
	                    	<select type="text" id="TRANS_TYPE"  name="TRANS_TYPE" datasource="T.TRANS_TYPE" kind="dic" src="FYFS" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    	<td><label>关单状态：</label></td>
                    	<td>
	                    	<select type="text" id="CLOSE_STATUS"  name="CLOSE_STATUS" datasource="CLOSE_STATUS" kind="dic" src="" filtercode="" datatype="1,is_null,30" action="show" >
                                <option value="-1" selected>--</option>
                                <option value="0">未关闭</option>
                                <option value="1">已关闭</option>
                            </select>
                    	</td>
                   </tr>
                   <%
                   		if(isAmUser){
                   	%>
                   	<tr>
                        <td><label>客户名称：</label></td>
                        <td><input type="text" id="CUSTORM_NAME" name="CUSTORM_NAME" datasource="CUSTORM_NAME" datatype="1,is_null,30" operation="like"/></td>
                    	<td><label>合同号：</label></td>
                        <td><input type="text" id="ARMY_CONT_NO" name="ARMY_CONT_NO" datasource="ARMY_CONT_NO" datatype="1,is_null,30" operation="like"/></td>
                   </tr>
                   	<%
                   		}
                   %>
                   <tr>
                        <td><label>订单金额汇总（元）：</label></td>
                        <td><input type="text" id="orderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
                    	<td><label>实发金额汇总（元）：</label></td>
                        <td colspan="3"><input type="text" id="sendOrderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
                   </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <!-- <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li> -->
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
                        <th fieldname="ORDER_NO" refer="showLink">订单号</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
                        <th fieldname="APPLY_DATE">申请日期</th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <%
                        	if(isAmUser){
                        %>
                        <th fieldname="CUSTORM_NAME">客户名称</th>
                        <th fieldname="ARMY_CONT_NO">合同号</th>
                        <%
                        	}
                        %>
                        <th fieldname="SALEUSER_NAME">销售员</th>
                        <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">订单金额(元)</th>
                        <th fieldname="REAL_AMOUNT"  refer="amountFormat" align="right">实发金额(元)</th>
                        <th fieldname="CHECK_DATE">订单审核日期</th>
                        <th fieldname="ORDER_STATUS">订单状态</th>
                        <th fieldname="IF_CREDIT">使用额度</th>
                        <th fieldname="IF_DELAY_ORDER">是否延期</th>
                        <th fieldname="SHIP_STATUS">订单发运状态</th>
                        <th fieldname="TRANS_TYPE">订单发运方式</th>
                        <!-- <th fieldname="COUNTRY_NAME" colwidth="150px">收货地址（省/市/区/县）</th> -->
                        <th fieldname="DELIVERY_ADDR">收货地址</th>
                        <th fieldname="LINK_MAN">联系人</th>
                        <th fieldname="PHONE">联系电话</th>
                        <th fieldname="CREATE_TIME">发料单生成日期</th>
                        <th fieldname="CLOSE_DATE">订单关闭日期</th>
                        <th fieldname="SPRINTDATE">订单打印日期</th>
                        <th fieldname="PRINT_USER">打印人</th>
                        <!---->  <th colwidth="115px" refer="showBtn" title="[--]"  action="showBtn" >操作</th> 
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
 	<form id="searchAmountFm" method="post" style="display:none">
		<input type="hidden" id="paramsAmount" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/searchOrder.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
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
             var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/download.do");
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
        $("#paramsAmount").val(sCondition);		// 保存查询条件值至查询汇总的Form参数中
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
    }
    
    // 查询结束后回调函数:
    function callbackSearch(responseText, tabId){
    	 var searchAmountUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/queryAmount.ajax";
    	sendPost(searchAmountUrl,"", $("#paramsAmount").val() ,callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
    }
    
	 // 查询回调函数
	function callbackShowDetailsInfo(res,sData){
		var applicationInfo;							// 此变量保存回调对象中包含的后台查询到的数据
		var explorer = window.navigator.userAgent;		// 判断浏览器
		
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		
		// 调用显示主信息的函数
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		var rowData = jsonObj["ROW_0"];			// 获取第一行的数据
		var hz1 = amountFormatNew(parseFloat(rowData["ORDER_AMOUNT_D"]));
		var hz2 = amountFormatNew(parseFloat(rowData["REAL_AMOUNT_D"]));
		$("#orderAmountSum").val(hz1 == 0 ? 0.00 : hz1);
		$("#sendOrderAmountSum").val(hz2 == 0 ? 0.00 : hz2);
	}

    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    }
    function openDetail(ORDER_ID){
    	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

    //列表审核链接
/*     function doPrint(rowobj){
    	$row = $(rowobj);
    	$("#orderId").val($(rowobj).attr("ORDER_ID"));
    	$("#dia-ORDER_NO").val($(rowobj).attr("ORDER_NO"));
    	$("#dia-ORG_NAME").val($(rowobj).attr("ORG_NAME"));
    	$("#dia-ORDER_AMOUNT").val($(rowobj).attr("ORDER_AMOUNT"));
    	$("#dia-APPLY_DATE").val($(rowobj).attr("APPLY_DATE_sv"));
    	window.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printOrderDtl.jsp");
    } */
    
    // 操作列的回调函数
   <%----%>  function showBtn(obj){
        var $tr = $(obj).parent();
        var forcastStatus = $tr.find("td").eq(11).attr("code");
        if(forcastStatus == <%=DicConstant.DDZT_06 %>){
        	obj.html("<A class=op title=[打印] onclick=doPrint(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>[打印]</A>");
        }
    }

    function doPrint(rowobj) {
    	$row = $(rowobj);
        var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/printOrderPdf.do?ORDER_ID="+$(rowobj).attr("ORDER_ID");
        window.open(queryUrl);
    }
    function doPrint1(rowobj) {
    	$row = $(rowobj);
        var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/printProofPdf.do?ORDER_ID="+$(rowobj).attr("ORDER_ID");
        window.open(queryUrl);
    } 
    
    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }

</script>