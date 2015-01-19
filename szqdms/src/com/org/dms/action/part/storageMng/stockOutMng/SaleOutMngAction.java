package com.org.dms.action.part.storageMng.stockOutMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.SaleOutMngDao;
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
import com.org.mvc.context.ResponseWrapper;

/**
 * 销售出库Action
 *
 * 配送中心出库
 * @author zhengyao
 * @date 2014-08-06
 * @version 1.0
 */
public class SaleOutMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义销售出库Dao对象
    private SaleOutMngDao saleOutMngDao = SaleOutMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 销售订单查询
     * @throws Exception
     */
    public void searchSaleOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = null;
            // 查询销售订单表
            baseResultSet = saleOutMngDao.searchSaleOrder(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 销售出库
     * @throws Exception
     */
    public void saleOut() throws Exception {

        try {
            // 销售订单表对应实体
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            // 销售订单ID
            String orderId = hashMap.get("ORDERID");
            // 销售订单号
            String orderNo = hashMap.get("ORDERNO");
         // 配件ID
            String dtlIds = hashMap.get("DTLIDS");
            // 配件ID
            String partIds = hashMap.get("PARTIDS");
            // 应出数量
            String auditCounts = hashMap.get("AUDITCOUNTS");
            // 实出数量
            String outCounts = hashMap.get("OUTCOUNTS");
            String supplierIds = hashMap.get("SUPPLIERIDS");
            String[] dtlIdArr = dtlIds.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] partIdArr = partIds.split(",");
            String[] auditCountArr = auditCounts.split(",");
            String[] outCountArr = outCounts.split(",");
            String partIdoutCount = "";
            ptBuSaleOrderVO.setOrderId(orderId);
            ptBuSaleOrderVO.setOrderStatus(DicConstant.DDZT_06);
            ptBuSaleOrderVO.setDeliveryDate(Pub.getCurrentDate());
            // 修改销售订单表
            String url="配送中心销售出库。/action/part/storageMng/stockOutMng/SaleOutMngAction/saleOut.ajax";
            for (int i = 0;i<dtlIdArr.length;i++) {
                // 配件主键,出库数量,应出数量,销售订单号
                partIdoutCount = dtlIdArr[i]+","+partIdArr[i] + "," + outCountArr[i] + "," + auditCountArr[i] + ","+ supplierIdArr[i]+","+orderNo;
                //更新订单明细、修改配件服务站库存表
                saleOutMngDao.updateDealerStock(partIdoutCount,user);
                // 修改配件库存服务站异动表
                saleOutMngDao.insertDealerStockChange(partIdoutCount,user);
                String aAmount = String.valueOf(Integer.parseInt(auditCountArr[i])-Integer.parseInt(outCountArr[i]));
                saleOutMngDao.insetStockDtl(outCountArr[i],aAmount,auditCountArr[i],user,orderId,url,partIdArr[i]);
                
                
                //
            }
            saleOutMngDao.freezStockOccupy(orderId,dtlIds);
            QuerySet getRealAmount = saleOutMngDao.getRealAmount(orderId);
            ptBuSaleOrderVO.setRealAmount(getRealAmount.getString(1, "REAL_AMOUNT"));
            saleOutMngDao.updateSaleOrder(ptBuSaleOrderVO,user);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutMsg("", "出库成功！");
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 销售订单明细查询
     * @throws Exception
     */
    public void searchSaleOrderDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = saleOutMngDao.searchSaleOrderDtl(pageManager,conditions);
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
