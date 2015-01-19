<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:运费里程数维护
	 Version:1.0
     Author：suoxiuli 2014-08-21
     Remark：transMiles
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>运费里程数维护</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/TransMilesAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/TransMilesAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:750,height:230,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchTransMiles");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-transMilesList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/logisticsMng/transMilesAdd.jsp?action=1", "addtransMiles", "新增运费里程数", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/storage/logisticsMng/transMilesAdd.jsp?action=2", "updatetransMiles", "修改运费里程数", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?milesId="+$(rowobj).attr("MILES_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	
	<div id="progressBar1" class="progressBar">loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 仓储管理   &gt; 物流信息管理   &gt; 运费里程数维护</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchTransMiles">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchTransMiles">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>省　　份：</label></td>
					    <td>
					    	<input type="text" id="provinceCode" name="provinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" 
				    			dicwidth="320" datasource="PROVINCE_CODE" datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>城　　市：</label></td>
					    <td>
				    	 	<input type="text" id="cityCode"  name="cityCode" kind="dic" dicwidth="320" datasource="CITY_CODE" 
				    	 		datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>区　　县：</label></td>
					    <td>
					    	<input type="text" id="countryCode" name="countryCode" kind="dic" dicwidth="320" datasource="COUNTRY_CODE" 
					    		datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					<tr>
						<td><label>运输里程：</label></td>
					    <td>
					    	<input type="text" id="transMiles1" name="transMiles1" datasource="TRANS_MILES" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="transMiles2" name="transMiles2" datasource="TRANS_MILES" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
					    </td>
					    <td><label>运输单价：</label></td>
					    <td >
					    	<input type="text" id="unitPrice1" name="unitPrice1" datasource="UNIT_PRICE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="unitPrice2" name="unitPrice2" datasource="UNIT_PRICE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
					    </td>
					    <td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
					    		<option value="100201" selected>有效</option>
					    	</select>
					    </td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_transMilesList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-transMilesList" name="tablist" ref="page_transMilesList" refQuery="tab-searchTransMiles" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="BIRTHLAND_NAME" >出发地点</th>
							<th fieldname="PROVINCE_NAME" >省份</th>
							<th fieldname="CITY_NAME" >城市</th>
							<th fieldname="COUNTRY_NAME" >区县</th>
							<th fieldname="TRANS_MILES" >运输里程</th>
							<th fieldname="UNIT_PRICE" >运输单价</th>
							<th fieldname="STATUS" >有效标识</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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

<script type="text/javascript">

function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="provinceCode"){
		//$("#provinceName").val($("#provinceCode").val());
		$("#cityCode").attr("src","XZQH");
		$("#cityCode").attr("isreload","true");
		$("#cityCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
		return true;
	}
	
	if(id=="cityCode"){
		//$("#cityName").val($("#dia-cityCode").val());
		$("#countryCode").attr("src","XZQH");
		$("#countryCode").attr("isreload","true");
		$("#countryCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
		return true;
	}

	if(id=="countryCode"){
		$("#countryName").val($("#countryCode").val());
		return true;
	}
	
	if(id=="dia-provinceCode"){
		$("#dia-provinceName").val($("#dia-provinceCode").val());
		$("#dia-cityCode").attr("src","XZQH");
		$("#dia-cityCode").attr("isreload","true");
		$("#dia-cityCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
		return true;
	}
	
	if(id=="dia-cityCode"){
		$("#dia-cityName").val($("#dia-cityCode").val());
		$("#dia-countryCode").attr("src","XZQH");
		$("#dia-countryCode").attr("isreload","true");
		$("#dia-countryCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
		return true;
	}
	
	if(id=="dia-countryCode"){
		$("#dia-countryName").val($("#dia-countryCode").val());
		return true;
	}
	
	return true;
}

</script>

</html>