package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.MoneyRemitQueryDao;
import com.org.framework.Globals;
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
 * 
 * ClassName: MoneyRemitQueryAction 
 * Function: 回款信息查询
 * date: 2014年10月24日 下午7:10:10
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class MoneyRemitQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("MoneyRemitQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private MoneyRemitQueryDao dao = MoneyRemitQueryDao.getInstance(atx);
    // 定义reponse对象
    private ResponseWrapper responseWrapper = atx.getResponse();
    // 获取session中的user对象
    private User user = (User) atx.getSession().get(Globals.USER_KEY);

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
            hBean.setName("REMIT_ID");
            hBean.setTitle("流水号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AGENCY_CODE");
            hBean.setTitle("办事处编号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AGENCY_NAME");
            hBean.setTitle("办事处");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("配送中心编号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("配送中心");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT_TYPE");
            hBean.setTitle("票据种类");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("REMIT_STATUS_NAME");
            hBean.setTitle("入账状态");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("DRAFT_NO");
            hBean.setTitle("承兑汇票号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("BILL_AMOUNT");
            hBean.setTitle("票据金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("TO_ACCOUNT_DATE");
            hBean.setTitle("入账日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("FILIING_DATE");
            hBean.setTitle("录入日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("UPDATE_USER");
            hBean.setTitle("确认人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("UPDATE_TIME");
            hBean.setTitle("确认日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REMARK");
            hBean.setTitle("备注");
            header.add(hBean);

            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
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

}
