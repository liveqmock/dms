package com.org.dms.action.part.basicInfoMng;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBaInfoDao;
import com.org.dms.dao.part.basicInfoMng.PtBaPartSupplierRlDao;
import com.org.dms.dao.part.basicInfoMng.PtBaPictureDao;
import com.org.dms.dao.part.basicInfoMng.PtBaSupplierDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaInfoVO_Ext;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPartSupplierRlVO_Ext;
import com.org.dms.vo.part.PtBaPictureVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
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

public class PtBaPartSupplierRlAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OrgPersonAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBaPartSupplierRlDao dao = PtBaPartSupplierRlDao.getInstance(atx);
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
      
    	
        try
        {           
        	PtBaPartSupplierRlVO vo = new PtBaPartSupplierRlVO();
        	PtBaPartSupplierRlVO_Ext extvo = new PtBaPartSupplierRlVO_Ext();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			extvo.setValue(hm);
			//设置通用字段
			vo.setStatus(DicConstant.YXBS_01);
			vo.setPartIdentify(DicConstant.YXBS_01);			//配件标识
			vo.setSeIdentify(DicConstant.YXBS_02);				//服务标识
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
//			QuerySet getSupId = dao.getSupId(hm.get("SUPPLIER_CODE"));
			

			//判断配件代码是否已存在
			QuerySet qs = dao.checkSupplier(vo.getPartId(), vo.getSupplierId());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("配件供应商关系已存在，保存失败！");
				}
			}
