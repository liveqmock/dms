package com.org.dms.action.part.basicInfoMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.basicInfoMng.PtBaInfoDao;
import com.org.dms.dao.part.basicInfoMng.PtBaPriceLogDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaInfoVO_Ext;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.frameImpl.Constant;
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

public class PtBaPriceLogAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OrgPersonAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象

    private PtBaPriceLogDao dao = PtBaPriceLogDao.getInstance(atx);   
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {
        	PtBaPriceLogVO vo = new PtBaPriceLogVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());			
			vo.setLogId("");
			vo.setPriceType("1"); //销售价格
	
			//通过dao，执行插入
			dao.insertPtBaPriceLog(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"配件销售价格信息新增成功！");  		
			
			//更新档案销售价格信息
			dao.updatePtBaInfo(vo.getPartId(), vo.getNowPrice());
			atx.setOutMsg(vo.getRowXml(),"配件档案中销售价格信息调整成功！");  	

        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
    
    public void insertArmy() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {
        	PtBaPriceLogVO vo = new PtBaPriceLogVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());			
			vo.setLogId("");
			vo.setPriceType("2"); //军品价格
	
			//通过dao，执行插入
			dao.insertPtBaPriceLog(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"配件军品价格信息新增成功！");  		
			
			//更新档案计划价格信息
			dao.updatePtBaInfoArmy(vo.getPartId(), vo.getNowPrice());
			atx.setOutMsg(vo.getRowXml(),"配件档案中军品价格信息调整成功！");  	

        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
    
    
    public void insertPlan() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {
        	PtBaPriceLogVO vo = new PtBaPriceLogVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());			
			vo.setLogId("");
			vo.setPriceType("3"); //计划价格
	
			//通过dao，执行插入
			dao.insertPtBaPriceLog(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"配件计划价格信息新增成功！");  		
			
			//更新档案计划价格信息
			dao.updatePtBaInfoPlan(vo.getPartId(), vo.getNowPrice());
			atx.setOutMsg(vo.getRowXml(),"配件档案中计划价格信息调整成功！");  	

        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
  //修改配件价格
    public void updatePrice() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	PtBaPriceLogVO tempVO = new PtBaPriceLogVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setPriceType("1"); //销售价格

//            dao.updatePtBaPriceLog(tempVO);
            dao.insertPtBaPriceLog(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件销售原始价格信息调整成功！");
            
            dao.updatePtBaInfo(tempVO.getPartId(), tempVO.getOriginalPrice()); 
            atx.setOutMsg(tempVO.getRowXml(),"配件档案中销售价格信息调整成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    
    //修改
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	PtBaPriceLogVO tempVO = new PtBaPriceLogVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setPriceType("1"); //销售价格

//            dao.updatePtBaPriceLog(tempVO);
            dao.insertPtBaPriceLog(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件销售原始价格信息调整成功！");
            
            dao.updatePtBaInfo(tempVO.getPartId(), tempVO.getOriginalPrice()); 
            atx.setOutMsg(tempVO.getRowXml(),"配件档案中销售价格信息调整成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改计划价格
    public void updatePlan() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	PtBaPriceLogVO tempVO = new PtBaPriceLogVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setPriceType("3"); //计划价格

//            dao.updatePtBaPriceLog(tempVO);
            dao.insertPtBaPriceLog(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件计划原始价格信息调整成功！");
            
            dao.updatePtBaInfoPlan(tempVO.getPartId(), tempVO.getOriginalPrice()); 
            atx.setOutMsg(tempVO.getRowXml(),"配件档案中计划价格信息调整成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改军品价格
    public void updateArmy() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	PtBaPriceLogVO tempVO = new PtBaPriceLogVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setPriceType("2"); //军品价格

//            dao.updatePtBaPriceLog(tempVO);
            dao.insertPtBaPriceLog(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件军品原始价格信息调整成功！");
            
            dao.updatePtBaInfoArmy(tempVO.getPartId(), tempVO.getOriginalPrice()); 
            atx.setOutMsg(tempVO.getRowXml(),"配件档案中军品价格信息调整成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    //删除
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String log_id = Pub.val(request, "log_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaPriceLogStatus(log_id, Constant.YXBS_02);           
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
    
    //查询
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
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 调价记录导出
     * @throws Exception
     */
    public void adjustPriceDownload()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORIGINAL_PRICE");
            hBean.setTitle("调价前价格");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("NOW_PRICE");
            hBean.setTitle("调价后价格");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("JGCY");
            hBean.setTitle("价差");
            header.add(4,hBean);

            hBean = new HeaderBean();
            hBean.setName("PRICE_TYPE");
            hBean.setTitle("配件价格类型");
            header.add(5,hBean);

            hBean = new HeaderBean();
            hBean.setName("UPDATE_USER");
            hBean.setTitle("调价人");
            header.add(6,hBean);

            hBean = new HeaderBean();
            hBean.setName("UPDATE_TIME");
            hBean.setTitle("调价时间");
            header.add(7,hBean);

            hBean = new HeaderBean();
            hBean.setName("WAREHOUSE_NAME");
            hBean.setTitle("仓库名称");
            header.add(8,hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("库存数量");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CYJE");
            hBean.setTitle("差异金额 ");
            header.add(9,hBean);
            QuerySet qs = dao.searchPriceReportDownload(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "调价记录", header, qs);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }

  //调价轨迹报表查询
    public void searchPriceReport() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	  
	    ResponseWrapper response = atx.getResponse();
		PageManager page = new PageManager();
		
		String conditions = RequestUtil.getConditionsWhere(request,page);
//		String if_null = Pub.val(request, "if_null");
		try
		{
			BaseResultSet bs = dao.searchPriceReport(page,conditions);
			atx.setOutData("bs", bs);
			
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //调价轨迹报表查询
    public void searchPriceSum() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	  
	    ResponseWrapper response = atx.getResponse();
		PageManager page = new PageManager();
		
		String conditions = RequestUtil.getConditionsWhere(request,page);
//		String if_null = Pub.val(request, "if_null");
		try
		{
			BaseResultSet bs = dao.searchPriceReportSum(page,conditions);
			atx.setOutData("bs", bs);
			
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //查询军品价格
    public void searchArmy() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();	   
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchArmy(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //查询计划价格
    public void searchPlan() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();	   
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchPlan(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}


}

