 <?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发运出库</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-ddlb").is(":hidden"))
		{
			$("#tab-ddlb").show();
			$("#tab-ddlb").jTable();
		}
	});
});

//列表审核链接
function doSave(row)
{
	alertMsg.info("保存成功.");
}
function doSubmit(){
	alertMsg.confirm("确认结算?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
}
function doApproveOk1(){
	alertMsg.info("操作成功.");
}
function doApproveOk2(){
	return false;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 物流信息管理   &gt; 运费调整结算</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>发运单号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>承运单位：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" scr="E#WL1=物流公司一:WL2=物流公司二:WL3=物流公司三" datasource="DDBH" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>发运日期：</label></td>
					    <td>
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_ddlb" >
			<table style="display:none;width:100%;" id="tab-ddlb" name="tablist" ref="page_ddlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="DDBH" ordertype='local' class="desc">发运单号</th>
							<th fieldname="GHPJK" >承运单位</th>
							<th fieldname="GHPJK" >承运车辆</th>
							<th fieldname="GHPJK" >承运司机</th>
							<th fieldname="GHPJK" >联系电话</th>
							<th fieldname="GHPJK" >发运日期</th>
							<th fieldname="GHPJK" >公里数</th>
							<th fieldname="GHPJK" >运费单价</th>
							<th fieldname="TBSJ">计划运费</th>
							<th fieldname="SHY" colwidth="70">实际运费</th>
							<th fieldname="SHY" colwidth="110">备注</th>
							<th colwidth="70" type="link" title="[保存]|[结算]"  action="doSave|doSubmit" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>FYD20140519001</div></td>
							<td><div>物流公司一</div></td>
							<td><div>陕A-B1234</div></td>
							<td><div>李四</div></td>
							<td><div>13672369298</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>360</div></td>
							<td><div>1.5</div></td>
							<td><div align="right">540.00</div></td>
							<td><div><input type="text" style="width:60px;" value="540"/></div></td>
							<td><div><input type="text" style="width:100px;" value=""/></div></td>
							<td><a href="#" onclick="doSave()" class="op">[保存]</a><a href="#" onclick="doSubmit()" class="op">[结算]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>FYD20140519002</div></td>
							<td><div>物流公司一</div></td>
							<td><div>陕A-B1234</div></td>
							<td><div>王四</div></td>
							<td><div>13672369298</div></td>
							<td><div>2014-05-20</div></td>
							<td><div>450</div></td>
							<td><div>1.8</div></td>
							<td><div align="right">810.00</div></td>
							<td><div><input type="text" style="width:60px;" value="810"/></div></td>
							<td><div><input type="text" style="width:100px;" value=""/></div></td>
							<td><a href="#" onclick="doSave()" class="op">[保存]</a><a href="#" onclick="doSubmit()" class="op">[结算]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>