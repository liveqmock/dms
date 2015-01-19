<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="dia_di_fm_searchPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="dia_di_searchPartTable">
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia_di_partCode" name="dia_di_partCode" datasource="PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia_di_partName" name="dia_di_partName" datasource="PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia_di_searchPart" >查&nbsp;&nbsp;询</button></div></div></li>
						 	<li><div class="button"><div class="buttonContent"><button type="button" id="dia_di_confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="dia_di_Part">
                <table style="display:none;width:100%;"  id="dia_di_PartList" multivals="dia_di_partInfo" name="dia_di_PartList" ref="dia_di_Part" refQuery="dia_di_searchPartTable">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="SE_CLPRICE" align="right" refer="diaamountFormat">单价</th>
                            <th fieldname="QUANTITY" colwidth="60" refer="createInputBox" >配件数量</th>
                           </tr>
                    </thead>
                </table>
            </div>
        </div>
        <div id="dia_di_partInfo" style="display:none">
             <table style="width:100%;">
                 <tr>
                     <td>
                         <textarea style="width:80%;height:1px;display:" id="val0" name="multivals" column="1" readOnly></textarea>
                    	 <textarea style="width:80%;display:" id="val2" name="multivals" readOnly></textarea>
                    	 <textarea style="width:80%;display:" id="val3" name="multivals" readOnly></textarea>
                    	 <textarea style="width:80%;display:" id="val4" name="multivals" readOnly></textarea>
                    	 <textarea style="width:80%;display:" id="val5" name="multivals" readOnly></textarea>
                     </td>
                 </tr>
             </table>
        </div>
        <form method="post" id="diadipartInfoFromH" style="display:none">
        	<input type="text" id="diadipartId" datasource="PART_ID"></input>
        	<input type="text" id="diadipartCode" datasource="PART_CODE"></input>
        	<input type="text" id="diadipartName" datasource="PART_NAME"></input>
        	<input type="text" id="diadipartCount" datasource="PART_COUNT"></input>
        	<input type="text" id="diadipartPrice" datasource="PART_PRICE"></input>
        </form>
    </div>
<script type="text/javascript">
var diadiparturl="<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction";
$(function(){
	//初始化查询配件信息
	searchPart();
	$("#dia_di_searchPart").bind("click",function(){
		searchPart();
	});
	//确定
	$("#dia_di_confirmPart").bind("click",function(){
		var authorId=$("#authorId").val();
		var partIds = $('#val0').val();
        if(!partIds){
            alertMsg.warn('请选择预授权配件信息!');
        }else{
        	$("#diadipartId").val($("#val0").val());
        	$("#diadipartCount").val($("#val2").val());
        	$("#diadipartCode").val($("#val3").val());
        	$("#diadipartName").val($("#val4").val());
        	$("#diadipartPrice").val($("#val5").val());
        	var $f = $("#diadipartInfoFromH");
	        if (submitForm($f) == false) return false;
	        var sCondition = {};
	        sCondition = $f.combined(1) || {};
	        var url =diadiparturl+"/authPartSave.ajax?authorId="+authorId;
	        doNormalSubmit($f, url, "dia_di_confirmPart", sCondition, saveCallBack);
        }
	});
});
//查询配件信息
function searchPart(){
	var $f = $("#dia_di_fm_searchPart");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var diadiparturl1 =diadiparturl+"/searchPart.ajax?authorId="+$("#authorId").val(); 
	doFormSubmit($f,diadiparturl1,"",1,sCondition,"dia_di_PartList");
}
//金额格式化
function diaamountFormat(obj){
  return amountFormatNew($(obj).html());
}
//保存回调
function saveCallBack(res){
	try{
		searchAuthPart();
		searchPart();
		$("#val0").val("");
		$("#val2").val("");
		$("#val3").val("");
		$("#val4").val("");
		$("#val5").val("");
	}catch(e){
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</div>
