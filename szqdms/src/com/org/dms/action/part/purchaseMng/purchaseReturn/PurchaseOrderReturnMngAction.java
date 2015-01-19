package com.org.dms.action.part.purchaseMng.purchaseReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.purchaseMng.purchaseReturn.PurchaseOrderReturnMngDao;
import com.org.dms.vo.part.PtBuPchReturnDtlVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
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

public class PurchaseOrderReturnMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderReturnMngDao dao = PurchaseOrderReturnMngDao.getInstance(atx);

	    /**
	     * 
	     * @date()2014年7月22日下午5:12:33
	     * @author Administrator
	     * @to_do:采购退货单查询
	     * @throws Exception
	     */
	    public void returnOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.returnOrderSearch(page,user,conditions);
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
	     * @date()2014年7月23日下午5:06:57
	     * @author Administrator
	     * @to_do:原采购订单查询
	     * @throws Exception
	     */
	    public void purchaseSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			String SUPPLIER_ID = Pub.val(request,"supplierId");
			try
			{
				BaseResultSet bs = dao.purchaseSearch(page,user,conditions,SUPPLIER_ID);
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
	     * @date()2014年7月28日上午9:53:37
	     * @author Administrator
	     * @to_do:待选择退货件
	     * @throws Exception
	     */
	    public void searchPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            String RETURN_ID = Pub.val(request, "RETURN_ID");
	            String SUPPLIER_ID = Pub.val(request, "SUPPLIER_ID");
	            QuerySet qs = dao.queryId();
				String WAREHOUSE_ID = qs.getString(1, "WAREHOUSE_ID");
	            BaseResultSet bs = dao.searchPart(page, user, conditions,SUPPLIER_ID,RETURN_ID,WAREHOUSE_ID);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月28日上午10:24:40
	     * @author Administrator
	     * @to_do:退货单新增
	     * @throws Exception
	     */
	    public void purchaseReturnOrderInsert() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String supplierCode = hm.get("SUPPLIER_CODE");
				QuerySet checkPch = dao.checkRet(supplierCode,user);
				if(checkPch.getRowCount()>0){
					throw new Exception("该供应商有未提报的采购退货单，不能新增采购退货单.");
				}else{
					String RETURN_NO = PartOddNumberUtil.getPurchaseReturnOrderNo(atx.getDBFactory(),supplierCode );
		        	PtBuPchReturnVO vo = new PtBuPchReturnVO();
					vo.setValue(hm);
					vo.setReturnNo(RETURN_NO);
					vo.setReturnStatus(DicConstant.CGTHDZT_01);
					vo.setSignStauts(DicConstant.THDYSZT_02);
					vo.setSettleStatus(DicConstant.GYSJSZT_01);
					vo.setStatus(DicConstant.YXBS_01);
					vo.setCompanyId(user.getCompanyId());
					vo.setOemCompanyId(user.getOemCompanyId());
					vo.setCreateUser(user.getAccount());
					vo.setCreateTime(Pub.getCurrentDate());//
					vo.setOrgId(user.getOrgId());
					vo.setOrderUser(user.getAccount());
					vo.setOrderDate(Pub.getCurrentDate());
					dao.insertReturnOrder(vo);
					atx.setOutMsg(vo.getRowXml(),"退货单新增成功！");
				}
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月23日下午8:32:35
	     * @author Administrator
	     * @to_do:采购退货单修改
	     * @throws Exception
	     */
	    public void purchaseReturnOrderUpdate() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchReturnVO tempVO = new PtBuPchReturnVO();
	        try
	        {
	            HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				tempVO.setValue(hm);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.purchaseReturnOrderUpdate(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"退货单修改成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月23日下午8:48:52
	     * @author Administrator
	     * @to_do:退货单配件查询
	     * @throws Exception
	     */
	    public void returnPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			try
			{
				String RETURN_ID = Pub.val(request, "RETURN_ID");
				String SUPPLIER_ID = Pub.val(request, "SUPPLIER_ID");
				QuerySet qs = dao.queryId();
				String WAREHOUSE_ID = qs.getString(1, "WAREHOUSE_ID");
				page.setPageRows(10000);
				BaseResultSet bs = dao.returnPartSearch(page,user,RETURN_ID,SUPPLIER_ID,WAREHOUSE_ID);
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
	     * @date()2014年7月28日上午10:25:06
	     * @author Administrator
	     * @to_do:退货单配件新增
	     * @throws Exception
	     */
	    public void insertOrderPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	
	        	QuerySet getW_ID = dao.queryId();
				String WAREHOUSE_ID = getW_ID.getString(1, "WAREHOUSE_ID");
	        	
	        	HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String RETURN_ID = hm.get("RETURNID");//退货单ID
	            String SUPPLIER_ID = hm.get("SUPPLIERID");
	            String PART_IDS = hm.get("PARTIDS");
	            String PARTCODES = hm.get("PARTCODES");
	            String PARTNAMES = hm.get("PARTNAMES");
	            String PCH_PRICES = hm.get("PCH_PRICE");
	            String PLAN_PRICES = hm.get("PLAN_PRICE");
	            String[] PART_ID = PART_IDS.split(",");
	            String[] PART_CODE = PARTCODES.split(",");
	            String[] PART_NAME = PARTNAMES.split(",");
	            String[] PCH_PRICE = PCH_PRICES.split(",");
	            String[] PLAN_PRICE = PLAN_PRICES.split(",");
	            String[] SUPPLIER_IDS = SUPPLIER_ID.split(",");
	            for(int i=0;i<PART_ID.length;i++){
	            	QuerySet qs1=dao.queryPid(PART_ID[i],WAREHOUSE_ID);
	            	if(qs1.getRowCount()>0){
	            		for(int j=0;j<qs1.getRowCount();j++){
	            			String pId = qs1.getString(j+1, "POSITION_ID");
	            			String SUPPLIER_CODE=null;
	    		            String SUPPLIER_NAME=null;
	    		            String pCode=null;
	    		            String pName=null;
	    		            QuerySet qs3=dao.queryPositions(pId);
	    		            pCode=qs3.getString(1, "POSITION_CODE");
	    		            pName=qs3.getString(1, "POSITION_NAME");
//	    	            	QuerySet qs =dao.querySupplier(PART_ID[i],pId);
	    		            QuerySet qs =dao.querySupplier1(PART_ID[i],pId);
	    	            	 if(qs.getRowCount()>0){
	    	            		SUPPLIER_ID = qs.getString(1, "SUPPLIER_ID");
	    	 	            	SUPPLIER_CODE=qs.getString(1, "SUPPLIER_CODE");
	    	 	            	SUPPLIER_NAME=qs.getString(1, "SUPPLIER_NAME");
	    	 	            }
	    		            PtBuPchReturnDtlVO vo= new PtBuPchReturnDtlVO();
	    		            vo.setPartId(PART_ID[i]);
	    		            vo.setPartCode(PART_CODE[i]);
	    		            vo.setPartName(PART_NAME[i]);
	    		            vo.setPositionId(pId);
	    		            vo.setPositionCode(pCode);
	    		            vo.setPositionName(pName);
	    		            vo.setPositionId(pId);
	    		            vo.setPositionCode(pCode);
	    		            vo.setPositionName(pName);
	    		            vo.setSupplierId(SUPPLIER_ID);
	    		            vo.setSupplierCode(SUPPLIER_CODE);
	    		            vo.setSupplierName(SUPPLIER_NAME);
	    		            vo.setReturnId(RETURN_ID);
	    		            vo.setPchPrice(PCH_PRICE[i]);
	    		            vo.setPlanPrice(PLAN_PRICE[i]);
	    		            vo.setCreateUser(user.getAccount());
	    		            vo.setCreateTime(Pub.getCurrentDate());
	    		            dao.insertPart(vo);
	            		}
	            	}
	            }
	            //返回插入结果和成功信息
	            atx.setOutMsg("", "新增成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 
	     * @date()2014年7月28日上午10:25:06
	     * @author Administrator
	     * @to_do:退货单配件新增(附带库位信息)
	     * @throws Exception
	     */
	    public void retAmountInsert() throws Exception {
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	try {
	    		HashMap<String, String> hm;
	    		hm = RequestUtil.getValues(request);
	    		String RETCOUNT = hm.get("RETCOUNT");
	    		String detailId = hm.get("DETAILIDS");
	    		String POSITIONIDS = hm.get("POSITIONIDS");
	    		String yAmounts = hm.get("YAMOUNT");//原退货数量(逗号分隔)
	    		String remark = hm.get("REMARKS");
	    		String[] positionId = POSITIONIDS.split(",");
	    		String[] dtlId = detailId.split(",");
	    		String[] retCount=RETCOUNT.split(",");
        		String[] yAmount=yAmounts.split(",");
	    		for(int i=0;i<dtlId.length;i++){
	    			if(!dtlId[i].equals("")){
		    			dao.insertParts(dtlId[i],positionId[i],user,retCount[i],remark);
		    			QuerySet qs=dao.queryContent(dtlId[i]);
		    			String pId=qs.getString(1, "PART_ID");//配件ID
		    			String sId=qs.getString(1, "SUPPLIER_ID");//供应商ID
		    			//更新库存明细冻结数量(可能增加或者减少,yAmount-outAmount)
		        		dao.updateLockStockDtlByOutBillDtl(pId, sId, positionId[i], yAmount[i], retCount[i], user);
		    			//更新库存冻结数量(可能增加或者减少,yAmount-outAmount)
		        		dao.updateLockStockByOutBillDtl(pId, sId, positionId[i],yAmount[i], retCount[i], user);
	    			}
	    		}
	    		dao.deleteParts(detailId);
	    		//返回插入结果和成功信息
	    		atx.setOutMsg("", "新增成功！");
	    	} catch (Exception e) {
	    		atx.setException(e);
	    		logger.error(e);
	    	}
	    }
	    /**
	     * 
	     * @date()2014年7月28日上午10:25:06
	     * @author Administrator
	     * @to_do:删除退货明细
	     * @throws Exception
	     */
	    public void deleteParts() throws Exception {
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	try {
	    		String dtlIds=Pub.val(request, "dtlIds");
	    		String[] dtlId = dtlIds.split(",");
	    		for(int i=0;i<dtlId.length;i++){
		    		QuerySet qs = dao.queryDelete(dtlId[i]);
		    		String partId=qs.getString(1, "PART_ID");
		    		String returnId=qs.getString(1, "RETURN_ID");
		    		String supplierId=qs.getString(1, "SUPPLIER_ID");
		    		 //解锁库存明细(根据出库单主键+配件+供应商)
		            dao.unLockStockDtlByOutBillDtl(returnId,partId, supplierId,user);
		            //解锁库存(根据出库单主键+配件+供应商)
		            dao.unLockStockByOutBillDtl(returnId,partId, supplierId,user);
	    		}
	    		dao.deleteParts(dtlIds);
	    		//返回插入结果和成功信息
	    		atx.setOutMsg("", "删除成功！");
	    	} catch (Exception e) {
	    		atx.setException(e);
	    		logger.error(e);
	    	}
	    }
	    
	    /**
	     * 采购退货申请提报
	     * @throws Exception
	     */
	    public void purRetSubmit() throws Exception {
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	try {
	    		 HashMap<String, String> hm;
	             //将request流转换为hashmap结构体111
	             hm = RequestUtil.getValues(request);
	    		 String returnId=Pub.val(request, "returnId");
	    		 QuerySet checkZero = dao.checkZero(returnId);
				 String partCode = checkZero.getString(1, "PART_CODES");
				 if(!"".equals(partCode)&&partCode!=null){
					 throw new Exception("配件"+partCode+"退货数量为0.");
				 }
	    		 QuerySet qs=dao.queryAmount(returnId);
	             String amount=qs.getString(1, "AMOUNT");
	             String count=qs.getString(1, "COUNT");
	             String planAmount=qs.getString(1, "PLAN_AMOUNT");
	             PtBuPchReturnVO vo =new PtBuPchReturnVO();
	             vo.setReturnId(returnId);
	             vo.setReturnStatus(DicConstant.CGTHDZT_02);
	             vo.setUpdateTime(Pub.getCurrentDate());
	             vo.setUpdateUser(user.getAccount());
	             vo.setAmount(amount);
	             vo.setCount(count);
	             vo.setPlanAmount(planAmount);
	             dao.updateReturnOrder(vo);//修改退货单状态为已申请
		 	     atx.setOutMsg("1", "提报成功！");
	             
	    	} catch (Exception e) {
	    		atx.setException(e);
	    		logger.error(e);
	    	}
	    }
	    /**
	     * 
	     * @date()2014年7月23日下午9:09:33
	     * @author Administrator
	     * @to_do:退货单删除（同时删除主表跟明细表）
	     * @throws Exception
	     */
	    public void returnOrderDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String RETURN_ID = Pub.val(request, "RETURN_ID");
				QuerySet qs = dao.checkPart(RETURN_ID);
				if(qs.getRowCount()>0){
					dao.returnOrderPartDelete(RETURN_ID);
				}
	            dao.returnOrderDelete(RETURN_ID);
				atx.setOutMsg("","订单删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月29日下午2:53:22
	     * @author Administrator
	     * @to_do:采购退货单配件删除
	     * @throws Exception
	     */
	    public void partDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String DETAIL_ID = Pub.val(request, "DETAIL_ID");
				/**
				 * 更改退货总数采购金额计划总金额 BEGIN
				 */
				QuerySet prices = dao.geOldtPartAmount(DETAIL_ID);
				String COUNT = prices.getString(1, "COUNT");
				String PLAN_AMOUNT = prices.getString(1, "PLAN_AMOUNT");
				String AMOUNT = prices.getString(1, "AMOUNT");
				String RETURN_ID = prices.getString(1, "RETURN_ID");
				String R_AMOUNT = prices.getString(1, "R_AMOUNT");
				String R_COUNT = prices.getString(1, "R_COUNT");
				String R_PLAN_AMOUNT = prices.getString(1, "R_PLAN_AMOUNT");
				float new_planAmount = Float.parseFloat(R_PLAN_AMOUNT)-Float.parseFloat(PLAN_AMOUNT);
				float new_pchAmount = Float.parseFloat(R_AMOUNT)-Float.parseFloat(AMOUNT);
				int new_pchCount = Integer.parseInt(R_COUNT)-Integer.parseInt(COUNT);
				PtBuPchReturnVO tmpVO = new PtBuPchReturnVO();
				tmpVO.setReturnId(RETURN_ID);
				tmpVO.setPlanAmount(String.valueOf(new_planAmount));
				tmpVO.setAmount(String.valueOf(new_pchAmount));
				tmpVO.setCount(String.valueOf(new_pchCount));
				dao.updateReturnOrder(tmpVO);
				/**
				 * END
				 */
	            dao.delParts(DETAIL_ID);
				atx.setOutMsg("","配件删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void returnReport() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchReturnVO tempVO = new PtBuPchReturnVO();
	        try
	        {
	        	 
				 String RETURN_ID = Pub.val(request, "RETURN_ID");
				 tempVO.setReturnId(RETURN_ID);
				 tempVO.setReturnStatus(DicConstant.CGTHDZT_02);
				 tempVO.setUpdateUser(user.getAccount());
				 tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateReturnOrder(tempVO);
				atx.setOutMsg("","退货单提交成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月30日下午3:54:39
	     * @author Administrator
	     * @to_do:采购退货单导入配件查询
	     * @throws Exception
	     */
	    public void searchImport()throws Exception{
	 		PageManager page = new PageManager();
	 		User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		RequestWrapper request = atx.getRequest();
	 		try
	 		{
	 			String tmpNo = Pub.val(request,"tmpNo");
	 			BaseResultSet bs = dao.searchImport(page,user,tmpNo);
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
	     * @date()2014年7月30日下午3:57:50
	     * @author Administrator
	     * @to_do:将临时表数据插入采购退货明细表中
	     * @throws Exception
	     */
	    public void purchaseOrderPartImport()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	 		String RETURN_ID=Pub.val(request, "RETURN_ID");//退货单ID
	 		QuerySet querysId =dao.querySid(RETURN_ID);//查询供应商ID
			String sId = querysId.getString(1, "SUPPLIER_ID");//供应商ID
			QuerySet qs2=dao.querySupplier(sId);//查询供应商Code，Name
			QuerySet qs5 = dao.queryId();
			String wId = qs5.getString(1, "WAREHOUSE_ID");
			String SUPPLIER_CODE=null;
            String SUPPLIER_NAME=null;
			 if(qs2.getRowCount()>0){
	            	SUPPLIER_CODE=qs2.getString(1, "SUPPLIER_CODE");//供应商代码
	            	SUPPLIER_NAME=qs2.getString(1, "SUPPLIER_NAME");//供应商名称
	            }
	 		try
	 		{
	 			QuerySet qs=dao.queryTmp(user);
	 			if(qs.getRowCount()>0){
	 				for(int i=0;i<qs.getRowCount();i++){
	 					String pchPrice=null;
	 					String planPrice=null;
	 					String pCode=qs.getString(i+1, "PART_CODE");//配件代码
	 					String rAmount=qs.getString(i+1,"RETURN_COUNT");//退货数量
	 					String remark=qs.getString(i+1,"REMARKS");//备注
	 					String poCode=qs.getString(i+1, "POSITION_CODE");//库位代码
	 					QuerySet qs3=dao.queryPositionId(poCode);//查询库位ID，库位Name
    		            String poId=qs3.getString(1, "POSITION_ID");//库位ID
    		            String poName=qs3.getString(1, "POSITION_NAME");//库位Name
	 					QuerySet qs1=dao.queryPartId(pCode);//查询配件ID，配件Name
	 					String pId=qs1.getString(1, "PART_ID");//配件ID
	 					String pName=qs1.getString(1, "PART_NAME");//配件Name
	 					QuerySet qs4 = dao.queryPrices(pId,poId,sId,wId);//查询计划价，采购价
	 					if(qs4.getRowCount()>0){
	 						 pchPrice=qs4.getString(1, "PCH_PRICE");//采购价
		 					 planPrice=qs4.getString(1, "PLAN_PRICE");//计划价
	 					}
	 					QuerySet qs6 =dao.queryDtl(pId,poId,wId);
	 					String yrAmount=null;
	 					PtBuPchReturnDtlVO vo=new PtBuPchReturnDtlVO();
	 					if(qs6.getRowCount()>0){
	 						String dtlId=qs6.getString(1, "DETAIL_ID");
	 						yrAmount=qs6.getString(1, "RETURN_AMOUNT");
	 						vo.setDetailId(dtlId);
	 					}else{
	 						yrAmount="0";
	 					}
	 					vo.setReturnId(RETURN_ID);
	 					vo.setPartId(pId);
	 					vo.setPartCode(pCode);
	 					vo.setPartName(pName);
	 					vo.setReturnAmount(rAmount);
	 					vo.setRemarks(remark);
	 					vo.setCreateUser(user.getAccount());
	 					vo.setCreateTime(Pub.getCurrentDate());
	 					vo.setCount(String.valueOf(Integer.parseInt(rAmount)+Integer.valueOf(yrAmount)));
	 					vo.setSupplierId(sId);
	 					vo.setSupplierCode(SUPPLIER_CODE);
	 					vo.setSupplierName(SUPPLIER_NAME);
	 					vo.setPositionId(poId);
	 					vo.setPositionCode(poCode);
	 					vo.setPositionName(poName);
	 					vo.setPchPrice(pchPrice);
	 					vo.setPlanPrice(planPrice);
	 					vo.setAmount(String.valueOf(Integer.parseInt(rAmount)*Float.parseFloat(pchPrice)));
	 					vo.setPlanAmount(String.valueOf(Integer.parseInt(rAmount)*Float.parseFloat(planPrice)));
	 					if(qs6.getRowCount()>0){
	 						dao.updatePart(vo);
	 					}else{
	 						dao.insertPart(vo);
	 					}
	 					//更新库存明细冻结数量(可能增加或者减少,yAmount-outAmount)
		        		dao.updateLockStockDtlByOutBillDtl(pId, sId, poId, yrAmount, rAmount, user);
		    			//更新库存冻结数量(可能增加或者减少,yAmount-outAmount)
		        		dao.updateLockStockByOutBillDtl(pId, sId, poId,yrAmount, rAmount, user);
	 				}
	 			}
	 		}
	 		catch (Exception e)
	 		{
	 			logger.error(e);
	 			atx.setException(e);
	 		}
	    }
	    
	    public void expData()throws Exception{

	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
		    	User user = (User) atx.getSession().get(Globals.USER_KEY);
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
	            hBean.setName("POSITION_CODE");
	            hBean.setTitle("库位代码");
	            header.add(1,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("RETURN_COUNT");
	            hBean.setTitle("退货数量");
	            header.add(1,hBean);

	            QuerySet querySet = dao.expData(conditions,user);
	            ExportManager.exportFile(responseWrapper.getHttpResponse(), "pchRetPart", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
}
