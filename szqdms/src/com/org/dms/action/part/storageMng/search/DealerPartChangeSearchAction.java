package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerPartChangeSearchDao;
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

public class DealerPartChangeSearchAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerPartChangeSearchDao dao = DealerPartChangeSearchDao.getInstance(atx);
	    
	    public void partChangeSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				HashMap<String, String> hm = RequestUtil.getValues(request);
				String BEGIN_DATE = hm.get("BEGIN_DATE");
				String END_DATE = hm.get("END_DATE");
				BaseResultSet bs = dao.partChangeSearch(page,user,conditions,BEGIN_DATE,END_DATE);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void download()throws Exception{

	        try {
	        	//获取封装后的request对象
	        	
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
				PageManager page = new PageManager();
	            // 将request流中的信息转化为where条件
				page.setPageRows(999999999);
	            String conditions = RequestUtil.getConditionsWhere(request, page);
//	            HashMap<String, String> hm = RequestUtil.getValues(request);
				String BEGIN_DATE = Pub.val(request, "BEGIN_DATE");
				String END_DATE = Pub.val(request, "END_DATE");
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
	            hBean.setName("ORG_CODE");
	            hBean.setTitle("渠道代码");
	            header.add(2,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORG_NAME");
	            hBean.setTitle("渠道名称");
	            header.add(3,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("BEGIN_COUNT");
	            hBean.setTitle("期初库存数");
	            header.add(4,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PERIOD_IN_COUNT");
	            hBean.setTitle("本期入库数");
	            header.add(5,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PERIOD_OUT_COUNT");
	            hBean.setTitle("本期出库数");
	            header.add(6,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("END_COUNT");
	            hBean.setTitle("期末库存数");
	            header.add(7,hBean);
	            QuerySet querySet = dao.download(conditions,user,BEGIN_DATE,END_DATE);
	            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "PJCRKCX", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }

}
