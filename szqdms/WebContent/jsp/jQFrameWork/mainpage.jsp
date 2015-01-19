<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String  contextPath = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/head.jsp" />
<link href="<%=contextPath %>/css/mainmap.css" rel="stylesheet" type="text/css" />
<script src="<%=contextPath %>/lib/plugins/map/raphael.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/map/map.js" type="text/javascript"></script>
<script type="text/javascript">
var searchMsgUrl = "<%=contextPath%>/AlertInfoAction/searchTaskAndMsg.ajax";
$(function(){
	$("#home-panel").show();
	//查询待办和消息
	sendPost(searchMsgUrl,"","",searchTaskCallBack,false);
	$("div",$("#textProv")).click(function(){
		var c = $(this).attr("name");
		aus[c][0].onclick();
	});
});
function searchTaskCallBack(res)
{
	//生成待办提醒列表
	//生成消息提醒列表
	var rows = res.getElementsByTagName("ROW");
	if(rows && rows.length > 0)
	{
		var tasks = false,msgs = false;
		var $taskUl = $("<ul></ul>");
		var $msgUl = $("<ul></ul>");
		for(var i=0;i<rows.length;i++)
		{
			var alertType = getNodeText(rows[i].getElementsByTagName("ALERT_TYPE").item(0));
			var typeValue = rows[i].getElementsByTagName("ALERT_TYPE").item(0).getAttribute("sv");
			var createTime = rows[i].getElementsByTagName("CREATE_TIME").item(0).getAttribute("sv");
			var title = getNodeText(rows[i].getElementsByTagName("ALERT_TITLE").item(0));
			var desr = getNodeText(rows[i].getElementsByTagName("DESR").item(0));
			var xh = getNodeText(rows[i].getElementsByTagName("XH").item(0));
			if(alertType == "101401") //待办
			{
				var $li = $("<li style='padding-bottom:5px;'></li>");
				var $div0 = $("<div></div>");
				var $a = $("<a href='javascript:void(0);' ref_desc='"+desr+"' ref_typevalue='"+typeValue+"' ref_type='"+alertType+"' ref_xh='"+xh+"'  onclick='parent.doLinkMsg(this);'></a>");				
				$a.attr("title",title);
				$a.text(title);
				var $div = $("<div style='margin:5px;'></div>");
				$div.html(createTime);
				$div0.append($a.wrap("<div></div>"))
				$div0.append($div);
				$li.append($div0);
				$taskUl.append($li);
				if(tasks == false)
					tasks = true;
			}
			else	//消息
			{
				var $li = $("<li style='padding-bottom:5px;'></li>");
				var $div0 = $("<div></div>");
				var $a = $("<a href='javascript:void(0);' ref_desc='"+desr+"' ref_typevalue='"+typeValue+"' ref_type='"+alertType+"' ref_xh='"+xh+"'  onclick='parent.doLinkMsg(this);'></a>");				
				$a.attr("title",title);
				$a.text(title);
				var $div = $("<div style='margin:5px;'></div>");
				$div.html(createTime);
				$div0.append($a.wrap("<div></div>"))
				$div0.append($div);
				$li.append($div0);
				$msgUl.append($li);
				if(msgs == false)
					tasks = true;
			}
		}
		if(tasks == true)
			$("#div-taskList").append($taskUl);
		else
			$("#div-taskList").html("暂无待办任务");
		if(tasks == true)
			$("#div-msgList").append($msgUl);
		else
			$("#div-msgList").html("暂无消息提醒");
		//alert($("#div-taskList").html());
	}else
	{
		$("#div-taskList").html("暂无待办任务");
		$("#div-msgList").html("暂无消息提醒");
		
	}
	loadMap();
}
function initStyle(){
}
</script>
</head>
<body>
<div style="width:100%;height:100%;">
	<div class="page" >
		<div class="pageContent" layoutH="400">
		<div id="home-panel" style="display:none;width:25%;height:400px;float:left;">
			<div class="panel" defH="210"  style="width:100%;float:left;display:block;text-align:left;">
				<h1><img src="<%=contextPath %>/images/default/db.png"></img>待办事项</h1>
					<div id="div-taskList" class="listpageright">   
	       			</div>
			</div>
			<div class="panel" defH="200" style="width:100%;float:left;display:block;text-align:left;">
				<h1><img src="<%=contextPath %>/images/default/xxtx.png">消息提醒</img></h1>
				<div id= "div-msgList" class="listpageright"></div>
			</div>
		</div>
		<div id="home-map" style="width:auto%;float:left;display:block;margin:10px;">
			<div id="canvas">
	            <div id="paper"></div>
	            <div id="textProv">
	                <div class="mapsHeilj" name="heilongjiang">黑龙江</div>
	                <div class="mapsJil" name="jilin">吉林</div>
	                <div class="mapsLiaon" name="liaoning">辽宁</div>
	                <div class="beij" name="beijing">北京</div>
	                <div class="tianj" name="tianjin">天津</div>
	                <div class="hebei" name="hebei">河北</div>
	                <div class="shanx" name="shanxi">山西</div>
	                <div class="neimg" name="neimenggu">内蒙古</div>
	                <div class="shand" name="shandong">山东</div>
	                <div class="jiangs" name="jiangsu">江苏</div>
	                <div class="anh" name="anhui">安徽</div>
	                <div class="shangh" name="shanghai">上海</div>
	                <div class="zhej" name="zhejiang">浙江</div>
	                <div class="jiangx" name="jiangxi">江西</div>
	                <div class="fuj" name="fujian">福建</div>
	                <div class="taiw" name="taiwan">台湾</div>
	                <div class="guangd" name="guangdong">广东</div>
	                <div class="guangx" name="guangxi">广西</div>
	                <div class="hain" name="hainan">海南</div>
	                <div class="xinj" name="xinjiang">新疆</div>
	                <div class="gans" name="gansu">甘肃</div>
	                <div class="ningx" name="ningxia">宁夏</div>
	                <div class="qingh" name="qinghai">青海</div>
	                <div class="shan3x" name="shan3i">陕西</div>
	                <div class="chongq" name="chongqing">重庆</div>
	                <div class="sic" name="sichuan">四川</div>
	                <div class="guiz" name="guizhou">贵州</div>
	                <div class="yunn" name="yunnan">云南</div>
	                <div class="xiz" name="xizang">西藏</div>
	                <div class="hen" name="henan">河南</div>
	                <div class="hub" name="hubei">湖北</div>
	                <div class="hun" name="hunan">湖南</div> 
				</div>
	            <!-- start 东北 -->
	            <div id="heilongjiang">
	                <h2>黑龙江</h2>
	                <p>黑龙江</p>
	            </div>
	            <div id="jilin">
	                <h2>吉林区</h2>
	                <p>共有<a>100</a>家渠道商<br></br>
					         上月整车月销售：600辆<br></br>
					         上月配件月销售金额：1200万元<br></br>
					         上月售后服务金额：320万元<br></br>
					</p>
	            </div>
	            <div id="liaoning">
	                <h2>辽宁</h2>
	                <p>辽宁</p>
	            </div>
	            <!-- end 东北 -->
	            <!-- start 华北地区 -->
	            <div id="beijing">
	                <h2>北京</h2>
	                <p>北京</p>
	            </div>
	            <div id="tianjin">
	                <h2>天津</h2>
	                <p>天津</p>
	            </div>
	            <div id="hebei">
	                <h2>河北</h2>
	                <p>河北</p>
	            </div>
	            <div id="shanxi">
	                <h2>山西</h2>
	                <p>山西</p>
	            </div>
	            <div id="neimenggu">
	                <h2>内蒙古</h2>
	                <p>内蒙古</p>
	            </div>
	            <!-- end 华北地区 -->
	            <!-- start 华东地区 -->
	            <div id="shandong">
	                <h2>山东</h2>
	                <p>山东</p>
	            </div>
	            <div id="jiangsu">
	                <h2>江苏</h2>
	                <p>江苏</p>
	            </div>
	            <div id="anhui">
	                <h2>安徽</h2>
	                <p>安徽</p>
	            </div>
	            <div id="shanghai">
	                <h2>上海</h2>
	                <p>上海</p>
	            </div>
	            <div id="zhejiang">
	                <h2>浙江</h2>
	                <p>浙江</p>
	            </div>
	            <div id="jiangxi">
	                <h2>江西</h2>
	                <p>江西</p>
	            </div>
	            <div id="fujian">
	                <h2>福建</h2>
	                <p>福建</p>
	            </div>
	            <div id="taiwan">
	                <h2>台湾</h2>
	                <p>台湾</p>
	            </div>
	            <!-- end 华东地区 -->
	            <!-- start 华南地区 -->
	            <div id="guangdong">
	                <h2>广东</h2>
	                <p>广东</p>
	            </div>
	            <div id="guangxi">
	                <h2>广西</h2>
	                <p>广西</p>
	            </div>
	            <!-- <div id="xianggang">
	                <h2>香港</h2>
	                <p>香港</p>
	            </div>
	            <div id="aomen">
	                <h2>澳门</h2>
	                <p>澳门</p>
	            </div> -->
	            <div id="hainan">
	                <h2>海南</h2>
	                <p>海南</p>
	            </div>
	            <!-- end 华南地区 -->
	            <!-- start 西北地区 -->
	            <div id="xinjiang">
	                <h2>新疆</h2>
	                <p>新疆</p>
	            </div>
	            <div id="gansu">
	                <h2>甘肃</h2>
	                <p>甘肃</p>
	            </div>
	            <div id="ningxia">
	                <h2>宁夏</h2>
	                <p>宁夏</p>
	            </div>
	            <div id="qinghai">
	                <h2>青海</h2>
	                <p>青海</p>
	            </div>
	            <div id="shan3i">
	                <h2>陕西</h2>
	                <p>共有<a>100</a>家渠道商<br></br>
					         上月整车月销售：600辆<br></br>
					         上月配件月销售金额：1200万元<br></br>
					         上月售后服务金额：320万元<br></br>
					</p>
	            </div>
	            <!-- end 西北地区 -->
	            <!-- start 华西地区【西南地区】 -->
	            <div id="chongqing">
	                <h2>重庆</h2>
	                <p>重庆</p>
	            </div>
	            <div id="sichuan">
	                <h2>四川</h2>
	                <p>四川</p>
	            </div>
	            <div id="guizhou">
	                <h2>贵州</h2>
	                <p>贵州</p>
	            </div>
	            <div id="yunnan">
	                <h2>云南</h2>
	                <p>云南</p>
	            </div>
	            <div id="xizang">
	                <h2>西藏</h2>
	                <p>西藏</p>
	            </div>
	            <!-- end 华西地区【西南地区】 -->
	            <!-- start 华中地区 -->
	            <div id="henan">
	                <h2>河南</h2>
	                <p>河南</p>
	            </div>
	            <div id="hubei">
	                <h2>湖北</h2>
	                <p>湖北</p>
	            </div>
	            <div id="hunan">
	                <h2>湖南</h2>
	                <p>湖南</p>
	            </div>
	            <!-- end 华中地区 -->
	        </div>
	        </div>
	  </div>
	</div>  
</div>	        
<script type="text/javascript">
	/**
	try{
		if(!parent.can)
			parent.getMsg();
	}catch(e){}
	*/
</script>
</body>
</html>