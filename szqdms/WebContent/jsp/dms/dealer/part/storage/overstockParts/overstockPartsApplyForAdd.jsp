<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="width:100%;">
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 积压件管理   &gt; 积压件调拨申请</h4>
    <div class="page" >
    <div class="pageHeader" >
         <div class="searchBar" align="left" >
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save" >保存</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cancel" >取消</button></div></div></li>
                    </ul>
                </div>
        </div>
    </div>
    <div class="pageContent" style="background-color: #EEF4F5;">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add" href="javascript:addParts();"><span>添加配件</span></a></li>
			</ul>
		</div>
        <div style="background-color: #EEF4F5;visibility:hidden;" id="showPartsDiv" class="pageContent" >
            <table style="width:100%;" id="addPartDetails"  >
            	<thead>
            		<tr>
	            			<th type="multi" name="XH" unique="STOCK_ID" ></th>
                            <th colwidth="110">配件代码</th>
                            <th>配件名称</th>
                            <th colwidth="70">可用数量</th>
                            <th colwidth="70">配件价格</th>
                            <th >渠道名称</th>
                            <th colwidth="80">渠道代码</th>
                            <th colwidth="70">申请数量</th>
                         	<th >操作</th>
            		</tr>
            	</thead>
                <tbody id="addPartDetailsTbody" style="height:100%">
                	
                </tbody>
            </table>
        </div>
    </div>
	<div class="formBar">
		<ul>
			<li>申请单总金额：<input type="text" id="AMOUNT" name="AMOUNT" value="" readonly="readonly"></li>
		</ul>
	</div>
	
	</div>
</div>
<form id="tempUmsaveForm" style="display:none" method="post" class="editForm">
	<input type="hidden" name="stockIds_f" id="stockIds_f" datasource="stockIds_f" val=""/>
	<input type="hidden" name="partsCount_f" id="partsCount_f" datasource="partsCount_f" val="" />
	<%--积压件初始状态 --%>
	<input type="hidden" name="overstockApplyStatus" id="overstockApplyStatus" datasource="overstockApplyStatus" value="<%=DicConstant.JYJSQZT_01 %>" />
</form>
					    <textarea id="val0" name="multivals" column="1" style="display:none"></textarea>
						<textarea id="val1" name="multivals"  style="display:none"></textarea>
						<textarea id="val2" name="multivals"  style="display:none"></textarea>
						<textarea id="val3" name="multivals"  style="display:none"></textarea>
						<textarea id="val4" name="multivals"  style="display:none"></textarea>
						<textarea id="val5" name="multivals" style="display:none"></textarea>
						<textarea id="val6" name="multivals" style="display:none"></textarea>
						
						<!-- 唯一键：配件仓库代码主键的选择保存区域，用来比较用户删除或者重新添加的动态变化 -->
						<textarea id="val0_bak" style="display:none"></textarea>
<script type="text/javascript">

// 添加配件Table显示状态，初始为隐藏，则状态为false
var showStatus = false;

// 弹出临时保存变量
var dia_dialog = $("body").data("addJYJApply");

$(function(){
	
	
	// 保存申请单
	$("#btn-save").click(function(){
		if(validForm()){
			var countArray = $("input[name='partCount_input']");
    		var stockIdsTemp = $("#val0").val();
    		var countTemp = "";
    		$.each(countArray, function(index, obj){
    			countTemp += $(obj).val();
    			if(index + 1 != countArray.length){
    				countTemp += ",";
    			}
    		});
            		
    		$("#stockIds_f").val(stockIdsTemp);
    		$("#partsCount_f").val(countTemp);
            		
    		// 保存Action
    		var saveActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/applyStatusSave.ajax";
    		 
    		// 保存操作
    		var $f = $("#tempUmsaveForm");
    		sCondition = $f.combined(1) || {};
    		doNormalSubmit($f, saveActionURL, "btn-save", sCondition, function(responseText,tabId,sParam){
    			//$.pdialog.closeCurrent();
    			$.pdialog.close(dia_dialog);
            	alertMsg.correct("保存成功");
            	$("#btn-search").click();
            });
        }
	});
	
	// 取消申请单
	$("#btn-cancel").click(function(){
		alertMsg.confirm("确定取消？", {
            okCall: function(){
            	//$.pdialog.closeCurrent();
    			$.pdialog.close(dia_dialog);
            }
      	});
	});
	
});

