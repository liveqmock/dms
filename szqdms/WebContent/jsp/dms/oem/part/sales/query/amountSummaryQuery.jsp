<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>库存金额汇总表</title>
<style>
#invertoryTable
{
	table-layout: fixed;
	width:900px;
	height:400px;
	border-collapse:collapse;
	margin-top: 25px;
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 财务相关   &gt; 库存金额汇总表</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>日期范围：</label></td>
									<td>
										<input type="text" id="beginDate" name="beginDate" style="width: 75px;" datasource="beginDate" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -30px; margin-top: 5px;">至</span>
	                                    <input type="text" id="endDate" name="endDate" style="width: 75px; margin-left: -30px;" datasource="endDate" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDate\')}', maxDate:'%y-%M-{%d-1}'})" kind ="date"  operation="<="/>
									</td>
									<td><label>仓库：</label></td>
									<td>										
										<input type="text" id="CODE" name="CODE" datasource="CODE" datatype="0,is_null,300" 
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100111, 100110) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
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
									<th colspan="8" id="tableTitle">库存金额汇总表</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="showTitle">起始日期</td>
									<td colspan="3" class="contentDate"><span id="beginDateCentent"></span></td>
									<td class="showTitle">终止日期</td>
									<td colspan="2" class="contentDate"><span id="endDateCentent"></span></td>
									<td class="showTitle">单位：元</td>
								</tr>
								<tr>
									<td class="showTitle">期初库存</td>
									<td colspan="3" class="showTitle">入库金额</td>
									<td colspan="3" class="showTitle">出库金额</td>
									<td class="showTitle">期末库存</td>
								</tr>
								<tr id="startTr">
									<!-- 期初库存 -->
									<td rowspan="15"  class="showTitle"><span id="INIT_INVENTORY_D"></span></td>
									<td rowspan="8"  class="showTitle">采购入库</td>
									<td>陕汽供货</td>
									<td class="contentAmount"><span id="PURCHASE_SQ_D"></span></td>
									<td class="showTitle" colspan="2">销售出库</td>
									<td class="contentAmount"><span id="SALES_AMOUNT_D"></span></td>
									<td rowspan="15"  class="showTitle"><span id="END_INVENTORY_D"></span></td>
								</tr>
								<tr id="BBK_TR">
									<td>其他采购</td>
									<td class="contentAmount"><span id="PURCHASE_OTHER_D"></span></td>
									<td rowspan="7"  class="showTitle" >转库</td>
									<td>本部库</td>
									<td class="contentAmount"><span id="MOVE_BBK_D"></span></td>
								</tr>
								<tr id="BBXCK_TR">
									<td>其他采购</td>
									<td class="contentAmount"><span id="PURCHASE_OTHER_D"></span></td>
									<td rowspan="7"  class="showTitle" >转库</td>
									<td>本部西厂库</td>
									<td class="contentAmount"><span id="MOVE_XCK_D"></span></td>
								</tr>
								<tr id="BBDBFK_TR">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>本部待报废库</td>
									<td class="contentAmount"><span id="MOVE_DBFK_D"></span></td>
								</tr>
								<tr id="ZSK_TR">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>质损库</td>
									<td class="contentAmount"><span id="MOVE_ZSK_D"></span></td>
								</tr>
								<tr id="ZGK_TR">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>整改库</td>
									<td class="contentAmount"><span id="MOVE_ZGK_D"></span></td>
								</tr>
								<tr id="JPK_TR">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>军品库</td>
									<td class="contentAmount"><span id="MOVE_JPK_D"></span></td>
								</tr>
								<tr id="JPDBFK_TR">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>军品库待报废库</td>
									<td class="contentAmount"><span id="MOVE_JPDBFK_D"></span></td>
								</tr>
								<tr>
									<td class="showTitle">小计</td>
									<td class="showTitle contentAmount"><span id="XJ1"></span></td>
									<td class="showTitle">小计</td>
									<td class="showTitle contentAmount"><span id="XJ2"></span></td>
								</tr>
								<tr>
									<td class="showTitle" colspan="2">移库入库</td>
									<td class="contentAmount"><span id="MOVE_IN_D"></span></td>
									<td class="showTitle" colspan="2">采购退件出库</td>
									<td class="contentAmount"><span id="CGTJ_OUT_D"></span></td>
								</tr>
								<tr>
									<td colspan="2" class="showTitle">销售退件入库</td>
									<td class="contentAmount"><span id="XSTJ_IN_D"></span></td>
									<td colspan="2" class="showTitle">配件公司自行耗用</td>
									<td class="contentAmount"><span id="OTHER_COMPANY_D"></span></td>
								</tr>
								<tr>
									<td colspan="2" class="showTitle">调价(加价)</td>
									<td class="contentAmount"><span id="PRICING_AMOUNT_D"></span></td>
									<td colspan="2" class="showTitle">调价(降价)</td>
									<td class="contentAmount"><span id="PRICING_AMOUNT_DESC_D"></span></td>
								</tr>
								<tr>
									<td colspan="2"  class="showTitle">盘点调账(盘盈)</td>
									<td class="contentAmount"><span id="INVEN_RECON_AMOUNT_D"></span></td>
									<td colspan="2"  class="showTitle">盘点调账(盘亏)</td>
									<td class="contentAmount"><span id="INVEN_RECON_AMOUNT_DESC_D"></span></td>
								</tr>
								<tr>
									<td colspan="2" class="showTitle">其他入库</td>
									<td class="contentAmount"><span id="QT_IN_D"></span></td>
									<td colspan="2" class="showTitle">厂内部门管理耗用</td>
									<td class="contentAmount"><span id="OTHER_DEPARTMENT_D"></span></td>
								</tr>
								<tr>
									<td colspan="2"  class="showTitle"></td>
									<td class="contentAmount"></span></td>
									<td colspan="2"  class="showTitle">其他</td>
									<td class="contentAmount"><span id="OTHER_OTHER_D"></span></td>
								</tr>
								<tr>
									<td colspan="2"  class="showTitle"></td>
									<td class="contentAmount"></td>
									<td colspan="2"  class="showTitle">三包领用(调度)</td>
									<td class="contentAmount"><span id="OTHER_CLAIM_D"></span></td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td colspan="2"  class="showTitle">总计</td>
									<td class="showTitle contentAmount"><span id="ZJ1"></span></td>
									<td colspan="2"  class="showTitle">总计</td>
									<td class="showTitle contentAmount"><span id="ZJ2"></span></td>
									<td>&nbsp;</td>
								</tr>
		                    </tbody>
					</table>
				</div>
			</div>
			<br />
			<br />
		</div>
	</div>
 	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	
	/*
		 初始化显示内容 : 数字代表数组下表
		 0 本部库  K000001
		 1 本部西厂库 K000002
		 2 本部待报废库 K000003
		 3 质损库 K000006
		 4 整改库 K000005
		 5 军品库 K000004
		 6 军品待报废库 K000007
	 */
	var moveTrObj = [
			{"id":'BBK_TR',    "content": "<tr id='BBK_TR'>" + $("#BBK_TR").html() + "</tr>"},
			{"id":'BBXCK_TR',  "content": "<tr id='BBXCK_TR'>" + $("#BBXCK_TR").html() + "</tr>"},
			{"id":'BBDBFK_TR', "content": "<tr id='BBDBFK_TR'>" + $("#BBDBFK_TR").html() + "</tr>"},
			{"id":'ZSK_TR',    "content": "<tr id='ZSK_TR'>" + $("#ZSK_TR").html() + "</tr>"},
			{"id":'ZGK_TR',    "content": "<tr id='ZGK_TR'>" + $("#ZGK_TR").html() + "</tr>"},
			{"id":'JPK_TR',    "content": "<tr id='JPK_TR'>" + $("#JPK_TR").html() + "</tr>"},
			{"id":'JPDBFK_TR',    "content": "<tr id='JPDBFK_TR'>" + $("#JPDBFK_TR").html() + "</tr>"}
	];
	
	// 显示数组
	var showArray = [];
	
	// 删除全部移库显示内容
	function deleteAllMoveTr(){
		for(var i = 0, len = moveTrObj.length; i < len ; i++){
			$("#"+ moveTrObj[i]["id"]).remove();
		}
	}
	
	// 添加显示移库显示内容：每次显示6个中的5个，其中一个不显示，不显示的tr为用户选择的所属库
	function addShowContent(array){
		var showContent = "";
		for(var i = 0, len = array.length; i < len; i++){
			showContent += (moveTrObj[array[i]])["content"];
		}
		$("#startTr").after(showContent);
		
		// 本部库与西厂库同在是需要处理表格跨行跨列的问题
		if(!(array.toString() == "1,2,3,4,5,6" || array.toString() == "0,2,3,4,5,6")){
			$("#BBXCK_TR").html("").html("<td>&nbsp;</td><td>&nbsp;</td><td>本部西厂库</td><td class='contentAmount'><span id='MOVE_XCK_D'></span></td>")
		}
	}

	// 查询
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/salesMng/search/AmountSummaryQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		if (checkFormInput($f)) {
			var sCondition = {};
	    	sCondition = $f.combined() || {};
			sendPost(queryAction,"",sCondition,callbackShowPartInfoFun,null,null);
		}
		
	});
	
	// 初始化显示表格
	/* 数字代表所在数组位置
		0 本部库  K000001
		1 本部西厂库 K000002
		2 本部待报废库 K000003
		3 质损库 K000006
		4 整改库 K000005
		5 军品库 K000004
		6 军品待报废库 K000007
	*/
	function initShowTab(){
		
		// 删除表格显示内容
		
		deleteAllMoveTr();
		
		// 获取用户选择的仓库代码
		var warehouseCode = $("#CODE").attr("code");
		switch(warehouseCode){
		case "K000001": // 本部库
			showArray = [1, 2, 3, 4, 5, 6];
			break;
		case "K000002": // 本部西厂库
			showArray = [0, 2, 3, 4, 5, 6];
			break;
		case "K000003": // 本库待报废库
			showArray = [0, 1, 3, 4, 5, 6];
			break;
		case "K000004": // 军品库
			showArray = [0, 1, 2, 3, 4, 6];
			break;
		case "K000005": // 整改库
			showArray = [0, 1, 2, 3, 5, 6];
			break;
		case "K000006": // 质损库
			showArray = [0, 1, 2, 4, 5, 6];
			break;
		case "K000007": // 军品待报废库
			showArray = [0, 1, 2, 3, 4, 5];
		default:
			break;
		}
		
		// TableTitle修改
		$("#tableTitle").text($("#CODE").val() + "库存金额汇总表");
		
		// 根据显示数组内容（内容保存下表）显示内容
		addShowContent(showArray);
	}
	
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
		
		// 初始化显示表格
		initShowTab();
		
		// 显示对应的值
		showOverstockInfo(eval("(" + partInfoObj + ")"));
		
	}
	
	// 显示订单主信息详情
	function showOverstockInfo(jsonObj){

		
		var dataJSON = jsonObj['ROW_0'];
 		$("#invertoryTable").find("span").each(function(index,obj){
			var inputName = $(obj).attr("id");
			$(obj).html(amountFormatNew(dataJSON[inputName]));
		});
 		
 		// 小计1
 		var xj1 = parseFloat(dataJSON["PURCHASE_SQ_D"]) + parseFloat(dataJSON["PURCHASE_OTHER_D"]);
 		$("#XJ1").html( xj1 == 0 ? "0.00" : amountFormatNew(xj1));
 		
 		// 小计2 : 小计2先累计销售出库金额
 		//dataJSON["SALES_AMOUNT_D"]
 		var xj2 = parseFloat(0);
 		
 		// 判断当前显示TR，显示模式
		// 获取用户选择的仓库代码
		// 1 本部库 2 本部西厂库 3 本部待报废库 4 质损库 5 整改库 6 军品库 
		var warehouseCode = $("#CODE").attr("code");
		switch(warehouseCode){
			case "K000001": // 本部库
				// showArray = [1, 2, 3, 4, 5, 6];
		 		xj2 +=   
		 			  parseFloat(dataJSON["MOVE_BBK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_XCK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_DBFK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZSK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZGK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPK_D"]) +
		 			  parseFloat(dataJSON["MOVE_JPDBFK_D"]) ;
				break;
			case "K000002": // 本部西厂库
				// showArray = [0, 2, 3, 4, 5, 6];
		 		xj2 += 
		 			  parseFloat(dataJSON["MOVE_BBK_D"])+ 
		 			  parseFloat(dataJSON["MOVE_DBFK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZSK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZGK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPK_D"]) +
		 			  parseFloat(dataJSON["MOVE_JPDBFK_D"]) ;
				break;
			case "K000003": // 本库待报废库
				// showArray = [0, 1, 3, 4, 5, 6];
		 		xj2 += 
		 			  parseFloat(dataJSON["MOVE_BBK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_XCK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZSK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZGK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPK_D"]) +
		 			  parseFloat(dataJSON["MOVE_JPDBFK_D"]) ;
				break;
			case "K000004": // 军品库
				// showArray = [0, 1, 2, 3, 4, 6];
		 		xj2 += 
		 			  parseFloat(dataJSON["MOVE_BBK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_XCK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_DBFK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZSK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZGK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPDBFK_D"]) ;
				break;
			case "K000005": // 整改库
				// showArray = [0, 1, 2, 3, 5, 6];
		 		xj2 += 
		 			  parseFloat(dataJSON["MOVE_BBK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_XCK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_DBFK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZSK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPK_D"]) +
		 			  parseFloat(dataJSON["MOVE_JPDBFK_D"]) ;
				break;
			case "K000006": // 质损库
				// showArray = [0, 1, 2, 4, 5, 6];
		 		xj2 += 
		 			  parseFloat(dataJSON["MOVE_BBK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_XCK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_DBFK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZGK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPK_D"]) +
		 			  parseFloat(dataJSON["MOVE_JPDBFK_D"]) ;
				break;
			case "K000007": // 军品待报废库
				// showArray = [0, 1, 2, 3, 4, 5];
		 		xj2 += 
		 			  parseFloat(dataJSON["MOVE_BBK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_XCK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_DBFK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZSK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_ZGK_D"]) + 
		 			  parseFloat(dataJSON["MOVE_JPK_D"]);
			default:
				break;
		}
 		
 		
 		$("#XJ2").html(xj2 == 0 ? "0.00" :amountFormatNew(xj2));
 		
 		// 总计1
 		var zj1 = xj1 + parseFloat(dataJSON["MOVE_IN_D"]) + parseFloat(dataJSON["XSTJ_IN_D"]) + parseFloat(dataJSON["PRICING_AMOUNT_D"])+ parseFloat(dataJSON["QT_IN_D"]) + parseFloat(dataJSON["INVEN_RECON_AMOUNT_D"]);
 		$("#ZJ1").html(amountFormatNew(zj1) == false ? "0.00" : amountFormatNew(zj1));
 		
 		// 总计2
 		var zj2 = xj2 + parseFloat(dataJSON["OTHER_COMPANY_D"]) + parseFloat(dataJSON["CGTJ_OUT_D"]) + 
 					    parseFloat(dataJSON["OTHER_DEPARTMENT_D"]) + parseFloat(dataJSON["OTHER_OTHER_D"]) + 
 					    parseFloat(dataJSON["OTHER_CLAIM_D"]) + parseFloat(dataJSON["PRICING_AMOUNT_DESC_D"]) + 
 					    parseFloat(dataJSON["INVEN_RECON_AMOUNT_DESC_D"])+parseFloat(dataJSON["SALES_AMOUNT_D"]);
 		$("#ZJ2").html(amountFormatNew(zj2) == false ? "0.00" : amountFormatNew(zj2));
 		
 		// 期末库存
 		var qmkc = parseFloat(dataJSON["INIT_INVENTORY_D"]) + zj1 - zj2;
 		$("#END_INVENTORY_D").html(amountFormatNew(qmkc) == false ? "0.00" : amountFormatNew(qmkc));
 		
		
		// 起始日期
		$("#beginDateCentent").html($("#beginDate").val());
		
		// 截止日期
		$("#endDateCentent").html($("#endDate").val());
		
		$("#invertoryTable").css("visibility", "visible");
	}
		// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/search/PartAssemblyQueryAction/exportExcel.do");
		$("#exportFm").submit();
	});
	
});

// 金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

// 表选回调函数
function afterDicItemClick(id, $row, selIndex){
  var ret = true;
  $("#WAREHOUSE_NAME").val($row.attr('WAREHOUSE_NAME'));
  $("#WAREHOUSE_CODE").val($row.attr('WAREHOUSE_CODE'));
  return ret;

}
</script>