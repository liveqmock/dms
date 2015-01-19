<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔修改信息</title>
<script type="text/javascript">
var spdwxxx_dialog = parent.$("body").data("spdwxxx");
$(function() 
{
	//设置高度
	$("#dia-div-wxxxbj").height(document.documentElement.clientHeight-30);
	$("#gclibxk").show();
	$("button[name='btn-pre']").bind("click",function(event) 
		{
				$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
		});
	$("button[name='btn-next']").bind("click", function(event) 
		{
				var $tabs = $("#dia-tabs");
				switch (parseInt($tabs.attr("currentIndex"))) 
				{
					case 0:
						break;
					case 1:
						break;
				}
			$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
			//跳转后实现方法
			(function(ci) 
			{
				switch (parseInt(ci)) 
				{
					case 1://第2个tab页					
						break;
					case 2://第3个tab页
						break;
					default:
						break;
				}
			})
			(parseInt($tabs.attr("currentIndex")));
	 });
	$("button.close").click(function() 
	{
		parent.$.pdialog.closeCurrent();
		return false;
	});
	
	$("#jjlb").show();
	$("#jjlb").jTable(); //审核轨迹
});
function doUpdate(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/jjgl/jjgl/spdjjbj.jsp?action=2", "spdjjbj", "旧件信息编辑", options);
}
function addOldPart(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/jjgl/jjgl/spdjjbj.jsp?action=1", "spdjjbj", "旧件信息新增", options);
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>维修信息</span></a></li>
					<li><a href="javascript:void(0)"><span>旧件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page" id="dia-div-wxxxbj" style="overflow:auto;">
			<div class="pageContent">
				<form method="post" id="dia-fm-wxdawh" class="editForm" style="width: 99%;">
					<div align="left">
						<table class="editTable" id="dia-tab-wxxx">
							<tr>
								<td><label>索赔单号：</label></td>
								<td><input type="text" id="dia-spdh" name="dia-spdh" value="索赔单号1" readonly="readonly"/></td>
								<td><label>VIN：</label></td>
								<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="VIN1" readonly="readonly"/></td>
								<td><label>发动机号：</label></td>
								<td><input type="text" id="dia-in-engineno" name="dia-in-engineno" value="发动机号1" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>车辆型号：</label></td>
								<td><input type="text" id="dia-in-clxh" name="dia-in-clxh" value="车辆型号1" readonly="readonly" /></td>
								<td><label>合格证号：</label></td>
								<td><input type="text" id="dia-in-hgzh" name="dia-in-hgzh" value="合格正好1" readonly="readonly" /></td>
								<td><label>发动机型号：</label></td>
								<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="发动机型号1" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>用户类型：</label></td>
								<td><input type="text" id="YHLX" name="YHLX" value="民车" readonly="readonly"/></td>
								<td><label>车辆用途：</label></td>
								<td><input type="text" id="CLYT" name="CLYT" value="非公路用车" readonly="readonly"/></td>
								<td><label>驱动形式：</label></td>
								<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="8*4" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>备注：</label></td>
								<td colspan="5"><textarea id="dia-in-bxbz" style="width: 450px; height: 50px;" name="dia-in-bxbz" datatype="1,is_null,100"></textarea></td>
							</tr>
						</table>
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
			<div class="page">
				<div class="pageContent">  
					<div id="jj">
						<table width="100%" id="jjlb" name="jjlb" style="display: none" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:" append="plus|addOldPart">序号</th>
									<th>旧件代码</th>
									<th>旧件名称</th>
									<th>旧件类别</th>
									<th>旧件数量</th>
									<th>供应商代码</th>
									<th>供应商名称</th>
									<th colwidth="45" type="link" title="[编辑]" action="doUpdate">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type ="radio" name="ra"/></td>
									<td>旧件代码1</td>
									<td>旧件名称1</td>
									<td>类别1</td>
									<td>1</td>
									<td>供应商代码1</td>
									<td>供应商名称1</td>
									<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
								</tr>
								<tr>
									<td><input type ="radio" name="ra"/></td>
									<td>旧件代码2</td>
									<td>旧件名称2</td>
									<td>类别2</td>
									<td>3</td>
									<td>供应商代码2</td>
									<td>供应商名称2</td>
									<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
								</tr>
								<tr>
									<td><input type ="radio" name="ra"/></td>
									<td>旧件代码3</td>
									<td>旧件名称3</td>
									<td>类别3</td>
									<td>3</td>
									<td>供应商代码3</td>
									<td>供应商名称3</td>
									<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
				</ul>
			</div>
			</div>
			
		</div>	
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
</body>
</html>