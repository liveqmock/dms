package com.org.dms.action.part.basicInfoMng;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.SafeStockDao;
import com.org.dms.vo.part.PtBaStockSafestocksVO;

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
 * 安全库存管理action
 */
public class SafeStockAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "SafeStockAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private SafeStockDao dao = SafeStockDao.getInstance(atx);
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
        PtBaStockSafestocksVO tempVO = new PtBaStockSafestocksVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			String saftyId = tempVO.getSaftyId();
			
			//查看是新增还是修改，新增addLowerLimit、addUpperLimit是空
			if (saftyId.equals(""))
			{
				//新增
				tempVO.setCreateUser(user.getAccount());
				tempVO.setCreateTime(Pub.getCurrentDate());
				tempVO.setStatus(DicConstant.YXBS_01);
				dao.insertSafeStock(tempVO);
			} else {
				//修改
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
				dao.updateSafeStock(tempVO);
			}
			
            atx.setOutMsg(tempVO.getRowXml(),"安全库存修改成功！");
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
            //更新安全库存为无效状态
            boolean b = dao.updateSafeStockLowerUpper(saftyId, user.getAccount());
            atx.setOutMsg("","安全库存重置成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 安全库存查询
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
	 * 
	 * @date()2014年8月27日
	 * @author Administrator
	 * @to_do:主车厂安全库存临时表导入校验方法：临时表数据校验
	 * @param user
	 * @param bParams
	 * @return
	 * @throws Exception
	 */
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchSafeStockTmpInfo(user, "");//查询此用户下的所有安全库存临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				String p="^(0|[1-9][0-9]*)$";//校验代码
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String stockCode = qs.getString(i+1,"STOCK_CODE");//仓库代码
	    		String partCode = qs.getString(i+1,"PART_CODE");//配件代码
	    		String lowerLimit = qs.getString(i+1,"LOWER_LIMIT");//库存下限
	    		String upperLimit = qs.getString(i+1,"UPPER_LIMIT");//库存上限
				
	    		//1、空校验
				//仓库代码
				if(null==stockCode || "".equals(stockCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("仓库代码不能为空!");
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
				
				if (!stockCode.equals("") && !partCode.equals("")) {
					//2、是否存在验证：判断业务表PT_BU_STOCK是否存在相同的配件和仓库信息：不存在-则删除
		    		QuerySet qs1 = dao.checkStockPartCode(stockCode, partCode);
					if(qs1.getRowCount() > 0)
					{
						String n = qs1.getString(1, 1);
						if(Integer.parseInt(n) == 0)
						{
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
							errors.setErrorDesc("业务表不存在此仓库或配件，请删除此行数据!");
		                    errorList.add(errors);
						}
					}
				}
			}
			
			//3、重复数据校验，临时表中存在相同的仓库代码和配件代码信息，则必须删除一个
			QuerySet qs2 = dao.searchSafeStockTmpRepeatData(user, "", ""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String stockCode2 = qs2.getString(j+1, "STOCK_CODE"); //仓库代码
					String partCode2 = qs2.getString(j+1, "PART_CODE"); //配件代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchSafeStockTmpRepeatData(user, stockCode2, partCode2);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("行仓库代码和配件代码是重复数据!");
							errorList.add(errors);
						}
	                    
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = "行仓库代码和配件代码是重复数据，重复行是("+errorStr+")！";
					
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
     * 安全库存临时表查询（显示临时表数据在页面弹出框中）
     * @throws Exception
     * Author suoxiuli 2014-08-30
     */
    public void searchImport() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		
		try
		{
			BaseResultSet bs = dao.searchSafeStockTmpInfo(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
    /**
     * 配件三包期设置导入
     * @throws Exception
     * Author suoxiuli 2014-11-5
     */
    /*public void insertImport() throws Exception {

        try {
        	String tmpNo = Pub.val(requestWrapper, "tmpNo");
        	String sql = "";
        	if (!"".equals(tmpNo)&&tmpNo!=null) {
        		sql = " AND A.TMP_NO NOT IN ("+tmpNo+") ";
        	}
            // 配件三包期设置添加
            claimCyclSetMngDao.updateClaimCycleSet(user,sql);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }*/
    
	/**
     * 导入确定按钮：把临时表的正确数据放入到主表
     * @throws Exception
     * Author suoxiuli 2014-11-5
     */
    public void safeStockImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchSafeStockTmpInfo(user, errorInfoRowNum);//查询临时表的所有信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		String stockCode = qs.getString(i+1,"STOCK_CODE");//仓库代码
 		    		String partCode = qs.getString(i+1,"PART_CODE");//配件代码
 		    		String lowerLimit = qs.getString(i+1,"LOWER_LIMIT");//库存下限
 		    		String upperLimit = qs.getString(i+1,"UPPER_LIMIT");//库存下限
 		    		
 		    		QuerySet qs1 = dao.checkSafeStocksCode(stockCode, partCode);
					if(qs1.getRowCount() > 0)
					{
						String saftyId = qs1.getString(1, 1);
						dao.updateImportSafeStockFromTmp(user, saftyId, lowerLimit, upperLimit);
					} else {
						dao.insertImportSafeStockFromTmp(user, stockCode, partCode);
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
            hBean.setName("STOCK_CODE");
            hBean.setTitle("仓库代码");
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
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "safeStockErrorDataExp", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}