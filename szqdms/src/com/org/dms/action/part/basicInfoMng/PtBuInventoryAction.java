package com.org.dms.action.part.basicInfoMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBuInventoryDao;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.dms.vo.part.PtBuInventoryDtlVO;
import com.org.dms.vo.part.PtBuInventoryVO;
import com.org.dms.vo.part.PtBuStockDtlVO;
import com.org.dms.vo.part.PtBuStockVO;
import com.org.frameImpl.Constant;
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

public class PtBuInventoryAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "PtBaInfoAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBuInventoryDao dao = PtBuInventoryDao.getInstance(atx);   
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);  	
        try
        {
        	PtBuInventoryVO vo = new PtBuInventoryVO();
//            PtBaInfoVO_Ext extvo = new PtBaInfoVO_Ext();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			String inventory_id = dao.getId();
			String inventory_no = dao.createInventory_No(hm.get("WAREHOUSE_CODE"));
			
			vo.setInventoryId(inventory_id);
			vo.setInventoryNo(inventory_no);
			
			vo.setCompanyId(user.getCompanyId());
			vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			vo.setStatus(DicConstant.YXBS_01);		

			dao.insertPtBuInventory(vo);			
			atx.setOutMsg(vo.getRowXml(),"配件库存盘点信息新增成功！");  		

        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);            
        }
    }
 //新增全部
    public void insertAll() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);  	
        try
        {
        	PtBuInventoryVO vo = new PtBuInventoryVO();
//            PtBaInfoVO_Ext extvo = new PtBaInfoVO_Ext();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			String inventory_id = dao.getId();
			String inventory_no = dao.createInventory_No(hm.get("WAREHOUSE_CODE"));
			
			vo.setInventoryId(inventory_id);
			vo.setInventoryNo(inventory_no);
			
			vo.setCompanyId(user.getCompanyId());
			vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());			
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			vo.setStatus(DicConstant.YXBS_01);		

			dao.insertPtBuInventory(vo);			
			dao.insertAll(inventory_id, vo.getWarehouseId(),inventory_no);
			dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_01.toString());
			atx.setOutMsg(vo.getRowXml(),"配件库存盘点信息新增成功！");  		

        }
        catch (Exception e)
        {
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
    	PtBuInventoryVO tempVO = new PtBuInventoryVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
            dao.updatePtBuInventory(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件库存盘点信息修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //盘点开始
    public void checkStart() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
            dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_02.toString());//更新盘点表状态
            dao.updatePaperCount(inventory_id);//更新账面数量
            dao.lockStock(inventory_id);//更新库存配件锁定状态
            atx.setOutMsg("","盘点开始！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //盘点结束
    public void checkOver() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
            dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_03.toString());
            atx.setOutMsg("","盘点结束！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //盘点结果封存
    public void checkConfirm() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
            dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_06.toString());
            atx.setOutMsg("","盘点结果封存！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //盘点调整完成
    public void checkTrim() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
            dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_04.toString());
            
            QuerySet qs = dao.getStockDtlId(inventory_id);
            if(qs.getRowCount() > 0 ){
            	for(int i=0;i<qs.getRowCount();i++){
            		String stock_dtl_id = qs.getString(i+1,"STOCK_DTL_ID");			//库存明细主键
            		String material_count = qs.getString(i+1,"MATERIAL_COUNT");		//实盘数量
            		if(!"".equals(material_count)&&material_count!=null){
            		String	stock_id = dao.getStockId(stock_dtl_id);				//库存主键
            		
            		String occupy_amount = dao.getAvailableAmount(stock_id);		//占用数量
            		String available_amount = String.valueOf(Integer.parseInt(material_count)-Integer.parseInt(occupy_amount));		//可用库存
            		
            		PtBuStockDtlVO dtlvo = new PtBuStockDtlVO();
            		dtlvo.setDtlId(stock_dtl_id);
            		dtlvo.setAmount(material_count);
            		dtlvo.setAvailableAmount(available_amount);
//            		dtlvo.setUpdateUser(user.getPersonName());
//            		dtlvo.setUpdateTime(Pub.getCurrentDate());
            		dao.updateStockDtlAmont(dtlvo);
            		
            		PtBuStockVO	 vo = new PtBuStockVO();
            		vo.setStockId(stock_id);
            		vo.setAmount(material_count);
            		vo.setAvailableAmount(available_amount);
//            		vo.setUpdateUser(user.getPersonName());
//            		vo.setUpdateTime(Pub.getCurrentDate());
            		dao.updateStockAmont(vo);
            		}
            		
            	}
            	
            }
            dao.updateAmount(inventory_id);//更新账面金额，实盘金额，计划价
            dao.unlockStock(inventory_id);//盘点锁定解除
            atx.setOutMsg("","盘点调整完成！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //盘点调整
    public void adjust() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
            dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_04.toString());
            atx.setOutMsg("","调整完成！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //审核通过
    public void checkUpdatePass() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	PtBuInventoryVO vo = new PtBuInventoryVO();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
        	HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
            //更新配件档案信息为无效状态
            dao.checkUpdatePass(inventory_id, vo.getCheckRemarks(),DicConstant.PDZT_05.toString());
            atx.setOutMsg("","审核通过！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //审核驳回
    public void checkUpdateNoPass() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	PtBuInventoryVO vo = new PtBuInventoryVO();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
        	HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
            //更新配件档案信息为无效状态
            dao.checkUpdateNoPass(inventory_id, vo.getCheckRemarks(),DicConstant.PDZT_03.toString());
            atx.setOutMsg("","审核驳回！");
        }
        catch (Exception e)
        {
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
        String inventory_id = Pub.val(request, "inventory_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBuInventoryStatus(inventory_id, DicConstant.YXBS_02); 
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //删除库存盘点明细
    public void dtlDelete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String DTL_ID = Pub.val(request, "DTL_ID");
        try
        {
            //更新配件档案信息为无效状态
            dao.dtlDelete(DTL_ID); 
            atx.setOutMsg("","删除成功！");
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
  //盘点结果查询
    public void searchResult() throws Exception
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
			BaseResultSet bs = dao.searchResult(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //盘点财务确认查询
    public void searchSeale() throws Exception
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
			BaseResultSet bs = dao.searchSeale(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //盘点结果封存查询
    public void searchConfirm() throws Exception
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
			BaseResultSet bs = dao.searchConfirm(page,conditions);
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
    public void searchExp() throws Exception
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
			BaseResultSet bs = dao.searchExp(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //展示已添加信息
    public void partList() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String inventory_id = Pub.val(request, "inventory_id");
		try
		{
			BaseResultSet bs = dao.partList(page, conditions,inventory_id);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //查找配件
    public void partSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String warehouse_id = Pub.val(request, "warehouse_id");
		try
		{
			BaseResultSet bs = dao.partSearch(page, conditions,warehouse_id);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //批量修改
    public void batchUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
//    	User user = (User) atx.getSession().get(Globals.USER_KEY);
//        String creatUser = user.getPersonName();
        
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 	 			
 			String  dtl_ids= hm.get("DTL_IDS");
 			String  material_counts= hm.get("MATERIAL_COUNTS");
 			String[] dtl_id = dtl_ids.split(",");
            String[] material_count = material_counts.split(",");
            for(int i=0;i<dtl_id.length ;i++){
            	String dtlId=dtl_id[i];
            	QuerySet qss = dao.getDate(dtlId);
            	String PAPER_COUNT = qss.getString(1,"PAPER_COUNT");	 //账面数量
            	String materialCount=material_count[i];					 //实盘数量	
            	
//            	float PAPER_AMOUNT = Float.parseFloat(PLAN_PRICE)*Float.parseFloat(PAPER_COUNT);			//账面金额
//		    	float MATERIAL_AMOUNT = Float.parseFloat(PLAN_PRICE)*Float.parseFloat(materialCount);		//实物金额
		    	int pCount =Integer.parseInt(PAPER_COUNT);
		    	int mCount = Integer.parseInt(materialCount);
		    	
		    	PtBuInventoryDtlVO vo = new PtBuInventoryDtlVO();
		    		vo.setDtlId(dtlId);
		    		vo.setMaterialCount(materialCount);
		    		
		    	if(pCount < mCount){
	    			vo.setInventoryResult(DicConstant.PDJG_01);					//盘盈
	    		}else if(pCount > mCount){
	    			vo.setInventoryResult(DicConstant.PDJG_02);					//盘亏
	    		}else if(pCount == mCount){
	    			vo.setInventoryResult(DicConstant.PDJG_03);					//相符
	    		}			
            	
		    	dao.updateImportPtBuDtlFromTmp(vo);
            }
            atx.setOutMsg("","库存盘点明细信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    //批量新增
    public void batchInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
//        PtBuInventoryDtlVO dtlvo = new PtBuInventoryDtlVO();  
        String dtl_id = Pub.val(request, "dtl_id");
        String inventory_id = Pub.val(request, "inventory_id");
        String warehouse_id = Pub.val(request, "warehouse_id");
        String inventory_no = Pub.val(request, "inventory_no");
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
			dao.batchInsertPchAttribute(dtl_id, inventory_id, warehouse_id, inventory_no);
			dao.checkInventoryStatus(inventory_id,DicConstant.PDZT_01.toString());
            atx.setOutMsg("","批量新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    //批量删除
    public void batchDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String dtl_id = Pub.val(request, "dtl_id");
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
			dao.batchDelete(dtl_id);
            atx.setOutMsg("","批量删除成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 库存盘点设置导出
     * @throws Exception
     */
    public void checkDownload()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String warehouse_id = Pub.val(request, "warehouse_id");
    	String inventory_id = Pub.val(request, "inventory_id");
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("DTL_ID");
            hBean.setTitle("库存明细主键");
            header.add(1,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AREA_CODE");
            hBean.setTitle("库区代码");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("POSITION_NAME");
            hBean.setTitle("库位名称");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("USER_NAME");
            hBean.setTitle("库管员");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("库存数量");
            header.add(9,hBean);
            
            
//            hBean = new HeaderBean();
//            hBean.setName("inventory_id");
//            hBean.setTitle("盘点主键");
//            header.add(10,hBean);
            
            QuerySet qs = dao.checkDownload(conditions,warehouse_id);
            ExportManager.exportFile(response.getHttpResponse(), "partStockMessage", header, qs);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    //验证配件采购价格明细零时表数据的准确性、此处调用searchTmp
//    public List<ExcelErrors> checkData()throws Exception{
//    	User user = (User) atx.getSession().get(Globals.USER_KEY);
//    	ExcelErrors errors = null;
//		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
//		QuerySet qs = dao.searchTmp(user, "");//查询此用户下的所临时表信息
//		if(qs.getRowCount()>0){
//			for(int i=0;i<qs.getRowCount();i++){
//				errors=new ExcelErrors();
//				String p="^(0|[1-9][0-9]*)$";//校验钱
//				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
//				String DTL_ID = qs.getString(i+1, "DTL_ID"); //库存明细主键
//
//				
//	   //1.必填项空校验
//				//配件代码
//				if(null==DTL_ID || "".equals(DTL_ID)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("库存明细主键不能为空!");
//                    errorList.add(errors);
//                }
//				
//		//2.校验盘点明细主键是否存在
//				QuerySet qs1 = dao.getDtlId(DTL_ID);
//				if(qs1.getRowCount() == 0){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("该条信息错误，请核对信息!");
//                    errorList.add(errors);
//				}
//		
//			}
//		//3.重复数据校验,零时表中存在相同的(库存明细主键)则必须删除一个		
//			QuerySet qs2 = dao.searchCheckTmpRepeatData(user,""); 
//			if(qs2.getRowCount() > 0)
//			{
//				for(int j=0; j<qs2.getRowCount(); j++){
//					String dtlId = qs2.getString(j+1, "DTL_ID"); //配件库存明细主键
//					
//					String errorStr = "";
//					QuerySet qs3 = dao.searchCheckTmpRepeatData(user, dtlId);
//					for(int k=0; k<qs3.getRowCount(); k++){
//						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
//						
//						errors=new ExcelErrors();
//						errors.setRowNum(Integer.parseInt(rowNum3));
//						
//						if (k != (qs3.getRowCount() -1)) {
//							errors.setErrorDesc("配件库存明细主键是重复数据!");
//							errorList.add(errors);
//						}
//						
//						errorStr = errorStr + rowNum3 + ",";
//					}
//					
//					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
//					errorStr = errorStr + "配件库存明细主键重复，请删除重复数据！重复行是("+errorStr+")！";
//					
//					//添加错误描述
//					errors.setErrorDesc(errorStr);
//					errorList.add(errors);
//				}
//			}
//		}
//		
//		if(errorList!=null && errorList.size()>0){
//			return errorList;
//		}else{
//			return null;
//		}
//    }
    //零时表正确信息查询
    public void searchCheckTmpImport() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchCheckTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //确定按钮导入
    public void checkImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String inventory_id=Pub.val(request, "inventory_id");
    	QuerySet qs1 = dao.getInventoryDate(inventory_id);
		String inventory_no = qs1.getString(1,"INVENTORY_NO");
		String warehouse_id = qs1.getString(1,"WAREHOUSE_ID");
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchTmp(user, errorInfoRowNum);//查询此用户下的所有临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		String dtl_id = qs.getString(i+1,"DTL_ID");
 		    		dao.batchInsertPchAttribute(dtl_id, inventory_id, warehouse_id, inventory_no);
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
    //库存盘点错误信息导出
    public void expCheckTmpErrorData()throws Exception{

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
            hBean.setName("DTL_ID");
            hBean.setTitle("库存明细主键");
            header.add(1,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AREA_CODE");
            hBean.setTitle("库区代码");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("POSITION_NAME");
            hBean.setTitle("库位名称");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("USER_NAME");
            hBean.setTitle("库管员");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("库存数量");
            header.add(9,hBean);
            
            QuerySet querySet = dao.expCheckTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "partStockErrorMessage", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void modDownload()throws Exception{

    	// 定义request对象
    	ResponseWrapper response = atx.getResponse();
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        String inventory_id = Pub.val(request, "inventory_id");
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
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("AREA_CODE");
    		hBean.setTitle("库区代码");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("POSITION_NAME");
    		hBean.setTitle("库位名称");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("WHOUSE_KEEPER");
    		hBean.setTitle("库管员");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("PLAN_PRICE");
    		hBean.setTitle("计划价");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("PAPER_COUNT");
    		hBean.setTitle("账面数量");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("MATERIAL_COUNT");
    		hBean.setTitle("实盘数量");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("CYSL");
    		hBean.setTitle("差异数量");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("PAPER_AMOUNT");
    		hBean.setTitle("账面金额");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("MATERIAL_AMOUNT");
    		hBean.setTitle("实盘金额");
    		header.add(10,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("CYJE");
    		hBean.setTitle("差异金额");
    		header.add(11,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("INVENTORY_RESULT");
    		hBean.setTitle("盘点结果");
    		header.add(12,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("REMARKS");
    		hBean.setTitle("差异原因");
    		header.add(13,hBean);
            
            QuerySet querySet = dao.modDownload(inventory_id,conditions);
            QuerySet getNo = dao.getNo(inventory_id);
            ExportManager.exportFile(response.getHttpResponse(), "第"+getNo.getString(1, "INVENTORY_NO")+"库存盘点明细表", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    //partSearch
    public void partCountSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String inventory_id = Pub.val(request, "inventory_id");
		try
		{
			BaseResultSet bs = dao.partCountSearch(page, conditions,inventory_id);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //addMCount
    public void addMCount() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
//    	User user = (User) atx.getSession().get(Globals.USER_KEY);
//        String creatUser = user.getPersonName();
        
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 	 			
 			String  dtl_ids= hm.get("DTL_IDS");
 			String  material_counts= hm.get("MATERIAL_COUNTS");
 			String  remarks= hm.get("REMARKS");
 			String[] dtl_id = dtl_ids.split(",");
            String[] material_count = material_counts.split(",");
            String[] remark = remarks.split(",");
            for(int i=0;i<dtl_id.length ;i++){
            	String dtlId=dtl_id[i];
            	QuerySet qss = dao.getDate(dtlId);
            	String PAPER_COUNT = qss.getString(1,"PAPER_COUNT");	 //账面数量
            	String materialCount=material_count[i];					 //实盘数量	
		    	int pCount =Integer.parseInt(PAPER_COUNT);
		    	int mCount = Integer.parseInt(materialCount);
		    	
		    	PtBuInventoryDtlVO vo = new PtBuInventoryDtlVO();
	    		vo.setDtlId(dtlId);
	    		vo.setMaterialCount(materialCount);
	    		if(!"nu".equals(remark[i])){
	    			vo.setRemarks(remark[i]);
            	}
		    	if(pCount < mCount){
	    			vo.setInventoryResult(DicConstant.PDJG_01);					//盘盈
	    		}else if(pCount > mCount){
	    			vo.setInventoryResult(DicConstant.PDJG_02);					//盘亏
	    		}else if(pCount == mCount){
	    			vo.setInventoryResult(DicConstant.PDJG_03);					//相符
	    		}			
		    	dao.updateImportPtBuDtlFromTmp(vo);
            }
            atx.setOutMsg("","实盘数量录入成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

}

