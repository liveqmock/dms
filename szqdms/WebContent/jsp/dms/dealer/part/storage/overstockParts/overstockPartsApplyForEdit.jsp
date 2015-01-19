<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div  style="width:100%;">
    <div class="page" >
    <div class="pageHeader" >
        <form method="post">
            <div class="searchBar" align="left" >
                <table class="searchContent" id="overstock_info_s">
                    <tr>
                        <td><label>申请单号：</label></td>
                        <td>
                        	<input readonly="readonly" type="hidden" name="OVERSTOCK_ID_S" style="width:180px;" />
                        	<input readonly="readonly" type="text" name="OVERSTOCK_NO_S" style="width:180px;" />
                        </td>
                        <td><label>申请单状态：</label></td>
                        <td><input readonly="readonly" type="text" name="APPLY_STATUS_S" style="width:180px;"  /></td>
                        <td><label>申请人：</label></td>
                        <td><input readonly="readonly" type="text" name="APPLY_USER_S" style="width:180px;"  /></td>
                    </tr>
                    <tr>
                        <td><label>申请时间：</label></td>
                        <td><input readonly="readonly" type="text" name="APPLY_DATE_S" style="width:180px;" /></td>
                        <td><label>申请单总金额：</label></td>
                        <td colspan="3"><input readonly="readonly" type="text" id="AMOUNT_S" name="AMOUNT_S" style="width:180px;" /></td>
                    </tr>
                    <tr>
                        <td><label>申请渠道名称：</label></td>
                        <td><input readonly="readonly" type="text" name="ORG_CODE_S" style="width:180px;"  /></td>
                        <td><label>申请渠道代码：</label></td>
                        <td colspan="3"><input readonly="readonly" type="text" name="ORG_NAME_S" style="width:180px;"   /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                    	<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="applyEditSaveBtn">确&nbsp;&nbsp;定</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="applyEditCancelBtn">取&nbsp;&nbsp;消</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
	<form id="searchPartInfoForm" method="post" style="display:none">
    </form>
    <div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add" href="javascript:addParts();"><span>添加配件</span></a></li>
			</ul>
		</div>
        <div id="fm-part-info-show"> 
            <table style="display:none;width:100%;" id="fm-part-info-show-table" ref="fm-part-info-show" refQuery="searchPartInfoForm" >
                    <thead>
                        <tr>
                        	<th type="multi" name="XH" unique="STOCK_ID"></th>
                            <th fieldname="PART_CODE" >配件代码</th>
                            <th fieldname="PART_NAME"  colwidth="120">配件名称</th>
                            <th fieldname="AVAILABLE_AMOUNT">可用数量</th>
                            <th fieldname="SALE_PRICE">配件价格</th>
                            <th fieldname="ORG_NAME">渠道名称</th>
                            <th fieldname="ORG_CODE">渠道代码</th>
                            <th fieldname="APPLY_COUNTS" refer="applyEditCountFun">申请数量</th>
                            <th colwidth="80" type="link" title="[删除]"  action="applyDeletePartInfoFun" >操作</th>
                        </tr>
                    </thead>
                    <tbody id="fm-part-info-show-table-tbody">
                    </tbody>
            </table>
        </div>
        
	    <textarea id="val0" name="multivals" column="1" style="display:none"></textarea>
		<textarea id="val1" name="multivals"  style="display:none"></textarea>
		<textarea id="val2" name="multivals"  style="display:none"></textarea>
		<textarea id="val3" name="multivals"  style="display:none"></textarea>
		<textarea id="val4" name="multivals"  style="display:none"></textarea>
		<textarea id="val5" name="multivals" style="display:none"></textarea>
		<textarea id="val6" name="multivals" style="display:none"></textarea>
		
    </div>
    </div>
    
    <form id="tempUmsaveForm" style="display:none" method="post" class="editForm">
			<input type="hidden" name="stockIds_f" id="stockIds_f" datasource="stockIds_f" val=""/>
			<input type="hidden" name="partsCount_f" id="partsCount_f" datasource="partsCount_f" val="" />
			<%--积压件初始状态 --%>
			<input type="hidden" name="overstockApplyStatus" id="overstockApplyStatus" datasource="overstockApplyStatus" value="<%=DicConstant.JYJSQZT_01 %>" />
			<input type="hidden" name="overstockId_f" id="overstockId_f" datasource="overstockId_f" val=""/>
			<input type="hidden" name="overstockNo_f" id="overstockNo_f" datasource="overstockNo_f" val=""/>
	</form>
	
    <script type="text/javascript">
    
    var dia_dialog = $("body").data("overstockApplyEdit");
    
    // 获取表格下的数据显示行
    function getTableContentTbody(tableId){
    	if($("#"+tableId+"_content > tbody > tbody").size() > 0){
    		return $("#"+tableId+"_content > tbody > tbody");
    	}else{
    		return $("#"+tableId+"_content > tbody");
    	}
    }

	
	// 初始化加载的Table：删除行操作:obj 当前行对象
	var firstRemoveStatus = true;// 是否第一次删除标示,删除一个元素后变为false，用于保存样式
    function applyDeletePartInfoFun(obj){
    	
    	// 判断是否为第一次删除，如果是则复制第一行的首行样式，防止删除第一行导致其他行样式改变
    	if(firstRemoveStatus){
    		// 获取首行的TD样式
    		initTdStyle();
    		// 为所有行添加TD样式
    		firstRemoveStatus = false;
    	}
    	
    	alertMsg.confirm("确定删除此配件？", {
            okCall: function(){
            	// 给TD添加样式
            	addAllRowFirstTDStyle();
            	
            	$(obj).remove();
            	$("div[class='gridScroller']").css("background-color","#EAEEF5");
            	
            	/*
            	 *添加此处代码是因为框架JS存在bug，
            	 * mine.table.js：createRow函数中，代码is(:visible)判断，在IE11下全行删除后判断为false，导致在当前页面创建了2个相同的xxxx_content的Table
            	 * ID相同则导致其他JS无法获取DOM元素
            	 * 添加此处代码，则JS在来判断is(:visible)为ture，消除创建2个形同ID的bug
            	 * 但此代码有影响，表格Title与内容存在一定间距
            	 * 其他相同这样的代码，用处相同
            	 */
            	if($("#fm-part-info-show-table_content").height() == 0){
            		$("#fm-part-info-show-table_content").append("<tr style='height:1px;line-height:1px;'>&nbsp;</tr>");
            	}
            	
            	// 初始化行号
            	initLineNum();
            	
            	// 重新初始化临时选中变量的值
            	initStockIdsVal();
            	
            	// 重新初始化textarea中的值
            	initTextareaVal();
            	
            	// 计算配件总金额
            	sumPartPrice();
            }
      	});
    }
	
    // 动态添加的行：删除行操作
    function delRow(obj){
    	
    	alertMsg.confirm("确定此配件？", {
            okCall: function(){
            	// 给TD添加样式
            	addAllRowFirstTDStyle();
            	
            	$(obj).parent().parent().remove();
            	$("div[class='gridScroller']").css("background-color","#EAEEF5");
            	
            	if($("#fm-part-info-show-table_content").height() == 0){
            		$("#fm-part-info-show-table_content").append("<tr style='height:1px;line-height:1px;'>&nbsp;</tr>");
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
	
	// 保存首行的TD样式，用于给后面的所有行添加TD的样式，防止删除首行后，其他行的TD宽度发生变化
    var thStyleArray = [];
    function initTdStyle(){
    	$("tr:first","#fm-part-info-show-table").find("th").each(function(index,thObj){
    		thStyleArray.push($(thObj).attr("style"));
    	});
    }

    
    // 给所有行添加首行TD样式
    function addAllRowFirstTDStyle(){
    	getTableContentTbody("fm-part-info-show-table").find("tr").each(function(index,objTr){
    		if(index != 0){
	    		$(objTr).find("td").each(function(indexj,objTd){
	    			$(objTd)[0].style.cssText = thStyleArray[indexj];
	    		});
    		}
    	});
    }
    
    // 初始化行号
    function initLineNum(){
    	getTableContentTbody("fm-part-info-show-table").find("tr").each(function(index,obj){
    		$($(obj).find("td")[0]).html(index + 1).css("text-align","center");
    	});
    }
    
	// 重新初始化stockId的值
	var stockIds = "";
    function initStockIdsVal(){
    	stockIds = "";
    	var checkBoxArray = getTableContentTbody("fm-part-info-show-table").find("input:checkbox");
    	checkBoxArray.each(function(index,obj){
    		if(index+1 == checkBoxArray.length){
    			stockIds += $(checkBoxArray[index]).val();
    		}else{
    			stockIds += $(checkBoxArray[index]).val() + ",";
    		}
    	});
    }
 
    /**
     * 确定配件选择
     */
     // 已选择的仓库零件代码主键，此字符串随用户添加，删除变化而变化，用于比较用户是否已经添加过此配件
    var firstAddRowStatus = true;
    function addPartsCallBack(){
    	
    	// 判断是不是第一次添加行，如果是，则初始化行中的TD样式
    	if(firstAddRowStatus){
    		// 获取首行的TD样式
    		initTdStyle();
    		firstAddRowStatus = false;
    	}

    	var selectStockIdsArray = $("#val0").val() == "" ? [] : $("#val0").val().split(",");
    	var selectPartCodesArray = $("#val1").val() == "" ? [] : $("#val1").val().split(",");
    	var selectPartNamesArray = $("#val2").val() == "" ? [] : $("#val2").val().split(",");
    	var selectAvaiLableAmountArray = $("#val3").val() == "" ? [] : $("#val3").val().split(",");
    	var selectSupplierName = $("#val4").val() == "" ? [] : $("#val4").val().split(",");
    	var selectSupplierCode = $("#val5").val() == "" ? [] : $("#val5").val().split(",");
    	var selectPartPrice = $("#val6").val() == "" ? [] : $("#val6").val().split(",");
    	
    	var $tab = $("#fm-part-info-show-table");
    	
    	// 创建行对象
    	var addTrArray = [];
    	for(var i = 0 ; i < selectStockIdsArray.length; i++){
    		
    		// 判断行信息是否已经添加过了，如果没有添加，则创建行内容，如果添加过了则不再添加
    		if(stockIds.indexOf(selectStockIdsArray[i]) != -1){
    			delete selectStockIdsArray[i];
    		}else{
    			$tab.createRow();
    			
    			// 获取最新添加的行对象
    			addTrArray.push($("tr:last",getTableContentTbody("fm-part-info-show-table")));
    			stockIds += selectStockIdsArray[i] +  ",";
    		}
    	}
    	
    	/*
    	 * 首层循环，循环需要添加的行数,必须使用此变量来循环selectStockIdsArray
    	 */
    	
    	for(var i = 0 ,ii = 0; i < selectStockIdsArray.length; i++){
    		
    		if( typeof(selectStockIdsArray[i]) == "undefined"){
    			continue;
    		}
    		
    		// 送保存添加行的数组中取行对象时，记得从0开始，所以取了变量ii
    		var tdArray = $(addTrArray[ii++]).find("td");
    		
     		// 二层循环循环添加行的列，给每个列添加数据
     		for(var j = 0 ; j < tdArray.length; j++){
     			
     			// 复制style：防止删除首行时，其他行的列的宽度会发生变化
    			// 添加样式
     			if(thStyleArray.length > 0){
     				$(tdArray[j])[0].style.cssText = thStyleArray[j];
     			}
     			
    			switch(j){
    			case 0:
    				//行号列
    				
    				break;
    			case 1:
    				$(tdArray[j]).html("<input type='checkbox' name='tempPartStockId' value='"+selectStockIdsArray[i]+"'/>");
    				break;
    			case 2:
    				$(tdArray[j]).html(selectPartCodesArray[i]);
    				break;
    			case 3:
    				$(tdArray[j]).html(selectPartNamesArray[i] == "null" ? "" :  "<div style='overflow:hidden;white-space:nowrap;' >"+selectPartNamesArray[i]+"</div>");
    				break;
    			case 4:
    				$(tdArray[j]).html(selectAvaiLableAmountArray[i] == "null" ? "0" : selectAvaiLableAmountArray[i]);
    				break;
    			case 5:
    				$(tdArray[j]).html(selectPartPrice[i] == "null" ? "" : selectPartPrice[i]);
    				break;
    			case 6:
    				$(tdArray[j]).html(selectSupplierName[i] == "null" ? "" : selectSupplierName[i]);
    				break;
    			case 7:
    				$(tdArray[j]).html(selectSupplierCode[i] == "null" ? "" : selectSupplierCode[i]);
    				break;
    			case 8:
    				// 添加input
    				$(tdArray[j]).html("<input type='text' name='partCount_input' onkeyup='checkCountAndSumPrice(this)' />");
    				break;
    			case 9:
    				// 添加操作FUN
    				$(tdArray[j]).html("<a title='删除' class='op' href='#' onclick='delRow(this)'>[删除]</a>");
    				break;
    			default:
    				break;
    			}
    			
    			
    		}
     		
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
    	var validCount = $($(obj).parent().parent().parent().find("td")[4]).text();
    	validCount = $.trim(validCount) == "" ? 0 : parseFloat($.trim(validCount));
    	
    	if($(obj).val() > validCount){
    		alertMsg.error("申请数量不能大于可用数量");
    		$(obj).val("").focus();
    		return false;
    	}
    	
    	return true;
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
	
	// 初始化查询申请单主键ID
    var selectOverstockId = $("#tab-index").getSelectedRows()[0].attr("OVERSTOCK_ID");
    
    // Ajax动态加载数据
    $(function(){
    	
    	// 获取申请单主信息ActionURL
    	var getOverstockInfoURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/queryOverstockApplyById.ajax";
    	
    	// 获取配件主信息
		sendPost(getOverstockInfoURL+"?overstockId="+selectOverstockId,"","",callbackShowOverstockInfoFun,null,null);
		
		// 查询配件主信息回调函数，加载后台查询到的数据
		function callbackShowOverstockInfoFun(res,sData){
			
			// 此变量保存回调对象中包含的后台查询到的数据
			var applicationInfo;
			
			// 判断浏览器
			var explorer = window.navigator.userAgent;
			// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
			if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
				applicationInfo = res.text;
			}else{
				applicationInfo = res.firstChild.textContent;
			}

			// 调用显示主信息的函数
			showOverstockInfo(eval("(" + applicationInfo + ")"))
			
			// 调用查询主信息的子表配件信息
			showOverstiockPartInfo();
		}
		
		// 显示申请单主信息详情
    	function showOverstockInfo(jsonObj){
    		$("#overstock_info_s").find("input").each(function(index,obj){
    			var inputName = $(obj).attr("name")
    			$(obj).val(jsonObj[inputName]);
    		});
    	}
		
    	// 显示申请单配件详细信息
    	function showOverstiockPartInfo(){
    		var getOverstockPartInfoURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/queryOverstockApplyPartInfo.ajax";
    		doFormSubmit($("#searchPartInfoForm"), getOverstockPartInfoURL+"?overstockId="+selectOverstockId,null,1,null, "fm-part-info-show-table");
    	}
    	
    	
    	// 保存申请单
    	$("#applyEditSaveBtn").click(function(){
    		// 先校验
    		if(validForm()){
    			
        		// 初始化隐藏域textarea中的值，方便获取stockId的值
            	initTextareaVal();
        		var overstockId = $("input[name='OVERSTOCK_ID_S']").val();
        		var overstockNo = $("input[name='OVERSTOCK_NO_S']").val();
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
        		$("#overstockId_f").val(overstockId);
        		$("#overstockNo_f").val(overstockNo);
                    		
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
    	
    	
        // 关闭按钮
    	$("#applyEditCancelBtn").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	}) 
        
    })
    
    // 自定义申请数量显示格式
    function applyEditCountFun($cell){
    	return "<input type='text' name='partCount_input' value='"+$cell.text()+"' onkeyup='checkCountAndSumPrice(this)' >";
    }
    
    /**
     * 打开配件选择页面
     */
    function addParts(){
    	// 首先初始化隐藏域的值
    	initTextareaVal();
    	
    	// 初始化主键ID临时字符串
    	initStockIdsVal();
    	
    	var options = {max:false,width:1000,height:460,mask:true,mixable:false,minable:false,resizable:true,drawable:true};
    	$.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/overstockParts/overstockParts.jsp", "addJYJPartsTemp", "积压件选择", options);
    }
    
	// 用来计算配件总价格 TODO 此处代码存在问题，需要补充修改
    function sumPartPrice(){
    	var sum = 0;
    	// 计算每一行的总价格，并合计
    	getTableContentTbody("fm-part-info-show-table").find("tr").each(function(index,obj){
    		// 第5列为价格，第8列为客户输入数量
    		var count = $($(obj).find("td")[8]).find("input").val();
    		sum += parseFloat($($(obj).find("td")[5]).text()) * parseFloat((count == "" ? 0 : count));	
    		sum = Math.round(sum * 100) / 100;
    	});
    	$("#AMOUNT_S").val((typeof(sum).toString() == "undefined" || typeof(sum).toString().toUpperCase() == "NAN") ? 0 : sum);
    }
    

	// 重新初始化隐藏域textarea中的值
    function initTextareaVal(){
    	
    	// 获取添加的行
    	var trArray = getTableContentTbody("fm-part-info-show-table").find("tr");
    	
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
	
    // checkBox选中函数
    function doCheckbox(checkbox) {
    	// 判断用户选择的配件是否属于同一渠道Code，如果不相同，则不允许用户选择。
    	var orgCodes = $("#val5").val() == "" ? "" : $("#val5").val();
    	
    	// 获取选中checkbox的行对象
    	var trOrgCode = "";
    	var trObj = $(checkbox).parent().parent().parent();
    	if(typeof(trObj.attr("ORG_CODE")) != "undefined"){
    		trOrgCode = trObj.attr("ORG_CODE");
    	}
    	
    	//alert("#val5:"+orgCodes);
    	//alert("tr:"+trOrgCode);
    	
    	if(orgCodes.indexOf(trOrgCode) == -1 && orgCodes != ""){
    		alertMsg.error("不能选择不同渠道的配件");
    		$(checkbox).prop("checked",false);
    		return;
    	}
    	
    	// 保存需要的行对象中的值
    	var arr = [
    	           trObj.attr("STOCK_ID"),// 配件库存主键ID
    	           trObj.attr("PART_CODE"),
    	           trObj.attr("PART_NAME") == "" ? "null" :  trObj.attr("PART_NAME"),
    	           trObj.attr("AVAILABLE_AMOUNT") == "" ? "null" :  trObj.attr("AVAILABLE_AMOUNT"),// 可用数量
    	           trObj.attr("ORG_NAME") == "" ? "null" :  trObj.attr("ORG_NAME"),// 渠道名称
    	           trObj.attr("ORG_CODE") == "" ? "null" :  trObj.attr("ORG_CODE"),// 渠道Code
    	           trObj.attr("SALE_PRICE") == "" ? "null" :  trObj.attr("SALE_PRICE"),// 销售价格
    	          ];
    	//var $checkbox = trObj.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($(checkbox), arr);
    }
    </script>
</div>