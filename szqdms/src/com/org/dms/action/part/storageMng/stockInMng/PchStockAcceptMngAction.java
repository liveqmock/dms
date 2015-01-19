package com.org.dms.action.part.storageMng.stockInMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.stockInMng.PchStockAcceptMngDao;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PchStockAcceptMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PchStockAcceptMngDao dao = PchStockAcceptMngDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年9月1日下午2:58:43
	     * @author Administrator
	     * @to_do:采购订单验收查询
	     * @throws Exception
	     */
	    public void orderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			HashMap<String, String> hm = RequestUtil.getValues(request);
			String partCode = hm.get("PART_CODE");
			String partName = hm.get("PART_NAME");
			try
			{
				String orgId = user.getOrgId();
				QuerySet checkOrg = dao.checkOrg(orgId);
				String warehouseType = checkOrg.getString(1, "WAREHOUSE_TYPE");
				BaseResultSet bs = dao.orderSearch(page,user,conditions,partCode,partName,warehouseType);
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
	     * @date()2014年9月1日下午2:58:51
	     * @author Administrator
	     * @to_do:采购订单验收
	     * @throws Exception
	     */
	    public void acceptPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String SPLIT_ID = hm.get("SPLITID");
	            String DETAIL_IDS = hm.get("DETAILIDS");
	            String SHIP_COUNTS = hm.get("ACCEPTCOUNTS");
	            String HADSHIPCOUNTS = hm.get("HADACCEPTCOUNTS");
	            String DISTRIBUTIONNOS = hm.get("DISTRIBUTIONNOS");
	            String[] DETAIL_ID = DETAIL_IDS.split(",");
	            String[] SHIP_COUNT = SHIP_COUNTS.split(",");
	            String[] HAD_SHIP_COUNT = HADSHIPCOUNTS.split(",");
	            String[] DISTRINBUTION_NO = DISTRIBUTIONNOS.split(",");
	            int counts = 0;
	            for (int i = 0; i < DETAIL_ID.length; i++) {
	            	QuerySet checkPosi = dao.CheckPosi(DETAIL_ID[i],SPLIT_ID,user);
	            	QuerySet checkPosi1 = dao.CheckPosi(DETAIL_ID[i],SPLIT_ID,user);
	            	if(checkPosi.getRowCount()>0&&checkPosi1.getRowCount()>0){
	            		int count = Integer.parseInt(SHIP_COUNT[i])+Integer.parseInt(HAD_SHIP_COUNT[i]);
		            	dao.acceptPart(DETAIL_ID[i],String.valueOf(count),DISTRINBUTION_NO[i],user);
		            	// 添加验收日志 by fuxiao 20141116
		            	dao.insertAcceptPartLog(new String[]{SHIP_COUNT[i], user.getAccount(), DETAIL_ID[i]});
		            	counts = counts+Integer.parseInt(SHIP_COUNT[i]);
	            	}else if(checkPosi1.getRowCount()==0){
	            		QuerySet getPartCode = dao.getPartCode(DETAIL_ID[i],SPLIT_ID);
	            		throw new Exception("配件"+getPartCode.getString(1, "CODE")+"尚未有默认库位，请维护默认库位之后再入库");
	            	}else{
	            		QuerySet getPartCode = dao.getPartCode(DETAIL_ID[i],SPLIT_ID);
	            		throw new Exception("配件"+getPartCode.getString(1, "CODE")+"尚未有对应库管员，请维护库管员之后再入库");
	            	}
	            	
	            }
	            dao.updateAccept(SPLIT_ID,counts,user);
	            atx.setOutMsg("", "验收成功！");
	        } catch (Exception e) {
	        	e.printStackTrace();
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void orderPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			page.setPageRows(10000);
			try
			{
				String orgId = user.getOrgId();
				QuerySet checkOrg = dao.checkOrg(orgId);
				String warehouseType = checkOrg.getString(1, "WAREHOUSE_TYPE");
				String SPLIT_ID = Pub.val(request, "SPLIT_ID");
				BaseResultSet bs = dao.orderPartSearch(page,user,SPLIT_ID,conditions,warehouseType);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}

}
