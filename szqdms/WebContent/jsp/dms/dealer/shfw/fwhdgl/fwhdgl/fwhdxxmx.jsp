<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动信息明细</title>
<script type="text/javascript">
$(function(){
	/* $("#DI_HDMC").val("名称1");
	$("#in-ckrq").val("2013-03-05");
	$("#in-jsrq").val("2013-09-25 "); */
	/* if($("#DI_SFSP").val()==0){
		$("#la_gdfy").hide();
		$("#DI_SFGDFY").hide();
		$("#la_hdfy").hide();
		$("#DI_HDFY").hide();	
	}
	if($("#DI_SFSP").val()==1){
		$("#la_gdfy").show();
		$("#DI_SFGDFY").show();
		if($("#SFGDFY").val()==1){
			$("#la_hdfy").show();
			$("#DI_HDFY").show();
		}
		if($("#SFGDFY").val()==0){
			$("#la_hdfy").hide();
			$("#DI_HDFY").hide();
		}
	} */
	$("#fwhdcxlb").show();
	$("#fwhdcxlb").jTable(); //车型
	$("#fwhdcllb").show();
	$("#fwhdcllb").jTable();//车龄
	$("#fwhdxmgslb").show();
	$("#fwhdxmgslb").jTable();//工时
	$("#fwhdxmpjlb").show();
	$("#fwhdxmpjlb").jTable();//配件
	$("#fwhdxmqtfylb").show();
	$("#fwhdxmqtfylb").jTable();//项目
	$("#fwhdfwslb").show();
	$("#fwhdfwslb").jTable();//服务商信息
	$("#fwhdfjlb").show();
	$("#fwhdfjlb").jTable();//附件
	/* $("#searchModel").click(function(){
		$("#fwhdcxlb").show();
		$("#fwhdcxlb").jTable(); //车型
	});
	$("#searchModelYear").click(function(){
		$("#fwhdcllb").show();
		$("#fwhdcllb").jTable();//车龄
	});
	$("#searchWorkH").click(function(){
		$("#fwhdxmgslb").show();
		$("#fwhdxmgslb").jTable();//工时
	});
	
	$("#searchPart").click(function(){
		$("#fwhdxmpjlb").show();
		$("#fwhdxmpjlb").jTable();//配件
	});
	$("#searchProject").click(function(){
		$("#fwhdxmqtfylb").show();
		$("#fwhdxmqtfylb").jTable();//项目
	});
	$("#searchDealer").click(function(){
		$("#fwhdfwslb").show();
		$("#fwhdfwslb").jTable();//服务商信息
	});
	$("#searchAtt").click(function(){
		$("#fwhdfjlb").show();
		$("#fwhdfjlb").jTable();//附件
	}); */
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("fwhdxxmx");
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
					<li><a href="javascript:void(0)"><span>活动信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>车型信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>车龄信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>工时信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>配件信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>项目信息</span></a></li>
					<li ><a href="javascript:void(0)"><span>附件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-fwhdxx" class="editForm" >
					<div align="left">
					<fieldset>
					<table class="editTable" id="fwhdxx">
					    <tr>
					    	<td><label>活动代码：</label></td>
							<td>代码1</td>
							<td><label>活动名称：</label></td>
							<td colspan="3">名称1</td>
						</tr>
						<tr>	
							<td><label>活动类别：</label></td>
							<td>整改</td>
							<td><label>处理方式：</label></td>
							<td>维修</td>
							<td><label>行驶里程(公里)：</label></td>
							<td>2000</td>
						</tr>
						<tr>
						    <td><label>活动日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30"  value="2014-3-2"  readonly/>
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30"  value="2014-5-30" readonly/>
					   		 </td>
					 	</tr>
						<tr>	
							<td><label>是否索赔：</label></td>
							<td id="DI_SFSP">是</td>
							<td id="la_gdfy"><label>是否固定费用：</label></td>
							<td id="DI_SFGDFY">是</td>
							<td id="la_hdfy"><label>活动费用(元)：</label></td>
							<td id="DI_HDFY">300.00</td>
						</tr>
						<tr>
							<td><label>解决方案：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
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
						<li><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page">
			<!-- <div class="pageHeader">
			<form id="fm-fwhdcx" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
					</div>
				</div>
			</form>
			</div> -->
			<div class="pageContent">
				<div id="fwhdcx">
					<table width="100%" id="fwhdcxlb" name="fwhdcxlb" style="display: none" >
						<thead>
							<tr>
								<th  name="XH" style="display:">序号</th>
								<th>车型代码</th>
								<th>车型名称</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>代码1</td>
								<td>名称1</td>
							</tr>
							<tr>
								<td>2</td>
								<td>代码2</td>
								<td>名称2</td>
							</tr>
							<tr>
								<td>3</td>
								<td>代码3</td>
								<td>名称3</td>
							</tr>
							<tr>
								<td>4</td>
								<td>代码4</td>
								<td>名称4</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next3" name="btn-next">下一步</button></div></div></li>
				</ul>
			</div>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page">
			<div class="pageContent">
				<!-- <form id="fm-fwhdcl" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdclTable">
					</table>
					<div class="subBar">
					</div>
				</div>
				</form> -->
				<div id="fwhdcl">
					<table width="100%" id="fwhdcllb" name="fwhdcllb" style="display:none" >
						<thead>
							<tr>
								<th>起始日期</th>
								<th>结束日期</th>
								<th>类型</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>2014-5-15</td>
								<td>2014-5-30</td>
								<td>生产日期</td>
							</tr>
							<tr>
								<td>2014-2-20</td>
								<td>2014-5-30</td>
								<td>销售日期</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next4" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next5" name="btn-next">下一步</button></div></div></li>
				</ul>
			</div>
		</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<!-- <div class="pageHeader">
			<form id="fm-fwhdxmgs" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
					</div>
				</div>
			</form>
			</div> -->
			<div class="pageContent">
				<div id="fwhdxmgs">
					<table width="100%" id="fwhdxmgslb" name="fwhdxmgslb" style="display: none" >
						<thead>
							<tr>
								<th  name="XH" style="display:" ></th>
								<th>工时代码</th>
								<th>工时名称</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>代码1</td>
								<td>名称1</td>
							</tr>
							<tr>
								<td>2</td>
								<td>代码2</td>
								<td>名称2</td>
							</tr>
							<tr>
								<td>3</td>
								<td>代码3</td>
								<td>名称3</td>
							</tr>
							<tr>
								<td>4</td>
								<td>代码4</td>
								<td>名称4</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next6" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next7" name="btn-next">下一步</button></div></div></li>
				</ul>
			</div>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page">
			<!-- <div class="pageHeader">
			<form id="fm-fwhdxmpj" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
					</div>
				</div>
			</form>
			</div> -->
			<div class="pageContent">
				<div id="fwhdxmpj">
					<table width="100%" id="fwhdxmpjlb" name="fwhdxmpjlb" style="display: none" >
						<thead>
							<tr>
								<th  name="XH" style="display:" >序号</th>
								<th>配件代码</th>
								<th>配件名称</th>
								<th>数量</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>代码1</td>
								<td>名称1</td>
								<td>2</td>
							</tr>
							<tr>
								<td>2</td>
								<td>代码2</td>
								<td>名称2</td>
								<td>1</td>
							</tr>
							<tr>
								<td>3</td>
								<td>代码3</td>
								<td>名称3</td>
								<td>3</td>
							</tr>
							<tr>
								<td>4</td>
								<td>代码4</td>
								<td>名称4</td>
								<td>2</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next8" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next9" name="btn-next">下一步</button></div></div></li>
				</ul>
			</div>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page">
			<!-- <div class="pageHeader">
			<form id="fm-fwhdxmqtfy" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
					</div>
				</div>
			</form>
			</div> -->
			<div class="pageContent">
				<div id="fwhdxmqtfy">
					<table width="100%" id="fwhdxmqtfylb" name="fwhdxmqtfylb" style="display: none" >
						<thead>
							<tr>
								<th name="XH" style="display:" >序号</th>
								<th>项目代码</th>
								<th>项目名称</th>
								<th>金额(元)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>代码1</td>
								<td>名称1</td>
								<td>100.00</td>
							</tr>
							<tr>
								<td>2</td>
								<td>代码2</td>
								<td>名称2</td>
								<td>100.00</td>
							</tr>
							<tr>
								<td>3</td>
								<td>代码3</td>
								<td>名称3</td>
								<td>100.00</td>
							</tr>
							<tr>
								<td>4</td>
								<td>代码4</td>
								<td>名称4</td>
								<td>100.00</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next10" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next11" name="btn-next">下一步</button></div></div></li>
				</ul>
			</div>
			</div>
		</div>
		<div class="tabsContent" id="fjxxC">
			<div class="page">
			<!-- <div class="pageHeader">
		    <form id="fm-fwhdfj" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fm-fwhdfjTable">
					</table>
					<div class="subBar">
					</div>
				</div>
			</form>
			</div> -->
			<div class="pageContent">
				<div id="fwhdfj">
					<table width="100%" id="fwhdfjlb" name="fwhdfjlb" style="display: none" >
						<thead>
							<tr>
								<th  name="XH" style="display:" >序号</th>
								<th>附件名称</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td><a href="#" >ip配置.jsp</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="next12" name="btn-pre">上一步</button></div></div></li>
				</ul>
			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>