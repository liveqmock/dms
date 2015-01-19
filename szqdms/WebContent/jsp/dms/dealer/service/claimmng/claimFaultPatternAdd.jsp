<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-dia-pattern-search">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-dia-pattern-search">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>故障代码：</label></td>
					    <td><input type="text" id="dia-in-cx-gzdm" name="dia-in-cx-gzdm" datasource="T1.FAULT_PATTERN_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>故障名称：</label></td>
					    <td><input type="text" id="dia-in-cx-gzmc" name="dia-in-cx-gzmc" datasource="T1.FAULT_PATTERN_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>配件图号：</label></td>
					    <td><input type="text" id="dia-in-cx-pjth" name="dia-in-cx-pjth" datasource="PART_CODE" datatype="1,is_digit_letter,30" operation="like" action="show" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-in-cx-pjmc" name="dia-in-cx-pjmc" datasource="PART_NAME" datatype="1,is_null,30" operation="like" action="show" /></td>
					</tr>
					<tr>
						<td><label>工时代码：</label></td>
					    <td><input type="text" id="dia-in-cx-gsdm" name="dia-in-cx-gsdm" datasource="T3.TIME_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>工时名称：</label></td>
					    <td><input type="text" id="dia-in-cx-gsmc" name="dia-in-cx-gsmc" datasource="T3.TIME_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-dia-pattern-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-dia-pattern-confirm" >确&nbsp;&nbsp;定</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dia-pattern" >
			<table style="display:none;width:100%;" id="tab-pattern-list" name="tablist" ref="page-dia-pattern" refQuery="fm-dia-pattern-search" >
					<thead>
						<tr>
							<th type="multi" name="XH" unique="RELATION_ID" ></th>
							<th fieldname="FAULT_PATTERN_CODE" colwidth="80">故障代码</th>
							<th fieldname="FAULT_PATTERN_NAME" colwidth="150" >故障名称</th>
							<th fieldname="POSITION_NAME" colwidth="100" >部位名称</th>
							<th fieldname="TIME_CODE" colwidth="120" >工时代码</th>
							<th fieldname="TIME_NAME" colwidth="150" >工时名称</th>
						    <th fieldname="AMOUNT_SET" colwidth="60">维修工时</th>
						</tr>
					</thead>
			</table>
			</div>
				<table style="display:none">
				<tr><td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td></tr>
				</table>
	     </div>
	</div>
<script type="text/javascript">
var diaClaimFalutPatternUrl="<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
var patternClaimId=$("#dia-claimId").val();
$(function(){
	$("#btn-dia-pattern-search").bind("click",function(event){
		var $f = $("#fm-dia-pattern-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	diaClaimFalutPatternSearchUrl=diaClaimFalutPatternUrl+"/searchPattern.ajax?cliamId="+patternClaimId+"&claimType="+$("#dia-in-splx").attr("code");
		doFormSubmit($f,diaClaimFalutPatternSearchUrl,"btn-dia-pattern-search",1,sCondition,"tab-pattern-list");
	});
	$("#btn-dia-pattern-confirm").bind("click",function(event){
		var relationId=$("#val0").val();
		if(relationId=="")
		{
			alertMsg.info("请选择故障信息。");
			return true;
		}else
		{
			var userType=$("#dia-user_typeId").val();
			//跳转到保存方法
			var diaClaimFalutPatternAddUrl =diaClaimFalutPatternUrl+"/addClaimFattern.ajax?cliamId="+patternClaimId+"&relationId="+relationId+"&claimType="+$("#dia-in-splx").attr("code")+"&userType="+userType;
			sendPost(diaClaimFalutPatternAddUrl,"","",diaAddClaimFaultBack,"false");
		}
	});
});
//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var mxid = $(checkbox).val();
	arr.push(mxid);
	multiSelected($checkbox,arr);
}
//添加完故障信息后回调方法
function diaAddClaimFaultBack(res)
{
	try
	{
		searchClaimPattern();
		$.pdialog.closeCurrent();
		
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
//打开配件信息
/* function showPartInfo(){
	// hasBtn="true" callFunction="showPartInfo()" 
	var url = webApps + "jsp/dms/oem/service/basicinfomng/partInfoSearchIndex.jsp";
	win.open(url);
} */
</script>
</div>

