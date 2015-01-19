<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>待维护配件合同校验</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关  &gt; 待维护配件合同校验</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<tr>
						<td><label>是否存在：</label></td>
                        <td>
                            <select type="text" id="ifIn"  name="ifIn" datasource="T.IF_IN" kind="dic" src="SF" datatype="1,is_null,30" operation="=">
                                <option value="" selected>--</option>
                            </select>
                        </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-download-index" >下&nbsp;载&nbsp;模&nbsp;板</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-import-index" >导&nbsp;&nbsp;入</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index" >导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="IF_IN">是否在合同内</th>
							<th fieldname="SUP_NAMES" >供应商名称</th>
							<th fieldname="UNIT_PRICES">供应商价格</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
      <input type="hidden" id="data" name="data"></input>
   </form>
</div>
</body>
</html>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/salesMng/search/ContractPartCheckMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		doSearch();
	});
	//下载模板
	$('#btn-download-index').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=partCheck.xls");
        window.location.href = url;
    });
	$('#btn-import-index').bind('click', function () {
        importXls("PT_BU_PART_CONT_CHK_TMP",true,3,3,"/jsp/dms/oem/part/sales/orderMng/searchInfo/partContractCheckimportSuccess.jsp");
    });
	$("#btn-export-index").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/search/ContractPartCheckMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});
function doSearch(){
	var searchUrl = mngUrl+"/partSearch.ajax";
	var $f = $("#fm-searchContract");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
}
</script>