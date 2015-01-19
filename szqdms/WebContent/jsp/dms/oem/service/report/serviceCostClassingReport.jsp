<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:服务站费用分梯统计表
	 Version:1.0
     Collator：baixiaoliangn@sxqc.com
     Date：2014-07
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<jsp:include page="/head.jsp" />
<title>服务站费用分梯统计表</title>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：/service/basicinfomng/RulePartMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * RulePartMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/ServiceCostClassingReportAction/search.ajax";
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		
		var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/reportForms/ServiceCostClassingReportAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});


/* //金额格式化
function amountFormat(obj){
	  alert("1111");
	var $obj = $(obj);
	var $tr = $obj.parent().parent().parent();
	  var m;
       m = $tr.find("td").eq(1).text();
     alert("1111"+m);
      if(m=='材料费（元）'||m=='总费用（元）'){
    	  return amountFormatNew($(obj).html());  
      }
      
	return $(obj).html();
} */
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;服务站费用分梯统计表</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">	
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				   <tr>
						<td>
				 		<td><label>结算月份：</label></td>
                        <td><input type="text" id="settle_date" name="settle_date" datasource="T.SETTLE_DATE" datatype="0,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" /></td>
							<td colspan="2" align="left" >
					        	<font >每个结算月生成上月费用
					            </font>
					        </td>
						</tr>
				    
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
				
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="NAME" >分类</th>
								<!-- <th fieldname="A1" refer="amountFormat" align="right" >大于0万小于等于0.5万</th> -->
								<th fieldname="A1"  >大于0万小于等于0.5万</th>
					
								<th fieldname="A2"  >大于0.5万小于等于1万</th>
								<th fieldname="A3"  >大于1万小于等于2万</th>
								<th fieldname="A4"  >大于2万小于等于4万</th>
								<th fieldname="A5"   >大于4万小于等于8万</th>
								<th fieldname="A6"  >大于8万小于等于16万</th> 
								<th fieldname="A7"  >大于16万小于等于32万</th> 
								<th fieldname="A8"  >大于32万小于等于64万</th> 
								<th fieldname="A9"  >大于64万</th> 
								<th fieldname="A10"  >合计</th> 
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>