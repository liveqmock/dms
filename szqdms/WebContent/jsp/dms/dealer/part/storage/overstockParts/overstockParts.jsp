<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="addJYJParts" style="width:100%;">
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;积压件选择：</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="partForm">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
                    </tr>
                    <tr>
                        <td><label>渠道名称：</label></td>
                        <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="ORG_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>渠道代码：</label></td>
                        <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="ORG_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-parts-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-parts-save">确&nbsp;&nbsp;定</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    
        <div id="partSelectDiv" style="">
            <table style="width:350px;display:none;" id="partsTables" name="tablist" ref="partSelectDiv" refQuery="partForm">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="STOCK_ID" style=""></th>
                            <th fieldname="PART_CODE" class="desc">配件代码</th>
                            <th fieldname="PART_NAME" >配件名称</th>
                            <th fieldname="SALE_PRICE" >配件价格</th>
                            <th fieldname="PART_TYPE" >配件类型</th>
                            <th fieldname="UNIT" >计量单位</th>
                            <th fieldname="AVAILABLE_AMOUNT">可用数量</th>
                            <th fieldname="ORG_NAME" >渠道名称</th>
                            <th fieldname="ORG_CODE">渠道代码</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
      </div>
      
    </div>
</div>
<script type="text/javascript">

// 设置弹出框ID:用来关闭弹出框体
var dialog2 = $("body").data("addJYJPartsTemp");

$(function(){
	 
	// 积压件维护查询
	$("#btn-parts-search").click(function(){
		var seachActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartAction/queryOverstockUsableParts.ajax";
		var $f = $("#partForm");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,seachActionURL,"btn-parts-search",1,sCondition,"partsTables");
	});
	
	// 确定选择配件按钮
	$("#btn-parts-save").click(function(){

		// 回调
		addPartsCallBack();
		$.pdialog.close(dialog2);
	});
	
	$("#btn-parts-search").click();
})

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