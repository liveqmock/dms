package com.org.dms.action.part.salesMng.returnPurchaseMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.returnPurchaseMng.ReturnPurchaseCheckMngDao;
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
 * 退件审核Action
 *
 * 配送中心退件审核
 * @author zhengyao
 * @date 2014-08-02
 * @version 1.0
 */
public class ReturnPurchaseCheckMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private ReturnPurchaseCheckMngDao returnPurchaseCheckMngDao = ReturnPurchaseCheckMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 退件申请查询(车厂)
     * @throws Exception
     */
    public void searchReturnPurchaseApplyCar() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)returnPurchaseCheckMngDao.searchReturnPurchaseApplyCar(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

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
            BaseResultSet baseResultSet = (BaseResultSet)returnPurchaseCheckMngDao.searchReturnPurchaseApply(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 审核驳回
     * @throws Exception
     */
    public void doCheckReturn() throws Exception {

        try {
            // 退件申请表对应实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            ptBuReturnApplyVO.setValue(hashMap);
            // 申请状态(审核驳回)
            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_04);
            returnPurchaseCheckMngDao.updateReturnPurchaseApply(ptBuReturnApplyVO,user);
            // 减库存
            returnPurchaseCheckMngDao.updateDealerStock(ptBuReturnApplyVO);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutMsg("", "修改成功！");
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

//    /**
//     * 审核通过(车厂)
//     * @throws Exception
//     */
//    public void doCheckPassCar() throws Exception {
//
//        try {
//            // 退件申请表对应实体
//            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
//            ptBuReturnApplyVO.setValue(hashMap);
//            // 申请状态(审核通过)
//            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_03);
//            // 修改退件申请表
//            returnPurchaseCheckMngDao.updateReturnPurchaseApply(ptBuReturnApplyVO,user);
//            // 修改配件服务站库存表
//            returnPurchaseCheckMngDao.updateStock(ptBuReturnApplyVO);
//            // 输出结果集，第一个参数”bs”为固定名称，不可修改
//            actionContext.setOutMsg("", "修改成功！");
//        } catch (Exception e) {
//            logger.error(e);
//            actionContext.setException(e);
//        }
//    }
    /**
     * 审核通过
     * @throws Exception
     */
    public void doCheckPass() throws Exception {

        try {
            // 退件申请表对应实体
            PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
            ptBuReturnApplyVO.setValue(hashMap);
            // 申请状态(审核通过)
            ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_03);
            // 修改退件申请表
            returnPurchaseCheckMngDao.updateReturnPurchaseApply(ptBuReturnApplyVO,user);
//            // 修改配件服务站库存表
//            returnPurchaseCheckMngDao.updateDealerStock(ptBuReturnApplyVO);
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
            BaseResultSet baseResultSet = returnPurchaseCheckMngDao.searchReturnPurchaseApplyDtl(pageManager,conditions);
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
