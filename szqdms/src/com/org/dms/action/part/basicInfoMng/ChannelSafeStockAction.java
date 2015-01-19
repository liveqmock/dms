package com.org.dms.action.part.basicInfoMng;

import java.io.OutputStream;
import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.ChannelSafeStockDao;
import com.org.dms.vo.part.PtBaChannelSafestockVO;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import org.apache.log4j.Logger;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 渠道安全库存管理action
 */
public class ChannelSafeStockAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ChannelSafeStockAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ChannelSafeStockDao dao = ChannelSafeStockDao.getInstance(atx);
    // 定义response对象
    ResponseWrapper responseWrapper= atx.getResponse();

    /**
     * 修改安全库存
     * @throws Exception
     * @Author suoxiuli 2014-07-25
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaChannelSafestockVO tempVO = new PtBaChannelSafestockVO();
        
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//查看是新增还是修改，新增addLowerLimit、addUpperLimit是空
			String saftyId = tempVO.getSaftyId();
			if (saftyId.equals(""))
			{
				//新增
				tempVO.setCreateUser(user.getAccount());
				tempVO.setCreateTime(Pub.getCurrentDate());
				tempVO.setStatus(DicConstant.YXBS_01);
				dao.insertChannelSafeStock(tempVO);
				
			} else {
				//修改
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
				dao.updateChannelSafeStock(tempVO);
			}
			
			atx.setOutMsg(tempVO.getRowXml(),"安全库存上限限设置成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 重置安全库存
     * @throws Exception
     * @Author suoxiuli 2014-07-25
     */
    public void doReSet() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String saftyId = Pub.val(request, "saftyId");
        try
        {
            //更新渠道安全库存为无效状态
            dao.updateChannelSafeStockLowerUpper(saftyId,user.getAccount());
            atx.setOutMsg("","安全库存重置成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 渠道安全库存查询
     * @throws Exception
     * Author suoxiuli 2014-07-25
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
	 * 校验渠道安全库存临时表数据
	 * @throws Exception
     * Author suoxiuli 2014-09-01
	 */
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchTmp(user, "");//查询此用户下的所有安全库存临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				String p="^(0|[1-9][0-9]*)$";//校验代码
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String orgCode = qs.getString(i+1,"ORG_CODE");//仓库代码
	    		String partCode = qs.getString(i+1,"PART_CODE");//配件代码
	    		String lowerLimit = qs.getString(i+1,"LOWER_LIMIT");//库存下限
	    		String upperLimit = qs.getString(i+1,"UPPER_LIMIT");//库存上限
				
	    		//1、空校验
				//渠道商代码
				if(null==orgCode || "".equals(orgCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("渠道商代码不能为空!");
                    errorList.add(errors);
                }
				
				//配件代码
				if(null==partCode || "".equals(partCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不能为空!");
                    errorList.add(errors);
                }
				
				//库存下限
				if(null==lowerLimit || "".equals(lowerLimit)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件库存下限不能为空!");
                    errorList.add(errors);
                }
				if(null!=lowerLimit && !"".equals(lowerLimit)){
	    			if(!lowerLimit.matches(p)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("库存下限是>=0的数字!");
	                    errorList.add(errors);
	    			}
                }
				
				//库存上限
				if(null==upperLimit || "".equals(upperLimit)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件库存上限不能为空!");
                    errorList.add(errors);
                }
				if(null!=upperLimit && !"".equals(upperLimit)){
	    			if(!upperLimit.matches(p)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("库存上限是>=0的数字!");
	                    errorList.add(errors);
	    			}
                }
				if (!lowerLimit.equals("") && !upperLimit.equals("")) {
					//库存下限必须<=库存上限
					if(Integer.parseInt(lowerLimit) >= Integer.parseInt(upperLimit)){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("配件库存下限不能大于库存上限!");
	                    errorList.add(errors);
	                }
				}
				
				if (!orgCode.equals("") && !partCode.equals("")) {
					//2、是否存在验证：判断业务表PT_BU_DEALER_STOCK是否存在相同的渠道商和仓库信息：不存在-则删除
		    		QuerySet qs1 = dao.checkOrgPartCode(orgCode, partCode);
					if(qs1.getRowCount() > 0)
					{
						String n = qs1.getString(1, 1);
						if(Integer.parseInt(n) == 0)
						{
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
							errors.setErrorDesc("业务表不存在此渠道商或配件，请删除此行数据!");
		                    errorList.add(errors);
						}
					}
				}
			}
			
			//3、重复数据校验，临时表中存在相同的仓库代码和配件代码信息，则必须删除一个
			QuerySet qs2 = dao.searchChanSafeStockTmpRepeatData(user, "", ""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String orgCode2 = qs2.getString(j+1, "ORG_CODE"); //仓库代码
					String partCode2 = qs2.getString(j+1, "PART_CODE"); //配件代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchChanSafeStockTmpRepeatData(user, orgCode2, partCode2);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						if (k != (qs3.getRowCount() - 1)) {
							errors.setErrorDesc("行渠道代码和配件代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}

					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = "行渠道商代码和配件代码重复，重复行是("+errorStr+")！";
	
					//添加错误描述
					//errors=new ExcelErrors();
					errors.setErrorDesc(errorStr);
					errorList.add(errors);
				}
			}
		}
		
		if(errorList!=null && errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    }
    
    /**
     * 渠道安全库存临时表查询(导入数据正确，显示临时表数据)
     * @throws Exception
     * Author suoxiuli 2014-07-25
     */
    public void searchTmp() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		
		try
		{
			BaseResultSet bs = dao.searchTmp(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 导入确定按钮：把临时表的数据放入到主表
     * @throws Exception
     */
    public void insertChanSafeStockFromTmp()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String errorInfoRowNum = Pub.val(request, "rowNum");
 		try
 		{
 			//更新
 		    QuerySet qs = dao.searchTmp(user, errorInfoRowNum);//查询临时表的所有信息
		    if(qs.getRowCount() > 0 ){
		    	for(int i=0;i<qs.getRowCount();i++){
		    		String orgCode = qs.getString(i+1,"ORG_CODE");//服务商代码
		    		String partCode = qs.getString(i+1,"PART_CODE");//配件代码
		    		String lowerLimit = qs.getString(i+1,"LOWER_LIMIT");//库存下限
		    		String upperLimit = qs.getString(i+1,"UPPER_LIMIT");//库存下限
		    		
		    		QuerySet qs1 = dao.checkChannelSafeStocksCode(orgCode, partCode);
					if(qs1.getRowCount() > 0)
					{
						String saftyId = qs1.getString(1, 1);
						dao.updateImportChannSafeStockFromTmp(user, saftyId, lowerLimit, upperLimit);
					} else {
						dao.insertImportChannSafeStockFromTmp(user, orgCode, partCode);
					}
		    	}
		    }
 		    
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 配送中心库存预警明细查询
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
    public void DCSafeStockWarnQuery() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.DCSafeStockWarnQuery(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 导出错误数据按钮：把临时表的错误数据导出到EXCEL
     * @throws Exception
     * Author suoxiuli 2014-11-5
     */
    public void expSafeStockTmpErrorData()throws Exception{
    	
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	// 将request流中的信息转化为where条件
        String conditions = Pub.val(request, "errorDataRowNum");
    	
        try {
        	
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;

            hBean = new HeaderBean();
            hBean.setName("ROW_NUM");
            hBean.setTitle("导入数据EXCEL行号");
            header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道商代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("LOWER_LIMIT");
            hBean.setTitle("库存下限");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("UPPER_LIMIT");
            hBean.setTitle("库存上限");
            header.add(4,hBean);

            QuerySet querySet = dao.expSafeStockTmpErrorData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "channSafeStockErrorDataExp", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
//---------------报表查询--------------------------------------------------
    /**
     * 配送中心库存预警明细查询导出：报表的导出按钮
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
    public void reportExportExcel() throws Exception{

    	ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager page = new PageManager();
    	page.setPageRows(99999);
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            
            hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
    		
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("配送中心代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("配送中心名称");
            header.add(2,hBean);
			
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("库存数量");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LOWER_LIMIT");
            hBean.setTitle("下限数量");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DELIVERY_COUNT");
            hBean.setTitle("在途数量");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DIFF_AMOUNT");
            hBean.setTitle("差异数量");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DIFF_RATE");
            hBean.setTitle("差异率");
            header.add(9,hBean);

            QuerySet querySet = dao.reportExportExcel(page, conditions);
            os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
            ExportManager.exportFile(response.getHttpResponse(), "配送中心库存预警明细查询", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}