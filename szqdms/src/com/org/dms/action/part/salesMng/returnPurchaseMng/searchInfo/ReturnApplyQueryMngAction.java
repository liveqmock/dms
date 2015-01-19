package com.org.dms.action.part.salesMng.returnPurchaseMng.searchInfo;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.returnPurchaseMng.searchInfo.ReturnApplyQueryMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 退件申请查询Action
 *
 * 退件申请查询
 * @author zhengyao
 * @date 2014-10-31
 * @version 1.0
 */
public class ReturnApplyQueryMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private ReturnApplyQueryMngDao dao = ReturnApplyQueryMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();

    /**
     * 订单查询导出
     * @throws Exception
     */
//    public void download()throws Exception{
//
//        try {
//        	//获取封装后的request对象
//        	RequestWrapper request = actionContext.getRequest();
//        	// 定义查询分页对象
//            PageManager pageManager = new PageManager();
//            // 将request流中的信息转化为where条件
//            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
//            List<HeaderBean> header = new ArrayList<HeaderBean>();
//            HeaderBean hBean = null;
//            hBean = new HeaderBean();
//            hBean.setName("ORDER_NO");
//            hBean.setTitle("订单编号");
//            header.add(0,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("ORDER_TYPE");
//            hBean.setTitle("订单类型");
//            header.add(1,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("APPLY_DATE");
//            hBean.setTitle("申请日期");
//            header.add(2,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("ORG_CODE");
//            hBean.setTitle("渠道代码");
//            header.add(3,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("ORG_NAME");
//            hBean.setTitle("渠道名称");
//            header.add(4,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("SALEUSER_NAME");
//            hBean.setTitle("销售员");
//            header.add(5,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("ORDER_AMOUNT");
//            hBean.setTitle("订单金额(元)");
//            header.add(6,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("REAL_AMOUNT");
//            hBean.setTitle("实发金额(元)");
//            header.add(7,hBean);
//
//            hBean = new HeaderBean();
//            hBean.setName("CHECK_DATE");
//            hBean.setTitle("订单审核日期");
//            header.add(8,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("ORDER_STATUS");
//            hBean.setTitle("订单状态");
//            header.add(9,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("DELIVERY_ADDR");
//            hBean.setTitle("收货地址");
//            header.add(10,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("CREATE_TIME");
//            hBean.setTitle("发料单生成日期");
//            header.add(11,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("CLOSE_DATE");
//            hBean.setTitle("订单关闭日期");
//            header.add(12,hBean);
//            
////            hBean = new HeaderBean();
////            hBean.setName("");
////            hBean.setTitle("订单打印日期");
////            header.add(13,hBean);
////            
////            hBean = new HeaderBean();
////            hBean.setName("");
////            hBean.setTitle("打印人");
////            header.add(14,hBean);
//            QuerySet querySet = dao.download(conditions);
//            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
//        } catch (Exception e) {
//            actionContext.setException(e);
//            logger.error(e);
//        }
//    }

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
            BaseResultSet baseResultSet = (BaseResultSet)dao.searchReturnPurchaseApply(pageManager, user, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 订单明细查询
     * @throws Exception
     */
//    public void searchOrderDtl() throws Exception {
//
//        try {
//            // 定义查询分页对象
//            PageManager pageManager = new PageManager();
//            // 将request流中的信息转化为where条件
//            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
//            // BaseResultSet：结果集封装对象
//            BaseResultSet baseResultSet = dao.searchOrderDtl(pageManager,conditions);
//            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
//            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
//            baseResultSet.setFieldDic("IF_SUPPLIER", DicConstant.SF);
//            // 输出结果集，第一个参数”bs”为固定名称，不可修改
//            actionContext.setOutData("bs", baseResultSet);
//        } catch (Exception e) {
//            logger.error(e);
//            actionContext.setException(e);
//        }
//    }
}
