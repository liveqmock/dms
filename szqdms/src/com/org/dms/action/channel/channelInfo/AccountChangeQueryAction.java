package com.org.dms.action.channel.channelInfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.channel.channelInfo.AccountChangeQueryDao;
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
 * 账户异动查询Action
 *
 * 账户异动查询
 * @author zhengyao
 * @date 2014-10-31
 * @version 1.0
 */
public class AccountChangeQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("AccountChangeQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private AccountChangeQueryDao dao = AccountChangeQueryDao.getInstance(atx);
    // 定义reponse对象
    private ResponseWrapper responseWrapper = atx.getResponse();
    // 获取session中的user对象
    private User user = (User) atx.getSession().get(Globals.USER_KEY);

	/**
	 * 
	 * queryListInfo: 账户异动查询  
	 * @author fuxiao Date:2014年10月30日上午10:36:58
	 */
	public void queryListInfo() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
     * 订单查询导出
     * @throws Exception
     */
    public void downloadOem()throws Exception{

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
            hBean.setName("AGENCY_CODE");
            hBean.setTitle("办事处代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AGENCY_NAME");
            hBean.setTitle("办事处名称");
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
            hBean.setName("ACCOUNT_TYPE");
            hBean.setTitle("账户类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("LOG_TYPE");
            hBean.setTitle("操作类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("AMOUNT");
            hBean.setTitle("异动金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SOURCEACCOUNT_TYPE");
            hBean.setTitle("资金来源");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IN_DATE");
            hBean.setTitle("入账日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("REMARKS");
            hBean.setTitle("备注");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CREATE_USER");
            hBean.setTitle("操作人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CREATE_TIME");
            hBean.setTitle("操作时间");
            header.add(hBean);

            QuerySet querySet = dao.downloadOem(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

	/**
	 * 
	 * queryListInfo: 账户异动查询  
	 * @author fuxiao Date:2014年10月30日上午10:36:58
	 */
	public void queryListInfoOem() {
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryListOem(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
