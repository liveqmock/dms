<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包策略</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	if(action != "1"){
		$("#FM_SBCLDM").val("策略代码1");
		$("#FM_SBCLMC").val("策略名称1");
		$("#in-ksrq").val("2014-5-28");
		$("#in-jsrq").val("2014-10-1");
		$("#btnNext1").show();	
	}else{
		$("#cxxxH").hide();
		$("#cxxxC").hide();
		$("#sfxxH").hide();
		$("#sfxxC").hide();
	}
	$("#searchModel").click(function(){
		if($("#scclcxlb").is(":hidden")){
			$("#scclcxlb").show();
			$("#scclcxlb").jTable();
			$("#btnNext2").show();	
			$("#btnNext4").show();	
		}
	});
	
	$("#searchProvinces").click(function(){
		if($("#sbclsflb").is(":hidden")){
			$("#sbclsflb").show();
			$("#sbclsflb").jTable();
			$("#btnNext3").show();	
		}
	});
	
	var dialog = parent.$("body").data("sbclxx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	
	
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex"))-1);
	});
	 //下一步
	$("button[name='btn-next']").bind("click",function(event){
	var $tabs = $("#tabs");
	switch(parseInt($tabs.attr("currentIndex")))
 	{
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
    }
 	$tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
 	//跳转后实现方法
 	(function(ci){ 
		switch(parseInt(ci))
     	{
			case 1://第2个tab页
     	   		if(action == "1")
     	   		{
     	   		}else
     	   		{
     	   		}
    	   		break;
			case 2://第3个tab页
     	   		if(action == "1")
     	   		{
     	   		}else
     	   		{
     	   		}
    	   		break;
     	   	default:
     	   		break;
     	  }
 	   })(parseInt($tabs.attr("currentIndex")));
	});
});
function doSave(){
	var $f = $("#fm-sbclxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
	$("#cxxxH").show();
	$("#cxxxC").show();
	$("#sfxxH").show();
	$("#sfxxC").show();
	$("#btnNext1").show();	
}
function doDelete(){
	alertMsg.info("删除成功");
}
function doAddModel(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbclcxxz.jsp", "sbclcx", "三包策略车型新增", options);
}
function doAddProvinces(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbclsfxz.jsp", "sbclsf", "三包策略省份新增", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>三包策略信息</span></a></li>
					<li id="cxxxH"><a href="javascript:void(0)"><span>车型信息</span></a></li>
					<li id="sfxxH"><a href="javascript:void(0)"><span>省份信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-sbclxx" class="editForm" >
					<div align="left">
					<fieldset>
					<table class="editTable" id="sbclxx">
						<tr>	
							<td><label>三包策略代码：</label></td>
							<td colspan="5"><input type="text" id="FM_SBCLDM" name="FM_SBCLDM" datatype="0,is_null,100" /></td>
							<td><label>三包策略名称：</label></td>
							<td><input type="text" id="FM_SBCLMC" name="FM_SBCLMC" datatype="0,is_null,100"/></td>
						</tr>
						<tr>
						    <td><label>策略日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ksrq" style="width:75px;" name="in-ksrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-jsrq" datasource="CKRQ" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   		 </td>
					 	</tr>
						<tr>	
							<td><label>三包规则：</label></td>
							<td colspan="5"><select  type="text" id="FM_SBGZ" name="FM_SBGZ" kind="dic" class="combox" src="E#1=规则1:2=规则2"  datatype="0,is_null,100" value="" >
									<option value="1" selected>规则1</option>
								</select>
							</td>
							<td ><label>状态：</label></td>
							<td ><select  type="text" id="ZT" name="ZT" kind="dic" class="combox" src="YXBS"  datatype="0,is_null,100" value="" >
									<option value="1" >有效</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
					<li id="btnNext1" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</div>
		    <div class="page">
			<div class="pageHeader" id="cxxxC">
				<form id="fm-scclcx" method="post">
					<div class="searchBar" align="left">
						<table class="searchContent" id="fm-scclcxTable">
							<tr>
								<td><label>车型代码：</label></td>
								<td><input type="text" id="CXDM" name="CXDM" datatype="1,is_null,100"  value="" /></td>
								<td><label>车型名称：</label></td>
								<td><input type="text" id="CXMC" name="CXMC" datatype="1,is_null,100" value="" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchModel">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addModel" onclick="doAddModel()">新&nbsp;&nbsp;增</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="deleteModel" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="scclcx">
					<table width="100%" id="scclcxlb" name="scclcxlb" style="display: none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:"></th>
								<th>车型代码</th>
								<th>车型名称</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码1</td>
								<td>名称1</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码2</td>
								<td>名称2</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码3</td>
								<td>名称3</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码4</td>
								<td>名称4</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="btnNext4" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next4" name="btn-pre">上一步</button></div></div></li>
					<li id="btnNext2" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-next">下一步</button></div></div></li>
				</ul>
			</div>
			</div>
			<div class="page">
			<div class="pageHeader" id="sfxxC">
				<form id="fm-scclsf" method="post">
					<div class="searchBar" align="left">
						<table class="searchContent" id="fm-scclsfTable">
							<tr>
								<td><label>省份：</label></td>
								<td><input type="text" id="SF" name="SF" datatype="1,is_null,100"  value="" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchProvinces">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addProvinces" onclick="doAddProvinces()">新&nbsp;&nbsp;增</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="deleteProvinces" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="sbclsf">
					<table width="100%" id="sbclsflb" name="sbclsflb" style="display: none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:"></th>
								<th>身份</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>吉林</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>山西</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>陕西</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>上海</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="btnNext3" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next3" name="btn-pre">上一步</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>