//			vo.setSupplierId(getSupId.getString(1, "SUPPLIER_ID"));
			//通过dao，执行插入
			dao.insertPtBaPartSupplierRl(vo);
			
			//返回插入结果和成功信息
			atx.setOutMsg(extvo.getRowXml(),"配件供应商关系信息新增成功(配件)！");           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
    //新增供货关系(服务)
    public void insertService() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
      
    	
        try
        {           
        	PtBaPartSupplierRlVO vo = new PtBaPartSupplierRlVO();
        	PtBaPartSupplierRlVO_Ext extvo = new PtBaPartSupplierRlVO_Ext();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			extvo.setValue(hm);
			//设置通用字段
			vo.setStatus(DicConstant.YXBS_01);
			vo.setPartIdentify(DicConstant.YXBS_02);			//配件标识
			vo.setSeIdentify(DicConstant.YXBS_01);				//服务标识
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			

			//判断配件代码是否已存在
			QuerySet qs = dao.checkSupplier(vo.getPartId(), vo.getSupplierId());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("配件供应商关系已存在，保存失败！");
				}
			}
	
			//通过dao，执行插入
			dao.insertPtBaPartSupplierRl(vo);
			
			//返回插入结果和成功信息
			atx.setOutMsg(extvo.getRowXml(),"配件供应商关系信息新增成功(服务)！");           
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
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaPartSupplierRlVO tempVO = new PtBaPartSupplierRlVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
//			QuerySet getSupId = dao.getSupId(hm.get("SUPPLIER_CODE"));
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getPersonName());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updatePtBaPartSupplierRl(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件供货关系信息修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    //修改采购价格
    public void updatePchPrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaPartSupplierRlVO tempVO = new PtBaPartSupplierRlVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			tempVO.setPchUser(user.getPersonName());
			tempVO.setPchTime(Pub.getCurrentDate());
			dao.updatePchPrice(tempVO.getRelationId(), tempVO.getPchPrice());
			
			
            //插入采购价格轨迹
			PtBaPriceLogVO volog = new PtBaPriceLogVO();
			HashMap<String,String> hmm;
			hmm = RequestUtil.getValues(request);
			volog.setValue(hmm);
			volog.setOriginalPrice(original_price);
			volog.setNowPrice(now_price);
			volog.setPriceType("205505");
			volog.setCreateUser(user.getPersonName());
			volog.setCreateTime(Pub.getCurrentDate());
			dao.insertPchPriceLog(volog);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件采购价格修改成功！");
         
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
        String relation_id = Pub.val(request, "relation_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaPartSupplierRlPartIdentify(relation_id, DicConstant.YXBS_02);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
    //删除供货关系(服务)
    public void deleteService() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String relation_id = Pub.val(request, "relation_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaPartSupplierRlSeIdentify(relation_id, DicConstant.YXBS_02);
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
  //查询
    public void searchService() throws Exception
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
			BaseResultSet bs = dao.searchService(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //查询配件采购价格
    public void searchPrice() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();	   
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String if_null = Pub.val(request, "if_null");
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchPrice(page,conditions,if_null);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //配件弹出
    public void partsupplierrlSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.partsupplierrlSearch(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //服务弹出
    public void partsupplierrlFwSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.partsupplierrlFwSearch(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 采购价格导出
     * @throws Exception
     */
    public void pchPriceDownload()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String if_null = Pub.val(request, "if_null");
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
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(4,hBean);

            hBean = new HeaderBean();
            hBean.setName("PCH_PRICE");
            hBean.setTitle("采购价格");
            header.add(5,hBean);
            
            QuerySet qs = dao.pchPriceDownload(conditions,if_null);
            ExportManager.exportFile(response.getHttpResponse(), "采购价格", header, qs);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    //配件采购价格错误信息导出
    public void expPchPriceTmpErrorData()throws Exception{

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
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(2,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PCH_PRICE");
            hBean.setTitle("采购价格");
            header.add(5,hBean);
            
            QuerySet querySet = dao.expPchPriceTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "配件采购价格错误信息", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }

    //searchImport
    public void searchImport() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchImport(pageManager, conditions,user);
            baseResultSet.setFieldDic("PART_STATUS", DicConstant.YXBS);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    public void insertImport() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 合同明细主键
            String forcastId = Pub.val(request, "forcastMonth");
            String tmpNo = Pub.val(request, "tmpNo");
            QuerySet getTmp = dao.getTmp(tmpNo,user);
            for(int i = 0;i<getTmp.getRowCount();i++){
            	String partId = getTmp.getString(i+1, "PART_ID");
            	String supplierId = getTmp.getString(i+1, "SUPPLIER_ID");
            	String applyCycle = getTmp.getString(i+1, "APPLY_CYCLE");
            	String partStatus = getTmp.getString(i+1, "PART_STATUS");
            	QuerySet checkIn = dao.checkIn(partId,supplierId);
            	if(checkIn.getRowCount()>0){
            		PtBaPartSupplierRlVO u_vo = new PtBaPartSupplierRlVO();
            		u_vo.setRelationId(checkIn.getString(1, "RELATION_ID"));
            		u_vo.setPartId(partId);
            		u_vo.setSupplierId(supplierId);
            		u_vo.setApplyCycle(applyCycle);
            		u_vo.setPartIdentify(partStatus);
            		u_vo.setUpdateUser(user.getAccount());
            		u_vo.setUpdateTime(Pub.getCurrentDate());
            		dao.updatePtBaPartSupplierRl(u_vo);
            	}else{
            		PtBaPartSupplierRlVO i_vo = new PtBaPartSupplierRlVO();
            		i_vo.setPartId(partId);
            		i_vo.setSupplierId(supplierId);
            		i_vo.setApplyCycle(applyCycle);
            		i_vo.setPartIdentify(partStatus);
            		i_vo.setCreateUser(user.getAccount());
            		i_vo.setCreateTime(Pub.getCurrentDate());
            		i_vo.setOemCompanyId(user.getOemCompanyId());
            		i_vo.setStatus(DicConstant.YXBS_01);
            		dao.insertPtBaPartSupplierRl(i_vo);
            	}
            }
//        	String sql = "";
//        	if (!"".equals(tmpNo)&&tmpNo!=null) {
//        		sql = " AND T.TMP_NO NOT IN ("+tmpNo+") ";
//        	}
//            // 合同明细更新
//            dao.updateForecastDtl(forcastId,user,sql);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    
    public void expData()throws Exception{

        try {
            // 获取session中的user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            // 定义response对象
            ResponseWrapper responseWrapper= atx.getResponse();
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("APPLY_CYCLE");
            hBean.setTitle("供货周期");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_STATUS");
            hBean.setTitle("配件标识");
            header.add(3,hBean);
            
            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "GHGX", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    
    
    public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager page = new PageManager();
    	page.setPageRows(99999);
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("APPLY_CYCLE");
    		hBean.setTitle("供货周期");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_STATUS");
    		hBean.setTitle("配件标识");
    		header.add(hBean);
    		
    		QuerySet qs = dao.download(conditions,page);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "供货关系", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}
    //批量修改服务标识
    public void batchUpdateService() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String relationIds = Pub.val(request, "relationIds");
        String[] relationIdsArr = relationIds.split(",");
        PtBaPartSupplierRlVO vo = new PtBaPartSupplierRlVO();
        
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
        	if(relationIdsArr.length>0){
        		for(int i=0; i<relationIdsArr.length;i++){
        			vo.setRelationId(relationIdsArr[i]);
        			vo.setSeIdentify(hm.get("SE_IDENTIFY"));
        			vo.setUpdateUser(user.getAccount());
	    			vo.setUpdateTime(Pub.getCurrentDate());
	    			dao.updatePtBaPartSupplierRl(vo);
        		}
        		atx.setOutMsg("","服务标识批量修改成功！");
        	 }
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
}

