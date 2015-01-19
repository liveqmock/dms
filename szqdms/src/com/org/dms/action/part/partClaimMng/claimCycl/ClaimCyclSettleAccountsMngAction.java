package com.org.dms.action.part.partClaimMng.claimCycl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.partClaimMng.ClaimCyclSettleAccountsMngDao;
import com.org.dms.vo.part.PtBuClaimApplyVO;
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
 * 配件三包结算Action
 *
 * 配件三包结算
 * @author zhengyao
 * @date 2014-08-15
 * @version 1.0
 */
public class ClaimCyclSettleAccountsMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义配件三包结算Dao对象
    private ClaimCyclSettleAccountsMngDao claimCyclSettleAccountsMngDao = ClaimCyclSettleAccountsMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 配件三包申请结算
     * @throws Exception
     */
    public void updateClaimCyclApply() throws Exception {

        try {
            // 申请单ID
//            String applyIds = hashMap.get("APPLYIDS");
        	String applyIds = Pub.val(requestWrapper, "IDS");
            String[] applyIdArr = applyIds.split(",");
            for (int i = 0; i < applyIdArr.length; i++) {
                PtBuClaimApplyVO ptBuClaimApplyVO = new PtBuClaimApplyVO();
                ptBuClaimApplyVO.setApplyId(applyIdArr[i]);
                ptBuClaimApplyVO.setSettleAccount(DicConstant.PJSBSPJSZT_01);
                claimCyclSettleAccountsMngDao.updateClaimCyclApply(ptBuClaimApplyVO, user);
            }
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 配件三包申请查询(终审通过)
     * @throws Exception
     */
    public void searchClaimCyclApply() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclSettleAccountsMngDao.searchClaimCyclApply(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}