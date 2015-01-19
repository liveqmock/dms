<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>延保策略与配件关系</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyPartMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyPartMngAction/delete.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:800,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchExtendWarrantyPart");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-extendWarrantyPartList");
	});
	
	//新增按钮响应
	$("#btn-batchAdd").bind("click", function(event){
	
		//先清空val0的内容
		$("#val0").val("");
		
		$("#tab-extendWarrantyPartList_content input[type=checkbox]").each(function(){
 			$(this).attr("checked",false);
  		});
	
		var diaPartOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/service/serviceparamng/extendwarrantypart/extendwarrantypartBatchAdd.jsp?action=1", "add", "新增延保策略与配件关系", diaPartOptions);
	});
	
	//批量删除按钮响应
	$("#btn-batchDelete").bind("click", function(event){
		
		var relationIds = $("#val0").val();
		if (relationIds) 
		{
			var deleteUrlBatch = deleteUrl + "?relationIds="+relationIds;
			sendPost(deleteUrlBatch,"btn-batchDelete","",batchDeleteCallBack,"true");                                                           
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	}); 
});

//列表编辑连接
function doUpdate(rowobj)
{
	//先清空val0的内容
	$("#val0").val("");
	$("#tab-extendWarrantyPartList_content input[type=checkbox]").each(function(){
 		$(this).attr("checked",false);
  	});
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	$("#tab-extendWarrantyPartList_content input[type=checkbox]").each(function(){
 		$("#val0").val($(this).val());
  	});

	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceparamng/extendwarrantypart/extendwarrantypartUpdate.jsp?action=2", "update", "修改延保策略与配件关系", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$("#val0").val("");
	$("#tab-extendWarrantyPartList_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	});
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);

	$row = $(rowobj);
	$("#val0").val($(rowobj).attr("RELATION_ID"));
	var deleteUrlOne = deleteUrl + "?relationIds="+$(rowobj).attr("RELATION_ID");
	sendPost(deleteUrlOne,"delete","",batchDeleteCallBack,"true");
}

//批量删除回调函数
function batchDeleteCallBack(res)
{
	try
	{   
	 	$("#btn-search").trigger("click");
	 	
	 	//清空val0的内容
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//延保月份输入框
function createInputSt(obj)
{
    return '<input type="text" name="WARRANTY_MONTH" width="10px" onblur="doInputStBlur(this)"  value='+obj.text()+' ></input>';
}

//input框延保月份焦点移开事件 步骤一
function doInputStBlur(obj){
	var $obj = $(obj);
	var $tr = $obj.parent().parent().parent();
    if($obj.val() == "")//为空直接返回
        return ;	
    if($obj.val() && !isNum($obj))//
    {
        alertMsg.warn("请输入正确的数量！");
        $obj.val("");
        return;
    }
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var s = $obj.val();
    if(s)
    {
        checkObj.attr("checked", true);
    }
    doSelectedBefore($tr,checkObj,1);
}

/**
 * $tr:当前行对象jquery 入库数量 步骤二
 * @param $obj:checkbox的jQuery对象
 * @param type:
 */
function doSelectedBefore($tr,$obj,type)
{
    var $input = $tr.find("td").eq(4).find("input:first");
    var s = "";
    if($input && $input.get(0).tagName=="INPUT")
        s = $input.val();
    else
    {
        s = $tr.find("td").eq(4).text();
    }
    doCheckbox($obj.get(0));
}

//列表复选,步骤三
function doCheckbox(checkbox) {
	var $t=$(checkbox);
	while($t[0].tagName != "TABLE")
    {
		$t = $t.parent();
    }
	if($t.attr("id").indexOf("tab-searchPartList")==0){
		var $tr = $(checkbox).parent().parent().parent();
	    var $input = $tr.find("td").eq(4).find("input:first");
	    var sl = "";
	    if($input && $input.size()>0 && $input.get(0).tagName=="INPUT"){
	    	sl = $input.val();    
	    }
	    else
	    {
	        sl = $tr.find("td").eq(4).text();
	    }
	  	//判断延保月份是否有值，如没有值，提示输入
	    var arr = [];
	    arr.push($tr.attr("PART_ID"));
	    arr.push(sl);
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    multiSelected($checkbox, arr,$("#di_hidInfo"));
	    if($checkbox.is(":checked"))
	  	{
	  		if(!sl)
	  		{
	  			alertMsg.warn("请输入延期月份！");
	  			$(checkbox).attr("checked",false);
	  			return false;
	  		}
	  	}
	    //设置input框显示或文本只读
	    if($checkbox.is(":checked")){
	    	$tr.find("td").eq(4).html("<div>"+sl+"</div>");
	    }else
	    {
	    	$tr.find("td").eq(4).html("<div><input type='text' name='WARRANTY_MONTH' onblur='doInputStBlur(this);' maxlength='6' value='"+sl+"'/></div>");
	    }
	}
   
   if($t.attr("id").indexOf("tab-extendWarrantyPartList")==0){
	   	var arr = [];
		var $checkbox = $(checkbox);
		var mxid = $(checkbox).val();
		arr.push(mxid);
		multiSelected($checkbox,arr,$("#extendHide"));
   }
}

/*
 * 翻页回显方法:步骤四
 */
function customOtherValue($row,checkVal)
{
    var $inputObj = $row.find("td").eq(4);
    var val="";
    if($("#val1").val())
    {
        var pks =$("#val2").val();
        var pk = pks.split(",");
        var re=$("#val1").val();
        var res = re.split(",");
        for(var i=0;i<pk.length;i++)
        {
            if(pk[i] == checkVal)
            {
                val = res[i];
	            break;
            }
        }
    }
    if(val)
    {
        $inputObj.html("<div>"+val+"</div>");
    }
}
function isNum($obj)
{
	var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;延保策略与配件关系</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchExtendWarrantyPart" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchExtendWarrantyPart">
						<tr>
							<td><label>延保代码：</label></td>
							<td><input type="text" id="warrantyCode" name="warrantyCode" datasource="WARRANTY_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>延保名称：</label></td>
							<td><input type="text" id="warrantyName" name="warrantyName" datasource="WARRANTY_NAME" operation="like" datatype="1,is_null,30" /></td>
							<td><label>延保月份：</label></td>
							<td><input type="text" id="warrantyMonth" name="warrantyMonth" datasource="WARRANTY_MONTH" operation="like" datatype="1,is_null,30" /></td>
						</tr>
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="PART_CODE" operation="like" datatype="1,is_null,300" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="PART_NAME" operation="like" datatype="1,is_null,400" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchAdd">新&nbsp;&nbsp;增</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchDelete" >批量删除</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-excel" >Excel</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_extendWarrantyPartList">
				<table style="display:none;width:100%;" id="tab-extendWarrantyPartList" name="tablist" multivals="extendHide" ref="page_extendWarrantyPartList" refQuery="tab-searchExtendWarrantyPart">					
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="RELATION_ID" ></th>
							<th fieldname="WARRANTY_CODE" >延保代码</th>
							<th fieldname="WARRANTY_NAME">延保名称</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="WARRANTY_MONTH">延保月份</th>
							<th fieldname="CREATE_USER">创建人</th>
							<th fieldname="CREATE_TIME">创建时间</th>
							<th fieldname="UPDATE_USER">修改人</th>
							<th fieldname="UPDATE_TIME">修改时间</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>	
				</table>
			</div>
			<table style="display:none" id="extendHide">
				<tr><td>
						<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>