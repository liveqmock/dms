package com.org.dms.action.part.storageMng.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.DealerStockForDealerQueryDao;
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
 * @ClassName: DealerStockForDealerQueryAction 
 * @Function: 渠道信息查询 - 仓储相关 - 库存查询Action
 * @date: 2014年9月10日 下午3:01:32
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DealerStockForDealerQueryAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger("DealerStockForDealerQueryAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DealerStockForDealerQueryDao  dao = DealerStockForDealerQueryDao.getInstance(atx);
	
    /**
     * 库存查询
     * @Title: queryListInfo
     * @author fuxiao
     * @since JDK 1.6
     */
	public void queryListInfo(){
		try {
			RequestWrapper request = atx.getRequest();
			PageManager page = new PageManager();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(request, page);
			atx.setOutData("bs", this.dao.queryList(page, conditions, user));
		} catch (Exception e) {
			 logger.error(e);
             atx.setException(e);
		}
		
	}
	
	/**
	 * 
	 * @Title: download
	 * @Description: 下载
	 * @throws Exception
	 * @return: void
	 */
	 public void download()throws Exception{
	        try {
	        	RequestWrapper request = atx.getRequest();
	        	ResponseWrapper responseWrapper = atx.getResponse();
	        	
	        	 User user = (User) atx.getSession().get(Globals.USER_KEY);
	        	// 定义查询分页对象
	            PageManager pageManager = new PageManager();
	            // 将request流中的信息转化为where条件
	            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
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
	            hBean.setName("AMOUNT");
	            hBean.setTitle("库存数量");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("OCCUPY_AMOUNT");
	            hBean.setTitle("占用数量");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("AVAILABLE_AMOUNT");
	            hBean.setTitle("可用数量");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("SALE_PRICE");
	            hBean.setTitle("销售价格(元)");
	            header.add(hBean);
	            
	            QuerySet querySet = dao.download(conditions,user);
	            ExportManager.exportFile(responseWrapper.getHttpResponse(), "订单导出", header, querySet);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
}
