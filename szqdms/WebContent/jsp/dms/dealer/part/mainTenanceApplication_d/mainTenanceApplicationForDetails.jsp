<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div style="width: 100%;height: 100%;overflow-y: auto;overflow-x: hidden;" id="applicationEditPageDiv" >
			<div class="pageHeader"  style="position: static;">
					<!-- 定义隐藏域条件 -->
					<div class="searchBar" align="left">
						<table class="searchContent" id="applicationDetailsTables">
							<!-- 定义查询条件 -->
							<tr>
								<td><label>申请单号</label></td>
								<td>
									<input type="hidden" id="APPLICATION_ID_D" name="APPLICATION_ID_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_ID_D" />
									<input type="text" id="APPLICATION_NO_D" name="APPLICATION_NO_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_NO_D" />
								</td>
								<td><label>申请单状态</label></td>
								<td colspan="3">
									<input type="text" id="APPLICATION_STATUS_D" name="APPLICATION_STATUS_D"  datasource="APPLICATION_STATUS_D" />
								</td>
								<!-- 								
								<td><label>车辆识别码</label></td>
								<td><input type="text" id="VIN_D" name="VIN_D"  dataSource="VIN_D" /></td>
								 -->							
 							</tr>
							<tr>
								<td><label>申请单类型</label></td>
								<td>
									<input type="text" id="APPLICATION_TYPE_D" name="APPLICATION_TYPE_D" datatype="1,is_digit_letter,30" dataSource="APPLICATION_TYPE_D" />
								</td>
								<td><label>申请时间</label></td>
								<td>
									<input type="text" id="APPLICATION_TIME_D" name="APPLICATION_TIME_D" datasource="APPLICATION_TIME_D" datatype="1,is_null,30"/> 
                                </td>
								<td><label>申请人</label></td>
								<td><input type="text" id="APPLICATION_PERSON_D" name="APPLICATION_PERSON_D"  dataSource="APPLICATION_PERSON_D" /></td>
							</tr>
							<tr>
								<td><label>申请单位</label></td>
								<td><input type="text" id="APPLICATION_WORK_D" name="APPLICATION_WORK_D"  dataSource="APPLICATION_WORK_D" /></td>
								<td><label>申请人联系方式</label></td>
								<td colspan="3"><input type="text" id="APPLICATION_INFOMATION_D" name="APPLICATION_INFOMATION_D"  dataSource="APPLICATION_INFOMATION_D" /></td>
							</tr>
							<tr>
								<td><label>技术科审批人</label></td>
								<td><input type="text" id="ENGINEERING_DEPARTMENT_D" name="ENGINEERING_DEPARTMENT_D"  dataSource="ENGINEERING_DEPARTMENT_D" /></td>
								<td><label>技术科审批时间</label></td>
								<td><input type="text" id="ENGINEERING_DEPARTMENT_DATE_D" name="ENGINEERING_DEPARTMENT_DATE_D"  dataSource="ENGINEERING_DEPARTMENT_DATE_D" /></td>
								<td><label>技术科审批备注</label></td>
								<td><input type="text" id="ENGINEERING_DEPARTMENT_REMARK_D" name="ENGINEERING_DEPARTMENT_REMARK_D"  dataSource="ENGINEERING_DEPARTMENT_REMARK_D" /></td>
							</tr>
							<tr>
								<td><label>采购科审批人</label></td>
								<td><input type="text" id="PURCHASING_DEPARTMENT_D" name="PURCHASING_DEPARTMENT_D"  dataSource="PURCHASING_DEPARTMENT_D" /></td>
								<td><label>采购科审批时间</label></td>
								<td><input type="text" id="PURCHASING_DEPARTMENT_DATE_D" name="PURCHASING_DEPARTMENT_DATE_D"  dataSource="PURCHASING_DEPARTMENT_DATE_D" /></td>
								<td><label>采购科审批备注</label></td>
								<td><input type="text" id="PURCHASING_DEPARTMENT_REMARK_D" name="PURCHASING_DEPARTMENT_REMARK_D"  dataSource="PURCHASING_DEPARTMENT_REMARK_D" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
								<li>&nbsp;</li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div style="widht:100%;">
					<iframe id="applicationTypeFrame" marginwidth="0" marginheight="0" src="" frameborder="0" scrolling="no" width="100%" border="0"></iframe>
			</div>
	</div>
<script type="text/javascript">

$(function(){
	
	var applicationId = $("#tab-contract").getSelectedRows()[0].attr("APPLICATION_ID");
	
	var getDetailsURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationDetails.ajax";
	
	// 查询申请表主信息
	sendPost(getDetailsURL+"?applicationId="+applicationId,"","",callbackShowDetailsInfo,null,null);
	
	// 主信息查询回调函数
	function callbackShowDetailsInfo(res,sData){
		
		// 此变量保存回调对象中包含的后台查询到的数据
		var applicationInfo;
		
		// 判断浏览器
		var explorer = window.navigator.userAgent;
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		// 调用显示主信息的函数
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		$("#applicationDetailsTables").find("input").each(function(index,obj){
			var inputName = $(obj).attr("name")
			$(obj).val(jsonObj[inputName]);
		});
		var iframeSrc = "";
		switch(jsonObj["APPLICATION_TYPE_CODE_D"]){
			case "307201": // 307201 零件编号录入
				iframeSrc = "partInfoDetails.jsp";
				break;
			case "307202": // 307202 驾驶室总成录入
				iframeSrc = "cabAssemblyDetails.jsp";
				break;
			case "307203": // 307203 驾驶室本体录入
				iframeSrc = "cabInfoDetails.jsp";
				break;
			case "307204": // 307204 零件编号变更(禁用)
				iframeSrc = "partInfoChangeDetails.jsp";
				break;
			case "307205": // 307205 供货商变更(禁用)
				iframeSrc = "supplierChangeDetails.jsp";
				break;
			default:
				iframeSrc = "";
				break;
		}
		if(iframeSrc != ""){
			$("#applicationTypeFrame").attr("src",iframeSrc).show();
		}else{
			$("#applicationTypeFrame").hide();
		}
	}
});

</script>