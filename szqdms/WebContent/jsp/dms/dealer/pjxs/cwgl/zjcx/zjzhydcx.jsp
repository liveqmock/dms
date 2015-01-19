<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>资金账户异动查询</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#zjzhlb").is(":hidden")){
			$("#zjzhlb").show();
			$("#zjzhlb").jTable();
		}
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;资金查询&gt;资金账户异动查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="zjzhcxform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="zjzhcxTable">
					<tr>
						<td><label>异动日期：</label></td>
						<td>
				    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   	</td>
						<td><label>账户类型：</label></td>
						<td><select type="text" id="ZHLX" name="ZHLX" class="combox" kind="dic" src="E#1=现金账户:2=承兑汇票账户:3=材料费账户:4=信用额度账户:5=返利账户" datatype="1,is_null,100" value="" >
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>操作类型：</label></td>
						<td><select type="text" id="CZLX" name="CZLX" class="combox" kind="dic" src="E#1=打款:2=扣款:3=占用" datatype="1,is_null,100" value="" >
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
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="zjzh">
			<table width="100%" id="zjzhlb" name="zjzhlb" style="display: none" >
				<thead>
					<tr>
						<th>异动日期</th>
						<th>账户类型</th>
						<th>操作类型</th>
						<th>异动金额</th>
						<th>凭证号码</th>
						<th>金税发票号</th>
						<th>外部单据号</th>
						<th>描述</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>2014-05-20</td>
						<td>定金账户</td>
						<td>打款</td>
						<td>10,000,000.00</td>
						<td>100A012233</td>
						<td>1953B012233</td>
						<td>1953C022231</td>
						<td></td>
					</tr>
					<tr>
						<td>2014-05-25</td>
						<td>定金账户</td>
						<td>扣款</td>
						<td>10,000,000.00</td>
						<td>100A012234</td>
						<td>1953B012234</td>
						<td>1953C022232</td>
						<td></td>
					</tr>
					<tr>
						<td>2014-05-28</td>
						<td>定金账户</td>
						<td>占用</td>
						<td>10,000,000.00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>2014-05-20</td>
						<td>定金账户</td>
						<td>打款</td>
						<td>10,000,000.00</td>
						<td>100A012235</td>
						<td>1953B012235</td>
						<td>1953C022234</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>