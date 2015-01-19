package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderCheckDao;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
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

/**
 * @Author: gouwentan
 * @Date: 2014-08-06
 * @Description:配件订单审核Action
 */
public class PartOrderCheckAction {
    private Logger logger = com.org.framework.log.log.getLogger("PartOrderCheckAction");
    private ActionContext atx = ActionContext.getContext();
    private PartOrderCheckDao dao = PartOrderCheckDao.getInstance(atx);
    /**
     * 订单审核查询
     */
    public void partOrderCheckSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
        	QuerySet querySet = dao.isAmCheck(user);
        	boolean flag = false;
        	if (querySet.getRowCount() > 0) {
        		flag = true;
        	}
            BaseResultSet bs = dao.partOrderCheckSearch(page, user, conditions,flag);
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
     * 订单资金使用查询
     */
    public void orderFundsListSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderFundsListSearch(page, user, conditions,orderId);
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
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        page.setPageRows(10000);
        try {
            BaseResultSet bs = dao.partOrderDetailSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
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
            //释放资金占用
            dao.orderReleaseFreez(orderId,user);
            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setOrderId(orderId);
            cvo.setCheckUser(user.getAccount());
            cvo.setCheckDate(Pub.getCurrentDate());
            cvo.setCheckResult(DicConstant.DDZT_04);
            cvo.setCheckOrg(user.getOrgId());
            cvo.setRemarks(remarks);
            dao.partOrderCheckLogInsert(cvo);

            /** 2015-1-6 start 直营订单审核-查原单ID **/
            QuerySet sourceQuerySet = dao.sourceOrderId(orderId);
            String dirSourceOrderId = sourceQuerySet.getString(1, "DIR_SOURCE_ORDER_ID");
            if (dirSourceOrderId.length()>10) {
            	/** 2015-1-6 start 直营订单审核-服务站订单状态,审核日志**/
            	// 订单状态
            	vo.setOrderId(dirSourceOrderId);
            	// 服务站订单
            	dao.partOrderCheckApproveBack(vo);
            	
            	cvo.setCheckId("");
            	// 审核日志
            	cvo.setOrderId(dirSourceOrderId);
            	dao.partOrderCheckLogInsert(cvo);
            	
            	// 删除直营订单
            	dao.deleteOrder(orderId);
            	/** 2015-1-6 end **/
            }
            /** 2015-1-6 end **/

            atx.setOutMsg(vo.getRowXml(), "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    
    /**
     * 订单审核通过
     */
    public void partOrderCheckPass() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String orderId = request.getParamValue("orderId");
            String orderType = request.getParamValue("orderType");
            String flag = request.getParamValue("flag");
            
            QuerySet querySet = dao.orderCheck(orderId);
            String partCode = "";
            if (querySet.getRowCount() > 0) {
            	for (int i =0;i<querySet.getRowCount();i++) {
            		if(partCode==""){
            			partCode += querySet.getString(i+1, "PART_CODE");
            		}else{
            			partCode += "，"+querySet.getString(i+1, "PART_CODE");
            		}
            		
            	}
                throw new Exception("订单中存在三包急件延期订单的配件,请优先审核三包急件延期订单！\n"+partCode);
            }
            
            String dtlIds = hm.get("DTLIDS");
            // 审核数量
            String auditCounts = hm.get("AUDITCOUNTS");
            // 备注
            String remarks = hm.get("REMARKS");
            // 计生号
            String planProduceNo = hm.get("PLAN_PRODUCE_NO");
            String[] dtlId = dtlIds.split(",");
            String[] auditCount = auditCounts.split(",");
            
            // 直发订单审核
            if(DicConstant.DDLX_05.equals(orderType)){
            	QuerySet querySet1 = dao.partSupplierCheck(dtlIds);
            	if (querySet1.getRowCount() > 0) {
	            	for (int i =0;i<querySet1.getRowCount();i++) {
	            		if(partCode==""){
	            			partCode += querySet1.getString(i+1, "PART_CODE");
	            		}else{
	            			partCode += "，"+querySet1.getString(i+1, "PART_CODE");
	            		}
	            	}
	            	throw new Exception("配件不存在供货关系！\n"+partCode);
            	}
            }

            /** 2015-1-6 start 直营订单审核-查原单ID **/
            QuerySet sourceQuerySet = dao.sourceOrderId(orderId);
            String dirSourceOrderId = sourceQuerySet.getString(1, "DIR_SOURCE_ORDER_ID");
            /** 2015-1-6 end **/

            int auditAllCount=0;
            for(int i=0;i<dtlId.length;i++){
                //更新订单明细审核数量
            	auditAllCount = auditAllCount+Integer.parseInt(auditCount[i]);
                dao.orderAuditCountUpdate(auditCount[i], dtlId[i], orderId, user,orderType);
                if (dirSourceOrderId.length()>10) {
                	/** 2015-1-6 start 直营订单审核-服务站订单明细修改审核数量 **/
                	// 服务站订单
                	dao.orderAuditCountUpdateServer(auditCount[i], dtlId[i], dirSourceOrderId, user);
                	/** 2015-1-6 end **/
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
            vo.setPlanProduceNo(planProduceNo);
            //更新订单状态
            dao.partOrderCheckPass(vo);

            
            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setOrderId(orderId);
            // 审核人
            cvo.setCheckUser(user.getAccount());
            // 审核时间
            cvo.setCheckDate(Pub.getCurrentDate());
            // 创建人
            cvo.setCreateUser(user.getAccount());
            // 创建时间
            cvo.setCreateTime(Pub.getCurrentDate());
            // 审核结果
            cvo.setCheckResult(DicConstant.DDZT_03);
            // 状态
            cvo.setStatus(DicConstant.YXBS_01);
            cvo.setCheckOrg(user.getOrgId());
            cvo.setOemCompanyId(user.getOemCompanyId());
            cvo.setRemarks(remarks);
            //插入审核日志
            dao.partOrderCheckLogInsert(cvo);
            
            /** 2015-1-6 start 直营订单审核-订单状态和审核日志 **/
            if (dirSourceOrderId.length()>10) {
            	// 更新服务站订单状态
            	vo.setOrderId(dirSourceOrderId);
            	// 更新订单状态
                dao.partOrderCheckPass(vo);

                cvo.setCheckId("");
                // 服务站订单审核日志
                cvo.setOrderId(dirSourceOrderId);
                // 插入审核日志
                dao.partOrderCheckLogInsert(cvo);
            }
            /** 2015-1-6 end **/

            if(DicConstant.DDLX_05.equals(orderType)){
            	//订单释放资金占用
                dao.orderReleaseFreez(orderId,user);
                //直发订单只冻结资金
                dao.orderFreez(orderId, "2");
                dao.createPchOrder(orderId,user);
            }else{
            	//订单释放资金占用
                dao.orderReleaseFreez(orderId,user);
                //订单占用资金及库存
                dao.orderFreez(orderId, "1");
                if("2".equals(flag)){
            		/** 2015-1-6 start 直营订单审核-订单状态和审核日志 **/
            		if (dirSourceOrderId.length()>10) {
            			//生成延期订单(直营订单,有原单)
                		dao.dbDelayOrderInfoInsert(orderId,user,dirSourceOrderId);
            		} else {
            			//生成延期订单
            			dao.delayOrderInfoInsert(orderId,user);
            		}
            		/** 2015-1-6 end **/
                }
            }
            atx.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    
    public void download()throws Exception{

    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		String orderId = Pub.val(request, "orderId");
        
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
            hBean.setWidth(50);
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("计量单位");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MINI");
            hBean.setTitle("最小包装");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("UNIT_PRICE");
            hBean.setTitle("价格");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORDER_COUNT");
            hBean.setTitle("订购数量");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("库存总数量");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OCCUPY_AMOUNT");
            hBean.setTitle("锁定库存");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AVAILABLE_AMOUNT");
            hBean.setTitle("可用库存");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PERSON_NAME");
            hBean.setTitle("库管员");
            header.add(10,hBean);
            
            QuerySet querySet = dao.download(orderId);
            ExportManager.exportFile(response.getHttpResponse(), "订单明细", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
