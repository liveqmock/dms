package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.OutBillModSearchMngDao;
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

public class OutBillModSearchMngAction {
	
	private Logger logger = com.org.framework.log.log.getLogger(
	        "PartAcceptReferenceMngAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private OutBillModSearchMngDao dao = OutBillModSearchMngDao.getInstance(atx);
	
	
	
	public void outBillModSearch() throws Exception {
        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(request,page);
            BaseResultSet baseResultSet = dao.outBillModSearch(page, user,conditions);
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	
	public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单编号");
            header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道代码");
            header.add(1,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("渠道名称");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("OLD_COUNT");
            hBean.setTitle("原数量");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MOD_COUNT");
            hBean.setTitle("调整后数量");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DEF_COUNT");
            hBean.setTitle("差异数量");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MOD_DATE");
            hBean.setTitle("调整时间");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MOD_MAN");
            hBean.setTitle("调整人");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("KEEP_MAN");
            hBean.setTitle("库管员");
            header.add(10,hBean);
            
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            QuerySet querySet = dao.download(conditions);
            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "出库单调整表", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}