/**
 * 打开配件选择页面
 */
function addParts(){
	
	initTextareaVal();
	
	var options = {max:false,width:1000,height:460,mask:true,mixable:false,minable:false,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/overstockParts/overstockParts.jsp", "addJYJPartsTemp", "积压件选择", options);
}


// 删除行操作
function delRow(obj){
	
	alertMsg.confirm("确定此配件？", {
        okCall: function(){
        	
        	addAllRowFirstTDStyle();
        	
        	$(obj).parent().parent().remove();
        	$("div[class='gridScroller']").css("background-color","#EAEEF5");
        	
        	/*
        	 *添加此处代码是因为框架JS存在bug，
        	 * mine.table.js：createRow函数中，代码is(:visible)判断，在IE11下全行删除后判断为false，导致在当前页面创建了2个相同的xxxx_content的Table
        	 * ID相同则导致其他JS无法获取DOM元素
        	 * 添加此处代码，则JS在来判断is(:visible)为ture，消除创建2个形同ID的bug
        	 * 但此代码有影响，表格Title与内容存在一定间距
        	 */
        	if($("#addPartDetails_content").height() == 0){
        		$("#addPartDetails_content").append("<tr style='height:1px;line-height:1px;'>&nbsp;</tr>");
        	}
        	
        	
        	// 重新初始化临时选中变量的值
        	initStockIdsVal();
        	
        	// 重新初始化textarea中的值
        	initTextareaVal();
        	
        	// 初始化行号
        	initLineNum();
        	
        	// 计算配件总金额
        	sumPartPrice();
        }
  	});
}

// 重新初始化stockId的值
function initStockIdsVal(){
	stockIds = "";
	var checkBoxArray = $("#addPartDetailsTbody > tr").find("input:checkbox");
	
	for(var i = 0 ; i < checkBoxArray.length; i++){
		if(i+1 == checkBoxArray.length){
			stockIds += $(checkBoxArray[i]).val();
		}else{
			stockIds += $(checkBoxArray[i]).val() + ",";
		}
	}
	
}

// 保存首行的TD样式，用于给后面的所有行添加TD的样式，防止删除首行后，其他行的TD宽度发生变化
var thStyleArray = [];
function initTdStyle(){
	var _thArray = $($("tr:first","#addPartDetails")).find("th");
	_thArray.each(function(index, _thObj){
		thStyleArray.push($(_thObj).attr("style"));
	});
}

// 给所有行添加首行TD样式
function addAllRowFirstTDStyle(){
	$("tr","#addPartDetailsTbody").each(function(index,objTr){
    		$(objTr).find("td").each(function(indexj,objTd){
    			$(objTd)[0].style.cssText = thStyleArray[indexj];
    		});
	});
}

// 重新初始化隐藏域textarea中的值
function initTextareaVal(){
	
	// 获取添加的行
	var trArray = $("#addPartDetailsTbody > tr");
	
 	var temp_stockIds = "",
 		temp_partCodes = "",
 		temp_partName = "",
 		temp_availableAmount = "",
 		temp_supplierName = "",
 		temp_supplierCode = "",
 		temp_partPrice = "";
 	
 	trArray.each(function(index,trObj){
 		
 		$(trObj).find("td").each(function(indexj,tdObj){
 			switch(indexj){
 			case 1:
 				temp_stockIds += $(tdObj).find("input:checkbox").val();
 				break;
 			case 2:
 				temp_partCodes +=  $.trim($(tdObj).text());
 				break;
 			case 3:
 				temp_partName +=  $.trim($(tdObj).text() == null ? "" : $($(tdObj).find("div")).text());
 				break;
 			case 4:
 				temp_availableAmount +=  $.trim($(tdObj).text() == null ? "" : $(tdObj).text());
 				break;
 			case 5:
 				temp_partPrice += $.trim($(tdObj).text() == null ? "" : $(tdObj).text());
 				break;
 			case 6:
 				temp_supplierName +=  $.trim($(tdObj).text() == null ? "" : $(tdObj).text());
 				break;
 			case 7:
 				temp_supplierCode +=  $.trim($(tdObj).text() == null ? "" : $(tdObj).text());
 				break;
 			default:
 				
 				break;
 			}
 		});
 		
 		if(index + 1 != trArray.length){
			temp_stockIds += ",";
			temp_partCodes += ",";
			temp_partName += ",";
			temp_availableAmount += ",";
			temp_supplierName += ",";
			temp_supplierCode += ",";
			temp_partPrice += ",";
		}
 		
 	});	
 	$("#val0").val(temp_stockIds);
	$("#val1").val(temp_partCodes);
	$("#val2").val(temp_partName);
	$("#val3").val(temp_availableAmount);
	$("#val4").val(temp_supplierName);
	$("#val5").val(temp_supplierCode); 
	$("#val6").val(temp_partPrice);
	
}

/**
 * 初始化行号
 */
function initLineNum(){
	$("#addPartDetailsTbody > tr").each(function(index,obj){
		$($(obj).find("td")[0]).html(index + 1).css("text-align","center");
	});
}



/**
 * 确定配件选择
 */
 // 已选择的仓库零件代码主键，此字符串随用户添加，删除变化而变化，用于比较用户是否已经添加过此配件
var stockIds = "";
function addPartsCallBack(){

	var selectStockIdsArray = $("#val0").val() == "" ? [] : $("#val0").val().split(",");
	var selectPartCodesArray = $("#val1").val() == "" ? [] : $("#val1").val().split(",");
	var selectPartNamesArray = $("#val2").val() == "" ? [] : $("#val2").val().split(",");
	var selectAvaiLableAmountArray = $("#val3").val() == "" ? [] : $("#val3").val().split(",");
	var selectSupplierName = $("#val4").val() == "" ? [] : $("#val4").val().split(",");
	var selectSupplierCode = $("#val5").val() == "" ? [] : $("#val5").val().split(",");
	var selectPartPrice = $("#val6").val() == "" ? [] : $("#val6").val().split(",");
	
	var $tab = $("#addPartDetails");
	
	// 创建行对象
	var tempTrArray = [];
	for(var i = 0 ; i < selectStockIdsArray.length; i++){
		
		// 判断行信息是否已经添加过了，如果没有添加，则创建行内容，如果添加过了则不再添加
		if(stockIds.indexOf(selectStockIdsArray[i]) != -1){
			delete selectStockIdsArray[i];
		}else{
			$tr = $tab.createRow();
			tempTrArray.push($("tr:last","#addPartDetailsTbody"));
			stockIds += selectStockIdsArray[i] +  ",";
		}
	}
	
	
	/*
	 * 首层循环，循环需要添加的行数
	 */
	for(var i = 0, trIndex = 0; i < selectStockIdsArray.length; i++){
		
		if( typeof(selectStockIdsArray[i]) == "undefined"){
			continue;
		}
		var tempTdArray = $(tempTrArray[trIndex++]).find("td");
		
 		// 二层循环循环添加行的列，给每个列添加数据
 		for(var j = 0 ; j < tempTdArray.length; j++){
 			
 			// 添加样式
 			if(thStyleArray.length > 0){
 				$(tempTdArray[j])[0].style.cssText = thStyleArray[j];
 			}
 			
			switch(j){
			case 0:
				//行号列
				
				break;
			case 1:
				// TODO 需要添加到内存对象中取
				$(tempTdArray[j]).html("<input type='checkbox' name='tempPartStockId' value='"+selectStockIdsArray[i]+"'/>");
				break;
			case 2:
				$(tempTdArray[j]).html(selectPartCodesArray[i]);
				break;
			case 3:
				$(tempTdArray[j]).html(selectPartNamesArray[i] == "null" ? "" : "<div style='overflow:hidden;white-space:nowrap;' >"+selectPartNamesArray[i]+"</div>");
				break;
			case 4:
				$(tempTdArray[j]).html(selectAvaiLableAmountArray[i] == "null" ? "0" : selectAvaiLableAmountArray[i]);
				break;
			case 5:
				$(tempTdArray[j]).html(selectPartPrice[i] == "null" ? "" : selectPartPrice[i]);
				break;
			case 6:
				$(tempTdArray[j]).html(selectSupplierName[i] == "null" ? "" : selectSupplierName[i]);
				break;
			case 7:
				$(tempTdArray[j]).html(selectSupplierCode[i] == "null" ? "" : selectSupplierCode[i]);
				break;
			case 8:
				// 添加input
				$(tempTdArray[j]).html("<input type='text' name='partCount_input' datatype='1,is_digit_0,30' onkeyup='checkCountAndSumPrice(this)' />");
				break;
			case 9:
				// 添加操作FUN
				$(tempTdArray[j]).html("<a title='删除' class='op' href='#' onclick='delRow(this)'>[删除]</a>");
				break;
			default:
				break;
			}
			
		}
 		
	}

	
	// 显示添加行后的table,并复制style
 	if(i > 0 && !showStatus){
		$("#showPartsDiv").css("visibility","visible");
		
		// 将table的显示状态置为true，则添加配件一次后，以后全部显示
		showStatus = true;
		
		// 调用计算表格TH样式函数
		initTdStyle();
		
	}
	
	// 初始化行号
	initLineNum();
};

// 检测客户输入正确性及计算总金额
function checkCountAndSumPrice(obj){
	if(checkCount(obj)){
		sumPartPrice();
	}
}

// 用来计算配件总价格
function sumPartPrice(){
	
	var sum = 0;
	
	// 计算每一行的总价格，并合计
	$("#addPartDetailsTbody > tr").each(function(index,obj){
		// 第5列为价格，第8列为客户输入数量
		var count = $($(obj).find("td")[8]).find("input").val();
		sum += parseFloat($($(obj).find("td")[5]).text()) * parseFloat((count == "" ? 0 : count));	
		sum = Math.round(sum * 100) / 100;
	});
	
	$("#AMOUNT").val((typeof(sum).toString() == "undefined" || typeof(sum).toString().toUpperCase() == "NAN") ? 0 : sum);
}

// 提交前的校验:通过return true;否则 false
function validForm(){
	var returnVal = false;
	var countArray = $("input[name='partCount_input']");
	if(countArray.length > 0){
		for(var i = 0 ; i < countArray.length ; i++){
			returnVal = checkCount(countArray[i]);
			if(!returnVal){
				return returnVal;
			}
		}
	}else{
		alertMsg.error("请选择需要申请的配件");
		returnVal = false;
	}
	return returnVal;
}

// 检测输入对象是否符合要求
function checkCount(obj){
	
	if(!$.trim($(obj).val())){
		alertMsg.error("申请数量不能为空");
		$(obj).val("").focus();
		return false;
	}
	
	var pattern = /^\+?[1-9][0-9]*$/;
	if (!pattern.exec($(obj).val())){
		alertMsg.error("只能输入非零的正整数，并且不能以0开头.");
		$(obj).val("").focus();
		return false;
	}
	
	// 获取可用数量
	var validCount = $($(obj).parent().parent().find("td")[4]).text();
	validCount = $.trim(validCount) == "" ? 0 : parseFloat($.trim(validCount));
	
	if($(obj).val() > validCount){
		alertMsg.error("申请数量不能大于可用数量");
		$(obj).val("").focus();
		return false;
	}
	
	return true;
}
</script>