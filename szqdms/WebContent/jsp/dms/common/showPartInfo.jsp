<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%
	String contextPath = request.getContextPath();
	String isSingle = request.getParameter("isSingle") == null ? "1" : request.getParameter("isSingle") ;/* 是否单选(1:单选,0:多选,默认为1) */
	String showAllPart = request.getParameter("showAllPart") == null ? "0" : request.getParameter("showAllPart") ;/* 是否显示全部配件(1:全部显示, 0:显示状态为有效的配件配件,默认为0) */
	String showPartType = request.getParameter("showPartType") == null ? "1" : request.getParameter("showPartType") ;/* 是否显示配件类型(1:显示, 0:不显示,默认为1) */
	String showMinPack = request.getParameter("showMinPack") == null ? "1" : request.getParameter("showMinPack") ;/* 是否显示最小包装数(1:显示,0:不显示,默认为1) */
	String showMinUnit = request.getParameter("showMinUnit") == null ? "1" : request.getParameter("showMinUnit") ;/* 是否显示最小包装单位(1:显示,0:不显示,默认为1) */
	String showSalePrice = request.getParameter("showSalePrice") == null ? "0" : request.getParameter("showSalePrice") ;/* 是否显示销售价格 (1:显示, 0:不显示,默认为0) */
	String showPlanPrice = request.getParameter("showPlanPrice") == null ? "0" : request.getParameter("showPlanPrice") ;/* 是否显示计划价格(1:显示, 0:不显示,默认为0) */

	System.out.println("isSingle = " + isSingle + " , showAllPart = " + showAllPart + " showPartType = " + showPartType + " showMinPack = " + showMinPack + " showMinUnit = " + showMinUnit + " showSalePrice = " + showSalePrice + " showPlanPrice = " + showPlanPrice );
%>
<div id="dia-showOrgTreeInfo">
	<div class="page">
		<div class="pageHeader" style="" >
			<form method="post" id="fm-Info" class="editForm" >
				<input type="hidden" id="PART_STATUS" name="PART_STATUS" datasource="PART_STATUS" datatype="1,is_null,300" value="<%=DicConstant.PJZT_01%>"/>
				<!-- 隐藏域 -->
				<div align="left" style="padding-bottom:5px;">
					<table class="editTable" id="tab-showInfo">
						<tr>
							<td><label>配件代码：</label></td>
						    <td><input type="text" id="dia-partCode" name="dia-partCode" datasource="PART_CODE" datatype="1,is_null,300"  operation="like" /></td>
						    <td><label>配件名称：</label></td>
						    <td><input type="text" id="dia-partName" name="dia-partName" datasource="PART_NAME" datatype="1,is_null,300"  operation="like" /></td>
						    <td>
						    	<div class="button"><div class="buttonContent"><button type="button" id="btn-Search">查&nbsp;&nbsp;询</button></div></div>
						    	<div class="button"><div class="buttonContent"><button type="button" id="btn-ok">确&nbsp;&nbsp;定</button></div></div>
								<div class="button" style="margin-left:5px;"><div class="buttonContent"><button class="close" id="btn-Close" type="button">关&nbsp;&nbsp;闭</button></div></div>
						    </td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
    <div class="pageContent">
        <div id="page_grid">
            <table style="display:none;width:100%;" id="tab-grid-index" name="tablist" ref="page_grid" refQuery="fm-Info">
                <thead>
                    <tr>
                        <th type="single" name="XH" unique="PART_ID"></th>
                        <th fieldname="PART_CODE" colwidth="130px">配件代码</th>
                        <th fieldname="PART_NAME" colwidth="150px">配件名称</th>
                        <th fieldname="PART_TYPE" colwidth="60px">配件类型</th>
                        <th fieldname="MIN_PACK" colwidth="70px">最小包装数</th>
                        <th fieldname="MIN_UNIT" colwidth="80px">最小包装单位</th>
                        <th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价格</th>
                        <th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
		</div>
	</div>
</div>
<textarea id="val0" name="multivals" column="1" style="display:none" ></textarea>
<textarea id="val1" name="multivals" style="display:none" ></textarea>
<textarea id="val2" name="multivals" style="display:none" ></textarea>

