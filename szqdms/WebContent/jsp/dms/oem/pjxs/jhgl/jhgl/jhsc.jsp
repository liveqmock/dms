<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>计划生产</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#pjjhlb").is(":hidden")){
			$("#pjjhlb").show();
			$("#pjjhlb").jTable();
		}
		$("#di_jh").show();
	});
});
function open(){
	alertMsg.info("弹出仓库选择树！");
}
function doSave(){
	alertMsg.info("保存成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：计划管理&gt;计划管理&gt;计划生成</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="jhscform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="jhscTable">
					<tr>
					    <td><label>日期区间：</label></td>
					    <td>
				    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalender:true,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalender:true,dateFmt:'yyyy-MM-dd'})" />
				   		 </td>
				   		 <td><label>仓库范围：</label></td>
						<td><input type="text" id="CKFW" name="CKFW" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
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
		<div id="pjjh">
			<table width="100%" id="pjjhlb" name="pjjhlb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>配件仓库</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>是否新增</th>
						<th>提报数量</th>
						<th>总出库量</th>
						<th colwidth="80">配件总装车量</th>
						<th colwidth="110">现有车型配件增量</th>
						<th>故障率</th>
						<th colwidth="90">是否保修故障率</th>
						<th>本区域出库量</th>
						<th>库存数量</th>
						<th>推荐计划</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>配件仓库1</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
					<tr>
						<td>2</td>
						<td>配件仓库2</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
					<tr>
						<td>3</td>
						<td>配件仓库3</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div style="display: none" id="di_jh">
	<form method="post" id="fm-jh" class="editForm" >
		<div align="left">
		<table class="editTable" id="t_jh">
		    <tr>
				<td><label>计划名称：</label></td>
				<td><input type="text" id="JHMC" name="JHMC" datatype="0,is_null,100" /></td>
				<td><label>计划日期：</label></td>
				<td><input type="text" id="JHRQ"  name="JHRQ" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalender:true,dateFmt:'yyyy-MM-dd'})" /></td>
			</tr>
		</table>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>
</body>
</html>