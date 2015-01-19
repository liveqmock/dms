package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.orderMng.PartDelayOrderQueryMngDao;
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
public class PartDelayOrderQueryMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private PartDelayOrderQueryMngDao dao = PartDelayOrderQueryMngDao.getInstance(actionContext);
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
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_STATUS");
            hBean.setTitle("配件状态");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("配件单位");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("USER_ACCOUNT");
            hBean.setTitle("库管员");
            header.add(4,hBean);

            hBean = new HeaderBean();
            hBean.setName("WAREHOUSE_NAME");
            hBean.setTitle("仓库");
            header.add(5,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_COUNT");
            hBean.setTitle("未执行订单需求数量");
            header.add(6,hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("可用库存");
            header.add(7,hBean);

            hBean = new HeaderBean();
            hBean.setName("MIN_PACK");
            hBean.setTitle("最小包装");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("COUNT");
            hBean.setTitle("当前缺件数量");
            header.add(9,hBean);
            
/*            String where = "";
            QuerySet querySet1 = dao.getWhere(user);
            if (querySet1.getRowCount() > 0) {
            	where = querySet1.getString(1, "T");
            }*/

            QuerySet querySet = dao.download(conditions);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "YQDDHZ", header, querySet);
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
            // String where = "";
            // QuerySet querySet = dao.getWhere(user);
            // if (querySet.getRowCount() > 0) {
            //	where = querySet.getString(1, "T");
            // }
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrder(pageManager, user, conditions, "");
            // 单位
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            // 最小计量单位
            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
            // 配件状态
            baseResultSet.setFieldDic("PART_STATUS", DicConstant.PJZT);
            baseResultSet.setFieldUserID("USER_ACCOUNT");
            // 订单类型
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
