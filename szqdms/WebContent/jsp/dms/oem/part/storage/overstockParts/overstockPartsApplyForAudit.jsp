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
                        <td><input readonly="readonly" type="text" name="ORG_NAME_S" style="width:180px;"  /></td>
                        <td><label>申请渠道代码：</label></td>
                        <td colspan="3"><input readonly="readonly" type="text" name="ORG_CODE_S" style="width:180px;"   /></td>
                    </tr>
                </table>
               
            </div>
        </form>
    </div>
	<form id="searchPartInfoForm" method="post" style="display:none">
    </form>
    <div class="pageContent">
        <div id="fm-part-info-show"> 
            <table style="display:none;width:100%;" id="fm-part-info-show-table" ref="fm-part-info-show" refQuery="searchPartInfoForm" >
                    <thead>
                        <tr>
                        	<th type="multi" name="XH" unique="STOCK_ID" style="display:none;"></th>
                            <th fieldname="PART_CODE" >配件代码</th>
                            <th fieldname="PART_NAME"  colwidth="120">配件名称</th>
                            <th fieldname="AVAILABLE_AMOUNT">可用数量</th>
                            <th fieldname="SALE_PRICE">配件价格</th>
                            <th fieldname="ORG_NAME">渠道名称</th>
                            <th fieldname="ORG_CODE">渠道代码</th>
                            <th fieldname="APPLY_COUNTS" refer="applyEditCountFun">申请数量</th>
                        </tr>
                    </thead>
                    <tbody id="fm-part-info-show-table-tbody">
                    </tbody>
            </table>
        </div>
    </div>
    <br />
    <br />
     <div class="pageHeader" >
     <div class="searchBar" align="left" >
    	<form id="tempUmsaveForm" method="post">
			<%--积压件申请单ID --%>
			<input type="hidden" name="overstockId" id="overstockId_f"  datasource="overstockId_f" val=""/>
			<input type="hidden" name="overstockApplyStatus" id="overstockApplyStatus" datasource="overstockApplyStatus" val=""/>
			<table class="searchContent">
				<tr>
                    <td align="right" width="100"><label>审核确认：</label></td>
                    <td>
                    	<label for="applyStatus_f_1" style="float: left;">同意</label>
                    	<input type="radio" name="applyStatus_f" id="applyStatus_f_1" style="margin-top: 3px;" value="<%=DicConstant.JYJSQZT_05%>"/>
                    	<label for="applyStatus_f_2" style="float: left;">驳回</label>
                    	<input type="radio" name="applyStatus_f" id="applyStatus_f_2" style="margin-top: 3px;" value="<%=DicConstant.JYJSQZT_06%>"/>
                    </td>
                </tr>
				<tr>
                    <td align="right"><label>备注：</label></td>
                    <td>
                    	<textarea style="width:300px;height:100px;" name="checkRemark_f" id="checkRemark_f" datasource="checkRemark_f" ></textarea>
                    </td>
                </tr>
			</table>
			 <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="applyConfirmBtn">确&nbsp;&nbsp;认</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="applyEditCancelBtn">取&nbsp;&nbsp;消</button></div></div></li>
                    </ul>
            </div>
      	</form>
      	</div>
      </div>
    </div>
    
    <script type="text/javascript">
    
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
    	$("#applyConfirmBtn").click(function(){
    		// 先校验
    		if(validForm()){
        		var overstockId = $("input[name='OVERSTOCK_ID_S']").val();
        		$("#overstockId_f").val(overstockId);
        		$("#overstockApplyStatus").val($("input[name='applyStatus_f']:checked").val());
        		
        		// 保存Action
        		var saveActionURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/applyStatusSave.ajax";
        		
        		// 保存操作
        		var $f = $("#tempUmsaveForm");
	    		sCondition = $f.combined(1) || {};
	    		doNormalSubmit($f, saveActionURL, "applyConfirmBtn", sCondition, function(responseText,tabId,sParam){
	    			$.pdialog.closeCurrent();
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
    
    // 校验Form
    function validForm(){
    	// 获取申请确认状态
    	if($("input[name='applyStatus_f']:checked").size() <= 0){
    		alertMsg.error("请选择确认状态");
    		return false;
    	}
    	
    	var validStr = is_noquotation($("#checkRemark_f"));
    	if(!(validStr === true)){
    		alertMsg.error(validStr);
    		return false;
    	}
    	return true;
    }
 
    </script>
</div>