<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>合同维护</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 采购相关   &gt; 采购合同查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>合同编号：</label></td>
					    <td><input type="text" id="CONTRACT_NO" name="CONTRACT_NO" datatype="1,is_null,60" dataSource="CONTRACT_NO" operation="like" /></td>
				    	<td><label>厂家名称：</label></td>
					    <td>
					    	<input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME"  
					    			kind="dic" src="T#PT_BU_PCH_CONTRACT:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_CODE,SUPPLIER_NAME}:1=1 GROUP BY SUPPLIER_NAME,SUPPLIER_CODE" datatype="1,is_null,10000" />
					    	<input type="hidden" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" operation="=" datatype="1,is_null,500"/>
					    </td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td>
							<input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,600" operation="like" dataSource="PART_CODE"  />
						</td>
						<td><label>配件名称：</label></td>
						<td>
							<input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,600" operation="like" dataSource="PART_NAME" />
						</td>
					</tr>
					<tr>
					    <td><label>采购合同状态：</label></td>
					    <td>
					    	<select class="combox" id="CONTRACT_STATUS" name="CONTRACT_STATUS" dataSource="T.CONTRACT_STATUS" 
					    			kind="dic" src="<%=DicConstant.CGHTZT %>" filtercode="<%=DicConstant.CGHTZT_02%>|<%=DicConstant.ZJZHLX_03%>|<%=DicConstant.CGHTZT_04%>|<%=DicConstant.CGHTZT_05%>|<%=DicConstant.CGHTZT_06%>|<%=DicConstant.CGHTZT_07%>|<%=DicConstant.CGHTZT_08%>|<%=DicConstant.CGHTZT_09%>|<%=DicConstant.CGHTZT_10%>"  
					    			datatype="1,is_null,6">
	                            <option value="-1" selected>--</option>
	                        </select>
					    </td>
					    <td><label>创建日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="CREATE_TIME_B" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="CREATE_TIME_E" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
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
							<th fieldname="SUPPLIER_NAME">厂家名称</th>
							<th fieldname="SUPPLIER_CODE" >厂家编码</th>
							<th fieldname="CONTRACT_NO" refer="showLink">合同编号</th>
							<th fieldname="CONTRACT_STATUS" colwidth="80px">采购合同状态</th>
							<th fieldname="CREATE_TIME" colwidth="80">创建日期</th>
							<th fieldname="SIGN_DATE" colwidth="80">签订日期</th>
							<th fieldname="EFFECTIVE_CYCLE" colwidth="140" refer="toAppend">有效期</th>
							<th fieldname="SUPPLIER_STATUS" style="display:none">供应商状态</th>
							<th fieldname="CREATE_USER" >签订人</th>
							<th colwidth="105" type="link" title="[附件信息]"  action="showDtl" >操作</th>
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
</html>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/ContractInfoSearchManageAciton";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/contractSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	
    	// 后台没有使用自动拼接方法，如果如果添加查询条件，请也在后台添加拼接SQL的逻辑
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
	}).click();
	
	// 重置
	$("#btn-clear").click(function(){
		$("input","#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
});
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("CONTRACT_ID")+") class='op'>"+$row.attr("CONTRACT_NO")+"</a>";
}
function openDetail(CONTRACT_ID){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseContract/contractInfo.jsp?CONTRACT_ID="+CONTRACT_ID, "contractDetail", "合同明细", options,true);
}
function toAppend(obj){
	 var $tr = $(obj).parent();
	 return $tr.attr("EFFECTIVE_CYCLE_BEGIN_sv")+"~"+ $tr.attr("EFFECTIVE_CYCLE_END_sv");
}
function showDtl(obj)
{
	var $row = $(obj);
	var contractId = $(obj).attr("CONTRACT_ID");
	var dtlOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseContract/contractDetailInfo.jsp?CONTRACT_ID="+contractId, "查看附件", "查看附件", dtlOptions);
}

//表选回调函数：id,触发函数对象ID，$row 查询结果点击的行对象, selIndex，查询结果点击行号
function afterDicItemClick(id, $row, selIndex){
  var ret = true; 		// 返回值
  $("#SUPPLIER_NAME").val($row.attr("SUPPLIER_NAME")); // 设置选择字典的值
  $("#SUPPLIER_CODE").val($row.attr("SUPPLIER_CODE")); // 设置选择字典的值
  return ret;
}
</script>