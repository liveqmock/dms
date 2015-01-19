<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	$("#la_gdfy").hide();
	$("#DI_SFGDFY").hide();
	$("#la_hdfy").hide();
	$("#DI_HDFY").hide();
	//初始化页面
	if(action != "1")
	{
		$("#hddmT").show();
		$("#hddmI").show();
		$("#DI_HDMC").val("名称1");
		$("#in-ckrq").val("2013-03-05");
		$("#in-jsrq").val("2013-09-25 ");
		projectHide();
		$("#DI_SFSP").change(function(){
		    var sfspVal=$(this).val();
		    if(sfspVal==0){
				$("#la_gdfy").hide();
				$("#DI_SFGDFY").hide();
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
				projectHide();
		    }
			if(sfspVal==1){
				$("#la_gdfy").show();
				$("#DI_SFGDFY").show();
				if($("#SFGDFY").val()==1){
					$("#la_hdfy").show();
					$("#DI_HDFY").show();
					projectHide();
				}
				if($("#SFGDFY").val()==0){
					$("#la_hdfy").hide();
					$("#DI_HDFY").hide();
					projectShow();
				}
			}    
		});
		$("#SFGDFY").change(function(){
		    var sfgdfyVal=$(this).val();
		    if(sfgdfyVal==0){
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
				projectShow();
		    }
			if(sfgdfyVal==1){
				$("#la_hdfy").show();
				$("#DI_HDFY").show();
				projectHide();
			}    
		});
		$("#fwhdcllb").show();
		$("#fwhdcllb").jTable(); //车龄
		$("#fwhdfjlb").show();
		$("#fwhdfjlb").jTable(); //附件
	}else{
		$("#cxxxH").hide();
		$("#cxxxC").hide();
		$("#clxxH").hide();
		$("#clxxC").hide();
		$("#fjxxH").hide();
		$("#fjxxC").hide();
		projectHide();
		$("#DI_SFSP").change(function(){
		    var sfspVal=$(this).val();
		    if(sfspVal==0){
				$("#la_gdfy").hide();
				$("#DI_SFGDFY").hide();
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
		    }
			if(sfspVal==1){
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
			}    
		});
		$("#SFGDFY").change(function(){
		    var sfgdfyVal=$(this).val();
		    if(sfgdfyVal==0){
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
		    }
			if(sfgdfyVal==1){
				$("#la_hdfy").show();
				$("#DI_HDFY").show();
			}    
		});
	}
	
	$("#searchModel").click(function(){
		if($("#fwhdcxlb").is(":hidden")){
			$("#fwhdcxlb").show();
			$("#fwhdcxlb").jTable(); //车型
		}
	});
	$("#searchWorkH").click(function(){
		if($("#fwhdxmgslb").is(":hidden")){
			$("#fwhdxmgslb").show();
			$("#fwhdxmgslb").jTable();//工时
		}
		
	});
	
	$("#searchPart").click(function(){
		if($("#fwhdxmpjlb").is(":hidden")){
			$("#fwhdxmpjlb").show();
			$("#fwhdxmpjlb").jTable();//配件
		}
	});
	$("#searchProject").click(function(){
		if($("#fwhdxmqtfylb").is(":hidden")){
			$("#fwhdxmqtfylb").show();
			$("#fwhdxmqtfylb").jTable();//项目
		}
	});
	$("#searchAtt").click(function(){
		$("#fwhdfjlb").show();
		$("#fwhdfjlb").jTable();//附件
	});
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("fwhdxx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	
	/* //下一步
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
  	   	case 3:
  		   	break;
  	   	case 4:
  		   	break;
  	   	case 5:
  		   	break;
  	   	case 6:
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
     	   	case 3://第4个tab页
    	   		if(action == "1")
    	   		{
     	   		}else
     	   		{
     	   		}
	   			break;
     	   	case 4://第5个tab页
	 	   		if(action == "1")
	 	   		{
	     	   	}else
	     	   	{
	     	   	}
	   			break;
     	   	case 5://第6个tab页
	 	   		if(action == "1")
	 	   		{
	     	   	}else
	     	   	{
	     	   	}
	   			break;
	   		case 6://第7个tab页
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
	}); */
});
//新增车型
function doAddModel(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdcxxz.jsp", "fwhdcx", "服务活动车型新增", options);
}
//新增工时
function doAddWorkH(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdgsxz.jsp", "fwhdgs", "服务活动工时新增", options);
}
//新增配件
function doAddPart(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdpjxz.jsp", "fwhdpj", "服务活动配件新增", options);
}
//新增项目
function doAddProject(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdxmxz.jsp", "fwhdxm", "服务活动项目新增", options);
}
//新增附件
function doAddAtt(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/fwhdgl/fwhdgl/fwhdfjxz.jsp", "fwhdfj", "服务活动附件新增", options);
}
//删除附件
function doDeleteAtt(){
	alertMsg.info("删除成功");
}
//批量删除
function doDelete(rowobj){
	row = $(rowobj);
	alertMsg.info("批量删除成功");
}
function projectShow(){
	$("#gsxxH").show();
	$("#gsxxC").show();
	$("#pjxxH").show();
	$("#pjxxC").show();
	$("#xmxxH").show();
	$("#xmxxC").show();
}
function projectHide(){
	$("#gsxxH").hide();
	$("#gsxxC").hide();
	$("#pjxxH").hide();
	$("#pjxxC").hide();
	$("#xmxxH").hide();
	$("#xmxxC").hide();
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if(row)
			row.remove();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doSaveAct(){
	var $f = $("#fm-fwhdxx");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
	$("#hddmT").show();
	$("#hddmI").show();
	$("#cxxxH").show();
	$("#cxxxC").show();
	$("#clxxH").show();
	$("#clxxC").show();
	$("#fjxxH").show();
	$("#fjxxC").show();
	if($("#DI_SFSP").val()==1){
		projectShow();
	}
	if($("#SFGDFY").val()==1){
		projectHide();

	}
	$("#fwhdcllb").show();
	$("#fwhdcllb").jTable(); //车龄
	$("#fwhdfjlb").show();
	$("#fwhdfjlb").jTable(); //附件
}
function doSaveModelYear(){
	alertMsg.info("保存成功！");
}
function doSaveClxx(){
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
					<li><a href="javascript:void(0)"><span>活动信息</span></a></li>
					<li id="cxxxH"><a href="javascript:void(0)"><span>车型信息</span></a></li>
					<li id="clxxH"><a href="javascript:void(0)"><span>车龄信息</span></a></li>
					<li id="gsxxH"><a href="javascript:void(0)"><span>工时信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
					<li id="xmxxH"><a href="javascript:void(0)"><span>项目信息</span></a></li>
					<li id="fjxxH"><a href="javascript:void(0)"><span>附件信息</span></a></li>
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
							<td id="hddmT" style="display:none"><label>活动代码：</label></td>
							<td id="hddmI" style="display:none"><input type="text" id="DI_HDDM" name="DI_HDDM" value="活动代码1" datatype="0,is_null,100" readonly="readonly"/></td>
							<td><label>活动名称：</label></td>
							<td colspan="5"><input type="text" id="DI_HDMC" name="DI_HDMC" datatype="0,is_null,100" /></td>
						</tr>
						<tr>	
							<td><label>活动类别：</label></td>
							<td><select  type="text" id="DI_HDLB" name="DI_HDLB" kind="dic" class="combox" src="E#1=整改:2=促销"  datatype="0,is_null,100" value="" >
									<option value=1 selected>维修</option>
								</select>
							</td>
							<td><label>处理方式：</label></td>
							<td><select type="text" id="DI_CLFS" name="DI_CLFS"  kind="dic" class="combox" src="E#1=维修:2=零件更换" datatype="0,is_null,100" value="" >
									<option value=1 selected>维修</option>
								</select>
							</td>
							<td><label>行驶里程(公里)：</label></td>
							<td><input type="text" id="DI_XSLC" name="DI_XSLC" datatype="1,is_double,100"/></td>
						</tr>
						<tr>
						    <td><label>活动日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="0,is_null,30" onclick="WdatePicker()" />
					   		 </td>
					 	</tr>
						<tr>	
							<td><label>是否索赔：</label></td>
							<td><select  type="text" id="DI_SFSP" name="DI_SFSP" kind="dic" class="combox" src="SF"  datatype="1,is_null,100" value="" >
									<option value="0" selected>否</option>
								</select>
							</td>
							<td id="la_gdfy"><label>是否固定费用：</label></td>
							<td id="DI_SFGDFY"><select  type="text" id="SFGDFY" name="SFGDFY" kind="dic" class="combox" src="SF"  datatype="1,is_null,100" value="" >
									<option value="0" >否</option>
								</select>
							</td>
							<td id="la_hdfy"><label>活动费用(元)：</label></td>
							<td><input type="text" id="DI_HDFY" name="DI_HDFY" datatype="1,is_double,100"/></td>
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
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSaveAct()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
						<!-- <li><div class="button"><div class="buttonContent"><button type="button" id="next1" name="btn-next">下一步</button></div></div></li> -->
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="tabsContent" id="cxxxC">
			<div class="page">
			<div class="pageHeader">
			<form id="fm-fwhdcx" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdcxTable">
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
				<div id="fwhdcx">
					<table width="100%" id="fwhdcxlb" name="fwhdcxlb" style="display: none" >
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
				<div class="formBar" id="fb1" style="display:none">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="next2" name="btn-next">下一步</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
		</div>
		<div class="tabsContent" id="clxxC">
			<div class="page">
			<div class="pageContent">
				<div id="fwhdcl">
					<table width="100%" id="fwhdcllb" name="fwhdcllb" edit="true" style="display:none" >
						<thead>
							<tr>
								<th fieldname="QSRQ" colWidth="80" ftype="input" fonclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})">起始日期</th>
								<th fieldname="JSRQ" colWidth="80" ftype="input" fonclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})">结束日期</th>
								<th colWidth="80">类型</th>
								<th  colwidth="85" type="link" title="[保存]"  action="doSaveClxx">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>2014-05-04</td>
								<td>2014-06-20</td>
								<td>生产日期</td>
								<td><a href="#" onclick="doSaveClxx()" class="op">[保存]</a></td>
							</tr>
							<tr>
								<td>2014-06-24</td>
								<td>2014-07-20</td>
								<td>销售日期</td>
								<td><a href="#" onclick="doSaveClxx()" class="op">[保存]</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar" id="fb2" style="display:none">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSaveModelYear()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
						<!-- <li><div class="button"><div class="buttonContent"><button type="button" id="next3" name="btn-next">下一步</button></div></div></li> -->
					</ul>
			</div>
			</div>
		</div>
		<div class="tabsContent" id="gsxxC">
			<div class="page">
			<div class="pageHeader">
			<form id="fm-fwhdxmgs" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdxmgsTable">
						<tr>
							<td><label>工时代码：</label></td>
							<td><input type="text" id="GSDM" name="GSDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>工时名称：</label></td>
							<td><input type="text" id="GSMC" name="GSMC" datatype="1,is_null,100" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchWorkH">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addWorkH" onclick="doAddWorkH()">新&nbsp;&nbsp;增</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="deleteWorkH" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent">
				<div id="fwhdxmgs">
					<table width="100%" id="fwhdxmgslb" name="fwhdxmgslb" style="display: none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:" ></th>
								<th>工时代码</th>
								<th>工时名称</th>
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
				<!-- <div class="formBar" id="fb3" style="display:none">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="next4" name="btn-next">下一步</button></div></div></li>
					</ul>
				</div> -->
			</div>
			</div>
		</div>
		<div class="tabsContent" id="pjxxC">
			<div class="page">
			<div class="pageHeader">
			<form id="fm-fwhdxmpj" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fm-fwhdxmpjTable">
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
				<div id="fwhdxmpj">
					<table width="100%" id="fwhdxmpjlb" name="fwhdxmpjlb" style="display: none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:" ></th>
								<th>配件代码</th>
								<th>配件名称</th>
								<th>数量</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码1</td>
								<td>名称1</td>
								<td>2</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码2</td>
								<td>名称2</td>
								<td>1</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码3</td>
								<td>名称3</td>
								<td>3</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码4</td>
								<td>名称4</td>
								<td>2</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- <div class="formBar" id="fb4" style="display:none">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="next5" name="btn-next">下一步</button></div></div></li>
					</ul>
				</div> -->
			</div>
			</div>
		</div>
		<div class="tabsContent" id="xmxxC">
			<div class="page">
			<div class="pageHeader">
			<form id="fm-fwhdxmqtfy" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdxmqtfyTable">
						<tr>
							<td><label>项目代码：</label></td>
							<td><input type="text" id="XMDM" name="XMDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>项目名称：</label></td>
							<td><input type="text" id="XMMC" name="XMMC" datatype="1,is_null,100" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchProject">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addProject" onclick="doAddProject()">新&nbsp;&nbsp;增</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="deleteProject" onclick="doDelete()">删&nbsp;&nbsp;除</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent">
				<div id="fwhdxmqtfy">
					<table width="100%" id="fwhdxmqtfylb" name="fwhdxmqtfylb" style="display: none" >
						<thead>
							<tr>
								<th type="multi" name="XH" style="display:" ></th>
								<th>项目代码</th>
								<th>项目名称</th>
								<th>金额(元)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码1</td>
								<td>名称1</td>
								<td>100.00</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码2</td>
								<td>名称2</td>
								<td>100.00</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码3</td>
								<td>名称3</td>
								<td>100.00</td>
							</tr>
							<tr>
								<td><input type ="checkbox" /></td>
								<td>代码4</td>
								<td>名称4</td>
								<td>100.00</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- <div class="formBar" id="fb5" style="display:none">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="next4" name="btn-next">下一步</button></div></div></li>
					</ul>
				</div> -->
			</div>
			</div>
		</div>
		<div class="tabsContent" id="fjxxC">
			<div class="page">
			<div class="pageHeader">
			<form id="fm-fwhdfj" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fm-fwhdfjTable">
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="addAtt" onclick="doAddAtt()">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent">
				<div id="fwhdfj">
					<table width="100%" id="fwhdfjlb" name="fwhdfjlb" style="display: none" >
						<thead>
							<tr>
								<th  name="XH" style="display:" >序号</th>
								<th>附件名称</th>
								<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td><a href="#" >ip配置.jsp</a></td>
								<td ><a href="#" onclick="doDeleteAtt()" class="op">[删除]</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>