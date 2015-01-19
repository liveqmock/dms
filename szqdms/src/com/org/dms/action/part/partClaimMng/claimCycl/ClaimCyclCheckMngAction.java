package com.org.dms.action.part.partClaimMng.claimCycl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.partClaimMng.ClaimCyclCheckMngDao;
import com.org.dms.vo.part.PtBuClaimApplyLogVO;
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
 * 配件三包审核Action
 *
 * 配件三包审核(配送中心,服务站)
 * @author zhengyao
 * @date 2014-08-15
 * @version 1.0
 */
public class ClaimCyclCheckMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义配件三包审核Dao对象
    private ClaimCyclCheckMngDao claimCyclCheckMngDao = ClaimCyclCheckMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 配件三包申请通过
     * @throws Exception
     */
    public void claimCyclApplyCheck() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            PtBuClaimApplyVO ptBuClaimApplyVO = new PtBuClaimApplyVO();
            PtBuClaimApplyLogVO ptBuClaimApplyLogVO = new PtBuClaimApplyLogVO();
            ptBuClaimApplyVO.setValue(hashMap);
            ptBuClaimApplyLogVO.setValue(hashMap);
            // 审核结果(申请状态)
            ptBuClaimApplyLogVO.setCheckResult(ptBuClaimApplyVO.getApplyStatus());
            // 通过Dao,执行删除配件三包期设置
            claimCyclCheckMngDao.updateClaimCyclApply(ptBuClaimApplyVO,user);
            // 审核日期
            ptBuClaimApplyLogVO.setCheckDate(Pub.getCurrentDate());
            // 审核人
            ptBuClaimApplyLogVO.setCheckUser(user.getAccount());
            // 审核组织ID
            ptBuClaimApplyLogVO.setCheckOrgId(user.getOrgId());
            // 审核组织代码
            ptBuClaimApplyLogVO.setCheckOrgCode(user.getOrgCode());
            // 审核组织名称
            ptBuClaimApplyLogVO.setCheckOrgName(user.getOrgDept().getOName());
            // 创建人
            ptBuClaimApplyLogVO.setCreateUser(user.getAccount());
            // 创建时间
            ptBuClaimApplyLogVO.setCreateDate(Pub.getCurrentDate());
            // 新增配件三包申请日志表
            claimCyclCheckMngDao.insertClaimCyclApplyLog(ptBuClaimApplyLogVO);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件三包申请查询
     * @throws Exception
     */
    public void searchClaimCyclApply() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String flag = Pub.val(requestWrapper, "flag");
            if ("'oem'".equals(flag)) {
                flag = DicConstant.PJSBSPZT_04;
            } else {
                flag = DicConstant.PJSBSPZT_02;
            }
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclCheckMngDao.searchClaimCyclApply(pageManager, user, conditions,flag);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    //searchSourceOrder
    public void searchSourceOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String PART_ID = Pub.val(requestWrapper, "PART_ID");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclCheckMngDao.searchSourceOrder(pageManager, user, conditions,PART_ID);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}