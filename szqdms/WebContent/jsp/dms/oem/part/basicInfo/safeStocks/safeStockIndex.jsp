<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:安全库存管理
	 Version:1.0
     Author：suoxiuli 2014-07-26
     Remark：direct type
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>安全库存管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/SafeStockAction/search.ajax";
var doReSetUrl = "<%=request.getContextPath()%>/part/basicInfoMng/SafeStockAction/doReSet.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchSafeStock");//获取页面提交请求的form对象
		//判断上下限的指的合理性下限<上限
		var lowerLimit1 = $("#lowerLimit1").val();
		var lowerLimit2 = $("#lowerLimit2").val();
		var upperLimit1 = $("#upperLimit1").val();
		var upperLimit2 = $("#upperLimit2").val();
		if (parseInt(lowerLimit1) >= parseInt(lowerLimit2)) {
			alert("下限区间值第一个值必须小于第二个值！");
			return false;
		}
		if (parseInt(upperLimit1) >= parseInt(upperLimit2)) {
			alert("上限区间值第一个值必须小于第二个值！");
			return false;
		}
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-safeStockList");
	});
	
	//配件模版导出按钮响应
    $('#btn-expTpl').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=safeStockImp.xls");
        window.location.href = url;
    });

    //配件明细导入按钮响应
    $('#btn-impPromPart').bind('click', function () {
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页                               
        importXls("PT_BA_STOCK_SAFESTOCKS_TMP","",5,3,"/jsp/dms/oem/part/basicInfo/safeStocks/importSuccess.jsp");
    });                         
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/safeStocks/safeStockAdd.jsp?action=2", "修改安全库存", "修改安全库存", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doReSet(rowobj)
{
	$row = $(rowobj);
	var url = doReSetUrl + "?saftyId="+$(rowobj).attr("SAFTY_ID");
	sendPost(url,"doReSet","",doReSetCallBack,"true");
}
//删除回调方法
function  doReSetCallBack(res)
{
	try
	{
		if($row) 
		{   
		    $("#btn-search").trigger("click");
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("stockCode") == 0)
	{  
		$("#stockName").val($("#"+id).val());
		return true;
	}
	
	return true;
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：  配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 安全库存管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchSafeStock">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchSafeStock">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>仓库名称：</label></td>
					    <td>
					    	<!-- input type="text" id="stockCode"  name="stockCode" datasource="STOCK_CODE"  datatype="1,is_null,30" operation="like" /-->
					    	<input type="text" id="stockCode"  name="stockCode" kind="dic" 
				    			src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS='100201'" 
				    			datasource="A.WAREHOUSE_CODE" datatype="1,is_null,30" operation="like" />
				    		<input type="hidden" id="stockName"  name="stockName" datasource="A.WAREHOUSE_NAME"  />
					    </td>
					    <td><label>配件代码：</label></td>
					    <td>
					    	<input type="text" id="partCode"  name="partCode" datasource="A.PART_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>配件名称：</label></td>
					    <td  colspan='3'>
					    	<input type="text" id="partName" name="partName" datasource="A.PART_NAME" datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					<tr>
						<td><label>下限区间：</label></td>
					    <td >
					    	<input type="text" id="lowerLimit1" name="lowerLimit1" datasource="B.LOWER_LIMIT" datatype="1,is_digit,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="lowerLimit2" name="lowerLimit2" datasource="B.LOWER_LIMIT" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
					    </td>
					    <td><label>上限区间：</label></td>
					    <td colspan="3">
					    	<input type="text" id="upperLimit1" name="upperLimit1" datasource="B.UPPER_LIMIT" datatype="1,is_digit,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="upperLimit2" name="upperLimit2" datasource="B.UPPER_LIMIT" datatype="1,is_digit,10" operation="<=" style="width:60px;"/>
					    </td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expTpl" >配件模版导出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-impPromPart" >配件明细导入</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_safeStockList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-safeStockList" name="tablist" ref="page_safeStockList" refQuery="tab-searchSafeStock" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="STOCK_NAME" >仓库名称</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="LOWER_LIMIT" >库存下限</th>
							<th fieldname="UPPER_LIMIT" >库存上限</th>
							<th colwidth="85" type="link" title="[编辑]|[重置]"  action="doUpdate|doReSet" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>	
</body>
</html>