<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:用户密码管理
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
    <title>用户密码管理</title>
    <script type="text/javascript">
        /**
         * 查询提交方法,方式为：sysmng/usermsg/UserMngAction/search.ajax
         * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
         * UserMngAction/为提交到后台的action类名
         * search为提交请求类中需要执行的方法名
         * .ajax表示请求为ajax请求
         */
        var searchUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/search.ajax";
        //初始化
        $(function()
        {
            //查询按钮响应
            $("#btn-search").bind("click", function(event){
                var $f = $("#fm-searchUser");//获取页面提交请求的form对象
                var sCondition = {};//定义json条件对象
                //combined()：实现将页面条件按规则拼接成json
                sCondition = $f.combined() || {};
                /**
                 * doFormSubmit:提交查询请求
                 * @$f:提交form表单的jquery对象
                 * @searchUrl:提交请求url路径
                 * @"search":提交查询操作按钮id
                 * @1:查询结果返回时显示第几页，默认显示第一页数据
                 * @sCondition：页面定义的查询条件（json）
                 * @"yhlb":查询返回结果显示的table表格id
                 */
                doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-userlist");
            });
        });
        //列表编辑连接
        function doView(rowobj)
        {
            $("td input:first",$(rowobj)).attr("checked",true);
            openChangePassword();
        }

    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 用户管理   &gt; 用户管理</h4>
    <div class="page" >
        <div class="pageHeader" >
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchUser">
                <!-- 定义隐藏域查询条件 -->
                <input type="hidden" id="stauts" name="status" datasource="U.STATUS" value="<%=Constant.YXBS_01%>" ></input>
                <div class="searchBar" align="left" >
                    <table class="searchContent" id="tab-searchUser">
                        <!-- 定义查询条件 -->
                        <tr><td><label>用户账号：</label></td>
                            <td><input type="text" id="account" name="account" datasource="ACCOUNT" datatype="1,is_null,30" operation="like" /></td>
                            <td><label>所属组织：</label></td>
                            <td><input type="text" id="orgCode"  name="orgCode" datasource="ORG_CODE"  kind="dic" src="ZZJG" datatype="1,is_null,30" operation="like" />
                            </td>
                        </tr>
                        <tr>
                            <td><label>姓名：</label></td>
                            <td><input type="text" id="personName" name="personName" datasource="PERSON_NAME" operation="like"  datatype="1,is_null,30" /></td>
                            <td><label >工号：</label></td>
                            <td><input type="text" id="userSn" name="userSn" datasource="USER_SN" operation="like" datatype="1,is_null,30" /></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="page_userlist" >
                <!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
                <table style="display:none;width:100%;" id="tab-userlist" name="tablist" ref="page_userlist" refQuery="tab-searchUser" >
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ACCOUNT" >用户账号</th>
                        <th fieldname="PERSON_NAME" >姓名</th>
                        <th fieldname="USER_SN">工号</th>
                        <th fieldname="SEX" >性别</th>
                        <th fieldname="CONTACT_WAY" >联系方式</th>
                        <th fieldname="MAILNAME" >邮件地址</th>
                        <th fieldname="ORG_ID" >所属组织</th>
                        <th fieldname="PERSON_KIND" >用户类型</th>
                        <th fieldname="SECRET_LEVEL" >数据密级</th>
                        <th fieldname="STATUS" >有效标识</th>
                        <th colWidth="65" type="link" title="[修改密码]"  action="doView" >操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>