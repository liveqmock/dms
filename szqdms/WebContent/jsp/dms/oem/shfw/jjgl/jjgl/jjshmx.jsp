<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件终审</title>
<script type="text/javascript">
$(function(){
	$("#saveB").hide();
	$("#inStoreB").hide();
	$("#preB").hide();
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("jjshmx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	$("#searchDetail").click(function(){
		if($("#hyqdlb").is(":hidden")){
			$("#hyqdlb").show();
			$("#hyqdlb").jTable();
		}
		$("#saveB").show();
		$("#inStoreB").show();
		$("#preB").show();
	});
	
	//上一步
	$("button[name='pre-next']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex"))-1);
	});
	 //下一步
	$("button[name='btn-next']").bind("click",function(event){
	var $tabs = $("#tabs");
	switch(parseInt($tabs.attr("currentIndex")))
 	{
		case 0:
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
	alertMsg.info("保存成功！");
}
function doInStore(){
	alertMsg.info("入库完成！");
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
					<li ><a href="javascript:void(0)"><span>回运清单</span></a></li>
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
							<td><label>服务商代码：</label></td>
							<td>服务商代码1</td>
							<td><label>服务商名称：</label></td>
							<td>服务商名称1</td>
						</tr>
						<tr>	
							<td><label>回运单号：</label></td>
							<td>回运单号1</td>
							<td><label>运输方式：</label></td>
							<td>配货</td>
							<td ><label>装箱总数量：</label></td>
							<td>1</td>
						</tr>
						<tr>
							<td><label>服务商回运日期：</label></td>
							<td><input  type="text"  value="2014-5-20" readonly/></td>
							<td><label>配送中心回运日期：</label></td>
							<td><input  type="text" value="2014-5-25" readonly/></td>
							<td><label>旧件产生年：</label></td>
							<td><input  type="text" id="JJCSN" name="JJCSN" style="width:75px;" value="2014" readonly/>
								<span style="float:left;margin-left:-10px;margin-top:5px;">月</span>
								<input  type="text" id="JJCSY" name="JJCSY"  style="width:75px;margin-left:15px;"  value="5" readonly/>
							</td>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</div>
			<div class="page">
			<div class="pageHeader">
			<form id="fm-hyqddr" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="hyqddrTable">
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text"  value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchDetail">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent" >
				<div id="hyqd">
					<table width="100%" id="hyqdlb" ref="hyqd" name="hyqdlb" style="display:none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:">序号</th>
								<th>索赔单号</th>
								<th>配件代码</th>
								<th>配件名称</th>
								<th>应返数量</th>
								<th>实返数量</th>
								<th>已入库数量</th>
								<th>入库数量</th>
								<th>生成供应商</th>
								<th>责任供应商</th>
								<th>责任供应商代码</th>
								<th>质损原因</th>
								<th>问题描述</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号1</td>
								<td>配件代码1</td>
								<td>配件名称1</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td><input type="text" value="4"/></td>
								<td>供应商1</td>
								<td>责任供应商1</td>
								<td>责任供应商代码1</td>
								<td>破裂</td>
								<td><input type="text"/></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号2</td>
								<td>配件代码2</td>
								<td>配件名称2</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td><input type="text" value="4"/></td>
								<td>供应商2</td>
								<td>责任供应商2</td>
								<td>责任供应商代码2</td>
								<td>破裂</td>
								<td><input type="text"/></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号3</td>
								<td>配件代码3</td>
								<td>配件名称3</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td><input type="text" value="4"/></td>
								<td>供应商3</td>
								<td>责任供应商3</td>
								<td>责任供应商代码3</td>
								<td>破裂</td>
								<td><input type="text"/></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>索赔单号4</td>
								<td>配件代码4</td>
								<td>配件名称4</td>
								<td>5</td>
								<td>5</td>
								<td>1</td>
								<td><input type="text" value="4"/></td>
								<td>供应商4</td>
								<td>责任供应商4</td>
								<td>责任供应商代码4</td>
								<td>破裂</td>
								<td><input type="text"/></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="formBar">
					<ul>
						<li id="saveB"><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="inStoreB"><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doInStore()" id="sub">入&nbsp;&nbsp;库&nbsp;&nbsp;完&nbsp;&nbsp;成</button></div></div></li>
						<li id="preB"><div class="buttonActive"><div class="buttonContent"><button type="button" name="pre-next" id="pre-next">上&nbsp;&nbsp;一&nbsp;&nbsp;步</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>