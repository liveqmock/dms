<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运装箱</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	for(var i=2010;i<=2020; i++){
		if(i==2014){
			$("#JJCSN").append("<option value="+i+" selected>"+i+"</option>");
		}else{
			$("#JJCSN").append("<option value="+i+">"+i+"</option>");
		}
	}	
	for(var j=1;j<=12; j++){
		if(j==3){
			$("#JJCSY").append("<option value="+j+" selected>"+j+"</option>"); 
		}else{
			$("#JJCSY").append("<option value="+j+">"+j+"</option>"); 
		}
		
	}
	//初始化页面
	if(action !=1 ){
		$("#ZXZSL").val(1);
		$("#btnNext1").show();
// 	     $("#hyqdlb").show();
// 		$("#hyqdlb").jTable(); 
	}else{
		$("#hydhH").hide();
		$("#hydhC").hide();
		$("#hyqdH").hide();
		$("#hyqdC").hide();
		$("#submitB").hide();
	}
	$("#searchDetail").click(function(){
		if($("#hyqdlb").is(":hidden")){
			$("#hyqdlb").show();
			$("#hyqdlb").jTable();
		}
		$("#btnNext2").show();
	});
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("jjhyzxxz");
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
     	   	default:
     	   		break;
     	  }
 	   })(parseInt($tabs.attr("currentIndex")));
	});
});
function doSave(){
	var $f = $("#fm-jjxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
	$("#hyqdH").show();
	$("#hyqdC").show();
	$("#submitB").show();
	$("#btnNext1").show();
}
function doSubmit(){
	var $f = $("#fm-jjxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("提报成功！");
}
function doExp(){
	alertMsg.info("下载回运数据");
}
function doImp(){
	alertMsg.info("导入回运清单");
}
function doDelete(){
	alertMsg.info("删除成功！");
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
					<li ><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li id="hyqdH"><a href="javascript:void(0)"><span>回运清单导入</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-jjxx" class="editForm" >
					<div align="left">
					<fieldset>
					<table class="editTable" id="jjxx">
					    <tr>
							<td id="hydhH"><label>回运单号：</label></td>
							<td colspan="3" id="hydhC">12</td>
						</tr>
						<tr>	
							<td><label>旧件产生年：</label></td>
							<td><select  type="text" id="JJCSN" name="JJCSN" style="width:75px;" >
								</select>        
								<span style="float:left;margin-left:10px;margin-top:5px;">月</span>
								<select  type="text" id="JJCSY" name="JJCSY"  style="width:75px;margin-left:15px;" datatype="0,is_date,100">
								</select>        
							</td>
						</tr>
						<tr>	
							<td><label>运输方式：</label></td>
							<td><select  type="text" id="HYFS" name="HYFS" kind="dic" class="combox" src="E#1=配货:2=专车:3=铁路:4=空运"  datatype="1,is_null,100" value="" >
									<option value="1" selected>配货</option>
								</select>
							</td>
							<td ><label>装箱总数量：</label></td>
							<td><input type="text" id="ZXZSL" name="ZXZSL" datatype="0,is_digit,100"/></td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="3"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="submitB"><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSubmit()" id="sub">提&nbsp;&nbsp;报</button></div></div></li>
						<li id="btnNext1" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="tabsContent" id="hyqdC">
			<div class="page">
			<div class="pageHeader">
			<form id="fm-hyqddr" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="hyqddrTable">
						<tr>
							<td><label>箱号：</label></td>
							<td><input type="text" id="XH" name="XH" datatype="1,is_null,100"  value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchDetail">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">下载回运数据</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="imp" onclick="doImp()">导&nbsp;&nbsp;入</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="delete" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent">
				<div id="hyqd">
					<table width="100%" id="hyqdlb" name="hyqdlb" style="display:none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:"></th>
								<th>索赔单号</th>
								<th>车辆型号</th>
								<th>VIN</th>
								<th>行驶里程</th>
								<th>配件代码</th>
								<th>配件名称</th>
								<th align="right">配件金额(元)</th>
								<th>应返数量</th>
								<th>实返数量</th>
								<th>箱号</th>
								<th>生成供应商</th>
								<th>责任供应商</th>
								<th>责任供应商代码</th>
								<th>质损原因</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号1</td>
								<td>车型1</td>
								<td>VIN1</td>
								<td>2000</td>
								<td>配件代码1</td>
								<td>配件名称1</td>
								<td>100.00</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td>供应商1</td>
								<td>责任供应商1</td>
								<td>责任供应商代码1</td>
								<td>破裂</td>
								<td></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号2</td>
								<td>车型2</td>
								<td>VIN2</td>
								<td>2000</td>
								<td>配件代码2</td>
								<td>配件名称2</td>
								<td>100.00</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td>供应商2</td>
								<td>责任供应商2</td>
								<td>责任供应商代码2</td>
								<td>破裂</td>
								<td></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号3</td>
								<td>车型3</td>
								<td>VIN3</td>
								<td>2000</td>
								<td>配件代码3</td>
								<td>配件名称3</td>
								<td>100.00</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td>供应商3</td>
								<td>责任供应商3</td>
								<td>责任供应商代码3</td>
								<td>破裂</td>
								<td></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号4</td>
								<td>车型4</td>
								<td>VIN4</td>
								<td>2000</td>
								<td>配件代码4</td>
								<td>配件名称4</td>
								<td>100.00</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td>供应商4</td>
								<td>责任供应商4</td>
								<td>责任供应商代码4</td>
								<td>破裂</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="btnNext2" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-pre">上一步</button></div></div></li>
				</ul>
			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>