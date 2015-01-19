<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算单发票签收</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#jsdlb").is(":hidden")){
			$("#jsdlb").show();
			$("#jsdlb").jTable();
		}
	});
});
function open(){
	alertMsg.info("弹出服务商树！");
}
function doDetail(){
	var options = {max:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/cwgl/jsgl/jsdmx.jsp", "jsdmx", "结算单明细", options,true);
}
function doUpdate(){
	alertMsg.info("签收成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;结算管理&gt;结算单发票签收</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="jsdcxform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="jsdcxTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
							<td><label>快递单号：</label></td>
							<td><input type="text" value="" /></td>
						</tr>	
						<tr>
							<td><label>结算单号：</label></td>
							<td><input type="text"   value="" /></td>
							<td><label>结算产生日期：</label></td>
						    <td>
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq"  datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   		</td>
					   		<td><label>结算类型：</label></td>
							<td><select type ="text" id="JSLX" name="JSLX" class="combox" kind="dic" src="E#1=材料费:2=服务费">
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="jsd" >
				<table width="100%" id="jsdlb" name="jsdlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:" colwidth="20">序号</th>
							<th colwidth="60">服务商代码</th>
							<th colwidth="60">服务商名称</th>
							<th colwidth="60">结算单号</th>
							<th colwidth="60">快递单号</th>
							<th colwidth="60">结算产生日期</th>
							<th colwidth="60">结算类型</th>
							<th colwidth="60">索赔单数</th>
							<th align="right" colwidth="80">服务费/材料费</th>
							<th align="right" colwidth="80">旧件运费/配件返利</th>
							<th align="right" colwidth="60">政策支持</th>
							<th align="right" colwidth="60">礼金</th>
							<th align="right" colwidth="60">售车奖励</th>
							<th align="right" colwidth="60">考核费用</th>
							<th align="right" colwidth="60">其他费用</th>
							<th align="right" colwidth="60">索赔总金额(元)</th>
							<th colwidth="60" type="link"  title="[签收]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td><a href="#" onclick="doDetail()" class="op">结算单号1</a></td>
							<td>快递单号1</td>
							<td>2014-4-8</td>
							<td>服务费</td>
							<td>4</td>
							<td>400</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>400</td>
							<td><a href="#" onclick="doUpdate()" class="op">[签收]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td><a href="#" onclick="doDetail()" class="op">结算单号2</a></td>
							<td>快递单号2</td>
							<td>2014-4-8</td>
							<td>材料费</td>
							<td>4</td>
							<td>400</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>400</td>
							<td><a href="#" onclick="doUpdate()" class="op">[签收]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务商代码2</td>
							<td>服务商名称2</td>
							<td><a href="#" onclick="doDetail()" class="op">结算单号3</a></td>
							<td>快递单号3</td>
							<td>2014-4-8</td>
							<td>服务费</td>
							<td>4</td>
							<td>400</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>400</td>
							<td><a href="#" onclick="doUpdate()" class="op">[签收]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务商代码3</td>
							<td>服务商名称3</td>
							<td><a href="#" onclick="doDetail()" class="op">结算单号4</a></td>
							<td>快递单号4</td>
							<td>2014-4-8</td>
							<td>服务费</td>
							<td>4</td>
							<td>400</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>400</td>
							<td><a href="#" onclick="doUpdate()" class="op">[签收]</a></td>
						</tr>
						<tr>
							<td>5</td>
							<td>服务商代码4</td>
							<td>服务商名称4</td>
							<td><a href="#" onclick="doDetail()" class="op">结算单号5</a></td>
							<td>快递单号5</td>
							<td>2014-4-8</td>
							<td>服务费</td>
							<td>4</td>
							<td>400</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>400</td>
							<td><a href="#" onclick="doUpdate()" class="op">[签收]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>