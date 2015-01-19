<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchNotice">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchNotice">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>用户帐号：</label></td>
                            <td><input type="text" id="dia-ACCOUNT" name="dia-ACCOUNT" datasource="ACCOUNT" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>用户名称：</label></td>
                            <td><input type="text" id="dia-person_name" name="dia-person_name" datasource="PERSON_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchUser">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-addUser">确&nbsp;&nbsp;定</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeUser">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-userList">
                <table style="display:none;width:100%;" multivals="userVals" id="tab-userList" name="tab-userList" ref="div-userList"
                       refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="USER_ID" style=""></th>
                            <th fieldname="ACCOUNT">用户帐号</th>
                            <th fieldname="USER_ID" style="display:none">用户ID</th>
                            <th fieldname="PERSON_NAME">用户名称</th>
                            <th fieldname="ONAME">所属部门</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedUsers" style="display:none">
                <legend align="left" >&nbsp;[已选定用户]</legend>
                <div id="userVals">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:400px;height:10px; display:none" id="user-val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="user-val1" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-userInfo">
                <input type="hidden" id="userId" name="userId" datasource="USER_ID"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction";
function diaInsertCallBack(res)
{
	try
	{
		//刷新查询页面数据
		var $f = $("#fm-noticeUser");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchNoticeUser.ajax?&typeId="+$("#typeId").val();
		doFormSubmit($f,searchUrl,"searchUser",1,sCondition,"noticeUserlb");
		//刷新新增页面数据
		var $f = $("#fm-searchNotice");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchUsers.ajax?&typeId="+$("#typeId").val();
		doFormSubmit($f,searchUrl,"btn-searchUser",1,sCondition,"tab-userList");
		$("#user-val0").val("");
		$("#user-val1").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
//弹出窗体
var dialog = $("body").data("noticeManageUserAdd");
$(function()
{
	//查询按钮响应
	$("#btn-searchUser").bind("click", function(event){
		var $f = $("#fm-searchNotice");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchUsers.ajax?&typeId="+$("#typeId").val();
		doFormSubmit($f,searchUrl,"btn-searchUser",1,sCondition,"tab-userList");
		$('#selectedUsers').show();
	});
	
	$("#btn-closeUser").bind("click",function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#btn-addUser").bind("click", function(event)
			{
				var mxids=$("#user-val0").val();
			    if(mxids=="")
			    {
			    	alertMsg.warn("请选择用户！");
			    	return true;
			    }else{
	                $('#userId').val(mxids);
	                var $f = $("#fm-userInfo");
	                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
	                if (submitForm($f) == false) return false;
	                var sCondition = {};
	                //将需要提交的内容拼接成json
	                sCondition = $f.combined(1) || {};
	                var xzcxUrl =diaSaveAction+"/insertUsers.ajax?&typeId="+$("#typeId").val();
	                doNormalSubmit($f, xzcxUrl, "btn-addUser", sCondition, diaInsertCallBack);
			    }
			});
});
</script>