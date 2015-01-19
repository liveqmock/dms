package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.ArmyPurchaseOrderDao;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderShippingVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
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

public class ArmyPurchaseOrderMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ArmyPurchaseOrderDao dao = ArmyPurchaseOrderDao.getInstance(atx);
    /**
     * 
     * @date()2014年7月11日下午4:29:48
     * @author Administrator
     * @to_do:采购订单查询(民品采购订单)
     * @throws Exception
     */
    public void orderSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.orderSearch(page,user,conditions);
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
     * @date()2014年7月14日下午12:04:27
     * @author Administrator
     * @to_do:订单已选择配件查询
     * @throws Exception
     */
    public void orderPartSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
			BaseResultSet bs = dao.orderPartSearch(page,user,PURCHASE_ID);
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
     * @date()2014年7月16日下午9:04:37
     * @author Administrator
     * @to_do:待选择配件查询
     * @throws Exception
     */
    public void searchPart() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
            String SUPPLIER_ID = Pub.val(request, "SUPPLIER_ID");
            BaseResultSet bs = dao.searchPart(page, user, conditions,SUPPLIER_ID,PURCHASE_ID);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 
     * @date()2014年7月11日下午4:46:13
     * @author Administrator
     * @to_do:采购订单新增(民品采购订单)
     * @throws Exception
     */
    public void purchaseOrderInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			//功过采购类型与采购类别生成采购订单号
			//begin
			String purchaseType = DicConstant.CGDDLX_04;
			String supplierCode = hm.get("SUPPLIER_CODE");
			QuerySet checkPch = dao.checkPch(purchaseType,supplierCode,user);
			if(checkPch.getRowCount()>0){
				QuerySet getDic  = dao.getDic(purchaseType);
				String code=  getDic.getString(1, "DIC_VALUE");
				throw new Exception("该供应商有未提报的"+code+"采购订单，不能新增"+code+"采购订单.");
			}else{
	        	String ORDER_NO = PartOddNumberUtil.getPurchaseOrderNo(atx.getDBFactory(),purchaseType,supplierCode );
	        	//end
	        	PtBuPchOrderVO vo = new PtBuPchOrderVO();
				vo.setValue(hm);
				vo.setOrderNo(ORDER_NO);
				vo.setPurchaseType(purchaseType);
				vo.setOrderType(DicConstant.CGDDLX_04);
				vo.setOrderStatus(DicConstant.CGDDZT_01);
				vo.setStatus(DicConstant.YXBS_01);
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());//
				vo.setOrgId(user.getOrgId());
				vo.setApplyDate(Pub.getCurrentDate());
				dao.insertPurchaseOrder(vo);
				atx.setOutMsg(vo.getRowXml(),"订单新增成功！");
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
     * @date()2014年7月11日下午4:49:10
     * @author Administrator
     * @to_do:采购订单修改(民品采购订单)
     * @throws Exception
     */
    public void purchaseOrderUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuPchOrderVO tempVO = new PtBuPchOrderVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());//
            dao.updatePurchaseOrder(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"订单修改成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 
     * @date()2014年7月11日下午4:55:36
     * @author Administrator
     * @to_do:采购订单删除(删除主表以及配件信息)
     * @throws Exception
     */
    public void purchaseOrderDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        try
        {
			String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
			QuerySet qs = dao.checkPart(PURCHASE_ID);
			if(qs.getRowCount()>0){
				dao.purchaseOrderPartDelete(PURCHASE_ID);
			}
            dao.purchaseOrderDelete(PURCHASE_ID);
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
     * @date()2014年7月14日下午8:40:48
     * @author Administrator
     * @to_do:采购订单新增配件
     * @throws Exception
     */
    public void insertOrderPart() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String PURCHASE_ID = hm.get("PURCHASID");//采购订单ID
            String PART_IDS = hm.get("PARTIDS");//配件ID
            String PART_CODES = hm.get("PARTCODES");//配件代码
            String PART_NAMES = hm.get("PARTNAMES");//配件名称
            String PCH_COUNTS = hm.get("PCHCOUNTS");//采购数量
            String PCH_PRICES = hm.get("PCHPRICES");//采购价格
            String PCH_AMOUNTS = hm.get("PCHAMOUNTS");//采购总价
            String DELIVERYCYCLES = hm.get("DELIVERYCYCLES");//供货周期
            String REMARKS = hm.get("REMARKS");//备注
            String[] PART_ID = PART_IDS.split(",");
            String[] PART_CODE = PART_CODES.split(",");
            String[] PART_NAME = PART_NAMES.split(",");
            String[] PCH_COUNT = PCH_COUNTS.split(",");
            String[] PCH_PRICE = PCH_PRICES.split(",");
            String[] PCH_AMOUNT = PCH_AMOUNTS.split(",");
            String[] DELIVERYCYCLE = DELIVERYCYCLES.split(",");
            String[] REMARK = REMARKS.split(",");
            float planAmount = 0;
            float amount = 0;
            int count = 0;
            
            for (int i = 0; i < PART_ID.length; i++) {
            	QuerySet qs_price = dao.getPlanPrice(PART_ID[i]);
            	float planPrice = Float.parseFloat(qs_price.getString(1, 1));
            	float plan = Integer.parseInt(PCH_COUNT[i])*planPrice;
            	
            	
            	Pattern pattern = Pattern.compile("[0-9]*");  
                Matcher matcher = pattern.matcher(DELIVERYCYCLE[i]);  
                if (matcher.matches() == false) {
                    QuerySet qs1 = dao.getDeliveryCyclNum(DELIVERYCYCLE[i]);
                    if(qs1.getRowCount()>0){
                    	DELIVERYCYCLE[i] = qs1.getString(1, "PARAVALUE2");
                    }
                }
            	
            	
            	PtBuPchOrderDtlVO pVo = new PtBuPchOrderDtlVO();
            	pVo.setPurchaseId(PURCHASE_ID);
            	pVo.setPartId(PART_ID[i]);
            	pVo.setPartCode(PART_CODE[i]);
            	pVo.setPartName(PART_NAME[i]);
            	pVo.setPchCount(PCH_COUNT[i]);
            	pVo.setPchPrice(PCH_PRICE[i]);
            	pVo.setPlanPrice(String.valueOf(planPrice));
            	pVo.setPchAmount(PCH_AMOUNT[i]);
            	pVo.setPlanAmount(String.valueOf(plan));
            	pVo.setDeliveryCycle(DELIVERYCYCLE[i]);
            	if(!"nu".equals(REMARK[i])){
            		pVo.setRemarks(REMARK[i]);
            	}
                dao.insertPart(pVo);
                
                //配件采购总数，采购总金额，计划采购总金额
                amount = amount+Float.parseFloat(PCH_AMOUNT[i]);
                count = count + Integer.parseInt(PCH_COUNT[i]);
                planAmount = planAmount + plan;
            }
            //将配件采购总数，采购总金额，计划采购总金额插入主表中
            PtBuPchOrderVO amoVo = new PtBuPchOrderVO();
            amoVo.setPurchaseId(PURCHASE_ID);
            amoVo.setPlanAmount(String.valueOf(planAmount));
            amoVo.setPurchaseAmount(String.valueOf(amount));
            amoVo.setPurchaseCount(String.valueOf(count));
            dao.updatePurchaseOrder(amoVo);
            //返回插入结果和成功信息
            atx.setOutMsg("", "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 
     * @date()2014年7月17日上午10:40:21
     * @author Administrator
     * @to_do:采购订单配件删除
     * @throws Exception
     */
    public void partDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        try
        {
			String DETAIL_ID = Pub.val(request, "DETAIL_ID");
			/**
			 * 更改采购总数采购金额计划总金额 BEGIN
			 */
			QuerySet prices = dao.geOldtPartAmount(DETAIL_ID);
			String PCH_COUNT = prices.getString(1, "PCH_COUNT");
			String PLAN_AMOUNT = prices.getString(1, "PLAN_AMOUNT");
			String PCH_AMOUNT = prices.getString(1, "PCH_AMOUNT");
			String PURCHASE_ID = prices.getString(1, "PURCHASE_ID");
			String P_PCH_AMOUNT = prices.getString(1, "P_PCH_AMOUNT");
			String P_PCH_COUNT = prices.getString(1, "P_PURCHASE_COUNT");
			String P_PLAN_AMOUNT = prices.getString(1, "P_PLAN_AMOUNT");
			float new_planAmount = Float.parseFloat(P_PLAN_AMOUNT)-Float.parseFloat(PLAN_AMOUNT);
			float new_pchAmount = Float.parseFloat(P_PCH_AMOUNT)-Float.parseFloat(PCH_AMOUNT);
			int new_pchCount = Integer.parseInt(P_PCH_COUNT)-Integer.parseInt(PCH_COUNT);
			PtBuPchOrderVO tmpVO = new PtBuPchOrderVO();
			tmpVO.setPurchaseId(PURCHASE_ID);
			tmpVO.setPlanAmount(String.valueOf(new_planAmount));
			tmpVO.setPurchaseAmount(String.valueOf(new_pchAmount));
			tmpVO.setPurchaseCount(String.valueOf(new_pchCount));
			dao.updatePurchaseOrder(tmpVO);
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
    /**
     * 
     * @date()2014年7月11日下午5:00:44
     * @author Administrator
     * @to_do:采购订单提报(民品采购订单)
     * @throws Exception
     */
    public void purchaseOrderReport() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuPchOrderVO tempVO = new PtBuPchOrderVO();
        try
        {
			String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
			/**
			 * 根据配件采购周期进行拆单begin
			 */
			//获取拆分单信息病插入拆分单主表
			/**
			 * 提交之前清除此采购单未提报的拆分单与拆分单明细信息
			 */
			dao.deleteSplitDtl(PURCHASE_ID);
			dao.deleteSplit(PURCHASE_ID);
			/*********************************/
			QuerySet qs = dao.getDelivery(PURCHASE_ID);
			for(int i =1;i<=qs.getRowCount();i++){
				String DELIVERY_CYCLE = qs.getString(i, "DELIVERY_CYCLE");
				String SELECT_MONTH = qs.getString(i, "SELECT_MONTH");
				String PURCHASE_TYPE = qs.getString(i, "PURCHASE_TYPE");
				String SUPPLIER_ID = qs.getString(i, "SUPPLIER_ID");
				String SUPPLIER_NAME = qs.getString(i, "SUPPLIER_NAME");
				String SUPPLIER_CODE = qs.getString(i, "SUPPLIER_CODE");
				Pattern pattern = Pattern.compile("[0-9]*");  
                Matcher matcher = pattern.matcher(DELIVERY_CYCLE);  
                if (matcher.matches() == false) {
                    QuerySet qs1 = dao.getDeliveryCyclNum(DELIVERY_CYCLE);
                    if(qs1.getRowCount()>0){
                    	DELIVERY_CYCLE = qs1.getString(1, "PARAVALUE2");
                    }
                } 
				
//				Date date=new   Date();//取时间 
				Date date = Pub.getCurrentDate();
		        Calendar   calendar   =   new   GregorianCalendar(); 
		     	calendar.setTime(date); 
		     	calendar.add(calendar.DATE,Integer.parseInt(DELIVERY_CYCLE));//当前日期加采购周期
		     	date=calendar.getTime();   //要求完成日期
				
				//生成拆分单号
				String ORDER_NO = qs.getString(i, "ORDER_NO");
				String SPLIT_NO = PartOddNumberUtil.getSplitNo(atx.getDBFactory(),ORDER_NO );
				//插入拆分单主表
				PtBuPchOrderSplitVO s_po = new PtBuPchOrderSplitVO();
				s_po.setPurchaseId(PURCHASE_ID);
				s_po.setSplitNo(SPLIT_NO);
				s_po.setOrderNo(ORDER_NO);
				s_po.setSelectMonth(SELECT_MONTH);
				s_po.setPurchaseType(PURCHASE_TYPE);
				s_po.setSupplierId(SUPPLIER_ID);
				s_po.setSupplierCode(SUPPLIER_CODE);
				s_po.setSupplierName(SUPPLIER_NAME);
				s_po.setDeliveryCycle(DELIVERY_CYCLE);
				s_po.setOrderType(PURCHASE_TYPE);
				s_po.setOrderStatus(DicConstant.CGDDZT_01);
				//s_po.setApplyUser(user.getAccount());
				//s_po.setApplyDate(Pub.getCurrentDate());
				s_po.setStatus(DicConstant.YXBS_01);
				s_po.setSettleStatus(DicConstant.GYSJSZT_01);
				s_po.setCompanyId(user.getCompanyId());
				s_po.setOemCompanyId(user.getOemCompanyId());
				s_po.setCreateUser(user.getAccount());
				s_po.setCreateTime(Pub.getCurrentDate());
				s_po.setOrgId(user.getOrgId());
				s_po.setRepuirementTime(date);
				dao.insertSplitOrder(s_po);
				//循环将拆分单明细数据插入拆分单明细表,同时将拆分单总采购量，总采购金额，总计划金额放入拆分单表中
				float planAmount = 0;//计划总金额
	            float amount = 0;//采购总金额
	            int count = 0;//总采购数
				String SPLIT_ID = s_po.getSplitId();
				
				QuerySet checkIfSQ = dao.checkIfSQ(SUPPLIER_ID);//校验是否为陕汽制造供应商，如果为是 直接变为出库状态
				
				QuerySet qs_p = dao.getSplitPart(PURCHASE_ID,DELIVERY_CYCLE);
				for(int j =1;j<=qs_p.getRowCount();j++){
					String PART_ID = qs_p.getString(j, "PART_ID");
					String PART_NAME = qs_p.getString(j, "PART_NAME");
					String PART_CODE = qs_p.getString(j, "PART_CODE");
					String PCH_COUNT = qs_p.getString(j, "PCH_COUNT");
					String PCH_PRICE = qs_p.getString(j, "PCH_PRICE");
					String PCH_AMOUNT = qs_p.getString(j, "PCH_AMOUNT");
					String REMARKS = qs_p.getString(j, "REMARKS");
					String IF_SUPLY = qs_p.getString(j, "IF_SUPLY");
					//获取计划单价
					QuerySet qs_price = dao.getPlanPrice(PART_ID,PURCHASE_ID);
					float planPrice = Float.parseFloat(qs_price.getString(1, 1));
					float plan = Integer.parseInt(PCH_COUNT)*planPrice;
					
					PtBuPchOrderSplitDtlVO s_p_vo = new PtBuPchOrderSplitDtlVO();
					s_p_vo.setSplitId(SPLIT_ID);
					s_p_vo.setPartId(PART_ID);
					s_p_vo.setPartCode(PART_CODE);
					s_p_vo.setPartName(PART_NAME);
					if(DicConstant.SF_01.equals(IF_SUPLY)){
						s_p_vo.setSupplierId(SUPPLIER_ID);
						s_p_vo.setSupplierCode(SUPPLIER_CODE);
						s_p_vo.setSupplierName(SUPPLIER_NAME);
					}else{
						QuerySet tmp = dao.getTmpSup("9XXXXXX");
						s_p_vo.setSupplierId(tmp.getString(1, "SUPPLIER_ID"));
						s_p_vo.setSupplierCode(tmp.getString(1, "SUPPLIER_CODE"));
						s_p_vo.setSupplierName(tmp.getString(1, "SUPPLIER_NAME"));
					}
					s_p_vo.setPchCount(PCH_COUNT);
					s_p_vo.setPchPrice(PCH_PRICE);
					s_p_vo.setPchAmount(PCH_AMOUNT);
					if(checkIfSQ.getRowCount()>0){
						s_p_vo.setShipCount(PCH_COUNT);
					}
					s_p_vo.setRemarks(REMARKS);
					s_p_vo.setPlanPrice(String.valueOf(planPrice));
					s_p_vo.setPlanAmount(String.valueOf(plan));
					dao.insertSplitOrderPart(s_p_vo);
					
					if(checkIfSQ.getRowCount()>0){
		            	PtBuPchOrderShippingVO s_vo = new PtBuPchOrderShippingVO();
		            	s_vo.setDetailId(s_p_vo.getDetailId());
		            	s_vo.setPurchaseId(SPLIT_ID);
		            	s_vo.setShippingAmount(PCH_COUNT);
		            	s_vo.setShippingMoney(PCH_AMOUNT);
		            	s_vo.setShipDate(Pub.getCurrentDate());
		            	dao.inserShipLog(s_vo);
					}
					//获取计划总金额，采购总金额，总采购数
	                planAmount = planAmount + plan;//计划总金额
	                amount = amount+Float.parseFloat(PCH_AMOUNT);//采购总金额
	                count = count + Integer.parseInt(PCH_COUNT);//总采购数
				}
				//更新拆分单总采购量，拆分单总采购金额，拆分单总计划金额
				
				PtBuPchOrderSplitVO amVo = new PtBuPchOrderSplitVO();
				amVo.setSplitId(SPLIT_ID);
				amVo.setPlanAmount(String.valueOf(planAmount));//计划总金额
				amVo.setPurchaseAmount(String.valueOf(amount));//采购总金额
				amVo.setPurchaseCount(String.valueOf(count));//总采购数
				if(checkIfSQ.getRowCount()>0){
					amVo.setOrderStatus(DicConstant.CGDDZT_04);
					amVo.setIfOnTime(DicConstant.SF_01);
					amVo.setFirstShipDate(Pub.getCurrentDate());
					amVo.setLastShipDate(Pub.getCurrentDate());
					amVo.setShipTimes("1");
				}
				dao.updatePurchaseOrderSplit(amVo);
				
			}
            /**
             * 根据配件采购周期进行拆单end
             */
			//主表状态修改
			tempVO.setPurchaseId(PURCHASE_ID);
			//tempVO.setOrderStatus(DicConstant.CGDDZT_02);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updatePurchaseOrder(tempVO);
			atx.setOutMsg("","订单提交成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    public void searchImport()throws Exception{
 		PageManager page = new PageManager();
 		RequestWrapper request = atx.getRequest();
 		RequestUtil.getConditionsWhere(request,page);
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			BaseResultSet bs = dao.searchImport(page,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    public void orderPartCountSave() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        HashMap<String, String> hm = RequestUtil.getValues(request);
        try {
        	String PURCHASE_ID = hm.get("PCH_ID");
			String DETAIL_ID = hm.get("DTL_ID");
			String COUNT = hm.get("COUNT");
			String REMARKS = hm.get("RMK"); 
            QuerySet getOld = dao.getOld(PURCHASE_ID,DETAIL_ID);
            String PCH_COUNT = getOld.getString(1, "PCH_COUNT");
            String PCH_PRICE = getOld.getString(1, "PCH_PRICE");
            String PLAN_PRICE = getOld.getString(1, "PLAN_PRICE");
            String PCH_AMOUNT = getOld.getString(1, "PCH_AMOUNT");
            String PLAN_AMOUNT = getOld.getString(1, "PLAN_AMOUNT");
            if(REMARKS==null){
            	REMARKS = "";
            }
            dao.updateOrderPart(DETAIL_ID,PURCHASE_ID,COUNT,REMARKS,user);
            /**
             * 更改订单主表中的采购数量采购总金额计划总金额
             */
            String NEW_PLAN_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PLAN_PRICE));
            String NEW_PCH_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PCH_PRICE));
            dao.updatePurAmount(PURCHASE_ID,PLAN_AMOUNT,NEW_PLAN_AMOUNT,PCH_AMOUNT,NEW_PCH_AMOUNT,PCH_COUNT,COUNT,user);
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    //dtlDownload
    public void dtlDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
		try
		{
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
			hBean.setName("UNIT");
			hBean.setTitle("单位");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("MIN_UNIT");
			hBean.setTitle("最小包装");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("PCH_PRICE");
			hBean.setTitle("采购价格");
			header.add(4,hBean);

			hBean = new HeaderBean();
			hBean.setName("PCH_COUNT");
			hBean.setTitle("采购数量");
			header.add(5,hBean);

			hBean = new HeaderBean();
			hBean.setName("PCH_AMOUNT");
			hBean.setTitle("金额");
			header.add(6,hBean);

			hBean = new HeaderBean();
			hBean.setName("DELIVERY_CYCLE");
			hBean.setTitle("供货周期");
			header.add(7,hBean);

			hBean = new HeaderBean();
			hBean.setName("REMARKS");
			hBean.setTitle("备注");
			header.add(8,hBean);
			QuerySet qs = dao.dtlDownload(PURCHASE_ID);
			ExportManager.exportFile(response.getHttpResponse(), "军品采购订单明细信息", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}

}
