package com.org.dms.action.part.partClaimMng.claimCycl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.partClaimMng.ClaimCyclApplyMngDao;
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
 * 配件三包申请Action
 *
 * 配件三包申请(配送中心,服务站)
 * @author zhengyao
 * @date 2014-08-11
 * @version 1.0
 */
public class ClaimCyclApplyMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义配件三包期设置Dao对象
    private ClaimCyclApplyMngDao claimCyclApplyMngDao = ClaimCyclApplyMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 配件三包申请提报
     * @throws Exception
     */
    public void applyClaimCyclApply() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String applyId = Pub.val(requestWrapper, "applyId");
            PtBuClaimApplyVO ptBuClaimApplyVO = new PtBuClaimApplyVO();
            // 主键ID
            ptBuClaimApplyVO.setApplyId(applyId);
            String applyStatus = "";
            if (DicConstant.ZZLB_09.equals(user.getOrgDept().getOrgType())) {
                // ------ 配送中心
                applyStatus = DicConstant.PJSBSPZT_04;
            } else if (DicConstant.ZZLB_10.equals(user.getOrgDept().getOrgType())||DicConstant.ZZLB_11.equals(user.getOrgDept().getOrgType())) {
                // ------ 服务站，维修站
                applyStatus = DicConstant.PJSBSPZT_02;
            }
            // 通过Dao,执行删除配件三包期设置
            claimCyclApplyMngDao.applyClaimCyclApply(ptBuClaimApplyVO,applyStatus);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "提报成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件三包申请查询
     * @throws Exception
     */
    public void searchClaimCycl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclApplyMngDao.searchClaimCyclApply(pageManager, user, conditions,true);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 配件三包申请查询(审核)
     * @throws Exception
     */
    public void searchClaimCyclApply() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclApplyMngDao.searchClaimCyclApply(pageManager, user, conditions,true);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 查询入库订单
     * @throws Exception
     */
    public void searchInStockOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclApplyMngDao.searchInStockOrder(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 查询出库订单
     * @throws Exception
     */
    public void searchOutStockOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclApplyMngDao.searchOutStockOrder(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增配件三包申请
     * @throws Exception
     */
    public void insertClaimCyclApply() throws Exception {

        try {
            PtBuClaimApplyVO ptBuClaimApplyVO = new PtBuClaimApplyVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuClaimApplyVO.setValue(hashMap);
            // 配件三包申请单号
            ptBuClaimApplyVO.setApplyNo(PartOddNumberUtil.getClaimCyclOrderNo(actionContext.getDBFactory()));
            // 申请状态(已保存)
            ptBuClaimApplyVO.setApplyStatus(DicConstant.PJSBSPZT_01);
            // 结算状态(未结算)
            ptBuClaimApplyVO.setSettleAccount(DicConstant.PJSBSPJSZT_01);
            // 申请日期
            ptBuClaimApplyVO.setApplyDate(Pub.getCurrentDate());
            // 申请人
            ptBuClaimApplyVO.setApplyUser(user.getAccount());
            // 渠道ID
            ptBuClaimApplyVO.setOrgId(user.getOrgId());
            // 渠道代码
            ptBuClaimApplyVO.setOrgCode(user.getOrgCode());
            // 渠道名称
            ptBuClaimApplyVO.setOrgName(user.getOrgDept().getOName());
            // 所属公司
            ptBuClaimApplyVO.setCompanyId(user.getCompanyId());
            // 创建人
            ptBuClaimApplyVO.setCreateUser(user.getAccount());
            // 创建时间
            ptBuClaimApplyVO.setCreateTime(Pub.getCurrentDate());
            // 状态(有效)
            ptBuClaimApplyVO.setStatus(DicConstant.YXBS_01);
            ptBuClaimApplyVO.setOemCompanyId(user.getOemCompanyId());
            claimCyclApplyMngDao.insertClaimCyclApply(ptBuClaimApplyVO);
            actionContext.setOutMsg(ptBuClaimApplyVO.getRowXml(),"新增成功！");
            
//            PtBuClaimApplyLogVO ptBuClaimApplyLogVO = new PtBuClaimApplyLogVO();
//            // 申请ID
//            ptBuClaimApplyLogVO.setApplyId(ptBuClaimApplyVO.getApplyId());
//            ptBuClaimApplyLogVO.setCreateUser(user.getAccount());
//            ptBuClaimApplyLogVO.setCreateDate(Pub.getCurrentDate());
//            claimCyclApplyMngDao.insertClaimCyclApplyLog(ptBuClaimApplyLogVO);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改配件三包申请
     * @throws Exception
     */
    public void updateClaimCyclApply() throws Exception {

        try {
            PtBuClaimApplyVO ptBuClaimApplyVO = new PtBuClaimApplyVO();
            // 将hashmap映射到vo对象中,完成匹配赋值
            ptBuClaimApplyVO.setValue(hashMap);
            // 通过Dao,执行更新
            claimCyclApplyMngDao.updateClaimCyclApply(ptBuClaimApplyVO);
            // 返回更新结果和成功信息
            actionContext.setOutMsg(ptBuClaimApplyVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除配件三包申请
     * @throws Exception
     */
    public void deleteClaimCyclApply() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String applyId = Pub.val(requestWrapper, "applyId");
            PtBuClaimApplyVO ptBuClaimApplyVO = new PtBuClaimApplyVO();
            // 主键ID
            ptBuClaimApplyVO.setApplyId(applyId);
            // 通过Dao,执行删除配件三包期设置
            claimCyclApplyMngDao.deleteClaimCyclApply(ptBuClaimApplyVO);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}