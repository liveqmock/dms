<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page import="com.org.framework.util.Pub"%>
<% 
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.MONTH, -1);
	int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	int minday = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
	int month = calendar.get(Calendar.MONTH) + 1;
	int year = calendar.get(Calendar.YEAR);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算发票打印</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/financeMng/SettleInvoicePrintAction";
var day='<%=minday%>';
var mon='<%=month%>';
var year='<%=year%>';
var lastDay='<%=maxday%>';
$(function()
{
	$("#settleInvoiceBaseInfo").height(document.documentElement.clientHeight-30);
	var selectedRows=opener.$("#settleInvoicePrintList_content").getSelectedRows();
	$("#dia_settleId").val(selectedRows[0].attr("SETTLE_ID"));
	$("#OrgName").html(selectedRows[0].attr("ONAME")+"-"+selectedRows[0].attr("SNAME"));//服务站 名称
	$("#companyName").html("<br/>"+selectedRows[0].attr("COMPANY_NAME"));//
	/* 获取浏览器时间
	var d = new Date();
	var year = d.getFullYear();//年
	var mon = d.getMonth() + 1;//月
	var day =d.getDate();//日
	d.setDate(1);
	d.setMonth(d.getMonth()+1);
	var cdt = new Date(d.getTime()-1000*60*60*24);
	var lastDay=cdt.getDate(); */
	
	var settleType=selectedRows[0].attr("SETTLE_TYPE");
	if(settleType==<%=DicConstant.JSLX_01%>){
		$("#settleType").html("服务站服务费用通知单");
		$("#checkNotice").html("现将"+year+"年"+mon+"月份（"+year+"年"+mon+"月1日-"+mon+"月"+lastDay+"日终审通知应付服务费用）和"+year+"年"+mon+"月返旧件运费汇总如下。请服务站仔细 阅读下列相关内容：");
		$("#yearCheckNotice").text(year+"年月终审通过服务费用");
		$("#reCostsH").text("旧件运费");
	}else{
		$("#settleType").html("服务站材料费用通知单");
		$("#checkNotice").html("现将"+year+"年"+mon+"月份（"+year+"年"+mon+"月1日-"+mon+"月"+lastDay+"日终审通知应付材料费用）和"+year+"年"+mon+"月返旧件汇总如下。请服务站仔细 阅读下列相关内容：");
		$("#yearCheckNotice").text(year+"年月终审通过材料务费用");
		$("#reCostsH").text("配件返利");
	}
	$("#costs").text("￥"+selectedRows[0].attr("COSTS"));
	$("#reCosts").text("￥"+selectedRows[0].attr("RE_COSTS"));
	$("#policySupH").text("￥"+selectedRows[0].attr("POLICY_SUP"));//政策支持
	$("#cashGift").text("￥"+selectedRows[0].attr("CASH_GIFT"));//礼金
	$("#carAward").text("￥"+selectedRows[0].attr("CAR_AWARD"));//售车奖励
	$("#apCosts").text("￥"+selectedRows[0].attr("AP_COSTS"));//考核费用
	$("#othersCosts").text("￥"+selectedRows[0].attr("OTHERS"));//其它费用
	$("#summary").text("￥"+selectedRows[0].attr("SUMMARY"));//汇总
	$("#remarks").text("备注："+selectedRows[0].attr("REMARKS"));//汇总
	var sum=toChinese(selectedRows[0].attr("SUMMARY"));//备注
	$("#changeCapital").text("本次结算费用总计：（人民币大写）："+sum);
	$("#currentDate").html("<br/>"+selectedRows[0].attr("JSN")+"年"+selectedRows[0].attr("JSY")+"月"+selectedRows[0].attr("JSR")+"日"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	//$("#currentDate").html("<br/>"+year+"年"+mon+"月"+day+"日"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	$("#orgCode").text("服务站代码："+selectedRows[0].attr("ORG_CODE"));
	
	$("#print").bind("click",function(){
		var settleId=$("#dia_settleId").val();
		var url = diaSaveAction + "/settleUpdate.ajax?settleId="+settleId;
		sendPost(url,"print","",printCallBack,"false");
// 		$("#opBtn").hide();
// 		window.print();
	});
	//查看附件
	$("#viewAttr").bind("click",function(){
		var settleId=$("#dia_settleId").val();
		$.filestore.view(settleId);
	});
	$("button.close").click(function(){
		window.close();
		return false;
	});
});
//打印回调更新状态
function printCallBack(res){
	$("#opBtn").hide();
	document.body.innerHTML=document.getElementById("settleInvoiceBaseInfo").innerHTML;
	window.print();
	return true;
}
//数字变大写
function toChinese(n) {
	if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
	return "数据非法";
	var unit = "仟佰拾亿仟佰拾万仟佰拾元角分",
	str = "";
	n += "00";
	var p = n.indexOf('.');
	if (p >= 0)
		n = n.substring(0, p) + n.substr(p+1, 2);
	unit = unit.substr(unit.length - n.length);
	for (var i=0; i < n.length; i++)
		str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
	var chineseStr = str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
	return chineseStr;
}
</script>
</head>
<body style="overflow:auto;">
<div id="settleInvoiceBaseInfo" style="width:99%;">
<table style="width:80%;" align="center" id="test">
<tr>
<td align="center">
<div style="text-align: left;font-size:14px" id="OrgName"></div>
<br/>
<div style="text-align: center;font-size:16px;font-weight:bold;" id="settleType"></div>
	<table width="100%" border="0" cellSpacing= "0"  cellPadding= "1" frame="void">
		<input type="hidden" id="dia_settleId" datasource="SETTLE_ID"/>
		<tr>
			<td style="font-size:14px">
				<br/>
				<span style="font-size:14px" id="checkNotice"></span>
				<br/><br/>
				<span style="font-size:14px">1、 请各服务站仔细核对,于三日内按照新的开票信息开具并寄出增值税专用发票(开票内容为:维修费、修理费或服务费 请加盖发票专用章），我们收到费用发票后，及时核对、整理、汇总发票并交财务支付贵站服 务费用。</span>
				<br/><br/>
				<span style="font-size:14px">2、 请服务商确认签字后，详细、 认真填写服务商信息，请将此页与增值税专用发票（发票联及抵扣联）速寄：陕重汽 销司销售服务部索赔追偿科，请勿投寄错，以免延误费用结算。每月22日前将收到的发票（以签收日期为准）递交财  务，发票延迟收到将按时间段顺延交公司财务挂帐。</span>
				<br/><br/>
				<span style="font-size:14px">3、 贵服务站确实需要改“单位名称”、“账号及开户行”，应及时以书面形式写明更改原因并加盖公章，随该单寄陕重 汽销司销售服务部索赔追偿科（接财务通知：原则上一年内不允许服务商更改相应的单位名称、 账号及开户行）， 以备案待查。</span>
				<br/><br/>
				<span style="font-size:14px">4、 如有异议，请见该通知三日内以书面传真至029-86956854索赔追偿科，过期不再受理。</span>
			</td>
		</tr>
	</table>
	<br/>
    <table width="100%" border="1" cellSpacing="0" bordercolor="#000000" cellPadding="1">
		<tr>
			<td style="text-align: center;font-size:14px;font-weight:bold;width:33%">项目</td>
			<td style="text-align: center;font-size:14px;font-weight:bold;width:33%">月度合计费用</td>
			<td style="text-align: center;font-size:14px;font-weight:bold;width:34%">备注</td>
		</tr>
		<tr>
			<td id="yearCheckNotice" style="text-align: center;font-size:14px;font-weight:bold;"></td>
			<td id="costs" style="text-align: center;font-size:14px;font-weight:bold;"></td>
			<td style="text-align: center;font-size:14px;" rowspan="8"> 
				<br/>
				<span style="font-size:14px">1、合计金额为负数时，本月不用开票。</span>
				<br/><br/>
				<span style="font-size:14px">2、旧件补助标准以管理科制定的为依据。</span>
				<br/><br/>
				
			</td>			
		</tr>
		<tr>
			<td id="reCostsH" style="text-align: center;font-size:14px;font-weight:bold;"></td>
			<td id="reCosts" style="text-align: center;font-size:14px;font-weight:bold;"></td>		
		</tr>
		<tr>
			<td  style="text-align: center;font-size:14px;font-weight:bold;">政策支持</td>
			<td id="policySupH" style="text-align: center;font-size:14px;font-weight:bold;"></td>		
		</tr>
		<tr>
			<td  style="text-align: center;font-size:14px;font-weight:bold;">服务促销(含礼金卡等)</td>
			<td id="cashGift" style="text-align: center;font-size:14px;font-weight:bold;"></td>		
		</tr>
		<tr>
			<td style="text-align: center;font-size:14px;font-weight:bold;">售车奖励</td>
			<td id="carAward" style="text-align: center;font-size:14px;font-weight:bold;"></td>			
		</tr>
		<tr>
			<td style="text-align: center;font-size:14px;font-weight:bold;">考核费用</td>
			<td id="apCosts" style="text-align: center;font-size:14px;font-weight:bold;"></td>			
		</tr>
		<tr>
			<td style="text-align: center;font-size:14px;font-weight:bold;">其他费用</td>
			<td id="othersCosts" style="text-align: center;font-size:14px;font-weight:bold;"></td>			
		</tr>
		<tr>
			<td style="text-align: center;font-size:14px;font-weight:bold;">汇总</td>
			<td id="summary" style="text-align: center;font-size:14px;font-weight:bold;"></td>			
		</tr>			
	</table>
	<table width="100%" border="0" cellSpacing= "0"  cellPadding= "1" frame="void">
		<tr>
			<td style="font-size:14px">
			<br/>
				<span style="font-size:14px">说明：无</span>
			</td>
		</tr>
		<tr>
			<td style="font-size:14px">
			<br/>
				<span style="font-size:14px" id="remarks">备注：</span>
			</td>
		</tr>
		<tr>
			<td style="font-size:14px">
			<br/>
				<span style="font-size:14px;font-weight:bold;" id="changeCapital"></span>
			</td>
		</tr>
		<tr>
			<td style="font-size:14px">
			<br/>
				<span style="font-size:14px;font-weight:bold;">服务商确认签字：                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</span>
			</td>
		</tr>		
	</table>
	<br/>
	<table width="100%" border="1" cellSpacing="0" bordercolor="#000000" cellPadding="1">
		<tr>
			<td style="text-align: left;font-size:14px;width:33%">开发票信息</td>
			<td style="text-align: left;font-size:14px;width:33%">发票邮寄信息</td>
			<td style="text-align: left;font-size:14px;width:34%">服务商信息</td>
		</tr>
		<tr>
			<td style="text-align: left;font-size:14px;">单位全称：陕西重型汽车有限公司</td>
			<td style="text-align: left;font-size:14px;"  rowspan="6">
				<span style="text-align: left;font-size:14px;">单位名称：陕重汽销司销售</span>
				<br/>
				<span style="text-align: left;font-size:14px;">服务部索赔追偿科</span>
				<br/>
				<span style="text-align: left;font-size:14px;">地址：西安市经济技术开发</span>
				<br/>
				<span style="text-align: left;font-size:14px;">区泾渭工业园陕汽大道</span>
				<br/>
				<span style="text-align: left;font-size:14px;">收件人：李英</span>
				<br/>
				<span style="text-align: left;font-size:14px;">邮编：710200</span>
				<br/>
				<span style="text-align: left;font-size:14px;">电话：029-86956855</span>
			</td>
			<td style="text-align: left;font-size:14px;">单位全称:</td>		
		</tr>
		<tr>
			<td style="text-align: left;font-size:14px;">税号：610197741272070</td>
			<td style="text-align: left;font-size:14px;">税号：</td>		
		</tr>
		<tr>
			<td style="text-align: left;font-size:14px;height: 40px;">地址：西安市经济技术开发区泾渭工业园</td>
			<td style="text-align: left;font-size:14px;height: 40px;">地址：</td>		
		</tr>
		<tr>
			<td style="text-align: left;font-size:14px;">电话：029-86955402</td>
			<td style="text-align: left;font-size:14px;">电话：</td>		
		</tr>
		<tr>
			<td style="text-align: left;font-size:14px;">开户银行：工行西安市东新街支行</td>
			<td style="text-align: left;font-size:14px;">开户银行：</td>		
		</tr>
		<tr>
			<td style="text-align: left;font-size:14px;">帐号：3700012109022133020</td>
			<td style="text-align: left;font-size:14px;">帐号：</td>		
		</tr>			
	</table>
	<table width="100%" border="0" cellSpacing= "0"  cellPadding= "1" frame="void">
		<tr>
			<td style="font-size:14px;text-align:right;font-weight:bold;" colspan="2"><br/>陕西重型汽车有限公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td style="font-size:14px;text-align:right;font-weight:bold;" colspan="2"><br/>销售公司销售服务部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td id="currentDate" style="font-size:14px;text-align:right;font-weight:bold;" colspan="2"></td>
		</tr>
		<tr>
			<td id="orgCode" style="font-size:14px;text-align:left"><br/></td>
			<td style="font-size:14px;text-align:right" id="companyName"></td>
		</tr>		
	</table>
	</td>
	</tr>
</table>
<div id="opBtn" class="formBar">
	<ul>
		<li><div class="button"><div class="buttonContent"><button type="button" id="print">打&nbsp;&nbsp;印</button></div></div></li>
		<li><div class="button"><div class="buttonContent"><button type="button" id="viewAttr">查看附件</button></div></div></li>
		<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
	</ul>
</div>
</div>
</body>
</html>