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
                        <td><input readonly="readonly" type="text" name="OVERSTOCK_NO_S" style="width:180px;" /></td>
                        <td><label>申请单状态：</label></td>
                        <td><input readonly="readonly" type="text" name="APPLY_STATUS_S" style="width:180px;"  /></td>
                        <td><label>申请人：</label></td>
                        <td><input readonly="readonly" type="text" name="APPLY_USER_S" style="width:180px;"  /></td>
                    </tr>
                    <tr>
                        <td><label>申请时间：</label></td>
                        <td><input readonly="readonly" type="text" name="APPLY_DATE_S" style="width:180px;" /></td>
                        <td><label>申请单总金额：</label></td>
                        <td colspan="3"><input readonly="readonly" type="text" name="AMOUNT_S" style="width:180px;" /></td>
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
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="detailsPartInfoBtn">关闭</button></div></div></li>
                    </ul>
                </div>
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
                            <th fieldname="PART_NAME" class="desc" colwidth="120">配件名称</th>
                            <th fieldname="PART_CODE" class="desc">配件代码</th>
                            <th fieldname="APPLY_COUNTS" >申请数量</th>
                            <th fieldname="UNIT" >计量单位</th>
                            <th fieldname="PART_TYPE" >配件类型</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
    </div>
    </div>
    
    <script type="text/javascript">
	$("#detailsPartInfoBtn").click(function(){
		$.pdialog.closeCurrent();
		return false;
	}) 
    var selectOverstockId = $("#tab-index").getSelectedRows()[0].attr("OVERSTOCK_ID");
    
    $(function(){
    	var getOverstockInfoURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/queryOverstockApplyById.ajax";
    	// 获取配件主信息
		sendPost(getOverstockInfoURL+"?overstockId="+selectOverstockId,"","",callbackShowPartInfoFun,null,null);
		
		// 根据orderId查询订单主信息的回调函数
		function callbackShowPartInfoFun(res,sData){
			
			// 此变量保存回调对象中包含的零件JSON信息
			var applicationInfo;
			
			// 判断浏览器
			var explorer = window.navigator.userAgent;
			// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
			if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
				applicationInfo = res.text;
			}else{
				applicationInfo = res.firstChild.textContent;
			}
			
			// 显示对应的值
			showOverstockInfo(eval("(" + applicationInfo + ")"))
			
			// 显示配件信息
			showOverstiockPartInfo();
		}
		
		// 显示订单主信息详情
    	function showOverstockInfo(jsonObj){
    		$("#overstock_info_s").find("input").each(function(index,obj){
    			var inputName = $(obj).attr("name")
    			$(obj).val(jsonObj[inputName]);
    		});
    	}
		
    	// 显示配件详细信息
    	function showOverstiockPartInfo(){
    		var getOverstockPartInfoURL = "<%=request.getContextPath()%>/part/storageMng/overstockParts/DealerOverstockPartApplyAction/queryOverstockApplyPartInfo.ajax";
    		doFormSubmit($("#searchPartInfoForm"), getOverstockPartInfoURL+"?overstockId="+selectOverstockId,null,1,null, "fm-part-info-show-table");
    	}
    })
    </script>
</div>