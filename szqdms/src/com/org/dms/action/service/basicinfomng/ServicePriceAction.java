package com.org.dms.action.service.basicinfomng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.basicinfomng.ServicePriceDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
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
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class ServicePriceAction {
	 private Logger logger = com.org.framework.log.log.getLogger("ServicePriceAction");
	 private ActionContext atx = ActionContext.getContext();
	 private ServicePriceDao dao = ServicePriceDao.getInstance(atx);   
	 
	 
	 
	 
	//验证服务索赔价格零时表数据的准确性
    public List<ExcelErrors> checkClData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchSeBaClPriceTmp(user, "");//查询此用户下的所有服务索赔价格临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
				String SE_CLPRICE = qs.getString(i+1, "SE_CLPRICE"); //服务索赔价格

				
	   //1.必填项空校验
				//配件代码
				if(null==PART_CODE || "".equals(PART_CODE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不能为空!");
                    errorList.add(errors);
                }
				//服务索赔价格
				if(null==SE_CLPRICE || "".equals(SE_CLPRICE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("服务索赔价格不能为空!");
                    errorList.add(errors);
                }
				
		//2.校验配件代码是否存在		
				QuerySet qs1 = dao.checkPart_code(PART_CODE);
				if(qs1.getRowCount() == 0){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不存在，请核对信息!");
                    errorList.add(errors);
				}
		
			}
		//3.重复数据校验,零时表中存在相同的(配件代码+供应商代码)则必须删除一个		
			QuerySet qs2 = dao.searchSeBaClPriceTmpRepeatData(user,""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
					String errorStr = "";
					QuerySet qs3 = dao.searchSeBaClPriceTmpRepeatData(user, partCode);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = errorStr + "配件代重复，请删除重复数据！重复行是("+errorStr+")！";
					
					//添加错误描述
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
    
  //验证服务追偿价格零时表数据的准确性
    public List<ExcelErrors> checkReData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchSeBaRePriceTmp(user, "");//查询此用户下的所有服务追偿价格临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
				String SE_REPRICE = qs.getString(i+1, "SE_REPRICE"); //服务追偿价格

				
	   //1.必填项空校验
				//配件代码
				if(null==PART_CODE || "".equals(PART_CODE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不能为空!");
                    errorList.add(errors);
                }
				//服务追偿价格
				if(null==SE_REPRICE || "".equals(SE_REPRICE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("服务追偿价格不能为空!");
                    errorList.add(errors);
                }
		//2.校验配件代码是否存在		
				QuerySet qs1 = dao.checkPart_code(PART_CODE);
				if(qs1.getRowCount() == 0){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不存在，请核对信息!");
                    errorList.add(errors);
				}		
		
			}
			//3.重复数据校验,零时表中存在相同的(配件代码+供应商代码)则必须删除一个		
			QuerySet qs2 = dao.searchSeBaRePriceTmpRepeatData(user,""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
					String errorStr = "";
					QuerySet qs3 = dao.searchSeBaRePriceTmpRepeatData(user, partCode);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = errorStr + "配件代重复，请删除重复数据！重复行是("+errorStr+")！";
					
					//添加错误描述
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
    
    
  //服务索赔价格零时表数据查询
    public void searchSeBaClPriceTmpImport() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchSeBaClPriceTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //服务索赔价格零时表数据查询
    public void searchSeBaRePriceTmpImport() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchSeBaRePriceTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //将服务索赔价格信息导入正式表
    public void seBaClPriceImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchSeBaClPriceTmp(user, errorInfoRowNum);//查询此用户下的所有服务索赔价格临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		//必填
 					String PART_CODE = qs.getString(i+1,"PART_CODE");
 					String SE_CLPRICE = qs.getString(i+1,"SE_CLPRICE");				//服务索赔价格
 					
 					//获得	PART_ID,PART_NAME			
 					QuerySet qs1 = dao.getPartInfo(PART_CODE);
 					String PART_ID = qs1.getString(1, "PART_ID");
 					String PART_NAME = qs1.getString(1, "PART_NAME");
 					//主表VO
 					PtBaInfoVO vo = new PtBaInfoVO();
 		    		PtBaPriceLogVO logvo = new PtBaPriceLogVO();
					
					
					QuerySet qs2 = dao.checkSePrice("SE_CLPRICE", PART_ID, SE_CLPRICE);
					if(qs2.getRowCount() == 0)
					{
						QuerySet qs3 = dao.checkSePrice("SE_CLPRICE", PART_ID, "");
						String SE_CLPRICE_OLD = qs3.getString(1, "SE_CLPRICE");        //服务索赔价格原价
						//更新配件档案表服务索赔价格信息
						vo.setPartId(PART_ID);
						vo.setSeClprice(SE_CLPRICE);
						vo.setPriceUser(user.getAccount());
						vo.setPriceTime(Pub.getCurrentDate());
						dao.updateSePrice(vo);
						
						//插入服务索赔价格调价日志	
						
						logvo.setLogId("");
 						logvo.setOriginalPrice(SE_CLPRICE_OLD);
 						logvo.setNowPrice(SE_CLPRICE);
 						logvo.setPriceType(DicConstant.PJJGLX_06);
 						logvo.setCreateUser(user.getPersonName());
 						logvo.setCreateTime(Pub.getCurrentDate());
 						
 						logvo.setPartId(PART_ID);
 						logvo.setPartCode(PART_CODE);
 						logvo.setPartName(PART_NAME);
 						
 						dao.insertSePriceLog(logvo);
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
  //将服务追偿价格信息导入正式表
    public void seBaRePriceImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchSeBaRePriceTmp(user, errorInfoRowNum);//查询此用户下的所有服务追偿价格临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		//必填
 					String PART_CODE = qs.getString(i+1,"PART_CODE");
 					String SE_REPRICE = qs.getString(i+1,"SE_REPRICE");				//服务追偿价格
 					
 					//获得	PART_ID,PART_NAME			
 					QuerySet qs1 = dao.getPartInfo(PART_CODE);
 					String PART_ID = qs1.getString(1, "PART_ID");
 					String PART_NAME = qs1.getString(1, "PART_NAME");
 					
 					//主表VO
 					PtBaInfoVO vo = new PtBaInfoVO();
 		    		PtBaPriceLogVO logvo = new PtBaPriceLogVO();
					
					
					QuerySet qs2 = dao.checkSePrice("SE_REPRICE", PART_ID, SE_REPRICE);
					if(qs2.getRowCount() == 0)
					{
						QuerySet qs3 = dao.checkSePrice("SE_REPRICE", PART_ID, "");
						String SE_REPRICE_OLD = qs3.getString(1, "SE_REPRICE");        //服务追偿价格原价
						
						
						//更新配件档案表服务追偿价格信息
						vo.setPartId(PART_ID);
						vo.setSeReprice(SE_REPRICE);
						vo.setPriceUser(user.getAccount());
						vo.setPriceTime(Pub.getCurrentDate());
						dao.updateSePrice(vo);
						
						//插入服务追偿价格调价日志	
						
						logvo.setLogId("");
 						logvo.setOriginalPrice(SE_REPRICE_OLD);
 						logvo.setNowPrice(SE_REPRICE);
 						logvo.setPriceType(DicConstant.PJJGLX_07);
 						logvo.setCreateUser(user.getPersonName());
 						logvo.setCreateTime(Pub.getCurrentDate());
 						
 						logvo.setPartId(PART_ID);
 						logvo.setPartCode(PART_CODE);
 						logvo.setPartName(PART_NAME);
 						
 						dao.insertSePriceLog(logvo);
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
    
  //服务索赔价格错误信息导出
    public void expSeClPriceTmpErrorData()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = Pub.val(request, "errorDataRowNum");
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROW_NUM");
    		hBean.setTitle("导入数据EXCEL行号");
    		header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);

            
            hBean = new HeaderBean();
            hBean.setName("SE_CLPRICE");
            hBean.setTitle("服务索赔价格");
            header.add(2,hBean);
            
            QuerySet querySet = dao.expSeClPriceTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "服务索赔价格错误信息", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
  //服务追偿价格错误信息导出
    public void expSeRePriceTmpErrorData()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = Pub.val(request, "errorDataRowNum");
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROW_NUM");
    		hBean.setTitle("导入数据EXCEL行号");
    		header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);

            
            hBean = new HeaderBean();
            hBean.setName("SE_REPRICE");
            hBean.setTitle("服务追偿价格");
            header.add(2,hBean);
            
            QuerySet querySet = dao.expSeRePriceTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "服务追偿价格错误信息", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    //服务索赔价格导出
    public void expSeClPrice()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);

            
            hBean = new HeaderBean();
            hBean.setName("SE_CLPRICE");
            hBean.setTitle("服务索赔价");
            header.add(2,hBean);
            
            QuerySet querySet = dao.downloadServicePrice(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "服务索赔价格", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
  //服务追偿价格导出
    public void expSeRePrice()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);

            
            hBean = new HeaderBean();
            hBean.setName("SE_REPRICE");
            hBean.setTitle("服务追偿价");
            header.add(2,hBean);
            
            QuerySet querySet = dao.downloadServicePrice(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "服务追偿价格", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
