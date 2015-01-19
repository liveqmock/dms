package com.org.dms.action.service.oldpartMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartPackDao;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;
/**
 * 旧件装箱ACTION
 * @author zts
 *
 */
public class OldPartPackAction {
    private Logger logger = com.org.framework.log.log.getLogger(
        "OldPartPackAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartPackDao dao = OldPartPackDao.getInstance(atx);
    
    /**
     * 回运单查询
     * @throws Exception
     */
    public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    

    /**
     * 回运单新增
     */
    public void oldPartReturnInsert()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	
	        	SeBuReturnOrderVO vo = new SeBuReturnOrderVO();
				HashMap<String,String> hm;
				String orderNo=dao.getOrderNo();
				hm = RequestUtil.getValues(request);
				String proDate = hm.get("PRODUCE_DATE");//旧件产生时间
			QuerySet qs = dao.queryReturnOrder(user,proDate);
        	if(qs.getRowCount()>0){
        		atx.setOutMsg("",""+proDate+"月已存在一条未提报的回运单，不可重复增加。");
        	}else{
				vo.setValue(hm);
				vo.setOrderNo(orderNo);
				vo.setOrderStatus(DicConstant.HYDZT_01);//回运单状态
				vo.setStatus(DicConstant.YXBS_01);//状态(有效)
				vo.setCreateUser(user.getAccount());// 设置创建人
				vo.setCreateTime(Pub.getCurrentDate());// 创建时间
				vo.setOrgId(user.getOrgId());
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				dao.insertOldReturn(vo);
				vo.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
				vo.bindFieldToDic("TRANS_TYPE", "HYDYSFS");
				vo.bindFieldToDic("ORDER_STATUS", "HYDZT");
				atx.setOutMsg(vo.getRowXml(),"回运单新增成功！");
        	}
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 回运单修改
     * @throws Exception
     */
    public void oldPartReturnUpdate() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuReturnOrderVO vo = new SeBuReturnOrderVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());// 设置创建人
			vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
			dao.updateOldReturn(vo);
			vo.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
			vo.bindFieldToDic("TRANS_TYPE", "HYDYSFS");
			vo.bindFieldToDic("ORDER_STATUS", "HYDZT");
			atx.setOutMsg(vo.getRowXml(),"回运单保存成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 提报
     * @throws Exception
     */
    public void returnOldReport()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = Pub.val(request, "orderId");//回运单ID
        String flag = Pub.val(request, "flag");//标示符  如果1直接提报，如果是2先保存再提报
        SeBuReturnOrderVO vo = new SeBuReturnOrderVO();
        try
        {
        	//判断回运单是否有件
			QuerySet qs = dao.checkReport(orderId);
			if(qs.getRowCount() > 0)
			{
				if(flag.equals("1")){
					vo.setOrderId(orderId);
				}
				if(flag.equals("2")){
					HashMap<String,String> hm;
					hm = RequestUtil.getValues(request);
					vo.setValue(hm);
				}
				vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
				vo.setOrderStatus(DicConstant.HYDZT_05);
				vo.setApplyDate(Pub.getCurrentDate());//提报日期
				vo.setReturnDate(Pub.getCurrentDate());//回运日期
				//回运单提报
				String produceDate=vo.getProduceDate();
	            dao.updateOldReturn(vo);
	            dao.insertMissOldPart(orderId,user,produceDate);//将已存在于回运单中的索赔单中的未回运旧件置为缺失。
	            QuerySet qsMissCount = dao.checkMissCount(orderId);
	            if(qsMissCount.getRowCount()>0){
	            	for(int i=0;i<qsMissCount.getRowCount();i++){
	            		String dtlId=qsMissCount.getString(i+1, "DETAIL_ID");
	            		String shouldCount=qsMissCount.getString(i+1, "SHOULD_COUNT");
	            		String oughtCount=qsMissCount.getString(i+1, "OUGHT_COUNT");
	            		int missCount=Integer.valueOf(shouldCount)-Integer.valueOf(oughtCount);
	            		if(missCount>0){
	            			String miss = String.valueOf(missCount);
	            			dao.updateMissCount(dtlId,miss);
	            		}
	            	}
	            }
	            QuerySet qs1=dao.queryClaim(orderId);
	            for(int i=0;i<qs1.getRowCount();i++){
	            	String claimId=qs1.getString(i+1, "CLAIM_ID");
	            	SeBuClaimVO vo1 =new SeBuClaimVO();
	            	vo1.setClaimId(claimId);
	            	vo1.setOldpartStatus(DicConstant.SF_01);
	            	dao.updateClaim(vo1);//提报之后，为每一个索赔单赋一个状态，100101代表这个索赔单所在的回运单已经提报。
	            }										   
	            //返回更新结果和成功信息
	            atx.setOutMsg("","提报成功！");
			}else{
				atx.setOutMsg("1","请先导入回运旧件，再提报！");
			}
        } catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 回运单删除
     * @throws Exception
     */
    public void oldPartDelete()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String orderId = Pub.val(request, "orderId");
        try
        {
        	dao.updateFaultPartAlreadyById(orderId);
        	//更改索赔单导入数据信息
        	dao.updateFaultPartByOrder(orderId);
        	//删除明细
        	dao.returnOldDetailDelete(orderId);
        	//删除回运单
            dao.returnOldDelete(orderId);
            //返回更新结果和成功信息
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 查询回运清单
     * @throws Exception
     */
    public void returnPartSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		String orderId=Pub.val(request, "orderId");
 		try
 		{
 			BaseResultSet bs = dao.returnPartSearch(page,orderId,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 删除明细
     * @throws Exception
     */
    public void detailDelete() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String detailIds = Pub.val(request, "detailIds");
        try
        {
        	//更改索赔单导入数据信息
        	dao.updateFaultPart(detailIds);
        	dao.updateFaultPartAlready(detailIds);
        	//删除明细
            dao.detailDelete(detailIds);
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 下载
     * @throws Exception
     */
    public void download()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        String produceDate = Pub.val(request, "produceDate");
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("CODE");
    		hBean.setTitle("渠道商代码");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ONAME");
    		hBean.setTitle("渠道商名称");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_CODE");
    		hBean.setTitle("故障代码");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MODELS_CODE");
    		hBean.setTitle("车辆型号");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_CODE");
    		hBean.setTitle("旧件代码");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_NAME");
    		hBean.setTitle("旧件名称");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_COUNT");
    		hBean.setTitle("旧件数量");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SSSL");
    		hBean.setTitle("集中点入库数量");
    		header.add(10,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("生产供应商代码");
    		header.add(11,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("生产供应商名称");
    		header.add(12,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MAIN_SUPP_CODE");
    		hBean.setTitle("责任供应商代码");
    		header.add(13,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MAIN_SUPP_NAME");
    		hBean.setTitle("责任供应商名称");
    		header.add(14,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_REASON");
    		hBean.setTitle("质损原因");
    		header.add(15,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("REMARK");
    		hBean.setTitle("备注");
    		header.add(16,hBean);
    		
    		QuerySet qs = dao.download(produceDate,conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "回运清单数据", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 下载
     * @throws Exception
     */
    public void expData()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = Pub.val(request, "seqs");
    	try
    	{
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("ROW_NUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_CODE");
    		hBean.setTitle("故障代码");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MODELS_CODE");
    		hBean.setTitle("车辆型号");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MILEAGE");
    		hBean.setTitle("行驶里程");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_AMOUNT");
    		hBean.setTitle("配件金额(元)");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ACTUL_COUNT");
    		hBean.setTitle("配件数量");
    		header.add(9,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BOX_NO");
    		hBean.setTitle("箱号");
    		header.add(10,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_CODE");
    		hBean.setTitle("生产供应商代码");
    		header.add(11,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("生产供应商名称");
    		header.add(12,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DUTYSUPPLY_CODE");
    		hBean.setTitle("责任供应商代码");
    		header.add(13,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DUTYSUPPLY_NAME");
    		hBean.setTitle("责任供应商名称");
    		header.add(14,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BROKEN_REASON");
    		hBean.setTitle("质损原因");
    		header.add(15,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REMARKS");
    		hBean.setTitle("备注");
    		header.add(16,hBean);
    		QuerySet qs = dao.expData(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "回运清单错误数据", header, qs);
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    
    /**
     * 导入验证成功查询
     * @throws Exception
     */
    public void searchImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		RequestUtil.getConditionsWhere(request,page);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.searchImport(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 导入数据校验
     * @return
     * @throws Exception
     */
    /*public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs1=dao.checkList1(user);//索赔单号、故障代码、配件代码、供应商代码
		QuerySet qs6=dao.checkList6(user);//数量不能大于故障配件数量
		//QuerySet qs2=dao.checkList2(user);//校验箱号不能为空
		QuerySet qs3=dao.checkList3(user);//校验导入的数据是否重复
	//	QuerySet qs4=dao.checkList4(user);//校验导入的数据是否在旧件明细表中存在
		QuerySet qs5=dao.checkList5(user);//校验导入的数据不存在旧件不回运表
		if(qs1.getRowCount()>0){
			for(int i=0;i<qs1.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum=qs1.getString(i+1, "ROW_NUM"); 
				String claimNo=qs1.getString(i+1,"CLAIM_NO");
				String partCode=qs1.getString(i+1,"PART_CODE");
				String supplyCode=qs1.getString(i+1,"PROSUPPLY_CODE");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"与原索赔单数据不一致！");
				errorList.add(errors);
			}
		}
		if(qs2.getRowCount()>0){
			for(int i=0;i<qs2.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum =qs2.getString(i+1,"ROW_NUM");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("箱号不能空！");
				errorList.add(errors);
			}
		}
		if(qs3.getRowCount()>0){
			for(int i=0;i<qs3.getRowCount();i++){
				errors=new ExcelErrors();
				String claimNo=qs3.getString(i+1,"CLAIM_NO");
				String partCode=qs3.getString(i+1,"PART_CODE");
				String supplyCode=qs3.getString(i+1,"PROSUPPLY_CODE");
				errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"导入数据重复！");
				errorList.add(errors);
			}
		}
		if(qs4.getRowCount()>0){
			for(int i=0;i<qs4.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum=qs4.getString(i+1, "ROW_NUM"); 
				String claimNo=qs4.getString(i+1,"CLAIM_NO");
				String partCode=qs4.getString(i+1,"PART_CODE");
				String supplyCode=qs4.getString(i+1,"PROSUPPLY_CODE");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"数据已导入！");
				errorList.add(errors);
			}
		}
		if(qs5.getRowCount()>0){
			for(int i=0;i<qs5.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum=qs5.getString(i+1, "ROW_NUM"); 
				String claimNo=qs5.getString(i+1,"CLAIM_NO");
				String partCode=qs5.getString(i+1,"PART_CODE");
				String supplyCode=qs5.getString(i+1,"PROSUPPLY_CODE");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"数据申请不回运！");
				errorList.add(errors);
			}
		}
		if(qs6.getRowCount()>0){
			for(int i=0;i<qs6.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum=qs6.getString(i+1, "ROW_NUM"); 
				String claimNo=qs6.getString(i+1,"CLAIM_NO");
				String partCode=qs6.getString(i+1,"PART_CODE");
				String supplyCode=qs6.getString(i+1,"PROSUPPLY_CODE");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"配件数量大于索赔单数量！");
				errorList.add(errors);
			}
		}
		if(errorList!=null&&errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    }
    */
    /**
     * 插入正式表
     * @throws Exception
     */
    public void oldPartPackImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String orderId=Pub.val(request, "orderId");
 		String tmpNo = Pub.val(request, "tmpNo");
 		try
 		{
 			String rowNo = "";
        	if ("".equals(tmpNo)==false) {
        		rowNo = " AND T.ROW_NUM NOT IN ("+tmpNo+") ";
        	}
 			//插入旧件回运表
 			dao.insertDetail(user,orderId,rowNo);
 			//更新索赔单配件表旧件入库数量
 			dao.updateClaimPartCount(user,rowNo);
 			//更新索赔单配件表 是否回运字段
 			dao.updateClaimPart(user,rowNo);
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }  
}
