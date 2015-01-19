<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔维修信息</title>
<script type="text/javascript">
function doPrint(){
	alertMsg.info("打印成功");
}
$(function()
{
	$("button.close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
	$("#jsdxx").height(document.documentElement.clientHeight-30);
});
</script>
</head>
<body>
<div id="jsdxx" style="overflow:auto;width:99%;">
<div style="text-align: left;font-size:16px">济南办事处  莱芜解放服务站</div>
<br/>
<div style="text-align: center;font-size:20px;font-weight:bold;">服务站服务费用通知单</div>
<br/>
          <table width="100%" border="0" cellSpacing= "0"  cellPadding= "1" frame="void">
				<tr>
					<td style="font-size:16px">
						<br/>
						<span style="font-size:16px">现将2014年3月份（2014年3月1日-3月31日终审通知应付服务费用）和2014年3月返旧件汇总如下。请服务站仔细 阅读下列相关内容：</span>
						<br/><br/>
						<span style="font-size:16px">1、 请各服务站仔细核对,于三日内按照新的开票信息开具并寄出增值税专用发票(开票内容为:维修费、修理费或服务费 请加盖发票专用章），我们收到费用发票后，及时核对、整理、汇总发票并交财务支付贵站服 务费用。</span>
						<br/><br/>
						<span style="font-size:16px">2、 请服务商确认签字后，详细、 认真填写服务商信息，请将此页与增值税专用发票（发票联及抵扣联）速寄：陕重汽 销司销售服务部索赔追偿科，请勿投寄错，以免延误费用结算。每月22日前将收到的发票（以签收日期为准）递交财  务，发票延迟收到将按时间段顺延交公司财务挂帐。</span>
						<br/><br/>
						<span style="font-size:16px">3、 贵服务站确实需要改“单位名称”、“账号及开户行”，应及时以书面形式写明更改原因并加盖公章，随该单寄陕重 汽销司销售服务部索赔追偿科（接财务通知：原则上一年内不允许服务商更改相应的单位名称、 账号及开户行）， 以备案待查。</span>
						<br/><br/>
						<span style="font-size:16px">4、 如有异议，请见该通知三日内以书面传真至029-86956854索赔追偿科，过期不再受理。</span>
					</td>
				</tr>
			</table>
			<br/><br/>
            <table width="100%" border="1" cellSpacing="0" bordercolor="#000000" cellPadding="1">
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;width:33%">项目</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;width:33%">月度合计费用</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;width:34%">备注</td>
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">2014年月终审通过服务费用</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 3306</td>
					<td style="text-align: center;font-size:16px;" rowspan="8"> 
						<br/>
						<span style="font-size:16px">1、合计金额为负数时，本月不用开票。</span>
						<br/><br/>
						<span style="font-size:16px">2、旧件补助标准以管理科制定的为依据。</span>
						<br/><br/>
						<span style="font-size:16px">3、费用已按四舍五入处理，开票金额以大写金额为准。</span>
						<br/><br/>
					</td>			
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">旧件运费</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 0</td>		
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">政策支持</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 0</td>		
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">礼金</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 0</td>		
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">售车奖励</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 0</td>			
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">考核费用</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 0</td>			
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">其他费用</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 2880</td>			
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;font-weight:bold;">汇总</td>
					<td style="text-align: center;font-size:16px;font-weight:bold;">￥ 6186</td>			
				</tr>			
			</table>
			<table width="100%" border="0" cellSpacing= "0"  cellPadding= "1" frame="void">
				<tr>
					<td style="font-size:16px">
					<br/>
						<span style="font-size:16px">其他费用说明：无</span>
					</td>
				</tr>
				<tr>
					<td style="font-size:20px">
					<br/>
						<span style="font-size:20px">本次结算费用总计：（人民币大写）：                 贰仟玖佰柒拾肆元整 </span>
					</td>
				</tr>
				<tr>
					<td style="font-size:20px">
					<br/>
						<span style="font-size:20px">服务商确认签字：                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</span>
					</td>
				</tr>		
			</table>
			<br/>
			<table width="100%" border="1" cellSpacing="0" bordercolor="#000000" cellPadding="1">
				<tr>
					<td style="text-align: left;font-size:16px;width:33%">开发票信息</td>
					<td style="text-align: left;font-size:16px;width:33%">发票邮寄信息</td>
					<td style="text-align: left;font-size:16px;width:34%">服务商信息</td>
				</tr>
				<tr>
					<td style="text-align: left;font-size:16px;">单位全称：陕西重型汽车有限公司</td>
					<td style="text-align: left;font-size:16px;"  rowspan="6">
						<span style="text-align: left;font-size:16px;">单位名称：陕重汽销司销售</span>
						<br/>
						<span style="text-align: left;font-size:16px;">服务部索赔追偿科</span>
						<br/>
						<span style="text-align: left;font-size:16px;">地址：西安市经济技术开发</span>
						<br/>
						<span style="text-align: left;font-size:16px;">区泾渭工业园陕汽大道</span>
						<br/>
						<span style="text-align: left;font-size:16px;">收件人：李英</span>
						<br/>
						<span style="text-align: left;font-size:16px;">邮编：710200</span>
						<br/>
						<span style="text-align: left;font-size:16px;">电话：029-86956855</span>
					</td>
					<td style="text-align: left;font-size:16px;">单位全称:</td>		
				</tr>
				<tr>
					<td style="text-align: left;font-size:16px;">税号：610197741272070</td>
					<td style="text-align: left;font-size:16px;">税号：</td>		
				</tr>
				<tr>
					<td style="text-align: left;font-size:16px;height: 40px;">地址：西安市经济技术开发区泾渭工业园</td>
					<td style="text-align: left;font-size:16px;height: 40px;">地址：</td>		
				</tr>
				<tr>
					<td style="text-align: left;font-size:16px;">电话：029-86955402</td>
					<td style="text-align: left;font-size:16px;">电话：</td>		
				</tr>
				<tr>
					<td style="text-align: left;font-size:16px;">户银行：工行西安市东新街支行</td>
					<td style="text-align: left;font-size:16px;">户银行：</td>		
				</tr>
				<tr>
					<td style="text-align: center;font-size:16px;">帐号：3700012109022133020</td>
					<td style="text-align: center;font-size:16px;">帐号：</td>		
				</tr>			
			</table>
			<table width="100%" border="0" cellSpacing= "0"  cellPadding= "1" frame="void">
				<tr>
					<td style="font-size:16px;text-align:right;font-weight:bold;" colspan="2"><br/>陕西重型汽车有限公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td style="font-size:16px;text-align:right;font-weight:bold;" colspan="2"><br/>销售公司销售服务部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td style="font-size:16px;text-align:right;font-weight:bold;" colspan="2"><br/>2014年04月08日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td style="font-size:16px;text-align:left"><br/>服务站代码：0634425</td>
					<td style="font-size:16px;text-align:right"><br/>莱芜解放汽车销售服务有限公司</td>
				</tr>		
			</table>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doPrint()" id="print">打&nbsp;&nbsp;印</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
</div>
</body>
</html>