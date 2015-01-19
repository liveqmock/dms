<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>出库单调整查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关  &gt; 出库单调整查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道代码：</label></td>
					    <td>
					    	<input type="text" id="ORG_CODE" name="ORG_CODE" datasource="T1.ORG_CODE" datatype="1,is_null,300000" kind="dic" src="T#TM_ORG:CODE:ONAME{ORG_ID,CODE,ONAME}:1=1 AND ORG_TYPE = 200005 AND STATUS='100201' ORDER BY CODE" readonly="true" operation="in"/>
					    </td>
						<td><label>库管员：</label></td>
						<td>
							<input type="text" id="KEEP_MAN" name="KEEP_MAN" datatype="1,is_null,600" operation="like" dataSource="T.KEEP_MAN" />
						</td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,600" operation="like" dataSource="T2.PART_CODE"  />
						</td>
						<td><label>配件名称：</label></td>
						<td>
							<input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,600" operation="like" dataSource="T2.PART_NAME" />
						</td>
					</tr>
					<tr>
						<td><label>调整日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.MOD_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.MOD_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index" >导&nbsp;&nbsp;出</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重置查询条件</button></div></div></li>
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
							<th fieldname="ORDER_NO">订单编号</th>
							<th fieldname="ORG_CODE">渠道代码</th>
							<th fieldname="ORG_NAME">渠道名称</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="OLD_COUNT">原数量</th>
							<th fieldname="MOD_COUNT">调整后数量</th>
							<th fieldname="DEF_COUNT">差异数量</th>
							<th fieldname="MOD_DATE">调整时间</th>
							<th fieldname="MOD_MAN">调整人</th>
							<th fieldname="KEEP_MAN">库管员</th>
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
var mngUrl = "<%=request.getContextPath()%>/part/storageMng/search/OutBillModSearchMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/outBillModSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	
    	// 后台没有使用自动拼接方法，如果如果添加查询条件，请也在后台添加拼接SQL的逻辑
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	});
	$("#btn-export-index").click(function(){
        var $f = $("#fm-condition");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var id = $("#WAREHOUSE_ID").val();
        var type = $("#WAREHOUSE_TYPE").val();
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/OutBillModSearchMngAction/download.do?WAREHOUSE_ID="+id+"&WAREHOUSE_TYPE="+type);
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
	// 重置
	$("#btn-clear").click(function(){
		$("input","#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
});

function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "ORG_CODE")
	{
		$("#ORG_CODE").val($row.attr("CODE"));
		$("#ORG_CODE").attr("code",$row.attr("CODE"));
	}
	return ret;
}
</script>