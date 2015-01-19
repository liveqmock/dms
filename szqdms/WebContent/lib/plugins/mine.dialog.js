/**
     Collator：andy.ten@tom.com
     Date：2011-10
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
*/

/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出公司选择组件
 */
function openCompany(objId)
{
	var options = {width:650,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/jQFrameWork/dialog/SingleSelectCompany.jsp";
	$.pdialog.open(url, objId, "公司查询", options);
}

/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出人员选择组件
 */
function openPerson(objId)
{
	var options = {width:650,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true};
	var url = webApps +"/jsp/jQFrameWork/dialog/SelectPerson.jsp";
	$.pdialog.open(url, objId, "人员查询", options);
}

/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出菜单选择组件
 */
function openMenus(action,jsdm)
{
	var action1 = action?action:0;
	var jsdm1 = jsdm?jsdm:"";
	var h = document.documentElement.clientHeight;
	var options = {width:400,height:h,max:false,maxable:false,mask:true,mixable:false,minable:false,resizable:false,drawable:false}; 
	var url = webApps +"/jsp/jQFrameWork/dialog/MultiSelectMenus.jsp?action="+action1+"&jsdm="+jsdm1;
	$.pdialog.open(url,"MultiSelectMenus", "功能列表", options);
}

/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出角色选择组件
 */
function openRoles(type)
{
	var t = "";
	if(type == "1")
		t = "1";
	else
		t = "2";
	var options = {width:600,height:460,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/jQFrameWork/dialog/MultiSelectRoles.jsp?type="+t;
	$.pdialog.open(url,"MultiSelectRoles", "选择角色", options);
}

/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出人员选择组件
 */
function openGrantPersons(type)
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/jQFrameWork/dialog/GrantPersons.jsp?type="+type;
	$.pdialog.open(url,"SelectPersons", "授予人员角色", options);
}

/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出密码修改组件
 */
function openChangePassword()
{
	var options = {width:450,height:300,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/jQFrameWork/dialog/changePassword.jsp";
	$.pdialog.open(url,"SelectPersons", "密码维护", options);
}
/**
 * create by andy.ten@tom.com 2011-12-04
 * 弹出组织选择树
 * id:弹出选择树的input框id
 * busType:业务类型[1,2]，1表示配件2表示售后
 * partOrg:配件业务使用：[1,2]，1表示只显示配送中心2表示只显示服务商和经销商，不传表示全显示
 * multi:[1,2],1表示默认是多选，2表示是单选
 */
function showOrgTree(id,busType,partOrg,multi)
{
	var options = {max:false,width:780,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	var m = multi?multi:"1";
	$.pdialog.open(webApps+"/jsp/dms/common/showOrgTree.jsp?id="+id+"&busType="+busType+"&partOrg="+partOrg+"&multi="+m, "showOrgTree", "组织选择", options);
}

/**
 * 弹出配件选择窗口
 */
function showPartInfo(args /* 配件查询参数,可选可配置：{isSingle:是否单选(1: 单选, 0: 多选,默认为1),
											  showAllPart:是否显示全部配件(1: 全部显示, 0: 显示状态为有效的配件配件,默认为0), 
											  showPartType:是否显示配件类型(1: 显示, 0: 不显示, 默认为1),
											  showMinPack: 是否显示最小包装数(1:显示,0:不显示,默认为1),
											  showMinUnit: 是否显示最小包装单位(1:显示,0:不显示,默认为1),
											  showSalePrice:是否显示销售价格 (1:显示,0:不显示,默认为0),
											  showPlanPrice：是否显示计划价格(1:显示,0:不显示,默认为0),
											    } */,
					    options /* 弹出窗口参数,可选,不传则使用默认弹出参数 */){
	options = options || {max:false,width:780,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	var showPartArgs = [];
	// 遍历
	for(var tempArgs in arguments[0]){
		showPartArgs.push(tempArgs+"="+arguments[0][tempArgs]);
	}
	$.pdialog.open(webApps+"/jsp/dms/common/showPartInfo.jsp?"+showPartArgs.join("&"), "showPartInfo", "配件选择", options);
}