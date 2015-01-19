package com.org.dms.action.part.salesMng.orderMng;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.DelPartOrderCheckDao;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Author: sunxuedong
 * @Date: 2014.08.19
 * @Description:渠道商配件订单审核Action
 */
public class DelPartOrderCheckAction {
	private Logger logger = com.org.framework.log.log.getLogger("DelPartOrderCheckAction");
	private ActionContext atx = ActionContext.getContext();
	private DelPartOrderCheckDao dao = DelPartOrderCheckDao.getInstance(atx);
	/**
     * 订单审核查询
     */
    public void partOrderCheckSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orgId =user.getOrgId();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.partOrderCheckSearch(page, user, conditions,orgId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 资金账户可用余额查询
     */
    public void accountSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orgId = request.getParamValue("orgId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.accountSearch(page, orgId, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单审核记录查询
     */
    public void orderCheckListSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderCheckListSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单订购配件列表查询
     */
    public void partOrderDetailSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
        String orgId= user.getOrgId();
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        page.setPageRows(10000);
        try {
            BaseResultSet bs = dao.partOrderDetailSearch(page, user, conditions,orderId,orgId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单审核通过
     */
    public void partOrderCheckPass() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orgId= user.getOrgId();
        try {
        	HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
			String orderId = request.getParamValue("orderId");
			String flag = request.getParamValue("flag");
			String dtlIds = hm.get("DTLIDS");
			String partIds = hm.get("PARTIDS");
			String supplierIds = hm.get("SUPPLIERIDS");
			String auditCounts = hm.get("AUDITCOUNTS");
			String remarks = hm.get("REMARKS");
			String []dtlId = dtlIds.split(",");
			String []partId = partIds.split(",");
			String []supplierId = supplierIds.split(",");
			String []auditCount = auditCounts.split(",");
			int auditAllCount = 0;
			for(int i=0;i<dtlId.length;i++){
				if(Integer.parseInt(auditCount[i])<0){
					throw new Exception("审核数量不能为负数.");
				}
				auditAllCount = auditAllCount+Integer.parseInt(auditCount[i]);
				//更新订单明细审核数量
				dao.orderAuditCountUpdate(auditCount[i], dtlId[i], orderId, user);
				//冻结库存
            	if(null==supplierIds||"".equals(supplierIds.trim())){
            		dao.updateAmount1(partId[i],orgId,auditCount[i]);
            	}else{
            		dao.updateAmount2(partId[i],supplierId[i],orgId,auditCount[i]);
            	}
			}
			PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
			vo.setOrderId(orderId);
			if(auditAllCount>0){
				vo.setOrderStatus(DicConstant.DDZT_03);
				vo.setShipStatus(DicConstant.DDFYZT_01);
			}else{
				vo.setOrderStatus(DicConstant.DDZT_06);
 	            vo.setCloseDate(Pub.getCurrentDate());
			}
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			//更新订单状态
			dao.partOrderCheckPass(vo);
			PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
			cvo.setOrderId(orderId);
			cvo.setCheckUser(user.getAccount());
			cvo.setCheckDate(Pub.getCurrentDate());
			cvo.setCheckResult(DicConstant.DDZT_03);
			cvo.setCheckOrg(user.getOrgId());
			cvo.setRemarks(remarks);
			//插入审核日志
            dao.partOrderCheckLogInsert(cvo);
        	//订单占用库存
        	if("2".equals(flag)){
        		//生成延期订单
        		dao.delayOrderInfoInsert(orderId,user);
        	}
            atx.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单审核驳回
     */
    public void partOrderCheckApproveBack() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String orderId = request.getParamValue("orderId");
            String remarks = hm.get("REMARKS");
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setOrderStatus(DicConstant.DDZT_04);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.partOrderCheckApproveBack(vo);
            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setOrderId(orderId);
            cvo.setCheckUser(user.getAccount());
            cvo.setCheckDate(Pub.getCurrentDate());
            cvo.setCheckResult(DicConstant.DDZT_04);
            cvo.setCheckOrg(user.getOrgId());
            cvo.setRemarks(remarks);
            dao.partOrderCheckLogInsert(cvo);
            atx.setOutMsg(vo.getRowXml(), "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
}
