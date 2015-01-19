package com.org.dms.action.part.storageMng.stockOutMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.ReturnPurchaseOutMngDao;
import com.org.dms.vo.part.PtBuReturnApplyVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 退件出库Action
 *
 * 服务站退件出库
 * @author zhengyao
 * @date 2014-08-05
 * @version 1.0
 */
public class ReturnPurchaseOutMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义退货出库Dao对象
    private ReturnPurchaseOutMngDao returnPurchaseOutMngDao = ReturnPurchaseOutMngDao.getInstance(actionContext);
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
            // 查询销售订单表
            baseResultSet = returnPurchaseOutMngDao.searchSaleOrder(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 退件出库
     * @throws Exception
     */
    public void returnPurchaseOut() throws Exception {

        try {
            // 退件申请表对应实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            // 退件订单ID
            String returnIds = hashMap.get("RETURNIDS");
            // 退件订单编号
            String returnNo = hashMap.get("RETURNNO");
            // 配件ID
            String partIds = hashMap.get("PARTIDS");
            // 出库数量
            String outCounts = hashMap.get("OUTCOUNTS");
            // 退件数量
            String auditCounts = hashMap.get("AUDITCOUNTS");
            // 退件订单ID
            String[] returnIdArr = returnIds.split(",");
            // 配件ID
            String[] partIdArr = partIds.split(",");
            // 出库数量
            String[] outCountArr = outCounts.split(",");
            // 退件数量
            String[] auditCountArr = auditCounts.split(",");
            // 配件主键,出库数量,退件数量,退件订单号
            String partIdoutCount = "";
            ptBuReturnApplyVO.setReturnId(returnIdArr[0]);
            // 申请状态(已出库)
            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_06);
            // 修改退件申请表
            returnPurchaseOutMngDao.updateReturnPurchaseApply(ptBuReturnApplyVO,user);
            for (int i = 0;i<returnIdArr.length;i++) {
                // 配件主键,出库数量,退件数量,退件订单号
                partIdoutCount = partIdArr[i] + "," + outCountArr[i] + "," + auditCountArr[i] + "," +returnNo;
                // 修改配件服务站库存表
                returnPurchaseOutMngDao.updateDealerStock(partIdoutCount,user);
                // 修改配件服务站异动表
                returnPurchaseOutMngDao.insertDealerStockChange(partIdoutCount,user);
            }
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
            BaseResultSet baseResultSet = returnPurchaseOutMngDao.searchReturnPurchaseApplyDtl(pageManager,conditions);
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
