<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配送中心库存金额汇总表</title>
<style>
#invertoryTable
{
	table-layout: fixed;
	width:900px;
	height:400px;
	border-collapse:collapse;
	margin-top: 50px;
	margin-left: 100px;
	visibility: hidden; 
}
#invertoryTable, #invertoryTable th, #invertoryTable td
{
	border: 1px solid #B8D0D6;
}

#invertoryTable th
{
	height:35px;
	line-height:35px;
	text-align: center;
	font-size: 16px;
	font-weight: bold;
	background-color: #F0EFF0;
}
#invertoryTable td
{
	height: 20px;
	line-height: 20px;
}
.showTitle
{
	text-align: center;
	vertical-align: middle;
	font-weight: bold;
}
.contentDate
{
	text-align: center;
}
.contentAmount
{
	text-align: right;
	padding-right: 10px;
}
.
</style>
</head>
<body>
	<div id="layout" style="width: 100%;overflow-y: scroll;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 财务相关   &gt; 库存金额汇总表</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<input type="hidden" id="ORG_ID" name="ORG_ID" datasource="ORG_ID" datatype="0,is_null,30" value="<%=user.getOrgId()%>"/>
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>日期范围：</label></td>
									<td>
										<input type="text" id="beginDate" name="beginDate" style="width: 75px;" datasource="beginDate" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'2014-11-03', maxDate:'#F{$dp.$D(\'endDate\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -30px; margin-top: 5px;">至</span>
	                                    <input type="text" id="endDate" name="endDate" style="width: 75px; margin-left: -30px;" datasource="endDate" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDate\')}', maxDate:'%y-%M-{%d-1}'})" kind ="date"  operation="<="/>
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
			<div class="pageContent" >
				<div >
					<table id="invertoryTable">
							<thead>
								<tr>
									<th colspan="6">库存金额汇总表</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="showTitle">起始日期</td>
									<td class="contentDate"><span id="beginDateCentent"></span></td>
									<td class="showTitle">终止日期</td>
									<td class="contentDate"><span id="endDateCentent"></span></td>
									<td class="showTitle" colspan="2">单位：元</td>
								</tr>
								<tr>
									<td class="showTitle">期初库存</td>
									<td colspan="2" class="showTitle">入库金额</td>
									<td colspan="2" class="showTitle">出库金额</td>
									<td class="showTitle">期末库存</td>
								</tr>
								<tr>
									<!-- 期初库存 -->
									<td rowspan="6"  class="showTitle"><span id="INIT_INVENTORY_D"></span></td>
									<td>订单验收入库</td>
									<td class="contentAmount"><span id="CHECK_ORDER_AMOUNT_D"></span></td>
									<td>销售出库</td>
									<td class="contentAmount"><span id="SALES_AMOUNT_D"></span></td>
									<td rowspan="6"  class="showTitle"><span id="END_INVENTORY_D"></span></td>
								</tr>
								<tr>
									<td>销售退件入库</td>
									<td class="contentAmount"><span id="SALES_RETURN_AMOUNT_D"></span></td>
									<td>实销出库</td>
									<td class="contentAmount"><span id="SELL_AMOUNT_D"></span></td>
								</tr>
								<tr>
									<td>实销退件入库</td>
									<td class="contentAmount"><span id="SELL_RETURN_AMOUNT_D"></span></td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
	 							<tr>
									<td>入库价差</td>
									<td class="contentAmount"><span id="PRICE_DIFFERENCE_IN_D"></span></td>
									<td>出库价差</td>
									<td class="contentAmount"><span id="PRICE_DIFFERENCE_OUT_D"></span></td>
								</tr>
								<tr >
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="showTitle">合计</td>
									<td class="showTitle contentAmount"><span id="HJ1"></span></td>
									<td class="showTitle">合计</td>
									<td class="showTitle contentAmount"><span id="HJ2"></span></td>
								</tr>
		                    </tbody>
					</table>
				</div>
			</div>
			<br />
			<br />
		</div>
	</div>

</body>
</html>
<script type="text/javascript">
$(function(){
	
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/salesMng/search/DealerAmountSummaryQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		if (checkFormInput($f)) {
			var sCondition = {};
	    	sCondition = $f.combined() || {};
			sendPost(queryAction,"",sCondition,callbackShowPartInfoFun,null,null);
		}
		
	})
	
	// 根据orderId查询订单主信息的回调函数
	function callbackShowPartInfoFun(res,sData){
		
		// 此变量保存回调对象中包含的零件JSON信息
		var partInfoObj;
		
		// 判断浏览器
		var explorer = window.navigator.userAgent;
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			partInfoObj = res.text;
		}else{
			partInfoObj = res.firstChild.textContent;
		}
		
		// 显示对应的值
		showOverstockInfo(eval("(" + partInfoObj + ")"))
		
	}
	
	// 显示订单主信息详情
	function showOverstockInfo(jsonObj){

		
		var dataJSON = jsonObj['ROW_0'];
 		$("#invertoryTable").find("span").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).html(amountFormatNew(dataJSON[inputName]));
		});
 		
 		// 合计1 屏蔽价差
 		// var hJ1 = parseFloat(dataJSON["CHECK_ORDER_AMOUNT_D"]) + parseFloat(dataJSON["SALES_RETURN_AMOUNT_D"]) + parseFloat(dataJSON["SELL_RETURN_AMOUNT_D"]) + parseFloat(dataJSON["PRICE_DIFFERENCE_D"]);
 		var hJ1 = parseFloat(dataJSON["CHECK_ORDER_AMOUNT_D"]) + parseFloat(dataJSON["SALES_RETURN_AMOUNT_D"]) + parseFloat(dataJSON["SELL_RETURN_AMOUNT_D"]) + parseFloat(dataJSON["PRICE_DIFFERENCE_IN_D"]);
 		$("#HJ1").html(hJ1 == 0 ? "0.0" : amountFormatNew(hJ1));
 		
 		// 合计2
 		var hJ2 = parseFloat(dataJSON["SALES_AMOUNT_D"]) + parseFloat(dataJSON["SELL_AMOUNT_D"]) + parseFloat(dataJSON["PRICE_DIFFERENCE_OUT_D"]);;
 		$("#HJ2").html(hJ2 == 0 ? "0.0" : amountFormatNew(hJ2));
 		
 		// 期末库存
 		var qmkc = parseFloat(dataJSON["INIT_INVENTORY_D"]) + hJ1 - hJ2;
 		$("#END_INVENTORY_D").html(amountFormatNew(qmkc) == false ? "0.00" : amountFormatNew(qmkc));
 		
		
		// 起始日期
		$("#beginDateCentent").html($("#beginDate").val());
		
		// 截止日期
		$("#endDateCentent").html($("#endDate").val());
		
		$("#invertoryTable").css("visibility", "visible");
	}

	
})

// 金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

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
	showOrgTree(id,1,1,1);
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	  $("#ORG_CODE").val(orgCode);
	  $("#ORG_NAME").val(orgName);
	  $("#ORG_ID").val(orgId);
}

</script>