<script type="text/javascript">

$(function(){
	
	
	// 配件控件默认参数
	var partOption = {isSingle: "<%=isSingle%>" === "" ? 1 : <%=isSingle%>, 	  	  /* 是否单选(1:单选, 0:多选,默认为1) */
		  		      showAllPart: "<%=showAllPart%>" === "" ? 0 : <%=showAllPart%>, 	  /* 是否显示全部配件(1:全部显示,0:显示状态为有效的配件配件,默认为0) */
		  			  showPartType: "<%=showPartType%>" === "" ? 1 : <%=showPartType%>, 	  /* 是否显示配件类型(1:显示,0:不显示,默认为1) */
		  			  showMinPack: "<%=showMinPack%>" === "" ? 1 : <%=showMinPack%>,	  /* 是否显示最小包装数(1:显示,0:不显示,默认为1) */
		  			  showMinUnit: "<%=showMinUnit%>" === "" ? 1 : <%=showMinUnit%>,	  /* 是否显示最小包装单位(1:显示,0:不显示,默认为1) */
		  			  showSalePrice: "<%=showSalePrice%>" === "" ? 0 : <%=showSalePrice%>,  /* 是否显示销售价格 (1:显示,0:不显示,默认为0) */
		  			  showPlanPrice: "<%=showPlanPrice%>" === "" ? 0 : <%=showPlanPrice%>   /* 是否显示计划价格(1:显示,0:不显示,默认为0) */
		    };
	
	// 控件显示参数初始化显示页面
	(function(){
		if(!partOption["isSingle"]) $("TH[name='XH']", "#tab-grid-index").attr("type", "multi");// 是否多选
		if(partOption["showAllPart"]) $("#PART_STATUS").attr("action", "show");// 是否只显示配件状态为有效的配件信息
		if(!partOption["showPartType"]) $("TH[fieldname='PART_TYPE']", "#tab-grid-index").hide();// 是否显示配件类型
		if(!partOption["showMinPack"]) $("TH[fieldname='MIN_PACK']", "#tab-grid-index").hide();// 是否显示最小包装数
		if(!partOption["showMinUnit"]) $("TH[fieldname='MIN_UNIT']", "#tab-grid-index").hide();// 是否显示最小包装单位
		if(!partOption["showSalePrice"]) $("TH[fieldname='SALE_PRICE']", "#tab-grid-index").hide();// 是否显示销售价格
		if(!partOption["showPlanPrice"]) $("TH[fieldname='PLAN_PRICE']", "#tab-grid-index").hide(); // 是否显示计划价格
	})();
	
    // 查询按钮响应方法
    $("#btn-Search").click(function(){
   	 var $f = $("#fm-Info");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PartInfoQueryAction/queryListInfo.ajax";
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-Search", 1, sCondition, "tab-grid-index");
    });
    
    // 确定选择
    $("#btn-ok").click(function(){
    	var res = {};
    	res["PART_ID"] = $("#val0").html();
    	res["PART_CODE"] = $("#val1").html();
    	res["PART_NAME"] = $("#val2").html();
    	
    	// 配件选择回调函数
    	showPartInfoCallback(res);
    	$("#btn-Close").click();
    });
})

// 多选函数
function doCheckbox(checkbox){
	var arr = [];
	var $checkbox = $(checkbox);
	var $tr= $checkbox.parent().parent().parent();
	var PART_ID = $tr.attr("PART_ID");
	var PART_CODE = $tr.attr("PART_CODE");
	var PART_NAME = $tr.attr("PART_NAME");
	arr.push(PART_ID);
	arr.push(PART_CODE);
	arr.push(PART_NAME.replace(/\,/g, "，"));
	multiSelected($checkbox,arr);
}

// 单选函数
function doRadio(radio){
	var $radio = $(radio)
	var $tr= $radio.parent().parent().parent();
	var PART_ID = $tr.attr("PART_ID");
	var PART_CODE = $tr.attr("PART_CODE");
	var PART_NAME = $tr.attr("PART_NAME");
	$("#val0").html(PART_ID);
	$("#val1").html(PART_CODE);
	$("#val2").html(PART_NAME);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());

}
</script>