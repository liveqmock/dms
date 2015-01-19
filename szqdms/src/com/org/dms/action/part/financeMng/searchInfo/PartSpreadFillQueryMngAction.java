package com.org.dms.action.part.financeMng.searchInfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.searchInfo.PartSpreadFillQueryMngDao;
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
 * 配件价差调整查询Action
 *
 * 配件价差调整查询
 * @author zhengyao
 * @date 2014-11-05
 * @version 1.0
 */
public class PartSpreadFillQueryMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private PartSpreadFillQueryMngDao dao = PartSpreadFillQueryMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义reponse对象
    private ResponseWrapper responseWrapper = actionContext.getResponse();

    /**
     * 配件价差调整导出
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
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道名称");
            header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("渠道名称");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("FILL_TYPE");
            hBean.setTitle("应补类型");
            header.add(4,hBean);

            hBean = new HeaderBean();
            hBean.setName("PRICE_DATE");
            hBean.setTitle("调价时间");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OLD_PRICE");
            hBean.setTitle("原销售价格");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("NEW_PRICE");
            hBean.setTitle("调整后价格");
            header.add(7,hBean);

            hBean = new HeaderBean();
            hBean.setName("SPREAD");
            hBean.setTitle("价差(元)");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DELIVERY_COUNT");
            hBean.setTitle("发货数量");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CHANEL_COUNT");
            hBean.setTitle("出渠道数量");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALE_COUNT");
            hBean.setTitle("出终端数量");
            header.add(11,hBean);

            hBean = new HeaderBean();
            hBean.setName("FILL_COUNT");
            hBean.setTitle("应补数量");
            header.add(12,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("FILL_AMOUNT");
            hBean.setTitle("应补金额(元)");
            header.add(13,hBean);
            
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "JCYBCX", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件价差调整查询
     * @throws Exception
     */
    public void searchOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrder(pageManager, user, conditions);
            baseResultSet.setFieldDateFormat("PRICE_DATE", "yyyy-MM-dd HH:mm:ss");
            baseResultSet.setFieldDic("FILL_TYPE", DicConstant.JCLX);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    public void searchOrderDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrderDtl(pageManager, user, conditions);
            baseResultSet.setFieldDateFormat("PRICE_DATE", "yyyy-MM-dd HH:mm:ss");
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}
