<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String  contextPath = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=contextPath %>/css/mainmap.css" rel="stylesheet" type="text/css" />
<script src="<%=contextPath %>/lib/jquery/jquery.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/map/raphael.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/map/map.js" type="text/javascript"></script>
<title></title>
</head>
<body>
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
        <p>共有<a>1125</a>家服务站，销售任务完成30%<br></br>上月销售量:4000万</p>
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
        <p>陕西</p>
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
<script type="text/javascript" defer="defer">
loadMap();
$("div",$("#textProv")).click(function(){
	var c = $(this).attr("name");
	aus[c][0].onclick();
});
</script>
</body>
</html>