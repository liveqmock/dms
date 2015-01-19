package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderModifyDao;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PurchaseOrderModifyAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PurchaseOrderModifyDao dao = PurchaseOrderModifyDao.getInstance(atx);
    /**
     * 
     * @date()2014年7月16日下午8:55:44
     * @author Administrator
     * @to_do:采购订单调整查询
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
     * @date()2014年7月16日下午8:56:00
     * @author Administrator
     * @to_do:采购订单配件查询
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
			String SPLIT_ID = Pub.val(request, "SPLIT_ID");
			BaseResultSet bs = dao.orderPartSearch(page,user,SPLIT_ID);
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
     * @date()2014年7月16日下午9:05:04
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
        	 HashMap<String, String> hm;
             hm = RequestUtil.getValues(request);
             String SPLIT_ID = Pub.val(request, "SPLIT_ID");
             String SUPPLIER_ID = Pub.val(request, "SUPPLIER_ID");
             String DELIVERY_CYCLE = hm.get("DELIVERY_CYCLE");
             BaseResultSet bs = dao.searchPart(page, user, conditions,SUPPLIER_ID,SPLIT_ID,DELIVERY_CYCLE);
             atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 
     * @date()2014年7月17日上午10:40:57
     * @author Administrator
     * @to_do:订单配件调整
     * @throws Exception
     */
    public void updateOrderSplitPart() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String SPLIT_ID = hm.get("SPLITID");//采购订单ID
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
            //获取原订单采购总金额，采购总数，计划总金额
            QuerySet old = dao.getOldAmount(SPLIT_ID);
            String PURCHASE_ID = old.getString(1,"PURCHASE_ID");
            float OLD_PLAN_AMOUNT = Float.parseFloat(old.getString(1, "PLAN_AMOUNT"));//原采购订单计划总金额
            float OLD_PURCHASE_AMOUNT = Float.parseFloat(old.getString(1, "PURCHASE_AMOUNT"));//原采购订单采购总金额
            float OLD_PURCHASE_COUNT = Float.parseFloat(old.getString(1, "PURCHASE_COUNT"));//原采购订单采购总数
            float S_OLD_PLAN_AMOUNT = Float.parseFloat(old.getString(1, "S_PLAN_AMOUNT"));//该采购拆分单计划总金额
            float S_OLD_PURCHASE_AMOUNT = Float.parseFloat(old.getString(1, "S_PURCHASE_AMOUNT"));//该采购拆分单采购总金额
            float S_OLD_PURCHASE_COUNT = Float.parseFloat(old.getString(1, "S_PURCHASE_COUNT"));//该采购拆分单采购总数量
            
            //调整后配件计划总金额，采购总数量，采购总金额
            float planAmount = 0;
            float amount = 0;
            int count = 0;
            
            for (int i = 0; i < PART_ID.length; i++) {
            	QuerySet qs_price = dao.getPlanPrice(PART_ID[i]);
            	float planPrice = Float.parseFloat(qs_price.getString(1, 1));
            	float plan = Integer.parseInt(PCH_COUNT[i])*planPrice;
            	PtBuPchOrderSplitDtlVO pVo = new PtBuPchOrderSplitDtlVO();
            	pVo.setSplitId(SPLIT_ID);
            	pVo.setPartId(PART_ID[i]);
            	pVo.setPartCode(PART_CODE[i]);
            	pVo.setPartName(PART_NAME[i]);
            	pVo.setPchCount(PCH_COUNT[i]);
            	pVo.setPchPrice(PCH_PRICE[i]);
            	pVo.setPchAmount(PCH_AMOUNT[i]);
            	pVo.setPlanAmount(String.valueOf(plan));
            	if(REMARK[i].equals("anull")){
            		pVo.setRemarks("");
            	}else{
            		pVo.setRemarks(REMARK[i]);
            	}
                dao.insertPart(pVo);
                
                //配件采购总数，采购总金额，计划采购总金额
                amount = amount+Float.parseFloat(PCH_AMOUNT[i]);
                count = count + Integer.parseInt(PCH_COUNT[i]);
                planAmount = planAmount + plan;
            }
            //将配件采购总数，采购总金额，计划采购总金额插入拆分表中
            PtBuPchOrderSplitVO amoVo = new PtBuPchOrderSplitVO();
            amoVo.setSplitId(SPLIT_ID);
            amoVo.setPlanAmount(String.valueOf(S_OLD_PLAN_AMOUNT+planAmount));
            amoVo.setPurchaseAmount(String.valueOf(S_OLD_PURCHASE_AMOUNT+amount));
            amoVo.setPurchaseCount(String.valueOf(S_OLD_PURCHASE_COUNT+count));
            dao.updatePurchaseOrderSplit(amoVo);
            //将调整后采购总数，采购总金额，计划总金额插入采购订单主表中
            PtBuPchOrderVO newVo = new PtBuPchOrderVO();
            newVo.setPurchaseId(PURCHASE_ID);
            newVo.setPlanAmount(String.valueOf(OLD_PLAN_AMOUNT+planAmount));
            newVo.setPurchaseAmount(String.valueOf(OLD_PURCHASE_AMOUNT+amount));
            newVo.setPurchaseCount(String.valueOf(OLD_PURCHASE_COUNT+count));
            newVo.setUpdateUser(user.getAccount());
            newVo.setUpdateTime(Pub.getCurrentDate());
            dao.updatePurchaseOrder(newVo);
            //返回插入结果和成功信息
            atx.setOutMsg("", "调整成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 
     * @date()2014年7月27日下午2:18:40
     * @author Administrator
     * @to_do:采购订单配件调整删除
     * @throws Exception
     */
    public void partDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        try
        {
			String DETAIL_ID = Pub.val(request, "DETAIL_ID");
			/**
			 * 更改采购拆分单采购总数采购金额计划总金额 BEGIN
			 */
			QuerySet prices = dao.getOldSplit(DETAIL_ID);
			String SPLIT_ID = prices.getString(1, "SPLIT_ID");
			String S_PCH_AMOUNT = prices.getString(1, "S_PCH_AMOUNT");
			String S_PCH_COUNT = prices.getString(1, "S_PCH_COUNT");
			String S_PLAN_AMOUNT = prices.getString(1, "S_PLAN_AMOUNT");
			PtBuPchOrderSplitVO tmpVO = new PtBuPchOrderSplitVO();
			tmpVO.setSplitId(SPLIT_ID);
			tmpVO.setPlanAmount(S_PLAN_AMOUNT);
			tmpVO.setPurchaseAmount(S_PCH_AMOUNT);
			tmpVO.setPurchaseCount(S_PCH_COUNT);
			dao.updatePurchaseOrderSplit(tmpVO);
			/**
			 * END
			 */
			/**
			 * 更改采购拆分单采购总数采购金额计划总金额 BEGIN
			 */
			QuerySet oldPurchase = dao.getOldPurchase(DETAIL_ID);
			String PURCHASE_ID = oldPurchase.getString(1, "PURCHASE_ID");
			String P_PCH_AMOUNT = oldPurchase.getString(1, "P_PURCHASE_AMOUNT");
			String P_PCH_COUNT = oldPurchase.getString(1, "P_PURCHASE_COUNT");
			String P_PLAN_AMOUNT = oldPurchase.getString(1, "P_PLAN_AMOUNT");
			PtBuPchOrderVO pchVO = new PtBuPchOrderVO();
			pchVO.setPurchaseId(PURCHASE_ID);
			pchVO.setPlanAmount(P_PLAN_AMOUNT);
			pchVO.setPurchaseAmount(P_PCH_AMOUNT);
			pchVO.setPurchaseCount(P_PCH_COUNT);
			dao.updatePurchaseOrder(pchVO);
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
     * @date()2014年7月27日下午2:31:55
     * @author Administrator
     * @to_do:订单调整成功
     * @throws Exception
     */
    public void orderModify ()throws Exception
	 {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        HashMap<String,String> hm;
		hm = RequestUtil.getValues(request);
       try{
       	String SPLIT_ID = Pub.val(request, "SPLIT_ID");
//       	String REPUIREMENT_TIME = Pub.val(request, "REPUIREMENT_TIME");
       	PtBuPchOrderSplitVO tmpVo = new PtBuPchOrderSplitVO();
//       	tmpVo.setSplitId(SPLIT_ID);
//       	tmpVo.setRepuirementTime(REPUIREMENT_TIME);
       	tmpVo.setSplitId(SPLIT_ID);
       	tmpVo.setValue(hm);
       	tmpVo.setOrderStatus(DicConstant.CGDDZT_02);
       	tmpVo.setUpdateTime(Pub.getCurrentDate());
       	tmpVo.setUpdateUser(user.getAccount());
       	dao.updatePurchaseOrderSplit(tmpVo);
       	atx.setOutMsg("","订单调整成功！");
       }catch(Exception e){
       	
       }
	 }
    public void orderPartCountSave() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        HashMap<String, String> hm = RequestUtil.getValues(request);
        try {
        	
			String SPLIT_ID = hm.get("PCH_ID");
			String DETAIL_ID = hm.get("DTL_ID");
			String COUNT = hm.get("COUNT");
			String REMARKS = hm.get("RMK");
			String PART_ID = hm.get("P_ID");
            QuerySet getOld = dao.getOld(SPLIT_ID,DETAIL_ID);
            String PCH_COUNT = getOld.getString(1, "PCH_COUNT");
            String PCH_PRICE = getOld.getString(1, "PCH_PRICE");
            String PLAN_PRICE = getOld.getString(1, "PLAN_PRICE");
            String PCH_AMOUNT = getOld.getString(1, "PCH_AMOUNT");
            String PLAN_AMOUNT = getOld.getString(1, "PLAN_AMOUNT");
            if(REMARKS==null){
            	REMARKS = "";
            }
            dao.updateSplOrderPart(DETAIL_ID,SPLIT_ID,COUNT,REMARKS,user);
            /**
             * 更改拆分单主表中的采购数量采购总金额计划总金额
             */
            String NEW_PLAN_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PLAN_PRICE));
            String NEW_PCH_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PCH_PRICE));
            dao.updateSplAmount(SPLIT_ID,PLAN_AMOUNT,NEW_PLAN_AMOUNT,PCH_AMOUNT,NEW_PCH_AMOUNT,PCH_COUNT,COUNT,user);
            /**
             * 更改采购单主表中的采购数量采购总金额计划总金额
             */
            QuerySet getPch = dao.getPch(SPLIT_ID,PART_ID);
            String PURCHASE_ID = getPch.getString(1, "PURCHASE_ID");
            String PCH_DETAIL_ID = getPch.getString(1, "DETAIL_ID");
            dao.updatePchOrderPart(PCH_DETAIL_ID,PURCHASE_ID,COUNT,REMARKS,user);
            dao.updatePurAmount(PURCHASE_ID,PLAN_AMOUNT,NEW_PLAN_AMOUNT,PCH_AMOUNT,NEW_PCH_AMOUNT,PCH_COUNT,COUNT,user);
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}
