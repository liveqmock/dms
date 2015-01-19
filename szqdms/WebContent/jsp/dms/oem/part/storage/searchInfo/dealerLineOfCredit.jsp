<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgType = user.getOrgDept().getOrgType();
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>应收账款统计</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 应收账款统计</h4>
		<div class="page">
			<div class="pageHeader" 
			<%
				if(orgType.equals(DicConstant.ZZLB_09)){
			%>
				style="display: none;"
			<%		
				}
			%>
			>
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>渠道商代码：</label></td>
									<td>										
											<%
												if(orgType.equals(DicConstant.ZZLB_09)){
											%>
												<input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="A.ORG_CODE" value="<%=user.getOrgCode() %>"  datatype="1,is_null,100" operation="=" readonly="readonly" />
											<%		
												} else {
											
											%>
												<input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="A.ORG_CODE"  datatype="1,is_null,100" operation="in" readonly="readonly" hasBtn="true" callFunction="open();"/>
											<%
												}
											%>
									</td>
									<td><label>渠道商名称：</label></td>
									<td>										
										<input type="text" id="ORG_NAME" name="ORG_NAME"  datatype="1,is_null,100" operation="in" readonly="readonly" />
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重&nbsp;&nbsp;置</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
			</div>
			
			<div class="pageContent">
				<div id="page_contract" >
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:none"></th>
									<th fieldname="P_ORG_CODE">办事处代码</th>
									<th fieldname="P_ORG_NAME">办事处名称</th>
									<th fieldname="ORG_CODE">渠道商代码</th>
									<th fieldname="ORG_NAME">渠道商名称</th>
									<th fieldname="CLOSE_AMOUNT" refer="amountFormat" align="right">期初结转欠款</th>
									<th fieldname="BALANCE_AMOUNT" refer="amountFormat" align="right">信用总额</th>
									<!-- <th fieldname="OCCUPY_AMOUNT" refer="amountFormat" align="right">占用额度</th> -->
									<th fieldname="SUM_CLOSE_ORDER_AMOUNT" refer="amountFormat" align="right">占用（已关）</th>
									<th fieldname="SUM_OTHER_ORDER_AMOUNT" refer="amountFormat" align="right">占用（未关）</th>
									<th fieldname="AVAILABLE_AMOUNT" refer="amountFormat" align="right">可用额度</th>
									<th type="link" title="[占用详情]|[账户余额]" colwidth="130"   action="openDetailsInfo|openDtl" >操作</th>
								</tr>
							</thead>
							<tbody>
		                    </tbody>
					</table>
				</div>
			</div>
			<textarea id="val0" name="multivals" style="width:400px;height:10px;display: none;"></textarea>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	
	// 查询
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerLineOfCreditAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	}).click();
	
	// 清除
	$("#btn-clear").click(function(){
		$("#ORG_CODE").val("");
		$("#ORG_NAME").val("");
		$("#val0").val("");
	})
	
})
function open(){
	var id="ORG_CODE";
	/* 	showOrgTree(id,busType); */
	/**
	 * create by andy.ten@tom.com 2011-12-04
	 * 弹出组织选择树
	 * id:弹出选择树的input框id
	 * busType:业务类型[1,2]，1表示配件2表示售后
	 * partOrg:配件业务使用：[1,2]，1表示只显示配送中心2表示只显示服务商和经销商，不传表示全显示
	 * multi:[1,2],1表示默认是多选，2表示是单选
	 */
	showOrgTree(id,1,1,1);
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	  $("#ORG_CODE").val(orgCode).attr("code",orgCode); 
	  $("#ORG_NAME").val(orgName);
}

//打开详情页面
function openDetailsInfo(row){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/searchInfo/dealerLineOfCreditDetails.jsp?id="+$(row).attr("ACCOUNT_ID"), "accountDetails", "占用详情", options);
}
function openDtl(rowobj){
	$("td input[type=radio]", $(rowobj)).attr("checked", true);
	var options = {max:false,width:700,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/searchInfo/dealerLineOfCreditDtl.jsp", "editWin", "账户余额", options);
}
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>