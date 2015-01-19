<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算单生成</title>
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
function doDownloadMb()
{
	alertMsg.info("下载导入模板！");
}
function pltz()
{
	alertMsg.info("批量调整");
}
function sckptzd()
{
	alertMsg.info("生成开票通知单");
}
function doUpdate(){
	var options = {max:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/cwgl/jsgl/jsdtzmx.jsp", "jsdtzmx", "结算单调整明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;结算管理&gt;结算单生成</h4>
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
						    <td><label>结算产生日期：</label></td>
						    <td>
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq"  datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   		</td>
						</tr>	
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="xzmb" onclick="doDownloadMb()">下载导入模板</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="pltz" onclick="pltz()">批量调整</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="sckptzd" onclick="sckptzd()">生成开票通知单</button></div></div></li>
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
							<th  name="XH" style="text-align:center;vertical-align:middle;" colwidth="10" rowspan="2" >序号</th>
							<th colwidth="80" style="text-align:center;vertical-align:middle; " rowspan="2">服务商代码</th>
							<th colwidth="80" style="text-align:center;vertical-align:middle;" rowspan="2">服务商名称</th>
							<th colwidth="60" style="text-align:center;vertical-align:middle;" rowspan="2">结算日期</th>
							<th colwidth="480" style="text-align:center;vertical-align:middle;" colspan="8">服务费汇总</th>
							<th colwidth="480" style="text-align:center;vertical-align:middle;" colspan="8">材料费汇总</th>
					  	    <th rowspan="2" colwidth="40" type="link" title="[调整]"  action="doUpdate" >操作</th>
						</tr>
						<tr>
							<th align="right" colwidth="60">服务费</th>
							<th align="right" colwidth="60">旧件运费</th>
							<th align="right" colwidth="60">政策支持</th>
							<th align="right" colwidth="60">礼金</th>
							<th align="right" colwidth="60">售车奖励</th>
							<th align="right" colwidth="60">考核费用</th>
							<th align="right" colwidth="60">其他费用</th>
							<th align="right" colwidth="60">汇总</th>
							<th align="right" colwidth="60">材料费</th>
							<th align="right" colwidth="60">配件返利</th>
							<th align="right" colwidth="60">政策支持</th>
							<th align="right" colwidth="60">礼金</th>
							<th align="right" colwidth="60">售车奖励</th>
							<th align="right" colwidth="60">考核费用</th>
							<th align="right" colwidth="60">其他费用</th>
							<th align="right" colwidth="60">汇总</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td>2014-4-8</td>
							<td>400.00</td>
							<td>100.00</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>500.00</td>
							<td>1,000.00</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1,000.00</td>
							<td><a href="#" onclick="doUpdate()" class="op">[调整]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码2</td>
							<td>服务商名称2</td>
							<td>2014-4-8</td>
							<td>400.00</td>
							<td>100.00</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>500.00</td>
							<td>1,000.00</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1,000.00</td>
							<td><a href="#" onclick="doUpdate()" class="op">[调整]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>