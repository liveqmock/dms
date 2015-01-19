<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>打款登记</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#dkdzlb").is(":hidden")){
			$("#dkdzlb").show();
			$("#dkdzlb").jTable();
		}
	});
});
function doApply(){
	alertMsg.info("提报成功！");
}
function doDelete(){
	alertMsg.info("删除成功！");
}
function doAdd(){
	var options = {max:false,width:500,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/cwgl/dkgl/dkdjxz.jsp", "dkdjxx", "打款登记信息", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;打款管理&gt;打款登记</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="dkdjform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="dkdjTable">
					<tr>
						<td><label>登记日期：</label></td>
						<td>
				    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   	</td>
						<td><label>账户类型：</label></td>
						<td><select type="text" id="ZHLX" name="ZHLX" class="combox" kind="dic" src="E#1=现金账户:2=承兑汇票账户" datatype="1,is_null,100" value="" >
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="zjzh">
			<table width="100%" id="dkdzlb" name="dkdzlb" style="display: none" >
				<thead>
					<tr>
						<th>登记日期</th>
						<th>账户类型</th>
						<th>承兑票号</th>
						<th align="right">金额</th>
						<th>备注</th>
						<th colwidth="85" type="link" title="[提报]|[删除]"  action="doApply|doDelete">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>2014-05-20</td>
						<td>现金账户</td>
						<td></td>
						<td>10,000,000.00</td>
						<td></td>
						<td><a href="#" onclick="doApply()" class="op">[提报]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
					<tr>
						<td>2014-05-25</td>
						<td>承兑汇票</td>
						<td>100A012234</td>
						<td>10,000,000.00</td>
						<td></td>
						<td><a href="#" onclick="doApply()" class="op">[提报]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
					<tr>
						<td>2014-05-20</td>
						<td>现金账户</td>
						<td></td>
						<td>10,000,000.00</td>
						<td></td>
						<td><a href="#" onclick="doApply()" class="op">[提报]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
					<tr>
						<td>2014-05-20</td>
						<td>现金账户</td>
						<td></td>
						<td>10,000,000.00</td>
						<td></td>
						<td><a href="#" onclick="doApply()" class="op">[提报]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>