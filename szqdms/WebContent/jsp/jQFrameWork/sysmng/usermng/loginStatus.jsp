<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:用户查询
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>设置登录状态</title>
<script type="text/javascript">
//查询提交方法
var url = "<%=request.getContextPath()%>/OrgPerson/OrgPersonAction.do?method=search";
$(function()
{
	$("#search").click(function(){
		var $f = $("#yhcxFm");// 固定
		var sCondition = {}; //固定
    	sCondition = $("#yhcx").combined() || {}; //固定
		doFormSubmit($f.eq(0),url,"search",1,sCondition,"yhlb");
	});
	$("#plcz").click(function(){
		var czdlztUrl = "<%=request.getContextPath()%>/FrameWork/LogAction.do?method=doResetLogStatus";
		sendPost(czdlztUrl,"doReset","","","true");
	});
});

//列表连接
function doReset(rowobj)
{
	$("td:first input[type=radio]",$(rowobj)).attr("checked",true);
	var czdlztUrl = "<%=request.getContextPath()%>/FrameWork/LogAction.do?method=doResetLogStatus&userid="+$(rowobj).attr("ACCOUNT");
	sendPost(czdlztUrl,"doReset","","","true");
}

//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var $tr= $(checkbox).parent().parent().parent();
	var account=$tr.attr("ACCOUNT");
	var yhmc = $tr.attr("PERSON_NAME");
	arr.push(account);
	arr.push(yhmc);
	multiSelected($checkbox,arr);
}
</script>
</head>
<body>
<div id="layout" width="100%">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 用户管理   &gt; 用户查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="yhcxFm">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="yhcx">
			<tr><td><label>用户账号：</label></td>
			    <td><input type="text" id="yhzh" name="yhzh" datasource="ACCOUNT" datatype="1,is_null,30" operation="like" value=""/></td>
				<td><label>姓名：</label></td>
			    <td><input type="text" id="yhmc" name="yhmc" datasource="PERSON_NAME" operation="like" datatype="1,is_null,30" value=""/></td>
			</tr>
			<tr>
			    <td><label>所属组织：</label></td>
			    <td><input type="text" id="szbm"  name="szbm" datasource="DEPARTMENT" kind="dic" src="ZZJG" datatype="1,is_null,30" operation="like" value=""/>
			    </td>
			</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="plcz">批量重置</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none" width="100%"  id="yhlb" name="tablist" ref="page_yhlb" refQuery="yhcx" >
					<thead>
						<tr>
							<th type="multi" name="XH" style="display:" unique="ACCOUNT"></th>
							<th fieldname="ACCOUNT">用户账号</th>
							<th fieldname="PERSON_NAME">姓名</th>
							<th fieldname="SEX" >性别</th>
							<th fieldname="IDCARD" >身份证号</th>
							<th fieldname="CONTACT_WAY" >联系方式</th>
							<th fieldname="MAILNAME" >邮件地址</th>
							<th fieldname="DEPARTMENT" >所属部门</th>
							<th fieldname="PERSON_KIND" >用户类型</th>
							<th fieldname="SECRET_LEVEL" >数据密级</th>
							<th fieldname="FLAG" >有效标识</th>
							<th type="link" colwidth="80" title="[重置登录状态]" action="doReset" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table style="">
				<tr><td>
					<textarea id="val0" name="multivals" style="width:400px;height:20px" column="1" style="display:" ></textarea>
					<textarea id="val1" name="multivals" style="width:400px;height:20px"  style="display:" ></textarea>
				</td></tr>
		</table>
	</div>
	</div>
</div>	
</body>
</html>