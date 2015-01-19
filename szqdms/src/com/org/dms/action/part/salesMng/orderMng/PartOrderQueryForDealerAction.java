package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderQueryForDealerDao;
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
 * 订单查询Action
 *
 * 订单查询
 * @author zhengyao
 * @date 2014-10-23
 * @version 1.0
 */
public class PartOrderQueryForDealerAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private PartOrderQueryForDealerDao dao = PartOrderQueryForDealerDao.getInstance(actionContext);
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
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单编号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_TYPE");
            hBean.setTitle("订单类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("APPLY_DATE");
            hBean.setTitle("申请日期");
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
            hBean.setName("SALEUSER_NAME");
            hBean.setTitle("销售员");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_AMOUNT");
            hBean.setTitle("订单金额(元)");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("REAL_AMOUNT");
            hBean.setTitle("实发金额(元)");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_DATE");
            hBean.setTitle("订单审核日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORDER_STATUS");
            hBean.setTitle("订单状态");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SHIP_STATUS");
            hBean.setTitle("订单发运状态");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("COUNTRY_NAME");
            hBean.setTitle("收货地址(省/室/区/县)");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DELIVERY_ADDR");
            hBean.setTitle("收货地址");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CREATE_TIME");
            hBean.setTitle("发料单生成日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CLOSE_DATE");
            hBean.setTitle("订单关闭日期");
            header.add(hBean);
            
//            hBean = new HeaderBean();
//            hBean.setName("");
//            hBean.setTitle("订单打印日期");
//            header.add(13,hBean);
//            
//            hBean = new HeaderBean();
//            hBean.setName("");
//            hBean.setTitle("打印人");
//            header.add(14,hBean);
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 订单查询
     * @throws Exception
     */
    public void searchOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrder(pageManager, user, conditions, true);
            // 订单类型
            baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
            // 订单状态
            baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
            // 订单发运状态
            baseResultSet.setFieldDic("SHIP_STATUS", DicConstant.DDFYZT);
            // 发运方式
            baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);
            // 提报日期
            baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
            // 订单审核日期
            baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
            // 发料单生成日期
            baseResultSet.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
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
    public void searchOrderDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrderDtl(pageManager,conditions);
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
            baseResultSet.setFieldDic("IF_SUPPLIER", DicConstant.SF);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
}
