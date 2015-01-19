package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.PartAcceptReferenceMngDao;
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

public class PartAcceptReferenceMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "PartAcceptReferenceMngAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PartAcceptReferenceMngDao dao = PartAcceptReferenceMngDao.getInstance(atx);
	    
	    public void acceptSearch() throws Exception {

	        try {
	        	RequestWrapper request = atx.getRequest();
		        User user = (User) atx.getSession().get(Globals.USER_KEY);
				PageManager page = new PageManager();
	            Map<String, String> hm = RequestUtil.getValues(request);
	            String warehouseId = hm.get("WAREHOUSE_ID");
	            String warehouseType = hm.get("WAREHOUSE_TYPE");
	            String conditions = RequestUtil.getConditionsWhere(request,page);
	            BaseResultSet baseResultSet = dao.acceptSearch(page, user,warehouseId,warehouseType,conditions);
	            atx.setOutData("bs", baseResultSet);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
	    //
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
	            hBean.setName("PART_CODE");
	            hBean.setTitle("配件代码");
	            header.add(0,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("PART_NAME");
	            hBean.setTitle("配件名称");
	            hBean.setWidth(50);
	            header.add(1,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("UPPER_LIMIT");
	            hBean.setTitle("库存上限");
	            header.add(2,hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("AVAILABLE_AMOUNT");
	            hBean.setTitle("可用数量");
	            header.add(3,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("ORDER_COUNT");
	            hBean.setTitle("延期订单需求数量");
	            header.add(4,hBean);

	            hBean = new HeaderBean();
	            hBean.setName("WILL_ACCEPT_COUNT");
	            hBean.setTitle("可验收数量");
	            header.add(5,hBean);
	            
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
/*	            Map<String, String> hm = RequestUtil.getValues(request);
	            String warehouseId = hm.get("WAREHOUSE_ID");
	            String warehouseType = hm.get("WAREHOUSE_TYPE");*/
	            String warehouseId = Pub.val(request, "WAREHOUSE_ID");
	            String warehouseType = Pub.val(request, "WAREHOUSE_TYPE");
	            QuerySet querySet = dao.download(warehouseId,warehouseType,conditions);
	            ExportManager.exportFile(atx.getResponse().getHttpResponse(), "参考表", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }

}
