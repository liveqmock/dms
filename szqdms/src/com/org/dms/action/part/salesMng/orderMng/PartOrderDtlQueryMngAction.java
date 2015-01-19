package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderDtlQueryMngDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 订单明细查询Action
 *
 * 订单明细查询
 * @author zhengyao
 * @date 2014-10-23
 * @version 1.0
 */
public class PartOrderDtlQueryMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private PartOrderDtlQueryMngDao dao = PartOrderDtlQueryMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义reponse对象
    private ResponseWrapper responseWrapper = actionContext.getResponse();

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
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT_PRICE");
            hBean.setTitle("单价(元)");
            header.add(hBean);

//            hBean = new HeaderBean();
//            hBean.setName("SALE_PRICE");
//            hBean.setTitle("经销商价(元)");
//            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_USER");
            hBean.setTitle("审核员");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CLOSE_DATE");
            hBean.setTitle("订单关闭日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("渠道名称");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_COUNT");
            hBean.setTitle("订单数量");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("DELIVERY_COUNT");
            hBean.setTitle("实发数量");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("计量单位");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("实发金额（元）");
            header.add(hBean);
            
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 订单明细查询
     * @throws Exception
     */
    public void searchOrderDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrderDtl(pageManager, user, conditions);
            // 单位
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            
            // 订单关闭日期
            baseResultSet.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
            
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
