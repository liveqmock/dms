<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="dia_fm_searchUser">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="dia_searchUserTable">
                        <tr>
                        	<td><label>用户账号：</label></td>
					    	<td><input type="text" id="account" name="account" datasource="ACCOUNT" datatype="1,is_null,30" operation="like" /></td>
                        	<td><label>所属组织：</label></td>
					    	<td><input type="text" id="orgCode"  name="orgCode" datasource="ORG_ID" dicwidth="300"  kind="dic" src="ZZJG" datatype="1,is_null,30" operation="like" /></td>
                        </tr>
                        <tr>
                            <td><label>用户名称：</label></td>
                            <td><input type="text" id="dia_personName" name="dia_personName" datasource="PERSON_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        
                    </table>
                    <div class="subBar">
                        <ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia_searchUser" >查&nbsp;&nbsp;询</button></div></div></li>
						 	<li><div class="button"><div class="buttonContent"><button type="button" id="dia_confirmUser">确&nbsp;&nbsp;定</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="dia_user">
                <table style="display:none;width:100%;" id="dia_UserList" name="dia_UserList" ref="dia_user" refQuery="dia_searchUserTable">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="ACCOUNT" style=""></th>
                            <th fieldname="ACCOUNT">用户账号</th>
                            <th fieldname="PERSON_NAME">用户名称</th>
                            <th fieldname="ORG_CODE">所属组织</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
         <div id="dia_userInfo" style="display:none">
             <table style="width:100%;">
                 <tr>
                     <td>
                         <textarea style="width:80%;height:26px;display:" id="val0" name="multivals" column="1" readOnly></textarea>
                         <textarea style="width:80%;height:26px;display:" id="val1" name="multivals" readOnly></textarea>
                     </td>
                 </tr>
             </table>
        </div>
        <form method="post" id="diaUserInfoFromH" style="display:none">
        	<input type="text" id="diaAccount" datasource="ACCOUNT"></input>
        	<input type="text" id="diaUserName" datasource="USER_NAME"></input>
        </form>
    </div>
<script type="text/javascript">
var diaUrl = "<%=request.getContextPath()%>/service/claimmng/CheckUserAction";
$(function(){
	searchUser();
	$("#dia_searchUser").bind("click",function(){
		searchUser();
	});
	//确定
	$("#dia_confirmUser").bind("click",function(){
		var accounts = $('#val0').val();
        if(!accounts){
            alertMsg.warn('请选择用户信息!');
        }else{
        	$("#diaAccount").val($("#val0").val());
        	$("#diaUserName").val($("#val1").val());
        	var $f = $("#diaUserInfoFromH");
	        if (submitForm($f) == false) return false;
	        var sCondition = {};
	        sCondition = $f.combined(1) || {};
	        var url =diaUrl+"/checkUserSave.ajax";
	        doNormalSubmit($f, url, "dia_confirmUser", sCondition, saveCallBack);
        }
	});
	
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
});
function searchUser()
{
	$("#val0").val("");
	var $f = $("#dia_fm_searchUser");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var diaUrl1 =diaUrl+"/searchUser.ajax"; 
	doFormSubmit($f,diaUrl1,"",1,sCondition,"dia_UserList");
}
//复选
function doCheckbox(checkbox) {
	var $t=$(checkbox);
	while($t[0].tagName != "TABLE")
    {
		$t = $t.parent();
    }
	if($t.attr("id").indexOf("dia_UserList")==0){
		var $tr = $(checkbox).parent().parent().parent();
	    var arr = [];
	    arr.push($tr.attr("ACCOUNT"));
	    arr.push($tr.attr("PERSON_NAME"));
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    multiSelected($checkbox, arr,$("#dia_userInfo"));
	}
}
//保存回调
function saveCallBack(res){
	try{
		$("#search").trigger("click");
		searchUser();
		$("#val0").val("");
		$("#val1").val("");
	}catch(e){
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</div>
