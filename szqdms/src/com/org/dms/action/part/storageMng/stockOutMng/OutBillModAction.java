package com.org.dms.action.part.storageMng.stockOutMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.OutBillModDao;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuStockOutModLogVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class OutBillModAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "OutBillModAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private OutBillModDao dao = OutBillModDao.getInstance(atx);
    
	public void searchOutBill() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
        	HashMap<String, String> hm = RequestUtil.getValues(request);
        	String partCode = hm.get("PART_CODE");
            String partName = hm.get("PART_NAME");
            BaseResultSet bs = dao.searchOutBill(page, user, conditions,partCode,partName);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	
	public void searchPart() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String OUT_ID = Pub.val(request, "OUT_ID");
            BaseResultSet bs = dao.searchPart(page, user, conditions, OUT_ID);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	public void orderPartCountSave() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        HashMap<String, String> hm = RequestUtil.getValues(request);
        try {
        	
			String OUT_ID = hm.get("OUT_ID");
			String DTL_ID = hm.get("DTL_ID");
			String COUNT = hm.get("COUNT");
			String POSITION_ID = hm.get("POS_ID");
			
			QuerySet getOld = dao.getOld(OUT_ID,DTL_ID);
			String SALE_PRICE = getOld.getString(1, "SALE_PRICE");
			String PLAN_PRICE = getOld.getString(1, "PLAN_PRICE");
			String OUT_AMOUNT = getOld.getString(1, "OUT_AMOUNT");
			String WAREHOUSE_ID = getOld.getString(1, "WAREHOUSE_ID");
			String PART_ID = getOld.getString(1, "PART_ID");
			String ORDER_ID = getOld.getString(1, "ORDER_ID");
			String KEEP_MAN = getOld.getString(1, "KEEP_MAN");
			
			QuerySet checkLock = dao.checkLock1(PART_ID,WAREHOUSE_ID);
            String locks = checkLock.getString(1, "CODES");
            if(!"".equals(locks)&&locks!=null){
            	throw new Exception("配件"+locks+"处于库存锁定状态，不能进行入库操作");
            }
            /****校验库存锁定状态*******************************/
            QuerySet checkLock1 = dao.checkLock2(PART_ID,WAREHOUSE_ID);
            String locks2 = checkLock1.getString(1, "CODES");
            if(!"".equals(locks2)&&locks2!=null){
            	throw new Exception("配件"+locks2+"处于盘点锁定状态，不能进行入库操作");
            }
			
			String SALE_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(SALE_PRICE));
			String PLAN_AMOUNT = String.valueOf(Integer.parseInt(COUNT)*Float.parseFloat(PLAN_PRICE));
			
			String DEF_COUNT = String.valueOf(Integer.parseInt(OUT_AMOUNT)-Integer.parseInt(COUNT));
			PtBuStockOutModLogVO l_vo = new PtBuStockOutModLogVO();
			l_vo.setOrderId(ORDER_ID);
			l_vo.setPartId(PART_ID);
			l_vo.setOutId(OUT_ID);
			l_vo.setOldCount(OUT_AMOUNT);
			l_vo.setModCount(COUNT);
			l_vo.setDefCount(DEF_COUNT);
			l_vo.setKeepMan(KEEP_MAN);
			l_vo.setModDate(Pub.getCurrentDate());
			l_vo.setModMan(user.getAccount());
			l_vo.setCreateUser(user.getAccount());
			l_vo.setCreateDate(Pub.getCurrentDate());
			dao.insertLog(l_vo);
			
			
			/****更新出库单配件信息**********/
			dao.updateOutDtl(OUT_ID,DTL_ID,COUNT,SALE_AMOUNT,PLAN_AMOUNT,user);
			/****更新发料单**********/
			dao.updateIssueOrderDtl(ORDER_ID,PART_ID,POSITION_ID,COUNT,user);
			/****更新配件库存信息**********/
			dao.updateStock(WAREHOUSE_ID,PART_ID,DEF_COUNT,user);
			/****更新配件库存明细信息**********/
			dao.updateStockDtl(PART_ID,POSITION_ID,DEF_COUNT,user);
			/****更新装箱清单信息***************************/
			QuerySet checkBox = dao.checkBox(PART_ID,ORDER_ID);
			if(checkBox.getRowCount()>0){
				dao.deleteBoxNo(PART_ID,ORDER_ID);
			}
			QuerySet qs = dao.getOutAmount(OUT_ID);
			if(qs.getRowCount()>0){
				String outAmount = qs.getString(1, "OUT_AMOUNT");
        		if(Integer.parseInt(outAmount)==0){
        			//释放资金占用
        			dao.orderReleaseFreez(ORDER_ID, user);
	                PtBuSaleOrderVO s_vo = new PtBuSaleOrderVO();
	                s_vo.setOrderId(ORDER_ID);
	                //s_vo.setShipStatus(DicConstant.DDFYZT_03);
	                s_vo.setOrderStatus(DicConstant.DDZT_06);
	                s_vo.setCloseDate(Pub.getCurrentDate());
	                s_vo.setUpdateTime(Pub.getCurrentDate());
	                s_vo.setUpdateUser(user.getAccount());
	                dao.updatePartSaleOrder(s_vo);
        		}
			}
//			dao.updateSaleOrder(PART_ID,ORDER_ID,COUNT,user);
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}
