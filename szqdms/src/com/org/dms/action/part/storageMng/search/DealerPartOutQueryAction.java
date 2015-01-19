package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerPartOutQueryDao;
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

public class DealerPartOutQueryAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerPartOutQueryDao dao = DealerPartOutQueryDao.getInstance(atx);
	    
	    public void partOutSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request,page);
	        try {
	            BaseResultSet bs = dao.partOutSearch(page,user,conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    /**
	     * 配件出库查询导出
	     * @throws Exception
	     */
	    public void partOutDownload()throws Exception{
	    	 User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	        	// 定义查询分页对象
	        	PageManager page = new PageManager();
	            // 将request流中的信息转化为where条件
	            String conditions = RequestUtil.getConditionsWhere(request, page);
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
	            hBean.setName("SUPPLIER_CODE");
	            hBean.setTitle("供应商代码");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("SUPPLIER_NAME");
	            hBean.setTitle("供应商名称");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORG_CODE");
	            hBean.setTitle("接收方代码");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("ORG_NAME");
	            hBean.setTitle("接收方名称");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("OUT_NO");
	            hBean.setTitle("单号");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("STORAGE_TYPE");
	            hBean.setTitle("出库类型");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("COUNT");
	            hBean.setTitle("数量");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PRICE");
	            hBean.setTitle("单价");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("AMOUNT");
	            hBean.setTitle("金额");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("APPLY_DATE");
	            hBean.setTitle("出库日期");
	            header.add(hBean);
	            
	            QuerySet querySet = dao.partOutDownload(conditions,user);
	            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "PJCKCX", header, querySet);
	        } catch (Exception e) {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void partInSearch() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PageManager page = new PageManager();
	        String conditions = RequestUtil.getConditionsWhere(request,page);
	        try {
	            BaseResultSet bs = dao.partInSearch(page,user,conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    
	    /**
	     * 配件入库查询导出
	     * @throws Exception
	     */
	    public void partInDownload()throws Exception{
	    	 User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	        	// 定义查询分页对象
	        	PageManager page = new PageManager();
	            // 将request流中的信息转化为where条件
	            String conditions = RequestUtil.getConditionsWhere(request, page);
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
	            hBean.setName("SUPPLIER_CODE");
	            hBean.setTitle("供应商代码");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("SUPPLIER_NAME");
	            hBean.setTitle("供应商名称");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORG_CODE");
	            hBean.setTitle("发/退货方代码");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("ORG_NAME");
	            hBean.setTitle("发/退货方名称");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("IN_NO");
	            hBean.setTitle("单号");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("STORAGE_TYPE");
	            hBean.setTitle("入库类型");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("COUNT");
	            hBean.setTitle("数量");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PRICE");
	            hBean.setTitle("单价");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("AMOUNT");
	            hBean.setTitle("金额");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("APPLY_DATE");
	            hBean.setTitle("入库日期");
	            header.add(hBean);
	            
	            QuerySet querySet = dao.partInDownload(conditions,user);
	            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "PJRKCX", header, querySet);
	        } catch (Exception e) {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
