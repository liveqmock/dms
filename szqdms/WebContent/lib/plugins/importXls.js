/**
     Collator：andy.ten@tom.com
     Date：2012-18
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
*/
/**
 * 上传文件功能 
 * @param {Object} bType：业务类型
 * @param {Object} bParams：业务主键
 * @param {Object} sCol：最大列数
 * @param {Object} sRow：允许的最大空白行数
 * @param {Object} bUrl：成功后返回页bUrl
 */
function importXls(bType,bParams,sCol,sRow,bUrl)
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true};
	var col,row;
	if(!sCol) col = "1";
	else col = sCol;
	if(!sRow) row = "1";
	else row = sRow;
	var u = bUrl;
	if(bUrl)
		u = bUrl.replace(/[\\/]/g,";");
	var url = webApps +"/jsp/jQFrameWork/dialog/ImportXls.jsp?bType="+bType+"&bParams="+bParams+"&maxCol="+col+"&blankRows="+row+"&bUrl="+u;
	var oId = "importXls";
	$.pdialog.open(url, oId, "导入文件", options,true);
	
}