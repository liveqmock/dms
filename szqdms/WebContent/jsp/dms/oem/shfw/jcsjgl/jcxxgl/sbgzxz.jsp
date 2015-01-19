<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包规则</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	if(action != "1"){
		$("#FM_SBGZDM").val("规则代码1");
		$("#FM_SBGZDM").attr("readonly",true);
		$("#FM_SBGZMC").val("规则名称1");
		$("#btnNext1").show();
	}else{
		$("#gzxxP").hide();
		$("#gzxxC").hide();
	}
	$("#searchPart").click(function(){
		if($("#sbgzpjlb").is(":hidden")){
			$("#sbgzpjlb").show();
			$("#sbgzpjlb").jTable();
			$("#btnNext2").show();
		}
	});
	
	var dialog = parent.$("body").data("sbgzxx");
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
	var $f = $("#fm-sbgzxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功！");
	$("#gzxxP").show();
	$("#gzxxC").show();
	$("#btnNext1").show();
}
function doDelete(){
	alertMsg.info("删除成功");
}
function doAddPart(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbgzpjxz.jsp", "sbgzpj", "三包规则配件新增", options);
}
function doUpdate(){
	alertMsg.info("保存成功！");
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
					<li><a href="javascript:void(0)"><span>三包规则信息</span></a></li>
					<li id="gzxxP"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-sbgzxx" class="editForm" >
					<div align="left">
					<fieldset>
					<table class="editTable" id="sbgzxx">
						<tr>	
							<td><label>三包规则代码：</label></td>
							<td colspan="5"><input type="text" id="FM_SBGZDM" name="FM_SBGZDM" datatype="0,is_null,100" /></td>
							<td><label>三包规则名称：</label></td>
							<td><input type="text" id="FM_SBGZMC" name="FM_SBGZMC" datatype="0,is_null,100"/></td>
						</tr>
						<tr>	
							<td><label>规则类型：</label></td>
							<td colspan="5"><select  type="text" id="FM_GZLX" name="FM_GZLX" kind="dic" class="combox" src="E#1=公路用车:2=非公路用车"  datatype="0,is_null,100" value="" >
									<option value="1" selected>公路用车</option>
								</select>
							</td>
							<td ><label>状态：</label></td>
							<td ><select  type="text" id="ZT" name="ZT" kind="dic" class="combox" src="YXBS"  datatype="0,is_null,100" value="" >
									<option value="1" >有效</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>用户类别：</label></td>
							<td><select type="text" id="FM_YHLB" name="FM_YHLB" class="combox" kind="dic" src="E#1=军车:2=民车" datatype="0,is_null,100">
									<option value=1>军车</option>
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
			<div class="pageHeader" id="gzxxC">
				<form id="fm-scgzpj" method="post">
					<div class="searchBar" align="left">
						<table class="searchContent" id="fm-scgzpjTable">
							<tr>
								<td><label>配件代码：</label></td>
								<td><input type="text" id="PJDM" name="PJDM" datatype="1,is_null,100"  value="" /></td>
								<td><label>配件名称：</label></td>
								<td><input type="text" id="PJMC" name="PJMC" datatype="1,is_null,100" value="" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchPart">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addPart" onclick="doAddPart()">新&nbsp;&nbsp;增</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="deletePart" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="sbgzpj">
					<table width="100%" id="sbgzpjlb" name="sbgzpjlb" edit="true" style="display: none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:"></th>
								<th>配件代码</th>
								<th>配件名称</th>
								<th fieldname="SBYF"  ftype="input" freadonly="false">三包月份</th>
								<th fieldname="SBLC" ftype="input" freadonly="false">三包里程</th>
								<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码1</td>
								<td>名称1</td>
								<td><div>24</div></td>
								<td><div>25000</div></td>
								<td><a href="#" onclick="doUpdate()" class="op">[保存]</a></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码2</td>
								<td>名称2</td>
								<td><div>24</div></td>
								<td><div>25000</div></td>
								<td><a href="#" onclick="doUpdate()" class="op">[保存]</a></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码3</td>
								<td>名称3</td>
								<td><div>24</div></td>
								<td><div>25000</div></td>
								<td><a href="#" onclick="doUpdate()" class="op">[保存]</a></td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码4</td>
								<td>名称4</td>
								<td><div>24</div></td>
								<td><div>25000</div></td>
								<td><a href="#" onclick="doUpdate()" class="op">[保存]</a></td>
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