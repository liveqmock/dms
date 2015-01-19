<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>退件申请查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 退货管理   &gt; 退件申请查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>退件单号：</label></td>
                        <td><input type="text" id="returnNo-index" name="returnNo-index" datasource="RETURN_NO" operation="like" /></td>
                        <td><label>接收方：</label></td>
                        <td>
							<input type="text" id="returnCode-index" name="returnCode-index" datasource="RECEIVE_ORG_CODE" datatype="1,is_digit_letter_cn,30" 
								   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100110, 100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
								   operation="=" isreload="true" kind="dic"
							/>
                        </td>
                        <td><label>退件申请状态：</label></td>
                        <td>
							<select class="combox" name="APPLY_SATUS" id="APPLY_SATUS" datasource="APPLY_SATUS"  kind="dic" 
									src="<%=DicConstant.TJSQDZT%>" operation="=" datatype="1,is_null,6" 
									filtercode="<%=DicConstant.TJSQDZT_02%>|<%=DicConstant.TJSQDZT_03%>|<%=DicConstant.TJSQDZT_04%>|<%=DicConstant.TJSQDZT_05%>|<%=DicConstant.TJSQDZT_06%>"
									>
								<option value="-1" selected="selected">--</option>
							</select>
                        </td>
                    </tr>
                    <tr>
						<td><label>渠道商代码：</label></td>
						<td>										
							<input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="APPLY_ORG_CODE"  datatype="1,is_null,100" operation="=" readonly="readonly" hasBtn="true" callFunction="open();"/>
						</td>
						<td><label>渠道商名称：</label></td>
						<td>										
							<input type="text" id="ORG_NAME" name="APPLY_ORG_NAME"  datatype="1,is_null,100" readonly="readonly" />
						</td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear-btn" >重置查询条件</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_tab_index">
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_tab_index">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none"></th>
                            <th fieldname="RETURN_NO" colwidth="150"  refer="showLink">退件单号</th>
                            <th fieldname="APPLY_DATE" colwidth="130px">申请时间</th>
                            <th fieldname="IN_DATE" colwidth="130px">入库时间</th>
                            <th fieldname="RECEIVE_ORG_NAME" >接收方</th>
                            <th fieldname="APPLY_ORG_CODE" >退件单位代码</th>
                            <th fieldname="APPLY_ORG_NAME" >退件单位名称</th>
                            <th fieldname="CHECK_REMARKS" >审核意见</th>
                            <th fieldname="APPLY_SATUS" >退件申请状态</th>
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
<script type="text/javascript">
    // 初始化方法
    $(function(){

        // 查询按钮绑定
        $("#btn-search-index").bind("click",function(event) {
            // 查询退件申请
            searchReturnPurchaseApply();
        });

        // 清除
        $("#btn-clear-btn").click(function(){
        	$("input", "#tab-ddcx").each(function(index, obj){
        		$(obj).val("");
        	});
        });
      
    });

    // 查询退件申请
    function searchReturnPurchaseApply() {
        var searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchReturnPurchaseApplyQuery.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("RETURN_ID")+") class='op'>"+$row.attr("RETURN_NO")+"</a>";
    }
    //列表编辑链接
    function openDetail(RETURN_ID) {
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/common/returnPurchaseInfoDetail.jsp?RETURN_ID="+RETURN_ID, "returnPurchaseApplyEdit", "退件申请明细", options);
    }
    
    // 渠道选择
    function open(){
    	var id="ORG_CODE";
    	/* 	showOrgTree(id,busType); */
    	/**
    	 * create by andy.ten@tom.com 2011-12-04
    	 * 弹出组织选择树
    	 * id:弹出选择树的input框id
    	 * busType:业务类型[1,2]，1表示配件2表示售后
    	 * partOrg:配件业务使用：[1,2]，1表示只显示配送中心2表示只显示服务商和经销商，不传表示全显示
    	 * multi:[1,2],1表示默认是多选，2表示是单选
    	 */
    	showOrgTree(id,1,1,2);
    	//页面实现回调方法：
    	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
    }
    function showOrgTreeCallBack(jsonObj)
    {
    	  var orgId=jsonObj.orgId;
    	  var orgCode=jsonObj.orgCode;
    	  var orgName=jsonObj.orgName;
    	  $("#ORG_CODE").val(orgCode).attr("code",orgCode); 
    	  $("#ORG_NAME").val(orgName);
    }
    
</script>