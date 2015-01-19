package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.InvoiceEntustQueryDao;
import com.org.framework.Globals;
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
 * 
 * ClassName: ShipmentsRequirementAction 
 * Function: 发货满足率查询统计
 * date: 2014年10月27日 上午10:02:48
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class InvoiceEntustQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("InvoiceEntustQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 获取session中的user对象
    private User user = (User) atx.getSession().get(Globals.USER_KEY);
	// 定义dao对象
	private InvoiceEntustQueryDao dao = InvoiceEntustQueryDao.getInstance(atx);
	// 定义reponse对象
    private ResponseWrapper responseWrapper = atx.getResponse();

    /**
     * 订单查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ENTRUST_NO");
            hBean.setTitle("委托单号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("PRINT_DATE");
            hBean.setTitle("打印日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("USER_ACCOUNT");
            hBean.setTitle("经办人");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("TARIFF_TYPE");
            hBean.setTitle("发票类别");
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
            hBean.setName("ADDRESS");
            hBean.setTitle("地址");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("TELEPHONE");
            hBean.setTitle("电话");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("TARIFF_NO");
            hBean.setTitle("税号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OPEN_BANK");
            hBean.setTitle("开户行");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("BANK_ACCOUNT");
            hBean.setTitle("账号");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_INVOICE_AMOUNT");
            hBean.setTitle("合计金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("STATUS");
            hBean.setTitle("状态");
            header.add(hBean);
            
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "发票委托单导出", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 * 
	 * queryListInfo: 表单查询  
	 * @author fuxiao Date:2014年10月23日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	public void deleteInvoiceEntust(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			HashMap<String, String> hm;
            hm = RequestUtil.getValues(requestWrapper);
            String entustId=Pub.val(requestWrapper, "ENTRUST_ID");
			dao.deleteEntust(entustId, user);
			atx.setOutMsg("", "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
