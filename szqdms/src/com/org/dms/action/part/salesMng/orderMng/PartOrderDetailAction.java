package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.salesMng.orderMng.PartOrderDetailDao;
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

public class PartOrderDetailAction {
	private Logger logger = com.org.framework.log.log.getLogger("PartOrderDetailAction");
    private ActionContext atx = ActionContext.getContext();
    private PartOrderDetailDao dao = PartOrderDetailDao.getInstance(atx);
    // 定义reponse对象
    private ResponseWrapper responseWrapper = atx.getResponse();

    /**
     * 订单明细查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            String orderId = Pub.val(request, "ORDER_ID");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单编号");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("计量单位");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("MINI");
            hBean.setTitle("最小包装");
            header.add(4,hBean);

            hBean = new HeaderBean();
            hBean.setName("UNIT_PRICE");
            hBean.setTitle("经销商价");
            header.add(5,hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商");
            header.add(6,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_COUNT");
            hBean.setTitle("订购数量");
            header.add(7,hBean);

            hBean = new HeaderBean();
            hBean.setName("AUDIT_COUNT");
            hBean.setTitle("审核数量");
            header.add(8,hBean);

            hBean = new HeaderBean();
            hBean.setName("DELIVERY_COUNT");
            hBean.setTitle("发运数量");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SIGN_COUNT");
            hBean.setTitle("签收数量");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(11,hBean);
            
            QuerySet querySet = dao.download(orderId);
            QuerySet querySet2 = dao.getOrgName(orderId);
            String orgName = "订单明细";
            if (querySet2.getRowCount() > 0) {
	            if (!"0".equals(orgName)) {
	            	orgName = querySet2.getString(1, "ORG_NAME");
	            }
            }
            ExportManager.exportFile(responseWrapper.getHttpResponse(), orgName, header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 
     * @date()2014年10月13日下午3:10:55
     * @author Administrator
     * @to_do:订单主信息查询
     * @throws Exception
     */
    public void saleOrderInfoSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			String ORDER_ID = Pub.val(request, "ORDER_ID");
			BaseResultSet bs = dao.saleOrderInfoSearch(page,user,conditions,ORDER_ID);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 
     * @date()2014年10月13日下午3:11:08
     * @author Administrator
     * @to_do:资金可用余额查询
     * @throws Exception
     */
    public void accountSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager page = new PageManager();
        String orgId = request.getParamValue("orgId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.accountSearch(page, orgId, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 
     * @date()2014年10月13日下午3:11:51
     * @author Administrator
     * @to_do:订单资金使用查询
     * @throws Exception
     */
    public void orderFundsListSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderFundsListSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 
     * @date()2014年10月13日下午3:12:50
     * @author Administrator
     * @to_do:订单审核记录查询
     * @throws Exception
     */
    public void orderCheckListSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderCheckListSearch(page, user, conditions,orderId);
            bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
        	bs.setFieldUserID("CHECK_USER");
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    public void partOrderDetailSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
//        String pchOrderNo = request.getParamValue("pchOrderNo");
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        page.setPageRows(10000);
        try {
            BaseResultSet bs = dao.partOrderDetailSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}
