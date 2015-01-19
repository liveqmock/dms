package com.org.dms.action.part.storageMng.search;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.storageMng.search.PartSafesStocksQueryDao;
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
 * ClassName: PartSafesStocksQueryAction   
 * Function: 车厂仓储配件上下限查询
 * date: 2014年10月23日 上午10:40:16  
 * @author fuxiao  
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartSafesStocksQueryAction {
	// 日志类
	private Logger logger = com.org.framework.log.log.getLogger("PartSafesStocksQueryAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private PartSafesStocksQueryDao dao = PartSafesStocksQueryDao.getInstance(atx);

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
			atx.setOutData("bs", this.dao.queryList(pageManager, conditions, user, true));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}

	/**
	 * 
	 * exportExcel: 查询数据导出  
	 * @author fuxiao Date:2014年10月23日
	 *
	 */
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
/*        	hBean = new HeaderBean();
    		hBean.setName("STOCK_CODE");
    		hBean.setTitle("仓库代码");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("STOCK_NAME");
    		hBean.setTitle("仓库名称");
    		header.add(hBean);*/
    		

	    	hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件编号");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		hBean.setWidth(50);
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("UNIT");
    		hBean.setTitle("计量单位");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MM");
    		hBean.setTitle("最小包装");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("UPPER_LIMIT");
    		hBean.setTitle("库存上限");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("LOWER_LIMIT");
    		hBean.setTitle("库存下限");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("USER_NAME");
    		hBean.setTitle("采购员");
    		header.add(hBean);
        	hBean = new HeaderBean();
    		hBean.setName("AREA_CODE");
    		hBean.setTitle("库区代码");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("AREA_NAME");
    		hBean.setTitle("库区名称");
    		header.add(hBean);
    		hBean = new HeaderBean();
    		hBean.setName("AVAILABLE_AMOUNT");
    		hBean.setTitle("可用数量");
    		header.add(hBean);
    		QuerySet qs = dao.queryDownInfo(conditions,user);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "配件上下限数据", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}
}
