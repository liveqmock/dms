<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运装箱</title>
<!-- 导入需要饮用的JS -->
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/otherPlugins.js" type="text/javascript"></script>
<script type="text/javascript">
//查询Action
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPacPJGLkAction/searchOnBox.ajax";

//异步保存boxNo
var saveBoxNoUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPacPJGLkAction/saveAsynBoxNo.ajax";

var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function(){
	
	// 查询
	$("#search").click(function(){
		var $f = $("#oldPartform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
	
	// 下载
	$("#exp").bind("click",function(){
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartPacPJGLkAction/download.do");
		$("#exportFm").submit();
	});
	
	// 导入
	$("#imp").bind("click",function(){
		//9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("PT_BU_CLAIM_APPLY_TMP",'', 7, 3,"/jsp/dms/dealer/service/oldpart/importPjOldPartPackSuccess.jsp");
	});

});

// 声明临时变量，保存用户点击修改时正确的箱号
var boxNoTmp = "";

/*
 * 编辑当前行的BoxNo
 */
function doEdit(rowobj){
	try{
		var $BOX_NO = $($(rowobj).children()[4]);
		boxNoTmp = $BOX_NO.text();
		$BOX_NO.html("<input type='text'  datatype='0,is_digit,30'  value='"+boxNoTmp+"' onblur='checkInputBoxNo(this)' onkeyup='enterFun(this)'/>");
		$BOX_NO.find("input").focusEnd();
	}catch(e){
		alertMsg.error(e);
	}
}

// boxNoInput控件：当用户按下回车时相应的函数
// 此方法只兼容IE
function enterFun(obj){
	
	// 当用户按下了回车键（keycode = 13）时触发blur相同的函数
	// 当用户按下ESC键（keycode = 27）时取消
	if(event.keyCode == 13){
		checkInputBoxNo(obj);
	}else if(event.keyCode == 27){
		$(obj).parent().html(boxNoTmp);
	}
}

// boxNoInput控件onblur事件校验
function checkInputBoxNo(obj){
	try{
		var valide = is_null($(obj));
		if(valide === true && $(obj).val().length > 15){
			valide = "数据过长，请重新输入";
		}
		if(valide === true ){
			saveAsynBoxNo(obj);
		}else{
			$(obj).parent().html(boxNoTmp);
			alertMsg.error(valide);
			return;
		}
	}catch(e){
		$(obj).parent().html(boxNoTmp);
		alertMsg.error(e);
		return
	}
}

// 异步保存箱号
function saveAsynBoxNo(obj){
	try{
		var applyId = $($(obj).parent().parent().children()[2]).text();
		var boxNo = $(obj).val();
		var url = saveBoxNoUrl + "?applyId=" + applyId + "&boxNo=" + boxNo;
		sendPost(url,null,null,function(message){
			$(obj).parent().html($(obj).val());
			return true;
		},null,null);
	}catch(e){
		$(obj).parent().html(boxNoTmp);
		alertMsg.error(e);
		return
	}
}

/*
 * 导入成功后的回调方法
 */
function impCall(){
	$("#search").click();
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;配件管理&gt;旧件回运装箱</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartTable">
						<tr>
							<td><label>箱号：</label></td>
							<td><input type="text" id="BOX_NO" name="BOX_NO" datasource="BOX_NO" datatype="1,is_null,300" operation="like" value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE" datatype="1,is_null,300" operation="like"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" datatype="1,is_null,300"  operation="like" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp">下载导入模板</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="imp">批量调整</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldPart">
				<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldPart" refQuery="oldPartTable"  >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="APPLY_ID" style="display: none"></th>
							<th fieldname="APPLY_NO"  ordertype='local' class="desc" colwidth="130px">申请单号</th>
							<th fieldname="BOX_NO"  ordertype='local' class="desc">箱号</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>	
							<th fieldname="CLAIM_COUNT" align="right" colwidth="60px">索赔数量</th>
							<th fieldname="ORG_CODE" colwidth="80px">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="FAULT_CONDITONS">故障情况</th>
							<th colwidth="45" type="link"  title="[修改]" action="doEdit">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
	 <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</body>
</html>