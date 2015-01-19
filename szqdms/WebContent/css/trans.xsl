<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:msxsl="urn:schemas-microsoft-com:xslt"
		xmlns:user="http://www.andy.ten.com/andyten">
<xsl:output method="html" indent="yes"/>

<msxsl:script language="JScript" implements-prefix="user">
<![CDATA[
var sOldTypeId = "";
var sNewTypeId = "";
var iStart = 0;

function SetStartFlag(iFlag){
	iStart = iFlag;
	return iStart;
}

function GetStartFlag(){
	return iStart;
}

function GetByUnitTitle(CJDWDM,CJ2){
	var sTitle = CJDWDM.nextNode().getAttribute("sv");
	var sText = CJ2.nextNode().text;
	return sTitle + " ["+sText+"]";
}

function GetByEventTitle(YWLX, RWLX){
	//var sTitle = YWLX.nextNode().getAttribute("sv") + "-" + RWLX.nextNode().getAttribute("sv");
	var sTitle = RWLX.nextNode().getAttribute("sv");
	return sTitle;
}
function GetByYwlxTitle(YWLX){
	var sTitle = YWLX.nextNode().text;//getAttribute("sv");
	return sTitle;
}

function GetByTimeTitle(CJSJ){
	var sDate = CJSJ.nextNode().text;
	var d = new Date(sDate.substring(0,4)+"/"
		+sDate.substr(4,2)+"/"
		+sDate.substr(6,2)+" "
		+sDate.substr(8,2)+":"
		+sDate.substr(10,2)+":"
		+sDate.substr(12,2));//new Date(sDate.replace(/-/g,"/"));
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var sTitle = year + "年" + month + "月" + day + "日 " + GetWeek(d);
	var today = new Date();
	if(today.getFullYear()==year
		&&today.getMonth()==month-1
		&&today.getDate()==day){
		sTitle = "今日的任务 (" + sTitle + ")";
	}
	return sTitle;
}

function GetTypeIdByUnit(CJDWDM){
	sNewTypeId = CJDWDM.nextNode().text;
	return sNewTypeId;
}

function GetTypeIdByYwlx(YWLX){
	sNewTypeId = YWLX.nextNode().text;
	return sNewTypeId;
}

function GetTypeIdByEvent(RWLX,YWLX){
	//sNewTypeId = YWLX.nextNode().text + "-" + RWLX.nextNode().text;
	sNewTypeId = RWLX.nextNode().text;
	return sNewTypeId;
}

function GetTypeIdByTime(CJSJ){
	var s = CJSJ.nextNode().text;
	s = s.substring(0,8);//s.substring(0, s.indexOf(" "));
	sNewTypeId = s;
	return sNewTypeId;
}

function IsNewTypeId()
{
	if (sNewTypeId != sOldTypeId){
		sOldTypeId = sNewTypeId;
		return true;
	}
	else{
		return false;
	}
}

function TestId(OrderBy, CJSJ, YWLX,RWLX, CJDWDM){
	var s;
	var sOrder = OrderBy.nextNode().text
	if (sOrder == "CJSJ"){
		s = CJSJ.nextNode().text;
		s = s.substring(0,8);//s.substring(0, s.indexOf(" "));
	}
	else if (sOrder == "CJDWDM"){
		s = CJDWDM.nextNode().text;
	}
	else if (sOrder == "YWLX"){
		s = YWLX.nextNode().text;
	}
	else {
		//s = YWLX.nextNode().text + "-" + RWLX.nextNode().text;
		s = RWLX.nextNode().text;
	}
	return s==sNewTypeId;
}

function GetDateDiff(sDate)
{
	var msel = 1000 * 60 * 60 * 24;
	var t1 = Date.parse(new Date());
	var t2 = Date.parse(sDate.substring(0,4)+"/"
		+sDate.substr(4,2)+"/"
		+sDate.substr(6,2)+" "
		+sDate.substr(8,2)+":"
		+sDate.substr(10,2)+":"
		+sDate.substr(12,2));//  sDate.replace(/-/g,"/"));

	t1 = Math.floor(Math.abs(t1 / msel));
	t2 = Math.floor(Math.abs(t2 / msel));

	return t1 - t2;
}

function GetWaitTime(OrderBy, CJSJ){
	var sDate = CJSJ.nextNode().text;
	if (OrderBy.nextNode().text == "CJSJ"){
		return sDate.substr(8,2)+":"+sDate.substr(10,2)+":"+sDate.substr(12,2);
	}
	else {
		var sColor;
		var iDiff = GetDateDiff(sDate);
		if (iDiff < 7)
			sColor = "navy";
		else if (iDiff < 14)
			sColor = "#9900CC";
		else if (iDiff < 21)
			sColor = "#FF6600";
		else
			sColor = "red";
		var sHtml = "<span style='color:" + sColor + "'>" + iDiff + "</span>";
		return sHtml;
	}
}

function FormatDate(CJSJ){
	var sDate = CJSJ.nextNode().text;
	var d = new Date(sDate.substring(0,4)+"/"
		+sDate.substr(4,2)+"/"
		+sDate.substr(6,2)+" "
		+sDate.substr(8,2)+":"
		+sDate.substr(10,2)+":"
		+sDate.substr(12,2)); //new Date(sDate.replace(/-/g,"/"));
	var s = d.getFullYear() + "年"
		+ (d.getMonth() + 1) + "月"
		+ d.getDate() + "日"
		+ " " + GetWeek(d);
	s += "\n\t\t       "
		+ d.getHours() + "时"
		+ d.getMinutes() + "分"
		+ d.getSeconds() + "秒";
	return s;
}

function GetWeek(date){
	var x = new Array("星期日", "星期一", "星期二","星期三","星期四", "星期五","星期六");
	return x[date.getDay()];
}
//]]>
</msxsl:script>

<!--
排序字段:
	CJDWDM					来源单位
	RWLX	 待办事件
	CJSJ						操作时间(默认)
-->
<xsl:param name="OrderBy">CJSJ</xsl:param>

<xsl:template match="/">
	<xsl:apply-templates select="//RESULT"/>
</xsl:template>

<xsl:template match="RESULT">
	<xsl:apply-templates select="ROW">
		<xsl:sort select="$OrderBy" data-type="text"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template match="ROW">
	<xsl:variable name="TypeId">
		<xsl:choose>
			<xsl:when test="$OrderBy='CJDWDM'">
				<xsl:value-of select="user:GetTypeIdByUnit(CJDWDM)"/>
			</xsl:when>
			<xsl:when test="$OrderBy='RWLX'">
				<xsl:value-of select="user:GetTypeIdByEvent(RWLX,YWLX)"/>
			</xsl:when>
			<xsl:when test="$OrderBy='YWLX'">
				<xsl:value-of select="user:GetTypeIdByYwlx(YWLX)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="user:GetTypeIdByTime(CJSJ)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="TaskTitle" select="MEMO"/>
	<xsl:variable name="ByTimeTitle" select="user:GetByTimeTitle(CJSJ)"/>
	<xsl:variable name="ByCJDWDMTitle" select="user:GetByUnitTitle(CJDWDM,CJDWDM)"/>
	<xsl:variable name="ByEventTitle" select="user:GetByEventTitle(YWLX, RWLX)"/>
	<xsl:variable name="ByYwlxTitle" select="user:GetByYwlxTitle(YWLX)"/>
	<xsl:if test="user:IsNewTypeId()">
		<xsl:variable name="tmp" select="user:SetStartFlag(1)"/>
		<div class="takListH">
			<table cellpadding="0" cellspacing="0">
				<tr onclick="doStretch()">
					<td class="Title">
						<xsl:choose>
							<xsl:when test="$OrderBy='CJDWDM'">
								<xsl:value-of select="$ByCJDWDMTitle"/>
							</xsl:when>
							<xsl:when test="$OrderBy='RWLX'">
								<xsl:value-of select="$ByEventTitle"/>
							</xsl:when>
							<xsl:when test="$OrderBy='YWLX'">
								<xsl:value-of select="$ByYwlxTitle"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$ByTimeTitle"/>
							</xsl:otherwise>
						</xsl:choose>
					</td>
					<td width="25">
						<xsl:choose>
							<xsl:when test="position()=1"><img src="/szqdms/images/default/vdown.gif" type="tree"/></xsl:when>
							<xsl:otherwise><img src="/szqdms/images/default/vup.gif" type="tree"/></xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
			</table>
		</div>
	</xsl:if>

	<xsl:if test="user:GetStartFlag()">
		<xsl:variable name="tmp" select="user:SetStartFlag(0)"/>
		<div class="takList">
			<table cellpadding="0" cellspacing="0" width="100%">
				<xsl:if test="position()>1">
					<xsl:attribute name="style">display:none;</xsl:attribute>
				</xsl:if>
				<xsl:for-each select="../ROW">
					<xsl:if test="user:TestId($OrderBy, CJSJ, YWLX, RWLX,CJDWDM)">
						<tr onmouseover="list_MsOver()" onmouseout="list_MsOut()" onclick="doTask('{ID}','{SEQ}','{SJBH}','{RWLX}','{SPBH}','{YWLX}','{LINKURL}')">
							<xsl:attribute name="title">
							<xsl:value-of select="MEMO"/>
	-->> 用户名称: <xsl:value-of select="CJRXM"/>
	-->> 操作单位: <xsl:value-of select="CJDWDM"/>
	-->> 操作时间: <xsl:value-of select="user:FormatDate(CJSJ)"/></xsl:attribute>
							<td class="Title"><span class="Text"><xsl:value-of select="MEMO"/></span></td>
							<td class="Separat">|</td>
							<td width="80" class="Title"><span class="Text"><xsl:value-of select="YWLX/@sv"/></span></td>
							<td class="Separat">|</td>
							<td width="120" class="Title"><span class="Text"><xsl:value-of select="CJDWDM/@sv"/></span></td>
							<td class="Separat">|</td>
							<td width="80" class="Title"><span class="Text"><xsl:value-of select="RWLX/@sv"/></span></td>
							<td class="Separat">|</td>
							<td width="80" class="Title"><span class="Text"><xsl:value-of select="CJRXM"/></span></td>
							<td class="Separat">|</td>
							<td width="68" class="Title" align="center"><span class="Text"><xsl:value-of disable-output-escaping="yes" select="user:GetWaitTime($OrderBy, CJSJ)"/></span></td>
						</tr>
					</xsl:if>
				</xsl:for-each>
			</table>
			<div class="Divide"></div>
		</div>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>