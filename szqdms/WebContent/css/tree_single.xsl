<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:msxsl="urn:schemas-microsoft-com:xslt"
		xmlns:user="http://www.andy.ten.com/andyten">
<xsl:output method="html" indent="yes"/>
<msxsl:script language="JScript" implements-prefix="user">
<![CDATA[
function HasChild(R)
{
	if (R.length>0)
	{
		return true;
	}
	else{
		return false;
	}
}

function getNodeDeep(R)
{
	var p = R.nextNode();
	var i = 0;
	while(true)
	{
		if(p.parentNode)
		{
			p = p.parentNode;
			++i;
		}
		else
			break;
	}
	return i;
}

function getPrefixImg(R1,root)
{
	var str = "";
	var n = R1.nextNode();
	if(n.getAttribute('l') == '1')
	{
		if(n.nextSibling)
			str += "<img src='/szqdms/images/trees/T.gif' align='absbottom' type='tree'/>";
		else
			str += "<img src='/szqdms/images/trees/L.gif' align='absbottom' type='tree'/>";
	}
	else
	{
		var exp = n.getAttribute('e');
		if(exp != '2' && exp != '3')
			if(n.nextSibling)
				str += "<img src='/szqdms/images/trees/Tplus.gif' onClick='g_xDic.lineClick(this);' align='absbottom' type='tree'/>";
			else
				str += "<img src='/szqdms/images/trees/Lplus.gif' onClick='g_xDic.lineClick(this);' align='absbottom' type='tree'/>";
		else
		{
			if(n.nextSibling)
				str += "<img src='/szqdms/images/trees/Tminus.gif' onClick='g_xDic.lineClick(this);' align='absbottom' type='tree'/>";
			else
				str += "<img src='/szqdms/images/trees/Lminus.gif' onClick='g_xDic.lineClick(this);' align='absbottom' type='tree'/>";
		}		
	}
	
	var p = n;
	var parr = [];
	while(true)
	{
		if(p.parentNode)
		{
			p = p.parentNode;
			parr[parr.length] = p;
		}
		else
			break;
	}
	
	var rootnode = root.nextNode();
	var prenum = rootnode.getAttribute("prefix");
	var prestr = rootnode.getAttribute("prefixstr");
	if(prenum)
	{
		prenum = prenum - 0;
	}
	else
	{
		prenum = 0;
	}
	
	for(var i=0;i<parr.length-2;i++)
	{
		if(parr[i].nextSibling)
			str = "<img src='/szqdms/images/trees/I.gif' align='absbottom' type='tree'/>" + str;
		else 
			str = "<img src='/szqdms/images/trees/blank.gif' align='absbottom' type='tree'/>" + str;
	}
	
/*	for(var i=0;i<prenum;i++)
	{
		if(i==prenum-1)
			str = "<img src='/szqdms/images/trees/I.gif' align='absbottom' type='tree'/>" + str;
		else
			str = "<img src='/szqdms/images/trees/blank.gif' align='absbottom' type='tree'/>" + str;
	}
*/
	if(!prestr) prestr = "";
	return prestr + str;
}

function getChildCount(R)
{
	return R.length;
}

function getChildSelectedCount(R)
{
	var count = 0;
	for(var i=0;i<R.length;i++)
	{
		var s = R[i].getAttribute("e");
		if(s == 3)
			count++;
	}
	return count;
}

//]]>
</msxsl:script>

<xsl:param name="HasImage">true</xsl:param>
<xsl:param name="HasCode">false</xsl:param>
<xsl:param name="Expand">false</xsl:param>

<xsl:template match="/">
	<DIV class="treeyangshi" align="left">
		<xsl:apply-templates select="/DATA/R"/>
	</DIV>
</xsl:template>

<xsl:template match="R">
	<xsl:variable name="exp" select="@e"/>
	<xsl:variable name="leaf" select="@l"/>
	<!--<xsl:variable name="deep" select="user:getNodeDeep(.)"/>-->
	<div style="height:18px;"><xsl:attribute name="id">p<xsl:value-of select="@c"/></xsl:attribute>
		<xsl:attribute name="title"><xsl:value-of select="@c"/></xsl:attribute>
		<xsl:attribute name="exp"><xsl:value-of select="@e"/></xsl:attribute>
		<xsl:attribute name="leaf"><xsl:value-of select="@l"/></xsl:attribute>
		<xsl:attribute name="pid"><xsl:value-of select="@p"/></xsl:attribute>
		<xsl:attribute name="cn"><xsl:value-of select="user:getChildCount(./R)"/></xsl:attribute>
		<xsl:attribute name="csn"><xsl:value-of select="user:getChildSelectedCount(./R)"/></xsl:attribute>
		<xsl:value-of select="user:getPrefixImg(.,/DATA)"/>
		<xsl:if test="$HasImage='true'">
			<xsl:choose>
				<xsl:when test="$leaf='1'"></xsl:when>
			</xsl:choose>
		</xsl:if>
		
		<xsl:choose>
			<xsl:when test="$exp='1'">
				<INPUT type="radio" align="middle" style="width:14px;" id="radio1" name="radios" onClick='g_xDic.radioClick(this);'/>
			</xsl:when>
			<xsl:when test="$exp='2'">
				<INPUT type="radio" align="middle" style="width:14px;" id="radio2" name="radios" onClick='g_xDic.radioClick(this);'/>
			</xsl:when>
			<xsl:when test="$exp='3'">
				<INPUT type="radio" align="middle" style="width:14px;" id="radio3" name="radios" onClick='g_xDic.radioClick(this);'/>
			</xsl:when>
			<xsl:otherwise>
				<INPUT type="radio" align="middle" style="width:14px;" id="radio4" name="radios" onClick='g_xDic.radioClick(this);'/>
			</xsl:otherwise>
		</xsl:choose>
		<a href="JavaScript:void(0);" onClick="g_xDic.itemClick(this);">
			<xsl:value-of select="@t"/>
			<xsl:if test="$HasCode='true'">
				[<xsl:value-of select="@c"/>]
			</xsl:if>
		</a>
	</div>
	<xsl:if test="user:HasChild(./R)">
		<div><xsl:attribute name="id">c<xsl:value-of select="@c"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="$exp='1'">
					<xsl:attribute name="style">display:none;</xsl:attribute>
				</xsl:when>
				<xsl:when test="$exp='2'">
					<xsl:attribute name="style">display:;</xsl:attribute>
				</xsl:when>
				<xsl:when test="$exp='3'">
					<xsl:attribute name="style">display:;</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="style">display:none;</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="$Expand='true'">
				<xsl:attribute name="fill">true</xsl:attribute>
				<xsl:apply-templates select="./R"/>
			</xsl:if>
			<xsl:if test="$Expand!='true'">
				<xsl:choose>
					<xsl:when test="$exp='2'">
						<xsl:attribute name="fill">true</xsl:attribute>
						<xsl:apply-templates select="./R"/>
					</xsl:when>
					<xsl:when test="$exp='3'">
						<xsl:attribute name="fill">true</xsl:attribute>
						<xsl:apply-templates select="./R"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="fill">false</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</div>
	</xsl:if>
</xsl:template>

</xsl:stylesheet>