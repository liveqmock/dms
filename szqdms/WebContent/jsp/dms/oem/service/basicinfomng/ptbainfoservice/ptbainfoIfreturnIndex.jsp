<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件是否回运设置</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
var diaAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction";
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/searchService.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/deleteService.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:850,height:410,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-pjlb");
	});
	//将旧件设置成需要回运
	$("#btn-return").bind("click", function(event){
		var mxids=$("#partIdVals").val();
	    if(mxids=="")
	    {
	    	alertMsg.warn("请选择配件。");
	    	return true;
	    }else{
               $('#partIds').val(mxids);
               var $f = $("#fm-partlInfo");
               //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
               if (submitForm($f) == false) return false;
               var sCondition = {};
               //将需要提交的内容拼接成json
               sCondition = $f.combined(1) || {};
               var url =diaAction+"/needReturn.ajax";
               doNormalSubmit($f, url, "btn-return", sCondition, diaInsertCallBack);
	    }
	});
	//将旧件设置成需要回运
	$("#btn-noReturn").bind("click", function(event){
		var mxids=$("#partIdVals").val();
	    if(mxids=="")
	    {
	    	alertMsg.warn("请选择配件。");
	    	return true;
	    }else{
              $('#partIds').val(mxids);
              var $f = $("#fm-partlInfo");
              //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
              if (submitForm($f) == false) return false;
              var sCondition = {};
              //将需要提交的内容拼接成json
              sCondition = $f.combined(1) || {};
              var url =diaAction+"/noReturn.ajax";
              doNormalSubmit($f, url, "btn-return", sCondition, diaInsertCallBack);
	    }
	});
});
function diaInsertCallBack(res)
{
	try
	{
		$("#btn-cx").click();
		$("#partIdVals").val("");
		$("#partIds").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
var $row;
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
//列表复选
function doCheckbox(checkbox)
{
	var $checkbox = $(checkbox);
	var arr = [];
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("tab-pjlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("PART_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#dia-partIdVals"));
	}
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件回运设置</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="in-pjdm" name="in-pjdm" datasource="PART_CODE" datatype="1,is_null,300" operation="like" /></td>
						<td><label>配件属性：</label></td>
					    <td><input type="text" id="in-pjsx" name="in-pjsx" kind="dic" src="PJSX" datasource="ATTRIBUTE" datatype="1,is_null,300" /></td>
						<td><label>配件类型：</label></td>
					    <td><input type="text" id="in-pjlx"  name="in-pjlx" kind="dic" src="PJLB" datasource="PART_TYPE"  datatype="1,is_null,300" /></td>
					</tr>
					<tr>
					    <td><label>是否回运：</label></td>
					    <td><select type="text" id="in-ifreturn"  name="in-ifreturn" datasource="IF_RETURN" kind="dic" src="SF" datatype="1,is_null,300" >
					    		<option value="-1" selected>--</option>
					    </select></td>
					    <td><label>服务状态：</label></td>
					    <td>
					    	<select type="text" id="in-pjzt"  name="in-pjzt" datasource="SE_STATUS" kind="dic" src="YXBS" datatype="1,is_null,300" >
					    		<option value="100201" selected>有效</option>
					    	</select>
					    </td>
					    <td><label>是否油品：</label></td>
					    <td>
					    	<select type="text" id="in-ifoil"  name="in-ifoil" datasource="IF_OIL" kind="dic" src="SF" datatype="1,is_null,300" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-return" >回&nbsp;&nbsp;运</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-noReturn" >不&nbsp;回&nbsp;运</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" multivals="dia-partIdVals" name="tablist" ref="page_yhlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="PART_ID"></th>								
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="PART_TYPE" >配件类型</th>
							<th fieldname="UNIT" >计量单位(服务)</th>
							<th fieldname="PART_STATUS" >配件状态</th>
							<th fieldname="SE_STATUS" >服务状态</th>
							<th fieldname="POSITION_NAME" >一级总成</th>
							<th fieldname="F_POSITION_NAME" >二级总成</th>
							<th fieldname="IF_OIL" >是否油品</th>
							<th fieldname="IF_RETURN" >是否回运</th>
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
	<div id="dia-partIdVals">
		<table style="width:100%;">
			<tr><td>
				<textarea id="partIdVals" name="multivals" style="width:80%;height:26px;display:none " column="1" ></textarea>
			</td></tr>
		</table>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
	<form id="fm-partlInfo">
        <input type="hidden" id="partIds" name="partIds" datasource="PART_ID"/>
    </form>
</div>
</body>
</html>