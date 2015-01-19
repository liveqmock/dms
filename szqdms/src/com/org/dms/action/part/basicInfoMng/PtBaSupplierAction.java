package com.org.dms.action.part.basicInfoMng;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBaSupplierDao;
import com.org.dms.vo.part.PtBaSupplierVO;
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

public class PtBaSupplierAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OrgPersonAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBaSupplierDao dao = PtBaSupplierDao.getInstance(atx);
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
        	PtBaSupplierVO vo = new PtBaSupplierVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//设置配件标识、服务标识、有效标识
			vo.setStatus(DicConstant.YXBS_01);
			vo.setPartIdentify(DicConstant.YXBS_01);
			vo.setSeIdentify(DicConstant.YXBS_02);
			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			//判断供应商代码是否已存在
			QuerySet qs = dao.checkCode(vo.getSupplierCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("供应商代码已存在，保存失败！");
				}
			}		
	
			//通过dao，执行插入
			dao.insertPtBaSupplier(vo);
			
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"配件供应商信息新增成功！");           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
    public void insertService() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {           
        	PtBaSupplierVO vo = new PtBaSupplierVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//设置配件标识、服务标识、有效标识
			vo.setStatus(DicConstant.YXBS_01);
			vo.setSeIdentify(DicConstant.YXBS_01);
			vo.setPartIdentify(DicConstant.YXBS_02);
			
			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			//判断供应商代码是否已存在
			QuerySet qs = dao.checkCode(vo.getSupplierCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("供应商代码已存在，保存失败！");
				}
			}		
	
			//通过dao，执行插入
			dao.insertPtBaSupplier(vo);
			
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"服务供应商信息新增成功！");           
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
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaSupplierVO tempVO = new PtBaSupplierVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updatePtBaSupplier(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"供应商信息修改成功！");
         
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
        String supplier_id = Pub.val(request, "supplier_id");
        try
        {
            dao.updatePtBaSupplierPartIdentify(supplier_id, DicConstant.YXBS_02);        
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //删除供应商(服务)信息 
    public void deleteService() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String supplier_id = Pub.val(request, "supplier_id");
        try
        {
            dao.updatePtBaSupplierSeIdentify(supplier_id, DicConstant.YXBS_02);        
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
    
    public void partSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.partSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 供应商信息查询
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
    public void supplierInfoQuery() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.supplierInfoQuery(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 供应商信息查询导出
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
    public void exportExcel() throws Exception{

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
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("厂家代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("厂家名称");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ERP_NO");
            hBean.setTitle("ERP编码");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("STATUS");
            hBean.setTitle("是否有效");
            header.add(hBean);
			
            hBean = new HeaderBean();
            hBean.setName("INVOICE_TYPE");
            hBean.setTitle("发票类型");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TAX_RATE");
            hBean.setTitle("税率(%)");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_QUALIFY");
            hBean.setTitle("厂家资质");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LEGAL_PERSON");
            hBean.setTitle("厂家法人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LEGAL_PERSON_PHONE");
            hBean.setTitle("法人联系方式");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("BUSINESS_PERSON");
            hBean.setTitle("业务联系人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("BUSINESS_PERSON_PHONE");
            hBean.setTitle("联系方式");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("OPEN_ACCOUNT");
            hBean.setTitle("结算周期(月)");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("EFFECT_DATE");
            hBean.setTitle("有效期(天)");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("GUARANTEE_MONEY");
            hBean.setTitle("质保金");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("WARRANTY_PERIOD");
            hBean.setTitle("质保期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RECOVERY_CLAUSE");
            hBean.setTitle("追偿条款");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(hBean);

            QuerySet querySet = dao.download(conditions,page);
            os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
            ExportManager.exportFile(response.getHttpResponse(), "供应商信息查询", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}

