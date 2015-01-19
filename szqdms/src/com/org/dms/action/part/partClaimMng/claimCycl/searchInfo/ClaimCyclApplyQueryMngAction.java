package com.org.dms.action.part.partClaimMng.claimCycl.searchInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.partClaimMng.searchInfo.ClaimCyclApplyQueryMngDao;
import com.org.dms.vo.part.PtBuClaimApplyVO;
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
 * 配件三包申请Action
 *
 * 配件三包申请(配送中心,服务站)
 * @author zhengyao
 * @date 2014-08-11
 * @version 1.0
 */
public class ClaimCyclApplyQueryMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义配件三包期设置Dao对象
    private ClaimCyclApplyQueryMngDao claimCyclApplyMngDao = ClaimCyclApplyQueryMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义response对象
    ResponseWrapper responseWrapper= actionContext.getResponse();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 订单查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = actionContext.getRequest();
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("APPLY_NO");
            hBean.setTitle("三包申请单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("APPLY_STATUS");
            hBean.setTitle("状态");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("申请单位代码");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("申请单位名称");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_ORG_NAME_1");
            hBean.setTitle("审核部门");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("IN_ORDER_NO");
            hBean.setTitle("入库单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OUT_ORDER_NO");
            hBean.setTitle("出库单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SOURCE_IN_NO");
            hBean.setTitle("经销商入库单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SOURCE_OUT_NO");
            hBean.setTitle("经销商出库单号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("单位");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CLAIM_COUNT");
            hBean.setTitle("数量");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("SALE_PRICE");
            hBean.setTitle("经销商价");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("合计金额");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("生产厂家");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CUSTOMER_NAME");
            hBean.setTitle("购货单位");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("LINK_MAN");
            hBean.setTitle("联系人");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PHONE");
            hBean.setTitle("联系电话");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("FAULT_CONDITONS");
            hBean.setTitle("故障情况");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("经销商意见");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_REMARK_1");
            hBean.setTitle("配送中心意见");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_REMARK_2");
            hBean.setTitle("配件销售科意见");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("APPLY_DATE");
            hBean.setTitle("申请时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("OUT_DATE");
            hBean.setTitle("出库时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_DATE_3");
            hBean.setTitle("作废时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("APPLY_DATE");
            hBean.setTitle("提交至配送中心时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_DATE_1");
            hBean.setTitle("提交至配件销售科时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_DATE_1");
            hBean.setTitle("初审通过时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_USER_1");
            hBean.setTitle("初审人");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_DATE_2");
            hBean.setTitle("终审时间");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_USER_2");
            hBean.setTitle("终审人");
            header.add(hBean);
            
            QuerySet querySet = claimCyclApplyMngDao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "配件三包申请查询", header, querySet);
        } catch (Exception e) {
        	e.printStackTrace();
            actionContext.setException(e);
            logger.error(e);
        }
    }

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
     * 配件三包申请车厂查询
     * @throws Exception
     */
    public void searchClaimCyclApplyOem() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclApplyMngDao.searchClaimCyclApplyOem(pageManager, user, conditions);
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
    public void getPartClaimInfo() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            String applyId = Pub.val(requestWrapper, "APPLY_ID");
            BaseResultSet baseResultSet = claimCyclApplyMngDao.getPartClaimInfo(pageManager, user, applyId);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}