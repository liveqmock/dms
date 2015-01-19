package com.org.dms.action.part.basicInfoMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBuInventoryDao;
import com.org.dms.dao.part.basicInfoMng.PtBuInventoryDtlDao;
import com.org.dms.vo.part.PtBaStockSafestocksVO;
import com.org.dms.vo.part.PtBuInventoryDtlVO;
import com.org.dms.vo.part.PtBuInventoryVO;
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

public class PtBuInventoryDtlAction {
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
            dao.updatePtBuInventoryStatus(inventory_id, Constant.YXBS_02); 
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
//    //批量新增
//    public void batchInsert() throws Exception
//    {
//    	RequestWrapper request = atx.getRequest();
//        User user = (User) atx.getSession().get(Globals.USER_KEY);
////        PtBuInventoryDtlVO dtlvo = new PtBuInventoryDtlVO();  
//        String partIds = Pub.val(request, "partIds");
//        String inventory_id = Pub.val(request, "inventory_id");
//        try
//        {
//        	HashMap<String,String> hm;
// 			hm = RequestUtil.getValues(request);
////			dao.batchInsertPchAttribute(partIds,inventory_id);
//			dao.batchInsertPchAttribute(partIds, inventory_id, warehouse_id);
//            
//            atx.setOutMsg("","批量新增成功！");
//        }
//        catch (Exception e)
//        {
//        	//设置失败异常处理
//        	atx.setException(e);
//            logger.error(e);
//        }
//    }
    
    
    //验证库存明细零时表数据的准确性、此处调用searchTmpDtl
//    public List<ExcelErrors> checkData()throws Exception{
//    	User user = (User) atx.getSession().get(Globals.USER_KEY);
//    	ExcelErrors errors = null;
//		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
//		QuerySet qs = dao.searchTmpDtl(user, "");//查询此用户下的所有库存临时表信息
//		if(qs.getRowCount()>0){
//			for(int i=0;i<qs.getRowCount();i++){
//				errors=new ExcelErrors();
//				String p="^(0|[1-9][0-9]*)$";//校验钱
//				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
//				String DTL_ID = qs.getString(i+1, "DTL_ID"); //盘点明细主键
//				String INVENTORY_NO = qs.getString(i+1, "INVENTORY_NO"); //盘点编号
//				String WHOUSE_KEEPER = qs.getString(i+1, "WHOUSE_KEEPER"); //库管员
//				String AREA_CODE = qs.getString(i+1, "AREA_CODE"); //库区代码
//				String POSITION_NAME = qs.getString(i+1, "POSITION_NAME"); //库位名称
//				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
//				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
//				String SUPPLIER_NAME = qs.getString(i+1, "SUPPLIER_NAME"); //供应商名称
//				String PLAN_PRICE = qs.getString(i+1,"PLAN_PRICE");//计划价
//				String PAPER_COUNT = qs.getString(i+1,"PAPER_COUNT");//账面数量
//				String MATERIAL_COUNT = qs.getString(i+1,"MATERIAL_COUNT");//实物数量
//				
////				String PAPER_AMOUNT = qs.getString(i+1,"PAPER_AMOUNT");//账面金额
////				String MATERIAL_AMOUNT = qs.getString(i+1,"MATERIAL_AMOUNT");//实物金额
//				
//				String REMARKS = qs.getString(i+1,"REMARKS");//差异原因
//				
//	    		
//	    		
////	    		String AMOUNT = qs.getString(i+1,"AMOUNT");//金额
////	    		String INVENTORY_RESULT = qs.getString(i+1,"INVENTORY_RESULT");//盘点结果
//	    		
//				
//				
//		//1.必填项空校验
//				//盘点明细主键
//				if(null==DTL_ID || "".equals(DTL_ID)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("盘点明细主键不能为空!");
//                    errorList.add(errors);
//                }
//				//盘点编号
//				if(null==INVENTORY_NO || "".equals(INVENTORY_NO)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("盘点编号不能为空!");
//                    errorList.add(errors);
//                }
//				//库管员
//				if(null==WHOUSE_KEEPER || "".equals(WHOUSE_KEEPER)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("库管员不能为空!");
//                    errorList.add(errors);
//                }
//				//库区代码
//				if(null==AREA_CODE || "".equals(AREA_CODE)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("库区代码不能为空!");
//                    errorList.add(errors);
//                }
//				//库位名称
//				if(null==POSITION_NAME || "".equals(POSITION_NAME)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("库位名称不能为空!");
//                    errorList.add(errors);
//                }
//				//配件代码
//				if(null==PART_CODE || "".equals(PART_CODE)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("配件代码不能为空!");
//                    errorList.add(errors);
//                }
//				//配件名称
//				if(null==PART_NAME || "".equals(PART_NAME)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("配件名称不能为空!");
//                    errorList.add(errors);
//                }
//				//供应商名称
//				if(null==SUPPLIER_NAME || "".equals(SUPPLIER_NAME)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("供应商名称不能为空!");
//                    errorList.add(errors);
//                }
//				//计划价
//				if(null==PLAN_PRICE || "".equals(PLAN_PRICE)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("计划价不能为空!");
//                    errorList.add(errors);
//                }
//				//账面数量
//				if(null==PAPER_COUNT || "".equals(PAPER_COUNT)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("账面数量不能为空!");
//                    errorList.add(errors);
//                }
//				//实物数量
//				if(null==MATERIAL_COUNT || "".equals(MATERIAL_COUNT)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("实物数量不能为空!");
//                    errorList.add(errors);
//                }
//				//差异原因
//				if(null==REMARKS || "".equals(REMARKS)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("差异原因不能为空!");
//                    errorList.add(errors);
//                }
//				
//				//2.校验盘点明细主键是否存在
//				QuerySet qs1 = dao.getDate(DTL_ID);
//				if(qs1.getRowCount() == 0){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("盘点明细主键不存在，请核对信息!");
//                    errorList.add(errors);
//				}
//				
//				
//			}
//				
//				//3.重复数据校验,零时表中存在相同的(盘点明细主键)则必须删除一个		
//				QuerySet qs2 = dao.searchTmpRepeatData(user,""); 
//				if(qs2.getRowCount() > 0)
//				{
//					for(int j=0; j<qs2.getRowCount(); j++){
//						String dtlId = qs2.getString(j+1, "DTL_ID"); //盘点明细主键
//						
//						String errorStr = "";
//						QuerySet qs3 = dao.searchTmpRepeatData(user, dtlId);
//						for(int k=0; k<qs3.getRowCount(); k++){
//							String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
//							
//							errors=new ExcelErrors();
//							errors.setRowNum(Integer.parseInt(rowNum3));
//							
//							if (k != (qs3.getRowCount() -1)) {
//								errors.setErrorDesc("盘点明细主键是重复数据!");
//								errorList.add(errors);
//							}
//							
//							errorStr = errorStr + rowNum3 + ",";
//						}
//						
//						errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
//						errorStr = errorStr + "盘点明细主键重复，请删除重复数据！重复行是("+errorStr+")！";
//						
//						//添加错误描述
//						errors.setErrorDesc(errorStr);
//						errorList.add(errors);
//					}
//				}
//				
//			}
//		
//			if(errorList!=null && errorList.size()>0){
//				return errorList;
//			}else{
//				return null;
//			}
//    }
    //库存明细错误信息导出
    public void expTmpErrorData()throws Exception{
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
    		hBean.setName("POSITION_CODE");
            hBean.setTitle("库位代码");
            header.add(0,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("MATERIAL_COUNT");
            hBean.setTitle("实物数量");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("REMARKS");
            hBean.setTitle("差异原因");
            header.add(4,hBean);
//            List<HeaderBean> header = new ArrayList<HeaderBean>();
//            HeaderBean hBean = null;
//	    	hBean = new HeaderBean();
//    		hBean.setName("ROW_NUM");
//    		hBean.setTitle("导入数据EXCEL行号");
//    		header.add(0,hBean);
//            
//      		
//      		hBean = new HeaderBean();
//      		hBean.setName("DTL_ID");
//            hBean.setTitle("盘点明细主键");
//            header.add(1,hBean);
//      		
//      		hBean = new HeaderBean();
//      		hBean.setName("INVENTORY_NO");
//            hBean.setTitle("盘点编号");
//            header.add(2,hBean);
//              
//            hBean = new HeaderBean();
//            hBean.setName("WHOUSE_KEEPER");
//            hBean.setTitle("库管员");
//            header.add(3,hBean);
//              
//            hBean = new HeaderBean();
//            hBean.setName("AREA_CODE");
//            hBean.setTitle("库区代码");
//            header.add(4,hBean);
//              
//            hBean = new HeaderBean();
//            hBean.setName("POSITION_NAME");
//            hBean.setTitle("库位名称");
//            header.add(5,hBean);
//              
//              
//            hBean = new HeaderBean();
//            hBean.setName("PART_CODE");
//            hBean.setTitle("配件代码");
//            header.add(6,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("PART_NAME");
//            hBean.setTitle("配件名称");
//            hBean.setWidth(50);
//            header.add(7,hBean);
//                         
//            hBean = new HeaderBean();
//            hBean.setName("SUPPLIER_NAME");
//            hBean.setTitle("供应商名称");
//            header.add(8,hBean);
//              
//            hBean = new HeaderBean();
//            hBean.setName("PLAN_PRICE");
//            hBean.setTitle("计划价");
//            header.add(9,hBean);
//              
//            hBean = new HeaderBean();
//            hBean.setName("PAPER_COUNT");
//            hBean.setTitle("账面数量");
//            header.add(10,hBean);
//              
//            hBean = new HeaderBean();
//            hBean.setName("MATERIAL_COUNT");
//            hBean.setTitle("实物数量");
//            header.add(11,hBean);
//              
//              
//            hBean = new HeaderBean();
//            hBean.setName("REMARKS");
//            hBean.setTitle("差异原因");
//            header.add(12,hBean);
//              
            QuerySet querySet = dao.expTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "库存明细错误信息", header, querySet);
            
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 库存明细导入临时表查询
     * @throws Exception
     * Author suoxiuli 2014-07-25
     */
    public void searchTmp() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String id = Pub.val(request, "ID");
		try
		{
			BaseResultSet bs = dao.searchTmpImport(page, conditions, user,id);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 导入确定按钮：把配件库存盘点设置明细临时表的数据放入到主表
     * 表数据需要改造为联合数据
     * @throws Exception
     */
    public void dtlImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 			String id = Pub.val(request, "ID");
 		    QuerySet qs = dao.searchTmpDtl(user, errorInfoRowNum,id);//查询此用户下的所有库存明细临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		String DTL_ID = qs.getString(i+1,"DTL_ID");							//盘点明细主键
 		    		String MATERIAL_COUNT = qs.getString(i+1,"MATERIAL_COUNT");			//实物数量
 		    		String PAPER_COUNT = qs.getString(i+1, "PAPER_COUNT");				//账面数量
 		    		String REMARKS = qs.getString(i+1,"REMARKS");						//备注(差异原因)
 		    		
// 		    		QuerySet qss = dao.getDate(DTL_ID);
// 		    		String PAPER_COUNT = qss.getString(1,"PAPER_COUNT");	 //账面数量
// 		    		String PLAN_PRICE = qss.getString(1,"PLAN_PRICE");	     //计划价
// 		    		
 		    		
// 		    		float PAPER_AMOUNT = Float.parseFloat(PLAN_PRICE)*Float.parseFloat(PAPER_COUNT);			//账面金额
// 		    		float MATERIAL_AMOUNT = Float.parseFloat(PLAN_PRICE)*Float.parseFloat(MATERIAL_COUNT);		//实物金额
 		    		
// 		    		double MATERIAL_AMOUNT = Double.parseDouble(PLAN_PRICE)*Double.parseDouble(MATERIAL_COUNT);
 		    		int MATERIAL_COUNT_1 = Integer.parseInt(MATERIAL_COUNT);
 		    		int PAPER_COUNT_1 = Integer.parseInt(PAPER_COUNT);
 		    		
 		    		//主表VO
 		    		PtBuInventoryDtlVO vo = new PtBuInventoryDtlVO();
 		    		vo.setDtlId(DTL_ID);
 		    		vo.setMaterialCount(MATERIAL_COUNT);
// 		    		vo.setPaperAmount(String.valueOf(PAPER_AMOUNT));
// 		    		vo.setMaterialAmount(String.valueOf(MATERIAL_AMOUNT));
 		    		if(!"".equals(REMARKS)&&REMARKS!=null){
 		    			vo.setRemarks(REMARKS);
 		    		}
 		    		
 		    		if(PAPER_COUNT_1 < MATERIAL_COUNT_1){
 		    			vo.setInventoryResult(DicConstant.PDJG_01);					//盘盈
 		    		}
 		    		if(PAPER_COUNT_1 > MATERIAL_COUNT_1){
 		    			vo.setInventoryResult(DicConstant.PDJG_02);					//盘亏
 		    		}
 		    		if(PAPER_COUNT_1 == MATERIAL_COUNT_1){
 		    			vo.setInventoryResult(DicConstant.PDJG_03);					//相符
 		    		}
 		    		
 		    		dao.updateImportPtBuDtlFromTmp(vo);
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
     * 库存盘点表导出
     * @throws Exception
     */
    public void download()throws Exception{

    	// 定义request对象
    	ResponseWrapper response = atx.getResponse();
    	RequestWrapper request = atx.getRequest();
//    	PageManager page = new PageManager();
//    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	
        String inventory_id = Pub.val(request, "inventory_id");
        
        try {
//            List<HeaderBean> header = new ArrayList<HeaderBean>();
//            HeaderBean hBean = null;
//            hBean = new HeaderBean();
//            hBean.setName("ROWNUM");
//    		hBean.setTitle("序号");
//    		header.add(0,hBean);
//    		
//    		hBean = new HeaderBean();
//    		hBean.setName("DTL_ID");
//            hBean.setTitle("盘点明细主键");
//            header.add(1,hBean);
//    		
//    		hBean = new HeaderBean();
//    		hBean.setName("INVENTORY_NO");
//            hBean.setTitle("盘点编号");
//            header.add(2,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("WHOUSE_KEEPER");
//            hBean.setTitle("库管员");
//            header.add(3,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("AREA_CODE");
//            hBean.setTitle("库区代码");
//            header.add(4,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("POSITION_NAME");
//            hBean.setTitle("库位名称");
//            header.add(5,hBean);
//            
//            
//            
//            
//            
//            hBean = new HeaderBean();
//            hBean.setName("PART_CODE");
//            hBean.setTitle("配件代码");
//            header.add(6,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("PART_NAME");
//            hBean.setTitle("配件名称");
//            hBean.setWidth(50);
//            header.add(7,hBean);
//                       
//            hBean = new HeaderBean();
//            hBean.setName("SUPPLIER_NAME");
//            hBean.setTitle("供应商名称");
//            header.add(8,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("PLAN_PRICE");
//            hBean.setTitle("计划价");
//            header.add(9,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("PAPER_COUNT");
//            hBean.setTitle("账面数量");
//            header.add(10,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("MATERIAL_COUNT");
//            hBean.setTitle("实物数量");
//            header.add(11,hBean);
//            
//            
//            hBean = new HeaderBean();
//            hBean.setName("REMARKS");
//            hBean.setTitle("差异原因");
//            header.add(12,hBean);
        	
        	
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("POSITION_CODE");
            hBean.setTitle("库位代码");
            header.add(0,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("MATERIAL_COUNT");
            hBean.setTitle("实物数量");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
    		hBean.setName("REMARKS");
            hBean.setTitle("差异原因");
            header.add(4,hBean);
            
            QuerySet querySet = dao.download(inventory_id);
            ExportManager.exportFile(response.getHttpResponse(), "库存盘点明细表", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void dtlDownload()throws Exception{

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
            
            QuerySet querySet = dao.dtlDownload(inventory_id);
            QuerySet getNo = dao.getNo(inventory_id);
            ExportManager.exportFile(response.getHttpResponse(), "第"+getNo.getString(1, "INVENTORY_NO")+"库存盘点明细表", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
}

