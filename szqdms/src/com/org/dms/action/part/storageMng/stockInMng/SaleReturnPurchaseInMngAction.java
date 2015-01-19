package com.org.dms.action.part.storageMng.stockInMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockInMng.SaleReturnPurchaseInMngDao;
import com.org.dms.vo.part.PtBuReturnApplyDtlVO;
import com.org.dms.vo.part.PtBuReturnApplyVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 销售退件入库Action
 *
 * 配送中心或车厂销售退件入库
 * @author zhengyao
 * @date 2014-08-06
 * @version 1.0
 */
public class SaleReturnPurchaseInMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义销售退件入库Dao对象
    private SaleReturnPurchaseInMngDao saleReturnPurchaseInMngDao = SaleReturnPurchaseInMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 退件申请查询
     * @throws Exception
     */
    public void searchReturnPurchaseApply() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = null;
            // ------ 服务站退件出库
            baseResultSet = saleReturnPurchaseInMngDao.searchReturnPurchaseApply(pageManager, user, conditions);
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 销售退件入库
     * @throws Exception
     */
    public void returnPurchaseIn() throws Exception {

        try {
            // 退件申请表对应实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            // 退件订单ID
            String returnId = hashMap.get("RETURN_ID");
            String returnIds = hashMap.get("RETURNIDS");
            // 退件订单编号
            String returnNo = hashMap.get("RETURNNO");
            // 配件ID
            String partIds = hashMap.get("PARTIDS");
            // 入库数量
            String inCounts = hashMap.get("INCOUNTS");
            // 应入数量
            String returnCounts = hashMap.get("RETURNCOUNTS");
            // 供应商ID
            String supplierIds = hashMap.get("SUPPLIERIDS");
            // 退件订单ID
            String[] returnIdArr = returnIds.split(",");
            // 配件ID
            String[] partIdArr = partIds.split(",");
            // 出库数量
            String[] inCountArr = inCounts.split(",");
            // 应入数量
            String[] returnCountArr = returnCounts.split(",");
            // 供应商ID
            String[] supplierIdArr = supplierIds.split(",");
            // 配件主键,出库数量
            String partIdInCount = "";
            ptBuReturnApplyVO.setReturnId(returnId);
            //ptBuReturnApplyVO.setCloseDate(Pub.getCurrentDate());
            // 申请状态(已关闭)
            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_05);
            // 修改退件申请表
            saleReturnPurchaseInMngDao.updateReturnPurchaseApply(ptBuReturnApplyVO,user);
            for (int i = 0;i<returnIdArr.length;i++) {
            	PtBuReturnApplyDtlVO vo = new PtBuReturnApplyDtlVO();
            	vo.setDtlId(returnIdArr[i]);
            	vo.setInCount(inCountArr[i]);
            	saleReturnPurchaseInMngDao.updateApplyDtl(vo);
                // 配件主键,出库数量,应入数量,退件订单
                partIdInCount = partIdArr[i] + "," + inCountArr[i] + "," + returnCountArr[i] + "," + returnNo+","+supplierIdArr[i];
                // 修改配件配送中心库存表
                saleReturnPurchaseInMngDao.updateDealerStock(partIdInCount,user,returnId);
                // 修改配件配送中心异动表
                saleReturnPurchaseInMngDao.insertDealerStockChange(partIdInCount,user,returnId);
//                // 修改配件服务站库存表
//                saleReturnPurchaseInMngDao.updateDealerStock1(partIdInCount,user,returnIdArr[0]);
//                // 修改配件库存服务站异动表
//                saleReturnPurchaseInMngDao.insertDealerStockChange1(partIdInCount,user);
                
            }
            saleReturnPurchaseInMngDao.updateApplyStockDtl(returnId,returnIds);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutMsg("", "修改成功！");
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 退件申请明细查询
     * @throws Exception
     */
    public void searchReturnPurchaseApplyDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = saleReturnPurchaseInMngDao.searchReturnPurchaseApplyDtl(pageManager,conditions);
            // 单位
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            // 最小计量单位
            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}
