<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchArea">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchArea">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>办事处代码：</label></td>
                            <td><input type="text" id="dia-CODE" name="dia-CODE" datasource="CODE"
                                       datatype="1,is_null,30" operation="like"/></td>
                            <td><label>办事处名称：</label></td>
                            <td><input type="text" id="dia-ONAME" name="dia-ONAME" datasource="ONAME"
                                       datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchArea">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-confirmArea">确&nbsp;&nbsp;定</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeArea">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-areaList">
                <table style="display:none;width:100%;" id="tab-areaList" name="tablist" ref="div-areaList"
                       refQuery="tab-searchArea">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="ORG_ID" style=""></th>
                            <th fieldname="CODE">办事处代码</th>
                            <th fieldname="ONAME">办事处名称</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedArea" style="display: none">
                <legend align="left" >&nbsp;[已选定范围]</legend>
                <div>
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display: none" id="val0" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-areaInfo">
                <input type="hidden" id="promId" name="promId" datasource="PROMID"/>
                <input type="hidden" id="areaIds" name="areaIds" datasource="AREAIDS"/>
                <input type="hidden" id="areaCodes" name="areaCodes" datasource="AREACODES"/>
                <input type="hidden" id="areaNames" name="areaNames" datasource="AREANAMES"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var areaSelWin = $("body").data("areaSelWin");
    $(function () {
    	tosearchArea();
    	$("#tab-areaList").attr("layoutH",document.documentElement.clientHeight-270);
        $('#selectedArea').show();
        $("#btn-closeArea").click(function () {
            $.pdialog.close(areaSelWin);
            return false;
        });
        $("#btn-searchArea").bind("click", function (event) {
        	tosearchArea();
        });
        //确定按钮响应
        $('#btn-confirmArea').bind('click',function(){
            //添加促销范围URL
            var insertPromAreaUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction/insertPromArea.ajax";
            var areaIds = $('#val0').val();
            if(!areaIds){
                alertMsg.warn('请选择范围!')
            }else{
                $('#promId').val($('#dia-PROM_ID').val());//促销活动ID
                $('#areaIds').val(areaIds);
                $('#areaCodes').val($('#val1').val());
                $('#areaNames').val($('#val2').val());

                //获取需要提交的form对象
                var $f = $("#fm-areaInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromAreaUrl, "btn-confirmArea", sCondition, insertPromAreaCallBack);
            }
        });
    });
	function tosearchArea(){
		
		$('#val0').val("");//促销活动ID
        $('#val1').val("");
        $('#val2').val("");
		
		
		//查询范围URL
        var searchAreaUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction/searchArea.ajax?promotionId="+$('#dia-PROM_ID').val();
        var $f = $("#fm-searchArea");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchAreaUrl, "btn-search", 1, sCondition, "tab-areaList");
        
	}
    //新增促销范围回调
    function insertPromAreaCallBack(){
        $.pdialog.close(areaSelWin);
        //查询促销范围
        searchPromArea();
    }
    //列表复选
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        arr.push($tr.attr("ORG_ID"));
        arr.push($tr.attr("CODE"));
        arr.push($tr.attr("ONAME"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");
        multiSelected($checkbox, arr);
    }
</script>