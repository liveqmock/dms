<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:发运地址管理
	 Version:1.0
     Author：suoxiuli 2014-07-11
     Remark：transport address
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发运地址管理</title>

<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/TransAddressAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/TransAddressAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:730,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchTransAddress");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-transAddressList");
	});
	
	//模版导出按钮响应
    $('#btn-expTpl').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=transAddressImp.xls");
        window.location.href = url;
    });

    //导入按钮响应
    $('#btn-import-index').bind('click', function () {
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页  
        //""传的参数                             
        importXls("PT_BA_TRANSPORT_ADDRESS_TMP","",12,3,"/jsp/dms/oem/part/basicInfo/transAddress/importSuccess.jsp");
    });

    //导出按钮响应
    $("#btn-export-index").click(function(){
        var url = encodeURI("<%=request.getContextPath()%>/part/basicInfoMng/TransAddressAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
        
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/transAddress/transAddressAdd.jsp?action=1", "addTransAddress", "新增发运地址", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/transAddress/transAddressAdd.jsp?action=2", "updateTransAddress", "修改发运地址", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?adressId="+$(rowobj).attr("ADDRESS_ID")+"&adress="+$(rowobj).attr("ADDRESS");
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
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 发运地址管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchTransAddress">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchTransAddress">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道商代码：</label></td>
					    <td>
					    	<input type="text" id="orgCode"  name="orgCode" datasource="A.ORG_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>渠道商名称：</label></td>
					    <td>
					    	<input type="text" id="orgName" name="orgName" datasource="A.ORG_NAME" datatype="1,is_null,100" operation="like" />
					    </td>
					    <td><label>渠道商类型：</label></td>
					    <td>
					    	<select id="orgType" name="orgType" datasource="O.ORG_TYPE" datatype="1,is_null,30" operation="=" >
					    		<option value="-1" selected>-------</option>
					    		<option value="200005" >配送中心</option>
					    		<option value="200006" >配件经销商</option>
					    		<option value="200007" >服务商</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>省　　　份：</label></td>
					    <td>
					    	<input type="text" id="provinceCode" name="provinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" 
				    			dicwidth="320" datasource="A.PROVINCE_CODE" datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>城　　　市：</label></td>
					    <td>
				    	 	<input type="text" id="cityCode"  name="cityCode" kind="dic" dicwidth="320" datasource="A.CITY_CODE" 
				    	 		datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>区　　　县：</label></td>
					    <td>
					    	<input type="text" id="countryCode" name="countryCode" kind="dic" dicwidth="320" datasource="A.COUNTRY_CODE" 
					    		datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					<tr>
						<td><label>联　系　人：</label></td>
					    <td>
					    	<input type="text" id="linkMan"  name="linkMan" datasource="A.LINK_MAN"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>联 系 电 话：</label></td>
					    <td>
					    	<input type="text" id="phone"  name="phone" datasource="A.PHONE"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>有 效 标 识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="A.STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
					    	</select>
					    </td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expTpl">模板导出</button></div></div></li>
                        	<li><div class="button"><div class="buttonContent"><button type="button" id="btn-import-index">导&nbsp;&nbsp;入</button></div></div></li>
                        	<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_transAddressList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-transAddressList" name="tablist" ref="page_transAddressList" refQuery="tab-searchTransAddress" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ORG_NAME" >渠道商名称</th>
							<th fieldname="PROVINCE_NAME" >省份</th>
							<th fieldname="CITY_NAME" >城市</th>
							<th fieldname="COUNTRY_NAME" >区县</th>
							<th fieldname="LINK_MAN" >联系人</th>
							<th fieldname="PHONE" >联系电话</th>
							<th fieldname="ADDR_TYPE" >地址类型</th>
							<th fieldname="ZIP_CODE" >邮编</th>
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
	
	if(id.indexOf("dia-orgCode") == 0)
	{  
		$("#dia-orgName").val($("#"+id).val());
		
		if($row.attr("ORG_ID")){
			$("#dia-orgId").val($row.attr("ORG_ID"));
		}
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