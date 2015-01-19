<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>验收参考表</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关  &gt; 参考表</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
				    	<td><label>仓库名称：</label></td>
					    <td>
					    	<input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME"  
					    			kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME,WAREHOUSE_ID,WAREHOUSE_TYPE}:1=1 AND WAREHOUSE_STATUS = 100201 AND WAREHOUSE_TYPE IN(100101,100102,100105) ORDER BY WAREHOUSE_CODE" datatype="0,is_null,10000" />
					    	<input type="hidden" id="WAREHOUSE_ID" name="WAREHOUSE_ID" datasource="WAREHOUSE_ID" action="show" operation="=" datatype="1,is_null,500"/>
					    	<input type="hidden" id="WAREHOUSE_TYPE" name="WAREHOUSE_TYPE" datasource="WAREHOUSE_TYPE" action="show" operation="=" datatype="1,is_null,500"/>
					    </td>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,600" operation="like" dataSource="T.PART_CODE"  />
						</td>
						<td><label>配件名称：</label></td>
						<td>
							<input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,600" operation="like" dataSource="T.PART_NAME" />
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
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="UPPER_LIMIT">库存上限</th>
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
							<th fieldname="ORDER_COUNT">延期订单需求数量</th>
							<th fieldname="WILL_ACCEPT_COUNT" >可验收数量</th>
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
var mngUrl = "<%=request.getContextPath()%>/part/storageMng/search/PartAcceptReferenceMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/acceptSearch.ajax";
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
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/PartAcceptReferenceMngAction/download.do?WAREHOUSE_ID="+id+"&WAREHOUSE_TYPE="+type);
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

//表选回调函数：id,触发函数对象ID，$row 查询结果点击的行对象, selIndex，查询结果点击行号
function afterDicItemClick(id, $row, selIndex){
  var ret = true; 		// 返回值
  $("#WAREHOUSE_NAME").val($row.attr("WAREHOUSE_NAME")); // 设置选择字典的值
  $("#WAREHOUSE_ID").val($row.attr("WAREHOUSE_ID")); // 设置选择字典的值
  $("#WAREHOUSE_TYPE").val($row.attr("WAREHOUSE_TYPE")); // 设置选择字典的值
  return ret;
}
</script>