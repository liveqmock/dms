package com.org.dms.action.part.purchaseMng.purchaseReturn;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseReturn.PurchaseOrderReturnCloseDao;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PurchaseOrderReturnCloseAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderReturnCloseDao dao = PurchaseOrderReturnCloseDao.getInstance(atx);
	    
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
	    //purchaseOrderClose
	    public void purchaseOrderClose() throws Exception {
	        //获取封装后的request对象
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String RETURN_ID = Pub.val(request, "RETURN_ID");//采购退货单ID
	        try
	        {
	        	/***获取仓库信息***/
	        	QuerySet getWareInfo = dao.getWareInfo(RETURN_ID);
	            String WAREHOUSE_ID = getWareInfo.getString(1, "WAREHOUSE_ID");
	        	QuerySet checkOut = dao.checkOut(RETURN_ID);
	        	if(checkOut.getRowCount()>0){
	        		/****如果存在出库单***********************************************************/
	        		String OUT_ID = checkOut.getString(1, "OUT_ID");
	        		/***释放明细表库存***/
	        		dao.updateStockDtl(OUT_ID, user);//已经存在在出库单中的释放库存减去占用，减去总额
	        		dao.updateStockDtl1(OUT_ID,RETURN_ID, user);//未存在在出库单中的 减去占用 加上可用 总额不变
	        		/***释放主表库存***/
	        		dao.updateStock(WAREHOUSE_ID, OUT_ID, user);//已经存在在出库单中的释放库存减去占用，减去总额
	        		dao.updateStock1(WAREHOUSE_ID, OUT_ID,RETURN_ID, user);//未存在在出库单中的 减去占用 加上可用 总额不变
	        		/***生成出库流水***/
	        		dao.insertOutFlow(OUT_ID, user);
	        	}else{
	        		/****如果不存在出库单***********************************************************/
	        		/***释放主表库存***/
	        		dao.updateStockNoOut(WAREHOUSE_ID, RETURN_ID, user);
	        		/***释放明细表库存***/
	        		dao.updateStockDtlNoOut(RETURN_ID, user);
	        	}
	        	/****更新采购退货单状态***********************************************************/
	        	PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
	        	r_vo.setReturnId(RETURN_ID);
	        	r_vo.setReturnStatus(DicConstant.CGTHDZT_04);
	        	r_vo.setCloseDate(Pub.getCurrentDate());
	        	r_vo.setUpdateTime(Pub.getCurrentDate());
	        	r_vo.setUpdateUser(user.getAccount());
	        	dao.updateRet(r_vo);
	        }
	        catch (Exception e)
	        {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }

}
