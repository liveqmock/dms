<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单修改</title>
<script type="text/javascript">
//初始化方法
$(function(){
	//查询方法 查询索赔单通过的、返旧件的、没结算的
	$("#btn-cx").bind("click",function(event){
		if($("#tab-spdlb").is(":hidden"))
		{
			$("#tab-spdlb").show();
			$("#tab-spdlb").jTable();
		}
	});
});
//列表编辑链接(修改)
function doUpdate(row)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/jjgl/jjgl/spdxgmx.jsp", "spdxxwh", "索赔单信息维护", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 旧件管理  &gt; 旧件管理   &gt; 索赔单修改</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-sbdcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-sbdcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						<td><label>回运单号：</label></td>
					    <td><input type="text" id="in-hydh" name="in-hydh"  /></td>
					</tr>	
					<tr>
						<td><label>索赔单号：</label></td>
					    <td><input type="text" id="in-spdh" name="in-spdh" /></td>
						<td><label>索赔类型：</label></td>
					    <td><select type="text" id="in-spdlx" name="in-spdlx" class="combox" kind="dic" src="E#1=正常保修:2=首保:3=服务活动:4=售前维修:5=三包急件索赔" >
					    		<option value=-1>--</option>
					    	</select>
					    </td>
				    	<td><label>提报日期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="REPORT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="REPORT_DATE" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_spdlb" >
			<table style="display:none;width:100%;" id="tab-spdlb" name="tablist" ref="page_spdlb" refQuery="fm-sbdcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th >服务商代码</th>
							<th >服务商名称</th>
							<th >回运单号</th>
							<th fieldname="CLAIM_NO" ordertype='local' class="desc">索赔单号</th>
							<th>索赔类型</th>
							<th fieldname="APPLY_TIME" ordertype='local' class="desc">提报时间</th>
							<th colwidth="105" type="link" title="[编辑]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>服务商代码1</div></td>
							<td><div>服务商名称1</div></td>
							<td><div>回运单号1</div></td>
							<td><div>索赔单号01</div></td>
							<td><div>正常保修</div></td>
							<td><div>2014-05-20 15:30 </div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>服务商代码2</div></td>
							<td><div>服务商名称2</div></td>
							<td><div>回运单号2</div></td>
							<td><div>索赔单号02</div></td>
							<td><div>正常保修</div></td>
							<td><div>2014-05-20 12:10 </div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>服务商代码3</div></td>
							<td><div>服务商名称3</div></td>
							<td><div>回运单号3</div></td>
							<td><div>索赔单号03</div></td>
							<td><div>正常保修</div></td>
							<td><div>2014-05-20 12:30 </div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>