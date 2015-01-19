<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务商星级评定维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/DealerStarMngAction/search.ajax";


//定义弹出窗口样式
var diaAddOptions = {max:false,width:750,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchDealerStar");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};

		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-dealerStarList");
	});
	
	//模版导出按钮响应
    $('#btn-expTemplet').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=dealerStarImp.xls");
        window.location.href = url;
    });

    //Excel导入按钮响应
    $('#btn-impExcel').bind('click', function () {
        //3:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页                               
        importXls("MAIN_DEALER_STAR_TMP","",3,3,"/jsp/dms/oem/service/basicinfomng/dealerstar/importSuccess.jsp");
    });
    
    //导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchDealerStar");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/basicinfomng/DealerStarMngAction/download.do");
		$("#exportFm").submit();
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/dealerstar/dealerStarEdit.jsp?action=2", "update", "修改服务商星级", diaAddOptions);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;服务商星级维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchDealerStar" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchDealerStar">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="dealerCode" name="dealerCode" datasource="T.DEALER_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="dealerName" name="dealerName" datasource="T.DEALER_NAME" operation="like" datatype="1,is_null,300" /></td>
						</tr>
						<tr>
							<td><label>所属办事处：</label></td>
							<td><input type="text" id="oName" name="oName" datasource="M.ONAME" operation="like" datatype="1,is_null,300" /></td>
							<td><label>星级：</label></td>
							<td><input type="text"  id="dealerStar" name="dealerStar" datasource="T.DEALER_STAR" operation="like" kind="dic" src="T#USER_PARA_CONFIGURE:PARAKEY:PARANAME:APPTYPE='2003'" datatype="1,is_null,30" /> </td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expTemplet" >模板导出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-impExcel" >Excel导入</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expExcel" >导出Excel</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_dealerStarList">
				<table style="display:none;width:100%;" id="tab-dealerStarList" name="tablist" ref="page_dealerStarList" refQuery="tab-searchDealerStar">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ONAME" >所属办事处</th>
							<th fieldname="DEALER_CODE" >服务商代码</th>
							<th fieldname="DEALER_NAME" >服务商名称</th>
							<th fieldname="PARANAME" >星级</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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