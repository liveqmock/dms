 <?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车厂库存查询</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-hzcx").bind("click",function(event){
		if($("#tab-hzlb").is(":hidden"))
		{
			$("#page_kcmxlb").hide();
			$("#tab-mxlb").hide();
			$("#page_kchzlb").show();
			$("#tab-hzlb").show();
			$("#tab-hzlb").jTable();
		}
	});
	$("#btn-mxcx").bind("click",function(event){
		if($("#tab-mxlb").is(":hidden"))
		{
			$("#page_kchzlb").hide();
			$("#tab-hzlb").hide();
			$("#page_kcmxlb").show();
			$("#tab-mxlb").show();
			$("#tab-mxlb").jTable();
		}
	});
});

function doSubmit(){
	alertMsg.confirm("确认提交?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
}
function toDelete(){
	alertMsg.confirm("确认删除?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 库存管理   &gt; 车厂库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-kccx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-kccx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>仓库选择：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#CK1=本部库:CK2=待报废库:CK3=质损库" datasource="DDBH" datatype="0,is_null,30" operation="=" /></td>
					    <td><label>库区选择：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#A01=A01:A02=A02:A03=A03" datasource="DDBH" datatype="1,is_null,30" operation="=" /></td>
						<td><label>库位选择：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#A0101=A01-01:A0102=A01-02:A0103=A01-03" datasource="DDBH" datatype="1,is_null,30" operation="=" /></td>
					</tr>
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh"  datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" datasource="DDBH" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>供应商：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" kind="dic" src="E#GYS1=供应商一:GYS2=供应商二:GYS3=供应商三" datasource="DDBH" datatype="1,is_null,30" operation="=" /></td>
				   </tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-hzcx" >汇总查询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-hzxz" >汇总下载</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-mxcx" >明细查询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-mxxz" >明细下载</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_kchzlb" >
			<table style="display:none;width:100%;" id="tab-hzlb" name="tablist" ref="page_kchzlb" refQuery="fm-kccx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th fieldname="DDBH" >仓库</th>
							<th fieldname="DDBH" colwidth="100">配件代码</th>
							<th fieldname="GHPJK" colwidth="100">配件名称</th>
							<th fieldname="GHPJK" colwidth="80">库存数量</th>
							<th fieldname="GHPJK" colwidth="80">占用数量</th>
							<th fieldname="GHPJK" colwidth="80">可用库存</th>
							<th fieldname="GHPJK" >计划价</th>
							<th fieldname="GHPJK" >计划金额</th>
							<th fieldname="GHPJK" >经销商价</th>
							<th fieldname="GHPJK" >经销金额</th>
							<th fieldname="GHPJK" >库管员</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div>本部库</div></td>
							<td><div>PJ0001</div></td>
							<td><div>配件一</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
							<td><div>20.00</div></td>
							<td><div align="right">1,000.00</div></td>
							<td><div>25.00</div></td>
							<td><div align="right">1,250.00</div></td>
							<td><div>张三</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div>本部库</div></td>
							<td><div>PJ0002</div></td>
							<td><div>配件二</div></td>
							<td><div>110</div></td>
							<td><div>30</div></td>
							<td><div>80</div></td>
							<td><div>10.00</div></td>
							<td><div align="right">1,100.00</div></td>
							<td><div>12.00</div></td>
							<td><div align="right">1,220.00</div></td>
							<td><div>张三</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td><div>本部库</div></td>
							<td><div>PJ0003</div></td>
							<td><div>配件三</div></td>
							<td><div>190</div></td>
							<td><div>30</div></td>
							<td><div>160</div></td>
							<td><div>10.00</div></td>
							<td><div align="right">1,900.00</div></td>
							<td><div>11.00</div></td>
							<td><div align="right">2,090.00</div></td>
							<td><div>李四</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td><div>本部库</div></td>
							<td><div>PJ0004</div></td>
							<td><div>配件四</div></td>
							<td><div>70</div></td>
							<td><div>30</div></td>
							<td><div>40</div></td>
							<td><div>10.00</div></td>
							<td><div align="right">700.00</div></td>
							<td><div>15.00</div></td>
							<td><div align="right">1,050.00</div></td>
							<td><div>李四</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>5</div></td>
							<td><div>本部库</div></td>
							<td><div>PJ0005</div></td>
							<td><div>配件五</div></td>
							<td><div>150</div></td>
							<td><div>30</div></td>
							<td><div>120</div></td>
							<td><div>200.00</div></td>
							<td><div align="right">30,000.00</div></td>
							<td><div>15.00</div></td>
							<td><div align="right">33,750.00</div></td>
							<td><div>王五</div></td>
						</tr>
					</tbody>
			</table>
		</div>
		<div id="page_kcmxlb">
			<table style="display:none;width:100%;" id="tab-mxlb" name="tablist" ref="page_kcmxlb" refQuery="fm-kccx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th fieldname="DDBH">仓库</th>
							<th fieldname="DDBH">库区</th>
							<th fieldname="GHPJK">库位</th>
							<th fieldname="DDBH">配件代码</th>
							<th fieldname="GHPJK">配件名称</th>
							<th fieldname="GHPJK">供应商</th>
							<th fieldname="GHPJK" colwidth="60">库存数量</th>
							<th fieldname="GHPJK" colwidth="60">占用数量</th>
							<th fieldname="GHPJK" colwidth="60">可用库存</th>
							<th fieldname="GHPJK">计划价</th>
							<th fieldname="GHPJK">计划金额</th>
							<th fieldname="GHPJK">经销商价</th>
							<th fieldname="GHPJK">经销金额</th>
							<th fieldname="GHPJK">库管员</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div>本部库</div></td>
							<td><div>A01</div></td>
							<td><div>A01-01</div></td>
							<td><div>PJ0001</div></td>
							<td><div>配件一</div></td>
							<td><div>待定供应商</div></td>
							<td><div>50</div></td>
							<td><div>30</div></td>
							<td><div>20</div></td>
							<td><div>20.00</div></td>
							<td><div align="right">1,000.00</div></td>
							<td><div>25.00</div></td>
							<td><div align="right">1,250.00</div></td>
							<td><div>张三</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div>本部库</div></td>
							<td><div>A02</div></td>
							<td><div>A02-01</div></td>
							<td><div>PJ0002</div></td>
							<td><div>配件二</div></td>
							<td><div>供应商一</div></td>
							<td><div>70</div></td>
							<td><div>30</div></td>
							<td><div>40</div></td>
							<td><div>10.00</div></td>
							<td><div align="right">700.00</div></td>
							<td><div>15.00</div></td>
							<td><div align="right">1,050.00</div></td>
							<td><div>李四</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td><div>本部库</div></td>
							<td><div>A02</div></td>
							<td><div>A02-02</div></td>
							<td><div>PJ0003</div></td>
							<td><div>配件三</div></td>
							<td><div>待定供应商</div></td>
							<td><div>70</div></td>
							<td><div>30</div></td>
							<td><div>40</div></td>
							<td><div>10.00</div></td>
							<td><div align="right">700.00</div></td>
							<td><div>15.00</div></td>
							<td><div align="right">1,050.00</div></td>
							<td><div>李四</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td><div>本部库</div></td>
							<td><div>A03</div></td>
							<td><div>A03-01</div></td>
							<td><div>PJ0004</div></td>
							<td><div>配件四</div></td>
							<td><div>待定供应商</div></td>
							<td><div>150</div></td>
							<td><div>30</div></td>
							<td><div>120</div></td>
							<td><div>200.00</div></td>
							<td><div align="right">30,000.00</div></td>
							<td><div>15.00</div></td>
							<td><div align="right">33,750.00</div></td>
							<td><div>王五</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>5</div></td>
							<td><div>本部库</div></td>
							<td><div>A03</div></td>
							<td><div>A03-02</div></td>
							<td><div>PJ0005</div></td>
							<td><div>配件五</div></td>
							<td><div>供应商三</div></td>
							<td><div>150</div></td>
							<td><div>30</div></td>
							<td><div>120</div></td>
							<td><div>200.00</div></td>
							<td><div align="right">30,000.00</div></td>
							<td><div>15.00</div></td>
							<td><div align="right">33,750.00</div></td>
							<td><div>王五</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>