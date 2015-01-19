<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预测上报</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#ycyflb").is(":hidden")){
			$("#ycyflb").show();
			$("#ycyflb").jTable();
		}
	});
});
function doDelete(){
	//alert(alertMsg.confirm("确定删除?"));
	alertMsg.info("删除成功");
}
function doAdd(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/jhgl/ycgl/ycsbxz.jsp?action=1", "ycsbxx", "预测上报新增", options,true);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/jhgl/ycgl/ycsbxz.jsp?action=2", "ycsbxx", "预测上报编辑", options,true);
}
function doDetail(){
	var options = {max:false,width:700,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/pjxs/jhgl/ycgl/yccxmx.jsp", "ycmx", "预测明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：计划管理&gt;预测管理&gt;预测上报</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="ycsbform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="ycsbTable">
					<tr>
						<td><label>预测月份：</label></td>
						<td><input type="text" id="YCYF" name="YCYF"datatype="1,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" /></td>
						<td><label>状态：</label></td>
						<td><select type="text" id="ZT" name="ZT"  datatype="1,is_null,100" value="" class="combox" kind="dic" src="E#1=已保存:2=已提报">
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="ycyf">
			<table width="100%" id="ycyflb" name="ycyflb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>预测月份</th>
						<th>状态</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>2014-06</td>
						<td>已保存</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>2014-05</td>
						<td>已提报</td>
						<td><a href="#" onclick="doDetail()" class="op">[明细]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>