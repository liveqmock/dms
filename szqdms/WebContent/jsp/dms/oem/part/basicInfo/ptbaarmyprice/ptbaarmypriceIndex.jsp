<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件军品价格管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
//var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/searchArmyPrice.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPriceLogAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-pjlb");
	});
	
    $("#btnExp").click(function(){
        var $f = $("#fm-pjcx");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/expArmyPrice.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });

    //配件明细导入按钮响应
    $("#btnImp").bind("click", function () {
        importXls("PT_BA_ARMY_PRICE_TMP",null,4,3,"/jsp/dms/oem/part/basicInfo/ptbaarmyprice/armyPriceImportSuccess.jsp");
    });
	
	
});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbaarmyprice/ptbaarmypriceAdd.jsp?action=2", "editPjda", "修改配件军品价格", diaAddOptions);
}


//金额格式化
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
function doSearch(){
	var $f = $("#fm-pjcx");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-pjlb");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件军品价格管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>						
					    <td><label>配件代码：</label></td>					   
					    <td><input type="text" id="in-PART_CODE"  name="in-PART_CODE" datasource="PART_CODE"  datatype="1,is_null,300" operation="like"  /></td>
						 <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="in-PART_NAME"  name="in-PART_NAME" datasource="PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>
					</tr>						
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button"  id="btnExp" name="btnExp">模版导出</button></div></div></li>
	                    <li><div class="button"><div class="buttonContent"><button type="button"  id="btnImp" name="btnImp">明细导入</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_yhlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>									
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>							
							<th fieldname="ARMY_PRICE" refer="amountFormat" align="right">军品价格</th>						
							<th colwidth="55" type="link" title="[编辑]"  action="doUpdate" >操作</th>
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>